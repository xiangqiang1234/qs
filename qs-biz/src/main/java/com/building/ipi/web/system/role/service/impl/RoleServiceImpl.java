package com.building.ipi.web.system.role.service.impl;

import com.building.ipi.common.feature.orm.mybatis.Page;
import com.building.ipi.common.generic.GenericDao;
import com.building.ipi.common.generic.GenericServiceImpl;
import com.building.ipi.web.common.SessionInfoUtil;
import com.building.ipi.web.common.TreeModel;
import com.building.ipi.web.system.role.dao.RoleMapper;
import com.building.ipi.web.system.role.model.Role;
import com.building.ipi.web.system.role.model.RoleExample;
import com.building.ipi.web.system.role.model.RolePermissionModel;
import com.building.ipi.web.system.role.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色Service实现类
 *
 * @author StarZou
 * @since 2014年6月10日 下午4:16:33
 */
@Service
public class RoleServiceImpl extends GenericServiceImpl<Role, String> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public GenericDao<Role, String> getDao() {
        return roleMapper;
    }

    @Override
    public List<Role> selectRolesByUserId(String userId) {
        return roleMapper.selectRolesByUserId(userId);
    }

    @Override
    public Page<Role> selectPageList(Page<Role> page) {
        List<Role> list = roleMapper.selectPageList(page);
        page.setResult(list);
        return page;
    }

    @Override
    public List<Role> getRoleList() {
        String roleSign = SessionInfoUtil.getSessionUser().getRoleSign();
        RoleExample example = new RoleExample();
        if(!"admin".equals(roleSign)){
            example.createCriteria().andRoleSignNotEqualTo("admin");
        }
        return roleMapper.selectByExample(example);
    }

    @Override
    public List<TreeModel> getPermissionTree(String id) {
        String userRoleId = SessionInfoUtil.getSessionUser().getRoleId();
        String roleSign = SessionInfoUtil.getSessionUser().getRoleSign();
        if("admin".equals(roleSign)){
            return roleMapper.getPermissionTree2(id);
        }else{
            return roleMapper.getPermissionTree(id,userRoleId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setPermission(String[] ids, String id) {
        List<RolePermissionModel> list = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            RolePermissionModel rolePermissionModel = new RolePermissionModel();
            rolePermissionModel.setPermissionId(ids[i]);
            rolePermissionModel.setRoleId(id);
            list.add(rolePermissionModel);
        }
        //删除权限
        roleMapper.deletePermission(id);
        //保存权限
        roleMapper.setPermission(list,id);
        roleMapper.setPermission2(list,id);
    }

    @Override
    public Boolean roleUnique(HttpServletRequest request) {
        boolean flag = false;
        String id = request.getParameter("id") == null ? "" : request.getParameter("id");
        String roleSign = request.getParameter("roleSign");
        RoleExample roleExample = new RoleExample();
        roleExample.createCriteria().andRoleSignEqualTo(roleSign);
        List<Role> list = roleMapper.selectByExample(roleExample);
        if (list.isEmpty() || list.get(0).getId().equals(id)) {
            flag = true;
        }
        return flag;
    }
}
