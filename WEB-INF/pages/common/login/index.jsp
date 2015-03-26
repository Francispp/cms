<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.inc"%>

<html>
	<head>

		<title>内容管理系统</title>

		<%@ include file="/common/ext/ext-res.inc"%>
		<%@ include file="/common/js.inc"%>

		
		<style type="text/css">

	#header{
	    background: url(${ctx}/portals/common/images/header-bar.gif) repeat-x bottom;
	    border-bottom: 1px solid #083772;
	    padding:5px 4px;
	}

	/*主页面logo*/
	#main-page-logo{
		background-repeat: no-repeat;
		height:50;
	}
	
	/*主页面标题*/
	#main-page-title{
		height:1;
		FILTER: Glow(Color=#000000,Strength=3);
		font-size:32px;
		color:#FFFFFF;
		margin-left:50; 
		letter-spacing:5; 
		font-weight:bold;
		text-align:left;
	}
 	#application-tree{
 		text-align: left;
 	}
</style>

		<script language="javascript">  

 /**
  * 页面布局设计
  */
 var LayoutFrame = function(){
        return {
            init : function(){

               var mainLayout = new Ext.BorderLayout(document.body, {
                    north: {split:false,initialSize: 50},
                    west: {split:true,animate: true,initialSize: 200,titlebar: true,collapsible: true,minSize: 100,maxSize: 400},
                    center: {autoScroll: false,titlebar: true}
                });
                 
				mainLayout.beginUpdate();
	            mainLayout.add('north', northPanel = new Ext.ContentPanel('header', {fitToFrame: true}));                            
                mainLayout.add('west', new Ext.ContentPanel('application-tree', {title: '功能菜单', fitToFrame:true, closable:false}));
                //url: "${ctx}/index!menu.action"
                mainLayout.add('center',new Ext.ContentPanel('content', {title:'当前登陆用户：${loginer.username}',fitToFrame:true, closable:false}));
	
				mainLayout.endUpdate();

				//tree=new dhtmlXTreeObject("application-tree","100%","100%",0);
				//tree.loadXML("${ctx}/portals/common/menuxml.jsp");
				//tree.setOnClickHandler(onNodeSelect);
		//Ext.get('application-tree').dom.src='${ctx}/index!menu.action';		
                Ext.get('application-info-iframe').dom.src='';
  				
           }
     };
       
}();
function onNodeSelect(id){
	switch(id){
		case "index":
			Ext.get('application-info-iframe').dom.src='${ctx}/portals/common/first.jsp';
			break;
		case "logout":
			Ext.MessageBox.confirm('提示', '您确定退出系统么?', logout);
			break;
		case "11":
			Ext.get('application-info-iframe').dom.src='${ctx}/register/reportAll.do';
			break;
		case "31":
			Ext.get('application-info-iframe').dom.src='${ctx}/admin/user.do';
			break;
		default:
		;
	}
}
function logout(btn){
	if(btn=='yes')
		document.location.href = '${ctx}/j_acegi_logout';
}
Ext.EventManager.onDocumentReady(LayoutFrame.init, LayoutFrame, true);
</script>

	</head>

	<body>

		<div id="header">
			<div id="main-page-logo">
				<div id="main-page-title" style="font-family:黑体;">
					内容管理系统
				</div>
			</div>
		</div>

		<div id="application-tree"></div>

		<div id="content" style="text-align: left;">
			<iframe id="application-info-iframe" src="" width="100%"
				height="100%" style="border:0px none;"></iframe>
		</div>
	</body>
</html>
