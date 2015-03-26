package com.cyberway.cms.document.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.dom.DOMDocumentFactory;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.ecside.table.limit.Limit;
import org.springframework.util.CollectionUtils;

import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.Document;
import com.cyberway.cms.domain.DocumentItem;
import com.cyberway.common.base.objects.Constants;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.objects.UserFrame;

public class DocumentManagerService extends HibernateEntityDao<Document>
{
	private Log _log = LogFactory.getLog(DocumentManagerService.class);
	private ChannelManagerService channelService;
	
	public synchronized Long allocateUniqueId ()
	{
		Long id = null;
		do
		{
			try
			{
				id = Long.valueOf(RandomStringUtils.randomNumeric(19));
			}
			catch (NumberFormatException ex)
			{
				id = null;
			}
		}while (id == null);
		
		return id;
	}
	
	public void exportToXml (Collection<Document> data, OutputStream outputStream) throws IOException
	{
		Validate.notNull(outputStream);
		
		if (!CollectionUtils.isEmpty(data))
		{
			OutputFormat format = new OutputFormat ();
			format.setIndent(true);
			format.setNewlines(true);
			XMLWriter writer = new XMLWriter (outputStream, format);
			
			org.dom4j.Document xmlDocument = DOMDocumentFactory.getInstance().createDocument();
	        Element root = xmlDocument.addElement("Documents");
	        for (Document  document : data)
	        {
	        	Element element = root.addElement("Document");
	        	element.addElement("id").setText(ObjectUtils.toString(document.getId()));
	        	element.addElement("issued").setText(ObjectUtils.toString(document.getIssued()));
	        	//element.addElement("author").setText(ObjectUtils.toString(document.getAuthor() == null ? null : document.getAuthor().getUserid() ));
	        	element.addElement("channel").setText(ObjectUtils.toString(document.getChannel() == null ? null : document.getChannel().getId()));
	        	if (document.getTimeCreated() == null)
	        	{
	        		element.addElement("timeCreated").setText(StringUtils.EMPTY);
	        	}
	        	else
	        	{
	        		element.addElement("timeCreated").setText(DateFormatUtils.format(document.getTimeCreated(), Constants.EXPORT_DATE_FORMAT));
	        	}

	        	Element propertiesE = element.addElement("Properties");
	        	
	        	if (!CollectionUtils.isEmpty(document.getItems()))
	        	{
	        		for (DocumentItem item : document.getItems())
	        		{
	        			Element propertyE = propertiesE.addElement("Property");
	        			propertyE.addAttribute("name", item.getName());
	        			propertyE.addAttribute("valueType", item.getValueType());
	        			
	        			if (ObjectUtils.equals(item.getValueType(), DocumentItem.VALUETYPE_DATE))
	        			{
	        				Date date = (Date)item.getValue();
	        				if (date == null)
	        	        	{
	        	        		propertyE.setText(StringUtils.EMPTY);
	        	        	}
	        	        	else
	        	        	{
	        	        		propertyE.setText(DateFormatUtils.format(date, Constants.EXPORT_DATE_FORMAT));
	        	        	}
	        			}
	        		}
	        	}
	        }
	        
	        writer.write(xmlDocument);
		}
	}
	
	public Collection<Document> importFromXml (InputStream inputStream, boolean overwrite) throws DocumentException
	{
		Validate.notNull(inputStream);
		
		List<Document> data = new ArrayList<Document> ();
		
		SAXReader reader = new SAXReader ();
		org.dom4j.Document xmlDocument = reader.read(inputStream);
		
		for (Element documentE : (Iterable<Element>)xmlDocument.getRootElement().elements("Document"))
		{
			try
			{
	        	String id = documentE.element("id").getTextTrim();
	        	String issued = documentE.element("issued").getTextTrim();
	        	String author = documentE.element("author").getTextTrim();
	        	String channel = documentE.element("channel").getTextTrim();
	        	String timeCreated = documentE.element("timeCreated").getTextTrim();
	        
	        	Document document = null;
	        	if (!overwrite)
	        	{
	        		document = new Document ();
	        	}
	        	else
	        	{
	        		Document exists = StringUtils.isNumeric(id) ? get(Long.valueOf(id)) : null;
	        		document = exists == null ? new Document () : exists;
	        	}
	        	
	        	//document.setIssued((Boolean)new BooleanConverter ().convert(Boolean.class, issued));
	        	//document.setAuthor(StringUtils.isEmpty(author) ? null : (CoreUser)get(CoreUser.class, Long.valueOf(author)));
	        	document.setChannel(StringUtils.isEmpty(channel) ? null : (Channel)get(Channel.class, Long.valueOf(channel)));
	        	document.setTimeCreated(StringUtils.isEmpty(timeCreated) ? null : DateUtils.parseDate(timeCreated, new String[] { Constants.EXPORT_DATE_FORMAT }));
	        	
	        	for (Element propertyE : (Iterable<Element>)documentE.element("Properties").elements("Property"))
	        	{
	        		String name = propertyE.attributeValue("name");
	        		String value = propertyE.getText();
	        		
	        		if (ObjectUtils.equals(propertyE.attributeValue("valueType"), DocumentItem.VALUETYPE_DATE))
	        		{
	        			document.set(name, StringUtils.isBlank(value) ? null : DateUtils.parseDate(value, new String[] { Constants.EXPORT_DATE_FORMAT })); 
	        		}
	        		else if (ObjectUtils.equals(propertyE.attributeValue("valueType"), DocumentItem.VALUETYPE_NUMBER))
	        		{
	        			document.set(name, StringUtils.isBlank(value) ? null : Long.valueOf(value));
	        		}
	        		else if (ObjectUtils.equals(propertyE.attributeValue("valueType"), DocumentItem.VALUETYPE_BOOLEAN))
	        		{
	        			document.set(name, StringUtils.isBlank(value) ? null : new BooleanConverter ().convert(Boolean.class, value));
	        		}
	        		else if (ObjectUtils.equals(propertyE.attributeValue("valueType"), DocumentItem.VALUETYPE_OBJECT))
	        		{
	        			throw new UnsupportedOperationException ();
	        		}
	        		else
	        		{
	        			document.set(name, value);
	        		}
	        	}
	        	
	        	data.add(document);
	        	saveOrUpdate(document);
			}
			catch (Exception ex)
			{
				_log.error("cann't import document from xml", ex);
			}
		}
		
		return data;
	}
	/**
	 * 重载方法
	 * 支持EC控件的查询方法
	 * 
	 * @param filterMap
	 *            过滤条件
	 * @param limit
	 *            EC的limit对象
	 * @return
	 * @throws
	 * @throws Exception
	 *             TODO:去除日期类型模糊查询带的Oralce物理特性
	 */
	public Page findECPage(Limit limit,CriteriaSetup criteriaSetup,Channel channel,UserFrame loginer) throws Exception {
		/*Criteria criteria = this.getEntityCriteria();
		//CriteriaSetup criteriaSetup = new CriteriaSetup();
		String hql="select item.Document from DocumentItem as item ";
		if(criteriaSetup==null)
			criteriaSetup = new CriteriaSetup();		
		criteriaSetup.setup(criteria);
		if (limit.isFiltered()) {
			Filter[] filters = limit.getFilterSet().getFilters();
			for (Filter filter : filters) {
				Class type = null;
				String property=filter.getProperty();
				try{
				 type = this.getEntityClass().getDeclaredField(
						filter.getProperty()).getType();
				     if (type.equals(Date.class) || type.equals(java.sql.Date.class)) {// 如果是日期型的做特殊处理
						// 注意这里别名和字段名的取法，这里存在bug，字段名称和日期格式问题...
						criteria.add(Restrictions.sqlRestriction("to_char({alias}."
								+ getColumnByProperty(filter.getProperty()) + ",'"
								+ getDateFormatByProperty(filter.getProperty())
								+ "') like ?", "%" + filter.getValue() + "%",
								Hibernate.CHARACTER));
					 } else {
						criteria.add(Restrictions.like(property, filter
								.getValue(), MatchMode.ANYWHERE));
						logger.info("limit Filter:"+filter.getProperty()+" like '"+filter.getValue()+"'");
					 }				 
				}catch(Exception e){
					type=String.class;
					property="items.name";//+filter.getProperty();
					criteria.add(Restrictions.like(property, filter.getProperty(), MatchMode.ANYWHERE));
				}
				
			}
		}
		//select item.document from DocumentItem item,item.document doc where item.name='mytime'
		//List list=this.find("select item.Document from DocumentItem as item, item.Document as doc where item.name='mytime'", null);
		//logger.info("list is size:"+list.size());
		Map orderMap = ExtremeTablePage.getSort(limit);		
		//if (!CollectionUtils.isEmpty(orderMap))
			//sortCriteria(criteria, orderMap, null);		
		return pagedQuery(criteria, limit.getPage(), limit.getCurrentRowsDisplayed());*/
		//, item.Document as doc
		//List list=this.find("select item.Document from DocumentItem as item where name='我的日期' and item.Document.id=?", new Object[]{new Long(59)});
		//List list=this.find("select item.Document from DocumentItem as item inner join Document as doc on doc.id=item.Document.id where item.name='我的日期' and item.Document.id=?", new Object[]{new Long(59)});
        
		//logger.info("list is size:"+list.size());
		Page page=super.findECPage(limit, criteriaSetup);
		/*if(channel!=null && channel.getIsflow()==1){
			 List mgrs=new ArrayList();
			 mgrs.add(channel.getFlowname());
			 Map procs=flowService.getOnlineAssigns(mgrs, loginer, 0);
			 page.setData(FlowBizService.getFlowDataList(((List)(page.getResult())),procs,0));
			 channel=null;
			 loginer=null;
			 procs=null;
		}*/
		return page;
}	
	/**
	 * 获得待办在办任务
	 * @param limit
	 * @param criteriaSetup
	 * @param loginer
	 * @return
	 * @throws Exception
	 */
	/*public Page getAssigns(Limit limit,CriteriaSetup criteriaSetup,UserFrame loginer) throws Exception{
		 List mgrs=new ArrayList();
		 mgrs.add("");
		Map procs=flowService.getOnlineAssigns(mgrs, loginer, 0);
		List data=new ArrayList();
		
		Page page=new Page(1,procs.size(),10,data);
		
		return page;
	}*/
	/**
	 * 发布文档
	 * @param ids
	 * @return
	 */
	public boolean saveIssueDocs(List<Long> ids){
		//Document doc=null;
		boolean succ=true;
		for(Long id:ids){
			//doc=this.get(id);
			//发布文档
			if(!saveIssueDoc(id)){
				succ=false;
				break;
			}
		}		
		return succ;
	}
	/**
	 * 取消发布
	 * @param ids
	 * @return
	 */
	public boolean saveUnIssueDocs(List<Long> ids){
		//Document doc=null;
		boolean succ=true;
		for(Long id:ids){
			//doc=this.get(id);
			//发布文档
			if(!saveUnIssueDoc(id)){
				succ=false;
				break;
			}
		}		
		return succ;
	}	
	/**
	 * 发布文档
	 * @param id
	 * @return
	 */
	public boolean saveIssueDoc(Long id){
		Document doc=this.get(id);
		//发布文档
		if(doc.getIssued()==0||doc.getIssued()==2||doc.getIssued()==4){
			Channel channel=channelService.getChannelFromCache(doc.getChannel().getId());
			if(channel.getIsflow()==1 && doc.getIssued()==0)//带流程的频道，不能在草稿状态下发布
				return false;
				
			doc.setIssued(5);
			doc.setTimeIssued(new Date());
			this.save(doc);

		}else
			return false;
		return true;
	}
	/**
	 * 取消发布的文档
	 * @param id
	 * @return
	 */
	public boolean saveUnIssueDoc(Long id){
		Document doc=this.get(id);
		//发布文档
		if(doc.getIssued()==5){
			doc.setIssued(4);
			//doc.setTimeIssued(new Date());
			this.save(doc);
			
		}else
			return false;
		return true;
	}
    /**
     * 更新文档状态
     * @param id
     */
    public void updateDocumentState(Long id){
    	//供流程使用
    	/*Document doc=this.get(id);
    	CoreFlow flow=null;
    	if(doc.getFlowinfo()!=null && doc.getFlowinfo().getFid()!=null)
    		flow=(CoreFlow)this.get(CoreFlow.class,doc.getFlowinfo().getFid());
    	if(flow==null||flow.getState()==null)
    		return ;
    	int state=flow.getState().intValue();
    	if(state==1)
    		doc.setIssued(2);//流程已完成，设置成待发布
    	else if(state==-1)
    		doc.setIssued(3);//流程已中止，设置成已否状态
    	else if(doc.getIssued()==0)
    		 doc.setIssued(1);
    	else
    		return ;
    	this.save(doc);*/
    }

	
}
