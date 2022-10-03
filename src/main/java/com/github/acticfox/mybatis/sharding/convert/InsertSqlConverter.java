/**
 * 
 */
package com.github.acticfox.mybatis.sharding.convert;

import java.util.regex.Pattern;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;

/**
 * 类InsertSqlConverter.java的实现描述：
 * 
 * <pre>
 * insert sql解析转换器
 * 
 * <pre>
 * @author fanyong.kfy 2017年2月23日 上午11:52:39
 */
public class InsertSqlConverter extends AbstractSqlConverter {

    @Override
    protected Statement doConvert(Statement statement, String suffix, Pattern includePattern) {
        if (!(statement instanceof Insert)) {
            throw new IllegalArgumentException("The argument statement must is instance of Insert.");
        }
        Insert insert = (Insert) statement;

        String name = insert.getTable().getName();

        if (includePattern.matcher(name).find()) {
            insert.getTable().setName(this.convertTableName(name, suffix));
        }

        return insert;
    }

}
