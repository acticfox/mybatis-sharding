package com.github.acticfox.mybatis.sharding.plugin;

import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.Connection;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.acticfox.mybatis.sharding.config.ExecutionConfig;
import com.github.acticfox.mybatis.sharding.config.TableConfig;
import com.github.acticfox.mybatis.sharding.context.ExecuteInfoContext;
import com.github.acticfox.mybatis.sharding.convert.SqlConverterFactory;
import com.github.acticfox.mybatis.sharding.locator.Locator;
import com.github.acticfox.mybatis.sharding.locator.Locators;
import com.github.acticfox.mybatis.sharding.util.Reflections;
import com.google.common.annotations.VisibleForTesting;

/**
 * 类TableShardInterceptor.java的实现描述：
 * 
 * <pre>
 * 表分区拦截器
 * 
 * <pre>
 * @author fanyong.kfy 2017年2月20日 上午10:50:41
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class TableShardInterceptor implements Interceptor {

	private static final Logger log = LoggerFactory.getLogger(TableShardInterceptor.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();

		BoundSql boundSql = statementHandler.getBoundSql();

		String sql = tryConvertSql(boundSql);
		log.info("convert sql:{}", sql);
		if (StringUtils.isNotEmpty(sql)) {
			Reflections.setFieldValue(boundSql, "sql", sql);
		}

		return invocation.proceed();

	}

	@VisibleForTesting
	String tryConvertSql(BoundSql boundSql) {

		ExecutionConfig executeInfo = ExecuteInfoContext.getExecuteInfo();

		if (executeInfo == null) {
			return null;
		}

		TableConfig tableConfig = executeInfo.getTableConfig();
		if (tableConfig == null) {
			return null;
		}

		Locator locator = Locators.instance.takeLocator(checkNotNull(tableConfig.getRule()));
		String targetSuffix = locator.locate(tableConfig.getParams());

		SqlConverterFactory converterFactory = SqlConverterFactory.getInstance();
		return converterFactory.convert(boundSql.getSql(), targetSuffix, checkNotNull(tableConfig.getTablePattern()));

	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}

}
