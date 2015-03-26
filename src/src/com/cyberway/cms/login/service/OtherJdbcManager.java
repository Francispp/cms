package com.cyberway.cms.login.service;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.cyberway.common.domain.CoreUser;
import com.cyberway.common.user.service.UserManagerService;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.BeanUtil;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;

/**
 * 第三方的连接管理
 * @author caiw
 *
 */
public class OtherJdbcManager {

	private DataSource otherDataSource;
	private CoreUser publicUser;
	private org.springframework.jdbc.core.JdbcTemplate otherJdbcTemplate;//第三方连接jdbctemplate
	private org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;//当前连接jdbctemplate
	/**
	 * 根据指定用户id，获得用户信息
	 * @param loginid
	 * @return
	 */
	public CoreUser getLoginerByLoginid(String loginid){
		
		//String sql = "select u.userid,u.loginid,u.username,u.password,u.deptcode,d.deptname from core_user u,core_dept d where u.deptcode=d.deptid and u.loginid=?";
		String sql = "select D02ADA,D02PIL,D02ENA,D02CNA from db2admin.zdb002  where D02ADA=? with ur";        
		//setDataSource(this.otherDataSource);
		//取公用用户基本信息(名为public,表示外部用户)
        if(publicUser==null){
		 UserManagerService userService=(UserManagerService)ServiceLocator.getBean("userManagerService");
		 List<CoreUser> pl=userService.find("from CoreUser where loginid=?", new Object[]{"public"});
		 if(pl!=null && pl.size()>0)
			 publicUser=pl.get(0);
        }
        SqlRowSet rowSet=otherJdbcTemplate.queryForRowSet(sql,new Object[] {new Long(loginid)});
        //SqlRowSet rowSet=this.createJdbcTemplate(otherDataSource).queryForRowSet(sql,new Object[] {loginid});
		//SqlRowSet rowSet = this.getJdbcTemplate().queryForRowSet(sql,new Object[] { loginid});
		CoreUser user = new CoreUser();
		try{
		 BeanUtil.updateObject(publicUser,user);
		}catch(Exception e){
			
		}
		if (rowSet.next()) {
			//user.setUserid(rowSet.getLong(1));
			user.setLoginid(loginid);
			user.setLevel(rowSet.getString(2));
			user.setUsername(rowSet.getString(4));
			
			user.setEname(rowSet.getString(3));
		
		}else
			return null;
		
		return user;
	}
	
	/**
	 * 
	 * @param loginid
	 * @return
	 */
	public int executeRoleExpression(String sql ,Loginer loginer){
		int rt=0;
		if(StringUtil.isEmpty(sql)|| loginer!=null)
			return rt;
		try{
			 //替换参数 ${loginid}
			 String qsql=StringUtil.replaceAll(sql, "${loginid}", loginer.getLoginid());
			 SqlRowSet rowSet=jdbcTemplate.queryForRowSet(qsql,new Object[] {});
			 if (rowSet.next()) {
				int count=rowSet.getInt(1);
				if(count>0)
					rt=1;
			 }
		}catch(Exception e){
			
		}
		return rt;
	}
	public DataSource getOtherDataSource() {
		return otherDataSource;
	}
	public void setOtherDataSource(DataSource otherDataSource) {
		this.otherDataSource = otherDataSource;
	}
	public org.springframework.jdbc.core.JdbcTemplate getOtherJdbcTemplate() {
		return otherJdbcTemplate;
	}
	public void setOtherJdbcTemplate(
			org.springframework.jdbc.core.JdbcTemplate otherJdbcTemplate) {
		this.otherJdbcTemplate = otherJdbcTemplate;
	}
	public org.springframework.jdbc.core.JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(
			org.springframework.jdbc.core.JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


}
