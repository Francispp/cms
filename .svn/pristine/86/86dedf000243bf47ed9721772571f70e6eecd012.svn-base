<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<%@ include file="/common/ext/ext-res.inc"%>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/dhtmlxtree/tree.inc"%>

<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>

</head>
<script>
 function huadong(liObj,treeURL,contentURL)
        {
	 
	
        	var ulLink = document.getElementById("menu_id");
        	var siteId=parent.document.getElementById("currentsite").value;
        	var lis = ulLink.getElementsByTagName("li");
        	var liLen = lis.length;
        	for(var x=0;x<liLen;x++)
        		{
        			lis.item(x).className = "menu-link";
        		}
        	liObj.className = "li-hover";
        	alert(treeURL);
		    parent.global_ab.fn.changeBaseFrameContent("",treeURL,contentURL);
        }
        
		</script>
<body>


<div class="menu">
<ul id="menu_id">
<li class="li-hover" style="cursor:hand;" onclick="javascript:huadong(this,'${ctx}/cms/site!channeltree.action','')"><a><span><img src="${default_imagepath}/edit-tubiao.gif"/></span>文档编辑</a></li>
<li class="menu-link" style="cursor:hand;" onclick="javascript:huadong(this,'','${ctx}/cms/site!recycle.action')"><a><span><img src="${default_imagepath}/delete-tubiao.gif"/></span>文档回收站</a></li>

</ul>
</div>



</body>
</html>

