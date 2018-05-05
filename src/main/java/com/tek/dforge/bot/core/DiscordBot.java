package com.tek.dforge.bot.core;

import java.awt.image.BufferedImage;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.tek.dforge.bot.lib.IDiscordBot;
import com.tek.dforge.bot.lib.commands.CommandManager;
import com.tek.dforge.bot.lib.commands.ICommandHandler;
import com.tek.dforge.bot.lib.util.JDAUtil;
import com.tek.dforge.core.DiscordForge;
import com.tek.dforge.plugin.BakedPlugin;
import com.tek.dforge.plugin.IForgePlugin;
import com.tek.dforge.ui.MainController;
import com.tek.dforge.util.Reference;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import net.dv8tion.jda.bot.entities.ApplicationInfo;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Game.GameType;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class DiscordBot implements IDiscordBot{

	private JDA jda;
	private HashMap<ListenerAdapter, BakedPlugin> listeners;
	private CommandManager commandManager;
	
	public DiscordBot() {
		listeners = new HashMap<ListenerAdapter, BakedPlugin>();
		commandManager = new CommandManager();
	}
	
	public void start() {
		start(DiscordForge.getInstance().getConfig().getProperty("token"), DiscordForge.getInstance().getConfig().getProperty("presence"));
	}
	
	public void registerListener(IForgePlugin plugin, ListenerAdapter listener) {
		listeners.put(listener, DiscordForge.getInstance().getPluginManager().getBakedPlugin(plugin.getClass()));
	}
	
	public void registerCommand(IForgePlugin plugin, ICommandHandler commandHandler) {
		commandManager.getCommandHandlers().put(commandHandler, DiscordForge.getInstance().getPluginManager().getBakedPlugin(plugin.getClass()));
	}
	
	public void updatePresence(String presence) {
		if(jda != null) jda.getPresence().setGame(Game.of(GameType.DEFAULT, presence));
	}
	
	@Override
	public void finishedLoading() {
		MainController.getInstance().btnStart.getStyleClass().add("disabled");
		MainController.getInstance().btnStop.getStyleClass().remove("disabled");
		
		ApplicationInfo appinfo = jda.asBot().getApplicationInfo().complete();
		URL img = JDAUtil.imageURL(appinfo.getIconUrl());
		
		if(img != null) {
			try {
				HttpURLConnection hc;
				hc = (HttpURLConnection) img.openConnection();
				
				hc.setDoOutput(true);
				hc.setDoInput(true);
				hc.setUseCaches(false);
				hc.setRequestProperty("Authorization", jda.getToken());
				hc.setRequestProperty("User-Agent", Reference.USERAGENT);
				hc.setRequestProperty("Cookie", Reference.COOKIE);
				hc.setRequestMethod("GET");
				
				BufferedImage image = ImageIO.read(hc.getInputStream());
				MainController.getInstance().imageDiscord.setImage(SwingFXUtils.toFXImage(image, null));
			}catch(Exception e) { e.printStackTrace(); }
		}
		
		Platform.runLater(() -> {
			MainController.getInstance().lblName.setText(appinfo.getName());
		});
	}
	
	@Override
	public void setJDA(JDA jda) {
		this.jda = jda;
	}
	
	@Override
	public JDA getJDA() {
		return jda;
	}
	
	public HashMap<ListenerAdapter, BakedPlugin> getListeners() {
		return listeners;
	}
	
	public CommandManager getCommandManager() {
		return commandManager;
	}
	
}
