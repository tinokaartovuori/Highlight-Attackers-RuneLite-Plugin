package com.kart5a;

import com.google.inject.Provides;
import javax.inject.Inject;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@PluginDescriptor(
		name = "Highlight Attackers"
)
public class HighlightAttackersPlugin extends Plugin {
	@Inject
	private Client client;

	@Inject
	private HighlightAttackersConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private HighlightAttackersOverlay overlay;

	@Getter
	private Set<NPC> focusingNpcs = new HashSet<>();
	@Getter
	private Set<NPC> attackingNpcs = new HashSet<>();
	private Set<Integer> ignoredNpcIds = new HashSet<>();
	private Set<String> ignoredNpcNames = new HashSet<>();

	@Override
	protected void startUp() throws Exception {
		overlayManager.add(overlay);
		parseIgnoredNpcIds();
		parseIgnoredNpcNames();
		log.info("Highlight Attackers started!");
	}

	@Override
	protected void shutDown() throws Exception {
		overlayManager.remove(overlay);
		focusingNpcs.clear();
		attackingNpcs.clear();
		ignoredNpcIds.clear();
		ignoredNpcNames.clear();
		log.info("Highlight Attackers stopped!");
	}

	@Provides
	HighlightAttackersConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(HighlightAttackersConfig.class);
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event) {
		if (event.getGameState() == GameState.LOGGED_IN) {
			focusingNpcs.clear();
			attackingNpcs.clear();
			parseIgnoredNpcIds();
			parseIgnoredNpcNames();
		}
	}

	@Subscribe
	public void onGameTick(GameTick event) {
		updateNpcs();
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event) {
		if (event.getGroup().equals("highlightattackers")) {
			if (event.getKey().equals("ignoreNpcIds")) {
				parseIgnoredNpcIds();
				log.info("Updated ignored NPC IDs: " + ignoredNpcIds);
			} else if (event.getKey().equals("ignoreNpcNames")) {
				parseIgnoredNpcNames();
				log.info("Updated ignored NPC Names: " + ignoredNpcNames);
			}
		}
	}

	private void updateNpcs() {
		focusingNpcs.clear();
		attackingNpcs.clear();
		Player localPlayer = client.getLocalPlayer();
		if (localPlayer == null) {
			return;
		}

		for (NPC npc : client.getNpcs()) {
			if (!ignoredNpcIds.contains(npc.getId()) && !ignoredNpcNames.contains(npc.getName().toLowerCase(Locale.ROOT))) {
				if (npc.getInteracting() == localPlayer) {
					focusingNpcs.add(npc);
					// Assume NPC is damaging the player if it is interacting with the player and has an active animation
					if (npc.isInteracting() && npc.getAnimation() != -1) { // Add more conditions if needed
						attackingNpcs.add(npc);
					}
				}
			}
		}
	}

	private void parseIgnoredNpcIds() {
		ignoredNpcIds = Stream.of(config.ignoreNpcIds().split(","))
				.map(String::trim)
				.filter(s -> !s.isEmpty())
				.map(this::safeParseInt)
				.filter(i -> i != null)
				.collect(Collectors.toSet());
	}

	private void parseIgnoredNpcNames() {
		ignoredNpcNames = Stream.of(config.ignoreNpcNames().split(","))
				.map(String::trim)
				.filter(s -> !s.isEmpty())
				.map(name -> name.toLowerCase(Locale.ROOT))
				.collect(Collectors.toSet());
	}

	private Integer safeParseInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			log.warn("Invalid NPC ID format in ignore list: " + str);
			return null;
		}
	}
}
