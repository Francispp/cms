package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.common.utils.BodyBuilder;

public class CheckboxListWriter extends FormFieldWriter
{

	public CheckboxListWriter()
	{
		super ("CheckboxList", "");
	}

	@Override
	protected boolean isAllowAttribute(AttributeToken token) {
		boolean result = super.isAllowAttribute(token);
		if (result)
		{
			result = !ObjectUtils.equals(token.getName(), "_type") &&
						!ObjectUtils.equals(token.getName(), "parentId");
		}
		return result;
	}
	
	@Override
	protected void writeComponentStart(StartElementToken startElement) {
		String id = TokenUtils.getAttributeValue(startElement, getTemplate(), "id");
		if (StringUtils.isBlank(id))
		{
			id = getIdAllocator().allocate(TokenUtils.getAttributeValue(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute));
		}		
		getComponentIdStack().push(id);
		getMarkupWriter().element("div", "id", getComponentIdStack().peek(), "class", "boxlist");
	}
	
	@Override
	protected void writeComponentEnd(){
		String id = getComponentIdStack().peek();
		String type = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "_type");//下拉的来源类型
		String name = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "_name");
		String parentId = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "parentId");//下拉数据的查询条件值;
		String clazz = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "class");
		if(!name.startsWith("domain.")){
			name = "domain."+name;
		}
		if(clazz==null){
			clazz = "";
		}
		if("CoreCommonInfo".equals(type)){//类别表数据}
			BodyBuilder jsp = new BodyBuilder();
			jsp.begin();
			jsp.addln("String val = (String)request.getAttribute(\""+name+"\");");
			jsp.addln("com.cyberway.common.commoninfo.service.CommonInfoService service=(com.cyberway.common.commoninfo.service.CommonInfoService)ServiceLocator.getBean(\"commonInfoService\");");
			jsp.addln("List<com.cyberway.common.domain.CoreCommonInfo> list"+id+" = null;");
			try{
				jsp.addln("list"+id+" = service.getCoreCommonInfosByCommonTypeId("+Long.parseLong(parentId)+"L);");
			}catch (Exception e) {
				jsp.addln("String pid = (String)request.getAttribute(\"domain."+parentId+"\");");
				jsp.addln("list"+id+" = service.getCoreCommonInfosByCommonTypeId(Long.parseLong(pid));");
			}
			jsp.addln("if(list"+id+"!=null && list"+id+".size()>0){");
			jsp.addln("for(com.cyberway.common.domain.CoreCommonInfo info : list"+id+"){");
			jsp.addln("out.write(\"\\n<input type='checkbox' class='"+clazz+"' name='"+name+"' value='\"+info.getCode()+\"'\");");
			jsp.addln("if(val!=null){");
			jsp.addln("String[] arr = val.split(\",\");");
			jsp.addln("for(String str : arr){");
			jsp.addln("if(info.getCode().equals(str.trim()))");
			jsp.addln("out.write(\" checked=true\");");
			jsp.addln("}");
			jsp.addln("}");
			jsp.addln("out.write(\"/>\");");
			jsp.addln("out.write(info.getContent());");
			jsp.addln("out.write(\"&nbsp;&nbsp;\");");
			jsp.addln("}");
			jsp.addln("}");
			jsp.end();
			getMarkupWriter().writeRaw("<%"+jsp.toString()+"%>");
		}
		super.writeComponentEnd();
	}
}