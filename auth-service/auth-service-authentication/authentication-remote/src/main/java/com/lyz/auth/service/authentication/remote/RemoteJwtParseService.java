package com.lyz.auth.service.authentication.remote;

import com.lyz.auth.service.authentication.bo.AuthUserBO;

/**
 * Desc:dubbo service
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 13:33
 */
public interface RemoteJwtParseService {

    /**
     * 解析token
     *
     * @param token
     * @param applicationName
     * @return
     * @param <T>
     */
    <T extends AuthUserBO> T parseToken(final String token, final String applicationName);

    /**
     * 生成token
     *
     * @param authUser
     * @return
     */
    <T extends AuthUserBO> String generateToken(final T authUser);

    /**
     * 获取失效时间
     *
     * @param token
     * @return
     */
    Long getExpiration(final String token);
}
