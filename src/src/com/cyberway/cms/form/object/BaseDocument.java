package com.cyberway.cms.form.object;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;

import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.core.objects.CoreFlow;

/**
 * @author caiw
 *
 *  信息表单基类，供信息pojo对象继承
 *  
 */
public class BaseDocument implements Serializable{
	public static String[] DOCUMENT_STATE={"草稿","正审","待发","已否","取消发布","已发"};
	public static String[] DOCUMENT_YES_NO={"否","是"};
	public static String[] DOCUMENT_TYPE={"所有","WEB","WAP"};
	protected Long id; //主健
	protected String title;//标题	
	protected int issued;//文档的状态，0草稿，1正审，2待发，3已否，4已返，5已发
	protected Date timeCreated = new Date ();//创建日期
	protected Date timeIssued ;//发布时间
	protected int status = 1;//文档删除状态 1为正常，0为删除, -1为过期
	protected Date timeDeleted;//删除时间
	protected Channel channel;//所属频道
	protected CmsSite site;//所属站点
	private Long authorId;//文档创建者id
	private String authorCname;//文档创建者名
	protected Date timeLastUpdated ;//最后修改时间
	private Long isHeadline;
	
	protected Long docType;//0、适用于 WEB跟WAP，1、只适用于WEB，2、只适用于WAP 
	private Long orderNumber;
	
	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	protected Long toped;
	protected Long newsed;
	protected Long firstpage;
	
	protected CoreFlow flowinfo=new CoreFlow();//对应流程
	
		
	/**
	 * 取得当前文档状态
	 * @return
	 */
	public String getStateName() {
		return DOCUMENT_STATE[issued];//取得文档状态
	}
	
	/**
	 * 取得当前文档类型
	 * @return
	 */
	public String getTypeName() {
		return DOCUMENT_TYPE[docType == null ? 0 : docType.intValue()];//取得文档类型
	}
	
	/**
	 * 取得最是否首页
	 * @return
	 */
	public String getFirstpageName() {
		return DOCUMENT_YES_NO[firstpage == null ? 0 : firstpage.intValue()];//取得文档状态
	}
	
	/**
	 * 取得是否最新文档
	 * @return
	 */
	public String getNewsedName() {
		return DOCUMENT_YES_NO[newsed == null ? 0 : newsed.intValue()];//取得文档状态
	}
	
	/**
	 * 取得当前是否置顶
	 * @return
	 */
	public String getTopedName() {
		return DOCUMENT_YES_NO[toped == null ? 0 : toped.intValue()];//取得文档状态
	}
	
	
	public void setStateName(String statename){
		
	}

	@Field(index=Index.UN_TOKENIZED)
	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	} 
	
	@Field(index=Index.UN_TOKENIZED)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Field(index=Index.UN_TOKENIZED)
	public int getIssued() {
		return issued;
	}

	public void setIssued(int issued) {
		this.issued = issued;
	}

	@Field(index=Index.UN_TOKENIZED)
	public Long getDocType() {
		return docType;
	}

	public void setDocType(Long docType) {
		this.docType = docType;
	}
	
	@Field(index=Index.UN_TOKENIZED)
	public Date getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
	}

	@Field(index=Index.TOKENIZED)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Field(index=Index.UN_TOKENIZED)
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Field(index=Index.UN_TOKENIZED)
	public Date getTimeDeleted() {
		return timeDeleted;
	}

	public void setTimeDeleted(Date timeDeleted) {
		this.timeDeleted = timeDeleted;
	}

	@Field(index=Index.UN_TOKENIZED)
	public Date getTimeIssued() {
		return timeIssued;
	}

	public void setTimeIssued(Date timeIssued) {
		this.timeIssued = timeIssued;
	}
	
	@Field(index=Index.UN_TOKENIZED)
	public CmsSite getSite() {
		return site;
	}
	public void setSite(CmsSite site) {
		this.site = site;
	}
	public CoreFlow getFlowinfo() {
		return flowinfo;
	}
	public void setFlowinfo(CoreFlow flowinfo) {
		this.flowinfo = flowinfo;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}
	@Field(index=Index.UN_TOKENIZED)
	public String getAuthorCname() {
		return authorCname;
	}	
	public void setAuthorCname(String authorCname) {
		this.authorCname = authorCname;
	}
	@Field(index=Index.UN_TOKENIZED)
	public Date getTimeLastUpdated() {
		return timeLastUpdated;
	}

	public void setTimeLastUpdated(Date timeLastUpdated) {
		this.timeLastUpdated = timeLastUpdated;
	}

	public Long getIsHeadline() {
		return isHeadline;
	}

	public void setIsHeadline(Long isHeadline) {
		this.isHeadline = isHeadline;
	}

	public Long getFirstpage() {
		return firstpage;
	}

	public void setFirstpage(Long firstpage) {
		this.firstpage = firstpage;
	}

	public Long getNewsed() {
		return newsed;
	}

	public void setNewsed(Long newsed) {
		this.newsed = newsed;
	}

	public Long getToped() {
		return toped;
	}

	public void setToped(Long toped) {
		this.toped = toped;
	}

	

	
	
	
}
