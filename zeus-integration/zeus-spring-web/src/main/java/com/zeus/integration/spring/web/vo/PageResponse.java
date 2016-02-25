package com.zeus.integration.spring.web.vo;


import com.zeus.data.api.model.Pagination;
import com.zeus.data.api.model.Query;

/**
 * 分页返回响应
 *
 * @author lindeqiang
 * @since 2015/3/2 9:15
 */
public class PageResponse extends Response {
    //查询条件
    private Query query;
    //分页信息
    private Pagination pagination;

    public PageResponse() {
    }

    public PageResponse(Pagination pagination, Query query) {
        this.query = query;
        this.pagination = pagination;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
