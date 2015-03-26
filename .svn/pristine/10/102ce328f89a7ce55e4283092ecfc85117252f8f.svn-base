package com.cyberway.cms.column.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cyberway.cms.column.service.ColumnManagerService;
import com.cyberway.cms.domain.CmsColumn;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

/**
 * 栏目管理
 * @author caiw
 *
 */
public class ColumnController extends BaseBizController<CmsColumn> {
	//CommonService<CmsColumn> service;
	private SiteManagerService siteService;
	private ColumnManagerService service=(ColumnManagerService)this.getServiceById("columnManagerService");
	private Long siteid;
	private Long pcolumnid;
	private List sites;
	private String _treeXml;
	private String pid;
	 //WebLogic的特殊情况,在页面中不可以使用例如:<ww:select name="domain.issued" list="#{true:'是',false:'否'}" />
  	//改为:<ww:select name="domain.issued" list="trueOfFalseMap" />
  	private Map<Integer, String> trueOfFalseMap1 = null;
  	
	@Override
	protected EntityDao getService() {
		/*if(service==null){
			service=(CommonService<CmsColumn>)this.getServiceById("commonService");
		    service.setEntityObject(CmsColumn.class);
		}*/
		/*if(service==null)
			service=(ColumnManagerService)this.getServiceById("columnManagerService");*/
		return service;
	}
   
	public String execute() throws Exception{
		//显示树型结构
		if(pageStyle==0){
			return "index";
		}
		return list();
	}
	
	public String list()throws Exception{		
		super.list();
		return this.LIST_RESULT;
	}
	/**
	 * 信息采集树
	 * 
	 * @return
	 */
	public String tree() throws Exception {		
		setSites(getSiteService().getHaveTheSites(getLoginer(),1));
		return "tree";
	}
	/* (non-Javadoc)
	 * @see com.cyberway.core.web.BaseBizController#edit()
	 */
	public String edit()throws Exception{
		String rt=super.edit();
		if(id==null){
			if(siteid!=null)
				domain.setSite(this.getSiteService().getSiteFromCache(siteid));

		}else{
			if(domain.getParent()!=null)
			 pcolumnid=domain.getParent().getId();
		}
		if(pcolumnid!=null){
			domain.setParent(service.get(pcolumnid));
			domain.setSite(getSiteService().getSiteFromCache(domain.getParent().getSite().getOid()));
		}
		return rt;
	}
	/* (non-Javadoc)
	 * @see com.cyberway.core.web.BaseBizController#saveOrUpdate()
	 */
	public String saveOrUpdate() throws Exception{
		if(domain.getParent()!=null && domain.getParent().getId()==null)
			domain.setParent(null);
		return super.saveOrUpdate();
	}
	/**
	 * 生成站点xml
	 * @return
	 * @throws Exception
	 */
	public String xml() throws Exception {
		
		_treeXml = service.getSiteTreeXml(id);

		return HTMLXTREE_RESULT;
	}
	/**
	 * 生成栏目xml
	 * @return
	 * @throws Exception
	 */
	public String columnxml() throws Exception {
		if (StringUtil.isNumber(pid))// pid为栏目id
			_treeXml = service.getColumnTreeXml(getLoginer(), id,new Long(pid));
		else
			// pid为站点id,形式如：T_1
			_treeXml = service.getColumnTreeXml(getLoginer(), id, null);

		return HTMLXTREE_RESULT;
	}
	/**
	 * 获得站点siteservice
	 * @return
	 */
	private SiteManagerService getSiteService(){
		if(siteService==null)
		 siteService = (SiteManagerService) this.getServiceById("siteManagerService");
		return siteService;
	}
	public Long getPcolumnid() {
		return pcolumnid;
	}

	public void setPcolumnid(Long pcolumnid) {
		this.pcolumnid = pcolumnid;
	}

	public Long getSiteid() {
		return siteid;
	}

	public void setSiteid(Long siteid) {
		this.siteid = siteid;
	}

	public List getSites() {
		return sites;
	}

	public void setSites(List sites) {
		this.sites = sites;
	}
	public String getTreeXml() {
		return _treeXml;
	}

	public void setTreeXml(String treeXml) {
		_treeXml = treeXml;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Map<Integer, String> getTrueOfFalseMap1() {
		if(trueOfFalseMap1 != null){
			return trueOfFalseMap1;
		}else{
			trueOfFalseMap1 = new HashMap<Integer, String>();
			trueOfFalseMap1.put(new Integer(1), "有效");
			trueOfFalseMap1.put(new Integer(0), "无效");
			return trueOfFalseMap1;
		}
	}
}
