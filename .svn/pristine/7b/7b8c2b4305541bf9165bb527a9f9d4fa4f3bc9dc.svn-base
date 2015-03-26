package com.cyberway.common.xtree.view;

import java.util.Map;

import com.cyberway.common.xtree.DefineTree;
import com.cyberway.common.xtree.service.XTreeService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.web.BaseBizController;
import com.opensymphony.xwork2.ActionContext;

/**
 * 树对象控制器
 * @author caiw
 *
 */
public class XTreeAction extends BaseBizController{

	private String treeNode;

	private XTreeService service=(XTreeService)getServiceById("xtreeService"); 
	
	@Override
	protected EntityDao getService() {
		return service;
	}	
	
	public String loadXTree() throws Exception { 
		ActionContext ctx = ActionContext.getContext();
		Map param = ctx.getSession();
		DefineTree dtree = (DefineTree) param.get(XTreeService.DEFINE_XTREE_VARIABLE);
		//xTreeManager.setDefineTree(dtree);
		logger.debug("XTreeAction____"+dtree.getParentNodeId());
		setTreeNode(service.getXTreeByParentNode(dtree));		
		
		return SUCCESS;
	}
	
	public String getTreeNode() {
		return treeNode;
	}
	public void setTreeNode(String labelNode) {
		this.treeNode = labelNode;
	}

}
