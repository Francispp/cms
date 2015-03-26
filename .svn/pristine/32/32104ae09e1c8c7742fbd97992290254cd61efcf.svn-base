package com.cyberway.core.web.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OnLine {
    private static long online = 0;
    private static long logonline = 0;
    protected static Log logger = LogFactory.getLog(OnLine.class);
    public static long getLogonline(){
    	return logonline;
    }
    public static long getOnline() { 
    	if(logonline>online)
    		return logonline;
        return online; 
    }     
    public synchronized static void raise(){ 
        online++;
        logger.info("getOnline 在线人数："+getOnline());
    }  
    public synchronized static void reduce(){ 
        online--;
        logger.info("reduce 在线人数："+getOnline());
    }
    public synchronized static void logRaise(){
    	logonline++;
    	logger.info("logRaise 已登陆在线人数："+getLogonline());
    }
    public synchronized static void logReduce(){
    	logonline--;
    	logger.info("logReduce 已登陆在线人数："+getLogonline());
    }
}
