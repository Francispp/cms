package com.cyberway.cms.component.docShare.service;


import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.component.docShare.domain.DocShareRelation;
import com.cyberway.core.dao.HibernateEntityDao;


/**
 * 文档共享设置管理
 * @author hdap
 */
public class DocShareRelationService extends HibernateEntityDao<DocShareRelation>{
	ChannelManagerService channelManagerService;
	 
	/**
	 *  检测设置对象是否可实现信息共享
	 * @param docShareRelation
	 * @return
	 */
	public boolean checkdocShareRelationService(DocShareRelation docShareRelation){
		if(docShareRelation==null||docShareRelation.getBaseChannelId()==null ||docShareRelation.getTargetChannelId()==null)
			return false;
		return channelManagerService.checkCanMoveDocBychannel(docShareRelation.getBaseChannelId(), docShareRelation.getTargetChannelId()).booleanValue();

	}

	public ChannelManagerService getChannelManagerService() {
		return channelManagerService;
	}

	public void setChannelManagerService(ChannelManagerService channelManagerService) {
		this.channelManagerService = channelManagerService;
	}
}
