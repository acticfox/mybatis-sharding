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
 * 类DbShardWith.java的实现描述：
 * 
 * <pre>
 * 用于参与分库判断的条件数据.
 * 
 * <pre>
 * @author fanyong.kfy 2016年12月6日 下午7:20:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.PARAMETER, ElementType.TYPE })
public @interface DbShardWith {

    /**
     * 需要用被标注数据内部数据的情况.
     * 
     * @return
     */
    String[] props() default {};

}
