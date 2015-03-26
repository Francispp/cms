var Advertisement = Component.extend(
{
	initialize : function ()
	{	
		this.parent ("object", 
			"Advertisement",  
			"CMS_ADVERTISEMENT", 
			"advertisement", 
			"350", 
			"420", 
			"广告标签",
			"plugins/cms/images/advertisement.gif");

		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
		this.views = [
			{name : "Basic", title : "基本", contentEl : "basicView"}
				]
		this.addEvent ("onHandlePage", function (editor)
		{
			Ext.onReady (function ()
			{
				var store = new Ext.data.SimpleStore({fields : ["_name", "value"], data : $A(editor.parent.getFormFields().options).map (function (item)
				{
					return [item.text, item.value];
				})});
			//	var comboBox = new Ext.form.ComboBox ({id : "nameField", applyTo : "nameField", mode : "local", store : store, width : 290, displayField : "_name", valueField : "value", triggerAction : "all"});
			//	var comboBox1 = new Ext.form.ComboBox ({id : "idField", applyTo : "idField", mode : "local", store : store, width : 290, displayField : "_name", valueField : "value", triggerAction : "all"});
			});
		});
		this.addEvent ("onPageInitialize", function (component)
		{
			if (component)
			{
			$("buttonScriptTypeField").value = GetAttribute (component, "buttonScriptTypeField");
			$("buttonTypeRadio").value = GetAttribute (component, "buttonTypeRadio");
			$("styleField").value = GetAttribute (component, "_style");
			$("showType").value = GetAttribute (component, "showType");
			$("descriptionField").value = GetAttribute (component, "title");
			$("jumpURL").value = GetAttribute (component, "jumpURL");

			var iconList = "";
			iconList = GetAttribute (component, "iconList");
			if(iconList != null && iconList.length > 0)
			{
			var option = document.createElement ("OPTION");
			   	$("iconList").appendChild (option);
		      	option.value = option.innerText = iconList;
			}	
			}
		}.bind (this));
		
		this.addEvent ("onComponentInitialize", function (component)
		{	
			var iconFields = [];	
		
			SetAttribute(component, "buttonScriptTypeField", $("buttonScriptTypeField").getValue ());
			SetAttribute(component, "buttonTypeRadio", $("buttonTypeRadio").getValue ());
			SetAttribute(component, "_style", $("styleField").getValue ());
			SetAttribute(component, "showType", $("showType").getValue ());
			SetAttribute(component, "title", $("descriptionField").getValue ());

			$A($("iconList").options).each (function (option)
			{
			iconFields.push(option.value);
				
			});
			SetAttribute (component, "iconList", String(iconFields));
			
			
			SetAttribute(component, "jumpURL", $("jumpURL").getValue ());

				
		}.bind (this));
	}
})