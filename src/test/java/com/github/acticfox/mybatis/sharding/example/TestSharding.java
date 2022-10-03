package com.github.acticfox.mybatis.sharding.example;

import java.util.List;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.acticfox.mybatis.sharding.example.dao.TestDao;
import com.github.acticfox.mybatis.sharding.example.model.Test;
import com.google.common.collect.ImmutableMap;

public class TestSharding {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(
				"spring-config.xml");
		TestDao bean = classPathXmlApplicationContext.getBean(TestDao.class);

		for (int i = 1; i < 10; i++) {
			Test test = new Test();
			test.setA(i);
			test.setB(i);
			test.setC(i);
			bean.save(test, i);
		}

		Map<String, Object> param = ImmutableMap.<String, Object>of("a", 2);
		List<Test> result = bean.query(param);

		System.out.println(result);

	}

}
