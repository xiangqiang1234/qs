package com.building.ipi.web.system.user.service;

import com.building.ipi.common.feature.orm.mybatis.Page;
import com.building.ipi.common.generic.GenericService;
import com.building.ipi.web.common.TreeModel;
import com.building.ipi.web.system.user.model.SysUser;
import com.building.ipi.web.system.user.model.UserModel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 用户 业务 接口
 *
 * @author yuzj
 * @since 2017-12-23
 **/
public interface UserService extends GenericService<SysUser, String> {

    /**
     * 用户验证
     *
     * @param user
     * @return
     */
    SysUser authentication(SysUser user);

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    SysUser selectByUsername(String username);

    /**
     * 查询用户列表
     *
     * @param page
     * @param request
     * @return
     */
    Page<UserModel> selectPageList(Page<UserModel> page, HttpServletRequest request);

    /**
     * 获取用户详细信息
     *
     * @param user
     * @return
     */
    UserModel getUserModel(SysUser user);

    /**
     * 用户冻结/解冻
     *
     * @param request
     * @return
     */
    boolean userBlock(HttpServletRequest request);

    /**
     * 新增用户
     *
     * @param model
     * @return
     */
    void userAdd(UserModel model);

    /**
     * 修改用户
     *
     * @param user
     * @param request
     * @return
     */
    void userUpdate(SysUser user, HttpServletRequest request);

    /**
     * 唯一性验证
     *
     * @param request
     * @return
     */
    Boolean userUnique(HttpServletRequest request);

    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    int updatePassword(HttpServletRequest request);

    /**
     * 密码验证
     *
     * @param request
     * @return
     */
    Boolean checkPassword(HttpServletRequest request);

    /**
     * 原密码校验 APP
     * @param userModel
     * @param request
     * @return
     */
    Boolean checkPasswordApp(UserModel userModel, HttpServletRequest request);

    /**
     * 修改密码 APP
     * @param userModel
     * @param request
     * @return
     */
    int updatePasswordApp(UserModel userModel, HttpServletRequest request);


    /**
     * 查询用户列表  其他模块选择用户
     *
     * @param page
     * @param type 用户类型
     * @return
     */
    Page<UserModel> getUserList(Page<UserModel> page, String type);
}
