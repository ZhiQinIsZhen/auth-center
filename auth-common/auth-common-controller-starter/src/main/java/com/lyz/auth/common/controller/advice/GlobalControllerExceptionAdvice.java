package com.lyz.auth.common.controller.advice;

import com.lyz.auth.common.biz.constant.CommonBizConstant;
import com.lyz.auth.common.controller.result.Result;
import com.lyz.auth.common.remote.exception.CommonExceptionCodeEnum;
import com.lyz.auth.common.remote.exception.RemoteServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Objects;

/**
 * Desc:global exception advice
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 9:28
 */
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalControllerExceptionAdvice {

    @ExceptionHandler({Exception.class})
    public Result exception(Exception exception) {
        log.error("未知异常", exception);
        return Result.error(CommonExceptionCodeEnum.UNKNOWN_EXCEPTION);
    }

    @ExceptionHandler({RpcException.class})
    public Result rpcException(RpcException exception) {
        log.error("远程服务调用异常->rpc", exception);
        return Result.error(CommonExceptionCodeEnum.REMOTE_SERVICE_FAIL);
    }

    @ExceptionHandler({BindException.class})
    public Result bindException(BindException exception) {
        if (Objects.nonNull(exception) && exception.hasErrors()) {
            List<ObjectError> errors = exception.getAllErrors();
            for (ObjectError error : errors) {
                return Result.error(CommonExceptionCodeEnum.PARAMS_VALIDATED.getCode(), error.getDefaultMessage());
            }
        }
        return Result.error(CommonExceptionCodeEnum.PARAMS_VALIDATED);
    }

    @ExceptionHandler({ValidationException.class})
    public Result validationException(ValidationException exception) {
        String[] message = exception.getMessage().split(CommonBizConstant.DEFAULT_JOINER);
        if (message.length >= 2) {
            return Result.error(CommonExceptionCodeEnum.PARAMS_VALIDATED.getCode(), message[1].trim());
        }
        return Result.error(CommonExceptionCodeEnum.PARAMS_VALIDATED);
    }

    @ExceptionHandler({RemoteServiceException.class})
    public Result remoteServiceException(RemoteServiceException exception) {
        return Result.error(exception.getCode(), exception.getMessage());
    }
}
