package com.cyberway.weixin.test;

import net.sf.json.JSONObject;

import com.cyberway.weixin.service.MediaService;
import com.cyberway.weixin.util.AccessTokenUtil;
import com.cyberway.weixin.util.Constants;

public class Test3 {
	public static String LOCALHOST_IMAGE_URL = "E:\\workspace\\LMS_PHASE2\\images\\bg_top_afterClass.jpg";
	public static void main(String[] args){
		String access_token = AccessTokenUtil.getAccessToken(Constants.CORPID,Constants.SECRET).getToken();
		JSONObject jsonobject = MediaService.uploadMedia("POST","image", LOCALHOST_IMAGE_URL);
		if (null != jsonobject) {
			if(jsonobject.containsKey("type")){
				String type = jsonobject.getString("type");
				String media_id = jsonobject.getString("media_id");
				String created_at = jsonobject.getString("created_at");
				String error = String.format("操作成功 type:{%s}  media_id:{%s}  created_at:{%s}",type, media_id,created_at);
				System.out.println(error);
				System.out.println("---------------------------------------------------------------------------------------------");
				String savePath = MediaService.downloadMedia(access_token,"GET", media_id, "E:\\图书收集");
				System.out.println("下载成功之后保存在本地的地址为："+savePath);
			}else{
				String error = String.format("操作失败 errcode:{%s} errmsg:{%s}",jsonobject.getString("errcode"), jsonobject.getString("errmsg"));
				System.out.println(error);
			}
		}
	}
}
