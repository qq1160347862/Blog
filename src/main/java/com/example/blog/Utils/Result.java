package com.example.blog.Utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Result {
    private Boolean success;
    private Integer code;
    private String message;
    private Map<String,Object> data = new HashMap<>();
    private static Integer SUCCESS = 2000;
    private static Integer ERROR = 2001;


    private Result(){}

    //静态方法
    public static Result ok() {
        Result r = new Result();
        r.setSuccess(true);
        r.setCode(SUCCESS);
        r.setMessage("成功");
        return r;
    }
    public static Result ok(String msg) {
        Result r = new Result();
        r.setSuccess(true);
        r.setCode(SUCCESS);
        r.setMessage(msg);
        return r;
    }

    public static Result error() {
        Result r = new Result();
        r.setSuccess(false);
        r.setCode(ERROR);
        r.setMessage("失败");
        return r;
    }
    public static Result error(String msg) {
        Result r = new Result();
        r.setSuccess(false);
        r.setCode(ERROR);
        r.setMessage(msg);
        return r;
    }

    //设置Result类的data属性
    public Result data(String key, Object value){
        this.data.put(key,value);
        return this;
    }
    public Result data(Map<String,Object> map){
        this.setData(map);
        return this;
    }
}
