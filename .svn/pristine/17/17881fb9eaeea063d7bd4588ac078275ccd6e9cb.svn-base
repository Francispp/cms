var Tree = ActiveX.extend(
{
	initialize : function ()
	{
		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
	
		this.parent ("object",
			"Tree", 
			"00460182-9E5E-11D5-B7C8-B8269041DD57", 
			"CMS_TREE", 
			"tree", 
			"340", 
			"400", 
			"树状菜单",
			"plugins/cms/images/tree.gif");
	   this.views = [
			{name : "Basic", title : "基本", contentEl : "basicView"}
			//,{name : "Template", title : "模板", contentEl : "templatesView"},
			//{name : "PrivilegeScript", title : "权限控制", contentEl : "privilegeScriptView", page : basepath + "privilegeScript.html" }
		]
	  this.addEvent ("onHandlePage", function (editor)
		{
			$("popupImg").addEvent ("click", function ()  
				{
					var retval = Json.evaluate (window.showModalDialog("channelPicker.html", null, "dialogWidth=400px;dialogHeight=400px;status=no;scroll=yes;resizable=yes"));
					
					$("channelTextField").value = retval.text;
					$("channelValueField").value = retval.id;
				});			
		});					
	    this.addEvent ("onComponentCreated", function (component)
		{
			//SetAttribute (component, "type", $("passwordField").checked ? "password" : "text");
		});
		
		this.addEvent ("onPageInitialize", function (component)
		{
			if (component)
			{
				$("channelValueField").value = GetAttribute (component, "channelId");
				$("channelTextField").value = GetAttribute (component, "channelName");
				//$("isdynamicField").value = GetAttribute (component, "isdynamic");
				/*$A($("styleField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "_style");
				});*/
				$("deployStyleField").value = GetAttribute (component, "deployStyle");
				$("LinkField").value = GetAttribute (component, "link");
				//show node image
				var isShowNodeImg=GetAttribute (component, "isShowNodeImg");
				if(isShowNodeImg && isShowNodeImg!='')
				 $("isShowNodeImgField").value=isShowNodeImg;
				 //custom tree css 
				var isCustomCss=GetAttribute (component, "isCustomCss");
				if(isCustomCss && isCustomCss!='')
				 $("isCustomCssField").value=isCustomCss;				 
			}
		}.bind (this));
		
		this.addEvent ("onComponentInitialize", function (component)
		{
			SetAttribute(component, "channelId", $("channelValueField").getValue ());
			SetAttribute(component, "channelName", $("channelTextField").getValue ());
			//SetAttribute(component, "isdynamic", $("isdynamicField").getValue ());
			SetAttribute(component, "deployStyle", $("deployStyleField").getValue ());
			SetAttribute(component, "link", $("LinkField").getValue ());
			SetAttribute(component, "isShowNodeImg", $("isShowNodeImgField").value);
			SetAttribute(component, "isCustomCss", $("isCustomCssField").value);
		});		
	}
});