package cn.rg.cems.common.util;

import cn.rg.cems.common.model.Result;
import cn.rg.cems.common.model.ReturnCode;

public class ResultData {

    private Result<Object> result;

    public ResultData(ReturnCode returnCode, Object data) {
        result = new Result<>();
        result.setState(returnCode.getState());
        result.setMessage(returnCode.getMessage());
        result.setData(data);
    }

    public Result<Object> getResult() {
        return result;
    }

    public static Result<Object> getResult(ReturnCode returnCode,Object object) {
        Result<Object> result = new Result<>();
        result.setState(returnCode.getState());
        result.setMessage(returnCode.getMessage());
        result.setData(object);
        return result;
    }

}
