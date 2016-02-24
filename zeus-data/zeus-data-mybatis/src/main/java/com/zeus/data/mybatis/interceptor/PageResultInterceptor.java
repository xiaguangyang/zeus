package com.zeus.data.mybatis.interceptor;


import com.zeus.data.api.model.Page;
import com.zeus.data.api.model.QPageQuery;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;


import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询结果拦截器
 *
 * @author quweixin
 * @version V1.0
 */
@Intercepts({@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class PageResultInterceptor extends PageInterceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 目标对象转换
        ResultSetHandler resultSetHandler = (ResultSetHandler) invocation.getTarget();

        // 获取MappedStatement,Configuration对象
        MetaObject metaObject =
                MetaObject.forObject(resultSetHandler, new DefaultObjectFactory(), new DefaultObjectWrapperFactory());
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("mappedStatement");
        String statement = mappedStatement.getId();
        if (!isPageSql(statement)) {
            return invocation.proceed();
        }

        // 获取分页参数
        QPageQuery pageQuery = (QPageQuery) metaObject.getValue("boundSql.parameterObject");

        List<Page> result = new ArrayList<Page>(1);
        Page page = new Page();
        page.setPagination(pageQuery.getPagination());
        page.setResult((List) invocation.proceed());
        result.add(page);

        return result;
    }
}
