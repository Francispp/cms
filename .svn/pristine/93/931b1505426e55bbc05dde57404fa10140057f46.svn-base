<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<%@ include file="/common/js.inc"%>

<html>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<script>
        //all nodeids
         var NODEIDS=new Array;
function getNodeSelectUserIds(nodeid){
        var selectStr = "";
 	var allUserids = document.all.tags('input');
	for (i=0; i<allUserids.length; i++){
		if (allUserids(i).type=="checkbox" && allUserids(i).checked 
		    && allUserids(i).value!=null&&allUserids(i).value!=""
		     && allUserids(i).id!=null && allUserids(i).id==nodeid){
                  selectStr+=allUserids[i].value+",";
		}
	}
	return  selectStr.substring(0,selectStr.length-1);
      //alert(selectStr.substring(0,selectStr.length-1));
}
function getAllNodeUsers(){
//alert(NODEIDS.length);
var nodeusers="";
 for(i=0;i<NODEIDS.length;i++){ 
   nodeusers+=NODEIDS[i]+":"+getNodeSelectUserIds(NODEIDS[i])+";";
 }
 return  nodeusers; 
}	  
function getSel(){
	var astr = tree.getSelectIds();
	alert(astr);
}
//root node click url
var rootUrl="javascript:return fasle;";
//node click url
var itemUrl="javascript:return fasle;";

function clickRole(roleobjstr,treeobj){
                var roleobj=null;                
  		var sels = document.getElementsByName(roleobjstr.id);
		size = sels.length;	
		//alert(roleobjstr.checked);	
		for(var i=0;i<size;i++){		
		    sels[i].checked=roleobjstr.checked;
			//if(sels[i].checked){
				//str+=sels[i].value+",";
			//}			
		}
		//treeobj.reload;
}
function checkUserOne(currobj){
   var chkID=document.getElementsByName(currobj.name);    
   var chkall=document.getElementById(currobj.name);
  //alert(chkall.checked);
   allchecked=true;
   	for (i=0; i<chkID.length; i++){
		if (chkID(i).type=="checkbox" && chkID(i).checked==false && chkID(i)!=chkall){			
			allchecked=false;
		}
	}
	if (allchecked==false) {
		chkall.checked=false;
	}else{
		chkall.checked=true;
	}
}
function selectUsers(){
 window.returnValue=getAllNodeUsers();
  //alert(window.returnValue);
  //ClosePage();
   window.close();
  
}
function ClosePage(){
    window.returnValue=null;
    window.close();
}
</script>
<script language="javascript">
function onchangeExamineContent(){
 document.getElementById('examineContent').value=document.getElementById('select_examineContent').value;
}
</script>
<%@ include file="/common/cxtree.inc"%>
<base target="_self">
<body topmargin="0" leftmargin="0" topmargin="0" leftmargin="0">
<ww:form id="myform" name="myform" namespace="/flow" action="" method="POST">
<ww:hidden name="activityid"/>
<ww:hidden name="id"/>
<div align="center">
<%@ include file="head.inc"%>
<iframe src='${ctx}/flow/track.action?id=<ww:property value="id"/>'
	frameborder=0 width=100% height=100> </iframe>
<%@ include file="foot.inc"%>
</div>
</ww:form>
</body>
</html>
<script language="javascript">
 var url=replaceAll(window.location.href,'transact','comple');
 myform.action=url;
 
 //提交页面
function doConfirm(){
 alert('请选择具体的流程操作！');
}
</script>