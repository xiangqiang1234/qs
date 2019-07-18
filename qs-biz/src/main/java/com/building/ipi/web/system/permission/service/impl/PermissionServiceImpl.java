package com.building.ipi.web.system.permission.service.impl;

import com.building.ipi.common.feature.orm.mybatis.Page;
import com.building.ipi.common.generic.GenericDao;
import com.building.ipi.common.generic.GenericServiceImpl;
import com.building.ipi.web.common.SessionInfoUtil;
import com.building.ipi.web.system.permission.dao.SysPermissionMapper;
import com.building.ipi.web.system.permission.model.SysPermission;
import com.building.ipi.web.system.permission.model.SysPermissionModel;
import com.building.ipi.web.common.TreeModel;
import com.building.ipi.web.system.permission.service.PermissionService;
import com.building.ipi.web.system.rolepermission.dao.SysRolePermissionMapper;
import com.building.ipi.web.system.rolepermission.model.SysRolePermission;
import com.building.ipi.web.system.rolepermission.model.SysRolePermissionExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * 权限Service实现类
 *
 * @author StarZou
 * @since 2014年6月10日 下午12:05:03
 */
@Service
public class PermissionServiceImpl extends GenericServiceImpl<SysPermission, String> implements PermissionService {

    @Resource
    private SysPermissionMapper permissionMapper;

    @Resource
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public GenericDao<SysPermission, String> getDao() {
        return permissionMapper;
    }

    @Override
    public List<SysPermission> selectPermissionsByRoleId(String roleId) {
        return permissionMapper.selectPermissionsByRoleId(roleId);
    }

    @Override
    public Page<SysPermission> selectPageList(Page<SysPermission> page,SysPermission sysPermission) {
        List<SysPermission> list = permissionMapper.selectPageList(page, sysPermission);
        page.setResult(list);
        return page;
    }

    @Override
    public List<TreeModel> selectTree(SysPermission model) {
        return permissionMapper.selectTree(model);
    }

    @Override
    @Transactional
    public int deletePermission(String id) {
        return permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public SysPermissionModel selectOneById(String id) {
        return permissionMapper.selectOneById(id);
    }

    @Override
    public List<SysRolePermission> selectRoleByperId(String id) {
        SysRolePermissionExample sysRolePermissionExample = new SysRolePermissionExample();
        sysRolePermissionExample.createCriteria().andPermissionIdEqualTo(id);
        return sysRolePermissionMapper.selectByExample(sysRolePermissionExample);
    }

    @Override
    public List<SysPermission> getPermissionList() {
        String roleId = SessionInfoUtil.getSessionUser().getRoleId();
        String roleSign = SessionInfoUtil.getSessionUser().getRoleSign();
        if("admin".equals(roleSign)){
            return permissionMapper.selectByExample(null);
        }else{
            return permissionMapper.getPermissionListByRoleId(roleId);
        }
    }

    @Override
    public List<SysPermission> selectAllPermissions() {
        return permissionMapper.selectByExample(null);
    }
}
