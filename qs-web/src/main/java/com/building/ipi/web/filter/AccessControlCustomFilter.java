package com.building.ipi.web.filter;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;

/**
 * @author xq
 */
public class AccessControlCustomFilter extends AccessControlFilter {
    final Logger log = Logger.getLogger(getClass());

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        log.debug(System.currentTimeMillis());
        Subject subject = SecurityUtils.getSubject();
        Boolean isAllowed = false;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String urlPath = httpServletRequest.getRequestURI();
        String contextPath = httpServletRequest.getContextPath();
        String url = urlPath.replaceFirst(contextPath, "");
        StringTokenizer token = new StringTokenizer(url, "/");
        //链接中执行方法的名字
        String actionName = "";
        while (token.hasMoreTokens()) {
            actionName = token.nextToken();
        }
        /**
         * 注册不需要拦截的链接
         */
        if (isNoLoginAuthFilter(actionName)) {
            isAllowed = true;
            saveRequest(request);
            return isAllowed;
        }
        if (subject.isAuthenticated()) {
            try {
                if (StringUtils.isNoneBlank(url)) {
                    if (onAfterLoginAuthFilter(url, actionName) || subject.isPermitted(url)) {
                        isAllowed = true;
                    }
                } else {
                    isAllowed = true;
                }
                if (isAllowed) {
                    saveRequest(request);
                }
            } catch (Exception e) {
                log.error("error", e);
            }
            log.debug(System.currentTimeMillis());
            return isAllowed;
        } else {
            redirectToLogin(request, response);
            return isAllowed;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        log.info("没有权限===");
        return false;
    }

    /**
     * 登录后不需要进行拦截的链接
     *
     * @param url
     * @returns
     */
    protected Boolean onAfterLoginAuthFilter(String url, String actionName) {
        if ("/".equals(url)
                || "/control/page/admin".equals(url)
                || "/control/page/member".equals(url)
                || url.indexOf("/member/center/") > -1
                || url.indexOf("/admin/frame/") > -1) {
            return true;
        }
        return actionName.indexOf("noauth_") > -1;
    }

    /**
     * 不需要进行拦截的链接
     *
     * @param actionName
     * @return
     */
    protected Boolean isNoLoginAuthFilter(String actionName) {
        return actionName.indexOf("noauth_register_") > -1;
    }

}
