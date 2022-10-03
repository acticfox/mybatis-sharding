/*
 * Copyright 2017 Aliyun.com All right reserved. This software is the
 * confidential and proprietary information of Aliyun.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Aliyun.com .
 */
package com.github.acticfox.mybatis.sharding.plugin;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.github.acticfox.mybatis.sharding.config.ExecutionConfig;
import com.github.acticfox.mybatis.sharding.config.ExecutionConfigBuilder;
import com.github.acticfox.mybatis.sharding.context.ExecuteInfoContext;

/**
 * 类ShraddingInterceptor.java的实现描述：
 * 
 * <pre>
 * 数据库、表分区路由规则解析
 * 
 * <pre>
 * @author fanyong.kfy 2017年2月21日 下午6:55:56
 */
public class ShradConfigInterceptor implements MethodInterceptor {

    /*
     * (non-Javadoc)
     * @see
     * org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept
     * .MethodInvocation)
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Class<?> clz = invocation.getThis().getClass();
        ExecutionConfig executeInfo = ExecutionConfigBuilder.instance.build(clz, method, invocation.getArguments());

        try {
            ExecuteInfoContext.setExecuteInfo(executeInfo);
            return invocation.proceed();
        } finally {
            ExecuteInfoContext.clearExecuteInfo();
        }
    }
}
