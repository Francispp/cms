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
public class RoleTO extends BaseTO {
    private String roleName;
    private Collection groups;
    private Collection users;
	
	public RoleTO(){
		groups = new ArrayList();
		users = new ArrayList();
	}
	public RoleTO(String roleName){
		this.roleName = roleName;
		groups = new ArrayList();
		users = new ArrayList();
	}
	
	/**
	 * 
	 * @return
	 */
	public Collection getGroups() {
		return groups;
	}

	/**
	 * 
	 * @return
	 */
	public String getRoleName() {
		return roleName;
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
	public void setGroups(Collection groups) {
		this.groups = groups;
	}

	/**
	 * 
	 * @param string
	 */
	public void setRoleName(String string) {
		roleName = string;
	}

	/**
	 * 
	 * @param string
	 */
	public void setUsers(Collection users) {
		this.users = users;
	}

}
