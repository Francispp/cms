var Email = Component.extend(
{
	initialize : function ()
	{
		this.parent ("img", "Email", "CMS_EMAIL", "email", "340", "500", "邮件", "plugins/cms/images/email.gif");
		
		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
		
		this.views = [
			{name : "Basic", title : "基本", contentEl : "basicView"},
			{name : "PrivilegeScript", title : "权限控制", contentEl : "privilegeScriptView", page : basepath + "privilegeScript.html" }
		]
		
		this.addEvent ("onHandlePage", function (editor)
		{
			$("directField").onpropertychange = function ()
			{
				if (event.propertyName == "checked")
				{
					if ($("directField").checked)
					{
						$("basicView:addresseeView").style.display = "inline";
						$("basicView:addresseeView1").style.display = "inline";
						$("basicView:titleView").style.display = "inline";
						$("basicView:bodyView").style.display = "inline";
					}
					else
					{
						$("basicView:addresseeView").style.display = "none";
						$("basicView:addresseeView1").style.display = "none";
						$("basicView:titleView").style.display = "none";
						$("basicView:bodyView").style.display = "none";
					}
				}
			};
			
			Ext.onReady (function ()
			{ 
				new Ext.form.TextField ({id : "subjectField", applyTo : "subjectField", allowBlank : false});
				new Ext.form.TextArea ({id : "bodyField", applyTo : "bodyField", allowBlank : false, style : "width:100%", height : 150});
				
				var triggerField = new Ext.form.TriggerField ({ id : "toField", readOnly : true, allowBlank : false });
			});
		});
		
		this.addEvent ("onPageInitialize", function (component)
		{
			if (component)
			{
				$("subjectField").value = GetAttribute (component, "subject");
				$("bodyField").value = GetAttribute (component, "body");
				$("toValueField").value = GetAttribute (component, "to");
				$("toField").value = GetAttribute (component,"toName");
				$("ccValueField").value = GetAttribute (component, "cc");
				$("ccField").value = GetAttribute (component,"ccName");
				var iconList = "";
				iconList = GetAttribute (component, "iconList");
				if(iconList != null && iconList.length > 0)
				{
				var option = document.createElement ("OPTION");
			       	$("iconList").appendChild (option);
			       	option.value = option.innerText = iconList;
				}
				if(GetAttribute (component, "address")=="false")
				$("addressField").checked = false;
				else
				$("addressField").checked = true;
				$("styleField").value = GetAttribute (component, "_style");
				if(GetAttribute (component, "direct")=="false")
				$("directField").checked = false;
				else
				$("directField").checked = true;
				if ($("directField").checked)
				        {
						$("basicView:addresseeView").style.display = "inline";
						$("basicView:addresseeView1").style.display = "inline";
						$("basicView:titleView").style.display = "inline";
						$("basicView:bodyView").style.display = "inline";
					}
					else
					{
						$("basicView:addresseeView").style.display = "none";
						$("basicView:addresseeView1").style.display = "none";
						$("basicView:titleView").style.display = "none";
						$("basicView:bodyView").style.display = "none";
					}
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
			var iconFields = [];
			$A($("iconList").options).each (function (option)
			{
			iconFields.push(option.value);
				
			});
			SetAttribute (component, "iconList", String(iconFields));
			SetAttribute(component, "subject", $("subjectField").getValue ());
			SetAttribute(component, "body", $("bodyField").getValue ());
			SetAttribute(component, "to", $("toValueField").getValue ());
			SetAttribute(component, "toName", $("toField").getValue ());
			SetAttribute(component, "cc", $("ccValueField").getValue ());
			SetAttribute(component, "ccName", $("ccField").getValue ());
			SetAttribute(component, "address", $("addressField").checked ? "true" : "false");
			SetAttribute(component, "_style", $("styleField").getValue ());
			if($("readOnlyPrivilegeScriptField"))
		         SetAttribute(component, "readOnlyPrivilegeScriptField", $("readOnlyPrivilegeScriptField").getValue ());
		        if($("hiddenPrivilegeScriptField"))
		         SetAttribute(component, "hiddenPrivilegeScriptField", $("hiddenPrivilegeScriptField").getValue ());			
		        if($("privilegeScriptField"))
  		         SetAttribute(component, "privilegeScript", $("privilegeScriptField").getValue ());
			SetAttribute(component, "direct", $("directField").checked ? "true" : "false");
		});
	}
});