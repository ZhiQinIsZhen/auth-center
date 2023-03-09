package com.lyz.auth.service.authentication.exception;

import com.lyz.auth.common.remote.exception.IExceptionService;
import com.lyz.auth.common.remote.exception.RemoteServiceException;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 14:18
 */
public class RemoteAuthServiceException extends RemoteServiceException {

    public RemoteAuthServiceException(IExceptionService codeService) {
        super(codeService);
    }
}
