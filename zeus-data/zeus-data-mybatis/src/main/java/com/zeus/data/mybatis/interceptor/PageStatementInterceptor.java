package com.zeus.data.mybatis.interceptor;

//import com.jd.cap.data.api.model.QPageQuery;
import com.zeus.data.api.model.QPageQuery;
import com.zeus.data.mybatis.dialect.Dialect;
import com.zeus.data.mybatis.dialect.MySqlDialect;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author quweixin
 * @version V1.0
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PageStatementInterceptor extends PageInterceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler handler = (StatementHandler) invocation.getTarget();

        // 获取MappedStatement,Configuration对象
        MetaObject metaObject =
                MetaObject.forObject(handler, new DefaultObjectFactory(), new DefaultObjectWrapperFactory());
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        String statement = mappedStatement.getId();
        if (!isPageSql(statement)) {
            return invocation.proceed();
        }

        Configuration configuration = (Configuration) metaObject.getValue("delegate.configuration");
        Executor executor = (Executor) metaObject.getValue("delegate.executor");

        // 获取分页参数
        BoundSql boundSql = handler.getBoundSql();
        QPageQuery pageQuery = (QPageQuery) boundSql.getParameterObject();
        String countStatement = buildCountStatement(statement);
        List<Integer> counts = executor.query(configuration.
                getMappedStatement(countStatement), pageQuery, RowBounds.DEFAULT, null);

        int count = 0;
        if (counts != null && !counts.isEmpty()) {
            count = counts.get(0) == null ? 0 : counts.get(0);
        }

        pageQuery.getPagination().setTotalRecord(count);

        String sql = boundSql.getSql();
        if (logger.isDebugEnabled()) {
            logger.debug("raw SQL : " + sql);
        }

        if (sql == null || sql.isEmpty() || sql.contains(" limit ")) {
            return invocation.proceed();
        }

        Dialect.Type dialectType = null;
        try {
            String config = configuration.
                    getVariables().getProperty("dialect");
            if (config != null) {
                dialectType = Dialect.Type.valueOf(config.toUpperCase());
            }
        } catch (Exception e) {
        }
        Dialect dialect = null;
        switch (dialectType) {
            case MYSQL:
                dialect = new MySqlDialect();
                break;
            default:
                throw new SQLException("'dialect' property is invalid.");
        }
        String originalSql = (String) metaObject.getValue("delegate.boundSql.sql");
        metaObject.setValue("delegate.boundSql.sql",
                dialect.getLimitString(originalSql, pageQuery.getPagination().getStart(),
                        pageQuery.getPagination().getPageSize()));
        metaObject.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
        metaObject.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);

        if (logger.isDebugEnabled()) {
            logger.debug("pagination SQL : " + sql);
        }
        return invocation.proceed();
    }
}
