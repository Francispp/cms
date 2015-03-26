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
import com.cyberway.ldap.persistence.GroupDAO;
import com.cyberway.ldap.persistence.dto.BaseTO;
import com.cyberway.ldap.persistence.dto.GroupTO;
import com.cyberway.ldap.persistence.dto.UserTO;


/**
 * Access Groups in LDAP
 */
public class LdapGroupDAO extends BaseLdapDAO implements GroupDAO{
	

	/**
	 * 
	 * @param props
	 */
	public LdapGroupDAO(Properties props) {
		super(props);
	}
	
	/* (non-Javadoc)
	 * @see com.sample.persistence.GroupDAO#exists(com.sample.persistence.dto.GroupTO)
	 */
	public boolean exists(GroupTO group)throws LdapLookupException{
		return super.exists(group);
	}

	/**
	 * Add a User to this Group
	 * @param user The user to add
	 */
	public void addUserToGroup(UserTO user, GroupTO group) throws LdapLookupException{

		ModificationItem[] mods = new ModificationItem[1];
		Attribute mod = 
			new BasicAttribute("uniqueMember", getUserDAO().getDN(user));
		mods[0] = 
			new ModificationItem(DirContext.ADD_ATTRIBUTE, mod);
		
		modifyAttributes(this.getDN(group),mods);
	}
	
	/**
	 * Returns the collection of all the groups which this User is a member
	 * of.
	 * 
	 * @return Collection List of GroupTO objects
	 * @throws LdapLookupException
	 */
	public Collection getGroupsForUsername(String username) throws LdapLookupException {
		UserTO user = new UserTO(username);
	
		// Set up criteria to search on
		String filter = new StringBuffer()
			.append("(&")
			.append("(objectClass=groupOfUniqueNames)")
			.append("(uniqueMember=")
			.append(getUserDAO().getDN(user))
			.append(")")
			.append(")")
			.toString();
			
		// Set up search constraints
		SearchControls cons = new SearchControls();
		cons.setSearchScope(SearchControls.SUBTREE_SCOPE);	
		List groups = new ArrayList();
		List strings = (List) super.search(filter,cons);
		Iterator it = strings.iterator();
		while(it.hasNext()){
			String s = (String) it.next();
			GroupTO group = new GroupTO(s);
			groups.add(group);
		}
		return groups;
	}
			
	/**
	 * Remove a user from this Group
	 * @param user
	 * @throws LdapLookupException
	 */
	public void removeUserFromGroup(UserTO user,GroupTO group) throws LdapLookupException{
		
		if(!exists(group)){
			throw new LdapLookupException("The specified Group does not exist");
		}
		ModificationItem[] mods = new ModificationItem[1];
		Attribute mod = 
			new BasicAttribute("uniqueMember", getUserDAO().getDN(user));
		mods[0] = 
			new ModificationItem(DirContext.REMOVE_ATTRIBUTE, mod);
		
		modifyAttributes(this.getDN(group),mods);
			
	}



	/* (non-Javadoc)
	 * @see com.sample.persistence.ldap.BaseLdapDAO#getDN(com.sample.persistence.dto.BaseTO)
	 */
	public String getDN(BaseTO entity)throws LdapLookupException{
		if(! (entity instanceof GroupTO)){
			throw new LdapLookupException("Specified entity was not a GroupTO instance");
		}
		GroupTO group = (GroupTO)entity;
		
		return new StringBuffer()
			.append(getConfiguration().getProperty("groupDNPrefix"))
			.append(group.getGroupName())
			.append(",ou=")
			.append(getConfiguration().getProperty("groupOU"))
			.append(",")
			.append(getConfiguration().getProperty("ldapSuffix"))
			.toString();
	}

	
	/* (non-Javadoc)
	 * @see com.sample.persistence.ldap.BaseLdapDAO#remove()
	 */
	public boolean remove(GroupTO group) throws LdapLookupException {
		return super.remove(group);
	}

	/* (non-Javadoc)
	 * @see com.sample.persistence.ldap.BaseLdapDAO#getAttributes(com.sample.persistence.transferobjects.BaseTO)
	 */
	public Attributes getAttributes(BaseTO entity)throws LdapLookupException{
		if(!(entity instanceof GroupTO)){
				throw new LdapLookupException("Specified entity was not a GroupTO instance");
		}
		GroupTO group = (GroupTO)entity;
		// Create a container set of attributes
		Attributes container = new BasicAttributes();
		// Create the objectclass to add
		Attribute objClasses = new BasicAttribute("objectClass");
		objClasses.add("top");
		objClasses.add("groupOfUniqueNames");

		// Assign the name and description to the group
		Attribute cn = new BasicAttribute("cn", group.getGroupName());
		Attribute member =
			new BasicAttribute("uniqueMember",getConfiguration().getProperty("rootDN"));//
		// Add these to the container
		container.put(objClasses);
		container.put(cn);
		container.put(member);
		return container;
	}

	/* (non-Javadoc)
	 * @see com.sample.persistence.GroupDAO#create(com.sample.persistence.transferobjects.GroupTO)
	 */
	public boolean create(GroupTO group) throws DataAccessException {
		return super.create(group);
	}



	/* (non-Javadoc)
	 * @see com.sample.persistence.GroupDAO#getAllGroups()
	 */
	public Collection getAllGroups() throws DataAccessException {
		String subcontext = new StringBuffer()
			.append("ou=")
			.append(getConfiguration().getProperty("groupOU"))
			.append(",")
			.append(getConfiguration().getProperty("ldapSuffix")).toString();
		
		String filter = "(objectClass=groupOfUniqueNames)";
		SearchControls controls = new SearchControls();
		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		List groups = new ArrayList();
		List strings = (List) super.search(subcontext,filter,controls);
		Iterator it = strings.iterator();
		while(it.hasNext()){
			String s = (String) it.next();
			GroupTO group = new GroupTO(s);
			groups.add(group);
		}
		return groups;
	}

}
