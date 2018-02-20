package com.qm.shop.bank.action;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frame.core.action.FtpImgDownUploadAction;
import com.qm.shop.bank.service.BankInfoService;


@Controller
@RequestMapping("/shop")
@Scope("prototype")
public class BankInfoAction extends FtpImgDownUploadAction {
	@Autowired
	private BankInfoService bankInfoService;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@RequestMapping("/bank/all")
	@ResponseBody
	public String all() {
		JSONObject modelMap = new JSONObject();  
		List<Map<String, Object>> list = bankInfoService.getAll();
		modelMap.put("success", true);
		modelMap.put("data", list);
		return modelMap.toString();
	}
	
}
