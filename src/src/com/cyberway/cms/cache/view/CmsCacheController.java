package com.cyberway.cms.cache.view;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.cyberway.cms.cache.domain.CmsCache;
import com.cyberway.cms.cache.service.CmsCacheService;
import com.cyberway.cms.document.service.HibernateSynchronizer;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

/**
 * com.cyberway.cms.cache.view.CmsCacheController
 * 
 * @author Janice Yang
 * 
 * @createTime 2012-2-3 下午2:57:09
 * 
 * @Description:
 * 
 */
public class CmsCacheController extends BaseBizController<CmsCache> {
	private CmsCacheService	service	= (CmsCacheService) getServiceById("cmsCacheService");

	@Override
	protected EntityDao<CmsCache> getService() {
		return service;
	}

	@Override
	public String execute() throws Exception {
		return list();
	}

	@Override
    public String saveOrUpdate() throws Exception {
		domain=getService().saveOrUpdate(domain);
		addActionMessage("保存成功!");
		return EDIT_RESULT;
    }
	
	/**
	 * 初始化缓存
	 */
	public void initCache() {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		String msgStr = "";

		if (!StringUtil.isEmpty(keys)) {
			CmsCache cache = null;
			List<Long> list = StringUtil.splitToList(keys, ",");
			try {
				for (Long id : list) {//清除缓存
					cache = service.get(id);
					Object object = getServiceById(cache.getManageClassPath());
					Method method = object.getClass().getMethod(cache.getRemoveAllMethodName().trim(), new Class[] {});
					// 执行该方法
					method.invoke(object, new Object[] {});
				}
				
				for (Long id : list) {//重新建立缓存
					cache = service.get(id);
					Object object = getServiceById(cache.getManageClassPath());
					Method method = object.getClass().getMethod(cache.getInitMethodName().trim(), new Class[] {});
					// 执行该方法
					method.invoke(object, new Object[] {});
				}
				msgStr = "1";
			} catch (Exception e) {
				e.printStackTrace();
				msgStr = "0";
			}
		} else {
			msgStr = "0";
		}

		try {
			response.getWriter().print(msgStr);
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 清除缓存
	 */
	public void removeAllCache() {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		String msgStr = "";

		if (!StringUtil.isEmpty(keys)) {
			CmsCache cache = null;
			List<Long> list = StringUtil.splitToList(keys, ",");
			try {
				for (Long id : list) {
					cache = service.get(id);
					Object object = getServiceById(cache.getManageClassPath());
					Method method = object.getClass().getMethod(cache.getRemoveAllMethodName().trim(), new Class[] {});
					// 执行该方法
					method.invoke(object, new Object[] {});
				}
				msgStr = "1";
			} catch (Exception e) {
				e.printStackTrace();
				msgStr = "0";
			}
		} else {
			msgStr = "0";
		}

		try {
			response.getWriter().print(msgStr);
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查看所有缓存
	 * 
	 * @return
	 */
	public String viewAllCache() {
		get();
		Object object = getServiceById(domain.getManageClassPath());
		try {
			Method method = object.getClass().getMethod(domain.getGetAllMethodName(), new Class[] {});
			List<String> keys = (List<String>) method.invoke(object, new Object[] {});
			setItems(keys);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "viewAllCacheList";
	}

	/**
	 * 查找缓存
	 * 
	 * @return
	 */
	public String searchCache() {
		String searchKey = (String) getParameterValue("key");
		if (StringUtils.trimToNull(searchKey) != null) {
			get();
			try {
				Object object = getServiceById(domain.getManageClassPath());
				Method method = object.getClass().getMethod(domain.getGetMethodName(), new Class[] { String.class });
				Element element = (Element) method.invoke(object, new Object[] { searchKey });
				if (element != null) {
					List<String> list = new ArrayList<String>();
					list.add((String) element.getKey());
					setItems(list);
				}
				return "viewAllCacheList";
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return "viewAllCacheList";
		}
		return "viewAllCacheList";
	}

	/**
	 * 查看单个缓存的内容
	 * 
	 * @return
	 */
	public String viewCache() {
		HibernateSynchronizer h = (HibernateSynchronizer) getServiceById("fulltext.synchronizer");
		h.run();
		
		String key = (String) getParameterValue("key");
		get();
		try {
			Object object = getServiceById(domain.getManageClassPath());
			Method method = object.getClass().getMethod(domain.getGetMethodName(), new Class[] { String.class });
			Element element = (Element) method.invoke(object, new Object[] { key });

			setItems(toObjectString(element.getValue()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "viewCache";
	}

	/**
	 * 将一个对象的所有属性名和属性值放到List中
	 * 
	 * @param o
	 * @return
	 */
	private List<CacheValueItem> toObjectString(Object o) {
		if (null == o) {
			return null;
		}
		
		CacheValueItem v = null;

		List<CacheValueItem> list = new ArrayList<CacheValueItem>();

		if (o instanceof String) {
			v = new CacheValueItem();
			v.setType("String");
			v.setArgName("value");
			v.setValue(o.toString());
			list.add(v);
		} else {
			Class<?> clazz = o.getClass();
			Method[] methods = clazz.getMethods();

			for (Method method : methods) {
				v = new CacheValueItem();
				String mname = method.getName();
				Class<?> type = method.getReturnType();
				if (mname.length() <= 3 || mname.equals("getClass")) {
					continue;
				}

				if (mname.substring(0, 3).equals("get")) {
					try {
						Object return0 = method.invoke(o, new Object[] {});
						mname = mname.replaceFirst("get", "");
						mname = StringUtils.lowerCase(mname.charAt(0) + "") + mname.substring(1);
						v.setType(type.getSimpleName());
						v.setArgName(mname);

						if (null == type.getPackage() || type.getPackage().equals(Package.getPackage("java.lang"))) {
							if(return0 != null){
								v.setValue(return0.toString());
							}
						} else {
							// c.append(" = ").append(toObjectString(returnO, dep + 1));
						}

						list.add(v);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * com.cyberway.cms.cache.view.CacheValueItem
	 *
	 * @author Janice Yang
	 *
	 * @createTime 2012-3-5 上午11:48:51 
	 *
	 * @Description:缓存内容中的具体数据
	 *
	 */
	public class CacheValueItem implements Serializable {
		/**
		 * 类型
		 */
		private String type;
		
		/**
		 * 变量名
		 */
		private String argName;
		
		private String flag1 = "=";
		private String flag2 = ";";
		
		/**
		 * 值
		 */
		private String value;

		public String getType() {
        	return type;
        }

		public void setType(String type) {
        	this.type = type;
        }

		public String getFlag1() {
        	return flag1;
        }

		public void setFlag1(String flag1) {
        	this.flag1 = flag1;
        }

		public String getFlag2() {
        	return flag2;
        }

		public void setFlag2(String flag2) {
        	this.flag2 = flag2;
        }

		public String getValue() {
        	return value;
        }

		public void setValue(String value) {
        	this.value = value;
        }

		public String getArgName() {
        	return argName;
        }

		public void setArgName(String argName) {
        	this.argName = argName;
        }
	}

}
