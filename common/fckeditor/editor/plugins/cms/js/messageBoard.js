MessageBoard = Component.extend(
{
	initialize : function ()
	{
		this.parent ("div", 
			"MessageBoard",  
			"CMS_MESSAGEBOARD", 
			"messageBoard", 
			"400", 
			"400", 
			"留言板",
			"plugins/cms/images/datepicker.gif");

		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
		
	   	this.views = [
			{name : "Basic", title : "基本", contentEl : "basicView"},
			{name : "ValueScript", title : "值脚本", contentEl : "valueScriptView", page : basepath + "valueScript.html"},
			{name : "HiddenScript", title : "隐藏脚本", contentEl : "hiddenScriptView", page : basepath + "hiddenScript.html"},
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
				$("pageSizeField").value = GetAttribute (component, "pageSize");
				$("deleteAbleField").checked = GetAttribute (component, "deleteAble") == "true" ? true : false;
				$A($("styleList").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "_style"); 
				});
			}
		});
		
		this.addEvent ("onComponentInitialize", function (component, editor)
		{
			SetAttribute (component, "displayContent", Json.toString ($A($("displayContentList").options).map (function (option)
			{
				return option.value;
			})));
			SetAttribute (component, "pageSize", $("pageSizeField").getValue ());
			SetAttribute (component, "deleteAble", $("deleteAbleField").checked);
			SetAttribute (component, "_style", $("styleList").getValue ());
		});
	}
});