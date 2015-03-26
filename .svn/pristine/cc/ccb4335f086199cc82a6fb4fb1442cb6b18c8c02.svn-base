var TemplateInclude = Component.extend(
{
	initialize : function ()
	{
		this.parent ("dir", 
			"TemplateInclude",  
			"CMS_TEMPLATEINCLUDE", 
			"templateInclude", 
			"340", 
			"500",  
			"嵌套模板");
		
		this.addEvent ("onProcessComponent", function (document, component, editor)
		{
			if (GetAttribute (component, "action") == "reference")
			{
				var imageEl = new Element ("img", { 
					"src" : "plugins/cms/images/templateInclude.gif",
					"_fckfakelement" : "true",
					"_fckrealelement" : editor ? editor.FCKTempBin.AddElement(component) : FCKTempBin.AddElement(component),
					"t_type" : this.componentType
				});
					
				component.insertAdjacentHTML ("beforeBegin", $(imageEl).outerHTML);
				component.parentNode.removeChild (component);
			}							//当前站点
			

		}.bind (this));
			
		this.addEvent ("onHandlePage", function (component)
		{
			$("siteField").value=component.parent.document.getElementById ("siteField").value;	
			Ext.onReady(function()
			{
				var TemplateTreeLoader = Ext.extend(Ext.tree.TreeLoader,
				{
					load : function(node, callback)
					{
				        if(this.clearOnLoad)
				        {
				            while(node.firstChild)
				            {
				                node.removeChild(node.firstChild);
				            }
				        }
				        
				       if(this.doPreload(node))
				        {
				            if(typeof callback == "function")
				            {
				                callback();
				            }
				        }
				        else
				        {
				            if(this.fireEvent("beforeload", this, node, callback) !== false)
				            {	
				            	if (node == node.getOwnerTree ().getRootNode ())
				            	{
				            		SiteManagerService.getAllSites (function (data)
									{	
										if (data)
										{
											node.beginUpdate ();
											
											$A(data).each (function (site)
											{											
											 if($("siteField").value==(site.oid+''))
											  node.appendChild (this.createNode ({ text : site.sitename, type : 0, site : site.oid }));
											}.bind (this));
											
											node.endUpdate ();
											
											if(typeof callback == "function")
							            	{
							                	callback(this, node);
							            	}
										}
									}.bind (this));				            		
				            		//node.appendChild (this.createNode ({ text : "站点模板", type : 0 }));
				            		//node.appendChild (this.createNode ({ text : "公共模板", type : 1 }));
				            	}
				            	/*else if (node.attributes.type == 0)
				            	{
				            		SiteManagerService.getAllSites (function (data)
									{	
										if (data)
										{
											node.beginUpdate ();
											
											$A(data).each (function (site)
											{
												node.appendChild (this.createNode ({ text : site.sitename, type : 2, site : site.oid }));
											}.bind (this));
											
											node.endUpdate ();
											
											if(typeof callback == "function")
							            	{
							                	callback(this, node);
							            	}
										}
									}.bind (this));
				            	}
				            	else if (node.attributes.type == 0)
				            	{
				            		TemplateManagerService.findCommons (function (data)
									{	
										if (data)
										{
											node.beginUpdate ();
											
											$A(data).each (function (template)
											{
												node.appendChild (this.createNode ({ text : template.name, id : template.id, type : 4, leaf : true}));
											}.bind (this));
											
											node.endUpdate ();
											
											if(typeof callback == "function")
							            	{
							                	callback(this, node);
							            	}
										}
									}.bind (this));
				            	}*/
				            	else if (node.attributes.type == 0)
				            	{
				            		ChannelManagerService.getFirstChannelsBySite (node.attributes.site, function (data)
									{	
										if (data)
										{
											node.beginUpdate ();
											
											$A(data).each (function (channel)
											{									
											        
												 node.appendChild (this.createNode ({ text : channel.name, type : 3, channel : channel.id}));
											}.bind (this));
										
											node.endUpdate ();
												
											if(typeof callback == "function")
							            	{
							                	callback(this, node);
							            	}
										}
									}.bind (this));
									
									TemplateManagerService.findBySiteNotChannel (node.attributes.site, function (data)
									{	
										if (data)
										{
											node.beginUpdate ();
											
											$A(data).each (function (template)
											{											
												node.appendChild (this.createNode ({ text : template.name, id : template.id, type : 4, leaf : true}));
											}.bind (this));
										
											node.endUpdate ();
												
											if(typeof callback == "function")
							            	{
							                	callback(this, node);
							            	}
										}
									}.bind (this));
				            	}
				            	else if (node.attributes.type == 3)
				            	{
				            		ChannelManagerService.findByParent (node.attributes.channel, function (data)
									{
										if (data)
										{
											node.beginUpdate ();
											
											$A(data).each (function (channel)
											{
												node.appendChild (this.createNode ({ text : channel.name, type : 3, channel : channel.id}));
											}.bind (this));
											
											node.endUpdate ();
											
											if(typeof callback == "function")
							            	{
							                	callback(this, node);
							            	}
										}
									}.bind (this));
									
									TemplateManagerService.findByChannel (node.attributes.channel, function (data)
									{	
										if (data)
										{
											node.beginUpdate ();
											
											$A(data).each (function (template)
											{
												node.appendChild (this.createNode ({ text : template.name, id : template.id, type : 4, leaf : true}));
											}.bind (this));
											
											node.endUpdate ();
										
											if(typeof callback == "function")
							            	{
							                	callback(this, node);
							            	}
										}
									}.bind (this));
				            	}
				            }
				            else
				            {
				            	if(typeof callback == "function")
				            	{
				                	callback();
				            	}
				            }
				        }
				    }
				});

				var treePanel = new Ext.tree.TreePanel ({ id : "treePanel", 
					renderTo : "treePanel", 
					root : new Ext.tree.AsyncTreeNode ({ loader : new TemplateTreeLoader ()}), 
					rootVisible : false, 
					border : false,
					autoScroll : true,
					height : 350 });
				treePanel.addListener ("click", function (node)
				{
					if (node.isLeaf ())
					{
						$("templateField").value = node.id;
						$("templateNameField").value = node.text;
						window.parent.Ok ();
					}
				});
				
				treePanel.getRootNode ().expand ();
				
			});
		}.bind (this));
		
		this.addEvent ("onPageInitialize", function (component)
		{
			if (component)
			{
				$("referenceActionField").checked = GetAttribute (component, "action") == "reference";
				$("templateField").value = GetAttribute (component, "template");
				$("templateNameField").value = GetAttribute (component, "templateName");
			}
		});
		
		this.addEvent ("onComponentInitialize", function (component)
		{
			SetAttribute (component, "template", $("templateField").value);
			SetAttribute (component, "templateName", $("templateNameField").value);
			SetAttribute (component, "action", $("insertActionField").checked ? "insert" : "reference");
			
			
			if ($("insertActionField").checked && GetAttribute (component, "template"))
			{
				TemplateManagerService.getById (GetAttribute (component, "template"), { callback : function (template)
				{
					component.innerHTML = template.body;
				}, async : false });
			}
		}.bind (this));
	}
});