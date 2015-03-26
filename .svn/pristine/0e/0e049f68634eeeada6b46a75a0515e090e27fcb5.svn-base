var Clickcount = Component.extend(
{
	initialize : function ()
	{	
		this.parent ("object", 
			"Clickcount",  
			"CMS_CLICKCOUNT", 
			"clickcount", 
			"340", 
			"400", 
			"点击率标签",
			"plugins/cms/images/clickcount.gif");
		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
		this.views = [
			{name : "Basic", title : "基本", contentEl : "basicView"},
			//{name : "HiddenScript", title : "隐藏脚本", contentEl : "hiddenScriptView", page : basepath + "hiddenScript.html" },
			{name : "PrivilegeScript", title : "权限控制", contentEl : "privilegeScriptView", page : basepath + "privilegeScript.html" }
		]
		
		this.addEvent ("onPageInitialize", function (component)
		{
			if (component)
			{
				$("clickTypeField").value = GetAttribute (component, "clickType");
				
				$("descriptionField").value = GetAttribute (component, "title");
				$A($("styleField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "_style");
				});
				if($("readOnlyPrivilegeScriptField"))
			           $("readOnlyPrivilegeScriptField").value = GetAttribute (component, "readOnlyPrivilegeScriptField");
		                if($("hiddenPrivilegeScriptField"))
			           $("hiddenPrivilegeScriptField").value = GetAttribute (component, "hiddenPrivilegeScriptField");
		                if($("privilegeScript"))  
		                   $("privilegeScriptField").value = GetAttribute (component, "privilegeScript");
			}
		}.bind (this));
		
		this.addEvent ("onComponentInitialize", function (component)
		{
			SetAttribute(component, "clickType", $("clickTypeField").getValue ());
			SetAttribute(component, "_style", $("styleField").getValue ());
			SetAttribute(component, "title", $("descriptionField").getValue ());
			if($("readOnlyPrivilegeScriptField"))
		         SetAttribute(component, "readOnlyPrivilegeScriptField", $("readOnlyPrivilegeScriptField").getValue ());
		        if($("hiddenPrivilegeScriptField"))
		         SetAttribute(component, "hiddenPrivilegeScriptField", $("hiddenPrivilegeScriptField").getValue ());			
		        if($("privilegeScriptField"))
  		         SetAttribute(component, "privilegeScript", $("privilegeScriptField").getValue ());			
		}.bind (this));
	}
})