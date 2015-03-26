package com.cyberway.common.media.album.service;

import java.util.ArrayList;
import java.util.List;

import com.cyberway.common.media.album.domain.Album;
import com.cyberway.common.media.video.service.VideoService;
import com.cyberway.common.xtree.DHtmlXTree;
import com.cyberway.common.xtree.DHtmlXTree.Node;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;

public class AlbumService extends HibernateEntityDao<Album> {

	/**
	 * 获得指定专辑tree xml
	 * 
	 * @param id
	 * @return
	 */
	public String getAlbumsTreeXml(Long pid, boolean bfag) {
		DHtmlXTree tree = new DHtmlXTree();
		if (bfag) {
			tree.setTreeId("T_" + pid);
		} else {
			tree.setTreeId(String.valueOf(pid));
		}
		List<Album> albums = find("from Album where pid=?",
				new Object[] { pid });
		if (albums != null && albums.size() > 0) {
			Album album = get(pid);
			Node albumNode = tree.initNode();
			albumNode.setId(album.getId().toString());
			albumNode.setText(album.getTitle());

			
			tree.setIsDynamicLoad(true);

			List subnd = new ArrayList();
			for (Album _album : albums) {
				subnd.add(appendAlbum(tree, _album));
			}
			// 只加载站点
			albumNode.setChild("1");
			tree.setListNode(subnd);
			// tree.addNode(albumNode);
		}
		return tree.getDHtmlXmlTree();
	}

	/**
	 * 得到第一级专辑
	 * 
	 * @param id
	 * @return
	 */
	public String getRootTreeXml(String albumType,Long siteId) {
		List<Album> list=getRootAlbums(albumType,siteId);
		DHtmlXTree tree = new DHtmlXTree();
		tree.setTreeId("album_list");
		//Album _album = get(id);
		for(Album _album:list){
			Node albumNode = tree.initNode();
			albumNode.setId(_album.getId().toString());
			albumNode.setText(_album.getTitle());
			tree.setIsDynamicLoad(true);
	
			// 只加载站点
			albumNode.setChild("1");
			
			tree.addNode(albumNode);
			}
		return tree.getDHtmlXmlTree();
	}


	/**
	 * 加载下级节点
	 * 
	 * @param tree
	 * @param channel
	 * @return
	 */
	private Node appendAlbum(DHtmlXTree tree, Album album) {
		Node node = tree.initNode();
		node.setText(album.getTitle());
		node.setId(album.getId().toString());
		if (tree.getIsDynamicLoad())
			node.setChild("1");
		return node;
	}

	/**
	 * 得到根目录
	 * 
	 * @return
	 */
	public List getRootAlbums(String albumType,Long siteId) {
		return find("from Album where pid is null and mediaType=? and siteId=?",
				new Object[] { albumType ,siteId});
	}

	/**
	 * 得到同类型所有专辑
	 * 
	 * @return
	 */
	public List getAlbums(String albumType) {
		return find("from Album where mediaType=?", new Object[] { albumType });
	}
	/**
	 * 子专辑
	 **/
	public List getChildAlbum(Long pid) {
		return find("from Album where pid=?", new Object[] { pid });
	}
	
}
