package com.cyberway.cms.staticResource.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.web.context.ServletContextAware;

import com.cyberway.cms.Constants;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.domain.StaticResource;
import com.cyberway.cms.site.cache.SiteCache;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.utils.FileUtil;
import com.cyberway.core.utils.StringUtil;

public class StaticResourceService extends HibernateEntityDao<StaticResource> implements ServletContextAware{
	private SiteCache siteCache;// 站点缓存管理器
	private ServletContext _servletContext;
	private Map _notSRes=new HashMap();//非静态资料

	public void setServletContext(ServletContext servletContext)
	{
		_servletContext = servletContext;
	}
	
	/**
	 * 初始化站点
	 */
	public synchronized void init() {
		List<StaticResource> ress = getAll();
		//logger.info("ress is size:" + ress.size());
		for (StaticResource res : ress){
			//siteCache.putStaticResourceInCache(siteCache.getStaticResourceKey(String.valueOf(res.getSiteid()), res.getName()), res.getServerpath());		
			putStaticResourceInCache(siteCache.getStaticResourceKey(String.valueOf(res.getSiteid()), res.getName()), res.getServerpath());
		}
		}
	/**
	 * put staticResource to cache
	 * @param resKey
	 * @param value
	 */
	public void putStaticResourceInCache(String resKey,String value){
		siteCache.putStaticResourceInCache(resKey,value);		
		//若使用绝对路径，同步更新 解决amway 集群问题
		if(Constants.IS_REALPATH){
			try{
			String sourceSRPath=getRealPath(value);
			FileUtil.update(sourceSRPath, _servletContext.getRealPath(value));
			
			}catch(Exception e){
				
			}
		}
	}
	/**
	 * 检测服务器上目录是否存在，不存在则创建,返回地址
	 * 
	 * @param staticResourcePathName
	 * @return
	 * @throws Exception
	 */
	public String getRealPath(String staticResourcePathName) throws Exception {
		String fullpath = "";
		if(Constants.IS_REALPATH)
		{
		 fullpath = Constants.ABSOLUTE_PATH;//+Constants.STATICRESOURCE_PATH;
		}
		else{
		 // fullpath = "";//this._servletContext.getRealPath(Constants.STATICRESOURCE_PATH);
			fullpath = this._servletContext.getRealPath("/");
		}
		java.io.File f = new java.io.File(fullpath);
		if (!f.isDirectory()) {
			f.mkdir();
		}
		fullpath = fullpath +  staticResourcePathName;
		java.io.File file = new java.io.File(fullpath);
		if (!file.isDirectory()) {
			file.mkdir();
		}
		return fullpath;
	}	
	/**
	 * 根据站点、资源名称，获得资源路径
	 * @param siteid
	 * @param resName
	 * @return
	 */
	public String getStaticResourcePath(Long siteid,String resName){
		if(siteid==null || StringUtil.isEmpty(resName))
			return null;
        String resKey=siteCache.getStaticResourceKey(siteid.toString(), resName);
		String rsPath=siteCache.getStaticResourceFromCache(resKey);
/*		//若不为空，检测文件是否存在
		if(!StringUtil.isEmpty(rsPath)){
		File rpfile=new File(_servletContext.getRealPath(rsPath));
		if(!rpfile.exists()){
			try{
			FileUtil.update(getRealPath(rsPath), _servletContext.getRealPath(rsPath));
			} catch (Exception ex) {
				
			}
		}
		}*/
	    //若为空，且是静态资源类型
		if(StringUtil.isEmpty(rsPath) && resName.indexOf("/")<0){
		  if(_notSRes.containsKey(resKey))
			  return null;
		   StaticResource res=getStaticResourceByName(siteid,resName);
		  if(res==null){
			  _notSRes.put(resKey, null);
			  return null;
		  }
		  putStaticResourceInCache(siteCache.getStaticResourceKey(String.valueOf(res.getSiteid()), res.getName()), res.getServerpath());
		  rsPath=res.getServerpath();
		}
		return rsPath;
	}
	/**
	 * 获得站点上指定名称静态资料对象
	 * @param siteid
	 * @param resName
	 * @return
	 */
	public StaticResource getStaticResourceByName (Long siteid,String resName)
	{
		Validate.notNull(siteid);
		Validate.notNull(resName);
		
		DetachedCriteria criteria = DetachedCriteria.forClass(StaticResource.class);
		CmsSite site=siteCache.getSiteFromCache(siteid.toString());
		criteria.add(Restrictions.eq("siteid", siteid.longValue()));
		criteria.add(Restrictions.eq("name", resName));
    	List temps=(List<StaticResource>)getHibernateTemplate().findByCriteria(criteria);
    	if(temps!=null && temps.size()>0)
    		return (StaticResource)temps.get(0);
    	else
		    return null;
	}	
	/* (non-Javadoc)
	 * @see com.cyberway.core.dao.HibernateEntityDao#saveOrUpdate(java.lang.Object)
	 */
	public StaticResource saveOrUpdate(StaticResource obj){
		obj=super.saveOrUpdate(obj);
		
		siteCache.putStaticResourceInCache(siteCache.getStaticResourceKey(String.valueOf(obj.getSiteid()), obj.getName()), obj.getServerpath());		
		return obj;
	}	
	
	public String getSiteName(long siteid)
	{
		SiteManagerService siteService = new SiteManagerService();
		CmsSite cs = siteService.getObject(siteid);
		if(cs !=null)
			return cs.getSitename();
		else
			return "";
		
	}
	
	public void removeFile(List list){
	        Iterator it = list.iterator();
			while(it.hasNext())
			{
				String delid = it.next().toString();
				StaticResource staticResource = get(Long.parseLong(delid));
				if(staticResource != null && staticResource.getServerpath() != null && !StringUtils.isEmpty(staticResource.getServerpath()))
				{
					File file;
					if(Constants.IS_REALPATH)
					{
						file = new File(Constants.ABSOLUTE_PATH+staticResource.getServerpath());
					}
					else
					{
						file = new File(ServletActionContext.getServletContext().getRealPath(staticResource.getServerpath()));
					}
				
				 if (file.isFile()){
	                   file.delete();
	                }
				}
				siteCache.removeStaticResourceFromCache(siteCache.getStaticResourceKey(String.valueOf(staticResource.getSiteid()), staticResource.getName()));
				super.remove(staticResource);
			}
			
	    }
	
	/**
	 * 删除静态资源,包括:数据库,缓存,站点发布平台的文件,站点运行平台的文件
	 * @param list 包含静态资源id的列表
	 */
	public void removeFileBoth(List list) {
		Iterator it = list.iterator();
		while (it.hasNext()) {
			String delid = it.next().toString();
			StaticResource staticResource = get(Long.parseLong(delid));
			if (staticResource != null && staticResource.getServerpath() != null && !StringUtils.isEmpty(staticResource.getServerpath())) {
				File file;
				if (Constants.IS_REALPATH) {
					file = new File(Constants.ABSOLUTE_PATH + staticResource.getServerpath());
				} else {
					file = new File(ServletActionContext.getServletContext().getRealPath(staticResource.getServerpath()));
				}

				if (file.isFile()) {
					file.delete();//删除站点发布平台上的文件
				}
				
			}
			try {
				siteCache.removeStaticResourceFromCache(siteCache.getStaticResourceKey(String.valueOf(staticResource.getSiteid()),
				        staticResource.getName()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			super.remove(staticResource);
		}

	}
	
	public String getFileType(String s)
	{
	if(s.equalsIgnoreCase(".jpg") || s.equalsIgnoreCase(".gif") || s.equalsIgnoreCase(".png"))
		return "image";
	else if(s.equalsIgnoreCase(".css"))
		return "css";
	else if(s.equalsIgnoreCase(".swf"))
		return "flash";
	else if(s.equalsIgnoreCase(".js"))
		return "java script";
	else
		return "document";
	}
	public void setSiteCache(SiteCache siteCache) {
		this.siteCache = siteCache;
	}
	/**
	 * 读取资源文件数据
	 * @param realPath
	 * @return
	 * @throws Exception
	 */
	public String readFileContent(String realPath) throws Exception {
	    FileReader fr = new FileReader(realPath);
	    int ch = 0;
	    StringBuffer sb = new StringBuffer();
	    while ((ch = fr.read()) != -1) {
	      sb.append((char) ch);
	    }
	    return sb.toString();
	  }
	
	  //	把在线编辑获取的文件内容存入服务器对应文件中
	  public void saveFileByTextContent(String strFileName, String strFileContent)
	      throws Exception {
	    FileOutputStream fos = new FileOutputStream(strFileName);
	    fos.write(strFileContent.getBytes());
	    fos.close();
	  }

	
	public Collection<StaticResource> findByType (final Long siteId, String type)
	{
		return CollectionUtils.select(super.findBy("type", type), new Predicate ()
		{
			public boolean evaluate(Object obj)
			{
				return ObjectUtils.equals(((StaticResource)obj).getSiteid(), siteId);
			}
		});
	}
}
