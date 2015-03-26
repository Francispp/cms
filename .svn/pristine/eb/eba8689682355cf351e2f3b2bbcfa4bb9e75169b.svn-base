package com.cyberway.core.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cyberway.cms.component.webuser.domain.WebUser;
import com.cyberway.core.Constants;
import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

public class Loginer implements Serializable,UserFrame {
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	private static GeneralCacheAdministrator cache = new GeneralCacheAdministrator();
	/**
	 * 登录者类的Session名称
	 */
	public final static String LOGININFO_SESSION = Constants.USER_IN_SESSION;

	public final static int NO_SUCH_ID = -1;//用户不存在

	public final static int WRONG_PASSWORD = -2;//密码错误

	public final static int THE_ID_IS_OVERTIME = -3;//用户过期

	public final static int SUCCESS_LOGININ = 1;//登录成功

	public final static int ADMIN_LOGININ = 2;//管理员登录
	
	public final static int THE_SAME_NAME_EXIST = -4;//用户名相同
	
	public final static String _SYSTEM_CONFIG_MENU="_system_config_menu";//当前用户的配置菜单
	
	public final static String USER_AD="AD";//AD用户
	public final static String USER_LOCAL="LOCAL";//本地用户
	public final static String USER_OTHER="OTHER";//其他用户
	public final static String USER_WEB="WEB";
	/**
	 * 登陆者状态,见上面的常量
	 */
	private int loginerStatus;	
	
	/**
	 * 登录者userid
	 */
    private Long userid;

	/**
	 * 登录者用户名
	 */
    private String loginid;

	/**
	 * 登录者密码
	 */
    private String password;

	/**
	 * 登录者部门id
	 */
    private Long deptcode;

	/**
	 * 登录者部门名称
	 */
    private String  deptname;
	/**
	 * 登录者中文名
	 */
    private String username;	
    
    /**
	 * 登录者中邮箱
	 */
    private String userEmail;
    
    private Long siteId=0l;
    
    private String siteName;
	/**
	 * 登录时间
	 */    
    private Date loginTime;
    
    //用户类型（内部用户，外部用户）
    private String userType;
    

    /**
     * 登录页面样式
     */
    private String indexStyle;
    private boolean isAdminUser;//是否为超级管理员用户
    private List roles;
    private int loginType=0;//0-后台，1-前台  2-web上
    private Object webUser;
    
    private boolean isADUser;
    
    public boolean getIsADUser() {
		return isADUser;
	}
	public void setIsADUser(boolean isADUser) {
		this.isADUser = isADUser;
	}
	public boolean getIsAdminUser(){
    	if(!isAdminUser)
    		checkIsAdministratorUser();
    	return isAdminUser;
    }
    
    private Long  orgid;//当前用户的机构id
    private String orgName;//机构名称
    private String orgCode;//机构编码
	/**
	 * 登录的当前Portal的信息
	 */
	private Portal portal;
    //取得当前能管理的门户,若是超级管理员,可管理所有的门户资源
	private List portals;
	
	 private String roleCode;
	
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	/**
	 * 检测用户是否为管理员
	 * @param loginer
	 * @return
	 */
	public boolean checkIsAdministratorUser(){
		isAdminUser = false;//一般用户
		/**
		 * 静态生成时，认为创建的的临时用户的Useri等于0，而此时是公用用户，会严重干扰权限验证
		 *  com.cyberway.cms.Constants.PUBLIC_USERID=0
		 */
		/*if(getUserid()!=null && getUserid().intValue()==0){//判断管理员的条件，待后修改
			isAdminUser=true;
		}*/
		if(getLoginid()!=null && getLoginid().equals("admin"))
			isAdminUser=true;
		return isAdminUser;
	}	
	/**
	 * 判断是否有权限
	 * @param pmCode 权限代码
	 * @return
	 */
	public boolean haveThePermission(String pmCode) {
		//System.out.println("haveThePermission:"+pmCode);
		boolean rt = false;
		//若为超级用户，则拥有所有权限
		if(checkIsAdministratorUser())
			return true;
		
		if (this.getPermissions() == null || this.getPermissions().size()==0) {
			return rt;
		}
		if(this.getPermissions().contains(pmCode))
			rt=true;
/*		APermission ap = new APermission();
		ap.setPmId(pmCode);
		ap.setOrgCode(orgCode);
		if(this.getPermissions().contains(ap))
			return true;
		else*/
			return rt;
	}	
	public List getPermissions() {
		try {
			return (List) cache.getFromCache(getUserid().toString());
		} catch (NeedsRefreshException e) {
			e.printStackTrace();
			return null;
		}
	}

/*	public void setSystemConfigMenus(List systemConfigMenus) {
		cache.flushEntry(getUserid().toString()+this._SYSTEM_CONFIG_MENU);
		cache.putInCache(getUserid().toString()+this._SYSTEM_CONFIG_MENU,systemConfigMenus);
	}
	public List getSystemConfigMenus() {
		try {
			return (List) cache.getFromCache(getUserid().toString()+this._SYSTEM_CONFIG_MENU);
		} catch (NeedsRefreshException e) {
			e.printStackTrace();
			return null;
		}
	}*/

	public void setPermissions(List permissions) {
		cache.flushEntry(this.getUserid().toString());
		cache.putInCache(this.getUserid().toString(),permissions);
	}
	
	public static void setPermissions(List permissions,String userid) {
		cache.flushEntry(userid);
		cache.putInCache(userid,permissions);
	}
	
	public static List getPermissions(String userid) {
		try {
			return (List) cache.getFromCache(userid);
		} catch (NeedsRefreshException e) {
			e.printStackTrace();
			return null;
		}
	}	
	public Long getDeptcode() {
		return deptcode;
	}
	public void setDeptcode(Long deptcode) {
		this.deptcode = deptcode;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public int getLoginerStatus() {
		return loginerStatus;
	}
	public void setLoginerStatus(int loginerStatus) {
		this.loginerStatus = loginerStatus;
	}
	public String getLoginid() {
		return loginid;
	}
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public String getIndexStyle() {
		return indexStyle;
	}
	public void setIndexStyle(String indexStyle) {
		this.indexStyle = indexStyle;
	}
	public Portal getPortal() {
		if(portal==null)
			portal=new Portal();
		return portal;
	}
	public void setPortal(Portal portal) {
		this.portal = portal;
	}
	public List getPortals() {
		return portals;
	}
	public void setPortals(List portals) {
		this.portals = portals;
	}
	
	public List getRoles() {
		if(roles==null)
			roles=new ArrayList();
		return roles;
	}
	public void setRoles(List roles) {
		this.roles = roles;
	}
	public int getLoginType() {
		return loginType;
	}
	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public Long getOrgid() {
		return orgid;
	}
	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Object getWebUser() {
		if(webUser==null)
			webUser=new WebUser();
		return webUser;
	}
	public void setWebUser(Object webUser) {
		this.webUser = webUser;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{userId:").append(userid);
		buffer.append(", loginid:").append(loginid);
		buffer.append(", roleCode:").append(roleCode);
		buffer.append("}");
		return buffer.toString();
	}
	
}
