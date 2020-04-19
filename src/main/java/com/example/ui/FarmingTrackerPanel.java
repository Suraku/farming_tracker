package com.example.ui;

import javax.inject.Singleton;
import javax.swing.*;

import com.example.ExamplePlugin;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.PluginPanel;

import java.awt.image.BufferedImage;

@Singleton
public class FarmingTrackerPanel extends PluginPanel
{
    private final JLabel overallIcon = new JLabel();

    public FarmingTrackerPanel(final ItemManager itemManager, ExamplePlugin plugin)
    {

    }

    /**
     * loads an img to the icon on the header
     *
     * @param img the img for the header icon
     */
    public void loadHeaderIcon(BufferedImage img)
    {
        overallIcon.setIcon(new ImageIcon(img));
    }
}