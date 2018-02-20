package com.frame.ifpr.util;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.frame.core.util.SystemConfig;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/*密钥长度8位*/
public class DesBase64Tool {
	private static Logger logger = Logger.getLogger(DesBase64Tool.class);
	/*private static ResourceBundle rb = ResourceBundle
			.getBundle("config.system");*/
	public static final String DEF_KEY = SystemConfig.getValue("ifpr.des.key");// 默认KEY

	/* 解密 */
	public static String decryptDES(String decryptString) {
		return decryptDES(decryptString, DEF_KEY);
	}

	/* 加密 */
	public static String encryptDES(String encryptString) {
		return encryptDES(encryptString, DEF_KEY);
	}

	/* 加密 */
	public static String encryptDES(String encryptString, String encryptKey) {
		SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
		try {
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptedData = cipher.doFinal(encryptString
					.getBytes("UTF-8"));
			String str = new BASE64Encoder().encode(encryptedData);
			return toHexFromString(str); 
		} catch (Exception e) {
			logger.error(
					"DES加密出错key:" + encryptKey + "===str:" + encryptString, e);
			e.printStackTrace();
		}
		return null;

	}

	/* 解密 */
	public static String decryptDES(String decryptString, String decryptKey) {
		try {
			if(decryptKey == null){
				decryptKey = DEF_KEY;
			}
			String str = toStringFromHex(decryptString);
			byte[] byteMi = new BASE64Decoder().decodeBuffer(str);
			SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte decryptedData[] = cipher.doFinal(byteMi);
			return new String(decryptedData, "UTF-8");
		} catch (Exception e) {
			logger.error(
					"DES解密出错key:" + decryptKey + "===str:" + decryptString, e);
			e.printStackTrace();
		}
		return null;
	}

	// s-->16
	private static String toHexFromString(String s) {
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			if(ch!=10&&ch!=13)
				str.append(Integer.toHexString(ch));
		}
		return str.toString();
	}

	// 16-->s
	private static String toStringFromHex(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
						i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "utf-8");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}


 
	public static void main(String[] args) throws Exception {
		
		/*String key = "IDAPwd15";
		
		Map parMap = new HashMap();
		parMap.put("service_name", "getDoctorList");
		//parMap.put("orgId", "107");
		parMap.put("staffTypeId", "");
		parMap.put("depId", "20072430");
		//parMap.put("depId", "20072625");
		
		
		//parMap.put("userId", "6293c349b96747ee95c4e41eed7e30b8");
		//parMap.put("tonken", "MTYyNDI3NzU4Mw\u003d\u003d");
		
		String text = JSONObject.fromObject(parMap).toString();
		
		
		//String text111 = "{  \"userID\": \"0f01d7df25c8439c8c3ad9673447e51c\",  \"callType\": \"1\",  \"serviceName\": \"mkt_app_osc_req\",  \"appItems\": [  {  \"tpCode\": \"C000032TP23001352\",  \"merchantId\": \"TP23001352\",  \"price\": \"20\",  \"appId\": \"AP500000000000010403\",  \"prodId\": \"cpt0000000000002\",  \"prodName\": \"预约挂号\",  \"prodDesc\": {  \"医院\": \"第三军医大学西南医院\",  \"科室\": \"关节外科中心门诊\",  \"医生\": \"陈光兴\",  \"就诊时间\": \"2013-01-23 白天\"  }  },  {  \"tpCode\": \"C000575TP23001133\",  \"merchantId\": \"TP23001133\",  \"price\": \"60\",  \"appId\": \"AP500000000000010316\",  \"prodId\": \"cpt0000000000002\",  \"prodName\": \"电影\",  \"prodDesc\": {  \"影院\": \"重庆UME影城(渝中店)\",  \"场次\": \"2014-06-01 17:05\",  \"电影\": \"窃听风云3\",  \"座位\": \"L排10座\"  }  }  ]  }";
		//String text111 = "{  \"serviceName\":\"aam_area_list_req\",  \"callType\":\"001\",  \"params\": {  \"showGd\":\"0\",  \"pageSize\":\"100\",  \"currentPage\":\"1\"  }  }  ";
		//System.out.println(text);
		//System.out.println(DesBase64Tool.encryptDES(text, key));
		String text111 = "123abc";
		//System.out.println(DesBase64Tool.encryptDES("ALA050", key));
		String str = DesBase64Tool.encryptDES(text111, key);*/
		String str = "5a3655506f2b7a39524c695279453341436479743044623264633245687167374d632f58466153314f41443370317150786442452b71573174462f65367a6533572f74685036597545524e56536b644e783448756f70746f4b345636383857486f767551687444697346773d";
		//System.out.println(str);
		System.out.println(DesBase64Tool.decryptDES("665a4e33663532375074303d", "AJ100000"));

		
	}

}