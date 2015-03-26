//给指定对象权限
function doAccreditPermission(objectid,type){
 location.href=ctx+'/base/permission!tree.action?type='+type+'&objectid='+objectid;
}
//设置所有输入框为失效状态
function setElementsDisabled(bl){
	if(bl==null)
	  bl=true;
	var allelems=document.forms[0].elements;	
	if(allelems!=null&&allelems.length!=null){		
  for(i=0;i<allelems.length;i++){
		var obj=allelems[i];
		//if(obj.readOnly==false){
			 //obj.readOnly=true;
			 //if(obj.type=='input')
			   alert(obj.type);
			  obj.disabled=bl;
			 /*if(obj.type=="text" || obj.tagName=="TEXTAREA"){
				 obj.removeAttribute("title");
				 obj.setAttribute("ondblclick",null);
				 obj.setAttribute("onclick",null);
			 }*/
		//}
	}
}
}
//系统中用来提示信息方法
//hitMessage 提示内容　hitTitle　提示标题
function alertMessage(hitMessage,hitTitle){
	var _title="提示";
	if(hitTitle)
	  _title=hitTitle;
	if(hitMessage==null||hitMessage=='')
	  return ;
	//if(getExtMessageBox())	 
	// getExtMessageBox().alert(_title,hitMessage);
	//else
	 alert(hitMessage);
	}
//系统中用来提示信息方法
//hitMessage 提示内容　hitTitle　提示标题 hitMethod 处理方法	
function confirmMessage(hitMessage,hitTitle,hitMethod){
	var _title="提示";
	if(hitTitle)
	  _title=hitTitle;
	if(hitMessage==null||hitMessage=='')
	  return ;
	if(getExtMessageBox())  
	 getExtMessageBox().confirm(_title, hitMessage, hitMethod);
	else{
	 if(confirm(hitMessage)){
	  hitMethod('yes');
	 }else{
	  hitMethod('no');	
	 }
	}	 	  	  	
	}
	
function setCookie(cookieName, cookieValue, expires)
{
    if (expires)
    {
        //指定了 expires
        document.cookie = cookieName + "=" + escape(cookieValue) + "; expires=" + expires.toGMTString();
    }
    else
    {
        document.cookie = cookieName + "=" + escape(cookieValue) + "; expires=Wed,22 Dec 2099 23:50:50 UTC";
    }
}

//获取并返回 cookie 值
//不区分 cookieName 的大小写
//dfltValue 为默认返回值
//不考虑子键
function getCookie(cookieName, dfltValue)
{
    var lowerCookieName = cookieName.toLowerCase();
    var cookieStr = document.cookie;
    
    if (cookieStr == "")
    {
        return dfltValue;
    }
    
    var cookieArr = cookieStr.split("; ");
    var pos = -1;
    for (var i=0; i<cookieArr.length; i++)
    {
        pos = cookieArr[i].indexOf("=");
        if (pos > 0)
        {
            if (cookieArr[i].substring(0, pos).toLowerCase() == lowerCookieName)
            {
                return unescape(cookieArr[i].substring(pos+1, cookieArr[i].length));
            }
        }
    }
    
    return dfltValue;
}		