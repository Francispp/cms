var BeanShellScript = Component.extend(
{
	initialize : function ()
	{
		this.parent ("img", 
			"BeanShellScript", 
			"CMS_BEANSHELLSCRIPT", 
			"beanShellScript", 
			"400", 
			"360", 
			"动态脚本",
			"plugins/cms/images/beanShellScript.gif");
			
		this.addEvent ("onPageInitialize", function (component)
		{
			if (component)
			{
				$("valueField").value = GetAttribute (component, "value");
				if(GetAttribute (component, "languageType"))
				 $("languageField").value = GetAttribute (component, "languageType");
			}
		}.bind (this));
		
		this.addEvent ("onComponentInitialize", function (component)
		{
			SetAttribute (component, "value", $("valueField").getValue ());
			SetAttribute (component, "languageType", $("languageField").getValue ());
		});
	}
});