package cn.rg.cems.enter;

import cn.rg.cems.common.entity.Examinee;
import cn.rg.cems.common.entity.SignUp;
import cn.rg.cems.enter.service.BasicService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class EnterApplicationTests {

    @Resource
    private BasicService basicService;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Test
    void contextLoads() {
    }

}
