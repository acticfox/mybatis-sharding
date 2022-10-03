package com.github.acticfox.mybatis.sharding.convert;

import java.util.List;
import java.util.regex.Pattern;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.LateralSubSelect;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.TableFunction;
import net.sf.jsqlparser.statement.select.ValuesList;
import net.sf.jsqlparser.statement.select.WithItem;

import org.apache.commons.collections.CollectionUtils;

/**
 * 类SelectSqlConverter.java的实现描述：
 * 
 * <pre>
 * select sql解析转换器
 * 
 * <pre>
 * @author fanyong.kfy 2017年2月23日 上午11:53:06
 */
public class SelectSqlConverter extends AbstractSqlConverter {

    @Override
    protected Statement doConvert(Statement statement, String tableSuffix, Pattern includePattern) {

        if (!(statement instanceof Select)) {
            throw new IllegalArgumentException("The argument statement must is instance of Select.");
        }
        TableNameModifier modifier = new TableNameModifier(tableSuffix, includePattern);
        ((Select) statement).getSelectBody().accept(modifier);
        return statement;
    }

    private class TableNameModifier implements SelectVisitor, FromItemVisitor {

        private String  suffix;
        private Pattern includePattern;

        private TableNameModifier(String suffix, Pattern includePattern) {
            this.suffix = suffix;
            this.includePattern = includePattern;
        }

        @Override
        public void visit(PlainSelect plainSelect) {
            Table tableName = (Table) plainSelect.getFromItem();
            String tableNameName = tableName.getName().toLowerCase();
            if (includePattern.matcher(tableNameName).find()) {
                String newName = convertTableName(tableNameName, suffix);
                tableName.setName(newName);
            }
            //如果存在关联表，重置表名
            List<Join> joinList = plainSelect.getJoins();
            if (CollectionUtils.isEmpty(joinList)) {
                return;
            }
            for (Join join : joinList) {
                Table joinTable = (Table) join.getRightItem();
                String joinTableName = joinTable.getName().toLowerCase();
                if (includePattern.matcher(joinTableName).find()) {
                    String newName = convertTableName(joinTableName, suffix);
                    joinTable.setName(newName);
                }
            }
        }

        @Override
        public void visit(SetOperationList setOpList) {
            setOpList.accept(this);
        }

        @Override
        public void visit(WithItem withItem) {
            withItem.accept(this);
        }

        @Override
        public void visit(Table tableName) {
            String tableNameName = tableName.getName();

            if (includePattern.matcher(tableNameName).find()) {

                String newName = convertTableName(tableNameName, suffix);
                tableName.setName(newName);
            }
        }

        @Override
        public void visit(SubSelect subSelect) {

        }

        @Override
        public void visit(SubJoin subjoin) {

        }

        @Override
        public void visit(LateralSubSelect lateralSubSelect) {

        }

        @Override
        public void visit(ValuesList valuesList) {

        }

        /*
         * (non-Javadoc)
         * @see net.sf.jsqlparser.statement.select.FromItemVisitor#visit(net.sf.
         * jsqlparser.statement.select.TableFunction)
         */
        @Override
        public void visit(TableFunction tableFunction) {
            // TODO Auto-generated method stub

        }

    }

}
