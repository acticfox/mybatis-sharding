package com.github.acticfox.mybatis.sharding.context;

import com.github.acticfox.mybatis.sharding.config.ExecutionConfig;

/**
 * 类ExecuteInfoContext.java的实现描述：
 * 
 * <pre>
 * ExecutionConfig上下文，放到ThreadLocal存储
 * 
 * <pre>
 * @author fanyong.kfy 2017年2月22日 上午10:30:00
 */
public class ExecuteInfoContext {

    private static final ThreadLocal<ExecutionConfig> contextHolder = new ThreadLocal<ExecutionConfig>();

    public static ExecutionConfig getExecuteInfo() {
        return contextHolder.get();
    }

    public static void setExecuteInfo(ExecutionConfig executeInfo) {
        contextHolder.set(executeInfo);
    }

    public static void clearExecuteInfo() {
        contextHolder.remove();
    }

}
