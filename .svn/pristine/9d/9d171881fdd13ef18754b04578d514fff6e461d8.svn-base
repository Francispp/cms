var List = Component.extend(
{
	initialize : function ()
	{
		this.parent ("table", 
			"List",  
			"CMS_LIST", 
			"list", 
			"450", 
			"560", 
			"列表组件");

		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
		
	   	this.views = [
			{name : "Basic", title : "基本", contentEl : "basicView"},
			{name : "Extend", title : "高级设置", contentEl : "extendView"}
			//,{name : "PrivilegeScript", title : "权限控制", contentEl : "privilegeScriptView", page : basepath + "privilegeScript.html" }
		]
		
		this.addEvent ("onProcessComponent", function (document, component, editor)
		{
			var tableEl = new Element ("table", { 
					"styles" : { "background-color" : "#000000" },
					"width" : "100%",
					"_fckfakelement" : "true",
					"_fckrealelement" : editor ? editor.FCKTempBin.AddElement(component) : FCKTempBin.AddElement(component),
					"t_type" : this.componentType
				});
			var theadEl = new Element ("thead");
			
			var displayFields = [];
			var tbodyEl = component.getElementsByTagName ("tbody").length > 0 ? component.getElementsByTagName ("tbody")[0] : null;
			var trEl = tbodyEl && tbodyEl.getElementsByTagName("tr").length > 0 ? tbodyEl.getElementsByTagName("tr")[0] : null;
			if (trEl)
			{
				$A(trEl.getElementsByTagName ("td")).each (function (td)
				{
					var field = Json.evaluate (GetAttribute (td, "field"));
					theadEl.appendChild (new Element ("th", { innerText : field.title, "styles" : { "background-color" : "#FFFFFF" }}));
					displayFields.push (field);
				});
			}

			tableEl.appendChild (theadEl); 
			
			if (GetAttribute (component, "pageSize"))
			{
				var tbodyEl = new Element ("tbody");
				for (var index = 0; index < GetAttribute (component, "pageSize"); index++)
				{
					var trEl = new Element ("tr");
					displayFields.each (function (field)
					{
						var tdEl = new Element ("td", { "styles" : { "background-color" : "#FFFFFF" }});
						tdEl.innerHTML = "&nbsp;";
						trEl.appendChild (tdEl);
					});
					tbodyEl.appendChild (trEl);
				}
				
				tableEl.appendChild (tbodyEl);
			}
			
			component.insertAdjacentHTML ("beforeBegin", tableEl.outerHTML);
			component.parentNode.removeChild (component);
		}.bind (this));
		
		this.addEvent ("onHandlePage", function (editor)
		{
			Ext.onReady (function ()
			{
				var store = new Ext.data.SimpleStore({fields : ["name", "value"], data : $A(editor.parent.getFormFields().options).map (function (item)
				{ 
					return [item.text, item.value];
				})});
				 
				new Ext.form.ComboBox ({id : "optionField", applyTo : "optionField", mode : "local", store : store, displayField : "name", valueField : "value", triggerAction : "all", wdith : 100});
				
				$("popupImg").addEvent ("click", function ()  
				{
					var retval = Json.evaluate (window.showModalDialog("channelPicker.html", null, "dialogWidth=400px;dialogHeight=400px;status=no;scroll=yes;resizable=yes"));
					//var chnUrl="/cms/site!selectChanelTree.action?siteid=&channelId=";		 		
					//selectChannelEx($("channelValueField"),$("channelTextField"),chnUrl);
					var np = retval.text.split('|'); 
					$("channelTextField").value = np[0];
					$("channelValueField").value = retval.id; 
				});
				
				/*$("imageBrowseButton").addEvent ("click", function ()
				{
					selectAttachment($("newsImageField"), DEFUALT_TEMPLATE_ATTACHMENT_PATH,"0");
				});*/
				
				new Ext.form.ComboBox ({id : "availableDisplayFieldInput", applyTo : "availableDisplayFieldInput", mode : "local", store : store, displayField : "name", valueField : "value", triggerAction : "all", wdith : 155});
				
				$("addFieldButton").addEvent ("click", function ()
				{
					if ($("availableDisplayFieldInput").value)
					{
						var option = document.createElement ("OPTION");
						option.value = Json.toString ({ title : $("availableDisplayFieldInput").value, property : $("availableDisplayFieldInput").value, type : "text"}); 
						option.innerText = $("availableDisplayFieldInput").value;
						Ext.getCmp("availableDisplayFieldInput").store.each (function (record)
						{
							if (record.get("name") == $("availableDisplayFieldInput").value)
							{
								var field = Json.evaluate (option.value);
								field.property = record.get("value");
								option.value = Json.toString (field);
							}
						});
						
						var exists = $A($("displayFieldSelect").options).some (function (item)
						{
							return item.value == option.value;
						});
						
						if (!exists)
						{
							$("displayFieldSelect").appendChild (option);
						}
					}
				});
				
				$("removeFieldButton").addEvent("click", function ()
				{
					$A($("displayFieldSelect").options).each (function (option)
					{
						if (option.selected)
						{
							$("displayFieldSelect").removeChild (option);
						}					});
				});
			});
			
			$("displayFieldSelect").addEvent ("dblclick", function ()
			{
				$A($("displayFieldSelect").options).each (function (option)
				{
					if (option.selected)
					{
					
						var field = Json.evaluate (window.showModalDialog ("../dialog/list/field.html", option.value,"dialogHeight:400px;dialogWidth:400px;center:yes;"));
						if(field!=null)
						{						
							option.value = Json.toString (field);
							option.innerText = field.title;
						}
					}
				}.bind (this));
			}.bind (this));
		});
		
	    this.addEvent ("onPageInitialize", function (component)
		{
			if (component) 
			{ 
				$("channelValueField").value = GetAttribute (component, "channel");
				$("channelTextField").value = GetAttribute (component, "channelText"); 
				$("isViewOnly").checked = GetAttribute (component, "isViewOnly") == "1";
				$("styleField").value = GetAttribute (component, "_style");
				$("pageSizeField").value = GetAttribute (component, "pageSize"); 
				$("newsImageField").value = GetAttribute (component, "newsImage");
				
				$("isNewsImagePlace").checked = GetAttribute (component, "isNewsImagePlace") == "1";
				$("newsConditionField").value = GetAttribute (component, "newsCondition");
				$("whereField").value = GetAttribute (component, "where");
				$("dynaConditionField").value = GetAttribute (component, "dynaCondition");
				//$("privilegeScriptField").value = GetAttribute (component, "privilegeScript");
				 
				var orderFields = GetAttribute (component, "orderBy");
				var sortFields = GetAttribute (component, "sortOrder"); 
				if(orderFields && orderFields.length > 0  && sortFields != null && sortFields.length > 0){
					$A(orderFields.split(',')).each (function (field)
					{
						var option = document.createElement ("OPTION");
						
						$("orderFields").appendChild (option);
						
						option.value = field;
						option.text = field;
					}.bind (this)); 
					
					$A(sortFields.split(',')).each (function (field)
					{
						var option = document.createElement ("OPTION");
						
						$("sortFields").appendChild (option);
						
						option.value = field;
						option.text = field;
					}.bind (this));
				}
			 	
				$A($("infotype").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "infotype");
				});
				 
				
				$A($("showHeaderField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "showHeader");
				});
				
				$A($("showSearchBarField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "showSearchBar");
				});
				
				$A($("paginationField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "pagination");
				});
				
				var tbodyEl = component.getElementsByTagName ("tbody")[0];
				var trEl = tbodyEl.getElementsByTagName ("tr")[0];
				var displayFields = [];
				$A(trEl.getElementsByTagName ("td")).each (function (td)
				{
					displayFields.push (Json.evaluate(GetAttribute (td, "field")));
				});
				
				$A(displayFields).each (function (field)
				{
					var option = document.createElement ("OPTION");
					
					$("displayFieldSelect").appendChild (option);
					
					option.value = Json.toString (field);
					option.innerText = field.title;
				}.bind (this));
			}
		}.bind (this));
		
		this.addEvent ("onComponentInitialize", function (component, editor)
		{
			SetAttribute (component, "channel", $("channelValueField").value);
			SetAttribute (component, "channelText", $("channelTextField").value);
			 
			 if($("isViewOnly").checked)
			 SetAttribute (component, "isViewOnly", "1");
			else
			 SetAttribute (component, "isViewOnly", '0');	
			 
			SetAttribute (component, "_style", $("styleField").value);
			SetAttribute (component, "pageSize", $("pageSizeField").value);
			SetAttribute (component, "showHeader", $("showHeaderField").getValue ());
			SetAttribute (component, "where", $("whereField").value);
			SetAttribute (component, "newsImage", $("newsImageField").value);
			if($("isNewsImagePlace").checked)
			 SetAttribute (component, "isNewsImagePlace", $("isNewsImagePlace").value);
			else
			 SetAttribute (component, "isNewsImagePlace", '');	
			SetAttribute (component, "newsCondition", $("newsConditionField").value);
			if($("dynaConditionField").value!='')
			 SetAttribute (component, "dynaCondition", $("dynaConditionField").value);
			//SetAttribute(component, "privilegeScript", $("privilegeScriptField").getValue ());
			//SetAttribute (component, "orderBy", $("orderByField").value);
			//alert($("orderByField").value);
			SetAttribute(component, "showSearchBar", $("showSearchBarField").value); 
			
			 var orderFields = "";
			$A($("orderFields").options).each (function (option){ 
				orderFields += ","+option.value; 
			});
			SetAttribute (component, "orderBy", orderFields.length > 0 ? orderFields.substring(1) : orderFields);
			
			
			 var sortFields = "";
			$A($("sortFields").options).each (function (option){ 
				sortFields += ","+option.value; 
			});
			SetAttribute (component, "sortOrder", sortFields.length > 0 ? sortFields.substring(1) : sortFields);
			
			
			SetAttribute (component, "infotype", $("infotype").getValue ());
			SetAttribute (component, "pagination", $("paginationField").value);

			$A(component.childNodes).each (function (node)
			{
				component.removeChild (node);
			});
			
			var tbodyEl = editor.FCK.EditorDocument.createElement ("tbody");
			var trEl = editor.FCK.EditorDocument.createElement ("tr");
			tbodyEl.appendChild (trEl);
			var displayFields = [];
			$A($("displayFieldSelect").options).each (function (option)
			{
				var tdEl = editor.FCK.EditorDocument.createElement ("td");
				SetAttribute (tdEl, "field", option.value);
				trEl.appendChild (tdEl);
			});
			component.appendChild (tbodyEl);
		});		
	}
});