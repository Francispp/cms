package com.cyberway.weixin.service;

import com.cyberway.weixin.api.API;
import com.cyberway.weixin.api.TagAPI;
/**
 * 
 * @com.cyberway.weixin.service.TagService
 * TODO : 标签处理类
 * @author yanruqian
 * @version 1.0.0
 * @createDate：2015年2月9日 上午10:50:00
 */
public class TagService {

	public static String Create_Tag(String tagname) {
		String PostData = "{\"tagname\": \"%s\"}";
		return String.format(PostData, tagname);
	}

	public static String Updata_Tag(String tagid, String tagname) {
		String PostData = "{\"tagid\": \"%s\",\"tagname\": \"%s\"}";
		return String.format(PostData, tagid, tagname);
	}

	public static String Delete_Tag(String tagid) {
		String delete_url = TagAPI.TAG_DELETE_URL.replace("ID", tagid);
		return delete_url;
	}

	public static String Get_Tag_Person(String tagid) {
		String get_tagperson_url = TagAPI.TAG_GET_PERSON_URL.replace("ID", tagid);
		return get_tagperson_url;
	}

	public static String Add_Tag_Person(String tagid, String userlist) {
		String PostData = "{\"tagid\": \"%s\",\"userlist\":\"%s\"}";
		return String.format(PostData, tagid, userlist);
	}

	public static String Delete_Tag_Person(String tagid, String userlist) {

		String str[] = userlist.split(",");
		StringBuilder sb = new StringBuilder();
		sb.append("{\"tagid\": \"");
		sb.append(tagid);
		sb.append("\",");
		sb.append("\"userlist\": [\"");
		for(int i=0;i<str.length;i++){
			if(i==0){
				sb.append(str[i]);
			}else{
				sb.append("\",\""+str[i]);
			}
		}
		sb.append("\"]}");
		return sb.toString();
//		System.out.println(str[i]);
	}
}
