package com.github.acticfox.mybatis.sharding.convert;

import java.util.List;
import java.util.regex.Pattern;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.update.Update;

/**
 * 类UpdateSqlConverter.java的实现描述：
 * 
 * <pre>
 * update sql解析转换器
 * 
 * <pre>
 * @author fanyong.kfy 2017年2月23日 上午11:54:24
 */
public class UpdateSqlConverter extends AbstractSqlConverter {

    @Override
    protected Statement doConvert(Statement statement, String suffix, Pattern includePattern) {
        if (!(statement instanceof Update)) {
            throw new IllegalArgumentException("The argument statement must is instance of Update.");
        }
        Update update = (Update) statement;
        List<Table> tableList = update.getTables();
        for (Table table : tableList) {
            if (includePattern.matcher(table.getName()).find()) {
                table.setName(this.convertTableName(table.getName(), suffix));
            }
        }

        return update;
    }

}
