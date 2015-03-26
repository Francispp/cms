package com.cyberway.core.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.ecside.table.limit.Filter;
import org.ecside.table.limit.Limit;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.cyberway.core.dao.support.CriteriaSetup;
import com.cyberway.core.dao.support.HqlPage;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.ectable.ExtremeTablePage;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.BeanUtils;
import com.cyberway.core.utils.GenericsUtils;
import com.cyberway.core.utils.StringUtil;

/**
 * 负责为单个Entity对象提供CRUD操作的Hibernate DAO基类.
 * <p/>
 * 子类只要在类定义时指定所管理Entity的Class, 即拥有对单个Entity对象的CRUD操作.
 * eg.
 * <pre>
 * public class UserManager extends HibernateEntityDao<User>{
 * }
 * </pre>
 *
 * @author caiw
 * @see HibernateGenericDao
 */
@SuppressWarnings("unchecked")
public class HibernateEntityDao<T> extends HibernateGenericDao implements EntityDao<T> {
    public static int COUNT_MODE = 1;
    public static int SCROLL_MODE = 2;
    public static int LIST_MODE = 3;
	/**
	 * DAO所管理的Entity类型.
	 */
	protected Class<T> entityClass;

	/**
	 * 取得entityClass.
	 * JDK1.4不支持泛型的子类可以抛开Class<T> entityClass,重载此函数达到相同效果。
	 */
	protected Class<T> getEntityClass() {
		return entityClass;
	}

	/**
	 * 在构造函数中将泛型T.class赋给entityClass
	 */
	public HibernateEntityDao() {
		entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	}
	/**
	 * 根据ID获取对象
	 */
	public T getObject(Serializable id) {
		return super.get(getEntityClass(), id);
		}
	/**
	 * 根据ID获取对象
	 */
	public T get(Serializable id) {
		//System.out.print("getIdName():"+getIdName()+"__Name:"+getEntityClass().getName());
		List<T> list=this.find("from "+getEntityClass().getName()+" where "+getIdName()+"=?", new Object[]{id});
		T obj=null;
		if(list!=null&&list.size()>0)
			obj=list.get(0);		
		return obj;
	}
	/**
	 * 取得指定对象，同时带出指定关系名
	 * @param id
	 * @param relations
	 * @return
	 */
	public T get(Serializable id,String[] relations){
		T obj=getObject(id);
		try{
			if(relations!=null){
				Object temp=null;
				for(String rl:relations){
					temp=PropertyUtils.getProperty(obj, rl);
					logger.info("relations:"+rl);
				}
				temp=null;
			}
		}catch(Exception e){
			logger.error(e);
		}
		return obj;
	} 
	/* (non-Javadoc)
	 * @see com.cyberway.core.dao.HibernateGenericDao#get(java.lang.Class, java.io.Serializable)
	 */
	public Object get(Class entityClass,Serializable id) {
		//System.out.print("getIdName():"+getIdName()+"__Name:"+getEntityClass().getName());
		List list=this.find("from "+entityClass.getName()+" where "+getIdName(entityClass)+"=?", new Object[]{id});
		Object obj=null;
		if(list!=null&&list.size()>0)
			obj=list.get(0);		
		return obj;
	}	
	/**
	 * 保存并返回对象
	 * @param obj
	 * @return
	 */
	public T saveOrUpdate(T obj){
		save(obj);
		return obj;
	}
	
	/**
	 * 保存对象集
	 * @param c
	 */
	public void saveOrUpdateAll(Collection c) {
		getHibernateTemplate().saveOrUpdateAll(c);
	}	
	/**
	 * 获取主键名
	 * @return
	 */
	public String getIdName() {
		String rt = null;
		rt = this.getHibernateTemplate().getSessionFactory().getClassMetadata(getEntityClass()).getIdentifierPropertyName();
		return rt;
	}
	/**
	 * 获取主键名
	 * @return
	 */
	public String getIdName(Class entityClass) {
		String rt = null;
		rt = this.getHibernateTemplate().getSessionFactory().getClassMetadata(entityClass).getIdentifierPropertyName();
		return rt;
	}	
	/**
	 * 获取全部对象
	 */
	public List<T> getAll() {
		return getAll(getEntityClass());
	}

	/**
	 * 根据ID移除对象.
	 */
	public void removeById(Serializable id) {
		removeById(getEntityClass(), id);
	}
    /**
     * 删除多条记录
     * @param ids
     */
    public void removeByIds(List<Long> ids){
    	if(ids!=null){
    		for (Long id : ids)
    			removeById(id);
    	}
    }
	/**
	 * 根据属性名和属性值查询对象.
	 *
	 * @return 符合条件的对象列表
	 */
	public List<T> findBy(String name, Object value) {
		return findBy(getEntityClass(), name, value);
	}

	/**
	 * 根据属性名和属性值查询单个对象.
	 *
	 * @return 符合条件的唯一对象
	 */
	public T findUniqueBy(String name, Object value) {
		return findUniqueBy(getEntityClass(), name, value);
	}

	/**
	 * 根据属性名和属性值以Like AnyWhere方式查询对象.
	 */
	public List<T> findByLike(String name, String value) {
		return findByLike(getEntityClass(), name, value);
	}

	/**
	 * 取得Entity的Criteria.
	 */
	protected Criteria getEntityCriteria() {
		return getCriteria(getEntityClass());
	}

	/**
	 * 判断对象某些属性的值在数据库中不存在重复
	 *
	 * @param names 在POJO里不能重复的属性列表,以逗号分割
	 *              如"name,loginid,password"
	 */
	public boolean isNotUnique(Object entity, String names) {
		return isNotUnique(getEntityClass(), entity, names);
	}
	/**
	 * 消除与 Hibernate Session 的关联
	 * 
	 * @param entity
	 */
	public void evit(Object entity) {
		getHibernateTemplate().evict(entity);
	}	
	/**
	 * 支持EC控件的查询方法
	 * 
	 * @param filterMap
	 *            过滤条件
	 * @param limit
	 *            EC的limit对象
	 * @return
	 * @throws
	 * @throws Exception
	 *             TODO:去除日期类型模糊查询带的Oralce物理特性
	 */
	public Page findECPage(Limit limit,CriteriaSetup criteriaSetup) throws Exception {
		Criteria criteria = this.getEntityCriteria();
		//CriteriaSetup criteriaSetup = new CriteriaSetup();
		if(criteriaSetup==null)
			criteriaSetup = new CriteriaSetup();	
		
		Map orderMap = ExtremeTablePage.getSort(limit);
		if (!CollectionUtils.isEmpty(orderMap))
			sortCriteria(criteria, orderMap, null);
		
		criteriaSetup.setup(criteria,orderMap);
		if (limit.isFiltered()) {
			Filter[] filters = limit.getFilterSet().getFilters();
			for (Filter filter : filters) {
				//Class type = this.getEntityClass().getDeclaredField(filter.getProperty()).getType();
				Field field = getField(this.getEntityClass(), filter.getProperty());
				if(field != null){
					Class type = field.getType();
					
					if (type.equals(Date.class) || type.equals(java.sql.Date.class)) {// 如果是日期型的做特殊处理
						// 注意这里别名和字段名的取法，这里存在bug，字段名称和日期格式问题...
						/*criteria.add(Restrictions.sqlRestriction("to_char({alias}."
								+ getColumnByProperty(filter.getProperty()) + ",'"
								+ getDateFormatByProperty(filter.getProperty())
								+ "') like ?", "%" + filter.getValue() + "%",
								Hibernate.CHARACTER));*/
						String filterValue = filter.getValue();
						String[] dates = filterValue.split(",");
						if(dates.length == 2 || (dates.length==1 && filterValue.endsWith(","))){
							try{
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								Calendar cal = Calendar.getInstance();
								if(StringUtils.isNotBlank(dates[0])){
									Date startDate = sdf.parse(dates[0]);
									if(dates.length>1 && StringUtils.isNotBlank(dates[1])){
										Date endDate = sdf.parse(dates[1]);
										cal.setTime(endDate);
										cal.add(Calendar.DAY_OF_MONTH, 1);
										criteria.add(Restrictions.ge(filter.getProperty(), startDate));
										criteria.add(Restrictions.lt(filter.getProperty(), cal.getTime()));
									} else {
										criteria.add(Restrictions.ge(filter.getProperty(), startDate));
									}
								} else if(dates.length>1 && StringUtils.isNotBlank(dates[1])){
									Date endDate = sdf.parse(dates[1]);
									cal.setTime(endDate);
									cal.add(Calendar.DAY_OF_MONTH, 1);
									criteria.add(Restrictions.lt(filter.getProperty(), cal.getTime()));
								}
							}catch(Exception e){
								addDateCriteria(criteria, filter);
							}
						}else{
							addDateCriteria(criteria, filter);
						}
					}else if (type.equals(java.lang.Integer.class)||"int".equals(type.getName())){
						if(!StringUtil.isEmpty(filter.getValue())&& StringUtil.isNumber(filter.getValue()))
						 criteria.add(Restrictions.eq(filter.getProperty(), new Integer(filter.getValue())));
					}else if (type.equals(java.lang.Long.class)||"long".equals(type.getName())){
						if(!StringUtil.isEmpty(filter.getValue())&& StringUtil.isNumber(filter.getValue()))
							 criteria.add(Restrictions.eq(filter.getProperty(), new Long(filter.getValue())));
					}else{				
						criteria.add(Restrictions.like(filter.getProperty(), filter
								.getValue(), MatchMode.ANYWHERE));
						logger.info("limit Filter:"+filter.getProperty()+" like '"+filter.getValue()+"'");
					}
				}
			}
		}

		return pagedQuery(criteria, limit.getPage(), limit.getCurrentRowsDisplayed());
	}

	private void addDateCriteria(Criteria criteria, Filter filter) {
		Date sdt = null;
		Date edt = null;
		Calendar cal = Calendar.getInstance();
		String dtStr = filter.getValue();
		if(dtStr.indexOf("-")==-1 && dtStr.length()==4){
			cal.set(new Integer(dtStr),0,1,0,0,0);
			sdt = cal.getTime();
			cal.set(new Integer(dtStr),11,31,23,59,59);
			edt = cal.getTime();
		}if(dtStr.indexOf("-")>3){
			String[] arr = dtStr.split("-");
			int year = new Integer(arr[0]);
			int month = 0;
			int day = 1;
			if(arr.length>1){
				month = new Integer(arr[1])-1;
			}
			if(arr.length>2){
				day = new Integer(arr[2]);
				cal.set(year,month,day,23,59,59);
				edt = cal.getTime();
			}else{
				if(month==11){
					cal.set(year+1,1,1,0,0,0);
				}else{
					cal.set(year,month+1,1,0,0,0);
				}
				edt = cal.getTime();
			}
			cal.set(year,month,day,0,0,0);
			sdt = cal.getTime();
		}
		if(sdt!=null && edt!=null){
			criteria.add(Restrictions.between(filter.getProperty(), sdt, edt));
		}
	}

	/**
	 * 根据fieldName获取对象里对应的属性.如果找不到,再用"_" + fieldName进行查找.
	 * 
	 * @param clazz
	 *            被查找的对象
	 * @param fieldName
	 *            属性名
	 * @return 对应的属性,如果找不到就返回null
	 */
	private Field getField(Class clazz, String fieldName) {
		Field field = null;
		try {
			field = BeanUtils.getDeclaredField(clazz, fieldName);
//			field = clazz.getDeclaredField(fieldName);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			try {
				field = BeanUtils.getDeclaredField(clazz, "_" + fieldName);
			} catch (SecurityException e1) {
				e1.printStackTrace();
			} catch (NoSuchFieldException e1) {
				e1.printStackTrace();
			}
		}
		return field;
	}
	
    /**
     * HQL分页查询，默认count的方式
     */
    public Page pagedQuery(String hql, int pageNo, int pageSize, Object... args) {
        return pagedQuery(hql, pageNo, pageSize, COUNT_MODE, args);
    }

    /**
     * HQL分页查询,可以指定具体的模式,
     * 如果是Count方式,须在此层完成hsql的转换与查询。
     */
    public Page pagedQuery(String hql, int pageNo, int pageSize, int mode, Object... args) {
        Assert.hasText(hql);
        Query query = getSession().createQuery(hql);
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i, args[i]);
        }
        if (mode == COUNT_MODE) {
            String countQueryString = " select count (*) " + removeSelect(removeOrders(hql));
            List countlist = getHibernateTemplate().find(countQueryString, args);
    		int totalCount =0;
    		if(countlist.get(0) instanceof Long)
    			totalCount = ((Long) countlist.get(0)).intValue();
    		else 
    		    totalCount = (Integer) countlist.get(0);
            return HqlPage.getPageInstanceByCount(query, pageNo, pageSize, totalCount);
        } else
            return HqlPage.getPageInstance(query, pageNo, pageSize, mode);
    }
	/**
	 * 得到记录总条数
	 * 修正排序被灭掉的错误 Dicky
	 * @return
	 * 2012-9-12 15:11:40
	 */
	public Integer getCount(CriteriaSetup criteriaSetup) {
		Criteria c = this.createCriteria();
		Order inOrder = criteriaSetup.getInOrder();
		criteriaSetup.setInOrder(null);
		criteriaSetup.setup(c);
		criteriaSetup.setInOrder(inOrder);
		return this.getCount(c);
	}
	/**
	 * 统计条数
	 * @param criteria
	 * @return
	 */
	public Integer getCount(Criteria criteria){
		Object rs = criteria.setProjection(Projections.rowCount()).uniqueResult();
		return rs==null?0:(Integer) rs;
	}
	/**
	 * 根据map内的数据得到实体对象
	 * @param data
	 * @return
	 */
	public T  buildEntity(Map<String, Object> data){
		//得到当前对象
		T entity = null;
		
		try{
			
			// 主键名称
			String pkidName = this.getIdName(this.entityClass);
			// 主键类型
			Class pkidType = BeanUtils.getPropertyType(this.entityClass, pkidName);
	
			//主键值
			if(!data.containsKey(pkidName)){
				if(data.containsKey("recordKey"))
					data.put(pkidName, data.get("recordKey"));				
			}
			String pkidVlaue =(String) data.get(pkidName);
			if(pkidVlaue!=null && pkidVlaue.trim().length()>0){//修改
			 Serializable pkid = BeanUtils.convertByClass(pkidType,
					pkidVlaue);
			try{
				entity = this.get(pkid);
			}catch(Exception e){
				//新增时不存在对象
			}
			}//否则，新增记录
			if(entity == null)  
				entity = this.getEntityClass().newInstance();
	
			// 将页面提交的数据转换为实体对象中的属性值
			BeanUtils.forceSetProperty(entity, data);

		}catch(Exception e){
			
			//logger.error(ExceptionUtils.formatStackTrace(e));
			
		}		
		
		return entity;
	}	
	/**
	 * 供页面ajax调用的保存方法
	 * 
	 * @param data
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	public boolean saveByAjax(Map<String, Object> data){

		boolean result = true;

		try{
			
			//得到当前对象
			T entity = buildEntity(data);			

			// 将页面提交的数据转换为实体对象中的属性值
			BeanUtils.forceSetProperty(entity, data);

			if (result && entity !=null) {
				this.saveOrUpdate(entity);
				//this.save(entity);
				
				this.flush();
				
				// 取主键值
				String pkidName = this.getIdName(this.entityClass);
				Object pkid = BeanUtils.getProperty(entity, pkidName);
				data.put(pkidName, pkid);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			result = false;		
			
		}
		
		return result;
	}

	/**
	 * 删除操作
	 * 
	 * @param data
	 * @return 返回为空表示没有删除成功
	 */
	public String removeByAjax(Map<String, Object> data) {

		boolean result = true;
		String id = null;
		T entity = null;
		try {
			/*entity = this.getEntityClass().newInstance();
			// 将页面提交的数据转换为实体对象中的属性值
			BeanUtils.forceSetProperty(entity, data);*/
			// 主键名称
			String pkidName = this.getIdName(this.entityClass);
			// 主键类型
			Class pkidType = BeanUtils.getPropertyType(this.entityClass, pkidName);
			//主键值
			if(!data.containsKey(pkidName)){
				if(data.containsKey("recordKey"))
					data.put(pkidName, data.get("recordKey"));				
			}	
			String pkidVlaue =(String) data.get(pkidName);
			if(pkidVlaue!=null && pkidVlaue.trim().length()>0){//修改
			 Serializable pkid = BeanUtils.convertByClass(pkidType,
					pkidVlaue);
			  this.removeById(pkid);
			  id=pkidVlaue;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			result = false;
		}

		/*String id = null;
		if (result) {
			this.remove(entity);
			
			String idName = this.getIdName(getEntityClass());
			id = (String) data.get(idName);
		}*/

		return id;
	}	
	/**
	 * 取得Entity的Criteria.
	 * 
	 * @see HibernateGenericDao#createCriteria(Class,Criterion[])
	 */
	public Criteria createCriteria(Criterion... criterions) {
		return createCriteria(getEntityClass(), criterions);
	}	
    /**
     * 去除select 子句，未考虑union的情况
     */
    private static String removeSelect(String hql) {
        Assert.hasText(hql);
        int beginPos = hql.toLowerCase().indexOf("from");
        Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
        return hql.substring(beginPos);
    }
    
    /**
     * 去除orderby 子句
     */
    private static String removeOrders(String hql) {
        Assert.hasText(hql);
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(hql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }    
	/**
	 * 通过pojo中的属性名得到真实的字段名称 这是模糊查询时间字段的时候需要用到 如果字段和属性名不同则请在子类中重构该方法
	 * 
	 * @param propertyName
	 * @return
	 */
	protected String getColumnByProperty(String propertyName) {
		return propertyName;
	}

	/**
	 * 通过pojo中的属性名得到时间格式 这是模糊查询时间字段的时候需要用到 如果与默认格式不同则请在子类中重构该方法
	 * 
	 * @param propertyName
	 * @return
	 */
	protected String getDateFormatByProperty(String propertyName) {
		return "yyyy-MM-dd hh24:mi";
	}

	public Integer getCount(String sql) { 
		String countQueryString = " select count (*)  " + removeSelect(removeOrders(sql));
		List countlist = getSession().createSQLQuery(countQueryString).list();
		int totalCount =0;
		if(countlist.get(0) instanceof Integer)
			totalCount = ((Integer) countlist.get(0)).intValue(); 
		
		return totalCount;
	}
	
	public Page findECPage(Limit limit, String hql, Object... args) throws Exception {
		Assert.hasText(hql);
		 
		if (limit.getTotalRows() < 1) return new Page();
		//实际查询返回分页对象
		int startIndex = Page.getStartOfPage(limit.getPage(), limit.getCurrentRowsDisplayed());
		List list = getSession().createSQLQuery(hql).setFirstResult(startIndex).setMaxResults(limit.getCurrentRowsDisplayed()).list();
		 	
		return new Page(startIndex, limit.getTotalRows(), limit.getCurrentRowsDisplayed(), list);
	}
 
	/**
	 * 获得hql执行指定范围数据
	 * @param hql
	 * @param startIndex
	 * @param maxResult
	 * @return
	 */
	public List getObjects(Class<T> entityClass,int startIndex,int maxResult, Criterion... criterions){
		
		Criteria criteria=createCriteria(entityClass,criterions);
		try{
		criteria.addOrder(Order.desc("id"));
		}catch(Exception e){e.printStackTrace();}
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(maxResult);
		return criteria.list();		 
	}

		/**
		 * 统计条数
		 * @param criteria
		 * @return
		 */
		public Integer getCount(Class<T> entityClass, Criterion... criterions){
			Criteria criteria=createCriteria(entityClass,criterions);
			return getCount(criteria);
	 }
	/**
	 * 获得的序号 yyMMddHHmmss
	 * @return
	 */
	public synchronized Long getSequence(){	
	  	SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
	  	String seq= format.format(new Date());
		//String seq=UtilDateTime.getDateStrNumber(new Date());//前20080505163212--yyyyMMddHHmmss

		seq+=RandomStringUtils.randomNumeric(3);//四位随机数
		return new Long(seq);
	}	
	
    public Long getLoginerSiteId(){
    	Loginer loginer = (Loginer)ServletActionContext.getContext().getSession().get(Loginer.LOGININFO_SESSION);
    		if(loginer != null && loginer.getSiteId() >0)
    			return loginer.getSiteId();
    		else return 0l;
    
    }
}
