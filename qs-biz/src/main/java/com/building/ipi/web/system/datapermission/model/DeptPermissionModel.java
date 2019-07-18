package com.building.ipi.web.system.datapermission.model;

import java.util.List;

/**
 * @Description: 部门数据权限Model
 * @author: yuzj
 * @Date: 2019/03/06 08:14
 */
public class DeptPermissionModel{
    private String deptId;
    private List<String> deptIds;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public List<String> getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(List<String> deptIds) {
        this.deptIds = deptIds;
    }
}
