package com.util;


import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;  

public   class  WaterMarkUtil {  

	
   /**   
     * 给图片添加水印
     * @param watermarkFilePath         水印图片路径   
     * @param srcImagePath 		源图片路径   
     * @param targerImagePath 	        目标图片路径   
     * @param degree 			水印图片旋转角度 
     */    
    public static void addImageWatermark(String watermarkFilePath, String srcImagePath, String targerImagePath, Integer degree) {     
        System.out.println("添加水印addImageWatermark：水印地址："+watermarkFilePath+"| 源图片地址："+srcImagePath +"|目标地址："+targerImagePath);
    	OutputStream os = null;     
        try {
        	Image srcImage = ImageIO.read(new File(srcImagePath));   
        	int orgImgWidth = srcImage.getWidth(null);
        	int orgImgHeight = srcImage.getHeight(null);
            BufferedImage graphics = new BufferedImage(srcImage.getWidth(null), srcImage.getHeight(null), BufferedImage.TYPE_INT_RGB);   
            // 得到画布对象     
            Graphics2D g = graphics.createGraphics();     
            // 设置对线段的锯齿状边缘处理     
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);     
    
            g.drawImage(srcImage.getScaledInstance(srcImage.getWidth(null), srcImage.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);     
    
            if (null != degree) {     
                // 设置水印旋转角度及坐标  
                g.rotate(Math.toRadians(degree),  (double) graphics.getWidth() / 2, (double) graphics.getHeight() / 2);     
            }     
            // 水印图象的路径 （如果需要设置透明度需要gif或者png格式的图片  ）
            ImageIcon imgIcon = new ImageIcon(watermarkFilePath);   
            
            // 得到Image对象。     
            Image image = imgIcon.getImage();  
            int orgWatermakeHeight = image.getHeight(null);
            int orgWatermakeWidth = image.getWidth(null);
            int markWidth = 200;
            int markHeight = orgWatermakeHeight * markWidth / orgWatermakeWidth;
            int preWidth = orgImgWidth / 6;
            if(orgImgHeight > orgImgWidth){
            	preWidth = orgImgHeight / 6;
            }
            int interval = 200;
            if(preWidth > 200){
            	interval = preWidth;
            	markWidth = preWidth;
            	markHeight = orgWatermakeHeight * markWidth/orgWatermakeWidth;
            	if(orgImgHeight > orgImgWidth){
            		markHeight = preWidth;
            		markWidth = orgWatermakeWidth * markHeight/orgWatermakeHeight ;
            	}
            }
            
            Image smallImage = image.getScaledInstance(markWidth,markHeight,Image.SCALE_FAST);
            ImageIcon smallIcon = new ImageIcon(smallImage);
            
            // 透明度     
            float alpha = 0.3f;
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));     
            // 表示水印图片的位置     
           // g.drawImage(image, 20, 30, null);     
           // g.drawImage(image,srcImage.getWidth(null)-imgIcon.getIconWidth(),  srcImage.getHeight(null)-imgIcon.getIconHeight(), null);     
         
         // 6、水印图片的位置  
           
            for (int height = smallIcon.getIconHeight(); height < graphics.getHeight(); height = height +interval+ smallIcon.getIconHeight()) {  
            	for (int weight = smallIcon.getIconWidth(); weight < graphics.getWidth(); weight = weight +interval+ smallIcon.getIconWidth()) {  
            		g.drawImage(smallImage, weight - smallIcon.getIconWidth(), height - smallIcon.getIconHeight(), null);  
            	}  
            } 
            
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));     
            g.dispose();     
            os = new FileOutputStream(targerImagePath);     
            // 生成图片     (可设置 jpg或者png格式)
            //ImageIO.write(graphics, "png", os);     
            //获取原文件的格式
            String fileType = srcImagePath.substring(srcImagePath.lastIndexOf("/")+1, srcImagePath.length());
            ImageIO.write(graphics, fileType, os);   
            
            //5、创建图像编码工具类  
           // JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);  
            //6、使用图像编码工具类输出缓存图像到目标文件中  
          //  en.encode(graphics); 
        } catch (Exception e) {     
            e.printStackTrace();     
        } finally {     
            try {     
                if (os != null) {
                	os.close();     
                }
            } catch (Exception e) {     
                e.printStackTrace();     
            }     
        }     
    }  
    public static void main(String[] args) {
    	//String markUrl = "/Users/zoujiang/Documents/img/2.jpg";
    	String markUrl = "/Users/zoujiang/Documents/img/1.jpg";
    	String resUrl = "/Users/zoujiang/Documents/img/timg.jpeg";
    	String tarUrl1 = "/Users/zoujiang/Documents/img/timg2.jpeg";
    	addImageWatermark(markUrl, resUrl, tarUrl1, 30);
	}
}