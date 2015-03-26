package com.cyberway.cms.document.service;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import com.cyberway.cms.Constants;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.CmsBaseDocument;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.domain.DocumentItem;
import com.cyberway.cms.domain.LogLucene;
import com.cyberway.cms.form.domain.CoreForm;
import com.cyberway.cms.form.object.BaseDocument;
import com.cyberway.cms.site.cache.SiteCache;
import com.cyberway.cms.siteDistribution.view.SiteDistributionController;
import com.cyberway.common.domain.CoreUser;
import com.cyberway.common.service.EntityEventListener;
import com.cyberway.common.service.HibernateEventListener;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.staticer.ftp.SiteCompositeFTPClient;
import com.cyberway.staticer.ftp.SiteFTPClient;

public class HibernateSynchronizer implements EntityEventListener,
		InitializingBean, Runnable, ServletContextAware {
	
	private static final Logger LOG = Logger.getLogger(HibernateSynchronizer.class);
	private Directory _directory = new RAMDirectory();
	private Analyzer _analyzer = new StandardAnalyzer();
	private HibernateEventListener _hibernateEventListener;
	
	private HibernateEntityDao _entityDao;
	private ServletContext _servletContext;
	private boolean _sync = false;
	private SiteCache siteCache;
	
	
	public void setSync(boolean sync) {
		_sync = sync;
	}

	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	public HibernateEntityDao getEntityDao() {
		return _entityDao;
	}

	public void setEntityDao(HibernateEntityDao entityDao) {
		_entityDao = entityDao;
	}

	public HibernateEventListener getHibernateEventListener() {
		return _hibernateEventListener;
	}

	public void setHibernateEventListener(
			HibernateEventListener hibernateEventListener) {
		_hibernateEventListener = hibernateEventListener;
	}

	public Directory getDirectory() {
		return _directory;
	}

	public void setDirectory(Directory directory) {
		_directory = directory;
	}

	public Analyzer getAnalyzer() {
		return _analyzer;
	}

	public void setAnalyzer(Analyzer analyzer) {
		_analyzer = analyzer;
	}

	protected void put(final BaseDocument entity, IndexWriter writer)
			throws Exception {
		// 控制指定频道，可不建索引
		if (entity != null && entity.getChannel() != null
				&& entity.getChannel().getId() != null) {
			Channel channel = siteCache.getChannelFromCach(entity
					.getChannel().getId());
			if (channel != null && channel.getIsSearch() != null
					&& channel.getIsSearch() <= 0)// 若isSearch为0时，频道下信息不建索引
				return;
		} else
			return;

		final Document document = new Document();

		BeanWrapper wrapper = new BeanWrapperImpl(entity);
		for (PropertyDescriptor descriptor : wrapper.getPropertyDescriptors()) {
			org.hibernate.search.annotations.Field annotation = descriptor
					.getReadMethod().getAnnotation(
							org.hibernate.search.annotations.Field.class);

			if (annotation != null) {
				Field.Index index = Field.Index.UN_TOKENIZED;
				if (ObjectUtils.equals(annotation.index(),
						org.hibernate.search.annotations.Index.NO)) {
					index = Field.Index.NO;
				} else if (ObjectUtils.equals(annotation.index(),
						org.hibernate.search.annotations.Index.NO_NORMS)) {
					index = Field.Index.NO_NORMS;
				} else if (ObjectUtils.equals(annotation.index(),
						org.hibernate.search.annotations.Index.UN_TOKENIZED)) {
					index = Field.Index.UN_TOKENIZED;
				} else if (ObjectUtils.equals(annotation.index(),
						org.hibernate.search.annotations.Index.TOKENIZED)) {
					index = Field.Index.TOKENIZED;
				}

				if (ClassUtils.isAssignable(
						wrapper.getPropertyType(descriptor.getName()),
						Date.class)) {
					Date value = (Date) wrapper.getPropertyValue(descriptor
							.getName());

					if (value != null) {
						document.add(new Field(descriptor.getName(),
								DateFormatUtils.format(value,
										DateFormatUtils.ISO_DATE_FORMAT
												.getPattern()),
								Field.Store.YES, index));
					}
				} else if (ClassUtils.isAssignable(
						wrapper.getPropertyType(descriptor.getName()),
						Channel.class)) {
					Channel value = (Channel) wrapper
							.getPropertyValue(descriptor.getName());

					if (value != null) {
						document.add(new Field(descriptor.getName(),
								ObjectUtils.toString(value.getId()),
								Field.Store.YES, index));
						value = siteCache.getChannelFromCach(value.getId());
						document.add(new Field("channelName", value.getName(), Field.Store.YES, Field.Index.UN_TOKENIZED));
					}
				} else if (ClassUtils.isAssignable(
						wrapper.getPropertyType(descriptor.getName()),
						CmsSite.class)) {
					CmsSite value = (CmsSite) wrapper
							.getPropertyValue(descriptor.getName());

					if (value != null) {
						document.add(new Field(descriptor.getName(),
								ObjectUtils.toString(value.getOid()),
								Field.Store.YES, index));
					}
				} else if (ClassUtils.isAssignable(
						wrapper.getPropertyType(descriptor.getName()),
						CoreUser.class)) {
					CoreUser value = (CoreUser) wrapper
							.getPropertyValue(descriptor.getName());

					if (value != null) {
						document.add(new Field(descriptor.getName(),
								ObjectUtils.toString(value.getUserid()),
								Field.Store.YES, index));
					}
				} else if("body".equalsIgnoreCase(descriptor.getName()) || "subBody".equalsIgnoreCase(descriptor.getName())){
					Object obj = wrapper.getPropertyValue(descriptor.getName());
					if(obj!=null && StringUtils.isNotBlank(obj.toString()) && document.getField(descriptor.getName()) == null && !obj.toString().startsWith(Constants.INFO_OFFICE_PATH)){
						document.add(new Field(descriptor.getName(), ObjectUtils.toString(wrapper.getPropertyValue(descriptor.getName())), Field.Store.YES, index));
					}
				} else {
					document.add(new Field(descriptor.getName(), ObjectUtils
							.toString(wrapper.getPropertyValue(descriptor.getName())), Field.Store.YES, index));
				}
			}
		}
		
		// 增加office内容到搜索中
		if (Constants.INFO_OFFICE_FILE_CONTENT_ISSEARCH) {
			CmsBaseDocument cbd = (CmsBaseDocument) _entityDao.get(CmsBaseDocument.class, entity.getId());
			if (cbd != null){
				String body = StringUtils.strip(cbd.getBody());
				if (StringUtils.isNotBlank(body)) {
					Field.Index index = Field.Index.TOKENIZED;
					document.add(new Field("filebody", body, Field.Store.NO, index));
					if(body.length()>50){
						body = body.substring(0, 48)+"...";
					}
					if(document.getField("body") == null){
						document.add(new Field("body", body, Field.Store.YES, index));
					}
					if(document.getField("subBody") != null){
						document.removeField("subBody");
					}
					document.add(new Field("subBody", body, Field.Store.YES, index));
				}
			}
		}
		writer.addDocument(document);

	}

	protected void put(final com.cyberway.cms.domain.Document entity,
			IndexWriter writer) throws Exception {
		// 控制指定频道，可不建索引
		if (entity != null && entity.getChannel() != null
				&& entity.getChannel().getId() != null) {
			Channel channel = siteCache.getChannelFromCach(entity
					.getChannel().getId());
			if (channel != null && channel.getIsSearch() != null
					&& channel.getIsSearch() <= 0)// 若isSearch为0时，频道下信息不建索引
				return;
		} else
			return;
		final Document document = new Document();

		BeanWrapper wrapper = new BeanWrapperImpl(entity);
		for (PropertyDescriptor descriptor : wrapper.getPropertyDescriptors()) {
			org.hibernate.search.annotations.Field annotation = descriptor
					.getReadMethod().getAnnotation(
							org.hibernate.search.annotations.Field.class);

			if (annotation != null) {
				Field.Index index = Field.Index.UN_TOKENIZED;
				if (ObjectUtils.equals(annotation.index(),
						org.hibernate.search.annotations.Index.NO)) {
					index = Field.Index.NO;
				} else if (ObjectUtils.equals(annotation.index(),
						org.hibernate.search.annotations.Index.NO_NORMS)) {
					index = Field.Index.NO_NORMS;
				} else if (ObjectUtils.equals(annotation.index(),
						org.hibernate.search.annotations.Index.UN_TOKENIZED)) {
					index = Field.Index.UN_TOKENIZED;
				} else if (ObjectUtils.equals(annotation.index(),
						org.hibernate.search.annotations.Index.TOKENIZED)) {
					index = Field.Index.TOKENIZED;
				}

				if (ClassUtils.isAssignable(
						wrapper.getPropertyType(descriptor.getName()),
						Date.class)) {
					Date value = (Date) wrapper.getPropertyValue(descriptor
							.getName());

					if (value != null) {
						document.add(new Field(descriptor.getName(),
								DateFormatUtils.format(value,
										DateFormatUtils.ISO_DATE_FORMAT
												.getPattern()),
								Field.Store.YES, index));
					}
				} else if (ClassUtils.isAssignable(
						wrapper.getPropertyType(descriptor.getName()),
						Channel.class)) {
					Channel value = (Channel) wrapper
							.getPropertyValue(descriptor.getName());

					if (value != null) {
						document.add(new Field(descriptor.getName(),
								ObjectUtils.toString(value.getId()),
								Field.Store.YES, index));
					}
				} else if (ClassUtils.isAssignable(
						wrapper.getPropertyType(descriptor.getName()),
						CmsSite.class)) {
					CmsSite value = (CmsSite) wrapper
							.getPropertyValue(descriptor.getName());

					if (value != null) {
						document.add(new Field(descriptor.getName(),
								ObjectUtils.toString(value.getOid()),
								Field.Store.YES, index));
					}
				} else if (ClassUtils.isAssignable(
						wrapper.getPropertyType(descriptor.getName()),
						CoreUser.class)) {
					CoreUser value = (CoreUser) wrapper
							.getPropertyValue(descriptor.getName());

					if (value != null) {
						document.add(new Field(descriptor.getName(),
								ObjectUtils.toString(value.getUserid()),
								Field.Store.YES, index));
					}
				} else if("body".equalsIgnoreCase(descriptor.getName()) || "subBody".equalsIgnoreCase(descriptor.getName())){
					Object obj = wrapper.getPropertyValue(descriptor.getName());
					if(obj!=null && StringUtils.isNotBlank(obj.toString()) && document.getField(descriptor.getName()) == null && !obj.toString().startsWith(Constants.INFO_OFFICE_PATH)){
						document.add(new Field(descriptor.getName(), ObjectUtils.toString(wrapper.getPropertyValue(descriptor.getName())), Field.Store.YES, index));
					}
				} else {
					document.add(new Field(descriptor.getName(), ObjectUtils
							.toString(wrapper.getPropertyValue(descriptor
									.getName())), Field.Store.YES, index));
				}
			}
		}

		CollectionUtils.forAllDo(entity.getItems(), new Closure() {
			public void execute(Object obj) {
				document.add(new Field(((DocumentItem) obj).getName(),
						ObjectUtils.toString(entity.get(((DocumentItem) obj)
								.getName())), Field.Store.YES,
						Field.Index.UN_TOKENIZED));
			}
		});

		if (Constants.INFO_OFFICE_FILE_CONTENT_ISSEARCH) {
			// 增加office内容到搜索中
			CmsBaseDocument cbd = (CmsBaseDocument) _entityDao.get(CmsBaseDocument.class, entity.getId());
			if (cbd != null){
				String body = StringUtils.strip(cbd.getBody());
				if (StringUtils.isNotBlank(body)) {
					Field.Index index = Field.Index.TOKENIZED;
					document.add(new Field("filebody", body, Field.Store.NO, index));
					if(body.length()>50){
						body = body.substring(0, 48)+"...";
					}
					if(document.getField("body") == null){
						document.add(new Field("body", body, Field.Store.YES, index));
					}
					if(document.getField("subBody") != null){
						document.removeField("subBody");
					}
					document.add(new Field("subBody", body, Field.Store.YES, index));
				}
			}
		}

		writer.addDocument(document);

	}

	protected void delete(Long id, IndexWriter writer) throws Exception {
		writer.deleteDocuments(new Term("id", ObjectUtils.toString(id)));
	}

	public void entityDeleted(Object entity) {
		if (ClassUtils.isAssignable(entity.getClass(), BaseDocument.class)) {
			IndexWriter writer = null;

			try {
				writer = new IndexWriter(getDirectory(), getAnalyzer());
				delete(((BaseDocument) entity).getId(), writer);
				
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				try {
					writer.close();
					if(!Constants.LUCENESYNCHROISMTYPE){
						try{
							ftpmodified();
						}catch(Exception ex){
							ex.printStackTrace();
						}
					}
				} catch (Exception e) {
				}
			}
		}
	}

	public void entityInserted(Object entity) {
		if (ClassUtils.isAssignable(entity.getClass(), BaseDocument.class)) {
			IndexWriter writer = null;

			try {
				writer = new IndexWriter(getDirectory(), getAnalyzer());

				if (ClassUtils.isAssignable(entity.getClass(),
						com.cyberway.cms.domain.Document.class)) {
					put((com.cyberway.cms.domain.Document) entity, writer);
				} else {
					put((BaseDocument) entity, writer);
				}
				
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				try {
					writer.close();
					if(!Constants.LUCENESYNCHROISMTYPE){
						try{
							ftpmodified();
						}catch(Exception ex){
							ex.printStackTrace();
						}
					}
				} catch (Exception e) {
				}
			}
		}
	}

	public void entityModified(Object entity, Object[] state, Object[] oldState) {
		if (ClassUtils.isAssignable(entity.getClass(), BaseDocument.class)) {
			IndexWriter writer = null;

			try {
				writer = new IndexWriter(getDirectory(), getAnalyzer());

				delete(((BaseDocument) entity).getId(), writer);

				if (ClassUtils.isAssignable(entity.getClass(),
						com.cyberway.cms.domain.Document.class)) {
					put((com.cyberway.cms.domain.Document) entity, writer);
				} else {
					put((BaseDocument) entity, writer);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				try {
					writer.close();
					if(!Constants.LUCENESYNCHROISMTYPE){
						try{
							ftpmodified();
						}catch(Exception ex){
							ex.printStackTrace();
						}
					}
				} catch (Exception e) {
				}
			}
		}
	}
	
	/*分发Lunece去FTP*/
	private void ftpmodified() throws Exception{
		Loginer loginer = (Loginer) ServletActionContext.getContext().getSession().get(Loginer.LOGININFO_SESSION);
		String resourceType = SiteDistributionController.LUCENE_RESOURCE;// 资源类型
		if(resourceType != null){
			SiteFTPClient siteFTPClient = new SiteCompositeFTPClient(loginer.getSiteId(), resourceType);
			
			//FTP目录
			//String ftpPath=centerDistribution.getDefaultRoot()+centerDistribution.getLucenePath();
			String ftpPath = Constants.DEFAULT_ROOT + Constants.LUCENE_PATH_FTP;
			
			int count=0;
			List<LogLucene> logLucenes=siteCache.getAllLuneces();
			
			//本地目录
			getDirectory().clearLock(IndexWriter.WRITE_LOCK_NAME);
			String filePath=getDirectory().toString().substring(getDirectory().toString().indexOf("@")+1, getDirectory().toString().length());
			File filez = new File(filePath);
			File[] filesz = filez.listFiles();
			if(filesz != null){
				for (int i = 0; i < filesz.length; i++) {
					if(filesz[i]==null){
						return;
					}
				if(!filesz[i].isDirectory()){
					String fileName=filesz[i].getPath().substring(filePath.length(), filesz[i].getPath().length());
					File file=new File (filePath+fileName);
					if(file.exists()){
						try{
							for(LogLucene logLucene:logLucenes){
								if(StringUtil.ifEqual(fileName, logLucene.getFileName())&&(file.lastModified()!=logLucene.getCreateTime())){
									//修改过后的文件
									//getFtpClientService().upload(ftpPath+fileName, FileUtils.readFileToByteArray(file),true);
									siteFTPClient.upload(loginer.getSiteId(), resourceType, ftpPath + fileName, file, true);
									LOG.info("上传成功"+"cmsURL"+filePath+fileName+"-----------"+"ftpURL:"+ftpPath+fileName);
									
									logLucene.setCreateTime(file.lastModified());
									siteCache.putLuneceCache(logLucene);
									count++;
									break;
								}
								if(StringUtil.ifEqual(fileName, logLucene.getFileName())){
									count++;
									break;
								}
							}
							if(count==0){
								//新出现的文件
								//getFtpClientService().upload(ftpPath+fileName, FileUtils.readFileToByteArray(file),true);
								siteFTPClient.upload(loginer.getSiteId(), resourceType, ftpPath + fileName, file, true);
								LOG.info("上传成功"+"cmsURL"+filePath+fileName+"-----------"+"ftpURL:"+ftpPath+fileName);
								
								LogLucene logLucene=new LogLucene();
								logLucene.setFileName(fileName);
								logLucene.setCreateTime(file.lastModified());
								siteCache.putLuneceCache(logLucene);
							}
							count=0;
						}catch(Exception ex){
							LOG.error("上传lucene文件失败", ex);
							continue;
						}
					}else{
	    				LOG.info("发布平台上文件不存在");
					}
				}
			}
		   }
			count=0;
			logLucenes=siteCache.getAllLuneces();
			 filez = new File(filePath);
			 filesz = filez.listFiles();
			for(LogLucene logLucene:logLucenes){
				if(filesz != null){
					for (int i = 0; i < filesz.length; i++) {
						if(filesz[i]==null){
							return;
						}
					if(!filesz[i].isDirectory()){
						String fileName=filesz[i].getPath().substring(filePath.length(), filesz[i].getPath().length());
						File file=new File (filePath+fileName);
							if(file.exists()){
								if(StringUtil.ifEqual(fileName, logLucene.getFileName())){
									count++;
									break;
								}
							}
						}
					}
				}
				if(count==0){
					try{
						//String rootPath = Configuration.servletContext().getRealPath("");
						String rootPath=getDirectory().toString().substring(getDirectory().toString().indexOf("@")+1, getDirectory().toString().length());
						//getFtpClientService().delete(centerDistribution.getDefaultRoot()+centerDistribution.getLucenePath()+logLucene.getFileName());
						siteFTPClient.delete(loginer.getSiteId(), resourceType, ftpPath + logLucene.getFileName(), rootPath + logLucene.getFileName());
						LOG.info("删除lucene文件成功"+logLucene.getFileName());
						
						siteCache.removeLuneceFromCache(logLucene.getFileName());
					}catch(Exception ex){
						LOG.info("删除lucene文件失败"+logLucene.getFileName()+ex.getMessage());
						continue;
					}
				}
				count=0;
			}
		}else{
			LOG.info("=================没有找到对应的资源类型,无法分发!======================");
		}
		
		//删除服务器多余的文件--用于手动删除本地文件时与服务器同步
		/*
		count=0;
		List<String> ls=centerDistribution.getList(ftpPath);
		for(String str:ls){
			try{
				if(filesz != null){
					for (int i = 0; i < filesz.length; i++) {
						if(filesz[i]==null){
							return;
						}
					if(!filesz[i].isDirectory()){
						String fileName=filesz[i].getPath().substring(filePath.length(), filesz[i].getPath().length());
						cacheName=fileName.startsWith("\\")?fileName.substring(1):fileName;
						File file=new File (filePath+fileName);
							if(file.exists()){
								if(StringUtil.ifEqual(cacheName, str)){
									count++;
									break;
								}
							}
						}
					}
				}
				if(count==0){
					getFtpClientService().delete(centerDistribution.getDefaultRoot()+centerDistribution.getLucenePath()+"/"+str);
					LOG.info("删除lucene文件成功");
				}
				count=0;
			}catch(Exception ex){
				LOG.info("删除lucene文件失败",ex);
				continue;
			}
		}
		*/
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		IndexWriter writer = null;
		
		//FTP路径
		String ftpPath = Constants.DEFAULT_ROOT + Constants.LUCENE_PATH_FTP;
		//本地文件路径
		String filePath=getDirectory().toString().substring(getDirectory().toString().indexOf("@")+1, getDirectory().toString().length());
		LogLucene logLucene;
		if (!Constants.DOCUMENT_BUILDINDEX)
			return;
		try {
			//清除FTP上的索引文件
			/*List<String> ls=centerDistribution.getList(ftpPath);
    		for(String str:ls){
    			try{
    				getFtpClientService().delete(centerDistribution.getDefaultRoot()+centerDistribution.getLucenePath()+str);
    				LOG.info("删除lucene文件成功");
    			}catch(Exception ex){
    				LOG.info("删除lucene文件失败"+ex.getMessage());
    				continue;
    			}
    		}*/
    		
    		//清除lock文件
			getDirectory().clearLock(IndexWriter.WRITE_LOCK_NAME);
			
			writer = new IndexWriter(getDirectory(), getAnalyzer(), true);
			
			List forms = getAllClassMetadata();
			for (Object form : forms) {
				if (form instanceof String) {
					String className = (String) form;
					BuildIndex(className, writer);
				}
			}
		} catch (Exception ex) {
			LOG.error("重建索引时出错！", ex);
		} finally {
			try {
				writer.close();
				
				if(!Constants.LUCENESYNCHROISMTYPE){
					File filez = new File(filePath);
		    		File[] filesz = filez.listFiles();
					if(filesz != null){
		    			for (int i = 0; i < filesz.length; i++) {
		    				if(filesz[i]==null){
		    					return;
		    				}
		    			if(!filesz[i].isDirectory()){
		    				String fileName=filesz[i].getPath().substring(filePath.length(), filesz[i].getPath().length());
		    			
		    				logLucene=new LogLucene();
		    				logLucene.setCreateTime(filesz[i].lastModified());
		    				logLucene.setFileName(fileName);
		    				
		    				siteCache.putLuneceCache(logLucene);
		    				
		    				/*File _file=new File (filePath+fileName);
		    				if(_file.exists()){
		    					try{
		    	    				getFtpClientService().upload(ftpPath+fileName, FileUtils.readFileToByteArray(_file),true);
		    	    				LOG.info("上传lucene文件成功"+"cmsURL"+filePath+fileName+"-----------"+"ftpURL:"+ftpPath);
		    	    			}catch(Exception ex){
		    	    				LOG.info("上传lucene文件失败"+ex.getMessage());
		    	    				continue;
		    	    			}
		    	    			
		    				}else{
			    				LOG.info("发布平台上文件不存在");
		    				}*/
		    			}
		    		}
		    	   }
				}
			} catch (Exception e) {
				LOG.error("", e);
			}
		}
	}

	/**
	 * 获得当前系统下，所有pojo
	 * 
	 * @return
	 */
	public List getAllClassMetadata() {
		List allClass = new ArrayList();
		List<CoreForm> forms = this.getEntityDao().getAll(CoreForm.class);
		for (CoreForm form : forms) {
			if (form != null && form.getPojoName() != null
					&& !allClass.contains(form.getPojoName()))
				allClass.add(form.getPojoName());
		}
		/*
		 * //默认加载动态表单;
		 * 
		 * allClass.add("com.cyberway.cms.domain.Document");
		 * 
		 * Map alls=getEntityDao().getSessionFactory().getAllClassMetadata();
		 * Iterator iterator = alls.keySet().iterator();
		 * while(iterator.hasNext()){ String clazz=(String)iterator.next();
		 * if(clazz.startsWith("com.cyberway.cms.form") &&
		 * !clazz.startsWith("com.cyberway.cms.form.CoreForm"
		 * ))//只取com.cyberway.cms.form下的 allClass.add(clazz); }
		 */
		return allClass;
	}


	/**
	 * 重建索引
	 * 
	 * @param class1
	 * @param writer
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean BuildIndex(String entityClass, IndexWriter writer) {
		try {
			if (entityClass == null || writer == null)
				return false;
			List objs;

			int total = getEntityDao().getCount(Class.forName(entityClass));
			// int total=getEntityDao().getCount(hql);
			int currNum = 0, maxNum = total;
			while (total > 0 && currNum < total) {
				if (maxNum > Constants.DOCUMENT_MAXOBJECT_COUNT)
					maxNum = currNum + Constants.DOCUMENT_MAXOBJECT_COUNT;
				/*
				 * if(maxNum>total) maxNum=total;
				 */

				objs = getEntityDao().getObjects(Class.forName(entityClass),
						currNum, Constants.DOCUMENT_MAXOBJECT_COUNT);
				currNum += Constants.DOCUMENT_MAXOBJECT_COUNT;

				for (Object entity : objs) {
					if (ClassUtils.isAssignable(entity.getClass(),
							com.cyberway.cms.domain.Document.class)) {
						put((com.cyberway.cms.domain.Document) entity, writer);
					} else if (ClassUtils.isAssignable(entity.getClass(),
							BaseDocument.class)) {
						put((BaseDocument) entity, writer);
					}
				}
			}
			
		} catch (Exception ex) {
			LOG.error("建立" + entityClass + "时出错！", ex);
		}

		return true;
	}

	public void afterPropertiesSet() throws Exception {
		if (_sync) {
			run();
		}
		_hibernateEventListener.getListeners().add(this);
	}

	public SiteCache getSiteCache() {
		return siteCache;
	}

	public void setSiteCache(SiteCache siteCache) {
		this.siteCache = siteCache;
	}
	
}
