package com.building.ipi.web.system.role.service;

import com.building.ipi.common.feature.orm.mybatis.Page;
import com.building.ipi.common.generic.GenericService;
import com.building.ipi.web.common.TreeModel;
import com.building.ipi.web.system.role.model.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 角色 业务接口
 *
 * @author yuzj
 * @since 2017年12月23日
 **/
public interface RoleService extends GenericService<Role, String> {
    /**
     * 通过用户id 查询用户 拥有的角色
     *
     * @param userId
     * @return
     */
    List<Role> selectRolesByUserId(String userId);

    /**
     * 查询角色列表
     *
     * @param page
     * @return
     */
    Page<Role> selectPageList(Page<Role> page);

    /**
     * 查询角色列表  用于下拉框查询
     *
     * @return
     */
    List<Role> getRoleList();

    /**
     * 获取角色的权限树
     *
     * @param id
     * @return
     */
    List<TreeModel> getPermissionTree(String id);

    /**
     * 角色赋权
     *
     * @param ids
     * @param id
     */
    void setPermission(String[] ids, String id);

    /**
     * 唯一性验证
     *
     * @param request
     * @return
     */
    Boolean roleUnique(HttpServletRequest request);
}
