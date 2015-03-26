<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="共享文档库" />
<html>
<head>
<title>${title}</title>
<link href="${ctx}/common/ext-2.0.2/resources/css/ext-all.css" rel="stylesheet" type="text/css"/>
		<script type="text/javascript" src="${ctx}/common/mootools.js"></script>
		<script type="text/javascript" src="${ctx}/common/ext-2.0.2/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="${ctx}/common/ext-2.0.2/ext-all.js"></script>
		<script src="${ctx}/dwr/interface/SiteManagerService.js" type="text/javascript"></script>
		<script src="${ctx}/dwr/interface/ChannelManagerService.js" type="text/javascript"></script>
		<script src="${ctx}/dwr/interface/DocumentCommonService.js" type="text/javascript"></script>
		<script src="${ctx}/dwr/engine.js" type="text/javascript"></script>
</head>
<body nowrap topmargin="0" leftmargin="0" >
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>				                     				
<div id="treePanel" ></div>
		<script type="text/javascript">
		var userid = "";
			window.returnValue = Json.toString ({ id : "", text : ""});
			
			Ext.onReady(function()
			{
				var ChannelTreeLoader = Ext.extend(Ext.tree.TreeLoader,
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
												node.appendChild (this.createNode ({ text : site.sitename, type : 1, site : site.oid }));
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
				            	callback(this, node);
				            	}
				            	else if (node.attributes.type == 1)
				            	{				            	
				            		ChannelManagerService.getFirstChannelsBySite (node.attributes.site, function (data)
									{	
										if (data)
										{
											node.beginUpdate ();
											
											$A(data).each (function (channel)
											{ 
											        var tnode= this.createNode ({ text : channel.name, type : 2, id : channel.id});									  
												  tnode.icon='';
												node.appendChild (tnode);
											}.bind (this));
										
											node.endUpdate ();
												
											if(typeof callback == "function")
							            	{
							                	callback(this, node);
							            	}
										}
									}.bind (this));
				            	}
				            	else if (node.attributes.type == 2)
				            	{				            	
				            		ChannelManagerService.findByParent (node.attributes.id, function (data)
									{
										if (data)
										{
											node.beginUpdate ();
											
											 if (data.length == 0){
										        DocumentCommonService.getShareDocumentsByChannel(node.attributes.id,function(docData)
										         {
										           $A(docData).each (function (document)
											       {
												     node.appendChild (this.createNode ({ text : document.title, type : 3, id : document.id, leaf:true}));
											        // node.expandChildNodes(true); 
											       }.bind (this));
											       
										         }.bind(this));
										       }
										       else
										       {
											     $A(data).each (function (channel)
											       {
												     node.appendChild (this.createNode ({ text : channel.name, type : 2, id : channel.id}));
												
											       }.bind (this));
										       }
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
				
				var treePanel = new Ext.tree.TreePanel ({ id : "treePanel", renderTo : "treePanel", root : new Ext.tree.AsyncTreeNode ({ loader : new ChannelTreeLoader ()}), rootVisible : false, border : false});
				
				treePanel.getSelectionModel ().addListener ("selectionchange", function (model, node)
				{
					if (node.attributes.type == 3)
					{
					   // DocumentCommonService.createDocumentByShare(169,node.id);
						//window.returnValue = Json.toString ({ id : node.id, text : node.text });
						window.returnValue = node.id;
						window.close ();
					}
				}, this);
				
				treePanel.getRootNode ().expand ();
			});
		</script>
	 
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
