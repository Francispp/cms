<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="title" value="角色管理列表" />

<head>
<title>${title}</title>
<script type="text/javascript" src="/common/cybercms_js/global_ab.js"></script>
<%@ include file="/common/meta.inc"%>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<%@ include file="/common/cxtree.inc"%>
<%@ include file="/common/dhtmlxtree/tree.inc"%>
<c:set var="icon_16_actions" value="${ctx}/images/common" />

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
  //设置角色是否选中
  function setRoleNodeSelected(roleId,isSelected){
   if(isSelected==null||isSelected=='')//默认为选中
    isSelected=true;
   //设置选中（或取消）对象
   if(roleId!=null && roleId.length!=null){
    for(var i=0;i<roleId.length;i++){
     var tnode=eval('T'+roleId[i]);//获得节点名称
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
			 if(!isNaN(sels[i].value)){
				str+=sels[i].value+",";
			 }else{
				   str+=sels[i].value.substring(2)+",";
			   }
			}			
		}
		
		sels = document.getElementsByName("selCheckObj");
		size = sels.length;
		for(var i=0;i<size;i++){
			if(sels[i].checked&&!sels[i].disabled){			
			   if(!isNaN(sels[i].value)){
				   str+=sels[i].value+",";
			   }else{
				   str+=sels[i].value.substring(2)+",";
			   }
			}			
		}
		if(str.endsWith(",")){
			return  str.substring(0,str.length-1);
		}else{
			return '';
		}
		
	}catch(ex){alert(ex.message);};         
	  //var astr = eval(treeName+".getSelectIdsValid()");	  
	  return str;
         }
  
  function addRole(){
	 
	  global_ab.fn.popWindow.addPopWindow('base/role!add.action?id=&pageStyle=1',"620px","508px",false);
  }
  
//刷新
	function refreshMenu(){
		location.reload()
	}
</script>

<style type="text/css">
/*选择资源图标*/
.selectRes {
	background-image: url('${icon_16_actions}/tools-check-spelling.png');
	background-repeat: no-repeat;
	background-position: center;
	text-align: center;
	cursor: hand;
}
</style>
</head>
<body style="margin: 0px; padding: 0px; width: 100%">
	<!-- 状态提示栏 -->
	<%@ include file="/common/messages.inc"%>

	<div class="content">
		<div class="info-box">
			<div id="itemTree_1"></div> 

	
			<script>
				 var TroleCode=new WebFXTree('角色列表', '',null, null, null,null,'true','T_roleCode',null,"selectChildNodes(TroleCode)","javascript:treeSelectId='roleCode';");
			       document.write(TroleCode);
			       
			<c:forEach var="item" items="${requestScope.items}" varStatus="status">
			 var T${item.oid}= new WebFXTreeItem('${item.rolename}', '', null, null, null,null,'true','T_${item.oid}',null,"selectChildNodes(T${item.oid});checkParentNodes(T${item.oid});","javascript:treeSelectId='${item.oid}';");             
			 TroleCode.add(T${item.oid});  
			 </c:forEach>
	      </script>
		</div>
	</div>
	<!-- 页脚 -->
	<%@ include file="/common/foot.inc"%>
	<script type="text/javascript">
	
	function roleDetail(id){
		top.setMainFrameUrl('/base/role!roleDetail.action?id='+id);
	}
var obj=new Array;
<c:forEach var="item" items="${coreRoles}" varStatus="status">
obj[${status.index}]='${item.oid}';
</c:forEach> 
setRoleNodeSelected(obj);
</script>

</body>
</html>
