package com.lyz.auth.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/8 15:18
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestUtil.class})
public class PatternUtilTest {


    @Test
    public void test() {
        String email = "liyangzhen0114@foxmail.com";
        log.info("is email : {}", PatternUtil.matchEmail(email));
    }
}