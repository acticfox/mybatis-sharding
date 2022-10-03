package com.github.acticfox.mybatis.sharding.ds;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.github.acticfox.mybatis.sharding.context.RouteDataSourceContext;

/**
 * 类ShardDataSource.java的实现描述：
 * 
 * <pre>
 * 支持选择具体数据源的数据源,基于spring的routing data source.
 * 
 * <pre>
 * @author fanyong.kfy 2017年2月22日 上午11:08:36
 */
public class ShardDataSource extends AbstractRoutingDataSource {

	/**
	 * 获取当前要使用的数据源key.
	 * 
	 * @return
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return RouteDataSourceContext.getRouteKey();
	}

}
