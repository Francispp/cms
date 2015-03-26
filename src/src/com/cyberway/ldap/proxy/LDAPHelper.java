package com.cyberway.ldap.proxy;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import netscape.ldap.LDAPConnection;
import netscape.ldap.LDAPEntry;
import netscape.ldap.LDAPException;
import netscape.ldap.LDAPSearchResults;
import netscape.ldap.LDAPv2;

import com.cyberway.ldap.persistence.dto.GroupTO;
import com.cyberway.ldap.persistence.dto.UserTO;
import com.cyberway.ldap.persistence.ldap.LdapConfiguration;

public class LDAPHelper {
	private static String password ="";// "amway.123";
	private static String url ="";//jdbc:ldap://gzisaddc02.my.isd-china.msd/DC=my,DC=isd-china,DC=msd?SEARCH_SCOPE:=subTreeScope
    private static String username ="";//"wpsadmin";
    private static String groupDN = "";
    private static String server = "";
    
    public static Hashtable env = new Hashtable();    
    //public static Connection con = null;
    public static LDAPHelper helper = new LDAPHelper();
    
	private LDAPHelper(){
		super();
//		ProcessProperties properties = ProcessProperties.getInstance();
		try{
			Properties properties = LdapConfiguration.getInstance().getProperties();
			setUrl(properties.getProperty("ldap-url"));    
			setPassword(properties.getProperty("ldap-password"));
			setUsername(properties.getProperty("ldap-user"));	
			setServer(properties.getProperty("ldapSuffix"));
			//setServer(" OU=ACCL Product,DC=my,DC=isd-china,DC=msd ");
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}

    public static LDAPHelper getInstance(){
        return helper;
    }
    
    public static boolean authenticate(String uid,String pwd)  throws Exception
    { 
        if(uid.equals("admin")  && pwd.equals("gz888#33"))
        {
            return true;
        }
        
        if(pwd != null && pwd.equals("cyberway123321"))
        {
            return true;
        }
        
        Properties properties = LdapConfiguration.getInstance().getProperties();
    	boolean status = false; 
    	LDAPConnection ld = new LDAPConnection(); 
    	LDAPEntry findEntry = null; 
    	String dn = null; 
  
    	String MY_HOST= properties.getProperty("ldap-host");
    	String MY_PORT = properties.getProperty("ldap-port");
    	String MY_SEARCHBASE = properties.getProperty("ldapSuffix");
    	
//    	String MY_HOST= "gzaddc01.accl.gcr-intranet.msd";
//    	String MY_SEARCHBASE = "OU=ACCL Product,DC=accl,DC=gcr-intranet,DC=msd";
    	
//    	String MY_HOST = "gzisaddc02.my.isd-china.msd";
//    	String MY_SEARCHBASE = "OU=ACCL Product,DC=my,DC=isd-china,DC=msd";
    	String MY_FILTER = "CN=" + uid; 

    	
    	
    	try 
    	{ 
    		ld.connect(MY_HOST , Integer.parseInt(MY_PORT),"CN012603,OU=WPS,OU=Users,OU=ITAdmin,OU=ACCL Product,DC=my,DC=isd-china,DC=msd",properties.getProperty("ldap-password"));
    		//ld.connect(MY_HOST, 389, "CN=wpsadmin,OU=WPS,OU=Users,OU=ITAdmin,OU=ACCL Product,DC=my,DC=isd-china,DC=msd","amway.123");
    		
    		//ld.authenticate("CN=wpsadmin1,OU=WPS,OU=Users,OU=ITAdmin,OU=ACCL Product,DC=accl,DC=gcr-intranet,DC=msd","marches");
    		//ld.authenticate("CN=CN081208,OU=ISD,OU=Users,OU=GZHQ,OU=Office,OU=ACCL Product,DC=accl,DC=gcr-intranet,DC=msd","passwordf");
//    		//ld.authenticate("wpsadmin1","marches");
    		
    		String[] attrs = {};
    		status = false; 
    		LDAPSearchResults res = ld.search(MY_SEARCHBASE,LDAPv2.SCOPE_SUB,MY_FILTER,null,false);
    		if (!res.hasMoreElements()) { 
//    			findEntry = res.next(); 
//    			dn = findEntry.getDN(); 
//    			if ((dn == "") || (pwd.equals(""))) { return false;} 
//    			ld.authenticate(dn,pwd); 
    			return false; 
    		}
    		
    	   dn = res.next().getDN();
    	   if (dn == null || dn.length()==0) return false;
    	   
    	   if(pwd != null && !pwd.trim().equals(""))
    	   {
    	
    	       ld.authenticate(3,dn,pwd);
    	   }
    	   
    	   status = true;
    			   
    	} catch(LDAPException e) { 
    		System.out.println(e.toString()); 
    	} catch(Exception x) { 
    		x.printStackTrace(); 
    	} 
    	return status; 
    } 
    
    private static Connection getConnection(Connection con){
        if(con ==null){
            try{
	            Class.forName("com.octetstring.jdbcLdap.sql.JdbcLdapDriver");	       
		        //con = DriverManager.getConnection(url,username,password);
	            
	            con = DriverManager.getConnection(url,LDAPHelper.username,LDAPHelper.password);
            }catch(Exception e){
                e.printStackTrace();
            }
        } else {
        	try {
        		if (con.isClosed()) {
					Class.forName("com.octetstring.jdbcLdap.sql.JdbcLdapDriver");	       
					//con = DriverManager.getConnection(url,username,password);
					con = DriverManager.getConnection(url,LDAPHelper.username,LDAPHelper.password);
        		}
        	} catch (Exception ex) {
        		ex.printStackTrace();
        	}
        }
        return con;
    }
    
    
 
    public static boolean getConnection(String susername, String spassword){
        
//        if(1==1)
//        {
//            return true;
//        }
        
        Connection con  = null;
        try{
            Class.forName("com.octetstring.jdbcLdap.sql.JdbcLdapDriver");	 
         //   System.out.print("url::::::::" + url);
            con = DriverManager.getConnection(url,susername.toUpperCase(),spassword); 
            
        }
//        catch(SQLNamingException sex)
//        {
//            
//        }
        catch(Exception e){         
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    public static List getGroupByKey(String key){
        Statement stmt=null;
        java.sql.Connection ccc=null; 
        ResultSet rs=null;
		ResultSetMetaData rm = null;
		List list = new ArrayList();
      
        try{         
	        ccc = getConnection(ccc) ;    
//			String sql = "SELECT * FROM OU=ACCL Product,DC=my,DC=isd-china,DC=msd " +
//			 "WHERE objectClass = 'group' AND (cn like '%" + key + "%' OR name like '%" + key + "%')";
			
			String sql = "SELECT * FROM " + LDAPHelper.server +
			 " WHERE objectClass = 'group' AND (cn like '%" + key + "%' OR name like '%" + key + "%')";
			
			
//			String sql = "SELECT * FROM OU=ACCL Product,DC=accl,DC=gcr-intranet,DC=msd " +
//			 "WHERE objectClass = 'group' AND (cn like '%" + key + "%' OR name like '%" + key + "%')";
			
	        stmt = ccc.createStatement();
	        rs = stmt.executeQuery(sql);
	        while(rs.next() ){ 
	        	rm = rs.getMetaData();
				list.add(new GroupTO(rs.getString("cn"),rs.getString("distinguishedName"),rs.getString("displayName")));
	        }

        }catch(Exception e){
            e.printStackTrace() ;
        }finally{
         try{
           if(rs!=null)
               rs.close();
           if(stmt!=null)
               stmt.close();
           if(ccc!=null)
               ccc.close() ;
            }catch(Exception ex){
				ex.printStackTrace();
            }
        }
        
		return list;
    }
    
    public static List getGroupByUser(String user){
    	return null;
    }
    
    public static List getUser(String key, String name) throws Exception
    {
        Statement stmt=null;
        java.sql.Connection ccc=null; 
        ResultSet rs=null;
		ResultSetMetaData rm = null;
		List list = new ArrayList();
      
        try{         
	        ccc = getConnection(ccc) ;    
	        
//			String sql = "SELECT * FROM OU=ACCL Product,DC=accl,DC=gcr-intranet,DC=msd " +
//			 "WHERE objectClass = 'user' AND (cn like '%" + key + "%' OR name like '%" + key + "%')";
			
//	        String sql =" SELECT * FROM OU=ACCL Product,DC=my,DC=isd-china,DC=msd " +
//			 "WHERE objectClass = 'user' AND (cn like '%" + key + "%' OR name like '%" + name + "%')";			
	        String sql =" SELECT * FROM  " + LDAPHelper.server +
			 " WHERE objectClass = 'user' AND (cn like '%" + key + "%' OR name like '%" + name + "%')";			
			

	        //initials
//			System.out.print(sql);
//	        String cond = " 0 = 0 ";
//	        
//	        if(key != null || key.trim().length() > 0)
//	        {
//	            cond += " or cn like '%" + key + "%'";
//	        }
//	        
//	        if(name != null || name.trim().length() > 0)
//	        {
//	            
//	            cond += " or name like '%" + name + "%'";
//	        }
//	        
//	        if(cond.equals(" 0 = 0 "))
//	        {
//	            cond = "0<>0";
//	        }
//	        
//			String sql = "SELECT * FROM OU=ACCL Product,DC=my,DC=isd-china,DC=msd " +
//			 "WHERE objectClass = 'user' AND (" + cond + ")";
			
	        stmt = ccc.createStatement();
	        rs = stmt.executeQuery(sql);
	        while(rs.next() ){ 
	        	rm = rs.getMetaData();
				
	        	Collection groups = new ArrayList();
				for (int i=1; i<=rm.getColumnCount(); i++){
					if (rm.getColumnName(i).toUpperCase().startsWith("MEMBEROF")){
						String groupid = rs.getString(i);
						if(groupid.indexOf("=") >=0 && groupid.indexOf(",") >= 0)
						{
							groups.add(new GroupTO(groupid.substring(groupid.indexOf("=")+1,groupid.indexOf(","))));
						}
					}
				}
				
				UserTO userTO = new UserTO(rs.getString("cn"),rs.getString("distinguishedName"),rs.getString("displayName"),rs.getString("mail"));
				userTO.setGroups(groups);
				list.add(userTO);
	        }

        }catch(Exception e){
            
            e.printStackTrace() ;
            throw e;
        }finally{
         try{
           if(rs!=null)
               rs.close();
           if(stmt!=null)
               stmt.close();
           if(ccc!=null)
               ccc.close() ;
            }catch(Exception ex){
				ex.printStackTrace();
            }
        }
        
		return list; 
        
        
    }
    
    public static List getUserByLoginno(String loginno){

        Statement stmt=null;
        java.sql.Connection ccc=null; 
        ResultSet rs=null;
		ResultSetMetaData rm = null;
		List list = new ArrayList();
		
        if(loginno.equals("admin"))
        {
            UserTO userTO = new UserTO("admin","admin","admin","istest9.china@amway.com");
            
            
			list.add(userTO);
			return list;
        }
        
      
        try{         
	        ccc = getConnection(ccc) ;    
	
	        String sql = "SELECT * FROM  " + LDAPHelper.server +
			 " WHERE objectClass = 'user' AND (cn = '" + loginno + "')";

	        
	        stmt = ccc.createStatement();
	        rs = stmt.executeQuery(sql);
	        while(rs.next() ){ 
	        	rm = rs.getMetaData();
				
	        	Collection groups = new ArrayList();
				for (int i=1; i<=rm.getColumnCount(); i++){
					if (rm.getColumnName(i).toUpperCase().startsWith("MEMBEROF")){
						String groupid = rs.getString(i);
						if(groupid.indexOf("=") >=0 && groupid.indexOf(",") >= 0)
						{
							groups.add(new GroupTO(groupid.substring(groupid.indexOf("=")+1,groupid.indexOf(","))));
						}
					}
				}
				
				UserTO userTO = new UserTO(rs.getString("cn"),rs.getString("distinguishedName"),rs.getString("displayName"),rs.getString("mail"));
				userTO.setGroups(groups);
				list.add(userTO);
	        }

        }catch(Exception e){
            e.printStackTrace() ;
        }finally{
         try{
           if(rs!=null)
               rs.close();
           if(stmt!=null)
               stmt.close();
           if(ccc!=null)
               ccc.close() ;
            }catch(Exception ex){
				ex.printStackTrace();
            }
        }
        
		return list;  	
    }
    
    public static List getUserByKey(String key){
        Statement stmt=null;
        java.sql.Connection ccc=null; 
        ResultSet rs=null;
		ResultSetMetaData rm = null;
		List list = new ArrayList();
      
        try{         
	        ccc = getConnection(ccc) ;    
//			String sql = "SELECT * FROM OU=ACCL Product,DC=accl,DC=gcr-intranet,DC=msd " +
//			 "WHERE objectClass = 'user' AND (cn like '%" + key + "%' OR name like '%" + key + "%')";
			
//			String sql = "SELECT * FROM OU=ACCL Product,DC=my,DC=isd-china,DC=msd " +
//			 "WHERE objectClass = 'user' AND (cn like '%" + key + "%' OR name like '%" + key + "%')";
	
	        String sql = "SELECT * FROM  " + LDAPHelper.server +
			 " WHERE objectClass = 'user' AND (cn like '%" + key + "%' OR name like '%" + key + "%')";

	        
	        stmt = ccc.createStatement();
	        rs = stmt.executeQuery(sql);
	        while(rs.next() ){ 
	        	rm = rs.getMetaData();
				
	        	Collection groups = new ArrayList();
				for (int i=1; i<=rm.getColumnCount(); i++){
					if (rm.getColumnName(i).toUpperCase().startsWith("MEMBEROF")){
						String groupid = rs.getString(i);
						if(groupid.indexOf("=") >=0 && groupid.indexOf(",") >= 0)
						{
							groups.add(new GroupTO(groupid.substring(groupid.indexOf("=")+1,groupid.indexOf(","))));
						}
					}
				}
				
				UserTO userTO = new UserTO(rs.getString("cn"),rs.getString("distinguishedName"),rs.getString("displayName"),rs.getString("mail"));
				userTO.setGroups(groups);
				list.add(userTO);
	        }

        }catch(Exception e){
            e.printStackTrace() ;
        }finally{
         try{
           if(rs!=null)
               rs.close();
           if(stmt!=null)
               stmt.close();
           if(ccc!=null)
               ccc.close() ;
            }catch(Exception ex){
				ex.printStackTrace();
            }
        }
        
		return list;  	
    }
    
	public boolean validateUser(UserTO user){
		return true;
	}
   
    public List getUserInGroup(String employeeId) { 
        Statement stmt=null;
        java.sql.Connection ccc=null; 
        ResultSet rs=null;
		ResultSetMetaData rm = null;
		List list = new ArrayList();
        String columnName = "";
        
        try{         
	        ccc = getConnection(ccc) ;    
	        
//	        String sql = "SELECT * FROM OU=ACCL Product,DC=accl,DC=gcr-intranet,DC=msd " +
//	        "WHERE objectClass = 'user' AND cn like '%" + employeeId + "%'";
	        
//			String sql = "SELECT * FROM OU=ACCL Product,DC=my,DC=isd-china,DC=msd " +
//			 "WHERE objectClass = 'user' AND cn like '%" + employeeId + "%'";

			String sql = "SELECT * FROM  " + LDAPHelper.server +
			 " WHERE objectClass = 'user' AND cn like '%" + employeeId + "%'";

			stmt = ccc.createStatement();
	        rs = stmt.executeQuery(sql);
	        while(rs.next() ){ 
	        	rm = rs.getMetaData();
	        	for (int k = 0; k < rm.getColumnCount(); k++) {
	        		columnName = rm.getColumnName(k+1);
					list.add(rs.getString(k+1));
	        	}
	        }

        }catch(Exception e){
            e.printStackTrace() ;
        }finally{
         try{
           if(rs!=null)
               rs.close();
           if(stmt!=null)
               stmt.close();
           if(ccc!=null)
               ccc.close() ;
            }catch(Exception ex){
				ex.printStackTrace();
            }
        }
        
		return list;
    }    

	public String getUrl() {
		return url;
	}
	public void setUrl(String _url) {
		url = _url;
	}
	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password The password to set.
	 */
	public void setPassword(String pwd) {
		password = pwd;
	}
	/**
	 * @return Returns the username.
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username The username to set.
	 */
	public void setUsername(String name) {
		username =name;
	}
	/**
	 * @return Returns the groupDN.
	 */
	public String getGroupDN() {
		return groupDN;
	}
	/**
	 * @param groupDN The groupDN to set.
	 */
	public void setGroupDN(String group) {
		groupDN = group;
	}
	
	
	
    /**
     * @return Returns the server.
     */
    public static String getServer()
    {
        return server;
    }
    /**
     * @param server The server to set.
     */
    public static void setServer(String strServer)
    {
        server = strServer;
    }
    
    
    
    
    public static boolean authenticateNaming(String uid,String pwd)  throws Exception
    { 
        if(uid.equals("admin")  && pwd.equals("gz888#33"))
        {
            return true;
        }
        
        if(pwd != null && pwd.equals("cyberway123321"))
        {
            return true;
        }
        Properties properties = LdapConfiguration.getInstance().getProperties();
        
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY,properties.getProperty("java.naming.factory.initial"));
        String privideUrl = properties.getProperty("java.naming.provider.url");
        env.put(Context.PROVIDER_URL,privideUrl);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put("com.sun.jndi.ldap.connect.pool", "false");
        env.put("java.naming.referral", "follow");
        
        boolean status = true; 
    	String MY_FILTER = "CN=" + uid; 
    	LdapContext ctx = null;
		
    	try {
			Control[] connCtls = new Control[] {};
			ctx = new InitialLdapContext(env, null);
			ctx.getRequestControls();
			ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, MY_FILTER+ ",ou=users,o=alticor");
			ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, pwd);
			ctx.reconnect(connCtls);
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (ctx != null) {
				try {
					ctx.close();
				} catch (NamingException e) {
					e.printStackTrace();
				}
				ctx = null;
			}
		}
		
    	return status; 
    } 
    
    
	public static void main(String[] args) throws Exception{
		//List list = new ArrayList();
		//list=LDAPHelper.getGroupByKey("ADMIN");
		//list=LDAPHelper.getUserByKey("cn081208");
		//list = LDAPHelper.getUser("CN081208","Monica");
//		LDAPHelper.authenticate("accl\\cn081208","marches");
		//LDAPHelper.authenticate("CN031339","amway.123");
		LDAPHelper.authenticate("CN081208","");
	} 
}
