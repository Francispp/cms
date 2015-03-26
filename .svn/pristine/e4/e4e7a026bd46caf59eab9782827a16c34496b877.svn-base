package com.cyberway.ldap.persistence.ldap;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.security.auth.login.LoginException;

import org.apache.log4j.Category;

import com.cyberway.ldap.persistence.DataAccessException;
import com.cyberway.ldap.persistence.Security;
import com.cyberway.ldap.persistence.UserDAO;
import com.cyberway.ldap.persistence.dto.BaseTO;
import com.cyberway.ldap.persistence.dto.GroupTO;
import com.cyberway.ldap.persistence.dto.UserTO;

/**
 * Access Users in LDAP 
 */
public class LdapUserDAO extends BaseLdapDAO implements UserDAO{
	

	private static Category log =
		org.apache.log4j.Category.getInstance(LdapUserDAO.class.getName());
	/**
	 * @param props
	 */
	public LdapUserDAO(Properties props) {
		super(props);
	} 
	
	/**
	 * Validates a username and password combination
	 * by attempting to connect to the LDAP server on behalf of the user.
	 * Returns void if succeeds, otherwise throws the LdapLookupException
	 * 
	 * @return void
	 * @throws LdapLookupException
	 * 
	 */
	public boolean validateUser(UserTO user)
		throws LoginException {
		if (user.getPassword()==null||user.getPassword().equals(""))
			throw new LoginException("Password was not specified");
		try {
			log.info("Attempting to validate user: " + user.getUserName());
			DirContext ctx = super.getInitialLdapContext(user);
		}catch (NamingException ne) {
			throw new LoginException(ne.getMessage());
		} catch (LdapLookupException e) {
			throw new LoginException(e.getMessage());
		}

		return true;
	}
	
	/**
	 * Changes this user's password.  Current password must be passed in so
	 * user can be verified before changing
	 * @param prevPass The current password in ldap
	 * @param newPass The replacement password
	 * @throws LdapLookupException
	 */
	public boolean changePassword(String username, String prevPass, String newPass) throws 
		LoginException,LdapLookupException{
		UserTO user = new UserTO(username,prevPass);
		//This throws a LoginException on failure
		this.validateUser(user);

		 
		ModificationItem[] mods = new ModificationItem[1];
		Attribute mod = 
			new BasicAttribute("userPassword", newPass);
		mods[0] = 
			new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod);
		modifyAttributes(this.getDN(user),mods);
		return true;
	}
	
		
	/* (non-Javadoc)
	 * @see com.sample.persistence.ldap.BaseLdapDAO#getDN(com.sample.persistence.dto.BaseTO)
	 */
	public String getDN(BaseTO entity) throws LdapLookupException{
		if(!(entity instanceof UserTO)){
			throw new LdapLookupException("Specified entity was not a UserTO instance");
			
		}
		UserTO user = (UserTO)entity;
		return new StringBuffer()
				.append(getConfiguration().getProperty("principalDNPrefix"))
				.append(user.getUserName())
				.append(",ou=")
				.append(getConfiguration().getProperty("principalOU"))
				.append(",")
				.append(getConfiguration().getProperty("ldapSuffix"))
				.toString();
	}


	/* (non-Javadoc)
	 * @see com.sample.persistence.UserDAO#create(com.sample.persistence.dto.UserTO)
	 */

	public boolean create(UserTO user) throws LdapLookupException {
		return super.create(user);
	}

	/* (non-Javadoc)
	 * @see com.sample.persistence.ldap.BaseLdapDAO#remove()
	 */
	public boolean remove(UserTO user) throws LdapLookupException {
		super.remove(user);
		List groups = (List)getGroupDAO().getGroupsForUsername(user.getUserName());

		Iterator it = groups.iterator();
		while(it.hasNext()){
			GroupTO group = (GroupTO)it.next();
			getGroupDAO().removeUserFromGroup(user,group);
		}		
		return true;
	}



	/* (non-Javadoc)
	 * @see com.sample.persistence.ldap.BaseLdapDAO#getAttributes(com.sample.persistence.transferobjects.BaseTO)
	 */
	public Attributes getAttributes(BaseTO entity)throws LdapLookupException{
		if(!(entity instanceof UserTO)){
			throw new LdapLookupException("Specified entity was not a UserTO instance");
		}
		UserTO user = (UserTO) entity;
		// Create a container set of attributes
		Attributes container = new BasicAttributes();

		// Create the objectclass to add
		Attribute objClasses = new BasicAttribute("objectClass");
		objClasses.add("top");
		objClasses.add("person");
		objClasses.add("organizationalPerson");
		objClasses.add("inetOrgPerson");

		// Assign the username, first name, and last name
		String cnValue = new StringBuffer(user.getFirstName())
			.append(" ")
			.append(user.getLastName())
			.toString();
		Attribute cn = new BasicAttribute("cn", cnValue);
		Attribute givenName = new BasicAttribute("givenName", user.getFirstName());
		Attribute sn = new BasicAttribute("sn", user.getLastName());
		Attribute uid = new BasicAttribute("uid", user.getUserName());
		// Add password
		Attribute userPassword;
		try {
			userPassword =
				new BasicAttribute("userPassword",
					Security.encryptSHA(user.getPassword()));
		} catch (NoSuchAlgorithmException e) {
			throw new LdapLookupException(e);
		}
		// Add these to the container
		container.put(objClasses);
		container.put(cn);
		container.put(sn);
		container.put(givenName);
		container.put(uid);
		container.put(userPassword);
		return container;
	}

	
		

	/* (non-Javadoc)
	 * @see com.sample.persistence.UserDAO#getAllUsers()
	 */
	public Collection getAllUsers() throws DataAccessException {
		String subcontext = new StringBuffer()
			.append("ou=")
			.append(getConfiguration().getProperty("principalOU"))
			.append(",")
			.append(getConfiguration().getProperty("ldapSuffix")).toString();

		String filter =
			new StringBuffer().append("(objectClass=inetOrgPerson)").toString();
		SearchControls controls = new SearchControls();
		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		List users = new ArrayList();
		List strings = (List) super.search(subcontext,filter,controls);
		Iterator it = strings.iterator();
		while(it.hasNext()){
			String s = (String) it.next();
			UserTO user = new UserTO(s);
			users.add(user);
		}
		return users;
	}

	/* (non-Javadoc)
	 * @see com.sample.persistence.UserDAO#exists(com.sample.persistence.transferobjects.UserTO)
	 */
	public boolean exists(UserTO entity) throws LdapLookupException{
		return super.exists(entity);
	}

}
