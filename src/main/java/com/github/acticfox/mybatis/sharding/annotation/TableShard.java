/*
 * Copyright 2016 Aliyun.com All right reserved. This software is the
 * confidential and proprietary information of Aliyun.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Aliyun.com .
 */
package com.github.acticfox.mybatis.sharding.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类TableShard.java的实现描述：
 * 
 * <pre>
 * 表区分策略注解
 * 
 * <pre>
 * @author fanyong.kfy 2017年2月17日 上午10:15:20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TableShard {

    /**
     * 参与分表区分的表明表达式.
     * 
     * @return
     */
    String tablePattern();

    /**
     * 选择表的规则.
     * 
     * @return
     */
    String rule();

}
