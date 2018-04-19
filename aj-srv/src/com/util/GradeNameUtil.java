package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aj.kindergarten.vo.TKindergartenGrade;
import com.frame.core.util.SystemConfig;

public class GradeNameUtil {
	
	static String[] ruleNames = {"小小班","小班","中班","大班","大大班"};
	

	public static String getGradeName(TKindergartenGrade grade){
		
		//纠偏天数
		int day = SystemConfig.getValue("aj.grade.upgrade.day") == null ? 60 : Integer.parseInt(SystemConfig.getValue("aj.grade.upgrade.day"));
		//开学日期
		String date = SystemConfig.getValue("aj.grade.upgrade.date") == null ? "09-01" : SystemConfig.getValue("aj.grade.upgrade.date");
		String createDate = grade.getCreateTime();
		String ruleString = "";
		String rule = grade.getRule();
		char[] ruleChar = rule.toCharArray();
		for(int i= 0 ; i< ruleChar.length ; i++){
			if(ruleChar[i] == '1'){
				ruleString += ruleNames[i] +";";
			}
		}
		String series = grade.getSeries();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String currentYear = sdf.format(new Date());
		long dayMil = day * 24 * 60 * 60 * 1000L;
		int index = 0;
		try {
			if(currentYear.equals(series)){
			
				//创建时间小于升学纠偏时间 并且 当前时间大于升学时间
				if(format1.parse(createDate).getTime() - (format1.parse(currentYear+"-"+date).getTime() - dayMil) < 0 
						&& (System.currentTimeMillis() >= format1.parse(currentYear+"-"+date).getTime())){
					index = 1;
				}
			}else if(Integer.parseInt(series) > Integer.parseInt(currentYear)){
				//未开学， 这样的情况不应该存在
			}else if(Integer.parseInt(series) < Integer.parseInt(currentYear)){
				
				if(format1.parse(createDate).getTime() - (format1.parse(currentYear+"-"+date).getTime() - dayMil) < 0
						&& (System.currentTimeMillis() < format1.parse(currentYear+"-"+date).getTime())){
					index = Integer.parseInt(currentYear) - Integer.parseInt(series);
				}else if(format1.parse(createDate).getTime() - (format1.parse(currentYear+"-"+date).getTime() - dayMil) < 0
						&& (System.currentTimeMillis() >= format1.parse(currentYear+"-"+date).getTime())){
					index = Integer.parseInt(currentYear) - Integer.parseInt(series) + 1;
				}else if(format1.parse(createDate).getTime() - (format1.parse(currentYear+"-"+date).getTime() - dayMil) >= 0
						&& (System.currentTimeMillis() < format1.parse(currentYear+"-"+date).getTime())){
					index = Integer.parseInt(currentYear) - Integer.parseInt(series) - 1;
				}else if(format1.parse(createDate).getTime() - (format1.parse(currentYear+"-"+date).getTime() - dayMil) >= 0
						&& (System.currentTimeMillis() >= format1.parse(currentYear+"-"+date).getTime())){
					index = Integer.parseInt(currentYear) - Integer.parseInt(series);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
			
		if(index > ruleString.split(";").length -1){
			index = ruleString.split(";").length -1;
		}
		String className = ruleString.split(";")[index];
		return className;
	}
}
