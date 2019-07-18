package com.building.ipi.common.entity;

import java.util.List;

/**
 * @author Created by xq on 2017/12/16.
 */
public class JSONListResult<T> extends Result {
    private static final long serialVersionUID = 1007725639001335417L;
    private List<T> dataList;

    public List<T> getData() {
        return this.dataList;
    }

    public void setData(List<T> dataList) {
        this.dataList = dataList;
    }

    public JSONListResult() {
    }

    public JSONListResult(Boolean success, int statusCode, String message) {
        super(success, statusCode, message);
    }

    public JSONListResult(List<T> dataList) {
        this.dataList = dataList;
        super.setSuccess(true);
    }

    public JSONListResult(List<T> dataList, String message) {
        this.dataList = dataList;
        super.setMessage(message);
        super.setSuccess(true);
    }

    public JSONListResult(List<T> dataList, String message, boolean success) {
        this.dataList = dataList;
        super.setMessage(message);
        super.setSuccess(success);
    }
}