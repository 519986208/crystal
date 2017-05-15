package com.cly.web;

import lombok.Data;

/**
 * Json结果对象
 */
@Data
public class Result {

    /**
     * 描述代码
     */
    private int    code;

    /**
     * 描述信息
     */
    private String message;

    /**
     * 提供内容封装
     */
    private Object data;

}
