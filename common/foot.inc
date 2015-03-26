<script language="javascript">
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
function setParentFrameSize(){
	try{
 if(parent!=null&&parent.document.getElementById("a_tabbar")!=null)
  {
   parent.document.getElementById("a_tabbar").style.height = '100%';
   
     var bHeight = document.body.scrollHeight;
     var dHeight = document.documentElement.scrollHeight;
     var height = Math.max(bHeight, dHeight);
  
   if(height < 300)
   {
   	parent.document.getElementById("a_tabbar").style.height = '100%';
   	//parent.dyniframesizeforparent("main_frame","100%");
    }
    else
    {
    	parent.document.getElementById("a_tabbar").style.height=height+50;
    	//parent.dyniframesizeforparent("main_frame",document.body.scrollHeight+20);
     }
    // alert(parent.document.getElementById("a_tabbar").style.height);
   setTimeout("top.scrollTo(0,0)",1);
  }
   }catch(E){}
}

function dyniframesizeforparent(iframename,bodyheight) {
  var pTar = null;
  if (parent.document.getElementById){
    pTar = parent.document.getElementById(iframename);
  }
  else{
    eval('pTar = ' + iframename + ';');
  }
  if (pTar && !window.opera){
    pTar.height = '100%';
    pTar.style.display="block";
    pTar.height = bodyheight;
    if (pTar.height < 500) {
    	pTar.height = 500;
    }
  }
}

function dyniframesizeforall(iframename) {
	try{
  var pTar = null;
  if (parent.document.getElementById){
    pTar = parent.document.getElementById(iframename);
  }
  if (pTar && !window.opera){
   var bHeight = pTar.contentWindow.document.body.scrollHeight;
var dHeight = pTar.contentWindow.document.documentElement.scrollHeight;
var height = Math.max(bHeight, dHeight);
height +=30;
if(pTar != null && height != null) {
	if(height > 400)
       pTar.height = height;
	else
		pTar.height =400;
}

  }
  }catch(E){}
}



//dyniframesizeforall("main_frame");
//setParentFrameSize();


function EscPage(){
	var event = arguments[0]||window.event;
	
    var currentKey = event.charCode||event.keyCode;
    if(currentKey==27){
    	try{
     	    global_ab.fn.popWindow.removePopWindow(false);
		}catch(e){}
    }
     if(currentKey==13){
    	return false;
     }
 
}

document.onkeypress=EscPage;

function refreshIframe(){
	var url = window.location.href;
	if(url.indexOf("?")==-1){
		url += "?_" + (new Date()).getTime();
	}else{
		url += "&_" + (new Date()).getTime();
	}
	window.location.href = url;
}
</script>