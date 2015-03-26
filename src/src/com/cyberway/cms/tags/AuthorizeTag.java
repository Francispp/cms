package com.cyberway.cms.tags;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cyberway.cms.permission.service.CmsPermissionService;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.StringUtil;


/**
 * 对用户授权进行认证的Tag, 如果用户没相应授权，则Tag中的Html代码全部不显示
 * @author cac
 */
public class AuthorizeTag extends TagSupport {

	private static final long serialVersionUID = 0L;

	private String resCode="";
	private int objectType;
	private String objectId;

	// ~ Methods
	// ========================================================================================================

	public int doStartTag() throws JspException {
		ServletContext sc = pageContext.getServletContext();
		WebApplicationContext webAppCtx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(sc);
		CmsPermissionService permService = (CmsPermissionService) webAppCtx
				.getBean("cmsPermissionService");
		
		//取出 tag 的名称
		/*final String tag = ExpressionEvaluationUtils.evaluateString("res", res,
				pageContext);*/
        if(StringUtil.isEmpty(resCode)||objectId==null)
        	return Tag.SKIP_BODY;
        Loginer loginer=(Loginer)pageContext.getSession().getAttribute(Loginer.LOGININFO_SESSION);
       
        //支持多个资源组合关系
        if(resCode.indexOf("||")>0||resCode.indexOf("&&")>0){
            boolean OR=false;
            boolean perm=true;
            String[] resCodes=null;
            if(resCode.indexOf("||")>0){
        	 OR=true;        	 
        	 resCodes=StringUtil.split(resCode,"||");
            }else
            	resCodes=StringUtil.split(resCode,"&&");
            for(int i=0;i<resCodes.length;i++){
                if(objectType==0){
                	perm=permService.haveThePermission(loginer, resCodes[i], new Long(objectId));
                	if(!perm && !OR)
            			return Tag.SKIP_BODY;                	
                }else{
                	perm=permService.haveThePermission(loginer, resCodes[i], objectType,new Long(objectId));
                	if(!perm && !OR)
            			return Tag.SKIP_BODY;        			 
        			}
                if(perm && OR)//若为｜｜，且有一个有权限时，返回包含内容
                	return Tag.EVAL_BODY_INCLUDE;
            }
            //无权限，返回
            if(OR)//若为或，返回无权限
             return Tag.SKIP_BODY; 
            else//若为与，返回有权限
             return Tag.EVAL_BODY_INCLUDE;
        }else{
        if(objectId != null && StringUtils.isNotBlank(objectId))
        {
        	//只有一个资源检测        
        if(objectType==0){
        	if(!permService.haveThePermission(loginer, resCode, new Long(objectId)))
    			return Tag.SKIP_BODY;		
        }else
		 if(!permService.haveThePermission(loginer, resCode, objectType, new Long(objectId)))
			return Tag.SKIP_BODY;		
        }
        else
        	return Tag.SKIP_BODY; 
        
        }
		return Tag.EVAL_BODY_INCLUDE;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}


}
