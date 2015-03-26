package com.cyberway.core.utils;

import java.beans.PropertyDescriptor;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import ognl.Ognl;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author caiw
 * @版本： 2.0
 */
public class BeanUtil {

    /**
     * 复制对象
     * @param spojo 源对象
     * @param tpojo 目标对象
     * @throws Exception
     */
    public static void updateObject(Object spojo,Object tpojo) throws Exception {
		PropertyDescriptor[] originFields = PropertyUtils.getPropertyDescriptors(spojo);
		for (int i = 0; i < originFields.length; i++) {
			String originFieldName = originFields[i].getName(); // origin字段名称
			Class originFieldType = originFields[i].getPropertyType(); // origin字段类型
			if (originFieldType == null || originFieldType.equals(Class.class)) {
				continue;
			}
			// 分支1：基本类型，如：String Long Integer Date等
			if (isBaseType(originFieldType)) {
				Object originFieldValue = Ognl.getValue(originFieldName,spojo);
				if(originFieldValue != null){
					try{
					Ognl.setValue(originFieldName,tpojo,originFieldValue);
					}catch(Exception e)
					{
						e.printStackTrace();
						continue;
					}
				}else if(!originFieldType.equals(String.class)){
					Ognl.setValue(originFieldName,tpojo,originFieldValue);
				}				
				continue;
			}
		}
    }
    /**
     * 复制对象，但除集合类型之名的
     * @param spojo
     * @param tpojo
     * @throws Exception
     */
    public static void updateObjectNotConllection(Object spojo,Object tpojo) throws Exception {
		PropertyDescriptor[] originFields = PropertyUtils.getPropertyDescriptors(spojo.getClass());
		for (int i = 0; i < originFields.length; i++) {
			String originFieldName = originFields[i].getName(); // origin字段名称
			Class originFieldType = originFields[i].getPropertyType(); // origin字段类型
			if (originFieldType == null || originFieldType.equals(Class.class)) {
				continue;
			}
			// 分支1：基本类型，如：String Long Integer Date等
			if (isBaseType(originFieldType)) {
				Object originFieldValue = Ognl.getValue(originFieldName,spojo);
				if(originFieldValue != null){
					Ognl.setValue(originFieldName,tpojo,originFieldValue);
				}else if(!originFieldType.equals(String.class)){
					Ognl.setValue(originFieldName,tpojo,originFieldValue);
				}				
				continue;
			}
			//分支2:pojo类型
			if (isPojoType(originFieldType)) {
				Object originFieldValue = Ognl.getValue(originFieldName,spojo);
				if(originFieldValue != null){
					Ognl.setValue(originFieldName,tpojo,originFieldValue);
				}			
				continue;
			}	
		}
    }
	

	/**
	 * 更新指下的字段
	 * @param tobj 更新对象
	 * @param sobj 源对象
	 * @param tf_sf  key--目标字段名,value--源字段名
	 * @throws Exception
	 */
	public static void updateObjectFields(Object tobj,Object sobj,Map tf_sf)throws Exception{
		if(tobj==null||sobj==null)
			throw new Exception("object is null!");
		if(tf_sf!=null){
			Iterator it=tf_sf.entrySet().iterator();
			while(it.hasNext()){
				Entry et=(Entry)it.next();
				String tfield=(String)et.getKey();
				String sfiels=(String)et.getValue();
				if(!StringUtil.isEmpty(tfield)&&!StringUtil.isEmpty(sfiels)){
				 Object tempobj=PropertyUtils.getProperty(sobj,sfiels);
				 PropertyUtils.setProperty(tobj,tfield,tempobj);
				}
			}
		}
	}
	public static boolean isBaseType(Class type) {
		if (type == null) {
			return false;
		}
		if (type.equals(Long.class) || type.equals(String.class)
				|| type.equals(Boolean.class) || type.equals(Integer.class)
				|| type.equals(Double.class) || type.equals(Float.class)
				|| type.equals(java.sql.Blob.class)
				|| type.equals(java.util.Date.class)
				|| type.equals(java.math.BigDecimal.class)
				|| type.equals(java.math.BigInteger.class)) {
			return true;
		}
		return false;
	}

	public static boolean isCollection(Class type) {
		if (type == null || type.equals(java.lang.Object.class)) {
			return false;
		} else if (type.equals(java.util.Collection.class)) {
			return true;
		}
		Class[] superInterface = type.getInterfaces();
		Class superClass = type.getSuperclass();
		for (int i = 0; i < superInterface.length; i++) {
			if (isCollection(superInterface[i]))
				return true;
		}
		if (isCollection(superClass))
			return true;
		return false;
	}

	public static boolean isPojoType(Class type) {
		if (type == null) {
			return false;
		}
		if(isBaseType(type))
			return false;
		
		if(isCollection(type))
			return false;
		else
			return true;
			
	}
}
