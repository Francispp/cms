<%@ page contentType="text/html; charset=UTF-8"%>
<?xml version='1.0' encoding='UTF-8'?>
<%@ include file="/common/taglibs.inc"%>
<%response.setContentType("text/xml");%>
<tree id="0">
	<!--item text="主页" id="index" select="1">
	 <userdata name="url">${ctx}/index!main.action</userdata>
	</item-->	
	<item text="系统配置" id="config" open="1">
	        <c:if test="${session.loginer.isAdminUser}">
	        <item text="门户系统管理" id="199" >
		<userdata name="url">${ctx}/base/portal.action</userdata>
		</item>
		</c:if>
		
		
		<cms:auth resCode="SYS_ORG_MANAGER">
		<item text="组织管理" id="100" >
		<userdata name="url">${ctx}/base/org.action</userdata>
		</item>
		</cms:auth>
		
		
		<cms:auth resCode="SYS_DEPT_VIEW">	
		<item text="部门管理" id="102" >
		<userdata name="url">${ctx}/base/dept.action</userdata>
		</item>
		</cms:auth>
		<cms:auth resCode="SYS_ROLE_VIEW">
		<item text="角色管理" id="104" >
		<userdata name="url">${ctx}/base/role.action</userdata>
		</item>	
		</cms:auth>		
		<cms:auth resCode="SYS_USER_VIEW">				
		<item text="用户管理" id="101">
		<userdata name="url">${ctx}/base/user.action</userdata>
		</item>
		</cms:auth>
		
		<cms:auth resCode="SYS_ROLE_VIEW">			
		<item text="级别管理" id="155" >
		<userdata name="url">${ctx}/base/grade!list.action</userdata>
		</item>
		</cms:auth>
		<cms:auth resCode="SYS_ROLE_VIEW">			
		<item text="身份管理" id="156" >
		<userdata name="url">${ctx}/base/identity!list.action</userdata>
		</item>
		</cms:auth>
		<cms:auth resCode="SYS_MODULE_VIEW">			
		<item text="模块管理" id="103" >
		<userdata name="url">${ctx}/base/module.action</userdata>
		</item>
		</cms:auth>
		
		<cms:auth resCode="SYS_PERMISSION_VIEW">		
		<item text="权限信息查询" id="105" >
		<userdata name="url">${ctx}/base/permission!listPerm.action</userdata>
		</item>
		</cms:auth>
		
	        <!--item text="模板管理" id="107" >
			<userdata name="url">${ctx}/cms/template!list.action</userdata>
		</item-->	
		<!--item text="菜单管理" id="108" >
		<userdata name="url">${ctx}/base/menu.action</userdata>
		</item-->
			<!-- 		
		<item text="流程设置" id="110" >
		<userdata name="url"></userdata>
		
		<item text="流程定义" id="111" >
		<userdata name="url">${ctx}/workflow/webflow/index.jsp</userdata>
		</item>
		<item text="流程人员映射" id="113" >
		<userdata name="url">${ctx}/flow/participantManager.action</userdata>
		</item>
		<item text="流程文件管理" id="115" >
		<userdata name="url">${ctx}/flow/xpdlFileManager.action</userdata>
		</item>		
		<item text="流程版本管理" id="112" >
		<userdata name="url">${ctx}/flow/versionManager.action</userdata>
		</item>
		
		</item>																		
		<item text="流程监控" id="120" >
		<userdata name="url"></userdata>
		<item text="流程状态监控管理" id="121" >
		<userdata name="url">${ctx}/flow/processMonitor.action</userdata>
		</item>
		<item text="任务管理" id="122" >
		<userdata name="url">${ctx}/flow/processMonitor!assigns.action</userdata>
		</item>				
		</item>
		
		<item text="信息抓取" id="130" >
		<userdata name="url"></userdata>
		
		<item text="控制台" id="131" >
		<userdata name="url">${ctx}/crawl/jobs!reStart.action</userdata>
		</item>
		<item text="抓取任务" id="132" >
		<userdata name="url">${ctx}/crawl/jobs!list.action</userdata>
		</item>
		<item text="提取表达式" id="133" >
		<userdata name="url">${ctx}/crawl/regular!list.action</userdata>
		</item>
		</item>
		
		<item text="CMS2数据导入" id="140">
		<userdata name="url">${ctx}/cms2/dbwork!tabbar.action</userdata>
		</item>
		<item text="XML数据导入" id="141">
		<userdata name="url">${ctx}/biz/index!tabbar.action</userdata>
		</item>
		
		<item text="站点数据导入" id="142">
		<userdata name="url">${ctx}/cms2/siteData!tabbar.action</userdata>
		</item>
		
		<item text="定时任务" id="180" >
		<userdata name="url">${ctx}/base/scheduler.action</userdata>
		</item>	
		 -->
		<item text="邮件模板管理" id="181" >
		<userdata name="url">${ctx}/base/email!list.action</userdata>
		</item>	
		<item text="更改用户密码" id="185" >
		<userdata name="url">${ctx}/base/user!updateUserPWD.action</userdata>
		</item>														
	</item>
	<item text="CMS管理" id="4000">
		<userdata name="url"></userdata>
		<cms:auth resCode="CMS_BASE_FORM_VIEW">
	        <item text="表单管理" id="106" >
			<userdata name="url">${ctx}/form/form.action</userdata>
		</item>
		</cms:auth>		
		<cms:auth resCode="CMS_BASE_PERMRESOURE_VIEW">
		<userdata name="url"></userdata>
		<item text="权限资源管理" id="211" >
		<userdata name="url">${ctx}/cms/resource.action</userdata>			
		</item>
		</cms:auth>		
		<!--item text="栏目管理" id="178" >
		<userdata name="url">${ctx}/cms/column.action</userdata>
		</item-->
		<cms:auth resCode="CMS_COMMON_TYPE_VIEW">
		<item text="常用类别管理" id="178" >
		<userdata name="url">${ctx}/base/commontype!list.action</userdata>
		</item>	
		
		</cms:auth>
		<cms:auth resCode="CMS_COMMON_INFO_VIEW">	
		<item text="常用信息管理" id="179" >
		<userdata name="url">${ctx}/base/commoninfo!list.action</userdata>
		</item>
		</cms:auth>
		
		  <item text="分发配置管理" id="101185">
	     	<userdata name="url">${ctx}/base/ftpService.action</userdata>
	      </item>	
	      
		<cms:auth resCode="CMS_COMMON_JSFUNCTION_VIEW">
		<item text="JS组件管理" id="107" >
			<userdata name="url">${ctx}/component/jsfunction.action</userdata>
		</item>	
		</cms:auth>	
		<item text="菜单管理" id="108" >
		<userdata name="url">${ctx}/base/menu.action</userdata>
		</item>
		<item text="外部用户管理" id="109" >
			<userdata name="url">${ctx}/cms/webuser!list.action</userdata>
		</item>
		<item text="页面缓存" id="10116" >
			<userdata name="url">${ctx}/cms/cmscacheurl!list.action</userdata>
		</item>
		<item text="留言管理" id="10117" >
			<userdata name="url">${ctx}/component/leaveword!list.action</userdata>
		</item> 
		<item text="信息共享管理" id="10119" >
			<userdata name="url">${ctx}/component/docShareRelation!list.action</userdata>
		</item>
		<item text="缓存管理" id="10120">
			<userdata name="url">${ctx}/cms/cmsCache.action</userdata> 
		</item>
		<item text="订阅管理" id="10118">
			<userdata name="url"></userdata>
		  <item text="用户订阅" id="101181">
			<userdata name="url">${ctx}/component/subcreibe!list.action</userdata>
		  </item>		
		  <item text="站点邮件服务" id="101182" >
			<userdata name="url">${ctx}/component/emailcfg!list.action</userdata>
		  </item>
		  <item text="频道费用设置" id="101183" >
			<userdata name="url">${ctx}/component/channelfee!list.action</userdata>
		  </item>
		   <item text="邮件发送日志" id="101184" >
			<userdata name="url">${ctx}/base/emaillog!list.action</userdata>
		  </item>
		 
		</item> 
	
		<item text="网络问卷" id="090620" >
			<userdata name="url"></userdata>
			<item text="问卷调查" id="survey">
				<userdata name="url">${ctx}/survey/questionnaire!list.action</userdata>
			</item>
			<item text="问卷问题列表" id="survey1">
				<userdata name="url">${ctx}/survey/question!list.action</userdata>
			</item>
		</item>
	</item>	
	<cms:auth resCode="SYS_LOG_VIEW">
	<item text="访问管理" id="5000">
		<userdata name="url"></userdata>
	 <item text="日志管理" id="5001">
		<userdata name="url">${ctx}/base/log.action</userdata>
	 </item>
	<!-- <item text="访问文档统计" id="5002">
		<userdata name="url">${ctx}/base/visit!selectList.action</userdata>
	 </item>
			-->
	</item>	
	</cms:auth>		
	<!--item text="业务模块" id="biz" open="1">
		<item text="简单例子" id="1001"/>
	</item-->
	<!-- 
	<item text="报表管理" id="2000">
		<userdata name="url"></userdata>
	  <item text="报表导出管理" id="2001">
		<userdata name="url">${ctx}/base/report!exportlist.action</userdata>
	  </item>		
	  <item text="报表配置管理" id="2002" >
		<userdata name="url">${ctx}/base/report.action</userdata>
	  </item>	 	
	</item>	 
	
	
	<item text="sample" id="9000" >
		<userdata name="url"></userdata>
	<item text="公用选择" id="9001" >
		<userdata name="url">${ctx}/test/select.jsp</userdata>
	</item>	
	<item text="例子" id="9001" >
		<userdata name="url">${ctx}/test/sample.jsp</userdata>
	</item>			
	</item>

	<item text="流程任务" id="biz" open="1">
		<item text="待办在办" id="100001" >
		<userdata name="url">${ctx}/flow/assign.action?flowstate=0</userdata>
		</item>
		<item text="待办" id="100002" >
		<userdata name="url">${ctx}/flow/assign.action?flowstate=1</userdata>
		</item>
		<item text="在办" id="100003" >
		<userdata name="url">${ctx}/flow/assign.action?flowstate=2</userdata>
		</item>
	 <item text="flow sample" id="biz_sub" open="0">
		<item text="待办在办" id="1100001" >
		<userdata name="url">${ctx}/sample/flow.action?flowstate=0</userdata>
		</item>
		<item text="待办" id="1100002" >
		<userdata name="url">${ctx}/sample/flow.action?flowstate=1</userdata>
		</item>
		<item text="在办" id="1100003" >
		<userdata name="url">${ctx}/sample/flow.action?flowstate=2</userdata>
		</item>
		<item text="未完成" id="1100004" >
		<userdata name="url">${ctx}/sample/flow.action?flowstate=5</userdata>
		</item>
		<item text="已完成" id="1100005" >
		<userdata name="url">${ctx}/sample/flow.action?flowstate=6</userdata>
		</item>
		<item text="已终止" id="1100006" >
		<userdata name="url">${ctx}/sample/flow.action?flowstate=7</userdata>
		</item>		
		
		 											
	</item>
						
</item>		
		-->
	<item text="内容 分发" id="ContentDistribution">
		<!-- <userdata name="url">${ctx}/base/distribution!addFile.action</userdata>
	
	
	<userdata name="url">${ctx}/base/distribution!tree.action</userdata>  
	-->	
	<userdata name="url">${ctx}/base/distribution!uploadFTP.action</userdata>
	
	</item>	
			
	<item text="视频专辑" id="mediavideo">
	<userdata name="url">${ctx}/base/album.action?albumType=video</userdata> 
	</item>	
	<item text="音频专辑" id="mediaaudio">
	<userdata name="url">${ctx}/base/album.action?albumType=audio</userdata> 
	</item>
	<item text="常用数据类型" id="commonDataType">
	<userdata name="url">${ctx}/cms/selectlist.action</userdata> 
	</item>
	<item text="注销" id="logout">
		<userdata name="url">${ctx}/login!logout.action</userdata>
	</item>		
</tree>