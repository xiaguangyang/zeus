package com.zeus.data.api;

import com.zeus.data.api.exception.*;

/**
 * 代码查询
 *
 * @param <M> 实体对象类型
 */
public interface Codeable<M> {

    /**
     * 根据代码进行查找
     *
     * @param code 代码
     * @return 实体对象
     * @throws RepositoryException
     */
    M findByCode(String code) throws RepositoryException;

}