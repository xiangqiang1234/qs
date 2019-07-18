package com.building.ipi.web.system.user.service.impl;


import com.building.ipi.common.feature.orm.mybatis.Page;
import com.building.ipi.common.generic.GenericDao;
import com.building.ipi.common.generic.GenericServiceImpl;
import com.building.ipi.common.util.ApplicationUtils;
import com.building.ipi.common.util.Decode;
import com.building.ipi.common.util.PasswordHash;
import com.building.ipi.web.common.SessionInfoUtil;
import com.building.ipi.web.system.datapermission.model.SysDataPermission;
import com.building.ipi.web.system.datapermission.service.DataPermissionService;
import com.building.ipi.web.common.TreeModel;
import com.building.ipi.web.system.user.model.SysUser;
import com.building.ipi.web.system.user.model.SysUserExample;
import com.building.ipi.web.system.user.model.UserModel;
import com.building.ipi.web.system.user.service.UserService;
import com.building.ipi.web.system.user.dao.SysUserMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户Service实现类
 *
 * @author yuzj
 * @since 2017-12-23
 */
@Service
public class UserServiceImpl extends GenericServiceImpl<SysUser, String> implements UserService {

    @Resource
    private SysUserMapper userMapper;
    @Resource
    private DataPermissionService dataPermissionService;

    @Override
    public GenericDao<SysUser, String> getDao() {
        return userMapper;
    }

    @Override
    public SysUser authentication(SysUser user) {
        return userMapper.authentication(user);
    }

    private static final String ERROR = "error";
    final Logger logger = Logger.getLogger(getClass());

    @Override
    public SysUser selectByUsername(String username) {
        SysUser user = null;
        SysUserExample example = new SysUserExample();
        example.createCriteria().andUsernameEqualTo(username);
        final List<SysUser> list = userMapper.selectByExample(example);
        if (!list.isEmpty()) {
            user = list.get(0);
        }
        return user;
    }

    /**
     * 查询用户列表
     *
     * @param page
     * @param request
     * @return
     */
    @Override
    public Page<UserModel> selectPageList(Page<UserModel> page, HttpServletRequest request) {
        String param = request.getParameter("param");
        List<UserModel> list = userMapper.selectPageList(page, Decode.getUtfCode(param), dataPermissionService.getDeptPermissionModel());
        page.setResult(list);
        return page;
    }

    @Override
    public UserModel getUserModel(SysUser user) {
        return userMapper.getUserModel(user);
    }

    @Override
    public boolean userBlock(HttpServletRequest request) {
        boolean flag=false;
        String id = request.getParameter("id");
        String state = request.getParameter("state");
        SysUser user = new SysUser();
        user.setId(id);
        user.setState(state);
        if (userMapper.updateByPrimaryKeySelective(user) > 0) {
            flag = true;
        }
        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void userAdd(UserModel user) {
        String id = ApplicationUtils.getUUID();
        UserModel userModel = SessionInfoUtil.getSessionUser();
        try {
            user.setId(id);
            user.setCreated(new Date());
            user.setCreatedby(userModel.getId());
            String password = PasswordHash.createHash("123456");
            String[] passwordAll = password.split(":");
            user.setSalt(passwordAll[1]);
            user.setPassword(passwordAll[2]);
        } catch (Exception e) {
            logger.error(ERROR,e);
        }
        //用户表信息保存
        userMapper.insertSelective(user);
        //用户与角色对应表信息保存
        userMapper.addUserRole(id, id, user.getRoleId());

        SysDataPermission sysDataPermission = new SysDataPermission();
        sysDataPermission.setType("2");
        sysDataPermission.setUserDeptId(id);
        sysDataPermission.setDeptId(user.getDepartmentId());
        dataPermissionService.insert(sysDataPermission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void userUpdate(SysUser user, HttpServletRequest request) {
        UserModel userModel = SessionInfoUtil.getSessionUser();
        user.setLastmodifiedby(userModel.getId());
        //用户表信息修改
        userMapper.updateByPrimaryKeySelective(user);
        //用户与角色对应表信息修改
        String roleId = request.getParameter("roleId");
        userMapper.updateUserRole(user.getId(), roleId);
    }

    @Override
    public Boolean userUnique(HttpServletRequest request) {
        boolean flag = false;
        String id = request.getParameter("id") == null ? "" : request.getParameter("id");
        String username = request.getParameter("username");
        SysUserExample userExample = new SysUserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<SysUser> list = userMapper.selectByExample(userExample);
        if (list.isEmpty() || list.get(0).getId().equals(id)) {
            flag = true;
        }
        return flag;
    }

    @Override
    public int updatePassword(HttpServletRequest request) {
        SysUser user=new SysUser();
        try {
            UserModel userModel = SessionInfoUtil.getSessionUser();
            String newPassword=request.getParameter("newPassword");
            String password = PasswordHash.createHash(newPassword);
            String[] passwordAll = password.split(":");
            user.setSalt(passwordAll[1]);
            user.setPassword(passwordAll[2]);
            user.setId(userModel.getId());
        } catch (Exception e) {
            logger.error(ERROR,e);
        }
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public Boolean checkPassword(HttpServletRequest request) {
        boolean flag = false;
        UserModel userModel = SessionInfoUtil.getSessionUser();
        String strPassword = userModel.getPassword();
        String strSalt = userModel.getSalt();
        String password = request.getParameter("password");
        String strPwdQuery = "1000:" + strSalt + ":" + strPassword;
        try {
            if (PasswordHash.validatePassword(password, strPwdQuery)) {
                flag = true;
            }
        } catch (Exception e) {
            logger.error(ERROR,e);
        }
        return flag;
    }

    @Override
    public Boolean checkPasswordApp(UserModel userModel, HttpServletRequest request) {
        boolean flag = false;
        String strPassword = userModel.getPassword();
        String strSalt = userModel.getSalt();
        String password = request.getParameter("password");
        String strPwdQuery = "1000:" + strSalt + ":" + strPassword;
        try {
            if (PasswordHash.validatePassword(password, strPwdQuery)) {
                flag = true;
            }
        } catch (Exception e) {
            logger.error(ERROR, e);
        }
        return flag;
    }

    @Override
    public int updatePasswordApp(UserModel userModel, HttpServletRequest request) {
        SysUser user = new SysUser();
        try {
            String newPassword = request.getParameter("newPassword");
            String password = PasswordHash.createHash(newPassword);
            String[] passwordAll = password.split(":");
            user.setSalt(passwordAll[1]);
            user.setPassword(passwordAll[2]);
            user.setId(userModel.getId());
        } catch (Exception e) {
            logger.error(ERROR, e);
        }
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public Page<UserModel> getUserList(Page<UserModel> page, String type) {
        List<UserModel> list = userMapper.getUserList(page, type, dataPermissionService.getDeptPermissionModel());
        page.setResult(list);
        return page;
    }
}
