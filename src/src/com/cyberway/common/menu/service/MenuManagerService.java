package com.cyberway.common.menu.service;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ognl.Ognl;

import com.cyberway.cms.domain.Channel;
import com.cyberway.common.base.objects.Constants;
import com.cyberway.common.domain.CoreDept;
import com.cyberway.common.domain.CoreMenu;
import com.cyberway.common.domain.CoreOrg;
import com.cyberway.common.xtree.DHtmlXTree;
import com.cyberway.common.xtree.DHtmlXTree.Node;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.utils.BeanUtil;
import com.cyberway.core.utils.BeanUtils;
import com.cyberway.core.utils.StringUtil;

public class MenuManagerService extends HibernateEntityDao<CoreMenu> {
	/**
	 * 供页面ajax调用的保存方法
	 * 
	 * @param data
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	public boolean saveByAjax(Map<String, Object> data){

		boolean result = true;

		try{
			
			//得到当前对象
			CoreMenu entity = buildEntity(data);			

			// 将页面提交的数据转换为实体对象中的属性值
			BeanUtils.forceSetProperty(entity, data);
			//增加门户关系
            if(data.containsKey("corePortal.oid")){
            	String poid=(String)data.get("corePortal.oid");
            	if(poid!=null&&poid.length()>0){
            		CoreOrg cp=new CoreOrg();
            		cp.setOid(new Long(poid));
            		//entity.setCorePortal(cp);
            	}
            }
			if (result && entity !=null) {
				this.save(entity);
				this.flush();
				
				// 取主键值
				String pkidName = this.getIdName(this.entityClass);
				Object pkid = BeanUtils.getProperty(entity, pkidName);
				data.put(pkidName, pkid);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			result = false;		
			
		}
		
		return result;
	}
	/**
	 * 移动菜单
	 * @param mid
	 * @param pmid
	 * @param portalid
	 * @return
	 */
	public boolean setMenuPM(String mid,String pmid,String portalid){
		if(StringUtil.isEmpty(mid)){
			return false;
		}
		CoreMenu cm=this.get(new Long(mid));
		if(!StringUtil.isEmpty(pmid)){//设置父菜id,且同时设置portalid			
			CoreMenu pcm=this.get(new Long(pmid));
			cm.setPmid(pcm.getOid());
			cm.setPortalid(pcm.getPortalid());
		}else{//父菜单id为空，重新设置portalid
			if(!StringUtil.isEmpty(portalid)){
			try{
			 cm.setPmid(null);
			 cm.setPortalid(new Long(portalid));
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
	/**
	 * 拷贝菜单到..下
	 * @param mid
	 * @param pmid
	 * @param portalid
	 * @return
	 * @throws Exception
	 */
	public boolean copyMenuTo(String mid,String pmid,String portalid)throws Exception{
		if(StringUtil.isEmpty(mid)){
			return false;
		}
		CoreMenu cm=this.get(new Long(mid));
		
		Long newPortalid;
		Long pid=null;
		if(!StringUtil.isEmpty(pmid)){//设置父菜id,且同时设置portalid	
			pid=new Long(pmid);
			CoreMenu pcm=this.get(pid);
			cm.setPmid(pcm.getOid());
			newPortalid=pcm.getPortalid();
		}else{//父菜单id为空，重新设置portalid
			if(!StringUtil.isEmpty(portalid)){
			try{
				newPortalid = Long.parseLong(portalid);	
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage());
				return false;
			}
			}else
				return false;
		}
		boolean succ=false;
        //递归复制
		succ=copyMenus(cm,pid,newPortalid);
		return true;
	}
	
	private boolean copyMenus(CoreMenu menu,Long pid,Long portalid)throws Exception{
		if(menu==null)
			return false;
		CoreMenu new_cm=new CoreMenu();
		BeanUtil.updateObject(menu,new_cm);
		new_cm.setOid(null);
		new_cm.setPortalid(portalid);
		new_cm.setPmid(pid);
		//若复制到相同节点下，增加标志位
	     if(menu.getPmid()!=null
	    		 &&pid!=null
	    		 && menu.getPmid().longValue()==pid.longValue()
	    		 ){
			  String rname=menu.getMenuname();
			  if(!StringUtil.isEmpty(rname))
				rname+=Constants.COPY_OBJECT_NAME_ADD_SIGN;
			  else
				rname=Constants.COPY_OBJECT_NAME_ADD_SIGN;
			  new_cm.setMenuname(rname);//增加复制时的标志	
		     }		
		this.save(new_cm);
		Set subMenus=menu.getSubMenus();
		if(subMenus!=null){
			Iterator it=subMenus.iterator();
			while(it.hasNext()){
				CoreMenu temp=(CoreMenu)it.next();
				copyMenus(temp,new_cm.getOid(),portalid);
			}		
		}		
		return true;
	}
	/**
	 * 获得指定系统下的所有菜单xml
	 * @param portalid
	 * @return
	 */
	public String getMenuXmls(Long portalid){
		List<CoreMenu> cms=this.find("from CoreMenu where portalid=? and pmid is null", new Object[]{portalid});
		DHtmlXTree	dx=new DHtmlXTree();
		dx.setTreeId("P_"+portalid);
		CoreOrg cp=(CoreOrg)get(CoreOrg.class, portalid);
		//设置根节点
		Node nd=dx.initNode();
		nd.setId("R_"+portalid);
		nd.setText(cp.getOrgName());
		nd.setIm0(cp.getInco());
		nd.setIm1(cp.getOpenInco());
		nd.setIm2(cp.getCloseInco());		
		List subnd=new ArrayList();
		 for(CoreMenu cm:cms){
			subnd.add(getNode(cm,dx));
			//dx.addNode(getNode(cm,dx));
		 }
		 nd.setSubNodeList(subnd);
		 dx.addNode(nd);
		return dx.getDHtmlXmlTree();
	}
	
	public List<CoreMenu> getMenusByPortalid(Long portalid)
	{
		List<CoreMenu> menus = find("from CoreMenu where portalid=? and pmid is null", new Object[]{portalid});
		return menus;
	}
	public List<CoreMenu> getMenusBypmip(Long pmid)
	{
		List<CoreMenu> menus = find("from CoreMenu where  pmid is not null and pmid=?", new Object[]{pmid});
		if(menus == null)
			menus = new ArrayList();
		return menus;
	}
	public String getAllMenusByPortalid(Long portalid)
	{
		StringBuffer menu = new StringBuffer();
		List<CoreMenu> menus = find("from CoreMenu where portalid=?", new Object[]{portalid});
		for(CoreMenu child : menus)
		{
			if(child.getPmid()!= null)
			continue;
			menu.append("<li><a href='" + child.getUrl() + "'>");
			menu.append(child.getMenuname());
			menu.append("</a>");
			getAllChildrenByMenu(menu, child);
			menu.append("</li>");
		}
		return menu.toString();
		
	}
	public String getAllChildrenByMenus(String keys) {
		List<CoreMenu> ls = new ArrayList();
		StringBuffer menu = new StringBuffer();
		if (!StringUtil.isEmpty(keys)) {
			List list = StringUtil.splitToList(keys, ",");
			Iterator it = list.iterator();
			while (it.hasNext()) {
				CoreMenu coreMenu = (CoreMenu)get(Long.parseLong(it.next().toString()));
				if (coreMenu != null)
					ls.add(coreMenu);
			}
		}
		for (CoreMenu child : ls) {
			menu.append("<li><a href='" + child.getUrl() + "'>");
			menu.append(child.getMenuname());
			menu.append("</a>");
			getAllChildrenByMenu(menu, child);
			menu.append("</li>");
		}
		System.out.println(menu.toString());
		return menu.toString();
	}
	public StringBuffer getAllChildrenByMenu(StringBuffer menu, CoreMenu coreMenu) {
		List<CoreMenu> children = find("from CoreMenu where  pmid is not null and pmid=?", new Object[]{coreMenu.getOid()});
		if (children != null && children.size() > 0) {
			menu.append("<ul>");
			for (CoreMenu child : children) {
				menu.append("<li><a href='" + child.getUrl() + "'>");
				menu.append(child.getMenuname());
				menu.append("</a>");
				getAllChildrenByMenu(menu, child);
				menu.append("</li>");
			}
			menu.append("</ul>");
		}
		return menu;
	}
	//取得指定菜单节点
	private Node getNode(CoreMenu cm ,DHtmlXTree dx){
		Node nd=dx.initNode();
		nd.setId(cm.getOid().toString());
		nd.setText(cm.getMenuname());
		nd.setIm0(cm.getInco());
		nd.setIm1(cm.getOpenInco());
		nd.setIm2(cm.getCloseInco());
		
		Set subm=cm.getSubMenus();
		if(subm!=null&&subm.size()>0){
		Iterator it = subm.iterator();
		List ls=new ArrayList();
			while (it.hasNext()) {
           ls.add(getNode((CoreMenu)it.next(),dx));
		}
		   nd.setSubNodeList(ls);
		}
		return nd;
	}
	
}
