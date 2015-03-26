package com.cyberway.common.media.album.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ognl.Ognl;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.Constants;
import com.cyberway.cms.site.cache.SiteCache;
import com.cyberway.common.distribution.service.DistributionService;
import com.cyberway.common.media.album.domain.MediaIntermediate;
import com.cyberway.common.media.audio.domain.Audio;
import com.cyberway.common.media.video.domain.Video;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;

public class MediaIntermediateService extends
		HibernateEntityDao<MediaIntermediate> {
	
	private SiteCache siteCache;
	
	public void init(){
		List<MediaIntermediate> mediaIntermediates=getAll();
		if(!mediaIntermediates.isEmpty()){			
			for(MediaIntermediate mediaIntermediate:mediaIntermediates){
				siteCache.putMedia(mediaIntermediate);
			}
		}
	}
	
	public SiteCache getSiteCache() {
		return siteCache;
	}

	public void setSiteCache(SiteCache siteCache) {
		this.siteCache = siteCache;
	}

	public List findView(String docid, String name, String mediaType) {
		List list=new ArrayList();
		if (docid != null && !StringUtils.isEmpty(docid) && !docid.equals("0")
				&& name != null && !StringUtils.isEmpty(name)
				&& mediaType != null && !StringUtils.isEmpty(mediaType)) {
			List<MediaIntermediate> mediaIntermediates=siteCache.getMediaIntermediateByStartWith(docid+"_"+mediaType);
			for(MediaIntermediate mediaIntermediate : mediaIntermediates){
				 if (mediaType != null && StringUtil.ifEqual(mediaType, "video")) {
						list.add(get(Video.class, mediaIntermediate.getMediaId()));
					}else if(mediaType != null && StringUtil.ifEqual(mediaType, "audio")){
						list.add(get(Audio.class, mediaIntermediate.getMediaId()));
				 }
				 
			}
		}
		return list;
	}

	/**
	 * 保存之前改变状态确定最终的类型 未传名字将会一份文档只能对应一个视频
	 * 
	 * docId 文档ID
	 * param 所有的参数[name、mediaType、mediaId,documentIssued]
	 * 
	 * Frank
	 * 
	 */
	public void beforsave(String docId, String param,HttpServletRequest request) {
		try {
			Loginer loginer = (Loginer)request.getSession().getAttribute(Loginer.LOGININFO_SESSION);
			String name="",mediaType="",filePath="",oldFilePath="";
			Long mediaId=0l,documentIssued=0l;
			Video video = null;
			Audio audio = null;
			if(!StringUtil.isNumber(param)&&param.indexOf("&")>0){
				String[] params=param.split("&");
				if(params.length==4){
					name=params[0].split("=")[1];
					mediaType=params[1].split("=")[1];
					mediaId=Long.valueOf(params[2].split("=")[1]);
					documentIssued=Long.valueOf(params[3].split("=")[1]);
				}
			}
			//修改过后直接取相应文档的ID
			List<MediaIntermediate> list=siteCache.getMediaIntermediateByStartWith(docId+"_"+mediaType);
			MediaIntermediate mediaIntermediate=null;
			for(MediaIntermediate _mediaIntermediate:list){
				if (mediaType != null && StringUtil.ifEqual(mediaType, "video")) {
						video =(Video)get(Video.class, _mediaIntermediate.getMediaId());
						oldFilePath=video.getFilePath();
					}else if(mediaType != null && StringUtil.ifEqual(mediaType, "audio")){
						audio =(Audio)get(Audio.class, _mediaIntermediate.getMediaId());
						oldFilePath=audio.getFilePath();
				 }
				mediaIntermediate=_mediaIntermediate;
				siteCache.removeMedia(_mediaIntermediate);
			}
			if(mediaIntermediate==null){
				mediaIntermediate=new MediaIntermediate();
			}
			//得到新选择的文件路径
			if (mediaType != null && StringUtil.ifEqual(mediaType, "video")) {
				video =(Video)get(Video.class, mediaId);
				filePath=video.getFilePath();
			}else if(mediaType != null && StringUtil.ifEqual(mediaType, "audio")){
				audio =(Audio)get(Audio.class, mediaId);
				filePath=audio.getFilePath();
			}
			
			Ognl.setValue("mediaId", mediaIntermediate, mediaId);
			Ognl.setValue("docId", mediaIntermediate, docId);
			Ognl.setValue("type", mediaIntermediate, mediaType);
			Ognl.setValue("name", mediaIntermediate, name);
			
			DistributionService distributionService = (DistributionService) ServiceLocator
			.getBean("distributionService");
			if(documentIssued==5l){
				distributionService.uploadByFtpflv(filePath, Constants.UPLOADS_MEDIA_PATH+filePath,oldFilePath, false,loginer.getSiteId());	
			}
			saveOrUpdate(mediaIntermediate);
			siteCache.putMedia(mediaIntermediate);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 清除所有media引用缓存
	 */
	public void removeAllCache() {
		siteCache.removeAllMedias();
	}

	/**
	 * 获取所有media缓存中的key
	 * 
	 * @return
	 */
	public List<String> getAllCacheKeys() {
		return siteCache.getAllMediaKeys();
	}
	
	/**
	 * 获得指定的media缓存
	 * 
	 * @param key
	 * @return
	 */
	public net.sf.ehcache.Element getElementFromCache(String key) {
		if (StringUtils.isNotEmpty(key)) {
			net.sf.ehcache.Element element = null;
			element = siteCache.getElementFromMedia(key);
			return element;
		} else
			return null;
	}
}
