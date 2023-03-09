package com.lyz.auth.service.authentication.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 14:12
 */
@Getter
@AllArgsConstructor
public enum LoginType {

    MOBILE(1, "手机号码登录"),
    EMAIL(2, "邮箱登录"),
    ;

    private int type;
    private String desc;
}
