package com.tek.dforge.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import com.tek.dforge.annotations.MainPluginClass;
import com.tek.dforge.core.DiscordForge;
import com.tek.dforge.enums.LoadStatus;
import com.tek.dforge.log.Logger;
import com.tek.dforge.util.FileUtil;
import com.tek.dforge.util.Reference;

@SuppressWarnings({"unchecked", "resource"})
public class PluginManager {
	
	private ArrayList<BakedPlugin> plugins;
	
	public PluginManager() {
		plugins = new ArrayList<BakedPlugin>();
	}
	
	public void loadAll() {
		disableAll();
		
		plugins.clear();
		
		DiscordForge.getInstance().getDiscordBot().getCommandManager().getCommandHandlers().clear();
		DiscordForge.getInstance().getDiscordBot().getListeners().clear();
		
		File folder = new File(Reference.FOLDER_PLUGIN);;
		
		for(File file : folder.listFiles()) {
			if(file.isFile()) {
				if(FileUtil.isJarFile(file)) {
					LoadStatus status = attemptLoad(file);
					
					if(status == LoadStatus.SUCCESS) {
						BakedPlugin plugin = plugins.get(0);
						Logger.log("Loaded BakedPlugin " + plugin.getMainClass().getPluginName());
					}else {
						Logger.log("Error while loading BakedPlugin " + file.getName() + " (" + status.getTextEquivalent() + ")");
					}
				}
			}
		}
	}
	
	public void enableAll() {
		for(BakedPlugin plugin : plugins) {
			plugin.getMainClass().enable();
			plugin.initConfig();
			Logger.log("Enabled BakedPlugin " + plugin.getMainClass().getPluginName() + "");
		}
	}
	
	public void disableAll() {
		for(BakedPlugin plugin : plugins) {
			plugin.disable();
			Logger.log("Disabled BakedPlugin " + plugin.getMainClass().getPluginName() + "");
		}
	}
	
	public LoadStatus attemptLoad(File file) {
		try(JarInputStream jis = new JarInputStream(new FileInputStream(file))) {
			BakedPlugin plugin = new BakedPlugin();
			JarEntry jarEntry;
			int mainCount = 0;
			
			ClassLoader classLoader = new URLClassLoader( new URL[]{ new URL("jar:" + file.toURI().toURL() + "!/") } );
			ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
			Method findLoadedClass = null;
			
			try{
				findLoadedClass = ClassLoader.class.getDeclaredMethod("findLoadedClass", new Class[] { String.class });
			}catch(Exception e) {
				return LoadStatus.REFLECTIONERROR;
			}
			
			findLoadedClass.setAccessible(true);
			
			while((jarEntry = jis.getNextJarEntry()) != null) {
				if(jarEntry.getName().endsWith(".class") && !jarEntry.isDirectory()) {
					String className = jarEntry.getName().substring(0, jarEntry.getName().length() - 6);
					className = className.replace('/', '.');
					Class<?> clazz = null;
					
					try{
						Object loadedClazz = findLoadedClass.invoke(currentClassLoader, className);
						if(loadedClazz == null) {
							clazz = classLoader.loadClass(className);
						}else {
							continue;
						}
					}catch(Exception e) {
						return LoadStatus.UNKNOWNCLASS;
					}
					
					if(clazz.isAnnotationPresent(MainPluginClass.class)) {
						try {
							if(clazz.newInstance() instanceof IForgePlugin) {
								plugin.setMainClass((Class<? extends IForgePlugin>) clazz);
								mainCount++;
							}else {
								return LoadStatus.INVALIDMAIN;
							}
						} catch (InstantiationException | IllegalAccessException e) {
							return LoadStatus.REFLECTIONERROR;
						}
					}
				}else if(!jarEntry.isDirectory()){
					plugin.addEntry(jarEntry);
				}
			}
			
			if(plugin.getMainClass() != null) {
				if(mainCount == 1) {
					plugins.add(0, plugin);
					
					return LoadStatus.SUCCESS;
				}else {
					return LoadStatus.MULTIMAIN;
				}
			}else {
				return LoadStatus.NOMAIN;
			}
		}catch(IOException e) {
			return LoadStatus.ERRORREAD;
		}
	}
	
	public void initFolders() {
		File pluginfolder = new File(Reference.FOLDER_PLUGIN);
		FileUtil.createFolder(pluginfolder);
		
		File configFolder = new File(Reference.FOLDER_CONFIG);
		FileUtil.createFolder(configFolder);
	}
	
	public BakedPlugin getBakedPlugin(String name) {
		for(BakedPlugin plugin : plugins) {
			if(plugin.getMainClass().getPluginName().equals(name)) {
				return plugin;
			}
		}
		
		return null;
	}
	
	public BakedPlugin getBakedPlugin(Class<? extends IForgePlugin> pluginClass) {
		for(BakedPlugin plugin : plugins) {
			if(plugin.getMainClass().getClass().equals(pluginClass)) {
				return plugin;
			}
		}
		
		return null;
	}
	
	public ArrayList<BakedPlugin> getPlugins() {
		return plugins;
	}
	
}
