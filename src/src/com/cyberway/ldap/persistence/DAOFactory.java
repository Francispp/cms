
package com.cyberway.ldap.persistence;

import java.io.IOException;

import com.cyberway.ldap.persistence.ldap.LdapConfiguration;
import com.cyberway.ldap.persistence.ldap.LdapDAOFactory;



/**
 * Abstract factory class to return factories of a specific type
 * 
 */
public abstract class DAOFactory {
	
	/**
	 * Constant representing an LDAP DAOFactory
	 */
	public static final int LDAP = 1;
	
	/**
	 * 
	 * @return A UserDAO implementation
	 */
	public abstract UserDAO getUserDAO();
	
	/**
	 * 
	 * @return A GroupDAO implementation
	 */
	public abstract GroupDAO getGroupDAO();
	
	/**
	 * 
	 * @return A RoleDAO implementation
	 */
	public abstract RoleDAO getRoleDAO();
	
	/**
	 * Returns a concrete factory of the type specified; use the
	 * constants specified by this file.
	 * @param i The factory type required
	 * @return A concrete factory implementation
	 */
	public static DAOFactory getFactory(int i) throws IOException {
		switch(i){
			case 1: return new LdapDAOFactory(
				LdapConfiguration.getInstance().getProperties());
			default:return null;
		}
		
		
	}


}
