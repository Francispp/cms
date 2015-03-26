package com.cyberway.core.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import ognl.DefaultTypeConverter;


/**
 * 日期格式转换器
 * @author caiw
 *
 */
public class DateConverter extends DefaultTypeConverter {
	private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public Object convertValue(Map ognlContext, Object value, Class toType) {
		Object result = null;
		if(value == null){
			
		}else if (toType == Date.class) {
			try {
				result = doConvertToDate(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (toType == String.class) {
			result = doConvertToString(value);
		}
		return result;
	}

	private Date doConvertToDate(Object value) throws Exception {
		Date result = null;
		String regex="(\\d{4}\\-([0-1]{1})*\\d{1}\\-([0-3]{1})*\\d{1}(\\s([0-2]{1})*\\d{1}\\:\\d{1,2}(\\:\\d{1,2})*)*)";
		if (value instanceof String) {
			try {
				String from = ((String)value).trim();
				if(from.equals("")){
					
				}else if(!from.matches(regex)){
					System.out.println("Could not parse date");
					throw new Exception("Could not parse date");
				}else if(from.length()==10){
					result = sdf1.parse(from);
				}else if(from.length()==16){
					result = sdf2.parse(from);
				}else if(from.length()==19){
					result = sdf3.parse(from);
				}
			} catch (ParseException e) {
				throw new Exception("Could not parse date", e);
			}
		} else if (value instanceof Object[]) {
			// let's try to convert the first element only 
			Object[] array = (Object[]) value;
			if ((array != null) && (array.length >= 1)) {
				value = array[0];
				result = doConvertToDate(value);
			}
		} else if (Date.class.isAssignableFrom(value.getClass())) {
			result = (Date) value;
		}
		return result;
	}

	private String doConvertToString(Object value) {
		String result = null;
		
		if (value instanceof Date) {
			Date from = (Date)value;
			if(from.getHours()==0&&from.getMinutes()==0&&from.getSeconds()==0){
				result = sdf1.format(from);
			}else{
				result = sdf2.format(from);
			}
		}
		return result;
	}

}
