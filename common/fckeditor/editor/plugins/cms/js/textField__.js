var TextField = FormField.extend(
{
	initialize : function ()
	{	
		this.parent ("input", "TextField", "CMS_TEXTFIELD", "textField", "340", "320", "文本框");
		
		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
		
		this.views = [
			{name : "Basic", title : "基本", contentEl : "basicView"},
			{name : "ValueScript", title : "值脚本", contentEl : "valueScriptView", page : basepath + "valueScript.html" },
			{name : "ValidateScript", title : "校验脚本", contentEl : "validateScriptView", page : basepath + "validateScript.html" },
			{name : "HiddenScript", title : "隐藏脚本", contentEl : "hiddenScriptView", page : basepath + "hiddenScript.html" },
			{name : "PrivilegeScript", title : "权限控制", contentEl : "privilegeScriptView", page : basepath + "privilegeScript.html" }
		]
		
		this.addEvent ("onHandlePage", function (editor)
		{
			Ext.onReady (function ()
			{
				CoreFormService.getById (editor.parent.document.getElementById ("formOid").value, function (result)
				{	
					var store = new Ext.data.SimpleStore({fields : ["name", "value"], data : result.formFields.map (function (item)
					{
						return [item.fieldName, item.fieldCode];
					})});
					
					var comboBox = new Ext.form.ComboBox ({id : "nameField", applyTo : "nameField", mode : "local", store : store, width : 290, displayField : "name", valueField : "value", triggerAction : "all"});
				});
			});
		});
		
		this.addEvent ("onComponentCreated", function (component)
		{
			SetAttribute (component, "type", $("passwordField").checked ? "password" : "text");
		});
		
		this.addEvent ("onPageInitialize", function (component)
		{
			$("nameField").value = GetAttribute (component, "name");
			Ext.getCmp("nameField").store.each (function (record)
			{
				if (record.get("value") == GetAttribute (component, "name"))
				{
					$("nameField").value = record.get("name");
				}
			});
			$("descriptionField").value = GetAttribute (component, "title");
			$A($("styleField").options).each (function (option)
			{
				option.selected = option.value == GetAttribute (component, "_style");
			});
			if (GetAttribute (component, "t_type"))
			{
				$("passwordField").disabled = true;
			}
			$("passwordField").checked = GetAttribute (component, "type") == "password";
		}.bind (this));
		
		this.addEvent ("onComponentInitialize", function (component)
		{
			SetAttribute(component, "name", $("nameField").getValue ());
			Ext.getCmp("nameField").store.each (function (record)
			{
				if (record.get("name") == $("nameField").getValue ())
				{
					SetAttribute (component, "name", record.get("value"));
				}
			});
			SetAttribute(component, "_style", $("styleField").getValue ());
			SetAttribute(component, "title", $("descriptionField").getValue ());
		}.bind (this));
	}
})