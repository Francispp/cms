package com.cyberway.cms.component.wsr.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.cyberway.cms.Constants;
import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.component.webuser.domain.WebUser;
import com.cyberway.cms.component.webuser.service.WebUserService;
import com.cyberway.cms.component.wsr.domain.Subcreibe;
import com.cyberway.cms.document.service.DocumentCommonService;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.form.object.BaseDocument;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.common.email.domain.CoreEmailLog;
import com.cyberway.common.email.service.EmailLogManagerService;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.utils.ServiceLocator;


/**
 * 网上订阅管理
 * @author hdap
 */
public class SubcreibeService extends HibernateEntityDao<Subcreibe>{
	
	 public boolean checkScByEmailAndChannnelId(String email,Long channelId){
		 boolean flag = false;
		 
		 List list  = this.find("FROM Subcreibe WHERE userEmail=? and channelId=?",new Object []{email,channelId});
		 if(list.size() > 0){
			 flag = true;
		 } 
		 return flag;
	 }
	 
	 /**
	  * 根据定阅的用户获取审核过的定阅记录
	  * @param webUserId
	  * @return
	  */
	 public List<Subcreibe> getRssByUser(Long webUserId)
	 {
		 List<Subcreibe> ls = this.find("FROM Subcreibe WHERE userId=? and approved =?", new Object []{webUserId,new Long(1)});
		 return ls;
	 }
	 /**
	  * RSS订阅创建邮件LOG，初始状态为发送不成功。通过定时任务进行发送。
	  * @param docId
	  */
	 public void checkSub(Long docId) 
	 {
		 ChannelManagerService channelService=(ChannelManagerService) ServiceLocator.getBean("channelManagerService");
			WebUserService webUserService = (WebUserService) ServiceLocator.getBean("WebUserService");
			SiteManagerService siteService=(SiteManagerService) ServiceLocator.getBean("siteManagerService");
			EmailLogManagerService emailLogService = (EmailLogManagerService)ServiceLocator.getBean("EmailLogManagerService");
			
			 BaseDocument document = docId == null ? null : getDocumentCommonService().get(docId);
			 if(document != null)
			 {
			 Long channelId = document.getChannel().getId();
			 Channel channel = channelService.getChannelFromCache(channelId);
				CmsSite site = siteService.getSiteFromCache(channel.getSite().getOid());
				HttpServletRequest request = ServletActionContext.getRequest();
				String siteHttp =site.getSitehttp();
				int siteport = request.getServerPort();
				String docUrl = Constants.CMS_HYPERTEXT_TRANSFER_PROTOCOL + siteHttp + (siteport == 80 ? "" :  ":" + siteport)+"/cms/docInfo!view.action?id="+docId;
				System.out.println(docUrl+"-----------");
		 //Loginer loginer =(Loginer)ActionContext.getContext().getSession().get(com.cyberway.core.Constants.USER_IN_SESSION);
		 List<Subcreibe> subs = this.findBy("channelId", channelId);
		 try{
		 for(Subcreibe sub : subs)
		 {
			 WebUser webUser =webUserService.get(sub.getUserId());
			 if(webUser != null)
			 {
			 CoreEmailLog emailLog = new CoreEmailLog();
			
			/* String body="";   
		      String line = "";   
			 URL url = new URL  (docUrl);   
			 HttpURLConnection urlConnection  = (HttpURLConnection)url.openConnection();   
			 BufferedReader in  = new BufferedReader(   
                    new InputStreamReader(   
                            urlConnection.getInputStream()));   
            while ((line = in.readLine()) != null) {    
           	 body+=line;   
            }   */
			 emailLog.setCreatetime(new Date());
			 emailLog.setContentURL(docUrl);
			 emailLog.setEmailSubject(document.getTitle());
			 emailLog.setTo(webUser.getEmail());
			 emailLog.setStatus(0);
			 emailLogService.saveOrUpdate(emailLog);
			 }
			 
		 }
		 }catch(Exception e){e.printStackTrace();}
			 }
	 }
	 public DocumentCommonService getDocumentCommonService ()
		{
			return (DocumentCommonService)ServiceLocator.getBean("documentCommonService");
		}
}
