<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/common/cyber_taglibs.inc" %>
<%@ include file="/common/buffalo.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title></title>
    <%@ include file="/common/meta.inc" %>
    <link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
    <%@ include file="/common/js.inc" %>
    <%@ include file="/common/ec/ec.inc" %>
	<script type="text/javascript" src="/common/cybercms_js/global_ab.js"></script>
    <script type="text/javascript">
    function newCache(){
      global_ab.fn.popWindow.addPopWindow("cms/cmsCache!edit.action", "580px", "307px", false);
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
      global_ab.fn.popWindow.addPopWindow("cms/cmsCache!edit.action?id=" + ids[0], "580px", "307px", false);
    }
	
	function del(){
	var keys="";
	keys=getSelectedID();
	if(keys==null||keys==''){
		alert('请先选择记录！');
		return ;
	}
	if(confirm("确定删除所选记录吗?")){
		location.href = "${ctx}/cms/cmsCache!delete.action?keys=" + keys;	
	}
}
		
      function editItem(id){
        location.href = 'cmsCache!viewAllCache.action?id=' + id;
      }
      
      function initCache(){
        var ids = getSelectedID();
        if (ids == null || ids == '') {
          alert('请先选择记录！');
          return;
        }
        
        var myAjax = new Ajax.Request('/cms/cmsCache!initCache.action', {
          method: 'post',
          parameters: "keys=" + ids,
          onComplete: function(r){
            if (r.responseText == "1") {
              alert("初始化缓存成功!");
            }
            else 
              alert("初始化缓存失败!");
          }
        });
      }
      
      function removeAllCache(){
        var ids = getSelectedID();
        if (ids == null || ids == '') {
          alert('请先选择记录！');
          return;
        }
        
        var myAjax = new Ajax.Request('/cms/cmsCache!removeAllCache.action', {
          method: 'post',
          parameters: "keys=" + ids,
          onComplete: function(r){
            if (r.responseText == "1") {
              alert("清除缓存成功!");
            }
            else 
              alert("清除缓存失败!");
          }
        });
      }
    </script>
  </head>
  <body>
    <!-- 状态提示栏 --><%@ include file="/common/messages.inc" %>
    <div class="system-header">
      <div class="edit-header-lion">
        <ul id="lion-ul">
          <li class="artEdit-btn_ab artEdit-btn-w4letters_ab">
            <a class="artEdit-btn-in_ab" href="javascript:initCache();"><img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab"/><span>重建缓存</span></a>
          </li>
          <li class="artEdit-btn_ab artEdit-btn-w4letters_ab">
            <a class="artEdit-btn-in_ab" href="javascript:removeAllCache();"><img src="${default_imagepath}/ico_017_trashFull.gif" class="ico_ab ico-017_ab" /><span>清除缓存</span></a>
          </li>
          <li class="artEdit-btn_ab artEdit-btn-w4letters_ab">
            <a class="artEdit-btn-in_ab" href="#" onclick="newCache();"><img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab"/><span>添加缓存类型</span></a>
          </li>
          <li class="artEdit-btn_ab">
            <a class="artEdit-btn-in_ab" href="#" onclick="edit();"><img src="${default_imagepath}/ico_014_textEdit.gif" class="ico_ab ico-014_ab" /><span>编辑缓存类型</span></a>
          </li>
		  <li class="artEdit-btn_ab artEdit-btn-w4letters_ab">
            <a class="artEdit-btn-in_ab" href="#" onclick="del();"><img src="${default_imagepath}/ico_017_trashFull.gif" class="ico_ab ico-017_ab" /><span>删除缓存类型</span></a>
          </li>
           <li class="artEdit-btn_ab">
        <a class="artEdit-btn-in_ab" href="javascript:ECSideUtil.reload('myTable');">
            <img src="${default_imagepath}/ico_019_refresh.gif" class="ico_ab ico-019_ab" />
            <span>刷新</span>
         </a>
    </li>
          <li class="fn-clear"></li>
        </ul>
      </div>
    </div>
    <div class="content">
      <div class="info-box">
        <ec:table items="items" var="item" action="${ctx}/cms/cmsCache.action" editable="false" batchUpdate="false" xlsFileName="缓存.xls"  minColWidth="80" generateScript="true" classic="true" listWidth="100%" rowsDisplayed="10" tableId="${tableId}" showPrint="true" resizeColWidth="true" filterable="true" filterRowsCallback="limit" sortRowsCallback="limit" retrieveRowsCallback="limit" useAjax="false">
          <ec:row recordKey="${item.id}" rowId="rowid_${GLOBALROWCOUNT}">
            <ec:column property="id" title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />" style="text-align:center">
              <input type="checkbox" name="_selectitem" value="${item.id}" onclick='checkOne(allbox);'/>
            </ec:column>
            <ec:column property="cacheName" title="缓存名称" width="15%" sortable="true" filterable="true" editTemplate="ecs_t_input" />
            <ec:column property="manageClassPath" title="缓存管理类的bean的id" width="15%" sortable="true" filterable="true" editTemplate="ecs_t_input" />
            <ec:column property="initMethodName" title="初始化缓存的方法名" width="15%" sortable="true" filterable="true" editTemplate="ecs_t_input" />
            <ec:column property="removeAllMethodName" title="清除掉所有缓存的方法名" width="15%" sortable="true" filterable="true" editTemplate="ecs_t_input" />
            <ec:column property="getMethodName" title="获取单个缓存的方法名" width="15%" sortable="true" filterable="true" editTemplate="ecs_t_input" />
            <ec:column property="getAllMethodName" title="获取所有缓存的方法名" width="15%" sortable="true" filterable="true" editTemplate="ecs_t_input" />
            <ec:column property="_1" title="操作" width="3%" sortable="false" editable="false" filterable="false" value="<a href='#' onclick='editItem(${item.id})'>查看</a>" style="text-align:center"/>
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
