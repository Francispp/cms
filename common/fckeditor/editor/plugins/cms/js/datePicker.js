var DatePicker = FormField.extend(
{
	initialize : function ()
	{
		this.parent ("input", "DatePicker", "CMS_DATEPICKER", "datePicker", "340", "400", "日期选择", "plugins/cms/images/datepicker.gif");
		
		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
		
		this.views = [
			{name : "Basic", title : "基本", contentEl : "basicView" },
			{name : "ValueScript", title : "值脚本", contentEl : "valueScriptView", page : basepath + "valueScript.html" },
			{name : "ValidateScript", title : "校验脚本", contentEl : "validateScriptView", page : basepath + "validateScript.html" },
			/*
			{name : "HiddenScript", title : "隐藏脚本", contentEl : "hiddenScriptView", page : basepath + "hiddenScript.html" },
			*/
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
						if (record.get("value") == GetAttribute (component, "_name"))
						{
							$("nameField").value = record.get("name");
						}
					});
				});
				$("styleField").value = GetAttribute (component, "_style"); 
				$A($("formatField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "format");
				});
				
				$("descriptionField").value = GetAttribute (component, "title");
				if($("readOnlyPrivilegeScriptField"))
			           $("readOnlyPrivilegeScriptField").value = GetAttribute (component, "readOnlyPrivilegeScriptField");
		                if($("hiddenPrivilegeScriptField"))
			           $("hiddenPrivilegeScriptField").value = GetAttribute (component, "hiddenPrivilegeScriptField");
		                if($("privilegeScript"))  
		                   $("privilegeScriptField").value = GetAttribute (component, "privilegeScript");
			}
		});
		
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
			SetAttribute(component, "format", $("formatField").getValue ()); 
			
			SetAttribute(component, "title", $("descriptionField").getValue ());
			if($("readOnlyPrivilegeScriptField"))
		         SetAttribute(component, "readOnlyPrivilegeScriptField", $("readOnlyPrivilegeScriptField").value);
		        if($("hiddenPrivilegeScriptField"))
		         SetAttribute(component, "hiddenPrivilegeScriptField", $("hiddenPrivilegeScriptField").value);			
		        if($("privilegeScriptField"))
  		         SetAttribute(component, "privilegeScript", $("privilegeScriptField").value);
		});
	}
});