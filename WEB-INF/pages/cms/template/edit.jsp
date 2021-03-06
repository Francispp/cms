<%@ page contentType="text/html; charset=UTF-8"%>

<%@ include file="/common/cyber_taglibs.inc"%>
<c:set var="title" value="模板编辑" />

<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.Enumeration"%>
<!DOCTYPE html>
<html>
	<head>
		<title>${title}</title>
		<%@ include file="/common/meta.inc"%>
		
		<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
		
		<%@ include file="/common/buffalo.inc"%>
		<%@ include file="/common/js.inc"%>
		
		<script src="${ctx}/common/frame_js/select.js"	type="text/javascript"></script>
		
		<%@ include file="/common/codemirror.inc"%>
		<%@ include file="/common/validation.inc"%>
		
		<ww:head/>
		<script type="text/javascript">
			function save(){
				codeMirror.save();
				if(valid.validate()){	
					myform.submit();
				}
			}
			
			function preview (){
				if(${domain.issued}){
					window.open ("${ctx}/cms/template!preview.action?id=${domain.id}&channelId=${domain.channel_id}");	
				}else{
					alert("请将模板的发布状态设置是为:是！");
				}
			}
			
			function generateAdminSummary (){
				save ();
				window.open ("${ctx}/cms/template!edit.action?formTemplateId=${domain.id}&channelId=${domain.channel_id}&templateType=4");
			}
			
			function goHelp(){
				location.href = "${ctx}/cms/label!titleList.action";
			}
			
			function goBack(){
				opener.document.getElementById("returnstatus").value=opener.location.href;
				window.close();
			}
		
			function defaultItem (siteid, channel, id, templateType){
				if (confirm("您确定要设置成默认模板吗?")){
					location.href="template!setDefault.action?id=" + id + "&siteId=" + siteid + "&channelId=" + channel + "&templateType=" + templateType;
				}	
			}
		
			function cancelDefaultItem (siteid, channel, id, templateType){
				if (confirm("您确定要取消默认模板吗?"))
					location.href="template!setDefault.action?id=&siteId=" + siteid + "&channelId=" + channel + "&templateType=" + templateType;
			}
		
			function editScript(){
					wx = '400px';
					wy = '320px';
			 	var url = ctx + "/cms/template!editscript.action?id=${domain.id}";
			 	var obj = showframe('编辑脚本', url);
					if (!obj) {
					document.getElementById("beforsavescript").value = obj.beforsavescript;
					document.getElementById("aftersavescript").value = obj.aftersavescript;
					save();
				}
			}
		
			function setInclude(siteId,channelId,templateId,templateType){
				var url ="templateGather!list.action?templateId="+templateId+"&channelId="+channelId+"&templateType="+templateType+"&siteId="+siteId;
				window.open(url,'','resizable=yes,scrollbars=yes,menubar=no,toolbar=yes,status=no,width=600px,height=500px');
			}
		</script>
	</head>
	<body class="artEdit_ab" style="text-align:center;">
		<%@ include file="/common/messages.inc" %>
		<form class="artEdit-frame_ab" method="post" action="template!saveOrUpdate.action?siteId=${siteId}&channelId=${channelId}&templateType=${templateType}" name="myform" id="myform">
			<ww:hidden	name="pageStyle" id="pageStyle"/>
			<ww:if test="siteId == null">
				<ww:if test="domain.id == null">
					<input type="hidden" value="<ww:property value='channelManagerService.getChannelFromCache (@java.lang.Long@valueOf (#request.channelId.toString ())).site.oid' />" id="siteField"/>
				</ww:if>
				<ww:else>
					<input type="hidden" value="<ww:property value='channelManagerService.getChannelFromCache (domain.channel_id).site.oid' />" id="siteField" />
				</ww:else>
			</ww:if>
			<ww:else>
				<ww:hidden name="siteId" id="siteField"/>
			</ww:else>
			<ww:hidden	name="domain.id" />
			<ww:hidden	name="domain.isPublishStatic" />
			<ww:hidden	name="domain.beforsavescript" id="beforsavescript"/>
			<ww:hidden	name="domain.aftersavescript" id="aftersavescript"/>
			<div class="artEdit-frame-header_ab">
				<img src="${default_imagepath}/pic_artEdit_logo.jpg" class="artEdit-logo_ab" width="204" height="30" alt="cms"/>
				<img src="${default_imagepath}/pic_artEdit_shadow.jpg" class="artEdit-imgShadow_ab" width="962" height="10" alt="cms"/>
			</div>

			<div class="artEdit-frame-border_ab artEdit-frame-tit_ab">模板编辑<img src="${default_imagepath}/btn_close.gif" class="artEdit-frame-close_ab" id="windowClose_ab"/></div>
			<ul class="artEdit-frame-border_ab artEdit-frame-editField_ab">
				<li class="artEdit-frame-blankForIe67"></li>

		 		<ww:if test="domain.id != null">
		 			<li class="artEdit-btn_ab">
			 			<a class="artEdit-btn-in_ab" href="javascript:preview();">
							<img src="${default_imagepath}/ico_012_zoom.gif" class="ico_ab ico-012_ab" />
							<span>预览</span>
						</a>
					</li>
		 		</ww:if>
					<c:if test="${isEdit==true}">
						<li class="artEdit-btn_ab">
						<a class="artEdit-btn-in_ab" href="javascript:save()">
							<img src="${default_imagepath}/ico_004_floppy.gif" class="ico_ab ico-004_ab" />
							<span>保存</span>
						</a>
					</li>
						<c:if test="${templateType==1||domain.type==1}">
							<li class="artEdit-btn_ab">
							<a class="artEdit-btn-in_ab" href="javascript:editScript()">
								<img src="${default_imagepath}/ico_007_pencil.gif" class="ico_ab ico-007_ab" />
								<span>编辑脚本</span>
							</a>
						</li>
						</c:if>
				</c:if> 

				<li class="artEdit-btn_ab">
					<a class="artEdit-btn-in_ab" href="javascript:goHelp()">
						<img src="${default_imagepath}/ico_056_notepad.gif"" class="ico_ab ico-009_ab" />
						<span>帮助文档</span>
					</a>
				</li>
				
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
							<td align="right" width="70" ><span class="artEdit-frame-inField-item-tit_ab"><font size="2">名称： </font></span></td>
							<td align="left" width="260">
								<ww:textfield name="domain.name" size="30" cssClass="textField_ab required validate-alphanum max-length-48"/>
							</td>
				 
							<td align="right" class="inside_list" width="100" ><font size="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类型：</font></td>
							<td align="left">
								<font size="2">	 <ww:property value="domain.typeName"/></font>
								<ww:hidden name="domain.type" />
							</td>
						</tr>

						<c:if test="${templateType==1||domain.type==1}">	
							<tr>
								<td align="right" class="inside_list" width="70"><span class="artEdit-frame-inField-item-tit_ab"><font size="2">表单：</font></span></td>
								<td align="left" width="260">
									<ww:select name="domain.form.oid" id="formOid" list="forms"	onchange="onChangeForm();"/>
								</td>
								<td align="right"	width="100"> </td>
								<td align="left">
									<div id="formFieldsView" style="display: none">
										<span class="artEdit-frame-inField-item-tit_ab"><font size="2">表单字段：</font></span>
										<ww:select name="domain.formFields" id="formFields" list="formFields"/> 
									</div>		
								</td>
							</tr>
						</c:if>
						<c:if test="${(templateType>1&& templateType<4)||domain.type>1&& domain.type<4}">
							<div id="formFieldsView" style="display: none">
								<font size="2">表单字段：</font><ww:select name="domain.formFields" id="formFields" list="formFields"/>
							</div> 
						</c:if>		
						<div id="tempformFieldsView" style="display: none">			
							<select name="tempformFields" id="tempformFields"> </select>
							<ww:select name="cmsResources" id="resources" list="resources"/>	 
						</div>		 
						<tr>
							<td align="right" class="inside_list" width="70"><font size="2">发布：</font></td>
							<td align="left" width="260">
								<ww:select name="domain.issued" list="trueOfFalseMap1" cssClass="select_ab select-w98_ab"/>
							</td>
							<td align="right" class="inside_list" width="100"><font size="2">模板说明：</font></td>
							<td align="left">
								<ww:textfield name="domain.description"	size="30"	cssClass="textField_ab textField-w261_ab max-length-100"/>
							</td>
						</tr>
					</table>
					<div class="fn-clear"></div>
				</li>
			</ul>
			<div class="artEdit-frame-border_ab artEdit-frame-pluInBg_ab" style="padding: 20 20px;" >
				<div class="artEdit-frame-pluIn_ab" >
					<ww:textarea id="bodyField" name="domain.body" cssStyle="width:100%;height:100px"/>
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
					window.close();
				}
			})();
			var valid = new Validation('myform',{immediate:true});
			<c:if test="${isEdit!=true}">
				setElementsDisabled(true);
			</c:if>
			var templateType = "${templateType}";
			
			codeMirror = CodeMirror.fromTextArea(document.getElementById("bodyField"), {
			mode: "text/html",
			extraKeys: {
				"Alt-/": "autocomplete",
				"F11": function(cm) {
					cm.setOption("fullScreen", !cm.getOption("fullScreen"));
				},
				"Esc": function(cm) {
					if (cm.getOption("fullScreen")) cm.setOption("fullScreen", false);
				}
			},
			lineNumbers: true,
			indentUnit: 4,
			indentWithTabs: true,
			styleActiveLine: true,
			theme: "eclipse",
			foldGutter: true,
			gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"]
			});
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
						window.opener.ECSideUtil.reload('myTable');
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