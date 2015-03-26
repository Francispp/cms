var Flow = Component.extend(
{
	initialize : function ()
	{	
		this.parent ("object", 
			"Flow",  
			"CMS_FLOW", 
			"flow", 
			"340", 
			"400", 
			"流程功能标签",
			"plugins/cms/images/flow.gif");
		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
		this.views = [
			{name : "Basic", title : "基本", contentEl : "basicView"}
		]
		
	 this.addEvent ("onComponentCreated", function (component)
		{
			
		});
		
		this.addEvent ("onPageInitialize", function (component)
		{
			if (component)
			{
				$("functionTypeField").value = GetAttribute (component, "functionType");				
				
				$("customField").value =  GetAttribute (component, "custom");
														
			}
		}.bind (this));
		
		this.addEvent ("onComponentInitialize", function (component)
		{
			SetAttribute(component, "functionType", $("functionTypeField").getValue ());
			
			SetAttribute(component, "custom", $("customField").getValue ());
			
		}.bind (this));
	}
})