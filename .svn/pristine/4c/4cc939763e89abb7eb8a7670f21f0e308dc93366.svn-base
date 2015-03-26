/*
 * Created on Aug 15, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cyberway.ldap.persistence;

import java.util.Collection;

import javax.security.auth.login.LoginException;

import com.cyberway.ldap.persistence.dto.UserTO;

/**
 * Required contract for UserDAO implementations
 */
public interface UserDAO{
	
	public boolean create(UserTO target)throws DataAccessException;
	public boolean remove(UserTO target)throws DataAccessException;
	
	public boolean changePassword(String username, String prevPass, 
		String newPass)throws LoginException,DataAccessException;
	public boolean validateUser(UserTO target) 
		throws LoginException;
	
	public boolean exists(UserTO entity)throws DataAccessException;
	public Collection getAllUsers()throws DataAccessException;
}
