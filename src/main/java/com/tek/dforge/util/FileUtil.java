package com.tek.dforge.util;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipFile;

public class FileUtil {
	
	public static void createFile(File file) {
		try {
			if(file.exists()) return;
			if(file.getParentFile() != null) file.getParentFile().mkdirs();
			file.createNewFile();
		}catch(Exception e) { e.printStackTrace(); }
	}
	
	public static void createFolder(File file) {
		try {
			if(file.exists()) return;
			if(file.getParentFile() != null) file.getParentFile().mkdirs();
			file.mkdir();
		}catch(Exception e) { e.printStackTrace(); }
	}
	
	public static boolean isJarFile(File file) {
		try{
			if (!isZipFile(file)) {
				return false;
			}
		
		    ZipFile zip = new ZipFile(file);
		    boolean hasManifest = zip.getEntry("META-INF/MANIFEST.MF") != null;
		    zip.close();
		    return hasManifest;
		}catch(Exception e) {
			return false;
		}
	}
	
	public static boolean isZipFile(File file) throws IOException {
	      if(file.isDirectory()) {
	          return false;
	      }
	      if(!file.canRead()) {
	          throw new IOException("Cannot read file "+file.getAbsolutePath());
	      }
	      if(file.length() < 4) {
	          return false;
	      }
	      DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
	      int test = in.readInt();
	      in.close();
	      return test == 0x504b0304;
	  }
	
}
