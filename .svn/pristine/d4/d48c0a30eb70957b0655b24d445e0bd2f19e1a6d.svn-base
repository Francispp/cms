package com.cyberway.cms.staticResource.view;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts2.ServletActionContext;
import org.ecside.table.limit.Limit;
import org.ecside.util.RequestUtil;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.cyberway.cms.Constants;
import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.StaticResource;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.cms.staticResource.service.StaticResourceService;
import com.cyberway.common.distribution.service.DistributionService;
import com.cyberway.common.fileDownload.BlobFileObject;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.FileUtil;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

public class StaticResourceController extends BaseBizController<StaticResource> {
	StaticResourceService	     service	         = (StaticResourceService) this.getServiceById("staticResourceService");

	SiteManagerService	         service1	         = (SiteManagerService) this.getServiceById("siteManagerService");

	DistributionService	         distributionService	= (DistributionService) this.getServiceById("distributionService");

	private static final int	 base	             = 1000;	                                                             // 这个基数必须是10的N次方

	private static long	         millis	             = 0, old = 0;
	static final int	         BUFFER	             = 2048;

	private File[]	             _file;

	private String[]	         _fileContentType;

	private String[]	         _fileFileName;

	private String	             xpdlName;

	private List	             fileAttributes;

	private String	             Upload;

	private String	             Filename;

	private String	             searchStr;

	/**
	 * 频道id
	 */
	private Long	             chnid;

	private String	             type;

	private List<StaticResource>	staticList;

	// WebLogic的特殊情况,在页面中不可以使用例如:<ww:select name="domain.issued"
	// list="#{true:'是',false:'否'}" />
	// 改为:<ww:select name="domain.issued" list="trueOfFalseMap" />
	private Map<String, String>	 trueOfFalseMap1	 = null;

	public Map<String, String> getTrueOfFalseMap1() {
		if (trueOfFalseMap1 != null) {
			return trueOfFalseMap1;
		} else {
			trueOfFalseMap1 = new HashMap<String, String>();
			trueOfFalseMap1.put("image", "image");
			trueOfFalseMap1.put("flash", "flash");
			trueOfFalseMap1.put("css", "css");
			trueOfFalseMap1.put("Java Script", "Java Script");
			trueOfFalseMap1.put("document", "document");
			return trueOfFalseMap1;
		}
	}

	public String getFilename() {
		return Filename;
	}

	public void setFilename(String filename) {
		Filename = filename;
	}

	public String getUpload() {
		return Upload;
	}

	public void setUpload(String upload) {
		Upload = upload;
	}

	public String getSearchStr() {
		return searchStr;
	}

	public void setSearchStr(String searchStr) {
		this.searchStr = searchStr;
	}

	public String execute() throws Exception {
		if (getLoginerSiteId() > 0) {
			CriteriaSetup criteria = new CriteriaSetup();
			List<Criterion> cList = new ArrayList<Criterion>();
			cList.add(Restrictions.eq("siteid", getLoginerSiteId()));
			cList.add(Restrictions.eq("channelid", 0L));
			criteria.setAddCriterions(cList);
			if (!StringUtil.isEmpty(searchStr)) {
				criteria.setInCriterion(Restrictions.like("name", "%" + searchStr + "%"));
			}

			doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		}
		return LIST_RESULT;
	}

	protected void doListEntity(CriteriaSetup criteriaSetup, String tableId, int pageSize) throws Exception {
		int totalRows = RequestUtil.getTotalRowsFromRequest(getHttpServletRequest(), tableId);
		if (totalRows < 0) {
			totalRows = getService().getCount(criteriaSetup);
		}
		Limit limit = RequestUtil.getLimit(getHttpServletRequest(), tableId, totalRows, 28);
		Page page = getService().findECPage(limit, criteriaSetup);
		this.setItems((List) page.getResult());
		RequestUtil.setTotalRows(getHttpServletRequest(), page.getTotalCount());
		setData(page);
	}

	public void findBySiteAndType() {
		if (GenericValidator.isBlankOrNull(type) || getLoginerSiteId() == 0)
			addActionError("引用名不能为空!");

		staticList = (List<StaticResource>) service.findByType(getLoginerSiteId(), type);
	}

	public String exportZip() throws Exception {
		if (getLoginerSiteId() != 0) {

			File f = new File(getHttpServletRequest().getRealPath(Constants.DYNAMICPAGE_PATH + getLoginerSiteId() + "/"));
			if (!f.exists()) {
				f.mkdirs();
			}
			List statics = service.findBy("siteid", getLoginerSiteId());
			Iterator its = statics.iterator();
			while (its.hasNext()) {
				StaticResource staticResource = (StaticResource) its.next();
				try {
					File oldFile = new File(getHttpServletRequest().getRealPath(staticResource.getServerpath()));
					File newFile = new File(getHttpServletRequest().getRealPath(
					        Constants.DYNAMICPAGE_PATH + getLoginerSiteId() + "/" + staticResource.getName()));
					FileUtil.writeTo(oldFile, newFile);
				} catch (Exception e) {
					continue;
				}
			}
			File files[] = f.listFiles();
			if (files.length > 0) {
				BufferedInputStream origin = null;
				File file = new File(getHttpServletRequest().getRealPath(Constants.ZIP_FILE));
				FileOutputStream dest = new FileOutputStream(file);
				ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

				byte data[] = new byte[BUFFER];
				for (int i = 0; i < files.length; i++) {
					FileInputStream fi = new FileInputStream(files[i]);
					origin = new BufferedInputStream(fi, BUFFER);
					ZipEntry entry = new ZipEntry(files[i].getName());
					out.putNextEntry(entry);
					int count;
					while ((count = origin.read(data, 0, BUFFER)) != -1) {
						out.write(data, 0, count);
					}
					origin.close();
				}
				out.close();

				// 删除临时文件
				FileUtil.delFolder(getHttpServletRequest().getRealPath(Constants.DYNAMICPAGE_PATH + getLoginerSiteId() + "/"));
				FileInputStream is = new FileInputStream(file);
				byte[] b = new byte[(int) file.length()];
				is.read(b, 0, (int) file.length());
				BlobFileObject bfo = new BlobFileObject();
				bfo.setContent(b);
				String fileName = getLoginerSiteId() + ".zip";
				is.close();
				if (StringUtils.isEmpty(fileName))
					fileName = "unknow_file";
				bfo.setFullfilename(fileName);
				this.getHttpServletRequest().setAttribute(Constants.DOWNLOAD_BLOB_FILE_OBJECT, bfo);

				return "file_download";
			} else {
				FileUtil.delFolder(getHttpServletRequest().getRealPath(Constants.DYNAMICPAGE_PATH + getLoginerSiteId() + "/"));
				throw new Exception("资源库为空！");
			}
		} else {
			throw new Exception("找不到指定资源！");
		}
	}

	public String importZip() throws Exception {
		String uploadDir = getHttpServletRequest().getRealPath(Constants.ZIP_FILE);
		boolean succ = false;
		for (int i = 0; i < _file.length; i++) {
			succ = saveZipFile(_file[i], new File(uploadDir));
		}
		if (succ) {
			try {
				org.apache.tools.zip.ZipFile zipFile = new org.apache.tools.zip.ZipFile(uploadDir, "GBK");
				Enumeration emu = zipFile.getEntries();
				while (emu.hasMoreElements()) {
					domain = new StaticResource();
					org.apache.tools.zip.ZipEntry entry = (org.apache.tools.zip.ZipEntry) emu.nextElement();
					// 会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
					if (entry.isDirectory()) {
						new File(getHttpServletRequest().getRealPath(Constants.DYNAMICPAGE_PATH + getLoginerSiteId()) + "/"
						        + entry.getName()).mkdirs();
						continue;
					}
					
					//过滤掉包含中文字符文件名的文件
					if(Pattern.compile("[\u4e00-\u9fa5]+").matcher(entry.getName()).find()){
						continue;
					}
					
					if (!StringUtil.isEmpty(entry.getName())) {
						if (entry.getName().toLowerCase().endsWith(".exe") || entry.getName().toLowerCase().endsWith(".bat")) {
							continue;
						}
					}
					
					BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
					
					String newfileName = getSequence() + entry.getName();
					File outputFile = new File(getRealPath(getLoginerSiteId()) + File.separatorChar + newfileName);
					String being = service.getStaticResourcePath(getLoginerSiteId(), entry.getName());
					// 资源名称已经存在
					if (being != null && !StringUtil.isEmpty(being)) {
						continue;
					}
					// 加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
					// 而这个文件所在的目录还没有出现过，所以要建出目录来。
					File parent = outputFile.getParentFile();
					if (parent != null && (!parent.exists())) {
						parent.mkdirs();
					}
					FileOutputStream fos = new FileOutputStream(outputFile);
					BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER);
					int count;
					byte data[] = new byte[BUFFER];
					while ((count = bis.read(data, 0, BUFFER)) != -1) {
						bos.write(data, 0, count);
					}
					domain.setName(entry.getName());
					int index = entry.getName().indexOf(".");
					if (index != -1) {
						String s = entry.getName().substring(index);
						domain.setType(service.getFileType(s));
					}
					domain.setSiteid(getLoginerSiteId());
					domain.setServerpath(Constants.STATICRESOURCE_PATH + domain.getSiteid() + "/" + newfileName);
					bos.flush();
					bos.close();
					// 若使用绝对路径，同步更新 解决amway 集群问题
					if (Constants.IS_REALPATH) {
						try {
							String sourceSRPath = service.getRealPath(domain.getServerpath());
							FileUtil.update(sourceSRPath, this.getHttpServletRequest().getRealPath(domain.getServerpath()));
						} catch (Exception e) {
						}
					}
					bis.close();
					domain.setUploadman(getLoginer().getUsername());
					domain.setUploadtime(new Date(System.currentTimeMillis()));
					service.saveOrUpdate(domain);

				}
				zipFile.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "close";
	}

	public String saveOrUpdate() throws Exception {
		String filepath = "";
		if (null != _file && _file.length > 0) {
			filepath = upload();
		}

		domain = service.saveOrUpdate(domain);
		if (StringUtil.isEmpty(filepath)) {
			this.addActionMessage(getText("RESOURCE.HINTINFO.SAVESUCCESS"));
		}

		return EDIT_RESULT;
	}

	public String saveResourceFile() throws Exception {
		service.saveFileByTextContent(service.getRealPath(domain.getServerpath()), domain.get_filecontent());
		// 若使用绝对路径，同步更新 解决amway 集群问题
		if (Constants.IS_REALPATH) {
			try {
				String sourceSRPath = service.getRealPath(domain.getServerpath());
				FileUtil.update(sourceSRPath, this.getHttpServletRequest().getRealPath(domain.getServerpath()));
			} catch (Exception e) {

			}
		}
		return "input_file";
	}

	public String edit() throws Exception {
		if (id != null) {
			get();
			domain.set_site(service1.get(domain.getSiteid()).getSitename());

		} else {
			domain = new StaticResource();
			if (getLoginerSiteId() != 0) {
				domain.setSiteid(getLoginerSiteId());
				domain.set_site(service1.get(getLoginerSiteId()).getSitename());
			}
			domain.setUploadman(getLoginer().getUsername());
			domain.setUploadtime(new Date(System.currentTimeMillis()));
		}
		return EDIT_RESULT;
	}

	public String fileDownload() throws Exception {
		if (id != null) {
			get();
			File file;
			if (Constants.IS_REALPATH) {
				file = new File(Constants.ABSOLUTE_PATH + domain.getServerpath());
			} else {
				file = new File(this.getHttpServletRequest().getRealPath(domain.getServerpath()));
			}
			FileInputStream is = new FileInputStream(file);
			byte[] b = new byte[(int) file.length()];
			is.read(b, 0, (int) file.length());
			BlobFileObject bfo = new BlobFileObject();
			bfo.setContent(b);
			String fileName = domain.getName();
			is.close();
			if (StringUtils.isEmpty(fileName))
				fileName = "unknow_file";
			bfo.setFullfilename(fileName);
			this.getHttpServletRequest().setAttribute(Constants.DOWNLOAD_BLOB_FILE_OBJECT, bfo);
			return "file_download";
		} else {
			addActionError("找不到指定资源！");
			logger.info("找不到指定资源！");
			return "download_error";

		}

	}

	public String editFile() throws Exception {
		if (id != null) {
			get();
			domain.set_site(service1.get(domain.getSiteid()).getSitename());
			String realPath = service.getRealPath(domain.getServerpath());
			domain.set_filecontent(service.readFileContent(realPath));
		} else {
			domain = new StaticResource();
			if (getLoginerSiteId() != 0) {
				domain.setSiteid(getLoginerSiteId());
				domain.set_site(service1.get(getLoginerSiteId()).getSitename());
			}
			domain.setUploadman(getLoginer().getUsername());
			domain.setUploadtime(new Date(System.currentTimeMillis()));
		}
		return "input_file";
	}

	public String multiUpload() throws Exception {
		domain = new StaticResource();
		if (getLoginerSiteId() != 0) {
			domain.setSiteid(getLoginerSiteId());
		}

		return "multiUpload1";
	}

	/**
	 * 站点静态资源批量上传
	 * 
	 * @return
	 * @throws Exception
	 */

	public String uploads() throws Exception {
		domain.setSiteid(getLoginerSiteId());
		String uploadDir = getRealPath(getLoginerSiteId());
		String filename = "";
		if (_fileFileName != null && _file != null && _file.length == _fileFileName.length) {
			for (int i = 0; i < _file.length; i++) {
				if(Pattern.compile("[\u4e00-\u9fa5]+").matcher(_fileFileName[i]).find()){
					throw new IOException("资源名称不可以存在中文字符:");
				}
				
				domain.setName(_fileFileName[i]);
				int index = _fileFileName[i].indexOf(".");
				if (index != -1) {
					String s = _fileFileName[i].substring(index);
					if (s != null && !StringUtil.isEmpty(s) && (StringUtil.ifEqual(s, ".exe") || StringUtil.ifEqual(s, ".bat"))) {
						throw new IOException("不能上传" + s + "类型的文件");
					}
					domain.setType(service.getFileType(s));
				}
				String being = "";
				being = service.getStaticResourcePath(getLoginerSiteId(), domain.getName());
				if (being != null && !StringUtil.isEmpty(being)) {
					throw new IOException("资源名称已经存在");
				}

				// 保存文件
				boolean succ = saveFile(_file[i], uploadDir, _fileFileName[i], false);
				filename += _fileFileName[i];
				// 若使用绝对路径，同步更新 解决amway 集群问题
				if (Constants.IS_REALPATH) {
					try {
						String sourceSRPath = service.getRealPath(domain.getServerpath());
						FileUtil.update(sourceSRPath, this.getHttpServletRequest().getRealPath(domain.getServerpath()));
					} catch (Exception e) {

					}
				}
				if (!succ) {
					return "xpdlupload";
				}

			}
			addActionMessage(getText("RESOURCE.HINTINFO.SAVESUCCESS"));
		} else {
			logger.debug("uploadFiles is null");
			addActionMessage(getText("RESOURCE.HINTINFO.SAVEFAILED"));
		}

		domain.setUploadman(getLoginer().getUsername());
		domain.setUploadtime(new Date(System.currentTimeMillis()));
		domain = service.saveOrUpdate(domain);

		return "multiUpload1";

	}

	public String recycle() throws Exception {
		CriteriaSetup criteria = new CriteriaSetup();
		criteria.addFilter("status", 0);
		doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		return "recycle_list";// "recycle_list1";
	}

	/**
	 * 删除站点静态资源
	 * 
	 * @return
	 */
	public String delete() throws Exception {
		if (!StringUtil.isEmpty(keys)) {
			List<Long> list = StringUtil.splitToList(keys, ",");
			this.logger.info("delete list size:" + list.size());
			try {
				// 删除运行平台上的文件
				distributionService.deleteCenterById(list, getLoginer().getSiteId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				service.removeFileBoth(list);
			} catch (Exception e1) {
				e1.printStackTrace();
				return channelStaResList();
			}
			this.addActionMessage(getText("RESOURCE.HINTINFO.DELETESUCCESS"));
		} else
			this.addActionError(getText("RESOURCE.HINTINFO.LETSELECTDELETERECORDWORD"));
		return "dellist";
	}

	/**
	 * 修改status,信息还原
	 * 
	 * @return
	 * @throws Exception
	 */
	public String revivification() throws Exception {
		if (!StringUtil.isEmpty(keys)) {
			List list = StringUtil.splitToList(keys, ",");
			Iterator it = list.iterator();
			while (it.hasNext()) {
				domain = service.get(Long.parseLong(it.next().toString()));
				domain.setStatus(1);
				domain.setTimeDeleted(new Date());
				service.saveOrUpdate(domain);
			}
			// getService().removeByIds(list);
			this.addActionMessage("成功还原！");
		} else
			this.addActionError("请选择需还原的记录！");
		return "revivification";
	}

	/**
	 * 清空回收站信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String cleanup() throws Exception {
		if (!StringUtil.isEmpty(keys)) {
			List list = StringUtil.splitToList(keys, ",");
			this.logger.info("delete list size:" + list.size());
			service.removeFile(list);
			this.addActionMessage("清空成功！");
		} else
			this.addActionError("请选择需清空的记录！");
		return recycle();
		// return "revivification";
	}

	@Override
	protected EntityDao getService() {
		// TODO Auto-generated method stub
		return service;
	}

	public String getXpdlName() {
		return xpdlName;
	}

	public void setXpdlName(String xpdlName) {
		this.xpdlName = xpdlName;
	}

	public List getFileAttributes() {
		return fileAttributes;
	}

	public void setFileAttributes(List fileAttributes) {
		this.fileAttributes = fileAttributes;
	}

	public File[] get_file() {
		return _file;
	}

	public void set_file(File[] _file) {
		this._file = _file;
	}

	public String[] get_fileContentType() {
		return _fileContentType;
	}

	public void set_fileContentType(String[] contentType) {
		_fileContentType = contentType;
	}

	public String[] get_fileFileName() {
		return _fileFileName;
	}

	public void set_fileFileName(String[] fileName) {
		_fileFileName = fileName;
	}

	/**
	 * 单一文件上传
	 * 
	 * @return
	 * @throws Exception
	 */
	public String upload() throws Exception {
		// String uploadDir = getRealPath(domain.getSiteid());
		String uploadDir = service.getRealPath("") + domain.getServerpath();
		String filename = "";
		if (_fileFileName != null && _file != null && _file.length == _fileFileName.length) {
			for (int i = 0; i < _file.length; i++) {
				logger.info("uploadFilesFileName:" + _fileFileName[i]);
				// 保存文件
				boolean succ = saveFile(_file[i], uploadDir, _fileFileName[i], true);
				filename += _fileFileName[i];
				// 若使用绝对路径，同步更新 解决amway 集群问题
				if (Constants.IS_REALPATH) {
					try {
						String sourceSRPath = service.getRealPath(domain.getServerpath());
						FileUtil.update(sourceSRPath, this.getHttpServletRequest().getRealPath(domain.getServerpath()));
					} catch (Exception e) {

					}
				}
				if (!succ) {
					return "xpdlupload";
				}
			}
			addActionMessage(getText("RESOURCE.HINTINFO.SAVESUCCESS"));
		} else {
			logger.debug("uploadFiles is null");
			addActionMessage(getText("RESOURCE.HINTINFO.SAVEFAILED"));
		}
		return filename;
	}

	/**
	 * 保存文件
	 */
	private boolean saveFile(File inFile, String filePath, String fileName, boolean isReplace) {
		boolean succ = false;
		File outputFile;
		String newfileName = getSequence() + fileName;
		if (inFile == null)
			return succ;
		try {
			outputFile = (isReplace) ? new File(filePath) : new File(filePath + File.separatorChar + newfileName);
			// outputFile = new File(filePath + File.separatorChar +
			// newfileName);
			// linux系统下renameTo方法只能起到改名作用
			// inFile.renameTo(outputFile);

			FileUtil.writeTo(inFile, outputFile);
			succ = true;
		} catch (Exception e) {
			e.printStackTrace();
			addActionMessage(getText("RESOURCE.HINTINFO.SAVEFAILED"));
		}
		if (!isReplace) {
			domain.setServerpath(Constants.STATICRESOURCE_PATH + domain.getSiteid() + "/" + newfileName);
		}
		return succ;
	}

	private boolean saveZipFile(File inFile, File outputFile) {
		boolean succ = false;
		if (inFile == null)
			return succ;
		try {
			FileUtil.writeTo(inFile, outputFile);
			succ = true;
		} catch (Exception e) {
			e.printStackTrace();
			addActionMessage(getText("RESOURCE.HINTINFO.SAVEFAILED"));
		}
		return succ;
	}

	/**
	 * 检测服务器上目录是否存在，不存在则创建
	 * 
	 * @param siteid
	 * @return
	 * @throws Exception
	 */
	private String getRealPath(long siteid) throws Exception {
		String fullpath = "";
		if (Constants.IS_REALPATH) {
			fullpath = Constants.ABSOLUTE_PATH + Constants.STATICRESOURCE_PATH;
		} else {
			fullpath = ServletActionContext.getServletContext().getRealPath(Constants.STATICRESOURCE_PATH);
		}
		java.io.File f = new java.io.File(fullpath);
		if (!f.isDirectory()) {
			f.mkdir();
		}
		fullpath = fullpath + "/" + siteid + "/";
		java.io.File file = new java.io.File(fullpath);
		if (!file.isDirectory()) {
			file.mkdir();
		}
		return fullpath;
	}

	/**
	 * 对文件进行唯一命名
	 * 
	 * @return
	 */
	public static synchronized long getSequence() {
		long result = System.currentTimeMillis();
		if (result == millis) {
			old++;
			result = millis * base + old;
		} else {
			millis = result;
			result *= base;
			old = 0;
		}
		return result;
	}

	/**
	 * 站点静态资源批量分发
	 * 
	 * @return
	 * @throws IOException
	 */
	public String distribution() throws IOException {
		int flag = Integer.valueOf(getHttpServletRequest().getParameter("flag"));
		List list = new ArrayList();

		if (chnid != null) {
			List<StaticResource> srListc = service.findBy("channelid", chnid);
			for (int i = 0; i < srListc.size(); i++) {
				list.add(srListc.get(i).getServerpath());
			}
		} else {
			List<StaticResource> srList = service.getAll();
			for (int i = 0; i < srList.size(); i++) {
				list.add(srList.get(i).getServerpath());
			}
		}
		// 批量分发
		if (flag == 0) {
			try {
				if (list.size() > 0) {
					distributionService.getStaticResource(list, getLoginer().getSiteId());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	/**
	 * 站点静态资源选择分发
	 * 
	 * @return
	 * @throws IOException
	 */
	public String selectDistribution() throws Exception {
		List list = new ArrayList();
		int flag = Integer.valueOf(getHttpServletRequest().getParameter("flag"));
		if (!StringUtil.isEmpty(keys)) {
			List keylist = StringUtil.splitToList(keys, ",");
			for (int i = 0; i < keylist.size(); i++) {
				StaticResource sr = service.get(Long.valueOf(keylist.get(i).toString()));
				list.add(sr.getServerpath());
			}
		}

		if (flag == 0) {
			try {
				if (list.size() > 0) {
					distributionService.getStaticResource(list, getLoginer().getSiteId());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	/**
	 * 列出频道下的静态资源
	 * 
	 * @return
	 * @throws Exception
	 */
	public String channelStaResList() throws Exception {
		CriteriaSetup criteria = new CriteriaSetup();
		criteria.addFilter("status", 1);
		if (getLoginerSiteId() != 0) {
			criteria.addFilter("siteid", getLoginerSiteId());
		}
		if (chnid != null) {
			criteria.addFilter("channelid", chnid);
			doListEntity(criteria, getTableId(), Page.DEFAULT_PAGE_SIZE);
		}

		return "channelRe_list";
	}

	/**
	 * 批量上传频道静态资源
	 * 
	 * @return
	 * @throws Exception
	 */
	public String multiUploadChannelRes() throws Exception {
		domain = new StaticResource();
		if (getLoginerSiteId() != 0) {
			domain.setSiteid(getLoginerSiteId());
		}
		if (chnid != null) {
			domain.setChannelid(chnid);
		}
		return "multiUploadChannelRes";
	}

	/**
	 * 频道静态资源批量上传
	 * 
	 * @return
	 * @throws Exception
	 */

	public String uploadChannelRes() throws Exception {
		domain.setSiteid(getLoginerSiteId());
		domain.setChannelid(chnid);
		String uploadDir = getRealPath(getLoginerSiteId());
		String filename = "";
		if (_fileFileName != null && _file != null && _file.length == _fileFileName.length) {
			for (int i = 0; i < _file.length; i++) {

				domain.setName(_fileFileName[i]);
				int index = _fileFileName[i].indexOf(".");
				if (index != -1) {
					String s = _fileFileName[i].substring(index);
					domain.setType(service.getFileType(s));
				}
				String being = "";
				being = service.getStaticResourcePath(getLoginerSiteId(), domain.getName());
				if (being != null && !StringUtil.isEmpty(being)) {
					throw new IOException("资源名称已经存在");
				}
				// 保存文件
				boolean succ = saveFile(_file[i], uploadDir, _fileFileName[i], false);
				filename += _fileFileName[i];
				// 若使用绝对路径，同步更新 解决amway 集群问题
				if (Constants.IS_REALPATH) {
					try {
						String sourceSRPath = service.getRealPath(domain.getServerpath());
						FileUtil.update(sourceSRPath, this.getHttpServletRequest().getRealPath(domain.getServerpath()));
					} catch (Exception e) {

					}
				}
				if (!succ) {
					return "xpdlupload";
				}

			}
			addActionMessage(getText("FileSaveSuccess"));
		} else {
			logger.debug("uploadFiles is null");
			addActionMessage(getText("ParameterError"));
		}

		domain.setUploadman(getLoginer().getUsername());
		domain.setUploadtime(new Date(System.currentTimeMillis()));
		domain = service.saveOrUpdate(domain);

		return "multiUploadChannelRes";

	}

	/**
	 * 删除频道静态资源
	 * 
	 * @return
	 */
	public String deleteChannelRes() throws Exception {
		if (!StringUtil.isEmpty(keys)) {
			List list = StringUtil.splitToList(keys, ",");
			this.logger.info("delete list size:" + list.size());

			try {
				// 删除运行平台上的文件
				distributionService.deleteCenterById(list, getLoginer().getSiteId());
			} catch (Exception e) {
				e.printStackTrace();

			}
			try {
				service.removeFileBoth(list);
			} catch (Exception e1) {
				e1.printStackTrace();
				return channelStaResList();
			}

			this.addActionMessage("删除成功！");
		} else
			this.addActionError("请选择需删除的记录！");
		return channelStaResList();
	}

	/**
	 * 将一个zip文件里面的所有文件上传为频道的静态资源
	 * 
	 * @return
	 * @throws Exception
	 */
	public String importZipChannelRes() throws Exception {
		ChannelManagerService channelService = (ChannelManagerService) getServiceById("channelManagerService");
		Channel channel = channelService.get(chnid);
		Validate.notNull(channel, "Channel is null!");
		String uploadDir = getHttpServletRequest().getRealPath(Constants.ZIP_FILE);
		boolean succ = false;
		for (int i = 0; i < _file.length; i++) {
			succ = saveZipFile(_file[i], new File(uploadDir));
		}
		if (succ) {
			try {
				ZipFile zipFile = new ZipFile(uploadDir);
				Enumeration emu = zipFile.entries();
				while (emu.hasMoreElements()) {
					domain = new StaticResource();
					ZipEntry entry = (ZipEntry) emu.nextElement();
					// 会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
					if (entry.isDirectory()) {
						new File(getHttpServletRequest().getRealPath(Constants.DYNAMICPAGE_PATH + getLoginerSiteId()) + "/"
						        + entry.getName()).mkdirs();
						continue;
					}
					BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
					String newfileName = getSequence() + entry.getName();
					File outputFile = new File(getRealPath(getLoginerSiteId()) + File.separatorChar + newfileName);
					String being = service.getStaticResourcePath(getLoginerSiteId(), entry.getName());
					// 资源名称已经存在
					if (being != null && !StringUtil.isEmpty(being)) {
						continue;
					}
					// 加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
					// 而这个文件所在的目录还没有出现过，所以要建出目录来。
					File parent = outputFile.getParentFile();
					if (parent != null && (!parent.exists())) {
						parent.mkdirs();
					}
					FileOutputStream fos = new FileOutputStream(outputFile);
					BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER);
					int count;
					byte data[] = new byte[BUFFER];
					while ((count = bis.read(data, 0, BUFFER)) != -1) {
						bos.write(data, 0, count);
					}
					domain.setName(entry.getName());
					int index = entry.getName().indexOf(".");
					if (index != -1) {
						String s = entry.getName().substring(index);
						domain.setType(service.getFileType(s));
					}
					domain.setSiteid(channel.getSite().getOid());
					domain.setChannelid(chnid);
					domain.setServerpath(Constants.STATICRESOURCE_PATH + domain.getSiteid() + "/" + newfileName);
					bos.flush();
					bos.close();
					// 若使用绝对路径，同步更新 解决amway 集群问题
					if (Constants.IS_REALPATH) {
						try {
							String sourceSRPath = service.getRealPath(domain.getServerpath());
							FileUtil.update(sourceSRPath, this.getHttpServletRequest().getRealPath(domain.getServerpath()));
						} catch (Exception e) {
						}
					}
					bis.close();
					domain.setUploadman(getLoginer().getUsername());
					domain.setUploadtime(new Date(System.currentTimeMillis()));
					service.saveOrUpdate(domain);

				}
				zipFile.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "close";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<StaticResource> getStaticList() {
		return staticList;
	}

	public Long getChnid() {
		return chnid;
	}

	public void setChnid(Long chnid) {
		this.chnid = chnid;
	}
}
