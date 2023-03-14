package com.lyz.auth.service.user.provider.auth;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lyz.auth.common.biz.util.BeanUtil;
import com.lyz.auth.common.util.DateUtil;
import com.lyz.auth.common.util.PatternUtil;
import com.lyz.auth.service.authentication.bo.AuthUserBO;
import com.lyz.auth.service.authentication.bo.AuthUserLoginBO;
import com.lyz.auth.service.authentication.bo.AuthUserLogoutBO;
import com.lyz.auth.service.authentication.bo.AuthUserRegisterBO;
import com.lyz.auth.service.authentication.constant.LoginType;
import com.lyz.auth.service.authentication.exception.AuthExceptionCodeEnum;
import com.lyz.auth.service.authentication.exception.RemoteAuthServiceException;
import com.lyz.auth.service.authentication.remote.RemoteAuthenticationService;
import com.lyz.auth.service.user.model.*;
import com.lyz.auth.service.user.model.base.UserAuthBaseDO;
import com.lyz.auth.service.user.service.*;
import com.lyz.auth.service.user.strategy.LoginTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 11:10
 */
@Slf4j
@DubboService
public class RemoteAuthenticationServiceImpl implements RemoteAuthenticationService {

    @Resource
    private IUserInfoService userInfoService;
    @Resource
    private IUserAuthMobileService userAuthMobileService;
    @Resource
    private IUserAuthEmailService userAuthEmailService;
    @Resource
    private IUserLoginLogService userLoginLogService;
    @Resource
    private IUserLogoutLogService userLogoutLogService;

    /**
     * 用户注册
     *
     * @param authUserRegister
     * @return
     * @param <T>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public <T extends AuthUserRegisterBO> Boolean registry(T authUserRegister) {
        if (StringUtils.isNotBlank(authUserRegister.getMobile())
                && userAuthMobileService.count(Wrappers.lambdaQuery(UserAuthMobileDO.builder().mobile(authUserRegister.getMobile()).build())) > 0) {
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.MOBILE_EXIST);
        }
        if (StringUtils.isNotBlank(authUserRegister.getEmail())
                && userAuthEmailService.count(Wrappers.lambdaQuery(UserAuthEmailDO.builder().email(authUserRegister.getEmail()).build())) > 0) {
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.EMAIL_EXIST);
        }
        UserInfoDO userInfoDO = BeanUtil.copyProperties(authUserRegister, UserInfoDO.class, (s, t) -> {
            t.setRegistryTime(DateUtil.currentDate());
        });
        userInfoService.save(userInfoDO);
        if (StringUtils.isNotBlank(userInfoDO.getMobile())) {
            UserAuthMobileDO mobileDO = UserAuthMobileDO.builder().mobile(userInfoDO.getMobile()).build();
            mobileDO.setUserId(userInfoDO.getUserId());
            mobileDO.setPassword(authUserRegister.getPassword());
            userAuthMobileService.save(mobileDO);
        }
        if (StringUtils.isNotBlank(userInfoDO.getEmail())) {
            UserAuthEmailDO emailDO = UserAuthEmailDO.builder().email(userInfoDO.getEmail()).build();
            emailDO.setUserId(userInfoDO.getUserId());
            emailDO.setPassword(authUserRegister.getPassword());
            userAuthEmailService.save(emailDO);
        }
        return Boolean.TRUE;
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
        AuthUserBO authUser = AuthUserBO.builder().username(username).loginType(PatternUtil.checkMobileEmail(username)).build();
        Long userId = this.getUserId(username, authUser);
        if (Objects.isNull(userId)) {
            return null;
        }
        UserInfoDO userInfoDO = userInfoService.getById(userId);
        authUser.setUserId(userId);
        authUser.setEmail(userInfoDO.getEmail());
        authUser.setMobile(userInfoDO.getMobile());
        authUser.setRegistryTime(userInfoDO.getRegistryTime());
        authUser.setNickName(userInfoDO.getNickName());
        authUser.setRealName(userInfoDO.getRealName());
        authUser.setSalt(userInfoDO.getSalt());
        authUser.setDevice(device);
        Date lastLoginTime = userLoginLogService.lastLoginTime(userId, device);
        Date lastLogoutTime = userLogoutLogService.lastLogoutTime(userId, device);
        authUser.setCheckTime(ObjectUtils.max(lastLoginTime, lastLogoutTime));
        return (T) authUser;
    }

    /**
     * 登录
     *
     * @param authUserLogin
     * @return
     * @param <T>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public <T extends AuthUserLoginBO> Date login(T authUserLogin) {
        UserLoginLogDO userLoginLogDO = BeanUtil.copyProperties(authUserLogin, UserLoginLogDO.class, (s, t) -> {
            t.setUserId(authUserLogin.getUserId());
            t.setLoginTime(DateUtil.currentDate());
        });
        userLoginLogService.save(userLoginLogDO);
        //可能会有时间误差
        return userLoginLogService.getById(userLoginLogDO.getId()).getLoginTime();
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
        UserLogoutLogDO userLogoutLogDO = BeanUtil.copyProperties(authUserLogout, UserLogoutLogDO.class, (s, t) -> {
            t.setUserId(s.getUserId());
            t.setLogoutTime(DateUtil.currentDate());
        });
        return userLogoutLogService.save(userLogoutLogDO);
    }

    /**
     * 根据username获取对应用户id
     *
     * @param username
     * @param authUser
     * @return
     */
    private Long getUserId(String username, AuthUserBO authUser) {
        LoginType loginType = LoginType.getByType(PatternUtil.checkMobileEmail(username));
        if (Objects.isNull(loginType)) {
            log.warn("username is not email or mobile");
            return null;
        }
        authUser.setLoginType(loginType.getType());
        LoginTypeService loginTypeService = LoginTypeService.LOGIN_TYPE_MAP.get(loginType);
        if (Objects.isNull(loginTypeService)) {
            log.warn("{} can not find LoginTypeService", loginType.name());
            return null;
        }
        UserAuthBaseDO userAuthBaseDO = loginTypeService.getByUsername(username);
        if (Objects.isNull(userAuthBaseDO)) {
            return null;
        }
        authUser.setPassword(userAuthBaseDO.getPassword());
        return userAuthBaseDO.getUserId();
    }
}
