package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.TemplateToken;
import com.cyberway.common.utils.BodyBuilder;

public class SelectWriter extends FormFieldWriter
{
	public SelectWriter()
	{
		super ("Select", "select");
	}
	
	@Override
	protected boolean isAllowBody(TemplateToken token) 
	{
		return true;
	}
	
	@Override
	protected boolean isAllowAttribute(AttributeToken attribute)
	{
		if (StringUtils.equalsIgnoreCase(attribute.getName(), "_name")
				|| StringUtils.equalsIgnoreCase(attribute.getName(), "id")
				|| StringUtils.equalsIgnoreCase(attribute.getName(), "_style")
				|| StringUtils.equalsIgnoreCase(attribute.getName(), "onchange")
				|| StringUtils.equalsIgnoreCase(attribute.getName(), "class"))
		{
			return true;
		}
		return false;
	}
	
	@Override
	protected void writeComponentEnd()
	{
		//StartElementToken startElement = getComponentStack().peek();
		String id = getComponentIdStack().peek();
		String name=TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "_name");
		String type = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "type");//下拉的来源类型
		String parentId = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "parentId");//下拉数据的查询条件值;如果是数字是固定值，如果是字母是动态值。
		String headKey = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "headKey");
		String headText = TokenUtils.getAttributeValue(getComponentStack().peek(), getTemplate(), "headText");
		if(headKey!=null && StringUtils.isNotBlank(headText)){
			getMarkupWriter().element("option", "value", headKey);
			getMarkupWriter().write(headText);
			getMarkupWriter().end();
		}
		if(!name.startsWith("domain.")){
			name = "domain."+name;
		}
		if("CoreCommonInfo".equals(type)){//类别表数据
			BodyBuilder jsp = new BodyBuilder ();
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
			jsp.addln("out.write(\"\\n<option value=\"+info.getCode()+\"\");");
			jsp.addln("if(val!=null && val.equals(info.getCode()))");
			jsp.addln("out.write(\" selected=true\");");
			jsp.addln("out.write(\">\");");
			jsp.addln("out.write(info.getContent());");
			jsp.addln("out.write(\"</option>\");");
			jsp.addln("}");
			jsp.addln("}");
			jsp.end();
			getMarkupWriter().writeRaw("<%"+jsp.toString()+"%>");
			super.writeComponentEnd();
		}else if("CoreArea".equals(type)){//地区表数据
			BodyBuilder jsp = new BodyBuilder ();
			jsp.begin();
			jsp.addln("String val = (String)request.getAttribute(\""+name+"\");");
			jsp.addln("com.cyberway.common.area.service.AreaService service=(com.cyberway.common.area.service.AreaService)ServiceLocator.getBean(\"areaService\");");
			jsp.addln("List<com.cyberway.common.domain.CoreArea> list"+id+" = null;");
			try{
				if(StringUtils.isBlank(parentId)){
					jsp.addln("list"+id+" = service.getProvinces();");
				}else if(parentId.length()==2){
					jsp.addln("list"+id+" = service.getCities(\""+Long.parseLong(parentId)+"\");");
				}else if(parentId.length()==4){
					jsp.addln("list"+id+" = service.getCounties(\""+Long.parseLong(parentId)+"\");");
				}else{
					Long.parseLong(parentId);
				}
			}catch (NumberFormatException e) {
				jsp.addln("String pid = (String)request.getAttribute(\"domain."+parentId+"\");");
				jsp.addln("if(pid!=null)");
				jsp.addln("list"+id+" = service.getCounties(pid);");
			}catch (Exception e) {
				e.printStackTrace();
			}
			jsp.addln("if(list"+id+"!=null && list"+id+".size()>0){");
			jsp.addln("for(com.cyberway.common.domain.CoreArea info : list"+id+"){");
			jsp.addln("out.write(\"\\n<option value=\"+info.getId()+\"\");");
			jsp.addln("if(val!=null && val.equals(info.getId()))");
			jsp.addln("out.write(\" selected=true\");");
			jsp.addln("out.write(\">\");");
			jsp.addln("out.write(info.getName());");
			jsp.addln("out.write(\"</option>\");");
			jsp.addln("}");
			jsp.addln("}");
			jsp.end();
			getMarkupWriter().writeRaw("<%"+jsp.toString()+"%>");
			super.writeComponentEnd();
		}else{
			super.writeComponentEnd();
			BodyBuilder script = new BodyBuilder ();
			script.addln("if(\"${%s}\"!=\"\"){",name);
			script.addln("var str;var strn=$(\"%s\");if(\"${%s}\".indexOf(', ')!=-1){str=\"${%s}\".split(', ');for(var i=0;i<strn.length;i++){for(var j=0;j<str.length;j++){if(strn[i].value==str[j]){strn[i].selected=true;}}}}else{for(var i=0;i<strn.length;i++){if(strn[i].value==\"${%s}\"){strn[i].selected=true;}}}}" ,id,name,name,name);
			getMarkupWriter().element("script", "type", "text/javascript");
			getMarkupWriter().writeRaw(script.toString());
			getMarkupWriter().end();
		}
	}
	
	@Override
	protected void writeValueScript(String valueScript)
	{
		valueScript = StringEscapeUtils.unescapeHtml(valueScript);
		valueScript = StringEscapeUtils.unescapeJava(valueScript);
		
		//demo script------var datas=COMMONSERVICE.getCommonInfosByType('test');if(datas!=null){var iter=datas.iterator();var rt ='';while(iter.hasNext()){var obj=iter.next();rt+=obj.getCode()+'@'+obj.getContent()+';';}$HTML.print(rt);}
		BodyBuilder script = new BodyBuilder ();
		script.begin();
		script.addln("var value = \"%s\";","<%=" + String.format("ObjectUtils.toString (scriptEngine.eval (\"%s\"))",valueScript) + "%>");
		script.addln("try{");
		script.addln("var rt = value.split(';');");
		script.addln("for(var i=0;i<rt.length;i++){");
		script.addln("var op = rt[i].split('@');");
		script.addln("var oOption = document.createElement('OPTION');");
		script.addln("oOption.text=op[0];");
		script.addln("oOption.value=op[1];");
		script.addln("$(\"%s\").add(oOption);", getComponentIdStack().peek());
		script.addln("}");
		script.addln("}catch(e){}");
		//	script.addln("$A($(\"%s\").options).each (function (option) () { if (option.value == value) { option.selected = option.value == value; } });");
		script.end();
		
		getMarkupWriter().element("script", "type", "text/javascript");
		getMarkupWriter().writeRaw(script.toString());
		getMarkupWriter().end();
	}
}