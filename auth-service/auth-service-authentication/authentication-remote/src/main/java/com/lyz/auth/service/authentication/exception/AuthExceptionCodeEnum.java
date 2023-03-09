package com.lyz.auth.service.authentication.exception;

import com.lyz.auth.common.remote.exception.IExceptionService;
import lombok.AllArgsConstructor;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 14:15
 */
@AllArgsConstructor
public enum AuthExceptionCodeEnum implements IExceptionService {
    FORBIDDEN("401", "登录后进行操作"),
    NO_RIGHT("403", "暂无权限"),
    LOGIN_FAIL("20001", "用户名或者密码错误"),
    AUTHORIZATION_FAIL("20002", "认证失败"),
    AUTHORIZATION_TIMEOUT("20003", "认证过期"),
    REGISTRY_ERROR("20004", "注册错误"),
    LOGIN_ERROR("20005", "登录错误"),
    OTHERS_LOGIN("20006", "该账号已在其他地方登录"),
    MOBILE_EXIST("20007", "该手机号码已注册"),
    EMAIL_EXIST("20008", "该邮箱地址已注册"),
    REGISTER_ERROR("20009", "注册失败"),
    ;

    private String code;

    private String message;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
