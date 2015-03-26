<%@ page contentType="text/html; charset=UTF-8"%>
<?xml version='1.0' encoding='UTF-8'?>
<%@ include file="/common/taglibs.inc"%>
<%response.setContentType("text/xml");%>
<tree id="0">
	<!--item text="主页" id="index" select="1">
	 <userdata name="url">${ctx}/index!main.action</userdata>
	</item-->	
	<item text="系统配置" id="config" open="1">
		<!--item text="组织管理" id="100" >
		<userdata name="url">${ctx}/base/portal.action</userdata>
		</item>	
		<item text="部门管理" id="102" >
		<userdata name="url">${ctx}/base/dept.action</userdata>
		</item-->				
		<item text="用户管理" id="101">
		<userdata name="url">${ctx}/base/user.action</userdata>
		</item>
		<item text="角色管理" id="104" >
		<userdata name="url">${ctx}/base/role.action</userdata>
		</item>				
		<!--item text="模块管理" id="103" >
		<userdata name="url">${ctx}/base/module.action</userdata>
		</item>			
		<item text="权限信息查询" id="105" >
		<userdata name="url">${ctx}/base/permission!listPerm.action</userdata>
		</item-->
		<!--item text="频道管理" id="106" > 
			<userdata name="url">${ctx}/cms/channel!list.action</userdata>
		</item-->
	        <item text="表单管理" id="106" >
			<userdata name="url">${ctx}/form/form.action</userdata>
		</item>			
	        <item text="模板管理" id="107" >
			<userdata name="url">${ctx}/cms/template!list.action</userdata>
		</item>	
		<!--item text="菜单管理" id="108" >
		<userdata name="url">${ctx}/base/menu.action</userdata>
		</item>				
		<item text="流程设置" id="110" >
		<userdata name="url"></userdata>
		
		<item text="流程定义" id="111" >
		<userdata name="url">${ctx}/workflow/webflow/index.jsp</userdata>
		</item>
		<item text="流程文件管理" id="115" >
		<userdata name="url">${ctx}/flow/xpdlFileManager.action</userdata>
		</item>		
		<item text="流程版本管理" id="112" >
		<userdata name="url">${ctx}/flow/versionManager.action</userdata>
		</item>
		<item text="流程人员映射" id="113" >
		<userdata name="url">${ctx}/flow/participantManager.action</userdata>
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
		</item-->
		
		<userdata name="url"></userdata>
		<item text="CMS权限资源管理" id="211" >
		<userdata name="url">${ctx}/cms/resource.action</userdata>			
		</item>
		<!--item text="栏目管理" id="178" >
		<userdata name="url">${ctx}/cms/column.action</userdata>
		</item-->	
		<item text="常用信息管理" id="179" >
		<userdata name="url">${ctx}/base/commoninfo!list.action</userdata>
		</item>	
		<item text="定时任务" id="180" >
		<userdata name="url">${ctx}/base/scheduler.action</userdata>
		</item>														
	</item>
	<item text="访问管理" id="5000">
		<userdata name="url"></userdata>
	 <item text="日志管理" id="5001">
		<userdata name="url">${ctx}/base/log.action</userdata>
	 </item>			
	</item>		
	<!--item text="业务模块" id="biz" open="1">
		<item text="简单例子" id="1001"/>
	</item-->
	<item text="sample" id="9000" >
		<userdata name="url"></userdata>
	<item text="公用选择" id="9001" >
		<userdata name="url">${ctx}/test/select.jsp</userdata>
	</item>	
	<item text="例子" id="9001" >
		<userdata name="url">${ctx}/test/sample.jsp</userdata>
	</item>			
	</item>			
	<item text="注销" id="logout">
		<userdata name="url">${ctx}/login!logout.action</userdata>
	</item>		
</tree>