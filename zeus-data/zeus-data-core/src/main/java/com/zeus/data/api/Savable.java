package com.zeus.data.api;

import com.zeus.data.api.exception.*;

/**
 * 保存
 *
 * @param <M> 实体类型
 */
public interface Savable<M> extends Addable<M>, Updatable<M> {
    /**
     * 不存在则增加，存在则修改
     *
     * @param model 实体对象
     * @return 实体对象
     * @throws BusinessException
     * @throws UniqueException
     * @throws RepositoryException
     * @throws OptimisticLockException
     */
    M addOrUpdate(M model) throws BusinessException, UniqueException, RepositoryException, OptimisticLockException;
}
