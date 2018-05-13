package com.tek.dforge.bot.lib.commands;

import java.util.List;

public interface ICommandHandler {
	
	public void run(Command command);
	public String getCommand();
	public List<String> getAliases();
	public String getDescription();
	public String getSyntax();
	public void setEnabled(boolean enabled);
	public boolean isEnabled();
	
	public default String asString() {
		return this.getCommand();
	}
	
}
