package com.mysoft.b2b.imagesys.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.util.DigestUtils;

public class FileUtil {
	
	public static String getFileFormat(File file){
		if(!file.exists()){
			return "file not exist";
		}
		String fileName = file.getName();
		int index = fileName.lastIndexOf(".");
		return fileName.substring(index+1);
	}
	
	public static Long getFileSize(File file){
		if(!file.exists()){
			return 0l;
		}
		return FileUtils.sizeOf(file);
	}
	
	public static String getMD5(File file){
		try{
			if(file.exists()){
				return DigestUtils.md5DigestAsHex(FileUtils.readFileToByteArray(file));
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
}
