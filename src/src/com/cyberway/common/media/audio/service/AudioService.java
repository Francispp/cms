package com.cyberway.common.media.audio.service;

import java.util.List;

import org.hibernate.Criteria;

import com.cyberway.cms.Constants;
import com.cyberway.cms.site.cache.SiteCache;
import com.cyberway.common.media.album.domain.MediaIntermediate;
import com.cyberway.common.media.album.service.MediaIntermediateService;
import com.cyberway.common.media.audio.domain.Audio;
import com.cyberway.common.message.utils.Limit;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.FileUtil;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.staticer.Configuration;

public class AudioService extends HibernateEntityDao<Audio> {
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
				Audio audio = get(id);
				if (audio != null) {
					audio.setAlbumId(albumId);
				}
				saveOrUpdate(audio);

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
	 * 删除音频
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public boolean deleteVideo(List<Long> ids) throws Exception {
		MediaIntermediateService mediaIntermediateService = (MediaIntermediateService) ServiceLocator
		.getBean("mediaIntermediateService");
		Audio audio = null;
		List list=null;
		boolean isInclude=false;
		if (ids != null) {
			for (Long id : ids) {
				list=siteCache.getMediaIntermediateByEndWith("audio_"+id);
				if(list!=null&&list.size()>0){
					isInclude=true;
					continue;
				}
				audio = get(id);
				if (audio != null) {
					// 删除本地文件
					FileUtil.delFile(Configuration.servletContext()
							.getRealPath("") +Constants.UPLOADS_MEDIA_PATH+ audio.getFilePath());

					// 删除数据的
					remove(audio);
				}
			}
		}
		return isInclude;
	}
	
	public List getListByAlbumId(Long albumId,Long siteId){
		return find("from Audio where albumId=? and siteId=?", new Object[]{albumId,siteId});
	}
	
	/**
	 * 流媒体文件信息转换成XML
	 * @param list
	 * @return
	 */
	public String toMediaXMLString(List<MediaIntermediate> mediaIntermediates) {
		String rtn = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		rtn += "<RECORDSET>";
		Audio audio=null;
		if(mediaIntermediates.size()>0){
			for(MediaIntermediate mediaIntermediate:mediaIntermediates){
				audio=get(mediaIntermediate.getMediaId());
				if(audio!=null){
					rtn += "<DATA>";
					rtn += "<AID>";
					rtn += audio.getId();
					rtn += "</AID>";
					
					rtn += "<FORMAT>";
					rtn += audio.getFormat();
					rtn += "</FORMAT>";

					rtn += "<FileName>";
					rtn += audio.getFilePath();
					rtn += "</FileName>";
					
					rtn += "<FULLPATH>";
					rtn += Constants.MEDIAFILE_PATH+audio.getFilePath();
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
		Audio audio=null;
		boolean isFirst = true;
		if(mediaIntermediates.size()>0){
			for(MediaIntermediate mediaIntermediate:mediaIntermediates){
				audio=get(mediaIntermediate.getMediaId());
				if(audio!=null){
					if(isFirst){
						isFirst = false;
					} else {
						rtn += ",";
					}
					rtn += "{";
					rtn += "\"AID\":\"";
					rtn += audio.getId();
					rtn += "\", ";
					
					rtn += "\"FORMAT\":\"";
					rtn += audio.getFormat();
					rtn += "\", ";

					rtn += "\"FileName\":\"";
					rtn += audio.getFilePath();
					rtn += "\", ";
					
					rtn += "\"FULLPATH\":\"";
					rtn += Constants.MEDIAFILE_PATH+audio.getFilePath();
					rtn += "\"";
					rtn += "}";
				}
			}
		}
		rtn += "]}";
		return rtn;
	}
}
