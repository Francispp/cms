package com.cyberway.cms.exposed;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;

import ognl.Ognl;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.form.object.BaseDocument;
import com.cyberway.cms.internal.template.token.ListWriter.Limit;
import com.cyberway.core.Constants;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.StringUtil;

/**
 * @author caiw 当前web环境接口
 * 
 */
public class WebJsUtil {
	private BaseDocument _doc;// 文档对象

	private Loginer _loginer;// 当前登录用户

	private PageContext pageContext;// 当前的pageContext

	private ServletRequest request;// 当前的request

	private WebJsUtil() {// 禁止不带参数初始化此类

	}

	/**
	 * 初始化Web
	 * 
	 * @param pc
	 */
	public WebJsUtil(PageContext pc) {// 带当前页面环境初始化此参数
		pageContext = pc;
		request = pageContext.getRequest();
	}

	/**
	 * 获得指定参数
	 * 
	 * @param tag
	 * @return
	 */
	public String getParameter(String tag) {
		String value = request.getParameter(tag);
		return (value == null) ? "" : value;
	}

	/**
	 * 获得当前登录的用户对象
	 * 
	 * @return
	 */
	public Loginer getWebUser() {
		if (_loginer == null
				&& pageContext.getSession().getAttribute(
						Constants.USER_IN_SESSION) != null) {
			_loginer = (Loginer) pageContext.getSession().getAttribute(
					Constants.USER_IN_SESSION);
		}
		if (_loginer == null) {// 若未登录，则为公共用户
			_loginer = new Loginer();
			_loginer.setLoginid("public");
		}
		return _loginer;
	}

	/**
	 * 获得当前的id
	 * 
	 * @return
	 */
	public Long getId() {
		return (Long) pageContext.getAttribute("id");
	}

	/**
	 * 获得当前频道id
	 * 
	 * @return
	 */
	public Long getChannelId() {
		Long chnId=null;		
		if(request.getAttribute("channelId")!=null)
			chnId=(Long)request.getAttribute("channelId");
		else
			chnId=(Long)pageContext.getAttribute("channelId");
		return chnId;
	}

	/**
	 * 获得当前站点id
	 * 
	 * @return
	 */
	public Long getSiteId() {
		Long siteid=null;
		
		if(request.getAttribute("siteid")!=null)
			siteid=(Long)request.getAttribute("siteid");
		else
			siteid=(Long)pageContext.getAttribute("siteid");
		if(siteid==null)
			siteid=(Long)request.getAttribute("siteId");
		return siteid;
	}

	/**
	 * 模板id
	 * 
	 * @return
	 */
	public Long getTemplateId() {
		Long tpid=null;
		if(request.getAttribute("templateId")!=null)
			tpid=(Long)request.getAttribute("templateId");
		else
			tpid=(Long)pageContext.getAttribute("templateId");		
		return tpid;
	}

	/**
	 * 获得模板名称
	 * 
	 * @return
	 */
	public String getTemplateName() {
		return request.getParameter("templateName");
	}

	/**
	 * 获得当前文档对象
	 * 
	 * @return
	 */
	public BaseDocument getDocument() {
		if (_doc == null) {
			if(request.getAttribute("domain")!=null)
				_doc = (BaseDocument) request.getAttribute("domain");	
			else
			   _doc = (BaseDocument) pageContext.getAttribute("domain");
			if (_doc == null)
				_doc = new BaseDocument();
		}
		return _doc;
	}
	/**
	 * 获取当前文档ID
	 * @return
	 */
	public Long getDocumentId() {
		Long tpid=null;
		if (tpid == null) {
			if(request.getAttribute("domain")!=null)
				tpid = ((BaseDocument) request.getAttribute("domain")).getId();	
			else
				tpid = ((BaseDocument) pageContext.getAttribute("domain")).getId();
			if (tpid == null)
				tpid = 0l;
		}
		return tpid;
	}

	/**
	 * 获得当前文档属性值
	 * 
	 * @param tag
	 * @return
	 */
	public Object getProperty(String tag) {
		Object obj = null;
		try {
			obj = Ognl.getValue(tag, getDocument());
		} catch (Exception e) {

		}
		
		return obj;
	}
	/**
	 * 获得指定对象 属性值
	 * @param tag
	 * @param objName
	 * @return
	 */
	public Object getProperty(String tag,String objName) {
		Object obj = null;
		if(StringUtil.isEmpty(objName)){
			if(request.getAttribute(tag)!=null)
			  obj=request.getAttribute(tag);
			else
			  obj=this.pageContext.getAttribute(tag);
		}else{
		try {
		    Object o=request.getAttribute(objName);
		     if(o==null)
		    	  o=pageContext.getAttribute(objName);
		    if(o!=null)
			 obj = Ognl.getValue(tag, o);
		} catch (Exception e) {

		}
		}
		return obj;
	}
	/**
	 * 获得属性值（String）
	 * @param tag
	 * @return
	 */
	public String getPropertyToString(String tag){
		Object obj=getProperty(tag);
		if(obj==null)
			return "";
		return obj.toString();
	}
    /**
     * 获得当前页面列表limit对象
     * @param pageSize 页记录数
     * @param recordCount 总记录数（未知可设置为null）
     * @param orderBy 排序字段
     * @param ascending 排序的升、降(false 为降，true为升)
     * @return
     */
    public org.ecside.table.limit.Limit getLimit(Integer pageSize, Integer recordCount, String orderBy, boolean ascending){
    	int pageIndex=1; 
    	if(!StringUtils.isBlank (request.getParameter ("pageIndex")) && StringUtils.isNumeric(request.getParameter ("pageIndex"))){
    		pageIndex=Integer.valueOf (request.getParameter ("pageIndex"));
    	 }
    	  //int pageIndex = StringUtils.isBlank (request.getParameter ("pageIndex")) ? 1 : Integer.valueOf (request.getParameter ("pageIndex"));
    	  //Limit(当前页数，默认的页条数，)
    	if(StringUtils.isBlank(orderBy))
    		orderBy="id";//默认按id排序
    	  Limit limit = new Limit (pageIndex, 10, recordCount, orderBy, ascending);
    	  return limit;
    }
    /**
     * 获得当前页码
     * @return
     */
    public Integer getPageIndex(){
    	int pageIndex=1; 
    	if(!StringUtils.isBlank (request.getParameter ("pageIndex")) && StringUtils.isNumeric(request.getParameter ("pageIndex"))){
    		pageIndex=Integer.valueOf (request.getParameter ("pageIndex"));
    	 }
    	return pageIndex;
    }
}
