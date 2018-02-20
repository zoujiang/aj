package com.aj.easemob.service;

import net.sf.json.JSONObject;

public interface EasemobGroupService {


	/**
	 * 创建群组
	 * @param param {
		    "groupname":"testrestgrp12",//群组名称
		    "desc":"server create group", //群组描述
		    "public":true,//是否公开
		    "maxusers":300,//群组人数（可选）
		    "approval":true,//是否需要验证
		    "owner":"18682759607",//所属用户
		    "members":["13637973949","15800299434"]//群组成员（自己除外，可选）， 注：假如成员为空，则members属性不能存在
		}
	 * @return
	 * @throws Exception
	 */
	public JSONObject createGroup(JSONObject param) throws Exception;
	
	/**
	 * 删除群组信息
	 * @param param{"group_id":"217216597725544884"}
	 * @return
	 * @throws Exception
	 */
	public JSONObject delGroup(JSONObject param) throws Exception;
	
	/**
	 * 获取参与的群组
	 * @param param{"ownerUserName":"13637973949"}
	 * @return
	 * @throws Exception
	 */
	public JSONObject getJoinedGroup(JSONObject param) throws Exception;
	
	/**
	 * 获取群组所有成员
	 * @param param{"group_id":"217216597725544884"}
	 * @return
	 * @throws Exception
	 */
	public JSONObject getGroupMembs(JSONObject param) throws Exception;
	
	/**
	 * 修改群组信息
	 * @param param {
				    "group_id":"217216597725544884",
				    "groupname":"testrestgrp12", //群组名称，修改时值不能包含斜杠（"/"）
				    "description":"update+groupinfo", //群组描述，修改时值不能包含斜杠（"/"）
				    "maxusers":300, //群组成员最大数（包括群主），值为数值类型
				}
	 * @return
	 * @throws Exception
	 */
	public JSONObject modGroup(JSONObject param) throws Exception;
	
	/**
	 * 获取群组详情
	 * @param param{"group_id":"217216597725544884"}
	 * @return
	 * @throws Exception
	 */
	public JSONObject getGroupDetail(JSONObject param) throws Exception;
	
	/**
	 * 添加群成员
	 * @param param{
			    "group_id":"217216597725544884",
			    "usernames":["13637973949","15800299434"]
			}
	 * @return
	 * @throws Exception
	 */
	public JSONObject addGroupMembs(JSONObject param) throws Exception;
	
	/**
	 * 删除群成员
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public JSONObject delGroupMembs(JSONObject param) throws Exception;
}
