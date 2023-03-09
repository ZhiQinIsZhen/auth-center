package com.lyz.auth.common.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Desc:该优先级比 validation 参数校验注解 高
 * 即 先处理 Trim 再 处理 validation
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 10:29
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Trim {
}
