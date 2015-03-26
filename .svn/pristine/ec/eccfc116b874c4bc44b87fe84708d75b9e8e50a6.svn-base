
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="常用类型数据" />

<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/validation.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>
<script src="${ctx}/common/frame_js/select.js" type="text/javascript"></script>
<script type="text/javascript">
function save(){
if(valid.validate())
 myform.submit();
}
function goBack(){
 location.href='/cms/selectlist!list.action';
}


Ext.onReady(function(){   
  var myempclass = new Array();
  <ww:iterator status="sts" value="typeList" id="type">
    myempclass[${sts.index}] = ["<ww:property value='#type'/>"];
  </ww:iterator>
  //myempclass[0]=["test"];
  //for(var i=0;i<empclassList.length;i++){
  //  myempclass[i] = [empclassList[i]];    
  //}
  var fs=new Ext.form.ComboBox({
            store: new Ext.data.SimpleStore({
                fields: ['type'],
                data : myempclass
            }),
            displayField:'type',
            triggerAction: 'all',
            mode:'local',
            selectOnFocus:true,
            width:135
        });
  fs.applyTo('domain.type');
});
</script>
<ww:head />
</head>
<body topmargin="0" leftmargin="0" style="margin:0px;padding:0px;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<!-- 页面标题 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" >
  
	<!-- 操作栏 -->
	<tr>
		<td valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bgcolor="#FFFFFF">
			<tr>
				<td height="1"></td>
			</tr>
			<tr>
				<td align="center">
				<div id="titel2">
				<div id="titel2_left">
				<div id="titel2_right">
				<div id="nav2">
				<div style="float: left;">
					 <strong class="font4">${title}</strong> 
				</div>
				<div style="float: left;">
				<ul>
					<c:if test="${isEdit==true}">
						<li id="button"><a href="javascript:save()">保存</a></li>
					</c:if>
					<li id="button"><a href="javascript:goBack()">返回</a></li>
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
    <tr>
      <td>
        <table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
          <tr>
            <td align="center" valign="top">
              <form method="post" action="/cms/selectlist!saveOrUpdate.action" name="myform">
              <ww:hidden name="domain.oid" id="domain.oid" />
              <table width="100%" border="0" align="left" cellpadding="0"
                cellspacing="0" class="view_content_table" bgcolor="#dfedef" style="margin-left:1px;">
                <tr>
                  <td width="100%" align="center" class="view_content_td">
                  <table width="100%" border="0" align="left" cellpadding="0"
                    cellspacing="1">
                    <tr>
                      <td align="left" class="inside_list">标&nbsp;&nbsp;&nbsp;&nbsp;题：</td>
                      <td align="left"><ww:textfield name="domain.title" size="78" cssClass="required  max-length-40"/></td>
                    </tr>
                    <tr>
                      <td align="left" class="inside_list">关键字：</td>
                      <td align="left"><ww:textfield name="domain.key" size="78" cssClass="required  max-length-40"/></td>
                    </tr>
                    <tr>
                      <td align="left" class="inside_list">分&nbsp;&nbsp;&nbsp;&nbsp;类：</td>
                      <td align="left"><ww:textfield name="domain.type" size="78" cssClass="max-length-40"/></td>
                    </tr>
                    <tr>
                      <td width="21%" align="left" valign="top" class="inside_list">选&nbsp;&nbsp;&nbsp;&nbsp;项：</td>
                      <td width="79%" align="left">
  <table width="500" border="0" cellspacing="1" cellpadding="0">
    <thead>
    <tr>
      <td width="6%" height="23">&nbsp;</td>
      <td width="48%"><input name="添加" type="button" id="添加" value="＋" onmousedown="addopt();"/>
      <input name="deleteopt" type="button" id="deleteopt" value="－" onclick="delopt()" onmouseover=""/></td>
      <td width="48%"><div align="right">
        <input name="上" type="button" id="" value=" ↑ " onclick="moveSelectedRow(-1)" onmouseover=""/>
        <input name="下" type="button" id="" value=" ↓ " onclick="moveSelectedRow(1)" onmouseover=""/>
      </div></td>
    </tr>
    <tr>
      <td>序号</td>
      <td>值</td>
      <td>选项名</td>
    </tr>
    </thead>
    <tbody id="optbody">
    <ww:iterator value="domain.options" id="opt" status="stat">
    <tr>
      <td>&nbsp;${opt.pos+1 }</td>
      <td><ww:hidden name="optids" id="optids" value="%{opt.oid }" /><input name="optkeys" type="text" onfocus="selectrow(this)" onblur="" value="${opt.key }" style="border:0px;width:100%;"></td>
      <td><input name="optvalues" type="text" onfocus="selectrow(this)" onblur="" value="${opt.value}" style="border:0px;width:100%;"></td>
    </tr>
    </ww:iterator>
    </tbody>
  </table>
                      </td>
                    </tr>
                    <tr>
                      <td width="21%" align="left" valign="top" class="inside_list">备&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
                      <td width="79%" align="left"><ww:textarea
                        name="domain.remark" cols="60" rows="4" cssClass="max-length-200"/></td>
                    </tr>
                  </table>
                  </td>
                </tr>
            
              </table>
              </form>
            </td>
          </tr>
        </table>
      </td>
    </tr>
</table>
<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
<script type="text/javascript">
var valid = new Validation('myform',{immediate:true});
<c:if test="${isEdit!=true}">
 setElementsDisabled(true);
</c:if>


var sectionRowIndex=null;
function addopt(){
  var length=$('optbody').rows.length;
  var newrow = $('optbody').insertRow();
  newrow.insertCell(0).innerHTML='&nbsp;'+(length+1);
  newrow.insertCell(1).innerHTML='<input name="optkeys" type="text" onfocus="selectrow(this)" style="border:0px;width:100%;">';
  newrow.insertCell(2).innerHTML='<input name="optvalues" type="text" onfocus="selectrow(this)" style="border:0px;width:100%;">';
}
function delopt(){
  if(sectionRowIndex==null)return;
  $('optbody').deleteRow(sectionRowIndex);
  sectionRowIndex=null;
  updateNum();
}
function updateNum(){
  var rows = $('optbody').rows;
  for(var i=0;i<rows.length;i++){
    rows[i].cells[0].innerHTML='&nbsp;'+(i+1);
  }
}
function selectrow(inpt){
deselectrow();
  var r=inpt.parentNode.parentNode;
  r.style.backgroundColor='#3089CA';
  sectionRowIndex=r.rowIndex-2;
}
function deselectrow(inpt){
  //var r=inpt.parentNode.parentNode;
  if(sectionRowIndex==null) return;
  var r=$('optbody').rows[sectionRowIndex];
  r.style.backgroundColor='';
  sectionRowIndex=null;
}
//function setSectionRowIndex(){sectionRowIndex=currentRowIndex;}
//function resetSectionRowIndex(){sectionRowIndex=null;}
function moveSelectedRow(target){
  if(sectionRowIndex==null)return;
  $('optbody').moveRow(sectionRowIndex,sectionRowIndex+target);
  sectionRowIndex=sectionRowIndex+target;
}
</script>
