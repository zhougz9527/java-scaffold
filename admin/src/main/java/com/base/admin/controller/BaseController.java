package com.base.admin.controller;

import com.base.common.result.Result;
import com.base.common.result.ResultEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.HashMap;
import java.util.Map;

public abstract class BaseController {

    protected static final Logger logger = LogManager.getLogger();

    private ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    protected Map<String, Object> put(String key, Object obj) {
        Map<String, Object> data = null == threadLocal.get() ? new HashMap<>() : threadLocal.get();
        data.put(key, obj);
        threadLocal.set(data);
        return data;
    }

    protected Map<String, Object> put(Map<String, Object> map) {
        Map<String, Object> data = null == threadLocal.get() ? new HashMap<>() : threadLocal.get();
        data.putAll(map);
        threadLocal.set(data);
        return data;
    }

    protected Result success() {
        return response(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg());
    }

    protected Result error(String errorMessage) {
        return response(999, errorMessage);
    }

    protected Result error(ResultEnum resultEnum) {
        return response(resultEnum.getCode(), resultEnum.msg);
    }

    private Result response(int code, String msg) {
        Map<String, Object> data = null == threadLocal.get() ? new HashMap<>() : threadLocal.get();
        threadLocal.remove();
        return new Result(code, msg, data);
    }

}
