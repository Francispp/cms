var Homewaplist = Component.extend(
{
	initialize : function ()
	{
		this.parent ("object", 
			"Homewaplist",  
			"CMS_HOMEWAPLIST", 
			"homewaplist", 
			"450", 
			"600", 
			"WAP首页列表标签","plugins/cms/images/homewaplist.gif");

		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
		
	   	this.views = [
			{name : "Basic", title : "基本", contentEl : "basicView"},
			{name : "Title", title : "Title设置", contentEl : "titleView"},
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
				
				//var newBox = new Ext.form.ComboBox ({id : "orderByField", applyTo : "orderByField", mode : "local", store : store, displayField : "name", valueField : "value", triggerAction : "all", wdith : 155});
				var newBox = new Ext.form.ComboBox ({id : "optionField", applyTo : "optionField", mode : "local", store : store, displayField : "name", valueField : "value", triggerAction : "all", wdith : 100});
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
					var np = retval.text.split('|'); 
					$("channelTextField").value = np[0];
					$("channelValueField").value = retval.id;
					$("channelPathField").value = np[1];
				 
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
					{      var fieldValue;
					         Ext.getCmp("availableDisplayFieldInput").store.each (function (record)
						{
							if (record.get("name") == $("availableDisplayFieldInput").value)
							{
								
								fieldValue =record.get("value");
								
							}
						});
						
						var textBox = $("texthtml");
                                                var pre = textBox.value.substr(0, start);
                                                var post = textBox.value.substr(end);
                                                var formatField = $("formatField").value;
                                                
                                                if($("typeField").getValue () == "date")
                                                {
                                               textBox.value = pre +"<ww:text name='{0, date, "+formatField+"}'><ww:param name='value' value='"+fieldValue+"'/></ww:text>" + post;
                                                }
                                                else if($("typeField").getValue () == "link" || $("typeField").getValue () == "news")
                                                {
                                                 if($("linkOpenInNewWindowField").checked)
                                                   textBox.value = pre + "<a href='"+$("pathField").value+"' target='_blank' >${"+fieldValue+"}</a>" + post;
                                                 else
                                                   textBox.value = pre + "<a href='"+$("pathField").value+"' >${"+fieldValue+"}</a>" + post;
                                               }
                                                else
                                                textBox.value = pre + "${"+fieldValue+"}" + post;
					}
				});
				
				
			});
			
			
		});
		
	    this.addEvent ("onPageInitialize", function (component)
		{
			if (component) 
			{ 
				$("channelValueField").value = GetAttribute (component, "channel");
				$("channelTextField").value = GetAttribute (component, "channelText");
				$("channelPathField").value = GetAttribute (component, "channelPath");
				$("pageSizeField").value = GetAttribute (component, "pageSize"); 
				$("whereField").value = GetAttribute (component, "where");
				$("texthtml").value = GetAttribute (component, "texthtml");
				$("pathField").value = GetAttribute (component, "pathField");
				$("titleHead").value = GetAttribute (component, "titleHead");
				$("titleEnd").value = GetAttribute (component, "titleEnd");
				$("page").value = GetAttribute (component, "page");
				$("dynaConditionField").value = GetAttribute (component, "dynaCondition");
				
				$("linkOpenInNewWindowField").checked = GetAttribute (component, "linkOpenInNewWindowField") == "1";
				
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
				updateHolders ();
				
			}
		}.bind (this));
		
		this.addEvent ("onComponentInitialize", function (component, editor)
		{
			SetAttribute (component, "channel", $("channelValueField").value);
			SetAttribute (component, "channelText", $("channelTextField").value);
			SetAttribute (component, "channelPath", $("channelPathField").value);
			SetAttribute (component, "pageSize", $("pageSizeField").value);
			SetAttribute (component, "where", $("whereField").value);
			SetAttribute (component, "pagination", $("paginationField").value);
			SetAttribute(component, "typeField", $("typeField").getValue ());
			SetAttribute(component, "formatField", $("formatField").getValue ());
			SetAttribute (component, "pathField", $("pathField").value);
			SetAttribute (component, "titleHead", $("titleHead").value);
			SetAttribute (component, "titleEnd", $("titleEnd").value);
			SetAttribute (component, "page", $("page").value);
			SetAttribute (component, "infotype", $("infotype").getValue ());
			if($("dynaConditionField").value!='')
			 SetAttribute (component, "dynaCondition", $("dynaConditionField").value);
			if($("linkOpenInNewWindowField").checked)
			 SetAttribute (component, "linkOpenInNewWindowField", "1");
			else
			 SetAttribute (component, "linkOpenInNewWindowField", '');
			
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

			$A(component.childNodes).each (function (node)
			{
				component.removeChild (node);
			});
			SetAttribute (component, "texthtml", $("texthtml").value);
			
			
			
		});		
	}
});