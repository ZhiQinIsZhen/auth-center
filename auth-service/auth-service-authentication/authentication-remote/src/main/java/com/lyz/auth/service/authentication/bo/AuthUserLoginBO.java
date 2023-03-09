package com.lyz.auth.service.authentication.bo;

import lombok.*;

import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 14:02
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserLoginBO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

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
     * 登录IP
     */
    private String loginIp;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 登录密码
     */
    private String loginPwd;

    /**
     * 登录应用名
     */
    private String applicationName;
}
