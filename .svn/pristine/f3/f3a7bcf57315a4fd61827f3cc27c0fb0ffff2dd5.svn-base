/*
 * Created on Aug 15, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.cyberway.ldap.persistence;

import java.util.Collection;

import com.cyberway.ldap.persistence.dto.GroupTO;
import com.cyberway.ldap.persistence.dto.RoleTO;


/**
 * Required contract for RoleDAO implementations 
 */
public interface RoleDAO{
	
	public boolean create(RoleTO role)throws DataAccessException;
	public boolean remove(RoleTO role)throws DataAccessException;
	public boolean exists(RoleTO entity)throws DataAccessException;
	public void addGroupToRole(GroupTO group,RoleTO role)throws DataAccessException;
	public void removeGroupFromRole(GroupTO group,RoleTO role)throws DataAccessException;
	
	public Collection getAllRoles()throws DataAccessException;
	public Collection getRolesForGroupName(String groupName)throws DataAccessException;
	public Collection getRolesForUsername(String target)throws DataAccessException;
}
