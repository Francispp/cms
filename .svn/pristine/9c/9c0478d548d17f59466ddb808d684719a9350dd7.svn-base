var Message = Component.extend(
{
	initialize : function ()
	{	
		this.parent ("object", 
			"Message",  
			"CMS_MESSAGE", 
			"message", 
			"340", 
			"440", 
			"留言版",
			"plugins/cms/images/message.gif");
		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
		this.views = [
			{name : "Basic", title : "基本", contentEl : "basicView"},
			{name : "PrivilegeScript", title : "权限控制", contentEl : "privilegeScriptView", page : basepath + "privilegeScript.html" }
		]
		this.addEvent ("onHandlePage", function (editor)
		{
			$("addDisplayContentButton").addEvent ("click", function ()
			{
				$A($("availDisplayContentList").options).each (function (option)
				{
					if (option.selected)
					{
						var exists = $A($("displayContentList").options).some (function (item)
						{
							return item.value == option.value;
						});
						
						if (!exists)
						{
							$("displayContentList").appendChild ($(option).clone ());
						}
					}
				});
			});
			
			$("removeDisplayContentButton").addEvent ("click", function ()
			{
				$A($("displayContentList").options).each (function (option)
				{
					if (option.selected)
					{
						$("displayContentList").removeChild (option);
					}
				});
			});
		});
		
		this.addEvent ("onPageInitialize", function (component)
		{
			if (component)
			{
				var values = $A(Json.evaluate (GetAttribute (component, "displayContent")));
				$A($("displayContentList").options).each (function (option)
				{
					if (!values.contains (option.value))
					{
						$("displayContentList").removeChild (option);
					}
				});
				$("listSize").value = GetAttribute (component, "listSize");
				$("privilegeScriptField").value = GetAttribute (component, "privilegeScript");
				$("readOnlyPrivilegeScriptField").value = GetAttribute (component, "readOnlyPrivilegeScriptField");
				$("hiddenPrivilegeScriptField").value = GetAttribute (component, "hiddenPrivilegeScriptField");
				$("deleteMessage").checked = GetAttribute (component, "deleteMessage") == "true" ? true : false;
			      if($("delete_choose")!=null && this.resources!=null )
			         {			
			          for(var i=1;i<this.resources.length;i++)
			            $("delete_choose").options[i]=new Option(this.resources.options[i].text,this.resources.options[i].value);
			         }	
			      $A($("delete_choose").options).each (function (option)
				{
					option.selected = $A(GetAttribute (component, "delete_choose").split (" ")).contains (option.value);
				});
				$A($("styleField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "_style");
				});
				
			}
		}.bind (this));
		
		this.addEvent ("onComponentInitialize", function (component)
		{      
			
			SetAttribute (component, "displayContent", Json.toString ($A($("displayContentList").options).map (function (option)
			{
				return option.value;
			})));
			SetAttribute(component, "listSize", $("listSize").getValue ());
			SetAttribute(component, "privilegeScript", $("privilegeScriptField").getValue ());
			SetAttribute(component, "readOnlyPrivilegeScriptField", $("readOnlyPrivilegeScriptField").getValue ());
			SetAttribute(component, "hiddenPrivilegeScriptField", $("hiddenPrivilegeScriptField").getValue ());
			SetAttribute(component, "_style", $("styleField").getValue ());
			SetAttribute (component, "deleteMessage", $("deleteMessage").checked+"");
			$A($("delete_choose").options).each (function (option)
			{
				if (option.selected)
				{
					SetAttribute (component, "delete_choose", option.value);
				}
			});
			
			


		}.bind (this));
	}
})