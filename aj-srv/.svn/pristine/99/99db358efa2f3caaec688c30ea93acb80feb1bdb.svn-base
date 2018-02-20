package com.aj.ad.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aj.ad.bean.FileUploadBean;
import com.aj.ad.bean.MsgVo;
import com.frame.core.action.FtpImgDownUploadAction;
import com.frame.core.service.AuthorService;
import com.frame.core.util.SystemConfig;


@Controller
public class PubFileUploadAction extends FtpImgDownUploadAction {
	private Logger logger = Logger.getLogger(PubFileUploadAction.class);
	
	@Autowired
	private AuthorService authorService;
	
	
	//保存
	@RequestMapping("/pub/fileUpload")
	@ResponseBody
	public MsgVo upload(FileUploadBean vo) {
		logger.info("fileUpload:");
		
		MsgVo msg = checkLogin();
		if (msg.isError()) {
			return msg;
		}
		
		try {
		
			if (vo == null) {
				return new MsgVo("请求有误");
			}
			if(vo.getFile() == null || StringUtils.isEmpty(vo.getFile().getOriginalFilename()) ){
				return new MsgVo("请选择需要上传的文件");
			}
			Long maxSize = Long.parseLong(SystemConfig.getValue("file.upload.size")) * 1024 * 1000;
			if (vo.getFile().getSize() > maxSize) {
				return new MsgVo("请选择大小" + SystemConfig.getValue("file.upload.size") + "M以内的文件");
			}
		
			String url = this.fileUpload("ads", vo.getFile());
			
			//String url = "http://c.hiphotos.baidu.com/image/pic/item/0b46f21fbe096b635415ca2e0f338744ebf8ac44.jpg";
			
			logger.info("fileUpload----url:" + url);
			msg = new MsgVo(true);
			msg.setObj(url);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("upload error:" + e.toString());
			msg = new MsgVo("文件上传失败:" + e.getMessage());
		}
		
		
		return msg;
	}
	
	
	
	private MsgVo checkLogin() {
		if (authorService.getSessionUser() == null) {
			return new MsgVo("登录已失效，请重新登录");
		}
		return new MsgVo(true);
	}
	
	
	
	
}
