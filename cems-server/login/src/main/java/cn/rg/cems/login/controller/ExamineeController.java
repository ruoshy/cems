package cn.rg.cems.login.controller;

import cn.rg.cems.common.entity.Account;
import cn.rg.cems.common.entity.Examinee;
import cn.rg.cems.common.entity.SignUp;
import cn.rg.cems.login.service.AccountService;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ExamineeController {

    @Resource
    private AccountService accountService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Account register(@RequestBody Examinee examinee) {
        accountService.register(examinee);
        return null;
    }

}
