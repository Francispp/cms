<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="选择站点分发ip列表" />
<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<script src="${ctx}/common/frame_js/select.js" type="text/javascript"></script>
<%@ include file="/common/ec/ec.inc"%>
<script type="text/javascript">
function save(){
	 var ids=getSelectedID();
	 /* if(ids==null||ids==''){
	    alertMessage('请先选择记录！');
	    return ;
	    } */
	    var dtype='${dtype}';
		var myUrl = "${ctx}/cms/cmsIpDistribution!saveSelectIp.action?siteId="+${siteId};
		var param="ids="+ids+"&did="+${id}+"&dtype="+dtype;
		
		 new Ajax.Request(myUrl, {
			    method:'post',
			    parameters:param,
			    onSuccess: function(transport) {
			    	alert('保存成功');
			    	 window.close();
			    },
			    onFailure: function(){alert('保存失败');}
			  });
		 
		 
}
</script>

</head>
<body nowrap topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<DIV ID="message"><%@ include file="/common/messages.inc"%></DIV>
<!-- 页面标题 -->
<!--div id="titel">
	<div id="titel_left">
	<div id="titel_right">
	 <div id="titel_txt"><c:out value="${title}"/></div>
	 </div>
	</div>
</div-->
<!-- 操作功能按钮条 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">

	<tr>
		<td valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td height="1"></td>
			</tr>
			<tr>
				<td>
				<div id="titel2">
				<div id="titel2_left">
				<div id="titel2_right">
				<div id="nav2">
				<div>
				<ul>
					<li id="button1"><a href="javascript:save();">保存</a></li>
				</ul>
				</div>
				</div>
				</div>
				</div>
				</div>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<!-- 页面主要内容 -->
	<tr>
		<td>
		<table width="99%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align="center" valign="top">
				<div id="list_scroll_content" align=justify style="height:320px;">
				<ec:table items="items" var="item" 
					action="${ctx}/base/ftpService!selectIp.action"
					batchUpdate="false"  minColWidth="80" generateScript="true"
					classic="true" listWidth="100%" rowsDisplayed="10"
					tableId="${tableId}" showPrint="false" resizeColWidth="true"
					filterable="true" filterRowsCallback="limit"
					sortRowsCallback="limit" retrieveRowsCallback="limit" >
					<ec:row recordKey="${item.id}" rowId="rowid_${GLOBALROWCOUNT}">
						<ec:column property="id"
							title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />全选"
							viewsDenied="xls" sortable="false" filterable="false" width="8%"
							style="text-align:left">
							
						
							<ww:if test="%{item.checked=='1' }">
							<input type="checkbox" name="_selectitem" value="${item.id}"
								onclick='checkOne(allbox);' checked="checked"/>
							</ww:if>
							<ww:else>
							<input type="checkbox" name="_selectitem" value="${item.id}"
								onclick='checkOne(allbox);' />
							</ww:else>
						
						</ec:column>
						<ec:column property="rowcount" cell="rowCount" sortable="false"
							title="序号" />
						<ec:column property="ftpIp" title="服务器ip" sortable="true"/>
						<ec:column property="port" title="服务器端口" />
						<ec:column property="isFtp" title="服务器类型" >
						<ww:if test="%{item.isFtp=='1'}">
						FTP
						</ww:if>
						<ww:else>
						SFTP
						</ww:else>
						</ec:column>
						<ec:column property="userName" title="用户名" sortable="true"/>
						<ec:column property="passWord" title="密码" />
					</ec:row>
				</ec:table> <!-- 编辑和过滤所使用的 通用的文本框模板 --> <textarea id="ecs_t_input" rows=""
					cols="" style="display:none">
				<input type="text" class="inputtext" value=""
					onblur="ECSideUtil.updateEditCell(this)" style="width:100%;"
					name="" />
			</textarea></div>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
