package com.cyberway.cms.component.function.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.cyberway.cms.Constants;
import com.cyberway.cms.component.function.domain.JSFunction;
import com.cyberway.cms.component.function.service.JSFunctionService;
import com.cyberway.cms.template.service.TemplateManagerService;
import com.cyberway.common.fileDownload.BlobFileObject;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.FileUtil;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

public class JSFunctionController extends BaseBizController<JSFunction> {
	private File _file;
	private List<JSFunction> faildItems;

	private JSFunctionService service = (JSFunctionService) this
			.getServiceById("JSFunctionService");

	@Override
	protected EntityDao<JSFunction> getService() {
		return service;
	}

	@Override
	public String execute() throws Exception {
		super.list();
		return "function_list";
	}

	public String[] getTypeList() {

		String[] res = this.service.typeList();
		if(logger.isDebugEnabled())
			for (String s : res)
				this.logger.debug("JS组件类型: " + s);
		
		return res;
	}

	/**
	 * 导出 数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public String exportData() throws Exception {
		Map<String , String> map = new LinkedHashMap<String , String>();
		map.put("type", "类型");
		map.put("functionName", "功能");
		map.put("describe", "说明");
		map.put("remark", "备注");
		map.put("code", "JS脚本");
		
//		CriteriaSetup criteria = new CriteriaSetup();
//		criteria.addFilter("id", keys);
//		doListEntity(criteria,  getTableId(), Page.DEFAULT_PAGE_SIZE);
		
		byte[] b = this.service.exportToExcel(service.findByIds(StringUtil.splitToList(keys,",")), map);
		BlobFileObject bfo = new BlobFileObject();
		bfo.setContent(b);
		bfo.setFullfilename("js组件"+System.currentTimeMillis()+".xls");
		this.getHttpServletRequest().setAttribute(Constants.DOWNLOAD_BLOB_FILE_OBJECT, bfo);
		return "exportToExcel";
	}
	
	public String toimportData(){
		return "uploadExcel";
	}

	/**
	 * 导入 数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public String importData() throws Exception {
		Map<String , String> map = new LinkedHashMap<String , String>();
		map.put("type", "类型");
		map.put("functionName", "功能");
		map.put("describe", "说明");
		map.put("remark", "备注");
		map.put("code", "JS脚本");
		if(_file != null){
			Map<String, List<JSFunction>> mess = this.service.importDataFormExcel(_file, map);
			this.setItems(mess.get("success"));
			this.setFaildItems(mess.get("faild"));
		}
		return "mess";
	}
	
	public String selectJSComList() throws Exception{
		CriteriaSetup criteriaSetup = new CriteriaSetup();
		if(domain.getType()!=null&&!"".equals(domain.getType())){
			criteriaSetup.addFilter("type", this.domain.getType());
			doListEntity(criteriaSetup, tableId, Page.DEFAULT_PAGE_SIZE);
		}else{
			list();
		}
		return "func_select";
	}
	
	public String selectJSComponent() throws Exception{
		JSFunction vo = (JSFunction)this.service.get(this.domain.getOid());
		this.setDomain(vo);
		return "func_select_re";
	}

	public File get_file() {
		return _file;
	}

	public void set_file(File _file) {
		this._file = _file;
	}

	public List<JSFunction> getFaildItems() {
		return faildItems;
	}

	public void setFaildItems(List<JSFunction> faildItems) {
		this.faildItems = faildItems;
	}

	

}
