package cn.rg.cems.enter.controller;

import cn.rg.cems.common.entity.Order;
import cn.rg.cems.common.entity.SignUp;
import cn.rg.cems.common.model.Result;
import cn.rg.cems.common.model.ReturnCode;
import cn.rg.cems.common.util.ResultData;
import cn.rg.cems.enter.service.BasicService;
import com.alibaba.fastjson.JSON;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class EnterController {

    @Resource
    private BasicService basicService;

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public Order signUp(@RequestBody SignUp signUp) {
        if (basicService.signUp(signUp))
            return signUp.getOrder();
        return null;
    }

    @RequestMapping("/pay")
    public Order pay(@RequestBody SignUp signUp) {
        if (basicService.pay(signUp)) {
            return signUp.getOrder();
        }
        return null;
    }
}
