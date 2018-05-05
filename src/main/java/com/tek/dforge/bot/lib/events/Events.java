package com.tek.dforge.bot.lib.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.tek.dforge.bot.lib.IDiscordBot;
import com.tek.dforge.bot.lib.commands.Command;
import com.tek.dforge.core.DiscordForge;

import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Events extends ListenerAdapter{
	
	private IDiscordBot bot;
	
	public Events(IDiscordBot bot) {
		this.bot = bot;
	}
	
	@Override
	public void onReady(ReadyEvent event) {
		bot.finishedLoading();
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		DiscordForge.getInstance().getDiscordBot().getCommandManager().handleCommand(new Command(event));
	}
	
	@Override
	public void onGenericEvent(Event event) {
		Class<? extends Event> eventClass = event.getClass();
		
		Method eventMethod = null;
		for(Method method : this.getClass().getMethods()) {
			if(method.getParameters().length == 1) {
				if(method.getParameters()[0].getType().equals(eventClass)) {
					eventMethod = method;
					break;
				}
			}
		}
		
		if(eventMethod == null) return;
		
		for(ListenerAdapter listener : DiscordForge.getInstance().getDiscordBot().getListeners().keySet()) {
			if(DiscordForge.getInstance().getDiscordBot().getListeners().get(listener).isEnabled()) {
				try {
					Method method = listener.getClass().getMethod(eventMethod.getName(), eventMethod.getParameterTypes());
					method.invoke(listener, event);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) { e.printStackTrace(); }
			}
		}
	}

}