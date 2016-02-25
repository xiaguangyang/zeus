package com.zeus.data.api.session;


import com.zeus.data.api.model.Role;

import java.io.Serializable;

/**
 * 会话用户
 * Created by hexiaofeng on 15-3-2.
 */
public interface UserDetail extends Serializable {

    /**
     * 获取当前用户ID
     *
     * @return 当前用户ID
     */
    long getId();

    /**
     * 获取代码
     *
     * @return 用户代码
     */
    String getCode();

    /**
     * 获取名称
     *
     * @return 名称
     */
    String getName();

    /**
     * 获取角色
     *
     * @return 角色
     */
    // TODO 权限上线后，删除
    int getRole();

    /**
     * 获取角色
     *
     * @return 角色
     */
    Role[] getRoles();

}
