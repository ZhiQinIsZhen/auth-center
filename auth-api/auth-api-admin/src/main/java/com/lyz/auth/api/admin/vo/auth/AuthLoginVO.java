package com.lyz.auth.api.admin.vo.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 14:22
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthLoginVO extends AuthStaffInfoVO {

    @ApiModelProperty(value = "鉴权token")
    private String token;

    @ApiModelProperty(value = "Token失效时间戳(ms)")
    private Long expiration;
}
