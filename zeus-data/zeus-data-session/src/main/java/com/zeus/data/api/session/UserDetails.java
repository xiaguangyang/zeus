package com.zeus.data.api.session;



import com.zeus.data.api.model.Role;

import java.util.Arrays;
import java.util.List;

/**
 * 用户工具类
 *
 * @author zhuchunlai
 * @version 6.4.49
 * @since 2016-01-05
 */
public final class UserDetails {

    private UserDetails() {
        // nothing to do.
    }

    /**
     * 是否是管理员
     *
     * @param user 用户
     * @return 管理员标识
     */
    public static boolean isAdmin(UserDetail user) {
        return isAdmin(user.getRoles());
    }

    /**
     * 是否是管理员
     *
     * @param roles 角色列表
     * @return 管理员标识
     */
    public static boolean isAdmin(Role... roles) {
        if (roles == null || roles.length == 0) {
            return false;
        }
        return isAdmin(Arrays.asList(roles));
    }

    /**
     * 是否是管理员
     *
     * @param roles 角色列表
     * @return 管理员标识
     */
    public static boolean isAdmin(List<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return false;
        }
        for (Role role : roles) {
            if (role == Role.SUPER_ADMIN || role == Role.ADMIN) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是垂直运维
     *
     * @param user 用户
     * @return 垂直运维标识
     */
    public static boolean isOperator(UserDetail user) {
        return isOperator(user.getRoles());
    }

    /**
     * 是否是垂直运维
     *
     * @param roles 角色列表
     * @return 垂直运维标识
     */
    public static boolean isOperator(Role... roles) {
        if (roles == null || roles.length == 0) {
            return false;
        }
        return isOperator(Arrays.asList(roles));
    }

    /**
     * 是否是垂直运维
     *
     * @param roles 角色列表
     * @return 垂直运维标识
     */
    public static boolean isOperator(List<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return false;
        }
        for (Role role : roles) {
            if (role == Role.OPERATOR) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是审核人
     *
     * @param user 用户
     * @return 审核员标识
     */
    public static boolean isAuditor(UserDetail user) {
        return isAuditor(user.getRoles());
    }

    /**
     * 是否是审核人
     *
     * @param roles 角色列表
     * @return 审核员标识
     */
    public static boolean isAuditor(Role... roles) {
        if (roles == null || roles.length == 0) {
            return false;
        }
        return isAuditor(Arrays.asList(roles));
    }

    /**
     * 是否是审核人
     *
     * @param roles 角色列表
     * @return 审核员标识
     */
    public static boolean isAuditor(List<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return false;
        }
        for (Role role : roles) {
            if (role == Role.AUDITOR) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是不是配额管理员
     *
     * @param userDetail 用户
     * @return 配额管理员标识
     */
    public static boolean isQuotaAdmin(UserDetail userDetail) {
        return isQuotaAdmin(userDetail.getRoles());
    }

    /**
     * 判断是不是配额管理员
     *
     * @param roles 角色列表
     * @return 是否是配额管理员的标识
     */
    public static boolean isQuotaAdmin(Role... roles) {
        if (roles == null || roles.length == 0) {
            return false;
        }
        return isQuotaAdmin(Arrays.asList(roles));
    }

    /**
     * 判断是不是配额管理员
     *
     * @param roles 角色列表
     * @return 配额管理员标识
     */
    public static boolean isQuotaAdmin(List<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return false;
        }
        for (Role role : roles) {
            if (role == Role.QUOTA_ADMIN) {
                return true;
            }
        }
        return false;
    }

}
