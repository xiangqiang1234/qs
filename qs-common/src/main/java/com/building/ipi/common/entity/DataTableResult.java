package com.building.ipi.common.entity;

import java.util.List;

/**
 * @author Created by xq on 2017/12/16.
 */
public class DataTableResult<T> extends Result {

    private static final long serialVersionUID = 7649445642752121137L;
    protected String sEcho;
    protected int iTotalRecords;
    protected int iTotalDisplayRecords;
    protected List<T> aaData = null;

    public DataTableResult() {
    }

    public DataTableResult(String sEcho, int iTotalRecords, int iTotalDisplayRecords, List<T> aaData) {
        this.sEcho = sEcho;
        this.iTotalRecords = iTotalRecords;
        this.iTotalDisplayRecords = iTotalDisplayRecords;
        this.aaData = aaData;
    }

    public String getsEcho() {
        return this.sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public int getiTotalRecords() {
        return this.iTotalRecords;
    }

    public void setiTotalRecords(int iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public int getiTotalDisplayRecords() {
        return this.iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public List<T> getAaData() {
        return this.aaData;
    }

    public void setAaData(List<T> aaData) {
        this.aaData = aaData;
    }
}
