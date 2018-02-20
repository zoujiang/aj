package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileZipUtil {

	/**
	 * files 要打包的文件
	 * targetUrl  zip文件存储地址
	 * */
	public static boolean zip(String[] files,String targetUrl){
		
		try {
			byte[] buffer = new byte[1024];   
			  
				ZipOutputStream out;
				if(!new File(targetUrl).getParentFile().exists()){
					  new File(targetUrl).getParentFile().mkdirs();
				  }
			   out = new ZipOutputStream(new FileOutputStream(targetUrl));
		  
		       for(int i=0;i<files.length;i++) {   
		   
		    	   File file = new File(files[i]);
		    	   
		           FileInputStream fis = new FileInputStream(file);   
		  
		           out.putNextEntry(new ZipEntry(file.getName()));   
		  
		           int len;   
		  
		           //读入需要下载的文件的内容，打包到zip文件   
		  
		          while((len = fis.read(buffer))>0) {   
		  
		        	  out.write(buffer,0,len);    
		  
		          }   
		  
		           out.closeEntry();   
		  
		           fis.close();   
		  
		       }   
		  
		        out.close();   
		  
		        System.out.println("生成Demo.zip成功");   
		  
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}
