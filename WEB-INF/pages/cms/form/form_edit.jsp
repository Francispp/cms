<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc" %>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="表单信息" />
 <!DOCTYPE HTML>
<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
   
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/validation.inc"%>
<script src="${ctx}/common/frame_js/select.js" type="text/javascript"></script>
 <script type="text/javascript" src="/common/cybercms_js/global_ab.js"></script>
 <link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript">
function save(){
if(valid.validate())
 myform.submit();
}
function goBack(){
 location.href='form.action';
}
function exportFields(){
 if('${domain.oid}'!=''){
   if(confirm('原字段会被清除，您确定要重新导入当前表单的字段吗？')){
    document.getElementById("isCreateField").value='true';
    save(); 
  }
 }else
  save();
}
</script>
  <style type="text/css">
      .pw-con-input-item_ab .lab-pw-tit_ab {
          width: 100px;
          display: block;
          float: left;
          text-align: right;
          height: 22px;
          line-height: 22px;
          color: #333;
      }
      
      .pw-w417_ab {
          width: 500px;
      }
      
      .pw-con-input_ab {
          width: 420px;
          margin: 0 auto;
      }
    </style>
<ww:head />
</head>
<body class="pw_ab" style="margin:0px;padding:0px;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
 <form name="myform" method="post" class="pw-borderOut_ab pw-w417_ab" action="${ctx }/form/form!saveOrUpdate.action">
     <ww:hidden name="domain.oid" id="domain.oid" /> <ww:hidden
			name="domain.isCreateField" id="isCreateField" />
      <div class="pw-borderIn_ab">
        <div class="pw-head_ab">
          <div class="pw-head-tit_ab">
            <img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" /><span>
              <ww:if test="domain.oid!=null">编辑表单</ww:if>
              <ww:else>新建表单</ww:else>
            </span>
          </div>
          <img src="${default_imagepath}/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
        </div>
        <div class="pw-con_ab">
          <ul class="pw-con-input_ab">
            <li class="pw-con-input-item_ab">
              <label class="lab-pw_ab">
                <img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" /><span class="lab-pw-tit_ab">表单代码：</span>
                <ww:textfield id="domain.formCode" name="domain.formCode" cssClass="textField_ab textField-w191_ab required max-length-36"></ww:textfield>
              </label>
            </li>
            <li class="pw-con-input-item_ab">
              <label class="lab-pw_ab">
                <img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" /><span class="lab-pw-tit_ab">表单名称：</span>
                <ww:textfield id="domain.formName" name="domain.formName" cssClass="textField_ab textField-w191_ab required max-length-24"></ww:textfield>
              </label>
              <div class="fn-clear"></div>
            </li>
            <li class="pw-con-input-item_ab">
              <label class="lab-pw_ab">
                <img src="${default_imagepath}/pic_blank.gif" class="ico_ab" /><span class="lab-pw-tit_ab">表单类型：</span>
                <ww:select list="formTypes" name="domain.formType" id="domain.formType"  cssClass="select_ab select-w193_ab max-length-40" ></ww:select>
              </label>
            </li>
            <li class="pw-con-input-item_ab">
              <label class="lab-pw_ab">
                <img src="${default_imagepath}/pic_blank.gif" class="ico_ab ico-003_ab" /><span class="lab-pw-tit_ab">pojo对象：</span>
                <c:choose>
						<c:when test="${domain.oid!=null}">
							  <ww:textfield id="domain.pojoName" name="domain.pojoName" readonly="true" cssClass="textField_ab textField-w191_ab required max-length-80"></ww:textfield>
						</c:when>
						<c:otherwise>
							  <ww:textfield id="domain.pojoName" name="domain.pojoName" cssClass="textField_ab textField-w191_ab required max-length-80"></ww:textfield>
						</c:otherwise>
					</c:choose>
					
              
              </label>
              <div class="fn-clear"></div>
            </li>
            <li class="pw-con-input-item_ab">
              <label class="lab-pw_ab">
                <img src="${default_imagepath}/pic_blank.gif" class="ico_ab" /><span class="lab-pw-tit_ab">备注：</span>
                <ww:textfield id="domain.remark" name="domain.remark" cssClass="textField_ab textField-w191_ab  max-length-120"></ww:textfield>
              </label>
              <div class="fn-clear"></div>
            </li>
           
          </ul>
          <div class="pw-con-btns_ab">
            <input type="button" class="pwSubmit_ab" value="" onclick="save();"/>
            <input type="button" class="pwCancel_ab" value="" onclick="global_ab.fn.popWindow.removePopWindow(false);"/>
          </div>
        </div>
      </div>
    </form>
 <c:if test="${not empty actionMessages}">
  <c:forEach var="err" items="${actionMessages}">
	   <script>global_ab.fn.popWindow.removePopWindow(false);</script>
  </c:forEach> 
</c:if>

<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>
<script type="text/javascript">
var valid = new Validation('myform',{immediate:true});
<c:if test="${isEdit!=true}">
 setElementsDisabled(true);
</c:if>
</script>
<script type="text/javascript">
  //<![CDATA[
  global_ab.init.popwindowPage_fn();
  (function(){
    var closeBtn = document.getElementById("pwClose_ab");
    closeBtn.onclick = function(){
      global_ab.fn.popWindow.removePopWindow(false);
    }
  })();
  
  //]]>
</script>
