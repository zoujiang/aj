package com.frame.core.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPCmd;
import org.apache.commons.net.ftp.FTPCommand;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.frame.core.constant.FtpConstant;

public class FtpUtil {
	private FTPClient ftpClient = null;
	private String hostname;
	private int port;
	private String username;
	private String password;
	private String remoteDir;

	// 构造方法

	public FtpUtil(String hostname, int port, String username, String password,
			String remoteDir) {
		this(hostname, port, username, password);
		if (remoteDir == null) {
			remoteDir = "/";
		}
	}

	public FtpUtil(String hostname, int port, String username, String password) {
		this.hostname = hostname;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	/**
	 * FTP登陆
	 * 
	 * @throws IOException
	 */
	public void login() throws Exception {
		ftpClient = new FTPClient();
		ftpClient.configure(getFTPClientConfig());
		ftpClient.setDefaultPort(port);
		ftpClient.setControlEncoding("UTF-8");
		ftpClient.connect(hostname);
		ftpClient.setDataTimeout(Integer.valueOf((String)SystemConfig.getValue(FtpConstant.FTP_DATATIMEOUT)));
		ftpClient.setConnectTimeout(Integer.valueOf((String)SystemConfig.getValue(FtpConstant.FTP_CONNECTTIMEOUT)));
		if (!ftpClient.login(username, password)) {
			throw new Exception("FTP登陆失败，请检测登陆用户名和密码是否正确!");
		}
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		if (remoteDir != null)
			ftpClient.changeWorkingDirectory(remoteDir);
	}
		
	/**
	 * 得到配置
	 * 
	 * @return
	 */
	private FTPClientConfig getFTPClientConfig() {
		// 创建配置对象
		FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
		conf.setServerLanguageCode(FTPClient.DEFAULT_CONTROL_ENCODING);
		return conf;
	}

	/**
	 * 关闭FTP服务器
	 */
	public void closeServer() {
		try {
			if (ftpClient != null) {
				ftpClient.logout();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
		}
	}

	/**
	 * 链接是否已经打开
	 * 
	 * @return
	 */
	public boolean serverIsOpen() {
		if (ftpClient == null) {
			return false;
		}
		return !ftpClient.isConnected();
	}

	/**
	 * 列表FTP文件
	 * 
	 * @param regEx
	 * @return
	 */
	public String[] listFiles(String regEx) {
		String[] names;
		try {
			names = ftpClient.listNames(regEx);
			if (names == null)
				return new String[0];
			return names;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String[0];
	}

	/**
	 * 取得FTP操作类的句柄
	 * 
	 * @return
	 */
	public FTPClient getFtpClient() {
		return ftpClient;
	}

	/**
	 * 上传
	 * 
	 * @throws Exception
	 */
	public boolean upload(String localFilePath, String remoteFilePath)
			throws Exception {
		boolean state = false;
		File localFile = new File(localFilePath);
		if (!localFile.isFile() || localFile.length() == 0) {
			return state;
		}
		FileInputStream localIn = new FileInputStream(localFile);
		state = this.upload(localIn, remoteFilePath);
		localIn.close();
		return state;
	}

	/**
	 * 上传
	 * 
	 * @throws Exception
	 */
	public boolean upload(InputStream localIn, String remoteFilePath)
			throws Exception {

		 this.createDir(new File(remoteFilePath).getParent());
		 ftpClient.enterLocalPassiveMode();
		 ftpClient.setControlEncoding("UTF-8");
		 ftpClient.setFileTransferMode(ftpClient.BINARY_FILE_TYPE);
		boolean result =false;
		try {
			result = ftpClient.storeFile(remoteFilePath, localIn);
			System.out.println(ftpClient.getReplyCode() +"---"+ftpClient.getReplyString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 移动
	 * 
	 * @throws Exception
	 */
	public boolean move( String remoteFilePath, String resoureFilePath)
			throws Exception {
	
		try {
			int RNFR = ftpClient.sendCommand(FTPCmd.RNFR,resoureFilePath);
			this.createDir(new File(remoteFilePath).getParent());
			int RANTO = ftpClient.sendCommand(FTPCmd.RNTO,remoteFilePath);
			if(RANTO == 250){
				//250 文件行为完成
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 是否存在FTP目录
	 * 
	 * @param dir
	 * @param ftpClient
	 * @return
	 */
	public boolean isDirExist(String dir) {
		try {
			int retCode = ftpClient.cwd(dir);
			return FTPReply.isPositiveCompletion(retCode);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 创建FTP多级目录
	 * 
	 * @param remoteFilePath
	 * @throws IOException
	 */
	public void createDir(String dir) throws IOException {
		// dir = dir.replaceAll("\\\\", "/");
		if (dir != null && !isDirExist(dir)) {
			File file = new File(dir);
			this.createDir(file.getParent());
			ftpClient.makeDirectory(dir);
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param remoteFilePath
	 */
	public boolean delFile(String remoteFilePath) {
		try {
			return ftpClient.deleteFile(remoteFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 下载
	 * 
	 * @throws Exception
	 */
	public void download(String localFilePath, String remoteFilePath)
			throws Exception {
		OutputStream localOut = new FileOutputStream(localFilePath);
		this.download(localOut, remoteFilePath);
		localOut.close();
	}

	/**
	 * 下载
	 * 
	 * @throws Exception
	 */
	public boolean download(OutputStream localOut, String remoteFilePath)
			throws Exception {
		boolean result = ftpClient.retrieveFile(remoteFilePath, localOut);
		return result;
		
	}

	public FTPFile getFile(String remoteFilePath) throws Exception {
		FTPFile ftpFile = null;
		try {
			String fileName = remoteFilePath.substring(
					remoteFilePath.lastIndexOf("/") + 1,
					remoteFilePath.length());
			String parentPath = remoteFilePath.substring(0,
					remoteFilePath.lastIndexOf("/"));
			FTPFile[] fs = ftpClient.listFiles(parentPath);
			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) {
					ftpFile = ff;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ftpFile;

	}

	public long getFileSize(String path){
		FTPFile[] files;
		long total = 0;
		try {
			files = ftpClient.listFiles(path);
			for(FTPFile file: files){
				if(file.isDirectory()){
					total += getFileSize(path+ "/"+file.getName());
				}else{
					total += file.getSize();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return total;
	}
	public int getFileCount(String path){
		FTPFile[] files;
		int total = 0;
		try {
			files = ftpClient.listFiles(path);
			
			for(FTPFile file: files){
				if(file.isDirectory()){
					total += getFileCount(path+ "/"+file.getName());
				}else{
					total += 1;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	/**
	 * 打包文件，
	 * from 源文件地址
	 * to 压缩包目标地址
	 * name 压缩包名称
	 * */
	public boolean zip(String from, String to, String name){
		boolean b = false;
		try {
			if(!isDirExist(to)){
				createDir(to);
			}
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
	public static void main(String[] args) throws IOException {
		FtpUtil ftp = new FtpUtil("120.25.74.162", 21, "ajftp", "ajftp123");
		 FileInputStream fis2 = null;
		 FileInputStream fis3 = null;
		 OutputStream os1 = new FileOutputStream("test.txt");
		 OutputStream os2 = new FileOutputStream("test2.txt");
		 
		 InputStream is1 = null;
		 InputStream is2 = null;
		 
		try {
			System.out.println("*********getFile***************");
			ftp.login();
		
			
			//ftp.move("/opt/sdp/ajftp/img/SP001/SC17030221420900011/1464617308822.jpg", "/opt/sdp/ajftp/img/clt/1464617308822.jpg");
			//ftp.move("/opt/sdp/ajftp/img/clt/1464617308822.jpg","/opt/sdp/ajftp/img/SP001/SC17030221420900011/1464617308822.jpg");
			//System.out.println(ftp.isDirExist("/opt/sdp/ajftp/img/clt"));
			//System.out.println(ftp.getFileCount("/opt/sdp/ajftp/img/"));
			
			///opt/sdp/ajftp/img/SP001/SC17030221420900011/DynamicAlbum/SDC17030722582000021
			Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("zip -rj /opt/sdp/ajftp/img/SP001/SC17030221420900011/aa/zip /opt/sdp/ajftp/img/SP001/SC17030221420900011");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			//fis2.close();
			//fis3.close();
			ftp.closeServer();
		}
		// FtpUtils.uploadFile(ftp, "upload/res", "cwen7.txt", fis2);
	}
	private void errorFileOutPut(FtpUtil ftp,String errorLine,String cgropId) throws IOException{
		InputStream in = null;
		String ftpAddress = (String) SystemConfig.getValue(FtpConstant.FTP_ADDRESS);
		String username = (String) SystemConfig.getValue(FtpConstant.USERNAME);
		String password = (String) SystemConfig.getValue(FtpConstant.PASSWORD);
		String port = (String) SystemConfig.getValue(FtpConstant.PORT);
		
		FtpUtil ftp1=new FtpUtil(ftpAddress, Integer.parseInt(port), username, password);
		try{
			ftp1.login();
			in = new ByteArrayInputStream(errorLine.getBytes());
			ftp1.upload(new FileInputStream("D:/cwen10.txt"), "upload/res/cusInfoImport/failure/"+cgropId+".txt");
		}catch(Exception e){
		}finally{
			if (null!=in) {
				in.close();
			}
			ftp1.closeServer();
		}
		
	}
	private void moveFile2Log(InputStream in,FtpUtil ftp, String fileName) {
		String unique = String.valueOf(System.currentTimeMillis());
		String suffix =  fileName.substring(fileName.lastIndexOf(".")+1);
		String cgropId = fileName.substring(0,fileName.lastIndexOf("."));
		//file.(new File(getRealPath() + "/res/cusInfoImport/log/"+cgropId+"_"+unique+"."+suffix));
		try {
			boolean f = ftp.upload(new FileInputStream("D:/cwen10.txt"), "upload/res/cusInfoImport/log/"+cgropId+"_"+unique+".txt");
			System.out.println(f);
			ftp.delFile("upload/res/cusInfoImport/"+fileName);
		} catch (Exception e) {
			
		}
	}

}
