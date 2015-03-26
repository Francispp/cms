var MediaView = Component.extend(
{
	initialize : function ()
	{	
		this.parent ("object", 
			"MediaView",  
			"CMS_MEDIAVIEW", 
			"mediaView", 
			"340", 
			"460", 
			"媒体显示标签",
			"plugins/cms/images/mediaview.gif");
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
				$A($("mediaType").options).each (function (option)
						{
							option.selected = option.value == GetAttribute (component, "_mediaType");
						});
						$("mediaWidth").value = GetAttribute (component, "_mediaWidth");
						$("mediaHeight").value = GetAttribute (component, "_mediaHeight");
						
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
			SetAttribute(component, "_mediaType", $("mediaType").getValue ());
			SetAttribute(component, "_mediaWidth", $("mediaWidth").getValue ());
			SetAttribute(component, "_mediaHeight", $("mediaHeight").getValue ());	
			
			
			SetAttribute(component, "privilegeScript", $("privilegeScriptField").getValue ());
			SetAttribute(component, "readOnlyPrivilegeScriptField", $("readOnlyPrivilegeScriptField").getValue ());
			SetAttribute(component, "hiddenPrivilegeScriptField", $("hiddenPrivilegeScriptField").getValue ());
			SetAttribute(component, "_style", $("styleField").getValue ());
			SetAttribute(component, "descriptionField", $("descriptionField").getValue ());
		}.bind (this));
	}
})