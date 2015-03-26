var Component = new Class (
{
	initialize : function (tagName, componentType, commandName, pageName, pageWidth, pageHeight, alias, image)
	{
		this.tagName = tagName;
		this.componentType = componentType;
		this.commandName = commandName;
		this.pageName = pageName;
		this.pageWidth = $defined (pageWidth) ? pageWidth : "350";
		this.pageHeight = $defined (pageHeight) ? pageHeight : "350";
		this.alias = alias;
		this.image = image;
		this.views = [];
		this.resources="";
		
		if ($defined (this.image))
		{
			this.addEvent ("onProcessComponent", function (document, component, editor)
			{
				var imageEl;
				if (editor)
				{
					imageEl = new Element ("img", { 
						"src" : this.image,
						"_fckfakelement" : "true",
						"_fckrealelement": editor.FCKTempBin.AddElement(component),
						"t_type" : this.componentType
					});
				}
				else
				{
					imageEl = new Element ("img", { 
						"src" : this.image,
						"_fckfakelement" : "true",
						"_fckrealelement" : FCKTempBin.AddElement(component),
						"t_type" : this.componentType
					});
				}
						
				component.insertAdjacentHTML ("beforeBegin", $(imageEl).outerHTML);
				component.parentNode.removeChild (component);
			}.bind (this));
		}
	},
	
	register : function ()
	{
		var page = FCKConfig.PluginsPath + "cms/dialog/" + this.pageName + ".html";
		if (this.alias)
		{
			FCKCommands.RegisterCommand(this.commandName, new FCKDialogCommand(this.commandName, this.alias, page, this.pageWidth, this.pageHeight));
			FCKToolbarItems.RegisterItem(this.commandName, new FCKToolbarButton(this.commandName, this.alias, null, FCK_TOOLBARITEM_ONLYICON, true, true));
		}
		else
		{
			FCKCommands.RegisterCommand(this.commandName, new FCKDialogCommand(this.commandName, this.componentType, page, this.pageWidth, this.pageHeight));
			FCKToolbarItems.RegisterItem(this.commandName, new FCKToolbarButton(this.commandName, this.componentType, null, FCK_TOOLBARITEM_ONLYICON, true, true));
		}
		
		FCK.RegisterDoubleClickHandler (function (element)
		{ 
			if (GetAttribute (element, "t_type") == this.componentType)
			{
				FCKCommands.GetCommand (this.commandName).Execute ();
			}
		}.bind (this), "*");
		
		FCK.ContextMenu.RegisterListener(
		{
	        AddItems : function(menu, tag, selectedTagName)
	        {
                if (GetAttribute (tag, "t_type") == this.componentType)
                {
					menu.AddSeparator();
					menu.AddItem(this.commandName, $defined (this.alias) ? this.alias : this.componentType, this.image);
                }
	        }.bind (this)
		});
		
		FCKDocumentProcessor.AppendNew().ProcessDocument = function (document)
		{
			$A(document.getElementsByTagName("*")).each (function (element)
			{
				if (element.attributes)
				{
					if (GetAttribute (element, "t_type") == this.componentType)
					{
						this.fireEvent ("onProcessComponent", [document, element]);
					}
				}
			}.bind (this));
		}.bind (this);
	},
	
	handleDialog : function (editor)
	{
		try{

		if (!$defined (editor))
			//从 FCK 编辑器的弹出窗口中获得 FCK 编辑器实例
			editor = window.parent.InnerDialogLoaded();
		//获得权限资源
		if(editor.parent) 
		 this.resources=editor.parent.getResourceFields();	
		}catch(e){}	
		var selectedEl = window.parent.Selection.GetSelectedElement();
		var activeEl;
		if (!selectedEl || GetAttribute (selectedEl, "t_type") != this.componentType)
		{
			selectedEl = editor.FCK.Selection.MoveToAncestorNode (this.tagName.toUpperCase ());
			
			if (selectedEl)
			{
				selectedEl = GetAttribute (selectedEl, "t_type") == this.componentType ? selectedEl : null;
			}
		}
		if (selectedEl && selectedEl._fckfakelement)
		{
			selectedEl = editor.FCK.GetRealElement (selectedEl);
		}
		activeEl = selectedEl;
		
		window.addEvent ("load", function ()
		{
			editor.FCKLanguageManager.TranslatePage(document);
			
			window.parent.SetOkButton(true);
			
			this.views.each (function (view)
			{
				window.parent.AddTab (view.name, view.title);
				
				if (view.page)
				{
					new Ajax (view.page, { method : "get", evalScripts : true, async : false, update : $(view.contentEl)}).request ();
				}
			}.bind (this));
			
			window.OnDialogTabChange = function (tabCode)
			{
				this.views.each (function (view)
				{
					ShowE(view.contentEl, (tabCode == view.name));
				}.bind (this));
			}.bind (this);
			
			this.fireEvent ("onHandlePage", editor);
			this.fireEvent ("onPageInitialize", activeEl);
		}.bind (this));
		
		window.parent.Ok = function ()
		{ 
		if($("readOnlyPrivilegeScriptField")!=null && $("readOnlyPrivilegeScriptField").value == '' && $("hiddenPrivilegeScriptField")!=null && $("hiddenPrivilegeScriptField").value == '' && $("privilegeScriptField")!=null)
		{
			$("privilegeScriptField").value = '';
		}
		 if($("nameField")!=null && ($("nameField").value == null || $("nameField").value == ''))
		    {
		     alert("名称不能为空!");
		    }
		   else
		    {
		     this.fireEvent ("onPageOk");
		    }
		}.bind (this)
		
		this.addEvent ("onPageOk", function ()
		{
			if (!activeEl)
			{
				activeEl = editor.FCK.EditorDocument.createElement(this.tagName.toLowerCase ());
				
				SetAttribute (activeEl, "t_type", this.componentType);
				
				this.fireEvent ("onComponentCreated", activeEl);
				
				activeEl = editor.FCK.InsertElementAndGetIt(activeEl);
			}
				 
			if (activeEl)
			{	
				this.fireEvent ("onComponentInitialize", [activeEl, editor]);
			}
			
			window.parent.Cancel ();
			
			this.fireEvent ("onProcessComponent", [editor.FCK.EditorDocument, activeEl, editor]);
		}.bind (this));
		this.addEvent ("onPageInitialize", function (component)
		{
			//增加权限选择项
			if($("popedom_choose")!=null && this.resources!=null ){			
			  for(var i=0;i<this.resources.length;i++)
			   $("popedom_choose").options[i]=new Option(this.resources.options[i].text,this.resources.options[i].value);
			}	
			
			if($("delete_choose")!=null && this.resources!=null ){			
			  for(var i=0;i<this.resources.length;i++)
			   $("delete_choose").options[i]=new Option(this.resources.options[i].text,this.resources.options[i].value);
			   
			    $A($("delete_choose").options).each (function (option)
				{
					option.selected = $A(GetAttribute (component, "delete_choose").split (" ")).contains (option.value);
				});
			}	
		}.bind (this));
	}
});
Component.implement(new Events);

var ActiveX = Component.extend (
{
	initialize : function (tagName, componentType, classid, commandName, pageName, pageWidth, pageHeight, alias, image)
	{
		this.parent (tagName, componentType, commandName, pageName, pageWidth, pageHeight, alias, image);
		this.classid = classid;
		
	    var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
		this.views = [
			{name : "PrivilegeScript", title : "权限控制", contentEl : "privilegeScriptView", page : basepath + "privilegeScript.html" }
		];
		this.addEvent ("onPageInitialize", function (component)
		{	
		     if($("readOnlyPrivilegeScriptField"))
			$("readOnlyPrivilegeScriptField").value = GetAttribute (component, "readOnlyPrivilegeScriptField");
		     if($("hiddenPrivilegeScriptField"))
			$("hiddenPrivilegeScriptField").value = GetAttribute (component, "hiddenPrivilegeScriptField");
		     if($("privilegeScript"))  
		        $("privilegeScriptField").value = GetAttribute (component, "privilegeScript");
		}.bind (this));
		
		this.addEvent ("onComponentInitialize", function (component)
		{
		    if($("readOnlyPrivilegeScriptField"))
		     SetAttribute(component, "readOnlyPrivilegeScriptField", $("readOnlyPrivilegeScriptField").getValue ());
		    if($("hiddenPrivilegeScriptField"))
		     SetAttribute(component, "hiddenPrivilegeScriptField", $("hiddenPrivilegeScriptField").getValue ());
			
		    if($("privilegeScriptField"))
  		     SetAttribute(component, "privilegeScript", $("privilegeScriptField").getValue ());
		});		
	}
});

var FormField = Component.extend(
{
	initialize : function (tagName, componentType, commandName, pageName, pageWidth, pageHeight, alias, image)
	{	
		this.parent (tagName, componentType, commandName, pageName, pageWidth, pageHeight, alias, image);
		
		var basepath = window.location.pathname.substring (0, window.location.pathname.lastIndexOf ("/")) + "/common/";
			
		this.views = [
			{name : "ValueScript", title : "值脚本", contentEl : "valueScriptView", page : basepath + "valueScript.html" },
			{name : "ValidateScript", title : "校验脚本", contentEl : "validateScriptView", page : basepath + "validateScript.html" },
			{name : "PrivilegeScript", title : "权限控制", contentEl : "privilegeScriptView", page : basepath + "privilegeScript.html" }
		];
		
		this.addEvent ("onPageInitialize", function (component)
		{
			if (component)
			{							        
				$("valueScriptField").value = GetAttribute (component, "valueScript");
				$("privilegeScriptField").value = GetAttribute (component, "privilegeScript");
				$("readOnlyPrivilegeScriptField").value = GetAttribute (component, "readOnlyPrivilegeScriptField");
				$("hiddenPrivilegeScriptField").value = GetAttribute (component, "hiddenPrivilegeScriptField");
				$("validateScriptTypeField").value = GetAttribute (component, "validateScriptType");
				$("validateScriptField").value = GetAttribute (component, "validateScript");
				//$A($("systemValidateScriptField").options).each (function (option)
				//{
				//	option.selected = $A(GetAttribute (component, "systemValidate").split (" ")).contains (option.value);
				//});
				
				
				$A(GetAttribute (component, "systemValidate").split (" ")).each (function (option)
				{
					
					var copy = document.createElement ("OPTION");
					$("systemValidateScriptField").options.add (copy);
					copy.value = option;
					copy.innerText = option;
					
					
					
					
					
				}.bind (this));
				
				
				
			}
		
		}.bind (this));
		
		this.addEvent ("onComponentInitialize", function (component)
		{
			SetAttribute(component, "valueScript", $("valueScriptField").getValue ());
			SetAttribute(component, "validateScriptType", $("validateScriptTypeField").getValue ());
			SetAttribute(component, "validateScript", $("validateScriptField").getValue ());
			var selectedOptions = [];
			$A($("systemValidateScriptField").options).each (function (option)
			{
				//if (option.selected)
				//{
					selectedOptions.push (option.value);
				//}
			});
			
			SetAttribute(component, "systemValidate", selectedOptions.length == 0 ? "" : selectedOptions.join (" "));
		});
	}
});