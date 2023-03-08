# Springboot Security Demo

[![Build Status](https://img.shields.io/badge/Build-ZhiQinlsZhen-red)](https://github.com/ZhiQinIsZhen/spring-security-demo)
![Maven](https://img.shields.io/maven-central/v/org.apache.dubbo/dubbo.svg)
![License](https://img.shields.io/github/license/alibaba/dubbo.svg)
![Springboot Version](https://img.shields.io/badge/Springboot-2.7.1-brightgreen)
![Device Version](https://img.shields.io/badge/Device-1.1.5.RELEASE-brightgreen)
![SpringJwt Version](https://img.shields.io/badge/SpringJwt-1.1.1.RELEASE-brightgreen)
![Device Version](https://img.shields.io/badge/jjwt-0.9.1-brightgreen)
![Dubbo Version](https://img.shields.io/badge/Dubbo-3.0.10-brightgreen)
![Mybatis-plus Version](https://img.shields.io/badge/MybatisPlus-3.5.2-brightgreen)
![Swagger Version](https://img.shields.io/badge/knife4j-2.0.9-brightgreen)

---

这是一个基于Springboot的框架，以及Apache Dubbo的关于Security的Demo。

---

### 项目结构
1.**lyz-api**：controller网关层

2.**lyz-common**：通用包的框架

3.**lyz-auth**：通用认证框架（基于security）

4.**lyz-service**：Dubbo的服务提供者

---

### common结构说明

1.**common-util**: 通用工具类框架
> + DateUtil: 日期工具类
> + IPUtil: IP地址获取工具类
> + JsonMapperUtil: Json转化工具类
> + PatternUtil: 正则表达式工具类

2.**common-remote**: 通用Dubbo远程接口框架(包含了参数验证器:validation)
> + RemoteServiceException: 业务异常的基类，每个服务自己的业务异常可以继承该接口
> + IExceptionCodeService: 业务异常错误枚举接口，每个服务自己的错误枚举可以继承该接口
> + BasePageBO: 分页查询的基类
> + RemotePage: 分页查询的返回类

3.**common-dao**: 通用DAO层的框架(基于Mybatis-plus)
> + 自动装配类中，加入了分页插件，以及乐观锁插件
> + 原数据Handler中装配了inset、update自动填充属性
> + BaseDO: 该DO是数据库模型的基类，里面定义了模型中通用的字段

4.**common-core**: 通用核心框架
> + CommonCloneUtil: 对象深拷贝，基于**BeanCopier**，有单个对象Object，数组对象List以及分页对象RemotePage
> + Dubbo Filter: 针对服务提供者发生业务异常时，可以向服务调用者直接抛出该业务异常，而不需要包装
> + RandomUtil: 随机数生产工具类

5.**common-controller**: 通用web或者网关层框架
> + GlobalControllerExceptionAdvice: 全局异常Advice
> + SwaggerBaseConfig: Swagger配置的基础类，每个服务网关可以继承该类
> + ErrorApiController: 错误重定向网关
> + Result、PageResult: 网关结果返回包装类

---

### auth结果说明

1.**auth-client**: 认证客户端jar
> + AuthExceptionHandleAdvice: 认证网关的Advice
> + Anonymous: 匿名访问注解
> + WebSecurityClientConfig: Security的配置类
> + JwtAuthenticationTokenFilter: JWT认证的过滤器
> + UserDetailsServiceImpl: Security的用户获取接口(UserDetailsService)的实现类

2.**auth-server**: 认证服务中心
> + 里面包含了每个认证服务对应的JWT生成的秘钥
> + 里面包含了解析JWT的工具类
> + 里面好汉了每个服务认证时调用Dubbo服务提供者的调用配置
> + RemoteAuthService是每个网关层服务提供者都要实现的Dubbo接口






    

    