package com.cyberway.common.menu.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cyberway.common.menu.domain.MenuResource;
import com.cyberway.core.dao.HibernateEntityDao;

/**
 * com.cyberway.common.menu.service.MenuResourceService
 * 
 * @author Janice Yang
 * 
 * @createTime 2012-2-24 下午12:13:51
 * 
 * @Description:
 * 
 */
public class MenuResourceService extends HibernateEntityDao<MenuResource> {
	/**
	 * 获取顶级菜单的集合
	 * 
	 * @return
	 */
	public List<MenuResource> getTopMenu() {
		Criteria c = getEntityCriteria();
		c.add(Restrictions.isNull("pid"));
		c.addOrder(Order.asc("orderNo"));
		return c.list();
	}
	
	/**
	 * 获取顶级菜单的集合
	 * 
	 * @return
	 */
	public List<MenuResource> getTopMenu(Long id) {
		Criteria c = getEntityCriteria();
		if(id != null){
			c.add(Restrictions.not(Restrictions.eq("id", id)));
		}
		c.add(Restrictions.isNull("pid"));
		c.addOrder(Order.asc("orderNo"));
		return c.list();
	}

	/**
	 * 判断菜单代码是否唯一
	 * 
	 * @param id
	 *            菜单id
	 * @param menuCode
	 *            菜单代码
	 * @return "0"表示不唯一,"1"表示唯一
	 */
	public int menuCodeIsUnique(Long id, String menuCode) {
		List<MenuResource> menuList;
		if (!id.equals(0L)) {
			MenuResource menu = get(id);
			if (menu.getMenuCode().equals(menuCode)) {
				return 1;
			} else {
				menuList = findBy("menuCode", menuCode);
				if (menuList.size() > 0) {
					return 0;
				} else {
					return 1;
				}
			}
		} else {
			menuList = findBy("menuCode", menuCode);
			if (menuList.size() > 0) {
				return 0;
			} else {
				return 1;
			}
		}
	}
}
