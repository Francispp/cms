package com.cyberway.common.comtemplate.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.cyberway.cms.form.service.CoreFormService;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.common.comtemplate.ComTemplateParser;
import com.cyberway.common.comtemplate.domain.ComTemplate;
import com.cyberway.common.comtemplate.service.ComTemplateManagerService;
import com.cyberway.common.service.DynamicPageService;
import com.cyberway.common.temLibrary.domain.TemLibrary;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.exception.BizException;
import com.cyberway.core.web.BaseBizController;

public class ComTemplateController extends BaseBizController<ComTemplate>{

	
	public final static String ASK_RESULT = "ask";
	
	private String _previewUrl;
	
	private Long comtemplateId;
	
	private Long temLibraryId;
	
	
	private Integer _comtemplateType;
	
	private Long _formcomtemplateId;
	
	private String publictemLibrary;
	
	private Map<Long,String> forms;
	
	private Map<String,String> formFields;
	
	private String tabstatus;//区分是否是模板管理还是权限管理
	
	private String comstatus;//区分是否是模板管理还是权限管理
	
	private boolean isNew=false;//是否新增
//	private Boolean isPublishStatic;
	
	private String isCloseWindw=new String();
	
//	private HtmlSynchroismService htmlSynchroismService = (HtmlSynchroismService) ServiceLocator.getBean("htmlSynchroismService");
	
	private Map<Boolean, String> trueOfFalseMap1 = null;
	
	ComTemplateManagerService service =(ComTemplateManagerService) getServiceById	("comtemplateManagerService");
	
	public ComTemplateController(){//构造器 
		
		setDefaultResult(LIST_RESULT);
	}
	
	public Map<Boolean, String> getTrueOfFalseMap1() {
		if(trueOfFalseMap1 != null){
			return trueOfFalseMap1;
		}else{
			trueOfFalseMap1 = new HashMap<Boolean, String>();
			trueOfFalseMap1.put(new Boolean(true), "是");
			trueOfFalseMap1.put(new Boolean(false), "否");
			return trueOfFalseMap1;
		}
    }
	
	public Map getForms() {
		if(forms==null){
			CoreFormService formService=(CoreFormService)this.getServiceById("coreFormService");
			forms=formService.getAllForm();
		}
		return forms;
	}

	
	public Integer getComTemplateType(){
		
		return  _comtemplateType;
	}
	
	public void setComTemplateType(Integer comtemplateType){
		
		_comtemplateType = comtemplateType;
	}
	
	public Long getFormComTemplateId(){
		
		return _formcomtemplateId;
	}
	
	public void setFormComTemplateId(Long formcomtemplateId){
		
		_formcomtemplateId= formcomtemplateId;
	}
	
	public String getPreviewUrl(){
		
		return _previewUrl;
	}
	
	public void setPreviewUrl(String previewUrl)
	{
		_previewUrl = previewUrl;
	}
	
	
	public SiteManagerService getSiteManagerService()
	{
		return (SiteManagerService) getServiceById("siteManagerService");
	}
	
	public DynamicPageService getDynamicPageService()
	{
		return (DynamicPageService) getServiceById("dynamicPageService");
	}
	
	public ComTemplateParser getComTemplateParser(){
		
		return (ComTemplateParser)getServiceById("comtemplate.parser.default");
	}
	
	
	public @SuppressWarnings("unchecked")
	Map<Integer, String> getComTemplateApplies ()
	{
		return (Map<Integer, String>)getServiceById("commtemplate.applies");
	}
	
	@Override
	public ComTemplateManagerService getService() {
		return (ComTemplateManagerService) getServiceById("comtemplateManagerService");
	}
	
	public Long getComTemplateId() {
		return comtemplateId;
	}

	public void setComTemplateId(Long comtemplateId) {
		this.comtemplateId = comtemplateId;
	}
	
	public String getComstatus() {
		return comstatus;
	}

	public void setComstatus(String comstatus) {
		this.comstatus = comstatus;
	}

	public String getTabstatus() {
		return tabstatus;
	}

	public void setTabstatus(String tabstatus) {
		this.tabstatus = tabstatus;
	}
	
	
	
	

	/*
	 * (non-Javadoc)返回模板管理
	 * @see com.cyberway.core.web.BaseBizController#list()
	 */
	@SuppressWarnings("unchecked")
	public String comTabxml()throws Exception
	{
		getSession().put("tabstatus", tabstatus);
		
		return "comtabxml";
	}	
	
	/*
	 * (non-Javadoc)进入模板页面
	 * @see com.cyberway.core.web.BaseBizController#list()
	 */
	@SuppressWarnings("unchecked")
	public String comTabbar()throws Exception
	{
		
		getSession().put("comstatus", comstatus);
		return "comtabbar";
	}
	
	/*
	 * 进入选择模板
	 */
	@SuppressWarnings("unchecked")
	public String comTabbar_Select()throws Exception{
		
		getSession().put("comstatus", comstatus);
		return "comtabbar_select";
		
	}
	
	/*
	 * 返回选择模板
	 */
	@SuppressWarnings("unchecked")
	public String comTabxml_Select()throws Exception
	{
		getSession().put("tabstatus", tabstatus);
		
		return "comtabxml_select";
	}
	
	
	/**
	 * 预览模板
	 * @return
	 * @throws Exception
	 */
	public synchronized String preview() throws Exception{
		StringBuffer sb=new StringBuffer();
		sb.append(this.getHttpServletRequest().getContextPath());
		if(id!=null)
		 domain=getService().get(id);
		else 
			throw new Exception("未指定模板Id!");
		setPreviewUrl(sb.toString());
		return "preview";
	}
	
	
	
	
	public String list() throws Exception{
		CriteriaSetup criteria = new CriteriaSetup();
		List<Criterion> addCriterions = criteria.getAddCriterions();
		if(addCriterions == null){
			addCriterions = new ArrayList<Criterion>();
		}
		addCriterions.add(Restrictions.eq("type", _comtemplateType));
		addCriterions.add(Restrictions.eq("temLibrary.id", temLibraryId));
		criteria.setAddCriterions(addCriterions);
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		return LIST_RESULT;
	}
	
	
	/*
	 * 选择模板界面内容
	 */
	
	public String Select_list()throws Exception{
		
		CriteriaSetup criteria = new CriteriaSetup();
		List<Criterion> addCriterions = criteria.getAddCriterions();
		if(addCriterions == null){
			addCriterions = new ArrayList<Criterion>();
		}
		addCriterions.add(Restrictions.eq("type", _comtemplateType));
		addCriterions.add(Restrictions.eq("temLibrary.id", temLibraryId));
		criteria.setAddCriterions(addCriterions);
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		return "select_list";
		
	}
	
	
	
	/*
	 * 
	 * (non-Javadoc)删除
	 * @see com.cyberway.core.web.BaseBizController#edit()
	 */
	public String delete() throws Exception {
		
		return super.delete();
	}
	
	
	public String execute() throws Exception
	{
		
		return list();
	}
	
	
	
	

	public String edit() throws Exception {
		String result = super.edit();
		if (getComTemplateType() != null)//指定模板类型
		{
			getDomain().setType(getComTemplateType());
		}

		return result;
	}
	
	
	private Map<Integer, String> getTemplateApplies() {
		// TODO Auto-generated method stub
		return (Map<Integer, String>)getServiceById("template.applies");
	}

	/*
	 * 编辑共用模板
	 */
	public String editscript(){
		if(getId()!=null)
			domain=getService().get(getId());
		return "editscript";
	}
	
	
	
	/*
	 * 保存
	 */
	public String saveOrUpdate() throws Exception {
		
		String result = EDIT_RESULT;
		boolean isNew =  getDomain().getId() == null;
		TemLibrary temLibrary = new TemLibrary();
		temLibrary.setId(temLibraryId);
		getDomain().setTemLibrary(temLibrary);
		getDomain().setType(_comtemplateType);
		if(isNew){
			getDomain().setTimeCreated(new java.util.Date());
		}else{
			 getDomain().setTimeCreated(getService().get(getDomain().getId()).getTimeCreated());
		}
		//增加最后修改时间
				getDomain().setLastModified(new java.util.Date());
				try{
					result = super.saveOrUpdate();
				}catch(BizException e){
					addActionMessage(e.getMessage());
					return result;
				}catch(Exception e){
					e.printStackTrace();
				}
				addActionMessage("保存成功!");
				return result;
	
	}
	
	
	
	
	public String getContent() throws Exception {
		if(id!=null){
			get();
		}else{
			domain=getDomainClass().newInstance();
		}	
		return "getContent";
	}

	public Long getTemLibraryId() {
		return temLibraryId;
	}

	public void setTemLibraryId(Long temLibraryId) {
		this.temLibraryId = temLibraryId;
	}

	
	/*
	 * 获得表单字段
	 */
	
	public Map<String,String> getFormFields() {
		formFields=new HashMap();	
		return formFields;
	}

	public void setFormFields(Map<String, String> formFields) {
		this.formFields = formFields;
	}

	public String getPublictemLibrary() {
		return publictemLibrary;
	}

	public void setPublictemLibrary(String publictemLibrary) {
		this.publictemLibrary = publictemLibrary;
	}
	
	/**
	 * 
	 * 复制公共模板
	 * @return
	 * @throws Exception
	 */
	public String publicItree() throws Exception {
		
		return "public";
	}
	
	
	
	
	/*
	 * 复制模板
	 */
	public String copy()throws Exception {
		TemLibrary temLibrary = new TemLibrary();
		temLibrary.setId(temLibraryId);
		getDomain().setTemLibraryId(temLibraryId);
		System.out.print("123");
		return null;
	}
	
	public String updateComTemplateForm()throws Exception
	{
		if(temLibraryId !=0)
		{
			if(getDomain().getId() == null)
			{
				isNew=true;//新增频道
			}
		domain = service.updateComTemplateForm(domain, temLibraryId);
		}
		return EDIT_RESULT;
	}
	
	
	

}
