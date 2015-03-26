package com.cyberway.cms.component.docShare.view;

import com.cyberway.cms.component.docShare.domain.DocShareRecord;
import com.cyberway.cms.component.docShare.service.DocShareRecordService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.web.BaseBizController;

/**
 * @author caiw
 * 共享文档控制器
 *
 */
public class DocShareRecordController extends BaseBizController<DocShareRecord> {
	private DocShareRecordService docShareRecordService = (DocShareRecordService) this.getServiceById("docShareRecordService");
	@Override
	protected EntityDao getService() {
		// TODO Auto-generated method stub
		return docShareRecordService;
	}

}
