package com.building.ipi.common.feature.orm.mybatis;

import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.List;

/**
 * Mybatis分页参数及查询结果封装. 注意所有序号从1开始.
 *
 * @param <T> Page中记录的类型.
 * @author StarZou
 * @since 2014年5月18日 下午1:34:32
 **/
public class Page<T> extends RowBounds {
    protected int pageNo = 1;
    protected int pageSize = 15;
    protected int offset;
    protected int limit;
    protected List<T> result = new ArrayList();
    protected int totalCount;
    protected int totalPages;
    protected String sEcho;

    private void calcOffset() {
        this.offset = (this.pageNo - 1) * this.pageSize;
    }

    private void calcLimit() {
        this.limit = this.pageSize;
    }

    private void calcPageNo() {
        int PageNoTmp = (this.offset + 1) / this.pageSize;
        if (PageNoTmp == 0) {
            PageNoTmp = 1;
        }

        this.pageNo = PageNoTmp;
    }

    public Page() {
        this.calcOffset();
        this.calcLimit();
    }

    public Page(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.calcOffset();
        this.calcLimit();
    }

    public Page(String sEcho, int offset, int pageSize) {
        this.sEcho = sEcho;
        this.offset = offset;
        this.pageSize = pageSize;
        this.calcPageNo();
        this.calcLimit();
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public int getFirst() {
        return (this.pageNo - 1) * this.pageSize + 1;
    }

    public int getOffset() {
        return this.offset;
    }

    public int getLimit() {
        return this.limit;
    }

    public List<T> getResult() {
        return this.result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        this.totalPages = this.getTotalPages();
    }

    public int getTotalPages() {
        if (this.totalCount < 0) {
            return -1;
        } else {
            int pages = this.totalCount / this.pageSize;
            int var10000;
            if (this.totalCount % this.pageSize > 0) {
                ++pages;
                var10000 = pages;
            } else {
                var10000 = pages;
            }

            return var10000;
        }
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getsEcho() {
        return this.sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }
}
