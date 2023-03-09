package com.lyz.auth.service.authentication.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 14:12
 */
@Getter
@AllArgsConstructor
public enum Device {
    MOBILE(1, "移动端"),
    WEB(2, "网页端"),
    ;

    private int type;
    private String desc;
}
