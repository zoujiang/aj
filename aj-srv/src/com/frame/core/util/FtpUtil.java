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

		// this.createDir(new File(remoteFilePath).getParent());
		boolean result = ftpClient.storeFile(remoteFilePath, localIn);
		return result;
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

	public static void main(String[] args) throws IOException {
		FtpUtil ftp = new FtpUtil("192.168.1.197", 21, "mkt", "mkt123!@#");
		 FileInputStream fis2 = null;
		 FileInputStream fis3 = null;
		 OutputStream os1 = new FileOutputStream("test.txt");
		 OutputStream os2 = new FileOutputStream("test2.txt");
		 
		 InputStream is1 = null;
		 InputStream is2 = null;
		 
		try {
			System.out.println("*********getFile***************");
			ftp.login();
			FTPFile[] files = ftp.getFtpClient().listFiles("upload/res/cusInfoImport");
			for (int i = 0; i < files.length; i++) {
				  if(!files[i].isDirectory()){
					  String fileName = "";
					  InputStream in = null;
					try {
						FTPFile filexls = files[i];
						fileName = filexls.getName();
						String suffix =  fileName.substring(fileName.lastIndexOf(".")+1);
						String cgropId = fileName.substring(0,fileName.lastIndexOf("."));
						if ("xls".equals(suffix)||"xlsx".equals(suffix)) {
							ExcelReadHelper excelReader = new ExcelReadHelper();
							in = ftp.getFtpClient().retrieveFileStream("upload/res/cusInfoImport/"+filexls.getName());
							ftp.errorFileOutPut(ftp,"ADADFADASDASASF",cgropId);//输出错误文件
							//移动文件
							ftp.moveFile2Log(in,ftp,fileName);
						}
							//
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						if (null!=in) {
							in.close();
						}
						ftp.closeServer();
					}
						
				  }
				}
		
			FTPFile file = ftp.getFile("upload/res/cwen11.txt");
			ftp.upload(new FileInputStream("d:/cwen10.txt"), "upload/res/11.txt" );
			System.out.println(file.getName());
			/*FTPFile file2 = ftp.getFile("upload/wod/1373250133687重庆短彩信测试地址.txt");
			System.out.println(file2.getName());
			
			System.out.println("***********upload*************");
			 fis2=new FileInputStream("D:/45.sql");
			 fis3 = new FileInputStream("D:/11.sql");
			System.out.println(ftp.upload(fis2, "upload/res/cwen11.txt"));
			System.out.println(ftp.upload(fis3, "upload/res/cwen10.txt"));   
			
			System.out.println("***********upload*************");*/
			//ftp.ftpClient.changeWorkingDirectory("/");
			//boolean result1 = ftp.download(os1, "upload/res/cwen10.txt");
			String filename = new String("upload/res/cwen10.txt".getBytes("GBK"), "ISO-8859-1"); 
			String filename2 = new String("upload/res/cwen11.txt".getBytes("GBK"), "ISO-8859-1");
			//boolean result1 = ftp.ftpClient.retrieveFile(filename, os1);
			is1 = ftp.ftpClient.retrieveFileStream(filename);
			ftp.ftpClient.completePendingCommand();
			System.out.println(is1.available() + "===> + is1.available()" );
			//is2 = ftp.ftpClient.retrieveFileStream(filename2);
			is2 = ftp.ftpClient.retrieveFileStream(filename2);
			ftp.ftpClient.completePendingCommand();
			System.out.println(is2.available() + "===> + is2.available()" );
			//boolean result2 = ftp.ftpClient.retrieveFile(filename, os2);
			//System.out.println( "===>" + result2 );
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
}
