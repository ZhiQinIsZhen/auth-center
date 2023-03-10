package com.lyz.auth.service.staff.exception;

import com.lyz.auth.common.remote.exception.IExceptionService;
import com.lyz.auth.common.remote.exception.RemoteServiceException;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 9:32
 */
public class RemoteStaffServiceException extends RemoteServiceException {

    public RemoteStaffServiceException(IExceptionService codeService) {
        super(codeService);
    }
}
