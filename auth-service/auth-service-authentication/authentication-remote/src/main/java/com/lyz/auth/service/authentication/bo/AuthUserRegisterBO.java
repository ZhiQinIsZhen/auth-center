package com.lyz.auth.service.authentication.bo;

import lombok.*;

import java.io.Serializable;

/**
 * Desc:user register bo
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 13:46
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserRegisterBO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 应用名称
     */
    private String applicationName;

    /**
     * 加密盐
     */
    private String salt;
}
