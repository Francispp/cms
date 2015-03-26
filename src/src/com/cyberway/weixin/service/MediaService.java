package com.cyberway.weixin.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.cyberway.weixin.api.API;
import com.cyberway.weixin.api.MediaAPI;
import com.cyberway.weixin.util.CommonUtil;
import com.cyberway.weixin.util.ConnectionUtil;

import net.sf.json.JSONObject;

public class MediaService {
	/**
	 * 
	*  TODO 文件上传到微信服务器 
	 * @param accessToken 接口访问凭证
	 * @param requestMethod 请求方式
	 * @param fileType 文件类型 
	 * @param filePath 文件路径 
	 * @return
	 */
    public static JSONObject uploadMedia(String uploadMediaUrl,String requestMethod,String filePath){   
        JSONObject jsonObject = null;  
        File file = new File(filePath);   
        if (!file.exists() || !file.isFile()) {   
            try {
				throw new IOException("文件不存在");
			} catch (IOException e) {
				e.printStackTrace();
			}   
        }   
		try {
			// 建立连接
			HttpURLConnection connection = ConnectionUtil.getConnection(uploadMediaUrl, requestMethod);
			if(connection!=null){
				// 设置请求头信息
				connection.setRequestProperty("Connection", "Keep-Alive");
				connection.setRequestProperty("Charset", "UTF-8");
				// 设置边界
				String BOUNDARY = "----------" + System.currentTimeMillis();
				connection.setRequestProperty("Content-Type","multipart/form-data; boundary=" + BOUNDARY);
				// 请求正文信息
				// 第一部分：
				StringBuilder sb = new StringBuilder();
				sb.append("--"); // 必须多两道线
				sb.append(BOUNDARY);
				sb.append("\r\n");
				sb.append("Content-Disposition: form-data;name=\"file\";filename=\""+ file.getName() + "\"\r\n");
				sb.append("Content-Type:application/octet-stream\r\n\r\n");
				byte[] head = sb.toString().getBytes("utf-8");
				// 获得输出流
				OutputStream out = new DataOutputStream(connection.getOutputStream());
				// 输出表头
				out.write(head);
				// 文件正文部分
				// 把文件已流文件的方式 推入到url中
				DataInputStream in = new DataInputStream(new FileInputStream(file));
				int bytes = 0;
				byte[] bufferOut = new byte[1024];
				while ((bytes = in.read(bufferOut)) != -1) {
					out.write(bufferOut, 0, bytes);
				}
				if(in!=null){
					in.close();
				}
				// 结尾部分
				byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n")
						.getBytes("utf-8");// 定义最后数据分隔线
				out.write(foot);
				if(out!=null){
					out.flush();
					out.close();
				}
				StringBuffer buffer = new StringBuffer();
				BufferedReader reader = null;
				// 定义BufferedReader输入流来读取URL的响应
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line = null;
				while ((line = reader.readLine()) != null) {
					buffer.append(line);
				}
				if(reader!=null){
					reader.close();
				}
				jsonObject = JSONObject.fromObject(buffer.toString());
			}
        } catch (IOException e) {   
        	e.printStackTrace();   
        }   
		return jsonObject;   
    }
    /**
     * 
     *  TODO 获取媒体文件
     * @param accessToken 接口访问凭证
     * @param requestMethod 请求方式
     * @param mediaId 媒体文件id
     * @param savePath文件在服务器上的存储路径
     * @return
     */
	public static String downloadMedia(String accessToken,String requestMethod,String mediaId, String savePath) {
		String filePath = null;
		String downloadMediaUrl = MediaAPI.MEDIA_DOWNLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
		try {
			HttpURLConnection connection = ConnectionUtil.getConnection(downloadMediaUrl, requestMethod);
			if(connection!=null){
				if (!savePath.endsWith("\\")) {
					savePath += "\\";
				}
				String contentType = connection.getHeaderField("Content-Type");
				String fileExt = CommonUtil.getFileEndWitsh(contentType);// 根据内容类型获取扩展名
				filePath = savePath + mediaId + fileExt;// 将mediaId作为文件名
				BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
				FileOutputStream fos = new FileOutputStream(new File(filePath));
				byte[] buf = new byte[8096];
				int size = 0;
				while ((size = bis.read(buf)) != -1){
					fos.write(buf, 0, size);
				}
				if(fos!=null){
					fos.close();
				}
				if(bis!=null){
					bis.close();
				}
			}
			connection.disconnect();
			String info = String.format("下载媒体文件成功，filePath=" + filePath);
			System.out.println(info);
		} catch (Exception e) {
			filePath = null;
			String error = String.format("下载媒体文件失败：%s", e);
			System.out.println(error);
		}
		return filePath;
	}
}
