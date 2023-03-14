package com.lyz.auth.service.user.exception;

import com.lyz.auth.common.remote.exception.IExceptionService;
import lombok.AllArgsConstructor;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 9:31
 */
@AllArgsConstructor
public enum UserExceptionCodeEnum implements IExceptionService {
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
