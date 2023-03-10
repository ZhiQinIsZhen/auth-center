package com.lyz.auth.security.client.config;

import com.google.common.collect.Sets;
import com.lyz.auth.common.util.JsonMapperUtil;
import com.lyz.auth.security.client.annotation.Anonymous;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Objects;
import java.util.Set;

/**
 * Desc:anonymous mapping
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 14:30
 */
@Slf4j
@Configuration
public class AnonymousMappingConfig implements ApplicationContextAware, InitializingBean, Ordered {

    private static Set<String> anonymousMappings = Sets.newHashSet();
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() {
        RequestMappingHandlerMapping handlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        if (Objects.isNull(handlerMapping)) {
            return;
        }
        handlerMapping.getHandlerMethods().forEach((k, v) -> {
            boolean hasAnonymous = v.getBeanType().isAnnotationPresent(Anonymous.class) ? true : v.hasMethodAnnotation(Anonymous.class) ? true : false;
            if (hasAnonymous) {
                anonymousMappings.addAll(k.getPatternsCondition().getPatterns());
            }
        });
        log.info("Anonymous mappings : {}", JsonMapperUtil.toJSONString(anonymousMappings));
    }

    /**
     * 获取免鉴权的mappings
     * can see annotation {@link Anonymous}
     *
     * @return
     */
    public static Set<String> getAnonymousMappings() {
        return anonymousMappings;
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
