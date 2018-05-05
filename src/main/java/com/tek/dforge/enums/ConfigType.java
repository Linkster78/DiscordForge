package com.tek.dforge.enums;

import java.util.ArrayList;
import java.util.List;

public enum ConfigType {
	
	STRING(String.class, ""),
	STRINGLIST(List.class, (List<String>)new ArrayList<String>()),
	NUMBER(Long.class, 0),
	BOOLEAN(Boolean.class, false);
	
	private Class<?> clazz;
	private Object defaultValue;
	
	private ConfigType(Class<?> clazz, Object defaultValue){
		this.clazz = clazz;
		this.defaultValue = defaultValue;
	}
	
	public Class<?> getClazz() {
		return clazz;
	}
	
	public Object getDefaultValue() {
		return defaultValue;
	}
	
}
