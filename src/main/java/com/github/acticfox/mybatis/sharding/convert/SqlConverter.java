/**
 * 
 */
package com.github.acticfox.mybatis.sharding.convert;

import net.sf.jsqlparser.statement.Statement;

/**
 * 类SqlConverter.java的实现描述：
 * 
 * <pre>
 * sql转换器接口
 * 
 * <pre>
 * @author fanyong.kfy 2017年2月23日 上午11:53:26
 */
public interface SqlConverter {
    /**
     * 对sql进行修改
     * 
     * @param statement
     * @param suffix
     * @param includePattern
     * @return
     */
    String convert(Statement statement, String suffix, String includePattern);
}
