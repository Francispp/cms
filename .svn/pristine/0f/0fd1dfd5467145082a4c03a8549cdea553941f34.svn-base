var MediaUpload = Component.extend(
{
	initialize : function ()
	{	
		this.parent ("object", 
			"MediaUpload",  
			"CMS_MEDIAUPLOAD", 
			"mediaUpload", 
			"340", 
			"460", 
			"媒体上传标签",
			"plugins/cms/images/mediaupload.gif");
		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
		this.views = [
			{name : "Basic", title : "基本", contentEl : "basicView"},
			{name : "PrivilegeScript", title : "权限控制", contentEl : "privilegeScriptView", page : basepath + "privilegeScript.html" }
		]

		this.addEvent ("onPageInitialize", function (component)
		{
			if (component)
			{
				$("privilegeScriptField").value = GetAttribute (component, "privilegeScript");
				$("readOnlyPrivilegeScriptField").value = GetAttribute (component, "readOnlyPrivilegeScriptField");
				$("hiddenPrivilegeScriptField").value = GetAttribute (component, "hiddenPrivilegeScriptField");


				$("nameField").value = GetAttribute (component, "_name");
				
				
				$("issueRadioField").value = GetAttribute (component, "_issueRadioField");
				
				$("issueRadio").value = GetAttribute (component, "_issueRadio");
				
				$A($("mediaType").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "_mediaType");
				});
				
				var temp=GetAttribute (component, "_mediaType");
				$(temp+"MediaTypeView").style.display="";
				
				$A($(temp+"UploadType").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "_uploadType");
				});
				
				$("showMediaUploadFormat").innerText= GetAttribute (component, "_uploadType");
				
				$("mediaWidth").value = GetAttribute (component, "_mediaWidth");
				$("mediaHeight").value = GetAttribute (component, "_mediaHeight");
				$("mediaSize").value = GetAttribute (component, "_mediaSize");
				$A($("styleField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "_style");
				});
				
				$("descriptionField").value = GetAttribute (component, "_descriptionField");
				
				
			}
			
			(function initView(){
				var count=0;
				var temp=$('mediaType');
				for(var i=0;i<temp.length;i++){
					if($(temp[i].value+'MediaTypeView').style.display==''){
						count++;
						break;
					}
				}
				if(count==0){
					if(temp.length>0){
						$(temp[0].value+'MediaTypeView').style.display="";
						var _temp=$(temp[0].value+'UploadType');
						if(_temp.length>0){
							showMediaUploadFormat(_temp[0].value);
						}
					}
				}
			})();
			
		}.bind (this));
		
		this.addEvent ("onComponentInitialize", function (component)
		{   
			SetAttribute(component, "privilegeScript", $("privilegeScriptField").getValue ());
			SetAttribute(component, "readOnlyPrivilegeScriptField", $("readOnlyPrivilegeScriptField").getValue ());
			SetAttribute(component, "hiddenPrivilegeScriptField", $("hiddenPrivilegeScriptField").getValue ());
			
			SetAttribute(component, "_name", $("nameField").getValue ());
	
			SetAttribute(component, "_issueRadio", $("issueRadio").getValue ());
			SetAttribute(component, "_issueRadioField", $("issueRadioField").getValue ());
			
			var temp=$("mediaType").getValue ();
			SetAttribute(component, "_mediaType", $("mediaType").getValue ());
			SetAttribute(component, "_uploadType", $(temp+"UploadType").getValue ());
			SetAttribute(component, "_mediaWidth", $("mediaWidth").getValue ());
			SetAttribute(component, "_mediaHeight", $("mediaHeight").getValue ());	
			SetAttribute(component, "_mediaSize", $("mediaSize").getValue ());
			SetAttribute(component, "_style", $("styleField").getValue ());
			SetAttribute(component, "_descriptionField", $("descriptionField").getValue ());
		}.bind (this));
	}
})