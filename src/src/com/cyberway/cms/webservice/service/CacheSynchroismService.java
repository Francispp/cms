package com.cyberway.cms.webservice.service;

import java.util.concurrent.ThreadPoolExecutor;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;

import com.cyberway.cms.Constants;
import com.cyberway.core.utils.StringUtil;

public class CacheSynchroismService {
	private final static Logger _log = Logger.getLogger(CacheSynchroismService.class);
	private ThreadPoolExecutor	_threadPoolExecutor;

	public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
		_threadPoolExecutor = threadPoolExecutor;
	}

	String	cache	= Constants.CACHE_SYNCHROISM;

	public void updateSite(Long siteId, String type) {
		if (StringUtil.isEmpty(cache) || StringUtil.ifEqual("/", cache)) {
			return;
		} else if (cache.indexOf(";") > 0) {
			String[] caches = cache.split(";");
			for (String str : caches) {
				_threadPoolExecutor.execute(new SiteSynchroism(str, siteId, type));
			}
		} else {
			_threadPoolExecutor.execute(new SiteSynchroism(cache, siteId, type));
		}
	}

	static class SiteSynchroism implements Runnable {
		Service		   service	= null;
		Call		   call		= null;
		private String	_url;
		private Long	_siteId;
		private String	_type;

		public SiteSynchroism(String url, Long siteId, String type) {
			_url = url;
			_siteId = siteId;
			_type = type;
		}

		public void run() {
			try {
				service = new Service();
				call = (Call) service.createCall();
				call.setTargetEndpointAddress(new java.net.URL(_url));
				call.setOperationName("updateSite");
				call.addParameter("siteId", org.apache.axis.encoding.XMLType.XSD_LONG, javax.xml.rpc.ParameterMode.IN);
				call.addParameter("type", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
				call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型
				call.setUseSOAPAction(true);
				call.invoke(new Object[] { _siteId, _type });
			} catch (Exception ex) {
				_log.error("-站点缓存更新失败->siteId:"+_siteId+", type:"+_type, ex);
			}
		}
	}

	public void updateCommonInfo(Long cciId, String opt) {
		if (StringUtil.isEmpty(cache) || StringUtil.ifEqual("/", cache)) {
			return;
		} else if (cache.indexOf(";") > 0) {
			String[] caches = cache.split(";");
			for (String str : caches) {
				_threadPoolExecutor.execute(new CommonInfoSynchroism(str, cciId, opt));
			}
		} else {
			_threadPoolExecutor.execute(new CommonInfoSynchroism(cache, cciId, opt));
		}
	}

	static class CommonInfoSynchroism implements Runnable {
		Service		   service	= null;
		Call		   call		= null;
		private String	_url;
		private Long	_cciId;
		private String	_opt;

		public CommonInfoSynchroism(String url, Long cciId, String opt) {
			_url = url;
			_cciId = cciId;
			_opt = opt;
		}

		public void run() {
			try {
				service = new Service();
				call = (Call) service.createCall();
				call.setTargetEndpointAddress(new java.net.URL(_url));
				call.setOperationName("updateCommonInfoCache");
				call.addParameter("cciId", org.apache.axis.encoding.XMLType.XSD_LONG, javax.xml.rpc.ParameterMode.IN);
				call.addParameter("opt", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
				call.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);// 设置返回类型
				call.setUseSOAPAction(true);
				call.invoke(new Object[] { _cciId, _opt });
				_log.info("-同步缓存成功-");
			} catch (Exception ex) {
				_log.error("-CommonInfo缓存更新失败-", ex);
			}
		}
	}
	
	public void updateCommonType(Long cctId, String opt) {
		if (StringUtil.isEmpty(cache) || StringUtil.ifEqual("/", cache)) {
			return;
		} else if (cache.indexOf(";") > 0) {
			String[] caches = cache.split(";");
			for (String str : caches) {
				_threadPoolExecutor.execute(new CommonTypeSynchroism(str, cctId, opt));
			}
		} else {
			_threadPoolExecutor.execute(new CommonTypeSynchroism(cache, cctId, opt));
		}
	}
	
	static class CommonTypeSynchroism implements Runnable {
		Service		   service	= null;
		Call		   call		= null;
		private String	_url;
		private Long	_cctId;
		private String	_opt;

		public CommonTypeSynchroism(String url, Long cctId, String opt) {
			_url = url;
			_cctId = cctId;
			_opt = opt;
		}

		public void run() {
			try {
				service = new Service();
				call = (Call) service.createCall();
				call.setTargetEndpointAddress(new java.net.URL(_url));
				call.setOperationName("updateCommonTypeCache");
				call.addParameter("cctId", org.apache.axis.encoding.XMLType.XSD_LONG, javax.xml.rpc.ParameterMode.IN);
				call.addParameter("opt", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
				call.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);// 设置返回类型
				call.setUseSOAPAction(true);
				call.invoke(new Object[] { _cctId, _opt });
				_log.info("-同步缓存成功-");
			} catch (Exception ex) {
				_log.error("-CommonType缓存更新失败-", ex);
			}
		}
	}
	
	public void updateChannel(Long channelId, String type) {
		if (StringUtil.isEmpty(cache) || StringUtil.ifEqual("/", cache)) {
			return;
		} else if (cache.indexOf(";") > 0) {
			String[] caches = cache.split(";");
			for (String str : caches) {
				_threadPoolExecutor.execute(new ChannelSynchroism(str, channelId, type));
			}
		} else {
			_threadPoolExecutor.execute(new ChannelSynchroism(cache, channelId, type));
		}
	}

	static class ChannelSynchroism implements Runnable {
		Service		   service	= null;
		Call		   call		= null;
		private String	_url;
		private Long	_channelId;
		private String	_type;

		public ChannelSynchroism(String url, Long channelId, String type) {
			_url = url;
			_channelId = channelId;
			_type = type;
		}

		public void run() {
			try {
				service = new Service();
				call = (Call) service.createCall();
				call.setTargetEndpointAddress(new java.net.URL(_url));
				call.setOperationName("updateChannel");
				call.addParameter("channelId", org.apache.axis.encoding.XMLType.XSD_LONG, javax.xml.rpc.ParameterMode.IN);
				call.addParameter("type", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
				call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型
				call.setUseSOAPAction(true);
				call.invoke(new Object[] { _channelId, _type });
				_log.info("-同步缓存成功-");
			} catch (Exception ex) {
				_log.error("-频道缓存更新失败-", ex);
			}
		}
	}

	public void updateCmsPermission(String recordKey, String type) {
		if (StringUtil.isEmpty(cache) || StringUtil.ifEqual("/", cache)) {
			return;
		} else if (cache.indexOf(";") > 0) {
			String[] caches = cache.split(";");
			for (String str : caches) {
				_threadPoolExecutor.execute(new CmsPermissionSynchroism(str, recordKey, type));
			}
		} else {
			_threadPoolExecutor.execute(new CmsPermissionSynchroism(cache, recordKey, type));
		}
	}

	static class CmsPermissionSynchroism implements Runnable {
		Service		   service	= null;
		Call		   call		= null;
		private String	_url;
		private String	_recordKey;
		private String	_type;

		public CmsPermissionSynchroism(String url, String recordKey, String type) {
			_url = url;
			_recordKey = recordKey;
			_type = type;
		}

		public void run() {
			try {
				service = new Service();
				call = (Call) service.createCall();
				call.setTargetEndpointAddress(new java.net.URL(_url));
				if("del".equals(_type)){
					call.setOperationName("deleteCmsPermission");
					call.addParameter("recordKey", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
					call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型
					call.setUseSOAPAction(true);
					call.invoke(new Object[] {_recordKey});
				}else{
					call.setOperationName("updateCmsPermission");
					call.addParameter("recordKey", org.apache.axis.encoding.XMLType.XSD_LONG, javax.xml.rpc.ParameterMode.IN);
					call.addParameter("type", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
					call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型
					call.setUseSOAPAction(true);
					call.invoke(new Object[] { Long.valueOf(_recordKey), _type });
				}
			} catch (Exception ex) {
				_log.error("-权限更新失败-", ex);
			}
		}
	}
	
	/**
	 * 更新运行平台角色缓存
	 * @param id 角色对象id
	 */
	public void updateRole(Long id) {
		if (StringUtil.isEmpty(cache) || StringUtil.ifEqual("/", cache)) {
			return;
		} else if (cache.indexOf(";") > 0) {
			String[] caches = cache.split(";");
			for (String str : caches) {
				_threadPoolExecutor.execute(new UpdateRoleSynchroism(str, id));
			}
		} else {
			_threadPoolExecutor.execute(new UpdateRoleSynchroism(cache, id));
		}
	}

	static class UpdateRoleSynchroism implements Runnable {
		Service		   service	= null;
		Call		   call		= null;
		private String	_url;
		private Long	_id;

		public UpdateRoleSynchroism(String url, Long id) {
			_url = url;
			_id = id;
		}

		public void run() {
			try {
				service = new Service();
				call = (Call) service.createCall();
				call.setTargetEndpointAddress(new java.net.URL(_url));
				call.setOperationName("updateRole");
				call.addParameter("id", org.apache.axis.encoding.XMLType.XSD_LONG, javax.xml.rpc.ParameterMode.IN);
				call.setReturnType(org.apache.axis.encoding.XMLType.XSD_BOOLEAN);// 设置返回类型
				call.setUseSOAPAction(true);
				call.invoke(new Object[] { _id });
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * 删除运行平台角色缓存
	 * @param id 角色对象id
	 */
	public void deleteRole(Long id) {
		if (StringUtil.isEmpty(cache) || StringUtil.ifEqual("/", cache)) {
			return;
		} else if (cache.indexOf(";") > 0) {
			String[] caches = cache.split(";");
			for (String str : caches) {
				_threadPoolExecutor.execute(new DeleteRoleSynchroism(str, id));
			}
		} else {
			_threadPoolExecutor.execute(new DeleteRoleSynchroism(cache, id));
		}
	}

	static class DeleteRoleSynchroism implements Runnable {
		Service		   service	= null;
		Call		   call		= null;
		private String	_url;
		private Long	_id;

		public DeleteRoleSynchroism(String url, Long id) {
			_url = url;
			_id = id;
		}

		public void run() {
			try {
				service = new Service();
				call = (Call) service.createCall();
				call.setTargetEndpointAddress(new java.net.URL(_url));
				call.setOperationName("deleteRole");
				call.addParameter("id", org.apache.axis.encoding.XMLType.XSD_LONG, javax.xml.rpc.ParameterMode.IN);
				call.setReturnType(org.apache.axis.encoding.XMLType.XSD_BOOLEAN);// 设置返回类型
				call.setUseSOAPAction(true);
				call.invoke(new Object[] { _id });
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
