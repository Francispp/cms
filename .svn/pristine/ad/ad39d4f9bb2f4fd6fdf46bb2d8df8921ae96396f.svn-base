<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="抓取任务管理列表" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/TablePager.css" />
<link href="${default_style}" rel="stylesheet" type="text/css" />
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<c:set var="icon_16_actions" value="${ctx}/images/common" />
<script type="text/javascript">	
var parameterUrl="&pageStyle=1";		
    function addItem(){
  location.href='${ctx}/crawl/jobs!newJob.action?id='+parameterUrl;
 }
</script>

</head>
<body nowrap topmargin="0" leftmargin="0">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<!-- 页面标题 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
	<tr>
		<td bgcolor="#ffffff" height="6"></td>
	</tr>
	<tr>
		<td bgcolor="#ffffff">
		<div id="titel1">
		<div id="titel1_left">
		<div id="titel1_right">
		<div id="titel1_txt">${title}</div>
		</div>
		</div>
		</div>
		</td>
	</tr>

	<tr>
		<td valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bgcolor="#FFFFFF">
			<tr>
				<td height="1"></td>
			</tr>
			<tr>
				<td>
				<div id="titel2">
				<div id="titel2_left">
				<div id="titel2_right">
				<div id="nav2">
				<div>
				<ul>
					<cms:auth resCode="SYS_ROLE_MODI">
						<li id="button"><a href="javascript:addItem();">新增</a></li>
					</cms:auth>
					<cms:auth resCode="SYS_ROLE_DELETE">
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
		</table>
		</td>
	</tr>
	
<!-- 页面主要内容 -->

<tr><td>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">

			
			<tr>
				<td align="center" valign="top">

	
     <tr>
     <td width="100%">
      <form styleId="formList" method="post" action="${ctx}/crawl/jobs.action">
<div class="ecSide"  id="myTable_main_content"  style="width:100%;" >
<table id="myTable_table"  border="0"  cellspacing="0"  cellpadding="0"  class="tableRegion"  width="100%"    >
<thead id="myTable_table_head" >
<tr>
<td valign="middle"    width="4%"  class="tableHeader"  onmouseover="ECSideUtil.lightHeader(this,'myTable');"  onmouseout="ECSideUtil.unlightHeader(this,'myTable');"><span class="columnSeparator" >&#160;</span><div class="headerTitle" ><input type='checkbox' name='allbox' onclick='checkAll(this);' />全选</div></td>
<td valign="middle"  columnName="loginid"  sortable="true"  editTemplate="ecs_t_input"  class="tableHeader"  onmouseover="ECSideUtil.lightHeader(this,'myTable');"  onmouseout="ECSideUtil.unlightHeader(this,'myTable');"><span class="columnSeparator" >&#160;</span><div class="headerTitle" >编号</div></td>
<td valign="middle"  columnName="loginid"  sortable="true"  editTemplate="ecs_t_input"  class="tableHeader"  onmouseover="ECSideUtil.lightHeader(this,'myTable');"  onmouseout="ECSideUtil.unlightHeader(this,'myTable');"><span class="columnSeparator" >&#160;</span><div class="headerTitle" >名称</div></td>
<td valign="middle"  columnName="ename"  sortable="true"  editTemplate="ecs_t_input"  class="tableHeader"  onmouseover="ECSideUtil.lightHeader(this,'myTable');"  onmouseout="ECSideUtil.unlightHeader(this,'myTable');"><span class="columnSeparator" >&#160;</span><div class="headerTitle" >状态</div></td>
<td valign="middle"  columnName="username"  sortable="true"  editTemplate="ecs_t_input"  class="tableHeader"  onmouseover="ECSideUtil.lightHeader(this,'myTable');"  onmouseout="ECSideUtil.unlightHeader(this,'myTable');"><span class="columnSeparator" >&#160;</span><div class="headerTitle" >显示XML</div></td>
<td valign="middle"  columnName="deptname"  sortable="true"  editTemplate="ecs_t_input"  class="tableHeader"  onmouseover="ECSideUtil.lightHeader(this,'myTable');"  onmouseout="ECSideUtil.unlightHeader(this,'myTable');"><span class="columnSeparator" >&#160;</span><div class="headerTitle" >查看日志</div></td>
<td valign="middle"  columnName="hotmail"  sortable="true"  editTemplate="ecs_t_input"  class="tableHeader"  onmouseover="ECSideUtil.lightHeader(this,'myTable');"  onmouseout="ECSideUtil.unlightHeader(this,'myTable');"><span class="columnSeparator" >&#160;</span><div class="headerTitle" >删除</div></td>


</tr>
	</thead>
	<tbody id="myTable_table_body" >
	<ww:iterator status="rowstatus" value="#request.pendingJobs">
		<tr id="rowid_1"  class="odd "   onmouseover="ECSideUtil.lightRow(this,'myTable');"  onmouseout="ECSideUtil.unlightRow(this,'myTable');"    recordKey="0" >
<td style="text-align:center"  width="4%"   >
<input type="checkbox" name="_selectitem" value="<ww:property value="UID"></ww:property>"  onclick='checkOne(allbox);'/>
                        </td>
<td style="text-align:center"   ><ww:property value="UID" escape="true"></ww:property></td>
<td style="text-align:center"   ><ww:property value="jobName" escape="true"></ww:property></td>
<td style="text-align:center"   ><ww:property value="status" escape="true"></ww:property></td>
<td style="text-align:center"   > <a target="_blank" href="${ctx}/crawl/jobs!viewOrder.action?jobUID=<ww:property value="UID"></ww:property>">显示XML</a></td>
<td style="text-align:center"   >提取条件</td>
<td style="text-align:center"   >删除</td>
</tr>
</ww:iterator>  	
	<ww:iterator status="rowstatus" value="#request.completedJobs">
		<tr id="rowid_1"  class="odd "   onmouseover="ECSideUtil.lightRow(this,'myTable');"  onmouseout="ECSideUtil.unlightRow(this,'myTable');"    recordKey="0" >
<td style="text-align:center"  width="4%"   >
<input type="checkbox" name="_selectitem" value="<ww:property value="UID"></ww:property>"  onclick='checkOne(allbox);'/>
                        </td>
<td style="text-align:center"   ><ww:property value="UID" escape="true"></ww:property></td>
<td style="text-align:center"   ><ww:property value="jobName" escape="true"></ww:property></td>
<td style="text-align:center"   ><ww:property value="status" escape="true"></ww:property></td>
<td style="text-align:center"   > <a target="_blank" href="${ctx}/crawl/jobs!viewOrder.action?jobUID=<ww:property value="UID"></ww:property>">显示</a></td>
<td style="text-align:center"   ><a href="${ctx}/crawl/extract!list.action?jobId=<ww:property value="oid"></ww:property>">查看</a></td>
<td style="text-align:center"   ><a href="${ctx}/crawl/console!deleteJob.action?jobUID=<ww:property value="UID"></ww:property>">删除</a></td>
</tr>
</ww:iterator>  	
	</tbody>
</table>
<div id="myTable_toolbar"  class="toolbar"  style="width:100%;" >
	<table id="myTable_toolbarTable"  class="toolbarTable"  cellpadding="0"  cellspacing="0" >
	<tr>
	<td class="pageNavigationTool"  nowrap="nowrap" >
	<input type="button"  disabled="disabled"  class="pageNav firstPageD"  onclick="ECSideUtil.gotoPage(1,'myTable');"  title="第一页" /><input type="button"  disabled="disabled"  class="pageNav prevPageD"  onclick="ECSideUtil.gotoPage(0,'myTable');"  title="上一页" /></td><td class="pageNavigationTool"  nowrap="nowrap" ><input type="button"  disabled="disabled"  class="pageNav nextPageD"  onclick="ECSideUtil.gotoPage(2,'myTable');"  title="下一页"  disabled="disabled" /><input type="button"  disabled="disabled"  class="pageNav lastPageD"  onclick="ECSideUtil.gotoPage(1,'myTable');"  title="最末页"  disabled="disabled" />
	</td>


<td class="separatorTool" >&#160;</td>


<td nowrap="nowrap"  class="statusTool" >&nbsp;</td>
</tr>
	</table>
</form>

        </td>
        </tr>
        <tr><td>&nbsp;</td></tr>
               			

</td></tr></table></td></tr>
</table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
<script language="javascript">
 dyniframesizeforall("iframe_users");
 setParentFrameSize();
</script>
