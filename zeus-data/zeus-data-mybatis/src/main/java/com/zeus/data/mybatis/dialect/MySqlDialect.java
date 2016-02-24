package com.zeus.data.mybatis.dialect;

/**
 * Created by quweixin on 16/2/24.
 * MySql数据库语言
 */
public class MySqlDialect implements Dialect {

    @Override
    public String getLimitString(String sql, int offset, int limit) {
        return getLimitString(sql, offset, Integer.toString(offset), Integer.toString(limit));
    }

    private String getLimitString(String sql, int offset, String offsetPlaceholder, String limitPlaceholder) {
        StringBuilder builder = new StringBuilder(sql);
        builder.append(" limit ");
        if (offset > 0) {
            builder.append(offsetPlaceholder).append(",").append(limitPlaceholder);
        } else {
            builder.append(limitPlaceholder);
        }
        return builder.toString();
    }
}
