package com.zeus.data.api;


import com.zeus.data.api.exception.RepositoryException;

import java.util.List;

/**
 * 列表查询
 *
 * @param <M> 实体类型
 */
public interface Listable<M> {

    /**
     * 查找所有实体
     *
     * @return 所有实体
     * @throws RepositoryException
     */
    List<M> findAll() throws RepositoryException;

}