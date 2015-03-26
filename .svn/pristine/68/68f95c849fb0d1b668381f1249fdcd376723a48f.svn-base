package com.cyberway.core.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;
import org.ecside.table.limit.Limit;

import com.cyberway.core.utils.JavaScriptEscape;
import com.cyberway.core.utils.ServiceLocator;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 最基础的一个Controller，供大家继承
 * @author caiw
 * 
 */
abstract public class BaseController extends ActionSupport {
	
	public class MapBrowse {

	}
	protected Log logger = LogFactory.getLog(getClass());
	
	private static final long serialVersionUID = 7789982561394923307L;

	protected static final String PRIMARY_KEY = "id"; // 持久类主键对应的页面参数名称

	private ActionContext context; // Webwork的ActionContext

	private String defaultResult; // Action默认的result，即默认execute的返回值
	
	protected Limit ecLimit;
	
	public Limit getEcLimit() {
		return ecLimit;
	}

	public void setEcLimit(Limit ecLimit) {
		this.ecLimit = ecLimit;
	}

	/**
	 * 获得Webwork的ActionContext
	 */
	public ActionContext getActionContext() {
		return context == null ? context = ActionContext.getContext() : context;
	}

	public String getDefaultResult() {
		return defaultResult;
	}

	public void setDefaultResult(String defaultResult) {
		this.defaultResult = defaultResult;
	}

	/**
	 * 获得当前Action的名称
	 */
	public String getActionName() {
		return getActionContext().getName();
	}

	/**
	 * 获得当前session
	 */
	public java.util.Map getSession() {
		return getActionContext().getSession();
	}
	
	/**
	 * 获得当前Http Servlet Request
	 */
	public HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) getActionContext().get(StrutsStatics.HTTP_REQUEST);
	}
	
	/**
	 * 获得当前Http Servlet Request
	 */
	public HttpServletRequest getRequest() {
		return (HttpServletRequest) getActionContext().get(StrutsStatics.HTTP_REQUEST);
	}
	
	/**
	 * 获得当前Http Servlet Response
	 */
	public HttpServletResponse getResponse() {
		return (HttpServletResponse) getActionContext().get(StrutsStatics.HTTP_RESPONSE);
	}

	/**
	 * 如果设定了defaultResult则返回defaultResult
	 */
	public String execute() throws Exception {
		return defaultResult == null ? super.execute() : defaultResult;
	}

	/**
	 * 根据指定的页面参数名称，获取页面传递来的参数值
	 * 
	 * @param parameter
	 * @return 单个对象
	 */
	protected Object getParameterValue(String parameter) {
		Object[] parameterArray = getParamenterArray(parameter);
		if (parameterArray != null && parameterArray.length == 1) {
			return parameterArray[0];
		} else {
			return null;
		}
	}
	
	/**
	 * 根据指定的页面参数名称，获取页面传递来的参数值
	 * 
	 * @param parameter
	 * @return 数组对象
	 */
	protected Object[] getParamenterArray(String parameter) {
		return (Object[]) (getActionContext().getParameters().get(parameter));
	}
	/**
	 * 根据service的ID得到Service实例
	 * @param id
	 * @return
	 */
	protected Object getServiceById(String id){
		return ServiceLocator.getBean(id);
	}
	protected Object getServiceById(Class claz){
		return ServiceLocator.getBean(claz.getName());
	}
	
	protected boolean addCookie(String key, Object value, Integer expiry){
		return addCookie(key, value, expiry, false);
	}
	
	/**
	 * expiry为空时，表示无生命周期，浏览器管理即删除
	 * @param key
	 * @param value
	 * @param expiry
	 * @param secure
	 * @return
	 */
	protected boolean addCookie(String key, Object value, Integer expiry, boolean secure){
		try{
			String val =  JavaScriptEscape.escape(value.toString());
			Cookie cookie = new Cookie(key, val);
			cookie.setPath("/");
			if(expiry!=null)
				cookie.setMaxAge(expiry);
			cookie.setSecure(secure);
			getResponse().addCookie(cookie);
			return true;
		}catch (Exception e) {
			logger.error("--", e);
		}
		return false;
	}
	
	protected void clearCookie(String key){
		Cookie cookie = new Cookie(key, "");
		cookie.setPath("/");
		cookie.setMaxAge(0);
		getResponse().addCookie(cookie);
	}
	
	protected void clearAllCookies(){
		Cookie[] cookies = getRequest().getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			cookie.setPath("/");
			cookie.setMaxAge(0);
			cookie.setValue(null);
			getResponse().addCookie(cookie);
		}
	}
	
	protected String getStringValueFromCookie(String key){
		Cookie[] cookies = getRequest().getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if(cookie.getName().equals(key)){
				return cookie.getValue();
			}
		}
		return null;
	}
	
	protected Long getLongValueFromCookie(String key){
		try{
			String value = getStringValueFromCookie(key);
			return Long.parseLong(value);
		}catch (Exception e) {
			//logger.error(e);
		}
		return null;
	}
}
