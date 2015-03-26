<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>页面缓存管理</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<script type="text/javascript">


var parameterUrl='&pageStyle=<ww:property value="pageStyle" />';
function addItem(){
  location.href='${ctx}/cms/cmscacheurl!edit.action?id='+parameterUrl;
}
function editItem(oid){
  location.href='${ctx}/cms/cmscacheurl!edit.action?id='+oid+parameterUrl;
}
function deleteItem(){
    var ids=getSelectedID();
    if(ids==null||ids==''){
        alertMessage('请先选择记录！');
        return ;
    }
    if(confirm('您确定要删除选择的记录吗？')){
        $('${tableId}').action = '${ctx}/cms/cmscacheurl!delete.action?keys='+ids;
        $('${tableId}').submit();
    }
}

function cleanup(ids){
    if(ids==null)
    	ids=getSelectedID();
    if(ids==null||ids==''){
        alertMessage('请先选择记录！');
        return ;
    }
    if(confirm('您确定要清除这些缓存吗？')){
        new Ajax.Request('${ctx}/cms/cmscacheurl!cleanup.action?keys='+ids, {
          onComplete: function(transport) {
            if (200 == transport.status){
              alert(transport.responseText);
            }else{
              alert("操作失败！");
            }
          }
        }); 
    }
}
</script>
</head>
<body nowrap topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<!-- 页面标题 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">

	<tr>
		<td bgcolor="#ffffff">
		<div id="titel1">
		<div id="titel1_left">
		<div id="titel1_right">
		<div id="titel1_txt">页面缓存管理</div>
		</div>
		</div>
		</div>
		</td>
	</tr>


  <!-- 操作栏 -->
  <tr>
    <td valign="top">
    <table width="100%" border="0" cellpadding="0" cellspacing="0"
      bgcolor="#FFFFFF">
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
          <cms:auth resCode="SYS_USER_MODI">
            <li id="button"><a href="javascript:addItem();">新增</a></li>
          </cms:auth>
          <cms:auth resCode="SYS_USER_DELETE">
            <li id="button"><a href="javascript:deleteItem();">删除</a></li>
            <li id="button"><a href="javascript:cleanup();">清除</a></li>
          </cms:auth>
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

<tr><td>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr><td align="center" valign="top">
    <c:set var="isEdit" value="false" />
	<cms:auth resCode="CMS_PERMRESOURCE_MANAGER">
	 <c:set var="isEdit" value="true" />
	</cms:auth> 
<ec:table items="items" var="item" action="${ctx}/cms/cmscacheurl!list.action"
	updateAction="${ctx}/cms/cmscacheurl!saveAjax.action"
	deleteAction="${ctx}/cms/cmscacheurl!deleteAjax.action"
	insertAction="${ctx}/cms/cmscacheurl!saveAjax.action"
	batchUpdate="false" xlsFileName="信息.xls" pdfFileName="信息.pdf"
	csvFileName="信息.csv" minColWidth="80" generateScript="true"  editable="${isEdit}"
	classic="true" listWidth="100%" rowsDisplayed="10" tableId="${tableId}"
	showPrint="true" resizeColWidth="true" filterable="true"
	filterRowsCallback="limit" sortRowsCallback="limit"
	retrieveRowsCallback="limit" useAjax="false">
	<ec:row recordKey="${item.oid}" rowId="rowid_${GLOBALROWCOUNT}">
		<ec:column property="_1" 
							title="<input type='checkbox' name='allbox' id='allbox' onclick='checkAll(this);' /><label for='allbox'>全选</label>"
							sortable="false" filterable="false"
							editable="false" width="4%" style="text-align:center;">
							<input type="checkbox" name="_selectitem" value="${item.oid}"
								onclick='checkOne(allbox);' />
						</ec:column>
      <ec:column property="_2" title="序号" width="3%" sortable="false"
            editable="false" filterable="false" value="${GLOBALROWCOUNT}"
            style="text-align:center" />
		<ec:column property="resource" title="资源名称" width="40%"
			sortable="true" filterable="true" editTemplate="ecs_t_input" editable="false" ondblclick="editItem(${item.oid})" style="cursor:hand;"/>
		<ec:column property="includeurl" title="缓存URL" width="40%"
			sortable="true" filterable="true" editTemplate="ecs_t_input" />
		<ec:column property="periods" title="缓存有效时间(s)" width="6%"
			sortable="true" filterable="true" editTemplate="ecs_t_input" />
		<ec:column property="cron" title="缓存定时更新(cron表达式)" width="10%"
			sortable="true" filterable="true" editTemplate="ecs_t_input" />
        <ec:column property="activated" title="是否生效" width="10%"
            sortable="true" filterable="true" editTemplate="ecs_t_actived" value="${item.activated?'有效':'无效'}"/>
        <ec:column property="_3" title="清除缓存" width="3%" sortable="false"
            editable="false" filterable="false" value="<a href='javascript:cleanup(${item.oid });' title='清除缓存'>清除</a>"
            style="text-align:center" />
	</ec:row>
</ec:table>


<!-- 编辑和过滤所使用的 通用的文本框模板 -->
<textarea id="ecs_t_input" rows="" cols="" style="display:none">
				<input type="text" class="inputtext" value=""
	onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="" />
</textarea>
<!-- 新建记录所用模板 -->
<textarea id="add_template" rows="" cols="" style="display:none">
&#160;
<tpsp />
&#160;
<tpsp />
<input type="text" name="resource" class="inputtext" value="" />
<tpsp />
<input type="text" name="includeurl" class="inputtext" value="" />
<tpsp />
<input type="text" name="periods" class="inputtext" value="" />
<tpsp />
<input type="text" name="cron" class="inputtext" value="" />
<tpsp />
&#160;
</textarea>
<textarea id="ecs_t_actived" rows="" cols="" style="display:none">
  <select id="ecs_t_select" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%" name="" value="">
    <option value=""> </option>
    <option value="true">有效</option>
    <option value="false">无效</option>
  </select>
</textarea>

</td></tr></table></td></tr>
</table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
