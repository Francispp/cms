var Button = Component.extend(
{
	initialize : function ()
	{	
		this.parent ("button", "Button", "CMS_BUTTON", "button", "340", "460", "自定义按钮");
		
		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
		
		this.views = [
			{name : "Basic", title : "基本", contentEl : "basicView"},
			{name : "PrivilegeScript", title : "权限控制", contentEl : "privilegeScriptView", page : basepath + "privilegeScript.html" }
		]
		
		this.addEvent ("onComponentCreated", function (component)
		{
			
		});
		
		
		
		this.addEvent ("onPageInitialize", function (component)
		{
			if (component)
			{
			      $A($("buttonField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "buttonField");
				});
			       
				//$("_nameField").value = component.innerText;
				$("_nameField").value = GetAttribute (component, "_value");
				$("onclickField").value = GetAttribute (component, "_onclick");
				//alert(GetAttribute (component, "_onclick"));
				$("descriptionField").value = GetAttribute (component, "title");
				$("buttonTypeRadio").value = GetAttribute (component, "buttonTypeRadio");
				$("buttonScriptTypeField").value = GetAttribute (component, "buttonScriptTypeField");
				var iconList = "";
				iconList = GetAttribute (component, "iconList");
				if(iconList != null && iconList.length > 0)
				{
				var option = document.createElement ("OPTION");
			       	$("iconList").appendChild (option);
			       	option.value = option.innerText = iconList;
				}
				
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
		
		this.addEvent ("onComponentInitialize", function (component)
		{	var iconFields = [];	
			
		      if($("buttonScriptTypeField").value != 1)
		      {
		      	SetAttribute(component, "value", $("_nameField").getValue ());
		      	
		      	}
		else{
		SetAttribute(component, "value", " ");
	}
			SetAttribute(component, "_value", $("_nameField").getValue ());
			SetAttribute(component, "_onclick", $("onclickField").getValue ());
			SetAttribute(component, "_style", $("styleField").getValue ());
			SetAttribute(component, "title", $("descriptionField").getValue ());
			SetAttribute(component, "buttonTypeRadio", $("buttonTypeRadio").getValue ());
			SetAttribute(component, "buttonScriptTypeField", $("buttonScriptTypeField").getValue ());
			$A($("iconList").options).each (function (option)
			{
			iconFields.push(option.value);
				
			});
			SetAttribute (component, "iconList", String(iconFields));
			SetAttribute(component, "buttonField", $("buttonField").value);	
			SetAttribute(component, "src", String(iconFields));
		       if($("readOnlyPrivilegeScriptField"))
		        SetAttribute(component, "readOnlyPrivilegeScriptField", $("readOnlyPrivilegeScriptField").getValue ());
		       if($("hiddenPrivilegeScriptField"))
		        SetAttribute(component, "hiddenPrivilegeScriptField", $("hiddenPrivilegeScriptField").getValue ());			
		       if($("privilegeScriptField"))
  		        SetAttribute(component, "privilegeScript", $("privilegeScriptField").getValue ());

		});
	}
})