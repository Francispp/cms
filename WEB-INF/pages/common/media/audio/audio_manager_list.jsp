<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<%@ include file="/common/meta.inc"%>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<script type="text/javascript">
	function addItem() {
		global_ab.fn.popWindow.addPopWindow("${ctx}/base/audio!add.action?albumId=${albumId}&isdefault=${isdefault}",
				"350px", "272px", false);
	}

	function editItem() {
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
		global_ab.fn.popWindow.addPopWindow("${ctx}/base/audio!edit.action?id="
				+ ids[0], "350px", "252px", false);
	}

	function delItem() {
		var keys = "";
		keys = getSelectedID();
		if (keys == null || keys == '') {
			alert('请先选择记录！');
			return;
		}
		if (confirm("确定删除所选记录吗?")) {
			location.href = "${ctx}/base/audio!delete.action?albumId=${albumId}&isdefault=${isdefault}&keys="
					+ keys;
		}
	}
	//播放音频
	function broadcastAudio() {
		var keys = "";
		keys = getSelectedID();
		if (keys == null || keys == '') {
			alert('请先选择记录！');
			return;
		}
		var ids = keys.split(",");
		if (ids.length > 1) {
			alert("一次只能选择一条!");
			return;
		}
		
		global_ab.fn.popWindow.addPopWindow("${ctx}/base/audio!broadcast.action?id="
				+ ids[0], "350px", "150px", false);


	}
	function changeAlbum() {
		var keys = getSelectedID();
		if (keys == null || keys == '') {
			alert('请选择想要转移的音频');
			return;
		}
		global_ab.fn.popWindow
				.addPopWindow(
						"${ctx}/base/album!changeAlbumtree.action?id=${albumId}&isdefault=${isdefault}&albumType=${albumType}&keys="
								+ keys, "385px", "383px", false);
	}
	 function reload(){
		 location.href='${ctx}/base/audio!listByAlbum.action?albumId=${albumId}&isdefault=${isdefault}&albumType=${albumType}';
	 }
</script>
</head>
<body>
	<!-- 状态提示栏 -->
	<%@ include file="/common/messages.inc"%>
	<div class="system-header">
		<div class="edit-header-lion">
			<ul id="lion-ul">
				<li class="artEdit-btn_ab artEdit-btn-w2letters_ab"><a
					class="artEdit-btn-in_ab" href="#" onclick="addItem();"> <img
						src="${default_imagepath}/ico_013_plus.gif"
						class="ico_ab ico-013_ab" /> <span>录入</span> </a>
				</li>
				<li class="artEdit-btn_ab"><a class="artEdit-btn-in_ab"
					href="#" onclick="editItem();"> <img
						src="${default_imagepath}/ico_014_textEdit.gif"
						class="ico_ab ico-014_ab" /> <span>编辑</span> </a>
				</li>
				<li class="artEdit-btn_ab artEdit-btn-w2letters_ab"><a
					class="artEdit-btn-in_ab" href="#" onclick="changeAlbum()"> <img
						src="${default_imagepath}/ico_017_trashFull.gif"
						class="ico_ab ico-017_ab" /> <span>转移</span> </a>
				</li>

				<li class="artEdit-btn_ab artEdit-btn-w2letters_ab"><a
					class="artEdit-btn-in_ab" href="#" onclick="delItem()"> <img
						src="${default_imagepath}/ico_017_trashFull.gif"
						class="ico_ab ico-017_ab" /> <span>删除</span> </a>
				</li>
				<li class="artEdit-btn_ab"><a class="artEdit-btn-in_ab"
					href="javascript:reload();"> <img
						src="${default_imagepath}/ico_019_refresh.gif"
						class="ico_ab ico-019_ab" /> <span>刷新</span> </a></li>

				<li class="artEdit-btn_ab artEdit-btn-w2letters_ab"><a
					class="artEdit-btn-in_ab" href="#" onclick="broadcastAudio()">
						<img src="${default_imagepath}/ico_017_trashFull.gif"
						class="ico_ab ico-017_ab" /> <span>试听</span> </a>
				</li>
				<li class="fn-clear"></li>
			</ul>
		</div>
	</div>
	<div class="content">
		<div class="info-box">


			<ec:table items="items" var="item" action="${ctx}/base/audio.action" editable="false"
				batchUpdate="false" xlsFileName="音频信息.xls" 
				 minColWidth="80" generateScript="true"
				classic="true" listWidth="100%" rowsDisplayed="10"
				tableId="${tableId}" showPrint="true" resizeColWidth="true"
				filterable="false" filterRowsCallback="limit"
				sortRowsCallback="limit" retrieveRowsCallback="limit" useAjax="false">

				<ec:row recordKey="${item.id}" rowId="rowid_${GLOBALROWCOUNT}">
					<ec:column property="_0"
						title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />"
						viewsDenied="xls" sortable="false" filterable="false"
						editable="false" width="4%" style="text-align:center">
						<input type="checkbox" name="_selectitem" value="${item.id}"
							onclick='checkOne(allbox);'>
					</ec:column>
					<ec:column property="_1" title="序号" width="3%"
						value="${GLOBALROWCOUNT}" style="text-align:center" />

					<ec:column property="title" title="标题" style="text-align:center" />
					<ec:column property="modifUserName" title="最后修改人"
						style="text-align:center" />

					<ec:column property="createTime" title="创建时间"
						editTemplate="ecs_t_input" style="text-align:center" />

				</ec:row>
			</ec:table>
		</div>
	</div>
	<!-- 页脚 -->
	<%@ include file="/common/foot.inc"%>
	<c:if test="${not empty actionErrors}">
		<c:forEach var="err" items="${actionErrors}">
			<script>
				alert("删除数据不完全，可能数据被引用");
			</script>
		</c:forEach>
	</c:if>


</body>
</html>