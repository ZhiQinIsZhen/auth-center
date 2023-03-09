package com.lyz.auth.security.client.config;

import com.lyz.auth.security.client.constant.SecurityClientConstant;
import com.lyz.auth.security.client.filter.JwtAuthenticationTokenFilter;
import com.lyz.auth.security.client.handler.JwtAuthenticationEntryPoint;
import com.lyz.auth.security.client.handler.RestfulAccessDeniedHandler;
import com.lyz.auth.service.authentication.remote.RemoteAuthenticationService;
import com.lyz.auth.service.authentication.remote.RemoteJwtParseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Desc:spring security client auto config
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 14:22
 */
@Slf4j
@ComponentScan(value = {"com.lyz.auth.security.client"})
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthSecurityClientAutoConfig implements EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .accessDeniedHandler(new RestfulAccessDeniedHandler())
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, SecurityClientConstant.OPTIONS_PATTERNS).permitAll()
                .antMatchers(HttpMethod.GET, SecurityClientConstant.SECURITY_IGNORE_RESOURCES).permitAll()
                /**
                 * {@link com.lyz.security.auth.client.annotation.Anonymous}注解的mappings
                 */
                .antMatchers(AnonymousMappingConfig.getAnonymousMappings().toArray(new String[0])).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(
                        new JwtAuthenticationTokenFilter(SecurityClientConstant.DEFAULT_TOKEN_HEADER_KEY, environment.getProperty(SecurityClientConstant.DUBBO_APPLICATION_NAME_PROPERTY)),
                        UsernamePasswordAuthenticationFilter.class)
                .headers().cacheControl()
                .and().frameOptions().sameOrigin();
        return http.build();
    }

    @DubboReference(group = "auth")
    private RemoteAuthenticationService remoteAuthenticationService;
    @DubboReference(group = "auth")
    private RemoteJwtParseService remoteJwtParseService;

    @Bean("remoteAuthenticationService-auth")
    public RemoteAuthenticationService remoteAuthService() {
        return remoteAuthenticationService;
    }

    @Bean("remoteJwtParseService-auth")
    public RemoteJwtParseService remoteJwtParseService() {
        return remoteJwtParseService;
    }
}
