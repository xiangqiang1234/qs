package com.building.ipi.web.system.datapermission.dao;

import com.building.ipi.common.generic.GenericDao;
import com.building.ipi.web.system.datapermission.model.SysDataPermission;
import com.building.ipi.web.system.datapermission.model.SysDataPermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author Administrator
 */
public interface SysDataPermissionMapper extends GenericDao<SysDataPermission,String>{
    int countByExample(SysDataPermissionExample example);

    int deleteByExample(SysDataPermissionExample example);
    @Override
    int deleteByPrimaryKey(String id);

    int insert(SysDataPermission record);
    @Override
    int insertSelective(SysDataPermission record);

    List<SysDataPermission> selectByExample(SysDataPermissionExample example);
    @Override
    SysDataPermission selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysDataPermission record, @Param("example") SysDataPermissionExample example);

    int updateByExample(@Param("record") SysDataPermission record, @Param("example") SysDataPermissionExample example);
    @Override
    int updateByPrimaryKeySelective(SysDataPermission record);

    int updateByPrimaryKey(SysDataPermission record);

    /**
     * 数据赋权
     *
     * @param list
     * @return
     */
    void setDataPermission(@Param("list") List<SysDataPermission> list);

    /**
     * 删除权限
     *
     * @param userDeptId
     * @return
     */
    void deleteDataPermission(@Param("userDeptId") String userDeptId);

    /**
     * 获取用户数据权限  返回值为部门列表
     *
     * @param userId
     * @param deptId
     * @return
     */
    List<String> getUserDataPermission(@Param("userId") String userId,@Param("deptId") String deptId);
}