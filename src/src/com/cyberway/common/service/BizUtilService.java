package com.cyberway.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;

import com.cyberway.core.dao.HibernateGenericDao;
import com.cyberway.core.objects.FlowConstants;
import com.cyberway.core.utils.BeanUtil;
import com.cyberway.core.utils.StringUtil;

/**
 * 业务公务操作Service
 * @author caiw
 *
 */
public class BizUtilService extends HibernateGenericDao {
	
	private CommonCache commonCache;//公用缓存
	/**
	 * 获得流程包ids
	 * @return
	 */
	public Map getLoadedPackageIds(){
		Map pkids=null;
		Object pkobjs=commonCache.getFlowDataFromCache(FlowConstants.FLOW_PACKAGEIDS_KEY);
		if(pkobjs!=null && pkobjs instanceof Map)
			pkids=(Map)pkobjs;
		else
			pkids=new HashMap();
		return pkids;
	}
	/**
	 * 获得流程版本名称
	 * @return
	 */
	public List getProcessMgrs(){
		List pmgr=null;
		Object pkobjs=commonCache.getFlowDataFromCache(FlowConstants.FLOW_PROCESSMGRS_KEY);
		if(pkobjs!=null && pkobjs instanceof List)
			pmgr=(List)pkobjs;
		else
			pmgr=new ArrayList();
		return pmgr;
	}
	/***
    *复制一条记录，同时复制其指定的子记录
    * @param pojoclass
    * @param oids
    * @param subname 子关系名
    * @param parentname 子对象指父的名字
    * @return
    */
   public boolean CopyObjectsAndsub(Class pojoclass,List oids,String subname,String parentname,List addcopyrelations,String keyname){
   	boolean succ=false;
   	String key="oid";
   	 if(!StringUtil.isEmpty(keyname))
   		 key=keyname;
		if(oids!=null&&!oids.isEmpty()){
		try{
		for(int i=0;i<oids.size();i++){
			Object obj=get(pojoclass,(Long)oids.get(i));
			if(obj==null)
				return succ;
			Object newobj=obj.getClass().newInstance();
			BeanUtil.updateObject(obj,newobj);
			//PropertyUtils.copyProperties(obj,newobj);
			PropertyUtils.setProperty(newobj,key,null);
			if(addcopyrelations!=null){
				for(int n=0;n<addcopyrelations.size();n++){
					String addcopy=(String)addcopyrelations.get(n);
				    PropertyUtils.setProperty(newobj,addcopy,PropertyUtils.getProperty(obj,addcopy));
				 }
			}
			//PropertyUtils.setProperty(newobj,"fsProvider",PropertyUtils.getProperty(obj,"fsProvider"));
			save(newobj);
			if(!StringUtil.isEmpty(subname)&&!StringUtil.isEmpty(parentname)){
				Object subset=PropertyUtils.getProperty(obj,subname);
				if(subset!=null&&subset instanceof Set){				    
				    Iterator it=((Set)subset).iterator();
				    while(it.hasNext()){
				    	Object tobj=it.next();
  		    	        Object newtobj=tobj.getClass().newInstance();
				    							
						BeanUtil.updateObject(tobj,newtobj);
				    	PropertyUtils.setProperty(newtobj,key,null);
				    	PropertyUtils.setProperty(newtobj,parentname,newobj);
				    	save(newtobj);
				    }
				}
			}else
				logger.debug("subname or parentname is null");
		}
		succ=true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		}		
		return succ;   	
   }
	public CommonCache getCommonCache() {
		return commonCache;
	}
	public void setCommonCache(CommonCache commonCache) {
		this.commonCache = commonCache;
	}
}
