package com.base.common.result;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Result implements Serializable {

    private int code;

    private String msg;

    private Map<String, Object> result;

    public Result(int code, String msg, Map<String, Object> result) {
        this.code = code;
        this.msg = msg;
        this.result = null != result ? result : new HashMap<>();
    }

    public Result(ResultEnum resultEnum) {
        this.code = resultEnum.code;
        this.msg = resultEnum.msg;
        result = new HashMap<>();
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

    public Result setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public Result setResult(Map<String, Object> result) {
        this.result = result;
        return this;
    }

    public Result put(String key, Object value) {
        result.put(key, value);
        return this;
    }

    public static Result getEmptySuccess() {
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), new HashMap<>());
    }

    public static Result getEmptyFail() {
        return new Result(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg(), new HashMap<>());
    }
}
