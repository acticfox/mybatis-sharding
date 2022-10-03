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
 * 类DbShard.java的实现描述：
 * 
 * <pre>
 * 数据源区分策略注解.
 * 
 * <pre>
 * @author fanyong.kfy 2016年12月6日 下午7:16:46
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DbShard {

    /**
     * 用于选择数据源的key.
     * 
     * @return
     */
    String dbKey();

    /**
     * 选择数据源的规则.
     * 
     * @return
     */
    String rule();

}
