package com.tek.dforge.bot.lib.util;

import java.net.URL;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;

public class JDAUtil {
	
	public static boolean verifyToken(String token) {
		JDABuilder builder = new JDABuilder(AccountType.BOT).setToken(token);
		
		try {
			builder.buildBlocking();
			return true;
		}catch(Exception e) { return false; }
	}
	
	public static URL imageURL(String url) {
		try {
			String b = "https://cdn.discordapp.com/app-icons/";
			String a = "https://cdn.discordapp.com/avatars/";
			String[] tokens = url.substring(b.length()).split("/");
			String id = tokens[0];
			String ua = tokens[1];
			
			String u = a + id + "/" + ua;
			
			return new URL(u);
		}catch(Exception e) {
			return null;
		}
	}
	
}
