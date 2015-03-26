<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/common/cyber_taglibs.inc" %>
<%@ include file="/common/buffalo.inc" %>
<c:set var="title" value="专辑管理" /> <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>${title}</title>
    <%@ include file="/common/meta.inc" %>
    <%@ include file="/common/js.inc" %>
    <%@ include file="/common/ext/ext-res.inc" %>
    <%@ include file="/common/validation.inc" %>
    <script src="${ctx}/common/frame_js/select.js" type="text/javascript"></script>
    <script src="${ctx}/common/cms_js/util.js" type="text/javascript"></script>
    <script type="text/javascript" src="/common/cybercms_js/global_ab.js"></script>
    <link href="${sytlePath}/css.css" rel="stylesheet" type="text/css" />
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
      .pw-con-input-ex_ab .lab-pw-tit_ab2 {
	float:left;
}
      
      .textarea-hb {
          border: 1px #999 solid;
          width: 90%;
          margin: 7px 0;
      }
      
      .textField-w150_ab {
          width: 150px;
      }
      
      .textField-w150_ab {
          width: 150px;
      }
      
      .select-w150_ab {
          width: 150px;
          _width: 150px;
      }
      
      .pw-con-input-item-plu_ab {
          border: 1px solid #999;
          width: 200px;
          height: 200px; #
      
      	height: 200px;
      }
      
      .pw-w600_ab {
      	width: 320px;
      	height: 256px
      }
      
      .textarea-w150_ab {
      	width: 150px;
      	height: 60px;
      }
    </style>
    <script type="text/javascript">
      function save(){
        var valid = new Validation('myform', {
          immediate: true
        });
        if (valid.validate()) 
          myform.submit();
      }
      
      function closed(){
        window.returnValue = 'true';
        window.close();
      }
    </script>
  </head>
  <body class="pw_ab" style="padding: 0px">
    <!-- 状态提示栏 --><%@ include file="/common/messages.inc" %>
    <form class="pw-borderOut_ab pw-w600_ab" method="post" action="${ctx}/base/album!saveOrUpdate.action" name="myform">
      <ww:hidden name="domain.id" id="domain.id" /><ww:hidden name="domain.createTime" id="domain.createTime" /><ww:hidden name="domain.imagePath" id="Photo_album" /><ww:hidden name="domain.pid" id="domain.pid" /><ww:hidden name="domain.mediaType" id="domain.mediaType" /><ww:hidden name="domain.siteId" id="domain.siteId" />
      <div class="pw-borderIn_ab">
        <div class="pw-head_ab">
          <div class="pw-head-tit_ab">
            <img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" /><span>创建专辑</span>
          </div>
          <img src="${default_imagepath}/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
        </div>
        <div class="pw-con_ab" style="height:222px;overflow-y:auto;">
          <ul class="pw-con-input_ab pw-con-input-ex_ab">
            <li class="pw-con-input-item_ab">
              <h2 class="pw-con-input-item-tit_ab">基本信息</h2>
            </li>
            <li class="pw-con-input-item_ab">
              <label class="lab-pw_ab">
                <span class="lab-pw-tit_ab"><span>专辑名称：</span><img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" /></span>
                <ww:textfield name="domain.title" cssClass="required textField_ab textField-w150_ab max-length-24" />
              </label>
            </li>
            <li class="pw-con-input-item_ab">
              <label class="lab-pw_ab">
                <span class="lab-pw-tit_ab"><span>专辑描述：</span></span>
                <span class="lab-pw-tit_ab2">
                <ww:textarea name="domain.discribe" cols="30" rows="6" cssClass="textField_hb textarea-w150_ab max-length-120" />
             	</span>
              </label>
            </li>
          </ul><!--
          <li class="pw-con-input-item_ab"><label class="lab-pw_ab">
          <span class="lab-pw-tit_ab"> <span>上传封面</span> <img
          src="${default_imagepath}/ico_003_must.gif"
          class="ico_ab ico-003_ab" /> </span>
          <button class="but_img"
          onclick="uploadPic('Photo_album','','','200','200','null','');">浏览封面</button>
          </label></li>
          <ul class="pw-con-input_ab pw-con-input-ex_ab pw-con-input-rx_ab">
          <li class="pw-con-input-item_ab">
          <h2 class="pw-con-input-item-tit_ab">封面预览</h2></li>
          <li class="pw-con-input-item_ab">
          <div class="pw-con-input-item-plu_ab">
          <div id="divPhoto_album"></div>
          <script language='javascript'>
          showPic('Photo_album', '200', '200',
          'null');
          </script>
          </div></li>
          </ul>
          -->
          <div class="fn-clear"></div>
          <div class="pw-con-btns_ab">
            <input type="button" class="pwSubmit_ab" value="" onclick="javascript:save();"/>
            <input type="button" class="pwCancel_ab" value="" onclick="javascript:global_ab.fn.popWindow.removePopWindow(false);"/>
          </div>
        </div>
      </div>
    </form>
    
    <c:if test="${not empty actionMessages}">
      <c:forEach var="err" items="${actionMessages}">
        <script>
          window.top.document.all.switchToTree.src = "${ctx}/base/album!tree.action?albumType=${domain.mediaType}";
          global_ab.fn.popWindow.removePopWindow(false);
        </script>
      </c:forEach>
    </c:if>
    <script type="text/javascript">
      //<![CDATA[
      global_ab.init.popwindowPage_fn();
      
      (function(){
        var closeBtn = document.getElementById("pwClose_ab");
        closeBtn.onclick = function(){
          global_ab.fn.popWindow.removePopWindow(false);
        }
      })();
      
      function getRoleIds(){
        var roles = window.frames["iframe_users"].getSelectIds();
        if (roles != null && roles.startsWith("roleCode")) {
          roles = roles.substring(9);
        }
        return roles;
        
      }
      
      //]]>
    </script>
    <!-- 页脚 -->
    <%@ include file="/common/foot.inc" %>
  </body>
</html>
<script type="text/javascript">
  var valid = new Validation('myform',{immediate:true});
  
  <c:if test="${isEdit!=true}">
   setElementsDisabled(true);
  </c:if>
</script>
