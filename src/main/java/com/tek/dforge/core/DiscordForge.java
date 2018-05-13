package com.tek.dforge.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.tek.dforge.action.ActionManager;
import com.tek.dforge.bot.core.DiscordBot;
import com.tek.dforge.bot.lib.commands.ICommandHandler;
import com.tek.dforge.config.ConfigManager;
import com.tek.dforge.plugin.IForgePlugin;
import com.tek.dforge.plugin.PluginManager;
import com.tek.dforge.util.FileUtil;
import com.tek.dforge.util.Reference;

import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class DiscordForge {
	
	private static DiscordForge instance;
	private Properties config;
	private PluginManager pluginLoader;
	private ConfigManager configManager;
	private ActionManager actionManager;
	private DiscordBot discordBot;
	
	public DiscordForge() {
		instance = this;
		pluginLoader = new PluginManager();
		configManager = new ConfigManager();
		actionManager = new ActionManager();
		config = new Properties();
		discordBot = new DiscordBot();
	}
	
	public void init() {
		pluginLoader.initFolders();
		pluginLoader.loadAll();
		pluginLoader.enableAll();
		
		initMainConfig();
	}
	
	public void close() {
		pluginLoader.disableAll();
		
		saveMainConfig();
		
		discordBot.stop();
		
		System.exit(0);
	}
	
	public void registerEvent(ListenerAdapter listener, IForgePlugin plugin) {
		discordBot.registerListener(plugin, listener);
	}
	
	public void registerCommand(ICommandHandler command, IForgePlugin plugin) {
		discordBot.registerCommand(plugin, command);
	}
	
	public void initMainConfig() {
		File configFile = new File(Reference.FOLDER_CONFIG + "/" + Reference.FILE_CONFIG);
		FileUtil.createFile(configFile);
		
		try {
			config.load(new FileInputStream(Reference.FOLDER_CONFIG + "/" + Reference.FILE_CONFIG));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(!config.containsKey("primaryColor")) config.setProperty("primaryColor", Reference.PRIMARYCOLOR);
		if(!config.containsKey("secondaryColor")) config.setProperty("secondaryColor", Reference.SECONDARYCOLOR);
		if(!config.containsKey("tertiaryColor")) config.setProperty("tertiaryColor", Reference.TERTIARYCOLOR);
		if(!config.containsKey("token")) config.setProperty("token", "");
		if(!config.containsKey("presence")) config.setProperty("presence", "");
		if(!config.containsKey("prefix")) config.setProperty("prefix", Reference.PREFIX);
	}
	
	public void saveMainConfig() {
		File configFile = new File(Reference.FOLDER_CONFIG + "/" + Reference.FILE_CONFIG);
		
		try {
			config.store(new FileOutputStream(configFile), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public PluginManager getPluginManager() {
		return pluginLoader;
	}
	
	public ConfigManager getConfigManager() {
		return configManager;
	}
	
	public ActionManager getActionManager() {
		return actionManager;
	}
	
	public Properties getConfig() {
		return config;
	}
	
	public DiscordBot getDiscordBot() {
		return discordBot;
	}
	
	public static DiscordForge getInstance() {
		return instance;
	}
	
}
