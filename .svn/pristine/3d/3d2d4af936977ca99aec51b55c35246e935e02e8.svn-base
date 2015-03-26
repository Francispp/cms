var DocumentLoop = Component.extend(
{
	initialize : function ()
	{
		this.parent ("dir", 
			"DocumentLoop", 
			"CMS_DOCUMENTLOOP", 
			"documentLoop", 
			"400", 
			"360", 
			"信息列表");
		
		this.addEvent ("onProcessElement", function (document, element)
		{
			var pageSize = GetAttribute (element, "pageSize");
			var nodes = new Array ();
			
			if (pageSize)
			{
				for (var index = 0; index < pageSize - 1; index++)
				{
					$A(element.childNodes).each (function (childNode)
					{
						var node = null;
						if ($type (childNode) == "textnode")
						{
							node = document.createElement ("span");
							SetAttribute (node, "_fcktextholder", "true");
							node.appendChild (childNode.cloneNode (true));
						}
						else
						{
							node = childNode.cloneNode (true);
						}
						
						node.setAttribute ("_fckfakelement", "true", 0);
						
						nodes.push (node);
					});
				}
				
				$A(nodes).each (function (node)
				{
					element.appendChild (node);
				});
			}
		}.bind (this));
		
		this.addEvent ("onHandlePage", function (editor)
		{
			var element = window.parent.Selection.MoveToAncestorNode(this.tagName);
			
			if (element && GetAttribute (element, "t_type") == this.componentType)
			{
				window.addEvent ("onload", function ()
				{
					var cloned = element.cloneNode (true);
					$A(cloned.childNodes).each (function (childNode)
					{
						if (GetAttribute (childNode, "_fckfakelement"))
						{
							cloned.removeChild (childNode);
						}
					});
				
					$("queryField").value = GetAttribute (element, "query");
					$("orderField").value = GetAttribute (element, "order");
					$("valueField").value = GetAttribute (element, "value");
					$("pageIndexField").value = GetAttribute (element, "pageIndex");
					$("pageSizeField").value = GetAttribute (element, "pageSize");
					$("ascendingField").checked = GetAttribute (element, "sort") == null || GetAttribute (element, "sort") == "" || GetAttribute (element, "sort") == "ascending" ? true : false;
					$("bodyField").value = element.innerHTML;
					
					window.parent.SetOkButton (true);
				}.bind (this));
				
				window.parent.Ok = function ()
				{
					SetAttribute(element, "query", $("queryField").value);
					SetAttribute(element, "order", $("orderField").value);
					SetAttribute(element, "sort", $("ascendingField").checked ? "ascending" : "descending");
					SetAttribute(element, "value", $("valueField").value);
					SetAttribute(element, "pageIndex", $("pageIndexField").value);
					SetAttribute(element, "pageSize", $("pageSizeField").value);
					element.innerHTML = $("bodyField").value;
					
					window.parent.Cancel ();
				}.bind (this);
			}
		});
	}
});