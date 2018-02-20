package com.aj.coupon.service;

import java.rmi.RemoteException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aam.model.TUser;
import com.aj.coupon.vo.TCouponExchange;
import com.aj.coupon.vo.TCouponInfoEntity;
import com.aj.coupon.vo.TCouponShopInfoEntity;
import com.aj.sms.SingletonClient;
import com.frame.core.dao.GenericDAO;
import com.frame.core.util.SystemConfig;
import com.frame.ifpr.exception.PublicException;
import com.frame.ifpr.service.PublishService;
import com.util.Constant;
import com.util.DateUtils;
import com.util.StringUtil;

import net.sf.json.JSONObject;


/**
 * 优惠券兑换
 * */

@Service("couponExchange")
public class CouponExchangeService implements PublishService{

	private Logger log = Logger.getLogger(CouponExchangeService.class);

	String imgUrl= SystemConfig.getValue("img.http.url");
	
	@Resource
	private GenericDAO baseDAO;
	
	@Override
	public Object publishService(Object obj) throws PublicException {



		JSONObject json = JSONObject.fromObject(obj);
		JSONObject result = new JSONObject();
		String serviceName = json.optString("serviceName");
		JSONObject params = json.optJSONObject("params");
		String userId = params.optString("userId");
		int couponId = params.optInt("couponId");

		JSONObject returnJSON = new JSONObject();
		returnJSON.put("serviceName", serviceName);

		String rt = checkParam(params, new String[]{"userId", "couponId"});
		if(rt != null){
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("errorMsg", rt + "不能为空！");
			return returnJSON.toString();
		}
		TUser user = baseDAO.get(TUser.class, Integer.parseInt(userId) );
		TCouponInfoEntity couponInfo = baseDAO.get(TCouponInfoEntity.class, couponId);
		TCouponShopInfoEntity couponShop = baseDAO.get(TCouponShopInfoEntity.class, couponInfo.getShopId());
		List<TCouponExchange> hadCouponList = baseDAO.getGenericByHql("from TCouponExchange where couponId = ? and userId = ?", couponId, userId);
		if(couponInfo != null && couponInfo.getLeftNum() > 0 && couponInfo.getLeftNum() > hadCouponList.size()){
			couponInfo.setLeftNum(couponInfo.getLeftNum() -1);
			baseDAO.update(couponInfo);
			TCouponExchange change = new TCouponExchange();
			change.setCouponId(couponId);
			change.setCouponName(couponInfo.getName());
			change.setExchangeTime(DateUtils.currentDate());
			change.setShopName(couponShop.getShopName());
			change.setUserId(userId);
			change.setUserNick(user.getNickName());
			change.setUserTel(user.getUserTel());
			String randStr = StringUtil.genRandomStr(8);
			change.setCouponCode(randStr);
			//发送短信， 短信前缀： 【我爱多宝鱼】您好，
			try {
				 String content = "【我爱多宝鱼】您好，" + "您在["+ couponShop.getShopName()+"]兑换的优惠券["+couponInfo.getName()+"]，兑换码："+randStr;
				 SingletonClient.getClient().sendSMS(new String[] {user.getUserTel()}, content, "",5);
			} catch (RemoteException e) {
				log.info("用户"+user.getUserTel()+"兑换优惠券"+couponInfo.getName()+"时，发送短信失败，兑换码为："+randStr);
			}
			baseDAO.save(change);
		}else{
			returnJSON.put("returnCode", Constant.RETURNCODE_FAILED);
			returnJSON.put("errorMsg", "优惠券余票不足或您已经领取了该券的最大单人限制张数");
			return returnJSON.toString();
		}
		
		
		result.put("succMsg", "兑换成功，兑换码已经发送到您的手机上面，使用时，请出示手机短信");
		returnJSON.put("returnCode", Constant.RETURNCODE_SUCCESS);
		returnJSON.put("result", result);
		returnJSON.put("errorMsg", "");
		return returnJSON.toString();
		
	}

	private String checkParam(JSONObject param, String[] keys){

		for(String key : keys){
			if(param.get(key) == null || "".equals(param.get(key).toString())){
				return key;
			}
		}
		return null;
	}

}
