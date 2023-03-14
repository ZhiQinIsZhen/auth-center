package com.lyz.auth.service.user.exception;

import com.lyz.auth.common.remote.exception.IExceptionService;
import com.lyz.auth.common.remote.exception.RemoteServiceException;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 9:32
 */
public class RemoteUserServiceException extends RemoteServiceException {

    public RemoteUserServiceException() {
        super();
    }

    public RemoteUserServiceException(IExceptionService codeService) {
        super(codeService);
    }
}
