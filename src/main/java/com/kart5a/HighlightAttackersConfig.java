package com.kart5a;

import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.awt.Color;

@ConfigGroup("highlightattackers")
public interface HighlightAttackersConfig extends Config {
	@ConfigItem(
			keyName = "ignoreNpcIds",
			name = "Ignore NPC IDs",
			description = "List of NPC IDs to ignore, separated by commas"
	)
	default String ignoreNpcIds() {
		return "";
	}

	@ConfigItem(
			keyName = "ignoreNpcNames",
			name = "Ignore NPC Names",
			description = "List of NPC Names to ignore, separated by commas"
	)
	default String ignoreNpcNames() {
		return "";
	}

	@ConfigItem(
			keyName = "borderWidth",
			name = "Border Width",
			description = "Width of the border around attacking NPCs"
	)
	default int borderWidth() {
		return 2;
	}

	@ConfigItem(
			keyName = "borderFeather",
			name = "Border Feather",
			description = "Feather of the border around attacking NPCs"
	)
	default int borderFeather() {
		return 2;
	}

	@Alpha
	@ConfigItem(
			keyName = "borderColor",
			name = "Border Color",
			description = "Color of the border around attacking NPCs",
			position = 4
	)
	default Color borderColor() {
		return new Color(255, 0, 0, 128);  // Default to semi-transparent red
	}
}
