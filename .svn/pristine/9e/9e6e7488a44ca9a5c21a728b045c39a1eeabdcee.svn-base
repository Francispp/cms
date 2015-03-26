var Operation = Component.extend(
{
	initialize : function ()
	{	
		this.parent ("object", 
			"Operation",  
			"CMS_OPERATION", 
			"operation", 
			"340", 
			"400", 
			"显示内容标签",
			"plugins/cms/images/operation.gif");
		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
		this.views = [
			{name : "Basic", title : "基本", contentEl : "basicView"}
		]
		
		this.addEvent ("onPageInitialize", function (component)
		{
			if (component)
			{
			        $("buttonScriptTypeField").value = GetAttribute (component, "buttonScriptTypeField");
				$("buttonTypeRadio").value = GetAttribute (component, "buttonTypeRadio");
				$("nameField").value = GetAttribute (component, "_name");						
			}
		}.bind (this));
		
		this.addEvent ("onComponentInitialize", function (component)
		{
			SetAttribute(component, "_name", $("nameField").getValue ());
			SetAttribute(component, "buttonTypeRadio", $("buttonTypeRadio").getValue ());
			SetAttribute(component, "buttonScriptTypeField", $("buttonScriptTypeField").getValue ());
		}.bind (this));
	}
})