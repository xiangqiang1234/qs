package com.building.ipi.web.system.permission.service;

import com.building.ipi.common.feature.orm.mybatis.Page;
import com.building.ipi.common.generic.GenericService;
import com.building.ipi.web.system.permission.model.SysPermission;
import com.building.ipi.web.system.permission.model.SysPermissionModel;
import com.building.ipi.web.common.TreeModel;
import com.building.ipi.web.system.rolepermission.model.SysRolePermission;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2017/11/24
 */
public interface PermissionService extends GenericService<SysPermission, String> {

    /**
     * 通过角色id 查询角色 拥有的权限
     *
     * @param roleId
     * @return
     */
    List<SysPermission> selectPermissionsByRoleId(String roleId);

    /**
     * 查询所有权限
     *
     * @return
     */
    List<SysPermission> selectAllPermissions();

    /**
     * 权限列表查询
     *
     * @param page
     * @param sysPermission
     * @return
     */
    Page<SysPermission> selectPageList(Page<SysPermission> page, SysPermission sysPermission);

    /**
     * 查询树
     *
     * @return
     */
    List<TreeModel> selectTree(SysPermission model);

    /**
     * 删除权限
     *
     * @param id
     * @return
     */
    int deletePermission(String id);

    /**
     * 查询单个数据
     *
     * @param id
     * @return
     */
    SysPermissionModel selectOneById(String id);

    /**
     * 查询当前权限是否已经赋值
     *
     * @param id
     * @return
     */
    List<SysRolePermission> selectRoleByperId(String id);

    /**
     * 获取当前用户权限
     *
     * @return
     */
    List<SysPermission> getPermissionList();
}