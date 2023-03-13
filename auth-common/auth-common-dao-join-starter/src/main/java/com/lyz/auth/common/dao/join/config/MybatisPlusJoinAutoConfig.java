package com.lyz.auth.common.dao.join.config;

import com.baomidou.mybatisplus.core.injector.AbstractSqlInjector;
import com.lyz.auth.common.dao.join.injector.MergeSqlInjector;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/13 9:37
 */
@Configuration
@ConditionalOnExpression("${mybatis-plus.join.enabled:true}")
public class MybatisPlusJoinAutoConfig {

    @Bean
    @Primary
    public MergeSqlInjector sqlInjector(List<AbstractSqlInjector> injectors) {
        return new MergeSqlInjector(injectors);
    }
}
