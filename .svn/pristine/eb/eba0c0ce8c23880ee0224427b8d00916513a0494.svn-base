<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/common/cyber_taglibs.inc" %>
<%@ include file="/common/buffalo.inc" %>
<c:set var="title" value="静态资源列表" /> <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>${title}</title>
    <%@ include file="/common/meta.inc" %>
    <link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
    <%@ include file="/common/js.inc" %>
    <%@ include file="/common/ec/ec.inc" %>
    <script type="text/javascript">
      
      var parameterUrl = "";
      //编辑
      function editItem(oid){
        global_ab.fn.popWindow.addPopWindow("${ctx}/cms/staticResource!edit.action?id=" + oid + parameterUrl, "460px", "430px", false);
      }
      
      function addItem(){
        location.href = 'staticResource!edit.action';
      }
      
      function importZip(){
        var title = "选择导入的文件";
        actionURL = "${ctx}/cms/staticResource!importZip.action&ftype=.zip";
        uploadXML(title, actionURL);
      }
      
      function exportZip(){
        location.href = 'staticResource!exportZip.action';
      }
      
      function multiUpload(){
        global_ab.fn.popWindow.addPopWindow("${ctx}/cms/staticResource!multiUpload.action", "417px", "340px", false);
      }
      
      function download(id){
        location.href = 'staticResource!fileDownload.action?id=' + id;
      }
      
      function deleteItem(){
        var ids = getSelectedID();
        if (ids == null || ids == '') {
          alert('请先选择记录！');
          return;
        }
        if (confirm('您确定要删除选择的记录吗？')) {
          location.href = 'staticResource!delete.action?keys=' + ids + parameterUrl;
        }
      }
      
      function deleteItemById(id){
        var ids = id;
        if (ids == null || ids == '') {
          alert('请先选择记录！');
          return;
        }
        if (confirm('您确定要删除选择的记录吗？')) {
          location.href = 'staticResource!delete.action?keys=' + ids + parameterUrl;
        }
      }
      
      function multiDistribution(){
        if (confirm('确定要分发全部静态资源吗？')) {
          var url = 'staticResource!distribution.action';
          new Ajax.Request(url, {
            method: 'post',
            parameters: {
              flag: 0
            },
            onSuccess: function(transport){
              alert('批量分发成功！');
            }
          });
        }
      }
      
      function selectDistribution(){
        var ids = getSelectedID();
        if (ids == null || ids == '') {
          alert('请先选择记录！');
          return;
        }
        if (confirm('确定要分发这些静态资源吗？')) {
          var url = 'staticResource!selectDistribution.action';
          new Ajax.Request(url, {
            method: 'post',
            parameters: {
              keys: ids,
              flag: 0
            },
            onSuccess: function(transport){
              alert('选择分发成功！');
            }
          });
        }
      }
      
      function reload(){
        location.href = '${ctx}/cms/staticResource.action';
      }
    </script>
  </head>
  <body style="margin:0px;padding:0px;width:100%;">
    <!-- 状态提示栏 -->
    <DIV ID="message">
      <%@ include file="/common/messages.inc" %>
    </DIV>
    <div class="edit-header">
      <div class="edit-header-top">
        <ul id="lion-ul-a">
          <cms:CmsAuth resCode="CMS_TEMPLATE_MANAGER" objectId="${loginer.siteId}" objectType="1">
            <li class="artEdit-btn_ab">
              <a class="artEdit-btn-in_ab" href="javascript:multiUpload();"><img src="${default_imagepath}/ico_054_administrativeDocs.gif" class="ico_ab ico-013_ab" /><span>批量录入</span></a>
            </li>
            <li class="artEdit-btn_ab">
              <a class="artEdit-btn-in_ab" href="javascript:selectDistribution();"><img src="${default_imagepath}/ico_092_doneSquare.gif" class="ico_ab ico-013_ab" /><span>选择分发</span></a>
            </li>
            <li class="artEdit-btn_ab">
              <a class="artEdit-btn-in_ab" href="javascript:importZip();"><img src="${default_imagepath}/ico_020_upcomingWork.gif" class="ico_ab ico-017_ab" /><span>导入</span></a>
            </li>
            <ww:if test="%{!empty items}">
              <li class="artEdit-btn_ab">
                <a class="artEdit-btn-in_ab" href="javascript:exportZip();"><img src="${default_imagepath}/ico_020_upcomingWork1.gif" class="ico_ab ico-017_ab" /><span>导出</span></a>
              </li>
            </ww:if>
            <li class="artEdit-btn_ab">
              <a class="artEdit-btn-in_ab" href="javascript:deleteItem();"><img src="${default_imagepath}/ico_017_trashFull.gif" class="ico_ab ico-017_ab" /><span>删除</span></a>
            </li>
          </cms:CmsAuth>
          <li class="artEdit-btn_ab">
            <a class="artEdit-btn-in_ab" href="javascript:reload();"><img src="${default_imagepath}/ico_019_refresh.gif" class="ico_ab ico-019_ab" /><span>刷新</span></a>
          </li>
        </ul>
      </div>
      <br></br>
      <div class="edit-header-bottom">
        <ul style="padding-bottom:5px">
          <li class="select-all">
            <input type='checkbox' name='allbox' onclick='checkAll(this);'/>
            全选
          </li>
          <li class="main-search">
            <input name="textfield" style="height:15px;font-size:11px;" type="text" class="main-search-input" id="searchStr" onfocus="if(this.value=='关键字搜索')value='';" onblur="if(this.value=='')value='关键字搜索';" onkeydown="if (event.keyCode==13) {searchAll(this.value);}" value="关键字搜索"/>
            <img src="${default_imagepath}/main-search-img.gif" onclick="javascript:searchAll(document.getElementById('searchStr').value)"/>
          </li>
        </ul>
      </div>
    </div>
    <div class="content" style="width:100%;margin:0px;padding:10px;">
      <div class="info-box" style="width:100%;margin:0px;padding:0px;">
        <form id="myTable" method="post" action="/cms/staticResource.action">
          <div class="media-img-div" style="margin:0px;padding:0px;width:100%;">
            <div class="media-img-box" style="margin:0px;padding:0px;width:100%;">
              <ul style="margin:0px auto;text-align:center;width:100%;">
                <ww:iterator status="rowstatus" value="items">
                  <li style="margin:10px 10px 0px 0px;">
                  <div class="media-img-li-top">
                    <div class="media-img-one">
					<c:choose>
					<c:when test="${fn:endsWith(serverpath,'.gif') ||fn:endsWith(serverpath,'.png') ||fn:endsWith(serverpath,'.jpg')}">
					<img name="serverpath" width="140" height="110" src="${serverpath }"/>
					</c:when>
					<c:otherwise>
					<img name="serverpath" width="140" height="110" src="/images/cybercms/file_icon.jpg"/>
					</c:otherwise>
					</c:choose>
                    </div>
                    <div class="media-img-two">
                      <a href="javascript:download(<ww:property value="id" />)"><img src="${default_imagepath}/ico_179_down.gif"/></a><a href="javascript:editItem(<ww:property value="id"></ww:property>);"><img src="${default_imagepath}/media-img-tubiao2.gif"/></a><a href="javascript:deleteItemById('<ww:property value="id"></ww:property>');"><img src="${default_imagepath}/media-img-tubiao1.gif"/></a>
                    </div>
                  </div>
                  <div class="media-img-li-bottom" style="overflow-x:hidden;">
                  <input type="checkbox" name="_selectitem" value="<ww:property value="id" />" onclick='checkOne(allbox);' />
                  <ww:if test="%{name.length()>20}">
                    <ww:property value="name.substring(0,18)+'...'"/>
                  </ww:if>
                  <ww:else>
                    <ww:property value="name"></ww:property>
                  </ww:else>
                  </li>
                </ww:iterator>
              </ul>
            </div>
          </div>
          <div class="media-img-box-bottom">
            <img src="${default_imagepath}/info-table-left.gif" style="float:left;"/><img src="${default_imagepath}/info-table-right.gif" style="float:right;"/>
          </div>
          </div>
          <div class="pag-box media-margin">
            <cms:pager style="tablePager" pageIndex="data.currentPageNo" pageSize="data.pageSize" recordCount="data.totalCount"></cms:pager>
          </div>
        </form>
      </div>
      <!-- 页脚 -->
      <%@ include file="/common/foot.inc" %>
      </body>
    </html>
    <script type="text/javascript">
      <!--
      
      function jsTrim(str){
        return str.replace(/\ /g, "");
      }
      
      function searchAll(str){
        str = jsTrim(str);
        if (str == '' || str == '关键字搜索') {
          alert('请输入有效字符');
          return;
        }
        var tstr = str.length;
        str = str.replace(/[^a-zA-Z\d\u4e00-\u9fa5_\-]/g, "");
        if (str.length != tstr) {
          alert('只能匹配中文、英文与数字');
          return;
        }
        location.href = "/cms/staticResource.action?searchStr=" + encodeURI(str);
      }
      
      //-->
    </script>
