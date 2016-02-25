package com.zeus.data.api.session;


import com.zeus.data.api.model.Role;

/**
 * 默认用户信息
 * Created by hexiaofeng on 15-8-26.
 */
public class UserInfo implements UserDetail {

    // ID
    protected long id;
    // 代码
    protected String code;
    // 名称
    protected String name;
    // 角色
    protected Role[] roles;

    public UserInfo() {
    }

    public UserInfo(long id, String code, String name, Role... roles) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.roles = roles;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    @Deprecated
    public int getRole() {
        if (roles != null) {
            return (int) roles[0].getId();
        } else {
            return (int) Role.USER.getId();
        }
    }

    @Override
    public Role[] getRoles() {
        return roles;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoles(Role... roles) {
        this.roles = roles;
    }

}
