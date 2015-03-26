/*
 * Created on Aug 15, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cyberway.ldap.persistence;

import java.util.Collection;

import com.cyberway.ldap.persistence.dto.GroupTO;
import com.cyberway.ldap.persistence.dto.UserTO;


/**
 * Required contract for GroupDAO implementations
 */
public interface GroupDAO{
	
	public boolean create(GroupTO group)throws DataAccessException;
	public boolean remove(GroupTO group)throws DataAccessException;
	
	public boolean exists(GroupTO entity)throws DataAccessException;
	public void addUserToGroup(UserTO user,GroupTO group)throws DataAccessException;
	public void removeUserFromGroup(UserTO user,GroupTO group)throws DataAccessException;
	public Collection getAllGroups()throws DataAccessException;
	public Collection getGroupsForUsername(String target)throws DataAccessException;
	
}
