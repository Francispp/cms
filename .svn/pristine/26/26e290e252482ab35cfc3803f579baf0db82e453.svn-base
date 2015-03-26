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
          width: 460px;
          display: block;
          float: left;
          text-align: left;
          height: 15px;
          line-height: 15px;
          color: #333;
      }
      
      .pw-w417_ab {
          width: 550px;
      }
      
      .pw-con-input_ab {
          width: 420px;
          margin: 0 auto;
      }
	  .pw-con_ab {
	border:1px solid #002991;
	padding:18px 4px 0 4px;
	_padding-left:0;
	background-color:#fff;
	height:400px;
}
.pw-con-input-item_ab .lab-pw_ab {
	height:15px;
	display:block;
}
    </style>
  </head>
  <body class="pw_ab">
    <%@ include file="/common/messages.inc" %>
    <form name="myform" method="post" class="pw-borderOut_ab pw-w417_ab" action="${ctx }/base/org!saveOrUpdate.action">
      <ww:hidden name="domain.id" id="domain.id" />
      <div class="pw-borderIn_ab">
        <div class="pw-head_ab">
          <div class="pw-head-tit_ab">
            <span>缓存详细内容</span>
          </div>
          <img src="${default_imagepath}/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
        </div>
        <div class="pw-con_ab" style="overflow-y:auto">
          <ul class="pw-con-input_ab">
          	<ww:iterator value="items" status="rowstatus">
          		<li class="pw-con-input-item_ab">
	              <label class="lab-pw_ab">
	                <span class="lab-pw-tit_ab">
	                	<span style="color:#000000;"><ww:property value="type"/>&nbsp;</span>
						<span style="color:#0000ff;"><ww:property value="argName"/>&nbsp;</span>
						<span style="color:#000000;"><ww:property value="flag1"/>&nbsp;</span>
						<span style="color:#0000ff;"><ww:property value="value"/></span>
						<span style="color:#000000;"><ww:property value="flag2"/></span>
					</span>
	              </label>
	            </li>
			</ww:iterator>
          </ul>
          <div class="pw-con-btns_ab">
            <input type="button" class="pwSubmit_ab" value="" onclick="global_ab.fn.popWindow.removePopWindow(false);"/>
            <input type="button" class="pwCancel_ab" value="" onclick="global_ab.fn.popWindow.removePopWindow(false);"/>
          </div>
        </div>
      </div>
    </form>
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
  //]]>
</script>
