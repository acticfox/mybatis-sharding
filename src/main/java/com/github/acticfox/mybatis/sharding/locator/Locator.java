package com.github.acticfox.mybatis.sharding.locator;

import java.util.Map;

/**
 * 类Locator.java的实现描述：
 * 
 * <pre>
 * sql路由解析器接口定义
 * 
 * <pre>
 * @author fanyong.kfy 2017年2月22日 上午11:10:27
 */
public interface Locator {

    String locate(Map<String, Object> locateParam);

}
