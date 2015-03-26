package com.cyberway.common.role.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ognl.Ognl;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.Constants;
import com.cyberway.cms.login.service.OtherJdbcManager;
import com.cyberway.common.domain.CoreADUserRole;
import com.cyberway.common.domain.CoreCommonType;
import com.cyberway.common.domain.CoreIdentity;
import com.cyberway.common.domain.CoreOrg;
import com.cyberway.common.domain.CoreRole;
import com.cyberway.common.domain.CoreUser;
import com.cyberway.common.domain.CoreUserRole;
import com.cyberway.common.permission.service.AuthorityManager;
import com.cyberway.common.service.CommonCache;
import com.cyberway.common.user.service.UserManagerService;
import com.cyberway.common.xtree.DHtmlXTree;
import com.cyberway.common.xtree.DHtmlXTree.Node;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.BeanUtils;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;

public class RoleManagerService extends HibernateEntityDao<CoreRole> {
	
	private UserManagerService userManagerService;
	private org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;

	/**
	 * 对权限缓存进行统一管理
	 */
	private AuthorityManager authorityManager;
	
	private CommonCache commonCache;
	
	public void init(){
		List<CoreRole> roleList = getAll();
		for(CoreRole role : roleList){
			commonCache.putRoleCacheInCache(role);
		}
	}

	/**
	 * @param authorityManager the authorityManager to set
	 */
	public void setAuthorityManager(AuthorityManager authorityManager) {
		this.authorityManager = authorityManager;
	}	
    /**
     * 
     * @param roleid
     * @param userids
     * @return
     */
    @SuppressWarnings("unchecked")
	public boolean insertUsersToRole(String roleid,String userids){
    	if(StringUtil.isEmpty(roleid)||StringUtil.isEmpty(userids)){
    		return false;
    	}
    	CoreUserRole ur;
    	CoreADUserRole ad;
    	userManagerService = (UserManagerService)ServiceLocator.getBean("userManagerService");
    	try{
    		String[] ids=userids.split(";");
    		List<CoreUserRole> list=null;
    		for(int i=0;i<ids.length;i++){
    		 if(StringUtil.isEmpty(ids[i]))
    			continue;
    		  list=this.find("from CoreUserRole where coreUser.userid = ? and coreRole.oid=?", new Object[]{Long.valueOf(ids[i]),Long.valueOf(roleid)});
    		  if(list!=null&&list.size()>0)
    			 continue;
    		  list=this.find("from CoreADUserRole where userid = ? and coreRole.oid=?", new Object[]{Long.valueOf(ids[i]),Long.valueOf(roleid)});
    		  if(list!=null&&list.size()>0)
    			 continue;
    		 CoreUser coreUser = userManagerService.get(new Long(ids[i]));
    		 if(coreUser != null)
    		 {
    		 ur=new CoreUserRole();
    		 Ognl.setValue("coreRole.oid", ur, new Long(roleid));
    		 Ognl.setValue("coreUser.userid", ur, new Long(ids[i]));
    		 this.save(ur);
    		 //同时修改权限操作
    		 modifyAuthorization(new Long(ids[i]),new Long(roleid),true);
    		 }
    		 else
    		 {
    			 ad=new CoreADUserRole();
        		 Ognl.setValue("coreRole.oid", ad, new Long(roleid));
        		 ad.setUserid(new Long(ids[i]));
        		 this.save(ad);
    		 }
    		
    		}
    		
    	}catch(Exception e){
    		logger.error(e.getMessage());
    	}
    	return true;
    }
    
    /**
     * 删除指定角色下某些用户
     * @param rolid
     * @param userids
     * @return
     */
    public boolean removeUsersFromRole(String roleid,String userids){
    	if(StringUtil.isEmpty(roleid)||StringUtil.isEmpty(userids)){
    		return false;
    	}
    	List<Long> ids=StringUtil.splitToList(userids,",");
    	List<Long> roleids=StringUtil.splitToList(roleid,",");
    	
    	for(Long _roleid:roleids){
    	for(Long uid:ids){
    	List list=this.find("from CoreUserRole where coreRole.oid=? and coreUser.userid = ?", new Object[]{new Long(_roleid),uid});
    	//删除权限操作
    	modifyAuthorization(uid,new Long(roleid),false);
    	for(int i=0;i<list.size();i++)
    		remove(list.get(i));    		
    	
    	}
    	}
    	return true;
    }
    /**
     *  移除本地和AD用户
     * @param roleid
     * @param userids
     * @return
     */
    public boolean removeUsersAndADUserFromRole(String roleid,String userids){
    	if(StringUtil.isEmpty(roleid)||StringUtil.isEmpty(userids)){
    		return false;
    	}
    	List<Long> ids=StringUtil.splitToList(userids,",");
    	for(Long uid:ids){
    	List list=this.find("from CoreUserRole where coreRole.oid=? and coreUser.userid = ?", new Object[]{new Long(roleid),uid});
    	//删除权限操作
    	modifyAuthorization(uid,new Long(roleid),false);
    	for(int i=0;i<list.size();i++)
    		remove(list.get(i));    
    	//移除AD用户
    	List adList=this.find("from CoreADUserRole where coreRole.oid=? and userid = ?", new Object[]{new Long(roleid),uid});
    	for(int i=0;i<adList.size();i++)
    		remove(adList.get(i)); 
    	}
    	
    	return true;
    }
    /**
     * 删除用户所用角色关系
     * @param userid
     * @return
     */
    public boolean removeCoreUserRolesByUserId(Long userid){
    	//List<Long> ids = new ArrayList<Long>();
    	//ids = this.find("from CoreRole where siteId is null or siteId<=0 or siteId=?", new Object[]{getLoginerSiteId()});
    	List<CoreUserRole> list=this.find("from CoreUserRole where coreUser.userid = ?  and (coreRole.siteId is null or coreRole.siteId<=0 or coreRole.siteId=?)", new Object[]{userid,getLoginerSiteId()});
    	//删除权限操作    	
    	for(int i=0;i<list.size();i++){
    		modifyAuthorization(userid,list.get(i).getCoreRole().getOid(),false);
    		remove(list.get(i));    		    	
    	}    	
    	return true;
    }
	/**
	 * 供页面ajax调用的保存方法
	 * 
	 * @param data
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	public boolean saveByAjax(Map<String, Object> data){

		boolean result = true;

		try{
			
			//得到当前对象
			CoreRole entity = buildEntity(data);			

			// 将页面提交的数据转换为实体对象中的属性值
			BeanUtils.forceSetProperty(entity, data);
			//增加门户关系
            if(data.containsKey("corePortal.oid")){
            	String poid=(String)data.get("corePortal.oid");
            	if(poid!=null&&poid.length()>0){
            		CoreOrg cp=new CoreOrg();
            		cp.setOid(new Long(poid));
            		entity.setCoreOrg(cp);
            	}
            }
			if (result && entity !=null) {
				this.save(entity);
				this.flush();
				
				// 取主键值
				String pkidName = this.getIdName(this.entityClass);
				Object pkid = BeanUtils.getProperty(entity, pkidName);
				data.put(pkidName, pkid);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			result = false;		
			
		}
		
		return result;
	}
	
	/**
	 * 获得基本类型角色所包含的用户
	 * @param roleid
	 * @return
	 */
	public List<CoreUser> getUsersByRoleId(Long roleid){
		List<CoreUser> list=new ArrayList();
		List<CoreUserRole> curs=find("from CoreUserRole where coreRole.oid=?", new Object[]{roleid});
		for(CoreUserRole cur:curs){
			CoreUser cu=cur.getCoreUser();
			cu.getUsername();
			list.add(cu);
			
		}
		return list;
	}
	/**
	 * 获取本地用户与AD用户
	 * @param roleid
	 * @return
	 */
	public List<CoreUser> getADUsersByRoleId(Long roleid){
		userManagerService = (UserManagerService)ServiceLocator.getBean("userManagerService");
		List<CoreUser> list=new ArrayList();
		
		 if(Constants.LOGON_USER.equals(Loginer.USER_AD) || (!Constants.LOGON_USER.equals(Loginer.USER_AD) && !Constants.LOGON_USER.equals(Loginer.USER_LOCAL)))
		{
		List<CoreADUserRole> curs=find("from CoreADUserRole where coreRole.oid=?", new Object[]{roleid});
		for(CoreADUserRole cur:curs){
			CoreUser cu=userManagerService.getADUserByEmpID(String.valueOf(cur.getUserid()));
			list.add(cu);
		}
		}
	   if(Constants.LOGON_USER.equals(Loginer.USER_LOCAL) || (!Constants.LOGON_USER.equals(Loginer.USER_AD) && !Constants.LOGON_USER.equals(Loginer.USER_LOCAL)))
		{
		List<CoreUserRole> coreurs=find("from CoreUserRole where coreRole.oid=?", new Object[]{roleid});
		for(CoreUserRole cur:coreurs){
			CoreUser cu=userManagerService.get(cur.getCoreUser().getUserid());
			list.add(cu);
		}
		}
		return list;
	}
	
	/**
	 * 获得用户所属的基本类型角色
	 * @param userid
	 * @return
	 */
	public List<CoreRole> getRolesByUserId(Long userid){
		//Loginer loginer = (Loginer)ServletActionContext.getContext().getSession().get(Loginer.LOGININFO_SESSION);
		//Long siteId = loginer.getSiteId();
		List<CoreRole> list= new ArrayList<CoreRole>();
		List<CoreUserRole> curs =curs=find("from CoreUserRole where coreUser.userid=?", new Object[]{userid});
		for(CoreUserRole cur:curs){
			CoreRole cr=cur.getCoreRole();			
			if(cr.getState()!=null && cr.getState()==0)
			  list.add(cr);
		}
		Long allRoleid=com.cyberway.cms.Constants.ALL_USER_ROLE_ID;
		if(allRoleid!=null ){//增加所有用户角色
			CoreRole acr=this.get(allRoleid);
			if(acr!=null && !list.contains(acr))
				list.add(acr);
		}
		return list;
	}
	
	public List<CoreRole> getRolesByUserIdAndSiteId(Long userid){
		//Loginer loginer = (Loginer)ServletActionContext.getContext().getSession().get(Loginer.LOGININFO_SESSION);
		//Long siteId = loginer.getSiteId();
		List<CoreRole> list= new ArrayList<CoreRole>();
		List<CoreUserRole> curs =curs=find("from CoreUserRole where coreUser.userid=? and (coreRole.siteId is null or coreRole.siteId<=0 or coreRole.siteId=?)", new Object[]{userid,getLoginerSiteId()});
		for(CoreUserRole cur:curs){
			CoreRole cr=cur.getCoreRole();			
			if(cr.getState()!=null && cr.getState()==0)
			  list.add(cr);
		}
		Long allRoleid=com.cyberway.cms.Constants.ALL_USER_ROLE_ID;
		if(allRoleid!=null ){//增加所有用户角色
			CoreRole acr=this.get(allRoleid);
			if(acr!=null && !list.contains(acr))
				list.add(acr);
		}
		return list;
	}
	
	/**
	 * 获取系统角色+当前站点所属角色
	 * @param siteId
	 * @return
	 */
	public List<CoreRole> getSysRoles(Long siteId){
		List<CoreRole> list= new ArrayList<CoreRole>();
		list = find("from CoreRole where siteId is null or siteId =0 or siteId=?", new Object[]{siteId});
		return list;
	}
	
	/**
	 * 获取AD用户角色
	 * @param userid
	 * @return
	 */
	public List<CoreRole> getADRolesByUserId(Long userid){
		List<CoreRole> list=new ArrayList();
		List<CoreADUserRole> curs=find("from CoreADUserRole where userid=?", new Object[]{userid});
		for(CoreADUserRole cur:curs){
			CoreRole cr=cur.getCoreRole();			
			if(cr.getState()!=null && cr.getState()==0)
			  list.add(cr);
		}
		return list;
	}

	/**
	 * 根据角色码获得角色集
	 * @param rolecode
	 * @return
	 */
	public List<CoreRole> getRolesByRoleCode(String roleCode){
		List<CoreRole> list=new ArrayList();
		List<CoreUserRole> curs=find("from CoreUserRole where coreRole.rolecode=?", new Object[]{roleCode});
		for(CoreUserRole cur:curs){
			CoreRole cr=cur.getCoreRole();			
			if(cr.getState()!=null && cr.getState()==0)
			  list.add(cr);
		}
		Long allRoleid=com.cyberway.cms.Constants.ALL_USER_ROLE_ID;
		if(allRoleid!=null ){//增加所有用户角色
			CoreRole acr=this.get(allRoleid);
			if(acr!=null && !list.contains(acr))
				list.add(acr);
		}
		return list;
	}
	
   /**
    * 根据角色名称获得角色集
 * @param roleName
 * @return
 */
public List<CoreRole> getRolesByRoleName(String roleName){
		List<CoreRole> list=find("from CoreRole where rolename=?", new Object[]{roleName});
		return list;	   
   }
	/**
	 * 获得指定类型的角色
	 * @param roleType
	 * @return
	 */
	public List<CoreRole> getCoreRolesByRoleType(Long roleType){
		List<CoreRole> roles=find("from CoreRole where roleType=?", new Object[]{roleType});
		return roles;
	}
	
	/**
	 * 根据站点ID获得指定类型的角色
	 * @author Dicky
	 * @param roleType
	 * @param siteId
	 * @return
	 * @time 2012-9-10 16:23:14
	 */
	@SuppressWarnings("unchecked")
	public List<CoreRole> getRolesByType(Long roleType, Long siteId){
		List<CoreRole> roles=find("from CoreRole where roleType=? and siteId=?", new Object[]{roleType,siteId});
		return roles;
	}
	
	/**
	 * 获得当前用户，所获ldap所有角色
	 * @return
	 */
	public List<CoreRole> getLDAPRolesByUser(Loginer loginer){
		List<CoreRole> roles=new ArrayList();
		roles.addAll(this.getADRolesByUserId(loginer.getUserid()));
		//调用SQL,返回为0，表示false;1表示true
		List<CoreRole> ldapRoles=this.getCoreRolesByRoleType(1L);
		OtherJdbcManager otherJdbc=(OtherJdbcManager)ServiceLocator.getBean("otherJdbcManager");
		
		if(ldapRoles!=null){
			for(CoreRole role:ldapRoles){
				//执行ＳＱＬ
				int execute=otherJdbc.executeRoleExpression(role.getRelationContent(), loginer);
				if(execute>0){
					roles.add(role);
				}
			}
		}
		
		Long allRoleid=com.cyberway.cms.Constants.ALL_USER_ROLE_ID;
		if(allRoleid!=null ){//增加所有用户角色
			CoreRole acr=this.get(allRoleid);
			if(acr!=null && !roles.contains(acr))
				roles.add(acr);
		}
		return roles;
	}
	
	/**
	 * 修改权限操作
	 * 
	 * @param userId
	 * @param roleId
	 * @param isAuth
	 *            为true表示授权 否则撤消授权
	 * @return
	 */
	public boolean modifyAuthorization(Long userid,Long roleid,boolean isAuth) {	
		// 是授权还是撤销授权
		
		// 处理权限缓存
		CoreUser user=(CoreUser)get(CoreUser.class,userid);
		
	    CoreRole role=get(roleid);
	    if(user != null)
	    {
	    	 String userLogid = user.getLoginid();

		String portalCode = role.getCoreOrg().getOrgCode();
		String password = user.getPassword();	
		boolean state=user.getState()==1?true:false;
		String roleCode = role.getOid().toString();

		this.authorityManager.renovateUser(userLogid, portalCode, password,
				state, roleCode, isAuth);
	    }

		return true;
	}	
	
	/**
	 * 搜索满足条件的角色xml
	 * @param key
	 * @return
	 */
	public String getXMLFromRoleString(String key,Long siteId)
	{
		if(key == null)
	      {
			key = "";
	      }
		  String hsql = "from CoreRole where ( lower(rolename) like '%"+key+"%' or lower(rolecode) like '%"+key+"%') and state=0 and (siteId is null or siteId<=0 or siteId="+siteId+")";
	      Collection coll = this.find(hsql);
	      String rtn = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		  rtn +="<RECORDSET>";
		  Iterator iter = coll.iterator();
		  
		  while(iter.hasNext())
		  {
			  CoreRole role = (CoreRole)iter.next();
		      
		      if(role != null)
		      {
		          rtn +="<DATA>";
		          
		          	rtn +="<ID>";
		          		rtn +=role.getOid();
		          	rtn +="</ID>";
		          	
		          	rtn +="<USERNUMBER>";
	          		rtn +=role.getRolecode();
	          	    rtn +="</USERNUMBER>";	          	
		          	
		          	rtn +="<NAME>";
		          	   String userename = role.getRolename();
		          	   userename = userename == null ? "": userename;
	          		   rtn +=userename.trim();
	          	    rtn +="</NAME>";          	    
		      	          	    
	          	  
	          	  rtn += "</DATA>";   
		      }
		  }
		  
		  rtn +="</RECORDSET>";
	      return rtn;
	}
	public org.springframework.jdbc.core.JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(
			org.springframework.jdbc.core.JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 根据id和角色类型查找角色
	 * 
	 * @param id
	 *            角色id
	 * @param roleType
	 *            角色类型:"0"代表"普通角色";"1"代表"LDAP角色";"2"代表"外部角色"
	 * @return
	 */
	public List<CoreRole> findRole(Long id, Long roleType) {
		return find("from CoreRole where oid = ? and roleType = ?", new Object[] { id, roleType });
	}
	public CommonCache getCommonCache() {
    	return commonCache;
    }
	public void setCommonCache(CommonCache commonCache) {
    	this.commonCache = commonCache;
    }
	
	/**
	 * 清除所有角色缓存
	 */
	public void removeAllCache() {
		commonCache.removeAllRole();
	}

	/**
	 * 获取所有角色缓存中的key
	 * 
	 * @return
	 */
	public List<String> getAllCacheKeys() {
		return commonCache.getRoleCacheKeys();
	}

	/**
	 * 获得指定的角色缓存
	 * 
	 * @param key
	 * @return
	 */
	public net.sf.ehcache.Element getElementFromCache(String key) {
		if (StringUtils.isNotEmpty(key)) {
			net.sf.ehcache.Element element = null;
			element = commonCache.getElementFromRole(key);
			return element;
		} else
			return null;
	}
	
	/**
	 * 获得指定的角色缓存
	 * 
	 * @param key
	 * @return
	 */
	public CoreRole getRoleFromCache(String key) {
		if (StringUtils.isNotEmpty(key)) {
			CoreRole element = commonCache.getRoleFromCache(key);
			return element;
		} else
			return null;
	}
	
	/**
	 * 根据身份和角色的组合从缓存中获取CoreRole对象
	 * @param searchKey 身份和角色的组合
	 * @return 角色列表
	 */
	public List<CoreRole> getRoleByGrade(String searchKey){
		return commonCache.getRoleByGrade(searchKey);
	}
	/**
	 * 得到角色
	 * 
	 * @param id
	 * @return
	 */
	public String getDefaultTreeXml() {
		List<CoreRole> roles = this.find("from CoreRole where siteId is null or siteId<=0", new Object[]{});
		DHtmlXTree tree = new DHtmlXTree();
		tree.setTreeId("role_list");
		for(CoreRole coreRole:roles){
			Node siteNode = tree.initNode();
			siteNode.setId(String.valueOf(coreRole.getOid()));
			siteNode.setText(coreRole.getRolename());
			tree.setIsDynamicLoad(true);
	
			// 只加载站点
			siteNode.setChild("1");
			
			tree.addNode(siteNode);
			}
		
		return tree.getDHtmlXmlTree();
	}
	
	/**
	 * 根据站点ID，文档ID，
	 * 身份+等级树
	 * @author Dicky
	 * @time 2012-9-12 14:15:36
	 * @version 1.0
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getIdentityGradeTreeXml(Long siteId, Long docId){
		DHtmlXTree tree = new DHtmlXTree();
		List<CoreRole> roles = this.find("from CoreRole where siteId = ? and roleType = ? order by identityGradeString asc", new Object[]{siteId, 2L});
		if(roles.size()==0) tree.getDHtmlXmlTree();
		Map<String, List<CoreRole>> roleMap = new HashMap<String, List<CoreRole>>();
		for (CoreRole coreRole : roles) {
			if(roleMap.get(coreRole.getRoleIdentity())==null){
				roleMap.put(coreRole.getRoleIdentity(), new ArrayList<CoreRole>());
			}
			roleMap.get(coreRole.getRoleIdentity()).add(coreRole);
		}
		List<Long> pers = this.find("select roleId from CmsPermission where setType=? and objectId=?", Constants.DOCUMENT_TYPE, docId);
		List<CoreIdentity> identities = this.find("from CoreIdentity where siteId =? order by orderno asc", new Object[]{siteId});
		for (CoreIdentity coreIdentity : identities) {
			Node node = tree.initNode();
			node.setId(coreIdentity.getIcode());
			node.setText(coreIdentity.getIname());
			List<Node> nodeList = new ArrayList<Node>();
			List<CoreRole> roleList = roleMap.get(coreIdentity.getIcode());
			if(roleList.size()==1){
				CoreRole coreRole = roleList.get(0);
				String code = coreIdentity.getIcode()+"_ALL";
				if(code.equalsIgnoreCase(coreRole.getIdentityGradeString())){
					node.setId(coreRole.getOid().toString());
					boolean checked = pers.contains(coreRole.getOid());
					node.setChecked(checked);
					tree.addNode(node);
					continue;
				}
			}
			for (CoreRole coreRole : roleList) {
				boolean checked = pers.contains(coreRole.getOid());
				//if(checked) pchecked = true;
				String[] codes = coreRole.getIdentityGradeString().split("&");
				String[] names = coreRole.getIdentityGradeText().split("&");
				Node childNode = tree.initNode();
				childNode.setId(coreRole.getOid().toString());
				String[] names1 = names[0].split("_");
				String name = names1.length>1 ? names1[1] : names1[0];
				childNode.setText(name);
				childNode.setChecked(checked);
				nodeList.add(childNode);
				if(codes.length>1){
					try{
						String codes1 = "";
						for (int i = 1; i < codes.length; i++) {
							codes1 += "/"+codes[i].split("_")[1];
						}
						childNode.setText(name.substring(0,name.lastIndexOf(")"))+codes1+")");
					}catch (Exception e) {
					}
				}
			}
			node.setSubNodeList(nodeList);
			tree.addNode(node);
		}
		return tree.getDHtmlXmlTree();
	}
	
	/**
	 * 加载所有角色
	 * 
	 * @param tree
	 * @param channel
	 * @return
	 */
	private Node appendRole(DHtmlXTree tree, CoreRole coreRole) {
		Node node = tree.initNode();
		node.setText(coreRole.getRolename());
		node.setId(coreRole.getOid().toString());
		if (tree.getIsDynamicLoad())
			node.setChild("1");
		return node;
	}

	/**
	 * dwr 验证 : rolecode 是否唯一
	 * @remark add by liaozhiyong 2012-03-21
	 * @param CoreRoleId
	 * @return String
	 */ 
	@SuppressWarnings("unchecked")
	public String dwrCoreRoleValidateRolecodeUnique(String CoreRoleId, String rolecode, String rolecodes, String siteId){
		String flag = "0";
			String hql = " from CoreRole where 1=1 ";
			if(StringUtils.isNotBlank(rolecode) && StringUtils.isNotBlank(rolecodes)){
				hql += " and (rolecode='"+rolecode.trim()+"' or identityGradeString='"+rolecodes+"') ";
			}else{
				 if(StringUtils.isNotBlank(rolecode)){
					 hql += " and rolecode='"+rolecode.trim()+"' ";
				 }
			}
			if(StringUtils.isNotBlank(CoreRoleId)){
				hql += " and oid!="+CoreRoleId+" ";
			}
			if(StringUtils.isNotBlank(siteId)){
				hql += " and siteId="+siteId+" ";
			} 
			List<CoreCommonType> list =this.find( hql );
			if(list!=null && list.size()>0){
				flag = "0";
			}else{
				flag = "1";
			}
		return flag;
	}
	
	/**
	 * 将角色加到缓存中
	 * 
	 * @param role
	 *            角色对象
	 */
	public void putRoleInCache(CoreRole role) {
		commonCache.putRoleCacheInCache(role);
	}
	
	/**
	 * 根据角色id,从缓存中移除掉对应的角色缓存
	 * 
	 * @param id
	 *            角色对象id
	 */
	public void removeRoleFormCache(Long id) {
		commonCache.removeRoleById(id.toString());
	}

}
