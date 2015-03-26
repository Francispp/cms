//选择外部用户
function selectWebusers(URL,idsField,namesField)
{   
	var rtn = window.showModalDialog(URL,'选择外部用户','resizable:yes;scroll:yes;status:no;dialogWidth='+800+'px;dialogHeight='+500+'px;center=yes;help=no');
	var strs = rtn.split(";");
	idsField.value=strs[0];
	namesField.value=strs[1];
}
//选择JS组件
function selectJSComponent(selectUrl) {
  wx = '532px';
  wy = '500px';
  var url;
  if(selectUrl != null){
    url = selectUrl;
  }else{
    url = ctx + '/component/jsfunction!selectJSComList.action';
  }
  var rtn = showframe('选择JS组件', url);
  $('jsfuncName').value = rtn.funcName; 
  $('jscode').value = rtn.jscode;
}

//选择部门
function selectDept(parentDept, multiSelect, selectChild, selectUrl,selectedids) {
  wx = '400px';
  wy = '500px';
  var url;
  if(selectUrl != null){
    url = selectUrl;
  }else{
    url = ctx + '/select/dept.action';
  }
  if (multiSelect != null && multiSelect) {
    url = url + '?multiSelect=TRUE';
    if(selectedids!=null&&selectedids.length!='') 
      url = url + '&selectedids='+selectedids;
  }else
    url = url + '?multiSelect=false';
    
  if (selectChild != null && selectChild) {
    url = url + '&selectChild=TRUE';
  }
  if (parentDept != null) {
    url = url + '&parent=' + parentDept;
  }
  var rtn = showframe('选择部门', url);
  return rtn;
}

//选择部门
function selectDeptEx(fieldId, fieldName, multiSelect, selectChild, parentDept, selectUrl) {
  if (fieldId == null && fieldName==null) {
    return;
  }
  var rtn = selectDept(parentDept, multiSelect, selectChild, selectUrl,fieldId.value);
  setSelectValues(rtn,fieldId, fieldName, multiSelect);  
 }
 

//选择角色
function selectRoleEx(fieldId, fieldName, multiSelect,type, selectUrl) {
  if (fieldId == null && fieldName==null) {
    return;
  }
  var rtn = selectRole1(multiSelect,type,selectUrl,fieldId.value);
  setSelectValues(rtn,fieldId, fieldName, multiSelect); 
}

//选择用户组
function selectRole1(multiSelect,type,selectUrl,selectedids) {
  wx = '400px';
  wy = '500px';
  var url = null;  
  var title="选择角色";
  if(selectUrl != null){
     url = selectUrl;
   }else{
	  if (type==null||type == 'G') {
	    url = ctx + '/select/role.action';
	    title="选择角色";
	  }
	  else if (type == 'B') {
	    url = ctx + '/select/role.action';
	    title="选择用户组";
	  }
  }
  if (multiSelect != null && multiSelect) {
    url = url + '?multiSelect=TRUE';
     if(selectedids!=null&&selectedids.length!='') 
       url = url + '&selectedids='+selectedids;
  }else
    url = url + '?multiSelect=false';

  var rtn = showframe(title, url);
  return rtn;
}

//选择公共模板树
function selectPublicChannelEx(fieldId,fieldName,selectUrl) {
  if (fieldId == null) {
    return false;
  }
  var rtn = selectChannel(fieldId,selectUrl);
  if (rtn == null || rtn == 'undefined') {
	}else if (rtn == '') {
		fieldId.value = '';
		if (fieldName != null) fieldName.value = '';
	}else {
			var t = rtn.split(';');
			fieldId.value = t[0];
			if (fieldName != null) fieldName.value = t[1];	
	}
}
//选择频道
function selectChannelEx(fieldId,fieldName,selectUrl) {
  if (fieldId == null) {
    return false;
  }
  var rtn = selectChannel(fieldId,selectUrl);
  if (rtn == null || rtn == 'undefined') {
	}else if (rtn == '') {
		fieldId.value = '';
		if (fieldName != null) fieldName.value = '';
	}else {
			var t = rtn.split(';');
			fieldId.value = t[0];
			if (fieldName != null) fieldName.value = t[1];	
	}
}
//选择频道，返回频道id
function selectChannelEx2(selectUrl) {
  var rtn = selectChannel('',selectUrl);
 
  if (rtn == null || rtn == 'undefined') {
	}else if (rtn == '') {
		return '';		
	}else {
			var t = rtn.split(';');
			return t[0];
			//if (fieldName != null) fieldName.value = t[1];	
	}
	return null;
}
//选择站点树
function selectChannelEx(fieldId,selectUrl) {
  if (fieldId == null) {
    return false;
  }
  var rtn = selectChannel(fieldId,selectUrl);
  return rtn;
}

//选择频道
function selectChannel(selectedids,selectUrl) {
  wx = '400px';
  wy = '500px';
  var url = null;  
  var title="选择频道";
  url = selectUrl;
  var rtn = showframe(title, url);
  return rtn;
}
 
//选择用户
function selectUserFromTree(multiSelect,type,parentDept, selectChild,selectedids) {
  wx = '400px';
  wy = '500px';
  var url = null;
  var title="选择用户";
  if (type == null || type == 'U') {
    url = ctx + '/select/user.action';
    title="选择用户";
  } else if (type == 'O') {
    url = ctx + '/select/user.action';
  }
  if (multiSelect != null && multiSelect) {
    url = url + '?multiSelect=TRUE';
    if(selectedids!=null&&selectedids.length!='') 
       url = url + '&selectedids='+selectedids;
  }else
    url = url + '?multiSelect=false';
    
  if (selectChild != null && selectChild) {
    url = url + '&selectChild=TRUE';
  }
  if (parentDept != null) {
    url = url + '&deptid=' + parentDept;
  }
  var rtn = showframe(title, url);
  return rtn;
}

//选择用户
function selectUserTreeOrSearch(fieldId, fieldName, multiSelect,type, parentDept, selectChild,isTree)
{
	if(isTree != null && isTree)
	{
	selectUserEx(fieldId, fieldName, multiSelect,type, parentDept, selectChild);	
	}
      else
      {
      	selectUser(fieldId,fieldName,multiSelect);
      	
      	}
	
}
function selectUserEx(fieldId, fieldName, multiSelect,type, parentDept, selectChild) {
  if (fieldId == null && fieldName==null) {
    return;
  }
  var rtn = selectUserFromTree(multiSelect, type, parentDept, selectChild,fieldId.value);
  setSelectValues(rtn,fieldId, fieldName, multiSelect);
}
//选择用户、用户组、部门
function selectUserObjcectEx(fieldId, fieldName, multiSelect,type, parentDept, selectChild) {
  if (fieldId == null && fieldName==null) {
    return;
  }
  var rtn = selectUserObjcect(multiSelect, type, parentDept, selectChild,fieldId.value);
  setSelectValues(rtn,fieldId, fieldName, multiSelect);
}
//选择用户
function selectUserObjcect(multiSelect,type,parentDept, selectChild,selectedids) {
  wx = '400px';
  wy = '500px';
  var url = null;
  var title="选择组织对象";

  if (type == null ) {
    url = ctx + '/select/user!selectAll.action';
    title="选择组织对象";
  } else if (type == 'O') {
    url = ctx + '/select/user!selectAll.action';
  }
  if (multiSelect != null && multiSelect) {
    URL = url + '?multiSelect=TRUE';
    if(selectedids!=null&&selectedids.length!='') 
       url = url + '&selectedids='+selectedids;
  }else
    url = url + '?multiSelect=false';
    
  if (selectChild != null && selectChild) {
    url = url + '&selectChild=TRUE';
  }
  if (parentDept != null) {
    url = url + '&deptid=' + parentDept;
  }
  var rtn = showframe(title, url);
  return rtn;
}
//设置选择的内容
function setSelectValues(rtn,fieldId, fieldName, multiSelect){
  if (rtn == null || rtn == 'undefined') {
  	return false;
  }  
  if (rtn == '') {
    if (fieldId != null) fieldId.value = '';
    if (fieldName != null) fieldName.value = '';
    return true;
  }else {
    if (multiSelect != null && multiSelect) {
      if (fieldId != null) fieldId.value = '';
      if (fieldName != null) fieldName.value = '';
      for (var i=0; i < rtn.length; i++) {
        var t = rtn[i].split(';');
        if (fieldId != null) fieldId.value += t[0] + ';';
        if (fieldName != null) fieldName.value += t[1] + ';';
      }
    }else {
      var t = rtn.split(';');
      if (fieldId != null) fieldId.value = t[0];
      if (fieldName != null) fieldName.value = t[1];
    }
     return true;
  }  
}
//显示选择窗口
function showframe(title,querystr,jsp) {
  var pth = window.location.pathname;
  var pos1 = pth.lastIndexOf("/");
  var pos2 = pth.lastIndexOf("\\");
  var pos = Math.max(pos1, pos2);

  querystr.title = title;
  if (querystr.url!=null){
    if (!(querystr.url.indexOf("/")==0 || querystr.url.indexOf("\\")==0))
      querystr.url = pth.substring(0,pos)+"/"+querystr.url;
  }
  else {
    if (!(querystr.indexOf("/")==0 || querystr.indexOf("\\")==0))
      querystr = pth.substring(0,pos)+"/"+querystr;
  }
  
  if(jsp == null)
  	jsp = "frame.jsp";
  return window.showModalDialog(ctx + '/'+jsp+'?title=' + title,querystr,'font-size:9pt;dialogWidth:' + wx + ';dialogHeight:' + wy + ';status:no;scroll=no;');
}

//返回单选的方法
function selectOne(value) {
  window.returnValue = value;
  window.close();
}
//返回多选的方法
function doReturn() {
  var sis = document.all("_selectitem");
  var rtn = new Array();
  var p = 0;

  if (sis.length != null) {
	  for (var i=0; i<sis.length; i++) {
	    var e = sis[i];
	    if (e.type == 'checkbox') {
	        if (e.checked ) {
	          rtn[p++] = e.value;
	        }
	    }
	  }
  }
  else {
    var e = sis;
    if (e.type == 'checkbox') {
      if (e.checked ) {
        rtn[p++] = e.value;
      }
    }
  }

  window.returnValue = rtn;
  //alert(rtn);
  window.close();
}
//取消操作
function doExit(){
  window.returnValue = null;  
  window.close();
}
//清空已选择的
function doEmpty(){
  
}
//设置自动选择已选择的
 function doSetSelected(selectids) {
  if(selectids==null||selectids.length==''||selectids=='undefined') 
    return ;
  var sis = document.all("_selectitem");
  var sids=";"+selectids;
  var p = 0;

  if (sis.length != null) {
	  for (var i=0; i<sis.length; i++) {
	    var e = sis[i];
	    if (e.type == 'checkbox'&&e.value!=null) {
	    var index=e.value.indexOf(";");
	        if (index>0&&sids.indexOf(";"+e.value.substring(0,index))>=0) {
	         e.checked=true;
	        }
	    }
	  }
  }
  else {
    var e = sis;
	    if (e.type == 'checkbox'&&e.value!=null) {
	    var index=e.value.indexOf(";");
	        if (index>0&&sids.indexOf(";"+e.value.substring(0,index))>=0) {
	        e.checked=true;
	        }
    }
  }
}


//选择频道
function selectTableFields(url) {
  wx = '660px';
  wy = '320px'; 
  var title="SQL向导"; 
  var rtn = showframe(title, url);
  return rtn;
}

//选择频道
function editField(url) {
  wx = '450px';
  wy = '320px'; 
  var title="字段编辑"; 
  var rtn = showframe(title, url);
  return rtn;
}

//确认订阅
function subcreibe(url,chnid) {
  wx = '450px';
  wy = '320px'; 
  var title="网上订阅";
  if(url.indexOf("?")>0){
  	url +="&";
  }else{
  	url +="?";
  }
  url +="channelId="+chnid;
  
  var rtn = showframe(title, url);
  return rtn;
}

//确认订阅
function subcreibe(chnid) {
 var url = '/component/subcreibe!intoSubcreibe.action';
  wx = '380px';
  wy = '40px';
  var title="网上订阅";
  if(url.indexOf("?")>0){
  	url +="&";
  }else{
  	url +="?";
  }
  url +="channelId="+chnid;
  showframe(title, url,'frame2.jsp');
}