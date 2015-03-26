package com.cyberway.weixin.api;
/**
 * 
 * @com.cyberway.weixin.api.MediaAPI
 * TODO :流媒体接口
 * @author yanruqian
 * @version 1.0.0
 * @createDate：2015年2月9日 上午10:46:23
 */
public class MediaAPI {
	/**
	 * 上传媒体文件
	 */
	public static String MEDIA_UPLOAD_URL = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	/**
	 * 获取媒体文件
	 */
	public static String MEDIA_DOWNLOAD_URL = "https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
}
