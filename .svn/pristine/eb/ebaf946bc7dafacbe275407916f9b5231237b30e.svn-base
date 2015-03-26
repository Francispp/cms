<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<%@ include file="/common/cyber_taglibs.inc"%>
<c:set var="title" value="类别树" />
	<head>
		<title>${title}</title>
		<link rel=stylesheet type=text/css href="/styles/cybercms/css.css">
<%@ include file="/common/buffalo.inc"%>
	</head>
	<body >
		<table  cellpadding=0% cellspacing=0%  >
			<tr>
				<td>
					<div ><!-- 右部 开始 -->
						<iframe id=switchToTree height=400px src="/base/temLibrary!publicItree2.action" frameBorder=0 width=153></iframe>
					</div>
				</td>
				<td >
					<div>
						<iframe id=main_frame  height=400px src="" frameBorder=0 width=900px name=main_frame></iframe>
					</div>
				</td>
			</tr>
		</table>
		<script type=text/javascript>
		  
		  //更改内容区内容
		  function setMainFrameUrl(murl){
		  	if(murl == null || murl==''){
			 	 document.all.main_frame.src='';
			 }else{
				 if(document.frames){
					 document.frames['main_frame'].location.href = murl;
				 } else {
					 document.getElementById('main_frame').src = murl;
				 }
			 }
		  }
			function FunSelectDisabled(flag){
				try{
					var isIE = !!window.ActiveXObject;   
					var isIE6 = isIE && !window.XMLHttpRequest;
					if(isIE6) {
						var currentsite = document.getElementById("currentsite");
						if(undefined!=currentsite || null!=currentsite){
							currentsite.disabled=flag;
						}
						var main_frame = document.frames['main_frame'];
						if(undefined!=main_frame || null!=main_frame){
							main_frame.document.getElementsByName("myTable_rd")[0].disabled=flag;
						}
					}
				}catch(e){
				}finally{
					return true;
				}
			}
		</script>
	</body>
</html>