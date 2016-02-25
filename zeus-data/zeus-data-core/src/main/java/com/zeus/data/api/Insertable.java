package com.zeus.data.api;

import com.zeus.data.api.exception.*;

/**
 * 增加
 *
 * @param <M> 实体类型
 */
public interface Insertable<M> {

    /**
     * 增加实体
     *
     * @param model 实体对象
     * @return 受影响的行数
     * @throws RepositoryException
     * @throws DuplicateKeyException
     */
    int add(M model) throws RepositoryException, DuplicateKeyException;
}