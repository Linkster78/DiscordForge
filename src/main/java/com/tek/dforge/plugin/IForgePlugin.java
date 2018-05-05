package com.tek.dforge.plugin;

public interface IForgePlugin { 
	
	/*
	 * Gets the name of the plugin
	 * 
	 * @return Plugin name
	 */
	public String getPluginName();
	
	/*
	 * Gets the author of the plugin
	 * 
	 * @return Plugin author
	 */
	public String getPluginAuthor();
	
	/*
	 * Gets the version of the plugin
	 * 
	 * @return Plugin version
	 */
	public String getPluginVersion();
	
	/*
	 * Takes care of enabling everything
	 */
	public void enable();
	
}
