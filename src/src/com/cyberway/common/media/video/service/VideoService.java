package com.cyberway.common.media.video.service;

import java.util.List;

import org.hibernate.Criteria;

import com.cyberway.cms.Constants;
import com.cyberway.cms.site.cache.SiteCache;
import com.cyberway.common.media.album.domain.MediaIntermediate;
import com.cyberway.common.media.album.service.MediaIntermediateService;
import com.cyberway.common.media.video.domain.Video;
import com.cyberway.common.message.utils.Limit;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.FileUtil;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.staticer.Configuration;

public class VideoService extends HibernateEntityDao<Video> {
	private SiteCache siteCache;
	public SiteCache getSiteCache() {
		return siteCache;
	}

	public void setSiteCache(SiteCache siteCache) {
		this.siteCache = siteCache;
	}
	/**
	 * 转移专辑
	 * 
	 * @param ids
	 * @param albumId
	 */
	public void changeAlbum(List<Long> ids, Long albumId) {
		if (ids != null) {
			for (Long id : ids) {
				Video video = get(id);
				if (video != null) {
					video.setAlbumId(albumId);
				}
				saveOrUpdate(video);

			}
		}
	}

	/**
	 * 分页查询
	 * 
	 * @param limit
	 * @param criteriaSetup
	 * @return
	 * @throws Exception
	 */
	public Page videoPage(Limit limit, CriteriaSetup criteriaSetup)
			throws Exception {
		Criteria criteria = this.getEntityCriteria();
		if (criteriaSetup == null)
			criteriaSetup = new CriteriaSetup();
		criteriaSetup.setup(criteria);
		return pagedQuery(criteria, limit.getPage(),
				limit.getCurrentRowsDisplayed());
	}

	
	/**
	 * 删除视频
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public boolean deleteVideo(List<Long> ids) throws Exception {
		MediaIntermediateService mediaIntermediateService = (MediaIntermediateService) ServiceLocator
		.getBean("mediaIntermediateService");
		Video video = null;
		List list=null;
		boolean isInclude=false;
		if (ids != null) {
			for (Long id : ids) {
				list=siteCache.getMediaIntermediateByEndWith("video_"+id);
				if(list!=null&&list.size()>0){
					isInclude=true;
					continue;
				}
				video = get(id);
				if (video != null) {
					// 删除本地文件
					FileUtil.delFile(Configuration.servletContext()
							.getRealPath("") +Constants.UPLOADS_MEDIA_PATH+ video.getFilePath());
					// 删除数据的
					remove(video);
				}

			}
		}
		return isInclude;
	}
	
	public List getListByAlbumId(Long albumId,Long siteId){
		return find("from Video where albumId=? and siteId=?", new Object[]{albumId,siteId});
	}
	
	/**
	 * 流媒体文件信息转换成XML
	 * @param list
	 * @return
	 */
	public String toMediaXMLString(List<MediaIntermediate> mediaIntermediates) {
		String rtn = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		rtn += "<RECORDSET>";
		Video video=null;
		if(mediaIntermediates.size()>0){
			for(MediaIntermediate mediaIntermediate:mediaIntermediates){
				video=get(mediaIntermediate.getMediaId());
				if(video!=null){
					rtn += "<DATA>";
					rtn += "<AID>";
					rtn += video.getId();
					rtn += "</AID>";
					
					rtn += "<FORMAT>";
					rtn += video.getFormat();
					rtn += "</FORMAT>";

					rtn += "<FileName>";
					rtn += video.getFilePath();
					rtn += "</FileName>";
					
					rtn += "<FULLPATH>";
					rtn += Constants.MEDIAFILE_PATH+video.getFilePath();
					rtn += "</FULLPATH>";
					rtn += "</DATA>";
				}
			}
		}
		rtn += "</RECORDSET>";
		return rtn;
	}
	
	/**
	 * 流媒体文件信息转换成JSON
	 * @param list
	 * @return
	 */
	public String toMediaJSONString(List<MediaIntermediate> mediaIntermediates) {
		String rtn = "";
		rtn += "{\"RECORDSET\":[";
		Video video=null;
		boolean isFirst = true;
		if(mediaIntermediates.size()>0){
			for(MediaIntermediate mediaIntermediate:mediaIntermediates){
				video=get(mediaIntermediate.getMediaId());
				if(video!=null){
					if(isFirst){
						isFirst = false;
					} else {
						rtn += ",";
					}
					rtn += "{";
					rtn += "\"AID\":\"";
					rtn += video.getId();
					rtn += "\", ";
					
					rtn += "\"FORMAT\":\"";
					rtn += video.getFormat();
					rtn += "\", ";

					rtn += "\"FileName\":\"";
					rtn += video.getFilePath();
					rtn += "\", ";
					
					rtn += "\"FULLPATH\":\"";
					rtn += Constants.MEDIAFILE_PATH+video.getFilePath();
					rtn += "\"";
					rtn += "}";
				}
			}
		}
		rtn += "]}";
		return rtn;
	}
}
