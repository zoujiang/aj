package com.frame.core.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.frame.core.vo.MessageModel;

/**
 * 文件（单文件和多文件）上传,下载 的Action基类
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0  
 */
public class FileDownUploadAction extends BaseAction {

	// 文件名
	private String fileName;

	/**
	 * 获取绝对路径
	 */
	public String getRealPath() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request.getSession().getServletContext().getRealPath("/upload");
	}
	public String getRealGePath() {
		///D:/tomcat/apache-tomcat-6.0.35/webapps/ams/WEB-INF/classes/
		String url = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
		url = url.replace("WEB-INF/classes", "temp");
		return url;
	}
	/**
	 * 文件上传(单个和多个)(默认)
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws FileUploadException 
	 */
	public String fileUpload(String module, CommonsMultipartFile file)
			throws ParseException, IOException, FileUploadException {
		fileName=file.getFileItem().getName();//获取文件名
		long size = file.getSize();
		if (StringUtils.isNotEmpty(fileName) && size == 0) {
			throw new FileUploadException("上传文件为空文件");
		}
		//IE上传文件路径 C:\Users\ACER\Desktop\JAVA开发工程师鄢泽來.doc
		fileName=fileName.substring(fileName.lastIndexOf("\\")+1);
		String DBPath = "";//数据库路径
		if (StringUtils.isNotEmpty(module)) {
			String dirPath = getRealPath() + "/" + module + "/";
			File tempFile = new File(dirPath);
			// 当文件夹不存在时，先建立文件夹
			if (!tempFile.exists() || !tempFile.isDirectory()) {
				tempFile.mkdirs();
			}
			DBPath = "/upload" + "/" + module + "/";
			if (StringUtils.isNotEmpty(fileName)) {
				String unique = String.valueOf(System.currentTimeMillis());
				File existsFile = new File(dirPath + unique + fileName);
				// 页面修改时，如果文件已存在，则不再上传
				if (existsFile.exists() && existsFile.isFile()) {
					DBPath = DBPath + fileName;
				}else{
					FileCopyUtils.copy(file.getBytes(), existsFile);
					// List里保存所有上传文件的全路径，用于存储到数据库
					DBPath = DBPath + unique + fileName;
				}
			} else {
				// 当文件上传的文本框为空时，对应返回的路径为空。
				DBPath = "";
			}
		}
		return DBPath;
	}
	
	/**
	 * 文件下载
	 * 
	 * @param request
	 * @param response
	 * @param fileName
	 * @return
	 */
	public MessageModel download(HttpServletResponse response,String fileName,String moduleName) {
		try {
			String path = this.getRealGePath() + File.separator + fileName;
			File file = new File(path);
			//还原"/"转义fileName.replaceAll("%2F", "/");
			fileName = URLEncoder.encode(fileName.substring(fileName.lastIndexOf("/")+1), "UTF-8");
			fileName = fileName.substring(13);
			// 获得输入流
			InputStream in = new FileInputStream(file);
			byte[] buffer;
			buffer = new byte[in.available()];
			in.read(buffer);
			in.close();
			/** 设置HTML头部信息 **/
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			response.addHeader("Content-Length", "" + buffer.length);
			response.setContentType("application/octet-stream;charset=UTF-8");

			/** 获得输出流 **/
			OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
			/** 从字节数组中将文件写到输出流中 **/
			outputStream.write(buffer);
			/** 清空输出流 **/
			outputStream.flush();
			outputStream.close();
			return new MessageModel(true, "文件下载成功");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return new MessageModel(false, "文件丢失,下载的文件不存在。");
		} catch (IOException e) {
			e.printStackTrace();
			return new MessageModel(false, "文件下载异常。");
		}
		
		
	}
	
	 /**
	 * 文件上传(单个和多个)(默认)
	 *
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public String fileUpload(String module,CommonsMultipartFile file,boolean flag)
			throws ParseException, IOException {
		fileName = file.getFileItem().getName();// 获取文件名
		String DBPath = "";// 数据库路径
		// String uniqueFileName;
		// InputStream inStream;// 文件输入流
		// OutputStream outStream;// 文件输出流
		if (StringUtils.isNotEmpty(module)) {
			String dirPath = getRealPath() + "/" + module + "/";
			File tempFile = new File(dirPath);
			// 当文件夹不存在时，先建立文件夹
			if (!tempFile.exists() || !tempFile.isDirectory()) {
				tempFile.mkdirs();
			}
			DBPath = "/upload" + "/" + module + "/";
			if (StringUtils.isNotEmpty(fileName)) {
				String unique = String.valueOf(System.currentTimeMillis());
				File existsFile = new File(dirPath + unique + fileName);
				// 页面修改时，如果文件已存在，则不再上传
				if (existsFile.exists() && existsFile.isFile()) {
					DBPath = DBPath + fileName;
				} else {
					FileCopyUtils.copy(file.getBytes(), existsFile);
					// inStream = request.getInputStream();
					// List里保存所有上传文件的全路径，用于存储到数据库
					// outStream = new FileOutputStream(dirPath + unique +
					// fileName);
					DBPath = DBPath + unique + fileName;
					// byte[] buf = new byte[1024];
					// int length = 0;
					// while ((length = inStream.read(buf)) > 0) {
					// outStream.write(buf, 0, length);
					// }
					// inStream.close();
					// outStream.close();
				}
			} else {
				// 当文件上传的文本框为空时，对应返回的路径为空。
				DBPath = "";
			}
		}
		return DBPath;
	 }

	/**
	 * 删除文件夹下所有文件及文件夹
	 */
	public void delFile(String delpath) {
		File file = new File(delpath);
		if (!file.isDirectory()) {
			file.delete();
		} else if (file.isDirectory()) {
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File delfile = new File(delpath + "/" + filelist[i]);
				if (!delfile.isDirectory()) {
					delfile.delete();
				} else if (delfile.isDirectory()) {
					delFile(delpath + "/" + filelist[i]);
				}
			}
			file.delete();
		}
	}
}