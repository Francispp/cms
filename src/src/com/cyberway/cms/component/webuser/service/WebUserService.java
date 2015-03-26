package com.cyberway.cms.component.webuser.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.acegisecurity.providers.encoding.PasswordEncoder;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cyberway.cms.Constants;
import com.cyberway.cms.component.webuser.domain.WebUser;
import com.cyberway.common.dept.service.DeptManagerService;
import com.cyberway.common.domain.CoreDept;
import com.cyberway.common.domain.CoreRole;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.ServiceLocator;

public class WebUserService extends HibernateEntityDao<WebUser> {
	
	private PasswordEncoder passwordEncoder;
	private JavaMailSenderImpl mailSender;
	private String fromUser;
	private String siteUrl;
	private static Logger log = Logger.getLogger(WebUserService.class);
	private static Map<String, String> jobLevelMap;
	
	@Override
	public WebUser saveOrUpdate(WebUser user) {
		if(user.getOid()==null){
			user.setOid(getSequence());
			user.setCreatetime(new Date());
			if(!GenericValidator.isBlankOrNull(user.getLoginpwd()))
				user.setLoginpwd(encodePassword(user.getLoginpwd(),null));
			user= super.saveOrUpdate(user);
			//添加默认角色
			String default_role = Constants.WEBUSER_DEFAULT_ROLE_ID;
			CoreRole role = (CoreRole)this.get(CoreRole.class, new Long(default_role));
			
			if(role!=null && role.getOid()!=null){//20091101修正若默认角色为空时，出错的原因
				WebRoleService webRoleService = (WebRoleService) ServiceLocator.getBean("WebRoleService");
			    webRoleService.insertUsersToRole(role.getOid().toString(), user.getOid().toString());
			}
			return user;
		}

		return super.saveOrUpdate(user);
	}
	
	/**
	 * 根据ID移除对象.
	 */
	public void removeById(Serializable id) {
		WebRoleService webRoleService = (WebRoleService) ServiceLocator.getBean("WebRoleService");
		List<CoreRole> roles=webRoleService.getRolesByUserId((Long)id);
		if(roles!=null){
			for(CoreRole cr:roles)
				webRoleService.removeUsersFromRole(cr.getOid().toString(), id.toString());
		}
		removeById(getEntityClass(), id);
	}
	/**
	 * web用户列表
	 * for(int i=0;i<paramNames.length;i++){
	 *		criteria.add(Restrictions.like(paramNames[i], "%"+values[i]+"%"));
	 *	}
	 * @param pageNo
	 * @param pageSize
	 * @param paramNames
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public Page list(int pageNo, int pageSize, DetachedCriteria criteria) throws Exception {
		
		String hql = "select count(*) as count from WebUser as wuser";
		Long totalRows = (Long)getHibernateTemplate().findByNamedParam(hql, new String[]{}, new Object[]{}).listIterator(0).next();
		
//		Integer totalRows = getCount(hql);
		
		Page page = new Page(Page.getStartOfPage(pageNo, pageSize), totalRows.intValue(), pageSize, null);
		
		
		List l = getHibernateTemplate().findByCriteria(criteria, Page.getStartOfPage(pageNo, pageSize), pageSize);
		
		page.setData(l);
		return page;
	}
	

	/**
	 * 审核用户
	 * @param keys  List<Long>
	 * @return
	 */
	public boolean auditUsers(final String keys) {
		Integer res = getHibernateTemplate().execute(new HibernateCallback<Integer>(){
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "update WebUser set approved = :approved where oid in ( " + keys + " )";
				Integer res = session.createQuery(hql)
												.setBoolean("approved", true)
												.executeUpdate();
				return res;
			}
		});
		return res > 0;
	}
	
	/**
	 * 激活/禁用 webuser
	 * @param keys
	 * @param islock  true 禁用; false 激活
	 * @return
	 */
	public boolean lockUsers(final String keys, final boolean islock) {
		Integer res = getHibernateTemplate().execute(new HibernateCallback<Integer>(){
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "update WebUser set locked = :locked where oid in ( " + keys + " )";
				Integer res = session.createQuery(hql)
												.setBoolean("locked", islock)
												.executeUpdate();
				return res;
			}
		});
		return res > 0;
	}
	
	
	/**
	 * Excel导入用户
	 * @param excel
	 * @param filename
	 */
	@SuppressWarnings("unchecked")
	public Map importFromExcel(File excel, String filename){
		Map result = new HashMap();
		int successCount = 0;
		String errMsg = "";
		List<Integer> failRows = new ArrayList<Integer>();
		List<String> failReasons = new ArrayList<String>();
		String defaultPwd = "123456Aa";
		DeptManagerService deptService = (DeptManagerService)ServiceLocator.getBean("deptManagerService");
		List<CoreDept> deptList = deptService.findBy("state", 0L);
		Map<String, Long> deptMap = new HashMap<String, Long>();
		for(CoreDept dept:deptList){
			deptMap.put(dept.getDeptname(), dept.getDeptid());
		}
		Workbook workbook = null;
		FileInputStream fis = null;
		Map<String, String> levelMap = getJobLevelMap();
		try{
			fis = new FileInputStream(excel);
			if (filename.endsWith(".xls")) {
				workbook = new HSSFWorkbook(fis);
			} else if (filename.endsWith(".xlsx")) {
				workbook = new XSSFWorkbook(fis);
			}
			Sheet sheet = workbook.getSheetAt(0);
			int rowNum = sheet.getPhysicalNumberOfRows();
			for(int readingRow=1;readingRow<rowNum;readingRow++){
				try {
					Row row = sheet.getRow(readingRow);
					String name = getCellString(row, 0);
					String empcode = getCellString(row, 1);
					String deptname = getCellString(row, 2);
					String email = getCellString(row, 3);
					String mobile = getCellString(row, 4);
					String sex = getCellString(row, 5);
					String level = getCellString(row, 6);
					if ("".equals(name)) {
						failRows.add(readingRow + 1);
						failReasons.add("名称为空");
						continue;
					}
					if ("".equals(empcode)) {
						failRows.add(readingRow + 1);
						failReasons.add("员工号为空");
						continue;
					}
					String levelName = "";
					if(!"".equals(level)){
						levelName = levelMap.get(level);
						if(levelName == null){
							failRows.add(readingRow + 1);
							failReasons.add("无效的职位级别");
							continue;
						}
					}
					WebUser user;
					List<WebUser> users = findBy("loginname", empcode);
					if (users.size() > 0) {
						user = users.get(0);
						if (!user.getIsInternal()) {
							failRows.add(readingRow + 1);
							failReasons.add("存在登录帐号与该员工员工号一样的外部用户");
							continue;
						}
						if (StringUtils.isNotBlank(email) && !email.equals(user.getEmail())) {
							users = findBy("email", email);
							if (users.size() > 0) {
								failRows.add(readingRow + 1);
								failReasons.add("邮箱已被占用");
								continue;
							}
						}
					} else {
						user = new WebUser();
						if(StringUtils.isNotBlank(email)){
							users = findBy("email", email);
							if (users.size() > 0) {
								failRows.add(readingRow + 1);
								failReasons.add("邮箱已被占用");
								continue;
							}
						}
					}
					user.setName(name);
					user.setEmpcode(empcode);
					user.setLoginname(empcode);
					if (StringUtils.isBlank(user.getLoginpwd())) {
						user.setLoginpwd(defaultPwd);
					}
					user.setEmail(email);
					user.setMobilephone(mobile);
					user.setSex(sex);
					user.setJobLevel(level);
					user.setJobLevelName(levelName);
					if(!"".equals(deptname)){
						user.setDeptname(deptname);
						Long deptId = deptMap.get(deptname);
						if (deptId == null) {
							CoreDept coreDept = new CoreDept();
							coreDept.setDeptname(deptname);
							coreDept.setDepttype(deptname);
							coreDept.setRemark(deptname);
							coreDept.setState(0L);
							coreDept = deptService.saveOrUpdate(coreDept);
							deptId = coreDept.getDeptid();
							deptMap.put(deptname, deptId);
						}
						user.setDeptid(deptId);
					}
					user.setIsInternal(true);
					saveOrUpdate(user);
					successCount++;
					if (successCount % 50 == 0) {
						getHibernateTemplate().flush();
						getHibernateTemplate().clear();
					}
				} catch (Exception e) {
					e.printStackTrace();
					failRows.add(readingRow + 1);
					failReasons.add("读取该行数据时出错");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			errMsg = "导入文件时出现错误!";
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		result.put("successCount", successCount);
		result.put("failRows", failRows);
		result.put("failReasons", failReasons);
		result.put("errMsg", errMsg);
		return result;
	}
	
	/**
	 * 获取Excel单元格内容字符串
	 * @param row 单元格所在行对象
	 * @param col 单元格列下标
	 * @return
	 */
	private String getCellString(Row row,int col){
		Cell cell = row.getCell(col);
		if(cell!=null){
			switch(cell.getCellType()){
			case Cell.CELL_TYPE_BLANK:
				return "";
			case Cell.CELL_TYPE_NUMERIC:
				return String.valueOf(new Double(cell.getNumericCellValue()).intValue());
			default:return cell.getStringCellValue();
			}
		}
		return "";
	}
	
	public String encodePassword(String pwd, Object obj){
		return this.passwordEncoder.encodePassword(pwd, obj);
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	/**
	 * 发送uuid到邮箱
	 * @param subject
	 * @param username
	 * @param uuid
	 * @param emailAddresses
	 * @return
	 */
	public boolean sendMail(String subject, String username, String uuid,String... emailAddresses){
		try {
			StringBuffer validUrl = new StringBuffer(siteUrl);
			validUrl.append("/webuser!checkByEmailForPwd.action");
			validUrl.append("?emailvalid=");
			validUrl.append(uuid);
			StringBuffer uuidHtml = new StringBuffer("<html><head></head><body><table><tr><td></td><td style='font-size:15px;'>");
			uuidHtml.append(username + "，你好！</td></tr>");
			uuidHtml.append("<tr><td></td><td style='font-size:15px;'>康佳已收到您提交的密码重置申请，请点击以下链接重置密码：</td></tr>");
			uuidHtml.append("<tr><td></td><td><a href='" );
			uuidHtml.append(validUrl);
			uuidHtml.append("'>"+validUrl+"</a></td></tr>");
			uuidHtml.append("<tr><td></td><td style='font-size:15px;color:#999999'>如无法点击，请将链接拷贝到浏览器地址栏中直接访问。</td></tr>");
			uuidHtml.append("</table></body></html>");
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true,"utf-8");
			message.setSentDate(new Date());
			helper.setFrom(mailSender.getUsername());
			helper.setTo(emailAddresses);
			helper.setText(uuidHtml.toString(),true);
			helper.setSubject(subject);
			mailSender.send(message);
			return true;
		} catch (MailException e) {
			log.error("发送邮件出错->", e);
			return false;
		} catch (MessagingException e) {
			log.error("邮件发送出错->", e);
			return false;
		}
	}
	
	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}
	
	private Map<String, String> getJobLevelMap(){
		if(jobLevelMap == null){
			jobLevelMap = new HashMap<String, String>();
			jobLevelMap.put("技术一级", "首席专家");
			jobLevelMap.put("技术二级", "首席专家");
			jobLevelMap.put("技术三级", "特级专家");
			jobLevelMap.put("技术四级", "特级专家");
			jobLevelMap.put("技术五级", "资深高级专家");
			jobLevelMap.put("技术六级", "高级专家");
			jobLevelMap.put("技术七级", "资深专家");
			jobLevelMap.put("技术八级", "专家");
			jobLevelMap.put("技术九级", "资深高级设计师");
			jobLevelMap.put("技术十级", "高级设计师");
			jobLevelMap.put("技术十一级", "设计师");
			jobLevelMap.put("技术十二级", "助理设计师");
			jobLevelMap.put("技术十三级", "技术员");
			jobLevelMap.put("管理一级", "首席专家");
			jobLevelMap.put("管理二级", "首席专家");
			jobLevelMap.put("管理三级", "特级专家");
			jobLevelMap.put("管理四级", "特级专家");
			jobLevelMap.put("管理五级", "资深高级专家");
			jobLevelMap.put("管理六级", "高级专家");
			jobLevelMap.put("管理七级", "资深专家");
			jobLevelMap.put("管理八级", "专家");
			jobLevelMap.put("专业九级", "资深高级经理");
			jobLevelMap.put("专业十级", "高级经理");
			jobLevelMap.put("专业十一级", "经理");
			jobLevelMap.put("专业十二级", "主管");
			jobLevelMap.put("专业十三级", "助理");
		}
		return jobLevelMap;
	}
}
