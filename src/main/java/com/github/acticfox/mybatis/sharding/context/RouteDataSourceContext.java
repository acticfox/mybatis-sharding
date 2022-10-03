package com.github.acticfox.mybatis.sharding.context;

/**
 * 类RouteDataSourceContext.java的实现描述：
 * 
 * <pre>
 * 数据源路由上下文
 * 
 * <pre>
 * @author fanyong.kfy 2017年2月22日 上午10:32:31
 */
public class RouteDataSourceContext {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static String getRouteKey() {
        return contextHolder.get();
    }

    public static void setRouteKey(String routeKey) {
        contextHolder.set(routeKey);
    }

    public static void clearRouteKey() {
        contextHolder.remove();
    }

}
