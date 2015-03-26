package com.cyberway.weixin.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.cyberway.weixin.enums.EnumMethod;

import net.sf.json.JSONObject;
/**
 * 
 * @com.cyberway.weixin.util.ConnectionUtil
 * TODO :
 * @author yanruqian
 * @version 1.0.0
 * @createDate：2015年01月27日 上午10:55:04
 */
public class ConnectionUtil {
	/**
	 *  
	 *  <p>TODO 建立连接  </p>
	 * @param request
	 * @return
	 */
	public static HttpURLConnection getConnection(String request,String RequestMethod){
		try{
	        URL url = new URL(request);  
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        if(connection!=null){
	        	connection.setDoOutput(true);  
	        	connection.setDoInput(true);  
	        	connection.setRequestMethod(RequestMethod);
	        	if(RequestMethod.equals(EnumMethod.POST.name())){ // post方式不能使用缓存
	        		connection.setUseCaches(false);
	        	}else{
	        		connection.setUseCaches(true);
	        	}
	        }
	        return connection;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	*  <p>TODO (这里用一句话描述这个方法的作用)</p>
	 * @param requestUrl 请求url
	 * @param requestMethod 请求方法
	 * @param param 参数
	 * @return
	 */
	public static JSONObject getHttpRequest(String requestUrl,String requestMethod, String param) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 建立连接
			HttpURLConnection connection = ConnectionUtil.getConnection(
					requestUrl, requestMethod);
			if (connection != null) {
				if (param != null) {
					OutputStream out = connection.getOutputStream();
					out.write(param.getBytes("UTF-8"));
					out.close();
				}
				// 流处理
				InputStream input = connection.getInputStream();
				InputStreamReader inputReader = new InputStreamReader(input,
						"UTF-8");
				BufferedReader reader = new BufferedReader(inputReader);
				String line;
				while ((line = reader.readLine()) != null) {
					buffer.append(line);
				}
				// 关闭连接、释放资源
				if (reader != null) {
					reader.close();
				}
				if (inputReader != null) {
					inputReader.close();
				}
				if (input != null) {
					input.close();
				}
				connection.disconnect();
				jsonObject = JSONObject.fromObject(buffer.toString());
			}
		} catch (Exception e) {
		}
		return jsonObject;
	}
}
