package com.cyberway.weixin.test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cyberway.weixin.api.API;
import com.cyberway.weixin.api.DepartmentAPI;
import com.cyberway.weixin.util.AccessTokenUtil;
import com.cyberway.weixin.util.CommonUtil;
import com.cyberway.weixin.util.Constants;

public class Test {
	public static void main(String[] args) {
			String access_token = AccessTokenUtil.getAccessToken(Constants.CORPID,Constants.SECRET).getToken();
//			String jostData = DepartmentService.Create("test", "1");
//			String jostData = Update("41", "微信开发者管理");
//			String jostData = DepartmentService.Delete("42");
			
			JSONObject jsonobject = CommonUtil.invoke(access_token, "POST", DepartmentAPI.DEPT_GET_LIST_URL, null);
			if (null != jsonobject) {
				int errcode = jsonobject.getInt("errcode");
				String errmsg = jsonobject.getString("errmsg");
				if (0 != errcode) {
					String error = String.format("操作失败 errcode:{%s} errmsg:{%s}",errcode, errmsg);
					System.out.println(error);
				} else {
					if(jsonobject.containsKey("department")){
						JSONArray jsonarray = jsonobject.getJSONArray("department");
						for(int i=0;i<jsonarray.size();i++){
							JSONObject data = jsonarray.getJSONObject(i);
							int id = data.getInt("id");
							String name = data.getString("name");
							String parentid = data.getString("parentid");
							System.out.println("部门 ID: "+id+" 部门名："+name+" 上级部门ID："+parentid);
						}
					}
					String error = String.format("操作成功 errcode:{%s} errmsg:{%s}",errcode, errmsg);
					System.out.println(error);
				}
		}
	}
}
