package com.aj.familyrelation.util;

public class FamilyRelationUtil {

	/**
	 * 查询反向callId
	 * */
	public static int getCallId(String create_user_sex, int callId){
		//sex 0 男  1女
		int returnCallId = 0;
		if(callId == 1){
			if("0".equals(create_user_sex)){
				returnCallId = 7;
			}else if("1".equals(create_user_sex)){
				returnCallId = 8;
			}
		}
		else if(callId == 2){
			if("0".equals(create_user_sex)){
				returnCallId = 7;
			}else if("1".equals(create_user_sex)){
				returnCallId = 8;
			}
		}
		else if(callId == 3 || callId == 4){
			returnCallId = 16;
		}
		else if(callId == 5 ){
			returnCallId = 6;
		}
		else if(callId == 6 ){
			returnCallId = 5;
		}
		else if(callId == 7 ||  callId == 8){
			if("0".equals(create_user_sex)){
				returnCallId = 1;
			}else if("1".equals(create_user_sex)){
				returnCallId = 2;
			}
		}
		else if(callId == 15){
			if("0".equals(create_user_sex)){
				returnCallId = 21;
			}else if("1".equals(create_user_sex)){
				returnCallId = 22;
			}
		}
		else if(callId == 15){
			if("0".equals(create_user_sex)){
				returnCallId = 3;
			}else if("1".equals(create_user_sex)){
				returnCallId = 4;
			}
		}
		else if(callId == 21 || callId == 22){
			returnCallId = 15;
		}
		else if(callId == 23 || callId == 24){
			if("0".equals(create_user_sex)){
				returnCallId = 7;
			}else if("1".equals(create_user_sex)){
				returnCallId = 8;
			}
		}
		else if(callId == 29 || callId == 30){
			if("0".equals(create_user_sex)){
				returnCallId = 29;
			}else if("1".equals(create_user_sex)){
				returnCallId = 30;
			}
		}
		
		return returnCallId;
	}
}
