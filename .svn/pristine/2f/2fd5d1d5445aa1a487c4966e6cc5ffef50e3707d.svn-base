<%@ page contentType="text/html; charset=UTF-8"%>
</table> 
<script language="javascript">
//取消
  function doCancel(){
   //var url=replaceAll(window.location.href,'!transact','!edit');
   //myform.action=url; 
   //myform.submit();
    window.returnValue = null;
    window.close();   
  }
  //切换页面
function changeTransactPage(obj)
{  

	document.getElementById('method').value=''+obj;
	 
   var curl=window.location.href;
   var pindex=curl.indexOf('?');
   if(pindex >0)
    curl=curl.substring(0,pindex);
   //window.location.href=url;
   //alert(url);
   myform.action=curl;
   myform.submit(); 
}
function openFlowPicture(){
 var features="width=800,height=600,resizable=1,scrollbars=1";
 wurl="${ctx}/flow/track!picture.action?id=${id}&process=${processid}";
 window.open(wurl,'myWin',features);
   return ;	
 }
  </script>