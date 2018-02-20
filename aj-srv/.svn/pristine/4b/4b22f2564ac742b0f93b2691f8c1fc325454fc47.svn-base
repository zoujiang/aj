package com.aj.easemob.service.impl;

import java.net.URL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.aj.easemob.service.EasemobGroupService;
import com.aj.easemob.utils.Constants;
import com.aj.easemob.utils.HTTPClientUtils;
import com.aj.easemob.utils.HTTPMethod;
import com.aj.easemob.utils.Roles;
import com.aj.easemob.vo.ClientSecretCredential;
import com.aj.easemob.vo.Credential;
import com.aj.easemob.vo.EndPoints;

@Service("easemobGroupService")
public class EasemobGroupServiceImpl implements EasemobGroupService {
    public static Credential CREDENTIAL_FOR_GROUP = new ClientSecretCredential(Constants.APP_CLIENT_ID, Constants.APP_CLIENT_SECRET, Roles.USER_ROLE_APPADMIN);

	@Override
	public JSONObject createGroup(JSONObject param) throws Exception {
		return HTTPClientUtils.sendHTTPRequest(EndPoints.CHATGROUPS_URL, CREDENTIAL_FOR_GROUP, param, HTTPMethod.METHOD_POST);
	}

	@Override
	public JSONObject delGroup(JSONObject param) throws Exception {
		URL delGroupUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatgroups/" + param.optString("group_id"));
		JSONObject object = new JSONObject();
		object = HTTPClientUtils.sendHTTPRequest(delGroupUrl, CREDENTIAL_FOR_GROUP, object, HTTPMethod.METHOD_DELETE);
		return object;
	}
	
	@Override
	public JSONObject getJoinedGroup(JSONObject param) throws Exception {
		URL getJoinedGroupUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/users/" + param.optString("ownerUserName") + "/joined_chatgroups");
		JSONObject object = new JSONObject();
		object = HTTPClientUtils.sendHTTPRequest(getJoinedGroupUrl, CREDENTIAL_FOR_GROUP, object, HTTPMethod.METHOD_GET);

		return object;
	}

	@Override
	public JSONObject getGroupMembs(JSONObject param) throws Exception {
		URL getJoinedGroupUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatgroups/" + param.optString("group_id") + "/users");
		JSONObject object = new JSONObject();
		object = HTTPClientUtils.sendHTTPRequest(getJoinedGroupUrl, CREDENTIAL_FOR_GROUP, object, HTTPMethod.METHOD_GET);

		return object;
	}

	@Override
	public JSONObject modGroup(JSONObject param) throws Exception {
		URL getJoinedGroupUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatgroups/" + param.optString("group_id"));
		return HTTPClientUtils.sendHTTPRequest(getJoinedGroupUrl, CREDENTIAL_FOR_GROUP, param, HTTPMethod.METHOD_PUT);
	}

	@Override
	public JSONObject getGroupDetail(JSONObject param) throws Exception {
		URL getJoinedGroupUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatgroups/" + param.optString("group_id"));
		JSONObject object = new JSONObject();
		object = HTTPClientUtils.sendHTTPRequest(getJoinedGroupUrl, CREDENTIAL_FOR_GROUP, object, HTTPMethod.METHOD_GET);

		return object;
	}

	@Override
	public JSONObject addGroupMembs(JSONObject param) throws Exception {
		URL getJoinedGroupUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatgroups/" + param.optString("group_id")+"/users");
		return HTTPClientUtils.sendHTTPRequest(getJoinedGroupUrl, CREDENTIAL_FOR_GROUP, param, HTTPMethod.METHOD_POST);
	}

	@Override
	public JSONObject delGroupMembs(JSONObject param) throws Exception {
		JSONArray array = param.optJSONArray("usernames");
		URL getJoinedGroupUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatgroups/" + param.optString("group_id")+"/users/" + array.join(",").toString().replaceAll("\"", ""));
		JSONObject object = new JSONObject();
		object = HTTPClientUtils.sendHTTPRequest(getJoinedGroupUrl, CREDENTIAL_FOR_GROUP, object, HTTPMethod.METHOD_DELETE);
		
		return object;
	}

}
