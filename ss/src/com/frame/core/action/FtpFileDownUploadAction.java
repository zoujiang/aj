package com.frame.core.action;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.frame.core.constant.FtpConstant;
import com.frame.core.util.FileUtil;
import com.frame.core.util.FtpUtil;
import com.frame.core.util.SystemConfig;
import com.frame.core.vo.MessageModel;

/**
 * FTP文件（单文件和多文件）上传,下载 的Action基类
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0  
 */
public class FtpFileDownUploadAction extends FileDownUploadAction {

	private Log log=LogFactory.getLog(FtpFileDownUploadAction.class);

	@Override
	public String fileUpload(String module, CommonsMultipartFile file)
			throws ParseException, IOException, FileUploadException {
		log.debug("fileUpload("+module+","+file+") - start");
		String ftpAddress = (String) SystemConfig.getValue(FtpConstant.FTP_ADDRESS);
		String username = (String) SystemConfig.getValue(FtpConstant.USERNAME);
		String password = (String) SystemConfig.getValue(FtpConstant.PASSWORD);
		String port = (String) SystemConfig.getValue(FtpConstant.PORT);
		String path = (String) SystemConfig.getValue(FtpConstant.FTP_PATH);
		String fileName = file.getFileItem().getName();// 获取文件名
		long size = file.getSize();
		if (StringUtils.isNotEmpty(fileName) && size == 0) {
			throw new FileUploadException("上传文件为空文件");
		}
		fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
		String DBPath = "";// 数据库路径
	//	FtpUtil ftp = null;
		if (StringUtils.isNotEmpty(module)) {
			if (StringUtils.isNotEmpty(fileName)) {
				String unique = String.valueOf(System.currentTimeMillis());
				fileName = unique + fileName;
				DBPath = "/" + module+"/"+fileName; // 不能这个路径/upload/wod
	//			ftp=new FtpUtil(ftpAddress, Integer.parseInt(port), username, password);
				boolean flag = false;
				try {
		//			ftp.login();
		//			flag = ftp.upload(file.getInputStream(), path + DBPath);
					flag = FileUtil.writeToLocal(path + DBPath, file.getInputStream());
				} catch (Exception e) {
					log.error(e.getMessage());
					throw new FileUploadException("FTP上传文件出错");
				} finally{
				//	ftp.closeServer();
				}
				if (!flag) {
					log.error("FTP文件上传失败");
					throw new IOException("FTP文件上传失败");
				}
				
			} else {
				// 当文件上传的文本框为空时，对应返回的路径为空。
				DBPath = "";
			}
		}
		log.debug("fileUpload("+module+","+file+") - end");
		return DBPath;
	}

	@Override
	public MessageModel download(HttpServletResponse response, String fileName,
			String moduleName) {
		log.debug("download("+fileName+","+moduleName+") - start");
		FtpUtil ftp = null;
		try {
			/** 设置HTML头部信息 **/
			response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "utf-8") + "\"");
			response.setContentType("application/octet-stream;charset=UTF-8");
			String ftpAddress = (String) SystemConfig.getValue(FtpConstant.FTP_ADDRESS);
			String username = (String) SystemConfig.getValue(FtpConstant.USERNAME);
			String password = (String) SystemConfig.getValue(FtpConstant.PASSWORD);
			String port = (String) SystemConfig.getValue(FtpConstant.PORT);
			String path = (String) SystemConfig.getValue(FtpConstant.FTP_PATH);
			ftp=new FtpUtil(ftpAddress, Integer.parseInt(port), username, password);
			OutputStream os=response.getOutputStream();
			ftp.login();
			log.debug("链接FTP,地址:" + ftpAddress + "用户名：" + username + "密码：" + username + "端口：" + username + "地址：" + path);
			String remotePath=path + "/" + moduleName+"/"+fileName;
			FTPFile file = ftp.getFile(remotePath);
			log.debug("ftp.getFile-----:" + remotePath);
			response.addHeader("Content-Length", "" + file.getSize());
			ftp.download(os, remotePath);
			log.debug("ftp.download------:" + remotePath);
			os.close();
			log.debug("流关闭");
			return new MessageModel(true, "文件下载成功");
		}catch (Exception e) {
			log.error(e.getMessage());
			return new MessageModel(false, "文件下载异常。");
		}finally{
			ftp.closeServer();
		}
	}

}
