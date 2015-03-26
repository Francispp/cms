package com.cyberway.cms.exposed;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import ognl.Ognl;
import ognl.OgnlException;

import org.ecside.table.limit.Limit;

import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.document.service.DocumentCommonService;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.form.domain.BasicDocument;
import com.cyberway.cms.form.domain.BizDocument2;
import com.cyberway.cms.form.object.BaseDocument;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;

/**
 * @author caiw
 * 文档对外的服务接口
 *
 */
public class DocumentServiceJsUtil {

	private DocumentCommonService documentCommonService;

	public DocumentCommonService getDocumentCommonService() {
		return documentCommonService;
	}

	public void setDocumentCommonService(DocumentCommonService documentCommonService) {
		this.documentCommonService = documentCommonService;
	}

	/**
	 * 获得指定文档信息
	 * @param docid
	 * @return
	 */
	public BaseDocument getDocument(Long docid){
		BaseDocument doc=null;
		try{
			doc=documentCommonService.getDocument(docid);
		}catch(Exception e){
			e.printStackTrace();
		}
		return doc;
	}
	/**
	 * 获得文档序号
	 * @return
	 */
	public synchronized Long getSequence(){
		return documentCommonService.getSequence();
	}
	
	/**
	 * 查询某频道下的已发布信息
	 * @param limit
	 * @param channelid
	 * @param where
	 * @return
	 */
	public Page findByPublishPage(Limit limit,Long channelid,String where){
		Page page=null;
		try{
			ChannelManagerService channelManagerService = (ChannelManagerService)ServiceLocator.getBean ("channelManagerService");
			
			if(documentCommonService == null){
				documentCommonService = (DocumentCommonService)ServiceLocator.getBean ("documentCommonService");
			}
			
			page=documentCommonService.findByPublishPage (limit, new CriteriaSetup (), channelManagerService.getChannelFromCache(channelid),where);
		}catch(Exception e){
			e.printStackTrace();
		}
		return page==null? new Page() : page;
	}
	
	/**
	 * 查询指定栏目下，满足条件的信息
	 * @param channelid
	 * @param where
	 * @return
	 */
	public List getByPublicDocs(Long channelid,String where){
		Limit limit = new com.cyberway.cms.tags.components.DocumentIterator.Limit (1, 100, null, "id", false);
		return (List)findByPublishPage(limit,channelid,where).getData();
	}
	/**
	 * 获得指定频道下，满足条件的第一条记录
	 * @param channelid
	 * @param where
	 * @return
	 */
	public Object getFirstDoc(Long channelid,String where){
		List docs=getByPublicDocs(channelid,where);
		if(docs!=null && docs.size()>0)
			return docs.get(0);
		else
			return null;
	}
	
	/**
	 * 获得指定频站点http,频道路径，条件的第一条记录
	 * @param sitePath
	 * @param channelpath
	 * @param where
	 * @return
	 */
	public Object getFirstDoc(String sitePath, String channelpath,String where){
		
		ChannelManagerService channelManagerService = (ChannelManagerService)ServiceLocator.getBean ("channelManagerService");
		//获取当前站点
		CmsSite cmsSite = channelManagerService.getSiteCache().getSiteFromCache(sitePath, 0); 
		
		//获取指定路径的频道
		Channel channel = null;
		if(cmsSite != null)
			channel = channelManagerService.getChannelByPath(cmsSite.getOid(), channelpath);
		
		//获取
		if(channel != null){
			List docs=getByPublicDocs(channel.getId(),where);
			if(docs!=null && docs.size()>0)
				return docs.get(0);
			else
				return null;			
		}
		
		return null; 
	}
	
	/**
	 * 获得指定频站点http,频道路径，符合条件的字符串. 用于SELECT 初始化
	 * @param sitePath
	 * @param channelpath
	 * @param where
	 * @param property
	 * @return
	 */
	public String initSelectTag(String sitePath, String channelpath,String where,String textproperty,String valueproperty){
		List docs = new ArrayList();
		StringBuffer sb = new StringBuffer(); 
		
		ChannelManagerService channelManagerService = (ChannelManagerService)ServiceLocator.getBean ("channelManagerService");
		//获取当前站点
		CmsSite cmsSite = channelManagerService.getSiteCache().getSiteFromCache(sitePath, 0); 
		
		//获取指定路径的频道
		Channel channel = null;
		if(cmsSite != null)
			channel = channelManagerService.getChannelByPath(cmsSite.getOid(), channelpath);
		
		//获取
		if(channel != null){
			docs = getByPublicDocs(channel.getId(),where);   
			for(int i = 0; i<docs.size(); i++){ 
				Object doc = docs.get(i);  
				try {
					Object text =  Ognl.getValue(textproperty, doc);
					Object value =  Ognl.getValue(valueproperty, doc);
					
					if(text != null && value != null){
						sb.append(";").append(text.toString()).append("@").append(value.toString());
					}
					 
				} catch (OgnlException e) {
					e.printStackTrace();
				}  
			}
			
		}
		
		return sb.toString().length() > 0 ? sb.toString().substring(1) : "";
	}
	
	
	/**
	 * 获得指定频站点http,频道路径，类型. 用于SELECT 初始化
	 * @param sitePath
	 * @param channelpath  多个用逗号分割
	 * @param type  1 站点下所有频道 2 当前频道和子频道  3 子频道 
	 * @param property
	 * @return
	 */
	public String initSelectTagOfChannel(String sitePath, String channelpath,int type,String textproperty,String valueproperty){
		StringBuffer sb = new StringBuffer(); 
		ChannelManagerService channelManagerService = (ChannelManagerService)ServiceLocator.getBean ("channelManagerService");
		
		if(StringUtil.isEmpty(sitePath) || (StringUtil.isEmpty(channelpath) && type != 1))
			return "";
		
		List<Channel> channels = new ArrayList<Channel>();
		List<Channel> childChannels = new ArrayList<Channel>();
		if(type == 1)
			childChannels= channelManagerService.getSiteCache().getAllChannels();
		else{
			//获取当前站点
			CmsSite cmsSite = channelManagerService.getSiteCache().getSiteFromCache(sitePath, 0); 
			String [] paths = channelpath.split(","); 
			
			//获取指定路径的频道 
			if(cmsSite != null){
				for (String path : paths) {
					Channel channel = channelManagerService.getChannelByPath(cmsSite.getOid(), path);
					if(channel != null)
						channels.add(channel);
				}
			}
			
			for (Channel channel : channels) {
				childChannels.addAll(channelManagerService.getChildChannelsByParent(channel.getId(),true));
			}
		}
		
		//获取 
		for(int i = 0; i<childChannels.size(); i++){
			Object chn = childChannels.get(i);  
			try {
				Object text =  Ognl.getValue(textproperty, chn);
				Object value =  Ognl.getValue(valueproperty, chn);
				
				if(text != null && value != null){
					sb.append(";").append(text.toString()).append("@").append(value.toString());
				}
				 
			} catch (OgnlException e) {
				e.printStackTrace();
			}
		}
		 
		
		return sb.toString().length() > 0 ? sb.toString().substring(1) : "";
	}
	
	/**
	 * 获得指定频站点http,频道路径，符合条件的字符串. 用于 初始化表单SELECT
	 * @param channelid
	 * @param where
	 * @param property
	 * @return
	 */
	public String initSelectTag(Long channelid,String where,String textproperty,String valueproperty){
		List docs = new ArrayList();
		StringBuffer sb = new StringBuffer(); 
		 
		docs = getByPublicDocs(channelid,where);
		for(int i = 0; i<docs.size(); i++){ 
			Object doc = docs.get(i);  
			try {
				Object text =  Ognl.getValue(textproperty, doc);
				Object value =  Ognl.getValue(valueproperty, doc);
				
				if(text != null && value != null){
					sb.append(";").append(text.toString()).append("@").append(value.toString());
				}
				 
			} catch (OgnlException e) {
				e.printStackTrace();
			}
		} 
		
		return sb.toString().length() > 0 ? sb.toString().substring(1) : "";
	}
	 
}
