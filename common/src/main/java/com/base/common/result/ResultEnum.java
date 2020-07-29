package com.base.common.result;


public enum ResultEnum {

    // 成功
    SUCCESS(1, Message.REQUEST_SUCCESS),

    // 参数不符合要求
    PARAMS_ERROR(101, Message.PARAMS_ERROR),

    // 用户未登录
    USER_NOT_LOGIN(102, Message.USER_NOT_LOGIN),

    // 失败
    FAIL(999, Message.PROCESS_ERROR),

    ;

    public int code;
    public String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
