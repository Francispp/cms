var User = Component.extend(
{
	initialize : function ()
	{	
		this.parent ("object", 
			"User",  
			"CMS_USER", 
			"user", 
			"340", 
			"400", 
			"人员选择标签",
			"plugins/cms/images/user.gif");
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
				var comboBox1 = new Ext.form.ComboBox ({id : "idField", applyTo : "idField", mode : "local", store : store, width : 290, displayField : "_name", valueField : "value", triggerAction : "all"});
			});
		});
		this.addEvent ("onPageInitialize", function (component)
		{
			if (component)
			{
				$("privilegeScriptField").value = GetAttribute (component, "privilegeScript");
				$("readOnlyPrivilegeScriptField").value = GetAttribute (component, "readOnlyPrivilegeScriptField");
				$("hiddenPrivilegeScriptField").value = GetAttribute (component, "hiddenPrivilegeScriptField");
				$("nameField").value = GetAttribute (component, "_name");
				$("idField").value = GetAttribute (component, "_id");
				Ext.onReady (function ()
				{
					Ext.getCmp("nameField").store.each (function (record)
					{
						if (record.get("value") == GetAttribute (component, "_name"))
						{
							$("nameField").value = record.get("_name");
						}
					});
					Ext.getCmp("idField").store.each (function (record)
					{
						if (record.get("value") == GetAttribute (component, "_id"))
						{
							$("idField").value = record.get("_name");
						}
					});
				});
				
				$("descriptionField").value = GetAttribute (component, "title");
				$A($("styleField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "_style");
				});
				
			}
		}.bind (this));
		
		this.addEvent ("onComponentInitialize", function (component)
		{
			SetAttribute(component, "privilegeScript", $("privilegeScriptField").getValue ());
			SetAttribute(component, "readOnlyPrivilegeScriptField", $("readOnlyPrivilegeScriptField").getValue ());
			SetAttribute(component, "hiddenPrivilegeScriptField", $("hiddenPrivilegeScriptField").getValue ());
			SetAttribute(component, "_name", $("nameField").value);
			Ext.getCmp("nameField").store.each (function (record)
			{
				if (record.get("_name") == $("nameField").getValue ())
				{
					SetAttribute (component, "_name", record.get("value"));
				}
			});
			SetAttribute(component, "_id", $("idField").value);
			Ext.getCmp("idField").store.each (function (record)
			{
				if (record.get("_name") == $("idField").getValue ())
				{
					SetAttribute (component, "_id", record.get("value"));
				}
			});
			SetAttribute(component, "_style", $("styleField").getValue ());
			SetAttribute(component, "title", $("descriptionField").getValue ());
		}.bind (this));
	}
})