<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<html>
	<head>
		<title>公共选择链接</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="${default_style}" rel="stylesheet" type="text/css">
		<%@ include file="/common/js.inc"%>
		<%@ include file="/common/ec/ec.inc"%>
	</head>
	<script type="text/javascript">
    
    function getFunctions(v){
      v = v.options[v.options.selectedIndex].value
      $('domain.type').value = v;
      window.name='mod_win';
      $('funcList').target='mod_win';
      $('funcList').submit();
    }
    
    function selectJSComponent(oid){
        var url = '${request.contextPath}/component/jsfunction!selectJSComponent.action';
        var pars = 'domain.oid=' + oid;
    	var myAjax = new Ajax.Request(
                    url,
                    {method: 'post', parameters: pars, onComplete: responseComplete}
                    );
    }
    
    function responseComplete(data){
    //alert(data.status);
    //alert(data.responseText);
    window.returnValue = data.responseText;
    window.close();
    }
    
//window.top.moveTo(screen.width/2-document.body.clientWidth/2,screen.height/2-document.body.clientHeight/2);
	</script>
	<body style="margin: 10px;">
		<div align="center" style="padding: 0px;">
			<form method="post" name="funcList" action="jsfunction!selectJSComList.action">
			<input type="hidden" name="domain.type"/>
				<div align="left" style="background: #ddd;font-size: medium;font-weight: bold;">请选择类型:<select size="1" name="sel" onchange="getFunctions(this)">
				<option value="">- 全部 -</option>
				<ww:iterator value="typeList" id="type" status="status">
				  <option value="${type }" <ww:if test="%{type == domain.type}">selected</ww:if>>${type }</option>
				</ww:iterator>
				</select>
				</div>
	</form>
<ec:table items="items" var="item" action="${ctx}/component/jsfunction!selectJSComList.action"
 editable="false"
	batchUpdate="false" xlsFileName="JS组件信息.xls" pdfFileName="JS组件信息.pdf"
	csvFileName="JS组件信息.csv" minColWidth="80" generateScript="true"
	classic="true" listWidth="100%" rowsDisplayed="10" tableId="${tableId}"
	showPrint="true" resizeColWidth="true" filterable="true"
	filterRowsCallback="limit" sortRowsCallback="limit"
	retrieveRowsCallback="limit" useAjax="false">
	<ec:row recordKey="${item.oid}" rowId="rowid_${GLOBALROWCOUNT}">
		<ec:column property="_1" title="序号" width="3%" sortable="false"
			editable="false" filterable="false"
			value="${GLOBALROWCOUNT}"
			style="text-align:center" />
		<ww:if test="%{empty domain.type}">
		<ec:column property="f_type" title="类型" width="40%"
			sortable="false" filterable="true" editTemplate="ecs_t_input" 
			value="${item.type}"  onclick="selectJSComponent(${item.oid})"/>
		</ww:if>
		<ec:column property="functionName" title="功能" width="57%"
			sortable="true"  filterable="true" editTemplate="ecs_t_input" 
			value="${item.functionName}"  onclick="selectJSComponent(${item.oid})"/>
	</ec:row>
</ec:table>
			
		</div>
	</body>
</html>
