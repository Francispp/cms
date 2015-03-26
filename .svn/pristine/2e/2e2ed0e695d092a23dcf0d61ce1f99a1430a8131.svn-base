<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/common/cyber_taglibs.inc" %>
<%@ include file="/common/buffalo.inc" %>
<c:set var="title" value="频道编辑" /> <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>${title}</title>
    <%@ include file="/common/meta.inc" %>
    <%@ include file="/common/js.inc" %>
    <%@ include file="/common/validation.inc" %>
    <link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
    <script src="/common/frame_js/select.js" type="text/javascript"></script>
    <style type="text/css">
    .column-table-left-ie6 {
	width:163px;
	height:31px;
	_line-height:21px;
	_height:21px;
	_padding-top:10px;
	line-height:28px;
	vertical-align:top;
	text-align:right;
	padding-right:16px;
	background:#fdffeb;
	border-bottom-width: 1px;
	border-right-width: 1px;
	border-bottom-style: solid;
	border-right-style: solid;
	border-bottom-color: #acacac;
	border-right-color: #acacac;	
	font-family:Arial, "宋体";
	font-size: 13px;
}
.column-table-left-ie6 img {
	vertical-align:middle;
	margin:-2px 8px 0px 0px;
}


.column-table-rigth-ie6 {
	width:163px;
	height:31px;
	_line-height:31px;
	_height:31px;
	_padding-top:5px;
	line-height:28px;
	vertical-align:top;
	text-align:left;
	padding-right:16px;
	background:#fdffeb;
	border-bottom-width: 1px;
	border-right-width: 1px;
	border-bottom-style: solid;
	border-right-style: solid;
	border-bottom-color: #acacac;
	border-right-color: #acacac;	
	font-family:Arial, "宋体";
	font-size: 12px;
}
    </style>
    <script type="text/javascript">
      function save(){
        if (valid.validate()) {
        
          var chnname = document.getElementById("domain_name").value;
          if (chnname.indexOf("&") != -1) {
            alert("频道名称不能含有“&”字符");
            document.getElementById("domain_name").focus();
            return;
          }
          myform.submit();
          
        }
      }
      
      function goBack(){
        location.href = 'channel!list.action?pageStyle=<ww:property value="pageStyle" />';
      }
      
      function chageType(){
        var typeValue = myform.resouretype.value;
        switch (typeValue) {
          case '0':
            document.all.ifprotectedTR.style.display = "none";
            document.all.protectedurlTR.style.display = "none";
            break;
          case '1':
            document.all.ifprotectedTR.style.display = "";
            document.all.protectedurlTR.style.display = "";
            break;
          default:
            break;
        }
      }
      
      //从xml文件中导入信息
      function inputData(){
        var title = "选择导入的文件";
        actionURL = "${ctx}/cms/channel!importChannelData.action";
        uploadXML(title, actionURL);
      }
      
      function exportData(){
        location.href = "channel!exportChannelData.action?id=${id}";
      }
      
      /*生成频道静态页面*/
      function publishChannel(){
        var id = document.getElementById("id").value;
        var obj = new Array;
        obj[0] = id + "";
        ExecuteService("if(reply.getResult()=='true'){}else{ alert('操作失败!');}", 'channelManagerService', 'channelTemplateStatic', obj);
      }
    </script>
  </head>
  <body style="margin:0px;padding:0px;width:100%;background:#fff;">
    <%@ include file="/common/messages.inc" %>
    <div class="system-header">
      <div class="edit-header-lion">
        <ul id="lion-ul">
          <c:if test="${isEdit==true}">
            <!--<c:if test="${domain.site.ispublished==1}">
            <li class="artEdit-btn_ab"> <a class="artEdit-btn-in_ab" href="javascript:publishChannel()"> <img src="${default_imagepath}/ico_006_world.gif" class="ico_ab ico-006_ab" /> <span>静态发布</span> </a> </li>
            </c:if>-->
            <li class="artEdit-btn_ab">
              <a class="artEdit-btn-in_ab" href="javascript:save()"><img src="${default_imagepath}/ico_004_floppy.gif" class="ico_ab ico-004_ab" /><span>保存</span></a>
            </li>
          </c:if>
          <c:if test="${pageStyle==1}">
            <li class="artEdit-btn_ab">
              <a class="artEdit-btn-in_ab" href="javascript:goBack()"><img src="${default_imagepath}/ico_130_communication.gif" class="ico_ab ico-130_ab" /><span>返回</span></a>
            </li>
          </c:if>
          <li class="fn-clear"></li>
        </ul>
      </div>
    </div>
    <div class="tbox">
      <div id="tab1-content0" class="block">
        <div class="role-input-left-bj" style="background:url();border:0px;">
          <div class="role-input-right-bj" style="background:url();border:0px;">
            <div class="role-content">
              <div class="balic-table-bj-b">
                <div class="site-content" style="width:100%;">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="column-table">
                    <form method="post" action="channel!saveOrUpdate.action" name="myform">
                      <ww:hidden name="domain.formTemplate.id" id="domain.formTemplate.id"/>
                      <ww:hidden name="domain.detailsTemplate.id" id="domain.detailsTemplate.id"/>
                      <ww:hidden name="domain.summaryTemplate.id" id="domain.summaryTemplate.id"/>
                      <ww:hidden name="domain.adminSummaryTemplate.id" id="domain.adminSummaryTemplate.id"/>
                      <ww:hidden name="domain.publicchannelid" id="publicchannelid"/>
                     
                      <ww:hidden name="domain.isflow" id="isflow" value="0"/>
                       <ww:hidden name="domain.isInheritPerm" id="isInheritPerm"/>
                       <ww:hidden name="domain.isInheritDocPerm" id="isInheritDocPerm"/>
                      <tr>
                        <td class="column-table-left-ie6">所属站点：</td>
                        <td class="column-table-left-ie6">
                          <ww:hidden name="domain.site.oid" id="domain_site_oid"/>
                          <ww:textfield name="domain.site.sitename" readonly="true" cssClass="column-input" />
                        </td>
                        <td class="column-table-left-ie6">上级频道：</td>
                        <td class="column-table-left-ie6">
                          <ww:hidden name="domain.parent.id"/>
                          <ww:textfield name="domain.parent.name" readonly="true" cssClass="column-input" />
                        </td>
                      </tr>
                      <tr>
                        <td class="column-table-left-ie6" >
                          <img src="${default_imagepath}/ico_003_must.gif"   />频道地址：
                        </td>
                        <td class="column-table-rigth-ie6">
                          <ww:textfield name="domain.channelPath" cssClass="required max-length-200 validate-alphanum column-input" />
                        </td>
                        <td class="column-table-left-ie6"><img src="${default_imagepath}/ico_003_must.gif"   /> 频道名称：
                        </td>
                        <td class="column-table-rigth-ie6">
                          <ww:textfield name="domain.name" cssClass="required max-length-40 column-input"/>
                        </td>
                      </tr>
                      <tr>
                        <td class="column-table-left-ie6">频道编号：</td>
                        <td class="column-table-left-ie6">
                         <ww:textfield name="domain.id" readonly="true" id="id" cssClass="column-input" />
                         
                        </td>
                          <td class="column-table-left-ie6">排序号：</td>
                        <td class="column-table-left-ie6">
                         <ww:textfield name="domain.sortOrder" cssClass="validate-integer max-value-1000000 column-input"/>
                        </td>
                       
                      </tr>
                      <tr>
                        <td class="column-table-left-ie6">是否为公共模版：</td>
                        <td class="column-table-right">
                          <ww:select name="domain.ispublicchannel" list="trueOfFalseMap1" />
                        </td>
                       <td class="column-table-left-ie6">是否发布：</td>
                        <td class="column-table-right">
                          <ww:select name="domain.ispublished" list="trueOfFalseMap1" />
                        </td>
                      </tr>
                      <ww:if test="domain.id"></ww:if>
                      <tr>
                        <td class="column-table-left-ie6">公共模板方式：</td>
                        <td class="column-table-right">
                          <ww:select name="domain.iscopy" list="trueOfFalseMap2" onchange="iscopy(this)"/>
                        </td>
                        <td class="column-table-left-ie6">
                          <span id="font" style="display:none;">栏目模板：</span>
                        </td>
                        <td class="column-table-right">
                          <span></span>
                          <table border=0 style="display:none" id="public">
                            <tr>
                              <td>
                                <ww:textfield name="domain.publicchannel" id="publicchannel" readonly="true"/>
                              </td>
                              <td>
                                <input type='button' class='srchbt' value='选择' onClick='selectPublicChannelEx(myform.publicchannelid,myform.publicchannel, "${ctx}/cms/site!publicItree.action?siteid=<ww:property value="domain.site.oid"/>")'>
                                <span id="update" style="display:">
                                  <input type="button" class="bt-save" value="更新" onClick="updateForm();"></span>
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                      <tr>
                        <td class="column-table-left-ie6">开放方式：</td>
                        <td class="column-table-right">
                          <ww:select name="domain.isOpenChannel" id="isOpenChannel" list="trueOfFalseMap3" />
                        </td>
                        <td class="column-table-left-ie6">是否索引：</td>
                        <td class="column-table-right">
                          <ww:select name="domain.isSearch" list="trueOfFalseMap4" />
                        </td>
                      </tr>
					<tr>
                        <td class="column-table-left-ie6">重定向地址：</td>
                        <td class="column-table-right">
                          <ww:textfield name="domain.redirect" cssClass="max-length-200 column-input"/>
                        </td>
                        <td class="column-table-left-ie6">&nbsp;</td>
                        <td class="column-table-right">&nbsp;</td>
                      </tr>
                      <tr>
                        <td class="column-table-left-ie6">频道描述：</td>
                        <td class="column-table-left-ie6" colspan="3">
                          <ww:textarea name="domain.descreble" cols="40" rows="5" cssClass="max-length-240"/>
                        </td>
                      </tr>
                    </form>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div style="width:100%;height:10px;"></div>
    <script>
      
      function column_left(thisTag){
        var span = thisTag.getElementsByTagName('span')[0];
        span.style.display = 'block';
        thisTag.onmouseout = function(){
          span.style.display = 'none';
        }
      }
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
   myform.domain_site_oid.value ='<ww:property value="domain.site.oid" />';
   <c:if test="${isEdit!=true}">
   setElementsDisabled(true);
  </c:if>	
  
</script>
<script>
  
  function updateForm()
   {
     if(!confirm("确定要更新当前的复制模板？"))
     {
  	return;
     }
     myform.action='channel!updateChannelForm.action?publicchannelid=' + myform.publicchannelid.value;
     myform.submit();
   }
  function iscopy(obj)
    {
  	if(obj.options[obj.selectedIndex].value == 0)
  	{
  	  ${"public"}.style.display="none";
  	  ${"font"}.style.display="none";
  	}
  	else
  	{
  	  ${"font"}.style.display="";
  	  ${"public"}.style.display="";
  	  if(obj.options[obj.selectedIndex].value == 2)
  		{
  		 ${"update"}.style.display="none";
  		}
  	  else
  		{
  		${"update"}.style.display="";
  		}
  	 }
     }
  <ww:if test="domain.iscopy">
    ${"public"}.style.display="";
    ${"font"}.style.display="";
  </ww:if>
  <ww:if test="domain.iscopy==2">
    ${"update"}.style.display="none";
  </ww:if>
  <c:if test="${isNew==true && domain.id!=null}">
  parent.document.all.switchToTree.src=parent.document.all.switchToTree.src;
  </c:if>
  
  
   
</script>
