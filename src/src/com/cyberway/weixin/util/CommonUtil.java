package com.cyberway.weixin.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cyberway.weixin.api.MemberAPI;
import com.cyberway.weixin.service.MemberService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 
 * @com.cyberway.weixin.util.CommonUtil
 * TODO :
 * @author yanruqian
 * @version 1.0.0
 * @createDate：2015年2月9日 上午10:51:26
 */
public class CommonUtil {
	public static JSONObject invoke(String access_token, String requestMethod,String RequestURL, String param) {
		JSONObject jsonobject = null;
		RequestURL = RequestURL.replace("ACCESS_TOKEN", access_token);
		jsonobject = ConnectionUtil.getHttpRequest(RequestURL, requestMethod, param);
		if (null != jsonobject) {
			return jsonobject;
		}
		return null;
	}
	/**
	 * 根据内容类型判断文件扩展名
	 * 
	 * @param contentType 内容类型
	 * @return
	 */
	public static String getFileEndWitsh(String contentType) {
		String fileEndWitsh = "";
		if ("image/jpeg".equals(contentType))
			fileEndWitsh = ".jpg";
		else if ("audio/mpeg".equals(contentType))
			fileEndWitsh = ".mp3";
		else if ("audio/amr".equals(contentType))
			fileEndWitsh = ".amr";
		else if ("video/mp4".equals(contentType))
			fileEndWitsh = ".mp4";
		else if ("video/mpeg4".equals(contentType))
			fileEndWitsh = ".mp4";
		return fileEndWitsh;
	}
	/**
	 * 
	*  <p>TODO (设置字符编码)</p>
	 * @param str
	 * @return
	 */
	public static String URLEncoder(String str){
		String result = str ;
		try {
		result = java.net.URLEncoder.encode(result,"UTF-8");	
		} catch (Exception e) {
        e.printStackTrace();
		}
		return result;
	}
	
	public static WXLoginer findByUserId(String userId){
		WXLoginer user = null;
		String requestUrl = MemberService.GPerson(userId);// 调取凭证
		JSONObject jsonobject = AccessTokenUtil.requestData(requestUrl, "POST", null,"1");
		int errcode = jsonobject.getInt("errcode");
		String errmsg = jsonobject.getString("errmsg");
		if (0 != errcode) {
			String error = String.format("操作失败 errcode:{%s} errmsg:{%s}",errcode, errmsg);
			System.out.println(error);
		} else {
			if(jsonobject.containsKey("userid")){
				user = new WXLoginer();
				user.setUserid(jsonobject.getString("userid"));
				user.setName(jsonobject.getString("name"));
//				user.setPosition(jsonobject.getString("position"));
				user.setMobile(jsonobject.getString("mobile"));
				user.setEmail(jsonobject.getString("email"));
				user.setWeixinid(jsonobject.getString("weixinid"));
				user.setAvatar(jsonobject.getString("avatar"));
				user.setStatus(jsonobject.getInt("status"));
			}		
		}
		return user;
	}
}
