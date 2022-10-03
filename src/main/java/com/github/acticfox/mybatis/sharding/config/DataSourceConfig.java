package com.github.acticfox.mybatis.sharding.config;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 类DataSourceConfig.java的实现描述：
 * 
 * <pre>
 * 数据源分区定义配置
 * 
 * <pre>
 * @author fanyong.kfy 2017年2月20日 上午10:16:08
 */
public class DataSourceConfig {

    private Set<String>         availableKey = Sets.newHashSet();

    private String              dataSourceName;

    private String              rule;

    private Map<String, Object> params       = Maps.newHashMap();

    public Set<String> getAvailableKey() {
        return availableKey;
    }

    public void setAvailableKey(Set<String> availableKey) {
        this.availableKey = availableKey;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

}
