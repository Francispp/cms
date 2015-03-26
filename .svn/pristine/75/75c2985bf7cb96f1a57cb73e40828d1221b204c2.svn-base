<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>

<html>
<c:set var="title" value="系统用户维护" />
<html>
<head>
<title>${title}</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@ include file="/common/ext/ext-res.inc"%>
<%@ include file="/common/js.inc"%>

<script type="text/javascript">		

var pwddialog,roledialog;
function showChangePwd(oid){
	var showBtn = Ext.get('pwd_'+oid);
	$('useroid').value = oid;
    showBtn.on('click', function(){
    		$("pwdfirst").value="";
    		$("pwdagain").value="";
            if(!pwddialog){
                pwddialog = new Ext.BasicDialog("pwd-dlg", { 
                        modal:true,
                        width:300,
                        height:180,
                        shadow:true,
                        proxyDrag: true
                });
                pwddialog.addKeyListener(27, pwddialog.hide, pwddialog);
            }
            pwddialog.show(showBtn.dom);
        }, this);
}
function del(oid){
	Ext.MessageBox.confirm('提示', '确定删除该记录？', function(btn){
	if(btn=='yes')
	alert(btn);
		//window.location.href = "${ctx}/admin/user!delete.do?oid="+oid;
	});
}
</script>		
</head>

	<body>
	<img alt="修改密码" id="pwd_1" onclick="showChangePwd('1')" style="cursor: pointer" src="${ctx}/images/icon/CrystalClear/22/status/dialog-password.png"/>
<img alt="删除该记录" onclick="del('1')" style="cursor: pointer" src="${ctx}/images/icon/CrystalClear/22/actions/edit-delete.png"/>
	<input type="hidden" id="useroid">
	<div id="pwd-dlg" style="visibility:hidden;">
	    <div class="x-dlg-hd">修改密码</div>
	    <div class="x-dlg-bd">
		    <div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>
    		<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc">
        	<div id="form-pwd">
				<table align="center" border="0" width="80%">
					<tr height="30">
					<td>新密码：<ww:password id="pwdfirst"/></td>
					</tr><tr height="30">
					<td>再确认：<ww:password id="pwdagain"/></td>
					</tr><tr height="30">
					<td align="right"><button type="button" onclick="changePwd();">修改</button></td>
					</tr>
				</table>
		    </div>
		    </div></div></div>
		    <div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>
	    </div>
	</div>	
	</body>
</html>
