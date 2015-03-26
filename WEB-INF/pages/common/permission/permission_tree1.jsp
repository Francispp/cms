<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="权限设置" />

<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">	
<%@ include file="/common/js.inc"%>
<!--link rel="stylesheet" href="${ctx}/styles/dtree.css" type="text/css"-->
<%@ include file="/common/cxtree.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>

<script type="text/javascript">
   function savePermission(){
        var msg="确定要保存设置的权限信息吗？";
 	Ext.MessageBox.confirm('提示', msg, function(btn){
	if(btn=='yes'){
	   var ids=getSelectIds();
	   myform.keys.value=ids;
	   myform.submit();
	}
	});
  }
   
 
  //选中或去除子节点
  function selectChildNodes(_this){
        var check_obj=document.getElementById(_this.selectId);        
        if(_this.childNodes!=null){        
    	for (var i = _this.childNodes.length - 1; i >= 0; i--) {
    	       var sub_check_obj=document.getElementById(_this.childNodes[i].selectId);    	   
    	        sub_check_obj.checked=check_obj.checked;
    	        //alert(check_obj.checked); 
    	        selectChildNodes(_this.childNodes[i]);
		}
    }
  }
  //检测父节点是否为选中或不选中
  function checkParentNodes(_this){
   //var check_obj=document.getElementById(_this.selectId);
   if(_this.parentNode!=null){ 
    var pnode= _this.parentNode; 
    var pcheck_obj=document.getElementById(pnode.selectId);
 
        if(pnode.childNodes!=null){
        var p_checked=true;        
    	for (var i = pnode.childNodes.length - 1; i >= 0; i--) {

    	       var sub_check_obj=document.getElementById(pnode.childNodes[i].selectId);    	   
    	        if(sub_check_obj.checked==false){
    	         p_checked=false;
    	         break;
    	         }    	       
		}
	 pcheck_obj.checked=p_checked;	
	 checkParentNodes(_this.parentNode);	
    }    
    }
  }
  //设置资源是否选中
  function setResourceNodeSelected(resourceids,isSelected){
   if(isSelected==null||isSelected=='')//默认为选中
    isSelected=true;
   //设置选中（或取消）对象
   if(resourceids!=null && resourceids.length!=null){
    for(var i=0;i<resourceids.length;i++){
     var tnode=eval('Tr'+resourceids[i]);//获得节点名称
     if(tnode!=null){     
      var check_obj=document.getElementById(tnode.selectId);
      if(check_obj!=null)
       check_obj.checked=isSelected;
      checkParentNodes(tnode);  
     }
    }
   }
  } 
  //获得选中的id 
function  getSelectIds(){
	var str = "";
	try{
		var sels = document.getElementsByName("selRadioObj");
		var size = sels.length;
		for(var i=0;i<size;i++){
			if(sels[i].checked){
			 if(!isNaN(sels[i].value))
				str+=sels[i].value+",";
			}			
		}
		
		sels = document.getElementsByName("selCheckObj");
		size = sels.length;		
		for(var i=0;i<size;i++){
			if(sels[i].checked&&!sels[i].disabled){			
			   if(!isNaN(sels[i].value))
				str+=sels[i].value+",";
			}			
		}
		
		return  str.substring(0,str.length-1);
	}catch(ex){alert(ex.message);};         
	  //var astr = eval(treeName+".getSelectIdsValid()");	  
	  return str;
         }
 function resetPermission(){
 sels = document.getElementsByName("selCheckObj");
		size = sels.length;		
		for(var i=0;i<size;i++){
			if(sels[i].checked&&!sels[i].disabled){			
			   sels[i].checked=false;
			}			
		}
 setResourceNodeSelected(obj);
 }         
</script>
<ww:head/>
</head>
<body  nowrap topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	style="background-color:#dfedef;">
	<tr>
		<td bgcolor="#ffffff" height="6"></td>
	</tr>
	<tr>
		<td bgcolor="#ffffff">
		<div id="titel1">
		<div id="titel1_left">
		<div id="titel1_right">
		<div id="titel1_txt"><c:out value="${typeName}"/><c:out value="${title}"/></div>
		</div>
		</div>
		</div>
		</td>
	</tr>
	<tr>
		<td valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bgcolor="#FFFFFF">
			<tr>
				<td height="1"></td>
			</tr>
			<tr>
				<td>
				<div id="titel2">
				<div id="titel2_left">
				<div id="titel2_right">
				<div id="nav2">
				<div>
				<ul>
				<li id="button"><a href="javascript:savePermission();">保存</a></li>
				<li id="button"><a href="javascript:resetPermission();">重置</a></li>
				</ul>
				</div>
				</div>
				</div>
				</div>
				</div>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<!-- 页面主要内容 -->
	<tr>
		<td align="center" valign="top">
<!-- 页面主要内容 -->

<table align="center" width="99%" border="0">
     <!--tr>
     <td align="left" > 
     &nbsp;&nbsp;&nbsp;${typeName}编号：${objectid}
     </td>
     </tr-->
     <tr>
     <td align="left" > 
     &nbsp;&nbsp;&nbsp;${typeName}名称：${domain.objectname}
     </td>
     </tr>
     <tr>
      <td width="80%" align="left" >
      <hr>
<div style="height:426px;overflow:auto">                  
      <script  language="javascript"> 
      var ResourceIco="${ctx}/common/xloadtree/images/iconText.gif";
      var treeName="";
      <c:forEach var="portal" items="${requestScope.portals}" varStatus="status">
       //document.write("<hr>");
       var T${portal.portalcode}=new WebFXTree('${portal.cname}', '',null, null, null,null,'true','T_${portal.portalcode}',null,"selectChildNodes(T${portal.portalcode})","javascript:treeSelectId='${portal.portalcode}';");
       document.write(T${portal.portalcode});
       
      </c:forEach>
      <c:forEach var="item" items="${requestScope.items}" varStatus="status">
               <c:choose>
                 <c:when test="${item.pmoCode==null}" >
                  var T${item.oid}= new WebFXTreeItem('${item.moName}', null, null, null, null,null,'true','T_${item.oid}',null,"selectChildNodes(T${item.oid});checkParentNodes(T${item.oid});","javascript:treeSelectId='${item.oid}';");
                  </c:when>
                  <c:otherwise>
                  var T${item.oid}= new WebFXTreeItem('${item.moName}', null, null, null, null,null,'true','T_${item.oid}',null,"selectChildNodes(T${item.oid});checkParentNodes(T${item.oid});","javascript:treeSelectId='${item.oid}';");
                  </c:otherwise>
                 </c:choose>
        <c:forEach var="resource" items="${item.coreResources}" varStatus="rstatus">
         var Tr${resource.resourceid}= new WebFXTreeItem('${resource.resourcename}', null, null, ResourceIco,null,null,'true','${resource.resourceid}',null,"checkParentNodes(Tr${resource.resourceid});","javascript:treeSelectId='${resource.resourceid}';");         
         T${item.oid}.add(Tr${resource.resourceid});  
        </c:forEach>         
      </c:forEach>
               
      <c:forEach var="item" items="${requestScope.items}" varStatus="status">
               <c:choose>
                 <c:when test="${item.pmoCode==null}" > 
                 if(T${item.pcode}!=null)
                  T${item.pcode}.add(T${item.oid});             
                  </c:when>
                  <c:otherwise>
                  if(T${item.pmoCode}!=null)
                   T${item.pmoCode}.add(T${item.oid});
                  </c:otherwise>
                 </c:choose>
       </c:forEach>      
  
     
      </script> 
   </div>         
            </td>
    </tr>               
</table>
		</td>
	</tr>
</table>		</td>
	</tr>
</table>
<form method="post" action="permission!saveOrUpdate.action" name="myform">
<ww:hidden  name="type" id="type"/>
<ww:hidden  name="objectid" id="objectid"/>
<ww:hidden  name="keys" id="keys" value=""/>
</form>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
<script type="text/javascript">
var obj=new Array;
<c:forEach var="item" items="${requestScope.AllResourcesAccredited}" varStatus="status">
 obj[${status.index}]='${item.resourceid}';
</c:forEach> 
setResourceNodeSelected(obj);
</script>
</body>
</html>
