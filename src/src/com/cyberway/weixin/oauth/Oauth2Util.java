package com.cyberway.weixin.oauth;

import com.cyberway.weixin.api.UserAPI;
import com.cyberway.weixin.util.AccessTokenUtil;
import com.cyberway.weixin.util.CommonUtil;
import com.cyberway.weixin.util.ConnectionUtil;
import com.cyberway.weixin.util.Constants;

import net.sf.json.JSONObject;
/**
 * 
 * @com.cyberway.weixin.oauth.Oauth2Util
 * TODO : Oauth2验证
 * @author yanruqian
 * @version 1.0.0
 * @createDate：2015年2月9日 上午10:48:55
 */
public class Oauth2Util {
	public static String GET_CODE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx5c2cc405f9858fc8&redirect_uri=http://wx.cyberway.com.cn:8084/weixin/attendance!signIn.action&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
	/**
	 * 企业获取code地址处理
	 * @param appid 企业的CorpID
	 * @param redirect_uri 授权后重定向的回调链接地址，请使用urlencode对链接进行处理
	 * @param response_type 返回类型，此时固定为：code
	 * @param scope 应用授权作用域，此时固定为：snsapi_base
	 * @param state 重定向后会带上state参数，企业可以填写a-zA-Z0-9的参数值
	 * @param #wechat_redirect 微信终端使用此参数判断是否需要带上身份信息
	 * 员工点击后，页面将跳转至 redirect_uri/?code=CODE&state=STATE，企业可根据code参数获得员工的userid
	 * */
	public static String getCode(){
		String get_code_url = "";
		get_code_url = GET_CODE.replace("CORPID", Constants.CORPID).replace("REDIRECT_URI", CommonUtil.URLEncoder(Constants.REDIRECT_URI));
		return get_code_url;
	}
	/**
	 * 根据code获取成员信息
	 * @param access_token 调用接口凭证
	 * @param code 通过员工授权获取到的code，每次员工授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期
	 * @param agentid 跳转链接时所在的企业应用ID
	 * 管理员须拥有agent的使用权限；agentid必须和跳转链接时所在的企业应用ID相同
	 * */
	public static String getUserID (String code ,String agentid){
		String UserId = null;
		String requestUrl = UserAPI.USER_CODE_TO_USERINFO.replace("CODE", code).replace("AGENTID", agentid);
		JSONObject jsonobject = AccessTokenUtil.requestData(requestUrl, "GET", null,"1");
		if(jsonobject.containsKey("UserId")){
			UserId = jsonobject.getString("UserId");
			return UserId;
		}else{
			System.out.println("操作失败："+jsonobject);
		}
		return null;
	}
}
