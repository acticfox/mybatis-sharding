package com.github.acticfox.mybatis.sharding.config;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import com.github.acticfox.mybatis.sharding.annotation.DbShard;
import com.github.acticfox.mybatis.sharding.annotation.DbShardWith;
import com.github.acticfox.mybatis.sharding.annotation.TableShard;
import com.github.acticfox.mybatis.sharding.annotation.TableShardWith;
import com.github.acticfox.mybatis.sharding.util.ReflectUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

/**
 * 类ExecutionConfigBuilder.java的实现描述：
 * 
 * <pre>
 * 数据源和表的配置构造器
 * 
 * <pre>
 * @author fanyong.kfy 2017年2月20日 上午10:21:43
 */
public enum ExecutionConfigBuilder {

	instance;

	public ExecutionConfig build(Class<?> mapperClazz, Method mapperMethod, Object[] executeParam) {

		if (mapperMethod == null || ArrayUtils.isEmpty(mapperMethod.getParameterTypes())) {
			return null;
		}
		DbShard dbAnnotation = mapperClazz.getAnnotation(DbShard.class);
		TableShard tableAnnotation = mapperClazz.getAnnotation(TableShard.class);
		if (tableAnnotation == null && dbAnnotation == null) {
			return null;
		}

		DbShardWith dbShardWith = mapperClazz.getAnnotation(DbShardWith.class);
		DataSourceConfig dataSourceConfig = null;
		if (dbAnnotation != null) {
			dataSourceConfig = buildDataSourceInfo(mapperMethod, dbAnnotation, dbShardWith, executeParam);
		}
		TableConfig tableConfig = null;
		if (tableAnnotation != null) {
			tableConfig = buildTableInfo(mapperMethod, tableAnnotation, executeParam);
		}

		return new ExecutionConfig(dataSourceConfig, tableConfig);

	}

	private TableConfig buildTableInfo(Method mapperMethod, TableShard tableAnnotation, Object[] executeParam) {
		Map<String, Object> params = Maps.newHashMap();
		Class<?>[] parameterTypes = mapperMethod.getParameterTypes();
		Annotation[][] parameterAnnotations = mapperMethod.getParameterAnnotations();
		for (int i = 0; i < parameterTypes.length; i++) {
			Object param = executeParam[0];
			Annotation[] annotations = parameterAnnotations[i];
			for (Annotation annotation : annotations) {
				TableShardWith tableShardWith = null;
				if (annotation instanceof TableShardWith) {
					tableShardWith = (TableShardWith) annotation;
				}
				if (tableShardWith == null) continue;
				// 单参数处理
				if (param.getClass().isPrimitive() || param instanceof Integer || param instanceof Boolean
						|| param instanceof String || param instanceof Long) {
					params.put(tableShardWith.props()[0], param);
				} else if (param instanceof Map) {
					for (String prop : tableShardWith.props()) {
						params.put(prop, Preconditions.checkNotNull(((Map) param)).get(prop));
					}
				} else {
					// 包装类非原生带内嵌参数的处理.
					for (String prop : tableShardWith.props()) {
						params.put(prop, Preconditions.checkNotNull(ReflectUtil.getFieldValue(param, prop)));
					}
				}

			}
		}

		TableConfig tableConfig = new TableConfig();
		tableConfig.setTablePattern(tableAnnotation.tablePattern().toLowerCase());
		tableConfig.setRule(tableAnnotation.rule());
		tableConfig.setParams(params);

		return tableConfig;
	}

	private Map<String, Object> buildDataSourceParam(DbShardWith dbShardWith, Object shardParam) {
		Map<String, Object> params = Maps.newHashMap();
		// 单参数处理
		if (shardParam.getClass().isPrimitive() || shardParam instanceof Integer || shardParam instanceof Boolean
				|| shardParam instanceof String || shardParam instanceof Long) {
			params.put(dbShardWith.props()[0], shardParam);
		} else if (shardParam instanceof Map) {
			for (String prop : dbShardWith.props()) {
				params.put(prop, Preconditions.checkNotNull(((Map) shardParam)).get(prop));
			}
		} else {
			// 包装类非原生带内嵌参数的处理.
			for (String prop : dbShardWith.props()) {
				params.put(prop, Preconditions.checkNotNull(ReflectUtil.getFieldValue(shardParam, prop)));
			}
		}

		return params;
	}

	private DataSourceConfig buildDataSourceInfo(Method mapperMethod, DbShard dbShardAnnotation,
			DbShardWith dbShardWith, Object[] executeParam) {
		Class<?>[] parameterTypes = mapperMethod.getParameterTypes();
		Map<String, Object> params = Maps.newHashMap();
		if (dbShardWith != null) {
			params = buildDataSourceParam(dbShardWith, executeParam[0]);
		}
		Annotation[][] parameterAnnotations = mapperMethod.getParameterAnnotations();
		for (int i = 0; i < parameterTypes.length; i++) {
			Annotation[] annotations = parameterAnnotations[i];
			for (Annotation annotation : annotations) {
				if (annotation instanceof DbShardWith) {
					dbShardWith = (DbShardWith) annotation;
				}
				if (dbShardWith == null) continue;

				params = buildDataSourceParam(dbShardWith, executeParam[0]);
			}
		}

		DataSourceConfig dataSourceConfig = new DataSourceConfig();
		dataSourceConfig.setDataSourceName(dbShardAnnotation.dbKey());
		dataSourceConfig.setRule(dbShardAnnotation.rule());
		dataSourceConfig.setParams(params);

		return dataSourceConfig;
	}

}
