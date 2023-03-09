package com.lyz.auth.service.authentication.bo;

import lombok.*;

import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 14:09
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthGrantedAuthorityBO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 应用名
     */
    private String applicationName;

    /**
     * 权限码
     */
    private String authority;
}
