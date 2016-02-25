package com.zeus.data.api;

import com.zeus.data.api.exception.*;
/**
 * 唯一标示查询
 *
 * @param <M> 实体类型
 */
public interface Identifiable<M> {

    /**
     * 查找实体
     *
     * @param id 实体对象ID
     * @return 实体对象
     * @throws RepositoryException
     */
    M findById(long id) throws RepositoryException;

}