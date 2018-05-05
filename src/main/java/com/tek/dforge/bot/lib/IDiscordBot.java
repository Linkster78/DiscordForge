package com.tek.dforge.bot.lib;

import javax.security.auth.login.LoginException;

import com.tek.dforge.bot.lib.events.Events;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Game.GameType;

public interface IDiscordBot {
	
	public void finishedLoading();
	
	public void setJDA(JDA jda);
	public JDA getJDA();
	
	public default void start(String token, String presence) {
		JDABuilder builder = new JDABuilder(AccountType.BOT).setToken(token).setEnableShutdownHook(true);
		
		builder.addEventListener(new Events(this));
		
		try {
			setJDA(builder.buildAsync());
			getJDA().getPresence().setGame(Game.of(GameType.DEFAULT, presence));
		} catch (LoginException e) { }
	}
	
	public default void stop() {
		if(getJDA() != null) {
			getJDA().shutdownNow();
			setJDA(null);
		}
	}
	
}
