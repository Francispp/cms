package com.cyberway.cms.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.cyberway.common.domain.CoreOrg;

/** @author Hibernate CodeGenerator */
public class CmsSite implements Serializable
{

	/** identifier field */
	private Long oid;

	/** nullable persistent field */
	private String sitename;

	/** nullable persistent field */
	private String sitehttp;

	/** nullable persistent field */
	private Integer siteport;

	/** nullable persistent field */
	private String resourcepath;

	/** nullable persistent field */
	private Integer ispublished;

	/** nullable persistent field */
	private int ishtml;

	/** nullable persistent field */
	private int isdynamic;
	
	/** nullable persistent field */
	private String manager;

	/** nullable persistent field */
	private String remark;

	/** nullable persistent field */
	private java.util.Date createtime;

	/** nullable persistent field */
	private java.lang.Long createuserid;

	/** nullable persistent field */
	private String createusername;
	
    private Long numberOfClick = 0L;//点击数
	
	private int status = 1;
	
	private Date timeDeleted;
	
	private Template _template = null;

	private Template _templateWap = null;
	
	private List<Channel> _channels = new ArrayList();
	
	//private List<Template> _templates = new ArrayList<Template> ();
    //20081104增加可配置站点是否需登录
	/** nullable persistent field */
	private Integer isLogined=0;
	
	/** nullable persistent field */
	private String loginUrl;	
	
	private CoreOrg coreOrg;

	private Boolean createhtml;
	
	private String domainNames;//扩展多个域名
	
	/**
	 * 暂时屏蔽访问
	 */
	private Boolean pingBiFangWen;
	
	/**
	 * 屏蔽访问时显示的页面地址
	 */
	private String pingBiDiZhi;
	/**
	 * 是否支持WAP true 支持WAP false 不支持
	 */
	private Boolean isSustainWap;
	
	/**
	 * 与site站点保持一致
	 */
	private Long portal_id=1L;
	
	public Boolean getIsSustainWap() {
		return isSustainWap;
	}

	public void setIsSustainWap(Boolean isSustainWap) {
		this.isSustainWap = isSustainWap;
	}

	/** full constructor */
	public CmsSite(String sitename, String sitehttp, Integer siteport,
			String resourcepath, Integer ispublished, String manager,
			String remark)
	{
		this.sitename = sitename;
		this.sitehttp = sitehttp;
		this.siteport = siteport;
		this.resourcepath = resourcepath;
		this.ispublished = ispublished;
		this.manager = manager;
		this.remark = remark;
	}

	/** default constructor */
	public CmsSite()
	{
	}

	public Long getOid()
	{
		return this.oid;
	}

	public void setOid(Long oid)
	{
		this.oid = oid;
	}

	public String getSitename()
	{
		return this.sitename;
	}

	public void setSitename(String sitename)
	{
		this.sitename = sitename;
	}

	public String getSitehttp()
	{
		return this.sitehttp;
	}

	public String getDomainNames() {
		return domainNames;
	}

	public void setDomainNames(String domainNames) {
		this.domainNames = domainNames;
	}

	public void setSitehttp(String sitehttp)
	{
		this.sitehttp = sitehttp;
	}

	public Integer getSiteport()
	{
		return this.siteport;
	}

	public void setSiteport(Integer siteport)
	{
		this.siteport = siteport;
	}

	public String getResourcepath()
	{
		return this.resourcepath;
	}

	public void setResourcepath(String resourcepath)
	{
		this.resourcepath = resourcepath;
	}

	public Integer getIspublished()
	{
		return this.ispublished;
	}

	public void setIspublished(Integer ispublished)
	{
		this.ispublished = ispublished;
	}

	public String getManager()
	{
		return this.manager;
	}

	public void setManager(String manager)
	{
		this.manager = manager;
	}

	public String getRemark()
	{
		return this.remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this).append("oid", getOid()).toString();
	}

	public java.util.Date getCreatetime()
	{
		return createtime;
	}

	public void setCreatetime(java.util.Date createtime)
	{
		this.createtime = createtime;
	}

	public java.lang.Long getCreateuserid()
	{
		return createuserid;
	}

	public void setCreateuserid(java.lang.Long createuserid)
	{
		this.createuserid = createuserid;
	}

	public String getCreateusername()
	{
		return createusername;
	}

	public void setCreateusername(String createusername)
	{
		this.createusername = createusername;
	}

	public List<Channel> getChannels()
	{
		return _channels;
	}


	public void setChannels(List<Channel> channels)
	{
		_channels = channels;
	}
	
/*	public List<Template> getTemplates()
	{
		return _templates;
	}

	public void setTemplates(List<Template> templates)
	{
		_templates = templates;
	}*/

	public Template getTemplate()
	{
		return _template;
	}

	public void setTemplate(Template template)
	{
		_template = template;
	}
	
	public Template getTemplateWap()
	{
		return _templateWap;
	}

	public void setTemplateWap(Template templateWap)
	{
		_templateWap = templateWap;
	}

	public int getIshtml() {
		return ishtml;
	}

	public void setIshtml(int ishtml) {
		this.ishtml = ishtml;
	}

	public int getIsdynamic() {
		return isdynamic;
	}

	public void setIsdynamic(int isdynamic) {
		this.isdynamic = isdynamic;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getTimeDeleted() {
		return timeDeleted;
	}
	public void setTimeDeleted(Date timeDeleted) {
		this.timeDeleted = timeDeleted;
	}

	public Long getNumberOfClick() {
		return numberOfClick;
	}

	public void setNumberOfClick(Long numberOfClick) {
		this.numberOfClick = numberOfClick;
	}

	public Integer getIsLogined() {
		if(isLogined==null)
			isLogined=0;
		return isLogined;
	}

	public void setIsLogined(Integer isLogined) {
		this.isLogined = isLogined;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public CoreOrg getCoreOrg() {
		return coreOrg;
	}

	public void setCoreOrg(CoreOrg coreOrg) {
		this.coreOrg = coreOrg;
	}

	public Boolean getCreatehtml() {
		return createhtml;
	}

	public void setCreatehtml(Boolean createhtml) {
		this.createhtml = createhtml;
	}

	public Boolean getPingBiFangWen() {
    	return pingBiFangWen;
    }

	public void setPingBiFangWen(Boolean pingBiFangWen) {
    	this.pingBiFangWen = pingBiFangWen;
    }

	public String getPingBiDiZhi() {
    	return pingBiDiZhi;
    }

	public void setPingBiDiZhi(String pingBiDiZhi) {
    	this.pingBiDiZhi = pingBiDiZhi;
    }

	public Long getPortal_id() {
		return portal_id;
	}

	public void setPortal_id(Long portal_id) {
		this.portal_id = portal_id;
	}

}
