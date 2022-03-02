package cn.rg.cems.common.handle;

import cn.rg.cems.common.model.ReturnCode;
import cn.rg.cems.common.util.ResultData;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        ReturnCode returnCode;
        if (e instanceof BadCredentialsException)
            returnCode = ReturnCode.ERROR_INFO;
        else if (e instanceof LockedException)
            returnCode = ReturnCode.ACCOUNT_LOCK;
        else
            returnCode = ReturnCode.FAIL;
        ResultData.getResult(returnCode, null).callback(response);
    }
}