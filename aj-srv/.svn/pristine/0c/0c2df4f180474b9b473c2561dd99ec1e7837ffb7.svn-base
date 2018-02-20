package com.frame.core.util.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.Converter;

public class DateConvert implements Converter {

    public Object convert(Class arg0, Object target) {
    	if(target instanceof String){
    		 String p = (String)target;
    	     if(p== null || p.trim().length()==0){
    	          return null;
    	     }   
    	     try{
    	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	        return df.parse(p.trim());
    	     }catch(Exception e){
    	    	 try {
    	             SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	             return df.parse(p.trim());
    	         } catch (ParseException ex) {
    	                return target;
    	         }
    	     }
    	}else if(target instanceof Date){
    		 Date date=(Date)target;
    		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		 return df.format(date);
    	}else if(target instanceof Character){
    		 return target+"";
    	}
        return target;
    }
}
