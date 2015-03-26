var Navigation = Component.extend(
{
	initialize : function ()
	{	
		this.parent ("object", 
			"Navigation",  
			"CMS_NAVIGATION", 
			"navigation", 
			"360", 
			"400", 
			"导航标签",
			"plugins/cms/images/navigation.gif");
		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
		this.views = [
			{name : "Basic", title : "基本", contentEl : "basicView"},
			{name : "PrivilegeScript", title : "权限控制", contentEl : "privilegeScriptView", page : basepath + "privilegeScript.html" }
		]
		
		this.addEvent ("onPageInitialize", function (component)
		{
			if (component)
			{
			    if(GetAttribute (component, "direct")=="false")
				$("directField").checked = false;
				else
				$("directField").checked = true;
				$("navigationField").value = GetAttribute (component, "navigationField");
				$("privilegeScriptField").value = GetAttribute (component, "privilegeScript");
				$("readOnlyPrivilegeScriptField").value = GetAttribute (component, "readOnlyPrivilegeScriptField");
				$("hiddenPrivilegeScriptField").value = GetAttribute (component, "hiddenPrivilegeScriptField");
				$("descriptionField").value = GetAttribute (component, "title");
				$A($("styleField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "_style");
				}); 
				var ltList = GetAttribute (component, "levelTargetList");
				if(ltList != null && ltList.length > 0)
				{
				
				var rt = new Array();
				if(String(ltList).indexOf(",") != -1)
				{
				rt = String(ltList).split(",");
				
				for(var i=0;i<rt.length;i++)
				{
					var option = document.createElement ("OPTION");
					
					$("levelTargetList").appendChild (option);
					
					option.value = option.innerText = rt[i]; 
				}
			       }
			       else
			       {
			       	var option = document.createElement ("OPTION");
			       	$("levelTargetList").appendChild (option);
			       	option.value = option.innerText = ltList;
			       	}
			    }
				
				
				if(GetAttribute (component, "direct")){
				   getTarget();
				   getNum();
				}
			}
		}.bind (this));
		
		this.addEvent ("onComponentInitialize", function (component)
		{
			var ltList = [];
			SetAttribute(component, "navigationField", $("navigationField").getValue ());
			SetAttribute(component, "privilegeScript", $("privilegeScriptField").getValue ());
			SetAttribute(component, "readOnlyPrivilegeScriptField", $("readOnlyPrivilegeScriptField").getValue ());
			SetAttribute(component, "hiddenPrivilegeScriptField", $("hiddenPrivilegeScriptField").getValue ());
			SetAttribute(component, "_style", $("styleField").getValue ());
			SetAttribute(component, "title", $("descriptionField").getValue ());
			SetAttribute(component, "direct", $("directField").checked ? "true" : "false"); 
			$A($("levelTargetList").options).each (function (option)
			{ ltList.push(option.value); });
			SetAttribute (component, "levelTargetList", String(ltList));
		}.bind (this));
	}
})