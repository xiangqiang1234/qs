package com.building.ipi.common.util;

import com.building.ipi.common.entity.DataTableResult;
import com.building.ipi.common.entity.JSONListResult;
import com.building.ipi.common.feature.orm.mybatis.Page;

/**
 * @author Created by xq on 2017/12/16.
 */
public class DatatableUtils {
    public DatatableUtils() {
    }

    public static DataTableResult getDataTableResult(DataTableResult dataTableResult, Page page, String sEcho) {
        if (null != page) {
            dataTableResult.setSuccess(true);
            dataTableResult.setStatusCode(200);
            dataTableResult.setAaData(page.getResult());
            dataTableResult.setsEcho(sEcho);
            dataTableResult.setiTotalDisplayRecords(page.getTotalCount());
            dataTableResult.setiTotalRecords(page.getTotalCount());
        }

        return dataTableResult;
    }

    public static DataTableResult getDataTableResultByList(DataTableResult dataTableResult, JSONListResult jsonListResult, String sEcho) {
        if (null != jsonListResult) {
            dataTableResult.setSuccess(true);
            dataTableResult.setStatusCode(200);
            dataTableResult.setAaData(jsonListResult.getData());
            dataTableResult.setsEcho(sEcho);
            dataTableResult.setiTotalDisplayRecords(jsonListResult.getData().size());
            dataTableResult.setiTotalRecords(jsonListResult.getData().size());
        }

        return dataTableResult;
    }
}
