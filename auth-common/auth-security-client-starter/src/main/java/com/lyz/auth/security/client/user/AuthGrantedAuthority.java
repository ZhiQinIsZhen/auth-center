package com.lyz.auth.security.client.user;

import com.google.common.base.Joiner;
import com.lyz.auth.common.biz.constant.CommonBizConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 15:27
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthGrantedAuthority implements GrantedAuthority {

    private String applicationName;

    private String authority;

    @Override
    public String getAuthority() {
        return Joiner.on(CommonBizConstant.DEFAULT_JOINER).join(applicationName, authority).toUpperCase();
    }
}
