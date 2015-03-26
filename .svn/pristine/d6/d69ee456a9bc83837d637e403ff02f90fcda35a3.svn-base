<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="问卷列表" />   
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<script src="${ctx}/common/datepicker/WdatePicker.js" type="text/javascript"></script>
<%@ include file="/common/ec/ec.inc"%>

<script type="text/javascript">			
var parameterUrl="";
function editItem(oid){
	window.location.href='/survey/questionnaire!edit.action?id='+oid+parameterUrl;
}
function addItem(){
	global_ab.fn.popWindow.addPopWindow('survey/questionnaire!add.action', '620px', '430px', false);
}
function deleteItem(qnid){
	var ids=getSelectedID();
	if((ids==null||ids=='') && qnid)
		 ids = qnid;
	if(ids==null||ids==''){
	   alert('请先选择记录！');
	   return ;
	}
	if(confirm('您确定要删除选择的记录吗？')){
		var url = '/survey/questionnaire!delete.action';
		new Ajax.Request(url, { method:'post', parameters: {keys:ids},
			onSuccess: function(transport){alert('已成功删除！');window.location.reload();}
		});
	}
}
//复制
function copy(oid){
	if(confirm("您确认要复制一份同样的问卷吗？")){
		var url = '/survey/questionnaire!copy.action';
		new Ajax.Request(url, { method:'post', parameters: {id:oid},
			onSuccess: function(transport){alert('成功复制了一份问卷！');window.location.reload();}
		});
	}
}
//发布
function publishing(qnid)
{
  var url = '/survey/questionnaire!publishing.action';
  new Ajax.Request(url, { method:'post', parameters: {id:qnid, 'domain.activated':'1'},
		onSuccess: function(transport){alert('发布成功！');location.reload();}
  });
}
//停止发布
function CancelPublishing(qnid)
{
  var url = '/survey/questionnaire!publishing.action';
  new Ajax.Request(url, { method:'post', parameters: {id:qnid, 'domain.activated':'-1'},
    onSuccess: function(transport){alert('已停止发布问卷！');location.reload();}
  });
}
</script>
</head>
<body nowrap topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<div class="system-header">
  <div class="edit-header-lion">
  <ul id="lion-ul">
    <li class="artEdit-btn_ab artEdit-btn-w2letters_ab">
        <a class="artEdit-btn-in_ab" href="javascript:addItem();">
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
        <a class="artEdit-btn-in_ab" href="${ctx}/survey/questionnaire!list.action">
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
<ec:table items="items" var="item" action="${ctx}/survey/questionnaire!list.action"
    updateAction="${ctx}/survey/questionnaire!saveAjax.action"
    deleteAction="${ctx}/survey/questionnaire!deleteAjax.action"
    insertAction="${ctx}/survey/questionnaire!saveAjax.action"
	batchUpdate="false" xlsFileName="survey.xls" pdfFileName="survey.pdf" editable="false"
	csvFileName="survey.csv" minColWidth="80" generateScript="true"
	classic="true" listWidth="100%" rowsDisplayed="10" tableId="${tableId}"
	showPrint="true" resizeColWidth="true" filterable="true"
	filterRowsCallback="limit" sortRowsCallback="limit"
	retrieveRowsCallback="limit" useAjax="false">
	<ec:row recordKey="${item.id}" rowId="rowid_${GLOBALROWCOUNT}">
		<ec:column  editable="false" property="_1" width="45" title="<input type='checkbox' name='allbox' onclick='checkAll(this);' />全选" style="text-align:center">
			<input type="checkbox" name="_selectitem" value="${item.id}" onclick='checkOne(allbox);' />
		</ec:column>
		<ec:column editable="false" onclick="editItem('${item.id}')" property="_2" title="序号" width="40" sortable="false" filterable="false" value="${GLOBALROWCOUNT}" tipTitle="点击查看详细信息" style="text-align:center" />
		<ec:column editable="false" property="name" title="调查主题" width="50%" sortable="true" filterable="true" editTemplate="ecs_t_input">
		<a href="javascript:editItem('${item.id}')">${item.name}</a>
		</ec:column>
		<ec:column editable="false" property="publishDate" title="开始时间" width="10%" sortable="true" filterable="true" editTemplate="ecs_t_select_date" value="${item.publishDate}"/>
		<ec:column editable="false" property="cutoffDate" title="结束时间" width="10%" filterable="true" editTemplate="ecs_t_select_date" value="${item.cutoffDate}"/>
		<ec:column editable="false" property="activated" title="状态" width="5%" filterable="true" editTemplate="ecs_t_select_activated">
			<ww:if test="item.activated=='0'">草稿</ww:if><ww:elseif test="item.activated=='1'">已发布</ww:elseif><ww:else>停止发布</ww:else>
		</ec:column>
        <ec:column editable="false" property="anonymity" title="可匿名答卷" width="5%" filterable="true" editTemplate="ecs_t_select">
			${item.anonymity=='0' ? '否' : '是'}
		</ec:column>
        <ec:column editable="false" property="_3" title="操作" width="*%" sortable="false" filterable="false">
            <a href="${ctx }/survey/questionnaire!survey.action?id=${item.id }" target="_blank"><img src="/images/cybercms/ico_006_world.gif" height="13" title="预览问卷"/></a>
			<ww:if test="%{item.activated=='0'}">
				<a href="javascript:javascript:publishing('${item.id}');"><img src="/images/cybercms/ico_171_feed.gif" title="发布"/></a>
			</ww:if>
			<ww:elseif test="%{item.activated=='1'}">
				<a href="javascript:javascript:CancelPublishing('${item.id}');"><img src="/images/cybercms/ico_021_busy.gif" title="停止发布"/></a>
			</ww:elseif>
			<a href="${ctx }/survey/questionnaire!result.action?id=${item.id }" target="_blank"><img src="/images/cms/poll.gif" title="查看统计结果"/></a>
			<a href="javascript:javascript:copy('${item.id}');"><img src="/images/cybercms/ico_173_oldVersion.gif" title="复制"/></a>
			<a href="javascript:deleteItem('${item.id}');"><img src="/images/cybercms/ico_156_recycleBinEmpty.gif" title="删除"/></a>
        </ec:column>
  </ec:row>
</ec:table>

<!-- 编辑和过滤所使用的 通用的文本框模板 -->
<textarea id="ecs_t_input" rows="" cols="" style="display:none">
	<input type="text" class="inputtext" onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="" value=""/>
</textarea>
<textarea id="ecs_t_select" rows="" cols=""style="display:none">
    <select style="width:60px;" onblur="ECSideUtil.updateEditCell(this)">
      <option value="1">是</option>
      <option value="0">否</option>
    </select>
</textarea>
<textarea id="ecs_t_select_activated" rows="" cols=""style="display:none">
    <select style="width:60px;" onblur="ECSideUtil.updateEditCell(this)">
		<option value="0">草稿</option>
		<option value="1">已发布</option>
		<option value="-1">已停止发布</option>
    </select>
</textarea>
<textarea id="ecs_t_select_date" rows="" cols="" style="display:none">
    <input type='text' class="inputtext" style="width:100px;" name="" value="" id="select_date" onfocus="WdatePicker({vel:this,dateFmt:'yyyy-M-d'})"/>
</textarea>
<!-- 新建记录所用模板 -->
<textarea id="add_template" rows="" cols="" style="display:none">
    &#160;
    <tpsp />
    &#160;
    <tpsp />
    <input type="text" name="name" class="inputtext" value="" />
    <tpsp />
    <input type='text' name="publishDate" class="inputtext" value="" style="width:100px;" id="publishDate" onfocus="WdatePicker({vel:this,dateFmt:'yyyy-M-d'})"/>
    <tpsp />
	<input type='text' name="cutoffDate" class="inputtext" value="" style="width:100px;" id="cutoffDate" onfocus="WdatePicker({vel:this,dateFmt:'yyyy-M-d'})"/>
    <tpsp />
    <select name="activated" style="width:60px;">
      	<option value="0">草稿</option>
		<option value="1">已发布</option>
		<option value="-1">已停止发布</option>
    </select>
    <tpsp />
    <select name="anonymity" style="width:60px;">
      <option value="1">是</option>
      <option value="0">否</option>
    </select>
    <tpsp />
    <input type="text" name="remark" class="inputtext" value="" />
    <tpsp />
</textarea>
</td></tr></table></td></tr>
</table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</div></div>
</body>
</html>
