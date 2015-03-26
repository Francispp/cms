var Login = Component.extend(
{
	initialize : function ()
	{	
		this.parent ("object", 
			"Login",  
			"CMS_LOGIN", 
			"login", 
			"340", 
			"430", 
			"外部用户登陆标签",
			"plugins/cms/images/login.gif");
		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
		this.views = [
			{name : "Basic", title : "基本", contentEl : "basicView"}
		]
		
		this.addEvent ("onPageInitialize", function (component)
		{
			if (component)
			{
				
				
				$("descriptionField").value = GetAttribute (component, "title");
				$A($("styleField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "_style");
				});
				$("jumppath").value = GetAttribute (component, "jumppath");
				$("member").value = GetAttribute (component, "member");
				$("sucpath").value = GetAttribute (component, "sucpath");
				$("logoupath").value = GetAttribute (component, "logoupath");
				
			}
		}.bind (this));
		
		this.addEvent ("onComponentInitialize", function (component)
		{
			
			SetAttribute(component, "_style", $("styleField").getValue ());
			SetAttribute(component, "title", $("descriptionField").getValue ());
			SetAttribute(component, "jumppath", $("jumppath").getValue ());
			SetAttribute(component, "member", $("member").getValue ());
			SetAttribute(component, "sucpath", $("sucpath").getValue ());
			SetAttribute(component, "logoupath", $("logoupath").getValue ());
			
					
		}.bind (this));
	}
})