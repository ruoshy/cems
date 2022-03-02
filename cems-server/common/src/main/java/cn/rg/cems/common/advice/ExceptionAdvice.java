package cn.rg.cems.common.advice;

import cn.rg.cems.common.model.ReturnCode;
import cn.rg.cems.common.util.ResultData;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler
    public ResultData defaultExceptionHandler(Exception e) {
        return new ResultData(ReturnCode.ERROR, e.getMessage());
    }
}
