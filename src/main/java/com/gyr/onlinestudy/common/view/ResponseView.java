package com.gyr.onlinestudy.common.view;

import java.io.Serializable;
import java.util.List;

public class ResponseView<T> implements Serializable {


    private String code = CODE_SUCCESS;//编码,表示成功
    private String message;//消息
    private List<T> data;//数据

    public static final String CODE_SUCCESS = "y";
    public static final String CODE_FAIL = "n";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }


}
