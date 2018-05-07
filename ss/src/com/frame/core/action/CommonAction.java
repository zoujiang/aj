package com.frame.core.action;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.frame.core.constant.FtpConstant;
import com.frame.core.util.ExportExcelUtils;
import com.frame.core.util.ExportTextUtils;
import com.frame.core.util.FileUtil;
import com.frame.core.util.SystemConfig;
/**     
 * 
 * @author：caiwen    
 * @date 2014-12-01 上午10:02:43    
 * @version  1.0     
 */
@Controller
public class CommonAction extends FileDownUploadAction {
	private static final String EXPORT_DATA = "Export_Data";// 导出数据
	private static final String EXPORT_HEADER = "Export_Header";// 导出表头
	private static final String EXPORT_TITLE = "Export_Title";// 导出标题
	private static final String EXPORT_FILENAME = "Export_FileName";// 文件名字
	
	@RequestMapping("/doExport")
	public void doExport(HttpServletRequest request,
			HttpServletResponse response) {
		String fileName = (String) request.getAttribute(EXPORT_FILENAME);
		String title = (String) request.getAttribute(EXPORT_TITLE);
		String headerColumn = (String) request.getAttribute(EXPORT_HEADER);
		List<?> dataList = (List<?>) request.getAttribute(EXPORT_DATA);
		
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		fileName = fileName + format.format(date) + ".xls";

		/** 设置HTML头部信息 
		response.setContentType("application/octet-stream;charset=gb2312");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		response.setHeader("pragma", "no-cache");
		**/
		/** 获得输出流 **/
		ByteArrayOutputStream byteOutPut = new ByteArrayOutputStream();
		try {
			HSSFWorkbook workbook = new ExportExcelUtils().exportExcel(dataList, headerColumn, fileName);
			workbook.write(byteOutPut);
			
			String url = getRealGePath();
			OutputStream out=new FileOutputStream(url + fileName);//文件本地存储地址
			workbook.write(out);
			out.close();
			File tempFile = new File(url + fileName);
			byteOutPut.close();
			GZIPOutputStream gizout = new GZIPOutputStream(new FileOutputStream(url + fileName+".gz"));
			byte[] buff = new byte[1024]; //设定读入缓冲区尺寸   
			FileInputStream in = new FileInputStream(tempFile); //把生成的csv文件
			
			int len;
			while ((len = in.read(buff)) != -1) {
				gizout.write(buff,0,len);
			}
			in.close();
			gizout.finish();
			gizout.close();
			tempFile.delete();//删除临时文件
			//上传压缩文件
			// tempFile.delete();
			// File gzTempFile = new File(url + fileName+".gz");
			//FileInputStream input = new FileInputStream(gzTempFile);
			//String realPath = SystemConfig.getValue("actlist.file.path") + fileName;
			//this.fileUpload(realPath, input);
			//input.close();
			//gzTempFile.delete();//删除压缩文件
			//this.fileUpload(realPath, new ByteArrayInputStream(byteOutPut.toByteArray()));
			//System.out.println(fileName);
			//String realPath = "/temp/" + fileName+".gz";
			//this.fileUpload(realPath, new ByteArrayInputStream(byteOutPut.toByteArray()));
			request.getRequestDispatcher("/admin/download?fileName="+fileName+".gz").forward(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(fileName);
		//return "forward:../download?fileName=" + fileName;
		
	}
	@RequestMapping("/doExportText")
	public void doExportText(HttpServletRequest request,
			HttpServletResponse response) {
		String fileName = (String) request.getAttribute(EXPORT_FILENAME);
		String headerColumn = (String) request.getAttribute(EXPORT_HEADER);
		List<?> dataList = (List<?>) request.getAttribute(EXPORT_DATA);
		
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		fileName = fileName + format.format(date) + ".txt";
		
		/** 获得输出流 **/
		ByteArrayOutputStream byteOutPut = new ByteArrayOutputStream();
		try {
			ExportTextUtils util = new ExportTextUtils();
			String url = getRealGePath();
			OutputStream out=new FileOutputStream(url + fileName);//文件本地存储地址
			util.exportCsv(dataList, headerColumn, new BufferedWriter(new OutputStreamWriter(out)));
			out.close();
			File tempFile = new File(url + fileName);
			byteOutPut.close();
			GZIPOutputStream gizout = new GZIPOutputStream(new FileOutputStream(url + fileName+".gz"));
			byte[] buff = new byte[1024]; //设定读入缓冲区尺寸   
			FileInputStream in = new FileInputStream(tempFile); //把生成的csv文件
			
			int len;
			while ((len = in.read(buff)) != -1) {
				gizout.write(buff,0,len);
			}
			in.close();
			gizout.finish();
			gizout.close();
			tempFile.delete();//删除临时文件
			request.getRequestDispatcher("/admin/download?fileName="+fileName+".gz").forward(request, response);
		} catch (IOException e) {
			log.error("" , e);
		} catch (Exception e) {
			log.error("" , e);
		}
		//System.out.println(fileName);
		//return "forward:../download?fileName=" + fileName;
		
	}	
	/**
	 * 下载活动附件
	 * 
	 * @param fileName
	 * @param request
	 */
	@RequestMapping("/download")
	public void ActivityDownloads(String fileName,
			HttpServletResponse response) {
		this.download(response, fileName, "temp");//下载附件
	}
	

	
	/**
	 * 生成文件上传到FTP
	 * @param module
	 * @param file
	 * @return
	 * @throws ParseException
	 * @throws FileUploadException
	 * @throws IOException 
	 * @throws Exception 
	 */
	public String fileUpload(String DBPath, InputStream input) throws Exception {
	//	String ftpAddress = (String) SystemConfig.getValue(FtpConstant.FTP_ADDRESS);
	//	String username = (String) SystemConfig.getValue(FtpConstant.USERNAME);
	//	String password = (String) SystemConfig.getValue(FtpConstant.PASSWORD);
	//	String port = (String) SystemConfig.getValue(FtpConstant.PORT);
		String path = (String) SystemConfig.getValue(FtpConstant.FTP_PATH);
	//	FtpUtil ftp = new FtpUtil(ftpAddress, Integer.parseInt(port), username, password);
	//	ftp.login();
	//	ftp.upload(input, path + DBPath);
		FileUtil.writeToLocal(path + DBPath, input);
	//	ftp.closeServer();
		return DBPath;
	}
	public String getRealGePath() {
		///D:/tomcat/apache-tomcat-6.0.35/webapps/ams/WEB-INF/classes/
		String url = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
		url = url.replace("WEB-INF/classes", "temp");
		return url;
	}
}
