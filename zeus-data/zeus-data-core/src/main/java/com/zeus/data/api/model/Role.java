package com.zeus.data.api.model;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 角色字典
 *
 * @author zhuchunlai
 * @version 6.4.49
 * @since 2016-01-05
 */
public enum Role implements Serializable {

    /*不能调整枚举顺序，序列化采用了ordinal做的处理*/
    USER(0, "ROLE_USER", "普通用户"),
    OPERATOR(1, "ROLE_OPER", "垂直运维"),//资源授权 运维部门
    ADMIN(2, "ROLE_ADMIN", "管理员"),
    SUPER_ADMIN(3, "ROLE_SUPER_ADMIN", "超级管理员"),
    QUOTA_ADMIN(4, "ROLE_QUOTA_ADMIN", "配额管理员"),//资源授权 配额部门
    AUDITOR(5, "ROLE_AUDITOR", "申请审批人");//资源授权 审批zone 审批部门

    // ID，用于持久化存储
    private final long id;
    // 角色编码，用于spring-security拦截
    private final String code;
    // 描述，用于展示
    private final String description;

    private Role(int id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

    public final long getId() {
        return id;
    }

    public final String getCode() {
        return code;
    }

    public final String getDescription() {
        return description;
    }


    /**
     * 基于角色ID获取角色
     *
     * @param roleId 角色ID
     * @return 角色
     */
    public static Role fromId(long roleId) {
        for (Role role : Role.values()) {
            if (role.id == roleId) {
                return role;
            }
        }
        return null;
    }

    /**
     * 基于角色编码获取角色
     *
     * @param code 角色编码
     * @return 角色
     */
    @SuppressWarnings("unused")
    public static Role fromCode(String code) {
        if (code == null || code.isEmpty()) {
            throw new IllegalArgumentException("code is null or empty.");
        }
        for (Role role : Role.values()) {
            if (role.code.equals(code)) {
                return role;
            }
        }
        return null;
    }


    @JsonValue
    public Map serialize() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("code", code);
        map.put("description", description);
        map.put("name", name());
        map.put("ordinal", ordinal());
        return map;
    }
}
