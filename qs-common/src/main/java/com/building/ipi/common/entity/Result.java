package com.building.ipi.common.entity;

import java.io.Serializable;

/**
 * Result : 响应的结果对象
 *
 * @author StarZou
 * @since 2014-09-27 16:28
 */
public class Result implements Serializable {
    private static final long serialVersionUID = 6288374846131788743L;
    public static final int statusCode_SUCCESS = 200;
    public static final int statusCode_ERROR = 500;
    public static final int statusCode_OUTTIME = 300;
    private String message;
    private int statusCode = 200;
    private boolean success = true;

    public Result() {
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Result(Boolean success, int statusCode, String message) {
        this.success = success.booleanValue();
        this.statusCode = statusCode;
        this.message = message;
    }
}
