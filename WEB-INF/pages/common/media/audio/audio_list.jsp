<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<%@ include file="/common/meta.inc"%>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
<script type="text/javascript" src="/common/cybercms_js/global_ab.js"></script>
<script type="text/javascript">
	function getCheck(){
		var _selectitem=document.getElementsByName("_selectitem");
		for(var i=0;i<_selectitem.length;i++){
			if(_selectitem[i].checked){
				return _selectitem[i].value+":"+document.getElementsByName("_selectitemvalue")[i].value;
			}
		}
	}
</script>
</head>
<body>
	<!-- 状态提示栏 -->
	<%@ include file="/common/messages.inc"%>
	<div class="content">
		<div class="info-box">
			
			
 <div class="media-img-div">
    <div class="media-img-box">
     <ec:table items="items" var="item" action="${ctx}/base/audio!listBySite.action" editable="false"
				batchUpdate="false"  minColWidth="80" generateScript="true"
				classic="true" listWidth="100%" rowsDisplayed="10"
				tableId="${tableId}" resizeColWidth="true"
				filterable="false" filterRowsCallback="limit"
				sortRowsCallback="limit" retrieveRowsCallback="limit" useAjax="false">
				<ec:row recordKey="${item.id}" rowId="rowid_${GLOBALROWCOUNT}">
					<ec:column property="_0"
						title="选择"
						viewsDenied="xls" width="4%" style="text-align:center">
						<input type="radio" name="_selectitem" value="${item.id}"/>
						<input type="hidden" name="_selectitemvalue" value="${item.filePath }"/>
					</ec:column>
					<ec:column property="_1" title="序号" width="3%" value="${GLOBALROWCOUNT}"
						style="text-align:center" />
					<ec:column property="title" title="标题" style="text-align:center" >
						</ec:column>
					
					<ec:column property="createTime" title="创建时间" editTemplate="ecs_t_input"
						style="text-align:center" />
					
				</ec:row>
			</ec:table>
    
    
    </div>
</div>
  <div class="media-img-box-bottom">
<img src="${default_imagepath}/info-table-left.gif" style="float:left;"/>
                	<img src="${default_imagepath}/info-table-right.gif" style="float:right;"/>
</div>
</div>

			
			
			
		</div>
	</div>
	<!-- 页脚 -->
	<%@ include file="/common/foot.inc"%>
</body>
</html>