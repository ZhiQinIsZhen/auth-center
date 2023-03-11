package com.lyz.auth.api.admin.controller.test;

import com.lyz.auth.api.admin.event.test.TestHelloEvent;
import com.lyz.auth.common.controller.result.Result;
import com.lyz.auth.common.util.DateUtil;
import com.lyz.auth.security.client.annotation.Anonymous;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Desc:Test
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 11:11
 */
@Api(tags = "接口测试")
@ApiResponses(value = {
        @ApiResponse(code = 0, message = "成功"),
        @ApiResponse(code = 1, message = "失败")
})
@Slf4j
@Anonymous
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private ApplicationContext applicationContext;

    @ApiOperation("你好")
    @GetMapping("/hello")
    public Result<String> register() {
        String msg = "lyz，你好！现在是北京时间: ".concat(DateUtil.formatDate(DateUtil.currentDate()));
        TestHelloEvent event = new TestHelloEvent(this, msg);
        applicationContext.publishEvent(event);
        return Result.success(msg);
    }
}
