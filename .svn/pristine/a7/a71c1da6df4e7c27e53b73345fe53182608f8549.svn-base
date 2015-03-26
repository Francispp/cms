package com.cyberway.weixin.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.cyberway.weixin.api.JsAPI;

import net.sf.json.JSONObject;

public class JSSDKUtil {
	public static final String jsApiList = 
			  "['onMenuShareTimeline','onMenuShareAppMessage','onMenuShareQQ','onMenuShareWeibo','startRecord','stopRecord',"
			+ "'onVoiceRecordEnd','playVoice','pauseVoice','stopVoice','onVoicePlayEnd','uploadVoice','downloadVoice','chooseImage',"
			+ "'previewImage','uploadImage','downloadImage','translateVoice','getNetworkType','openLocation','getLocation','hideOptionMenu',"
			+ "'showOptionMenu','hideMenuItems','showMenuItems','hideAllNonBaseMenuItem','showAllNonBaseMenuItem','closeWindow','scanQRCode']";
	
	public static String jsapi_ticket=null;
	public static Map<String, String> getJspTicket(String url,String type){
		if(jsapi_ticket == null){
			JSONObject jsonobject = AccessTokenUtil.requestData(JsAPI.GET_JSAPI_TICKET, "GET", null,"1");
			if(jsonobject.containsKey("ticket")){
				jsapi_ticket = jsonobject.getString("ticket");
				System.out.println("获取jsapi_ticket成功"+jsonobject.getString("ticket"));
			}
		}
		if(null != type || "signErr".equals(type)){
			JSONObject jsonobject = AccessTokenUtil.requestData(JsAPI.GET_JSAPI_TICKET, "GET", null,"1");
			if(jsonobject.containsKey("ticket")){
				jsapi_ticket = jsonobject.getString("ticket");
				System.out.println("第二次获取jsapi_ticket成功"+jsonobject.getString("ticket"));
			}
		}
		Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = UUID.randomUUID().toString();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String string1="";
        String signature = "";
        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +"&noncestr=" + nonce_str +"&timestamp=" + timestamp +"&url=" + url;
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
//        ret.put("url", url);
//        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("appId", Constants.CORPID);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        ret.put("jsApiList", jsApiList);
		return ret;
	} 
	public static Map<String, String> getCorpSign(String url){
		JSONObject jsonobject = AccessTokenUtil.requestData(JsAPI.GET_JSAPI_TICKET, "GET", null,"1");
		Map<String, String> ret = null;
		if (null != jsonobject) {
			if (0 != jsonobject.getInt("errcode")) {
				System.out.println("获取jsapi_ticket 失败 "+jsonobject);
			} else {
				ret = getSign(jsonobject.getString("ticket"), url);
				System.out.println("获取jsapi_ticket成功"+jsonobject+"--"+ret);
			}
		}
		return ret;
	}
	 public static Map<String, String> getSign(String jsapi_ticket, String url) {
	        Map<String, String> ret = new HashMap<String, String>();
	        String nonce_str = UUID.randomUUID().toString();
	        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
	        String string1="";
	        String signature = "";
	        string1 = "jsapi_ticket=" + jsapi_ticket +"&noncestr=" + nonce_str +"&timestamp=" + timestamp +"&url=" + url;//注意这里参数名必须全部小写，且必须有序
	        try
	        {
	            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	            crypt.reset();
	            crypt.update(string1.getBytes("UTF-8"));
	            signature = byteToHex(crypt.digest());
	        }catch (NoSuchAlgorithmException e){
	            e.printStackTrace();
	        }catch (UnsupportedEncodingException e){
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
	        for (byte b : hash) {
	            formatter.format("%02x", b);
	        }
	        String result = formatter.toString();
	        formatter.close();
	        return result;
	    }
	    /**
	     * 
	    *  <p>TODO js配置信息</p>
	     * @param requestUrl
	     * @param isDebug 是否开启debug模式
	     * @return
	     */
	    public static String getJsConfig(String requestUrl,Boolean isDebug){
			Map<String, String> resultMap = JSSDKUtil.getJspTicket(requestUrl,null);
			StringBuffer sb  = null;
			if(resultMap !=null){
				sb = new StringBuffer("wx.config({");
				sb.append("debug:").append(isDebug).append(",");
				sb.append("appId:");
				sb.append("'").append(Constants.CORPID).append("',");
				sb.append("timestamp:");
				sb.append(resultMap.get("timestamp")).append(",");
				sb.append("nonceStr:");
				sb.append("'").append(resultMap.get("nonceStr")).append("',");
				sb.append("signature:");
				sb.append("'").append(resultMap.get("signature")).append("',");
				sb.append("jsApiList:");
				sb.append(jsApiList);
				sb.append("});");
			 }
		    return sb.toString();
		}
	    
}
