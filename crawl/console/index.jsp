<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="org.archive.crawler.admin.CrawlJobHandler"%>
<%@ include file="/common/taglibs.inc"%>
<!--force IE into Quirks Mode-->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>控制台</title>
<%@ include file="/common/meta.inc"%>
<%@ include file="/common/ext/ext-res.inc"%>
<%@ include file="/common/js.inc"%>
<%@ include file="/common/dhtmlxMenu/menu.inc"%>
<link href="${sytlePath}/main_style.css" rel="stylesheet" type="text/css"/>
<link href="${sytlePath}/heritrix.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript">
        function doTerminateCurrentJob(){
            if(confirm("确定要结束当前的抓取任务吗？")){
                document.location = '<%out.print(request.getContextPath());%>/console/action.jsp?action=terminate';
            }
        }    
    </script>
</head>
<body>
  <script type="text/javascript">
        function doTerminateCurrentJob(){
            if(confirm("确定要结束当前抓取任务吗?")){
                document.location = '${ctx}/crawl/console!TerminateJob.action';
            }
        }    
    </script>
    
    <table border="0" cellspacing="0" cellpadding="0"><tr><td>
    <fieldset style="width: 750px">
        <legend> 
        <b><span class="legendTitle">抓取状态:</span> 
        <ww:if test="running">
         <a href='${ctx}/crawl/console!stopJobs.action'>停止</a>
        
        </ww:if>
        <ww:else>
      <a href='${ctx}/crawl/console!startJobs.action'>启动</a>
        </ww:else>
 </b>
        </legend>
        <div style="float:right;padding-right:50px;">
	        <b>内存占用</b><br>
	        <div style="padding-left:20px">
		        <%=(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/1024%> KB 
		        used<br>
		        <%=(Runtime.getRuntime().totalMemory())/1024%> KB
		        current heap<br>
		        <%=(Runtime.getRuntime().maxMemory())/1024%> KB
		        max heap
	        </div>
	    </div>
        
        <div style="padding-left:20px">
        <br>
        <b>任务:</b>
			<br>
			
	        ${pending} 个等待状态
	        ${completed} 个完成状态
        </div>

         </fieldset>
          <br>
            <fieldset style="width: 750px">
               <legend>
                <b><span class="legendTitle">任务状态:</span>
<ww:if test="crawling">         
<ww:if test="resume">
 <a href='${ctx}/crawl/console!ResumeJob.action'>唤醒</a>
 </ww:if>
 <ww:else>
<a href='${ctx}/crawl/console!PauseJob.action'>暂停</a> 
</ww:else>
 | <a href='javascript:doTerminateCurrentJob();'>结束</a>
 </ww:if>
               </legend>

             
                	<div style="float:right; padding-right:50px;">
                	<br>
                    <b>进度：</b>
                	<ww:if test="currentJob!= null">
                            <center>
                            <table border="0" cellpadding="0" cellspacing= "0" width="600"> 
                                <tr>
                                    <td align='right' width="25%">&nbsp;</td>
                                    <td class='completedBar' width="${completedBar}" align="right">
                                    &nbsp; <ww:if test="ratio < 50"><b>${ratio}%</b></ww:if>      
                                    </td>
                                    <td class='queuedBar' align="left" width="${queuedBar}">
                                   &nbsp; <ww:if test="ratio > 50"><b>${ratio}%</b></ww:if> 
                                    </td>
                                    <td width="25%" nowrap>&nbsp;</td>
                                </tr>
                            </table>  
                    		
                            </center>
</ww:if>
            	
	       
    </fieldset>
    </td></tr>
 </table>
</body>
</html>