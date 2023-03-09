package com.lyz.auth.security.client.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 15:08
 */
@Getter
@Setter
public class AuthUserDetails extends User {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private final Long userId;

    /**
     * 昵称
     */
    private final String nickName;

    /**
     * 真实姓名
     */
    private final String realName;

    /**
     * 用户邮箱
     */
    private final String email;

    /**
     * 用户手机
     */
    private final String mobile;

    /**
     * 登录类型
     * @see com.lyz.auth.service.authentication.constant.LoginType
     */
    private Integer loginType;

    /**
     * 登录设备
     * @see com.lyz.auth.service.authentication.constant.Device
     */
    private Integer device;

    /**
     * 用户角色
     */
    private final List<Integer> roleIds;

    /**
     * 加密盐
     */
    private final String salt;

    /**
     * 应用名
     */
    private final String applicationName;

    public AuthUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
                           Long userId, String nickName, String realName, String email, String mobile, Integer loginType, Integer device, List<Integer> roleIds, String salt, String applicationName) {
        super(username, password, authorities);
        this.userId = userId;
        this.nickName = nickName;
        this.realName = realName;
        this.email = email;
        this.mobile = mobile;
        this.loginType = loginType;
        this.device = device;
        this.roleIds = roleIds;
        this.salt = salt;
        this.applicationName = applicationName;
    }
}
