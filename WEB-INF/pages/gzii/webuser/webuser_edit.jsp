<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/template/public.inc"%>

<html>
  <%@ include file="/common/template/head.inc"%><head>
    <title>信息管理</title>
    <link href="/css/registerForm.css" rel="stylesheet" type="text/css"></link>
    
<script type="text/javascript">
function goBack(){
 location.href='/cms/groupmember!adminList.action?channelId=${channelId}';
}
</script>
  </head>
  <body leftMargin="0" topMargin="0">
    <%@ include file="/common/messages.inc"%>
    <table bgcolor="#FFFFFF" border="0" cellpadding="0" cellspacing="0" width="100%">
      <tbody>
        <tr>
          <td height="1"></td>
        </tr>
        <tr>
          <td bgcolor="#ffffff">
            <div id="titel1">
              <div id="titel1_left">
                <div id="titel1_right">
                  <div id="titel1_txt">
                    信息管理列表
                  </div>
                </div>
              </div>
            </div>
          </td>
        </tr>
        <tr>
          <td>
            <div id="titel">
              <div id="titel_left">
                <div id="titel_right">
                  <div id="nav">
                    <div>
                      <ul>
                        <cms:auth resCode="SYS_USER_MODI">
                          <li id="button">
                            <a href="javascript:submitForm();">保存</a>
                          </li>
                        </cms:auth>
                        <cms:auth resCode="SYS_USER_DELETE">
                          <li id="button">
                            <a href="javascript:goBack();">返回</a>
                          </li>
                        </cms:auth>
                      </ul>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </td>
        </tr>
      </tbody>
    </table>




<!-- 表单 -->


    <table bgColor="#c0c0c0" border="0" cellPadding="0" cellSpacing="1" width="810">
      <tbody>
        <tr>
          <td align="center" bgColor="#f7f7f7">
            <form action="${ctx}/cms/groupmember!saveOrUpdate.action?id=${id}&channelId=${channelId}"
              method="post" name="myform">
              <ww:hidden name="domain.webUser.oid"></ww:hidden>
              &nbsp;
              <table border="0" cellPadding="0" cellSpacing="0" width="90%">
                <tbody>
                  <tr>
                    <td class="content">
                      <table border="0" cellSpacing="1" id="registerForm" width="100%">
                        <tbody>
                          <tr>
                            <td width="120">
                              <div align="right">
                                登 录 名 称
                                <span style="color: #ff3300;">*</span>
                              </div>
                            </td>
                            <td colSpan="3">
                              <div>
                                <ww:textfield  cssClass="required max-length-24" id="TextField_8120583051" name="domain.fieldString15"></ww:textfield>
                              </div>
                            </td>
                          </tr>
                          <tr>
                            <td width="120">
                              <div align="right">
                                审 核 状 态
                                <span style="color: #ff3300;">*</span>
                              </div>
                            </td>
                            <td colSpan="3">
                              <div>
                                <div class="wwgrp">
                                  <div class="wwctrl">
                                    <ww:select name="domain.webUser.approved" list="#{true:'已审',false:'未审'}"/>
                                  </div>
                                </div>
                              </div>
                            </td>
                          </tr>
                          <tr>
                            <td width="120">
                              <div align="right">
                                用 户 状 态
                                <span style="color: #ff3300;">*</span>
                              </div>
                            </td>
                            <td colSpan="3">
                              <div>
                                <div class="wwgrp">
                                  <div class="wwctrl">
                                    <ww:select name="domain.webUser.locked" list="#{false:'激活',true:'禁用'}"/>
                                  </div>
                                </div>
                              </div>
                            </td>
                          </tr>
                          <tr>
                            <td colSpan="4">
                              <div align="right"></div>
                            </td>
                          </tr>
                          <tr>
                            <td width="120">
                              <div align="right">
                                单 位 全 称
                                <span style="color: #ff3300;">*</span>
                              </div>
                            </td>
                            <td colSpan="3">
                              <ww:textfield  cssClass="max-length-2" cssStyle="width:300px;" id="TextField_2705900336"
                                name="domain.fieldString1"></ww:textfield>
                            </td>
                          </tr>
                          <tr>
                            <td width="120">
                              <div align="right">
                                <span>详细通信地址</span>
                              </div>
                            </td>
                            <td colSpan="3">
                              <ww:textfield cssStyle="width:300px;" id="TextField_8110047866"
                                name="domain.fieldString2"  cssClass="max-length-120"></ww:textfield>
                            </td>
                          </tr>
                          <tr>
                            <td width="120">
                              <div align="right">
                                <span>邮编</span>
                              </div>
                            </td>
                            <td>
                              <ww:textfield id="TextField_2186376669" name="domain.fieldString3"  cssClass="max-length-20"></ww:textfield>
                            </td>
                            <td>
                              <span>申请理事单位</span>
                            </td>
                            <td>
                              <select id="Select_5557198217" name="domain.fieldString4">
                                <option value="是">
                                  是
                                </option>
                                <option value="否">
                                  否
                                </option>
                              </select>
                              <script type="text/javascript">if("${domain.fieldString4}"!="")
 $("Select_5557198217").value="${domain.fieldString4}";
</script>
                            </td>
                          </tr>
                          <tr>
                            <td width="120">
                              <div align="right">
                                <span>单位性质</span>
                              </div>
                            </td>
                            <td colSpan="3">
                              <select id="Select_7348178959" name="domain.fieldString5">
                                <option value="国有">
                                  国有
                                </option>
                                <option value="股份制">
                                  股份制
                                </option>
                                <option value="有限责任">
                                  有限责任
                                </option>
                                <option value="联营">
                                  联营
                                </option>
                                <option value="私营">
                                  私营
                                </option>
                                <option value="港澳台投资">
                                  港澳台投资
                                </option>
                                <option value="外商投资">
                                  外商投资
                                </option>
                                <option value="其他">
                                  其他
                                </option>
                              </select>
                              <script type="text/javascript">if("${domain.fieldString5}"!="")
 $("Select_7348178959").value="${domain.fieldString5}";
</script>
                            </td>
                          </tr>
                          <tr>
                            <td colSpan="4">
                              <div align="right"></div>
                            </td>
                          </tr>
                          <tr>
                            <td bgColor="#f3ddac" colSpan="4">
                              <div align="left" style="background-color: #f3ddac;">
                                <span>负责人详细资料</span>
                              </div>
                            </td>
                          </tr>
                          <tr>
                            <td colSpan="4">
                              <table border="0" width="100%">
                                <tbody>
                                  <tr>
                                    <td>
                                      <div align="right">
                                        <span>姓名</span>
                                      </div>
                                    </td>
                                    <td>
                                      <ww:textfield id="TextField_6556166203" name="domain.fieldString20" cssClass="max-length-40"></ww:textfield>
                                    </td>
                                    <td>
                                      <div align="right">
                                        <span>性别</span>
                                      </div>
                                    </td>
                                    <td>
                                      <ww:textfield id="TextField_4644890931" name="domain.fieldString21" cssClass="max-length-8"></ww:textfield>
                                    </td>
                                    <td height="100" rowSpan="4" width="100">
                                      &nbsp;
                                    </td>
                                  </tr>
                                  <tr>
                                    <td>
                                      <div align="right">
                                        证件号码
                                      </div>
                                    </td>
                                    <td>
                                      <ww:textfield id="TextField_4467050469" name="domain.fieldString22" cssClass="max-length-20"></ww:textfield>
                                    </td>
                                    <td>
                                      <div align="right">
                                        <span>职务</span>
                                      </div>
                                    </td>
                                    <td>
                                      <ww:textfield id="TextField_3404097229" name="domain.fieldString23"  cssClass="max-length-40"></ww:textfield>
                                    </td>
                                  </tr>
                                  <tr>
                                    <td>
                                      <div align="right">
                                        办公电话（含区号）
                                      </div>
                                    </td>
                                    <td>
                                      <ww:textfield id="TextField_5531436561" name="domain.fieldString24"   cssClass="max-length-40"></ww:textfield>
                                    </td>
                                    <td>
                                      <div align="right">
                                        <span>传真（含区号）</span>
                                      </div>
                                    </td>
                                    <td>
                                      <ww:textfield id="TextField_8514729116" name="domain.fieldString25"   cssClass="max-length-20"></ww:textfield>
                                    </td>
                                  </tr>
                                  <tr>
                                    <td>
                                      <div align="right">
                                        <span>手机</span>
                                      </div>
                                    </td>
                                    <td>
                                      <ww:textfield id="TextField_0164920128" name="domain.fieldString26"   cssClass="max-length-20"></ww:textfield>
                                    </td>
                                    <td>
                                      <div align="right">
                                        <span>电子信箱</span>
                                      </div>
                                    </td>
                                    <td>
                                      <ww:textfield id="TextField_0474456371" name="domain.fieldString27"   cssClass="max-length-20"></ww:textfield>
                                    </td>
                                  </tr>
                                </tbody>
                              </table>
                            </td>
                          </tr>
                          <tr>
                            <td bgColor="#f3ddac" colSpan="4">
                              <div align="left" style="background-color: #f3ddac;">
                                <span>联系人详细资料</span>
                              </div>
                            </td>
                          </tr>
                          <tr>
                            <td colSpan="4">
                              <table border="0" width="100%">
                                <tbody>
                                  <tr>
                                    <td>
                                      <div align="right">
                                        <span>姓名</span>
                                      </div>
                                    </td>
                                    <td>
                                      <ww:textfield id="TextField_1981560831" name="domain.fieldString30"   cssClass="max-length-40"></ww:textfield>
                                    </td>
                                    <td>
                                      <div align="right">
                                        <span>性别</span>
                                      </div>
                                    </td>
                                    <td>
                                      <ww:textfield id="TextField_7773763572" name="domain.fieldString31"   cssClass="max-length-4"></ww:textfield>
                                    </td>
                                    <td height="100" rowSpan="4" width="100">
                                      &nbsp;
                                    </td>
                                  </tr>
                                  <tr>
                                    <td>
                                      <div align="right">
                                        证件号码
                                      </div>
                                    </td>
                                    <td>
                                      <ww:textfield id="TextField_4761109440" name="domain.fieldString32"   cssClass="max-length-20"></ww:textfield>
                                    </td>
                                    <td>
                                      <div align="right">
                                        <span>职务</span>
                                      </div>
                                    </td>
                                    <td>
                                      <ww:textfield id="TextField_8538794665" name="domain.fieldString33"  cssClass="max-length-60"></ww:textfield>
                                    </td>
                                  </tr>
                                  <tr>
                                    <td>
                                      <div align="right">
                                        办公电话（含区号）
                                      </div>
                                    </td>
                                    <td>
                                      <ww:textfield id="TextField_9091865738" name="domain.fieldString34"   cssClass="max-length-20"></ww:textfield>
                                    </td>
                                    <td>
                                      <div align="right">
                                        <span>传真（含区号）</span>
                                      </div>
                                    </td>
                                    <td>
                                      <ww:textfield id="TextField_7334143216" name="domain.fieldString35"   cssClass="max-length-20"></ww:textfield>
                                    </td>
                                  </tr>
                                  <tr>
                                    <td>
                                      <div align="right">
                                        <span>手机</span>
                                      </div>
                                    </td>
                                    <td>
                                      <ww:textfield id="TextField_4370339990" name="domain.fieldString36"   cssClass="max-length-20"></ww:textfield>
                                    </td>
                                    <td>
                                      <div align="right">
                                        <span>电子信箱</span>
                                      </div>
                                    </td>
                                    <td>
                                      <ww:textfield id="TextField_3603662855" name="domain.fieldString37"   cssClass="max-length-20"></ww:textfield>
                                    </td>
                                  </tr>
                                </tbody>
                              </table>
                            </td>
                          </tr>
                          <tr>
                            <td colSpan="4">
                              <div align="right"></div>
                            </td>
                          </tr>
                          <tr>
                            <td bgColor="#f3ddac" colSpan="4">
                              <div align="left" style="background-color: #f3ddac;">
                                <span>单位详细资料</span>
                              </div>
                            </td>
                          </tr>
                          <tr>
                            <td width="120">
                              <div align="right">
                                行 业 类 别&nbsp;
                              </div>
                            </td>
                            <td colSpan="3">
                              <select id="Select_3672539313" name="domain.fieldString7">
                                <option value="10">
                                  国家机关
                                </option>
                                <option value="20">
                                  事业单位
                                </option>
                                <option value="21">
                                  &nbsp;&nbsp;--信息中心
                                </option>
                                <option value="22">
                                  &nbsp;&nbsp;--科研院所
                                </option>
                                <option value="23">
                                  &nbsp;&nbsp;--新闻出版
                                </option>
                                <option value="24">
                                  &nbsp;&nbsp;--院校
                                </option>
                                <option value="25">
                                  &nbsp;&nbsp;--其他
                                </option>
                                <option value="30">
                                  企业单位
                                </option>
                                <option value="31">
                                  &nbsp;&nbsp;--电信业
                                </option>
                                <option value="32">
                                  &nbsp;&nbsp;--计算机
                                </option>
                                <option value="33">
                                  &nbsp;&nbsp;--信息服务
                                </option>
                                <option value="34">
                                  &nbsp;&nbsp;--工业
                                </option>
                                <option value="35">
                                  &nbsp;&nbsp;--商业
                                </option>
                                <option value="36">
                                  &nbsp;&nbsp;--其他
                                </option>
                                <option value="40">
                                  其他
                                </option>
                              </select>
                              <script type="text/javascript">if("${domain.fieldString7}"!="")
 $("Select_3672539313").value="${domain.fieldString7}";
</script>
                            </td>
                          </tr>
                          <tr>
                            <td width="120">
                              <div align="right">
                                <span>单位简介（发展概况、规模、主要业务等）</span>
                              </div>
                            </td>
                            <td colSpan="3">
                              <ww:textarea cssStyle="" id="TextArea_5174032032" name="domain.fieldString8"></ww:textarea>
                            </td>
                          </tr>
                          <tr>
                            <td width="120">
                              <div align="right">
                                <div align="center" style="text-align: center; line-height: 12pt">
                                  <span>主要产品或</span>
                                </div>
                                <div align="center" style="text-align: center; line-height: 12pt">
                                  <span>服务特点</span>
                                </div>
                                <div align="center" style="text-align: center; line-height: 12pt">
                                  <span>（企业填写）</span>
                                </div>
                              </div>
                            </td>
                            <td colSpan="3">
                              <ww:textarea cssStyle="" id="TextArea_1842227036" name="domain.fieldString9"   cssClass="max-length-200"></ww:textarea>
                            </td>
                          </tr>
                          <tr>
                            <td width="120">
                              <div align="right">
                                <span>年营业额</span>
                              </div>
                            </td>
                            <td colSpan="3">
                              <select id="Select_8958118443" name="domain.fieldString10">
                                <option value="1">
                                  50以下
                                </option>
                                <option value="2">
                                  50&mdash;100
                                </option>
                                <option value="3">
                                  100&mdash;500
                                </option>
                                <option value="4">
                                  500&mdash;1000
                                </option>
                                <option value="5">
                                  1000&mdash;5000
                                </option>
                                <option value="6">
                                  5000&mdash;10000
                                </option>
                                <option value="7">
                                  10000以上
                                </option>
                              </select>
                              <script type="text/javascript">if("${domain.fieldString10}"!="")
 $("Select_8958118443").value="${domain.fieldString10}";
</script>
                            </td>
                          </tr>
                          <tr>
                            <td width="120">
                              <div align="right">
                                主要业务成果
                              </div>
                            </td>
                            <td colSpan="3">
                              <ww:textarea cssStyle="" id="TextArea_8482759038" name="domain.fieldString11"   cssClass="max-length-240"></ww:textarea>
                            </td>
                          </tr>
                          <tr>
                            <td width="120">
                              <div align="right">
                                对协会有何希望及要求
                              </div>
                            </td>
                            <td colSpan="3">
                              <ww:textarea cssStyle="" id="TextArea_8274220343" name="domain.fieldString12"  cssClass="max-length-240"></ww:textarea>
                            </td>
                          </tr>
                        </tbody>
                      </table>
                      <ww:hidden id="TextField_2984673236" name="domain.id"></ww:hidden>
                    </td>
                  </tr>
                  <tr>
                    <td class="content">
                      &nbsp;
                    </td>
                  </tr>
                  <tr>
                    <td background="../images/gylist_13.gif" height="3"></td>
                  </tr>

                  <tr>
                    <td>
                      &nbsp;
                    </td>
                  </tr>
                </tbody>
              </table>
            </form>
          </td>
        </tr>
      </tbody>
    </table>


    <c:if test="${false && domain.webUser.oid !=null}">
      <iframe id="iframe_users" 
        src="${ctx}/cms/role!list.action?webUserId=${domain.webUser.oid}" width="100%" height="200"
        style="border:0px none;"></iframe>
    </c:if>





    <script type="text/javascript"><!--
 var valid = new Validation('myform',{immediate:true});
 function ConfirmPwd(){
  if($('myform').loginpwd.value!=$('myform').loginpwd1.value){
    $('myform').loginpwd1.style.borderColor="#ff3300";
    alert('两次密码输入不一致!');
    return false;
  }
  $('myform').loginpwd1.style.borderColor="#A5D69D";
  return true;
 }
 function submitForm(){
  //if(!ConfirmPwd()) return false;
  if(valid.validate())
    $('myform').submit();
 }
 --></script>





    <%@ include file="/common/foot.inc"%>
  </body>
</html>