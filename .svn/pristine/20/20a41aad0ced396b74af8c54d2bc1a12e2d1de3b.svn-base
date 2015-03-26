package com.cyberway.weixin.service;

import com.cyberway.weixin.api.API;
import com.cyberway.weixin.api.DepartmentAPI;

/**
 * 
 * @com.cyberway.weixin.department.FormatData
 * TODO : 部门处理类
 * @author yanruqian
 * @version 1.0.0
 * @createDate：2015-1-27 上午10:17:35
 */
public class DepartmentService {
	/**
	 * 
	*  <p>TODO 创建部门</p>
	 * @param name
	 * @param parentid
	 * @return
	 */
	public static String Create(String name, String parentid) {
		String Postjson = "{\"name\": \"%s\",\"parentid\": \"%s\"}";
		return String.format(Postjson, name, parentid);
	}
	/**
	 * 
	*  <p>TODO 删除部门</p>
	 * @param id
	 * @return
	 */
	public static String Delete(String id) {
		String delete_url = DepartmentAPI.DEPT_DELETE_URL.replace("ID", id);
		return delete_url;
	}
	/**
	 * 修改部门
	*  <p>TODO (这里用一句话描述这个方法的作用)</p>
	 * @param name
	 * @param id
	 * @return
	 */
	public static String Update(String name, String id) {
		String Postjson = "{\"id\":\" %s\",\"name\": \"%s\"}";
		return String.format(Postjson, name, id);
	}
}
