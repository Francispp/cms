var Rss = ActiveX.extend(
{
	initialize : function ()
	{
		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
	
		this.parent ("object", 
			"Rss", 
			"00460182-9E5E-11D5-B7C8-B8269041DD57", 
			"CMS_RSS", 
			"rss", 
			"340", 
			"450", 
			"网上定阅标签",
			"plugins/cms/images/rss.gif");
	   this.views = [
			{name : "Basic", title : "基本", contentEl : "basicView"}
		]
	  this.addEvent ("onHandlePage", function (editor)
		{
			$("popupImg").addEvent ("click", function ()  
				{
					var openUrl = "channelPickerBySite1.html";
					
					var retval = Json.evaluate (window.showModalDialog(openUrl, oEditor.parent.document.getElementById ("siteField").value +","+oEditor.parent.portalid+","+oEditor.parent.portalname, "dialogWidth=400px;dialogHeight=400px;status=no;scroll=yes;resizable=yes"));

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
			        $("loginpath").value = GetAttribute (component, "loginpath");
				
			       $A($("styleField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "_style");
				});
				
				
				var fileTypeFields = "";
				fileTypeFields = GetAttribute (component, "optionFileTypeList");
				if(fileTypeFields != null && fileTypeFields.length > 0)
				{
				
				var rt = new Array();
				if(String(fileTypeFields).indexOf(",") != -1)
				{
				rt = String(fileTypeFields).split(",");
				
				for(var i=0;i<rt.length;i++)
				{
					var option = document.createElement ("OPTION");
					
					$("optionFileTypeList").appendChild (option);
					
					option.value = option.innerText = rt[i]; 
				}
			       }
			       else
			       {
			       	var option = document.createElement ("OPTION");
			       	$("optionFileTypeList").appendChild (option);
			       	option.value = option.innerText = fileTypeFields;
			       	}
			       }
			       
			       var fileValueFields = "";
				fileValueFields = GetAttribute (component, "optionFileValueList");
				if(fileValueFields != null && fileValueFields.length > 0)
				{
				
				var rt = new Array();
				if(String(fileValueFields).indexOf(",") != -1)
				{
				rt = String(fileValueFields).split(",");
				
				for(var i=0;i<rt.length;i++)
				{
					var option = document.createElement ("OPTION");
					
					$("optionFileValueList").appendChild (option);
					
					option.value = option.innerText = rt[i]; 
				}
			       }
			       else
			       {
			       	var option = document.createElement ("OPTION");
			       	$("optionFileValueList").appendChild (option);
			       	option.value = option.innerText = fileValueFields;
			       	}
			       }
			}
		}.bind (this));
		
		this.addEvent ("onComponentInitialize", function (component)
		{
			SetAttribute(component, "_style", $("styleField").getValue ());
			SetAttribute(component, "loginpath", $("loginpath").getValue ());
			var fileTypeFields = [];
			$A($("optionFileTypeList").options).each (function (option)
			{
			fileTypeFields.push(option.value);
				
			});
			SetAttribute (component, "optionFileTypeList", String(fileTypeFields));
			var fileValueFields = [];
			$A($("optionFileValueList").options).each (function (option)
			{
			fileValueFields.push(option.value);
				
			});
			SetAttribute (component, "optionFileValueList", String(fileValueFields));
			SetAttribute(component, "siteIdField", oEditor.parent.document.getElementById ("siteField").value);
			SetAttribute(component, "portalid", oEditor.parent.portalid);
			
		});		
	}
});