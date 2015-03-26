package com.cyberway.weixin.test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cyberway.weixin.api.API;
import com.cyberway.weixin.api.MemberAPI;
import com.cyberway.weixin.service.MemberService;
import com.cyberway.weixin.util.AccessTokenUtil;
import com.cyberway.weixin.util.CommonUtil;
import com.cyberway.weixin.util.Constants;

public class Test1 {
	public static void main(String[] args) {
		// 调取凭证
		String access_token = AccessTokenUtil.getAccessToken(Constants.CORPID,Constants.SECRET).getToken();
		// 拼装数据
//		String jsonData = MemberService.Create("test003", "test003", "项目经理",
//				"13802006666", "0", "020-44246666", "test003@cyberway.net.cn",
//				"test003");
//		String jsonData = MemberService.Updata("test001", "test001", "程序员", "13322226666", "1", "400-88882222", "test001@cyberway.net.cn", "test001", "1");
//		String jsonData = MemberService.Delete("test003");
//		String jsonData = MemberService.GPerson("test003");
//		String jsonData = MemberService.GGroup("41");
		String jsonData = MemberService.invite("libin","请关注公司微信号");
		// 提交数据，获取结果
		JSONObject jsonobject = CommonUtil.invoke(access_token, "POST", MemberAPI.MEMBER_INVITE_URL, jsonData);
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
						String department = data.getString("department");
						String name = data.getString("name");
						System.out.println("用户ID : "+userId+" 用户名："+name+" 部门ID: "+department);
					}
				}
				String error = String.format("操作成功 errcode:{%s} errmsg:{%s}",errcode, errmsg);
				System.out.println(error);
			}
		}
	}
}
