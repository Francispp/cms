/*
 * Created on Aug 19, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cyberway.ldap.persistence.dto;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 *  
 */
public class GroupTO extends BaseTO {
    private String groupName;
    private String distinguishedName;
    private String displayName;
    private Collection users;
    
    public GroupTO(){
		users = new ArrayList();
    }
	public GroupTO(String groupName){
    	this.groupName = groupName;
		users = new ArrayList();
	}
	
	public GroupTO(String groupName,String distinguishedName,String displayName){
    	this.groupName = groupName;
		this.distinguishedName = distinguishedName;
		this.displayName = displayName;
		users = new ArrayList();
	}
    
	/**
	 * 
	 * @return
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * 
	 * @return
	 */
	public Collection getUsers() {
		return users;
	}

	/**
	 * 
	 * @param string
	 */
	public void setGroupName(String string) {
		groupName = string;
	}

	/**
	 * 
	 * @param collection
	 */
	public void setUsers(Collection collection) {
		users = collection;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getDistinguishedName() {
		return distinguishedName;
	}
	public void setDistinguishedName(String distinguishedName) {
		this.distinguishedName = distinguishedName;
	}

}
