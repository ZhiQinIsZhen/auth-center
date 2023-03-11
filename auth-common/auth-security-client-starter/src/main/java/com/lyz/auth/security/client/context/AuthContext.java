package com.lyz.auth.security.client.context;

import com.google.common.base.Joiner;
import com.lyz.auth.common.biz.constant.CommonBizConstant;
import com.lyz.auth.common.biz.util.BeanUtil;
import com.lyz.auth.common.biz.util.HttpServletContext;
import com.lyz.auth.common.util.PatternUtil;
import com.lyz.auth.security.client.constant.SecurityClientConstant;
import com.lyz.auth.security.client.user.AuthGrantedAuthority;
import com.lyz.auth.security.client.user.AuthUserDetails;
import com.lyz.auth.service.authentication.bo.AuthUserBO;
import com.lyz.auth.service.authentication.bo.AuthUserLoginBO;
import com.lyz.auth.service.authentication.bo.AuthUserLogoutBO;
import com.lyz.auth.service.authentication.bo.AuthUserRegisterBO;
import com.lyz.auth.service.authentication.remote.RemoteAuthenticationService;
import com.lyz.auth.service.authentication.remote.RemoteJwtParseService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.Objects;

/**
 * Desc:auth context  = new InheritableThreadLocal<>()
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 14:35
 */
@Configuration
public class AuthContext implements ApplicationContextAware, EnvironmentAware, InitializingBean {

    private static String applicationName;
    private static Environment environment;
    private static ApplicationContext applicationContext;
    private static RemoteAuthenticationService remoteAuthenticationService;
    private static RemoteJwtParseService remoteJwtParseService;
    private static AuthenticationManager authenticationManager;

    private static InheritableThreadLocal<AuthUserBO> innerContext = new InheritableThreadLocal<>();

    @Override
    public void afterPropertiesSet() {
        this.remoteAuthenticationService = applicationContext.getBean("remoteAuthenticationService-auth", RemoteAuthenticationService.class);
        this.remoteJwtParseService = applicationContext.getBean("remoteJwtParseService-auth", RemoteJwtParseService.class);
        this.authenticationManager = applicationContext.getBean("authenticationManager", AuthenticationManager.class);
        this.applicationName = environment.getProperty(SecurityClientConstant.DUBBO_APPLICATION_NAME_PROPERTY);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * 获取认证用户
     *
     * @return
     */
    public static <T extends AuthUserBO> T getAuthUser() {
        return (T) innerContext.get();
    }

    /**
     * 设置认证用户
     *
     * @param authUser
     */
    public static <T extends AuthUserBO> void setAuthUser(T authUser) {
        innerContext.set(authUser);
    }

    /**
     * 移除认证用户
     */
    public static void remove() {
        innerContext.remove();
    }

    /**
     * 认证服务
     */
    public static class AuthService {
        /**
         * 用户注册
         *
         * @param authUserRegisterBO
         * @return
         */
        public static Boolean registry(AuthUserRegisterBO authUserRegisterBO) {
            authUserRegisterBO.setApplicationName(applicationName);
            return remoteAuthenticationService.registry(authUserRegisterBO);
        }

        /**
         * 登录
         *
         * @param authUserLoginBO
         */
        public static AuthUserBO login(AuthUserLoginBO authUserLoginBO) {
            authUserLoginBO.setApplicationName(environment.getProperty(SecurityClientConstant.DUBBO_APPLICATION_NAME_PROPERTY));
            authUserLoginBO.setDevice(DeviceContext.getDevice(HttpServletContext.getRequest()).getType());
            authUserLoginBO.setLoginType(PatternUtil.checkMobileEmail(authUserLoginBO.getLoginName()));
            authUserLoginBO.setLoginIp(HttpServletContext.getIpAddress(HttpServletContext.getRequest()));
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    Joiner.on(CommonBizConstant.DEFAULT_JOINER).join(
                            authUserLoginBO.getDevice(),
                            authUserLoginBO.getApplicationName(),
                            authUserLoginBO.getLoginName()),
                    authUserLoginBO.getLoginPwd());
            SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(authentication));
            AuthUserDetails authUserDetails = (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Date checkTime = remoteAuthenticationService.login(
                    AuthUserLoginBO.builder()
                            .applicationName(authUserLoginBO.getApplicationName())
                            .userId(authUserDetails.getUserId())
                            .loginType(authUserLoginBO.getLoginType())
                            .device(authUserLoginBO.getDevice())
                            .loginIp(authUserLoginBO.getLoginIp())
                            .build());
            return BeanUtil.copyProperties(authUserDetails, AuthUserBO.class, (s, t) -> {
                t.setCheckTime(checkTime);
                t.setToken(JwtService.generateToken(t));
                t.setExpiration(JwtService.getExpiration(t.getToken()));
            });
        }

        /**
         * 根据登录名查询用户信息
         *
         * @param username
         * @return
         */
        public static AuthUserBO loadByUsername(String username, Integer device) {
            return remoteAuthenticationService.loadByUsername(username, device);
        }

        /**
         * 登出
         *
         * @return
         */
        public static Boolean logout() {
            AuthUserBO authUser = getAuthUser();
            if (Objects.isNull(authUser)) {
                return Boolean.FALSE;
            }
            AuthUserLogoutBO authUserLogoutBO = BeanUtil.copyProperties(authUser, AuthUserLogoutBO.class, (s, t) -> {
                t.setLogoutType(PatternUtil.checkMobileEmail(s.getUsername()));
                t.setDevice(DeviceContext.getDevice(HttpServletContext.getRequest()).getType());
                t.setLogoutIp(HttpServletContext.getIpAddress());
            });
            return remoteAuthenticationService.logout(authUserLogoutBO);
        }
    }

    /**
     * JWT服务
     */
    public static class JwtService {
        /**
         * 解析token
         *
         * @param token
         * @param applicationName
         * @return
         */
        public static AuthUserBO parseToken(final String token, final String applicationName) {
            return remoteJwtParseService.parseToken(token, applicationName);
        }

        /**
         * 创建token
         *
         * @param authUser
         * @return
         */
        public static String generateToken(final AuthUserBO authUser) {
            return remoteJwtParseService.generateToken(authUser);
        }

        /**
         * 获取失效时间
         *
         * @param token
         * @return
         */
        public static Long getExpiration(final String token) {
            return remoteJwtParseService.getExpiration(token);
        }
    }

    /**
     * 转化
     */
    public static class Transform {
        /**
         * 转化
         *
         * @param authUser
         * @return
         */
        public static AuthUserDetails getByAuthUser(AuthUserBO authUser) {
            if (Objects.isNull(authUser)) {
                return null;
            }
            return new AuthUserDetails(
                    authUser.getUsername(),
                    authUser.getPassword(),
                    BeanUtil.copyProperties(authUser.getAuthorities(), AuthGrantedAuthority.class),
                    authUser.getUserId(),
                    authUser.getNickName(),
                    authUser.getRealName(),
                    authUser.getEmail(),
                    authUser.getMobile(),
                    authUser.getLoginType(),
                    authUser.getDevice(),
                    authUser.getRoleIds(),
                    authUser.getSalt(),
                    authUser.getApplicationName()
            );
        }
    }
}
