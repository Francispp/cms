var Photo = Component.extend(
{
	initialize : function ()
	{	
		this.parent ("object", 
			"Photo",  
			"CMS_PHOTO", 
			"photo", 
			"340", 
			"530", 
			"图片上传标签",
			"plugins/cms/images/photo.gif");
		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
		this.views = [
			{name : "Basic", title : "基本", contentEl : "basicView"},
			{name : "PrivilegeScript", title : "权限控制", contentEl : "privilegeScriptView", page : basepath + "privilegeScript.html" }
		]
		this.addEvent ("onHandlePage", function (editor)
		{
			Ext.onReady (function ()
			{
				var store = new Ext.data.SimpleStore({fields : ["_name", "value"], data : $A(editor.parent.getFormFields().options).map (function (item)
				{
					return [item.text, item.value];
				})});
				var comboBox = new Ext.form.ComboBox ({id : "nameField", applyTo : "nameField", mode : "local", store : store, width : 290, displayField : "_name", valueField : "value", triggerAction : "all"});
			});
		});
		this.addEvent ("onPageInitialize", function (component)
		{
			if (component)
			{
				var fileTypeFields = "";
				fileTypeFields = GetAttribute (component, "optionFileTypeList");
				$("privilegeScriptField").value = GetAttribute (component, "privilegeScript");
				$("readOnlyPrivilegeScriptField").value = GetAttribute (component, "readOnlyPrivilegeScriptField");
				$("hiddenPrivilegeScriptField").value = GetAttribute (component, "hiddenPrivilegeScriptField");
				$("nameField").value = GetAttribute (component, "_name");
				
				Ext.onReady (function ()
				{
					Ext.getCmp("nameField").store.each (function (record)
					{
						if (record.get("value") == GetAttribute (component, "_name"))
						{
							$("nameField").value = record.get("_name");
						}
					});
				});
				$("imgWidth").value = GetAttribute (component, "imgWidth");
				$("imgHeight").value = GetAttribute (component, "imgHeight");
				$("fileSizeField").value = GetAttribute (component, "fileSizeField");
				
				$("descriptionField").value = GetAttribute (component, "title");
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
			SetAttribute(component, "privilegeScript", $("privilegeScriptField").getValue ());
			SetAttribute(component, "readOnlyPrivilegeScriptField", $("readOnlyPrivilegeScriptField").getValue ());
			SetAttribute(component, "hiddenPrivilegeScriptField", $("hiddenPrivilegeScriptField").getValue ());
			SetAttribute(component, "_name", $("nameField").getValue ());
			Ext.getCmp("nameField").store.each (function (record)
			{
				if (record.get("_name") == $("nameField").getValue ())
				{
					SetAttribute (component, "_name", record.get("value"));
				}
			});
			SetAttribute(component, "imgWidth", $("imgWidth").getValue ());
			SetAttribute(component, "imgHeight", $("imgHeight").getValue ());
			SetAttribute(component, "fileSizeField", $("fileSizeField").getValue ());
	        SetAttribute(component, "optionFileTypeList", $("optionFileTypeList").getValue ());
	        $A($("optionFileTypeList").options).each (function (option){
	    			fileTypeFields.push(option.value);
	    	});
	    	SetAttribute (component, "optionFileTypeList", String(fileTypeFields));
			SetAttribute(component, "_style", $("styleField").getValue ());
			SetAttribute(component, "title", $("descriptionField").getValue ());
		}.bind (this));
	}
})