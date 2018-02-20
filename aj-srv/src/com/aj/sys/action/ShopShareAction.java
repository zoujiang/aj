package com.aj.sys.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aj.shop.vo.TShopInfo;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;

@Controller
@RequestMapping("/shopShare")
public class ShopShareAction {

	@Resource
	GenericDAO baseDAO;
	
	@RequestMapping(value = "/init")
	@ResponseBody
    public String getImage(String id,HttpServletResponse response){
		JSONObject jo = new JSONObject();
		if(id != null && !"".equals(id)){
			TShopInfo  ps = baseDAO.get(TShopInfo.class, id);
			if(ps != null){
				jo.put("success", true);
				ps.setLogo(SystemConfig.getValue("img.http.url") + ps.getLogo());
				jo.put("info", JSONSerializer.toJSON(ps).toString());
			}else{
				jo.put("success", false);
				jo.put("msg", "地址错误");
			}
		}else{
			jo.put("success", false);
			jo.put("msg", "地址错误");
		}
		return jo.toString();
  } 
}
