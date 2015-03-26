package com.cyberway.common.sitemap.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.domain.Channel;
import com.cyberway.common.commoninfo.service.CommonInfoService;
import com.cyberway.common.commoninfo.service.CommonTypeService;
import com.cyberway.common.domain.CoreCommonInfo;
import com.cyberway.common.domain.CoreCommonType;
import com.cyberway.common.sitemap.domain.SitemapItem;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.web.BaseController;

public class SitemapController extends BaseController {

	private static final long serialVersionUID = -2025202572879486905L;
	private SitemapItem xflcp;
	private SitemapItem sycp;
	private SitemapItem hdzx;
	private SitemapItem rlzy;
	private ChannelManagerService channelService;
	private CommonInfoService commonInfoService;
	private CommonTypeService commonTypeService;
	
	@Override
	public String execute() throws Exception {
		channelService = (ChannelManagerService) ServiceLocator.getBean("channelManagerService");
		commonInfoService = (CommonInfoService) ServiceLocator.getBean("commonInfoService");
		commonTypeService = (CommonTypeService) ServiceLocator.getBean("commonTypeService");
		
		//消费类产品
		xflcp = getCommonInfoSitmap(3L);

		//商用产品
		sycp = getCommonInfoSitmap(5L);
		
		hdzx = new SitemapItem();
		List<SitemapItem> hdzxSub = new ArrayList<SitemapItem>();
		//活动中心
		hdzxSub.add(getChannelSitemapItem(7L,0));
		//服务与支持
		hdzxSub.add(getChannelSitemapItem(10L,1));
		//大宗采购
		hdzxSub.add(getChannelSitemapItem(12L,0));
		//关于康佳
		hdzxSub.add(getChannelSitemapItem(4L,1));
		//新闻中心
		hdzxSub.add(getChannelSitemapItem(6L,1));
		//投资者关系
		hdzxSub.add(getChannelSitemapItem(8L,1));
		hdzx.setSubItems(hdzxSub);
		
		//人力资源
		rlzy = getChannelSitemapItem(9L,3);
		
		return SUCCESS;
	}
	
	/**
	 * 按频道及层数获取sitemap
	 * @param channelId
	 * @param floor
	 * @return
	 */
	private SitemapItem getChannelSitemapItem(Long channelId, int floor){
		Channel parentChannel = channelService.getChannelFromCache(channelId);
		return getChannelSitemapItem(parentChannel,floor);
	}
	
	/**
	 * 按频道及层数获取sitemap
	 * @param parentChannel
	 * @param floor
	 * @return
	 */
	private SitemapItem getChannelSitemapItem(Channel parentChannel, int floor){
		SitemapItem sitemap = new SitemapItem();
		sitemap.setName(parentChannel.getName());
		sitemap.setLink(getLinkStr(parentChannel));
		sitemap.setTarget(getTarget(sitemap.getLink()));
		ArrayList<SitemapItem> subItems = new ArrayList<SitemapItem>();
		if(floor > 0){
			floor--;
			List<Channel> subChannel = channelService.getChildChannelsByParent(parentChannel.getId(), true);
			for(Channel channel:subChannel){
				subItems.add(getChannelSitemapItem(channel, floor));
			}
		}
		sitemap.setSubItems(subItems);
		return sitemap;
	}
	
	/**
	 * 根据频道和分类获取sitemap
	 * @param channelId
	 * @return
	 */
	private SitemapItem getCommonInfoSitmap(Long channelId){
		SitemapItem sitemap = new SitemapItem();
		List<SitemapItem> subItems = new ArrayList<SitemapItem>();
		Channel parentChannel = channelService.getChannelFromCache(channelId);
		sitemap.setName(parentChannel.getName());
		sitemap.setLink(getLinkStr(parentChannel));
		sitemap.setTarget(getTarget(sitemap.getLink()));
		List<Channel> subChannel = channelService.getChildChannelsByParent(channelId, true);
		for(Channel channel:subChannel){
			SitemapItem subItem = new SitemapItem();
			subItem.setName(channel.getName());
			subItem.setLink(getLinkStr(channel));
			subItem.setTarget(getTarget(subItem.getLink()));
			List<SitemapItem> subSubItems = new ArrayList<SitemapItem>();
			List<CoreCommonType> commonTypeList = commonTypeService.findBy("keyword", channel.getName());
			if(commonTypeList.size()>0){
				Long typeId = commonTypeList.get(0).getId();
				List<CoreCommonInfo> commonInfoList = commonInfoService.getCoreCommonInfosByCommonTypeId(typeId);
				for(CoreCommonInfo info:commonInfoList){
					SitemapItem subSubItem = new SitemapItem();
					subSubItem.setName(info.getContent());
					StringBuilder sb = new StringBuilder("/");
					sb.append(channel.getChannelPath());
					sb.append("?code=");
					sb.append(info.getCode());
					subSubItem.setLink(sb.toString());
					subSubItems.add(subSubItem);
				}
			}
			subItem.setSubItems(subSubItems);
			subItems.add(subItem);
		}
		sitemap.setSubItems(subItems);
		return sitemap;
	}
	
	private String getLinkStr(Channel channel){
		String link;
		if(StringUtils.isNotBlank(channel.getRedirect())){
			link = channel.getRedirect();
		} else {
			link = "/"+channel.getChannelPath();
		}
		return link;
	}
	
	private String getTarget(String link){
		if(link.startsWith("http:")){
			return "_blank";
		}
		return "_self";
	}

	public SitemapItem getXflcp() {
		return xflcp;
	}

	public SitemapItem getSycp() {
		return sycp;
	}

	public SitemapItem getHdzx() {
		return hdzx;
	}

	public SitemapItem getRlzy() {
		return rlzy;
	}
}
