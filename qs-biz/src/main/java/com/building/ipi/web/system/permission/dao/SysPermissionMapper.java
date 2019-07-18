package com.building.ipi.web.system.permission.dao;

import com.building.ipi.common.feature.orm.mybatis.Page;
import com.building.ipi.common.generic.GenericDao;
import com.building.ipi.web.system.permission.model.SysPermission;
import com.building.ipi.web.system.permission.model.SysPermissionExample;
import java.util.List;
import com.building.ipi.web.system.permission.model.SysPermissionModel;
import com.building.ipi.web.common.TreeModel;
import org.apache.ibatis.annotations.Param;

public interface SysPermissionMapper extends GenericDao<SysPermission, String> {
    int countByExample(SysPermissionExample example);

    int deleteByExample(SysPermissionExample example);

    @Override
    int deleteByPrimaryKey(String id);

    int insert(SysPermission record);

    @Override
    int insertSelective(SysPermission record);

    List<SysPermission> selectByExample(SysPermissionExample example);

    @Override
    SysPermission selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysPermission record, @Param("example") SysPermissionExample example);

    int updateByExample(@Param("record") SysPermission record, @Param("example") SysPermissionExample example);

    @Override
    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);

    /**
     * 通过角色id 查询角色 拥有的权限
     *
     * @param roleId
     * @return
     */
    List<SysPermission> selectPermissionsByRoleId(String roleId);

    /**
     * 查询列表
     *
     * @param page
     * @param sysPermission
     * @return
     */
    List<SysPermission> selectPageList(Page<SysPermission> page, @Param("sysPermission") SysPermission sysPermission);

    /**
     * 查询树
     *
     * @return
     */
    List<TreeModel> selectTree(@Param("model") SysPermission model);

    /**
     * 查询单个数据
     *
     * @param id
     * @return
     */
    SysPermissionModel selectOneById(@Param("id") String id);

    /**
     * 根据角色获取权限
     *
     * @param roleId
     * @return
     */
    List<SysPermission> getPermissionListByRoleId(@Param("roleId") String roleId);
}