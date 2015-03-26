/*
 * Created on Aug 13, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cyberway.ldap.persistence.dto;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Base class for all Data Transfer Objects
 *  
 */
public abstract class BaseTO {
	
	/** 
	 * 
	 */
	public boolean equals(Object o) {
			return EqualsBuilder.reflectionEquals(this, o);
	}


	/**
	 * 
	 * @param o
	 * @return
	 */
	public int hashCode(Object o) {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer results = new StringBuffer();
		Class clazz = getClass();

		results.append(getClass().getName() + "\n");

		Field[] fields = clazz.getDeclaredFields();

		try {
			AccessibleObject.setAccessible(fields, true);

			for (int i = 0; i < fields.length; i++) {
				results.append("\t" + fields[i].getName() + "=" +
							   fields[i].get(this) + "\n");
			}
		} catch (Exception e) {
			// Don't bother reporting this
		}

		return results.toString();
	}

}
