package com.frame.task.action;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.frame.task.vo.QuartzContext;

public class ContextLoaderListener implements ServletContextListener {  
    /** 
     * @see ServletContextListener#contextInitialized(ServletContextEvent) 
     */  
    public void contextInitialized(final ServletContextEvent event) {  
        QuartzContext.getInstance().setContext(event.getServletContext());  
    }  
  
    /** 
     * @see ServletContextListener#contextDestroyed(ServletContextEvent) 
     */  
    public void contextDestroyed(final ServletContextEvent event) {  
        // do nothing  
    }  
}  

