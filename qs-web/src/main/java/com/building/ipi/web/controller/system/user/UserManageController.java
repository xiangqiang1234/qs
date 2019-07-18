package com.building.ipi.web.controller.system.user;

import com.alibaba.fastjson.JSON;
import com.building.ipi.common.entity.JSONResult;
import com.building.ipi.common.entity.Result;
import com.building.ipi.common.util.PasswordHash;
import com.building.ipi.web.common.TreeModel;
import com.building.ipi.web.system.user.model.UserModel;
import com.building.ipi.web.controller.CommonController;
import com.building.ipi.common.entity.DataTableResult;
import com.building.ipi.common.feature.orm.mybatis.Page;
import com.building.ipi.common.util.DatatableUtils;
import com.building.ipi.web.system.user.model.SysUser;
import com.building.ipi.web.system.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Created by Administrator on 2017/12/9.
 * 用户管理
 */
@Controller
@RequestMapping(value = "system/userManger/")
public class UserManageController extends CommonController {
    @Autowired
    private UserService userService;

    /**
     * 用户管理页面
     *
     * @return
     */
    @RequestMapping(value = "ui")
    public String userUi() {
        return "system/user/user_list";
    }

    /**
     * 查询列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public String getPageData(HttpServletRequest request) {
        Page<UserModel> page = getPageBean(request);
        String sEcho = page.getsEcho();
        Page<UserModel> pageData = userService.selectPageList(page, request);
        DataTableResult<UserModel> dataTableResult = new DataTableResult<>();
        dataTableResult = DatatableUtils.getDataTableResult(dataTableResult, pageData, sEcho);
        return JSON.toJSONString(dataTableResult);
    }

    /**
     * 添加页面
     *
     * @return
     */
    @RequestMapping(value = "addUi")
    public String addUi() {
        return "system/user/user_add";
    }

    /**
     * 添加用户
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    @ResponseBody
    public String addUser(UserModel model) {
        Result result = new Result();
        try {
            userService.userAdd(model);
            result.setMessage("保存成功！");
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMessage("保存失败！");
            result.setSuccess(false);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 用户冻结/解冻
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "userBlock", method = RequestMethod.POST)
    @ResponseBody
    public String userBlock(HttpServletRequest request) {
        Result result = new Result();
        if (userService.userBlock(request)) {
            result.setMessage("操作成功！");
            result.setSuccess(true);
        } else {
            result.setMessage("操作失败！");
            result.setSuccess(false);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 重置密码
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "passwordReset", method = RequestMethod.POST)
    @ResponseBody
    public String passwordReset(String id) {
        Result result = new Result();
        SysUser user = new SysUser();
        try {
            String password = PasswordHash.createHash("123456");
            String[] passwordAll = password.split(":");
            user.setSalt(passwordAll[1]);
            user.setPassword(passwordAll[2]);
            user.setId(id);
            userService.update(user);
            result.setMessage("密码重置成功！");
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMessage("密码重置失败！");
            result.setSuccess(false);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 修改页面
     *
     * @return
     */
    @RequestMapping(value = "updateUi")
    public ModelAndView updateUi(String id) {
        return new ModelAndView("system/user/user_update").addObject("id", id);
    }

    /**
     * 修改用户
     *
     * @param user
     * @param request
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public String update(SysUser user, HttpServletRequest request) {
        Result result = new Result();
        try {
            userService.userUpdate(user, request);
            result.setMessage("修改成功！");
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMessage("修改失败！");
            result.setSuccess(false);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 获取详细信息 用于修改和查看详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "getModel", method = RequestMethod.POST)
    @ResponseBody
    public String getModel(String id) {
        JSONResult<SysUser> result = new JSONResult<>();
        try {
            SysUser user = new SysUser();
            user.setId(id);
            user = userService.getUserModel(user);
            if (user == null) {
                result.setSuccess(false);
                result.setMessage("查询失败，请检查数据！");
            } else {
                result.setSuccess(true);
                result.setData(user);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("查询失败，请稍后再试！");
        }
        return JSON.toJSONString(result);
    }

    /**
     * 用户唯一性验证
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "userUnique", method = RequestMethod.POST)
    @ResponseBody
    public Boolean userUnique(HttpServletRequest request) {
        return userService.userUnique(request);
    }

    /**
     * 修改密码页面
     *
     * @return
     */
    @RequestMapping(value = "updatePasswordUi")
    public String updatePasswordUi() {
        return "system/user/user_updatePassword";
    }

    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public String updatePassword(HttpServletRequest request) {
        Result result = new Result();
        try {
            if (userService.updatePassword(request) == 1) {
                result.setMessage("修改密码成功！");
                result.setSuccess(true);
            } else {
                result.setMessage("修改密码失败！");
                result.setSuccess(false);
            }
        } catch (Exception e) {
            result.setMessage("修改密码失败！");
            result.setSuccess(false);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 密码验证
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "checkPassword", method = RequestMethod.POST)
    @ResponseBody
    public Boolean checkPassword(HttpServletRequest request) {
        return userService.checkPassword(request);
    }



    /**
     * 查询用户列表  其他模块选择用户
     *
     * @param type   用户类型 driver 驾驶员
     * @return
     */
    @RequestMapping(value = "getUserList", method = RequestMethod.POST)
    @ResponseBody
    public String getUserList(String type, HttpServletRequest request) {
        Page<UserModel> page = getPageBean(request);
        String sEcho = page.getsEcho();
        Page<UserModel> pageData = userService.getUserList(page, type);
        DataTableResult<UserModel> dataTableResult = new DataTableResult<>();
        dataTableResult = DatatableUtils.getDataTableResult(dataTableResult, pageData, sEcho);
        return JSON.toJSONString(dataTableResult);
    }


}
