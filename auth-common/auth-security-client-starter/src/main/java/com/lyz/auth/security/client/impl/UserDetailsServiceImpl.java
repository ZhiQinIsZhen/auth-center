package com.lyz.auth.security.client.impl;

import com.lyz.auth.common.biz.constant.CommonBizConstant;
import com.lyz.auth.security.client.context.AuthContext;
import com.lyz.auth.service.authentication.exception.AuthExceptionCodeEnum;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 14:50
 */
@Configuration
@ConditionalOnBean(value = {AuthenticationManager.class})
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        int index = username.indexOf(CommonBizConstant.DEFAULT_JOINER);
        if (index == -1) {
            throw new UsernameNotFoundException(AuthExceptionCodeEnum.AUTHORIZATION_FAIL.getMessage());
        }
        return AuthContext.Transform.getByAuthUser(
                AuthContext.AuthService.loadByUsername(
                        username.substring(index + 1),
                        Integer.valueOf(username.substring(0, index))
                )
        );
    }
}
