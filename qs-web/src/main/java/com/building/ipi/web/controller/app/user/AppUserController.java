package com.building.ipi.web.controller.app.user;

import com.alibaba.fastjson.JSON;
import com.building.ipi.common.entity.JSONResult;
import com.building.ipi.common.entity.Result;
import com.building.ipi.common.util.PasswordHash;
import com.building.ipi.web.system.user.model.SysUser;
import com.building.ipi.web.system.user.model.UserModel;
import com.building.ipi.web.system.user.service.UserService;
import com.building.ipi.web.controller.CommonController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Created by xq on 2018/1/2.
 */
@Controller
@RequestMapping(value = "/app/user")
public class AppUserController extends CommonController {
    @Resource
    private UserService userService;
    protected final Log logger = LogFactory.getLog(this.getClass());
    static final String  ALLOWED = "0";

    /**
     * APP用戶登陆
     *
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public String userLogin(String userName, String password) {
        String json = "";
        JSONResult<SysUser> result = new JSONResult<>();
        SysUser sysUser = userService.selectByUsername(userName);
        if (sysUser == null) {
            result.setSuccess(false);
            result.setMessage("用户名错误");
        } else {
            String strPassword = sysUser.getPassword();
            String strSalt = sysUser.getSalt();
            String strPwdQuery = "1000:" + strSalt + ":" + strPassword;
            if (ALLOWED.equals(sysUser.getState())) {
                result.setSuccess(false);
                result.setMessage("用户已冻结，禁止登录");
            } else {
                try {
                    if (PasswordHash.validatePassword(password, strPwdQuery)) {
                        UserModel userModel = userService.getUserModel(sysUser);
                        result.setData(userModel);
                        result.setSuccess(true);
                    } else {
                        result.setSuccess(false);
                        result.setMessage("密码错误");
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }
        json = JSON.toJSONString(result);
        return json;
    }

    /**
     * 密码验证
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/verPassword", method = RequestMethod.POST)
    @ResponseBody
    public Boolean checkPassword(String userName, HttpServletRequest request) {
        SysUser user = userService.selectByUsername(userName);
        UserModel userModel = userService.getUserModel(user);
        return userService.checkPasswordApp(userModel, request);
    }

    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/editPassword", method = RequestMethod.POST)
    @ResponseBody
    public String updatePassword(String userName, HttpServletRequest request) {
        Result result = new Result();
        SysUser user = userService.selectByUsername(userName);
        UserModel userModel = userService.getUserModel(user);
        try {
            if (userService.updatePasswordApp(userModel, request) == 1) {
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
}
