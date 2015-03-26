package com.cyberway.cms.channel.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import ognl.Ognl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.dom.DOMDocumentFactory;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.domain.Template;
import com.cyberway.cms.form.domain.CoreForm;
import com.cyberway.cms.permission.service.CmsPermissionService;
import com.cyberway.cms.site.cache.SiteCache;
import com.cyberway.cms.template.service.TemplateManagerService;
import com.cyberway.cms.webservice.service.HtmlSynchroismService;
import com.cyberway.common.base.objects.Constants;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.objects.UserFrame;
import com.cyberway.core.utils.BeanUtil;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;

public class ChannelManagerService extends HibernateEntityDao<Channel>
{
	private SiteCache siteCache;
	private TemplateManagerService templateManagerService;
	/**
	 * 初始化站点
	 */
	public synchronized void init(){
		List<Channel> channels=getAll();
		if(!channels.isEmpty()){			
			for(Channel channel:channels){
				siteCache.putChannelInCache(channel);
			}
		}
	}	
	
	public Channel getById (Long id)
	{
		return super.get(id);
	}
	
	/**
	 * 返回指定频道的默认模板id(所有获得默认模板的地方，都需经过此方法)
	 * @param channelid
	 * @param type 模板类型
	 * @return
	 */
	public Long getDefualutTemplateId(Long channelid,Integer type)throws Exception{
		Long tempid=null;
		Channel channel=getChannelFromCache(channelid);
		if(channel==null)
			throw new Exception("指定的频道不存在！");
		if(channel!=null && channel.getIscopy()==2){//频道设置引用公共模板
			if(channel.getPublicchannelid()!=null){
			  channel=getChannelFromCache(channel.getPublicchannelid());
			  if(channel!=null && channel.getIspublicchannel()!=1)
				  throw new Exception("引用的公用频道不存在！");
			}else
			  channel=null;
		}
		if(channel!=null && type!=null){	//取频道对应的默认模板				
			if(type.intValue()==1 && channel.getFormTemplate()!=null){//表单模板
				tempid=channel.getFormTemplate().getId();			
			}else if(type.intValue()==2 && channel.getDetailsTemplate()!=null){//细览模板
				tempid=channel.getDetailsTemplate().getId();	
			}else if(type.intValue()==3 && channel.getSummaryTemplate()!=null){//概览模板
				tempid=channel.getSummaryTemplate().getId();
			}else if(type.intValue()==4 && channel.getAdminSummaryTemplate()!=null){//后台概览模板
				tempid=channel.getAdminSummaryTemplate().getId();
			}else if(type.intValue()==9 && channel.getDetailsTemplateWap()!=null){//细览模板wap
				tempid=channel.getDetailsTemplateWap().getId();	
			}else if(type.intValue()==8 && channel.getSummaryTemplateWap()!=null){//概览模板wap
				tempid=channel.getSummaryTemplateWap().getId();
			}			
			}		
		return tempid;
	}
	/**
	 * 设置默认模板
	 * @param channelid
	 * @param templateid
	 * @param type
	 * @return
	 */
	public boolean setDefaultTemplate(String channelid,String templateid,Integer type)throws Exception{
		if(StringUtil.isEmpty(channelid)){
			logger.warn("频道id为空！");
			return false;
		}
		Template defaultTemp=null;
		Channel channel=get(new Long(channelid));
		if(!StringUtil.isEmpty(templateid) && StringUtil.isNumber(templateid))
			defaultTemp=(Template)get(Template.class, new Long(templateid));
		if(type==null)
			return false;
		String typename=null;
		if(type.intValue()==1){//表单模板
			typename="formTemplate";
		}else if(type.intValue()==2){//细览模板
			typename="detailsTemplate";
		}else if(type.intValue()==3){//概览模板
			typename="summaryTemplate";
		}else if(type.intValue()==4){//后台概览模板
			typename="adminSummaryTemplate";
		}else if(type.intValue()==9){//细览模板Wap
			typename="detailsTemplateWap";
		}else if(type.intValue()==8){//概览模板Wap
			typename="summaryTemplateWap";
		}
		if(typename!=null){
		 Ognl.setValue(typename, channel, defaultTemp);
		 save(channel);
		 siteCache.putChannelInCache(channel);		 
		 return true;
		}else
		 return false;
	}
	/**
	 * 移动菜单
	 * @param mid
	 * @param pmid
	 * @param portalid
	 * @return
	 */
	public boolean setChannelPM(String mid,String pmid,String siteid,UserFrame loginer){
		if(StringUtil.isEmpty(mid)){
			return false;
		}
		Long channelid=new Long(mid);
		//增加权限过滤　amway
		//Loginer loginer =(Loginer)ActionContext.getContext().getSession().get(com.cyberway.core.Constants.USER_IN_SESSION);
		CmsPermissionService permService=(CmsPermissionService)ServiceLocator.getBean("cmsPermissionService");
		if(!permService.haveThePermission((Loginer)loginer, "CMS_CHANNEL_MODI",com.cyberway.cms.Constants.CHANNEL_TYPE, channelid)){
			return false;
		}		
		Channel cm=this.get(new Long(mid));
		if(!StringUtil.isEmpty(pmid)){//设置父菜id,且同时设置siteid			
			Channel pcm=this.get(new Long(pmid));
			cm.setParent(pcm);
			cm.setSite(pcm.getSite());
		}else{//父菜单id为空，重新设置siteid
			if(!StringUtil.isEmpty(siteid)){
			try{
			 cm.setParent(null);
			 cm.setSite(new CmsSite());
			 Ognl.setValue("site.oid",cm,new Long(siteid));	
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage());
				return false;
			}
			}else
				return false;
		}
		this.save(cm);
		return true;
	}
	/**
	 * 拷贝菜单到..下
	 * @param mid
	 * @param pmid
	 * @param portalid
	 * @return
	 * @throws Exception
	 */
	public boolean copyChannelTo(String mid,String pmid,String siteid,UserFrame loginer)throws Exception{
		if(StringUtil.isEmpty(mid)){
			return false;
		}
		Long channelid=new Long(mid);
		//增加权限过滤　amway
		//Loginer loginer =(Loginer)ActionContext.getContext().getSession().get(com.cyberway.core.Constants.USER_IN_SESSION);
		CmsPermissionService permService=(CmsPermissionService)ServiceLocator.getBean("cmsPermissionService");
			
		Channel channel=this.get(new Long(mid));
		CmsSite cmsSite = null;
		Long pid=null;
		if(!StringUtil.isEmpty(pmid)){//设置父菜id
			pid=new Long(pmid);
         //增加权限过滤　amway
			if(!permService.haveThePermission((Loginer)loginer, "CMS_CHANNEL_ADD",com.cyberway.cms.Constants.CHANNEL_TYPE, pid)){
				return false;
			}			
			Channel pChannel=this.get(pid);
			cmsSite = pChannel.getSite();
			//if(pChannel != null)
			//channel.setParent(pChannel);
		}else{
			if(!StringUtil.isEmpty(siteid)){
		         //增加权限过滤　amway
				if(!permService.haveThePermission((Loginer)loginer, "CMS_CHANNEL_ADD",com.cyberway.cms.Constants.SITE_TYPE, new Long(siteid))){
					return false;
				}
				try{
					cmsSite=new CmsSite();
					cmsSite.setOid(new Long(siteid));	
				}catch(Exception e){
					e.printStackTrace();
					logger.error(e.getMessage());
					return false;
				}
				}else
					return false;
		}
		boolean succ=false;
        //递归复制
		succ=copyChannels(channel,pid,cmsSite);
		return true;
	}
	
	/**
	 *  更新频道模板
	 * @param channel
	 * @param publicchannelid
	 * @return
	 */
	public Channel updateChannelForm(Channel channel,Long publicchannelid)
	{
		if(channel.getParent().getId()==null)
			channel.setParent(null);
		    channel.setAdminSummaryTemplate(null);
		    channel.setDetailsTemplate(null);
		    channel.setFormTemplate(null);
		    channel.setSummaryTemplate(null);
		    channel = saveOrUpdate(channel);
		    channel = insertForm(channel, publicchannelid);
			if(channel != null)
			{
				channel = saveOrUpdate(channel);
			}
			return channel;
	}
	
	
	/**
	 * @author lan
	 * 复制公共模板
	 * @param domain
	 * @param publicchannelid
	 * @return
	 */
	public Channel insertForm(Channel domain,Long publicchannelid)
	{
		Channel publicChannel = this.get(publicchannelid);
		Template uniqueTemplate = null;
		 //-------------自动创建表单模版----------------
		Template formTemplate = publicChannel.getFormTemplate();
		if(formTemplate != null)
		{
			this.evit(formTemplate);
			formTemplate.setId(null);
			formTemplate.setChannel_id(domain.getId());
			formTemplate.setName(domain.getChannelPath()+"_form");
		uniqueTemplate = templateManagerService.findUniqueBy("name", domain.getChannelPath()+"_form");
		if(uniqueTemplate != null)
		{
			templateManagerService.remove(uniqueTemplate);
		}
		formTemplate = templateManagerService.saveOrUpdate(formTemplate);
		domain.setFormTemplate(formTemplate);
		}
         //-------------自动创建概览模版----------------
		Template summaryTemplate = publicChannel.getSummaryTemplate();
		 if(summaryTemplate != null)
		{   this.evit(summaryTemplate);
			 summaryTemplate.setId(null);
			 summaryTemplate.setChannel_id(domain.getId());
			 summaryTemplate.setName(domain.getChannelPath()+"_summary");
		 uniqueTemplate = templateManagerService.findUniqueBy("name", domain.getChannelPath()+"_summary");
		 if(uniqueTemplate != null)
		  {
			 templateManagerService.remove(uniqueTemplate);
		  }
		 summaryTemplate = templateManagerService.saveOrUpdate(summaryTemplate);
		 domain.setSummaryTemplate(summaryTemplate);
		}
         //-------------自动创建细览模版----------------
		 Template detailsTemplate = publicChannel.getDetailsTemplate();
		 if(detailsTemplate != null)
		{
			 this.evit(detailsTemplate);
			 detailsTemplate.setId(null);
			 detailsTemplate.setChannel_id(domain.getId());
			 detailsTemplate.setName(domain.getChannelPath()+"_detail");
		 uniqueTemplate = templateManagerService.findUniqueBy("name", domain.getChannelPath()+"_detail");
		 if(uniqueTemplate != null)
		  {
			 templateManagerService.remove(uniqueTemplate);
		  }
		 detailsTemplate = templateManagerService.saveOrUpdate(detailsTemplate);
		 domain.setDetailsTemplate(detailsTemplate);
		}
		  //-------------自动创建后台模版----------------
		 Template adminTemplate = publicChannel.getAdminSummaryTemplate();
		 if(adminTemplate != null)
		{
			 this.evit(adminTemplate);
			 adminTemplate.setId(null);
			 adminTemplate.setChannel_id(domain.getId());
			 adminTemplate.setName(domain.getChannelPath()+"_admin");
		 uniqueTemplate = templateManagerService.findUniqueBy("name", domain.getChannelPath()+"_admin");
		 if(uniqueTemplate != null)
		  {
			 templateManagerService.remove(uniqueTemplate);
		  }
		 adminTemplate = templateManagerService.saveOrUpdate(adminTemplate);
		domain.setAdminSummaryTemplate(adminTemplate);
		}
		 if(domain.getParent()==null ||domain.getParent().getId()== null)
		 {
			 domain.setParent(null);
		 }
		return domain;
	}
	private boolean copyChannels(Channel channel,Long pid,CmsSite cmsSite)throws Exception{
		if(channel==null)
		{
			return false;
		}
		Channel new_channel=new Channel();
		BeanUtil.updateObject(channel,new_channel);
		new_channel.setId(null);
		new_channel.setSite(cmsSite);
		if(pid!=null)
		 new_channel.setParent(this.get(pid));
		new_channel.setChannelPath(new_channel.getChannelPath()+System.currentTimeMillis());
		new_channel.setAdminSummaryTemplate(null);
		new_channel.setFormTemplate(null);
		new_channel.setDetailsTemplate(null);
		new_channel.setSummaryTemplate(null);
       //若复制到相同节点下，增加标志位
	     //if(channel.getId()!=null &&pid!=null && channel.getParent().getId().longValue()==pid.longValue())
	    if(channel.getId()!=null &&pid!=null)
	     {
			  String rname=channel.getName();
			  if(!StringUtil.isEmpty(rname))
				rname+=Constants.COPY_OBJECT_NAME_ADD_SIGN;
			  else
				rname=Constants.COPY_OBJECT_NAME_ADD_SIGN;
			  new_channel.setName(rname);//增加复制时的标志	
		     }		
		
		this.saveOrUpdate(new_channel);
		List childrens = channel.getChildren();
		if(childrens!=null){
			Iterator it=childrens.iterator();
			while(it.hasNext()){
				Channel temp=(Channel)it.next();
				copyChannels(temp,new_channel.getId(),cmsSite);
			}		
		}		
		return true;
	}
	
	/**
	 * 获得指定站点下所有频道
	 * @param siteid
	 * @return
	 */
	public List<Channel> getChannelsBySite(Long siteid){
		List<Channel> channels=siteCache.getAllChannels();
		if(channels.isEmpty())
			init();
		List site_chs=new ArrayList();
		for(Channel chn:channels){
			if(chn.getSite().getOid().intValue()==siteid.intValue())
				site_chs.add(chn);
		}
		return site_chs;
	}
	/**
	 * 获得一级频道
	 * @param siteid
	 * @return
	 */
	public  List<Channel>  getFirstChannelsBySite(Long siteid){
		List<Channel> channels=siteCache.getAllChannels();
		if(channels.isEmpty())
			init();
		List site_chs=new ArrayList();
		for(Channel chn:channels){
			if(chn.getSite().getOid().intValue()==siteid.intValue()&&(chn.getParent()==null||chn.getParent().getId()==null))
				site_chs.add(chn);
		}
		return site_chs;
	}
	
	/**
	 * 通过父频道ID 获取同一站点下面的一级子频道
	 * @param siteid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  List<Channel>  findByParentInSameSite(Long siteid, Long parentId){
		List<Channel> channels=siteCache.getAllChannels();
		if(channels.isEmpty())
			init();
		List site_chs=new ArrayList();
		for(Channel chn:channels){
			if(chn.getSite().getOid().intValue()==siteid.intValue()&&chn.getParent()!=null&&chn.getParent().getId().equals(parentId))
				site_chs.add(chn);
		}
		return site_chs;
	}
	
	/**
	 *通过父频道ID 获取下面的一级子频道
	 */
	public List<Channel> findByParent(Long parentId)
	{
    	 List<Channel> list  = findByParent(parentId,true);
    	 return list;
	}
	
	/**
	 *通过父频道ID 获取下面的一级子频道
	 */
	@SuppressWarnings("unchecked")
	public List<Channel> findByParent(Long parentId,boolean ispulished)
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Channel.class);
        Channel pchl=null;
        if(parentId!=null)
        	pchl=getChannelFromCache(parentId);
		criteria.add(Restrictions.eq("parent", pchl));
		if(ispulished){
			criteria.add(Restrictions.eq("ispublished", 1));
		}
		criteria.addOrder(Order.asc("sortOrder"));
    	return (List<Channel>)getHibernateTemplate().findByCriteria(criteria);
	}
	
	/**
	 * 用于模板中DWR取频道、解决重名方法DWR识别不了的问题
	 * @return
	 */
	public List<Channel> findByParentDwr(Long parentId){
		return findByParent(parentId);
	}
	
	
	/** 
	 *通过父频道ID 获取下面的一级子频道 并填充子频道信息
	 * @param parentId
	 * @return
	 */
	public  List<Channel>  getChildChannelsByParent(Long parentId){ 
		return getChildChannelsByParent(parentId,true);
	}
	
	/** 
	 *通过父频道ID 获取下面的一级子频道 并填充子频道信息
	 * @param parentId 低级频道ID
	 * @param ispulished 是否发布
	 * @return
	 */
	public  List<Channel>  getChildChannelsByParent(Long parentId,boolean ispulished){
		List<Channel> channels= siteCache.getAllChannels();
		if(channels.isEmpty())
			init();
		List<Channel> site_chs=new ArrayList<Channel>();
		for(Channel chn:channels){
			if(chn.getParent() != null && chn.getParent().getId().equals(parentId)){
				 if(!ispulished){
					 site_chs.add(chn);
					 // 填充子频道信息
					 chn.setChildren(getChildIntoChannel(channels,chn,ispulished));					 
				 }else if(ispulished && chn.getIspublished() > 0){
					 site_chs.add(chn);
					 // 填充子频道信息
					 chn.setChildren(getChildIntoChannel(channels,chn,ispulished));					 
				 } 
			}
		}
		return site_chs;
	}
 
	
	/**
	 * 获取 childChannels
	 * @param allchannels 缓存中的所有频道
	 * @param channel 要获取子频道信息的频道
	 * @return
	 */ 
	private List<Channel> getChildIntoChannel(List<Channel> allchannels,Channel channel,boolean ispulished)
	{ 
		List<Channel> childs = new ArrayList<Channel>();
		if(channel != null)
		{
			for(Channel chn:allchannels){
				if(chn.getParent() != null && chn.getParent().getId().equals(channel.getId())){
					if(!ispulished || (ispulished && chn.getIspublished() > 0)){
						//把 chn 添加到集合 给上级做子频道
						childs.add(chn);
						//获取 chn 的子频道
						chn.setChildren(getChildIntoChannel(allchannels,chn,ispulished)); 
					}
				}
			} 
		}
		return childs;
	}
	
	
	/**
	 * 从缓冲中获得频道对象
	 * @param channelid
	 * @return
	 */
	public Channel getChannelFromCache(Long channelid){
		Channel channel=siteCache.getChannelFromCach(channelid);
		if(channel==null){//若为空，则检测数据库中是否存在此对象
			channel=this.get(channelid);
			if(channel!=null)
				siteCache.putChannelInCache(channel);
		}
		return channel;
	}
	/**
	 * 根据频道地址，获得频道
	 * @param siteid
	 * @param path
	 * @return
	 */
	public Channel getChannelByPath(Long siteid,String path){
		Channel channel=null;
		List<Channel> channels=siteCache.getAllChannels();	
		if(channels.isEmpty()){
			init();
			channels=siteCache.getAllChannels();
		}
		
		for(Channel chn:channels){
			if(chn.getSite().getOid().intValue()==siteid.intValue() 
					&& chn.getChannelPath()!=null && path.equalsIgnoreCase(chn.getChannelPath())){
				channel=chn;
				break;
			}
		}
		return channel;
	}
	
	/**
	 * 根据频道地址，获得频道
	 * @param siteid
	 * @param path
	 * @return
	 */
	public Channel getChannelBySitehttpAndPath(String sitehttp,String path){
		Channel channel=null;
		List<Channel> channels=siteCache.getAllChannels();	
		if(channels.isEmpty()){
			init();
			channels=siteCache.getAllChannels();
		}
		
		for(Channel chn:channels){ 
			if(chn.getSite()!= null && sitehttp.equalsIgnoreCase(chn.getSite().getSitehttp())
					&& chn.getChannelPath()!=null && path.equalsIgnoreCase(chn.getChannelPath())){
				channel=chn;
				break;
			}
		}
		return channel;
		
	}
	
	/**
	 * 查找current的上N级目录，且是top的下一级目录
	 * @param currentId
	 * @param topId 的下一级Channel
	 * @return
	 */
	public Channel getTopChannel(Long currentId, Long topId){
		Channel temp = siteCache.getChannelFromCach(currentId);
		if(temp.getParent()!=null && temp.getParent().getId().equals(topId)){
			return temp;
		}else if(temp.getParent()!=null){
			return getTopChannel(temp.getParent().getId(), topId);
		}
		return null;
	}
	
	/**
	 * 删除指定频道,同时
	 * @param cid
	 * @return
	 */
	public boolean deleteChannel(String cid){
		this.removeById(Long.valueOf(cid));
		//getChannelCacheKey
		Channel ch=siteCache.getChannelFromCach(Long.valueOf(cid));
		if(ch!=null){			
		 siteCache.removeChannelFromCache(siteCache.getChannelCacheKey(ch.getSite().getOid().toString(), cid));
		}
		return true;
	}
	/**
	 * 清空频道以及频道下的模板
	 * @param ids
	 * @return
	 */
	public boolean removeWithForm(List<Long> ids)
	{ 
		
			for(int i=0;i<ids.size();i++)
		{
			
			 templateManagerService.removeByChannel(ids.get(i));
			 this.deleteChannel(String.valueOf(ids.get(i)));
		}
				
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.cyberway.core.dao.HibernateEntityDao#saveOrUpdate(java.lang.Object)
	 */
	public Channel saveOrUpdate(Channel channel){ 
		channel = super.saveOrUpdate(channel);
		siteCache.putChannelInCache(channel);
		return channel;
	}
	
	/**@author lan
	 * 频道及模板转换为XML
	 * @param channels
	 * @param root
	 */
	public void changeToXml(Collection<Channel> channels,Element root,boolean isChannel,Channel chn)
	{
		if (!CollectionUtils.isEmpty(channels))
		{
	        for (Channel channel : channels)
	        {
	        Element element = root.addElement("channel");
	        element.addElement("id").setText(ObjectUtils.toString(channel.getId()));
	        element.addElement("name").setText(ObjectUtils.toString(channel.getName()));
	        if(channel.getTimeCreated() == null)
	        {
	        	element.addElement("timeCreated").setText(StringUtils.EMPTY);
	        }
	        else
	        {
	        element.addElement("timeCreated").setText(ObjectUtils.toString(channel.getTimeCreated()));
	        }
	        if(channel.getTimeDeleted() == null)
	        {
	        	element.addElement("timeDeleted").setText(StringUtils.EMPTY);
	        }
	        else
	        {
	        element.addElement("timeDeleted").setText(ObjectUtils.toString(channel.getTimeDeleted()));
	        }
	        element.addElement("sortOrder").setText(ObjectUtils.toString(channel.getSortOrder()));
	        element.addElement("channelPath").setText(ObjectUtils.toString(channel.getChannelPath()));
	        element.addElement("infoSytle").setText(ObjectUtils.toString(channel.getInfoSytle()));
	        element.addElement("ispublished").setText(ObjectUtils.toString(channel.getIspublished()));
	        element.addElement("ispublicchannel").setText(ObjectUtils.toString(channel.getIspublicchannel()));
	        element.addElement("publicchannelid").setText(ObjectUtils.toString(channel.getPublicchannelid()));
	        if(channel.getPublicchannelid() != null && channel.getPublicchannelid() != 0)
	        {
	        	Channel publicChannel = this.getChannelFromCache(channel.getPublicchannelid());
				if(publicChannel != null)
				{
					 element.addElement("publicchannelpath").setText(publicChannel.getChannelPath());
				}
				else
		        {
		        	element.addElement("publicchannelpath").setText(null);
		        }
	        }
	        else
	        {
	        	element.addElement("publicchannelpath").setText(null);
	        }
	        element.addElement("iscopy").setText(ObjectUtils.toString(channel.getIscopy()));
	        element.addElement("status").setText(ObjectUtils.toString(channel.getStatus()));
	        element.addElement("Isflow").setText(ObjectUtils.toString(channel.getIsflow()));
	        element.addElement("isInheritPerm").setText(ObjectUtils.toString(channel.getIsInheritPerm()));
	        element.addElement("flowname").setText(ObjectUtils.toString(channel.getFlowname()));
	        element.addElement("numberOfClick").setText(ObjectUtils.toString(channel.getNumberOfClick()));
	        if(isChannel && chn != null && channel.equals(chn))
	        {
	        	element.addElement("parent_id").setText(null);
	        	element.addElement("parent_name").setText(null);
	        }
	        else
	        {
	        element.addElement("parent_id").setText(ObjectUtils.toString(channel.getParent() == null ? null : channel.getParent().getId()));
	        element.addElement("parent_name").setText(ObjectUtils.toString(channel.getParent() == null ? null : this.getChannelFromCache(channel.getParent().getId()).getChannelPath()));
	        }
	        element.addElement("site_id").setText(ObjectUtils.toString(channel.getSite() == null ? null : channel.getSite().getOid()));
	        element.addElement("formTemplate_id").setText(ObjectUtils.toString(channel.getFormTemplate() == null ? null : channel.getFormTemplate().getId()));
	        element.addElement("detailsTemplate_id").setText(ObjectUtils.toString(ObjectUtils.toString(channel.getDetailsTemplate() == null ? null : channel.getDetailsTemplate().getId())));
	        element.addElement("summaryTemplate_id").setText(ObjectUtils.toString(channel.getSummaryTemplate() == null ? null : channel.getSummaryTemplate().getId()));
	        element.addElement("adminSummaryTemplate").setText(ObjectUtils.toString(channel.getAdminSummaryTemplate() == null ? null : channel.getAdminSummaryTemplate().getId()));
	        Element  e_templates = element.addElement("Templates");
	       
	        Collection<Template> templates = templateManagerService.findByChannel(channel.getId());
	        templateManagerService.changeToXml(templates, e_templates);
	        }
		}
	}

	/**
	 * xml导出
	 * @param channel
	 * @param outputStream
	 * @throws IOException
	 */
	public void exportToXml (Collection<Channel> channels, OutputStream outputStream,Channel chn) throws IOException
	{
		Validate.notNull(outputStream);
		
		if (!CollectionUtils.isEmpty(channels))
		{
			OutputFormat format = new OutputFormat ();
			format.setIndent(true);
			format.setNewlines(true);
			XMLWriter writer = new XMLWriter (outputStream, format);
			Document document = DOMDocumentFactory.getInstance().createDocument();
	        Element root = document.addElement("Channels");
	        this.changeToXml(channels, root,true,chn);
	        writer.write(document);
		}
	}
/**
 * 从XML文件中读取数据
 * @author lan
 * @param channels 读取的频道
 * @param overwrite 是否重新创建CHANNEL
 * @param site 导入的站点 若为NULL则从XML文件中获取
 * @param parentChannel 导入到某一频道下，若为NULL则挂在根目录下并通过updateFromXml去更新其它频道的parent
 */	
public HashMap changeFromXml(Iterable<Element> channels,boolean overwrite,CmsSite site,Channel parentChannel,HashMap map)
{
	//HashMap map = new HashMap();
	for (Element channelE : (Iterable<Element>)channels)
	{
		try
		{
        	String id = channelE.element("id").getTextTrim();
        	String name = channelE.element("name").getText();
        	String timeCreated = channelE.element("timeCreated").getTextTrim();
        	String timeDeleted = channelE.element("timeDeleted").getTextTrim();
        	String sortOrder = channelE.element("sortOrder").getText();
        	String channelPath = channelE.element("channelPath").getText();
        	String infoSytle = channelE.element("infoSytle").getTextTrim();
        	String ispublished = channelE.element("ispublished").getTextTrim();
        	String ispublicchannel = channelE.element("ispublicchannel").getTextTrim();
        	String publicchannelid = channelE.element("publicchannelid").getTextTrim();
        	String iscopy = channelE.element("iscopy").getTextTrim();
        	String status = channelE.element("status").getTextTrim();
        	String isflow = channelE.element("Isflow").getTextTrim();
        	String isInheritPerm = channelE.element("isInheritPerm").getTextTrim();
        	String flowname = channelE.element("flowname").getTextTrim();
        	String numberOfClick = channelE.element("numberOfClick").getTextTrim();
        	String parent_id = channelE.element("parent_id").getTextTrim();
        	String site_id;
        	//更新站点
        	if(site != null && site.getOid() != null && site.getOid() != 0)
        	{
        		site_id = String.valueOf(site.getOid());
        	}
        	else
        	{
        		site_id = channelE.element("site_id").getTextTrim();
        	}
        	String formTemplate_id = channelE.element("formTemplate_id").getTextTrim();
        	String detailsTemplate_id = channelE.element("detailsTemplate_id").getTextTrim();
        	String adminSummaryTemplate_id = channelE.element("adminSummaryTemplate").getTextTrim();
        	Channel channel = null;
        	if (!overwrite)
        	{
        		channel = new Channel ();
        	}
        	else
        	{
        		Channel exists = StringUtils.isNumeric(id) ? get(Long.valueOf(id)) : null;
        		channel = exists == null ? new Channel () : exists;
        	}
        	
        	channel.setName(name);
        	//channel.setTimeCreated(StringUtils.isEmpty(timeCreated) ? null : DateUtils.parseDate(timeCreated, new String[] { EXPORT_DATE_FORMAT }));
        	//channel.setTimeDeleted(StringUtils.isEmpty(timeDeleted) ? null : DateUtils.parseDate(timeDeleted, new String[] { EXPORT_DATE_FORMAT }));
        	channel.setSortOrder(StringUtils.isEmpty(sortOrder) ? null : Integer.valueOf(sortOrder));
        	channel.setChannelPath(channelPath);
        	channel.setInfoSytle(infoSytle);
        	channel.setIspublished(StringUtils.isEmpty(ispublished) ? null : Integer.valueOf(ispublished));
        	channel.setIspublicchannel(StringUtils.isEmpty(ispublicchannel) ? null : Integer.valueOf(ispublicchannel));
        	channel.setPublicchannelid(StringUtils.isEmpty(publicchannelid) ? null : Long.valueOf(publicchannelid));
        	channel.setIscopy(StringUtils.isEmpty(iscopy) ? null : Integer.valueOf(iscopy));
        	channel.setStatus(StringUtils.isEmpty(status) ? null : Integer.valueOf(status));
        	channel.setIsflow(StringUtils.isEmpty(isflow) ? null : Integer.valueOf(isflow));
        	channel.setIsInheritPerm(StringUtils.isEmpty(isInheritPerm) ? null : Integer.valueOf(isInheritPerm));
        	channel.setFlowname(flowname);
        	channel.setNumberOfClick(StringUtils.isEmpty(numberOfClick) ? null : Long.valueOf(numberOfClick));
        	//更改导入第一个栏目的parent
        	if(parentChannel != null)
        	{
        		channel.setParent(parentChannel);
        		parentChannel = null;
        	}
        	//channel.setParent(StringUtils.isEmpty(parent_id)? null : this.get(Long.valueOf(parent_id)));
        	channel.setSite(StringUtils.isEmpty(site_id)? null : siteCache.getSiteFromCache(site_id));
        	channel.setFormTemplate(StringUtils.isEmpty(formTemplate_id)? null : templateManagerService.get(Long.valueOf(formTemplate_id)));
        	channel.setDetailsTemplate(StringUtils.isEmpty(detailsTemplate_id)? null : templateManagerService.get(Long.valueOf(detailsTemplate_id)));
        	channel.setAdminSummaryTemplate(StringUtils.isEmpty(adminSummaryTemplate_id)? null : templateManagerService.get(Long.valueOf(adminSummaryTemplate_id)));
        	
        	//若当前站点下存在相同则重命名
        	Boolean unique = this.isNotUnique(channel, "site.oid,channelPath");
        	if(unique)
        	{
        		channel.setChannelPath(channel.getChannelPath()+"_import"+new Date().getTime());
        		map.put(channelPath, channel.getChannelPath());
        	}
        	channel = saveOrUpdate(channel);
        	//导入频道下的所有模板
        	Iterable<Element> templates = channelE.element("Templates").elements("Template");
        	if(templates !=null)
        	{
        		map = templateManagerService.changeFromXml(templates, overwrite,channel,site,map);
        		//templateManagerService.changeFromXml(templates, overwrite,channel,null);
        	}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	return map;
}
/**
 * @author lan
 * 更新频道的parent
 * @param channels 导入的频道
 * @param site 导入到site站点下，null则从XML文件中获取
 * @param map 用于更新子频道的Parent，（此参数用于导入的父频道在当前站点中存在重名，修改父频道的channelPath后父频道下的子频道能跟着改变Parent）
 * @param chn 更新频道Parent时，top channel不进行更新，top channel的parent已经在changeFromXml中指定，NULL代表更新所有频道，一般用在导入站点
 */
public void updateFromXml(Iterable<Element> channels,CmsSite site,HashMap map,Channel chn)
{
	for (Element channelE : (Iterable<Element>)channels)
	{
		try
		{ 
			if(chn == null)
			{
        	String channelPath = channelE.element("channelPath").getText();
        	String parent_name = channelE.element("parent_name").getTextTrim();
        	if(map != null && map.containsKey(parent_name))
        	{
        		parent_name = map.get(parent_name).toString();
        	}
        	if(map != null && map.containsKey(channelPath))
        	{
        		channelPath = map.get(channelPath).toString();
        	}
        	if(parent_name != null && StringUtils.isNotEmpty(parent_name))
        	{
        	String site_id;
        	if(site != null && site.getOid() != null && site.getOid() != 0)
        	{
        		site_id = String.valueOf(site.getOid());
        	}
        	else
        	{
        		site_id = channelE.element("site_id").getTextTrim();
        	}
        	Channel channel =getChannelByPath(Long.valueOf(site_id), channelPath);
        	channel.setParent(StringUtils.isEmpty(parent_name)? null : getChannelByPath(Long.valueOf(site_id), parent_name));
            saveOrUpdate(channel);
        	}
			}
			chn = null;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
/**
 * 更新频道引用或者复制的公共模板ID
 * @param channels
 * @param site
 * @param map
 */
public void updatePublicFromXml(Iterable<Element> channels,CmsSite site,HashMap map)
{
	for (Element channelE : (Iterable<Element>)channels)
	{
		try
		{ 
			String iscopy = channelE.element("iscopy").getText();
			if(iscopy != null && !iscopy.equals("0"))
			{
        	String publicchannelpath = channelE.element("publicchannelpath").getText();
        	String channelPath = channelE.element("channelPath").getTextTrim();
        	if(map != null && map.containsKey(publicchannelpath))
        	{
        		publicchannelpath = map.get(publicchannelpath).toString();
        	}
        	if(map != null && map.containsKey(channelPath))
        	{
        		channelPath = map.get(channelPath).toString();
        	}
        	String site_id;
        	if(site != null && site.getOid() != null && site.getOid() != 0)
        	{
        		site_id = String.valueOf(site.getOid());
        	}
        	else
        	{
        		site_id = channelE.element("site_id").getTextTrim();
        	}
        	Channel channel =getChannelByPath(Long.valueOf(site_id), channelPath);
        	channel.setPublicchannelid(StringUtils.isEmpty(publicchannelpath)? null : getChannelByPath(Long.valueOf(site_id), publicchannelpath).getId());
            saveOrUpdate(channel);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
/**
 * 用于更新导入频道的default模板 
 * @param channels xml中的所有channel
 * @param site  导入的站点 
 * @param map   与原来的default模板和与原来的channelPath进行匹配的键值，确保与原导入数据之间的关系一致。
 */
public void updateDefaultTemplateFromXml(Iterable<Element> channels,CmsSite site,HashMap map)
{
	for (Element channelE : (Iterable<Element>)channels)
	{
		try
		{ 
			
			String formTemplate_id = channelE.element("formTemplate_id").getText();
			String detailsTemplate_id = channelE.element("detailsTemplate_id").getText();
			String summaryTemplate_id = channelE.element("summaryTemplate_id").getText();
			String adminSummaryTemplate = channelE.element("adminSummaryTemplate").getText();
		    if(map != null && StringUtils.isNotEmpty(formTemplate_id) || StringUtils.isNotEmpty(detailsTemplate_id) || StringUtils.isNotEmpty(summaryTemplate_id) || StringUtils.isNotEmpty(adminSummaryTemplate))
		    {
		    String channelPath = channelE.element("channelPath").getTextTrim();
        	if(map.containsKey(channelPath))
        	{
        		channelPath = map.get(channelPath).toString();
        	}
        	String site_id;
        	if(site != null && site.getOid() != null && site.getOid() != 0)
        	{
        		site_id = String.valueOf(site.getOid());
        	}
        	else
        	{
        		site_id = channelE.element("site_id").getTextTrim();
        	}
        	Channel channel =getChannelByPath(Long.valueOf(site_id), channelPath);
        	if(StringUtils.isNotEmpty(formTemplate_id) && map.containsKey(formTemplate_id))
        	{
        		formTemplate_id = map.get(formTemplate_id).toString();
        		channel.setFormTemplate(templateManagerService.get(Long.parseLong(formTemplate_id)));
        	}
        	if(StringUtils.isNotEmpty(detailsTemplate_id) && map.containsKey(detailsTemplate_id))
        	{
        		detailsTemplate_id = map.get(detailsTemplate_id).toString();
        		channel.setDetailsTemplate(templateManagerService.get(Long.parseLong(detailsTemplate_id)));
        	}
        	if(StringUtils.isNotEmpty(summaryTemplate_id) && map.containsKey(summaryTemplate_id))
        	{
        		summaryTemplate_id = map.get(summaryTemplate_id).toString();
        		channel.setSummaryTemplate(templateManagerService.get(Long.parseLong(summaryTemplate_id)));
        	}
        	if(StringUtils.isNotEmpty(adminSummaryTemplate) && map.containsKey(adminSummaryTemplate))
        	{
        		adminSummaryTemplate = map.get(adminSummaryTemplate).toString();
        		channel.setAdminSummaryTemplate(templateManagerService.get(Long.parseLong(adminSummaryTemplate)));
        	}
            saveOrUpdate(channel);
		    }

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
/**
 * 导入XML
 * @param inputStream
 * @param overwrite
 * @param site
 * @throws DocumentException
 */
	public void importFromXml (InputStream inputStream, boolean overwrite,CmsSite site) throws DocumentException
	{
		Validate.notNull(inputStream);
		
		SAXReader reader = new SAXReader ();
		Document document = reader.read(inputStream);
		Iterable<Element> channels = document.getRootElement().elements("channel");
		HashMap map = this.changeFromXml(channels, overwrite, site,null,new HashMap());
		this.updateFromXml(channels, site,map,new Channel());
	}
	/**
	 * 导入到parent中
	 * @param inputStream
	 * @param overwrite
	 * @param site
	 * @param channel
	 * @throws DocumentException
	 */
	public void importParentFromXml (InputStream inputStream, boolean overwrite,CmsSite site,Channel channel) throws DocumentException
	{
		Validate.notNull(inputStream);
		SAXReader reader = new SAXReader ();
		Document document = reader.read(inputStream);
		Iterable<Element> channels = document.getRootElement().elements("channel");
		HashMap map = this.changeFromXml(channels, overwrite, site,channel,new HashMap());
		this.updateFromXml(channels, site,map,channel);
	}

	/**
	 * 检测两频道之间的文档是否能移动
	 * @param chnid1
	 * @param chnid2
	 * @return
	 */
	public Boolean checkCanMoveDocBychannel(Long chnid1,Long chnid2){
		boolean ismove=false;
		if(chnid1!=null  && chnid2!=null){
		 Channel chn1=this.getChannelFromCache(chnid1);
		 Channel chn2=this.getChannelFromCache(chnid2); 
		 //引用模板时，
		 if(chn1==null || chn2==null)
			 return ismove;
		 //获取表单模板ID
		 Long formTemplate1id, formTemplate2id ;
		 
		 if(chn1.getFormTemplate()==null){
			 try {
				formTemplate1id = getDefualutTemplateId(chnid1, 1);
				if(formTemplate1id == null)
					return ismove; 
			} catch (Exception e) {
				return ismove; 
			}
		 }else{
			 formTemplate1id = chn1.getFormTemplate().getId();
		 }
		 
		 if(chn2.getFormTemplate()==null){
			 try {
				formTemplate2id = getDefualutTemplateId(chnid2, 1);
				
				if(formTemplate2id == null)
					return ismove; 
			} catch (Exception e) {
				return ismove; 
			}
		 }else{
			 formTemplate2id = chn2.getFormTemplate().getId(); 
		 }
		 
		 CoreForm cf1=getTemplateManagerService().getTemplateFormByTemplateId(formTemplate1id);
		 CoreForm cf2=getTemplateManagerService().getTemplateFormByTemplateId(formTemplate2id);
		 if(cf1!=null && cf2!=null){
			 if(cf1.getOid().intValue()==cf2.getOid().intValue())
				 ismove=true;
			 if(cf1.getPojoName().equalsIgnoreCase(cf2.getPojoName()))
				 ismove=true;
			 //cf1.getPojoName()
		 }
		}
		return ismove;
	}
	/**
	 * 获取childChannels
	 * @param channelid
	 * @param items 用于存储频道信息的集合 
	 * @return
	 */
	public List getChild(long channelid, List items)
	{  
		return getChild(channelid,items,true); 
	}
	
	/**
	 * 获取childChannels
	 * @param channelid
	 * @param items 用于存储频道信息的集合
	 * @param ispulished 是否发布
	 * @return 
	 */
	public List getChild(long channelid, List items, boolean ispulished)
	{ 
		Channel channel = this.get(channelid);
		if(channel != null)
		{
		Iterator it = channel.getChildren().iterator();
		while(it.hasNext())
		{
		 Channel chn = (Channel)it.next();
		 if(chn != null)
		 {
			 if(!ispulished){
				 items.add(chn);
				 getChild(chn.getId(),items);				 
			 }else if(ispulished && chn.getIspublished() > 0){
				 items.add(chn);
				 getChild(chn.getId(),items,ispulished);
			 }
		 }
		}
		}
		return items;
	}
	
	
	/**
	 * 获取 当前频道下的 第一个子叶  频道(包括当前频道)
	 * @param pchannelid 
	 * @param channelid 
	 * @return
	 */
	public Channel getFirstLeafChild(long channelid)
	{
		Channel channel = this.get(channelid);
		if (channel != null) {
			//如果存在子频道. 则进入到子频道
			if(channel.getChildren().size()> 0){ 
				Iterator it = channel.getChildren().iterator();
				while (it.hasNext()) {
					Channel chn = (Channel) it.next();
					if (chn != null) {
						return getFirstLeafChild(chn.getId()); 
					}
				}
			}else{
				// 如果是子叶频道 
				return channel;
			}
		}
		return channel;
	}
	/**
	 * 检查频道是否有HTML
	 * @param channelId
	 * @return
	 */
	public boolean checkIsHtmlOfChannel(Long channelid){
		Channel channel= this.getChannelFromCache(channelid);
		if(channel!=null){
			if(channel.getCreatehtml()!=null&&channel.getCreatehtml()){
				return true;
			}
		}
		return false;
	}

	/**
	 * 频道静态页面 Frank
	 */
	public boolean channelTemplate(CmsSite site, Channel channel) {
		if (channel != null) {
			HtmlSynchroismService htmlSynchroismService = (HtmlSynchroismService) ServiceLocator.getBean("htmlSynchroismService");
			htmlSynchroismService.deleteStaticHtmlByChannelId(channel.getId());
		} else {
			return false;
		}
		return true;
	}
	
	/**
	 * 选择频道静态发布
	 * @param keys
	 * @return
	 */
	public boolean staticChanels(String keys)
	{
		boolean isSucc = true;
		if(!StringUtil.isEmpty(keys)){
			List<String> ids=StringUtil.splitList(keys,",");
			if(ids!=null){
	    		for (String id : ids)
	    		{
	    			try
	    			{
	    			isSucc = channelTemplateStatic(id);
	    			}catch(Exception e ){e.printStackTrace();continue;}
	    		}
	    	}
		}

		return isSucc;
	}
	
	
	/**
	 * 将频道生成静态文件
	 * 
	 * @param channelId
	 * @return
	 */
	public boolean channelTemplateStatic(String channelId) {
		if (StringUtils.isNotBlank(channelId)) {
			Channel channel = siteCache.getChannelFromCach(Long.valueOf(channelId));
			channelTemplate(channel.getSite(), channel);
		} else {
			return false;
		}
		return true;
	}
	
	public SiteCache getSiteCache() {
		return siteCache;
	}
	public void setSiteCache(SiteCache siteCache) {
		this.siteCache = siteCache;
	}

	public TemplateManagerService getTemplateManagerService() {
		return templateManagerService;
	}

	public void setTemplateManagerService(
			TemplateManagerService templateManagerService) {
		this.templateManagerService = templateManagerService;
	}

	public List getChannelById(Long channelId){
		List array=new ArrayList();
		return getChild(channelId,array);
	}
	
	/**
	 * 清除所有频道缓存
	 */
	public void removeAllCache(){
		siteCache.removeAllChannel();
	}
	
	/**
	 * 获取所有频道缓存中的key
	 * @return
	 */
	public List<String> getAllCacheKeys(){
		return siteCache.getChannelCacheKeys();
	}
	
	/**
	 * 获得指定的频道缓存
	 * 
	 * @param key
	 * @return
	 */
	public net.sf.ehcache.Element getElementFromCache(String key) {
		if (StringUtils.isNotEmpty(key)){
			net.sf.ehcache.Element element = null;
			element = siteCache.getElementFromChannel(key);
			return element;
		}
		else
			return null;
	}
	
	/**
	 * 更新频道channel的子频道,使她们的_ispublished属性保持与父频道一致.
	 * 
	 * @param siteId
	 *            站点id
	 * @param channel
	 *            父频道
	 */
	public void updateSubChannelPublish(Long siteId, Channel channel) {
		List<Channel> channelList = getAllSubByParent(siteId, channel.getId());
		for (Channel cha : channelList) {
			if (channel.getIspublished() != cha.getIspublished()) {
				cha.setIspublished(channel.getIspublished());
				save(cha);
			}
		}
	}
	
	/**
	 * 根据站点id和频道id查找该频道的所有下级频道
	 * 
	 * @param siteId
	 *            站点id
	 * @param id
	 *            频道id
	 * @return
	 */
	public List<Channel> getAllSubByParent(Long siteId, Long id) {
		List<Channel> l = new ArrayList<Channel>();
		List<Channel> ls = new ArrayList<Channel>();

		Criteria c = getEntityCriteria();
		c.add(Restrictions.eq("site.oid", siteId));
		c.add(Restrictions.eq("parent.id", id));
		l = c.list();
		for (Channel cha : l) {
			List<Channel> lls = getAllSubByParent(siteId, cha.getId());
			if (lls.size() > 0) {
				ls.addAll(lls);
			}
		}
		l.addAll(ls);

		return l;
	}
	
	/**
	 * dwr 验证 : channelPath 是否唯一
	 * @remark dwrUnique add by liaozhiyong 2012-03-26
	 * @param ChannelId
	 * @return String (0 已经存在有，1是唯一)
	 */ 
	@SuppressWarnings("unchecked")
	public String dwrChannelValidateChannelPathUnique(String ChannelId, String channelPath){
		String flag = "0"; 
		if (channelPath != null && channelPath.length() > 0) {
			String hql = " from Channel where channelPath='"+channelPath+"' ";
			if(ChannelId!=null && ChannelId.length()>0){
				hql += " and id<>"+ChannelId+" ";
			}
			List<Channel> list = this.find( hql );
			if(list==null || list.size()<1){
				flag = "1";
			}
		}
		return flag;
	}
	
}
