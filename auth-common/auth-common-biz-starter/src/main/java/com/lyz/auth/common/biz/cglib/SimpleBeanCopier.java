package com.lyz.auth.common.biz.cglib;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/8 16:42
 */
public class SimpleBeanCopier<S, T> extends BaseBeanCopier {

    public SimpleBeanCopier() {
    }

    public SimpleBeanCopier(Class<S> sourceClass, Class<T> targetClass) {
        setSourceClass(sourceClass);
        setTargetClass(targetClass);
        init();
    }
}
