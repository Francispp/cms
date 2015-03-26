function trimStr(str){
	str=getValidStr(str);
	if (!str) return "";
	for(var i=str.length-1; i>=0; i--){
		if (str.charCodeAt(i, 10)!=32) break;
	}
	return str.substr(0, i+1);
}

function getValidStr(str) {
	str += "";
	if (str=="undefined" || str=="null" || str=="NaN")
		return "";
	else
		return str;
}

function encode(strIn)
{
	var intLen=strIn.length;
	var strOut="";
	var strTemp;

	for(var i=0; i<intLen; i++)
	{
		strTemp=strIn.charCodeAt(i);
		if (strTemp>255)
		{
			tmp = strTemp.toString(16);
			for(var j=tmp.length; j<4; j++) tmp = "0"+tmp;
			strOut = strOut+"^"+tmp;
		}
		else
		{
			if (strTemp < 48 || (strTemp > 57 && strTemp < 65) || (strTemp > 90 && strTemp < 97) || strTemp > 122)
			{
				tmp = strTemp.toString(16);
				for(var j=tmp.length; j<2; j++) tmp = "0"+tmp;
				strOut = strOut+"~"+tmp;
			}
			else
			{
				strOut=strOut+strIn.charAt(i);
			}
		}
	}
	return (strOut);
}

function decode(strIn)
{
	var intLen = strIn.length;
	var strOut = "";
	var strTemp;

	for(var i=0; i<intLen; i++)
	{
		strTemp = strIn.charAt(i);
		switch (strTemp)
		{
			case "~":{
				strTemp = strIn.substring(i+1, i+3);
				strTemp = parseInt(strTemp, 16);
				strTemp = String.fromCharCode(strTemp);
				strOut = strOut+strTemp;
				i += 2;
				break;
			}
			case "^":{
				strTemp = strIn.substring(i+1, i+5);
				strTemp = parseInt(strTemp,16);
				strTemp = String.fromCharCode(strTemp);
				strOut = strOut+strTemp;
				i += 4;
				break;
			}
			default:{
				strOut = strOut+strTemp;
				break;
			}
		}

	}
	return (strOut);
}
function getEncodeStr(str) {
	return encode(getValidStr(str));
}
//get the time from clinet
function getNowTime(obj){
	var d = new Date();
	var curDateTime = d.getFullYear() +"-"
									+addZero(d.getMonth()+1)+"-"
									+addZero(d.getDate())+" "
									+addZero(d.getHours())+":"
									+addZero(d.getMinutes());
	if(getValidStr(obj.value)==""){
		obj.value=curDateTime;
	}
}
//get the date from clinet
function getNowDate(obj){
	var d = new Date();
	var curDateTime = d.getFullYear() +"-"
									+addZero(d.getMonth()+1)+"-"
									+addZero(d.getDate())
	if(getValidStr(obj.value)==""){
		obj.value=curDateTime;
	}
}
//get the month from clinet
function getNowMonth(obj){
	var d = new Date();
	var curDateTime = d.getFullYear() +"-"
									+addZero(d.getMonth()+1);
	if(getValidStr(obj.value)==""){
		obj.value=curDateTime;
	}
}

//get the year from clinet
function getNowYear(obj){
	var d = new Date();
	var curDateTime = d.getFullYear() ;
	if(getValidStr(obj.value)==""){
		obj.value=curDateTime;
	}
}

function addZero(x) {
  return (x < 0 || x > 9 ? "" : "0") + x
}

function getDecodeStr(str) {
	return ((str)?decode(getValidStr(str)):"");
}
function compareText(str1, str2){
	str1=getValidStr(str1);
	str2=getValidStr(str2);
	if (str1==str2) return true;
	if (str1=="" || str2=="") return false;
	return (str1.toLowerCase()==str2.toLowerCase());
}

function isTrue(value){
	return (value==true || (typeof(value)=="number" && value!=0) ||
		compareText(value, "true") || compareText(value, "T") ||
		compareText(value, "yes") || compareText(value, "on"));
}

function getStringValue(value){
	if (typeof(value)=="string" || typeof(value)=="object")
		return "\""+getValidStr(value)+"\"";
	else if (typeof(value)=="date")
		return "\""+(new Date(value))+"\"";
	else if (getValidStr(value)=="")
		return "\"\"";
	else
		return value;
}

function getInt(value){
	var result=parseInt(value);
	if (isNaN(result)) result=0;
	return result;
}


function getFloat(value){
	var result=parseFloat(value);
	if (isNaN(result)) result=0;
	return result;
}

function getTypedValue(value, dataType){
	var result="";
	switch (dataType){
		case "string":{
			result=getValidStr(value);
			break;
		}
		case "byte":;
		case "short":;
		case "int":;
		case "long":{
			result=Math.round(parseFloat(value));
			break;
		}
		case "float":;
		case "double":;
		case "bigdecimal":{
			result=parseFloat(value);
			break;
		}
		case "date":;
		case "time":;
		case "timestamp":{
			value=getValidStr(value);
			result=new Date(value.replace(/-/g, "/"));
			break;
		}
		case "boolean":{
			result=isTrue(value);
			break;
		}
		default:{
			result=getValidStr(value);
			break;
		}
	}
	return result;
}
function getIEVersion(){
	var index=window.clientInformation.userAgent.indexOf("MSIE");
	if (index<0){
		return "";
	}
	else{
		return window.clientInformation.userAgent.substring(index+5, index+8);
	}
}
//replace String
function replaceAll(sourceStr,replaceStr,distStr){
	var resultStr=sourceStr;
	while(resultStr.indexOf(replaceStr)>-1){
		resultStr= resultStr.replace(replaceStr,distStr);
           }
	 return resultStr;
	}
function setElementsReadOlny(){
	var allelems=document.forms[0].elements;	
	if(allelems!=null&&allelems.length!=null){		
  for(i=0;i<allelems.length;i++){
		var obj=allelems[i];
		//if(obj.readOnly==false){
			 obj.readOnly=true;
			 if(obj.type=="text" || obj.tagName=="TEXTAREA"){
				 obj.removeAttribute("title");
				 obj.setAttribute("ondblclick",null);
				 obj.setAttribute("onclick",null);
			 }
		//}
	}
}
}
function setElementsDisabled(bl){
	if(bl==null)
	  bl=true;
	var allelems=document.forms[0].elements;	
	if(allelems!=null&&allelems.length!=null){		
  for(i=0;i<allelems.length;i++){
		var obj=allelems[i];
		//if(obj.readOnly==false){
			 //obj.readOnly=true;
		    if(obj.name !="pageIndex")
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

//get select values	
function getSelectedAll(checkName){	
	var checkObj = document.getElementsByName(checkName);
	var billIDs ="";	
	for(i=0;i<checkObj.length;i++){
		if(checkObj[i].checked){
			billIDs = billIDs+","+checkObj[i].value;
		}	
	}
	return billIDs.substr(1);
}
//select all
function selectAllRow(selfObj,checkName){
	var checkObj = document.getElementsByName(checkName);	
	for(i=0;i<checkObj.length;i++){
		checkObj[i].checked=selfObj.checked;
	
	}
}
//check one 
function checkOne(chkall){
	var chkID = document.all.tags('input');
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
//isEmpty 
function isEmpty(str){
	if(str==null || str==''){
		return true;
	}
	return false;
}
function ExecuteService(evalstr,classname,methodname,obj){
 buffalo.remoteCall("basic.callService",[classname,methodname,obj],function(reply){
  if(evalstr!="")
    eval(evalstr);                      
  //return reply.getResult(); 
 });
}
function updateSelect(sele,classname,methodname,obj){
buffalo.remoteCall("basic.getSelectValueNames",[classname,methodname,obj],function(reply){
                         var selects = reply.getResult();
                         var tempselevalue=sele.value;                         
                          if(sele.options.length>0){
                          for(i=sele.options.length-1;i>=0;i--){
                              sele.options[i]=null;
                             }
                         }
                         var Obj=sele;
                         eval(selects);
                         sele.value=tempselevalue;
                   }); 
}
function setFeildNotRead(feildnames){
	if(feildnames!=null&&feildnames.length!=null){
	 for(i=0;i<feildnames.length;i++){
	   eval("var obj=document.getElementById(\"wwgrp_myform_getText('"+feildnames[i]+"')\");");
	    eval("var contentobj=document.getElementById('"+feildnames[i]+"');");
	   if(obj!=null&&obj.style!=null){
	     obj.style.display ='none';	
	     if(contentobj!=null&&contentobj.style!=null)
	      contentobj.style.display ='none';
	     }    
	}		
	}	
	}
function setFeildReadOnly(feildnames){
	if(feildnames!=null&&feildnames.length!=null){
	 //alert(feildnames.length);
	 for(i=0;i<feildnames.length;i++){
	   eval("var obj=document.getElementById('"+feildnames[i]+"');");
	   eval("var divobj=document.getElementById(\"wwgrp_myform_getText('"+feildnames[i]+"')\");");	  
	   if(obj!=null){
	     //alert(obj.readOnly);
	     if(divobj!=null)
	        divobj.style.color="#666666";	      
	      obj.readOnly=true;
	      if(obj.type=="text" || obj.tagName=="TEXTAREA"){
				 obj.removeAttribute("title");
				 obj.setAttribute("ondblclick",null);
				 obj.setAttribute("onclick",null);
		  }
	      obj.ondblclick=null;	    	
	     }    
	}		
	}	
	}	
function setFeildNotNull(feildnames,inputclassName,textareaClassName){
	if(feildnames!=null&&feildnames.length!=null){
	 for(i=0;i<feildnames.length;i++){
	  eval("var fieldobj=document.getElementById('"+feildnames[i]+"');");
	  eval("var obj=document.getElementById(\"wwgrp_myform_getText('"+feildnames[i]+"')\");");
	   if(fieldobj!=null&&inputclassName!=null&&inputclassName!=""){
	   	if(fieldobj.tagName=="TEXTAREA"&&textareaClassName!=null&&textareaClassName!=""){	   		
	     	   fieldobj.className=textareaClassName;
	     	}else{
	     	   fieldobj.className=inputclassName;
		}
	     }
	     else{	   
	   if(obj!=null){
	     obj.style.color="blue";
	     //alert(obj.name);
	     }else{
	     if(fieldobj!=null){
	        //else
	           fieldobj.className="inputBlueLine";
	     }
	       //fieldobj.style.border_bottom_color="red";
	     }
	     }	     		    
	}		
	}	
	}
function checkingFeildNotNull(feildnames){
	if(feildnames!=null&&feildnames.length!=null){
	 for(i=0;i<feildnames.length;i++){
	  eval("var obj=document.getElementById('"+feildnames[i]+"');");
	  eval("var labelobj=document.getElementById(\"myform_getText('"+feildnames[i]+"')\");");
	   if(obj!=null&&obj.value!=null){
	     if(obj.value==""){
	     	if(labelobj!=null){
	     	var labeltext=labelobj.innerHTML;
	     	labeltext=trimStr(replaceAll(labeltext,"&nbsp;",""));
	     	alert(labeltext+HINT_FIELD_NOTNULL);
	       }else 
	        alert(HINT_FIELD_NOTNULL_DEFAULT);
	        obj.focus();
	      return false;
	     }
	}	    
	}		
	}
	return true;	
	}
	

function isNotNullProperty(fieldName){    
  if(notNullNames==null){
  	return false;
  }
  
  for(i=0;i<notNullNames.length;i++){  
  	if(notNullNames[i]==fieldName){
  		return true;
  		break;
  	}
  }
  return false;  
}

//**************************
// description：get chinese week string
// week: number of day  range:0~6
//**************************
function getCNWeek(week){
	switch(week){
	  case 0:return "星期日";break;
	  case 1:return "星期一";break;
	  case 2:return "星期二";break;	  
	  case 3:return "星期三";break;
	  case 4:return "星期四";break;
	  case 5:return "星期五";break;	
	  case 6:return "星期六";break;
	}
}


//**************************
// description:get chinese week string by date
// date:string of date (eg."2003-12-12")
//**************************
function getWeek(date){
	var d ;
	if(date=="" || date=="null" || date==" ") return "";
	if(date == null){
	   d = new Date();	   
	}else{	
	  try{
	    var ds = date.split(" ")[0].split("-");
	    var y = ds[0];
		var mm = ds[1]-1;
		var dd = ds[2];
		d = new Date(y,mm,dd);			  
	  }catch(e){
	  	alert("invalid date!");
	  }
	}	
	return getCNWeek(d.getDay());
}
