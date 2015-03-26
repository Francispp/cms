/*
 * Created on Aug 13, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cyberway.ldap.persistence.ldap;

import java.util.Properties;

import com.cyberway.ldap.persistence.DAOFactory;
import com.cyberway.ldap.persistence.GroupDAO;
import com.cyberway.ldap.persistence.RoleDAO;
import com.cyberway.ldap.persistence.UserDAO;


/**
 * This class is responsible for returning LDAP DAO classes to the 
 * client.  The factory is created with a properties object which
 * encapsulates details about the current LDAP configuration.
 * 
 *  
 */
public class LdapDAOFactory extends DAOFactory{
	private Properties configuration;

	/**
	 * Construct with a properties object which encapsulates details
	 * about the LDAP configuration
	 * @param configuration A Properties object with details of the 
	 * 						LDAP configuration
	 */
	public LdapDAOFactory(Properties configuration){
		this.configuration=configuration;
	}


	/* (non-Javadoc)
	 * @see com.sample.persistence.DAOFactory#getUserDAO()
	 */
	public UserDAO getUserDAO() {
		return (UserDAO) new LdapUserDAO(this.configuration);
	}


	/* 
	 * @see com.sample.persistence.DAOFactory#getGroupDAO()
	 */
	public GroupDAO getGroupDAO() {
		return (GroupDAO) new LdapGroupDAO(this.configuration);
	}

	/*
	 * @see com.sample.persistence.DAOFactory#getRoleDAO()
	 */
	public RoleDAO getRoleDAO() {
		return (RoleDAO) new LdapRoleDAO(this.configuration);
	}

}
