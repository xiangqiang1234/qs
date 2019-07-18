package com.building.ipi.web.system.department.service;

import com.building.ipi.common.feature.orm.mybatis.Page;
import com.building.ipi.common.generic.GenericService;
import com.building.ipi.web.system.department.model.SysDepartment;
import com.building.ipi.web.system.department.model.SysDepartmentModel;
import com.building.ipi.web.common.TreeModel;
import com.building.ipi.web.system.user.model.SysUser;

import java.util.List;

/**
 * @author Administrator
 */
public interface SysDepartmentService extends GenericService<SysDepartment, String> {
    /**
     * 列表查询
     *
     * @param page
     * @param model
     * @return
     */
    Page<SysDepartmentModel> selectPageList(Page<SysDepartmentModel> page, SysDepartmentModel model);

    /**
     * 部门树查询
     * @param userDeptId
     * @param type "" 为左侧部门树  1 部门权限树 2 用户权限树
     * @return
     */
    List<TreeModel> selectTree(String userDeptId,String type);

    /**
     * 查询标识是否已使用
     *
     * @param no
     * @return
     */
    List<SysDepartment> selectByperNo(String no);

    /**
     * 检查单位下面是否有用户
     *
     * @param id
     * @return
     */
    List<SysUser> selectUserBydepId(String id);

    /**
     * 删除单位
     *
     * @param id
     * @return
     */
    int deleteDepart(String id);

    /**
     * 查看详情
     *
     * @param id
     * @return
     */
    SysDepartmentModel selectOneById(String id);


    /**
     * 获取单位列表  添加修改用户用
     *
     * @return
     */
    List<SysDepartment> getDeptList();

    /**
     * 新增
     *
     * @param model
     * @return
     */
    String addModel(SysDepartmentModel model);

    /**
     * 修改
     *
     * @param model
     * @return
     */
    String updateModel(SysDepartmentModel model);

    /**
     * 部门数据权限赋权
     *
     * @param ids
     * @param id
     * @param type
     */
    void setDataPermission(String[] ids, String id, String type);
}
