package cn.rg.cems.common.model;

public enum ReturnCode {

    SUCCESS(200, "请求成功"),
    FAIL(400, "请求失败"),
    ERROR(-1, "请求异常"),
    NOT_LOGIN(101, "请求失败,请登录"),
    ERROR_INFO(102, "账户名或密码输入错误,登录失败"),
    NOT_PERMISSION(103, "权限不足"),
    ACCOUNT_LOCK(104, "账号被锁定");

    private final int state;
    private final String message;

    ReturnCode(int state, String message) {
        this.state = state;
        this.message = message;
    }

    public int getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }
}
