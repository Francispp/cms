<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/template/public.inc"%>
<!doctype html>
<html>
<head>
	<title>个人中心</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
    <link href="/css/base.css" rel="stylesheet" />
    <link href="/css/konka.company.css" rel="stylesheet" />
    <link href="/css/personar.css" rel="stylesheet" />
    <link href="/css/prsoner.gy.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="/css/hr.jobs.css" />
	<script src="/common/cms_js/util.js" type="text/javascript"></script>
	<script type="text/javascript" src="/common/jquery/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="/common/jquery/jquery.form.min.js"></script>
	<script src="/dwr/util.js" type="text/javascript"></script>
	<script src="/dwr/engine.js" type="text/javascript"></script>
	<script src="/dwr/interface/areaService.js" type="text/javascript"></script>
	<script language="javascript" src="/common/datepicker/WdatePicker.js" type="text/javascript"></script>
	<script type="text/javascript" src="/common/validation/prototype.js"></script>
	<script src="/scripts/validation.js" type="text/javascript"></script>
	<script src="/scripts/hr.page.js" type="text/javascript"></script>
	<script type="text/javascript" src="/scripts/konka.company.js"></script>
	<script src="/common/cms_js/cms.js" type="text/javascript" language="javascript"></script>
	<script src="/common/attachment/attachment.js" type="text/javascript" language="javascript"></script>
	<script src="/common/attachment/xml.js" type="text/javascript" language="javascript"></script>
	<style type="text/css">
	.mr_red_prompt{font-size:12px; color:#F00;margin-left:8px;vertical-align:top;height:22px;margin-top:6px; display:inline-block;}
	#advice-validate-mobile-phone-domain_mobilephone,.login_int .errorMessage{
			font-size: 12px;
			color: #F00;
			margin-left: 80px;
			vertical-align: top;
			height: 22px;
			margin-top: 2px;
			display: block;
			width: 340px;
			text-align: left;
		}
	.sr_up_pic{width:160px;height:160px;}
	.hrCommain{float:none;}
	.mrCommain, .srCommain{float:none;}
	</style>
	<c:if test="${success != null}">
	<script type="text/javascript">
		alert('${success}');
	</script>
	</c:if>
</head>
<body>
    <div class="kanKaBox">
		<%@ include file="/cms_file/templates/1/company_header_include.jsp"%>
        <div class="content">
            <div class="personalBox">
				<%@ include file="personLeft.jsp"%>
                <div class="ps_con">
                    <ul class="ps_tab">
						<li><a class="ps_tabCur" href="/webuser!personal.action" class="#">个人资料</a></li>
                    	<li><a href="/webuser!setpwd.action">修改密码</a></li>
						<ww:if test="showResumeLi">
	                    <li>
							<a id="resumeLink" href="#">个人简历</a>
							<div class="resumeList">
								<p class="mlist"><a href="/webuser!campusLog.action">校园简历</a></p>
								<p class="mlist"><a href="/webuser!socialLog.action">社会简历</a></p>
							</div>
						</li>
						</ww:if>
						<ww:else>
	                    <li>
							<a id="resumeLink" href="#">申请记录</a>
							<div class="resumeList">
								<p class="mlist"><a href="/webuser!tutorLog.action">导师申请</a></p>
								<p class="mlist"><a href="/webuser!courseLog.action">课程申请</a></p>
							</div>
						</li>
						</ww:else>
                    </ul>
                    <div class="setInfo ps_conInfo">
					<form id="infoForm" name="infoForm" method="post" action="/webuser!saveInfo.action">
					<input type="hidden" name="domain.headerPic" id="domain_headerPic" value="${domain.headerPic }"/>
					<input type="hidden" name="domain.loginname" value="${domain.loginname }"/>
					<ww:if test="domain.isInternal">
					<input type="hidden" name="domain.name" value="${domain.name }"/>
					</ww:if>
					<ww:else>
					<input type="hidden" name="domain.nickname" value="${domain.nickname }"/>
					<input type="hidden" name="domain.email" value="${domain.email }"/>
					</ww:else>
					<input type="hidden" name="domain.oid" value="${domain.oid }"/>
					<input type="hidden" name="domain.loginpwd" value="${domain.loginpwd }"/>
					<input type="hidden" name="domain.empcode" value="${domain.empcode }"/>
					<input type="hidden" name="domain.jobLevel" value="${domain.jobLevel }"/>
					<input type="hidden" name="domain.jobLevelName" value="${domain.jobLevelName }"/>
					<input type="hidden" name="domain.deptid" value="${domain.deptid }"/>
					<input type="hidden" name="domain.deptname" value="${domain.deptname }"/>
					<input type="hidden" name="domain.isInternal" value="${domain.isInternal }"/>
                        <div class="setInfo_block">
                            <h2 class="setInfo_block_title">
                                <span class="fl">基本信息</span><span class="fr"><span class="red">*</span>为必填项</span></h2>
                            <table>
                                <tr>
                                    <td width="244" class="textL">
                                        <span class="red">*</span>姓名：
                                    </td>
                                    <td>
										<ww:if test="domain.isInternal">
											<span class="blueC">${domain.name }</span>
										</ww:if>
										<ww:else>
											<ww:textfield name="domain.name" cssClass="in_Text width01 required max-length-10" />
										</ww:else>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="244" class="textL">
                                        <span class="red">*</span>电子邮件：
                                    </td>
                                    <td>
										<ww:if test="domain.isInternal">
                                        	<ww:textfield name="domain.email" cssClass="in_Text width01 required validate-email" />
										</ww:if>
										<ww:else>
                                        	<span class="blueC">${domain.email }</span>
										</ww:else>
                                    </td>
                                </tr>
								<ww:if test="domain.isInternal">
                                <tr>
                                    <td width="244" class="textL">
                                        <span class="red">*</span>昵称：
                                    </td>
                                    <td>
                                        <ww:textfield name="domain.nickname" cssClass="in_Text width01 required max-length-10" />
                                    </td>
                                </tr>
								</ww:if>
                                <tr>
                                    <td width="244" class="textL">
                                        <span class="red">*</span>手机号码：
                                    </td>
                                    <td>
										<ww:textfield name="domain.mobilephone" cssClass="in_Text width01 required validate-mobile-phone" />
                                    </td>
                                </tr>
                                <tr>
                                    <td width="244" class="textL">
                                        	性别：
                                    </td>
                                    <td>
                                        <ww:radio list="#{'男':'男','女':'女'}" name="domain.sex"/>
                                    </td>
                                </tr>
								<ww:if test="domain.isInternal">
                                <tr>
                                    <td width="244" class="textL">
                                        	员工号：
                                    </td>
                                    <td>
                                        <span class="blueC">${domain.empcode }</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="244" class="textL">
                                        	部门：
                                    </td>
                                    <td>
                                        <span class="blueC">${domain.deptname }</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="244" class="textL">
                                        	职位级别：
                                    </td>
                                    <td>
                                        <span class="blueC">${domain.jobLevel }</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="244" class="textL">
                                        	职级名称：
                                    </td>
                                    <td>
                                        <span class="blueC">${domain.jobLevelName }</span>
                                    </td>
                                </tr>
								</ww:if>
                                <tr>
                                    <td width="244" class="textL">
                                        <span class="red">*</span>生日：
                                    </td>
                                    <td>
										<ww:textfield name="domain.birthday" id="domain_birthday" cssStyle="width:175px" cssClass="in_Text Wdate required"
										onfocus="WdatePicker({vel:this,dateFmt:'yyyy-MM-dd',maxDate:'today'})"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="244" class="textL">
                                        	QQ号：
                                    </td>
                                    <td>
                                        <ww:textfield name="domain.qq" cssClass="in_Text width01" />
                                    </td>
                                </tr>
                                <tr>
                                    <td width="244" class="textL">
                                        	微信号：
                                    </td>
                                    <td>
                                        <ww:textfield name="domain.weixinhao" cssClass="in_Text width01" />
                                    </td>
                                </tr>
                                <tr>
                                    <td width="244" class="textL">
                                        <span class="red">*</span>住址：
                                    </td>
                                    <td>
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
										<select class="in_Text width02 validate-select" id="domain_juzhucity" name="domain.juzhucity"><option value="">-&#35831;&#36873;&#25321;-</option>
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
                                    </td>
                                </tr>
                                <tr>
                                    <td width="244" class="textL">
                                        详细地址：
                                    </td>
                                    <td>
                                        <ww:textfield name="domain.address" cssClass="in_Text width01" />
                                    </td>
                                </tr>
                                <tr>
                                    <td width="244" class="textL">
                                        证件号码：
                                    </td>
                                    <td>
										<ww:select cssClass="in_Text width02" 
										list="#{\"身份证\":\"身份证\",\"军人证\":\"军人证\",\"护照\":\"护照\"}"  name="domain.zhengjianprovince"/>
                                        <ww:textfield name="domain.zhengjian" cssClass="in_Text width02"/>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div class="setInfo_block">
                            <h2 class="setInfo_block_title">
                                <span class="fl">家庭情况</span></h2>
                            <table>
                                <tr>
                                    <td width="244" class="textL">
                                        职业：
                                    </td>
                                    <td>
										<ww:select name="domain.position" cssClass="in_Text " list="#{'国家机关':' 国家机关','党群组织':'党群组织','企业':'企业',
										'事业单位':'事业单位','专业技术人员':'专业技术人员','办事人员和有关人员':'办事人员和有关人员'
										,'商业':'商业','服务人员':'服务人员','农、林、牧、渔、水利业生产人员':'农、林、牧、渔、水利业生产人员'
										,'生产、运输设备操作人员及有关人员':'生产、运输设备操作人员及有关人员','军人':'军人','不便分类的其他从业人员':'不便分类的其他从业人员','未知':'未知'}"></ww:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="244" class="textL">
                                        教育程度：
                                    </td>
								<td>
                                        <ww:select name="domain.education" cssClass="in_Text " list="#{'研究生':' 研究生','大学本科':'大学本科','企业':'企业',
										'大学专科':'大学专科','中等职业教育':'中等职业教育'
										,'中等职业教育':'中等职业教育','普通高中':'普通高中','初中':'初中'
										,'小学':'小学'}"></ww:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="244" class="textL">
                                        婚姻：
                                    </td>
                                    <td>
                                        <ww:radio list="#{'未婚':'未婚','已婚':'已婚'}" name="domain.married"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="244" class="textL">
                                        家庭人数：
                                    </td>
                                    <td>
										<ww:radio list="#{'1人':'1人','2人':'2人','3人':'3人','3人以上':'3人以上'}" name="domain.family"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="244" class="textL">
                                        家庭收入：
                                    </td>
                                    <td>
										<ww:select name="domain.income" cssClass="in_Text " list="#{'3000以下':'3000以下','3000~5000':'3000~5000','5000~8000':'5000~8000',
										'8000~12000':'8000~12000','12000~20000':'12000~20000','20000以上':'20000以上'}"></ww:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="244" class="textL">
                                        子女数量：
                                    </td>
                                    <td>
                                        <ww:radio list="#{'无':'无','1人':'1人','2人':'2人','2人以上':'2人以上'}" name="domain.children"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="244" class="textL">
                                        房屋类型：
                                    </td>
                                    <td>
                                    <ww:select name="domain.houseType" cssClass="in_Text width02" list="#{'平房/自建房屋':'平房/自建房屋','小区楼房':'小区楼房','联排别墅':'联排别墅',
										'独栋别墅':'独栋别墅'}"></ww:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="244" class="textL">
                                        居室类型：
                                    </td>
                                    <td>
                                    <ww:select name="domain.room" cssClass="in_Text " list="#{1:'1',2:'2',3:'3'}"></ww:select>
                                        <span class="flTxt">室</span>
										<ww:select name="domain.ting" cssClass="in_Text " list="#{1:'1',2:'2',3:'3'}"></ww:select>
                                        <span class="flTxt">厅</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="244" class="textL">
                                        房屋拥有：
                                    </td>
                                    <td>
                                        <ww:select name="domain.ownerType" cssClass="in_Text width01" list="#{'自有':'自有','独栋':'独栋','合租':'合租'}"></ww:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="244" class="textL">
                                        房屋面积：
                                    </td>
                                    <td>
                                        <ww:select name="domain.houseArea" cssClass="in_Text width01" list="#{'40平米以下':'40平米以下','40-70平米':'40-70平米','70-100平米':'70-100平米',
										'100-150平米':'100-150平米','150-300平米':'150-300平米','300平米以上':'300平米以上'}"></ww:select>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div class="setInfo_block">
                            <h2 class="setInfo_block_title">
                                <span class="fl">其它信息</span></h2>
                            <table>
                                <tr>
                                    <td width="244" class="textL txtTop">
                                        爱好：
                                    </td>
                                    <td >
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
                                    </td>
                                </tr>
                                <tr>
                                    <td width="244" class="textL txtTop">
                                        喜欢的电视节目：
                                    </td>
                                    <td >
                                        <ww:checkboxlist name="domain.favorite" listValue="value" listKey="key" theme="simple" list="#{\"1\":\"新闻\",\"2\":\"动漫\",\"3\":\"电视剧\",\"4\":\"访谈\",\"5\":\"人文\",\"6\":\"戏曲\",\"7\":\"财经\",
										\"8\":\"体育\",\"9\":\"电影\",\"10\":\"娱乐\",\"11\":\"科教\",\"12\":\"农业\",\"13\":\"军事法制\",\"14\":\"纪录片\",\"15\":\"时装\",\"16\":\"旅游\",\"17\":\"购物\",\"18\":\"演奏乐器\",\"19\":\"户外体育运动\"}"/>
										<script type="text/javascript">
											var sels = [${domain.favorite}];
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
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <p class="setInfo_c">
                            <input type="button" onclick="saveInfo()" class="btn" value="保存"/>
                        </p>
				</form>
				</div>
                </div>
            </div>
        </div>
        <div class="clear">
        </div>
        <div class="wihte_03">
        </div>
		<%@ include file="/cms_file/templates/1/footer_include.jsp"%>
    </div>
    <script type="text/javascript">
	var valid = new Validation('infoForm',{immediate:true});
	function saveInfo(){
		if(valid.validate())
		document.infoForm.submit();
	}
	</script>
</body>
</html>
