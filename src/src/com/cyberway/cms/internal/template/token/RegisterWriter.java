package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;

public class RegisterWriter extends ComponentWriter{
	public RegisterWriter()
	{
		super("Register","div");
	}
	@Override
	protected boolean isAllowAttribute(AttributeToken token)
	{
		return false;
	}
	@Override
	protected void writeComponentStart(StartElementToken startElement)
	{
		String id = TokenUtils.getAttributeValue(startElement, getTemplate(), "id");
		String name = TokenUtils.getAttributeValue(startElement, getTemplate(), "_name");
		String style = TokenUtils.getAttributeValue(startElement, getTemplate(), "_style");
		String jumppath = TokenUtils.getAttributeValue(startElement, getTemplate(), "jumppath");
		String displayContent = TokenUtils.getAttributeValue(startElement, getTemplate(), "displayContent");

		if(name == null)
		{
			name = "";
		}
		if(displayContent == null)
		{
			displayContent = "";
		}
		if(jumppath == null || StringUtils.isEmpty(jumppath))
		{
			jumppath = "";
		}
		
		if (StringUtils.isBlank(id))
		{
			id = getIdAllocator().allocate(TokenUtils.getAttributeValue(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute));
		}
		getComponentIdStack().push(id);
		StringBuffer sb=new StringBuffer();
		String divid = getComponentIdStack().peek();
		getMarkupWriter().element("div","id",divid,"width","100%");
		sb.append("\n<%@ include file='/common/buffalo.inc'%>");
		sb.append("\n<%@ include file='/common/meta.inc'%>");
		sb.append("\n<%@ include file='/common/validation.inc'%>");
		sb.append("\n<%");
		sb.append("\nLoginer loginner = (Loginer)request.getSession().getAttribute(Loginer.LOGININFO_SESSION);");
		sb.append("\nif(loginner != null && loginner.getLoginType() == 2)");
		sb.append("\n{");
		sb.append("\nWebUserService webUserService = (WebUserService)ServiceLocator.getBean(\"WebUserService\");");
		sb.append("\nWebUser domain =  webUserService.get(loginner.getUserid());");
		sb.append("\nrequest.setAttribute(\"domain\",domain);");
		sb.append("\n}%>");
		sb.append("<ww:if test=\"${not empty session['loginer'] || session['loginer'].loginType == 2}\">");
		sb.append("<form method='post' name='myform' action='${ctx}/cms/webuser!registerSave.action?channelId=${channelId}&templateId=${templateId}'>");
		sb.append("</ww:if>");
		sb.append(" <ww:else>");
		sb.append("<form method='post' name='myform' action='${ctx}/cms/webuser!register.action?channelId=${channelId}&templateId=${templateId}'>");
		sb.append("</ww:else>");
	
		sb.append("\n<input type='hidden' name='jumppath' value='"+jumppath+"' />");
		sb.append("\n<ww:hidden id='oid' name='domain.oid' value='${domain.oid}'></ww:hidden>");
		sb.append("\n<ww:hidden id='siteId' name='domain.siteId' value='${domain.siteId}'></ww:hidden>");
		sb.append("\n<ww:hidden id='pageStyle' name='pageStyle'></ww:hidden>");
		sb.append("\n<ww:hidden id='groupNames' name='groupNames'></ww:hidden>");
		sb.append("\n<ww:hidden id='locked' name='domain.locked' value='false'></ww:hidden>");
		sb.append("\n<ww:hidden id='approved' name='domain.approved' value='true'></ww:hidden>");
		sb.append("\n<ww:hidden id='oldPwdStr' name='domain.oldPwdStr' value='${domain.loginpwd}'></ww:hidden>");
		sb.append("\n<input id='groupIds' type='hidden' name='groupIds' />");
		sb.append("\n<table border='0' cellSpacing='0' cellPadding='0' width='100%' align='center'>");
		sb.append("\n<tbody>");
		sb.append("\n<tr>");
		sb.append("\n<td  align='right'>用户名<span style='color: #f00'>*</span>：</td>");
		sb.append("\n<td align='left'><ww:textfield name='domain.name' cssClass='required' value='${domain.name}'></ww:textfield></td>");
		sb.append("\n</tr>");
		sb.append("\n<tr>");
		sb.append("\n<td  align='right'>登录名<span style='color: #f00'>*</span>：</td>");
		sb.append("\n<td align='left'><ww:textfield name='domain.loginname' cssClass='required' value='${domain.loginname}'></ww:textfield></td>");
		sb.append("\n</tr>");
		
		sb.append("\n<c:if test=\"${domain.loginpwd!=null}\">");
		//是否修改密码控制
		if(displayContent.indexOf("r_uloginpwd") != -1){
		sb.append("\n<tr>");
		sb.append("\n<td  width='37%' align='right'>是否修改密码</td>");
		sb.append("\n<td width='63%' align='left'><ww:checkbox name='domain.uloginpwd' cssClass='' value='${domain.uloginpwd}' onclick=\"showpwd()\"></ww:checkbox></td>");
		sb.append("\n</tr>");	
		}
		
		if(displayContent.indexOf("r_oldloginpwd") != -1)
		{
		sb.append("\n<tr id='r_oldloginpwd' style='display:none'>");
		sb.append("\n<td  width='37%' align='right'>旧密码：</td>");
		sb.append("\n<td width='63%' align='left'><ww:textfield name='domain.oldloginpwd' cssClass='' value='${domain.oldloginpwd}'></ww:textfield></td>");
		sb.append("\n</tr>");
		}
		else
		{
			sb.append("\n<ww:hidden name='domain.oldloginpwd' value='${domain.oldloginpwd}'/>");
		}
		//当注册需要显示，修改不需要显示
		sb.append("\n<tr id='r_loginpwd' style='display:none'>");
		sb.append("\n</c:if>");
		
		sb.append("\n<c:if test=\"${domain.loginpwd==null}\">");
		sb.append("\n<tr id='r_loginpwd'>");
		sb.append("\n</c:if>");
		
		sb.append("\n<td  width='37%' align='right'>密码<span style='color: #f00'>*</span> ：</td>");
		sb.append("\n<td align='left'><ww:password id='loginpwd' name='domain.loginpwd' value='${domain.loginpwd}'  cssClass='required min-length-4'></ww:password></td>");
		sb.append("\n</tr>");
		

		
		sb.append("\n<% String str=request.getParameter(\"checkoldpwd\"); ");
		sb.append("\n if(str!=null){ %>");
		
		sb.append("\n<script type='text/javascript'>");
		sb.append("\nalert('旧密码输入错误');");
		sb.append("\n</script>");
		
		
		sb.append("\n <% }%>");
		
		if(displayContent.indexOf("r_phone") != -1)
		{
		sb.append("\n<tr>");
		sb.append("\n<td  width='37%' align='right'>电话：</td>");
		sb.append("\n<td width='63%' align='left'><ww:textfield name='domain.phone' cssClass='validate-phone' value='${domain.phone}'></ww:textfield></td>");
		sb.append("\n</tr>");
		}
		else
		{
			sb.append("\n<ww:hidden name='domain.phone' value='${domain.phone}'/>");
		}
		if(displayContent.indexOf("r_mobilephone") != -1)
		{
		sb.append("\n<tr>");
		sb.append("\n<td  width='37%' align='right'>移动电话：</td>");
		sb.append("\n<td width='63%' align='left'><ww:textfield name='domain.mobilephone' cssClass='validate-mobile-phone' value='${domain.mobilephone}'></ww:textfield></td>");
		sb.append("\n</tr>");
		}
		else
		{
			sb.append("\n<ww:hidden name='domain.mobilephone' value='${domain.mobilephone}'/>");
		}
		if(displayContent.indexOf("r_sex") != -1)
		{
		sb.append("\n<tr>");
		sb.append("\n<td align='right'>性别：</td>");
		sb.append("\n<td align='left'><c:set value=\"'男','女'\" var='arr'></c:set><ww:radio name='domain.sex' value='${domain.sex}' cssClass='' list=\"#{'男':'男','女':'女'}\"></ww:radio></td>");
		sb.append("\n</tr>");
		}
		else
		{
			sb.append("\n<ww:hidden name='domain.sex' value='${domain.sex}'/>");
		}
		
		
		if(displayContent.indexOf("r_email") != -1)
		{
		sb.append("\n<tr>");
		sb.append("\n<td  align='right'>电子邮件：</td>");
		sb.append("\n<td align='left'><ww:textfield name='domain.email' cssClass='validate-email' value='${domain.email}'></ww:textfield></td>");
		sb.append("\n</tr>");
		}
		else
		{
			sb.append("\n<ww:hidden name='domain.email' value='${domain.email}'/>");
		}
		if(displayContent.indexOf("r_qq") != -1)
		{
		sb.append("\n<tr>");
		sb.append("\n<td  align='right'>QQ号码：</td>");
		sb.append("\n<td align='left'><ww:textfield name='domain.qq'  value='${domain.qq}' cssClass='required'></ww:textfield></td>");
		sb.append("\n</tr>");
		}
		else
		{
			sb.append("\n<ww:hidden name='domain.qq' value='${domain.qq}'/>");
		}
		sb.append("\n<tr>");
		sb.append("\n<td height='45' align='center' colspan='2'><input name='Submit22' onclick='save();' type='button' value='提交'></input>&nbsp;<input name='back' onclick='history.back();' type='button' value='返回'></input></td>");
		sb.append("\n</tr>");
		
		sb.append("\n</tbody>");
		sb.append("\n</table>");
		sb.append("\n</form>");
		sb.append("\n<script type='text/javascript'>");
		sb.append("\nvar valid = new Validation('myform',{immediate:true});");
		sb.append("\nfunction save(){if(valid.validate()){myform.submit();}}");
		sb.append("\n function showpwd(){if(document.getElementById('r_oldloginpwd').style.display=='none'){document.getElementById('r_oldloginpwd').style.display='block';}else{document.getElementById('r_oldloginpwd').style.display='none'}");
		sb.append("\n if(document.getElementById('r_loginpwd').style.display=='none'){document.getElementById('r_loginpwd').style.display='block';}else{document.getElementById('r_loginpwd').style.display='none';}}");
		sb.append("\n</script>");
		getMarkupWriter().writeRaw(sb.toString());
		
	}
	
	@Override
	protected void writeComponentEnd()
	{
		super.writeComponentEnd();
	

	}

}
