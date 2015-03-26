package com.cyberway.cms.exposed;

import java.util.List;

import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.site.service.SiteCommonService;
import com.cyberway.cms.site.service.SiteManagerService;

/**
 * @author caiw
 * 站点对外服务接口
 *
 */
public class SiteServiceJsUtil {

	private SiteManagerService siteManagerService;//站点管理service
	private SiteCommonService siteCommonService;//站点公用service

	public SiteManagerService getSiteManagerService() {
		return siteManagerService;
	}

	public void setSiteManagerService(SiteManagerService siteManagerService) {
		this.siteManagerService = siteManagerService;
	}
	/**
	 * 获得所有站点
	 * 
	 * @return
	 */
	public List<CmsSite> getAllSites(){
		return siteManagerService.getAllSites();
	}
	/**
	 * 获得站点信息
	 * @param siteid
	 * @return
	 */
	public CmsSite getSite(Long siteid){
		return siteManagerService.getSiteFromCache(siteid);
	}
	public String getSiteMenu(String siteid)
	{
		return siteManagerService.getChannelBySite(Long.valueOf(siteid));
	}
	public List<Channel> getChannelsBySiteid(String siteid) {
		return siteManagerService.getChannelsBySiteid(siteid);
	}
	public List<Channel> getChannelsByParent(Long channelid) {
		return siteManagerService.getChannelsByParent(channelid);	
	}

	/**
	 * 获得指定对象的点击率
	 * @param id
	 * @param type
	 * @return
	 */
	public Long getClickCount(Long id,int type){
		return siteCommonService.getClickCount(id, type);
	}
	
	/**
	 * 获得指定对象的点击率
	 * @param id
	 * @param type
	 * @return
	 */
	public Long getClickCount(Long siteId,Long ChannelId,Long id,int type){
		
		Long objId = 0l;
		
		//丫站点
		if(type == 1){
			if(siteId == null){
				
				
			}else
				objId=siteId;
		//频道
		}else if(type==2){
			if(ChannelId == null){
				
			}else
				objId=ChannelId; 
		}else{
			if(siteId == null){
				
			}else
				objId=id; 
		}
		
		
		return siteCommonService.getClickCount(objId, type);
	}
	
	public SiteCommonService getSiteCommonService() {
		return siteCommonService;
	}

	public void setSiteCommonService(SiteCommonService siteCommonService) {
		this.siteCommonService = siteCommonService;
	}
	
	public static void main(String[] args) {
		long he = Long.parseLong("");
		
		System.out.println(he);
	}
	
}
