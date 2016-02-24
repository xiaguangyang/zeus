package com.zeus.data.mybatis.dialect;

/**
 * Created by quweixin on 16/2/24.
 *
 * 支持的数据库语言
 */
public interface Dialect {

        public static enum Type {
            MYSQL,
            ORACLE
        }

        public String getLimitString(String sql, int offset, int maxResults);
}
