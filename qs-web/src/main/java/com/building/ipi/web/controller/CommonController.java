package com.building.ipi.web.controller;

import javax.servlet.http.HttpServletRequest;

import com.building.ipi.common.feature.orm.mybatis.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 公共视图控制器
 *
 * @author starzou
 * @since 2014年4月15日 下午4:16:34
 **/
@Controller
public class CommonController {
    /**
     * 首页
     *
     * @param request
     * @return
     */
    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        return "index";
    }
    /**
     * 获取分页参数
     * @param request
     * @return
     */
    public Page getPageBean(HttpServletRequest request) {
        String sEcho = request.getParameter("draw");
        String start = request.getParameter("iDisplayStart");
        String length = request.getParameter("iDisplayLength");
        Integer startInt = 0;
        Integer lengthInt = 10;

        if (StringUtils.isNoneBlank(start)) {
            startInt = new Integer(start);
        }
        if (StringUtils.isNoneBlank(length)) {
            if(StringUtils.equalsIgnoreCase("-1", length)) {
                lengthInt = Integer.MAX_VALUE;
            } else {
                lengthInt = new Integer(length);
            }
        }
        return new Page(sEcho,startInt,lengthInt);
    }
}