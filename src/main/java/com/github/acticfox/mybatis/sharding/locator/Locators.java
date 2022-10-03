package com.github.acticfox.mybatis.sharding.locator;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * 类Locators.java的实现描述：
 * 
 * <pre>
 * Locator 单例帮助类
 * 
 * <pre>
 * @author fanyong.kfy 2017年2月22日 上午11:11:37
 */
public enum Locators {


	instance;

	private static final Cache<String, Locator> locateCached = CacheBuilder.newBuilder().build();

	public Locator takeLocator(final String rule) {
		try {
			return locateCached.get(rule, new Callable<Locator>() {
				@Override
				public Locator call() throws Exception {
					return new GroovyLocator(rule);
				}
			});
		} catch (ExecutionException e) {
			return null;
		}
	}

}
