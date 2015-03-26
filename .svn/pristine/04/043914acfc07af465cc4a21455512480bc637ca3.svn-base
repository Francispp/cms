<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<script type='text/javascript' src='/dwr/interface/LabelService.js'></script>
 <!DOCTYPE HTML>
<html>
<%@ include file="/common/meta.inc" %>
<script type="text/javascript" src="/common/cybercms_js/global_ab.js"></script>
<%@ include file="/common/js.inc" %>
<%@ include file="/common/validation.inc"%>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>

<c:set var="title" value="标签编辑" />

<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.Enumeration"%>
<!DOCTYPE html>
<html>
	<head>
		<title>${title}</title>
		<%@ include file="/common/meta.inc"%>
		
		<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
		
		<%@ include file="/common/js.inc"%>
		
		<script src="${ctx}/common/frame_js/select.js"	type="text/javascript"></script>
		
		
		<ww:head/>
		<script type="text/javascript">
	
			function save(){
			  	var id = document.getElementById("domain.id").value;
			  	var title = document.getElementById("domain.title").value;
				var valid = new Validation('myform',{immediate:true});
				if(valid.validate()){
					LabelService.dwrLabelType(id,title,
						function(data){ 
							if (data == "0") {
								alert("标题名重复,必须唯一!");
								document.getElementById("domain.title").value = '';
								document.getElementById("domain.content").value = '';
								document.getElementById("domain.title").focus();
								return;	
							}
				  			myform.submit();
						} 
					); 
				}
		     }
		
			function goBack(){
				window.opener.location.reload();
				window.close();
// 				location.href = "${ctx}/cms/label!list.action" ;
			}
		</script>
	</head>
	<body class="artEdit_ab" style="text-align:center;">
		<%@ include file="/common/messages.inc" %>
		<form class="artEdit-frame_ab" method="post" action="${ctx }/cms/label!saveOrUpdate.action" name="myform" id="myform">
			<ww:hidden	id="domain.id" name="domain.id" />
			<div class="artEdit-frame-header_ab">
				<img src="${default_imagepath}/pic_artEdit_logo.jpg" class="artEdit-logo_ab" width="204" height="30" alt="cms"/>
				<img src="${default_imagepath}/pic_artEdit_shadow.jpg" class="artEdit-imgShadow_ab" width="962" height="10" alt="cms"/>
			</div>

			<div class="artEdit-frame-border_ab artEdit-frame-tit_ab">标签编辑<img src="${default_imagepath}/btn_close.gif" class="artEdit-frame-close_ab" id="windowClose_ab"/></div>
			<ul class="artEdit-frame-border_ab artEdit-frame-editField_ab">
				<li class="artEdit-frame-blankForIe67"></li>

					<c:if test="${isEdit==true}">
						<li class="artEdit-btn_ab">
						<a class="artEdit-btn-in_ab" href="javascript:save()">
							<img src="${default_imagepath}/ico_004_floppy.gif" class="ico_ab ico-004_ab" />
							<span>保存</span>
						</a>
					</li>
				</c:if> 

				
				<li class="artEdit-btn_ab">
					<a class="artEdit-btn-in_ab" href="javascript:goBack()">
						<img src="${default_imagepath}/ico_009_order1.gif" class="ico_ab ico-009_ab" />
						<span>返回</span>
					</a>
				</li>

				<li class="fn-clear"></li>
			</ul>
			<div class="artEdit-frame-border_ab artEdit-frame-blank_ab">
				<img src="${default_imagepath}/pic_artEdit_lines.gif" width="100%" height="6"/>
			</div>
			<ul class="artEdit-frame-border_ab artEdit-frame-inField_ab">
	
				<li class="artEdit-frame-inField-item_ab">
					<table width="92%" border="0" align="center" cellpadding="0" cellspacing="10">
						<tr class="label_ab">
						<td align="right" width="70" >
							<td align="right" width="70" ><span class="artEdit-frame-inField-item-tit_ab"><font size="2">标题： </font></span></td>
							<td align="left" width="260">
								<ww:textfield id="domain.title"  name="domain.title" size="30" cssClass="textField_ab required "/>
							</td>
						</tr>

					</table>
					<div class="fn-clear"></div>
				</li>
			</ul>
			<div class="artEdit-frame-border_ab artEdit-frame-pluInBg_ab" style="padding: 20 20px;" >
				<div class="artEdit-frame-pluIn_ab" >
				<ww:textarea cssClass="max-length-null" id="Html_7322696767" name="domain.content"></ww:textarea><script type="text/javascript"></script><%com.ckeditor.CKEditorConfig ckConfig_Html_7322696767 = new com.ckeditor.CKEditorConfig();ckConfig_Html_7322696767.addConfigValue("filebrowserImageUploadUrl", pageContext.getAttribute("ctx")+"uploadFile!uploadCK.action?file.fileType=image&file.docId="+request.getAttribute("id")+"&file.channelId="+request.getAttribute("channelId")+"&file.fieldName=domain.content");ckConfig_Html_7322696767.addConfigValue("filebrowserFlashUploadUrl", pageContext.getAttribute("ctx")+"uploadFile!uploadCK.action?file.fileType=flash&file.docId="+request.getAttribute("id")+"&file.channelId="+request.getAttribute("channelId")+"&file.fieldName=domain.content");%><ckeditor:replace basePath="/common/ckeditor/" config="<%=ckConfig_Html_7322696767%>" replace="domain.content"></ckeditor:replace></td>
					<%-- <ww:textarea id="domain.content" name="domain.content" cssStyle="width:100%;height:100px"/> --%>
				</div>
			</div>
			<div class="artEdit-frame-rights_ab">Copyright by Cyberway information Technology Co.,Ltd.All Rights Reserved.2014-2015 designed by cyberway</div>
		</form>
		<%@ include file="/common/foot.inc"%>

		<script src="${ctx}/common/cybercms_js/global_ab.js" type="text/javascript"></script>
		<script type="text/javascript">
			global_ab.init.btnAct_forIe6("li");
			global_ab.init.btnAct_forIe6("div");
			
			(function(){
				document.getElementById("windowClose_ab").onclick = function(){
					goBack();
				}
			})();
			var valid = new Validation('myform',{immediate:true});
			<c:if test="${isEdit!=true}">
				setElementsDisabled(true);
			</c:if>
			var templateType = "${templateType}";
			
			//获得表单的字段	
			function onChangeForm(){
				var obj=new Array;
				obj[0]=''+document.getElementById("formOid").value;
				updateSelect(document.myform.formFields,'coreFormService','getFieldsByForm',obj);
			}
			//获得当前表单的所有字段select对象
			function getFormFields(){
				//若不为表单模板，返回空的select对象
				if(document.myform.formFields==null)
				return document.myform.tempformFields;
				return document.myform.formFields;
			}
			function getResourceFields(){
				return document.myform.cmsResources;
			}	
			
			function whetherClose(){
				var domainid="${domain.id}";
				var isCloseWindow="${isCloseWindow}";
				if(domainid!=""&&isCloseWindow!=""){
					try {
						window.close();
					}catch(e){
						window.close();
					}
					window.close();
				}
			}
		
			whetherClose();
		</script>
	</body>
</html>
