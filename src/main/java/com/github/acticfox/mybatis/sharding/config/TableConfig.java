package com.github.acticfox.mybatis.sharding.config;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 类TableConfig.java的实现描述：
 * 
 * <pre>
 * 表分区配置
 * 
 * <pre>
 * @author fanyong.kfy 2017年2月21日 上午10:26:23
 */
public class TableConfig {

    private String              tablePattern;

    private String              rule;

    private Map<String, Object> params = Maps.newHashMap();

    public String getTablePattern() {
        return tablePattern;
    }

    public void setTablePattern(String tablePattern) {
        this.tablePattern = tablePattern;
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
