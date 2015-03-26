var Attach = Component.extend(
{
	initialize : function ()
	{	
		this.parent ("object", 
			"Attach",  
			"CMS_ATTACH", 
			"attach", 
			"340", 
			"300", 
			"附件上传",
			"plugins/cms/images/attach.gif");
		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
		this.views = [
			{name : "Basic", title : "基本", contentEl : "basicView"},
			{name : "PrivilegeScript", title : "权限控制", contentEl : "privilegeScriptView", page : basepath + "privilegeScript.html" }
		]
		
		
		this.addEvent ("onPageInitialize", function (component)
		{
			if (component)
			{
				var fileTypeFields = "";
				fileTypeFields = GetAttribute (component, "optionFileTypeList");
				$("nameField").value = GetAttribute (component, "_name");
				$("fileSizeField").value = GetAttribute (component, "fileSizeField");
				$("privilegeScriptField").value = GetAttribute (component, "privilegeScript");
				$("readOnlyPrivilegeScriptField").value = GetAttribute (component, "readOnlyPrivilegeScriptField");
				$("hiddenPrivilegeScriptField").value = GetAttribute (component, "hiddenPrivilegeScriptField");
				$("descriptionField").value = GetAttribute (component, "descriptionField");
				$A($("styleField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "_style");
				});
				if(fileTypeFields != null && fileTypeFields.length > 0)
				{
				
				var rt = new Array();
				if(String(fileTypeFields).indexOf(",") != -1)
				{
				rt = String(fileTypeFields).split(",");
				
				for(var i=0;i<rt.length;i++)
				{
					var option = document.createElement ("OPTION");
					
					$("optionFileTypeList").appendChild (option);
					
					option.value = option.innerText = rt[i]; 
				}
			       }
			       else
			       {
			       	var option = document.createElement ("OPTION");
			       	$("optionFileTypeList").appendChild (option);
			       	option.value = option.innerText = fileTypeFields;
			       	}
			       }
				
				
			}
		}.bind (this));
		
		this.addEvent ("onComponentInitialize", function (component)
		{      
			var fileTypeFields = [];
			SetAttribute(component, "_name", $("nameField").getValue ());
			SetAttribute(component, "fileSizeField", $("fileSizeField").getValue ());
		        SetAttribute(component, "optionFileTypeList", $("optionFileTypeList").getValue ());
			SetAttribute(component, "privilegeScript", $("privilegeScriptField").getValue ());
			SetAttribute(component, "readOnlyPrivilegeScriptField", $("readOnlyPrivilegeScriptField").getValue ());
			SetAttribute(component, "hiddenPrivilegeScriptField", $("hiddenPrivilegeScriptField").getValue ());
			SetAttribute(component, "_style", $("styleField").getValue ());
			SetAttribute(component, "descriptionField", $("descriptionField").getValue ());
			$A($("optionFileTypeList").options).each (function (option)
			{
			fileTypeFields.push(option.value);
				
			});
			SetAttribute (component, "optionFileTypeList", String(fileTypeFields));

		}.bind (this));
	}
})
