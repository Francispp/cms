package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.common.utils.BodyBuilder;
import com.cyberway.core.utils.StringUtil;

/**
 * @author caiw
 * 显示内容标签的过滤器
 *
 */
public class PropertyWriter extends ComponentWriter
{
	public PropertyWriter()
	{
		super ("Property", "");
	}
	
	@Override
	protected boolean isAllowAttribute(AttributeToken token) 
	{
		return false;
	}
/*	@Override
	protected void writeAttribute(AttributeToken attribute)
	{
		if (StringUtils.equalsIgnoreCase(attribute.getName(), "_name"))
		{
			
			String value=attribute.getValue();
			if(value!=null && value.indexOf(".")<0)//若名称未带.自去增加domain
				value="domain."+value;
			getMarkupWriter().attributes("value", attribute.getValue());
		}
		else
		{
			super.writeAttribute(attribute);
		}
	}	*/
	@Override
	protected void writeComponentStart(StartElementToken startElement) 
	{
		
		
		String name = TokenUtils.getAttributeValue(startElement, getTemplate(), "_name");
		String officeOcx = TokenUtils.getAttributeValue(startElement, getTemplate(), "officeOcx");
		String style = TokenUtils.getAttributeValue(startElement, getTemplate(), "_style");
		String photo = TokenUtils.getAttributeValue(startElement, getTemplate(), "photo");
		String imgWidth = TokenUtils.getAttributeValue(startElement, getTemplate(), "imgWidth");
		String imgHeight = TokenUtils.getAttributeValue(startElement, getTemplate(), "imgHeight");
		String isDate = TokenUtils.getAttributeValue(startElement, getTemplate(), "dateField");
		String formatType = TokenUtils.getAttributeValue(startElement, getTemplate(), "formatField");
		String type = TokenUtils.getAttributeValue(startElement, getTemplate(), "type");
		String parentId = TokenUtils.getAttributeValue(startElement, getTemplate(), "parentId");
		String nullToBlank = TokenUtils.getAttributeValue(startElement, getTemplate(), "nullToBlank");
		String defValue = TokenUtils.getAttributeValue(startElement, getTemplate(), "defValue");
		String escape = TokenUtils.getAttributeValue(startElement, getTemplate(), "escape");
		String province = TokenUtils.getAttributeValue(startElement, getTemplate(), "province");
		String city = TokenUtils.getAttributeValue(startElement, getTemplate(), "city");
		if(!StringUtil.isEmpty(name) && name.indexOf(".")<0)//若名称未带.自去增加domain
			name="domain."+name;
		if("CoreCommonInfo".equals(type)){
			BodyBuilder jsp = new BodyBuilder ();
			jsp.begin();
			jsp.addln("String val = (String)request.getAttribute(\""+name+"\");");
			jsp.addln("com.cyberway.common.commoninfo.service.CommonInfoService service=(com.cyberway.common.commoninfo.service.CommonInfoService)ServiceLocator.getBean(\"commonInfoService\");");
			jsp.addln("String trueContent = service.getCommonInfoContentByCodeAndType("+parentId+"L, val);");
			jsp.addln("out.write(trueContent==null ? \"\" : trueContent);");
			jsp.end();
			getMarkupWriter().writeRaw("<%"+jsp.toString()+"%>");
			getMarkupWriter().element("span");
		}else if("CoreArea".equals(type)){
			BodyBuilder jsp = new BodyBuilder ();
			jsp.begin();
			jsp.addln("String provinceVal = (String)request.getAttribute(\""+province+"\");");
			jsp.addln("String cityVal = (String)request.getAttribute(\""+city+"\");");
			jsp.addln("com.cyberway.common.area.service.AreaService service=(com.cyberway.common.area.service.AreaService)ServiceLocator.getBean(\"areaService\");");
			jsp.addln("String trueContent = service.getDetailById(provinceVal,cityVal);");
			jsp.addln("out.write(trueContent==null ? \"\" : trueContent);");
			jsp.end();
			getMarkupWriter().writeRaw("<%"+jsp.toString()+"%>");
			getMarkupWriter().element("span");
		}else{
			String valuePrefix = TokenUtils.getAttributeValue(startElement, getTemplate(), "valuePrefix");
			String valuePostfix = TokenUtils.getAttributeValue(startElement, getTemplate(), "valuePostfix");
			
			if (StringUtils.isBlank(name))
			{
				name="";
			}
			if (StringUtils.isBlank(escape))
			{
				escape="false";
			}
			 if(StringUtils.isEmpty(imgWidth))
		        {
		        	imgWidth = "";
		        }
		      if(StringUtils.isEmpty(imgHeight))
		        {
		        	imgHeight = "";
		        }
		      if(StringUtils.isEmpty(isDate))
		        {
		    	  isDate = "";
		        }
		      if(StringUtils.isEmpty(formatType))
		        {
		    	  formatType = "";
		        }
		      if(StringUtils.isEmpty(valuePrefix) || valuePrefix == null)
		        {
		    	  valuePrefix = "";
		        }
		      if(StringUtils.isEmpty(valuePostfix) || valuePrefix == null)
		        {
		    	  valuePostfix = "";
		        }
			//getComponentIdStack().push(id);
			
			
			if(!StringUtils.isEmpty(photo)&& photo.equals("1"))
			{
				
				if(StringUtils.isBlank(imgWidth)){
					imgWidth = "";
				}else{
					imgWidth = " width=\""+imgWidth+"\"";
				}
				if(StringUtils.isBlank(imgHeight)){
					imgHeight = "";
				}else{
					imgHeight = " height=\""+imgHeight+"\"";
				}
				if(StringUtils.isBlank(defValue)){
					getMarkupWriter().writeRaw("<img src=\"${ctx}${"+name+"}\" "+imgWidth+" "+imgHeight+"/>");
				}else{
					getMarkupWriter().writeRaw("<img src=\"${ctx}${("+name+"==null || "+name+"=='')? '"+defValue+"' : "+name+"}\" "+imgWidth+" "+imgHeight+"/>");
				}
			}
			else
			{
				if(!StringUtils.isEmpty(officeOcx)&&officeOcx.equals("1")){
				 StringBuffer sb=new StringBuffer();
				  sb.append("<script src=\"${ctx}/common/cms_js/util.js\" type=\"text/javascript\"></script>\n");
				  getMarkupWriter().writeRaw(sb.toString());
				}
				//增加样式控制
				if(!StringUtil.isEmpty(style)){
					 StringBuffer sb=new StringBuffer();
					  sb.append("<label class=\""+style+"\" >");
					  getMarkupWriter().writeRaw(sb.toString());
					}
				
				//getMarkupWriter().element(getElementName(),"value",name);
				boolean isOffecOcx = false;
				if(!StringUtils.isEmpty(officeOcx)&&officeOcx.equals("1")){
					isOffecOcx = true;
					//getMarkupWriter().attributes("isOffecOcx","true")
				}		
				boolean isDateType = false;
				if(!StringUtils.isEmpty(isDate)&&isDate.equals("1")){
					isDateType = true;
				}
				//判断name 是否是空
				//name=valuePrefix+name+valuePostfix;
				getMarkupWriter().element("ww:if","test",name+"!=null"+"&&"+name+".toString()!=''");
				if(StringUtils.isNotBlank(valuePrefix)){
					getMarkupWriter().element("c:out","value",valuePrefix);
					getMarkupWriter().end();
				}
				getMarkupWriter().element("cms:property", "value",name,"isOffecOcx",isOffecOcx,"isDate",isDateType,"formatType",formatType,"escape",escape);
				getMarkupWriter().end();
				if(StringUtils.isNotBlank(valuePostfix)){
					getMarkupWriter().element("c:out","value",valuePostfix);
					getMarkupWriter().end();
				}

				if(nullToBlank!=null&&"true".equals(nullToBlank.toLowerCase())){
					getMarkupWriter().end();
					getMarkupWriter().element("ww:else");
					getMarkupWriter().writeRaw("&nbsp;");
				}
				//super.writeComponentStart(startElement);
				
				//String name = TokenUtils.getAttributeValue(startElement, getTemplate(), "name");		
			}	
		}
	}
	
	@Override
	protected void writeComponentEnd() 
	{		
		String photo = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "photo");
		if(!"1".equals(photo)){
			super.writeComponentEnd();
		}
		String style = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "_style");
		
		//增加样式结束符
		if(!StringUtil.isEmpty(style) && (StringUtils.isEmpty(photo)|| !photo.equals("1"))){
			 StringBuffer sb=new StringBuffer();
			  sb.append("</label>");
			  getMarkupWriter().writeRaw(sb.toString());
			}		
	}
}
