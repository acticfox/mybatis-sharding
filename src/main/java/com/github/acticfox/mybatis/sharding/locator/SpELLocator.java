package com.github.acticfox.mybatis.sharding.locator;

import java.util.Map;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * 类SpELLocator.java的实现描述：
 * 
 * <pre>
 * spring 表达式处理
 * 
 * <pre>
 * @author fanyong.kfy 2017年2月22日 上午11:10:46
 */
@Deprecated
public class SpELLocator implements Locator {

    private String rule;

    public SpELLocator(String rule) {
        this.rule = rule;
    }

    @Override
    public String locate(Map<String, Object> locateParam) {

        ExpressionParser parser = new SpelExpressionParser();

        Expression expression = parser.parseExpression(this.rule);

        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariables(locateParam);

        return expression.getValue(context, String.class);

    }

}
