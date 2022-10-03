package com.github.acticfox.mybatis.sharding.convert;

import java.util.regex.Pattern;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;

/**
 * 类DeleteSqlConverter.java的实现描述：
 * 
 * <pre>
 * delete sql解析转换器
 * 
 * <pre>
 * @author fanyong.kfy 2017年2月23日 上午11:51:13
 */
public class DeleteSqlConverter extends AbstractSqlConverter {

    @Override
    protected Statement doConvert(Statement statement, String tableSuffix, Pattern includePattern) {
        if (!(statement instanceof Delete)) {
            throw new IllegalArgumentException("The argument statement must is instance of Delete.");
        }
        Delete delete = (Delete) statement;

        String name = delete.getTable().getName();

        if (includePattern.matcher(name).find()) {
            delete.getTable().setName(this.convertTableName(name, tableSuffix));
        }

        return delete;
    }

}
