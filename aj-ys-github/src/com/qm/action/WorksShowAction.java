package com.qm.action;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.frame.core.action.FtpImgDownUploadAction;
import com.frame.core.constant.FtpConstant;
import com.frame.core.util.DateUtil;
import com.frame.core.util.FileUtil;
import com.frame.core.util.SystemConfig;
import com.frame.system.vo.UserExtForm;
import com.qm.entities.WorksShow;
import com.qm.mapper.WorksShowMapper;
import com.qm.shop.Constant;
import com.qm.shop.common.action.BatchFileUploadAction;

@RestController
@RequestMapping("/show")
public class WorksShowAction extends FtpImgDownUploadAction {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WorksShowMapper worksShowMapper;
	
	@RequestMapping("/init")
	@ResponseBody
	public String showInit(String shopId, int shopType) {
		
		JSONObject result = new JSONObject();
		try {
			WorksShow show = new WorksShow();
			show.setShopId(shopId);
			show.setType(shopType);
			List<WorksShow> showList = worksShowMapper.selectByCondition(show);
			for(WorksShow s : showList){
				
				if(s.getImageurl() != null && !"".equals(s.getImageurl())){
					s.setImageurl(Constant.imgPrefix + s.getImageurl() );
				}
				if(s.getVideourl() != null && !"".equals(s.getVideourl())){
					s.setVideourl(Constant.resPrefix + s.getVideourl());
				}
			}
			
			result .put("success", true);
			result.put("data", showList);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("message", "初始化失败");
		}
		
		return  result.toString();
	}
	@RequestMapping("/deleteFile")
	@ResponseBody
	public String deleteFile(Integer id) {
		
		JSONObject result = new JSONObject();
		try {
			worksShowMapper.deleteByPrimaryKey(id);
			result .put("success", true);
			result.put("message", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("message", "删除失败");
		}
		
		return  result.toString();
	}
	@RequestMapping("/files/fileUpload")
	@ResponseBody
	public String fileUpload(@RequestParam(value = "file") MultipartFile file, Integer type, String shopId, HttpServletRequest request) {
		
		UserExtForm userExtForm = (UserExtForm) request.getSession().getAttribute(com.frame.core.constant.Constant.LoginAdminUser);

		String DBPath;
		try {
			String ftpAddress = (String) SystemConfig.getValue(FtpConstant.FTP_ADDRESS);
			String username = (String) SystemConfig.getValue(FtpConstant.USERNAME);
			String password = (String) SystemConfig.getValue(FtpConstant.PASSWORD);
			String port = (String) SystemConfig.getValue(FtpConstant.PORT);
			String path = (String) SystemConfig.getValue(FtpConstant.FTP_PATH);
			String module = (String) SystemConfig.getValue(FtpConstant.FTP_FILE_TEM_PATH);
			String fileName = file.getOriginalFilename();// 获取文件名
			String imageSuffix=fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
			if(StringUtils.isNotEmpty(fileName) && "".equals(imageSuffix)){
				 log.warn("fileUpload----service:"+"上传文件格式不正确文件名："+fileName+",支持格式为jpg,png");
				throw new FileUploadException("上传文件格式不正确文件名："+fileName);
			}
			long size = file.getSize();
			if (size == 0) {
				throw new FileUploadException("上传文件为空文件");
			}	
			
			fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
			DBPath = "";
			
		//	FtpUtil ftp = null;
			module = "kindergarten";
			if (StringUtils.isNotEmpty(module)) {
				if (StringUtils.isNotEmpty(fileName)) {
					String unique = String.valueOf(System.currentTimeMillis()) + new Random().nextInt(10);
					fileName = unique+"." + imageSuffix;
					DBPath = "/" + module+"/"+fileName; //    不能这个路径/upload/wod
		//			ftp=new FtpUtil(ftpAddress, Integer.parseInt(port), username, password);
					boolean flag = false;
					try {
		//				ftp.login();
						log.info("fileUpload("+module+","+file+","+SystemConfig.getValue("pic.needSmall.module.filetype")+") -  start");
		//				flag = ftp.upload(file.getInputStream(), path + DBPath);
						flag = FileUtil.writeToLocal(path + DBPath, file.getInputStream());
						int category = 1;
						//判断文件是否为视频
						if(BatchFileUploadAction.isVedioFile(fileName)){
							category = 2;
						}
						if(flag){
							WorksShow show = new WorksShow();
							show.setCategory(category);
							show.setCreateTime(DateUtil.getNowDate());
							show.setCreateUser(userExtForm.getAccount());
							if(category == 1){
								show.setImageurl(DBPath);
							}else if(category == 2){
								show.setVideourl(DBPath);
							}
							show.setShopId(shopId);
							show.setType(type);
							worksShowMapper.insertSelective(show);
						}
						
					} catch (Exception e) {
						log.error(e.getMessage());
						e.printStackTrace();
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
			JSONObject result = new JSONObject();
			result.put("success", true);
			result.put("url", DBPath);
			result.put("type", type);
			return result.toString();
		} catch (Exception e) {
		
			e.printStackTrace();
			JSONObject result = new JSONObject();
			result.put("success", false);
			result.put("url", "");
			return result.toString();
		}
	}
}
