<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<c:set var="title" value="表单信息" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/validation.inc"%>
<script src="${ctx}/common/frame_js/select.js" type="text/javascript"></script>
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
    myform.submit();    
  }
 }else
  save();
}
</script>
<ww:head />
</head>
<body style="background:#fff;width:100%;margin:0px;padding:0px;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>
<div class="system-header">
  <div class="edit-header-lion">
   <ul id="lion-ul">

                    <li class="artEdit-btn_ab"> <a class="artEdit-btn-in_ab" href="#" onclick="javascript:exportFields();"> <img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" /> <span>重新导入字段</span> </a> </li>
                    
                    <li class="artEdit-btn_ab"> <a class="artEdit-btn-in_ab" href="javascript:goBack();"> <img src="${default_imagepath}/ico_018_shortkey.gif" class="ico_ab ico-017_ab" /> <span>返回</span> </a> </li>
                  
                                       
                    <li class="fn-clear"></li>
                  </ul>
  </div>
 
</div>



	<!-- 页面主要内容 -->
	
<div class="content" style="margin:0px;">
  <div class="info-box" style="margin:0px;">
  
  <form method="post" action="${ctx }/form/form!saveOrUpdate.action" name="myform">
  <ww:hidden name="domain.oid" id="domain.oid" />
<ww:hidden name="domain.isCreateField" id="isCreateField" />
   <div class="balic-table-bj-a" style="width:100%;">
                  <div class="balic-box" >
                    <div class="balic-box-table" >
                      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="role-table">
                        <tr>
                          <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="role-table-tetle">
                              <tr>
                                <td width="100%" class="role-table-telet-right">表单基本信息</td>
                              </tr>
                            </table></td>
                        </tr>
                        <tr>
                          <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="role-con">
                              <tr class="role-con-link" >
                                <td width="20%" class="role-left-text">表单代码</td>
                                <td class="role-right-text">
							<ww:hidden name="domain.formCode"/>
							${domain.formCode}
								</td>
                                <td class="role-left-text">表单名称</td>
                                <td class="role-right-text">${domain.formName}
									<ww:hidden name="domain.formName"/>	
								 </td>
                              </tr>
                             <ww:hidden name="domain.isCreateField"/>
                              <tr class="role-con-link" >
                                <td class="role-left-text">表单类型</td>
                                <td class="role-right-text">
                                ${domain.formType}
								<ww:hidden name="domain.formType"/>
                               </td>
                                <td width="20%" class="role-left-text">pojo对象</td>
                                <td class="role-right-text">
                               		<ww:hidden name="domain.pojoName"/>
                                 ${domain.pojoName}
                                </td>
                              </tr>
                               
                               <tr class="role-con-link" >
                                <td class="role-left-text">状态</td>
                                <td class="role-right-text">
                               
 									
								</td>
                                <td width="20%" class="role-left-text">备注</td>
                                <td class="role-right-text"><span></span>${domain.remark}
									<ww:hidden name="domain.remark"/>
								</td>
                              </tr>
                              
                              
                            </table></td>
                        </tr>
                        <tr>
                          <td valign="top"><div> <img src="${default_imagepath}/info-table-left.gif" class="myTab-foot-left_ab" /> <img src="${default_imagepath}/info-table-right.gif" class="myTab-foot-right_ab" /> </div></td>
                        </tr>
                      </table>
                    </div>
                  </div>
                </div>
  
  </form>
  
   <div class="balic-table-bj-b">
                  <div class="balic-box" style="width:100%;">
                  
                  <iframe id="main_frame" onload="dyniframesizeforpage(this)"  allowtransparency="yes" scrolling="auto" src="${ctx}/form/formField.action?formid=${domain.oid}" width="100%" height="200" style="border:0px none;" frameborder="0"></iframe>
					
              		</div>
              </div>
  


</div></div>


<!-- 页脚 -->
<%@ include file="/common/foot.inc"%>
</body>
</html>

