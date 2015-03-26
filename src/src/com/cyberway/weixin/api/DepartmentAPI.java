package com.cyberway.weixin.api;
/**
 * 
 * @com.cyberway.weixin.api.DepartmentAPI
 * TODO : 部门接口
 * @author yanruqian
 * @version 1.0.0
 * @createDate：2015年2月9日 上午10:45:53
 */
public class DepartmentAPI {

	/**
	 *  创建部门地址
	 */
	public static String DEPT_CREATE_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=ACCESS_TOKEN";
	/**
	 *  更新部门地址
	 */
	public static String DEPT_UPDATE_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token=ACCESS_TOKEN";
	/**
	 *  删除部门地址
	 */
	public static String DEPT_DELETE_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token=ACCESS_TOKEN&id=ID";
	/**
	 * 获取部门列表地址
	 */
	public static String DEPT_GET_LIST_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN";

}
