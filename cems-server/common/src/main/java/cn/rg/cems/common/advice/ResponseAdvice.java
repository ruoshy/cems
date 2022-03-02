package cn.rg.cems.common.advice;

import cn.rg.cems.common.model.Result;
import cn.rg.cems.common.model.ReturnCode;
import cn.rg.cems.common.util.ResultData;
import com.alibaba.fastjson.JSON;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (o instanceof ResultData) {
            ResultData rd = (ResultData) o;
            return rd.getResult();
        }
        return ResultData.getResult(ReturnCode.SUCCESS, o);
    }
}
