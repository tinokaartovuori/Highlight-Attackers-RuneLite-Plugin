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
			description = "List of NPC IDs to ignore, separated by commas",
			position = 1
	)
	default String ignoreNpcIds() {
		return "";
	}

	@ConfigItem(
			keyName = "ignoreNpcNames",
			name = "Ignore NPC Names",
			description = "List of NPC Names to ignore, separated by commas",
			position = 2
	)
	default String ignoreNpcNames() {
		return "";
	}

	@ConfigItem(
			keyName = "borderWidth",
			name = "Border Width",
			description = "Width of the border around attacking NPCs",
			position = 3
	)
	default int borderWidth() {
		return 3;
	}

	@ConfigItem(
			keyName = "borderFeather",
			name = "Border Feather",
			description = "Feather of the border around attacking NPCs",
			position = 4
	)
	default int borderFeather() {
		return 3;
	}

	@Alpha
	@ConfigItem(
			keyName = "focusingBorderColor",
			name = "Focusing Color",
			description = "Color of the border around NPCs focusing on the player",
			position = 5
	)
	default Color focusingBorderColor() {
		return new Color(255, 255, 255, 80);  // Default to semi-transparent red
	}

	@Alpha
	@ConfigItem(
			keyName = "attackingBorderColor",
			name = "Attacking Color",
			description = "Color of the border around NPCs attacking the player",
			position = 6
	)
	default Color attackingBorderColor() {
		return new Color(255, 0, 0, 128);  // Default to semi-transparent green
	}
}
