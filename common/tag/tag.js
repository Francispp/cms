
//标签值脚本附值
function valueScript(objectName,objectValue){
 var formName = new Array();
 var formValue = new Array();
 try{
 formName=objectName.split(/@/);
 formValue=objectValue.split(/@/);
 if(formName.length == formValue.length)
 {
 for (intTmp=0;intTmp<formName.length;intTmp++){
 	document.getElementById(formName[intTmp]).value = formValue[intTmp];
      }
 }
  }
  catch(e){}
 }
 //标签Select选择
 function PopedomOption(selectID)
 {
 var createOption=new Array('','','不能为空','NotNull','必须为数字','Number');
 var popdomSelect=document.getElementById(selectID);
     popdomSelect.options.length = 0;
	for(i=0;i<createOption.length;i+=2)
	{
		popdomSelect.options[i/2] = new Option(createOption[i],createOption[i+1]);
	}
     
}
 