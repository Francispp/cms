

function officeDyniframesize(iframename) {
 dyniframesize('officeframe_name');
}
/*

 *前台页面公用JS
 *
 *
*/
//重设iframe大小
function dyniframesize(iframename) {
  var pTar = null;
  var pTd = null;
  
  if (document.getElementById){
    pTar = document.getElementById(iframename);
    pTd = document.getElementById("officetd")
  }
  else{
    eval('pTar = ' + iframename + ';');
    eval('pTd = officetd;');
  }
  if (pTar && !window.opera){
  
    
    
    
    
     try{
     
    
  
  var bHeight = pTar.contentWindow.document.body.scrollHeight;
var dHeight = pTar.contentWindow.document.documentElement.scrollHeight;
var height = Math.max(bHeight, dHeight);
//alert(bHeight+"------------"+dHeight);
height +=30;
if(pTar != null && height != null) {
	if(height > 300)
       pTar.height = height;
	else
		pTar.height =300;
}

  
  pTar.contentWindow.document.body.style.wordBreak="break-all";
   pTar.contentWindow.document.body.style.wordWrap="break-word";
  pTar.contentWindow.document.body.style.overflow="hidden";
  // pTar.contentWindow.document.body.style.display="inline";
     var ua = navigator.userAgent.toLowerCase();
    var s;
    s = ua.match(/iPad/i);
   var rt = new Array();
rt = pTar.contentWindow.document.getElementsByTagName("p");
for(i=0;i<rt.length;i++)
{
	var obj = rt[i];
	if (s != null && s == "ipad") {
	obj.style.marginLeft="0px";
	obj.style.marginRight="0px";
	obj.style.width="100%";
       }
    
}
   
   rt = pTar.contentWindow.document.getElementsByTagName("img");
for(i=0;i<rt.length;i++)
{
	var obj = rt[i];
	obj.alt="";
	
}
    bHeight = pTar.contentWindow.document.body.scrollHeight;
 dHeight = pTar.contentWindow.document.documentElement.scrollHeight;
height = Math.max(bHeight, dHeight);
//alert(bHeight+"------------"+dHeight);

if(pTar != null && height != null) {
	if(height > 300)
       pTar.height = height;
	else
		pTar.height =300;
}
  
  }catch(E){}
    
    
    
    
    
    
  }
}

function dyniframesizeformessage(iframename) {
  var pTar = null;
  var pTd = null;
  
  if (document.getElementById){
    pTar = document.getElementById(iframename);
    pTd = document.getElementById("officetd")
  }
  else{
    eval('pTar = ' + iframename + ';');
    eval('pTd = officetd;');
  }
  if (pTar && !window.opera){
    //begin resizing iframe
    pTar.style.display="block"
    if (pTar.contentDocument && pTar.contentDocument.body.offsetHeight){
      //ns6 syntax
      pTar.height = pTar.contentDocument.body.offsetHeight; 
    }
    else if (pTar.Document && pTar.Document.body.scrollHeight){
      //ie5+ syntax
      pTar.height = pTar.Document.body.scrollHeight + 20;
    }
    
    if (pTar.height < 600) {
    	pTar.height = 600;
    }
  }
  try{
  parent.dyniframesizeforpage("main_frame");
  }catch(e){}

}

function dyniframesizeforpage(ifm) {
 try{
  var bHeight = ifm.contentWindow.document.body.scrollHeight;
var dHeight = ifm.contentWindow.document.documentElement.scrollHeight;
var height = Math.max(bHeight, dHeight);
height +=30;
if(ifm != null && height != null) {
	if(height > 400)
       ifm.height = height;
	else
		ifm.height =400;
}
  
  
  }catch(E){}
}
function dyniframesizeforall(iframename) {
	try{
  var pTar = null;
  if (parent.document.getElementById){
    pTar = parent.document.getElementById(iframename);
  }
  if (pTar && !window.opera){
    pTar.height = '100%';
    //alert(pTar.document);
    //begin resizing iframe
    pTar.style.display="block";
    if (pTar.contentDocument && pTar.contentDocument.body.offsetHeight){
      //ns6 syntax
      pTar.height = pTar.contentDocument.body.offsetHeight+FFextraHeight?FFextraHeight:0;
    }
    else if (pTar.Document && pTar.Document.body.scrollHeight){
      //ie5+ syntax
      pTar.height = pTar.Document.body.scrollHeight;
    }
    //alert(pTar.height);
    if (pTar.height < 500) {
    	pTar.height = "100%";
    }
  }
  }catch(E){}
}
/*
 * 跳到指定页面
 * pageIndex 页数
 * pageIndexObj 页数对象，为空时，自动取名称为pageIndex的对象
 *
*/
function gotoPage(pageIndex,pageIndexObjId){
      if(!pageIndex||pageIndex=='')
       pageIndex=1;
       
      if(pageIndexObjId && pageIndexObjId!=null && document.getElementById(pageIndexObjId))
	 document.getElementById(pageIndexObjId).value=pageIndex;
      else{
        if(document.getElementsByName("pageIndex"))
         document.getElementsByName("pageIndex").value=pageIndex;
	}  
      if(document.forms.length>0){//当前页有form,用form提交
       document.forms[0].action=location.href;       
       document.forms[0].submit ();
      }else{//用链接提交
       var currUrl=location.href;       
       currUrl.replace("#",'');
       var pindex=currUrl.indexOf('&pageIndex=');
       if(pindex>0)
        currUrl=currUrl.substring(0,pindex);
         
       var params="&pageIndex="+pageIndex;
       location.href=currUrl+params;
      }
 }

function gotoPage1(pageIndex,pageIndexObjId){
    if(!pageIndex||pageIndex=='')
     pageIndex=1;
     
    if(pageIndexObjId && pageIndexObjId!=null && document.getElementById(pageIndexObjId))
	 document.getElementById(pageIndexObjId).value=pageIndex;
    else{
      if(document.getElementsByName("myTable_p"))
       document.getElementsByName("myTable_p")[0].value=pageIndex;
	}  
    if(document.forms.length>0){//当前页有form,用form提交
    // document.forms[0].action=location.href;       
     document.forms[0].submit ();
    }else{//用链接提交
     var currUrl=location.href;       
     currUrl.replace("#",'');
     var pindex=currUrl.indexOf('&myTable_p=');
     if(pindex>0)
      currUrl=currUrl.substring(0,pindex);
       
     var params="&myTable_p="+pageIndex;
     location.href=currUrl+params;
    }
}
 
 function sortList(order, orderEl){
      if(!order||order=='')
       pageIndex="id";
       
      if(orderEl && orderEl!=null && document.getElementById(orderEl))
	 document.getElementById(orderEl).value=order;
      else{
        if(document.getElementsByName("orderBy"))
         document.getElementsByName("orderBy").value=order;
	}  
      if(document.forms.length>0){//当前页有form,用form提交
       document.forms[0].action=location.href;       
       document.forms[0].submit ();
      }else
      	{//用链接提交
       var currUrl=location.href;       
       currUrl.replace("#",'');
       var pindex=currUrl.indexOf('&orderBy=');
       if(pindex>0)
       {       	
        	var descending="&descending=false";        	
        	if(currUrl.indexOf('&descending=false')>0){
        	   var descending='&descending=true';
        	}
        	currUrl=currUrl.substring(0,pindex);
        	//location.href=currUrl+"&orderBy="+order;
        
        	location.href=currUrl+"&orderBy="+order+descending;
        	//descending
       }
       else
       {
					pindex=currUrl.indexOf ('?orderBy=');
					if(pindex>0)
					{
        		currUrl=currUrl.substring(0,pindex);
        		location.href=currUrl+"?orderBy="+order;
        	}
        	else
        		{
        			if(location.href.indexOf('?')>0)
        			  location.href+= "&orderBy="+order;
        		        else
        			  location.href+= "?orderBy="+order;
        		}
      }
    }
 }
 
 function replaceUrl (url, name, value)
 {
 	
 	if (value)
 	{
 		
	       url = url.replace("#",'');
	       if (url.indexOf (name + "=") >= 0)
	       {
				var regex = new RegExp (name + "=[^&]*", "gi");
				url = url.replace (regex, name + "=" + value);
	       }
	       else
	       {
	       	if (url.indexOf ("&") >= 0)
	       	{
	       		url = url + "&" + name + "=" + value;
	       	}
	       	else
	       	{
	       		url = url + "?" + name + "=" + value;
	       	}
	       }
 	}else{//若为空
 	 if (url.indexOf (name + "=") >= 0)
	       {
				var regex = new RegExp (name + "=[^&]*", "gi");
				url = url.replace (regex, name + "=" + value);
	       }	
 	}
 		
 	
 	return url;
 }
 //设置复选框
 function setcheckbox(checkboxID,checkboxString,checkboxValue){
  var rt = new Array();
  rt = checkboxString.split(",");
  for(var i=0;i<rt.length;i++)
   {
    if(IgnoreSpaces(rt[i]) == checkboxValue)
     {
      document.getElementById(checkboxID).checked = true;
     }
   }
}

//标签复选框状态改态事件
 function changeCheckBox(_this,_id,fieldValueNo){
  if(_this)	
    if(_this.checked){
    
     document.getElementById(_id).value = _this.value;
    }else{
     document.getElementById(_id).value = fieldValueNo;
    }
  }
 	
function IgnoreSpaces(Str){ 
    var ResultStr = ""; 
    Temp=Str.split(" ");
    for(i = 0; i < Temp.length; i++) 
    ResultStr +=Temp[i]; 
    return ResultStr; 
} 
//禁止右键 
// for IE5+
function nocontextmenu(){
event.cancelBubble = true
event.returnValue = false;
return false;
}
//禁止右键 
// for all others
function norightclick(e){
if (window.Event ){
if (e && (e.which == 2 || e.which == 3))
return false;
}
else
if (event.button == 2 || event.button == 3){
event.cancelBubble = true
event.returnValue = false;
return false;}
}
//isOne=true为单选
function selectUser(id,text,isOne)
{
 if(isOne == undefined)
 {
 	isOne=false;
}
 
 var rtn = window.showModalDialog("/base/user!selectUser.action?selected=" + id.value+"&selectedname="+text.value+"&isOne="+isOne,null,"dialogWidth:490px;dialogHeight:510px;");
 if(rtn == undefined) 
 {
   return;
 }
 rtn=rtn.split(";");
  id.value="";
  text.value="";
  i=0;
 while(i<rtn.length-1)
 {
   id.value+=rtn[i]+";";
   i++;
   text.value+=rtn[i]+";";
   i++;
 }
 if(isOne){
  var temp=id.value;
  id.value=temp.replace(";","");
   temp=text.value;
  text.value=temp.replace(";","");
 }
  
}

//isOne=true为单选
//选择角色
function selectRole(id,text,isOne)
{
 if(!isOne)
   isOne=false;
 var rtn = window.showModalDialog("/base/role!selectRole.action?selected=" + id.value+"&selectedname="+text.value+"&isOne="+isOne,null,"dialogWidth:490px;dialogHeight:510px;");
 if(rtn == undefined) 
 {
   return;
 }
 rtn=rtn.split(";");
  id.value="";
  text.value="";
  i=0;
 while(i<rtn.length-1)
 {
   id.value+=rtn[i]+";";
   i++;
   text.value+=rtn[i]+";";
   i++;
 }
 if(isOne){
  var temp=id.value;
  id.value=temp.replace(";","");
   temp=text.value;
  text.value=temp.replace(";","");
 }
}

function y_ipad() {
	try{
    var ua = navigator.userAgent.toLowerCase();
    var s;
    s = ua.match(/iPad/i);
    
    
    if (s != null && s == "ipad") {
    //alert("测试"+s);
      document.all.Ipad.innerHTML="<h4 style='color: #f00'>此页面上的内容需要较新版本的 Adobe Flash Player。</h4>"
           }
        }catch(e){}
   }
function writeCookie(key,value,timeout){
	if(timeout){
		var expiration = new Date((new Date()).getTime() + timeout * 60 * 60000);//存放timeout小时 
		document.cookie = key+ "=" + escape(value)+ ";expires="+ expiration.toGMTString() + ";path=/";
	}else{
		document.cookie = key+ "=" + escape(value)+ ";path=/";
	}
}
function getCookie(key){
	var allcookies = document.cookie;
	var cookie_pos = allcookies.indexOf(key);
	if (cookie_pos != -1){
		try{
			cookie_pos += key.length + 1;
			var cookie_end = allcookies.indexOf(";", cookie_pos);
			if (cookie_end == -1){
				cookie_end = allcookies.length;
			}
			var vl = unescape(allcookies.substring(cookie_pos, cookie_end));
			if(vl=='""')return "";
			return vl;
		}catch(e){
			return "";
		}
	}
	return "";
}

function clearCookie(key){
	var expt = new Date();
	expt.setTime(expt.getTime() - 1*24*60*60*1000); 
	document.cookie = key+'=;expires=' + expt.toGMTString() + ';path=/';
}