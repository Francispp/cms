var StaticList = Component.extend(
{
	initialize : function ()
	{
		this.parent ("object", 
			"StaticList",  
			"CMS_STATICLIST", 
			"staticlist", 
			"400", 
			"460", 
			"静态列表标签","plugins/cms/images/staticlist.gif");

		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
		
	   	this.views = [
			{name : "Basic", title : "基本", contentEl : "basicView"},
			{name : "Extend", title : "高级设置", contentEl : "extendView"}
			
		]
		
		this.addEvent ("onHandlePage", function (editor)
		{
			Ext.onReady (function ()
			{
				var store = new Ext.data.SimpleStore({fields : ["name", "value"], data : $A(editor.parent.getFormFields().options).map (function (item)
				{ 
					return [item.text, item.value];
				})});
				
				var newBox = new Ext.form.ComboBox ({id : "orderByField", applyTo : "orderByField", mode : "local", store : store, displayField : "name", valueField : "value", triggerAction : "all", wdith : 155});
				//alert($("channelValueField").value);
				if($("channelValueField").value != null && $("channelValueField").value.length > 0)
				{
					
					CoreFormService.getFieldsByChannelId (parseInt($("channelValueField").value), function (data)
									{	
										if (data)
										{
											store.removeAll();
											 var rs = new Array();
											 var rsNumber = 0;
                                                                         
                                                                         $A(data).map (function (coreFormFiel){
                                                                         	var newRecord = new Ext.data.Record([{name: 'name'},{name: 'value'}]);
                                                                         	newRecord.set('name',coreFormFiel.fieldName);
                                                                                newRecord.set('value',coreFormFiel.fieldCode);
                                                                                rs[rsNumber] = newRecord;
                                                                                rsNumber++;
                                                                         	}.bind (this));
                                                                         store.insert(0,rs);
	 
										}
									}.bind (this));
					
				}
				$("popupImg").addEvent ("click", function ()  
				{
					var retval = Json.evaluate (window.showModalDialog("channelPicker.html", null, "dialogWidth=400px;dialogHeight=400px;status=no;scroll=yes;resizable=yes"));
					$("channelTextField").value = retval.text;
					$("channelValueField").value = retval.id;
					//
					CoreFormService.getFieldsByChannelId (retval.id, function (data)
									{	
										if (data)
										{
											store.removeAll();
											 var rs = new Array();
											 var rsNumber = 0;
                                                                         
                                                                         $A(data).map (function (coreFormFiel){
                                                                         	var newRecord = new Ext.data.Record([{name: 'name'},{name: 'value'}]);
                                                                         	newRecord.set('name',coreFormFiel.fieldName);
                                                                                newRecord.set('value',coreFormFiel.fieldCode);
                                                                                rs[rsNumber] = newRecord;
                                                                                rsNumber++;
                                                                         	}.bind (this));
                                                                         store.insert(0,rs);
                                                                       
											 //Ext.getCmp('orderByField').clearValue(); 
                                                                                        // Ext.getCmp('orderByField').store = store; 
											// Ext.getCmp('orderByField').enable(); 
											 //Ext.getCmp('orderByField').show();
										
											 
										}
									}.bind (this));
					
					

					
									
				//
					
				});
				
				
				
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
			
			
		});
		
	    this.addEvent ("onPageInitialize", function (component)
		{
			if (component) 
			{ 
				var fileTypeFields = "";
				fileTypeFields = GetAttribute (component, "displayFieldSelect");
				if(fileTypeFields != null && fileTypeFields.length > 0)
				{
				var jsonArray = Json.evaluate("["+fileTypeFields+"]");
				for(var i=0;i<jsonArray.length;i++)
				{
				var option = document.createElement ("OPTION");
				$("displayFieldSelect").appendChild (option);
					var json = jsonArray[i];
					option.value = Json.toString(jsonArray[i]);
					option.innerText = json.title; 
			        }
			        }
				$("channelValueField").value = GetAttribute (component, "channel");
				$("channelTextField").value = GetAttribute (component, "channelText");
				$("pageSizeField").value = GetAttribute (component, "pageSize");
				$("orderByField").value = GetAttribute (component, "orderBy");
				$("whereField").value = GetAttribute (component, "where");
				$("pathField").value = GetAttribute (component, "pathField");
				$("texthtml").value = GetAttribute (component, "texthtml");
				$("pagehtml").value = GetAttribute (component, "pagehtml");
				$("keyID").value = GetAttribute (component, "keyID");
				
				$("linkOpenInNewWindowField").checked = GetAttribute (component, "linkOpenInNewWindowField") == "1";
				
				Ext.onReady (function ()
				{
					Ext.getCmp("orderByField").store.each (function (record)
					{
						if (record.get("value") == GetAttribute (component, "name"))
						{
							$("orderByField").value = record.get("name");
						}
					});
				});
				$A($("sortOrderField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "sortOrder");
				});
				$A($("infotype").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "infotype");
				});
				$A($("typeField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "typeField");
				});
				$A($("formatField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "formatField");
				});
				$A($("paginationField").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "pagination");
				});
				$A($("tableView").options).each (function (option)
				{
					option.selected = option.value == GetAttribute (component, "tableView");
				});
				updateHolders ();
				
				
			}
		}.bind (this));
		
		this.addEvent ("onComponentInitialize", function (component, editor)
		{
			SetAttribute (component, "channel", $("channelValueField").value);
			SetAttribute (component, "channelText", $("channelTextField").value);
			SetAttribute (component, "pageSize", $("pageSizeField").value);
			SetAttribute (component, "where", $("whereField").value);
			SetAttribute (component, "pagination", $("paginationField").value);
			SetAttribute (component, "tableView", $("tableView").value);
			SetAttribute(component, "typeField", $("typeField").getValue ());
			SetAttribute(component, "formatField", $("formatField").getValue ());
			SetAttribute (component, "pathField", $("pathField").value);
			SetAttribute (component, "infotype", $("infotype").getValue ());
			if($("keyID").value != null && $("keyID").value.length >0)
			{
			SetAttribute (component, "keyID", $("keyID").value);
			}
		        else
		        {
		          SetAttribute (component, "keyID", String(new Date().getTime()));
		        }
			if($("linkOpenInNewWindowField").checked)
			 SetAttribute (component, "linkOpenInNewWindowField", "1");
			else
			 SetAttribute (component, "linkOpenInNewWindowField", '');
			
			Ext.getCmp("orderByField").store.each (function (record)
			{
				if (record.get("name") == $("orderByField").getValue ())
				{
					
					SetAttribute (component, "orderBy", record.get("value"));
				}
			});
			SetAttribute (component, "sortOrder", $("sortOrderField").getValue ());
			$A(component.childNodes).each (function (node)
			{
				component.removeChild (node);
			});
			SetAttribute (component, "texthtml", $("texthtml").value);
			SetAttribute (component, "pagehtml", $("pagehtml").value);
			var fileTypeFields = [];
			$A($("displayFieldSelect").options).each (function (option)
			{
			fileTypeFields.push(option.value);
				
			});
			SetAttribute (component, "displayFieldSelect", String(fileTypeFields));
		});		
	}
});