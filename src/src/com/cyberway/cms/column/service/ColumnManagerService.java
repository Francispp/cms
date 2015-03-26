package com.cyberway.cms.column.service;

import java.util.ArrayList;
import java.util.List;

import ognl.Ognl;

import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.CmsColumn;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.common.xtree.DHtmlXTree;
import com.cyberway.common.xtree.DHtmlXTree.Node;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.StringUtil;

public class ColumnManagerService extends HibernateEntityDao<CmsColumn>  {
	private SiteManagerService siteManagerService;//站点管理Bean
	/**
	 * 获得指定站点tree xml
	 * 
	 * @param siteid
	 * @return
	 */
	public String getSiteTreeXml(Long siteid) {
		DHtmlXTree tree = new DHtmlXTree();
		tree.setTreeId("S_" + siteid);
		CmsSite site = siteManagerService.getSiteFromCache(siteid);
		
		Node siteNode = tree.initNode();
		siteNode.setId("T_" + site.getOid().toString());
		siteNode.setText(site.getSitename());

		// 设置站点图标
		siteNode.setIm0("tombs.gif");
		siteNode.setIm1("tombs.gif");
		siteNode.setIm2("tombs.gif");
		//if(site.getIsdynamic()==1){ 
			tree.setIsDynamicLoad(true);
		//}
         //只加载站点
			siteNode.setChild("1");
		tree.addNode(siteNode);
		return tree.getDHtmlXmlTree();
	}

    public String getColumnTreeXml(Loginer loginer,Long siteid,Long columnid){
    	if(columnid==null){//只取第一级栏目
    		DHtmlXTree tree = new DHtmlXTree();
    		tree.setTreeId("T_" + siteid);
    		tree.setIsDynamicLoad(true);
    		List<CmsColumn> channels = this.find("from CmsColumn where site.oid=? and parent.id is null",new Object[] { siteid });

			for (CmsColumn channel : channels) {				
				 tree.addNode(appendColumn(tree, channel,loginer));
			}
    		return tree.getDHtmlXmlTree();    		
    	}else{//获得指定栏目下所有栏目
    		DHtmlXTree tree = new DHtmlXTree();
    		tree.setTreeId(columnid.toString());//tree id 必须为父的对象id
    		CmsColumn channel=(CmsColumn)get(CmsColumn.class, columnid);
    		for (CmsColumn chn : channel.getSubColumn()) {
      		  tree.addNode(appendColumn(tree, chn,loginer));
    		}
    		return tree.getDHtmlXmlTree();  	
    	}    	
    }
	/**
	 * 递归调用 
	 * @param tree
	 * @param channel
	 * @return
	 */
	private Node appendColumn(DHtmlXTree tree, CmsColumn channel,Loginer loginer) {
		Node node = tree.initNode();
		node.setText(channel.getName());
		node.setId(channel.getId().toString());	
		/*if(tree.getIsDynamicLoad())
			node.setChild("1");*/

		// 加载所有子节点
		if (channel.getSubColumn() != null && channel.getSubColumn().size() > 0) {
			List ls = new ArrayList();
			for (CmsColumn child : channel.getSubColumn()) {
			  ls.add(appendColumn(tree, child,loginer));
			}
			node.setSubNodeList(ls);
		}
		return node;
	} 
	/**
	 * 移动栏目
	 * @param mid
	 * @param pmid
	 * @param portalid
	 * @return
	 */
	public boolean setColumnPM(String mid,String pmid,String siteid){
		if(StringUtil.isEmpty(mid)){
			return false;
		}
		CmsColumn cm=this.get(new Long(mid));
		if(!StringUtil.isEmpty(pmid)){//设置父菜id,且同时设置siteid			
			CmsColumn pcm=this.get(new Long(pmid));
			cm.setParent(pcm);
			cm.setSite(pcm.getSite());
		}else{//父菜单id为空，重新设置siteid
			if(!StringUtil.isEmpty(siteid)){
			try{
			 cm.setParent(null);
			 cm.setSite(new CmsSite());
			 Ognl.setValue("site.oid",cm,new Long(siteid));	
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage());
				return false;
			}
			}else
				return false;
		}
		this.save(cm);
		return true;
	}	
	public void setSiteManagerService(SiteManagerService siteManagerService) {
		this.siteManagerService = siteManagerService;
	}
}
