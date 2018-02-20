package com.aj.easemob.service.impl;

import java.net.URL;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.aj.easemob.service.EasemobUserService;
import com.aj.easemob.utils.Constants;
import com.aj.easemob.utils.HTTPClientUtils;
import com.aj.easemob.utils.HTTPMethod;
import com.aj.easemob.utils.Roles;
import com.aj.easemob.vo.ClientSecretCredential;
import com.aj.easemob.vo.Credential;
import com.aj.easemob.vo.EndPoints;

@Service("easemobUserService")
public class EasemobUserServiceImpl implements EasemobUserService {

	// 通过app的client_id和client_secret来获取app管理员token
    public static Credential CREDENTIAL_FOR_USER = new ClientSecretCredential(Constants.APP_CLIENT_ID, Constants.APP_CLIENT_SECRET, Roles.USER_ROLE_APPADMIN);
    
	@Override
	public JSONObject createUser(JSONObject param) throws Exception{
		
		return HTTPClientUtils.sendHTTPRequest(EndPoints.USERS_URL, CREDENTIAL_FOR_USER, param, HTTPMethod.METHOD_POST);
	}

	@Override
	public JSONObject getUser(JSONObject param) throws Exception {
		
		URL userPrimaryUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/users/" + param.optString("userName"));
		
		return HTTPClientUtils.sendHTTPRequest(userPrimaryUrl, CREDENTIAL_FOR_USER, null, HTTPMethod.METHOD_GET);
	}

	@Override
	public JSONObject addFriend(JSONObject param) throws Exception {
		URL addFriendSingleUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/users/" + param.optString("ownerUserName") + "/contacts/users/" + param.optString("friendUserName"));

		JSONObject object = new JSONObject();
		object = HTTPClientUtils.sendHTTPRequest(addFriendSingleUrl, CREDENTIAL_FOR_USER, object, HTTPMethod.METHOD_POST);
		return object;
	}

	@Override
	public JSONObject delFriend(JSONObject param) throws Exception {
		URL addFriendSingleUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/users/" + param.optString("ownerUserName") + "/contacts/users/" + param.optString("friendUserName"));

		JSONObject object = new JSONObject();
		object = HTTPClientUtils.sendHTTPRequest(addFriendSingleUrl, CREDENTIAL_FOR_USER, object, HTTPMethod.METHOD_DELETE);
		return object;
	}

	@Override
	public JSONObject getFriends(JSONObject param) throws Exception {
		URL addFriendSingleUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/users/" + param.optString("ownerUserName") + "/contacts/users");

		JSONObject object = new JSONObject();
		object = HTTPClientUtils.sendHTTPRequest(addFriendSingleUrl, CREDENTIAL_FOR_USER, object, HTTPMethod.METHOD_GET);

		return object;
	}

	@Override
	public JSONObject modPwd(JSONObject param) throws Exception {
		URL modPwdUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/users/" + param.optString("ownerUserName") + "/password");

		return HTTPClientUtils.sendHTTPRequest(modPwdUrl, CREDENTIAL_FOR_USER, param, HTTPMethod.METHOD_PUT);
	}

	@Override
	public JSONObject modNick(JSONObject param) throws Exception {
		URL modNickUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/users/" + param.optString("ownerUserName"));

		return HTTPClientUtils.sendHTTPRequest(modNickUrl, CREDENTIAL_FOR_USER, param, HTTPMethod.METHOD_PUT);
	}
}
