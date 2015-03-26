<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script src="${ctx}/common/prototype/prototype.js" type="text/javascript"></script>
<script src="/common/validation/prototype.js" type="text/javascript"></script>
<script src="${ctx}/common/validation/validation.js" type="text/javascript"></script>
<script src="${ctx}/dwr/engine.js" type="text/javascript"></script>
<script src="${ctx}/dwr/util.js" type="text/javascript"></script>
<script src="${ctx}/common/frame_js/select.js" type="text/javascript"></script>
<script src="${ctx}/common/jstemplate/template.js" type="text/javascript"></script>
<script src="${ctx}/common/core_js/checkall.js" type="text/javascript"></script>
<script src="${ctx}/common/core_js/common.js" type="text/javascript"></script>
<script src="${ctx}/common/core_js/core.js" type="text/javascript"></script>
<script src="${ctx}/common/fckeditor/fckeditor.js" type="text/javascript"></script>
<script src="${ctx}/common/tag/tag.js" type="text/javascript"></script>
<script src="${ctx}/common/ec/js/ecside.js" type="text/javascript"></script>
<script src="${ctx}/common/ec/js/ecside_msg_utf8_cn.js" type="text/javascript"></script>
<script src="${ctx}/common/datepicker/WdatePicker.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${default_style}" />
<link rel="stylesheet" type="text/css" href="${ctx}/common/validation/style.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/common/ec/css/ecside_style.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/demo.css" />
<script src="${ctx}/common/cms_js/cms.js" type="text/javascript"></script>
<script src="${ctx}/common/attachment/attachment.js" type="text/javascript"></script>
<!-- <script src="${ctx}/common/attachment/xml.js" type="text/javascript"></script> -->
<script src="${ctx}/common/attachment/json.js" type="text/javascript"></script>
<script src="${ctx}/common/cms_js/util.js" type="text/javascript"></script>
<script src="${ctx}/common/menu/dbMenu.js" type="text/javascript"></script>
<script type="text/javascript">	
 var ctx='${ctx}';
 var siteId='${siteId}'
 var channelId='${channelId}';	
 var Id = '${id}';
 var BeforsaveExeJS=new Array;
    //DWREngine.setErrorHandler(errorHandler);	
    function errorHandler(errorString, exception)
    {
        alert(errorString);
    }
    function getExt(){
    	return top.Ext;
    	}  
    function getExtMessageBox(){
    	if(getExt())
    	 return getExt().MessageBox;
    	else 
    	 return null;
    	}						 
</script>
<xml id="myXML"></xml>
<ww:head />