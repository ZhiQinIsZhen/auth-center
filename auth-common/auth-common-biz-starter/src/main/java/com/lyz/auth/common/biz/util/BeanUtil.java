package com.lyz.auth.common.biz.util;

import cn.hutool.core.util.ReflectUtil;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.lyz.auth.common.biz.cglib.SimpleBeanCopier;
import com.lyz.auth.common.biz.constant.CommonBizConstant;
import com.lyz.auth.common.remote.page.RemotePage;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/8 16:50
 */
@UtilityClass
public class BeanUtil {

    /**
     * 深拷贝{@link SimpleBeanCopier}的副本
     */
    private static final SimpleBeanCopier copier = new SimpleBeanCopier();

    /**
     * 缓存容器
     */
    private static final Map<String, SimpleBeanCopier> COPIER_MAP = new ConcurrentHashMap<>(256);

    /**
     * 单个对象拷贝
     *
     * @param source
     * @param targetClass
     * @return
     * @param <S>
     * @param <T>
     */
    public static <S, T> T copyProperties(S source,  Class<T> targetClass) {
        return copyProperties(source, targetClass, null);
    }

    @Deprecated
    public static <S, T> T copyProperties(S source,  Supplier<T> instanceTarget) {
        return (T) copyProperties(source, instanceTarget.get().getClass(), null);
    }

    /**
     * 单个对象拷贝
     *
     * @param source
     * @param targetClass
     * @param ext
     * @return
     * @param <S>
     * @param <T>
     */
    public static <S, T> T copyProperties(S source,  Class<T> targetClass, BiConsumer<S, T> ext) {
        if (source == null) {
            return null;
        }
        SimpleBeanCopier simpleBeanCopier = getSimpleBeanCopier(source.getClass(), targetClass);
        T target = (T) simpleBeanCopier.apply(source);
        if (Objects.nonNull(ext)) {
            ext.accept(source, target);
        }
        return target;
    }

    /**
     * 数组对象拷贝
     *
     * @param sources
     * @param targetClass
     * @return
     * @param <S>
     * @param <T>
     */
    public static <S, T> List<T> copyProperties(List<S> sources, Class<T> targetClass) {
        return copyProperties(sources, targetClass, (BiConsumer<S, T>) null);
    }

    /**
     * 数组对象拷贝
     *
     * @param sources
     * @param targetClass
     * @param ext
     * @return
     * @param <S>
     * @param <T>
     */
    public static <S, T> List<T> copyProperties(List<S> sources, Class<T> targetClass, BiConsumer<S, T> ext) {
        if (sources == null) {
            return null;
        }
        if (sources.size() == 0) {
            return Lists.newArrayList();
        }
        return sources.stream().map(source -> copyProperties(source, targetClass, ext)).collect(Collectors.toList());
    }

    /**
     * 分页对象拷贝
     *
     * @param pageSource
     * @param targetClass
     * @return
     * @param <S>
     * @param <T>
     */
    public static <S, T> RemotePage<T> copyProperties(RemotePage<S> pageSource, Class<T> targetClass) {
        return copyProperties(pageSource, targetClass, (BiConsumer<S, T>) null);
    }

    /**
     * 分页对象拷贝
     *
     * @param pageSource
     * @param targetClass
     * @param ext
     * @return
     * @param <S>
     * @param <T>
     */
    public static <S, T> RemotePage<T> copyProperties(RemotePage<S> pageSource, Class<T> targetClass, BiConsumer<S, T> ext) {
        if (pageSource == null) {
            return null;
        }
        if (pageSource.getTotal() == 0 || CollectionUtils.isEmpty(pageSource.getList())) {
            return new RemotePage<>(
                    Lists.newArrayList(),
                    pageSource.getTotal(),
                    pageSource.getPages(),
                    pageSource.getPageNum(),
                    pageSource.getPageSize(),
                    pageSource.isHasNextPage()
            );
        }
        return new RemotePage<>(
                pageSource.getList().stream().map(source -> copyProperties(source, targetClass, ext)).collect(Collectors.toList()),
                pageSource.getTotal(),
                pageSource.getPages(),
                pageSource.getPageNum(),
                pageSource.getPageSize(),
                pageSource.isHasNextPage()
        );
    }

    /**
     * MAP转化为对应的实体类
     *
     * @param map
     * @param targetClass
     * @return
     * @param <T>
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> targetClass) {
        if (Objects.isNull(map)) {
            return null;
        }
        T result = ReflectUtil.newInstance(targetClass);
        Map<String, Field> fieldMap = ReflectUtil.getFieldMap(targetClass);
        fieldMap.forEach((k, v) -> {
            Object value;
            if (map.containsKey(k) && Objects.nonNull(value = map.get(k))) {
                ReflectUtil.setFieldValue(result, v, value);
            }
        });
        return result;
    }

    /**
     * 获取缓存的基础拷贝
     *
     * @param sourceClass
     * @param targetClass
     * @return
     * @param <S>
     * @param <T>
     */
    private static <S, T> SimpleBeanCopier getSimpleBeanCopier(final Class<S> sourceClass, final Class<T> targetClass) {
        String key = Joiner.on(CommonBizConstant.DEFAULT_JOINER).join(sourceClass.getName(), targetClass.getName());
        SimpleBeanCopier copier = COPIER_MAP.get(key);
        if (Objects.nonNull(copier)) {
            return copier;
        }
        copier = getClone();
        copier.setSourceClass(sourceClass);
        copier.setTargetClass(targetClass);
        copier.init();
        COPIER_MAP.putIfAbsent(key, copier);
        return copier;
    }

    /**
     * 字节码拷贝{@link SimpleBeanCopier}
     *
     * @return
     */
    private static SimpleBeanCopier getClone() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oop = new ObjectOutputStream(bos);
            oop.writeObject(copier);
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (SimpleBeanCopier) ois.readObject();
        } catch (Exception e) {
            return new SimpleBeanCopier();
        }
    }
}
