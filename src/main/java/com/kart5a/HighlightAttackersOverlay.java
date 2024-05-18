package com.kart5a;

import javax.inject.Inject;
import java.awt.*;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;

public class HighlightAttackersOverlay extends Overlay {
    private final Client client;
    private final HighlightAttackersPlugin plugin;
    private final HighlightAttackersConfig config;
    private final ModelOutlineRenderer outlineRenderer;

    @Inject
    private HighlightAttackersOverlay(Client client, HighlightAttackersPlugin plugin, HighlightAttackersConfig config, ModelOutlineRenderer outlineRenderer) {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        this.outlineRenderer = outlineRenderer;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        setPriority(OverlayPriority.HIGH);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        for (NPC npc : plugin.getAttackers()) {
            outlineRenderer.drawOutline(npc, config.borderWidth(), config.borderColor(), config.borderFeather());
        }
        return null;
    }
}
