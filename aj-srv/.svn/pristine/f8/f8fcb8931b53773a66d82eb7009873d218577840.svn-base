package com.aj.easemob.vo;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aj.easemob.utils.Roles;

/**
 * ClientSecretCredential
 * 
 * @author Lynch 2014-09-15
 *
 */
public class ClientSecretCredential extends Credential {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientSecretCredential.class);

	private static URL CLIENTSECRETCREDENTAIL_TOKEN_URL = null;

	@Override
	protected URL getUrl() {
		return CLIENTSECRETCREDENTAIL_TOKEN_URL;
	}

	public ClientSecretCredential(String clientID, String clientSecret, String role) {
		super(clientID, clientSecret);

		if (role.equals(Roles.USER_ROLE_APPADMIN)) {
			CLIENTSECRETCREDENTAIL_TOKEN_URL = EndPoints.TOKEN_APP_URL;
		}
	}

	@Override
	protected GrantType getGrantType() {
		return GrantType.CLIENT_CREDENTIALS;
	}

	@Override
	public Token getToken() {

		if (null == token || token.isExpired()) {
			try {
				JSONObject param = new JSONObject();
				param.put("grant_type", "client_credentials");
				param.put("client_id", tokenKey1);
				param.put("client_secret", tokenKey2);
				List<NameValuePair> headers = new ArrayList<NameValuePair>();
				headers.add(new BasicNameValuePair("Content-Type", "application/json"));

				HttpPost httpPost = new HttpPost();
				httpPost.setURI(CLIENTSECRETCREDENTAIL_TOKEN_URL.toURI());

                for (NameValuePair nameValuePair : headers) {
                    httpPost.addHeader(nameValuePair.getName(), nameValuePair.getValue());
                }
				httpPost.setEntity(new StringEntity(param.toString(), "UTF-8"));

				HttpResponse tokenResponse = client.execute(httpPost);
				HttpEntity entity = tokenResponse.getEntity();

				String results = EntityUtils.toString(entity, "UTF-8");

				LOGGER.info("-----------------------------返回结果-------------------------------statuscode:"
						+ tokenResponse.getStatusLine().toString());

				if (tokenResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

					JSONObject json = JSONObject.fromObject(results);
					String accessToken = json.getString("access_token");
					Long expiredAt = System.currentTimeMillis() + json.getLong("expires_in") * 1000;

					token = new Token(accessToken, expiredAt);
				}
			} catch (Exception e) {
				throw new RuntimeException("Some errors occurred while fetching a token by username and password .");
			}
		}

		return token;
	}

}
