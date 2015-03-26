package com.cyberway.core.dao.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cyberway.core.utils.BeanUtil;

/**
 * 查询回调类,组织查询条件
 * 
 * @author caiw
 *
 */
public class CriteriaSetup {
    
	/**
	 * 由业务传入一个简单查询
     * 由Restrictions类实现查询条件
	 */
	private Criterion inCriterion;
	private Order inOrder;
	private List<Order> inOrders;
	
	
    /**
     * 由业务传入多个查询条件
     * 由Restrictions类实现查询条件封装到List对象中
     */    
    private List<Criterion> addCriterions;
    /**  简单的过虑条件 */
    Map<String,Object> filters = new HashMap<String,Object>();    
	
    public void setup(Criteria criteria,Object... args) {
    	
        //得到列表传递条件
        if (filters != null && !filters.isEmpty()) {
            Set<String> keys = filters.keySet();
            for (String key : keys) {
                Object value = filters.get(key);
                if(value==null)
                  continue;
                if(BeanUtil.isCollection(value.getClass())){
                	criteria.add(Restrictions.in(key, (java.util.Collection)value));
                }else
                if (StringUtils.isNotBlank(value.toString()))
                    criteria.add(Restrictions.eq(key, value));
            }
        }
        //简单查询条件
        Criterion inc = getInCriterion();
        if(inc != null){
        	criteria.add(inc);
        }
        
        //多个查询条件
        List<Criterion> lsCris = getAddCriterions();
        if(lsCris != null && lsCris.size() > 0){
            for(Criterion criter : lsCris){
                criteria.add(criter);
            }
        }
        //简单排序
        if(getInOrder()!=null){
        	criteria.addOrder(getInOrder());
        }
        
      
       
        
        //简单排序   为列表标签和首页列表标签支持多字段排序
        if(getInOrders()!=null){ 
        	 if(args != null && args.length > 0){
             	Map	sortMap = (Map)args[0];  
 				for (Order inOrder : inOrders) {
 					boolean isconstans = false;
 					for (Object o : sortMap.keySet()) {
 	     				String fieldName = o.toString(); 
	 					if(fieldName.equals(inOrder.toString().substring(0,inOrder.toString().indexOf(" ")))){
	 						isconstans = true; break;
	 					}
 					}
 					
 					if(!isconstans){
 						criteria.addOrder(inOrder);
 					}
 				}
     		}else{
     			for (Order inOrder : inOrders) { 
            		criteria.addOrder(inOrder);
    			}
     		}
        }
    }

	public Criterion getInCriterion() {
		return inCriterion;
	}

	public void setInCriterion(Criterion inCriterion) {
		this.inCriterion = inCriterion;
	}

    public List<Criterion> getAddCriterions() {
        return addCriterions;
    }

    public void setAddCriterions(List<Criterion> addCriterions) {
        this.addCriterions = addCriterions;
    }

	public Order getInOrder() {
		return inOrder;
	}

	public void setInOrder(Order inOrder) {
		this.inOrder = inOrder;
	}

	public Map<String, Object> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}
	public void addFilter(String key,Object value){
		this.getFilters().put(key, value);
	}

	public List<Order> getInOrders() {
		return inOrders;
	}

	public void setInOrders(List<Order> inOrders) {
		this.inOrders = inOrders;
	} 
}

