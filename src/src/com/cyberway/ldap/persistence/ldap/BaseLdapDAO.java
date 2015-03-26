package com.cyberway.ldap.persistence.ldap;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import com.cyberway.ldap.persistence.DAOFactory;
import com.cyberway.ldap.persistence.Security;
import com.cyberway.ldap.persistence.dto.BaseTO;
import com.cyberway.ldap.persistence.dto.UserTO;



/**
 * Superclass for LDAP objects eg: LdapUserDAO, LdapGroupDAO and
 * LdapRoleDAO.
 *  
 */
public abstract class BaseLdapDAO {

	private Properties configuration;

	/**
	 * Constructor creates base classes with knowledge of
	 * the LDAP schema they will be accessing
	 * @param props A Properties object
	 */
	public BaseLdapDAO(Properties props){
		setConfiguration(props);
	}

	/**
	 * Checks that this Entity exists in LDAP.
	 * @return boolean
	 */
	public boolean exists(BaseTO entity) throws LdapLookupException{
		boolean res = false;
		String dn = this.getDN(entity);

		try {
			DirContext ctx = getInitialLdapContext();
			Object obj = ctx.lookup(dn);
			if (obj != null)
				res = true;
		} catch (NamingException e) {
			//NamingException indicates object does not exist
		}
		return res;
	}

	/**
	 * Subclasses need to implement this to return their LDAP DN
	 */
	public abstract String getDN(BaseTO entity) throws LdapLookupException;

	/**
	 * Method create.  Saves this Entity to LDAP.  
	 *
	 * @throws LdapLookupException
	 */
	public boolean create(BaseTO entity) throws LdapLookupException{
		try {
			getInitialLdapContext().createSubcontext(
				this.getDN(entity),this.getAttributes(entity));

		} catch (NamingException e) {
			throw new LdapLookupException(e);
		}
		return true;
	}

	/**
	 * Get the Attributes for this Entity.  This is used when calling the
	 * create method, to populate the values of the entry
	 * @return Attributes
	 * @throws LdapLookupException
	 */
	public abstract Attributes getAttributes(BaseTO entity) throws LdapLookupException;


 	/**
 	 * Generic method for changing the attributes of a given DN,
 	 * for example for adding a user to a group, or a group to a role
 	 *
 	 * @param dn The DN to modify
 	 * @param mods The modifications to apply
 	 */
 	public void modifyAttributes(String dn, ModificationItem[] mods)
 		throws LdapLookupException {
 		try {
 			getInitialLdapContext().modifyAttributes(dn, mods);
 		} catch (NamingException e) {
 			throw new LdapLookupException(e);
 		}
 	}


	/**
	 * Delete this Entity from LDAP. The Entity must exist in the database before this
	 * method is called or an LdapLookupException will be thrown
	 * @throws LdapLookupException
	 */

	public boolean remove(BaseTO entity) throws LdapLookupException{
		if (!this.exists(entity))
			throw new LdapLookupException("The specified entity does not exist");
		try {
			DirContext ctx = getInitialLdapContext();
			ctx.destroySubcontext(this.getDN(entity));
		} catch (NamingException e) {
			throw new LdapLookupException(e);
		}
		return true;
	}


	/**
	 * Returns the Initial Context of the LDAP directory with a user's username and password
	 *
	 * @param username
	 * @param password
	 * @return DirContext
	 * @throws NamingException
	 */
	public DirContext getInitialLdapContext(UserTO user)
		throws NamingException,LdapLookupException{

		if (user.getUserName() == null || user.getUserName().equals("")) {
			throw new LdapLookupException("Username must be specified");
		}
		if (user.getPassword() == null || user.getPassword().equals("")) {
			throw new LdapLookupException("Password must be specified");
		}
		// Get the properties we will need to create the context
		String contextFactory =
			getConfiguration().getProperty("java.naming.factory.initial");
		String providerURL =
			getConfiguration().getProperty("java.naming.provider.url");
		String securityAuth =
			getConfiguration().getProperty("java.naming.security.authentication");

		// Populate the environment for the new context
		Properties props = new Properties();
		props.setProperty(Context.SECURITY_PRINCIPAL,this.getDN(user));
		try {
			props.setProperty(Context.SECURITY_CREDENTIALS, Security.encryptSHA(user.getPassword()));
		} catch (NoSuchAlgorithmException e) {
			throw new LdapLookupException(e);
		}
		props.setProperty(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
		props.setProperty(Context.PROVIDER_URL, providerURL);
		props.setProperty(Context.SECURITY_AUTHENTICATION, securityAuth);
		DirContext dir = new InitialDirContext(props);
		return dir;

	}

	/**
	 * Returns an InitialContext of the LDAP directory with the rootDN username and password
	 * (or another privileged LDAP user who has the ability to edit entries) - the username and password for this
	 * user are set in the properties file - and are copied over to the relevant Context fields in the properties
	 * passed to create the context
	 *
	 * @return DirContext
	 * @throws NamingException
	 */
	public DirContext getInitialLdapContext() throws NamingException {

		// Get the properties we will need for the new context
		String rootDN = getConfiguration().getProperty("rootDN");
		String rootPW = getConfiguration().getProperty("rootPW");
		String contextFactory =
			getConfiguration().getProperty("java.naming.factory.initial");
		String providerURL =
			getConfiguration().getProperty("java.naming.provider.url");
		String securityAuth =
			getConfiguration().getProperty("java.naming.security.authentication");

		// Populate the environment for the new context
		Properties props = new Properties();
		props.setProperty(Context.SECURITY_PRINCIPAL, rootDN);
		props.setProperty(Context.SECURITY_CREDENTIALS, rootPW);
		props.setProperty(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
		props.setProperty(Context.PROVIDER_URL, providerURL);
		props.setProperty(Context.SECURITY_AUTHENTICATION, securityAuth);
		return new InitialDirContext(props);
	}
	/**
	 * Method search.  Generic search method for locating an object
	 * matching the given Filter and SearchControls
	 * @param filter The Filter to apply
	 * @param controls The SearchControls to apply
	 * @return List
	 */
	public Collection search(String filter, SearchControls controls)throws LdapLookupException{
		String ldapSuffix = getConfiguration().getProperty("ldapSuffix");
		return search(ldapSuffix,filter,controls);
	}
	/**
	 * Method search.  Generic search method for locating an object
	 * matching the given Filter and SearchControls, within the specified subcontext.
	 * If you want to search the whole tree, use search(String,SearchControls)
	 *
	 * @param subcontext The subcontext to search, eg "ou=Roles,dc=sample,dc=com"
	 * @param filter The Filter to apply
	 * @param controls The SearchControls to apply
	 * @return List
	 */
	public Collection search(String subcontext, String filter, SearchControls controls)throws LdapLookupException{
		List ret = new ArrayList();

		NamingEnumeration results;
		try {
			DirContext ctx = getInitialLdapContext();
			results = ctx.search(subcontext, filter, controls);
			while (results.hasMoreElements()) {
				SearchResult result = (SearchResult)results.nextElement();
				ret.add(getCNForDN(result.getName()));
			}
		}
		catch (NamingException e) {
				throw new LdapLookupException(e);
		}
		return ret;
	}

	/**
	 * Returns the CN for the given DN
	 *
	 * @param dn The DN to resolve
	 * @return cn The local CN
	 */
	public static String getCNForDN(String dN) {
		int start = dN.indexOf("=");
		int end = dN.indexOf(",");

		if (end == -1) {
			end = dN.length();
		}

		return dN.substring(start + 1, end);
	}

	/**
	 * Utility method for obtaining an LdapGroupDAO instance
	 * @return An LdapGroupDAO
	 * @throws LdapLookupException
	 */
	protected LdapGroupDAO getGroupDAO() throws LdapLookupException{
		LdapDAOFactory factory;
		try {
			factory = (LdapDAOFactory) DAOFactory.getFactory(DAOFactory.LDAP);
		} catch (IOException e) {
			throw new LdapLookupException(e);
		}
		return (LdapGroupDAO) factory.getGroupDAO();
	}
	/**
	 * Utility method for obtaining an LdapUserDAO instance
	 * @return LdapUserDAO
	 * @throws LdapLookupException
	 */
	protected LdapUserDAO getUserDAO() throws LdapLookupException{
		LdapDAOFactory factory;
		try {
			factory = (LdapDAOFactory) DAOFactory.getFactory(DAOFactory.LDAP);
		} catch (IOException e) {
			throw new LdapLookupException(e);
		}
		return (LdapUserDAO) factory.getUserDAO();
	}
	/**
	 * Utility method for obtaining an LdapRoleDAO instance
	 * @return LdapRoleDAO
	 * @throws LdapLookupException
	 */
	protected LdapRoleDAO getRoleDAO() throws LdapLookupException{
		LdapDAOFactory factory;
		try {
			factory = (LdapDAOFactory) DAOFactory.getFactory(DAOFactory.LDAP);
		} catch (IOException e) {
			throw new LdapLookupException(e);
		}
		return (LdapRoleDAO) factory.getRoleDAO();
	}

	/**
	 * Set the current LDAP configuration
	 * @param configuration The configuration to apply
	 */
	public void setConfiguration(Properties configuration) {
		this.configuration = configuration;
	}

	/**
	 * Get the current LDAP configuration
	 * @return Properties
	 */
	public Properties getConfiguration() {
		return configuration;
	}

}
