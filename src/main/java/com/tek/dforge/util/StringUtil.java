package com.tek.dforge.util;

import java.util.Collection;

public class StringUtil {
	
	public static boolean containsIgnoreCase(Collection<String> list, String str) {
		for(String token : list) {
			if(token.toUpperCase().contains(str.toUpperCase())) return true;
		}
		
		return false;
	}
	
	public static String listToString(Collection<?> objs, String separator) {
		StringBuilder str = new StringBuilder();
		
		for(Object obj : objs) {
			str.append(obj.toString() + separator);
		}
		
		if(str.length() != 0) str.setLength(str.length() - separator.length());
		
		return str.toString();
	}
	
}
