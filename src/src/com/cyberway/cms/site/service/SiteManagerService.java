package com.cyberway.cms.site.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.dom.DOMDocumentFactory;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.document.service.DocumentCommonService;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.domain.Template;
import com.cyberway.cms.form.object.BaseDocument;
import com.cyberway.cms.permission.service.CmsPermissionService;
import com.cyberway.cms.site.cache.SiteCache;
import com.cyberway.cms.template.service.TemplateManagerService;
import com.cyberway.cms.webservice.service.HtmlSynchroismService;
import com.cyberway.common.base.objects.Constants;
import com.cyberway.common.xtree.DHtmlXTree;
import com.cyberway.common.xtree.DHtmlXTree.Node;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.objects.UserFrame;
import com.cyberway.core.utils.BeanUtil;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;

public class SiteManagerService extends HibernateEntityDao<CmsSite> {
	private SiteCache siteCache;

	private CmsPermissionService permService;

	private ChannelManagerService channelManagerService;

	private TemplateManagerService templateManagerService;
	

	public static String CMS_SITE_VIEW = "CMS_SITE_VIEW";// 站点的浏览权限代码
	
	public static String CHANNEL_VIEW_CODE = "CMS_CHANNEL_VIEW";// 频道的浏览权限代码

	public static String DOCUMENT_VIEW_CODE = "CMS_DOCUMENT_VIEW"; // 文档的浏览权限代码

	public static String CMS_DOCUMENT_VIEW_AUTHOR = "CMS_DOCUMENT_VIEW_AUTHOR";// 文档作者读权限

	public final static String EXPORT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	

	private boolean isInit=false;
	/**
	 * 初始化站点
	 */
	@SuppressWarnings("unchecked")
	public synchronized void init() {
		List<CmsSite> sites = find(" from CmsSite where status>? order by oid",
				new Object[] { 0 });// getAll();
		for (CmsSite site : sites)
			siteCache.putSiteInCache(site);
		isInit=true;
	}
	
	
	//根据域名获取站点名称
	public CmsSite getCmsSite(String siteHttp) {
		CmsSite cmsSite=siteCache.getSiteFromMainSite(siteHttp);
		if(cmsSite==null){
			cmsSite=new CmsSite();
			cmsSite.setOid(0L);
			cmsSite.setSitename("CMS内容管理系统");
		}
		return cmsSite;

	}
  
	/**
	 * 拷贝菜单到..下
	 * 
	 * @param mid
	 * @param pmid
	 * @param portalid
	 * @return
	 * @throws Exception
	 */
	public boolean copySiteTo(String mid, String pmid, String siteid, UserFrame loginer) throws Exception {
		if (StringUtil.isEmpty(mid)) {
			return false;
		}
		
		CmsSite cmsSite = this.get(new Long(mid));
		CmsSite new_cmsSite = copySite(cmsSite);
		
		if (new_cmsSite != null && new_cmsSite.getOid() != null) {
			boolean succ = false;
			List channels = channelManagerService.getFirstChannelsBySite(new Long(mid));
			Iterator it = channels.iterator();
			while (it.hasNext()) {
				Channel channel = (Channel) it.next();
				succ = loopCopyChannel(channel, new Long(mid), copyChannels(channel, null, new_cmsSite), new_cmsSite);
			}
		} else {
			return false;
		}
		return true;
	}
	
	/**
	 * @param channel
	 * @param site
	 * @return
	 */
	public boolean loopCopyChannel(Channel oldChannel, Long siteId, Channel newChannel, CmsSite site){
		boolean succ = true;
		Channel channel = null;
		List<Channel> list = channelManagerService.findByParentInSameSite(siteId, oldChannel.getId());
		for(Channel cha : list){
			try {
	            channel = copyChannels(cha, newChannel, site);
	            if(!cha.getChildren().isEmpty()){
	            	if(!loopCopyChannel(cha, siteId, channel, site)){
	            		succ = false;
	            	}
	            }
            } catch (Exception e) {
            	succ = false;
	            e.printStackTrace();
            }
		}
		return succ;
	}

	/**
	 * 复制站点
	 * 
	 * @param cmsSite
	 * @return心
	 * @throws Exception
	 */
	private CmsSite copySite(CmsSite cmsSite) throws Exception {
		if (cmsSite == null) {
			return new CmsSite();
		}
		CmsSite new_cmsSite = new CmsSite();
		BeanUtil.updateObject(cmsSite, new_cmsSite);
		new_cmsSite.setOid(null);
		// new_cmsSite.setChannelPath(new_channel.getChannelPath()+System.currentTimeMillis());
		if (cmsSite.getOid() != null) {
			String rname = cmsSite.getSitename();
			if (!StringUtil.isEmpty(rname))
				rname += Constants.COPY_OBJECT_NAME_ADD_SIGN;
			else
				rname = Constants.COPY_OBJECT_NAME_ADD_SIGN;
			new_cmsSite.setSitename(rname);// 增加复制时的标志
		}

		this.saveOrUpdate(new_cmsSite);
		return new_cmsSite;
	}

	/**
	 * 复制站点下的所有频道
	 * 
	 * @param channel
	 * @param pChannel
	 * @param cmsSite
	 * @return
	 * @throws Exception
	 */
	private Channel copyChannels(Channel channel, Channel pChannel, CmsSite cmsSite) throws Exception {
		Channel new_channel = new Channel();
		BeanUtil.updateObject(channel, new_channel);
		new_channel.setId(null);
		new_channel.setSite(cmsSite);
		new_channel.setParent(pChannel);
		new_channel.setChannelPath(new_channel.getChannelPath() + System.currentTimeMillis());
		if (channel.getId() != null) {
			String rname = channel.getName();
			if (StringUtil.isEmpty(rname)) {
				rname = Constants.COPY_OBJECT_NAME_ADD_SIGN;
				new_channel.setName(rname);
			}
		}
		new_channel = channelManagerService.saveOrUpdate(new_channel);

		return new_channel;
	}

	public String getChannelBySite(Long siteid) {
		StringBuffer menu = new StringBuffer();
		List<Channel> ls = getSiteCache().getChannelsFromcachBySite(
				siteid.toString());
		for (Channel child : ls) {
			if (child.getParent() != null)
				continue;
			menu.append("<li><a href='/" + child.getChannelPath() + "'>");
			menu.append(child.getName());
			menu.append("</a>");
			getChildrenByChannel(menu, child);
			menu.append("</li>");
		}
		System.out.println(menu.toString());
		return menu.toString();
	}

	public String getChannelByIds(String keys) {
		List<Channel> ls = new ArrayList();
		StringBuffer menu = new StringBuffer();
		if (!StringUtil.isEmpty(keys)) {
			List list = StringUtil.splitToList(keys, ",");
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Channel channel = (Channel) getSiteCache().getChannelFromCach(
						Long.parseLong(it.next().toString()));
				if (channel != null)
					ls.add(channel);
			}
		}
		for (Channel child : ls) {
			menu.append("<li><a href='/" + child.getChannelPath() + "'>");
			menu.append(child.getName());
			menu.append("</a>");
			getChildrenByChannel(menu, child);
			menu.append("</li>");
		}
		System.out.println(menu.toString());
		return menu.toString();
	}
/**
 * 根据channelId找到对应的名字
 * @param channelId
 * @return
 */
	public String getChannelNameById(Long channelId){
		Channel channel=channelManagerService.get(channelId);
		return channel.getName();
	}
	public StringBuffer getChildrenByChannel(StringBuffer menu, Channel channel) {
		List<Channel> children = channelManagerService.findByParent(channel
				.getId());
		if (children != null && children.size() > 0) {
			menu.append("<ul>");
			for (Channel child : children) {
				menu.append("<li><a href='/" + child.getChannelPath() + "'>");
				menu.append(child.getName());
				menu.append("</a>");
				getChildrenByChannel(menu, child);
				menu.append("</li>");
			}
			menu.append("</ul>");
		}
		return menu;
	}

	/**
	 * 根据站点id获取站点下的一级频道
	 * @param siteid
	 * @return
	 */
	public List<Channel> getChannelsBySiteid(String siteid) {
		List<Channel> list = new ArrayList();
		for (Channel channel : getSiteCache().getChannelsFromcachBySite(siteid)) {
			if (channel.getParent() == null) {
				list.add(channel);
			}
		}
		return list;
	}
	
	/**
	 * 根据站点id获取站点下的所有频道
	 * @param siteid
	 * @return
	 */
	public List<Channel> getAllChannelsBySiteid(String siteid) {
		List<Channel> list = new ArrayList();
		for (Channel channel : getSiteCache().getChannelsFromcachBySite(siteid)) {
			list.add(channel);
		}
		return list;
	}

	public List<Channel> getChannelsByParent(Long channelid) {
		return channelManagerService.findByParent(channelid);
	}

	/**
	 * 递归调用
	 * 
	 * @param tree
	 * @param channel
	 * @return
	 */
	private Node appendChannel(DHtmlXTree tree, Channel channel, Loginer loginer) {
		Node node = tree.initNode();
		node.setText(channel.getName());
		node.setId(channel.getId().toString());
		if (tree.getIsDynamicLoad())
			node.setChild("1");

		// 加载所有子节点
		if (channel.getChildren() != null && channel.getChildren().size() > 0
				&& !tree.getIsDynamicLoad()) {
			List ls = new ArrayList();
			for (Channel child : channel.getChildren()) {
				// 检测的权限项 若为后台登录，检测频道的浏览权限，前台检测文档的浏览权限
				if (loginer.getLoginType() == 0 && child.getStatus() == 1) {// status为1,正常，0表示已经删除
					if (permService.haveThePermission(loginer,
							this.CHANNEL_VIEW_CODE, child.getId()))
						ls.add(appendChannel(tree, child, loginer));
				} else if (loginer.getLoginType() == 1
						&& child.getStatus() == 1
						&& child.getIspublished() == 1) {// 前台登录
					if (permService.haveThePermission(loginer,
							DOCUMENT_VIEW_CODE, 2, child.getId())
							|| permService.haveThePermission(loginer,
									DOCUMENT_VIEW_CODE + "_AUTHOR", 2, child
											.getId()))
						ls.add(appendChannel(tree, child, loginer));
				}
			}
			node.setSubNodeList(ls);
		}
		return node;
	}

	/**
	 * 递归调用(公共模板调用)
	 * 
	 * @param tree
	 * @param channel
	 * @param loginer
	 * @return
	 */
	private Node appendPublicChannel(DHtmlXTree tree, Channel channel,
			Loginer loginer) {
		Node node = tree.initNode();
		node.setText(channel.getName());
		node.setId(channel.getId().toString());
		if (tree.getIsDynamicLoad())
			node.setChild("1");

		// 加载所有子节点
		if (channel.getChildren() != null && channel.getChildren().size() > 0
				&& !tree.getIsDynamicLoad()) {
			List ls = new ArrayList();
			for (Channel child : channel.getChildren()) {
				// 检测的权限项 若为后台登录，检测频道的浏览权限，前台检测文档的浏览权限
				if (loginer.getLoginType() == 0 && child.getStatus() == 1
						&& child.getIspublicchannel() == 1) {// status为1,正常，0表示已经删除
					if (permService.haveThePermission(loginer,
							this.CHANNEL_VIEW_CODE, child.getId()))
						ls.add(appendChannel(tree, child, loginer));
				} else if (loginer.getLoginType() == 1
						&& child.getStatus() == 1
						&& child.getIspublished() == 1
						&& child.getIspublicchannel() == 1) {// 前台登录
					if (permService.haveThePermission(loginer,
							DOCUMENT_VIEW_CODE, 2, child.getId())
							|| permService.haveThePermission(loginer,
									DOCUMENT_VIEW_CODE + "_AUTHOR", 2, child
											.getId()))
						ls.add(appendChannel(tree, child, loginer));
				}
			}
			node.setSubNodeList(ls);
		}
		return node;
	}
	
	/**
	 * 获得指定站点tree xml(原方法)
	 * 
	 * @param siteid
	 * @return
	 */
	public String getSiteTreeXmls(Loginer loginer, Long siteid, String revi) {
		DHtmlXTree tree = new DHtmlXTree();
		tree.setTreeId("S_" + siteid);
		CmsSite site = getSiteFromCache(siteid);

		Node siteNode = tree.initNode();
		siteNode.setId("T_" + site.getOid().toString());
		siteNode.setText(site.getSitename());
		siteNode.setOpen(true);

		//if (loginer.getLoginType() != 1) {
			// 设置站点图标
		//	siteNode.setIm0("tombs.gif");
		//	siteNode.setIm1("tombs.gif");
		//	siteNode.setIm2("tombs.gif");
		//}

		if (site.getIsdynamic() == 1) {
			tree.setIsDynamicLoad(true);
		}
		if (site.getIsdynamic() != 1) { // 是否使用动态加载 自动加载第一层
			List subnd = new ArrayList();
			String sql;
			if (revi != null && revi.equals("true"))
				sql = "from Channel where site.oid=? and parent.id is null order by sortOrder";
			else
				sql = "from Channel where site.oid=? and parent.id is null and status = 1 order by sortOrder";
			List<Channel> channels = find(sql, new Object[] { site.getOid() });

			for (Channel channel : channels) {
				// 检测的权限项 若为后台登录，检测频道的浏览权限，前台检测文档的浏览权限
				if (loginer.getLoginType() == 0) {
					if (permService.haveThePermission(loginer,
							CHANNEL_VIEW_CODE, channel.getId()))
						subnd.add(appendChannel(tree, channel, loginer));
				} else if (loginer.getLoginType() == 1
						&& channel.getIspublished() == 1) {// 前台登录 只显示已发布的频道
					if (permService.haveThePermission(loginer,
							DOCUMENT_VIEW_CODE, 2, channel.getId())
							|| permService.haveThePermission(loginer,
									DOCUMENT_VIEW_CODE + "_AUTHOR", 2, channel
											.getId()))
						subnd.add(appendChannel(tree, channel, loginer));
				}

			}
			siteNode.setSubNodeList(subnd);
		} else
			// 只加载站点
	    siteNode.setChild("1");
		tree.addNode(siteNode);
		return tree.getDHtmlXmlTree();
	}

	/**
	 * 获得指定站点tree xml
	 * 
	 * @param siteid
	 * @return
	 */
	public String getSiteTreeXml(Loginer loginer, Long siteid, String revi,String siteName) {
		DHtmlXTree tree = new DHtmlXTree();
		tree.setTreeId("S_" + siteid);
		CmsSite site = getSiteFromCache(siteid);

		Node siteNode = tree.initNode();
		siteNode.setId("T_" + site.getOid().toString());
		if(siteName!=null){
			siteNode.setText(siteName);
		}else{
			siteNode.setText(site.getSitename());
		}
		siteNode.setOpen(true);

		//if (loginer.getLoginType() != 1) {
			// 设置站点图标
		//	siteNode.setIm0("tombs.gif");
		//	siteNode.setIm1("tombs.gif");
		//	siteNode.setIm2("tombs.gif");
		//}

		if (site.getIsdynamic() == 1) {
			tree.setIsDynamicLoad(true);
		}
		if (site.getIsdynamic() != 1) { // 是否使用动态加载 自动加载第一层
			List subnd = new ArrayList();
			String sql;
			if (revi != null && revi.equals("true"))
				sql = "from Channel where site.oid=? and parent.id is null order by sortOrder";
			else
				sql = "from Channel where site.oid=? and parent.id is null and status = 1 order by sortOrder";
			List<Channel> channels = find(sql, new Object[] { site.getOid() });

			for (Channel channel : channels) {
				// 检测的权限项 若为后台登录，检测频道的浏览权限，前台检测文档的浏览权限
				if (loginer.getLoginType() == 0) {
					if (permService.haveThePermission(loginer,
							CHANNEL_VIEW_CODE, channel.getId()))
						subnd.add(appendChannel(tree, channel, loginer));
				} else if (loginer.getLoginType() == 1
						&& channel.getIspublished() == 1) {// 前台登录 只显示已发布的频道
					if (permService.haveThePermission(loginer,
							DOCUMENT_VIEW_CODE, 2, channel.getId())
							|| permService.haveThePermission(loginer,
									DOCUMENT_VIEW_CODE + "_AUTHOR", 2, channel
											.getId()))
						subnd.add(appendChannel(tree, channel, loginer));
				}

			}
			siteNode.setSubNodeList(subnd);
		} else
			// 只加载站点
	    siteNode.setChild("1");
		tree.addNode(siteNode);
		return tree.getDHtmlXmlTree();
	}

	/**
	 * 公共模板树 tree xml
	 * 
	 * @param loginer
	 * @param siteid
	 * @param revi
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getPublicSiteTreeXml(Loginer loginer, Long siteid, String revi) {
		DHtmlXTree tree = new DHtmlXTree();
		tree.setTreeId("S_" + siteid);
		CmsSite site = getSiteFromCache(siteid);

		Node siteNode = tree.initNode();
		siteNode.setId("T_" + site.getOid().toString());
		siteNode.setText(site.getSitename());

		// 设置站点图标
		siteNode.setIm0("tombs.gif");
		siteNode.setIm1("tombs.gif");
		siteNode.setIm2("tombs.gif");

		if (site.getIsdynamic() == 1) {
			tree.setIsDynamicLoad(true);
		}
		if (site.getIsdynamic() != 1) { // 是否使用动态加载 自动加载第一层
			List subnd = new ArrayList();
			String sql;
			if (revi != null && revi.equals("true"))
				sql = "from Channel where site.oid=? and parent.id is null order by sortOrder";
			else
				sql = "from Channel where site.oid=? and parent.id is null and status = 1 and ispublicchannel=1 order by sortOrder";
			List<Channel> channels = find(sql, new Object[] { site.getOid() });

			for (Channel channel : channels) {
				// 检测的权限项 若为后台登录，检测频道的浏览权限，前台检测文档的浏览权限
				if (loginer.getLoginType() == 0) {
					if (permService.haveThePermission(loginer,
							CHANNEL_VIEW_CODE, channel.getId()))
						subnd.add(appendPublicChannel(tree, channel, loginer));
				} else if (loginer.getLoginType() == 1
						&& channel.getIspublished() == 1) {// 前台登录 只显示已发布的频道
					if (permService.haveThePermission(loginer,
							DOCUMENT_VIEW_CODE, 2, channel.getId())
							|| permService.haveThePermission(loginer,
									DOCUMENT_VIEW_CODE + "_AUTHOR", 2, channel
											.getId()))
						subnd.add(appendPublicChannel(tree, channel, loginer));
				}

			}
			siteNode.setSubNodeList(subnd);
		} else
			// 只加载站点
			siteNode.setChild("1");
		tree.addNode(siteNode);
		return tree.getDHtmlXmlTree();
	}

	/**
	 * 获得指定频道tree xml（指定频道的id为根节点）
	 * 
	 * @param siteid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getChannelTreeXml(Loginer loginer, Long channelid, String revi) {
		DHtmlXTree tree = new DHtmlXTree();
		tree.setTreeId("S_" + channelid);
		Channel channel = this.channelManagerService
				.getChannelFromCache(channelid);
		CmsSite site = getSiteFromCache(channel.getSite().getOid());

		Node siteNode = tree.initNode();
		siteNode.setId(channel.getId().toString());
		siteNode.setText(channel.getName());

		// 设置站点图标
		siteNode.setIm0("tombs.gif");
		siteNode.setIm1("tombs.gif");
		siteNode.setIm2("tombs.gif");
		if (site.getIsdynamic() == 1) {
			tree.setIsDynamicLoad(true);
		}
		if (site.getIsdynamic() != 1) { // 是否使用动态加载 自动加载第一层
			List subnd = new ArrayList();
			String sql;
			if (revi != null && revi.equals("true"))
				sql = "from Channel where site.oid=? and parent.id="
						+ channel.getId() + " order by sortOrder";
			else
				sql = "from Channel where site.oid=? and parent.id="
						+ channel.getId()
						+ " and status = 1 order by sortOrder";
			List<Channel> channels = find(sql, new Object[] { site.getOid() });

			for (Channel _channel : channels) {
				// 检测的权限项 若为后台登录，检测频道的浏览权限，前台检测文档的浏览权限
				if (loginer.getLoginType() == 0) {
					if (permService.haveThePermission(loginer,
							CHANNEL_VIEW_CODE, _channel.getId()))
						subnd.add(appendChannel(tree, _channel, loginer));
				} else if (loginer.getLoginType() == 1
						&& channel.getIspublished() == 1) {// 前台登录,只显示已发布的频道
					if (permService.haveThePermission(loginer,
							DOCUMENT_VIEW_CODE, 2, _channel.getId())
							|| permService.haveThePermission(loginer,
									DOCUMENT_VIEW_CODE + "_AUTHOR", 2, _channel
											.getId()))
						subnd.add(appendChannel(tree, _channel, loginer));
				}
			}
			siteNode.setSubNodeList(subnd);
		} else
			// 只加载站点
			siteNode.setChild("1");
		tree.addNode(siteNode);
		return tree.getDHtmlXmlTree();
	}

	/**
	 * 获得站点的频道的xml串
	 * 
	 * @param siteid
	 * @param channelid
	 * @return
	 */
	public String getChannelTreeXml(Loginer loginer, Long siteid,
			Long channelid, String revi) {
		return getChannelTreeXml(loginer,siteid,channelid,revi,null); 
	}
	
	
	/**
	 * 获得站点的频道的xml串
	 * 
	 * @param siteid
	 * @param channelid
	 * @return
	 */
	public String getChannelTreeXml(Loginer loginer, Long siteid,
			Long channelid, String revi,Long formid) {
		if (channelid == null) {// 只取第一级频道
			DHtmlXTree tree = new DHtmlXTree();
			tree.setTreeId("T_" + siteid);
			tree.setIsDynamicLoad(true);
			String sql;
			if (revi != null && revi.equals("true"))
				sql = "from Channel where site.oid=? and parent.id is null order by sortOrder";
			else
				sql = "from Channel where site.oid=? and parent.id is null and status = 1 order by sortOrder";
			List<Channel> channels = this.find(sql, new Object[] { siteid });

			for (Channel channel : channels) {
				// 检测的权限项 若为后台登录，检测频道的浏览权限，前台检测文档的浏览权限
				if (loginer.getLoginType() == 0) {
					if (permService.haveThePermission(loginer,
							this.CHANNEL_VIEW_CODE, channel.getId()))
						tree.addNode(appendChannel(tree, channel, loginer));
				} else if (loginer.getLoginType() == 1
						&& channel.getIspublished() == 1) {// 前台登录
					if (permService.haveThePermission(loginer,
							DOCUMENT_VIEW_CODE, 2, channel.getId())
							|| permService.haveThePermission(loginer,
									DOCUMENT_VIEW_CODE + "_AUTHOR", 2, channel
											.getId()))
						tree.addNode(appendChannel(tree, channel, loginer));
				}
			}
			return tree.getDHtmlXmlTree();
		} else {// 获得指定频道下所有频道
			DHtmlXTree tree = new DHtmlXTree();
			tree.setTreeId(channelid.toString());// tree id 必须为父的对象id
			Channel channel = (Channel) get(Channel.class, channelid);
			if (revi != null && revi.equals("true")) {
				for (Channel chn : channel.getChildren()) {
					// 检测的权限项 若为后台登录，检测频道的浏览权限，前台检测文档的浏览权限
					if (loginer.getLoginType() == 0) {
						if (permService.haveThePermission(loginer,
								this.CHANNEL_VIEW_CODE, chn.getId()))
							tree.addNode(appendChannel(tree, chn, loginer));
					} else if (loginer.getLoginType() == 1
							&& channel.getIspublished() == 1) {// 前台登录
						if (permService.haveThePermission(loginer,
								DOCUMENT_VIEW_CODE, 2, chn.getId())
								|| permService.haveThePermission(loginer,
										DOCUMENT_VIEW_CODE + "_AUTHOR", 2, chn
												.getId()))
							tree.addNode(appendChannel(tree, chn, loginer));
					}
				}
			} else {
				for (Channel chn : channel.getChildren()) {
					// 检测的权限项 若为后台登录，检测频道的浏览权限，前台检测文档的浏览权限
					if (loginer.getLoginType() == 0) {
						if (permService.haveThePermission(loginer,
								this.CHANNEL_VIEW_CODE, chn.getId())
								&& chn.getStatus() == 1)
							tree.addNode(appendChannel(tree, chn, loginer));
					} else if (loginer.getLoginType() == 1
							&& channel.getIspublished() == 1) {// 前台登录
						if (permService.haveThePermission(loginer,
								DOCUMENT_VIEW_CODE, 2, chn.getId())
								|| permService.haveThePermission(loginer,
										DOCUMENT_VIEW_CODE + "_AUTHOR", 2, chn
												.getId()))
							tree.addNode(appendChannel(tree, chn, loginer));
					}
				}
			}
			return tree.getDHtmlXmlTree();
		}

	}

	@SuppressWarnings("unchecked")
	public String getReChannelTreeXml(Loginer loginer, Long siteid,
			Long channelid) {
		if (channelid == null) {// 只取第一级频道
			DHtmlXTree tree = new DHtmlXTree();
			tree.setTreeId("T_" + siteid);
			tree.setIsDynamicLoad(true);
			List<Channel> channels = this
					.find(
							"from Channel where site.oid=? and parent.id is null order by sortOrder",
							new Object[] { siteid });

			for (Channel channel : channels) {
				if (permService.haveThePermission(loginer,
						this.CHANNEL_VIEW_CODE, channel.getId()))
					tree.addNode(appendChannel(tree, channel, loginer));
			}
			return tree.getDHtmlXmlTree();
		} else {// 获得指定频道下所有频道
			DHtmlXTree tree = new DHtmlXTree();
			tree.setTreeId(channelid.toString());// tree id 必须为父的对象id
			Channel channel = (Channel) get(Channel.class, channelid);
			for (Channel chn : channel.getChildren()) {
				if (permService.haveThePermission(loginer,
						this.CHANNEL_VIEW_CODE, chn.getId()))
					tree.addNode(appendChannel(tree, chn, loginer));
			}
			return tree.getDHtmlXmlTree();
		}

	}

	/**
	 * 选择公共模板树
	 * 
	 * @param loginer
	 * @param siteid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getPublicTreeXml(Loginer loginer, Long siteid) {

		DHtmlXTree tree = new DHtmlXTree();
		tree.setTreeId("T_" + siteid);
		tree.setIsDynamicLoad(false);
		List<Channel> channels = this
				.find(
						"from Channel where site.oid=? and parent.id is null and ispublicchannel=? and status = 1 order by sortOrder",
						new Object[] { siteid, 1 });
		for (Channel channel : channels) {
			channel.setParent(null);
			if (permService.haveThePermission(loginer, this.CHANNEL_VIEW_CODE,
					channel.getId()))
				tree.addNode(appendPublicChannel(tree, channel, loginer));
		}
		return tree.getDHtmlXmlTree();
	}

	/**
	 * 获得所有站点
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmsSite> getAllSites() {
		List<CmsSite> sites  = siteCache.getAllSites();
		if (sites.isEmpty()) {				
			if(!isInit){
			init();
			sites = siteCache.getAllSites();
			}
		}
		return sites;
	}

	/**
	 * 获得当前站点
	 * 
	 * @return
	 */
	public CmsSite getSite(Long siteId) {
		CmsSite site  = siteCache.getSiteFromCache(siteId.toString());
		if (site==null) {				
			if(!isInit){
			init();
			site = siteCache.getSiteFromCache(siteId.toString());
			}
		}
		return site;
	}
	/**
	 * 获得站点对象
	 * 
	 * @param siteHttp
	 * @return
	 */
	public CmsSite getSite(String siteHttp, int port) throws Exception {
		if (getAllSites().isEmpty())
			if(!isInit) init();
		return siteCache.getSiteFromCache(siteHttp, port);
	}

	/**
	 * 从Cache中获得站点
	 * 
	 * @param siteid
	 * @return
	 * @throws Exception
	 */
	public CmsSite getSiteFromCache(Long siteid) {
		if (getAllSites().isEmpty())
			if(!isInit) init();
		return siteCache.getSiteFromCache(siteid.toString());
	}

	/**
	 * 整个站点转化为XML
	 * 
	 * @param sites
	 * @param root
	 */
	public void changeToXml(Collection<CmsSite> sites, Element root) {
		if (!CollectionUtils.isEmpty(sites)) {
			for (CmsSite site : sites) {
				Element element = root.addElement("site");
				element.addElement("oid").setText(
						ObjectUtils.toString(site.getOid()));
				element.addElement("sitename").setText(
						ObjectUtils.toString(site.getSitename()));
				element.addElement("sitehttp").setText(
						ObjectUtils.toString(site.getSitehttp()));
				element.addElement("siteport").setText(
						ObjectUtils.toString(site.getSiteport()));
				element.addElement("resourcepath").setText(
						ObjectUtils.toString(site.getResourcepath()));
				element.addElement("ispublished").setText(
						ObjectUtils.toString(site.getIspublished()));
				element.addElement("status").setText(
						ObjectUtils.toString(site.getStatus()));
				element.addElement("ishtml").setText(
						ObjectUtils.toString(site.getIshtml()));
				element.addElement("isdynamic").setText(
						ObjectUtils.toString(site.getIsdynamic()));
				element.addElement("manager").setText(
						ObjectUtils.toString(site.getManager()));
				element.addElement("remark").setText(
						ObjectUtils.toString(site.getRemark()));
				element.addElement("templateid").setText(
						ObjectUtils
								.toString((site.getTemplate() != null) ? site
										.getTemplate().getId() : ""));

				if (site.getCreatetime() == null) {
					element.addElement("createtime").setText(StringUtils.EMPTY);
				} else {
					element.addElement("createtime").setText(
							ObjectUtils.toString(site.getCreatetime()));
				}
				if (site.getTimeDeleted() == null) {
					element.addElement("timeDeleted")
							.setText(StringUtils.EMPTY);
				} else {
					element.addElement("timeDeleted").setText(
							ObjectUtils.toString(site.getTimeDeleted()));
				}

				element.addElement("createuserid").setText(
						ObjectUtils.toString(site.getCreateuserid()));
				element.addElement("createusername").setText(
						ObjectUtils.toString(site.getCreateusername()));
				element.addElement("numberOfClick").setText(
						ObjectUtils.toString(site.getNumberOfClick()));
				// 增加首页模板
				Element e_homeTemplates = element.addElement("homeTemplates");
				Collection<Template> homeTemplates = templateManagerService
						.findBySite(site.getOid(), Template.TYPE_INDEX);
				templateManagerService.changeToXml(homeTemplates,
						e_homeTemplates);
				// 增加公用模板
				Element e_publicTemplates = element
						.addElement("publicTemplates");
				Collection<Template> publicTemplates = templateManagerService
						.findBySite(site.getOid(), Template.TYPE_ANY);
				templateManagerService.changeToXml(publicTemplates,
						e_publicTemplates);
				// 增加站点下的频道和模板
				Element e_channels = element.addElement("channels");
				Collection<Channel> channels = siteCache
						.getChannelsFromcachBySite(String
								.valueOf(site.getOid()));
				channelManagerService.changeToXml(channels, e_channels, false,
						null);
			}
		}
	}

	/**
	 * 站点导出XML
	 * 
	 * @param sites
	 * @param outputStream
	 * @throws IOException
	 */
	public void exportToXml(Collection<CmsSite> sites, OutputStream outputStream)
			throws IOException {
		Validate.notNull(outputStream);

		if (!CollectionUtils.isEmpty(sites)) {
			OutputFormat format = new OutputFormat();
			format.setIndent(true);
			format.setNewlines(true);
			XMLWriter writer = new XMLWriter(outputStream, format);
			Document document = DOMDocumentFactory.getInstance()
					.createDocument();
			Element root = document.addElement("Sites");
			this.changeToXml(sites, root);
			writer.write(document);
		}
	}

	public void exportInfoToXml(CmsSite site,OutputStream outputStream) throws Exception {
		Validate.notNull(outputStream);
        DocumentCommonService documentCommonService = (DocumentCommonService)ServiceLocator.getBean("documentCommonService");
		if (site != null) {
			OutputFormat format = new OutputFormat();
			format.setIndent(true);
			format.setNewlines(true);
			XMLWriter writer = new XMLWriter(outputStream, format);
			Document document = DOMDocumentFactory.getInstance().createDocument();
			Element siteE = document.addElement("Site");
			List<Channel> channels = channelManagerService.getSiteCache().getChannelsFromcachBySite(String.valueOf(site.getOid()));
			Element channelsE = siteE.addElement("Channels");
			for(Channel chn: channels)
			{
				Element channelE = channelsE.addElement("Channel");
				channelE.addAttribute("name", chn.getName());
				channelE.addAttribute("channelPath", chn.getChannelPath());
				List<BaseDocument> baseDocs = documentCommonService.getAllDocumentsByChannel(chn.getId());
				if(baseDocs != null && baseDocs.size() >0)
				documentCommonService.changeToXml(baseDocs, channelE, chn);	
			}
			writer.write(document);
		}
	}

	/**
	 * 从XML中获取站点信息、首页模板、公共模板、频道信息以及频道下的所有模板
	 * 
	 * @param sites
	 * @param overwrite
	 */
	@SuppressWarnings("unchecked")
	public void changeFromXml(Iterable<Element> sites, boolean overwrite) {
		for (Element siteE : (Iterable<Element>) sites) {
			try {
				String oid = siteE.element("oid").getTextTrim();
				String sitename = siteE.element("sitename").getText();
				String sitehttp = siteE.element("sitehttp").getTextTrim();
				String siteport = siteE.element("siteport").getTextTrim();
				String resourcepath = siteE.element("resourcepath").getText();
				String ispublished = siteE.element("ispublished").getText();
				String status = siteE.element("status").getTextTrim();
				String ishtml = siteE.element("ishtml").getTextTrim();
				String isdynamic = siteE.element("isdynamic").getTextTrim();
				String manager = siteE.element("manager").getTextTrim();
				String remark = siteE.element("remark").getTextTrim();
				String createuserid = siteE.element("createuserid")
						.getTextTrim();
				String createusername = siteE.element("createusername")
						.getTextTrim();
				String numberOfClick = siteE.element("numberOfClick")
						.getTextTrim();
				String templateid = siteE.element("templateid").getTextTrim();

				CmsSite site = null;
				if (!overwrite) {
					site = new CmsSite();
				} else {
					CmsSite exists = StringUtils.isNumeric(oid) ? get(Long
							.valueOf(oid)) : null;
					site = exists == null ? new CmsSite() : exists;
				}
				site.setSitename(sitename);
				site.setSitehttp(sitehttp);
				site.setSiteport(StringUtils.isEmpty(siteport) ? null : Integer
						.valueOf(siteport));
				site.setResourcepath(resourcepath);
				site.setIspublished(StringUtils.isEmpty(ispublished) ? null
						: Integer.valueOf(ispublished));
				site.setStatus(StringUtils.isEmpty(status) ? null : Integer
						.valueOf(status));
				site.setIshtml(StringUtils.isEmpty(ishtml) ? null : Integer
						.valueOf(ishtml));
				site.setIsdynamic(StringUtils.isEmpty(isdynamic) ? null
						: Integer.valueOf(isdynamic));
				site.setManager(manager);
				site.setRemark(remark);
				site.setCreateuserid(StringUtils.isEmpty(createuserid) ? null
						: Long.valueOf(createuserid));
				site.setCreateusername(createusername);
				site.setNumberOfClick(StringUtils.isEmpty(numberOfClick) ? null
						: Long.valueOf(numberOfClick));

				site = saveOrUpdate(site);
				HashMap map = new HashMap();
				Iterable<Element> homeTemplates = siteE
						.element("homeTemplates").elements("Template");
				if (homeTemplates != null) {
					map = templateManagerService.changeFromXml(homeTemplates,
							overwrite, null, site, map);
				}

				Iterable<Element> publicTemplates = siteE.element(
						"publicTemplates").elements("Template");
				if (publicTemplates != null) {
					map = templateManagerService.changeFromXml(publicTemplates,
							overwrite, null, site, map);
				}

				Iterable<Element> channels = siteE.element("channels")
						.elements("channel");
				if (channels != null) {
					map = channelManagerService.changeFromXml(channels,
							overwrite, site, null, map);
					channelManagerService.updateFromXml(channels, site, map,
							null);
					channelManagerService.updatePublicFromXml(channels, site,
							map);
					channelManagerService.updateDefaultTemplateFromXml(
							channels, site, map);
				}
				if (map != null && StringUtils.isNotEmpty(templateid)) {
					templateid = map.get(templateid).toString();
					Template template = templateManagerService.get(Long
							.parseLong(templateid));
					if (template != null) {
						site.setTemplate(template);
						saveOrUpdate(site);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 导入XML
	 * 
	 * @param inputStream
	 * @param overwrite
	 * @throws DocumentException
	 */
	public void importFromXml(InputStream inputStream, boolean overwrite)
			throws DocumentException {
		Validate.notNull(inputStream);

		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		Iterable<Element> sites = document.getRootElement().elements("site");
		this.changeFromXml(sites, overwrite);

	}

	/**
	 * 获得所有站点Map <id,sitename>
	 * 
	 * @return
	 */
	public Map<Long, String> getAllSitesMap() throws Exception {
		Map<Long, String> sites = new TreeMap();
		List<CmsSite> lsites = getAllSites();
		for (CmsSite site : lsites) {
			// this.evit(site);
			sites.put(site.getOid(), site.getSitename());
		}
		return sites;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cyberway.core.dao.HibernateEntityDao#saveOrUpdate(java.lang.Object)
	 */
	public CmsSite saveOrUpdate(CmsSite site) {
		site = super.saveOrUpdate(site);
		if (site.getStatus() > 0) {// 若为删除状态，清除缓冲站点信息
			this.siteCache.removeSiteFromCache(site.getOid().toString());
			this.siteCache.putSiteInCache(site);
		} else
			this.siteCache.removeSiteFromCache(site.getOid().toString());
		return site;
	}

	/**
	 * 获得能操作的站点
	 * 
	 * @param status
	 *            状态
	 * @return
	 * @throws Exception
	 */
	public List getHaveTheSites(Loginer loginer, Integer status)
			throws Exception {
		List<CmsSite> allsite = getAllSites();
		List lsite = new ArrayList();
		if (loginer != null && loginer.checkIsAdministratorUser()) {
			for (CmsSite site : allsite) {
				if (site.getStatus() == status)
					lsite.add(site);
			}
			return lsite;
		} else {
			// 检测当前用户有权限的站点
			for (CmsSite site : allsite) {
				// String portalid = site.getCorePortal().getOid().toString();
				// if (portalid.equals(loginer.getPortal().getPortalid()) &&
				// site.getStatus()==status)
				if (site.getStatus() == status)
					lsite.add(site);
			}
			return lsite;
		}
	}
	
	
	/**
	 * 获得能操作的站点
	 * 
	 * @param status
	 *            状态
	 * @return
	 * @throws Exception
	 */
	public List getHaveTheSite(Loginer loginer,Long siteId, Integer status)
			throws Exception {
		CmsSite allsite =getSite(siteId);
		List lsite = new ArrayList();
		if(allsite!=null){
		if (loginer != null && loginer.checkIsAdministratorUser()) {
			//for (CmsSite site : allsite) {
				if (allsite.getStatus() == status)
					lsite.add(allsite);
			//}
			return lsite;
		} else {
			// 检测当前用户有权限的站点
			//for (CmsSite site : allsite) {
				// String portalid = site.getCorePortal().getOid().toString();
				// if (portalid.equals(loginer.getPortal().getPortalid()) &&
				// site.getStatus()==status)
				if (allsite.getStatus() == status)
					lsite.add(allsite);
			//}
			return lsite;
		}
		}else{
			return lsite;
		}
	}
	
	
	/**
	 * 获得能操作的站点
	 * 
	 * @param status
	 *            状态
	 * @return
	 * @throws Exception
	 */
	public List getListCmsSite(HttpServletRequest request, Integer status)
			throws Exception {
		List<CmsSite> allsite = getAllSites();
		Loginer loginer =(Loginer)request.getSession().getAttribute("loginer");
		List lsite = new ArrayList();
		if (loginer != null && loginer.checkIsAdministratorUser()) {
			for (CmsSite site : allsite) {
				if (site.getStatus() == status ){
					//&& site.getIspublished() == 1
					lsite.add(site);
				}
			}
			return lsite;
		} else {
			// 检测当前用户有权限的站点
			for (CmsSite site : allsite) {
				if (loginer.getLoginType() == 0 && site.getStatus() == status) {// status为1,正常，0表示已经删除
					//&& site.getIspublished() == 1
					if (permService.haveThePermission(loginer,
							this.CMS_SITE_VIEW, site.getOid()))
						lsite.add(site);
				} 
				// String portalid = site.getCorePortal().getOid().toString();
				// if (portalid.equals(loginer.getPortal().getPortalid()) &&
				// site.getStatus()==status)
				//if (site.getStatus() == status && site.getIspublished() == 1)
				//	lsite.add(site);
			}
			return lsite;
		}
	}
	
	
	/**
	 * 获得下拉框能操作的站点
	 * 
	 * @param status
	 *            状态
	 * @return
	 * @throws Exception
	 */
	public List getListSelectCmsSite(Loginer loginer, Integer status)
			throws Exception {
		List<CmsSite> allsite = getAllSites();
		List lsite = new ArrayList();
		if (loginer != null && loginer.checkIsAdministratorUser()) {
			for (CmsSite site : allsite) {
				if (site.getStatus() == status ){
					lsite.add(site);
				}
			}
			return lsite;
		} else {
			// 检测当前用户有权限的站点
			for (CmsSite site : allsite) {
				if (site.getStatus() == status) {// status为1,正常，0表示已经删除
					
					if (permService.haveThePermission(loginer,
							this.CMS_SITE_VIEW, site.getOid()) || permService.haveThePermission(loginer,
									DOCUMENT_VIEW_CODE,new Integer(1), site.getOid()))
						lsite.add(site);
				} 
			}
			return lsite;
		}
	}
	
	/**
	 * 获得下拉框能操作的站点
	 * 
	 * @param status
	 *            状态
	 * @return
	 * @throws Exception
	 */
	public List<Long> getSitesByLoginer(Loginer loginer, Integer status)
			throws Exception {
		List<CmsSite> allsite = getAllSites();
		List<Long> ids = new ArrayList();
		if (loginer != null && loginer.checkIsAdministratorUser()) {
			for (CmsSite site : allsite) {
				if (site.getStatus() == status ){
					ids.add(site.getOid());
				}
			}
			return ids;
		} else {
			// 检测当前用户有权限的站点
			for (CmsSite site : allsite) {
				if (site.getStatus() == status) {// status为1,正常，0表示已经删除
					if (permService.haveThePermission(loginer,
							this.CMS_SITE_VIEW, site.getOid()))
						ids.add(site.getOid());
				} 
			}
			return ids;
		}
	}
		
	
	/**
	 * 获得能操作的站点
	 * 
	 * @param status
	 *            状态
	 * @return
	 * @throws Exception
	 */
	public List getPublishedSites(Loginer loginer, Integer status)
			throws Exception {
		List<CmsSite> allsite = getAllSites();
		
		List lsite = new ArrayList();
		if (loginer != null && loginer.checkIsAdministratorUser()) {
			for (CmsSite site : allsite) {
				if (site.getStatus() == status&& site.getIspublished() == 1){
					lsite.add(site);
				}
			}
			return lsite;
		} else {
			for (CmsSite site : allsite) {
				
				// 检测当前用户有权限的站点
//				if (loginer.getLoginType() == 0 && site.getStatus() == status&& site.getIspublished() == 1) {// status为1,正常，0表示已经删除
//					//
//					if (permService.haveThePermission(loginer,
//							this.CMS_SITE_VIEW, site.getOid()))
//						lsite.add(site);
//				} 
				
				// String portalid = site.getCorePortal().getOid().toString();
				// if (portalid.equals(loginer.getPortal().getPortalid()) &&
				// site.getStatus()==status)
				if (site.getStatus() == status && site.getIspublished() == 1)
					lsite.add(site);
			}
			return lsite;
		}
	}
	
	
	/**
	 * 获得能操作的站点
	 * 
	 * @param status
	 *            状态
	 * @return
	 * @throws Exception
	 */
	public List getPublishedSite(Loginer loginer,Long siteId, Integer status)
			throws Exception {
		//List<CmsSite> allsite = getAllSites();
		CmsSite site=getSite(siteId);
		
		List lsite = new ArrayList();
		if(site!=null){
		if (loginer != null && loginer.checkIsAdministratorUser()) {
			//for (CmsSite site : allsite) {
				if (site.getStatus() == status && site.getIspublished() == 1)
					lsite.add(site);
			//}
			return lsite;
		} else {
			// 检测当前用户有权限的站点
		//	for (CmsSite site : allsite) {
				// String portalid = site.getCorePortal().getOid().toString();
				// if (portalid.equals(loginer.getPortal().getPortalid()) &&
				// site.getStatus()==status)
				if (site.getStatus() == status && site.getIspublished() == 1)
					lsite.add(site);
		//	}
			return lsite;
		}
		}else{
			return lsite;
		}
	}
	
	
	/**
	 * 获得能操作的站点
	 * 
	 * @param status
	 *            状态
	 * @return
	 * @throws Exception
	 */
	public List getPublishedChannels(Loginer loginer,Long siteId, Integer status)
			throws Exception {
		List<Channel> allChannel = channelManagerService.getChannelsBySite(siteId);
		List lchannel = new ArrayList();
		if (loginer != null && loginer.checkIsAdministratorUser()) {
			for (Channel channel : allChannel) {
			
				lchannel.add(channel);
			}
			return lchannel;
		} else {
			// 检测当前用户有权限的站点
			for (Channel channel : allChannel) {
				lchannel.add(channel);
			}
			return lchannel;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cyberway.core.dao.HibernateEntityDao#removeByIds(java.util.List)
	 */
	public void removeByIds(List<Long> ids) {

		super.removeByIds(ids);
		if (ids != null) {
			for (Long id : ids) {
				siteCache.removeSiteFromCache(id.toString());
				// 删除站点下所有模板
				templateManagerService.removeBySite(id);
			}
		}

	}

	/**
	 * 检查站点是否有静态首页
	 * @param siteid
	 * @return
	 */
	public boolean checkIsHtmlOfIndex(Long siteid){
		CmsSite cmssite= this.getSiteFromCache(siteid);
		if(cmssite!=null){
			if(cmssite.getCreatehtml()!=null&&cmssite.getCreatehtml()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 整站点静态页面
	 * 
	 * Frank
	 */
	public boolean siteTemplate(String siteId) {
		if (!StringUtil.isEmpty(siteId) && StringUtil.isNumber(siteId)) {
			CmsSite site=siteCache.getSiteFromCache(siteId.toString());
			if (site != null) {
				HtmlSynchroismService htmlSynchroismService = (HtmlSynchroismService) ServiceLocator.getBean("htmlSynchroismService");
				htmlSynchroismService.deleteStaticHtmlBySiteId(site.getOid());
			}
		}
		return true;
	}
	
	public CmsSite findByHttp(String siteHttp, int port) {
		return siteCache.getSiteFromCache(siteHttp, port);
	}

	public SiteCache getSiteCache() {
		return siteCache;
	}

	public void setSiteCache(SiteCache siteCache) {
		this.siteCache = siteCache;
	}

	public CmsPermissionService getPermService() {
		return permService;
	}

	public void setPermService(CmsPermissionService permService) {
		this.permService = permService;
	}

	public ChannelManagerService getChannelManagerService() {
		return channelManagerService;
	}

	public void setChannelManagerService(
			ChannelManagerService channelManagerService) {
		this.channelManagerService = channelManagerService;
	}

	public TemplateManagerService getTemplateManagerService() {
		return templateManagerService;
	}

	public void setTemplateManagerService(
			TemplateManagerService templateManagerService) {
		this.templateManagerService = templateManagerService;
	}
	
	/**
	 * 清除掉所有站点缓存
	 */
	public void removeAllCahce(){
		siteCache.removeAllSites();
	}
	
	/**
	 * 获取所有站点缓存的key
	 * @return
	 */
	public List getAllSitesKey(){
		return siteCache.getAllSitesKey();
	}
	
	/**
	 * 获得指定的站点缓存
	 * 
	 * @param key
	 * @return
	 */
	public net.sf.ehcache.Element getElementFromCache(String key) {
		if (StringUtils.isNotEmpty(key)){
			net.sf.ehcache.Element element = null;
			element = siteCache.getElementFromSite(key);
			return element;
		}
		else
			return null;
	}
	
	/**
	 * 监测站点的域名和扩展域名是否唯一
	 * 
	 * @param id
	 *            站点id
	 * @param sitehttp
	 *            站点域名
	 * @param domainNames
	 *            站点扩展域名
	 * @return 0代表唯一,1代表域名不唯一,2代表扩展域名不唯一
	 */
	public int realmNameIsUnique(Long id, String sitehttp, String domainNames) {
		CmsSite site = siteCache.getSiteFromMainSite(sitehttp);
		if (site != null) {
			if (id.equals(0L)) {
				return 1;
			}else{
				if(!site.getOid().equals(id)){
					return 1;
				}
			}
		}

		if (StringUtils.isNotBlank(domainNames)) {
			CmsSite extendSite = siteCache.getSiteFromMainSite(domainNames);
			if (extendSite != null) {
				if (id.equals(0L)) {
					return 2;
				}else{
					if(!extendSite.getOid().equals(id)){
						return 2;
					}
				}
			}
		}

		return 0;
	}

}
