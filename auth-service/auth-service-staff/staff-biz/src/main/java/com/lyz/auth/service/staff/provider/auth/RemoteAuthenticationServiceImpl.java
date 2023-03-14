package com.lyz.auth.service.staff.provider.auth;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.yulichang.toolkit.MPJWrappers;
import com.lyz.auth.common.biz.util.BeanUtil;
import com.lyz.auth.common.util.DateUtil;
import com.lyz.auth.common.util.PatternUtil;
import com.lyz.auth.service.authentication.bo.*;
import com.lyz.auth.service.authentication.constant.LoginType;
import com.lyz.auth.service.authentication.exception.AuthExceptionCodeEnum;
import com.lyz.auth.service.authentication.exception.RemoteAuthServiceException;
import com.lyz.auth.service.authentication.remote.RemoteAuthenticationService;
import com.lyz.auth.service.staff.model.*;
import com.lyz.auth.service.staff.model.base.StaffAuthBaseDO;
import com.lyz.auth.service.staff.service.*;
import com.lyz.auth.service.staff.strategy.LoginTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private IStaffInfoService staffInfoService;
    @Resource
    private IStaffAuthMobileService staffAuthMobileService;
    @Resource
    private IStaffAuthEmailService staffAuthEmailService;
    @Resource
    private IStaffLoginLogService staffLoginLogService;
    @Resource
    private IStaffLogoutLogService staffLogoutLogService;
    @Resource
    private IStaffAuthorityService staffAuthorityService;
    @Resource
    private IStaffRoleService staffRoleService;

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
                && staffAuthMobileService.count(Wrappers.lambdaQuery(StaffAuthMobileDO.builder().mobile(authUserRegister.getMobile()).build())) > 0) {
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.MOBILE_EXIST);
        }
        if (StringUtils.isNotBlank(authUserRegister.getEmail())
                && staffAuthEmailService.count(Wrappers.lambdaQuery(StaffAuthEmailDO.builder().email(authUserRegister.getEmail()).build())) > 0) {
            throw new RemoteAuthServiceException(AuthExceptionCodeEnum.EMAIL_EXIST);
        }
        StaffInfoDO staffInfoDO = BeanUtil.copyProperties(authUserRegister, StaffInfoDO.class, (s, t) -> {
            t.setRegistryTime(DateUtil.currentDate());
        });
        staffInfoService.save(staffInfoDO);
        if (StringUtils.isNotBlank(staffInfoDO.getMobile())) {
            StaffAuthMobileDO mobileDO = StaffAuthMobileDO.builder().mobile(staffInfoDO.getMobile()).build();
            mobileDO.setStaffId(staffInfoDO.getStaffId());
            mobileDO.setPassword(authUserRegister.getPassword());
            staffAuthMobileService.save(mobileDO);
        }
        if (StringUtils.isNotBlank(staffInfoDO.getEmail())) {
            StaffAuthEmailDO emailDO = StaffAuthEmailDO.builder().email(staffInfoDO.getEmail()).build();
            emailDO.setStaffId(staffInfoDO.getStaffId());
            emailDO.setPassword(authUserRegister.getPassword());
            staffAuthEmailService.save(emailDO);
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
        Long staffId = this.getStaffId(username, authUser);
        if (Objects.isNull(staffId)) {
            return null;
        }
        StaffInfoDO staffInfoDO = staffInfoService.getById(staffId);
        authUser.setUserId(staffId);
        authUser.setEmail(staffInfoDO.getEmail());
        authUser.setMobile(staffInfoDO.getMobile());
        authUser.setRegistryTime(staffInfoDO.getRegistryTime());
        authUser.setNickName(staffInfoDO.getNickName());
        authUser.setRealName(staffInfoDO.getRealName());
        authUser.setSalt(staffInfoDO.getSalt());
        authUser.setDevice(device);
        Date lastLoginTime = staffLoginLogService.lastLoginTime(staffId, device);
        Date lastLogoutTime = staffLogoutLogService.lastLogoutTime(staffId, device);
        authUser.setCheckTime(ObjectUtils.max(lastLoginTime, lastLogoutTime));
        //查询角色信息
        List<StaffRoleDO> roles = staffRoleService.list(Wrappers.query(StaffRoleDO.builder().staffId(staffId).build()));
        authUser.setRoleIds(CollectionUtils.isEmpty(roles) ? null : roles.stream().map(StaffRoleDO::getRoleId).collect(Collectors.toList()));
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
        StaffLoginLogDO staffLoginLogDO = BeanUtil.copyProperties(authUserLogin, StaffLoginLogDO.class, (s, t) -> {
            t.setStaffId(authUserLogin.getUserId());
            t.setLoginTime(DateUtil.currentDate());
        });
        staffLoginLogService.save(staffLoginLogDO);
        //可能会有时间误差
        return staffLoginLogService.getById(staffLoginLogDO.getId()).getLoginTime();
    }

    /**
     * 登出
     *
     * @param authUserLogout
     * @return
     * @param <T>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public <T extends AuthUserLogoutBO> Boolean logout(T authUserLogout) {
        StaffLogoutLogDO staffLogoutLogDO = BeanUtil.copyProperties(authUserLogout, StaffLogoutLogDO.class, (s, t) -> {
            t.setStaffId(s.getUserId());
            t.setLogoutTime(DateUtil.currentDate());
        });
        return staffLogoutLogService.save(staffLogoutLogDO);
    }

    /**
     * 获取权限列表
     *
     * @param authUser
     * @return
     * @param <T>
     */
    @Override
    public <T extends AuthUserBO> List<AuthGrantedAuthorityBO> authorities(T authUser) {
        List<AuthGrantedAuthorityBO> result = staffRoleService.selectJoinList(AuthGrantedAuthorityBO.class,
                MPJWrappers.<StaffRoleDO>lambdaJoin()
                        .select(SystemAuthorityDO::getAuthority, SystemAuthorityDO::getApplicationName)
                        .leftJoin(SystemRoleAuthorityDO.class, SystemRoleAuthorityDO::getRoleId, StaffRoleDO::getRoleId)
                        .leftJoin(SystemAuthorityDO.class, SystemAuthorityDO::getAuthorityId, SystemRoleAuthorityDO::getAuthorityId)
                        .eq(StaffRoleDO::getStaffId, authUser.getUserId())
                        .eq(StaffRoleDO::getApplicationName, authUser.getApplicationName())
        );
        return result;
    }

    /**
     * 根据username获取对应用户id
     *
     * @param username
     * @param authUser
     * @return
     */
    private Long getStaffId(String username, AuthUserBO authUser) {
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
        StaffAuthBaseDO staffAuthBaseDO = loginTypeService.getByUsername(username);
        if (Objects.isNull(staffAuthBaseDO)) {
            return null;
        }
        authUser.setPassword(staffAuthBaseDO.getPassword());
        return staffAuthBaseDO.getStaffId();
    }
}
