package com.cyberway.weixin.business.auth.service;

import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.weixin.api.AuthAPI;
import com.cyberway.weixin.api.SuiteAPI;
import com.cyberway.weixin.business.auth.domain.Auth;
import com.cyberway.weixin.business.auth.domain.AuthDetails;
import com.cyberway.weixin.business.department.domain.Department;
import com.cyberway.weixin.entity.Authorize;
import com.cyberway.weixin.util.ConnectionUtil;
import com.cyberway.weixin.util.SuiteUtil;
/**
 * 
 * @com.cyberway.weixin.business.auth.service.AuthService
 * TODO :
 * @author yanruqian
 * @version 1.0.0
 * @createDate：2015年2月12日 下午4:22:41
 */
public class AuthService  extends HibernateEntityDao<Auth>{

	public  String getJsonAppData(String suiteId,String appId){
		String jsonData = "{\"suite_id\": \"%s\",\"appid\": [%s]}";
		return String.format(jsonData,suiteId,appId);
	}
	//获取永久授权码参数
	public  String getPermanmentData(String suiteId,String authCode){
		String jasonData = "{\"suite_id\": \"%s\",\"auth_code\": \"%s\"}";
		return String.format(jasonData, suiteId,authCode);			
	}
	//获取授权信息
	public  String getJsonAuthInfo(String suiteId,String authCorpid,String permanentCode){
		String jasonData = "{\"suite_id\": \"%s\",\"auth_corpid\": \"%s\",\"permanent_code\",\"%s\"}";
		return String.format(jasonData, suiteId,authCorpid,permanentCode);			
	}
	//获取企业号应用
	public  String getJsonAgent(String suiteId,String authCorpid,String permanentCode){
		String jasonData = "{\"suite_id\": \"%s\",\"auth_corpid\": \"%s\",\"permanent_code\",\"%s\",\"agentid\":\"1\"}";
		return String.format(jasonData, suiteId,authCorpid,permanentCode);		

	}
	
	/**
	 * 
	*  <p>TODO 获取临时授权码</p>
	 * @param suiteAccessToken
	 * @return
	 */
	public Authorize getCode(String suiteAccessToken){
		//预授权码
		String jsonAppData = getJsonAppData(SuiteUtil.SUITE_ID,SuiteUtil.SUITE_APP_ID);
		JSONObject jsonAppobject = ConnectionUtil.getHttpRequest(AuthAPI.GET_PRE_AUTH_CODE_URL+suiteAccessToken, "POST", jsonAppData);
		Authorize auth = null;
		if(jsonAppobject!=null){
			int errcode = jsonAppobject.getInt("errcode");
			String errmsg = jsonAppobject.getString("errmsg");
			if (0 != errcode) {
				String error = String.format("操作失败 errcode:{%s} errmsg:{%s}",errcode, errmsg);
				System.out.println(error);
			}else{
				auth = new Authorize();
				auth.setAuthCode(jsonAppobject.getString("pre_auth_code"));
				auth.setExpiresIn(jsonAppobject.getInt("expires_in"));
			}
		}
		return auth;
	}
	/**
	 * 
	*  <p>TODO获取企业号的永久授权码</p>
	 * @param jsonObject
	 * @return
	 */
	public Auth saveCorpAuthInfo(JSONObject jsonObject){
		Auth auth = null;
		AuthDetails details = new AuthDetails();
		Department department = new Department(); 
		if(jsonObject != null){
			if(!jsonObject.containsKey("errcode")){
				String errmsg = jsonObject.getString("access_token");//授权方（企业）access_token 
				int expires_in2 = jsonObject.getInt("expires_in");//授权方（企业）access_token超时时间 
				String permanent_code = jsonObject.getString("permanent_code");//企业号永久授权码 
				if(jsonObject.containsKey("auth_corp_info")){//授权方企业信息 
					JSONObject fromObject =jsonObject.getJSONObject("auth_corp_info");
					String corp_id = fromObject.getString("corpid");//授权方企业号id 
					String corp_name = fromObject.getString("corp_name");//授权方企业号名称 
					String corp_type = fromObject.getString("corp_type");//授权方企业号类型，认证号：verified, 注册号：unverified，体验号：test  
					String corp_round_logo_url = fromObject.getString("corp_round_logo_url");//授权方企业号圆形头像
					String corp_square_logo_url = fromObject.getString("corp_square_logo_url");//授权方企业号方形头像
					Integer corp_user_max = fromObject.getInt("corp_user_max");//授权方企业号用户规模
					Integer corp_agent_max = fromObject.getInt("corp_agent_max");//授权方企业号应用规模
					String corp_wxqrcode = fromObject.getString("corp_wxqrcode");//授权方企业号二维码 
					auth = new Auth();
					auth.setPermanentCode(permanent_code);
					auth.setCorpId(corp_id);
					auth.setCorpName(corp_name);
					auth.setCorpType(corp_type);
					auth.setCorp_round_logo_url(corp_round_logo_url);
					auth.setCorp_square_logo_url(corp_square_logo_url);
					auth.setCorp_user_max(corp_user_max);
					auth.setCorp_agent_max(corp_agent_max);
					auth.setCorp_wxqrcode(corp_wxqrcode);
					auth.setCreateDate(new Date());
				}
				if(jsonObject.containsKey("auth_info")){//授权信息
					JSONArray jsonarray = jsonObject.getJSONArray("auth_info");
					for(int i=0;i<jsonarray.size();i++){
						JSONObject data = jsonarray.getJSONObject(i);
						if(data.containsKey("agent")){//授权的应用信息
							Integer agentid = data.getInt("agentid");//授权方应用id 
							String name = data.getString("name");//授权方应用名字
							String square_logo_url = data.getString("square_logo_url");//授权方应用方形头像
							String round_logo_url = data.getString("round_logo_url");//授权方应用圆形头像
							Integer appid = data.getInt("appid");//服务商套件中的对应应用id
							JSONObject get_location = data.getJSONObject("api_group");//授权方应用敏感权限组，目前仅有get_location，表示是否有权限设置应用获取地理位置的开关
							details.setAgentId(agentid);
							details.setAgentName(name);
							details.setSquare_logo_url(square_logo_url);
							details.setRound_logo_url(round_logo_url);
							details.setAppId(appid);
							details.setCreateDate(new Date());
						} 
						if(data.containsKey("department")){//授权的通讯录部门
							Integer deptId = data.getInt("id");//部门id 
							String name = data.getString("name");//部门名称
							Integer parentid = data.getInt("parentid");//父部门id
							String writable = data.getString("writable");//是否具有该部门的写权限
							department.setDeptId(deptId);
							department.setDeptName(name);
							department.setParentId(parentid);
							department.setWritable(writable);
						}
					}
				}
				auth.setAuthDetails(details);
				auth.setDepartmet(department);
				this.saveOrUpdate(auth);
			}else{
				System.out.println(String.format("操作失败 errcode:{%s} errmsg:{%s}", jsonObject.getString("errcode"),jsonObject.getString("errmsg")));
			}
		}
		return auth;
	}
}
