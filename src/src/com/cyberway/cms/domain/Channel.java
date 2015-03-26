package com.cyberway.cms.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cyberway.cms.domain.Template;

public class Channel implements Serializable
{ 
	private Long _id;
	private String _name;
	private Channel _parent;
	private CmsSite _site;
	private String _channelPath;
	private String _remark;
	private String _infoSytle;
	private int _ispublished=1;
	private int _isflow=0;
	private int _isInheritPerm=1;
	private Long _isInheritDocPerm=1l;
	private int _ispublicchannel=0;
	private Long isOpenChannel =0L;
	private Long _publicchannelid = 0L;
	private String publicchannel;
	private int _iscopy =0;
	private String _flowname;
	private Template _formTemplate;
	private Template _detailsTemplate;
	private Template _summaryTemplate;
	private Template _detailsTemplateWap;
	
	private Template _summaryTemplateWap;
	private Template _adminSummaryTemplate;
	private int _sortOrder = 0;
	private int _status = 1;
	private Date _timeDeleted;
	private Date _timeCreated = new Date ();
    private Long numberOfClick = 0L;//点击数
    
	private Long _isSearch =1L;//是否需要搜索 1表示需要索引（默认为搜索） 0表示不建
	private List<Channel> _children = new ArrayList<Channel> ();
	
	private String _navigateImg;
	private String _bannerImg;
	private String _descreble;
	
	private Boolean _createhtml;
	private String redirect;//重定向字段

	//private List<Template> _templates = new ArrayList<Template> ();
	
	public List<Channel> getChildren()
	{
		return _children;
	}
	public void setChildren(List<Channel> children)
	{
		_children = children;
	}
	public Long getId()
	{
		return _id;
	}
	public void setId(Long id)
	{
		_id = id;
	}
	public CmsSite getSite() 
	{
		return _site;
	}
	public void setSite(CmsSite site) 
	{
		this._site = site;
	}
	public String getName()
	{
		return _name;
	}
	public void setName(String name)
	{
		_name = name;
	}
	public Channel getParent()
	{
		return _parent;
	}
	public void setParent(Channel parent)
	{
		_parent = parent;
	}
	public Template getFormTemplate()
	{
		return _formTemplate;
	}
	public void setFormTemplate(Template formTemplate)
	{
		_formTemplate = formTemplate;
	}
	public Template getDetailsTemplate()
	{
		return _detailsTemplate;
	}
	public void setDetailsTemplate(Template detailsTemplate)
	{
		_detailsTemplate = detailsTemplate;
	}
	public Template getSummaryTemplate()
	{
		return _summaryTemplate;
	}
	public void setSummaryTemplate(Template summaryTemplate)
	{
		_summaryTemplate = summaryTemplate;
	}
	
	public Template getDetailsTemplateWap()
	{
		return _detailsTemplateWap;
	}
	public void setDetailsTemplateWap(Template detailsTemplateWap)
	{
		_detailsTemplateWap = detailsTemplateWap;
	}
	public Template getSummaryTemplateWap()
	{
		return _summaryTemplateWap;
	}
	public void setSummaryTemplateWap(Template summaryTemplateWap)
	{
		_summaryTemplateWap = summaryTemplateWap;
	}
	
	
	public Template getAdminSummaryTemplate()
	{
		return _adminSummaryTemplate;
	}
	public void setAdminSummaryTemplate(Template adminSummaryTemplate)
	{
		_adminSummaryTemplate = adminSummaryTemplate;
	}
	public Date getTimeCreated()
	{
		return _timeCreated;
	}
	public void setTimeCreated(Date timeCreated)
	{
		_timeCreated = timeCreated;
	}
	public int getSortOrder()
	{
		return _sortOrder;
	}
	public void setSortOrder(int sortOrder)
	{
		_sortOrder = sortOrder;
	}
/*	public List<Template> getTemplates()
	{
		return _templates;
	}
	public void setTemplates(List<Template> templates)
	{
		_templates = templates;
	}*/
	public String getChannelPath() {
		return _channelPath;
	}
	public void setChannelPath(String path) {
		_channelPath = path;
	}
	public String getRemark() {
		return _remark;
	}
	public void setRemark(String remark) {
		_remark = remark;
	}
	public String getFlowname() {
		return _flowname;
	}
	public void setFlowname(String flowname) {
		this._flowname = flowname;
	}
	public int getIsflow() {
		return _isflow;
	}
	public void setIsflow(int isflow) {
		this._isflow = isflow;
	}
	public String getInfoSytle() {
		return _infoSytle;
	}
	public void setInfoSytle(String infomationSytle) {
		this._infoSytle = infomationSytle;
	}
	public int getIspublished() {
		return _ispublished;
	}
	public void setIspublished(int _ispublished) {
		this._ispublished = _ispublished;
	}
	public int getIsInheritPerm() {
		return _isInheritPerm;
	}
	public void setIsInheritPerm(int isInheritPerm) {
		this._isInheritPerm = isInheritPerm;
	}
	public Long getIsInheritDocPerm() {
		return _isInheritDocPerm;
	}
	public void setIsInheritDocPerm(Long _isInheritDocPerm) {
		this._isInheritDocPerm = _isInheritDocPerm;
	}
	
	public int getStatus() {
		return _status;
	}
	public void setStatus(int _status) {
		this._status = _status;
	}
	public Date getTimeDeleted() {
		return _timeDeleted;
	}
	public void setTimeDeleted(Date _timeDeleted) {
		this._timeDeleted = _timeDeleted;
	}
	public Long getNumberOfClick() {
		return numberOfClick;
	}
	public void setNumberOfClick(Long numberOfClick) {
		this.numberOfClick = numberOfClick;
	}
	public int getIspublicchannel() {
		return _ispublicchannel;
	}
	public void setIspublicchannel(int _ispublicchannel) {
		this._ispublicchannel = _ispublicchannel;
	}
	public Long getPublicchannelid() {
		return _publicchannelid;
	}
	public void setPublicchannelid(Long _publicchannelid) {
		this._publicchannelid = _publicchannelid;
	}
	public String getPublicchannel() {
		return publicchannel;
	}
	public void setPublicchannel(String publicchannel) {
		this.publicchannel = publicchannel;
	}
	public int getIscopy() {
		return _iscopy;
	}
	public void setIscopy(int _iscopy) {
		this._iscopy = _iscopy;
	}
	public Long getIsOpenChannel() {
		return isOpenChannel;
	}
	public void setIsOpenChannel(Long isOpenChannel) {
		this.isOpenChannel = isOpenChannel;
	}
	public Long getIsSearch() {
		if(_isSearch==null)
			_isSearch=1L;
		return _isSearch;
	}
	public void setIsSearch(Long search) {
		_isSearch = search;
	}
	public String getBannerImg() {
		return _bannerImg;
	}
	public void setBannerImg(String img) {
		_bannerImg = img;
	}
	public String getDescreble() {
		return _descreble;
	}
	public void setDescreble(String _descreble) {
		this._descreble = _descreble;
	}
	public String getNavigateImg() {
		return _navigateImg;
	}
	public void setNavigateImg(String img) {
		_navigateImg = img;
	}
	
	public Boolean getCreatehtml() {
		return _createhtml;
	}
	public void setCreatehtml(Boolean createhtml) {
		this._createhtml = createhtml;
	}
	public String getRedirect() {
		return redirect;
	}
	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}
	
}
