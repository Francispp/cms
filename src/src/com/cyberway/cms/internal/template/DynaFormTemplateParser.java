package com.cyberway.cms.internal.template;

import com.cyberway.cms.template.Template;

public class DynaFormTemplateParser extends DefaultTemplateParser
{
	@Override
	public Template parseTemplate(String templateBody)
	{
		final Template template = super.parseTemplate(templateBody);
//		
//		CollectionUtils.forAllDo(template.getTokens(), new Closure ()
//		{
//			public void execute(Object obj)
//			{
//				if (ObjectUtils.equals(((TemplateToken)obj).getTokenType(), TokenType.Attribute))
//				{
//					AttributeToken attribute = (AttributeToken)obj;
//					
//					if (ObjectUtils.equals(attribute.getName(), "name") && !StringUtils.isBlank(attribute.getValue()))
//					{
//						StartElementToken startElement = (StartElementToken)TokenUtils.getStartElement((AttributeToken)obj, template);
//					
//						if (TokenUtils.hasAttribute(startElement, template, TemplateConstants.ComponentTypeAttribute, "TextField") ||
//							TokenUtils.hasAttribute(startElement, template, TemplateConstants.ComponentTypeAttribute, "TextArea") ||
//							TokenUtils.hasAttribute(startElement, template, TemplateConstants.ComponentTypeAttribute, "Select") ||
//							TokenUtils.hasAttribute(startElement, template, TemplateConstants.ComponentTypeAttribute, "Checkbox") ||
//							TokenUtils.hasAttribute(startElement, template, TemplateConstants.ComponentTypeAttribute, "Html") ||
//							TokenUtils.hasAttribute(startElement, template, TemplateConstants.ComponentTypeAttribute, "Radio") || 
//							TokenUtils.hasAttribute(startElement, template, TemplateConstants.ComponentTypeAttribute, "DatePicker"))
//						{
//							if (!attribute.getValue().startsWith("domain."))
//							{
//								attribute.setValue("domain." + attribute.getValue());
//							}
//						}
//					}
//				}
//			}
//		});
		
		return template;
	}
}
