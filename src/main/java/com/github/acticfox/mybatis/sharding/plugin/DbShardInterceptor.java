package com.github.acticfox.mybatis.sharding.plugin;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.acticfox.mybatis.sharding.config.DataSourceConfig;
import com.github.acticfox.mybatis.sharding.config.ExecutionConfig;
import com.github.acticfox.mybatis.sharding.context.ExecuteInfoContext;
import com.github.acticfox.mybatis.sharding.context.RouteDataSourceContext;
import com.github.acticfox.mybatis.sharding.locator.Locator;
import com.github.acticfox.mybatis.sharding.locator.Locators;

/**
 * 类DbShardInterceptor.java的实现描述：
 * 
 * <pre>
 * 数据库路由拦截器处理
 * 
 * <pre>
 * @author fanyong.kfy 2017年2月22日 上午11:20:28
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
		@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class})})
public class DbShardInterceptor implements Interceptor {

	private static final Logger log = LoggerFactory.getLogger(DbShardInterceptor.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		String routingKey = routeKey();
		if (isNullOrEmpty(routingKey)) {
			return invocation.proceed();
		}
		RouteDataSourceContext.setRouteKey(routingKey);

		return invocation.proceed();
	}

	private String routeKey() {
		ExecutionConfig executeInfo = ExecuteInfoContext.getExecuteInfo();
		if (executeInfo == null) {
			return null;
		}
		DataSourceConfig dataSourceConfig = executeInfo.getDataSourceConfig();
		if (dataSourceConfig == null) {
			return null;
		}

		Locator locator = Locators.instance.takeLocator(dataSourceConfig.getRule());
		String dbSuffix = locator.locate(dataSourceConfig.getParams());
		if (isNullOrEmpty(dbSuffix)) {
			return null;
		}

		return dataSourceConfig.getDataSourceName() + "_" + dbSuffix;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
	 */
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties)
	 */
	@Override
	public void setProperties(Properties properties) {

	}

}
