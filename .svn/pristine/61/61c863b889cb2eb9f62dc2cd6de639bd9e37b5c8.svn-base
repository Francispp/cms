<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/common/cyber_taglibs.inc" %>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="业绩水平列表" /> <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>${title}</title>
    <%@ include file="/common/meta.inc" %>
    <link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
    <%@ include file="/common/js.inc" %>
    <%@ include file="/common/ec/ec.inc" %>
    <script type="text/javascript" src="/common/cybercms_js/global_ab.js"></script>
    <script type="text/javascript">
      function newGrade(){
        global_ab.fn.popWindow.addPopWindow("base/grade!edit.action","500px", "290px", false);
      }
      
      function edit(){
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
        global_ab.fn.popWindow.addPopWindow("base/grade!edit.action?id=" + ids[0],"500px", "290px", false);
      }
      
      function del(){
        var keys = "";
        keys = getSelectedID();
        if (keys == null || keys == '') {
          alert('请先选择记录！');
          return;
        }
        if (confirm("确定删除所选记录吗?")) {
          location.href = "${ctx}/base/grade!delete.action?keys=" + keys;
        }
      }
    </script>
  </head>
  <body nowrap topmargin="0" leftmargin="0">
    <!-- 状态提示栏 --><%@ include file="/common/messages.inc" %>
    <div class="system-header">
      <div class="edit-header-lion">
        <ul id="lion-ul">
        <cms:auth resCode="SYS_LEVEL_MAN">
          <li class="artEdit-btn_ab artEdit-btn-w4letters_ab">
            <a class="artEdit-btn-in_ab" href="#" onclick="newGrade()"><img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" /><span>新建</span></a>
          </li>
          <li class="artEdit-btn_ab">
            <a class="artEdit-btn-in_ab" href="#" onclick="edit();"><img src="${default_imagepath}/ico_014_textEdit.gif" class="ico_ab ico-014_ab" /><span>编辑</span></a>
          </li>
          </cms:auth>
           <li class="artEdit-btn_ab">
        <a class="artEdit-btn-in_ab" href="javascript:location.href='${ctx}/base/grade.action';">
            <img src="${default_imagepath}/ico_019_refresh.gif" class="ico_ab ico-019_ab" />
            <span>刷新</span>
         </a>
    </li>
    <cms:auth resCode="SYS_LEVEL_MAN">
          <li class="artEdit-btn_ab artEdit-btn-w2letters_ab">
            <a class="artEdit-btn-in_ab" href="#" onclick="del()"><img src="${default_imagepath}/ico_017_trashFull.gif" class="ico_ab ico-017_ab" /><span>删除</span></a>
          </li>
          </cms:auth>
          <li class="fn-clear"></li>
        </ul>
      </div>
    </div>
    <div class="content">
      <div class="info-box">
        <ec:table items="items" var="item" action="${ctx}/base/grade!list.action" editable="false" batchUpdate="false" xlsFileName="级别.xls"  minColWidth="80" generateScript="true" classic="true" listWidth="100%" rowsDisplayed="10" tableId="${tableId}" showPrint="true" resizeColWidth="true" filterable="true" filterRowsCallback="limit" sortRowsCallback="limit" retrieveRowsCallback="limit" useAjax="false">
          <ec:row recordKey="${item.gid}" rowId="rowid_${GLOBALROWCOUNT}">
            <ec:column property="_0" title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />" viewsDenied="xls" sortable="false" filterable="false" editable="false" width="4%" style="text-align:center">
              <input type="checkbox" name="_selectitem" value="${item.gid}" onclick='checkOne(allbox);'></ec:column>
            <ec:column property="_1" title="序号" width="3%" sortable="false" editable="false" filterable="false" value="${GLOBALROWCOUNT}" style="text-align:center"/>
            <ec:column property="gnumber" title="级别等级" width="10%" sortable="true" filterable="true" editTemplate="ecs_t_input" />
            <ec:column property="gname" title="级别名称" width="10%" sortable="true" filterable="true" editTemplate="ecs_t_input" />
            <ec:column property="gcode" title="级别代码" width="10%" sortable="true" filterable="true" editTemplate="ecs_t_input" />
            <ec:column property="gremark" title="备注" width="25%" sortable="true" filterable="true" editTemplate="ecs_t_input" />
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
