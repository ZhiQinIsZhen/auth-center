package com.lyz.auth.service.authentication.bo;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 13:34
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserBO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName;

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
     * 手机号码
     */
    private String mobile;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 加密盐
     */
    private String salt;

    /**
     * 检查时间
     * 用于是否但设备登录的
     */
    private Date checkTime;

    /**
     * 用户角色
     */
    private List<Integer> roleIds;

    /**
     * 注册时间
     */
    private Date registryTime;

    /**
     * token
     */
    private String token;

    /**
     * 过期时间
     */
    private Long expiration;

    /**
     * 权限列表
     */
    private List<AuthGrantedAuthorityBO> authorities = new ArrayList<>();

    /**
     * 应用名
     */
    private String applicationName;
}
