package com.cyberway.common.media.album.view;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ognl.Ognl;

import com.cyberway.common.media.album.domain.Album;
import com.cyberway.common.media.album.service.AlbumService;
import com.cyberway.common.media.audio.service.AudioService;
import com.cyberway.common.media.video.service.VideoService;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

public class AlbumController extends BaseBizController<Album> {
	AlbumService service = (AlbumService) this.getServiceById("albumService");

	private List albums;
	private String _treeXml;
	private String albumType;// 专辑类型
	private String pid;// 父节点
	private String editType;// 是否为编辑
	private Long siteId;//站点ID
	private String keys;//视频、音频的ID


	//WebLogic的特殊情况,在页面中不可以使用例如:<ww:select name="domain.issued" list="#{true:'是',false:'否'}" />
  	//改为:<ww:select name="domain.issued" list="trueOfFalseMap" />
	private Map<Integer,String> trueOfFalseMap1=null;
	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}


	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public String getAlbumType() {
		return albumType;
	}

	public void setAlbumType(String albumType) {
		this.albumType = albumType;
	}

	public String getTreeXml() {
		return _treeXml;
	}

	public void setTreeXml(String _treeXml) {
		this._treeXml = _treeXml;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public List getAlbums() {
		return albums;
	}

	public void setAlbums(List albums) {
		this.albums = albums;
	}

	@Override
	protected AlbumService getService() {
		return service;
	}
	public String saveOrUpdate() throws Exception{
		Loginer _loginer = getLoginer();
		if (_loginer != null) {
			Ognl.setValue("modifUserId", domain, _loginer.getUserid());
			Ognl.setValue("modifUserName", domain, _loginer.getUsername());
			Ognl.setValue("siteId", domain, _loginer.getSiteId());
		}
		addActionMessage("保存成功!");
		return super.saveOrUpdate();
	}
	/**
	 * 编辑
	 */
	public String edit() throws Exception {
		if (!StringUtil.isEmpty(pid)) {
			if (!StringUtil.isEmpty(albumType)) {
				id = Long.valueOf(pid);
				return super.edit();
			}
			Ognl.setValue("pid", domain, pid);
		}
		if (!StringUtil.isEmpty(albumType)) {
			Ognl.setValue("mediaType", domain, albumType);
		}
		Ognl.setValue("createTime", domain, new Date());
		return INPUT;
	}
	/**
	 * 删除专辑
	 */
	public String delete() throws Exception {
		if (!StringUtil.isEmpty(keys)) {
			List list=null;
			if(!StringUtil.isEmpty(albumType)&&StringUtil.ifEqual(albumType, "video")){
				VideoService service = (VideoService) this.getServiceById("videoService");
				list=service.getListByAlbumId(Long.valueOf(keys), getLoginerSiteId());
			}else{
				AudioService service = (AudioService) this.getServiceById("audioService");
				list=service.getListByAlbumId(Long.valueOf(keys), getLoginerSiteId());
			}
			
			if (list != null && list.size() > 0) {
				this.addActionError("err");
				return tree();
			}
			service.removeById(Long.valueOf(keys));
		}
		return tree();
	}

	public String execute() throws Exception {
		return "frame";
	}

	/**
	 * 树型结构
	 * 
	 * @return
	 */
	public String tree() throws Exception {
		return TREE_RESULT;
	}

	/**
	 * 获取树根节点xml
	 * 
	 * @return
	 * @throws Exception
	 */
	public String xml() throws Exception {
		_treeXml=service.getRootTreeXml(albumType,Long.valueOf(getLoginerSiteId()));
		return HTMLXTREE_RESULT;
	}
	/**
	 * 获取子节点
	 * 
	 * @return
	 */
	public String itemAlbum() {
		if (StringUtil.isNumber(pid))
			_treeXml = service.getAlbumsTreeXml(Long.parseLong(pid), false);
		else if (id != null)
			_treeXml = service.getAlbumsTreeXml(id, true);
		return HTMLXTREE_RESULT;
	}
	
	/**
	 * 转移专辑树形菜单
	 * 
	 * @return
	 */
	public String changeAlbumtree() throws Exception {
		return "changeAlbumTree";
	}
	
	/**
	 * 复制树形菜单
	 * 
	 * @return
	 */
	public String copytree() throws Exception {
		setAlbums(service.getRootAlbums(albumType,siteId));
		// 添加默认专辑
		Album _album = new Album();
		Ognl.setValue("isdefault", _album, 1);
		this.albums.add(0, _album);
		return "copyTree";
	}
	
	public Map<Integer, String> getTrueOfFalseMap1() {
		if(trueOfFalseMap1 != null){
			return trueOfFalseMap1;
		}else{
			trueOfFalseMap1 = new HashMap<Integer, String>();
			trueOfFalseMap1.put(new Integer(1), "是");
			trueOfFalseMap1.put(new Integer(0), "否");
			return trueOfFalseMap1;
		}
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	
	public void getAll(){
		this.albums=service.getRootAlbums(albumType,Long.valueOf(getLoginerSiteId()));
		
	}
}
