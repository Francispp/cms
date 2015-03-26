package com.cyberway.core.cache;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.util.Assert;

/**
 * 有些方法执行时,对({@link CachingInterceptor}所缓存的方法结果有影响,需要flushing 缓存.
 *
 * @author caiw
 * @see CachingInterceptor
 */
public class FlushingInterceptor implements AfterReturningAdvice {

    private CacheUtil cacheUtil;
    /**
     * 待删除的keyNames
     */
    private String[] cacheKeys;

    public void setCacheUtil(CacheUtil cacheUtil) {
        this.cacheUtil = cacheUtil;
    }

    public void setCacheKeys(String[] cacheKeys) {
        this.cacheKeys = cacheKeys;
    }

    public void afterReturning(Object returnValueObject, Method method,
                               Object[] args, Object target) throws Throwable {
        Assert.notNull(cacheUtil);
        Assert.notEmpty(cacheKeys);
        for (String cacheKey : cacheKeys) {
            if (cacheUtil.exist(cacheKey))
            	cacheUtil.remove(cacheKey);
        }
    }
}

