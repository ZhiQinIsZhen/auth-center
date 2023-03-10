package com.lyz.auth.common.controller.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lyz.auth.common.controller.deserialier.JsonTrimDeserializer;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;
import java.util.Optional;

/**
 * Desc:mvc auto config
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 10:30
 */
@Configuration
@AutoConfigureOrder(value = Ordered.HIGHEST_PRECEDENCE)
@ComponentScan(basePackages = {"com.lyz.auth.common.controller"})
public class WebMvcAutoConfig extends WebMvcConfigurationSupport {

    /**
     * 允许加载本地静态资源
     *
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/");
        super.addResourceHandlers(registry);
    }

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.extendMessageConverters(converters);
        if (!CollectionUtils.isEmpty(converters)) {
            Optional<HttpMessageConverter<?>> optional = converters.stream().filter(item -> item instanceof MappingJackson2HttpMessageConverter).findFirst();
            optional.ifPresent(item -> {
                MappingJackson2HttpMessageConverter converter = (MappingJackson2HttpMessageConverter) item;
                ObjectMapper objectMapper = converter.getObjectMapper();
                //生成JSON时,将所有Long转换成String
                SimpleModule simpleModule = new SimpleModule();
                simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
                simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
                simpleModule.addDeserializer(String.class, new JsonTrimDeserializer());
                objectMapper.registerModule(simpleModule);
                //BigDecimal转化为PlainToString
                objectMapper.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
            });
        }
    }
}
