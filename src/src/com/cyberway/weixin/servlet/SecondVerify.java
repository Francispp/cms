package com.cyberway.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import net.sf.json.JSONObject;

import com.cyberway.weixin.api.SecondVerifyAPI;
import com.cyberway.weixin.oauth.Oauth2Util;
import com.cyberway.weixin.util.AccessTokenUtil;
import com.cyberway.weixin.util.ConnectionUtil;
import com.cyberway.weixin.util.Constants;
import com.cyberway.weixin.util.JSSDKUtil;
/**
 * 
 * @com.cyberway.weixin.servlet.SecondVerify
 * TODO : 用户关注二次验证
 * @author yanruqian
 * @version 1.0.0
 * @createDate：2015年1月27日 上午10:50:29
 */
public class SecondVerify extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  request.setCharacterEncoding("UTF-8");
		  response.setCharacterEncoding("UTF-8"); 
		  PrintWriter out = response.getWriter();
		  String code = request.getParameter("code"); 
		  if (!"authdeny".equals(code)) {
		  // agentid 跳转链接时所在的企业应用ID 管理员须拥有agent的使用权限；agentid必须和跳转链接时所在的企业应用ID相同
			  String UserID = Oauth2Util.getUserID(code, "2");
	       // 二次验证地址          
			  if(UserID!=""){
				  String requestUrl = SecondVerifyAPI.SECOND_VERIFY_URL.replace("USERID", UserID);  
				  JSONObject jsonObject = AccessTokenUtil.requestData(requestUrl, "GET", null,"1");
//				  out.print("二次验证返回结果：\n"+jsonObject);  
		  	  	  request.setAttribute("jsonObject", jsonObject);  
				  request.setAttribute("UserID", UserID);
				  Map<String, String> ret = JSSDKUtil.getCorpSign(request.getRequestURL().toString());
				  if(ret !=null){
					 request.setAttribute("corpId", Constants.CORPID);
					 request.setAttribute("timestamp", ret.get("timestamp"));
					 request.setAttribute("nonceStr", ret.get("nonceStr"));
					 request.setAttribute("jsapi_ticket", ret.get("jsapi_ticket"));
					 request.setAttribute("signature", ret.get("signature"));
					 request.setAttribute("url", ret.get("url"));
				  }
				  request.getRequestDispatcher("index.jsp").forward(request, response);
			  }else{
				  out.print("获取用户信息失败。。。");
			  }
		  }
		  else{
			  out.print("授权获取失败，至于为什么，自己找原因。。。");
		  }
	 }
}
