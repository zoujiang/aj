package com.frame.core.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * FTP上传和下载工具类
 * 
 * @author lishun
 * @since 1.0
 */
public final class FtpUtils {
	
	public static FTPClient getFtpClient(String url, int port, String username,String password) throws IOException{
		FTPClient ftp =new FTPClient();
		ftp.connect(url, port);
		ftp.setFileType(FTPClient.BINARY_FILE_TYPE); 
		ftp.login(username, password);
		ftp.setControlEncoding("UTF-8");
		FTPClientConfig ftpConfig = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
        ftpConfig.setServerLanguageCode(FTPClient.DEFAULT_CONTROL_ENCODING); 
		ftp.configure(ftpConfig);
		int reply= ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
		}
		return ftp;
	}
	/**
	 * Description: 向FTP服务器上传文件
	 * 
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param path
	 *            FTP服务器保存目录
	 * @param filename
	 *            上传到FTP服务器上的文件名
	 * @param input
	 *            输入流
	 * @return 成功返回true，否则返回false
	 */
	public static String uploadFile(FTPClient ftp,String path, String filename, InputStream input) throws Exception{
		try {
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);  
			String imageSuffix=filename.substring(filename.lastIndexOf(".")+1).toLowerCase();
			String unique = String.valueOf(System.currentTimeMillis());
			filename = unique+"."+imageSuffix;
			filename = new String(filename.getBytes("utf-8"), "ISO-8859-1"); 
			if (!ftp.changeWorkingDirectory(path)) {// 如果不能进入dir下，说明此目录不存在！  
	             if (!ftp.makeDirectory(path)) {  
	                  throw new IOException("创建FTP目录失败");
	             }  
	        }  
	        ftp.changeWorkingDirectory(path);// 回到FTP根目录  
			ftp.storeFile(filename, input);
			input.close();
		} catch (IOException e) {
			throw new Exception(e);
		} 
		return filename;
	}

	/**
	 * Description: 从FTP服务器下载文件
	 * 
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名
	 * @param OutputStream
	 *            下载输出流
	 * @return
	 */
	public static FTPFile downFile(FTPClient ftp, String remotePath, String fileName) {
		try {
			ftp.changeWorkingDirectory(remotePath);
			FTPFile[] fs = ftp.listFiles();
			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) { 
					return ff;
				}
			}
			ftp.logout();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}
	public static void main(String[] args) throws Exception {
		FileInputStream fis=new FileInputStream("C:/Users/I7/Desktop/资料/QQ截图20151221102334.png");
		FTPClient ftp=FtpUtils.getFtpClient("127.0.0.1", 21, "tjftp", "tjftp");
		FtpUtils.uploadFile(ftp, "res/prod", "QQ截图20151221102334.png", fis);
		String filename="文件名";
		filename = new String(filename.getBytes("utf8"), "ISO-8859-1"); 
		System.out.println(filename);
		filename = new String(filename.getBytes("ISO-8859-1"), "utf8"); 
		System.out.println(filename);
	}
}
