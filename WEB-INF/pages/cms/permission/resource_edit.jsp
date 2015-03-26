<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/common/cyber_taglibs.inc" %>
<%@ include file="/common/buffalo.inc" %>
 <!DOCTYPE HTML>
<html>
  <head>
    <title></title>
    <%@ include file="/common/meta.inc" %>
    <%@ include file="/common/js.inc" %>
    <%@ include file="/common/validation.inc" %>
	<script type='text/javascript' src='/dwr/interface/cmsResourceService.js'></script>
    <link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
      .pw-con-input-item_ab .lab-pw-tit_ab {
          width: 80px;
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
    <script type="text/javascript">
      function save(){
	  	if (valid.validate()){
		  	var resourceCode = document.getElementById("domain.resourceCode").value;
			var id = ${domain.oid} + "";
		  	cmsResourceService.resourceCodeIsUnique(id, resourceCode, {
		    	callback:
				function (args) {
					if (args == 0) {
						alert("权限资源重复,必须唯一!");
						document.getElementById("domain.resourceCode").focus();
						return;	
					}
		  			myform.submit();
				},
		    	errorHandler:
				function(mess){}
			});
		  }
      }
      
    </script>
  </head>
  <body class="pw_ab" style="overflow:hidden;">
    <%@ include file="/common/messages.inc" %>
    <form name="myform" method="post" class="pw-borderOut_ab pw-w417_ab" action="${ctx }/cms/resource!saveOrUpdate.action">
      <ww:hidden name="domain.oid" id="domain.oid" />
      <div class="pw-borderIn_ab">
        <div class="pw-head_ab">
          <div class="pw-head-tit_ab">
            <img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" /><span>
              <ww:if test="domain.oid!=null">编辑权限资源</ww:if>
              <ww:else>新建权限资源</ww:else>
            </span>
          </div>
          <img src="${default_imagepath}/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
        </div>
        <div class="pw-con_ab" style="height:258px;overflow-y:auto;">
          <ul class="pw-con-input_ab" >
            <li class="pw-con-input-item_ab">
              <label class="lab-pw_ab">
                <img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" /><span class="lab-pw-tit_ab">资源编码：</span>
                <ww:textfield id="domain.resourceCode" name="domain.resourceCode" cssClass=" textField_ab textField-w191_ab required max-length-20"></ww:textfield>
              </label>
            </li>
            <li class="pw-con-input-item_ab">
              <label class="lab-pw_ab">
                <img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" /><span class="lab-pw-tit_ab">资源名称：</span>
                <ww:textfield id="domain.resourceName" name="domain.resourceName" cssClass=" textField_ab textField-w191_ab required max-length-24"></ww:textfield>
              </label>
              <div class="fn-clear"></div>
            </li>
            <li class="pw-con-input-item_ab">
              <label class="lab-pw_ab">
                <img src="${default_imagepath}/ico_003_must.gif" class="ico_ab" /><span class="lab-pw-tit_ab">资源类型：</span>
                <ww:select list="trueOfFalseMap1" name="domain.objectType" cssClass="required select_ab select-w193_ab"></ww:select>
              </label>
            </li>
			<li class="pw-con-input-item_ab">
              <label class="lab-pw_ab">
                <img src="${default_imagepath}/ico_003_must.gif" class="ico_ab" /><span class="lab-pw-tit_ab">同级显示：</span>
                <ww:select list="trueOfFalseMap2" name="domain.levelIsView"cssClass="select_ab select-w193_ab"></ww:select>
              </label>
            </li>
			<li class="pw-con-input-item_ab">
              <label class="lab-pw_ab">
                <img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" /><span class="lab-pw-tit_ab">排列：</span>
                <ww:textfield id="domain.orderNo" name="domain.orderNo" cssClass="textField_ab textField-w191_ab validate-number max-value-10000"></ww:textfield>
              </label>
              <div class="fn-clear"></div>
            </li>
			<li class="pw-con-input-item_ab">
              <label class="lab-pw_ab">
                <img src="${default_imagepath}/pic_blank.gif" class="ico_ab ico-003_ab" /><span class="lab-pw-tit_ab">说明：</span>
                <ww:textfield id="domain.remark" name="domain.remark" cssClass=" max-length-40 textField_ab textField-w191_ab"></ww:textfield>
              </label>
              <div class="fn-clear"></div>
            </li>
          <div class="pw-con-btns_ab">
            <input type="button" class="pwSubmit_ab" value="" onclick="save();"/>
            <input type="button" class="pwCancel_ab" value="" onclick="global_ab.fn.popWindow.removePopWindow(false);"/>
          </div>
        </div>
      </div>
    </form>
  </body>

<c:if test="${not empty actionMessages}">
  <c:forEach var="err" items="${actionMessages}">
	   <script>
	  // parent.parent.parent.frames['main_frame'].ECSideUtil.reload('myTable');
	   global_ab.fn.popWindow.removePopWindow(false);
	   </script>
  </c:forEach> 
</c:if>
</html>
<script type="text/javascript">
  //<![CDATA[
  global_ab.init.popwindowPage_fn();
  (function(){
    var closeBtn = document.getElementById("pwClose_ab");
    closeBtn.onclick = function(){
      global_ab.fn.popWindow.removePopWindow(false);
    }
  })();
  
  var valid = new Validation('myform', {
    immediate: true
  });
  //]]>
</script>
