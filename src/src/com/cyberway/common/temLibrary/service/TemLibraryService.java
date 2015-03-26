package com.cyberway.common.temLibrary.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.common.temLibrary.domain.TemLibrary;
import com.cyberway.common.xtree.DHtmlXTree;
import com.cyberway.common.xtree.DHtmlXTree.Node;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.exception.BizException;
import com.cyberway.core.utils.StringUtil;

/**
 * @author caiw
 * 
 *         公共信息管理service
 * 
 */
public class TemLibraryService extends HibernateEntityDao<TemLibrary> {
//	private SiteCache siteCache;
//	public synchronized void init(){
//		List<TemLibrary> TemLibraryList=getAll();
//		if(!TemLibraryList.isEmpty()){			
//			for(TemLibrary temLibrary:TemLibraryList){
//				siteCache.putCoreCommonTypeInCache(coreCommonType);
//			}
//		}
//	}
	
	private SiteManagerService siteManagerService;

	public TemLibrary saveOrUpdate(TemLibrary obj) {
		Boolean unique = this.isNotUnique(obj, "name");
		if (unique)
			throw new BizException("类别名不能重名！");
		return super.saveOrUpdate(obj);
	}

	public String getCommonTypeXmls() {
		List<TemLibrary> types = new ArrayList();
		types = this.find("from TemLibrary where parentid is null");
		DHtmlXTree dx = new DHtmlXTree();
		dx.setTreeId("P_" );
		Node nd = dx.initNode();
		nd.setId("R_");
		nd.setOpen(true);
		nd.setText("模版库分类");
		List subnd = new ArrayList();
		for(TemLibrary cm : types) {
			subnd.add(getNode(cm, dx));
		}
		nd.setSubNodeList(subnd);
		dx.addNode(nd);
		return dx.getDHtmlXmlTree();
	}

	private Node getNode(TemLibrary type, DHtmlXTree dx) {
		Node nd = dx.initNode();
		nd.setId(type.getId().toString());
		nd.setText(type.getName());

		Set subTypes = type.getSubTypes();
		if (subTypes != null && subTypes.size() > 0) {
			Iterator it = subTypes.iterator();
			List ls = new ArrayList();
			while (it.hasNext()) {
				ls.add(getNode((TemLibrary) it.next(), dx));
			}
			nd.setSubNodeList(ls);
		}
		return nd;
	}

	public boolean setParentType(String mid, String pmid, String siteid) {
		if (StringUtil.isEmpty(mid)) {
			return false;
		}
		TemLibrary type = this.get(new Long(mid));
		if (!StringUtil.isEmpty(pmid)) {
			TemLibrary pType = this.get(new Long(pmid));
			
			type.setParentid(pType.getId());
		} else {
			try { 
				type.setParentid(null);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				return false;
			}
		}
		this.save(type);
		return true;
	}
	
	/**
	 * dwr 验证 : Keyword 是否唯一
	 * @remark dwrUnique add by liaozhiyong 2012-03-13
	 * @param coreCommonTypeId
	 * @return String (0 已经存在有，1是唯一)
	 */
	@SuppressWarnings("unchecked")
	public String dwrTemLibraryValidateKeywordUnique(String temLibraryId, String name){
		String flag = "0";
		if (name != null && name.length() > 0) {
			String hql = " from TemLibrary where name='"+name+"' ";
			if(temLibraryId!=null && temLibraryId.length()>0){
				hql += " and id<>"+temLibraryId+" ";
			}  
			List<TemLibrary> list =this.find( hql );
			if(list==null || list.size()<1){
				flag = "1";
			}
		} 
		return flag;
	}
	
}

