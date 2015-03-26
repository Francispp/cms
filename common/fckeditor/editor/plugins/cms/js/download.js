var Download = Component.extend(
{
	initialize : function ()
	{	
		this.parent ("object", 
			"Download",  
			"CMS_DOWNLOAD", 
			"download", 
			"340", 
			"420", 
			"附件下载标签",
			"plugins/cms/images/download.gif");
		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
		this.views = [
			{name : "Basic", title : "基本", contentEl : "basicView"},
			{name : "PrivilegeScript", title : "权限控制", contentEl : "privilegeScriptView", page : basepath + "privilegeScript.html" }
		]
		
		
		this.addEvent ("onPageInitialize", function (component)
		{
			if (component)
			{
				
				$("nameField").value = GetAttribute (component, "_name");
				$("privilegeScriptField").value = GetAttribute (component, "privilegeScript");
				$("readOnlyPrivilegeScriptField").value = GetAttribute (component, "readOnlyPrivilegeScriptField");
				$("hiddenPrivilegeScriptField").value = GetAttribute (component, "hiddenPrivilegeScriptField");
				$("descriptionField").value = GetAttribute (component, "descriptionField");
				$A($("styleField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "_style");
				});
				
				
				
			}
		}.bind (this));
		
		this.addEvent ("onComponentInitialize", function (component)
		{      
			SetAttribute(component, "_name", $("nameField").getValue ());
			SetAttribute(component, "privilegeScript", $("privilegeScriptField").getValue ());
			SetAttribute(component, "readOnlyPrivilegeScriptField", $("readOnlyPrivilegeScriptField").getValue ());
			SetAttribute(component, "hiddenPrivilegeScriptField", $("hiddenPrivilegeScriptField").getValue ());
			SetAttribute(component, "_style", $("styleField").getValue ());
			SetAttribute(component, "descriptionField", $("descriptionField").getValue ());
	}.bind (this));
	}
})