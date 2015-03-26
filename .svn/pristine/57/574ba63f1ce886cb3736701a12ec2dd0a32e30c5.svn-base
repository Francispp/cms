<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/common/cyber_taglibs.inc" %>
<%@ include file="/common/buffalo.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title></title>
	<%@ include file="/common/buffalo.inc" %>
    <%@ include file="/common/meta.inc" %>
    <link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
    <%@ include file="/common/js.inc" %>
    <%@ include file="/common/ec/ec.inc" %>
    <script type="text/javascript">
      var parameterUrl = '&pageStyle=<ww:property value="pageStyle" />';
      function addItem(){
        location.href = 'channel!add.action?id=' + parameterUrl;
      }
      
      function editItem(oid){
        location.href = 'channel!edit.action?id=' + oid + parameterUrl;
      }
      
      function deleteItem(){
        var ids = getSelectedID();
        if (ids == null || ids == '') {
          alert('请先选择记录！');
          return;
        }
        if (confirm('您确定要删除选择的记录吗？')) {
          location.href = 'channel!delete.action?keys=' + ids + parameterUrl;
        }
      }
      
      function changeViewStyle(){
        location.href = "channel!list.action?pageStyle=0";
      }
      
      /*生成全站模板静态页面*/
      function publishSite(){
        var siteId = '${loginer.siteId}';
        if (siteId != '0') {
          if (confirm('您确定要执行静态采集整个站点任务吗？')) {
            var obj = new Array;
            obj[0] = siteId;
            ExecuteService("if(reply.getResult()=='true'){alert('成功执行采集任务');}else{ alert('操作失败!');}", 'siteManagerService', 'siteTemplate', obj);
          }
        }
        else 
          alert("请选择站点！");
      }
      
      /*生成频道静态页面*/
      function publishChannel(){
        var ids = getSelectedID();
        if (ids == null || ids == '') {
          alert('请先选择频道！');
          return;
        }
        if (confirm('您确定要执行静态采集频道任务吗？')) {
          var obj = new Array;
          obj[0] = ids;
          ExecuteService("if(reply.getResult()=='true'){alert('成功执行采集任务');}else{ alert('操作失败!');}", 'channelManagerService', 'staticChanels', obj);
        }
      }
      
      /*生成首页模板静态页面*/
      function publishIndex(){
      
        var siteId = '${loginer.siteId}';
        if (siteId != '0') {
          if (confirm('您确定要执行静态采集首页任务吗？')) {
            var obj = new Array;
            obj[0] = siteId;
            ExecuteService("if(reply.getResult()=='true'){alert('成功执行采集任务');}else{ alert('操作失败!');}", 'templateManagerService', 'indexStatic', obj);
          }
        }
        else 
          alert("请选择站点！");
      }
    </script>
  </head>
  <body nowrap topmargin="0" leftmargin="0">
    <!-- 状态提示栏 --><%@ include file="/common/messages.inc" %>
    <!-- 页面标题 -->
    <div class="system-header">
      <div class="edit-header-lion">
        <ul id="lion-ul">
          <ww:if test="#session.loginer.siteId >0">
            <li class="artEdit-btn_ab artEdit-btn-w4letters_ab">
              <a class="artEdit-btn-in_ab" href="javascript:publishSite();"><img src="${default_imagepath}/ico_076_home.gif" class="ico_ab ico-013_ab" /><span>全站采集</span></a>
            </li>
            <li class="artEdit-btn_ab artEdit-btn-w4letters_ab">
              <a class="artEdit-btn-in_ab" href="javascript:publishChannel();"><img src="${default_imagepath}/ico_093_ok.gif" class="ico_ab ico-013_ab" /><span>频道采集</span></a>
            </li>
            <li class="artEdit-btn_ab artEdit-btn-w4letters_ab">
              <a class="artEdit-btn-in_ab" href="javascript:publishIndex();"><img src="${default_imagepath}/ico_170_finishedWork.gif" class="ico_ab ico-013_ab" /><span>首页采集</span></a>
            </li>
          </ww:if>
          <li class="fn-clear"></li>
        </ul>
      </div>
    </div>
    <div class="content">
      <div class="info-box">
        <ec:table items="items" var="item" action="${ctx}/cms/channel.action" batchUpdate="false" minColWidth="80" generateScript="true" classic="true" listWidth="100%" rowsDisplayed="10" tableId="${tableId}" xlsFileName="用户列表.xls" showPrint="true" resizeColWidth="true" filterable="true" filterRowsCallback="limit" sortRowsCallback="limit" retrieveRowsCallback="limit" useAjax="false">
          <ec:row recordKey="${item.id}" rowId="rowid_${GLOBALROWCOUNT}">
            <ec:column property="id" title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />" viewsDenied="xls" sortable="false" filterable="false" width="3%" style="text-align:center">
              <input type="checkbox" name="_selectitem" value="${item.id}" onclick='checkOne(allbox);'/>
            </ec:column>
            <ec:column property="_1" title="序号" width="3%" sortable="false" editable="false" filterable="false" value="${GLOBALROWCOUNT}" style="text-align:center"/>
            <ec:column property="name" title="频道名称" sortable="true" style="text-align:center" editTemplate="ecs_t_input"></ec:column>
            <ec:column property="channelPath" title="频道路径" sortable="true" style="text-align:center" editTemplate="ecs_t_input"></ec:column>
            <ec:column property="parent.name" title="上级频道" editTemplate="ecs_t_input" style="text-align:center" filterable="false"/>
            <ec:column property="timeCreated" title="创建时间" editTemplate="ecs_t_input" style="text-align:center" filterable="false"/>
          </ec:row>
        </ec:table>
        <textarea id="ecs_t_input" rows="" cols="" style="display:none">
          <input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="" />
        </textarea>
      </div>
    </div>
    <!-- 页脚 -->
    <%@ include file="/common/foot.inc" %>
  </body>
</html>
