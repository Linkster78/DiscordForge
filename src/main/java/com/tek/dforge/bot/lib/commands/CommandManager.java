package com.tek.dforge.bot.lib.commands;

import java.util.HashMap;

import com.tek.dforge.core.DiscordForge;
import com.tek.dforge.plugin.BakedPlugin;
import com.tek.dforge.util.StringUtil;

public class CommandManager {
	
	private HashMap<ICommandHandler, BakedPlugin> commandHandlers;
	
	public CommandManager() {
		this.commandHandlers = new HashMap<ICommandHandler, BakedPlugin>();
	}
	
	public void handleCommand(Command command) {
		if(command.getAuthor().isBot()) return;
		
		for(ICommandHandler commandHandler : commandHandlers.keySet()) {
			if(commandHandlers.get(commandHandler).isEnabled()) {
				if(commandHandler.isEnabled()) {
					String prefix = DiscordForge.getInstance().getConfig().getProperty("prefix");
					if(command.getName().startsWith(prefix)) {
						String cmd = command.getName().substring(prefix.length());
					
						if(commandHandler.getCommand().equalsIgnoreCase(cmd) || StringUtil.containsIgnoreCase(commandHandler.getAliases(), cmd)) {
							commandHandler.run(command);
							break;
						}
					}
				}
			}
		}
	}
	
	public HashMap<ICommandHandler, BakedPlugin> getCommandHandlers() {
		return commandHandlers;
	}
	
}
