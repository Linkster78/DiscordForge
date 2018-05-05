package com.tek.dforge;

import com.tek.dforge.core.DiscordForge;
import com.tek.dforge.ui.GUI;

public class Launcher {
	
	public static void main(String[] args) {
		DiscordForge dforge = new DiscordForge();
		dforge.init();
		
		GUI.ilaunch(args);
	}
	
}
