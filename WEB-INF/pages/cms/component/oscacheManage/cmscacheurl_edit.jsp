
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>
<c:set var="title" value="缓存管理" />

<html>
<head>
<title>${title}</title>
<%@ include file="/common/meta.inc"%>
<link href="${default_style}" rel="stylesheet" type="text/css">
<%@ include file="/common/js.inc"%>
<%@ include file="/common/validation.inc"%>
<script type="text/javascript">
function save(){
  if(valid.validate())
	myform.submit();
}
function goBack(){
  location.href='${ctx}/cms/cmscacheurl!list.action';
}
function showHelp(){
  if($('cronhelp').style.display=='none'){
      $('cronhelp').show();
  }else{
      $('cronhelp').hide();
  }
  dyniframesizeforall('main_frame');
  setParentFrameSize();
}
</script>

<ww:head />
</head>
<body nowrap topmargin="0" leftmargin="0" style="margin:0px;padding:0px;">
<!-- 状态提示栏 -->
<%@ include file="/common/messages.inc"%>

<!-- 页面标题 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<!-- style="background-color:#dfedef;" -->	
	<tr>
		<td bgcolor="#ffffff">
		<div id="titel1"  style="width:100%;">
		<div id="titel1_left">
		<div id="titel1_right">
		<div id="titel1_txt"><c:out value="${title}" /></div>
		</div>
		</div>
		</div>
		</td>
	</tr>

	<!-- 操作栏 -->
	<tr>
		<td valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bgcolor="#FFFFFF">

			<tr>
				<td>
				<div id="titel2" style="width:100%;">
				<div id="titel2_left">
				<div id="titel2_right">
				<div id="nav2">
				<div>
				<ul>
					<c:if test="${isEdit==true}">
						<li id="button"><a href="javascript:save()">保存</a></li>
					</c:if>
					<li id="button"><a href="javascript:goBack()">返回</a></li>
				</ul>
				</div>
				</div>
				</div>
				</div>
				</div>
				</td>
			</tr>
		</table>
		</td>
	</tr>

	<!-- 页面主要内容 -->
	
	<tr><td>


<table width="100%" border="0" align="left" style="margin-top:-3px;" bgcolor="#dfedef" height="100%">
  <tr>
    <td align="center" valign="top">


    <form method="post" action="cmscacheurl!saveOrUpdate.action" name="myform">
    <ww:hidden name="domain.oid" id="domain.oid" /> <ww:hidden
      name="domain.isCreateField" id="isCreateField" />
    <table width="100%" border="0" align="center" cellpadding="0"
      cellspacing="1" class="view_content_table" bgcolor="#dfedef">
      <tr>
        <td width="100%" align="center" class="view_content_td">
        <table width="90%" border="0" align="center" cellpadding="0"
          cellspacing="10">
          <tr>
            <td width="23%" align="left" class="inside_list">资源名称 ：</td>
            <td width="77%" align="left"><ww:textfield name="domain.resource" size="78" cssClass="required max-length-24" /></td>
          </tr>
          <tr>
            <td align="left" class="inside_list">缓存URL：</td>
            <td align="left"><ww:textfield name="domain.includeurl" size="78" cssClass="required max-length-40"/></td>
          </tr>
          <tr>
            <td align="left" class="inside_list">缓存有效时间(s) ：</td>
            <td align="left"><ww:textfield name="domain.periods" cssClass="max-length-20" /></td>
          </tr>

          <tr>
            <td align="left" class="inside_list">缓存定时更新(cron表达式)：</td>
            <td align="left"><div style="width:175px;float:left;"><ww:textfield name="domain.cron" cssClass="" /></div>
            <div style="width:95px;float:left;padding-top:9px;"><a href="javascript:showHelp();">cron表达式说明</a></div></td>
          </tr>
          
                    <tr>
                      <td align="left" class="inside_list">立即生效：</td>
                      <td align="left"><ww:select name="domain.activated" list="trueOfFalseMap1" /></td>
                    </tr>
          <tr>
            <td width="21%" align="left" valign="top" class="inside_list">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
            <td width="79%" align="left"><ww:textarea name="domain.remark" cols="60" rows="4" cssClass="max-length-120"/></td>
          </tr>
          
        </table>
        </td>
      </tr>
    </table>
    </form>
    </td></tr>
    
    
    <tr><td>
    
        <div style="margin:30px 50px 30px 70px;display:none;" id='cronhelp'>
    <pre>
    
    
    
    一个cron表达式有至少6个（也可能7个）有空格分隔的时间元素。

按顺序依次为 
秒（0~59） 
分钟（0~59） 

小时（0~23） 

天（月）（0~31，但是你需要考虑你月的天数） 

月（0~11） 

天（星期）（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT） 

7.年份（1970－2099）

其中每个元素可以是一个值(如6),一个连续区间(9-12),一个间隔时间(8-18/4)(/表示每隔4小时),一个列表(1,3,5),通配符。
由于"月份中的日期"和"星期中的日期"这两个元素互斥的,必须要对其中一个设置?. 

0 0 10,14,16 * * ? 每天上午10点，下午2点，4点
0 0/30 9-17 * * ?   朝九晚五工作时间内每半小时
0 0 12 ? * WED 表示每个星期三中午12点 
"0 0 12 * * ?" 每天中午12点触发 
"0 15 10 ? * *" 每天上午10:15触发 
"0 15 10 * * ?" 每天上午10:15触发 
"0 15 10 * * ? *" 每天上午10:15触发 
"0 15 10 * * ? 2005" 2005年的每天上午10:15触发 
"0 * 14 * * ?" 在每天下午2点到下午2:59期间的每1分钟触发 
"0 0/5 14 * * ?" 在每天下午2点到下午2:55期间的每5分钟触发 
"0 0/5 14,18 * * ?" 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发 
"0 0-5 14 * * ?" 在每天下午2点到下午2:05期间的每1分钟触发 
"0 10,44 14 ? 3 WED" 每年三月的星期三的下午2:10和2:44触发 
"0 15 10 ? * MON-FRI" 周一至周五的上午10:15触发 
"0 15 10 15 * ?" 每月15日上午10:15触发 
"0 15 10 L * ?" 每月最后一日的上午10:15触发 
"0 15 10 ? * 6L" 每月的最后一个星期五上午10:15触发 
"0 15 10 ? * 6L 2002-2005" 2002年至2005年的每月的最后一个星期五上午10:15触发 
"0 15 10 ? * 6#3" 每月的第三个星期五上午10:15触发 


有些子表达式能包含一些范围或列表 

例如：子表达式（天（星期））可以为 “MON-FRI”，“MON，WED，FRI”，“MON-WED,SAT” 

“*”字符代表所有可能的值 

因此，“*”在子表达式（月）里表示每个月的含义，“*”在子表达式（天（星期））表示星期的每一天 

 

“/”字符用来指定数值的增量 

例如：在子表达式（分钟）里的“0/15”表示从第0分钟开始，每15分钟 

         在子表达式（分钟）里的“3/20”表示从第3分钟开始，每20分钟（它和“3，23，43”）的含义一样 


“？”字符仅被用于天（月）和天（星期）两个子表达式，表示不指定值 

当2个子表达式其中之一被指定了值以后，为了避免冲突，需要将另一个子表达式的值设为“？” 

 

“L” 字符仅被用于天（月）和天（星期）两个子表达式，它是单词“last”的缩写 

但是它在两个子表达式里的含义是不同的。 

在天（月）子表达式中，“L”表示一个月的最后一天 

在天（星期）自表达式中，“L”表示一个星期的最后一天，也就是SAT 

如果在“L”前有具体的内容，它就具有其他的含义了 

例如：“6L”表示这个月的倒数第６天，“ＦＲＩＬ”表示这个月的最一个星期五 

注意：在使用“L”参数时，不要指定列表或范围，因为这会导致问题 

 


字段   允许值   允许的特殊字符 
秒    0-59    , - * / 
分    0-59    , - * / 
小时    0-23    , - * / 
日期    1-31    , - * ? / L W C 
月份    1-12 或者 JAN-DEC    , - * / 
星期    1-7 或者 SUN-SAT    , - * ? / L C # 
年（可选）    留空, 1970-2099    , - * / 
</pre>
    </div>
    </td></tr></table>

		</td>
	</tr>
</table>


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
