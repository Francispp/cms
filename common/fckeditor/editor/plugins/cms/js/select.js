var Select = FormField.extend(
{
	initialize : function ()
	{
		this.parent ("select", "Select", "CMS_SELECT", "select", "400", "400", "select");
		
		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
		
		this.views = [
			{name : "Basic", title : "基本", contentEl : "basicView"},
			{name : "ValueScript", title : "值脚本", contentEl : "valueScriptView", page : basepath + "valueScript.html" },
			{name : "ValidateScript", title : "校验脚本", contentEl : "validateScriptView", page : basepath + "validateScript.html" },
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
				$("multipleField").checked = GetAttribute (component, "multiple");
				$("sizeField").value = GetAttribute (component, "size");
				
				$A($("optionLabelList").options).each (function (option)
				{
					$("optionLabelField").removeChild (option);
				}.bind (this));
				
				$A($("optionValueList").options).each (function (option)
				{
					$("optionValueField").removeChild (option);
				}.bind (this));
				
				$A(component.options).each (function (option)
				{
					var copy = document.createElement ("OPTION");
					
					$("optionLabelList").options.add (copy);
					
					copy.value = option.innerText;
					copy.innerText = option.innerText;
					copy.selected = option.selected;
					
					copy = document.createElement ("OPTION");
					
					$("optionValueList").options.add (copy);
					copy.value = option.value;
					copy.innerText = option.value;
					copy.selected = option.selected;
				}.bind (this));
			        if($("readOnlyPrivilegeScriptField"))
			           $("readOnlyPrivilegeScriptField").value = GetAttribute (component, "readOnlyPrivilegeScriptField");
		                if($("hiddenPrivilegeScriptField"))
			           $("hiddenPrivilegeScriptField").value = GetAttribute (component, "hiddenPrivilegeScriptField");
		                if($("privilegeScript"))  
		                   $("privilegeScriptField").value = GetAttribute (component, "privilegeScript");	
			}
		}.bind (this));
	
		this.addEvent ("onComponentInitialize", function (component, editor)
		{
			SetAttribute(component, "_name", $("nameField").value);
			Ext.getCmp("nameField").store.each (function (record)
			{
				if (record.get("name") == $("nameField").value)
				{
					SetAttribute (component, "_name", record.get("value"));
				}
			});
			SetAttribute(component, "multiple", $("multipleField").getValue ());
			SetAttribute(component, "size", $("sizeField").getValue ());
			
			$A(component.options).each (function (option)
			{
				component.removeChild (option);
			}.bind (this));
			
			for (var index = 0; index < $("optionValueList").options.length; index++)
			{
				var textOption = $("optionLabelList").options[index];
				var valueOption = $("optionValueList").options[index];
				
				var option = editor.FCK.EditorDocument.createElement("OPTION");
				
				component.options.add (option);
				
				option.value = valueOption.value;
				option.innerText = textOption.innerText;
				option.selected = valueOption.selected;
			}
			if($("readOnlyPrivilegeScriptField"))
		         SetAttribute(component, "readOnlyPrivilegeScriptField", $("readOnlyPrivilegeScriptField").value);
		        if($("hiddenPrivilegeScriptField"))
		         SetAttribute(component, "hiddenPrivilegeScriptField", $("hiddenPrivilegeScriptField").value);			
		        if($("privilegeScriptField"))
  		         SetAttribute(component, "privilegeScript", $("privilegeScriptField").value);
		}.bind (this));
	}
});