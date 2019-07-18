package com.building.ipi.web.system.department.model;

/**
 * @author Administrator on 2017/12/26.
 */
public class SysDepartmentModel extends SysDepartment {
    private String pidName;
    private String areaName;
    private String pSerialNumber;

    public String getpSerialNumber() {
        return pSerialNumber;
    }

    public void setpSerialNumber(String pSerialNumber) {
        this.pSerialNumber = pSerialNumber;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPidName() {
        return pidName;
    }

    public void setPidName(String pidName) {
        this.pidName = pidName;
    }
}
