package com.lyz.auth.service.user.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:15
 */
@Getter
@Setter
public class UserInfoBO implements Serializable {
    private static final long serialVersionUID = 477985923412638468L;

    private Long userId;

    private String realName;

    private String nickName;

    private String mobile;

    private String email;

    private String salt;

    private Date registryTime;
}
