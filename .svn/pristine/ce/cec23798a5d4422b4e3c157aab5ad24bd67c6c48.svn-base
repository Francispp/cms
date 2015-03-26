<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>   
<c:set var="title" value="问卷问题列表" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>

<%@ include file="/common/ec/ec.inc"%>


<script type="text/javascript">
 var parameterUrl="";
 //编辑
 function editItem(oid){
	 
	 global_ab.fn.popWindow.addPopWindow('/survey/question!edit.action?id='+oid+parameterUrl, '600px', '420px', false);
 
 }
 function deleteItem(){
  var ids=getSelectedID();
  if(ids==null||ids==''){
    alert('请先选择记录！');
    return ;
    }
 if(confirm('您确定要删除选择的记录吗？')){
  location.href='/survey/question!delete.action?keys='+ids;
 }
 }
 
</script>
</head>
<body nowrap topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<!-- 页面标题 -->
<div class="system-header">
  <div class="edit-header-lion">
  <ul id="lion-ul">
    <li class="artEdit-btn_ab artEdit-btn-w2letters_ab">
    
        <a class="artEdit-btn-in_ab" href="javascript:editItem();">
            <img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
            <span>新增</span>
         </a>
        
    </li>
    
   
    <li class="artEdit-btn_ab artEdit-btn-w2letters_ab">
   
          <a class="artEdit-btn-in_ab" href="javascript:deleteItem();">
            <img src="${default_imagepath}/ico_017_trashFull.gif" class="ico_ab ico-017_ab" />
            <span>删除</span>
         </a>
        
    </li>
     <li class="artEdit-btn_ab">
        <a class="artEdit-btn-in_ab" href="${ctx}/survey/question!list.action">
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
					
<ec:table items="items" var="item" action="${ctx}/survey/question!list.action"
    updateAction="${ctx}/survey/question!saveAjax.action"
    deleteAction="${ctx}/survey/question!deleteAjax.action"
    insertAction="${ctx}/survey/question!saveAjax.action"
	batchUpdate="false" xlsFileName="survey.xls" pdfFileName="survey.pdf" editable="false"
	csvFileName="survey.csv" minColWidth="80" generateScript="true"
	classic="true" listWidth="100%" rowsDisplayed="10" tableId="${tableId}"
	showPrint="true" resizeColWidth="true" filterable="true"
	filterRowsCallback="limit" sortRowsCallback="limit"
	retrieveRowsCallback="limit" useAjax="false">
	<ec:row recordKey="${item.id}" rowId="rowid_${GLOBALROWCOUNT}">
		<ec:column  editable="false" property="_1" width="3%" title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />全选" style="text-align:center">
			<input type="checkbox" name="_selectitem" value="${item.id}" onclick='checkOne(allbox);' />
		</ec:column>
		<ec:column editable="false" property="-2" title="序号" width="3%" sortable="false" filterable="false" value="${GLOBALROWCOUNT}" tipTitle="点击查看详细信息" style="text-align:center" />
		<ec:column editable="false" property="content" title="问卷问题" width="*%" sortable="true" filterable="true" editTemplate="ecs_t_input">
			<a href="javascript:editItem(${item.id})">${item.content}</a>
		</ec:column>
        <ec:column editable="false" property="createDate" title="创建时间" width="10%" sortable="true" filterable="true" editTemplate="ecs_t_input" value="${item.createDate}" />
        <ec:column editable="false" property="type" title="问题类型" width="10%" sortable="true" filterable="true" editTemplate="ecs_t_input"  >
        <c:if test="${item.type=='0'}">短答</c:if>
        <c:if test="${item.type=='1'}">单选</c:if>
        <c:if test="${item.type=='2'}">多选</c:if>
        <c:if test="${item.type=='3'}">单选短答</c:if>
        <c:if test="${item.type=='4'}">多选短答</c:if>
        <c:if test="${item.type=='5'}">自由长答</c:if>
        </ec:column>
        
  </ec:row>
</ec:table>



</td></tr></table></td></tr>
</table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</div>
</div>
</body>
</html>
