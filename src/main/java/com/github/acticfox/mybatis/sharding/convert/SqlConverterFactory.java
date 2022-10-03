package com.github.acticfox.mybatis.sharding.convert;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import com.google.common.base.Throwables;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;

/**
 * 类SqlConverterFactory.java的实现描述：
 * 
 * <pre>
 * sql转换器工厂
 * 
 * <pre>
 * @author fanyong.kfy 2017年2月23日 上午11:53:54
 */
public class SqlConverterFactory {
	private static final Log log = LogFactory.getLog(SqlConverterFactory.class);

	private static SqlConverterFactory factory;
	static {
		factory = new SqlConverterFactory();
	}
	private Map<String, SqlConverter> converterMap = new HashMap<String, SqlConverter>();
	private CCJSqlParserManager pm;

	private SqlConverterFactory() {

		pm = new CCJSqlParserManager();
		register();
	}

	public static SqlConverterFactory getInstance() {
		return factory;
	}

	private void register() {
		converterMap.put(Select.class.getName(), new SelectSqlConverter());
		converterMap.put(Insert.class.getName(), new InsertSqlConverter());
		converterMap.put(Update.class.getName(), new UpdateSqlConverter());
		converterMap.put(Delete.class.getName(), new DeleteSqlConverter());
	}

	/**
	 * 修改sql语句
	 * 
	 * @param sql
	 * @param suffix
	 * @return 修改后的sql
	 */
	public String convert(String sql, String suffix, String includePattern) {
		Statement statement = null;
		try {
			statement = pm.parse(new StringReader(sql));
		} catch (JSQLParserException e) {
			log.error(e.getMessage(), e);
			Throwables.propagate(e);
		}

		SqlConverter converter = this.converterMap.get(statement.getClass().getName());

		if (converter != null) {
			return converter.convert(statement, suffix, includePattern);
		}
		return sql;

	}


}
