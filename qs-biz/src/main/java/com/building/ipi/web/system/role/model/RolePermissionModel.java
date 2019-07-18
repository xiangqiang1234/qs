package com.building.ipi.web.system.role.model;

/**
 * 角色与权限对应表
 * @author yuzj 2017-12-26
 */
public class RolePermissionModel {
    private String id;
    private String roleId;
    private String permissionId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }
}
