package com.aj.easemob.service;

import net.sf.json.JSONObject;


public interface EasemobUserService {
	
	/**
	 * 注册用户
	 * @param param  {"username":"jliu","password":"123456","nickname":"建国"} 
	 * @return
	 * @throws Exception
	 */
	public JSONObject createUser(JSONObject param) throws Exception;
	
	/**
	 * 获取用户信息
	 * @param param {"username":"jliu"}
	 * @return
	 * @throws Exception
	 */
	public JSONObject getUser(JSONObject param) throws Exception;
	
	/**
	 * 添加好友
	 * @param param{"ownerUserName":"18682759607","friendUserName":"15800299434"}
	 * @return
	 * @throws Exception
	 */
	public JSONObject addFriend(JSONObject param) throws Exception;
	
	/**
	 * 删除好友
	 * @param param{"ownerUserName":"18682759607","friendUserName":"15800299434"}
	 * @return
	 * @throws Exception
	 */
	public JSONObject delFriend(JSONObject param) throws Exception;	
	
	/**
	 * 获取好友信息
	 * @param param{"ownerUserName":"18682759607"}
	 * @return
	 * @throws Exception
	 */
	public JSONObject getFriends(JSONObject param) throws Exception;
	
	/**
	 * 重置密码
	 * @param param{"ownerUserName":"186827596071","newpassword":"111111"}
	 * @return
	 * @throws Exception
	 */
	public JSONObject modPwd(JSONObject param) throws Exception; 
	
	/**
	 * 修改昵称
	 * @param param{"ownerUserName":"186827596071","nickname":"111111"}
	 * @return
	 * @throws Exception
	 */
	public JSONObject modNick(JSONObject param) throws Exception;
}
