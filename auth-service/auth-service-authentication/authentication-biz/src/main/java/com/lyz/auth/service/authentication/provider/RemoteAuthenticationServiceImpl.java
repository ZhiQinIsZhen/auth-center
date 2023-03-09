package com.lyz.auth.service.authentication.provider;

import com.alibaba.nacos.api.utils.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.base.Splitter;
import com.lyz.auth.common.biz.constant.CommonBizConstant;
import com.lyz.auth.common.biz.util.BeanUtil;
import com.lyz.auth.common.util.RandomUtil;
import com.lyz.auth.service.authentication.bo.*;
import com.lyz.auth.service.authentication.constant.GenericServiceMethod;
import com.lyz.auth.service.authentication.exception.AuthExceptionCodeEnum;
import com.lyz.auth.service.authentication.exception.RemoteAuthServiceException;
import com.lyz.auth.service.authentication.moedel.AuthApplicationDO;
import com.lyz.auth.service.authentication.remote.RemoteAuthenticationService;
import com.lyz.auth.service.authentication.service.IAuthApplicationService;
import com.lyz.auth.service.authentication.util.AuthGroupContext;
import com.lyz.auth.service.authentication.util.GenericServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 15:41
 */
@Slf4j
@DubboService
public class RemoteAuthenticationServiceImpl implements RemoteAuthenticationService {

    @Resource
    private IAuthApplicationService authApplicationService;
    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 用户注册
     *
     * @param authUserRegister
     * @return
     * @param <T>
     */
    @Override
    public <T extends AuthUserRegisterBO> Boolean registry(T authUserRegister) {
        if (StringUtils.isBlank(authUserRegister.getApplicationName())) {
            log.warn("用户注册错误，原因 : applicationName is blank");
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.REGISTRY_ERROR);
        }
        AuthUserRegisterBO registerBO = BeanUtil.copyProperties(authUserRegister, AuthUserRegisterBO.class, (s, t) -> {
            t.setPassword(passwordEncoder.encode(s.getPassword()));
        });
        AuthApplicationDO authApplicationDO = authApplicationService.getOne(
                Wrappers.lambdaQuery(AuthApplicationDO.builder().applicationName(registerBO.getApplicationName()).build())
        );
        if (Objects.isNull(authApplicationDO)) {
            log.warn("用户注册错误，原因：没有找到对应应用配置信息，applicationName : {}", registerBO.getApplicationName());
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.REGISTRY_ERROR);
        }
        registerBO.setSalt(RandomUtil.randomChars(16));
        try {
            AuthGroupContext.setAuthGroup(authApplicationDO.getDubboGroup());
            GenericService genericService = GenericServiceUtil.getByClassName(
                    RemoteAuthenticationService.class,
                    authApplicationDO.getDubboVersion(),
                    authApplicationDO.getDubboGroup(),
                    authApplicationDO.getDubboTimeout()
            );
            return GenericServiceUtil.invoke(GenericServiceMethod.REGISTRY.getMethod(), Boolean.class, genericService, registerBO);
        } finally {
            AuthGroupContext.remove();
        }
    }

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @param device
     * @return
     * @param <T>
     */
    @Override
    public <T extends AuthUserBO> T loadByUsername(String username, Integer device) {
        List<String> names = Splitter.on(CommonBizConstant.DEFAULT_JOINER).splitToList(username);
        AuthApplicationDO authApplicationDO = authApplicationService.getOne(
                Wrappers.lambdaQuery(AuthApplicationDO.builder().applicationName(names.get(0)).build())
        );
        if (Objects.isNull(authApplicationDO)) {
            log.warn("查询用户信息失败，原因没有找到对应的应用配置信息，applicationName : {}", names.get(0));
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.LOGIN_ERROR);
        }
        try {
            AuthGroupContext.setAuthGroup(authApplicationDO.getDubboGroup());
            GenericService genericService = GenericServiceUtil.getByClassName(
                    RemoteAuthenticationService.class,
                    authApplicationDO.getDubboVersion(),
                    authApplicationDO.getDubboGroup(),
                    authApplicationDO.getDubboTimeout()
            );
            AuthUserBO authUser = GenericServiceUtil.invoke(
                    GenericServiceMethod.LOAD_BY_USERNAME.getMethod(),
                    AuthUserBO.class,
                    genericService,
                    names.get(1), device);
            authUser.setDevice(device);
            authUser.setApplicationName(names.get(0));
            return (T) authUser;
        } finally {
            AuthGroupContext.remove();
        }
    }

    /**
     * 登录
     *
     * @param authUserLogin
     * @return
     * @param <T>
     */
    @Override
    public <T extends AuthUserLoginBO> Date login(T authUserLogin) {
        if (StringUtils.isBlank(authUserLogin.getApplicationName())) {
            log.warn("用户登陆错误，原因 : applicationName is blank");
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.LOGIN_ERROR);
        }
        AuthApplicationDO authApplicationDO = authApplicationService.getOne(
                Wrappers.lambdaQuery(AuthApplicationDO.builder().applicationName(authUserLogin.getApplicationName()).build())
        );
        if (Objects.isNull(authApplicationDO)) {
            log.warn("登陆失败，原因没有找到对应的应用配置信息，applicationName : {}", authUserLogin.getApplicationName());
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.LOGIN_ERROR);
        }
        try {
            AuthGroupContext.setAuthGroup(authApplicationDO.getDubboGroup());
            GenericService genericService = GenericServiceUtil.getByClassName(
                    RemoteAuthenticationService.class,
                    authApplicationDO.getDubboVersion(),
                    authApplicationDO.getDubboGroup(),
                    authApplicationDO.getDubboTimeout()
            );
            return GenericServiceUtil.invoke(GenericServiceMethod.LOGIN.getMethod(), Date.class, genericService, authUserLogin);
        } finally {
            AuthGroupContext.remove();
        }
    }

    /**
     * 登出
     *
     * @param authUserLogout
     * @return
     * @param <T>
     */
    @Override
    public <T extends AuthUserLogoutBO> Boolean logout(T authUserLogout) {
        if (StringUtils.isBlank(authUserLogout.getApplicationName())) {
            return Boolean.FALSE;
        }
        AuthApplicationDO authApplicationDO = authApplicationService.getOne(
                Wrappers.lambdaQuery(AuthApplicationDO.builder().applicationName(authUserLogout.getApplicationName()).build())
        );
        if (Objects.isNull(authApplicationDO)) {
            return Boolean.FALSE;
        }
        try {
            AuthGroupContext.setAuthGroup(authApplicationDO.getDubboGroup());
            GenericService genericService = GenericServiceUtil.getByClassName(
                    RemoteAuthenticationService.class,
                    authApplicationDO.getDubboVersion(),
                    authApplicationDO.getDubboGroup(),
                    authApplicationDO.getDubboTimeout()
            );
            return GenericServiceUtil.invoke(GenericServiceMethod.LOGOUT.getMethod(), Boolean.class, genericService, authUserLogout);
        } finally {
            AuthGroupContext.remove();
        }
    }

    /**
     * 获取权限
     *
     * @param authUser
     * @return
     */
    @Override
    public List<AuthGrantedAuthorityBO> authorities(AuthUserBO authUser) {
        if (StringUtils.isBlank(authUser.getApplicationName())) {
            log.warn("获取权限错误，原因 : applicationName is blank");
            return RemoteAuthenticationService.super.authorities(authUser);
        }
        AuthApplicationDO authApplicationDO = authApplicationService.getOne(
                Wrappers.lambdaQuery(AuthApplicationDO.builder().applicationName(authUser.getApplicationName()).build())
        );
        if (Objects.isNull(authApplicationDO)) {
            return RemoteAuthenticationService.super.authorities(authUser);
        }
        try {
            AuthGroupContext.setAuthGroup(authApplicationDO.getDubboGroup());
            GenericService genericService = GenericServiceUtil.getByClassName(
                    RemoteAuthenticationService.class,
                    authApplicationDO.getDubboVersion(),
                    authApplicationDO.getDubboGroup(),
                    authApplicationDO.getDubboTimeout()
            );
            return GenericServiceUtil.invokeList(GenericServiceMethod.AUTHORITIES.getMethod(), AuthGrantedAuthorityBO.class, genericService, authUser);
        } finally {
            AuthGroupContext.remove();
        }
    }
}
