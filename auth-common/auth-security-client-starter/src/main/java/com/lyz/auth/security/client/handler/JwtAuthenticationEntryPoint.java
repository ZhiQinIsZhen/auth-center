package com.lyz.auth.security.client.handler;

import com.lyz.auth.common.controller.result.Result;
import com.lyz.auth.common.util.JsonMapperUtil;
import com.lyz.auth.service.authentication.exception.AuthExceptionCodeEnum;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * Desc:auth point
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 14:27
 */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(JsonMapperUtil.toJSONString(Result.error(AuthExceptionCodeEnum.AUTHORIZATION_FAIL)));
    }
}
