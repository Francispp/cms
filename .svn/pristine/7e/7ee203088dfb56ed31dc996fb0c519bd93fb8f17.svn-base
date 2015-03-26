package com.cyberway.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.ecside.table.limit.Limit;

import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.Page;

/**
 * 针对单个Entity对象的操作定义.
 * 不依赖于具体ORM实现方案.
 *
 * @author caiw
 */
public interface EntityDao<T> {

	T get(Serializable id);

	List<T> getAll();

	void save(Object o);

	void remove(Object o);

	void removeById(Serializable id);
	
	T saveOrUpdate(T obj);

    void removeByIds(List<Long> ids);
	Page findECPage(Limit limit,CriteriaSetup criteriaSetup) throws Exception;
	Page findECPage(Limit limit,String hql,Object... args) throws Exception; 
	Integer getCount(CriteriaSetup criteriaSetup);
	Integer getCount(String sql);
	boolean saveByAjax(Map<String, Object> data);
	String removeByAjax(Map<String, Object> data);
	/**
	 * 取得指定对象，同时带出指定关系名
	 * @param id
	 * @param relations
	 * @return
	 */
	public T get(Serializable id,String[] relations);	
	
	
}
