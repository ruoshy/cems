package cn.rg.cems.enter.service;

import cn.rg.cems.common.entity.Order;
import cn.rg.cems.common.entity.SignUp;
import cn.rg.cems.enter.mapper.OrderMapper;
import cn.rg.cems.enter.mapper.SignUpMapper;
import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class BasicService {

    @Resource
    private SignUpMapper signUpMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 支付成功读取缓存中的报名信息
     * 添加订单信息、报名信息到数据库中
     *
     * @param s 报名信息
     * @return 操作结果
     */
    public boolean pay(SignUp s) {
        SignUp sign = findSignUpInCache(s);
        if (sign == null)
            return false;
        Order order = sign.getOrder();
        orderMapper.add(order);
        sign.setOrderId(order.getId());
        signUpMapper.add(sign);
        s.setOrder(order);
        return true;
    }

    /**
     * 从缓存中查找考生报名信息
     *
     * @param signUp 考试信息
     * @return 报名信息
     */
    public SignUp findSignUpInCache(SignUp signUp) {
        Object obj = redisTemplate.opsForValue().get("signUp_" + signUp.getExamineeId() + "_" + signUp.getExamId());
        if (obj == null)
            return null;
        return JSON.parseObject((String) obj, SignUp.class);
    }

    /**
     * 报名信息缓存在redis中有效期15分钟
     *
     * @param signUp 报名信息
     * @return 操作结果
     */
    public boolean signUp(SignUp signUp) {
        Order order = new Order();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");
        order.setId(sdf.format(new Date()));
        order.setDate(new Date());
        order.setMoney(120f);
        signUp.setOrder(order);
        String str = JSON.toJSONString(signUp);
        redisTemplate.opsForValue().set("signUp_" + signUp.getExamineeId() + "_" + signUp.getExamId(),
                str, 15, TimeUnit.MINUTES);
        return true;
    }

}
