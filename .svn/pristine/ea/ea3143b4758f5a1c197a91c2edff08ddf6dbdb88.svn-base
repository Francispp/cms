<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/common/cyber_taglibs.inc" %>
<%@ include file="/common/buffalo.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title></title>
    <%@ include file="/common/meta.inc" %>
	<%@ include file="/common/buffalo.inc"%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
    <%@ include file="/common/js.inc" %>
    <%@ include file="/common/ec/ec.inc" %>
    <script>
      //新增
      function insertItem(){
		global_ab.fn.popWindow.addPopWindow("${ctx}/cms/siteDistribution!edit.action", "500px", "338px", false);
      }
      
      //修改
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
		global_ab.fn.popWindow.addPopWindow("${ctx}/cms/siteDistribution!edit.action?id=" + ids[0],  "500px", "338px", false);
      }
      
      //删除
      function deleteItem(){
        var ids = getSelectedID();
        var siteId = document.getElementById("siteId").value;
        if (ids == null || ids == '') {
          alert('请先选择记录！');
          return;
        }
        if (confirm('您确定要删除选择的记录吗？')) {
          location.href = 'siteDistribution!delete.action?keys=' + ids;
        }
      }
      
      //分发
      function distribution(){
      
        var ids = getSelectedID();
        var siteId = document.getElementById("siteId").value;
        if (ids == null || ids == '') {
          alertMessage('请先选择记录！');
          return;
        }
        if (confirm('您确定要分发选择的记录吗？')) {
          var obj = new Array;
          obj[0] = ids + "";
          obj[1] = siteId + "";
          ExecuteService("if(reply.getResult()=='false'){ alert('分发失败!');}else{alert('分发成功!');}", 'siteDistributionService', 'distribution', obj);
        }
      }
      
      //撤销分发
      function deleteDistribution(){
      
        var ids = getSelectedID();
        var siteId = document.getElementById("siteId").value;
        if (ids == null || ids == '') {
          alertMessage('请先选择记录！');
          return;
        }
        if (confirm('您确定要撤销选择的记录吗？')) {
          var obj = new Array;
          obj[0] = ids + "";
          obj[1] = siteId + "";
          ExecuteService("if(reply.getResult()=='false'){alert('撤销分发失败!')}else{ alert('撤销分发成功!');}", 'siteDistributionService', 'deleteDistribution', obj);
        }
      }
      
      //查看分发记录
      function selectLog(id, dtype){
        var siteId = document.getElementById("siteId").value;
        location.href = '${ctx}/cms/distributionLog.action?id=' + id + "&siteId=" + siteId + "&dtype=" + dtype + "&myTable_s_lastTime=desc&myTable_s_status=desc";
      }
	  
    </script>
  </head>
  <body nowrap topmargin="0" leftmargin="0">
    <!-- 状态提示栏 --><%@ include file="/common/messages.inc" %>
    <div class="system-header">
      <div class="edit-header-lion">
        <ul id="lion-ul">
          <ww:if test="#session.loginer.siteId >0">
           <li class="artEdit-btn_ab artEdit-btn-w2letters_ab">
            <a class="artEdit-btn-in_ab" href="javascript:insertItem();"><img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" /><span>新增</span></a>
          </li>
          <li class="artEdit-btn_ab artEdit-btn-w4letters_ab">
            <a class="artEdit-btn-in_ab" href="javascript:distribution();"><img src="${default_imagepath}/ico_092_doneSquare.gif" class="ico_ab ico-013_ab" /><span>选择分发</span></a>
          </li>
          <li class="artEdit-btn_ab artEdit-btn-w4letters_ab">
            <a class="artEdit-btn-in_ab" href="javascript:deleteDistribution();"><img src="${default_imagepath}/ico_097_closeSquare.gif" class="ico_ab ico-013_ab" /><span>撤销分发</span></a>
          </li>
         
		  <li class="artEdit-btn_ab">
            <a class="artEdit-btn-in_ab" href="#" onclick="edit();"><img src="${default_imagepath}/ico_014_textEdit.gif" class="ico_ab ico-014_ab" /><span>编辑</span></a>
          </li>
            <li class="artEdit-btn_ab">
        <a class="artEdit-btn-in_ab" href="javascript:ECSideUtil.reload('myTable');">
            <img src="${default_imagepath}/ico_019_refresh.gif" class="ico_ab ico-019_ab" />
            <span>刷新</span>
         </a>
    </li>
          <li class="artEdit-btn_ab artEdit-btn-w2letters_ab">
            <a class="artEdit-btn-in_ab" href="javascript:deleteItem();"><img src="${default_imagepath}/ico_017_trashFull.gif" class="ico_ab ico-017_ab" /><span>删除</span></a>
          </li>
           </ww:if>
          <li class="fn-clear"></li>
        </ul>
      </div>
    </div>
    <div class="content">
      <div class="info-box">
        <ww:hidden name="siteId"></ww:hidden>
        <ec:table items="items" var="item" action="${ctx}/cms/siteDistribution.action" batchUpdate="false" xlsFileName="站点分发管理列表.xls"  minColWidth="80" generateScript="true" classic="true" listWidth="100%" rowsDisplayed="10" tableId="${tableId}" showPrint="true" resizeColWidth="true" filterable="false" filterRowsCallback="limit" sortRowsCallback="limit" retrieveRowsCallback="limit" useAjax="false">
          <ec:row recordKey="${item.id}" rowId="rowid_${GLOBALROWCOUNT}">
            <ec:column property="_0" title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />" viewsDenied="xls" sortable="false" filterable="false" editable="false" width="4%" style="text-align:center">
              <input type="checkbox" name="_selectitem" value="${item.id}" onclick='checkOne(allbox);'></ec:column>
            <ec:column property="_1" title="序号" width="3%" sortable="false" editable="false" filterable="false" value="${GLOBALROWCOUNT}" style="text-align:center"/>
            <ec:column property="dtypeName" title="分发类型" />
            <ec:column property="remark" title="备注" />
            <ec:column property="dlastTime" title="操作" style="text-align:center">
              <a href="#" onclick="selectLog(${item.id},'${item.dtype}')">查看分发记录</a>
            </ec:column>
          </ec:row>
        </ec:table>
      </div>
    </div>
    <!-- 页脚 -->
    <%@ include file="/common/foot.inc" %>
  </body>
</html>
