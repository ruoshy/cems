package cn.rg.cems.common.handle;

import cn.rg.cems.common.model.ReturnCode;
import cn.rg.cems.common.util.ResultData;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        ResultData.getResult(ReturnCode.NOT_PERMISSION, null).callback(response);
    }
}
