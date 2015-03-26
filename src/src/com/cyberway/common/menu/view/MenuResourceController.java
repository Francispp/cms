package com.cyberway.common.menu.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cyberway.common.menu.domain.MenuResource;
import com.cyberway.common.menu.service.MenuResourceService;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

/**
 * com.cyberway.common.menu.view.MenuResourceController
 * 
 * @author Janice Yang
 * 
 * @createTime 2012-2-24 下午12:15:24
 * 
 * @Description:
 * 
 */
public class MenuResourceController extends BaseBizController<MenuResource> {
	/**
	 * 顶级菜单
	 */
	List<MenuResource>	         topMenuResource	= new ArrayList<MenuResource>();

	/**
	 * 所有菜单集合
	 */
	List<MenuResource>	         menuResourceList	= new ArrayList<MenuResource>();

	// WebLogic的特殊情况,在页面中不可以使用例如:<ww:select name="domain.issued"
	// list="#{true:'是',false:'否'}" />
	// 改为:<ww:select name="domain.issued" list="trueOfFalseMap" />
	private Map<Integer, String>	trueOfFalseMap1	= null;
	private Map<Integer, String>	trueOfFalseMap2	= null;

	MenuResourceService	         service	        = (MenuResourceService) this.getServiceById("menuResourceService");

	@Override
	protected MenuResourceService getService() {
		return service;
	}

	@Override
	public String execute() throws Exception {
		return list();
	}

	@Override
	public String list() throws Exception {
		menuResourceList = service.getTopMenu();
		return LIST_RESULT;
	}

	@Override
	public String saveOrUpdate() throws Exception {
		if(domain.getId() != null && domain.getPid() != null){//编辑顶级菜单
			MenuResource m = service.get(domain.getId());
			if(m.getSubMenuResources().size() > 0){
				addActionMessage("保存失败.该菜单有下级菜单,请先移除掉其下级菜单后,再移动该菜单!");
				return EDIT_RESULT;
			}
		}
		domain = getService().saveOrUpdate(domain);
		addActionMessage("保存成功!");
		return EDIT_RESULT;
	}

	@Override
	public String delete() throws Exception {
		if (!StringUtil.isEmpty(keys)) {
			List<Long> list = StringUtil.splitToList(keys, ",");
			for (Long id : list) {
				MenuResource menu = service.get(id);
				if (menu.getPid() == null && menu.getSubMenuResources().size() != 0) {
					addActionMessage("菜单'" + menu.getMenuName() + "'下还有子菜单,请先删除子菜单后再删除该顶级菜单!");
					return execute();
				} else {
					getService().removeById(id);
				}
			}

			this.addActionMessage(this.getText("RESOURCE.HINTINFO.DELETESUCCESS"));

		} else
			this.addActionError(this.getText("RESOURCE.HINTINFO.LETSELECTDELETERECORDWORD"));
		return execute();
	}

	/**
	 * 选择图标
	 * 
	 * @return
	 */
	public String selectIco() {
		return "selectIco";
	}

	public List<MenuResource> getTopMenuResource() {
		if (domain.getId() != null && domain.getPid() == null) {// 当编辑顶级菜单时,在"所属父菜单"的下拉选项中剔除掉自己.
			topMenuResource = service.getTopMenu(domain.getId());
		} else {
			topMenuResource = service.getTopMenu();
		}
		return topMenuResource;
	}

	public void setTopMenuResource(List<MenuResource> topMenuResource) {
		this.topMenuResource = topMenuResource;
	}

	public List<MenuResource> getMenuResourceList() {
		return menuResourceList;
	}

	public void setMenuResourceList(List<MenuResource> menuResourceList) {
		this.menuResourceList = menuResourceList;
	}

	public Map<Integer, String> getTrueOfFalseMap1() {
		if (trueOfFalseMap1 != null) {
			return trueOfFalseMap1;
		} else {
			trueOfFalseMap1 = new HashMap<Integer, String>();
			trueOfFalseMap1.put(new Integer(1), "显示");
			trueOfFalseMap1.put(new Integer(0), "不显示");
			return trueOfFalseMap1;
		}
	}

	public Map<Integer, String> getTrueOfFalseMap2() {
		if (trueOfFalseMap2 != null) {
			return trueOfFalseMap2;
		} else {
			trueOfFalseMap2 = new HashMap<Integer, String>();
			trueOfFalseMap2.put(new Integer(2), "开放");
			trueOfFalseMap2.put(new Integer(0), "系统");
			trueOfFalseMap2.put(new Integer(1), "站点");
			return trueOfFalseMap2;
		}
	}

}
