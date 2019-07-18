package com.building.ipi.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.building.ipi.common.entity.Result;
import com.building.ipi.common.util.PasswordHash;
import com.building.ipi.web.system.user.model.SysUser;
import com.building.ipi.web.system.user.model.UserModel;
import com.building.ipi.web.system.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.GeneralSecurityException;


/**
 * 用户控制器
 *
 * @author StarZou
 * @since 2014年5月28日 下午3:54:00
 **/
@Controller
@RequestMapping(value = "/user")
public class LoginController {

    @Resource
    private UserService userService;
    private static final String ERROR = "error";
    final Logger logger = Logger.getLogger(getClass());

    /**
     * 用户登录
     *
     * @param user
     * @param result
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@Valid SysUser user, BindingResult result, Model model, HttpServletRequest request) {
        String userPassError = "用户名或密码错误 ！";
        String loginUrl = "redirect:/control/page/login";
        try {
            Subject subject = SecurityUtils.getSubject();
            //设置超时时间为30分钟
            subject.getSession().setTimeout(1800000);
            // 已登陆则 跳到首页
            if (subject.isAuthenticated()) {
                return "redirect:/";
            }
            if (result.hasErrors()) {
                model.addAttribute(ERROR, "参数错误！");
                return loginUrl;
            }
            SysUser comparisonUser = userService.selectByUsername(user.getUsername());
            if (comparisonUser == null) {
                model.addAttribute(ERROR, userPassError);
                return loginUrl;
            } else {
                if (!isUserActive(comparisonUser.getState())) {
                    model.addAttribute(ERROR, "用户已冻结，禁止登录 ！");
                    return loginUrl;
                }
                //密码验证
                if (!userLogin(user, comparisonUser, subject).isSuccess()) {
                    model.addAttribute(ERROR, userPassError);
                    return loginUrl;
                }
            }
        } catch (AuthenticationException e) {
            // 身份验证失败
            model.addAttribute("error", "用户名或密码错误 ！");
            return loginUrl;
        } catch (GeneralSecurityException e) {
            logger.error(ERROR, e);
        }
        return "redirect:/";
    }

    /**
     * 用户登出
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        // 登出操作
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }

    /**
     * 判断用户状态
     *
     * @param userState
     * @return
     */
    Boolean isUserActive(String userState) {
        return StringUtils.isNoneBlank(userState) && "1".equals(userState);
    }

    /**
     * 判断用户名 密码
     *
     * @param user
     * @param comparisonUser
     * @param subject
     * @return
     * @throws GeneralSecurityException
     */

    Result userLogin(SysUser user, SysUser comparisonUser, Subject subject) throws GeneralSecurityException {
        Result result = null;
        String strPassword = comparisonUser.getPassword();
        String strSalt = comparisonUser.getSalt();
        String strPwdQuery = getPasswordQuery(strSalt, strPassword);
        if (PasswordHash.validatePassword(user.getPassword(), strPwdQuery)) {
            subject.login(new UsernamePasswordToken(user.getUsername(), strPassword));
            //往 session 中赋值
            UserModel userModel = userService.getUserModel(user);
            subject.getSession().setAttribute("userModel", userModel);

            result = new Result(true, Result.statusCode_ERROR, "登录成功！");
        } else {
            result = new Result(false, Result.statusCode_ERROR, "验证密码时错误！");
        }
        return result;
    }

    /**
     * 根据密码和盐值获取验证用密码
     *
     * @param salt     盐值
     * @param password 密码
     * @return
     */
    String getPasswordQuery(String salt, String password) {
        return "1000:" + salt + ":" + password;
    }
}
