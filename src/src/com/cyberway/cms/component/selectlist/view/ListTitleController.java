package com.cyberway.cms.component.selectlist.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;

import com.cyberway.cms.component.selectlist.domain.ListOption;
import com.cyberway.cms.component.selectlist.domain.ListTitle;
import com.cyberway.cms.component.selectlist.service.ListOptionService;
import com.cyberway.cms.component.selectlist.service.ListTitleService;
import com.cyberway.core.web.BaseBizController;

public class ListTitleController extends BaseBizController<ListTitle> {

	private ListTitleService	service	= null;
	
	private ListOptionService listOptionService = (ListOptionService) getServiceById("listOptionService");

	private List<String>	    optids = new ArrayList<String>();
	private List<String>	    optkeys;	    //
	private List<String>	    optvalues;

	private ListTitle	        selectList;
	private Map<String, String>	selectMap;
	private String	            key;

	@Override
	protected ListTitleService getService() {
		if (this.service == null)
			this.service = (ListTitleService) super.getServiceById("ListTitleService");
		return service;
	}

	@Override
	public String saveOrUpdate() throws Exception {
		if (this.optkeys != null && this.optvalues != null){
			for (int i = 0; i < this.optkeys.size(); i++) {
				ListOption opt = null;
				if(!optids.isEmpty() && StringUtils.isNotBlank(optids.get(i))){
					opt = listOptionService.get(Long.valueOf(optids.get(i)));
					opt.setKey(optkeys.get(i));
					opt.setValue(optvalues.get(i));
					opt.setPos(i);
				}else{
					opt = new ListOption(this.optkeys.get(i), this.optvalues.get(i), i);
				}
				
				this.domain.getOptions().add(opt);
			}
		}
		// key 引用名不能重复
		if (this.getService().isNotUnique(domain, "key")) {
			addFieldError("key", "key 引用名不能重复!");
			return EDIT_RESULT;
		}
		String result = super.saveOrUpdate();
		listOptionService.deleteByTitleIdIsNull();

		return result;
	}

	/**
	 * 供webwork 标签调用 <ww:action id="sel" executeResult="false" namespace="/cms"
	 * name="selectlist!selectList"> <ww:param name="key" value=""/>
	 * </ww:action> <ww:select name="aaaaa" list="#attr.sel.options"
	 * listKey="key" listValue="value" required="true"/>
	 * 
	 * @param key
	 */
	public void selectList() {
		if (GenericValidator.isBlankOrNull(key))
			addActionError("引用名不能为空!");
		ListTitle sl = this.getService().getSelectListByKey(key);
		this.setSelectList(sl);
	}

	/**
	 * 与 selectList()方法用法相同，组装Map对象
	 */
	public void selectMap() {
		if (GenericValidator.isBlankOrNull(key))
			addActionError("引用名不能为空!");
		ListTitle sl = this.getService().getSelectListByKey(key);
		Set<ListOption> li = sl.getOptions();
		Map<String, String> smap = new TreeMap<String, String>();
		for (ListOption opt : li) {
			smap.put(opt.getKey(), opt.getValue());
		}
		this.setSelectMap(smap);
	}

	@Override
	public String execute() throws Exception {

		return list();
	}

	@Override
	public String edit() throws Exception {
		if (id != null) {
			List list = getService().find("from ListTitle title left join fetch title.options where title.oid=?", new Object[] { id });
			if (list.size() > 0) {
				domain = (ListTitle) list.get(0);
				// this.optkeys = new ArrayList<String>();
				// this.optvalues = new ArrayList<String>();
				// for(ListOption opt:this.domain.getOptions()){
				// this.optkeys.add(opt.getKey());
				// }
			}
		} else {
			domain = getDomainClass().newInstance();
		}
		return EDIT_RESULT;
	}

	/**
	 * 分类
	 * 
	 * @return
	 */
	public List<String> getTypeList() {
		return this.getService().typeList();
	}

	public List<String> getOptkeys() {
		return optkeys;
	}

	public List<String> getOptvalues() {
		return optvalues;
	}

	public void setOptkeys(List<String> optkeys) {
		this.optkeys = optkeys;
	}

	public void setOptvalues(List<String> optvalues) {
		this.optvalues = optvalues;
	}

	public ListTitle getSelectList() {
		return selectList;
	}

	public void setSelectList(ListTitle selectList) {
		this.selectList = selectList;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Map<String, String> getSelectMap() {
		return selectMap;
	}

	public void setSelectMap(Map<String, String> selectMap) {
		this.selectMap = selectMap;
	}

	public List<String> getOptids() {
    	return optids;
    }

	public void setOptids(List<String> optids) {
    	this.optids = optids;
    }
}
