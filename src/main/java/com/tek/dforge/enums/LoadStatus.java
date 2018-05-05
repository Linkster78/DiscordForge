package com.tek.dforge.enums;

public enum LoadStatus {
	
	ERRORREAD("Error while reading jarfile"),
	REFLECTIONERROR("Reflection Error"),
	UNKNOWNCLASS("Couldn't find class"),
	INVALIDMAIN("Main class must implement IForgePlugin"),
	NOMAIN("No main class found"),
	MULTIMAIN("Multiple main classes found"),
	SUCCESS("Success");
	
	private String textEquivalent;
	
	private LoadStatus(String textEquivalent) {
		this.textEquivalent = textEquivalent;
	}
	
	public String getTextEquivalent() {
		return textEquivalent;
	}
	
}
