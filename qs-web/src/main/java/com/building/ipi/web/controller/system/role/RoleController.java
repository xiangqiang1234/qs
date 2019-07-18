package com.building.ipi.web.controller.system.role;

import com.alibaba.fastjson.JSON;
import com.building.ipi.common.entity.DataTableResult;
import com.building.ipi.common.entity.JSONListResult;
import com.building.ipi.common.entity.JSONResult;
import com.building.ipi.common.entity.Result;
import com.building.ipi.common.feature.orm.mybatis.Page;
import com.building.ipi.common.util.ApplicationUtils;
import com.building.ipi.common.util.DatatableUtils;
import com.building.ipi.web.common.TreeModel;
import com.building.ipi.web.system.role.model.Role;
import com.building.ipi.web.system.role.service.RoleService;
import com.building.ipi.web.controller.CommonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yuzj on 2017/12/22.
 * 角色管理
 */
@Controller
@RequestMapping(value = "system/role/")
public class RoleController extends CommonController {
    @Autowired
    private RoleService roleService;

    /**
     * 角色管理页面
     *
     * @return
     */
    @RequestMapping(value = "ui")
    public String listUi() {
        return "system/role/role_list";
    }

    /**
     * 查询角色列表 分页查询
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public String getPageData(HttpServletRequest request) {
        Page<Role> page = getPageBean(request);
        String sEcho = page.getsEcho();
        Page<Role> pageData = roleService.selectPageList(page);
        DataTableResult<Role> dataTableResult = new DataTableResult<>();
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
        return "system/role/role_add";
    }

    /**
     * 添加角色
     *
     * @param role
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public String add(Role role) {
        Result result = new Result();
        role.setId(ApplicationUtils.getUUID());
        try {
            roleService.insert(role);
            result.setMessage("保存成功！");
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMessage("保存失败！");
            result.setSuccess(false);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 修改页面
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "updateUi")
    public ModelAndView updateUi(String id) {
        return new ModelAndView("system/role/role_update").addObject("id", id);
    }

    /**
     * 修改角色
     *
     * @param role
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public String update(Role role) {
        Result result = new Result();
        try {
            roleService.update(role);
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
        JSONResult<Role> result = new JSONResult<>();
        try {
            Role role = roleService.selectById(id);
            if (role == null) {
                result.setSuccess(false);
                result.setMessage("查询失败，请检查数据！");
            } else {
                result.setSuccess(true);
                result.setData(role);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("查询失败，请稍后再试！");
        }
        return JSON.toJSONString(result);
    }

    /**
     * 查询角色列表 用于用户管理分配角色
     *
     * @return
     */
    @RequestMapping(value = "getRoleList", method = RequestMethod.POST)
    @ResponseBody
    public List<Role> getRoleList() {
        return roleService.getRoleList();
    }

    /**
     * 赋权页面
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "setPermissionUi")
    public ModelAndView setPermissionUi(String id) {
        return new ModelAndView("system/role/role_setPermission").addObject("id", id);
    }

    /**
     * 获取角色的权限树
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "getPermissionTree", method = RequestMethod.POST)
    @ResponseBody
    public String getPermissionTree(String id) {
        List<TreeModel> list = roleService.getPermissionTree(id);
        JSONListResult<TreeModel> result = new JSONListResult<>(list);
        return JSON.toJSONString(result);
    }

    /**
     * 角色赋权
     *
     * @param ids
     * @param id
     * @return
     */
    @RequestMapping(value = "setPermission", method = RequestMethod.POST)
    @ResponseBody
    public String setPermission(@RequestParam(value = "ids[]") String[] ids, String id) {
        Result result = new Result();
        try {
            roleService.setPermission(ids, id);
            result.setMessage("赋权成功！");
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMessage("赋权失败！");
            result.setSuccess(false);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 角色标识唯一性验证
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "roleUnique", method = RequestMethod.POST)
    @ResponseBody
    public Boolean roleUnique(HttpServletRequest request) {
        return roleService.roleUnique(request);
    }
}
