function checkAll(all){	
	var chkID = document.all.tags('input');
	allchecked=true;

	for (i=0; i<chkID.length; i++){
		if (chkID(i).type=="checkbox" && chkID(i).checked==false && chkID(i)!=all){
			allchecked=false;
		}
	}
	for (i=0; i<chkID.length; i++){
		if (allchecked==true) {
			chkID(i).checked=false;
		}else{
			chkID(i).checked=true;
		}
	}
}

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

function delMainChecked(form1, chkall, childID, errorInfo){
	var chkID = document.all.tags('input');
	var param = "";
	for (i=0; i<chkID.length; i++){
		if (chkID(i).type=="checkbox" && chkID(i).checked==true && chkID(i)!=chkall){
			param = param + chkID(i).value + ",";
		}
	}
	param = param.substr(0,param.length-1);
	//alert(param);
	if (param!=""){
		if (confirm(errorInfo)){
			childID.value=param;
			form1.submit();
		}
	}

}

//一下为多个列表的情况（即多个全选框）
function checkAllEx(chkonename){	
	var chkID = document.all.tags('input');
	allchecked=true;
	for (i=0; i<chkID.length; i++){
		if (chkID(i).type=="checkbox" && chkID(i).checked==false && chkID(i).name==chkonename){
			allchecked=false;
		}
	}

	for (i=0; i<chkID.length; i++){
		if (allchecked==true) {
			if (chkID(i).type=="checkbox" && chkID(i).name==chkonename){
				chkID(i).checked=false;
			}
		}else{
			if (chkID(i).type=="checkbox" && chkID(i).name==chkonename){
				chkID(i).checked=true;
			}
			
		}
	}
}


function checkOneEx(chkall, chkOneName){
	var chkID = document.all.tags('input');
	allchecked=true;

	for (i=0; i<chkID.length; i++){
		if (chkID(i).type=="checkbox" && chkID(i).checked==false && chkID(i).name==chkOneName){
			
			allchecked=false;
		}
	}
	if (allchecked==false) {
		chkall.checked=false;
	}else{
		chkall.checked=true;
	}

}

//删除细表纪录
function delChecked(form1, chkoneName, childID, errorInfo){
	var chkID = document.all.tags('input');
	var param = "";
	for (i=0; i<chkID.length; i++){
		if (chkID(i).type=="checkbox" && chkID(i).checked==true && chkID(i).name==chkoneName){
			param = param + chkID(i).value + ",";
		}
	}

	param = param.substr(0,param.length-1);
	//alert(param);
	if (param!=""){
		childID.value=param;

		if (errorInfo==""){
			form1.submit();
			return;
		}
		if (confirm(errorInfo)){
			form1.submit();
		}
	}
	

}
//get select values	
function getSelectedID(checkName,t_document){
	if(checkName==null)
	  checkName="_selectitem";
	var checkObj = document.getElementsByName(checkName);
	if(t_document!=null)
	  checkObj = t_document.getElementsByName(checkName);
	var billIDs ="";	
	for(i=0;i<checkObj.length;i++){
		if(checkObj[i].checked&&checkObj[i].type=='checkbox'){
			billIDs = billIDs+","+checkObj[i].value;
			
		}	
	}
	return billIDs.substr(1);
}