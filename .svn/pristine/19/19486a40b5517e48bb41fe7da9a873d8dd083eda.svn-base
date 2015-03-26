<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<%@ include file="/common/meta.inc"%>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ec/ec.inc"%>
  <style type="text/css">
    .dystem-table-telet {
        height: 29px;
        line-height: 29px;
        background: url(../../images/cybercms/info-title-bj.gif) bottom repeat-x;
        color: #6e592d;
        font-size: 12px;
    }
	.dystem-center-text {
	width:200px;
	height: 29px;
    line-height: 29px;
	font-size: 12px;
	padding-right:19px;
	background:#f9f9f9;
	text-align:center;
}
.artEdit-btn_ab1 {
	height:27px;
	line-height:27px;
	display:block;
	float:left;
	padding-left:7px;
	margin-right:5px;
	cursor:pointer;
}
  </style>
    <script type="text/javascript">
    function search(){
	key = document.getElementById("search").value;
	if (key == "") {
		alert('请先输入要查找的key！');
    	return ;
	}
	location.href='cmsCache!searchCache.action?id=' + ${id} + '&key='+key;
}

function viewCache(key){
	global_ab.fn.popWindow.addPopWindow("${ctx}/cms/cmsCache!viewCache.action?id=" + ${id} + "&key=" + key, "620px", "500px", false);
}
function goBack(){
	 location.href='${ctx}/cms/cmsCache.action';
	}
    </script>
  </head>
  <body>
    <!-- 状态提示栏 --><%@ include file="/common/messages.inc" %>
    <div class="system-header">
      <div class="edit-header-lion">
        <ul id="lion-ul">
          <li class="artEdit-btn_ab1 artEdit-btn-w4letters_ab">
            <input id="search" type="text"></input>
          </li>
          <li class="artEdit-btn_ab">
            <a class="artEdit-btn-in_ab" href="javascript:search();"><img src="${default_imagepath}/ico_012_zoom.gif" class="ico_ab ico-012_ab" /><span>查询</span></a>
          </li>
          <li class="artEdit-btn_ab"> <a class="artEdit-btn-in_ab" href="javascript:goBack();"> <img src="${default_imagepath}/ico_018_shortkey.gif" class="ico_ab ico-017_ab" /> <span>返回</span> </a> </li>
          <li class="fn-clear"></li>
        </ul>
      </div>
    </div>
    <div class="content">
      <div class="info-box">
      <table width="500" border="1" align="center" cellpadding="0" cellspacing="0" class="dystem-table-tetle">
        <tr>
          <td class="dystem-table-telet" align="center">
            <strong class="font4">序号</strong>
          </td>
          <td class="dystem-table-telet" align="center">
            <strong class="font4">key</strong>
          </td>
        </tr>
		<ww:if test="%{!empty items}">
            <tr>
              <td class="dystem-center-text">没有数据!</td>
            </tr>
          </ww:if>
          <ww:else>
        <ww:iterator value="items" status="rowstatus">
            <tr>
              <td class="dystem-center-text">
                <ww:property value="#rowstatus.index"/>
              </td>
              <td class="dystem-center-text">
                <a href="#" onclick="viewCache('<ww:property/>')"><ww:property/></a>
              </td>
            </tr>
        </ww:iterator>
</ww:else>
      </table>
      </div>
    </div>
    <!-- 页脚 -->
    <%@ include file="/common/foot.inc" %>
  </body>
</html>
