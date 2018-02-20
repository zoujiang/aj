package com.frame.task.vo;

import javax.servlet.ServletContext;

public class QuartzContext {  
    private final static QuartzContext instance = new QuartzContext ();  
    private static ServletContext context;  
  
    private QuartzContext () {  
    }  
  
    public static QuartzContext getInstance() {  
        return instance;  
    }  
  
    public void setContext(final ServletContext context) {  
    	QuartzContext.context = context;  
    }  
  
    public ServletContext getContext() {  
        return context;  
    }  
} 