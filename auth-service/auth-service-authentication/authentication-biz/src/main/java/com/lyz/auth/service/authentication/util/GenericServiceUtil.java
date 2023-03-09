package com.lyz.auth.service.authentication.util;

import com.google.common.collect.Lists;
import com.lyz.auth.common.biz.util.BeanUtil;
import com.lyz.auth.common.remote.exception.CommonExceptionCodeEnum;
import com.lyz.auth.service.authentication.exception.RemoteAuthServiceException;
import lombok.experimental.UtilityClass;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 15:56
 */
@UtilityClass
public class GenericServiceUtil {

    private static final Object LOCK = new Object();

    private static final Map<String, GenericService> GENERIC_SERVICE_MAP = new ConcurrentHashMap<>(64);

    /**
     * 获取GenericService
     *
     * @param className
     * @param version
     * @param group
     * @param timeout
     * @param parameters
     * @return
     */
    public static GenericService getByClassName(String className, String version, String group, Integer timeout, Map<String, String> parameters) {
        if (!GENERIC_SERVICE_MAP.containsKey(className)) {
            synchronized (LOCK) {
                if (!GENERIC_SERVICE_MAP.containsKey(className)) {
                    ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
                    // 弱类型接口名
                    reference.setInterface(className);
                    reference.setVersion(version);
                    // 声明为泛化接口
                    reference.setGeneric(true);
                    reference.setGroup(group);
                    reference.setTimeout(timeout);
                    reference.setParameters(parameters);
                    GENERIC_SERVICE_MAP.put(className, reference.get());
                }
            }
        }
        return GENERIC_SERVICE_MAP.get(className);
    }

    public static GenericService getByClassName(String className, String version, String group, Integer timeout) {
        return getByClassName(className, version, group, timeout, null);
    }

    public static GenericService getByClassName(Class clazz, String version, String group, Integer timeout) {
        return getByClassName(clazz.getName(), version, group, timeout);
    }

    /**
     * 调用方法
     *
     * @param methodName
     * @param genericService
     * @param os
     * @param <T>
     * @return
     */
    public static <T> T invoke(String methodName, Class<T> targetClass, GenericService genericService, Object... os) {
        Object result = genericService.$invoke(methodName, paramTypes(os), os);
        if (result instanceof Map) {
            try {
                return BeanUtil.mapToBean((Map<String, Object>) result, targetClass);
            } catch (Exception e) {
                throw new RemoteAuthServiceException(CommonExceptionCodeEnum.REMOTE_SERVICE_FAIL);
            }
        }
        return (T) result;
    }

    public static <T> List<T> invokeList(String methodName, Class<T> targetClass, GenericService genericService, Object... os) {
        Object result = genericService.$invoke(methodName, paramTypes(os), os);
        if (!(result instanceof List)) {
            throw new RemoteAuthServiceException(CommonExceptionCodeEnum.REMOTE_SERVICE_FAIL);
        }
        List<Object> list = (List<Object>) result;
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        return list.stream().map(item -> {
            if (item instanceof Map) {
                try {
                    return BeanUtil.mapToBean((Map<String, Object>) item, targetClass);
                } catch (Exception e) {
                    throw new RemoteAuthServiceException(CommonExceptionCodeEnum.REMOTE_SERVICE_FAIL);
                }
            } else {
                return (T) item;
            }
        }).collect(Collectors.toList());
    }

    /**
     * 根据参数获取参数类型
     *
     * @param os
     * @return
     */
    private static String[] paramTypes(Object... os) {
        String[] paramTypes = null;
        if (Objects.nonNull(os)) {
            paramTypes = new String[os.length];
            for (int i = 0, j = os.length; i < j; i++) {
                paramTypes[i] = os[i].getClass().getName();
            }
        }
        return paramTypes;
    }
}
