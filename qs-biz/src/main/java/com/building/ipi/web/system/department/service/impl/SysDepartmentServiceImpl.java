package com.building.ipi.web.system.department.service.impl;

import com.alibaba.fastjson.JSON;
import com.building.ipi.common.entity.Result;
import com.building.ipi.common.feature.orm.mybatis.Page;
import com.building.ipi.common.generic.GenericDao;
import com.building.ipi.common.generic.GenericServiceImpl;
import com.building.ipi.common.util.ApplicationUtils;
import com.building.ipi.web.common.SessionInfoUtil;
import com.building.ipi.web.system.datapermission.model.SysDataPermission;
import com.building.ipi.web.system.datapermission.service.DataPermissionService;
import com.building.ipi.web.system.department.dao.SysDepartmentMapper;
import com.building.ipi.web.system.department.model.SysDepartment;
import com.building.ipi.web.system.department.model.SysDepartmentExample;
import com.building.ipi.web.system.department.model.SysDepartmentModel;
import com.building.ipi.web.system.department.service.SysDepartmentService;
import com.building.ipi.web.common.TreeModel;
import com.building.ipi.web.system.user.dao.SysUserMapper;
import com.building.ipi.web.system.user.model.SysUser;
import com.building.ipi.web.system.user.model.SysUserExample;
import com.building.ipi.web.system.user.model.UserModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class SysDepartmentServiceImpl extends GenericServiceImpl<SysDepartment, String> implements SysDepartmentService {

    @Resource
    private SysDepartmentMapper sysDepartmentMapper;
    @Resource
    private SysUserMapper userMapper;
    @Resource
    private DataPermissionService dataPermissionService;

    private static final String ADMIN = "admin";

    @Override
    public GenericDao<SysDepartment, String> getDao() {
        return sysDepartmentMapper;
    }

    @Override
    public Page<SysDepartmentModel> selectPageList(Page<SysDepartmentModel> page, SysDepartmentModel model) {
        List<SysDepartmentModel> list = sysDepartmentMapper.selectPageList(page, model, dataPermissionService.getDeptPermissionModel());
        page.setResult(list);
        return page;
    }

    @Override
    public List<TreeModel> selectTree(String userDeptId,String type) {
        List<TreeModel> treeModelList;
        String serialNumber = "";
        String deptId = SessionInfoUtil.getSessionUser().getDepartmentId();
        boolean isAdmin = SessionInfoUtil.getSessionUser().getRoleSign().equals(ADMIN);
        if (!isAdmin) {
            serialNumber = SessionInfoUtil.getSessionUser().getSerialNumber();
        }
        if(type == null || "".equals(type)){
            treeModelList = sysDepartmentMapper.selectTree(dataPermissionService.getDeptPermissionModel());
        }else{
            if("1".equals(type)){
                //获取部门权限树（及要分配的部门已存在的权限）
                treeModelList = sysDepartmentMapper.selectPermissionTree(serialNumber,type,userDeptId);
                if(!isAdmin){
                    //获取当前操作用户的部门数据权限
                    SysDataPermission sysDataPermission = new SysDataPermission();
                    sysDataPermission.setDeptId(deptId);
                    sysDataPermission.setType("1");
                    List<SysDataPermission> modelList = dataPermissionService.getModelList(sysDataPermission);
                    //筛选当前用户能够分配的数据权限
                    for(TreeModel treeModel : treeModelList){
                        boolean nocheck = true;
                        for (SysDataPermission model : modelList){
                            if(treeModel.getId().equals(model.getDeptId())){
                                nocheck = false;
                                break;
                            }
                        }
                        treeModel.setNocheck(nocheck);
                    }
                }
            }else{
                SysUser user = userMapper.selectByPrimaryKey(userDeptId);
                //获取部门权限树（及要分配的用户已存在的权限）
                treeModelList = sysDepartmentMapper.selectPermissionTreeByUser(serialNumber,userDeptId,user.getDepartmentId());
                if(!isAdmin){
                    //获取当前操作用户的部门数据权限
                    List<String> userDeptIds = new ArrayList<>();
                    userDeptIds.add(deptId);
                    userDeptIds.add(userDeptId);
                    List<SysDataPermission> modelList = dataPermissionService.getModelList(userDeptIds);
                    //筛选当前用户能够分配的数据权限
                    for(TreeModel treeModel : treeModelList){
                        boolean nocheck = true;
                        for (SysDataPermission model : modelList){
                            if(treeModel.getId().equals(model.getDeptId())){
                                nocheck = false;
                                break;
                            }
                        }
                        treeModel.setNocheck(nocheck);
                    }
                }
            }
        }
        return treeModelList;
    }

    @Override
    public List<SysDepartment> selectByperNo(String no) {
        SysDepartmentExample sysDepartmentExample = new SysDepartmentExample();
        sysDepartmentExample.createCriteria().andNoEqualTo(no);
        return sysDepartmentMapper.selectByExample(sysDepartmentExample);
    }

    @Override
    public List<SysUser> selectUserBydepId(String id) {
        SysUserExample userExample = new SysUserExample();
        userExample.createCriteria().andDepartmentIdEqualTo(id);

        return userMapper.selectByExample(userExample);
    }

    @Override
    public int deleteDepart(String id) {

        return sysDepartmentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public SysDepartmentModel selectOneById(String id) {

        return sysDepartmentMapper.selectById(id);
    }

    @Override
    public List<SysDepartment> getDeptList() {
        List<SysDepartment> list = new ArrayList<>();
        UserModel userModel = SessionInfoUtil.getSessionUser();
        if (ADMIN.equals(userModel.getRoleSign())) {
            SysDepartmentExample sysDepartmentExample = new SysDepartmentExample();
            sysDepartmentExample.createCriteria().andPidEqualTo("");
            list = sysDepartmentMapper.selectByExample(sysDepartmentExample);
        } else {
            list.add(sysDepartmentMapper.selectByPrimaryKey(userModel.getDepartmentId()));
        }
        return list;
    }

    @Override
    public String addModel(SysDepartmentModel model) {
        Result result = new Result();
        String id = ApplicationUtils.getUUID();
        model.setId(id);
        model.setCreated(new Date());
        model.setCreatedBy(SessionInfoUtil.getSessionUser().getId());
        //获取级联序列号
        String serialNumber = sysDepartmentMapper.getSerialNumber(model.getPid(),model.getpSerialNumber());
        model.setSerialNumber(serialNumber);
        SysDataPermission sysDataPermission = new SysDataPermission();
        sysDataPermission.setType("1");
        sysDataPermission.setUserDeptId(id);
        sysDataPermission.setDeptId(id);
        try {
            sysDepartmentMapper.insert(model);
            dataPermissionService.insert(sysDataPermission);
            result.setMessage("保存成功！");
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMessage("保存失败！");
            result.setSuccess(false);
        }
        return JSON.toJSONString(result);
    }

    @Override
    public String updateModel(SysDepartmentModel model) {
        Result result = new Result();
        model.setLastModified(new Date());
        model.setLastModifiedBy(SessionInfoUtil.getSessionUser().getId());
        try {
            String serialNumber = sysDepartmentMapper.getSerialNumber(model.getPid(),model.getpSerialNumber());
            int count = sysDepartmentMapper.updateByPrimaryKeySelective(model);
            if (count > 0) {
                //更新级联序列号
                if(!"".equals(model.getpSerialNumber()) && model.getpSerialNumber() != null){
                    sysDepartmentMapper.updateSerialNumber(model.getSerialNumber(),serialNumber);
                }
                result.setMessage("修改成功！");
                result.setSuccess(true);
            } else {
                result.setMessage("修改失败！");
                result.setSuccess(false);
            }
        } catch (Exception e) {
            result.setMessage("修改失败！");
            result.setSuccess(false);
        }
        return JSON.toJSONString(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setDataPermission(String[] ids, String id, String type) {
        List<SysDataPermission> modelList = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            SysDataPermission model = new SysDataPermission();
            model.setType(type);
            model.setUserDeptId(id);
            model.setDeptId(ids[i]);

            modelList.add(model);
        }
        //删除权限
        dataPermissionService.deleteDataPermission(id);
        //保存权限
        dataPermissionService.setDataPermission(modelList);
    }
}
