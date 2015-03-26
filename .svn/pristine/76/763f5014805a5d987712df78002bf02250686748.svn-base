<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<%@ include file="/common/meta.inc"%>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<script type="text/javascript" src="/common/cybercms_js/global_ab.js"></script>
<script type="text/javascript">
//从角色中移除用户
function removeUsersFromRole(){
	return getSelectedID();
}
</script>
</head>
<body style="background:#fff;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<div class="content" style="margin:0px;">
  <div class="info-box" style="margin:0px;">
    <table class="myTab_ab" width="100%" border="0" cellspacing="0" cellpadding="0">
      <thead>
      	<tr>
          <th class="myTab-firstCell_ab">
            <input type="checkbox" class="checkbox" name='allbox' onclick='checkAll(this);'/>
          </th>
          <th width="70px">
            <span>序号</span>
            <img src="${default_imagepath}/ico_188_list.gif" class="ico_ab ico-188_ab" />
          </th>
          <th width="15%">
            <span>登陆账号</span>
          </th>
          <th width="15%">
            <span>英文名</span>
          </th>
          <th width="15%">
            <span>中文名</span>
          </th>
          <th width="15%">
            <span>部门名称</span>
          </th>
          <th class="myTab-lastCell_ab">
            <span>电子邮件</span>
          </th>
        </tr>
      </thead>
      <tbody>
     <ww:iterator status="rowstatus" value="#request._data.result">
        <tr>
          <td class="myTab-firstCell_ab">
            <input type="checkbox" class="checkbox" name="_selectitem" onclick='checkOne(allbox);' value="<ww:property value = 'userid'/>"/>
          </td>
          <td><ww:property value = "#rowstatus.index + 1"/></td>
          <td class="myTab-menuName_ab myTab-menuName-first_ab">
			<span><ww:property value = "loginid"/></span>
          </td>
          <td class="myTab-markCell_ab">
            <ww:property value = "ename"/>
          </td>
          <td class="myTab-markCell_ab">
            <ww:property value = "username"/>
          </td>

           <td class="myTab-lastCell_ab">
            <ww:property value = "deptname"/>
          </td>
            <td class="myTab-lastCell_ab">
            <ww:property value = "hotmail"/>
          </td>
        </tr>
      </ww:iterator>
      <cms:pager style="tablePager"  pageIndex="data.currentPageNo" pageSize="data.pageSize" recordCount="data.totalCount"></cms:pager>
      </tbody>
      <tfoot>
        <tr>
          <td colspan="7">
            <div>
              <img src="${default_imagepath}/info-table-left.gif" class="myTab-foot-left_ab" /><img src="${default_imagepath}/info-table-right.gif" class="myTab-foot-right_ab" />
            </div>
          </td>
        </tr>
      </tfoot>
    </table>
  </div>
</div>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
<script type="text/javascript">
//<![CDATA[
//global_ab.fn.table_fn();
//]]>
</script>