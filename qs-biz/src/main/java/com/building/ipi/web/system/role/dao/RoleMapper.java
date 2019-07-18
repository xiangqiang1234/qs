package com.building.ipi.web.system.role.dao;

import com.building.ipi.common.feature.orm.mybatis.Page;
import com.building.ipi.common.generic.GenericDao;
import com.building.ipi.web.common.TreeModel;
import com.building.ipi.web.system.role.model.Role;
import com.building.ipi.web.system.role.model.RoleExample;
import com.building.ipi.web.system.role.model.RolePermissionModel;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 角色Dao 接口
 *
 * @author StarZou
 * @since 2014年7月5日 上午11:55:59
 **/
public interface RoleMapper extends GenericDao<Role, String> {
    int countByExample(RoleExample example);

    int deleteByExample(RoleExample example);
    @Override
    int deleteByPrimaryKey(String id);

    int insert(Role record);
    @Override
    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);
    @Override
    Role selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);
    @Override
    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    /**
     * 通过用户id 查询用户 拥有的角色
     *
     * @param userId
     * @return
     */
    List<Role> selectRolesByUserId(String userId);
    /**
     * 查询角色列表 分页查询
     *
     * @param page
     * @return
     */
    List<Role> selectPageList(Page<Role> page);

    /**
     * 获取权限树
     * @param id 要分配的用户的角色ID
     * @param userRoleId  当前用户角色ID
     * @return
     */
    List<TreeModel> getPermissionTree(@Param("id") String id,@Param("userRoleId") String userRoleId);

    /**
     * 获取权限树
     * @param id 要分配的用户的角色ID
     * @return
     */
    List<TreeModel> getPermissionTree2(@Param("id") String id);

    /**
     * 角色赋权
     * @param list
     * @param id
     * @return
     */
    void setPermission(@Param("list") List<RolePermissionModel> list,@Param("roleId") String id);

    /**
     * 角色赋权
     * @param list
     * @param id
     * @return
     */
    void setPermission2(@Param("list") List<RolePermissionModel> list,@Param("roleId") String id);

    /**
     * 删除角色的权限
     * @param id
     * @return
     */
    void deletePermission(@Param("id") String id);
}