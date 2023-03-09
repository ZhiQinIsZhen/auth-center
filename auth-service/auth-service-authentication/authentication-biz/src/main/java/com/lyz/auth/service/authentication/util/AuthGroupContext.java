package com.lyz.auth.service.authentication.util;

import lombok.experimental.UtilityClass;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 15:56
 */
@UtilityClass
public class AuthGroupContext {

    private static InheritableThreadLocal<String> innerContext = new InheritableThreadLocal<>();

    public static String getAuthGroup() {
        return innerContext.get();
    }

    public static void setAuthGroup(String authGroup) {
        innerContext.set(authGroup);
    }

    public static void remove() {
        innerContext.remove();
    }
}
