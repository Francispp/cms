var jsonReg = /^[\s\r\n]*\{.*\}[\s\r\n]*$/;

function LoadData(url, success, fail){
	var xmlhttp = getRequest(url, success, fail);
	xmlhttp.send();
}

function getRequest(url, success, fail){
	var xmlhttp;
	if (window.XMLHttpRequest){// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	} else {// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.open("POST", url ,true);
	xmlhttp.onreadystatechange = function(){
		if(xmlhttp.readyState==4){
			if(xmlhttp.status==200){
				if(success){
					var data = xmlhttp.responseText;
					if(jsonReg.test(data)){
						eval("data = " + data);
						success(data);
					} else {
						fail(-1);
					}
				}
			} else {
				if(fail){
					fail(xmlhttp.status);
				}
			}
		}
	}
	return xmlhttp;
}