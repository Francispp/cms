Search = Component.extend(
{
	initialize : function ()
	{
		this.parent ("table", 
			"Search",  
			"CMS_SEARCH", 
			"search", 
			"400", 
			"500", 
			"全文搜索");
		
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
						var store = new Ext.data.SimpleStore({fields : ["_name", "value"], data : $A(editor.parent.getFormFields().options).map (function (item)
						{
							return [item.text, item.value];
						})});
						var comboBox = new Ext.form.ComboBox ({id : "availableDisplayFieldInput", applyTo : "availableDisplayFieldInput", mode : "local", store : store, width : 150, displayField : "_name", valueField : "value", triggerAction : "all"});
					});
			
			
			
			$("addFieldButton").addEvent ("click", function ()
			{
				if ($("availableDisplayFieldInput").value)
				{
				
					var listDefault=$("showList").getValue ();
					
					if(listDefault!=null&&listDefault!='default'){
						  var fieldValue;
					         Ext.getCmp("availableDisplayFieldInput").store.each (function (record)
						{
							if (record.get("name") == $("availableDisplayFieldInput").value)
							{
								fieldValue =record.get("value");
								
							}
						});
						if(fieldValue==undefined){
							fieldValue=$("availableDisplayFieldInput").value;
						}
						var textBox = $("texthtml");
	                                            var pre = textBox.value.substr(0, start);
	                                            var post = textBox.value.substr(end);
	                                            textBox.value = pre + "<ww:property value=\"get ('"+fieldValue+"')\"></ww:property>" + post;
					
					}else{
						
					
					var option = document.createElement ("OPTION");
					option.value = Json.toString ({ title : $("availableDisplayFieldInput").value, property : $("availableDisplayFieldInput").value}); 
					option.innerText = $("availableDisplayFieldInput").value;
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
							}
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
				$("pageSizeField").value = GetAttribute (component, "pageSize");
				$("page").value = GetAttribute (component, "page");
				$("publishedField").checked = GetAttribute (component, "published") == "true" ? true : false;
				$A($("styleField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "_style");
				});
				
				$A($("showHeaderField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "showHeader");
				});
				
				$A($("showList").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "showList");
				});
				
				
				var listDefault=GetAttribute (component, "showList");
				
				if(listDefault!=null&&listDefault!='default'){
					$("texthtml").value = GetAttribute (component, "texthtml");
				}else{
				
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
			}
			
			(function initView(){
				var listDefault=GetAttribute (component, "showList");
				if(listDefault!=null&&listDefault!=''&&listDefault!='default'){
					$("showListHeader").style.display="none";
				}
					$(listDefault+"ShowList").style.display="";
				
				var temp=$('showList');
				for(var i=0;i<temp.length;i++){
					if($(temp[i]).selected){
						if(temp[i].value=='custom'){
							$("showListHeader").style.display="none";
						}
						$(temp[i].value+"ShowList").style.display="";
					}
				}
			})();
			
		}.bind (this));
		
		this.addEvent ("onComponentInitialize", function (component, editor)
		{
			
			SetAttribute(component, "pageSize", $("pageSizeField").getValue ());
			SetAttribute(component, "page", $("page").getValue ());
			SetAttribute(component, "_style", $("styleField").getValue ());
			SetAttribute(component, "site", editor.parent.document.getElementById ("siteField").value);
			SetAttribute(component, "published", $("publishedField").checked ? "true" : "false");
			SetAttribute (component, "showHeader", $("showHeaderField").getValue ());
			$A(component.childNodes).each (function (node)
			{
				component.removeChild (node);
			});
			
			SetAttribute (component, "showList", $("showList").getValue ());
			
			var listDefault=$("showList").getValue ();
			if(listDefault!=null&&listDefault!='default'){
				SetAttribute (component, "texthtml", $("texthtml").value);
				var tbodyEl = editor.FCK.EditorDocument.createElement ("tbody");
				var trEl = editor.FCK.EditorDocument.createElement ("tr");
				tbodyEl.appendChild (trEl);
				var tdEl = editor.FCK.EditorDocument.createElement ("td");
				SetAttribute (tdEl, "field", "{'title':'custom','property':'custom'}");
				trEl.appendChild (tdEl);
				component.appendChild (tbodyEl);
				
			}else{
				
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
				
				
		}
			
		}.bind (this));
	}
});