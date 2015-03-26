package com.cyberway.core.dao.support;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;

import com.cyberway.core.dao.HibernateEntityDao;

/**
 * 使用Hql查询的的分页查询类.
 * 支持执行Count查询, 利用JDBC的SrollLast()功能 和取出全部数据统计size三种方式取得总记录条数
 * @author Marc Huang
 */
public class HqlPage {

    /**
     * 默认以getCount方式创建Page
     */
    public static Page getPageInstance(Query query, int pageNo, int pageSize) {
        return getPageInstance(query, pageNo, pageSize, HibernateEntityDao.SCROLL_MODE);
    }


    /**
     * 使用不同模式创建Page.
     */
    public static Page getPageInstance(Query query, int pageNo, int pageSize, int mode) {

        if (mode == HibernateEntityDao.SCROLL_MODE)
            return HqlPage.getPageInstanceByScroll(query, pageNo, pageSize);
        if (mode == HibernateEntityDao.LIST_MODE)
            return HqlPage.getPageInstanceByList(query, pageNo, pageSize);
        if (mode == HibernateEntityDao.COUNT_MODE)
            throw new IllegalArgumentException("Error Mode,you should use getPageInstance(Query query, int pageNo, int pageSize, int mode,int totalCount)");
        return null;
    }

    public static Page getPageInstanceByCount(Query query, int pageNo, int pageSize, int totalCount) {
        return getPageResult(query, totalCount, pageNo, pageSize);
    }

    protected static Page getPageInstanceByScroll(Query query, int pageNo, int pageSize) {
        ScrollableResults scrollableResults = query.scroll(ScrollMode.SCROLL_SENSITIVE);
        scrollableResults.last();
        int totalCount = scrollableResults.getRowNumber() + 1;
        return getPageResult(query, totalCount + 1, pageNo, pageSize);
    }

    protected static Page getPageInstanceByList(Query query, int pageNo, int pageSize) {
        query.scroll(ScrollMode.FORWARD_ONLY);
        int totalCount = query.list().size();
        return getPageResult(query, totalCount, pageNo, pageSize);
    }

    private static Page getPageResult(Query q, int totalCount, int pageNo, int pageSize) {
        if (totalCount < 1) return new Page();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List list = q.setFirstResult(startIndex).setMaxResults(pageSize).list();

        return new Page(startIndex, totalCount, pageSize, list);
    }

}

