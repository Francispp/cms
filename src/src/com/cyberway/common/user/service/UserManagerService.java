package com.cyberway.common.user.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.providers.encoding.Md5PasswordEncoder;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.cyberway.cms.Constants;
import com.cyberway.common.domain.CoreRole;
import com.cyberway.common.domain.CoreUser;
import com.cyberway.common.role.service.RoleManagerService;
import com.cyberway.common.service.CommonCache;
import com.cyberway.core.acegi.AuthenticationHelper;
import com.cyberway.core.acegi.cache.AcegiCacheManager;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.AesEncrypt;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.utils.UtilDateTime;
import com.cyberway.core.utils.property.DefaultProperty;

public class UserManagerService extends HibernateEntityDao<CoreUser> {
	private AcegiCacheManager acegiCacheManager;
	private org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;
	public void setAcegiCacheManager(AcegiCacheManager acegiCacheManager) {
		this.acegiCacheManager = acegiCacheManager;
	}
	private RoleManagerService roleManagerService;
	public void setRoleManagerService(RoleManagerService roleManagerService) {
		this.roleManagerService = roleManagerService;
	}
	private CommonCache commonCache;//公共缓存管理器
	
	private static String DEFAULT_DES_STRING=AesEncrypt.getDesString("","K1cm34cI3yNsQla0jceDMw==");
	public CoreUser getById (Long id)
	{
		return get(id);
	}
	/**
	 * 判别是从本地还是从AEP中获取用户信息
	 * @param id
	 * @return
	 */
	public CoreUser getADUserById(Long id)
	{
		String sql = "select * from v_aep_employee where EMP_ID = ?";
		CoreUser user;
		if(Constants.LOGON_USER.equals(Loginer.USER_LOCAL))
		{
		return this.getById(id);
		}
		else if(Constants.LOGON_USER.equals(Loginer.USER_AD))
		{
			SqlRowSet rowSet=jdbcTemplate.queryForRowSet(sql,new Object[] {String.valueOf(id)});
			user = new CoreUser();
			  if(rowSet.next())
			  {
				  user.setUserid(Long.valueOf(rowSet.getString("EMP_ID")));
				  user.setEmail(rowSet.getString("EMAIL_ADDR"));
				  user.setUsername(rowSet.getString("ENGLISH_NAME"));
				  user.setEname(rowSet.getString("ENGLISH_NAME"));
				  user.setLoginid(rowSet.getString("EMP_NUMBER"));
			   }
			  return user;
		
		}
		else{
			user = this.getById(id);
			if(user == null)
			{
				user = new CoreUser();
				SqlRowSet rowSet=jdbcTemplate.queryForRowSet(sql,new Object[] {String.valueOf(id)});
				  if(rowSet.next())
				  {
					  user.setUserid(Long.valueOf(rowSet.getString("EMP_ID")));
					  user.setEmail(rowSet.getString("EMAIL_ADDR"));
					  user.setUsername(rowSet.getString("ENGLISH_NAME"));
					  user.setEname(rowSet.getString("ENGLISH_NAME"));
					  user.setLoginid(rowSet.getString("EMP_NUMBER"));
				   }
			}
			return user;	
		}
	}
	/**
	 * 从缓存中取用户信息
	 * @param userid
	 * @return
	 */
	public CoreUser getUserFromCache(String userid){
		return commonCache.getUserFromCache(userid);
	}
	/**
	 * 从缓存中获得所有用户信息
	 * @return
	 */
	public List<CoreUser> getAllUsersFromCache(){
		return commonCache.getAllUsers();
	}
	/**
	 * 初始化用户缓存
	 */
	public synchronized void init(){
/*		CoreUser cu=new CoreUser();
		cu.setUserid(1l);
		cu.setCoreDept(null);
		cu.setLoginid("test");
		this.save(cu);*/
		List<CoreUser> users=getAll();
		if(!users.isEmpty()){			
			for(CoreUser user:users){
				commonCache.putUserInCache(user);
			}
		}
	}	
	/**
	 * 加密算法
	 * 
	 * @param str
	 * @return
	 */
	public static String encodePassword(String str) {
		Md5PasswordEncoder md5pas = new Md5PasswordEncoder();
		String encodeStr = md5pas.encodePassword(str, null);
		return encodeStr;
	}
	
	public static String encodePassword(){
		Md5PasswordEncoder md5pas = new Md5PasswordEncoder();
		Date curr=new Date();
		String str=DEFAULT_DES_STRING+UtilDateTime.getNextDateAddHour(curr, curr.getDay()).getHours();
		String encodeStr = md5pas.encodePassword(str, null);
		return encodeStr;		
	}
	@Override
	public CoreUser saveOrUpdate(CoreUser user){
		this.evit(user);
		String passwd = user.getPassword();

		// 如果密码为空
		if (StringUtils.isBlank(passwd)) {
			String encryPass = encodePassword(DefaultProperty.getProperty("user.defuat.password","123456"));
			user.setPassword(encryPass);
		}
		
		//更新缓存
		if(user.getUserid() == null ){
			//添加默认角色
			String default_role=DefaultProperty.getProperty("user.defuat.role","1");
			CoreRole role = (CoreRole)this.get(CoreRole.class, new Long(default_role));
			Set roles = new HashSet();
			roles.add(role);
			user.setCoreUserRoles(roles);
			//缓存中没有的用户
			GrantedAuthorityImpl authority = new GrantedAuthorityImpl("ROLE_"+default_role);
			Collection<GrantedAuthorityImpl> auths = new HashSet();
			auths.add(authority);
			if(user.getState()==null)
				user.setState(new Long(1));
			boolean state=user.getState()==1?true:false;
			acegiCacheManager.addUser(user.getLoginid(), user.getPassword(), state, true, true, true, AuthenticationHelper.convertToGrantedAuthority(auths));
			user = super.saveOrUpdate(user);
			roleManagerService.insertUsersToRole(role.getOid().toString(), user.getUserid().toString());
			//更新用户缓存
			commonCache.putUserInCache(user);
		}else{
			//CoreUser old = this.get(user.getUserid());
			//String oldLogid = old.getLogid();
			//this.evit(old);
			//登录用户名不能更改
			String loginid=user.getLoginid();
			UserDetails ud = acegiCacheManager.getUser(loginid);
			acegiCacheManager.renovateUser(loginid, loginid, user.getPassword(), user.getState()==1?true:false,ud.isAccountNonExpired(),
			        ud.isCredentialsNonExpired(),ud.isAccountNonLocked(),ud.getAuthorities());
			user = super.saveOrUpdate(user);
			//更新用户缓存
			commonCache.putUserInCache(user);
		}
		return user;

	}

	/**
	 * 从AD用户中获取用户信息
	 * @param loginno
	 * @return
	 */
	public CoreUser getADUser(String loginno)
	{
		CoreUser user = new CoreUser(); 
		String sql = "select * from v_aep_employee where lower(EMP_NUMBER) = ?";
		 SqlRowSet rowSet=jdbcTemplate.queryForRowSet(sql,new Object[] {loginno.toLowerCase()});
		  if(rowSet.next())
		  {
			  //ad用户，取EMP_ID为用户userid,EMP_ID为字符串，进行转换		
			  String emp_id=rowSet.getString("EMP_ID");
			  Long tuserid=null;
				if(StringUtil.isNumber(emp_id))
					tuserid=new Long(emp_id);
				else
					tuserid=new Long("1008"+Math.abs(emp_id.hashCode()));
			  user.setUserid(tuserid);
			  user.setEmail(rowSet.getString("EMAIL_ADDR"));
			  user.setUsername(rowSet.getString("ENGLISH_NAME"));
			  user.setEname(rowSet.getString("ENGLISH_NAME"));
			  user.setLoginid(rowSet.getString("EMP_NUMBER"));
			 		  }

		return user;
	}
	/**
	 * 从AD用户中根据EMP_ID获取用户信息
	 * @param loginno
	 * @return
	 */
	public CoreUser getADUserByEmpID(String empid)
	{
		CoreUser user = new CoreUser(); 
		String sql = "select * from v_aep_employee where EMP_ID = ?";
		 SqlRowSet rowSet=jdbcTemplate.queryForRowSet(sql,new Object[] {empid});
		  if(rowSet.next())
		  {
			  //user.setUserid(Long.valueOf(rowSet.getString("EMP_ID")));
			  //ad用户，取EMP_ID为用户userid,EMP_ID为字符串，进行转换		
			  String emp_id=rowSet.getString("EMP_ID");
			  Long tuserid=null;
				if(StringUtil.isNumber(emp_id))
					tuserid=new Long(emp_id);
				else
					tuserid=new Long("1008"+Math.abs(emp_id.hashCode()));
			  user.setUserid(tuserid);
			  user.setHotmail(rowSet.getString("EMAIL_ADDR"));
			  user.setUsername(rowSet.getString("ENGLISH_NAME"));
			  user.setEname(rowSet.getString("ENGLISH_NAME"));
			  user.setLoginid(rowSet.getString("EMP_NUMBER"));
			  user.setDeptname(rowSet.getString("Z_DEPT_NAME_ZHS"));
		   }

		return user;
	}
	/**
	 * Ajax设置用户状态
	 * @param userid
	 * @param status
	 * @return
	 */
	public boolean setUserStatus(Integer userid,Integer status){
		boolean succ=false;
		try{
		CoreUser user=get(userid.longValue());
		user.setState(status.longValue());
		
		String loginid=user.getLoginid();
		UserDetails ud = acegiCacheManager.getUser(loginid);
		acegiCacheManager.renovateUser(loginid, loginid, user.getPassword(), user.getState()==1?true:false,ud.isAccountNonExpired(),
		        ud.isCredentialsNonExpired(),ud.isAccountNonLocked(),ud.getAuthorities());		
		this.save(user);
		succ=true;
		}catch(Exception e){
			logger.error(e);
		}
		return succ;
	}
	
	/**
	 * 修改用户密码(把用户密码重设为指定密码)
	 * 
	 * @param pass
	 * @return
	 */
	public void modifyUsersPassword(String userids, String pass) {
		String[] ids=userids.split(";");
		String encryPass;
		if(StringUtil.isEmpty(pass)){
		  encryPass = encodePassword(DefaultProperty.getProperty("user.defuat.password","123456"));
		}else
		  encryPass = encodePassword(pass);
		//user.setPassword(encryPass);	
		CoreUser user;
		for(int i=0;i<ids.length;i++){
   		 if(StringUtil.isEmpty(ids[i]))
   			continue;
   		 user=this.get(new Long(ids[i]));
 		 String loginid=user.getLoginid();
		 UserDetails ud = acegiCacheManager.getUser(loginid);
		 acegiCacheManager.renovateUser(loginid, loginid, user.getPassword(), user.getState()==1?true:false,ud.isAccountNonExpired(),
		        ud.isCredentialsNonExpired(),ud.isAccountNonLocked(),ud.getAuthorities());	   		 
   		 user.setPassword(encryPass);
   		 this.saveOrUpdate(user);
 		//更新用户缓存
 		commonCache.putUserInCache(user);
		}
	}
	/**
	 * 
	 * 从本地和和外部AEP中检索用户
	 * @param key
	 * @param rtn
	 * @return
	 */
	public String toUserStringFromAEPAndLocal(String key, String rtn)
	{
		rtn = this.toUserString(key, rtn);
		rtn = this.toUserStringFromAEP(key, rtn);
		return rtn;
	}
	/**
	 * 
	 * 从AEP中检索用户
	 * @param key
	 * @param rtn
	 * @return
	 */
	public String toUserStringFromAEP(String key, String rtn)
	{
		if(key == null)
	      {
			key = "";
	      }
		String sql = "select * from CMS_WEBUSER where isInternal=true and (name like ? or loginname like ? or email like ?)";
		 SqlRowSet rowSet=jdbcTemplate.queryForRowSet(sql,new Object[] {"%"+key.toLowerCase()+"%","%"+key.toLowerCase()+"%","%"+key.toLowerCase()+"%"});
		  while(rowSet.next())
		  {
		          rtn +="<DATA>";
		          
		          	rtn +="<ID>";
		          		rtn +=rowSet.getString("oid");
		          	rtn +="</ID>";
		          	
		          	rtn +="<USERNUMBER>";
	          		rtn +=rowSet.getString("empcode");
	          	    rtn +="</USERNUMBER>";	          	
		          	
		          	rtn +="<NAME>";
	          		   rtn +=rowSet.getString("name");
	          	    rtn +="</NAME>";
	          	    
		        	rtn +="<DEPT>";
	       		   		rtn +=rowSet.getString("deptname");;
	       	        rtn +="</DEPT>";
	          	    
	          	  
	          	  rtn += "</DATA>";   

		  }
	      return rtn;
	}
	/**
	 * 从本地检索用户
	 * @param key
	 * @param rtn
	 * @return
	 */
	public String toUserString(String key,String rtn)
	{
		if(key == null)
	      {
			key = "";
	      }
		  String hsql = "from CoreUser where lower(loginid) like ? or lower(ename) like ? or lower(username) like ?";
	      Collection coll = this.find(hsql, new Object[]{"%"+key.toLowerCase()+"%","%"+key.toLowerCase()+"%","%"+key.toLowerCase()+"%"});
		  Iterator iter = coll.iterator();
		  
		  while(iter.hasNext())
		  {
			  CoreUser user = (CoreUser)iter.next();
		      
		      if(user != null)
		      {
		          rtn +="<DATA>";
		          
		          	rtn +="<ID>";
		          		rtn +=user.getUserid();
		          	rtn +="</ID>";
		          	
		          	rtn +="<USERNUMBER>";
	          		rtn +=user.getEmpcode();
	          	    rtn +="</USERNUMBER>";	          	
		          	
		          	rtn +="<NAME>";
		          	   String ename = user.getEname();
		          	 ename = ename == null ? "": ename;
	          		   rtn +=ename.trim();
	          	    rtn +="</NAME>";
	          	    
		        	rtn +="<DEPT>";
		        		String dept = user.getDeptname();
		        		dept = dept == null ? "" : dept;
		        		
	       		   		rtn +=dept.trim();
	       	        rtn +="</DEPT>";
	          	    
	          	  
	          	  rtn += "</DATA>";   
		      }
		  }
	      return rtn;
	}
    /**
     * 删除多条记录
     * @param ids
     */
    public void removeByIds(List<Long> ids){
    	if(ids!=null){
    		for (Long id : ids)
    			removeById(id);
    	}
    }	
	/**
	 * 删除用户 ,先删除所属角色
	 * @param id
	 */
	public void removeById(Long id){
		//删除用户所属角色关系
		roleManagerService.removeCoreUserRolesByUserId(id);
		//从缓存中删除
		commonCache.removeUserFromCache(id.toString());
		CoreUser user = this.get(id);
		acegiCacheManager.removeUser(user.getLoginid());
		super.removeById(id);
	
	}
	/**
	 * ajax调用
	 * 显示员工分类下拉列表
	 * @return
	 */
	public String[] empclassList(){
		return (String[]) this.createQuery("select distinct empclass from CoreUser").list().toArray(new String[]{});
	}

	public CommonCache getCommonCache() {
		return commonCache;
	}

	public void setCommonCache(CommonCache commonCache) {
		this.commonCache = commonCache;
	}
	public org.springframework.jdbc.core.JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(
			org.springframework.jdbc.core.JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 清除所有用户缓存
	 */
	public void removeAllCache() {
		commonCache.removeAllCoreUser();
	}

	/**
	 * 获取所有用户缓存中的key
	 * 
	 * @return
	 */
	public List<String> getAllCacheKeys() {
		return commonCache.getCoreUserCacheKeys();
	}

	/**
	 * 获得指定的用户缓存
	 * 
	 * @param key
	 * @return
	 */
	public net.sf.ehcache.Element getElementFromCache(String key) {
		if (StringUtils.isNotEmpty(key)) {
			net.sf.ehcache.Element element = null;
			element = commonCache.getElementFromCoreUser(key);
			return element;
		} else
			return null;
	}

}
