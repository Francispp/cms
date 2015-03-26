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
    <script type='text/javascript' src='/dwr/interface/orgManagerService.js'></script>
    <link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
      .pw-con-input-item_ab .lab-pw-tit_ab {
          width: 110px;
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
          width: 440px;
          margin: 0 auto;
      }
    </style>
    <script type="text/javascript">
      function save(){
        if (valid.validate()) {
          if (checkLength()) 
            myform.submit();
        }
      }
      
      function checkLength(){
        var length = myform.newpassword.value.length;
        var newpassword1 = myform.newpassword.value;
        var newpassword2 = myform.newpassword1.value;
        var check = true;
        
        if (length < 3 || length > 8) {
          alertMessage('新密码不符合要求，请重新输入！');
          //location.href='user!updateUserPWD.action';
          return check = false;
        }
        
        if (newpassword1 != newpassword2) {
          alertMessage('重输密码与新密码不一致，请重新输入！');
          //location.href='user!updateUserPWD.action';
          return check = false;
        }
        return check;
      }
    </script>
  </head>
  <body class="pw_ab">
    <%@ include file="/common/messages.inc" %>
    <form name="myform" method="post" class="pw-borderOut_ab pw-w417_ab" action="${ctx}/base/user!updateUserPWD.action">
      <div class="pw-borderIn_ab">
        <div class="pw-head_ab">
          <div class="pw-head-tit_ab">
            <img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" /><span>修改密码</span>
          </div>
          <img src="${default_imagepath}/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
        </div>
        <div class="pw-con_ab">
          <ul class="pw-con-input_ab">
            <li class="pw-con-input-item_ab">
              <label class="lab-pw_ab">
                <img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" /><span class="lab-pw-tit_ab">旧密码：</span>
                <ww:password id="oldpassword" name="oldpassword" cssClass="textField_ab textField-w191_ab required"></ww:password>
              </label>
            </li>
            <li class="pw-con-input-item_ab">
              <label class="lab-pw_ab">
                <img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" /><span class="lab-pw-tit_ab">新密码：</span>
                <ww:password id="newpassword" name="newpassword" cssClass="textField_ab textField-w191_ab required"></ww:password>
              
              </label>
               <span style="color:#F00;margin-left:140px;">(密码长度不少于3个，不大于8个)</span>
              <div class="fn-clear"></div>
            </li>
            <li class="pw-con-input-item_ab">
              <label class="lab-pw_ab">
                <img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" /><span class="lab-pw-tit_ab">重新输入新密码：</span>
                <ww:password id="newpassword1" name="newpassword1" cssClass="textField_ab textField-w191_ab required"></ww:password>
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
  </body>
  <c:if test="${not empty actionMessages}">
	   	<c:forEach var="err" items="${actionMessages}">
	        <script>global_ab.fn.popWindow.removePopWindow(false);</script>
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
