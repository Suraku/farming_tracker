package com.example;

import com.example.ui.FarmingTrackerPanel;
import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.StatChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.util.ImageUtil;

import java.awt.image.BufferedImage;

@Slf4j
@PluginDescriptor(
	name = "Example"
)
public class ExamplePlugin extends Plugin
{
	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private ItemManager itemManager;

	@Inject
	private Client client;

	@Inject
	private ExampleConfig config;

	private NavigationButton navButton;
	private FarmingTrackerPanel panel;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Example started!");

		panel = new FarmingTrackerPanel(itemManager, this);

		final BufferedImage header = ImageUtil.getResourceStreamFromClass(getClass(), "panel_icon.png");
		panel.loadHeaderIcon(header);
		final BufferedImage icon = ImageUtil.getResourceStreamFromClass(getClass(), "panel_icon.png");

		navButton = NavigationButton.builder()
				.tooltip("Farming Tracker")
				.icon(icon)
				.priority(5)
				.panel(panel)
				.build();

		clientToolbar.addNavigation(navButton);
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Example stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}

	@Subscribe
	void onStatChanged(StatChanged event)
	{
		if (event.getSkill().name().toLowerCase().equals("crafting"))
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Y'all play r00nscape. Crafting: " + Integer.toString(event.getXp()), null);
		}
	}

	@Provides
	ExampleConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ExampleConfig.class);
	}
}
