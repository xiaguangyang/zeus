package com.zeus.data.api;


import com.zeus.data.api.exception.RepositoryException;
import com.zeus.data.api.model.Page;
import com.zeus.data.api.model.QPageQuery;
import com.zeus.data.api.model.Query;


/**
 * 分页查找对象
 *
 * @param <M> 实体类型
 */
public interface Pageable<M, Q extends Query> {

    /**
     * 分页查询
     *
     * @param query 分页查询条件
     * @return 分页数据
     * @throws RepositoryException
     */
    Page<M> findByQuery(QPageQuery<Q> query) throws RepositoryException;

}