package com.cyberway.weixin.business.attendance.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.weixin.api.DepartmentAPI;
import com.cyberway.weixin.api.MediaAPI;
import com.cyberway.weixin.api.SendAPI;
import com.cyberway.weixin.business.attendance.domain.Attendance;
import com.cyberway.weixin.entity.AccessToken;
import com.cyberway.weixin.service.MediaService;
import com.cyberway.weixin.util.AccessTokenUtil;
import com.cyberway.weixin.util.CommonUtil;
import com.cyberway.weixin.util.Constants;
import com.cyberway.weixin.util.JSSDKUtil;
import com.cyberway.weixin.util.WXLoginer;
/**
 * 
 * @com.cyberway.weixin.business.attendance.service.AttendanceService
 * TODO :
 * @author yanruqian
 * @version 1.0.0
 * @createDate：2015年2月16日 下午1:56:32
 */
public class AttendanceService extends HibernateEntityDao<Attendance>{
	/**
	 * 
	*  <p>TODO
	* touser 员工ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送 
	* toparty 部门ID列表，多个接收者用‘|’分隔，最多支持100个。当touser为@all时忽略本参数 
	* totag 标签ID列表，多个接收者用‘|’分隔。当touser为@all时忽略本参数 
	* msgtype 消息类型，此时固定为：mpnews 
	* agentid 企业应用的id，整型。可在应用的设置页面查看
	* mpnews 图文消息，一个图文消息支持1到10个图文
	* title 图文消息的标题
	* thumb_media_id 图文消息缩略图的media_id, 可以在上传多媒体文件接口中获得。此处thumb_media_id即上传接口返回的media_id
	* author 图文消息的作者
	* content_source_url 图文消息点击“阅读原文”之后的页面链接 
	* content 图文消息的内容，支持html标签
	* digest 图文消息的描述 
	* show_cover_pic 是否显示封面，1为显示，0为不显示
	* safe 表示是否是保密消息，0表示否，1表示是，默认
	*  </p>
	 * @return
	 */
	public String getMpnewsJsonData(String userId,boolean type){
		StringBuffer sb = new StringBuffer("{");
		sb.append("\"").append("touser").append("\":").append("\"").append(userId).append("\"").append(",");
		sb.append("\"").append("msgtype").append("\":").append("\"mpnews\"").append(",");
		sb.append("\"").append("agentid").append("\":").append("\"5\"").append(",");
		sb.append(getMpnewsData(type)).append(",");
		sb.append("\"").append("safe").append("\":").append("0").append("}");
		System.out.println(sb.toString());
		return sb.toString();
	}
	/**
	 * 
	*  <p>TODO 构造图文消息</p>
	 * @return
	 */
	public String getMpnewsData(boolean type){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		StringBuffer sb = new StringBuffer();
		sb.append("\"").append("mpnews").append("\":").append("{").append("\"").append("articles").append("\"").append(":[{");
		if(type){
			sb.append("\"").append("title").append("\":").append("\"").append("签到提醒").append("\"").append(",");
		}else{
			sb.append("\"").append("title").append("\":").append("\"").append("签退提醒").append("\"").append(",");
		}
		sb.append("\"").append("thumb_media_id").append("\":").append("\"").append(getMediaId()).append("\"").append(",");
//		sb.append("author:").append("").append(",");
//		sb.append("content_source_url:").append("").append(",");
		sb.append("\"").append("content").append("\":").append("\"").append(sdf.format(date)).append("\"");
//		sb.append("digest:").append("").append(",");
//		sb.append("show_cover_pic:").append("").append(",");
		sb.append("}").append("]").append("}");
		return sb.toString();
	}
	/**
	 * 
	*  <p>TODO 获取上传的多媒体ID</p>
	 * @return
	 */
	public String getMediaId(){
		String uploadMediaUrl = MediaAPI.MEDIA_UPLOAD_URL.replace("TYPE", "image");
		JSONObject jsonobject = AccessTokenUtil.requestData(uploadMediaUrl, "POST", "F:\\lyf.jpg", "0");
		String media_id = "";
		if(jsonobject.containsKey("media_id")){
			media_id = jsonobject.getString("media_id");
			System.out.println("上传成功: " + jsonobject);
		}else{
			System.out.println("上传失败: " + jsonobject);
		}
		
		return media_id;
	}
	public void sendMpnews(String userId,boolean type){
		JSONObject jsonobject = AccessTokenUtil.requestData(SendAPI.SEND_MESSAGE_URL, "POST", getMpnewsJsonData(userId,type), "1");
		Integer errcode= jsonobject.getInt("errcode");
		if(errcode==0){
			System.out.println("发送成功："+jsonobject);
		}else{
			System.out.println("发送失败："+jsonobject);
			
		}
	}
	
	public Attendance getCurrentRecord() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Attendance.class);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date today = cal.getTime();
		cal.add(Calendar.DATE, 1);
		Date tomorrow = cal.getTime();
		criteria.add(Restrictions.ge("signinTime", today));
		criteria.add(Restrictions.lt("signinTime", tomorrow));
		List<Attendance> list = getHibernateTemplate().findByCriteria(criteria);
		return list.size()>0?list.get(0):null;
	}
}
