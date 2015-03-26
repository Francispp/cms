package com.cyberway.weixin.util;

import net.sf.json.JSONObject;

import com.cyberway.weixin.api.API;
import com.cyberway.weixin.entity.AccessToken;
import com.cyberway.weixin.service.MediaService;
/**
 * 
 * @com.cyberway.weixin.util.AccessTokenUtil
 * TODO : 获取企业token
 * @author yanruqian
 * @version 1.0.0
 * @createDate：2015年01月27日 上午10:51:04
 */
public class AccessTokenUtil {
	public static String access_token = null;
	public static AccessToken getAccessToken(String corpID, String secret) {
		AccessToken accessToken = null;
		String requestUrl = API.COMPANY_ACCESS_TOKEN_URL.replace("CORPID", corpID).replace("CORPSECRET", secret);
		JSONObject jsonObject = ConnectionUtil.getHttpRequest(requestUrl, "GET", null);
		try {
			accessToken = new AccessToken();
			accessToken.setToken(jsonObject.getString("access_token"));
			accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			System.out.println("获取token成功:"+ jsonObject.getString("access_token") + "->> "+ jsonObject.getInt("expires_in"));
		} catch (Exception e) {
			accessToken = null;
			// 获取token失败
			String error = String.format("获取token失败 errcode:{} errmsg:{}",jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			System.out.println(error);
		}
		return accessToken;
	}
	/**
	 * 
	*  <p>TODO (这里用一句话描述这个方法的作用)</p>
	 * @param requestUrl 请求路劲
	 * @param requestMethod 请求方法
	 * @param param 参数
	 * @param type 1代表通用。0代表上传流媒体
	 * @return
	 */
	public static JSONObject requestData(String requestUrl,String requestMethod,String param,String type){
		if(access_token==null){
			access_token = getAccessToken(Constants.CORPID, Constants.SECRET).getToken();
		}
		String url = requestUrl.replace("ACCESS_TOKEN", access_token);
		JSONObject json = null;
		if(type.equals("1")){
			json = ConnectionUtil.getHttpRequest(url, requestMethod, param);
			if(json.containsKey("errcode")){
				int code = json.getInt("errcode");
				if(code==40001 || code==40014 ||code ==42001){
					access_token = getAccessToken(Constants.CORPID, Constants.SECRET).getToken();
					url = requestUrl.replace("ACCESS_TOKEN", access_token);
					json = ConnectionUtil.getHttpRequest(url, requestMethod, param);
				}
			}
		}else if(type.equals("0")){
			json = MediaService.uploadMedia(url,requestMethod, param);
			if(json.containsKey(json.getString("media_id"))){
				access_token = getAccessToken(Constants.CORPID, Constants.SECRET).getToken();
				url = requestUrl.replace("ACCESS_TOKEN", access_token);
				json = MediaService.uploadMedia(url,requestMethod, param);
			}
		}
		
		return json;
	}
}
