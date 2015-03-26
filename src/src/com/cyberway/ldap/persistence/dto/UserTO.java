/*
 * Created on Aug 13, 2003
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
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class UserTO extends BaseTO {
	
	private String password;
	private String firstName;
	private String lastName;
	private String userName;
	private String displayName;
	private String mail;
	private String distinguishedName;
	private Collection groups;
	private Collection roles;
	
	/**
	 * @param string
	 * @param inputPassword
	 */
	public UserTO(String username, String password) {
		groups = new ArrayList();
		roles = new ArrayList();
		this.userName = username;
		this.password = password;
		
	}
	
	public UserTO(String userName,String distinguishedName,String displayName,String mail){
		this.userName = userName;
		this.distinguishedName = distinguishedName;
		this.displayName = displayName;
		this.mail = mail;
		groups = new ArrayList();
		roles = new ArrayList();		
	}
	
	public UserTO(){
		groups = new ArrayList();
		roles = new ArrayList();
	}
	public UserTO(String username){
		this.userName = username;
		groups = new ArrayList();
		roles = new ArrayList();
	}
	
	/**
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @param string
	 */
	public void setPassword(String string) {
		password = string;
	}

	

	/**
	 * 
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * 
	 * @return
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * 
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 
	 * @param string
	 */
	public void setFirstName(String string) {
		firstName = string;
	}

	/**
	 * 
	 * @param string
	 */
	public void setLastName(String string) {
		lastName = string;
	}

	/**
	 * 
	 * @param string
	 */
	public void setUserName(String string) {
		userName = string;
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
	public Collection getRoles() {
		return roles;
	}

	/**
	 * 
	 * @param collection
	 */
	public void setGroups(Collection collection) {
		groups = collection;
	}

	/**
	 * 
	 * @param collection
	 */
	public void setRoles(Collection collection) {
		roles = collection;
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

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

}
