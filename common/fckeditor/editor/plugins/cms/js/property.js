var Property = Component.extend(
{
	initialize : function ()
	{	
		this.parent ("object", 
			"Property",  
			"CMS_PROPERTY", 
			"property", 
			"340", 
			"400", 
			"显示内容标签",
			"plugins/cms/images/property.gif");
		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
		this.views = [
			{name : "Basic", title : "基本", contentEl : "basicView"},
			{name : "PrivilegeScript", title : "权限控制", contentEl : "privilegeScriptView", page : basepath + "privilegeScript.html" }
		]
		this.addEvent ("onHandlePage", function (editor)
		{
			Ext.onReady (function ()
			{
				var store = new Ext.data.SimpleStore({fields : ["name", "value"], data : $A(editor.parent.getFormFields().options).map (function (item)
				{
					return [item.text, item.value];
				})});
				var comboBox = new Ext.form.ComboBox ({id : "nameField", applyTo : "nameField", mode : "local", store : store, width : 290, displayField : "name", valueField : "value", triggerAction : "all"});
			});
		});
		this.addEvent ("onPageInitialize", function (component)
		{
			if (component)
			{
				$("nameField").value = GetAttribute (component, "_name");
				Ext.onReady (function ()
				{
					Ext.getCmp("nameField").store.each (function (record)
					{
						if (record.get("value") == GetAttribute (component, "name"))
						{
							$("nameField").value = record.get("name");
						}
					});
				});
				
				$A($("styleField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "_style");
				});
				$A($("formatField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "formatField");
				});
				
				$("officeOcxField").checked = GetAttribute (component, "officeOcx") == "1";
				$("photoField").checked = GetAttribute (component, "photo") == "1";
				$("dateField").checked = GetAttribute (component, "dateField") == "1";
				try{
				viewproperty($("photoField"),"imgProperty");
				viewproperty($("dateField"),"dateProperty");
			         }
			         catch(E){}
			         
				$("imgWidth").value = GetAttribute (component, "imgWidth");
				$("imgHeight").value = GetAttribute (component, "imgHeight");
				$("privilegeScriptField").value = GetAttribute (component, "privilegeScript");
				$("readOnlyPrivilegeScriptField").value = GetAttribute (component, "readOnlyPrivilegeScriptField");
				$("hiddenPrivilegeScriptField").value = GetAttribute (component, "hiddenPrivilegeScriptField");

				$("valuePrefix").value = GetAttribute (component, "valuePrefix");
				$("valuePostfix").value = GetAttribute (component, "valuePostfix");
										
			}
		}.bind (this));
		
		this.addEvent ("onComponentInitialize", function (component)
		{
			SetAttribute(component, "_name", $("nameField").getValue ());
			Ext.getCmp("nameField").store.each (function (record)
			{
				if (record.get("name") == $("nameField").getValue ())
				{
					SetAttribute (component, "_name", record.get("value"));
				}
			});
			SetAttribute(component, "_style", $("styleField").getValue ());
			SetAttribute(component, "formatField", $("formatField").getValue ());
			SetAttribute(component, "privilegeScript", $("privilegeScriptField").getValue ());
			SetAttribute(component, "readOnlyPrivilegeScriptField", $("readOnlyPrivilegeScriptField").getValue ());
			SetAttribute(component, "hiddenPrivilegeScriptField", $("hiddenPrivilegeScriptField").getValue ());
			SetAttribute(component, "imgWidth", $("imgWidth").getValue ());
			SetAttribute(component, "imgHeight", $("imgHeight").getValue ());	
			SetAttribute(component, "valuePrefix", $("valuePrefix").getValue ());
			SetAttribute(component, "valuePostfix", $("valuePostfix").getValue ());
			if($("officeOcxField").checked)
			 SetAttribute(component, "officeOcx", $("officeOcxField").getValue ());	
			 else
			 SetAttribute(component, "officeOcx", "");	
			 if($("photoField").checked)
			 SetAttribute(component, "photo", $("photoField").getValue ());	
			else
			 SetAttribute(component, "photo", "");	
			 if($("dateField").checked)
			 SetAttribute(component, "dateField", $("dateField").getValue ());	
			else
			 SetAttribute(component, "dateField", "");		
		}.bind (this));
	}
})