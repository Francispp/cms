package com.cyberway.common.message.view;

import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.Order;

import com.cyberway.cms.Constants;
import com.cyberway.cms.component.webuser.domain.WebUser;
import com.cyberway.cms.document.service.DocumentCommonService;
import com.cyberway.common.commoninfo.service.CommonInfoService;
import com.cyberway.common.message.domain.Message;
import com.cyberway.common.message.service.MessageManagerService;
import com.cyberway.common.message.utils.Limit;
import com.cyberway.common.user.service.UserManagerService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.web.BaseBizController;

public class MessageController extends BaseBizController<Message>{
	MessageManagerService service = (MessageManagerService) this.getServiceById("messageManagerService");
	UserManagerService userService = (UserManagerService) getServiceById("userManagerService");
	DocumentCommonService	    docService	                = (DocumentCommonService) getServiceById("documentCommonService");
	CommonInfoService ciService=(CommonInfoService)this.getServiceById("commonInfoService");
	private String face;
	private int pageIndex;
	private int pageSize;
	private long docid;
	private boolean deleteMessage;
	private Loginer loginer;
	private String displayContent;
	private String delete_choose;
	private String message_style;
	private int average;

	public String getMessage_style() {
		return message_style;
	}

	public void setMessage_style(String message_style) {
		this.message_style = message_style;
	}

	public String getDelete_choose() {
		return delete_choose;
	}

	public void setDelete_choose(String delete_choose) {
		this.delete_choose = delete_choose;
	}

	public String getDisplayContent() {
		return displayContent;
	}

	public void setDisplayContent(String displayContent) {
		this.displayContent = displayContent;
	}

	public boolean isDeleteMessage() {
		return deleteMessage;
	}

	public void setDeleteMessage(boolean deleteMessage) {
		this.deleteMessage = deleteMessage;
	}

	public long getDocid() {
		return docid;
	}

	public void setDocid(long docid) {
		this.docid = docid;
	}

	@Override
	protected EntityDao getService() {
		return service;
	}

	public String execute() throws Exception {
		domain=getDomainClass().newInstance();
		CriteriaSetup criteria = new CriteriaSetup();
		Page page;
		if(docid!=0){
			criteria.addFilter("docid", docid);
			criteria.setInOrder(Order.desc("createtime"));
			if(getPageIndex() <1)
			{
				setPageIndex(1);
			}
			if(getPageSize()== 0)
			{
				setPageSize(Constants.MESSAGE_PAGESIZE);
			}
			Limit limit = new Limit (getPageIndex(), getPageSize());
			page=service.messagePage(limit, criteria);
			try{
				setAverage(service.getTotalScore(docid)/page.getTotalCount());
			}catch (Exception e) {
				setAverage(5);
			}
		}
		else
		{
			page= new Page();
		}
		this.getHttpServletRequest().setAttribute("_data",page);
		
		return LIST_RESULT;
	}
	
	
	
	@Override
	public String edit() throws Exception {
		super.edit();
		return "info";
	}

	@Override
	public String list() throws Exception {
		CriteriaSetup cs = new CriteriaSetup();
		cs.setInOrder(Order.desc("createtime"));
		doListEntity(cs, getTableId(), Page.DEFAULT_PAGE_SIZE);
		return "adminList";
	}

	public String saveOrUpdate() throws Exception{
		domain.setIpInfo(ServletActionContext.getRequest().getRemoteAddr());
		WebUser webUser = getWebUser();
		domain.setFace(webUser.getName());
		domain.setCreatetime(new Date());
		domain=service.saveOrUpdate(domain);
		return execute();
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public Loginer getLoginer() {
		return (Loginer)getSession().get(Loginer.LOGININFO_SESSION);
	}

	public void setLoginer(Loginer loginer) {
		this.loginer = loginer;
	}

	public int getAverage() {
		return average;
	}

	public void setAverage(int average) {
		this.average = average;
	}
}
