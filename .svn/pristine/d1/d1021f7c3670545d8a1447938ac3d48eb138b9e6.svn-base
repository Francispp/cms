package com.cyberway.core.acegi.taglib;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.ExpressionEvaluationUtils;

import com.cyberway.common.resource.service.ResourceManagerService;
import com.cyberway.core.acegi.cache.AcegiCacheManager;
import com.cyberway.core.acegi.resource.ResourceDetails;


/**
 * 对用户授权进行认证的Tag, 如果用户没相应授权，则Tag中的Html代码全部不显示
 * @author cac
 */
public class AuthorizeTag extends TagSupport {

	private static final long serialVersionUID = 0L;

	private String res = "";
	private String resCode="";

	// ~ Methods
	// ========================================================================================================

	public int doStartTag() throws JspException {
		ServletContext sc = pageContext.getServletContext();
		//pageContext.getSession().getAttribute("");
		WebApplicationContext webAppCtx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(sc);
		AcegiCacheManager acegiCacheManager = (AcegiCacheManager) webAppCtx
				.getBean("acegiCacheManager");
		ResourceManagerService resourceManagerService = (ResourceManagerService) webAppCtx
		.getBean("resourceManagerService");
		//若res为空，则从resCode来取得
		if(res==null||res.length()==0){
			if(resCode==null||resCode.length()==0)
				new JspException("标签必须带一个参数！");
			res=resourceManagerService.getResourceStringFromCache(resCode);
		}
      /*  boolean hasAuth = AuthUtils.hasAuthorize(pageContext,res);
        return (hasAuth) ? Tag.EVAL_BODY_INCLUDE : Tag.SKIP_BODY;*/
		//取出 tag 的名称
		final String tag = ExpressionEvaluationUtils.evaluateString("res", res,
				pageContext);

		
		//找出相应的资源
		ResourceDetails rd = acegiCacheManager.getResourceFromCache(tag);

		Collection required;
		if (rd == null) {
			return Tag.SKIP_BODY;
		}
		
		required = Arrays.asList(rd.getAuthorities());

		
		final Collection granted = getPrincipalAuthorities();
		Collection grantedCopy = copy(granted);
		
		//判断权限
		if ((null != tag) && !"".equals(tag)) {
			grantedCopy.retainAll(required);
			if (grantedCopy.isEmpty()) 
			{
				return Tag.SKIP_BODY;
			}
		}
		return Tag.EVAL_BODY_INCLUDE;
	}
	
	@Override
	public int doAfterBody() throws JspException {
		// TODO Auto-generated method stub
		return super.doAfterBody();
	}
	
	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		
		//还原初始值
		res="";
		resCode="";
		return super.doEndTag();
	}

	/**
	 * 获取当前用户授权
	 * @return
	 */
	private Collection getPrincipalAuthorities() {
		Authentication currentUser = SecurityContextHolder.getContext()
				.getAuthentication();
		if (null == currentUser) {
			return Collections.EMPTY_LIST;
		}
		if ((null == currentUser.getAuthorities())
				|| (currentUser.getAuthorities().length < 1)) {
			return Collections.EMPTY_LIST;
		}
		return Arrays.asList(currentUser.getAuthorities());
	}

	private Set copy(Collection c) {
		Set target = new HashSet();
		for (Iterator iterator = c.iterator(); iterator.hasNext();) {
			target.add(iterator.next());
		}
		return target;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

}
