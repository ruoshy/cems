package cn.rg.cems.common.handle;

import cn.rg.cems.common.model.ReturnCode;
import cn.rg.cems.common.util.ResultData;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LogoutHandlerImpl implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            ResultData.getResult(ReturnCode.FAIL, null).callback(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}