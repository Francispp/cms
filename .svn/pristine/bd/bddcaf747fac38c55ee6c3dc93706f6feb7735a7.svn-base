package com.cyberway.cms.component.webuser.view;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.ecside.util.RequestUtil;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.cyberway.cms.Constants;
import com.cyberway.cms.component.webuser.domain.WebUser;
import com.cyberway.cms.component.webuser.service.WebUserService;
import com.cyberway.cms.document.service.DocumentCommonService;
import com.cyberway.common.domain.CoreDept;
import com.cyberway.common.fileDownload.BlobFileObject;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.FileUtil;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.web.BaseBizController;

public class WebUserController extends BaseBizController<WebUser> {
	
	private static final String ADMIN_AJAX = "adminAjax";
	private static final String ADMIN_LIST = "adminList";
	private static final String ADMIN_INPUT = "adminInput";

    private static List<CoreDept> deptList = null;
	
	protected int pageSize;
	protected int pageNo; 
	private String ufileFileName;
	private File ufile;
	private String ufileContentType;
	protected String location;
	private Long deptid;
	private String newPassword;
	private Boolean isInternal;
	private String fromUrl;
	private String templateType;
	
	private static final long serialVersionUID = 6453816704492939722L;
	private WebUserService service = (WebUserService) this.getServiceById("WebUserService");
	private static final Logger Log = Logger.getLogger(WebUserController.class);
	
	protected EntityDao<WebUser> getService() {
		return service;
	}
	
	@SuppressWarnings("unchecked")
	public String uploadPic()throws Exception {
		WebUser user = service.get(domain.getOid());
		String uploadDir = "/cms_file/uploads/attachment/" + UUID.randomUUID().toString()
								+ ufileFileName.substring(ufileFileName.lastIndexOf("."));
		FileUtil.writeTo(ufile, new File(ServletActionContext.getServletContext().getRealPath(uploadDir)));
		user.setHeaderPic(uploadDir);
		service.saveOrUpdate(user);
		getSession().put(WebUser.WEB_USER_IN_SESSION, user);
		outWrite("{uploadDir:'" + uploadDir + "'}");
		return NONE;
	}
	
	@SuppressWarnings("unchecked")
	public String registerSave() throws Exception {
		String validateCode = getHttpServletRequest().getParameter("validateCode");
		if (validateCode != null ){
			if (!validateCode.equals(getSession().get("rand"))) {
				addFieldError("validateCode", "验证码错误！");
				return EDIT_RESULT;
			}
			List<WebUser> users = service.findBy("loginname", domain.getLoginname());
			if (users.size() > 0) 
			{
				addFieldError("domain.loginname", "用户已已被占用,请另起账号名！");
				return EDIT_RESULT;
			} 
			else 
			{
			List<WebUser> users_ = service.findBy("email", domain.getEmail());
			if (users_.size() > 0) 
			{
				addFieldError("domain.loginname", "邮箱已已被占用,请输入别的邮箱地址！");
				return EDIT_RESULT;
			}
			else 
			{
				domain.setName(domain.getLoginname());
				domain.setLoginpwd(domain.getOid() != null && !GenericValidator.isBlankOrNull(domain.getLoginpwd())
							? service.getPasswordEncoder().encodePassword(domain.getLoginpwd(),null)
							: domain.getLoginpwd());
				saveOrUpdate();
				setAttribute("webuserLoginname", domain.getLoginname());
				setAttribute("webuserLoginid", domain.getOid());
				getSession().put(WebUser.WEB_USER_IN_SESSION, domain);
				domain.setLoginpwd(null);
				return registerInfo();
			}
		}
	}
	return EDIT_RESULT;
}
	
	public String registerInfo() throws Exception {
		return "registerInfo";
	}
	
	public String toRegister() throws Exception {
		return EDIT_RESULT;
	}
	
	public String execute() throws Exception {
		return this.list();
	}
	
	public String adminList() throws Exception {
		CriteriaSetup criteria = new CriteriaSetup();
		List<Criterion> addCriterions = new ArrayList<Criterion>();
		if( isInternal==null || !isInternal){
			addCriterions.add(Restrictions.or(Restrictions.eq("isInternal", false), Restrictions.isNull("isInternal")));
		} else {
			addCriterions.add(Restrictions.eq("isInternal", true));
		}
		criteria.setAddCriterions(addCriterions);
		
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);		
		return ADMIN_LIST;
	}
	
	public String adminAdd() throws Exception {
		super.add();
		return ADMIN_INPUT;
	}
	
	public String adminEdit() throws Exception {
		super.edit();
		return ADMIN_INPUT;
	}
	
	public String adminDelete() throws Exception {
		super.delete();
		return adminList();
	}

	@SuppressWarnings("unchecked")
	public List<CoreDept> getDeptList() {
		if(deptList == null){
			deptList = new ArrayList<CoreDept>();
			CoreDept coreDept = new CoreDept();
			coreDept.setDepttype(""); //借用来存储ID字符串
			coreDept.setDeptname("-请选择-");
			deptList.add(coreDept);
			List<Object[]> list = service.find("select deptid,deptname from CoreDept");
			for(Object[] objs:list){
				coreDept = new CoreDept();
				coreDept.setDepttype(objs[0].toString()); //借用来存储ID字符串
				coreDept.setDeptname(objs[1].toString());
				deptList.add(coreDept);
			}
		}
		return deptList;
	}
	
	public String adminSaveOrUpdate() throws Exception {
		StringBuilder jsonString = new StringBuilder();
		jsonString.append("{\"success\": ");
		if(StringUtils.isNotBlank(newPassword)){
			domain.setLoginpwd(service.getPasswordEncoder().encodePassword(newPassword,null));
		}
		if(domain.getOid()==null){
			Boolean notUnique = service.isNotUnique(domain, "loginname");
			if(notUnique){
				jsonString.append("false, \"msg\": \"登录帐号已经存在\"}");
				this.getHttpServletRequest().setAttribute("ajaxMessage", jsonString.toString());
				return ADMIN_AJAX;
			}
			notUnique = service.isNotUnique(domain, "email");
			if(notUnique){
				jsonString.append("false, \"msg\": \"电子邮箱已经存在\"}");
				this.getHttpServletRequest().setAttribute("ajaxMessage", jsonString.toString());
				return ADMIN_AJAX;
			}
			notUnique = service.isNotUnique(domain, "empcode");
			if(notUnique){
				jsonString.append("false, \"msg\": \"员工编号已经存在\"}");
				this.getHttpServletRequest().setAttribute("ajaxMessage", jsonString.toString());
				return ADMIN_AJAX;
			}
		}else{
			WebUser _user = service.get(domain.getOid());
			if(!domain.getEmail().equals(_user.getEmail())){
				Boolean notUnique = service.isNotUnique(domain, "email");
				if(notUnique){
					jsonString.append("false, \"msg\": \"电子邮箱已经存在\"}");
					this.getHttpServletRequest().setAttribute("ajaxMessage", jsonString.toString());
					return ADMIN_AJAX;
				}
			}
		}
		domain=getService().saveOrUpdate(domain);
		jsonString.append("true, \"msg\": \"保存成功\"}");
		this.getHttpServletRequest().setAttribute("ajaxMessage", jsonString.toString());
		return ADMIN_AJAX;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public String list() throws Exception{
		if(this.tableId.equals(RequestUtil.getTableId(getHttpServletRequest()))){
			this.pageNo = RequestUtil.getPageNo(getHttpServletRequest());
			this.pageSize = RequestUtil.getCurrentRowsDisplayed(getHttpServletRequest());
		}
		this.pageNo = this.pageNo < 1 ? 1 : this.pageNo;
		this.pageSize = this.pageSize < 1 ? Page.DEFAULT_PAGE_SIZE : this.pageSize;
		
		DetachedCriteria criteria = DetachedCriteria.forClass(WebUser.class, "webuser");
		
		Page p = service.list(pageNo, pageSize, criteria);
		
		setItems((List) p.getResult());
		RequestUtil.setTotalRows(getHttpServletRequest(), tableId, p.getTotalCount());
		return "list2";
	}
	
	/**
	 * webuser 登录
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String login() throws Exception {
		if(domain != null && domain.getLoginname()!=null){
			WebUser _user = null;
			String loginName = domain.getLoginname();
			List<WebUser> users;
			if(loginName.indexOf("@")!=-1){
				users = service.findBy("email", domain.getLoginname());
			}else{
				users = service.findBy("loginname", domain.getLoginname());
			}
			if(users.size()>0){
				_user = users.get(0);
			}
			if(_user != null){
				if(!_user.getLoginpwd().equals(service.encodePassword(domain.getLoginpwd(),null))){
					addFieldError("domain.loginpwd", "密码错误！");
				}
				else
				{
					Integer expiry = null;
					try{
						if(getRequest().getParameter("storePwd")!=null){
							expiry = 60*60*24*7;
						}
					}catch (Exception e) {
					}
					addCookie("webuserLoginname", _user.getLoginname(), expiry);
					addCookie("outerLoginname", loginName, 60*60*24*7);//账号存放两周
					addCookie("webuserLoginid", _user.getOid().toString(), expiry);
					addCookie("outerLoginpwd", _user.getLoginpwd(), expiry);
					addCookie("isInternal", _user.getIsInternal(), expiry);//是否内部员工
					getSession().put(WebUser.WEB_USER_IN_SESSION,_user);
					/**
					 * 定位登录后进入的页面 2013-12-28 17:00:25
					 */
					if(StringUtils.isBlank(this.location) && StringUtils.isNotBlank(this.fromUrl)){
						this.location = this.fromUrl;
					}else if(StringUtils.isBlank(this.location)){
						this.location = "/cms/index.action?siteId=1&templateId=6";
					}
					return "location";
				}
			}else{
				if(domain.getLoginpwd() != null)
					addFieldError("domain.loginname", "用户不存在！");
				domain.setLoginpwd(null);
			}
		}
		return "login";
	}
	
	public void setAttribute(String name,Object obj){
		getHttpServletRequest().setAttribute(name, obj);
	}
	
	/**
	 * webuser 重置密码与修改密码
	 * @return
	 * @throws Exception
	 */
	public String resetpwd() throws Exception { 
		String emailResetpwdVal = String.valueOf(getParameterValue("emailResetpwd"));
		Boolean emailResetpwd = Boolean.valueOf("null".equals(emailResetpwdVal) ? "false" : emailResetpwdVal);
		String newLoginpwd = getHttpServletRequest().getParameter("newLoginpwd");
		if(newLoginpwd != null){
			if(!ServletActionContext.getRequest().getParameter("validateCode").equals(getSession().get("rand")))
			{
				addFieldError("validateCode", "验证码输入错误！");
				return emailResetpwd ? "resetPwd" : "setPassword";
			}
			WebUser _user = service.get(domain.getOid());
			if(_user != null){
				if(emailResetpwd || _user.getLoginpwd().equals(service.encodePassword(domain.getLoginpwd(),null)))
				{
					_user.setLoginpwd(service.getPasswordEncoder().encodePassword(newLoginpwd,null));
					service.saveOrUpdate(_user);
					domain = null;
					addCookie("outerLoginpwd", _user.getLoginpwd(), 60*60*24, true);
					setAttribute("success","密码修改成功！");
					return emailResetpwd ? "resetPwd" : "setPassword";
				}
				else
				{
					addFieldError("domain.loginpwd","旧密码错误！");
				}
				return "setPassword";
			}
		}
		return emailResetpwd ? "resetPwd" : "setPassword";
	}
	
	public String setpwd() throws Exception { 
		if(getWebUser() == null){
			return "login";
		}
		return "setPassword";
	}
	
	public String toSetInfo() throws Exception { 
		get();
		return "setInfo";
	}
	
	private DocumentCommonService getDocumentCommonService(){
		return (DocumentCommonService)ServiceLocator.getBean("documentCommonService");
	}
	
	public String initInfo() throws Exception {
		WebUser _user = getWebUser();
		if(_user == null){
			return "login";
		}
		domain.setEmail(_user.getEmail());
		domain.setMobilephone(_user.getMobilephone());
		return "initInfo";
	}
	
	/**
	 * webuser 保存初始个人信息
	 * @return
	 * @throws Exception
	 */
	public String saveInitInfo() throws Exception {
		WebUser _user = getWebUser();
		if(_user == null){
			return "login";
		}
		String newLoginpwd = getHttpServletRequest().getParameter("newLoginpwd");
		if(!ServletActionContext.getRequest().getParameter("validateCode").equals(getSession().get("rand")))
		{
			addFieldError("validateCode", "验证码输入错误！");
		} else if(StringUtils.isBlank(newLoginpwd)) {
			addFieldError("newLoginpwd","新密码不能为空！");
		} else if(_user.getLoginpwd().equals(service.encodePassword(domain.getLoginpwd(),null))) {
			String newPasswordEncode = service.getPasswordEncoder().encodePassword(newLoginpwd,null);
			if(StringUtils.isBlank(domain.getMobilephone())){
				addFieldError("domain.mobilephone","手机号码不能为空！");
			} else if(StringUtils.isBlank(domain.getEmail())){
				addFieldError("domain.email","邮箱不能为空！");
			} else if(newPasswordEncode.equals(_user.getLoginpwd())){
				addFieldError("newLoginpwd","新密码不能与旧密码相同！");
			} else {
				if(!domain.getEmail().equals(_user.getEmail())){
					List<WebUser> users_ = service.findBy("email", domain.getEmail());
					if(users_.size() > 0){
						addFieldError("domain.email", "邮箱已被占用！");
						return "initInfo";
					}
				}
				_user.setLoginpwd(newPasswordEncode);
				_user.setEmail(domain.getEmail());
				_user.setMobilephone(domain.getMobilephone());
				service.saveOrUpdate(_user);
				domain = null;
				addCookie("outerLoginpwd", _user.getLoginpwd(), 60*60*24, true);
				setAttribute("success","信息保存成功！");
				return "information";
			}
		} else {
			addFieldError("domain.loginpwd","旧密码错误！");
		}
		return "initInfo";
	}
	
	@SuppressWarnings("unchecked")
	public String saveInfo() throws Exception {
		WebUser _user = service.get(domain.getOid());
		if(!domain.getEmail().equals(_user.getEmail())){
			Boolean notUnique = service.isNotUnique(domain, "email");
			if(notUnique){
				addActionError("邮箱已被占用！");
				return "setInfo";
			}
		}
		saveOrUpdate();
		getSession().put(WebUser.WEB_USER_IN_SESSION,domain);
		setAttribute("success","保存成功！");
		return "setInfo";
	}
	
	public String personal() throws Exception {
		if(getWebUser() == null){
			this.location = "/webuser!login.action";
			return "location";
		}
		return "information";
	}
	
	/**
	 * 退出登录
	 * @return
	 * @throws Exception
	 */
	public String logout()throws Exception{
		if(this.getSession().containsKey(WebUser.WEB_USER_IN_SESSION))
			this.getSession().remove(WebUser.WEB_USER_IN_SESSION);
		clearAllCookies();
		domain = null;
		this.location = this.fromUrl;
		if(this.location.indexOf("/webuser!")!=-1){
			this.location = "/cms/index.action?siteId=1&templateId=6";
		}
		return "location";
	}
	
	public String checkByEmailForPwd() throws Exception {
		WebUser wu = service.findUniqueBy("emailPwdUUId", String.valueOf(getParameterValue("emailvalid")));
		if(wu != null){
			Date begin = wu.getEmailSentDate();
			if(begin != null && new Date().getTime() - begin.getTime() <= 30*60*1000){
				setAttribute("emailResetpwd", true);
				setAttribute("oid", wu.getOid());
				service.save(wu);
				return "resetPwd";
			}
			else{
				return "inputEmail";
			}
		}
		return "inputEmail";
	}
	
	/**
	 * 通过邮箱找回密码
	 * @return
	 * @throws Exception
	 */
	public String findPwdByEmail() throws Exception {
		String validateCode = ServletActionContext.getRequest().getParameter("validateCode");
		String email = domain.getEmail();
		WebUser wu = null;
		if(email == null){
			return "inputEmail";
		}else if(validateCode==null){
			return "inputEmail";
		}
		if(StringUtils.isBlank(validateCode)){
			addFieldError("validateCode", "请输入验证码！");
			return "inputEmail";
		}else if(!validateCode.equals(getSession().get("rand"))){
			addFieldError("validateCode", "验证码输入错误！");
			return "inputEmail";
		}
		if(StringUtils.isBlank(email)){
			addFieldError("domain.email", "请输入邮箱！");
		}else{
			//邮箱验证
			wu = service.findUniqueBy("email", email);
			if (wu == null) {
				addFieldError("domain.email", "用户邮箱不存在");
			}else{
				String uuid = UUID.randomUUID().toString();
				if (service.sendMail("找回密码 - 康佳官网", wu.getName(), uuid, email)) {
					wu.setEmailPwdUUId(uuid);
					wu.setEmailSentDate(new Date());
					service.save(wu);
					setAttribute(SUCCESS, "邮件已经发送成功，请注意查收");
				}
			}
		}
		return "inputEmail";
	}
	
	public String adminImportInput() throws Exception {
		return "import";
	}
	
	/**
	 * 导入员工
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String adminImport() throws Exception {
		HttpServletRequest request = getHttpServletRequest();
		if (ufile != null) {
			String fileName = ufileFileName.toLowerCase();
			if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
				Map result = service.importFromExcel(ufile, fileName);
				request.setAttribute("errMsg", result.get("errMsg"));
				request.setAttribute("successCount", result.get("successCount"));
				request.setAttribute("failRows", result.get("failRows"));
				request.setAttribute("failReasons", result.get("failReasons"));
			} else {
				request.setAttribute("errMsg", "不支持的文件格式!");
				request.setAttribute("successCount", 0);
				request.setAttribute("failRows", new ArrayList<Integer>());
				request.setAttribute("failReasons", new ArrayList<String>());
			}
		} else {
			request.setAttribute("errMsg", "请上传要导入的Excel文件!");
			request.setAttribute("successCount", 0);
			request.setAttribute("failRows", new ArrayList<Integer>());
			request.setAttribute("failReasons", new ArrayList<String>());
		}
		return "importResult";
	}

	/**
	 * 员工导入模板下载
	 * @return
	 * @throws Exception
	 */
	public String templateDownload() throws Exception {
		File file;
		File folder;
		FileInputStream is=null;
		String filename;
		try{
			if(Constants.IS_REALPATH){
				folder = new File(Constants.IMPORT_TEMPLATE_FOLDER);
			}else{
				folder = new File(this.getRequest().getSession().getServletContext().getRealPath(Constants.IMPORT_TEMPLATE_FOLDER));
			}
			if("xls".equals(templateType)){
				file = new File(folder, "webuser.xls");
				filename = "员工导入模板.xls";
			}else{
				file = new File(folder, "webuser.xlsx");
				filename = "员工导入模板.xlsx";
			}
			is = new FileInputStream(file);
			byte[] b = new byte[(int) file.length()];
			is.read(b, 0, (int) file.length());
			BlobFileObject bfo = new BlobFileObject();
			bfo.setContent(b);
			bfo.setFullfilename(filename);
			this.getHttpServletRequest().setAttribute(Constants.DOWNLOAD_BLOB_FILE_OBJECT, bfo);
			return "file_download";
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(is!=null){
				is.close();
			}
		}
		addActionError("下载出错！");
		return "download_error";
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getUfileFileName() {
		return ufileFileName;
	}

	public void setUfileFileName(String ufileFileName) {
		this.ufileFileName = ufileFileName;
	}

	public File getUfile() {
		return ufile;
	}

	public void setUfile(File ufile) {
		this.ufile = ufile;
	}

	public String getUfileContentType() {
		return ufileContentType;
	}

	public void setUfileContentType(String ufileContentType) {
		this.ufileContentType = ufileContentType;
	}

	public Long getDeptid() {
		return deptid;
	}

	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public Boolean getIsInternal() {
		return isInternal;
	}

	public void setIsInternal(Boolean isInternal) {
		this.isInternal = isInternal;
	}
	
	public static void resetDeptList(){
		deptList = null;
	}

	public String getFromUrl() {
		return fromUrl;
	}

	public void setFromUrl(String fromUrl) {
		this.fromUrl = fromUrl;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	
	public boolean getShowResumeLi(){
		try {
			WebUser _user = getWebUser();
			if(_user!=null&&(_user.getIsInternal()==null || !_user.getIsInternal())){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
