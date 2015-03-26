<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
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

<script type="text/javascript" src="/common/cybercms_js/global_ab.js"></script>
<style type="text/css">
.pw-w417_ab{
	width:385px
}
.pw-con-input-item-plu_ab {
	border:1px solid #999;
	width:236px;
	height:290px;
#height:313px;
float:left;
}
</style>
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
    if(pcheck_obj != null)
    	{
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
  }
  //设置资源是否选中
  function setResourceNodeSelected(resourceids,isSelected){
   if(isSelected==null||isSelected=='')//默认为选中
    isSelected=true;
   //设置选中（或取消）对象
   if(resourceids!=null && resourceids.length!=null){
    for(var i=0;i<resourceids.length;i++){
      var check_obj=document.getElementById(resourceids[i]);
      if(check_obj!=null)
       check_obj.checked=isSelected;
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
<body class="pw_ab" style="overflow:hidden;padding:0px;margin:0px;border:0px;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<form method="post" action="${ctx}/cms/permission!save.action" name="myform" class="pw-borderOut_ab pw-w417_ab">
<ww:hidden  name="type" id="type"/>
<ww:hidden  name="objectid" id="objectid" value="%{objectId}"/>
<ww:hidden  name="keys" id="keys" value=""/>
	<div class="pw-borderIn_ab"  style="height:400px;">
    	<div class="pw-head_ab">
        	<div class="pw-head-tit_ab">
            	<img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
                <span>赋权</span>
            </div>
            <img src="${default_imagepath}/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
        </div>
        <div class="pw-con_ab">
        	<ul class="pw-con-input_ab" style="height:300px; overflow-y:auto">
        	
        	<li class="pw-con-input-item_ab">
<c:forEach var="resource" items="${requestScope.AllResources}" varStatus="rstatus">
              <input type="Checkbox"  name="selCheckObj" id ="${resource.oid}" value="${resource.oid}" />${resource.resourceName}
              
               </c:forEach>
              
                			

                </li>
            

            </ul>
            <div class="pw-con-btns_ab">
            	<input type="button" class="pwSubmit_ab" value="" onclick="savePermission();" />
                <input type="button" class="pwCancel_ab" value="" onclick="javascript:global_ab.fn.popWindow.removePopWindow(false);"/>
            </div>
        </div>
    </div>



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
<script type="text/javascript">
//<![CDATA[
global_ab.init.popwindowPage_fn();

(function()
{
	var closeBtn = document.getElementById("pwClose_ab");
	closeBtn.onclick = function()
	{
		global_ab.fn.popWindow.removePopWindow(false);
	}
})();

//]]>
</script>
</body>
</html>
