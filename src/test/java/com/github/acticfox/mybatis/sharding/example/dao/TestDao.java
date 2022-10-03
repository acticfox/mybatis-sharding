/*
 * Copyright 2016 Aliyun.com All right reserved. This software is the
 * confidential and proprietary information of Aliyun.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Aliyun.com .
 */
package com.github.acticfox.mybatis.sharding.example.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.github.acticfox.mybatis.sharding.annotation.DbShard;
import com.github.acticfox.mybatis.sharding.annotation.DbShardWith;
import com.github.acticfox.mybatis.sharding.annotation.TableShard;
import com.github.acticfox.mybatis.sharding.annotation.TableShardWith;
import com.github.acticfox.mybatis.sharding.example.model.Test;

/**
 * 类TestDao.java的实现描述：
 * 
 * <pre>
 *
 * <pre>
 * @author fanyong.kfy 2016年12月7日 上午10:57:06
 */
@DbShard(dbKey = "ds", rule = "$a$ % 2")
@TableShard(tablePattern = "test", rule = "leftPad($a$ % 4+'', 3,'0')")
// @DbShardWith(props = "a")
@Repository
public class TestDao {

	@Resource
	public SqlSessionTemplate sqlSession;

	public void save(@DbShardWith(props = "a") @TableShardWith(props = "a") @Param(value = "a") Test test, int i) {
		sqlSession.insert("Test-manual.save", test);
	}

	public List<Test> query(@DbShardWith(props = "a") @TableShardWith(props = "a") Map<String, Object> param) {
		return sqlSession.selectList("Test-manual.query", param);
	}

	public void update(@DbShardWith(props = "a") @TableShardWith(props = "a") Map<String, Object> param) {
		sqlSession.update("Test-manual.update", param);
	}

}
