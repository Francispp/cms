package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;

public class LoginWriter extends ComponentWriter{

	public LoginWriter()
	{
		super("Login","");
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
		String style = TokenUtils.getAttributeValue(startElement, getTemplate(), "_style");
		String jumppath = TokenUtils.getAttributeValue(startElement, getTemplate(), "jumppath");
		String member = TokenUtils.getAttributeValue(startElement, getTemplate(), "member");
		String sucpath = TokenUtils.getAttributeValue(startElement, getTemplate(), "sucpath");
		String logoupath = TokenUtils.getAttributeValue(startElement, getTemplate(), "logoupath");
		
		if (StringUtils.isBlank(id))
		{
			id = getIdAllocator().allocate(TokenUtils.getAttributeValue(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute));
		}
		if(StringUtils.isBlank(style))
		{
			style ="";
		}
		
		getComponentIdStack().push(id);
		StringBuffer sb=new StringBuffer();
		
		
		
		sb.append("<div class='"+style+"'>");
		sb.append("<form action='/cms/webuser!login.action' method='post' onsubmit=\"if(memberloginname.value==''){alert('用户名不能为空');return false;}if(memberloginpwd.value==''){alert('密码不能为空');return false;}\">");
		sb.append("<input name='jumppath' value='"+sucpath+"'  type='hidden' />");
		sb.append("<input name='loginUrl' value='"+sucpath+"'  type='hidden' />");
		
		sb.append("<table border='0' cellPadding='0' cellSpacing='0' class='l_table'><tbody>");
		sb.append("<ww:if test=\"${empty session['loginer'] || session['loginer'].loginType != 2}\">");
		sb.append("<tr><td class='l_td_user'>用户名：</td>");
		sb.append("<td class='l_td_user_input'><input id='memberloginname' name='domain.loginname'  type='text'></input></td>");
		sb.append("<td class='l_td_user'>密码：</td>");
		sb.append("<td><input id='memberloginpwd' name='domain.loginpwd'  type='password'></input></td>");
		sb.append("<td><input name='Submit3' type='submit' value='登录'></input> </td>");
		sb.append("<td> <input name='Submit22' onclick=\"location='"+jumppath+"'\" type='button' value='注册'></input></td>");
		sb.append("</tr></ww:if><ww:elseif test=\"${session['loginer'].loginType == 2}\">");
		sb.append("<tr><td>会员名称：</td>");
		sb.append("<td >${session['loginer'].username}</td>");
		sb.append("<td>登录时间</td>");
		sb.append("<td><ww:date format='dd/MM/yyyy hh:mm' name=\"#session['loginer'].loginTime\"></ww:date></td>");
		sb.append("<td><a href='"+member+"'>会员中心 </a>| <a href='"+jumppath+"'>修改用户信息 </a>| <a href='/cms/webuser!logout.action?jumppath="+logoupath+"' >&nbsp;退出</a></td>");
		sb.append("</tr></ww:elseif></tbody> </table>");
		sb.append("</form>");
		sb.append("</div>");
		getMarkupWriter().writeRaw(sb.toString());
		
	}
	
	@Override
	protected void writeComponentEnd()
	{
	}
}
