package com.info.back.utils;

/**
 * @author cqry_2016
 * @create 2018-08-30 11:13
 **/

public class Result<T> {

    public static Result OK = new Result();

    private String result = "success";
    private String message = "";

    private T data;

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    public Result(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public static Result success() {
        return new Result();
    }

    public static Result success(Object o) {
        return new Result(o);
    }

    public static Result error(String msg) {
        Result r = new Result();
        r.setResult("fail");
        r.setMessage(msg);
        return r;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
