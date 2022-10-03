package com.github.acticfox.mybatis.sharding.locator;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringUtils.trim;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.groovy.control.CompilationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.acticfox.mybatis.sharding.plugin.DbShardInterceptor;
import com.google.common.base.Function;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;

import groovy.lang.GroovyClassLoader;

/**
 * 类GroovyLocator.java的实现描述：
 * 
 * <pre>
 * grovy 路由规则解析器
 * 
 * <pre>
 * @author fanyong.kfy 2017年2月22日 上午11:08:59
 */
public class GroovyLocator implements Locator {


	private static final Logger log = LoggerFactory.getLogger(DbShardInterceptor.class);

	private static final Pattern VARABLE_SEGMENT = Pattern.compile("\\$.*?\\$");
	private static final Pattern RETURN_SEGMENT = Pattern.compile("\\breturn\\b", Pattern.CASE_INSENSITIVE);
	private Function<Map, Object> functor;

	public GroovyLocator(String rule) {
		this.functor = generatorFunctorImplement(rule);
	}

	@Override
	public String locate(Map<String, Object> locateParam) {
		Map<String, Object> params = Maps.newHashMap();
		for (Map.Entry<String, Object> rawEntry : locateParam.entrySet()) {
			params.put(trim(rawEntry.getKey()).toUpperCase(), rawEntry.getValue());
		}
		return (String) this.functor.apply(params);
	}

	private Function<Map, Object> generatorFunctorImplement(String rawRuleString) {
		checkNotNull(rawRuleString);
		String groovyScript = buildGroovyScript(rawRuleString);
		Class<?> functorClass = null;
		try {
			GroovyClassLoader groovyClassLoader = new GroovyClassLoader(GroovyLocator.class.getClassLoader());
			functorClass = groovyClassLoader.parseClass(groovyScript);
		} catch (CompilationFailedException e) {
			Throwables.propagate(e);
		}

		Function<Map, Object> mapObjectFunction = null;
		try {
			mapObjectFunction = (Function<Map, Object>) functorClass.newInstance();
		} catch (Exception e) {
			Throwables.propagate(e);
		}

		return mapObjectFunction;

	}

	private String buildGroovyScript(String rule) {

		rule = handleVariableReplacement(rule);

		StringBuilder scriptBuilder = new StringBuilder();
		scriptBuilder.append("import static org.apache.commons.lang.StringUtils.*;").append("\n");
		scriptBuilder.append("import java.util.Map;").append("\n");
		scriptBuilder.append("public class INTERNAL_LOCATOR implements com.google.common.base.Function<Map, Object> {");
		scriptBuilder.append("@Override").append("\n");
		scriptBuilder.append("public Object apply(Map map) {");
		if (hasReturn(rule)) {
			scriptBuilder.append(rule);
			scriptBuilder.append(";}}");
		} else {
			scriptBuilder.append("return ");
			scriptBuilder.append(rule);
			scriptBuilder.append("+\"\";}}");
		}
		log.info("GroovyScript:{}", scriptBuilder.toString());
		return scriptBuilder.toString();
	}

	private String handleVariableReplacement(String expression) {

		Matcher matcher = VARABLE_SEGMENT.matcher(expression);
		StringBuilder builder = new StringBuilder();
		int cursor = 0;
		while (matcher.find(cursor)) {
			String var = matcher.group();
			var = var.substring(1, var.length() - 1);
			builder.append(expression.substring(cursor, matcher.start()));
			builder.append(parseTakeVarExp(var));
			cursor = matcher.end();
		}
		builder.append(expression.substring(cursor));
		return builder.toString();
	}

	private String parseTakeVarExp(String var) {
		return "(map.get(\"" + trim(var).toUpperCase() + "\"))";
	}

	private boolean hasReturn(String rule) {
		return RETURN_SEGMENT.matcher(rule).find();
	}
}
