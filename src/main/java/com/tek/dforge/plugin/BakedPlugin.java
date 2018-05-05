package com.tek.dforge.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.jar.JarEntry;

import com.tek.dforge.config.ConfigEntry;
import com.tek.dforge.config.ConfigSerializer;
import com.tek.dforge.core.DiscordForge;
import com.tek.dforge.util.FileUtil;
import com.tek.dforge.util.Reference;

public class BakedPlugin {
	
	private boolean enabled;
	private IForgePlugin mainClass;
	private ArrayList<JarEntry> resources;
	private Properties config;
	
	public BakedPlugin() {
		resources = new ArrayList<JarEntry>();
		config = new Properties();
		enabled = true;
	}
	
	public void setMainClass(Class<? extends IForgePlugin> mainClass) {
		try {
			this.mainClass = mainClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) { e.printStackTrace(); }
	}
	
	public void disable() {
		saveConfig();
	}
	
	public IForgePlugin getMainClass() {
		return mainClass;
	}
	
	public Properties getConfig() {
		return config;
	}
	
	public void addEntry(JarEntry entry) {
		resources.add(entry);
	}
	
	public void initConfig() {
		File configFile = new File(Reference.FOLDER_CONFIG + "/" + this.getMainClass().getPluginName() + ".cfg");
		FileUtil.createFile(configFile);
		
		try {
			config.load(new FileInputStream(configFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(ConfigEntry entry : DiscordForge.getInstance().getConfigManager().getEntries(this.getMainClass())) {
			if(config.containsKey(entry.id)) {
				entry.updateValue(ConfigSerializer.decode(entry.type, config.getProperty(entry.id)));
			}else {
				entry.updateValue(entry.type.getDefaultValue());
			}
		}
	}
	
	public void saveConfig() {
		File configFile = new File(Reference.FOLDER_CONFIG + "/" + this.getMainClass().getPluginName() + ".cfg");
		
		for(ConfigEntry entry : DiscordForge.getInstance().getConfigManager().getEntries(this.getMainClass())) {
			try {
				if(entry.field.get(entry.instance) != null) {
					config.setProperty(entry.id, ConfigSerializer.encode(entry.type, entry.field.get(entry.instance)));
				}
			} catch (IllegalArgumentException | IllegalAccessException e) { }
		}
		
		try {
			config.store(new FileOutputStream(configFile), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	@Override
	public String toString() {
		return this.getMainClass().getPluginName();
	}
	
}
