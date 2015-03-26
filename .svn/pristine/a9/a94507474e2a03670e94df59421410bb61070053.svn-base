<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.cyberway.core.utils.ServiceLocator"%>
<%@ page import="com.cyberway.common.area.service.AreaService"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ include file="/common/cyber_taglibs.inc"%>
<%@ include file="/common/buffalo.inc"%>
<c:set var="title" value="用户管理" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<script src="${ctx}/common/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>
<%@ include file="/common/meta.inc"%>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>
<%@ include file="/common/validation.inc"%>
<script src="${ctx}/common/frame_js/select.js" type="text/javascript"></script>
<script src="${ctx}/common/cms_js/util.js" type="text/javascript"></script>
<script src="${ctx}/dwr/interface/webUserService.js"
	type="text/javascript"></script>
<script src="/dwr/interface/areaService.js" type="text/javascript"></script>
	
<script type="text/javascript" src="/common/cybercms_js/global_ab.js"></script>
	<script language="javascript" src="/common/datepicker/WdatePicker.js" type="text/javascript"></script>
<link href="${sytlePath}/css.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
.pw-con-input-item_ab .lab-pw-tit_ab {
	width:100px;
	display:block;
	float:left;
	text-align:right;
	height:22px;
	line-height:22px;
	color:#333;
}
.textarea-hb{
	border:1px #999 solid;
	width:90%;
	margin:7px 0;
}
.textField-w150_ab {
	width:150px;
	_width:150px;
}
.textField-w100_ab {
	width:100px;
	_width:100px;
}
.pw-w650_ab{
	width:600px;
	height:470px;
	
}
</style>

<script type="text/javascript">
var jobLevelNames = ['','首席专家','首席专家','特级专家','特级专家','资深高级专家','高级专家',
                     '资深专家','专家','资深高级设计师','高级设计师','设计师','助理设计师',
                     '技术员','首席专家','首席专家','特级专家','特级专家','资深高级专家',
                     '高级专家','资深专家','专家','资深高级经理','高级经理','经理','主管','助理'];

function save(){
	<c:if test="${isInternal}">
		if(document.getElementById('deptid').value==''){
			alert('请选择部门');
			return;
		}
	</c:if>
	if(document.getElementById('domain_juzhucity').value==''){
		alert('请选择住址');
		return;
	}
	if(valid.validate()){
		jQuery.ajax({
            type: "POST",
            url: 'webuser!adminSaveOrUpdate.action',//提交的URL
            data: jQuery('#myform').serialize(), // 要提交的表单,必须使用name属性
            async: false,
            dataType: 'json',
            success: function (data) {
            	if(data.success){
                	alert(data.msg);
					parent.parent.parent.frames['main_frame'].refreshMenu();
			  		global_ab.fn.popWindow.removePopWindow(false);
            	}else{
                	alert(data.msg);
            	}
            },
            error: function (request) {
                alert("保存失败");
            }
        });
		//myform.submit();
	}
	else
		dyniframesizeforall("main_frame");
}
function goBack(){
 location.href='webuser!adminList.action?pageStyle=<ww:property value="pageStyle" />&isInternal=<ww:property value="isInternal" />';
}
function InitUserPassword(oid){
	var msg = "确定要初始化用户密码吗？";	
	confirmMessage(msg,'提示',function(btn){
	if(btn=='yes'){	
         userManagerService.modifyUsersPassword(oid,'');
         alertMessage('初始化密码成功！');
	}
	});
}

function updateKey(provinceId,cityId)
{
	var selectKey = document.getElementById(provinceId);
	var selectValue =selectKey.options[selectKey.selectedIndex].value;
	var changeKey = document.getElementById(cityId);
	areaService.getCities(selectValue,
		function(data){
			changeKey.options.length=1;
			DWRUtil.addOptions(changeKey,data,"id","name");
		}
	);
}
</script>
</head>
<body class="pw_ab" style="overflow:hidden;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<form id="myform" class="pw-borderOut_ab pw-w750_ab" method="post" action="webuser!adminSaveOrUpdate.action" name="myform">
	<ww:hidden name="domain.oid" id="oid" />
	<ww:hidden name="domain.headerPic" id="headerPic" />
	<ww:hidden name="domain.loginpwd" id="loginpwd" />
	<ww:hidden name="domain.deptname" id="deptname" />
    <c:if test="${domain.oid!=null}">
		<ww:hidden name="domain.isInternal" id="isInternal" />
		<ww:hidden name="domain.loginname" id="loginname" />
		<ww:hidden name="domain.empcode" id="empcode" />
	</c:if>
    <c:if test="${domain.oid==null}">
		<input name="domain.isInternal" type="hidden" id="isInternal" value="${isInternal}" />
	</c:if>
	<c:if test="${isInternal}">
		<ww:hidden name="domain.name" id="name" />
	</c:if>
	<c:if test="${!isInternal}">
		<ww:hidden name="domain.nickname" id="nickname" />
		<ww:hidden name="domain.deptid" id="deptid" />
		<ww:hidden name="domain.jobLevel" id="jobLevel" />
		<ww:hidden name="domain.jobLevelName" id="jobLevelName" />
	</c:if>
	<ww:hidden name="pageStyle" id="pageStyle" />
	<ww:hidden name="keys" id="keys" />
	<div class="pw-borderIn_ab">
    	<div class="pw-head_ab">
        	<div class="pw-head-tit_ab">
            	<img src="${default_imagepath}/ico_013_plus.gif" class="ico_ab ico-013_ab" />
                <span>新建\编辑用户</span>
            </div>
            <img src="${default_imagepath}/pic_popwindow_close.jpg" class="pwClose_ab" id="pwClose_ab" />
        </div>
        <div class="pw-con_ab" style="height:450px;overflow-y:auto;">
        	<ul class="pw-con-input_ab pw-con-input-ex_ab">
            	<li class="pw-con-input-item_ab">
                	<h2 class="pw-con-input-item-tit_ab">基本信息</h2>
                </li>

                  <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>登录帐号：</span>
                        	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab"/>
                        </span> 
                  		<c:if test="${domain.oid!=null}">
							<ww:property value="domain.loginname"/>
						</c:if>
						<c:if test="${domain.oid==null}">
						 <ww:textfield name="domain.loginname" cssClass="required max-length-16 textField_ab textField-w150_ab" />
						</c:if>
                    </label>
                </li>

            	<c:if test="${domain.oid==null}">
					<li class="pw-con-input-item_ab">
	                    <label class="lab-pw_ab">
	                    	<span class="lab-pw-tit_ab">
	                        	<span>密码：</span>
	                        	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
							</span> 
	                  	<ww:password name="newPassword" cssClass="in_Text width01 required min-length-6 max-length-20 textField_ab textField-w150_ab"/>
	                         </label>
	                </li>
				</c:if>

                 <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>姓名：</span>
                        <img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab" />
						</span> 
						<c:if test="${isInternal}">
                  		<ww:property value="domain.name"/>
						</c:if>
						<c:if test="${!isInternal}">
                  		<ww:textfield name="domain.name" cssClass="in_Text width01 required max-length-16 textField_ab textField-w150_ab"/>
						</c:if>
                    </label>
                </li>

                 <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>电子邮件：</span>
                        	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab"/>
                        </span> 
                  		<ww:textfield name="domain.email" cssClass="in_Text width01 required validate-email textField_ab textField-w150_ab"/>
                    </label>
                </li>
                 
                  <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>手机号码：</span>
                        	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab"/>
                        </span> 
                 	 <ww:textfield name="domain.mobilephone" cssClass="in_Text width01 required validate-mobile-phone textField_ab textField-w150_ab"/>
                    </label>
                </li>

               <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>性别：</span>
                        </span> 
                        <ww:radio list="#{'男':'男','女':'女'}" name="domain.sex"/>
                    </label>
                </li>

               <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>生日：</span>
                        </span> 
                  	<ww:textfield name="domain.birthday" id="domain_birthday" cssStyle="" cssClass="in_Text Wdate"
										onclick="WdatePicker({el:$dp.$('domain_birthday'),dateFmt:'yyyy-MM-dd',realDateFmt:'yyyy-MM-dd',isShowOthers:true})"/>
                    </label>
                </li>

				<c:if test="${isInternal}">
                  <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>昵称：</span>
                        	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab"/>
                        </span> 
                  		<ww:textfield name="domain.nickname" cssClass="in_Text width01 required max-length-16 textField_ab textField-w150_ab"/>
                    </label>
                </li>
                  <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>工号：</span>
                        	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab"/>
                        </span> 
                  		<c:if test="${domain.oid!=null}">
							<ww:property value="domain.empcode"/>
						</c:if>
						<c:if test="${domain.oid==null}">
                  			<ww:textfield name="domain.empcode" cssClass="in_Text width01 required textField_ab textField-w150_ab"/>
						</c:if>
                    </label>
                </li>
                  <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>部门：</span>
                        	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab"/>
                        </span> 
                 	<ww:select id="deptid" list="deptList" listKey="depttype" listValue="deptname" cssClass="validate-select" name="domain.deptid" onchange="document.getElementById('deptname').value=this.options[this.selectedIndex].text"></ww:select>
                    </label>
                </li>
                  <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>职位级别：</span>
                        	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab"/>
                        </span> 
                 	<ww:select id="jobLevel" list="#{'':'请选择','技术一级':'技术一级','技术二级':'技术二级','技术三级':'技术三级',
                 		'技术四级':'技术四级','技术五级':'技术五级','技术六级':'技术六级','技术七级':'技术七级','技术八级':'技术八级',
                 		'技术九级':'技术九级','技术十级':'技术十级','技术十一级':'技术十一级','技术十二级':'技术十二级','技术十三级':'技术十三级',
                 		'管理一级':'管理一级','管理二级':'管理二级','管理三级':'管理三级','管理四级':'管理四级','管理五级':'管理五级',
                 		'管理六级':'管理六级','管理七级':'管理七级','管理八级':'管理八级','专业九级':'专业九级','专业十级':'专业十级',
                 		'专业十一级':'专业十一级','专业十二级':'专业十二级','专业十三级':'专业十三级'}" 
                 		cssClass="validate-select" name="domain.jobLevel" onchange="document.getElementById('jobLevelName').value=jobLevelNames[this.selectedIndex]"></ww:select>
                    </label>
                </li>
                  <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>职级名称：</span>
                        	<img src="${default_imagepath}/ico_003_must.gif" class="ico_ab ico-003_ab"/>
                        </span> 
                 	<ww:textfield id="jobLevelName" name="domain.jobLevelName" readonly="true" cssClass="in_Text width01 textField_ab textField-w150_ab" />
                    </label>
                </li>
				</c:if>

               <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>QQ号：</span>
                        </span> 
                  		<ww:textfield name="domain.qq" cssClass="in_Text width01 textField_ab textField-w150_ab" />
                    </label>
                </li>

               <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>微信号：</span>
                        </span> 
                  		<ww:textfield name="domain.weixinhao" cssClass="in_Text width01 textField_ab textField-w150_ab" />
                    </label>
                </li>

               <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>住址：</span>
                        </span>
						<select class="in_Text width02" id="domain_juzhuprovince" name="domain.juzhuprovince" onchange="updateKey(this.id, 'domain_juzhucity')">
							<option value="">-&#35831;&#36873;&#25321;-</option>
							<%{
							  String val = (String)request.getAttribute("domain.juzhuprovince");
							  com.cyberway.common.area.service.AreaService service=(com.cyberway.common.area.service.AreaService)ServiceLocator.getBean("areaService");
							  List<com.cyberway.common.domain.CoreArea> listSelect_0568058069 = null;
							  listSelect_0568058069 = service.getProvinces();
							  if(listSelect_0568058069!=null && listSelect_0568058069.size()>0){
								  for(com.cyberway.common.domain.CoreArea info : listSelect_0568058069){
									  out.write("\n<option value="+info.getId()+"");
									  if(val!=null && val.equals(info.getId()))
									  	out.write(" selected=true");
									  out.write(">");
									  out.write(info.getName());
									  out.write("</option>");
								  }
							  }
							}
							%>
						</select>
                    </label>
                </li>

               <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>&nbsp;</span>
                        </span>
						<select class="in_Text width02" id="domain_juzhucity" name="domain.juzhucity"><option value="">-&#35831;&#36873;&#25321;-</option>
							<%{
							  String val = (String)request.getAttribute("domain.juzhucity");
							  com.cyberway.common.area.service.AreaService service=(com.cyberway.common.area.service.AreaService)ServiceLocator.getBean("areaService");
							  java.util.List<com.cyberway.common.domain.CoreArea> listdomain_currentResidence_city = null;
							  String pid = (String)request.getAttribute("domain.juzhuprovince");
							  if(pid!=null)
							  	listdomain_currentResidence_city = service.getCounties(pid);
							  if(listdomain_currentResidence_city!=null && listdomain_currentResidence_city.size()>0){
								  for(com.cyberway.common.domain.CoreArea info : listdomain_currentResidence_city){
									  out.write("\n<option value="+info.getId()+"");
									  if(val!=null && val.equals(info.getId()))
									  	out.write(" selected=true");
									  out.write(">");
									  out.write(info.getName());
									  out.write("</option>");
								  }
							  }
							}
							%>
						</select>
                    </label>
                </li>

               <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>详细地址：</span>
                        </span> 
                  	<ww:textfield name="domain.address" cssClass="in_Text width01 textField_ab textField-w150_ab" />
                    </label>
                </li>

               <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>证件号码：</span>
                        </span> 
						<ww:select cssClass="in_Text width01" 
						list="#{\"身份证\":\"身份证\",\"军人证\":\"军人证\",\"护照\":\"护照\"}"  name="domain.zhengjianprovince"/>
                    </label>
                </li>

               <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>&nbsp;</span>
                        </span>
						<ww:textfield name="domain.zhengjian" cssClass="in_Text width01 textField_ab textField-w150_ab"/>
                    </label>
                </li>

            </ul>
        	<ul class="pw-con-input_ab pw-con-input-ex_ab" style="width:380px;">
            	<li class="pw-con-input-item_ab">
                	<h2 class="pw-con-input-item-tit_ab">家庭情况</h2>
                </li>

                 <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>职业：</span>
                        	
                        </span> 
                  	<ww:select name="domain.position" cssClass="in_Text " list="#{'国家机关':' 国家机关','党群组织':'党群组织','企业':'企业',
										'事业单位':'事业单位','专业技术人员':'专业技术人员','办事人员和有关人员':'办事人员和有关人员'
										,'商业':'商业','服务人员':'服务人员','农、林、牧、渔、水利业生产人员':'农、林、牧、渔、水利业生产人员'
										,'生产、运输设备操作人员及有关人员':'生产、运输设备操作人员及有关人员','军人':'军人','不便分类的其他从业人员':'不便分类的其他从业人员','未知':'未知'}"></ww:select>
                    </label>
                </li>
                 
                  <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>教育程度：</span>
                        </span> 
                 	 <ww:select name="domain.education" cssClass="in_Text " list="#{'研究生':' 研究生','大学本科':'大学本科','企业':'企业',
										'大学专科':'大学专科','中等职业教育':'中等职业教育'
										,'中等职业教育':'中等职业教育','普通高中':'普通高中','初中':'初中'
										,'小学':'小学'}"></ww:select>
                    </label>
                </li>

               <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>婚姻：</span>
                        </span> 
                  	<ww:radio list="#{'未婚':'未婚','已婚':'已婚'}" name="domain.married"/>
                    </label>
                </li>

               <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>家庭人数：</span>
                        </span> 
                  	<ww:select list="#{'1人':'1人','2人':'2人','3人':'3人','3人以上':'3人以上'}" name="domain.family"/>
                    </label>
                </li>

               <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>家庭收入：</span>
                        </span> 
                  	<ww:select name="domain.income" cssClass="in_Text " list="#{'3000以下':'3000以下','3000~5000':'3000~5000','5000~8000':'5000~8000',
										'8000~12000':'8000~12000','12000~20000':'12000~20000','20000以上':'20000以上'}"></ww:select>
                    </label>
                </li>

               <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>子女数量：</span>
                        </span> 
                  	<ww:select list="#{'无':'无','1人':'1人','2人':'2人','2人以上':'2人以上'}" name="domain.family"/>
                    </label>
                </li>

               <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>房屋类型：</span>
                        </span> 
                  	<ww:select name="domain.houseType" cssClass="in_Text width02" list="#{'平房/自建房屋':'平房/自建房屋','小区楼房':'小区楼房','联排别墅':'联排别墅',
										'独栋别墅':'独栋别墅'}"></ww:select>
                    </label>
                </li>

               <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>居室类型：</span>
                        </span>
						<div>
	                  		<ww:select name="domain.room" cssClass="in_Text " list="#{1:'1室',2:'2室',3:'3室'}"></ww:select>
						</div>
                    </label>
                </li>

               <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>&nbsp;</span>
                        </span>
						<div>
							<ww:select name="domain.ting" cssClass="in_Text " list="#{1:'1厅',2:'2厅',3:'3厅'}"></ww:select>
						</div>
                    </label>
                </li>

               <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>房屋拥有：</span>
                        </span> 
                  		<ww:select name="domain.ownerType" cssClass="in_Text width01" list="#{'自有':'自有','独栋':'独栋','合租':'合租'}"></ww:select>
                    </label>
                </li>

               <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab">
                        	<span>房屋面积：</span>
                        </span> 
                  		<ww:select name="domain.houseArea" cssClass="in_Text width01" list="#{'40平米以下':'40平米以下','40-70平米':'40-70平米','70-100平米':'70-100平米',
										'100-150平米':'100-150平米','150-300平米':'150-300平米','300平米以上':'300平米以上'}"></ww:select>
                    </label>
                </li>
            </ul>
            
            
        	<ul class="pw-con-input_ab pw-con-input-ex_ab" style="width:650px;">
            	<li class="pw-con-input-item_ab">
                	<h2 class="pw-con-input-item-tit_ab">其它信息</h2>
                </li>

                 <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab" style="padding-bottom:25px;">
                        	<span>爱好：</span>
                        </span>
						<ww:checkboxlist name="domain.habit" listValue="value" listKey="key" theme="simple" list="#{\"1\":'高尔夫',\"2\":'读书',\"3\":'上网',\"4\":'手机',\"5\":'烹饪',\"6\":'花草',\"7\":'美容',
							\"8\":'美食',\"9\":'聚会',\"10\":'驾驶',\"11\":'跳舞',\"12\":'电视',\"13\":'收藏',\"14\":'宠物',\"15\":'时装',\"16\":'旅游',\"17\":'购物',\"18\":'演奏乐器',\"19\":'户外体育运动'}"/>
						<script type="text/javascript">
							var sels = [${domain.habit}];
							var objs = document.getElementsByName("domain.habit");
							for(var i=0; i<objs.length;i++){
								for(var k=0; k<sels.length;k++){
									if(sels[k]==objs[i].value){
										objs[i].checked=true;
										break;
									}
								}
							}
						</script>
                    </label>
                </li>
                 
                  <li class="pw-con-input-item_ab">
                    <label class="lab-pw_ab">
                    	<span class="lab-pw-tit_ab" style="padding-bottom:25px;">
                        	<span>喜欢的电视节目：</span>
                        </span>
						<ww:checkboxlist name="domain.favorite" listValue="value" listKey="key" theme="simple" list="#{\"1\":\"新闻\",\"2\":\"动漫\",\"3\":\"电视剧\",\"4\":\"访谈\",\"5\":\"人文\",\"6\":\"戏曲\",\"7\":\"财经\",
							\"8\":\"体育\",\"9\":\"电影\",\"10\":\"娱乐\",\"11\":\"科教\",\"12\":\"农业\",\"13\":\"军事法制\",\"14\":\"纪录片\",\"15\":\"时装\",\"16\":\"旅游\",\"17\":\"购物\",\"18\":\"演奏乐器\",\"19\":\"户外体育运动\"}"/>
						<script type="text/javascript">
							var sels = [${domain.habit}];
							var objs = document.getElementsByName("domain.favorite");
							for(var i=0; i<objs.length;i++){
								for(var k=0; k<sels.length;k++){
									if(sels[k]==objs[i].value){
										objs[i].checked=true;
										break;
									}
								}
							}
						</script>
                    </label>
                </li>
            </ul>
            
            <c:if test="${domain.oid!=null}">
	        	<ul class="pw-con-input_ab pw-con-input-ex_ab" style="width:650px;">
	            	<li class="pw-con-input-item_ab">
	                	<h2 class="pw-con-input-item-tit_ab">无需更改密码时请将新密码留空</h2>
	                </li>
	
	                 <li class="pw-con-input-item_ab">
	                    <label class="lab-pw_ab">
	                    	<span class="lab-pw-tit_ab" style="padding-bottom:25px;">
	                        	<span>新密码：</span>
	                        </span>
							<ww:password name="newPassword" cssClass="in_Text width01 textField_ab min-length-6 max-length-20 textField-w150_ab" />
	                    </label>
	                </li>
	            </ul>
			</c:if>

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
			parent.parent.parent.frames['main_frame'].refreshMenu();
	  		global_ab.fn.popWindow.removePopWindow(false);
		</script>
	</c:forEach> 
</c:if>

<script type="text/javascript">
//<![CDATA[
<c:if test="${isInternal}">
document.getElementById('deptid').value='${domain.deptid}';
</c:if>
global_ab.init.popwindowPage_fn();

(function()
{
	var closeBtn = document.getElementById("pwClose_ab");
	closeBtn.onclick = function()
	{
		global_ab.fn.popWindow.removePopWindow(false);
	}
})();

function getRoleIds(){
	var roles=window.frames["iframe_users"].getSelectIds();
	if(roles!=null&&roles.startsWith("roleCode")){
		roles=roles.substring(9);
	}
	return roles;

}
//]]>
</script>
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