package cn.rg.cems.common.handle;

import cn.rg.cems.common.model.ReturnCode;
import cn.rg.cems.common.util.ResultData;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        JSONObject data = new JSONObject();
        data.put("auth", authentication.getAuthorities());
        data.put("principal", authentication.getPrincipal());
        ResultData.getResult(ReturnCode.SUCCESS, data).callback(response);
    }
}