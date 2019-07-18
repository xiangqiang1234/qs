package com.building.ipi.web.system.datapermission.service.impl;

import com.building.ipi.common.generic.GenericDao;
import com.building.ipi.common.generic.GenericServiceImpl;
import com.building.ipi.web.common.SessionInfoUtil;
import com.building.ipi.web.system.datapermission.dao.SysDataPermissionMapper;
import com.building.ipi.web.system.datapermission.model.DeptPermissionModel;
import com.building.ipi.web.system.datapermission.model.SysDataPermission;
import com.building.ipi.web.system.datapermission.model.SysDataPermissionExample;
import com.building.ipi.web.system.datapermission.service.DataPermissionService;
import com.building.ipi.web.system.user.model.UserModel;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 数据权限
 * @author: yuzj
 * @Date: 2019/03/06 08:46
 */
@Service
public class DataPermissionServiceImpl extends GenericServiceImpl<SysDataPermission,String> implements DataPermissionService{
    @Resource
    private SysDataPermissionMapper dataPermissionMapper;

    @Override
    public GenericDao<SysDataPermission, String> getDao() {
        return dataPermissionMapper;
    }

    @Override
    public void setDataPermission(List<SysDataPermission> list) {
        dataPermissionMapper.setDataPermission(list);
    }

    @Override
    public void deleteDataPermission(String userDeptId) {
        dataPermissionMapper.deleteDataPermission(userDeptId);
    }
    @Override
    public List<String> getUserDataPermission() {
        Subject subject = SecurityUtils.getSubject();
        //获取session中存放的数据权限
        List<String> deptList = (List<String>)subject.getSession().getAttribute("deptPermissionList");
        //session中无权限 查询数据库
        if(deptList == null){
            UserModel user = SessionInfoUtil.getSessionUser();
            deptList = dataPermissionMapper.getUserDataPermission(user.getId(),user.getDepartmentId());
            //判断是否分配了自己部门权限  默认用户必须拥有自己部门的数据权限
            boolean userDept = false;
            for(String str : deptList){
                if(str.equals(user.getDepartmentId())){
                    userDept = true;
                }
            }
            if (!userDept){
                deptList.add(user.getDepartmentId());
            }
            //权限放到session中 避免重复查询数据库
            subject.getSession().setAttribute("deptPermissionList",deptList);
        }
        return deptList;
    }

    @Override
    public DeptPermissionModel getUserDataPermission2() {
        DeptPermissionModel model = new DeptPermissionModel();
        List<String> deptList = getUserDataPermission();
        if(deptList.size() == 1){
            model.setDeptId(deptList.get(0));
        }else{
            model.setDeptIds(deptList);
        }
        return model;
    }

    @Override
    public DeptPermissionModel getDeptPermissionModel() {
        DeptPermissionModel model = new DeptPermissionModel();
        UserModel userModel = SessionInfoUtil.getSessionUser();
        if(userModel != null && !"admin".equals(userModel.getRoleSign())){
            model = getUserDataPermission2();
        }
        return model;
    }

    @Override
    public List<SysDataPermission> getModelList(SysDataPermission model) {
        SysDataPermissionExample example = new SysDataPermissionExample();
        example.createCriteria().andUserDeptIdEqualTo(model.getDeptId()).andTypeEqualTo(model.getType());
        return dataPermissionMapper.selectByExample(example);
    }

    @Override
    public List<SysDataPermission> getModelList(List<String> userDeptIds) {
        SysDataPermissionExample example = new SysDataPermissionExample();
        example.createCriteria().andUserDeptIdIn(userDeptIds);
        return dataPermissionMapper.selectByExample(example);
    }
}
