package com.cyberway.cms.webservice.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cyberway.cms.Constants;
import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.document.service.DocumentCommonService;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.domain.Template;
import com.cyberway.cms.domain.TemplateGather;
import com.cyberway.cms.form.object.BaseDocument;
import com.cyberway.cms.site.cache.SiteCache;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.staticer.Configuration;
import com.cyberway.staticer.gather.PagePublisher;

/**
 * @author Dicky
 * @time 2012-9-24 16:40:59
 */
public class HtmlSynchroismService {

	private final static Logger _log = Logger.getLogger(HtmlSynchroismService.class);
	private ThreadPoolExecutor	_threadPoolExecutor;
	public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
		_threadPoolExecutor = threadPoolExecutor;
	}
	private final static String WSURL = com.cyberway.cms.Constants.HTML_SYNCHROISM;
	//每次发布文章需要删除全部文章的频道
	//private final static Long[] channelIds = new Long[]{127614L,127615L,128203L,128204L,128207L,128209L};
	//全站关联的频道
	private final static Long[] RELATION_ALL_CNIDS = new Long[]{144192L};
	private PagePublisher pagePublisher = (PagePublisher) ServiceLocator.getBean("cms.staticer.pagePublisher");
	private ChannelManagerService channelManagerService = (ChannelManagerService)ServiceLocator.getBean("channelManagerService");
	private SiteManagerService siteService = (SiteManagerService)ServiceLocator.getBean("siteManagerService");
	private DocumentCommonService docService = (DocumentCommonService) ServiceLocator.getBean("documentCommonService");
	private final static List<String> URL_LIST = new ArrayList<String>();

	public boolean deleteStaticHtmlBySiteId(Long siteId) {
		try {
			String[] urls = WSURL.split(";");
			for (int i = 0; i < urls.length; i++) {
				_threadPoolExecutor.execute(new HtmlSynchroismSer(urls[i], siteId, "siteId", "deleteStaticHtmlBySiteId"));
			}
			return true;
		} catch (Exception e) {
			_log.error("-删除站点失败---"+e.getMessage());
		}
		return false;
	}

	public boolean deleteStaticHtmlByChannelId(Long channelId) {
		try{
			List<Channel> list0 = channelManagerService.findByParent(channelId, false);
			deleteStaticHtmlByChannelId(channelId, list0.size()==0);
			for (Channel channel2 : list0) {
				deleteStaticHtmlByChannelId(channel2.getId());
			}
		}catch (Exception e) {
			_log.error("-删除频道失败 ---"+e.getMessage());
		}
		return true;
	}
	
	public boolean deleteStaticHtmlByChannelId(Long channelId, boolean isGather) {
		deleteGatherHtml(channelId, isGather);
		try {
			String[] urls = WSURL.split(";");
			for (int i = 0; i < urls.length; i++) {
				_threadPoolExecutor.execute(new HtmlSynchroismSer(urls[i], channelId, "channelId", "deleteStaticHtmlByChannelId"));
			}
			return true;
		} catch (Exception e) {
			_log.error("-删除频道失败----"+e.getMessage());
		}finally{
			deleteLocalStaticHtml(channelId);
		}
		return false;
	}
	
	public boolean deleteSummaryHtmlByChannelId(Long channelId) {
		deleteGatherHtml(channelId, true);
		try {
			String[] urls = WSURL.split(";");
			for (int i = 0; i < urls.length; i++) {
				_threadPoolExecutor.execute(new HtmlSynchroismSer(urls[i], channelId, "channelId", "deleteSummaryHtmlByChannelId"));
			}
			return true;
		} catch (Exception e) {
			_log.error("-删除频道概览模板失败----"+e.getMessage());
		}finally{
			deleteLocalStaticHtml(channelId);
		}
		return false;
	}

	/**
	 * 直接删除真个频道
	 * @param documentId
	 * @param channelId
	 * @return
	 */
	public boolean deleteStaticHtmlByDocumentId(Long documentId, Long channelId) {
		if(channelId==null ||  channelId==0){
			BaseDocument doc = docService.get(documentId);
			if(doc!=null){
				channelId = doc.getChannel().getId();
			}
		}
		deleteGatherHtml(channelId, true);
		return deleteStaticHtmlByChannelId(channelId);
	}
	
	/**
	 * 删除关联的HTML
	 * @author Dicky
	 * @time 2012-11-8 14:14:41
	 * @version 1.0
	 * @param channelId
	 * @param isGather 
	 */
	private void deleteGatherHtml(Long channelId, boolean isGather){
		try{
			if(isGather && channelId!=null && channelId>0){
				SiteCache siteCache = channelManagerService.getSiteCache();
				List<TemplateGather> sumTemplateGatherList = siteCache.getTemplateGathers(siteCache.getTemplateGathersKey(channelId, Template.TYPE_SUMMARY));
				for (TemplateGather gathar : sumTemplateGatherList) {
					deleteStaticHtmlByChannelId(gathar.getChannelId(), false);
				}
				List<TemplateGather> indexTemplateGatherList = siteCache.getTemplateGathers(siteCache.getTemplateGathersKey(channelId, Template.TYPE_INDEX));
				if(indexTemplateGatherList.size()>0){
					Channel channel = channelManagerService.getChannelFromCache(channelId);
					deleteIndexHtmlBySiteId(channel.getSite().getOid());
				}
				List<TemplateGather> detailTemplateGatherList = siteCache.getTemplateGathers(siteCache.getTemplateGathersKey(channelId, Template.TYPE_DETAILS));
				for (TemplateGather gathar : detailTemplateGatherList) {
					deleteStaticHtmlByChannelId(gathar.getChannelId(), false);
				}
				for (int i = 0; i < RELATION_ALL_CNIDS.length; i++) {
					deleteStaticHtmlByChannelId(RELATION_ALL_CNIDS[i], false);
				}
			}
		}catch (Exception e) {
			_log.error("-删除文档失败 ----"+e.getMessage());
		}
	}
	
	public boolean deleteIndexHtmlBySiteId(Long siteId) {
		try {
			String[] urls = WSURL.split(";");
			for (int i = 0; i < urls.length; i++) {
				_threadPoolExecutor.execute(new HtmlSynchroismSer(urls[i], siteId, "siteId", "deleteIndexHtmlBySiteId"));
			}
			return true;
		} catch (Exception e) {
			_log.error("-删除站点首页失败-"+"----"+e.getMessage());
		}
		return false;
	}
	
	private static final String CHANNEL_SEP = "C_";
	
	private void deleteLocalStaticHtml(final Long channelId){
		_threadPoolExecutor.execute(new Runnable(){
			public void run() {
				try{
					if(channelId!=null){
						Channel channel = channelManagerService.getChannelFromCache(channelId);
						CmsSite site = channel.getSite();
						Long pid = channel.getParent()!=null ? channel.getParent().getId() : channel.getId();
						deleteLocalStaticHtmlByParent(pid, site.getSitehttp());
					}
				}catch (Exception e) {
					_log.error("-删除频道HMTL失败-channelId:"+channelId+"----"+e.getMessage());
				}
			}
		});
	}
	public void deleteLocalHtmlBySite(final Long siteId){
		_threadPoolExecutor.execute(new Runnable(){
			public void run() {
				try{
					if(siteId!=null){
						CmsSite cmsSite = siteService.getSiteFromCache(siteId);
						String path = Constants.STATICHTML_ABSOLUTE_PATH + cmsSite.getSitehttp();
						File file = new File(path);
						if(file.exists()){
							FileUtils.deleteDirectory(file);
						}
					}
				}catch (Exception e) {
					_log.error("-删除站点HMTL失败->siteId:"+siteId+"----"+e.getMessage());
				}
			}
		});
	}
	
	private void deleteLocalStaticHtmlByParent(Long channelId, String sitehttp){
		try{
			List<Channel> list1 = channelManagerService.findByParent(channelId, true);
			for (Channel channel2 : list1) {
				String path = Constants.STATICHTML_ABSOLUTE_PATH + sitehttp + File.separator + CHANNEL_SEP + channel2.getId();
				File file = new File(path);
				if(file.exists()){
					FileUtils.deleteDirectory(file);
				}
				deleteLocalStaticHtmlByParent(channel2.getId(), sitehttp);
			}
		}catch (Exception e) {
			_log.error("-删除频道HMTL失败-"+"----"+e.getMessage());
		}
	}
	
	/**
	 * 运行平台通知其发布静态文件。
	 * @author Dicky
	 * @time 2012-11-7 16:21:21
	 * @version 1.0
	 * @param url
	 * @param channelId
	 * @param templateId
	 * @param roleCode
	 */
	public void publishStaticHtml(String url, Long channelId, Long templateId, String roleCode){
		String key = url+roleCode;
		try{
			if(URL_LIST.contains(key)){
				return;
			}else{
				URL_LIST.add(key);
			}
			CmsSite site = null;
			if(StringUtils.isNotBlank(url)){
				if(channelId!=null && channelId>0){
					Channel channel = channelManagerService.getChannelFromCache(channelId);
					if(!new Long(2).equals(channel.getIsOpenChannel())){
						roleCode=Configuration.defaultRole();
					}
					site = channel.getSite();
				}else if(url.indexOf("siteId")>0){
					try{
						String id = url.substring(url.indexOf("siteId=")+7);
						if(id.indexOf("&")>0){
							id = id.substring(0,id.indexOf("&"));
						}
						Long siteId = Long.valueOf(id);
						site = siteService.getSiteFromCache(siteId);
					}catch (Exception e) {
						URL_LIST.remove(key);
						return;
					}
				}else{
					URL_LIST.remove(key);
					return;
				}
				if(!new Integer(1).equals(site.getIsLogined())){
					roleCode=Configuration.defaultRole();
				}
				Map<String, String[]> variables = new HashMap<String, String[]>();
				if(url.indexOf("templateId")==-1 && url.indexOf("view")==-1){
					variables.put("templateId", new String[]{templateId.toString()});
				}
				if(url.indexOf("__urlKey")==-1){
					Long urlKey = System.currentTimeMillis();
					variables.put("__urlKey", new String[]{urlKey.toString()});
				}
				if(url.indexOf("__localRole")==-1){
					variables.put("__localRole", new String[]{roleCode});
				}
				url = Constants.CMS_HYPERTEXT_TRANSFER_PROTOCOL + site.getSitehttp() + ":" + site.getSiteport() + url;
				pagePublisher.publish(url, variables, roleCode, false);
				URL_LIST.remove(key);
			}
		}catch (Exception e) {
			_log.error("-异步分失败-"+"----"+e.getMessage());
		}finally{
			URL_LIST.remove(key);
		}
	}
	
	static class HtmlSynchroismSer implements  Runnable {
		private Service service = null;
		private Call call = null;
		private String _url  = null;
		private Long _id  = null;
		private String _param = null;
		private String _method  = null;
		
		public HtmlSynchroismSer(String url, Long id, String param, String method) {
			_url = url;
			_id = id;
			_param = param;
			_method = method;
		}
		
		public void run() {
			try {
				service = new Service();
				call = (Call) service.createCall();
				call.setTargetEndpointAddress(new java.net.URL(_url));
				call.setOperationName(_method);
				call.addParameter(_param,
						org.apache.axis.encoding.XMLType.XSD_LONG,
						javax.xml.rpc.ParameterMode.IN);
				call.setReturnType(org.apache.axis.encoding.XMLType.XSD_BOOLEAN);
				call.setUseSOAPAction(true);
				call.invoke(new Object[]{_id});
			} catch (Exception e) {
				_log.error("-删除HTML->"+_param+":"+_id+"----"+e.getMessage());
			}finally{
				service=null;
				call=null;
			}
		}
	}
}
