package com.github.acticfox.mybatis.sharding.locator;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

/**
 * 类GroovyScriptHelper.java的实现描述：
 * 
 * <pre>
 * grovy调用java帮助类方法
 * 
 * <pre>
 * @author fanyong.kfy 2017年2月22日 上午11:09:24
 */
public class GroovyScriptHelper {

    private static final FastDateFormat MONTH_PART = FastDateFormat.getInstance("yyyyMM");

    public static String month(Date date) {
        return MONTH_PART.format(date);
    }

    public static String leftPad(long data, int toLength) {
        return StringUtils.leftPad(String.valueOf(data), toLength, '0');
    }

    public static String right(final String str, final int len) {
        return StringUtils.right(str, len);
    }

}
