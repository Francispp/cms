package com.cyberway.ldap.persistence.ldap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;

import com.cyberway.ldap.persistence.DataAccessException;
import com.cyberway.ldap.persistence.RoleDAO;
import com.cyberway.ldap.persistence.dto.BaseTO;
import com.cyberway.ldap.persistence.dto.GroupTO;
import com.cyberway.ldap.persistence.dto.RoleTO;

/**
 * 
 *
 * Access Roles in LDAP
 */
public class LdapRoleDAO extends BaseLdapDAO implements RoleDAO{


	/**
	 * @param props
	 */
	public LdapRoleDAO(Properties props) {
		super(props);
		
	}
	
	/* (non-Javadoc)
	 * @see com.sample.persistence.RoleDAO#exists(com.sample.persistence.transferobjects.RoleTO)
	 */
	public boolean exists(RoleTO role)throws LdapLookupException{
		return super.exists(role);
	}
	

	/* (non-Javadoc)
	 * @see com.sample.persistence.RoleDAO#getRolesForGroupName(java.lang.String)
	 */
	public Collection getRolesForGroupName(String groupName)throws LdapLookupException {
		List roles = new ArrayList();
		// Set up criteria to search on
		String filter = new StringBuffer()
			.append("(&")
			.append("(objectClass=groupOfUniqueNames)")
			.append("(uniqueMember=")
			.append(getGroupDAO().getDN(new GroupTO(groupName)))
			.append(")")
			.append(")")
			.toString();
	
		// Set up search constraints
		SearchControls cons = new SearchControls();
		cons.setSearchScope(SearchControls.SUBTREE_SCOPE);	
		List roleNames = (List) search(filter,cons);
		Iterator it = roleNames.iterator();
		while(it.hasNext()){
			String s = (String) it.next();
			roles.add(new RoleTO(s));
		}

		return roles;
	}
	

	/* (non-Javadoc)
	 * @see com.sample.persistence.RoleDAO#getRolesForUsername(java.lang.String)
	 */
	public Collection getRolesForUsername(String target) throws LdapLookupException{
	
		List roles = new ArrayList();
		
		//First get all the groups this user is in
		List groups = (List) getGroupDAO().getGroupsForUsername(target);
		Iterator itGroup = groups.iterator();
		while(itGroup.hasNext()){
			//For each group, get all the roles in that group
			GroupTO group = (GroupTO) itGroup.next();
		
			List rolesInGroup;
			try {
				rolesInGroup =
					(List) getRoleDAO().getRolesForGroupName(
						group.getGroupName());
			}  catch (DataAccessException e) {
				throw new LdapLookupException(e);
			}
			Iterator itRole = rolesInGroup.iterator();
			while(itRole.hasNext()){
				//For each role, add the role to the result
				RoleTO role = (RoleTO) itRole.next();
				roles.add(role);
			}
		}
		return roles;
	}

	

	/* (non-Javadoc)
	 * @see com.sample.persistence.ldap.BaseLdapDAO#getDN()
	 */
	public String getDN(BaseTO entity) throws LdapLookupException {
		if(!(entity instanceof RoleTO)){
			throw new LdapLookupException("Specified entity was not a RoleTO instance");
		}
		RoleTO role = (RoleTO)entity;
		return new StringBuffer()
				.append(getConfiguration().getProperty("roleDNPrefix"))
				.append(role.getRoleName())
				.append(",ou=")
				.append(getConfiguration().getProperty("roleOU"))
				.append(",")
				.append(getConfiguration().getProperty("ldapSuffix"))				
				.toString();
	}

	/* (non-Javadoc)
	 * @see com.sample.persistence.ldap.BaseLdapDAO#getAttributes()
	 */
	public Attributes getAttributes(BaseTO entity)throws LdapLookupException {
		if(!(entity instanceof RoleTO)){
				throw new LdapLookupException("Specified entity was not a RoleTO instance");
		}
		RoleTO role = (RoleTO)entity;
		// Create a container set of attributes
		Attributes container = new BasicAttributes();
		// Create the objectclass to add
		Attribute objClasses = new BasicAttribute("objectClass");
		objClasses.add("top");
		objClasses.add("groupOfUniqueNames");
		Attribute member =
					new BasicAttribute("uniqueMember",getConfiguration().getProperty("rootDN") );
		// Assign the name and description to the group
		Attribute cn = new BasicAttribute("cn", role.getRoleName());
		// Add these to the container
		container.put(objClasses);
		container.put(cn);
		container.put(member);
		return container;
	}

	/* (non-Javadoc)
	 * @see com.sample.persistence.ldap.BaseLdapDAO#remove()
	 */
	public boolean remove(RoleTO role) throws LdapLookupException{
		return super.remove(role);
	}

	/* (non-Javadoc)
	 * @see com.sample.persistence.RoleDAO#create(com.sample.persistence.transferobjects.RoleTO)
	 */
	public boolean create(RoleTO role) throws LdapLookupException{
		return super.create(role);
	}

	/* (non-Javadoc)
	 * @see com.sample.persistence.RoleDAO#getAllRoles()
	 */
	public Collection getAllRoles() throws DataAccessException {
		String subcontext = new StringBuffer()
			.append("ou=")
			.append(getConfiguration().getProperty("roleOU"))
			.append(",")
			.append(getConfiguration().getProperty("ldapSuffix")).toString();
		
		String filter = "(objectClass=groupOfUniqueNames)";
		SearchControls controls = new SearchControls();
		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		List roles = new ArrayList();
		List strings = (List) super.search(subcontext,filter,controls);
		Iterator it = strings.iterator();
		while(it.hasNext()){
			String s = (String) it.next();
			RoleTO role = new RoleTO(s);
			roles.add(role);
		}
		return roles;
		
	}

	/* (non-Javadoc)
	 * @see com.sample.persistence.RoleDAO#update(com.sample.persistence.transferobjects.RoleTO)
	 */
	public boolean update(RoleTO target) {
		
		return false;
	}

	/* (non-Javadoc)
	 * @see com.sample.persistence.RoleDAO#addGroupToRole(com.sample.persistence.transferobjects.GroupTO, com.sample.persistence.transferobjects.RoleTO)
	 */
	public void addGroupToRole(GroupTO group, RoleTO role)
		throws DataAccessException {
		ModificationItem[] mods = new ModificationItem[1];
		Attribute mod = 
			new BasicAttribute("uniqueMember", getGroupDAO().getDN(group));
		mods[0] = 
			new ModificationItem(DirContext.ADD_ATTRIBUTE, mod);
		modifyAttributes(this.getDN(role),mods);

	}
	/* (non-Javadoc)
	 * @see com.sample.persistence.RoleDAO#removeGroupFromRole(com.sample.persistence.transferobjects.GroupTO, com.sample.persistence.transferobjects.RoleTO)
	 */
	public void removeGroupFromRole(GroupTO group, RoleTO role) throws DataAccessException {

		if(!getGroupDAO().exists(group)||!this.exists(role)){
			throw new LdapLookupException("The specified Group or Role does not exist");
		}
		ModificationItem[] mods = new ModificationItem[1];
		Attribute mod = 
			new BasicAttribute("uniqueMember", getGroupDAO().getDN(group));
		mods[0] = 
			new ModificationItem(DirContext.REMOVE_ATTRIBUTE, mod);
		modifyAttributes(this.getDN(role),mods);
		
	}


}
