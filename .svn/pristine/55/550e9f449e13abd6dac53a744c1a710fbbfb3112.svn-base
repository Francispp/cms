package com.cyberway.weixin.service;

import com.cyberway.weixin.api.API;
import com.cyberway.weixin.api.MemberAPI;

public class MemberService {
	/**
	 * 
	 * @param userid
	 *            员工UserID。对应管理端的帐号，企业内必须唯一。长度为1~64个字符
	 * @param name
	 *            成员名称。长度为1~64个字符
	 * @param position
	 *            职位信息
	 * @param mobile
	 *            手机号码。企业内必须唯一，mobile/weixinid/email三者不能同时为空
	 * @param gender
	 *            性别。gender=0表示男，=1表示女。默认gender=0
	 * @param tel
	 *            办公电话。长度为0~64个字符
	 * @param email
	 *            邮箱。长度为0~64个字符。企业内必须唯一
	 * @param weixinid
	 *            微信号。企业内必须唯一
	 * @return
	 */
	public static String Create(String userid, String name, String position,
			String mobile, String gender, String tel, String email,
			String weixinid) {
		String jsonData = "{\"userid\":\"%s\",\"name\":\"%s\",\"department\": [41],\"position\": \"%s\",\"mobile\": \"%s\",\"gender\": \"%s\",\"tel\": \"%s\",\"email\": \"%s\",\"weixinid\": \"%s\"}";
		return String.format(jsonData, userid, name, position, mobile, gender,
				tel, email, weixinid);
	}

	// 更新成员
	public static String Updata(String userid, String name, String position,
			String mobile, String gender, String tel, String email,
			String weixinid, String enable) {
		String jsonData = "{\"userid\": \"%s\",\"name\": \"%s\",\"department\": [41],\"position\": \"%s\",\"mobile\": \"%s\",\"gender\": \"%s\",\"tel\": \"%s\",\"email\": \"%s\",\"weixinid\": \"%s\",\"enable\": \"%s\"}";
		return String.format(jsonData, userid, name, position, mobile, gender,
				tel, email, weixinid, enable);
	}

	public static String Delete(String userid) {
		String delete_url = MemberAPI.MEMBER_DELETE_URL.replace("ID", userid);
		return delete_url;
	}

	public static String GPerson(String userid) {
		String getperson_url = MemberAPI.MEMBER_GET_PERSON_URL.replace("ID", userid);
		return getperson_url;
	}

	public static String GGroup(String department_id) {
		String getgroup_url = MemberAPI.MEMBER_GET_GROUP_URL.replace("ID", department_id);
		return getgroup_url;
	}
	public static String invite(String userid,String inviteTips){
		String jsonData = "{\"userid\": \"%s\",\"inviteTips\": \"%s\"}";
		return String.format(jsonData,userid,inviteTips);
	}
}
