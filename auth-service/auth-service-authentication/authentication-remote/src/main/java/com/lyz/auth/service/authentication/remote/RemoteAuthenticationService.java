package com.lyz.auth.service.authentication.remote;

import com.lyz.auth.service.authentication.bo.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Desc:auth service
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 13:43
 */
public interface RemoteAuthenticationService {

    /**
     * 用户注册
     *
     * @param authUserRegister
     * @return
     * @param <T>
     */
    <T extends AuthUserRegisterBO> Boolean registry(@NotNull T authUserRegister);

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @param device
     * @return
     * @param <T>
     */
    <T extends AuthUserBO> T loadByUsername(@NotBlank String username, @NotNull Integer device);

    /**
     * 登录
     *
     * @param authUserLogin
     * @return
     * @param <T>
     */
    <T extends AuthUserLoginBO> Date login(@NotNull T authUserLogin);

    /**
     * 登出
     *
     * @param authUserLogout
     * @return
     * @param <T>
     */
    <T extends AuthUserLogoutBO> Boolean logout(@NotNull T authUserLogout);

    /**
     * 获取权限列表
     *
     * @param authUser
     * @return
     * @param <T>
     */
    default <T extends AuthUserBO> List<AuthGrantedAuthorityBO> authorities(@NotNull T authUser) {
        return new ArrayList<>();
    }
}
