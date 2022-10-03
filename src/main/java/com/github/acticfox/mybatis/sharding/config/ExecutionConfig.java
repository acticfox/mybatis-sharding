package com.github.acticfox.mybatis.sharding.config;

/**
 * 类ExecutionConfig.java的实现描述：
 * 
 * <pre>
 * 综合数据源和表的分区规则配置
 * 
 * <pre>
 * @author fanyong.kfy 2017年2月20日 上午10:16:58
 */
public class ExecutionConfig {

    private DataSourceConfig dataSourceConfig;

    private TableConfig      tableConfig;

    public ExecutionConfig() {
    }

    public ExecutionConfig(DataSourceConfig dataSourceConfig, TableConfig tableConfig) {
        this.dataSourceConfig = dataSourceConfig;
        this.tableConfig = tableConfig;
    }

    public DataSourceConfig getDataSourceConfig() {
        return dataSourceConfig;
    }

    public void setDataSourceConfig(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    public TableConfig getTableConfig() {
        return tableConfig;
    }

    public void setTableConfig(TableConfig tableConfig) {
        this.tableConfig = tableConfig;
    }
}
