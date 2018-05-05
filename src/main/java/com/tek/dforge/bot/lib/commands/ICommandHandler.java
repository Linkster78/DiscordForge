package com.tek.dforge.bot.lib.commands;

import java.util.List;

public interface ICommandHandler {
	
	public void run(Command command);
	public String getCommandPrefix();
	public String getCommand();
	public List<String> getAliases();
	public String getDescription();
	
}
