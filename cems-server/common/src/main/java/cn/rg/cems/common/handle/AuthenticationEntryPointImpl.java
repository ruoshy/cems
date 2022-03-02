package cn.rg.cems.common.handle;

import cn.rg.cems.common.model.ReturnCode;
import cn.rg.cems.common.util.ResultData;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        ResultData.getResult(ReturnCode.NOT_LOGIN, null).callback(response);
    }
}