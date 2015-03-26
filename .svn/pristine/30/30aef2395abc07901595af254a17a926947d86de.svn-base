package com.cyberway.common.menu.view;

import java.util.HashMap;
import java.util.Map;

import com.cyberway.common.menu.service.MenuManagerService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.web.BaseBizController;
import com.cyberway.common.domain.CoreDept;
import com.cyberway.common.domain.CoreMenu;
import com.cyberway.common.domain.CoreOrg;

public class MenuController extends BaseBizController<CoreMenu> {
	MenuManagerService service=(MenuManagerService)this.getServiceById("menuManagerService");
    
	private Long portalid;
	private Long pmenuid;
	private String _treeXml;
	//WebLogic的特殊情况,在页面中不可以使用例如:<ww:select name="domain.issued" list="#{true:'是',false:'否'}" />
  	//改为:<ww:select name="domain.issued" list="trueOfFalseMap" />
	private Map<Integer,String> trueOfFalseMap1=null;
	@Override
	protected EntityDao getService() {
		// TODO Auto-generated method stub
		return service;
	}
	
	/* 列表
	 * @see com.cyberway.core.web.BaseController#execute()
	 */
	public String execute()throws Exception{
		//显示树型结构
		if(pageStyle==0){
			return "frame";
		}		
		CriteriaSetup criteria = new CriteriaSetup();
		if(!getLoginer().checkIsAdministratorUser())//若不为超级用户,则只列出当前登录门户的资源
			criteria.addFilter("portalid", new Long(getLoginer().getPortal().getPortalid()));

		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		//getHttpServletRequest().setAttribute("portals", getPortals());
		
		return LIST_RESULT;
	}
	/**
	 * 树型结构
	 * 
	 * @return
	 */
	public String tree() throws Exception{
		if(getLoginer().checkIsAdministratorUser())//若不为超级用户,则只列出当前登录门户的
		   items = service.getAll();
		else
		   items=service.find("from CoreMenu where portalid=?", new Object[]{new Long(getLoginer().getPortal().getPortalid())});
		getHttpServletRequest().setAttribute("items", items);
		return TREE_RESULT;
	}	
	//获得生成树的xml字符串
	public String xml(){
		if(portalid!=null){
			this.setTreeXml(service.getMenuXmls(portalid));
		}		
		
		return HTMLXTREE_RESULT;
	}
	/* 编辑
	 * @see com.cyberway.core.web.BaseBizController#edit()
	 */
	public String edit()throws Exception{

		if(id!=null){
			get();
	    }else{
			domain=new CoreMenu();
			domain.setPmid(pmenuid);
			//设置站点名称和id
			if(portalid==null){
				portalid=new Long(getLoginer().getPortal().getPortalid());			
			}
			domain.setPortalid(portalid);
	    }
		if(domain.getPmid()!=null){
			CoreMenu pcm=service.get(domain.getPmid());
			if(pcm!=null)
				domain.setPmname(pcm.getMenuname());
		}
			
		return EDIT_RESULT;
	}
	public Long getPortalid() {
		return portalid;
	}

	public void setPortalid(Long portalid) {
		this.portalid = portalid;
	}
	
	public String getTreeXml()
	{
		return _treeXml;
	}
	
	public void setTreeXml(String treeXml)
	{
		_treeXml = treeXml;
	}
	

	public Long getPmenuid() {
		return pmenuid;
	}

	public void setPmenuid(Long pmenuid) {
		this.pmenuid = pmenuid;
	}
	public Map<Integer, String> getTrueOfFalseMap1() {
		if(trueOfFalseMap1 != null){
			return trueOfFalseMap1;
		}else{
			trueOfFalseMap1 = new HashMap<Integer, String>();
			trueOfFalseMap1.put(new Integer(1), "无效");
			trueOfFalseMap1.put(new Integer(0), "正常");
			return trueOfFalseMap1;
		}
	}
}
