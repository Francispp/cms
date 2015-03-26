var But = Component.extend(
{
	initialize : function ()
	{	
		this.parent ("object", 
			"But",  
			"CMS_BUT", 
			"but", 
			"340", 
			"400", 
			"功能按钮标签",
			"plugins/cms/images/but.gif");
		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
		this.views = [
			{name : "Basic", title : "基本", contentEl : "basicView"},
			{name : "PrivilegeScript", title : "权限控制", contentEl : "privilegeScriptView", page : basepath + "privilegeScript.html" }
		]
		
		this.addEvent ("onPageInitialize", function (component)
		{
			if (component)
			{
				//$("buttonField").value = GetAttribute (component, "buttonField");
				$A($("buttonField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "buttonField");
				});
				var iconList = "";
				iconList = GetAttribute (component, "iconList");
				if(iconList != null && iconList.length > 0)
				{
				var option = document.createElement ("OPTION");
			       	$("iconList").appendChild (option);
			       	option.value = option.innerText = iconList;
				}
				$("privilegeScriptField").value = GetAttribute (component, "privilegeScript");
				$("readOnlyPrivilegeScriptField").value = GetAttribute (component, "readOnlyPrivilegeScriptField");
				$("hiddenPrivilegeScriptField").value = GetAttribute (component, "hiddenPrivilegeScriptField");
				$("descriptionField").value = GetAttribute (component, "title");
				$A($("styleField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "_style");
				});
				
			}
		}.bind (this));
		
		this.addEvent ("onComponentInitialize", function (component)
		{    
			var iconFields = [];
			$A($("iconList").options).each (function (option)
			{
			iconFields.push(option.value);
				
			});
			SetAttribute (component, "iconList", String(iconFields));
			SetAttribute(component, "privilegeScript", $("privilegeScriptField").value);
			SetAttribute(component, "readOnlyPrivilegeScriptField", $("readOnlyPrivilegeScriptField").value);
			SetAttribute(component, "hiddenPrivilegeScriptField", $("hiddenPrivilegeScriptField").value);
			SetAttribute(component, "buttonField", $("buttonField").value);			
			SetAttribute(component, "_style", $("styleField").value);
			SetAttribute(component, "title", $("descriptionField").value);
		}.bind (this));
	}
})