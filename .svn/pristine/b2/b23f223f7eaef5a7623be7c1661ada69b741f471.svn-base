var Register = Component.extend(
{
	initialize : function ()
	{	
		this.parent ("object", 
			"Register",  
			"CMS_REGISTER", 
			"register", 
			"340", 
			"440", 
			"外部用户注册",
			"plugins/cms/images/register.gif");
		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
		this.views = [
			{name : "Basic", title : "基本", contentEl : "basicView"}
			
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
				
				$A($("styleField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "_style");
				});
				
				$("jumppath").value = GetAttribute (component, "jumppath");
				
			}
		}.bind (this));
		
		this.addEvent ("onComponentInitialize", function (component)
		{      
			
			SetAttribute (component, "displayContent", Json.toString ($A($("displayContentList").options).map (function (option)
			{
				return option.value;
			})));
			
			SetAttribute(component, "jumppath", $("jumppath").getValue ());
			
			
			SetAttribute(component, "_style", $("styleField").getValue ());

		}.bind (this));
	}
})