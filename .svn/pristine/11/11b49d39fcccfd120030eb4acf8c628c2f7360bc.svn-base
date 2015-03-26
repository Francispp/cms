package com.cyberway.weixin.test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cyberway.weixin.api.API;
import com.cyberway.weixin.api.TagAPI;
import com.cyberway.weixin.service.TagService;
import com.cyberway.weixin.util.AccessTokenUtil;
import com.cyberway.weixin.util.CommonUtil;
import com.cyberway.weixin.util.Constants;

public class Test2 {
	public static void main(String[] args) {
		// 调取凭证
		String access_token = AccessTokenUtil.getAccessToken(Constants.CORPID,Constants.SECRET).getToken();
//		String jsonData = TagService.Updata_Tag("2", "篮球兴趣小组");
//		String jsonData = TagService.Create_Tag("爬山兴趣小组");
//		String jsonData = TagService.Add_Tag_Person("4", "test001");
		String jsonData = TagService.Get_Tag_Person("4");
//		String jsonData = TagService.Delete_Tag_Person("4", "test001,test002");
		// 提交数据，获取结果
		JSONObject jsonobject = CommonUtil.invoke(access_token, "POST",TagAPI.TAG_GET_PERSON_URL, jsonData);
		if (null != jsonobject) {
			int errcode = jsonobject.getInt("errcode");
			String errmsg = jsonobject.getString("errmsg");
			if (0 != errcode) {
				String error = String.format("操作失败 errcode:{%s} errmsg:{%s}",errcode, errmsg);
				System.out.println(error);
			} else {
				if(jsonobject.containsKey("userlist")){
					JSONArray jsonarray = jsonobject.getJSONArray("userlist");
					for(int i=0;i<jsonarray.size();i++){
						JSONObject data = jsonarray.getJSONObject(i);
						String userId = data.getString("userid");
						String name = data.getString("name");
						String department = data.getString("department");
						System.out.println("用户ID : "+userId+" 用户名："+name+" 部门："+department);
					}
				}
				String error = String.format("操作成功 errcode:{%s} errmsg:{%s}",errcode, errmsg);
				System.out.println(error);
			}
		}
	}
}
