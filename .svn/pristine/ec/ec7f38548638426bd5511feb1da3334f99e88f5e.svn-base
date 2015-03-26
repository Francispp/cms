<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>外部用户管理</title>
    <%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<script type="text/javascript">
var parameterUrl='&pageStyle=<ww:property value="pageStyle" />';
<!--
function getWebuserid(){
  var ids = '';
  $$('input[name="_selectitem"]').each(function(cb, index){
    if(cb.checked) ids += cb.webuserid + ',';
  });
  return ids.substring(0,ids.length-1);
}

//审核用户
function AuditUsers(){
    var ids=getWebuserid();
    if(ids==null||ids==''){
        alertMessage('请先选择记录！');
        return ;
    }
    if(confirm('确定审核这些用户么？')){
        $('myTable').action = 'webuser!auditUsers.action?keys='+ids;
        $('myTable').submit();
    }
}
//激活/禁用
function lockUsers(islock){
    var ids=getWebuserid();
    if(ids==null||ids==''){
        alertMessage('请先选择记录！');
        return ;
    }
    var mes = islock?'禁用':'激活';
    if(confirm('确定'+mes+'这些用户么？')){
        $('myTable').action = 'webuser!lockUsers.action?keys='+ids+'&islock='+islock;
        $('myTable').submit();
    }
}
function addItem(){
  location.href = 'groupmember!edit.action?channelId=${channelId}'+parameterUrl;
}
function editItem(oid){
  location.href = 'groupmember!edit.action?channelId=${channelId}&id='+oid+parameterUrl;
}
function deleteItem(){
    var ids=getSelectedID();
    if(ids==null||ids==''){
        alertMessage('请先选择记录！');
        return ;
    }
    if(confirm('您确定要删除选择的记录吗？')){
        $('myTable').action = 'groupmember!adminDelete.action?channelId=${channelId}&keys='+ids;
        $('myTable').submit();
    }
}

-->
</script>
  </head>
  <body leftMargin="0" topMargin="0">
    <%@ include file="/common/messages.inc"%>
    <table bgcolor="#FFFFFF" border="0" cellpadding="0" cellspacing="0" width="100%">
      <tbody>
        <tr>
          <td height="1"></td>
        </tr>
        <tr>
          <td bgcolor="#ffffff">
            <div id="titel1">
              <div id="titel1_left">
                <div id="titel1_right">
                  <div id="titel1_txt">
                    <cms:property formatType="" isDate="false" isOffecOcx="false" value="channel.name"></cms:property>
                    信息管理列表
                  </div>
                </div>
              </div>
            </div>
          </td>
        </tr>
        <tr>
          <td>
            <div id="titel">
              <div id="titel_left">
                <div id="titel_right">
                  <div id="nav">
                    <div>
                      <ul>
                        <cms:auth resCode="SYS_USER_MODI">
                          <li id="button"><a href="javascript:addItem();">新增</a></li>
                        </cms:auth>
                        <li id="button"><a href="javascript:AuditUsers();">审核</a></li>
                        <li id="button"><a href="javascript:lockUsers(false);">激活</a></li>
                        <li id="button"><a href="javascript:lockUsers(true);">禁用</a></li>
                        <cms:auth resCode="SYS_USER_DELETE">
                          <li id="button"><a href="javascript:deleteItem();">删除</a></li>
                        </cms:auth>
                      </ul>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
    <ec:table action="${ctx}/cms/groupmember!adminList.action" batchUpdate="false" classic="true"
      csvFileName="信息.csv" editable="false" filterRowsCallback="limit" filterable="true" generateScript="true"
      items="items" listWidth="100%" minColWidth="80" resizeColWidth="true" retrieveRowsCallback="limit"
      rowsDisplayed="10" showPrint="true" sortRowsCallback="limit" sortable="true" tableId="${tableId}"
      useAjax="false" var="item" xlsFileName="信息.xls">
      <tbody>
        <ec:row ondblclick="editItem('${item.id}','${item.flowinfo.activityid}')" recordKey="${item.id}"
          rowId="rowid_${GLOBALROWCOUNT}">
          <ec:column filterable="false" property="id" sortable="false" style="text-align: center"
            title="<input type='checkbox' name='allbox' id='allbox' onclick='checkAll(this);' /><label for='allbox'>全选</label>" viewsDenied="xls" width="2%">
            <input name="_selectitem" ondblclick="checkOne(allbox);" type="checkbox" value="${item.id}" webuserid="${item.webUser.oid}"></input>
          </ec:column>
          <ec:column filterable="false" property="_1" sortable="false" style="text-align: center" title="序号"
            value="${GLOBALROWCOUNT}"></ec:column>
          <ec:column filterable="true" onclick="editItem('${item.id}')" property="webUser.loginname" title="登录名"></ec:column>
          <ec:column filterable="true" onclick="editItem('${item.id}')" property="webUser.name" title="用户名"></ec:column>
          <ec:column property="webUser.approved" title="审核状态" value="${item.webUser.approved?'已审':'未审'}" sortable="true" filterable="true" style="text-align:center" />
          <ec:column filterable="true" onclick="editItem('${item.id}')" property="webUser.locked" value="${item.webUser.locked?'禁用':'激活'}" title="状态"></ec:column>
          
          <ec:column filterable="true" onclick="editItem('${item.id}')" property="fieldString1"
            title="单位名称"></ec:column>
          <ec:column filterable="true" onclick="editItem('${item.id}')" property="fieldString20"
            title="负责人"></ec:column>
          <ec:column filterable="false" onclick="editItem('${item.id}')" property="fieldString24"
            title="负责人电话"></ec:column>
        </ec:row>
      </tbody>
    </ec:table>

    <textarea id="ecs_t_input" style="display: none">								&lt;input type=&quot;text&quot; class=&quot;inputtext&quot; value=&quot;&quot; onblur_fckprotectedatt=&quot;%20onblur%3D%22ECSideUtil.updateEditCell(this)%22&quot; style=&quot;width:100%;&quot; name=&quot;&quot; /&gt;
            </textarea>
    <%@ include file="/common/foot.inc"%>
  </body>
</html>