package com.building.ipi.web.system.datapermission.service;

import com.building.ipi.common.generic.GenericService;
import com.building.ipi.web.system.datapermission.model.DeptPermissionModel;
import com.building.ipi.web.system.datapermission.model.SysDataPermission;

import java.util.List;

/**
 * @Description: 数据权限
 * @author: yuzj
 * @Date: 2019/03/06 08:16
 */
public interface DataPermissionService extends GenericService<SysDataPermission,String> {
    /**
     * 数据赋权
     *
     * @param list
     */
    void setDataPermission(List<SysDataPermission> list);

    /**
     * 删除权限
     *
     * @param userDeptId
     * @return
     */
    void deleteDataPermission(String userDeptId);

    /**
     * 获取用户数据权限  返回值为部门列表
     *
     * @return
     */
    List<String> getUserDataPermission();

    /**
     * 获取用户数据权限  返回值为部门列表字符串
     *
     * @return
     */
    DeptPermissionModel getUserDataPermission2();

    /**
     * 获取部门数据权限
     * @return
     */
    DeptPermissionModel getDeptPermissionModel();

    /**
     * 获取部门数据权限
     *
     * @param model
     * @return
     */
    List<SysDataPermission> getModelList(SysDataPermission model);

    /**
     * 获取部门数据权限
     * @param userDeptIds
     * @return
     */
    List<SysDataPermission> getModelList(List<String> userDeptIds);
}
