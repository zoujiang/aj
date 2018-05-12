package com.frame.core.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

public class FileUtil {
	/**
     * 将文件地址拼装成返回地址
     * @param addr
     * @return String
     */
    public static String getResponseAddr(String addr) {
        return addr;
    }
    
	/**
     * 将文件地址拼装成数据库地址
     * @param 
     * @return String base64
     */
    public static String getStoreAddr(String addr) {
        return addr;
    }
    /** 
	 * 将InputStream写入本地文件 
	 * @param destination 写入本地目录 
	 * @param input 输入流 
	 * @throws IOException 
	 */  
	public static boolean writeToLocal(String destination, InputStream input) {  
		FileOutputStream downloadFile = null;
	  try {
		  createFileIfNotExist(new File(destination));
		  int index;  
		  byte[] bytes = new byte[1024];  
		  downloadFile = new FileOutputStream(destination);  
		  while ((index = input.read(bytes)) != -1) {  
			  downloadFile.write(bytes, 0, index);  
			  downloadFile.flush();  
		  }  
		  
		  return true;
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		if(downloadFile != null){
			try {
				downloadFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}  
			try {
				input.close(); 
			} catch (IOException e) {
				e.printStackTrace();
			}  
		}
	}
	  return false;
	} 
	public static void createFileIfNotExist(File file){
		
		if(!file.exists()){
			
			if(file.isDirectory()){
				file.mkdirs();
			}else{
				
				File parentFile = file.getParentFile();
				parentFile.mkdirs();
			}
		}
	}
	
	public static Long[] getTotalSizeOfFilesInDir(File file) {
		Long[] data = new Long[2];
        if (file.isFile()){
        	data[0] = file.length();
        	data[1] = 1L;
        	return data;
        }
        	
        final File[] children = file.listFiles();
        Long[] childData = new Long[2];
        long total = 0;
        long count = 0;
        if (children != null){
        	
        	for (final File child : children){
        		count ++;
        		childData = getTotalSizeOfFilesInDir(child);
        		total += childData[0];
        		count += childData[1];
        	}
        }
        data[0] = total;
    	data[1] = count;
    	return data;
    }
	/**
	 * 移动
	 * 
	 * @throws Exception
	 */
	public static boolean move( String remoteFilePath, String resoureFilePath){
			  
        try {  
            File afile = new File(resoureFilePath);  
            return afile.renameTo(new File(remoteFilePath));
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
		return false;
	}
	
	/**
	 * 打包文件，
	 * from 源文件地址
	 * to 压缩包目标地址
	 * name 压缩包名称
	 * */
	public static boolean zip(String from, String to, String name){
		boolean b = false;
		try {
			createFileIfNotExist(new File(to));
		//	b = ftpClient.sendSiteCommand("zip -rj  "+ from +"/"+ name +" "+from);
		//	b = ftpClient.doCommand("zip", " -rj  "+ from +"/"+ name +" "+from);
			Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("zip -rj  "+ to +"/"+ name +" "+from);
            b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	/** 
     * @param im 
     *            原始图像 
     * @param resizeTimes 
     *            倍数,比如0.5就是缩小一半,0.98等等double类型 
     * @return 返回处理后的图像 
     */  
    public static BufferedImage zoomImage(InputStream srcfile, float resizeTimes) {  
          
        BufferedImage result = null;  
  
        try {  
            BufferedImage im = ImageIO.read(srcfile);  
  
            /* 原始图像的宽度和高度 */  
            int width = im.getWidth();  
            int height = im.getHeight();  
              
            /* 调整后的图片的宽度和高度 */  
            int toWidth = (int) (width * resizeTimes);  
            int toHeight = (int) (height * resizeTimes);  
  
            /* 新生成结果图片 */  
            result = new BufferedImage(toWidth, toHeight,  
                    BufferedImage.TYPE_INT_RGB);  
  
            result.getGraphics().drawImage(  
                    im.getScaledInstance(toWidth, toHeight,  
                            java.awt.Image.SCALE_SMOOTH), 0, 0, null);  
              
  
        } catch (Exception e) {  
            System.out.println("创建缩略图发生异常" + e.getMessage());  
        }  
          
        return result;  
  
    }  
    public static InputStream getImageStream(BufferedImage bufferedImage,String type){
		InputStream is = null;  
		 ByteArrayOutputStream bs = new ByteArrayOutputStream(); 
		 ImageOutputStream imOut; 
		 try {  
			 imOut = ImageIO.createImageOutputStream(bs);  
			 ImageIO.write(bufferedImage, type,imOut);
			 is= new ByteArrayInputStream(bs.toByteArray()); 
		 } catch (IOException e) {  
			 e.printStackTrace();  
			 } 
	   return is;  
	}
}
