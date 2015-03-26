var UserPicker = Component.extend(
{
	initialize : function ()
	{
		this.parent ("input", 
			"UserPicker", 
			"CMS_USERPICKER", 
			"userPicker", 
			"400", 
			"360", 
			"用户选择");

		this.addEvent ("onProcessElement", function (document, element)
		{
			var imageEl = new Element ("img", { 
				"src" : "plugins/cms/images/popup.png",
				"_fckfakelement" : "true",
				"_fckrealelement" : FCKTempBin.AddElement(element)
			});
			
			element
			element.insertAdjacentHTML ("afterEnd", $(imageEl).outerHTML);
		}.bind (this));
		
		this.addEvent ("onHandlePage", function (editor)
		{
			var element = window.parent.Selection.GetSelectedElement();
			if (element && GetAttribute (element, "t_type") == this.componentType)
			{
				element = editor.FCK.GetRealElement (element);
			}
			
			if (element)
			{
				window.addEvent ("onload", function ()
				{
					//$("bodyField").value = element.innerHTML;
					
					window.parent.SetOkButton (true);
				}.bind (this));
				
				window.parent.Ok = function ()
				{
					//element.innerHTML = $("bodyField").value;
					
					window.parent.Cancel ();
				}.bind (this);
			}
		});
	}
});