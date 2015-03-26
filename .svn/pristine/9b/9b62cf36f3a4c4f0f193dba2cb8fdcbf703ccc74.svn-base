<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/common/cyber_taglibs.inc" %>
<%@ include file="/common/buffalo.inc" %>
 <!DOCTYPE HTML>
<html>
 <head>
<title></title>
<%@ include file="/common/meta.inc" %>
<script type="text/javascript" src="/common/cybercms_js/global_ab.js"></script>
<%@ include file="/common/js.inc" %>
<%@ include file="/common/validation.inc" %>
<script type='text/javascript' src='/dwr/interface/TemLibraryService.js'></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>

<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
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
<script type="text/javascript">
	function save(){
	  	var id = document.getElementById("domain.id").value;
	  	var name = document.getElementById("domain.name").value;
		var valid = new Validation('myform',{immediate:true});
		if(valid.validate()){
			TemLibraryService.dwrTemLibraryValidateKeywordUnique(id,name,
				function(data){ 
					if (data == "0") {
						alert("类别名重复,必须唯一!");
						document.getElementById("domain.name").focus();
 						return;	
 					}
		  			myform.submit();
 				} 
 			); 
		}
     } 
</script>
  </head>
  
<body class="pw_ab" style="overflow:hidden;padding:0px;margin:0px;border:0px;">
    <%@ include file="/common/messages.inc" %>
    <form name="myform" method="post" class="pw-borderOut_ab pw-w417_ab" action="${ctx }/base/temLibrary!saveOrUpdateCommonType.action">
      <ww:hidden name="domain.id" id="domain.id" />
      <ww:hidden name="parentid" />
      <div class="pw-borderIn_ab">
        <div class="pw-head_ab">
          <div class="pw-head-tit_ab">
            <img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" /><span>
              <ww:if test="domain.id!=null">编辑模版库</ww:if>
              <ww:else>新建模版库</ww:else>
            </span>
          </div>
          <img src="${default_imagepath}/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
        </div>
        <div class="pw-con_ab">
          <ul class="pw-con-input_ab">
            <li class="pw-con-input-item_ab">
              <label class="lab-pw_ab">
                <img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" /><span class="lab-pw-tit_ab">模版库名：</span>
                <ww:textfield id="domain.name" name="domain.name" cssClass="textField_ab textField-w191_ab required max-length-24 "></ww:textfield>
              </label>
            </li> 
            <li class="pw-con-input-item_ab">
              <label class="lab-pw_ab">
                <img src="${default_imagepath}/pic_blank.gif" class="ico_ab" /><span class="lab-pw-tit_ab">说明：</span>
                <ww:textfield id="domain.remark" name="domain.remark" cssClass="textField_ab textField-w191_ab max-length-120 "></ww:textfield>
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
	   <script>
	   try{parent.parent.parent.frames['switchToTree'].refreshMenu();}catch(e){}
	   try{parent.parent.parent.frames['main_frame'].refreshMenu();}catch(e){}
	   global_ab.fn.popWindow.removePopWindow(false);
	   </script>
  </c:forEach> 
</c:if>
  </body>
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
