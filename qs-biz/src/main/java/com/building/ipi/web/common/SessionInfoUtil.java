package com.building.ipi.web.common;

import com.building.ipi.web.system.user.model.UserModel;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * 获取Session 中的值
 * @author yuzj 2017-12-25
 */
public class SessionInfoUtil {

    /**
     * 获取登录用户信息
     * @return
     */
    public static UserModel getSessionUser() {
        Subject subject = SecurityUtils.getSubject();
        UserModel userModel = (UserModel)subject.getSession().getAttribute("userModel");
        return userModel;
    }
}
