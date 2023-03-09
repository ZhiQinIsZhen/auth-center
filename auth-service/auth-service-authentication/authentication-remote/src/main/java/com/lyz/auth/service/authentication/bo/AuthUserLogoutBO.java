package com.lyz.auth.service.authentication.bo;

import lombok.*;

import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 14:06
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserLogoutBO implements Serializable {
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
     * 登出设备
     * @see com.lyz.auth.service.authentication.constant.Device
     */
    private Integer device;

    /**
     * 登出IP
     */
    private String logoutIp;

    /**
     * 登出应用名
     */
    private String applicationName;
}
