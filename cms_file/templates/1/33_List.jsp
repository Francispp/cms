
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/template/public.inc"%>
<!DOCTYPE html>
			<html>
	<%@ include file="/common/template/head.inc"%><head>
		<title>信息管理</title>
		<%@ include file="/common/cyber_taglibs.inc"%>
		<script src="${ctx}/common/cybercms_js/global_ab.js" type="text/javascript"><!--
 <!--
 -->
 --></script>
		<link href="/styles/cybercms/css.css" rel="stylesheet" type="text/css"></link>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8" /></head>
	<body leftMargin="0" topMargin="0">
		<%@ include file="/common/messages.inc" %>
		<div class="system-header">
			<div class="edit-header-lion">
				<ul id="lion-ul">
					<cms:CmsAuth objectId="${channelId}" objectType="2" resCode="CMS_DOCUMENT_ADD">
						<li class="artEdit-btn_ab artEdit-btn-w3letters_ab">
							<a class="artEdit-btn-in_ab" href="javascript:insertItem('',1);">
								<img alt="" class="ico_ab ico-013_ab" src="/images/cybercms/ico_013_plus.gif"></img>
								<span>新增</span>
							</a>
						</li>
					</cms:CmsAuth>
					<cms:CmsAuth objectId="${channelId}" objectType="2" resCode="CMS_DOCUMENT_MODI">
						<li class="artEdit-btn_ab artEdit-btn-w4letters_ab">
							<a class="artEdit-btn-in_ab" href="javascript:editItems('','',1);">
								<img alt="" class="ico_ab ico-014_ab" src="/images/cybercms/ico_014_textEdit.gif"></img>
								<span>编辑</span>
							</a>
						</li>
					</cms:CmsAuth>
					<cms:CmsAuth objectId="${channelId}" objectType="2" resCode="CMS_DOCUMENT_DELETE||CMS_DOCUMENT_DELETE_AUTHOR">
						<li class="artEdit-btn_ab artEdit-btn-w3letters_ab">
							<a class="artEdit-btn-in_ab" href="javascript:deleteItem();">
								<img alt="" class="ico_ab ico-017_ab" src="/images/cybercms/ico_017_trashFull.gif"></img>
								<span>删除</span>
							</a>
						</li>
					</cms:CmsAuth>
					<cms:CmsAuth objectId="${channelId}" objectType="2" resCode="CMS_DOCUMENT_ISSUE">
						<li class="artEdit-btn_ab artEdit-btn-w3letters_ab">
							<a class="artEdit-btn-in_ab" href="javascript:issueItem();">
								<img alt="" class="ico_ab ico-006_ab" src="/images/cybercms/ico_006_world.gif"></img>
								<span>发布</span>
							</a>
						</li>
					</cms:CmsAuth>
					<cms:CmsAuth objectId="${channelId}" objectType="2" resCode="CMS_DOCUMENT_ISSUE">
						<li class="artEdit-btn_ab artEdit-btn-w5letters_ab">
							<a class="artEdit-btn-in_ab" href="javascript:issueStaticItem();">
								<img alt="" class="ico_ab ico-092_ab" src="/images/cybercms/ico_092_doneSquare.gif"></img>
								<span>重新分发</span>
							</a>
						</li>
					</cms:CmsAuth>
					<cms:CmsAuth objectId="${channelId}" objectType="2" resCode="CMS_DOCUMENT_UNISSUE">
						<li class="artEdit-btn_ab artEdit-btn-w5letters_ab">
							<a class="artEdit-btn-in_ab" href="javascript:unIssueItem();">
								<img alt="" class="ico_ab ico-097_ab" src="/images/cybercms/ico_097_closeSquare.gif"></img>
								<span>撤消发布</span>
							</a>
						</li>
					</cms:CmsAuth>
					
					<cms:CmsAuth objectId="${channelId}" objectType="2" resCode="CMS_DOCUMENT_EXPORT">
						<li class="artEdit-btn_ab artEdit-btn-w3letters_ab">
							<a class="artEdit-btn-in_ab" href="javascript:exportData();">
								<img alt="" class="ico_ab ico-020_ab" src="/images/cybercms/ico_020_upcomingWork1.gif"></img>
								<span>导出</span>
							</a>
						</li>
					</cms:CmsAuth>
					
					<cms:CmsAuth objectId="${channelId}" objectType="2" resCode="CMS_DOCUMENT_IMPORT">
						<li class="artEdit-btn_ab artEdit-btn-w3letters_ab">
							<a class="artEdit-btn-in_ab" href="javascript:importData(${channelId});">
								<img alt="" class="ico_ab ico-020_ab" src="/images/cybercms/ico_020_upcomingWork.gif"></img>
								<span>导入</span>
							</a>
						</li>
					</cms:CmsAuth>
					<li class="artEdit-btn_ab artEdit-btn-w3letters_ab">
						<a class="artEdit-btn-in_ab" href="${ctx}/cms/document!adminList.action?channelId=${channelId}&time=new Date()">
							<img class="ico_ab ico-019_ab" src="/images/cybercms/ico_019_refresh.gif"></img>
							<span>刷新</span> 
						</a>
					</li>
					        
					       
					<li class="fn-clear"></li>
				</ul>
			</div>
		</div>
		<div class="content">
			<div class="info-box">
				<ec:table action="${ctx}/cms/document!adminList.action" batchUpdate="false" classic="true" csvFileName="信息.csv" editable="false" filterRowsCallback="limit" filterable="true" generateScript="true" items="items" listWidth="100%" minColWidth="80" resizeColWidth="true" retrieveRowsCallback="limit" rowsDisplayed="10" showPrint="true" sortRowsCallback="limit" sortable="true" tableId="${tableId}" useAjax="false" var="item" xlsFileName="信息.xls">
					<tbody>
						<ec:row recordKey="${item.id}" rowId="rowid_${GLOBALROWCOUNT}">
							<ec:column filterable="false" property="id" sortable="false" style="text-align: center" title="<input type=checkbox name=allbox onclick=checkAll(this) >全选" viewsDenied="xls" width="45"><input name="_selectitem" ondblclick="checkOne(allbox);" type="checkbox" value="${item.id}"></input></ec:column>
							<ec:column filterable="false" property="_1" sortable="false" style="text-align: center" title="序号" value="${GLOBALROWCOUNT}"></ec:column>

			<ec:column filterable="true" onclick="editItem('${item.id}','',1)" property="title" title="title"></ec:column>

							<ec:column filterable="true" property="issued" title="状态" value="${item.stateName}"></ec:column>
						</ec:row>
					</tbody>
				</ec:table>

				<textarea id="ecs_t_input" style="display: none">								&lt;input type=&quot;text&quot; class=&quot;inputtext&quot; value=&quot;&quot; onblur_fckprotectedatt=&quot;%20onblur%3D%22ECSideUtil.updateEditCell(this)%22&quot; style=&quot;width:100%;&quot; name=&quot;&quot; /&gt;
				</textarea>
			</div>
		</div>
		<%@ include file="/common/foot.inc" %>
		<script><!--
 <!--
 function refreshs(){
				location.reload();
			}
 -->
 --></script>
	</body>
</html>