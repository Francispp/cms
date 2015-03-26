package com.cyberway.core.ectable;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ecside.core.context.Context;
import org.ecside.core.context.HttpServletRequestContext;
import org.ecside.table.limit.Limit;
import org.ecside.table.limit.Sort;
import org.ecside.util.RequestUtil;



/**
 * 辅助ExtremeTable获取分页信息的Util类
 *
 * @author caiw
 */
public class ExtremeTablePage {


    static public Limit getLimit(HttpServletRequest request) {
    	if(request == null)
    		return null;
        return getLimit(request, Constants.DEFAULT_PAGE_SIZE);
    }

    /**
     * 从request构造Limit对象实例.
     * Limit的构造流程比较不合理，为了照顾export Excel时忽略信息分页，导出全部数据
     * 因此流程为程序先获得total count, 再使用total count 构造Limit，再使用limit中的分页数据查询分页数据
     * 而page函数是在同一步的，无法拆分，再考虑到首先获得的totalCount
     */
    static public Limit getLimit(HttpServletRequest request, int defautPageSize) {
        Context context = new HttpServletRequestContext(request);
/*        LimitFactory limitFactory =null;// new TableLimitFactory(context);
        TableLimit limit = new TableLimit(limitFactory);*/
        Limit limit =RequestUtil.getLimit(request);
        limit.setRowAttributes(999999999, defautPageSize);
        return limit;
    }

    /**
     * 将Limit中的排序信息转化为Map{columnName,升序/降序}
     */
    static public Map getSort(Limit limit) {
        Map sortMap = new HashMap();
        if (limit != null) {
            Sort sort = limit.getSort();
            if (sort != null && sort.isSorted()) {
                sortMap.put(sort.getProperty(), sort.getSortOrder());
            }
        }
        return sortMap;
    }
    }
