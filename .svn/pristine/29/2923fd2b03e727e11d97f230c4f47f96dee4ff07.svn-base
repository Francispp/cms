var Word = ActiveX.extend(
{
	initialize : function ()
	{
		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
	
		this.parent ("object", 
			"Word", 
			"00460182-9E5E-11D5-B7C8-B8269041DD57", 
			"CMS_WORD", 
			"word", 
			"340", 
			"400", 
			"Word编辑器",
			"plugins/cms/images/word.gif");
	   this.views = [
			{name : "Basic", title : "基本", contentEl : "basicView"},
			{name : "Template", title : "模板", contentEl : "templatesView"},
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
	    this.addEvent ("onComponentCreated", function (component)
		{
			//SetAttribute (component, "type", $("passwordField").checked ? "password" : "text");
		});
		
		this.addEvent ("onPageInitialize", function (component)
		{
			if (component)
			{
				$("nameField").value = GetAttribute (component, "_name");
				$("descriptionField").value = GetAttribute (component, "title");
				/*$A($("styleField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "_style");
				});*/
				$("templateUrlField").value = GetAttribute (component, "templateUrl");
				$("privilegeScriptField").value = GetAttribute (component, "privilegeScript");
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
			//SetAttribute(component, "_style", $("styleField").getValue ());
			SetAttribute(component, "title", $("descriptionField").getValue ());
			SetAttribute(component, "templateUrl", $("templateUrlField").getValue ());
		});		
	}
});