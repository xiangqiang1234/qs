package com.building.ipi.web.system.department.dao;

import com.building.ipi.common.feature.orm.mybatis.Page;
import com.building.ipi.common.generic.GenericDao;
import com.building.ipi.web.system.datapermission.model.DeptPermissionModel;
import com.building.ipi.web.system.department.model.SysDepartment;
import com.building.ipi.web.system.department.model.SysDepartmentExample;

import java.util.List;

import com.building.ipi.web.system.department.model.SysDepartmentModel;
import com.building.ipi.web.common.TreeModel;
import org.apache.ibatis.annotations.Param;

/**
 * @author yuzj 2018-03-21
 */
public interface SysDepartmentMapper extends GenericDao<SysDepartment, String> {
    int countByExample(SysDepartmentExample example);

    int deleteByExample(SysDepartmentExample example);

    @Override
    int deleteByPrimaryKey(String id);

    int insert(SysDepartment record);

    @Override
    int insertSelective(SysDepartment record);

    List<SysDepartment> selectByExample(SysDepartmentExample example);

    @Override
    SysDepartment selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysDepartment record, @Param("example") SysDepartmentExample example);

    int updateByExample(@Param("record") SysDepartment record, @Param("example") SysDepartmentExample example);

    @Override
    int updateByPrimaryKeySelective(SysDepartment record);

    int updateByPrimaryKey(SysDepartment record);

    /**
     * 列表查询
     *
     * @param page
     * @param model
     * @param deptModel
     * @return
     */
    List<SysDepartmentModel> selectPageList(Page<SysDepartmentModel> page, @Param("record") SysDepartmentModel model, @Param("deptModel") DeptPermissionModel deptModel);

    /**
     * 查询树
     *
     * @param deptModel
     * @return
     */
    List<TreeModel> selectTree(@Param("deptModel") DeptPermissionModel deptModel);

    /**
     * 查询部门树 部门数据赋权用
     *
     * @param serialNumber
     * @param type
     * @param userDeptId
     * @return
     */
    List<TreeModel> selectPermissionTree(@Param("serialNumber") String serialNumber,@Param("type") String type, @Param("userDeptId") String userDeptId);

    /**
     * 查询部门树 用户数据赋权用
     *
     * @param serialNumber
     * @param userDeptId
     * @param deptId
     * @return
     */
    List<TreeModel> selectPermissionTreeByUser(@Param("serialNumber") String serialNumber,@Param("userDeptId") String userDeptId,@Param("deptId") String deptId);

    /**
     * 查看详情
     *
     * @param id
     * @return
     */
    SysDepartmentModel selectById(@Param("id") String id);

    /**
     * 获取级联序列号
     *
     * @param pid
     * @param pSerialNumber
     * @return
     */
    String getSerialNumber(@Param("pid") String pid,@Param("pSerialNumber") String pSerialNumber);

    /**
     * 更新级联序列号
     *
     * @param serialNumberOld
     * @param serialNumberNew
     * @return
     */
    void updateSerialNumber(@Param("serialNumberOld") String serialNumberOld, @Param("serialNumberNew") String serialNumberNew);
}