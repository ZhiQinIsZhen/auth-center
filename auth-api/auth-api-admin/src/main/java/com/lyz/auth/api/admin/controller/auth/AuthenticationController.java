package com.lyz.auth.api.admin.controller.auth;

import com.lyz.auth.api.admin.dto.auth.LoginDTO;
import com.lyz.auth.api.admin.dto.auth.StaffRegisterDTO;
import com.lyz.auth.api.admin.event.auth.LoginEvent;
import com.lyz.auth.api.admin.vo.auth.AuthLoginVO;
import com.lyz.auth.common.biz.util.BeanUtil;
import com.lyz.auth.common.biz.util.HttpServletContext;
import com.lyz.auth.common.controller.result.Result;
import com.lyz.auth.security.client.annotation.Anonymous;
import com.lyz.auth.security.client.constant.SecurityClientConstant;
import com.lyz.auth.security.client.context.AuthContext;
import com.lyz.auth.service.authentication.bo.AuthUserBO;
import com.lyz.auth.service.authentication.bo.AuthUserLoginBO;
import com.lyz.auth.service.authentication.bo.AuthUserRegisterBO;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 14:06
 */
@Api(tags = "员工鉴权")
@ApiResponses(value = {
        @ApiResponse(code = 0, message = "成功"),
        @ApiResponse(code = 1, message = "失败")
})
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Resource
    private ApplicationContext applicationContext;

    @Anonymous
    @ApiOperation("注册")
    @PostMapping("/register")
    public Result<Boolean> register(@Validated({StaffRegisterDTO.Register.class}) @RequestBody StaffRegisterDTO staffRegister) {
        return Result.success(AuthContext.AuthService.registry(BeanUtil.copyProperties(staffRegister, AuthUserRegisterBO.class)));
    }

    @Anonymous
    @ApiOperation("登录")
    @PostMapping("/login")
    public Result<AuthLoginVO> login(@Validated({LoginDTO.Login.class}) @RequestBody LoginDTO loginDTO) throws IOException {
        AuthLoginVO authLoginVO = BeanUtil.copyProperties(
                AuthContext.AuthService.login(BeanUtil.copyProperties(loginDTO, AuthUserLoginBO.class)),
                AuthLoginVO.class,
                (s, t) -> t.setStaffId(s.getUserId()));
        applicationContext.publishEvent(new LoginEvent(this, authLoginVO.getStaffId(), authLoginVO.getRealName(), true));
        if (StringUtils.isNotBlank(loginDTO.getRedirect())) {
            HttpServletResponse response = HttpServletContext.getResponse();
            response.setHeader(SecurityClientConstant.DEFAULT_TOKEN_HEADER_KEY, authLoginVO.getToken());
            response.sendRedirect(loginDTO.getRedirect());
        }
        return Result.success(authLoginVO);
    }

    @ApiOperation("登出")
    @PostMapping("/logout")
    @ApiImplicitParam(name = "Authorization", value = "认证token", required = true, dataType = "String",
            paramType = "header", defaultValue = "Bearer ")
    public Result<Boolean> logout() {
        AuthUserBO authUser = AuthContext.getAuthUser();
        if (Objects.nonNull(authUser)) {
            applicationContext.publishEvent(new LoginEvent(this, authUser.getUserId(), authUser.getRealName(), false));
        }
        return Result.success(AuthContext.AuthService.logout());
    }
}
