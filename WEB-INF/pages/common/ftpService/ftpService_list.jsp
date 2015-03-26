<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="分发配置管理列表" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<script type='text/javascript' src='/dwr/interface/ftpServiceService.js'></script>
<script type="text/javascript">
	function newFtp() {
		global_ab.fn.popWindow.addPopWindow("${ctx}/base/ftpService!edit.action",
				"500px", "300px", false);
	}

	function edit() {
		var keys = "";
		keys = getSelectedID();
		if (keys == null || keys == '') {
			alert('请先选择记录！');
			return;
		}
		var ids = keys.split(",");
		if (ids.length > 1) {
			alert("一次只能编辑一条数据!");
			return;
		}
		global_ab.fn.popWindow.addPopWindow("${ctx}/base/ftpService!edit.action?id="
				+ ids[0], "500px", "300px", false);
	}

	function del() {
		var keys = "";
		keys = getSelectedID();
		if (keys == null || keys == '') {
			alert('请先选择记录！');
			return;
		}
		if (confirm("确定删除所选记录吗?")) {
			location.href = "${ctx}/base/ftpService!delete.action?keys=" + keys;
		}
	}

	function checkftpconnect() {
		var keys = "";
		keys = getSelectedID();
		if (keys == null || keys == '') {
			alert('请先选择记录！');
			return;
		}
		var ids = keys.split(",");
		if (ids.length > 1) {
			alert("一次只能测试一条数据!");
			return;
		}

		ftpServiceService.checkFtpConnect(keys, {
			callback : function(args) {
				if (args == 1) {
					alert("该ftp服务器配置可以连接上服务器!");
				} else {
					alert("该ftp服务器配置不可以连接上服务器!");
				}
			},
			errorHandler : function(mess) {
			}
		});
	}
</script>
</head>
<body nowrap topmargin="0" leftmargin="0">
	<!-- 状态提示栏 --><%@ include file="/common/messages.inc"%>
	<div class="system-header">
		<div class="edit-header-lion">
			<ul id="lion-ul">
				<ww:if test="#session.loginer.siteId >0">
					<li class="artEdit-btn_ab artEdit-btn-w4letters_ab"><a
						class="artEdit-btn-in_ab" href="#" onclick="newFtp()"><img
							src="${default_imagepath}/ico_013_plus.gif"
							class="ico_ab ico-013_ab" /><span>新建</span></a></li>
					<li class="artEdit-btn_ab"><a class="artEdit-btn-in_ab"
						href="#" onclick="edit();"><img
							src="${default_imagepath}/ico_014_textEdit.gif"
							class="ico_ab ico-014_ab" /><span>编辑</span></a></li>
					<li class="artEdit-btn_ab"><a class="artEdit-btn-in_ab"
						href="javascript:ECSideUtil.reload('myTable');"><img
							src="${default_imagepath}/ico_019_refresh.gif"
							class="ico_ab ico-019_ab" /><span>刷新</span></a></li>
					<li class="artEdit-btn_ab artEdit-btn-w2letters_ab"><a
						class="artEdit-btn-in_ab" href="#" onclick="del()"><img
							src="${default_imagepath}/ico_017_trashFull.gif"
							class="ico_ab ico-017_ab" /><span>删除</span></a></li>
					<li class="artEdit-btn_ab artEdit-btn-w2letters_ab"><a
						class="artEdit-btn-in_ab" href="#" onclick="checkftpconnect()"><img
							src="${default_imagepath}/ico_049_process.gif"
							class="ico_ab ico-017_ab" /><span>测试连接</span></a></li>
					<li class="fn-clear"></li>
				</ww:if>
			</ul>
		</div>
	</div>
	<div class="content">
		<div class="info-box">
			<ec:table items="items" var="item"
				action="${ctx}/base/ftpService.action" editable="false"
				batchUpdate="false" xlsFileName="分发配置管理列表.xls" minColWidth="80"
				generateScript="true" classic="true" listWidth="100%"
				rowsDisplayed="10" tableId="${tableId}" showPrint="true"
				resizeColWidth="true" filterable="true" filterRowsCallback="limit"
				sortRowsCallback="limit" retrieveRowsCallback="limit"
				useAjax="false">
				<ec:row recordKey="${item.id}" rowId="rowid_${GLOBALROWCOUNT}">
					<ec:column property="_0"
						title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />"
						viewsDenied="xls" sortable="false" filterable="false"
						editable="false" width="4%" style="text-align:center">
						<input type="checkbox" name="_selectitem" value="${item.id}"
							onclick='checkOne(allbox);'>
					</ec:column>
					<ec:column property="_1" title="序号" width="3%" sortable="false"
						editable="false" filterable="false" value="${GLOBALROWCOUNT}"
						style="text-align:center" />
					<ec:column property="ftpIp" title="服务器ip" sortable="true"
						filterable="true" style="text-align:center"
						editTemplate="ecs_t_input" />
					<ec:column property="port" title="服务器端口" sortable="true"
						filterable="true" style="text-align:center"
						editTemplate="ecs_t_input" />
					<ec:column property="isFtp" title="服务器类型" sortable="true"
						filterable="true" style="text-align:center"
						editTemplate="ecs_t_select_serviceType">
						<c:if test="${item.isFtp==1}">FTP</c:if>
						<c:if test="${item.isFtp==2}">SFTP</c:if>
					</ec:column>
					<ec:column property="userName" title="用户名" sortable="true"
						filterable="true" style="text-align:center"
						editTemplate="ecs_t_input" />
				</ec:row>
			</ec:table>
			<!-- 编辑和过滤所使用的 通用的文本框模板 -->
			<textarea id="ecs_t_input" rows="" cols="" style="display: none">
				<input type="text" class="inputtext" value=""
					onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
					name="" />
			</textarea>
		</div>
	</div>
	<!-- 页脚 -->
	<%@ include file="/common/foot.inc"%>
</body>
</html>
