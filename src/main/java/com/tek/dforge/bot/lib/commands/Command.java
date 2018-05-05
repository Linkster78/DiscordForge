package com.tek.dforge.bot.lib.commands;

import java.util.ArrayList;
import java.util.Arrays;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Command {
	
	private String rawMessage, name;
	private String[] arguments;
	private User author;
	private MessageChannel source;
	private ChannelType sourceType;
	private JDA instance;
	private MessageReceivedEvent rawEvent;
	
	public Command(MessageReceivedEvent rawEvent) {
		this.rawEvent = rawEvent;
		this.rawMessage = this.rawEvent.getMessage().getContentRaw();
		this.name = this.rawMessage.split(" ")[0];
		this.author = this.rawEvent.getAuthor();
		this.source = this.rawEvent.getChannel();
		this.sourceType = this.rawEvent.getChannelType();
		this.instance = this.rawEvent.getJDA();
		
		String contentRaw = this.rawMessage;
		String[] tokens = contentRaw.split(" ");
		ArrayList<String> tokenList = new ArrayList<String>(Arrays.asList(tokens));
		tokenList.remove(0);
		
		this.arguments = new String[tokenList.size()];
		
		for(int i = 0; i < tokenList.size(); i++) {
			this.arguments[i] = tokenList.get(i);
		}
	}

	public String getRawMessage() {
		return rawMessage;
	}
	
	public String getName() {
		return name;
	}
	
	public String[] getArguments() {
		return arguments;
	}
	
	public User getAuthor() {
		return author;
	}
	
	public MessageChannel getSource() {
		return source;
	}
	
	public ChannelType getSourceType() {
		return sourceType;
	}
	
	public JDA getInstance() {
		return instance;
	}
	
	public MessageReceivedEvent getRawEvent() {
		return rawEvent;
	}
	
	public void deleteMessage() {
		rawEvent.getMessage().delete().queue();
	}
	
	public Message replyMessage(String msg) {
		return source.sendMessage(msg).complete();
	}
	
	public Message replyMessageEmbed(MessageEmbed embed) {
		return source.sendMessage(embed).complete();
	}
	
	public Message replyPm(String msg) {
		PrivateChannel pm = author.openPrivateChannel().complete();
		return pm.sendMessage(msg).complete();
	}
	
	public Message replyPmEmbed(MessageEmbed embed) {
		PrivateChannel pm = author.openPrivateChannel().complete();
		return pm.sendMessage(embed).complete();
	}
}
