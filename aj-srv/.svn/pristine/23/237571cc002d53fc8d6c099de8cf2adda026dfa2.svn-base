package com.aj.sys.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aj.shop.vo.TShopInfo;
import com.aj.sys.vo.TPhotoShare;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;

@Controller
@RequestMapping("/photoShare")
public class PhotoShareAction {

	@Resource
	GenericDAO baseDAO;
	
	@RequestMapping(value = "/init")
	@ResponseBody
    public String getImage(String id, String type ,HttpServletResponse response){
		JSONObject jo = new JSONObject();
		if(id != null && !"".equals(id)){
			TPhotoShare  ps = baseDAO.get(TPhotoShare.class, id);
			if(ps != null){
				jo.put("success", true);
				/* if("shop".equals(type)){
					jo.put("url", ps.getUrl());
				}else{
					String[] urls = ps.getUrl().split(",");
					StringBuffer rUrlsBuf = new StringBuffer();
					for(String url : urls){
						rUrlsBuf.append(",");
						rUrlsBuf.append(url.substring(0, url.lastIndexOf("/")+1));
						rUrlsBuf.append( url.substring(url.lastIndexOf("/")+1 ).startsWith("s") ? url.substring(url.lastIndexOf("/")+1 ).substring(1) : url.substring(url.lastIndexOf("/")+1 ));
					}
					jo.put("url", rUrlsBuf.toString().substring(1));
				}*/
				jo.put("url", ps.getUrl());
				
				if(ps.getShopId() != null && !"".equals(ps.getShopId())){
					TShopInfo si = baseDAO.get(TShopInfo.class, ps.getShopId());
					if(si != null && si.getLogo() != null && !"".equals(si.getLogo())){
						jo.put("shopLogo",SystemConfig.getValue("img.http.url") + si.getLogo());
					}
					
				}
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
