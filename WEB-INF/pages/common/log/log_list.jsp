<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/common/cyber_taglibs.inc" %>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="日志列表" /> <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>${title}</title>
    <%@ include file="/common/meta.inc" %>
    <link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
    <%@ include file="/common/ec/ec.inc" %>
    <%@ include file="/common/js.inc" %>
    <script type="text/javascript">
      function deleteItem(){
        var ids = getSelectedID();
        if (ids == null || ids == '') {
          alertMessage('请先选择记录！');
          return;
        }
        if (confirm('您确定要删除选择的记录吗？')) {
          location.href = 'log!delete.action?keys=' + ids;
        }
      }
      
      function deleteAll(){
        if (confirm('您确定要清空所有日志吗？')) {
          location.href = "log!deleteAll.action";
        }
      }
    </script>
  </head>
  <body nowrap topmargin="0" leftmargin="0">
    <%@ include file="/common/messages.inc" %>
    <div class="system-header">
      <div class="edit-header-lion">
        <ul id="lion-ul">
          <li class="artEdit-btn_ab artEdit-btn-w2letters_ab">
            <a class="artEdit-btn-in_ab" href="#" onclick="deleteItem();"><img src="${default_imagepath}/ico_017_trashFull.gif" class="ico_ab ico-017_ab" /><span>删除</span></a>
          </li>
          <li class="artEdit-btn_ab artEdit-btn-w2letters_ab">
            <a class="artEdit-btn-in_ab" href="#" onclick="deleteAll();"><img src="${default_imagepath}/ico_017_trashFull.gif" class="ico_ab ico-017_ab" /><span>清空</span></a>
          </li>
          <li class="fn-clear"></li>
        </ul>
      </div>
    </div>
    <div class="content">
      <div class="info-box">
        <ec:table tableId="${tableId}" items="items" var="item" action="${ctx}/base/log.action" rowsDisplayed="10" classic="true" doPreload="false" filterable="true" filterRowsCallback="limit" sortRowsCallback="limit" retrieveRowsCallback="limit" useAjax="false">
          <ec:exportXls fileName="exportExcel.xls" tooltip="导出 Excel"/>
		  <ec:row recordKey="${item.oid}" rowId="rowid_${GLOBALROWCOUNT}">
            <ec:column property="groupid" title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />" viewsDenied="xls" sortable="false" filterable="false" width="4%" style="text-align:center">
              <input type="checkbox" name="_selectitem" value="${item.oid}" onclick='checkOne(allbox);'/>
            </ec:column>
            <ec:column property="rowcount" cell="rowCount" sortable="false" title="序号" style="text-align:center" />
            <ec:column property="accesstime" editTemplate="ecs_t_input" title="访问时间" sortable="true"></ec:column>
            <ec:column property="ipaddress" editTemplate="ecs_t_input" title="访问IP" sortable="true" />
            <ec:column property="accessname" editTemplate="ecs_t_input" title="访问者名称" sortable="true" />
            <ec:column property="accessurl" editTemplate="ecs_t_input" title="访问URL" sortable="true" />
            <ec:column property="remark" editTemplate="ecs_t_input" title="备注" />
          </ec:row>
        </ec:table>
        <!-- 编辑和过滤所使用的 通用的文本框模板 -->
			<textarea id="ecs_t_input" rows="" cols="" style="display:none">
				<input type="text" class="inputtext" value=""
					onblur="ECSideUtil.updateEditCell(this)" style="width:100%;"
					name="" />
			</textarea>
      </div>
    </div>
    <!-- 页脚 -->
    <%@ include file="/common/foot.inc" %>
  </body>
</html>
