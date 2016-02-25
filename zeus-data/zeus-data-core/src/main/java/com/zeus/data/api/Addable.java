package com.zeus.data.api;


import com.zeus.data.api.exception.BusinessException;
import com.zeus.data.api.exception.OptimisticLockException;
import com.zeus.data.api.exception.RepositoryException;
import com.zeus.data.api.exception.UniqueException;

/**
 * 增加
 *
 * @param <M> 实体类型
 */
public interface Addable<M> {
    /**
     * 增加实体
     *
     * @param model 实体对象
     * @return 实体对象
     * @throws BusinessException
     * @throws UniqueException
     * @throws RepositoryException
     * @throws OptimisticLockException
     */
    M add(M model) throws BusinessException, UniqueException, RepositoryException, OptimisticLockException;
}
