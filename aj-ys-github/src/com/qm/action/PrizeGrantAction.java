package com.qm.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.qm.entities.PrizeGrantInfo;
import com.qm.service.PrizeGrantService;

@RestController
@RequestMapping("/kindergarten/prizeGrant")
public class PrizeGrantAction {

	@Autowired
	private PrizeGrantService  prizeGrantService;
	
	@RequestMapping("/list")
	public String list(String name, Integer offset, Integer limit){
		
		PrizeGrantInfo info = new PrizeGrantInfo();
        info.setTeacherName(name);
        info.setOffset(offset);
        info.setPageSize(limit);
        List<PrizeGrantInfo> list = prizeGrantService.queryList(info);
        int total = prizeGrantService.getTotal(info);
        JSONObject json = new JSONObject();
        json.put("rows",list );
        json.put("total", total);
        return json.toJSONString();
	}
	
}
