package com.building.ipi.web.system.user.dao;

import com.building.ipi.common.feature.orm.mybatis.Page;
import com.building.ipi.common.generic.GenericDao;
import com.building.ipi.web.system.datapermission.model.DeptPermissionModel;
import com.building.ipi.web.common.TreeModel;
import com.building.ipi.web.system.user.model.SysUser;
import com.building.ipi.web.system.user.model.SysUserExample;
import java.util.List;

import com.building.ipi.web.system.user.model.UserModel;
import org.apache.ibatis.annotations.Param;

/**
 * @author yuzj
 */
public interface SysUserMapper extends GenericDao<SysUser, String> {
    int countByExample(SysUserExample example);

    int deleteByExample(SysUserExample example);

    @Override
    int deleteByPrimaryKey(String id);

    int insert(SysUser record);

    @Override
    int insertSelective(SysUser record);

    List<SysUser> selectByExample(SysUserExample example);

    @Override
    SysUser selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysUser record, @Param("example") SysUserExample example);

    int updateByExample(@Param("record") SysUser record, @Param("example") SysUserExample example);

    @Override
    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
    /**
     * 用户登录验证查询
     *
     * @param record
     * @return
     */
    SysUser authentication(@Param("record") SysUser record);

    /**
     * 查询用户列表
     * @param page
     * @param param
     * @param deptModel
     * @return
     */
    List<UserModel> selectPageList(Page<UserModel> page, @Param("param") String param, @Param("deptModel") DeptPermissionModel deptModel);

    /**
     * 获取用户详细信息
     * @param record
     * @return
     */
    UserModel getUserModel(@Param("record") SysUser record);

    /**
     * 保存用户与角色对应关系信息
     * @param id
     * @param userId
     * @param roleId
     * @return
     */
    int addUserRole(@Param("id") String id,@Param("userId") String userId,@Param("roleId") String roleId);

    /**
     * 修改用户与角色对应关系信息
     * @param userId
     * @param roleId
     * @return
     */
    int updateUserRole(@Param("userId") String userId,@Param("roleId") String roleId);

    /**
     * 其他模块选择用户
     *
     * @param page
     * @param type
     * @param deptModel
     * @return
     */
    List<UserModel> getUserList(Page<UserModel> page, @Param("type") String type, @Param("deptModel") DeptPermissionModel deptModel);
}