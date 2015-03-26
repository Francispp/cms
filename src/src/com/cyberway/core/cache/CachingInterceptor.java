package com.cyberway.core.cache;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.Assert;

/**
 * 利用AOP截获方法的执行.直接使用Cache作为方法的返回值,而无需实际执行方法.
 * 暂时使用EhCache作为Cache方案.
 * 如果该函数相关的数据库内容被改变,将由{@link FlushingInterceptor}进行刷新
 * todo:cacheKey需自动添加参数,加工后才能作为唯一key
 *
 * @author caiw
 * @see FlushingInterceptor
 */
public class CachingInterceptor implements MethodInterceptor {

    private CacheUtil cacheUtil;
    private String cacheKey;

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public void setCacheUtil(CacheUtil cacheUtil) {
        this.cacheUtil = cacheUtil;
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        Assert.hasText(cacheKey);
        Assert.notNull(cacheUtil);

        Object result = cacheUtil.get(cacheKey);
        //如果函数返回结果不在Cache中,将执行函数并将结果放入eache
        if (result == null) {
            result = invocation.proceed();
            cacheUtil.put(cacheKey,result);
        }
        return result;
    }

}

