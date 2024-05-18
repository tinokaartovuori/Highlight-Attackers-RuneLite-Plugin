package com.kart5a;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class HighlightAttackersPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(HighlightAttackersPlugin.class);
		RuneLite.main(args);
	}
}