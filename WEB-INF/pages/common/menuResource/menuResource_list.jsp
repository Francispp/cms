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
<script type="text/javascript" src="/common/cybercms_js/global_ab.js"></script>
<script type="text/javascript">
	function newMenu() {
		global_ab.fn.popWindow.addPopWindow("base/menuResource!edit.action","500px", "430px", false);

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
		global_ab.fn.popWindow.addPopWindow("base/menuResource!edit.action?id="+ ids[0], "500px", "430px", false);

	}

	function del() {
		var keys = "";
		keys = getSelectedID();
		if (keys == null || keys == '') {
			alert('请先选择记录！');
			return;
		}
		if (confirm("确定删除所选记录吗?")) {
			location.href = "${ctx}/base/menuResource!delete.action?keys="
					+ keys;
		}
	}
</script>
</head>
<body style="margin:0px;padding:0px;">

	<!-- 状态提示栏 -->
	<%@ include file="/common/messages.inc"%>
	<div class="system-header">
		<div class="edit-header-lion">
			<ul id="lion-ul"> 
				<cms:auth resCode="SYS_MENU_RES_MAN">
					<li class="artEdit-btn_ab artEdit-btn-w2letters_ab"><a
						class="artEdit-btn-in_ab" href="#" onclick="newMenu();"> <img
							src="${default_imagepath}/ico_013_plus.gif"
							class="ico_ab ico-013_ab" /> <span>新建</span> </a>
					</li>
					<li class="artEdit-btn_ab"><a class="artEdit-btn-in_ab"
						href="#" onclick="edit();"> <img
							src="${default_imagepath}/ico_014_textEdit.gif"
							class="ico_ab ico-014_ab" /> <span>编辑</span> </a>
					</li>
				</cms:auth>
				<li class="artEdit-btn_ab"><a class="artEdit-btn-in_ab"
					href="javascript:location.href='${ctx}/base/menuResource.action';">
						<img src="${default_imagepath}/ico_019_refresh.gif"
						class="ico_ab ico-019_ab" /> <span>刷新</span> </a>
				</li>
				<cms:auth resCode="SYS_MENU_RES_MAN">
					<li class="artEdit-btn_ab artEdit-btn-w2letters_ab"><a
						class="artEdit-btn-in_ab" href="#" onclick="del()"> <img
							src="${default_imagepath}/ico_017_trashFull.gif"
							class="ico_ab ico-017_ab" /> <span>删除</span> </a>
					</li>
				</cms:auth>
				<li class="fn-clear"></li>
			</ul>
		</div>
	</div>
	<div class="content" style="width:100%;">
		<div class="info-box" style="width:100%;">
			<table class="myTab_ab" width="100%" border="0" cellspacing="0"
				cellpadding="0">
				<thead>
					<tr>
						<th class="myTab-firstCell_ab"><input type="checkbox"
							class="checkbox" name='allbox' onclick='checkAll(this);' />
						</th>
						<th width="70px"><span>序号</span> 
						<img src="${default_imagepath}/ico_188_list.gif" class="ico_ab ico-188_ab" />
						</th>
						<th width="15%"><span>菜单名称</span>
						</th>
						<th width="15%"><span>菜单代码</span>
						</th>
						
						<th width="40%"><span>菜单URL</span>
						</th>
						<th class="myTab-lastCell_ab"><span>是否显示</span>
						</th>
					</tr>
				</thead>
				<tbody>
					<ww:iterator value="menuResourceList" status="rowstatus">
						<tr>
							<td class="myTab-firstCell_ab">
								<input type="checkbox" class="checkbox" name="_selectitem" onclick='checkOne(allbox);' value="<ww:property value = 'id'/>" />
							</td>
							<td><ww:property value="#rowstatus.index + 1" /></td>
							<TD class="myTab-menuName_ab myTab-menuName-first_ab">
							<img src="${default_imagepath}/ico_176_in.gif" 
									id='<ww:property value="#rowstatus.index + 1" />_in' 
								 	onClick="funBlockOrNone(this, <ww:property value="#rowstatus.index + 1" />)"  />
								<span><ww:property value="menuName" /> </span>
							</td>
							<td class="myTab-markCell_ab"><ww:property value="menuCode" />&nbsp;</td>
							<td class="myTab-markCell_ab"><ww:property value="url" />&nbsp;</td>
							<td class="myTab-lastCell_ab"><ww:if test="isView > 0">
								YES
								</ww:if>
								<ww:else>
								NO
								</ww:else></td>
						</tr>
						
						<ww:iterator value="subMenuResources" status="subrowstatus">
							<tr id="<ww:property value="#rowstatus.index + 1" />_<ww:property value="#subrowstatus.index + 1" />" style="display:none"> 
								<td class="myTab-firstCell_ab">
									<input type="checkbox" class="checkbox" name="_selectitem" onclick='checkOne(allbox);' value="<ww:property value = 'id'/>" />
								</td>
								<td><ww:property value="#subrowstatus.index + 1" /></td>
								<td class="myTab-menuName_ab">&nbsp;&nbsp;
									<img src="${default_imagepath}/<ww:property value = 'icoPath'/>" /> 
									<span><ww:property value="menuName" /></span>
								</td>
								<td class="myTab-markCell_ab"><ww:property value="menuCode" />&nbsp;
								</td>
								<td class="myTab-markCell_ab">
								<ww:if test="url.length()>0">
								<ww:property value="url" />&nbsp;
								</ww:if>
								<ww:elseif test="subUrl.length()>0"><ww:property value="subUrl" />&nbsp;</ww:elseif>
								<ww:else>
								<ww:property value="threedUrl" />&nbsp;
								</ww:else>
								
								</td>
								<td class="myTab-lastCell_ab">
								<ww:if test="isView > 0">
								YES
								</ww:if>
								<ww:else>
								NO
								</ww:else>
								</td>
							</tr>
						</ww:iterator>
					</ww:iterator>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="6">
							<div>
								<img src="${default_imagepath}/info-table-left.gif" class="myTab-foot-left_ab" />
								<img src="${default_imagepath}/info-table-right.gif" class="myTab-foot-right_ab" />
							</div>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</div>
	<!-- 页脚 --> 
</body>
</html>
<script type="text/javascript">

function funBlockOrNone(obj,idInfor) {  
	var flag = 0;
	var temp = obj.src.split('/'); 
	if(temp[temp.length-1]== 'ico_176_in.gif'){
		flag = 1;
		obj.src="${default_imagepath}/ico_175_out.gif";
	}else if(temp[temp.length-1]== 'ico_175_out.gif'){ 
		flag = 2;
		obj.src="${default_imagepath}/ico_176_in.gif";
	}  // alert("======================="+idInfor);
	for(var i=1;i<200;i++){
		var tempObj = document.getElementById(idInfor+"_"+i);
		if(undefined==tempObj || null==tempObj){
			break;
		}
		if(flag==1){
			tempObj.style.display="block";
		} else{ 
			tempObj.style.display="none"; 
		}
	} 
}
	//global_ab.fn.table_fn();
	//global_ab.fn.tableWidthList_fn1();
	//global_ab.init.btnAct_forIe6("li");
</script>