package com.cyberway.weixin.test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import com.cyberway.weixin.api.JsAPI;
import com.cyberway.weixin.entity.JSSDK;
import com.cyberway.weixin.util.AccessTokenUtil;
import com.cyberway.weixin.util.CommonUtil;
import com.cyberway.weixin.util.Constants;

public class Test4 {
	public static void main(String[] args) {
		String access_token = AccessTokenUtil.getAccessToken(Constants.CORPID,Constants.SECRET).getToken();
		String url = "http://qiye.omsapp.cn/jsapi";
		JSONObject jsonobject = CommonUtil.invoke(access_token, "GET",JsAPI.GET_JSAPI_TICKET, null);
		if (null != jsonobject) {
			int errcode = jsonobject.getInt("errcode");
			String errmsg = jsonobject.getString("errmsg");
			if (0 != errcode) {
				String error = String.format("获取jsapi_ticket 失败 errcode:{%s} errmsg:{%s}",errcode, errmsg);
				System.out.println(error);
			} else {
				Map<String, String> ret = getCorpSign(jsonobject.getString("ticket"), url);
				for (Map.Entry entry : ret.entrySet()) {
		            System.out.println(entry.getKey() + ", " + entry.getValue());
		        }
				
				String error = String.format("操作成功 errcode:{%s} errmsg:{%s}",errcode, errmsg);
				System.out.println(jsonobject+error);
			}
		}
	}

	    public static Map<String, String> getCorpSign(String jsapi_ticket, String url) {
	        Map<String, String> ret = new HashMap<String, String>();
//	        String nonce_str = UUID.randomUUID().toString();
//	        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
	        String nonce_str = "82693e11-b9bc-448e-892f-f5289f46cd0f";
	        String timestamp = "1419835025";
	        String string1="";
	        String signature = "";
	        //注意这里参数名必须全部小写，且必须有序
	        string1 = "jsapi_ticket=" + jsapi_ticket +"&noncestr=" + nonce_str +"&timestamp=" + timestamp +"&url=" + url;
	        System.out.println(string1+"----------------------------------");
	        try
	        {
	            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	            crypt.reset();
	            crypt.update(string1.getBytes("UTF-8"));
	            signature = byteToHex(crypt.digest());
	        }
	        catch (NoSuchAlgorithmException e)
	        {
	            e.printStackTrace();
	        }
	        catch (UnsupportedEncodingException e)
	        {
	            e.printStackTrace();
	        }
	        ret.put("url", url);
	        ret.put("jsapi_ticket", jsapi_ticket);
	        ret.put("nonceStr", nonce_str);
	        ret.put("timestamp", timestamp);
	        ret.put("signature", signature);
	        return ret;
	    }

	    private static String byteToHex(final byte[] hash) {
	        Formatter formatter = new Formatter();
	        for (byte b : hash)
	        {
	            formatter.format("%02x", b);
	        }
	        String result = formatter.toString();
	        formatter.close();
	        return result;
	    }
}
