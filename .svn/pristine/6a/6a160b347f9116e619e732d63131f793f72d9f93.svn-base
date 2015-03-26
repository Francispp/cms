<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/template/public.inc"%>			
<html>
<%@ include file="/common/template/head.inc"%><head>
        <title>信息管理</title>
        <%@ include file="/common/cyber_taglibs.inc"%>
<script src="${ctx}/common/cybercms_js/global_ab.js" type="text/javascript"></script>
        <link href="/styles/cybercms/css.css" rel="stylesheet" type="text/css"></link>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<script type="text/javascript" src="/common/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
function deleteItem(id)
  {
  	var ids=id;
  	if(ids || ids==null || ids=='')
 	 ids=getSelectedID();
 	if((ids==null||ids=='')&&Id){
 	  ids=Id;	
 	}
 	 if(ids==null||ids=='')
 	 {
 		alert('请先选择记录！');
 		return ;
 	}
 	if (confirm("您确定要删除选择的记录吗？"))
 	{
		$.get("/base/message!delete.action", {keys:ids},function(data){
			 location.reload();
		});
 	}
  }
</script>
</head>
    <body leftMargin="0" topMargin="0">
        <%@ include file="/common/messages.inc" %>
        <div class="system-header">
        <div class="edit-header-lion">
        <ul id="lion-ul">
            <li class="artEdit-btn_ab artEdit-btn-w3letters_ab"><a class="artEdit-btn-in_ab" href="javascript:deleteItem();"><img alt="" class="ico_ab ico-017_ab" src="/images/cybercms/ico_017_trashFull.gif"></img> <span>删除</span> </a></li>
            <!--<li class="artEdit-btn_ab artEdit-btn-w3letters_ab"><a class="artEdit-btn-in_ab" href="${ctx}/cms/document!adminList.action?channelId=${channelId}&time=new Date()"><img alt="" class="ico_ab ico-019_ab" src="/images/cybercms/ico_019_refresh.gif"></img> <span>刷新</span> </a></li>
        </ul>-->
        </div>
        </div>
        <div class="content">
        <div class="info-box">
        <ec:table action="${ctx}/base/message!list.action" batchUpdate="false" classic="true" csvFileName="信息.csv" editable="false" filterRowsCallback="limit" filterable="true" generateScript="true" items="items" listWidth="100%" minColWidth="80" resizeColWidth="true" retrieveRowsCallback="limit" rowsDisplayed="10" showPrint="true" sortRowsCallback="limit" sortable="true" tableId="${tableId}" useAjax="false" var="item" xlsFileName="信息.xls">
            <tbody>
                <ec:row recordKey="${item.id}" rowId="rowid_${GLOBALROWCOUNT}">
                    <ec:column filterable="false" property="id" sortable="false" style="text-align: center" title="<input type=checkbox name=allbox onclick=checkAll(this) >全选" viewsDenied="xls" width="2%"><input name="_selectitem" ondblclick="checkOne(allbox);" type="checkbox" value="${item.id}"></input></ec:column>
                    <ec:column filterable="false" property="_1" sortable="false" style="text-align: center" title="序号" value="${GLOBALROWCOUNT}"></ec:column>
                    <ec:column filterable="true" property="title" sortable="false" title="评论人"><a target="_blank" href="javascript:location='/base/message!edit.action?id=${item.id}'">${item.face}</a></ec:column>
                    <ec:column filterable="true" property="xinghao" title="评论信息">
                    ${fn:length(item.content) > 10 ? fn:substring(item.content,0,10) : item.content}
					<c:if test="${fn:length(item.content) > 10}">...</c:if>					
                    </ec:column>
                    <ec:column filterable="true" property="cankaojiage" title="IP地址">${item.ipInfo}</ec:column>
                    <ec:column filterable="true" property="timeIssued" title="发布时间" value="${item.createtime}"></ec:column>
                    <ec:column filterable="true" property="issued" sortable="false" title="产品信息" value="${item.productInfo}"></ec:column>
                </ec:row>
            </tbody>
        </ec:table>
        <textarea id="ecs_t_input" style="display: none">								&lt;input type=&quot;text&quot; class=&quot;inputtext&quot; value=&quot;&quot; onblur_fckprotectedatt=&quot;%20onblur%3D%22ECSideUtil.updateEditCell(this)%22&quot; style=&quot;width:100%;&quot; name=&quot;&quot; /&gt;
        </textarea></div>
        </div>
        <%@ include file="/common/foot.inc" %>
        <script>
 function refreshs(){
	 location.reload();
}
 -->
 --></script>
    </body>
</html>