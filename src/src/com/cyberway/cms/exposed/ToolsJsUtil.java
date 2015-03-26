package com.cyberway.cms.exposed;

import java.util.Date;

import ognl.Ognl;

import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.utils.UtilDateTime;

public class ToolsJsUtil {
	
	/**
	 * 字符类型转换成Long
	 * @param id
	 * @return
	 */
	public static final Long toLong(String id){
		if(StringUtil.isNumber(id))
			return new Long(id);
		else
			return null;
	}
	/**
	 * 指定时间是否在指定天数之内(如：最新N天之内的条件)
	 * @param date 
	 * @param numDate 天数
	 * @return
	 */
	public boolean thanDateWithCurrentDate(java.util.Date date,int numDate){
		boolean rt=false;
		if(date!=null){
			if(date.getTime()+1000*60*60*24*numDate>(new Date()).getTime())
			 rt=true;
		}
		return rt;
	}
	/**
	 * 获得指定对象 属性值
	 * @param tag
	 * @param objName
	 * @return
	 */
	public Object getProperty(Object obj,String fieldName) {
		Object valueObj = null;
		try {		    
		    if(obj!=null && fieldName!=null )
		    	valueObj = Ognl.getValue(fieldName, obj);
		} catch (Exception e) {

		}
		return valueObj;
	}
	//公用字符串操作类
	public static final StringUtil STRING_UTIL=new StringUtil();
	//公用日期操作类
	public static final UtilDateTime DATE_UTIL=new UtilDateTime();
	
}
