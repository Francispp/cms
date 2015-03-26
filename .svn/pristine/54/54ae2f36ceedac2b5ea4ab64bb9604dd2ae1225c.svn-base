var History = Component.extend(
{
	initialize : function ()
	{	
		this.parent ("div", 
			"History",  
			"CMS_HISTORY", 
			"history", 
			"340", 
			"400", 
			"历史记录",
			"plugins/cms/images/history.gif");

		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
		
		this.views = [
			{name : "Basic", title : "基本", contentEl : "basicView"},
			{name : "PrivilegeScript", title : "权限控制", contentEl : "privilegeScriptView", page : basepath + "privilegeScript.html" }
		]			
		this.addEvent ("onHandlePage", function (editor)
		{
			$("insertFieldButton").addEvent ("click", function ()
			{
				$A($("fieldInput").options).each (function (option)
				{
					if (option.selected)
					{
						var exists = $A($("displayField").options).some (function (item)
						{
							return item.value == option.value;
						});
						
						if (!exists)
						{
							/*var cloned = document.createElement("OPTION");
							
							$("displayField").options.add (cloned);
							
							cloned.value = option.value;
							cloned.innerText = option.innerText;*/
							//add line
							$("displayField").appendChild ($(option).clone ());
						}
					}
				});
			});
			
			$("removeFieldButton").addEvent ("click", function ()
			{
				$A($("displayField").options).each (function (option)
				{
					if (option.selected)
					{
						$("displayField").removeChild (option);
					}
				});
			});
		});
		
		 this.addEvent ("onPageInitialize", function (component)
		{
			if (component) 
			{ 
				var values = $A(Json.evaluate (GetAttribute (component, "displayFields")));
				$A($("displayField").options).each (function (option)
				{
								if (!values.contains (option.value))
					{
						$("displayField").removeChild (option);
					}
				});
				$A($("styleField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "_style");
				});
				if($("readOnlyPrivilegeScriptField"))
			           $("readOnlyPrivilegeScriptField").value = GetAttribute (component, "readOnlyPrivilegeScriptField");
		                if($("hiddenPrivilegeScriptField"))
			           $("hiddenPrivilegeScriptField").value = GetAttribute (component, "hiddenPrivilegeScriptField");
		                if($("privilegeScript"))  
		                   $("privilegeScriptField").value = GetAttribute (component, "privilegeScript");
			}
		});
		
		this.addEvent ("onComponentInitialize", function (component, editor)
		{
			SetAttribute(component, "_style", $("styleField").getValue ());
			SetAttribute (component, "displayFields", Json.toString ($A($("displayField").options).map (function (option)
			{
				return option.value;	
			}
			)));
			if($("readOnlyPrivilegeScriptField"))
		         SetAttribute(component, "readOnlyPrivilegeScriptField", $("readOnlyPrivilegeScriptField").getValue ());
		        if($("hiddenPrivilegeScriptField"))
		         SetAttribute(component, "hiddenPrivilegeScriptField", $("hiddenPrivilegeScriptField").getValue ());			
		        if($("privilegeScriptField"))
  		         SetAttribute(component, "privilegeScript", $("privilegeScriptField").getValue ());
		});
	}
});