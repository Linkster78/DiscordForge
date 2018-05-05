package com.tek.dforge.log;

import com.tek.dforge.util.Reference;

public class Logger {
	
	public static void log(String msg) {
		System.out.println(Reference.PREFIX_LOG + msg);
	}
	
}
