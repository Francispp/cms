package com.cyberway.cms.component.selectlist.service;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;

import org.springframework.dao.DataRetrievalFailureException;

import com.cyberway.cms.component.selectlist.domain.ListOption;
import com.cyberway.cms.component.selectlist.domain.ListTitle;
import com.cyberway.core.dao.HibernateEntityDao;

public class ListTitleService extends HibernateEntityDao<ListTitle> {

	private Cache	selectListCache;	// 根据key 存取

	@Override
	public ListTitle get(Serializable id) {

		return super.get(id);
	}

	@Override
	public void removeById(Serializable id) {
		this.remmoveSelectListInCache(this.get(id).getKey());
		super.removeById(id);
	}

	public ListTitle getSelectListByKey(String key) {
		ListTitle sl = this.getSelectListFromCache(key);
		if (sl != null)
			return sl;

		// 不在缓存中时
		List<ListTitle> l = this.find("from ListTitle sl join fetch sl.options where sl.key=?", new Object[] { key });
		if (l.size() > 0) {
			// 加入缓存
			this.putSelectListInCache(l.get(0));
			return l.get(0);
		} else
			return new ListTitle();
	}

	/**
	 * 通过关键字, 和选项key 获取当前key 值
	 * 
	 * @param key
	 *            关键字
	 * @param optionkey
	 *            选项key
	 */
	public String getVlaueByKeyAndOptionKey(String key, String optionkey) {
		String value = null;
		ListTitle listTitle = getSelectListByKey(key);
		if (listTitle == null || listTitle.getOptions() == null)
			return value;

		Iterator<ListOption> options = listTitle.getOptions().iterator();

		while (options.hasNext()) {
			ListOption option = options.next();
			if (option.getKey().equalsIgnoreCase(optionkey)) {
				value = option.getValue();
				break;
			}
		}

		return value;
	}

	@Override
	public ListTitle saveOrUpdate(ListTitle obj) {
		obj = super.saveOrUpdate(obj);
		// 加入缓存
		this.putSelectListInCache(obj);
		return obj;
	}

	// 从缓存中取
	public ListTitle getSelectListFromCache(String key) {
		Element element = null;
		try {
			element = this.selectListCache.get(key);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage(), cacheException);
		}
		if (element == null) {
			return null;
		} else {
			return (ListTitle) element.getValue();
		}
	}

	/**
	 * 取分类
	 * 
	 * @return
	 */
	public List<String> typeList() {
		List<String> l = find("select distinct sl.type from ListTitle sl");
		return l;
	}

	public void putSelectListInCache(ListTitle sl) {
		Element el = new Element(sl.getKey(), sl);
		if (logger.isDebugEnabled()) {
			logger.debug("Cache selectListCache put: " + el.getKey());
		}
		this.selectListCache.put(el);
	}

	public void remmoveSelectListInCache(String key) {
		this.selectListCache.remove(key);
	}

	public Cache getSelectListCache() {
		return selectListCache;
	}

	public void setSelectListCache(Cache selectListCache) {
		this.selectListCache = selectListCache;
	}

}
