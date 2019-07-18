package com.building.ipi.web.controller.system.permission;

import com.alibaba.fastjson.JSON;
import com.building.ipi.common.entity.DataTableResult;
import com.building.ipi.common.entity.JSONListResult;
import com.building.ipi.common.entity.JSONResult;
import com.building.ipi.common.entity.Result;
import com.building.ipi.common.feature.orm.mybatis.Page;
import com.building.ipi.common.util.ApplicationUtils;
import com.building.ipi.common.util.DatatableUtils;
import com.building.ipi.web.common.SessionInfoUtil;
import com.building.ipi.web.controller.CommonController;
import com.building.ipi.web.system.permission.model.SysPermission;
import com.building.ipi.web.system.permission.model.SysPermissionModel;
import com.building.ipi.web.common.TreeModel;
import com.building.ipi.web.system.permission.service.PermissionService;
import com.building.ipi.web.system.rolepermission.model.SysRolePermission;
import com.building.ipi.web.system.user.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Administrator on 2017/12/23.
 */
@Controller
@RequestMapping(value = "system/permissionManger/")
public class PermissionManageController extends CommonController {
    @Autowired
    private PermissionService permissionService;

    /**
     * 跳转列表页面
     *
     * @return
     */
    @RequestMapping(value = "ui", method = RequestMethod.GET)
    public String permissionUi() {
        return "system/permission/permission_list";
    }


    /**
     * 跳转修改页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "updateUi")
    public ModelAndView updateUi(HttpServletRequest request) {
        String id = request.getParameter("id");
        return new ModelAndView("system/permission/permission_update").addObject("id", id);
    }

    /**
     * 跳转新增页面
     *
     * @return
     */
    @RequestMapping(value = "addUi", method = RequestMethod.GET)
    public ModelAndView addUi() {
        return new ModelAndView("system/permission/permission_add");
    }

    /**
     * 跳转权限树页面
     *
     * @return
     */
    @RequestMapping(value = "addFormShowPerTreeUi", method = RequestMethod.GET)
    public ModelAndView addFormShowPerTreeUi() {
        return new ModelAndView("system/permission/permission_addTree");
    }


    /**
     * 查询列表
     *
     * @param request
     * @param sysPermission
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public String getPageData(HttpServletRequest request, SysPermission sysPermission) {
        Page<SysPermission> page = getPageBean(request);
        String sEcho = page.getsEcho();
        Page<SysPermission> pageData = permissionService.selectPageList(page, sysPermission);
        DataTableResult<SysPermission> dataTableResult = new DataTableResult<>();
        dataTableResult = DatatableUtils.getDataTableResult(dataTableResult, pageData, sEcho);
        return JSON.toJSONString(dataTableResult);
    }


    /**
     * 加载数据
     *
     * @return
     */
    @RequestMapping(value = "showTree", method = RequestMethod.POST)
    @ResponseBody
    public List<TreeModel> showTree(SysPermission model) {
        return permissionService.selectTree(model);
    }

    /**
     * 新增
     *
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public String add(SysPermissionModel sysPermissionModel) {
        Result result = new Result();
        sysPermissionModel.setId(ApplicationUtils.getUUID());
        try {
            permissionService.insert(sysPermissionModel);
            result.setMessage("保存成功！");
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMessage("保存失败！");
            result.setSuccess(false);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 删除
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(HttpServletRequest request) {
        String id = request.getParameter("id");
        Result result = new Result();
        try {
            //判断是否已经赋权
            List<SysRolePermission> list = permissionService.selectRoleByperId(id);
            if (list.isEmpty()) {
                int count = permissionService.deletePermission(id);
                if (count > 0) {
                    result.setMessage("删除成功！");
                    result.setSuccess(true);
                } else {
                    result.setMessage("删除失败！");
                    result.setSuccess(false);
                }
            } else {
                result.setMessage("该权限已被赋权！");
                result.setSuccess(false);
            }

        } catch (Exception e) {
            result.setMessage("删除失败！");
            result.setSuccess(false);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 获取详情
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getModel", method = RequestMethod.POST)
    @ResponseBody
    public String getModel(HttpServletRequest request) {
        String id = request.getParameter("id");
        JSONResult<SysPermission> result = new JSONResult<>();
        try {
            SysPermissionModel sysPermissionModel = permissionService.selectOneById(id);
            if (sysPermissionModel == null) {
                result.setSuccess(false);
                result.setMessage("查询失败！");
            } else {
                result.setSuccess(true);
                result.setData(sysPermissionModel);
            }

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("查询失败！");
        }
        return JSON.toJSONString(result);
    }

    /**
     * 修改
     *
     * @param sysPermissionModel
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public String update(SysPermissionModel sysPermissionModel) {
        Result result = new Result();
        try {
            int count = permissionService.update(sysPermissionModel);
            if (count > 0) {
                result.setMessage("修改成功！");
                result.setSuccess(true);
            } else {
                result.setMessage("修改失败！");
                result.setSuccess(false);
            }
        } catch (Exception e) {
            result.setMessage("修改失败！");
            result.setSuccess(false);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 获取用户权限数据
     *
     * @return
     */
    @RequestMapping(value = "getPermissionList", method = RequestMethod.POST)
    @ResponseBody
    public String getPermissionList() {
        JSONListResult<SysPermission> jsonListResult = new JSONListResult<>();
        List<SysPermission> listData = permissionService.getPermissionList();
        jsonListResult.setData(listData);
        return JSON.toJSONString(jsonListResult);
    }

    /**
     * 验证session是否失效
     *
     * @return
     */
    @RequestMapping(value = "checkPermission", method = RequestMethod.POST)
    @ResponseBody
    public String checkPermission() {
        UserModel model = SessionInfoUtil.getSessionUser();
        Result result = new Result();
        if (model == null || model.getId() == null || "".equals(model.getId())) {
            result.setSuccess(false);
        } else {
            result.setSuccess(true);
        }
        return JSON.toJSONString(result);
    }

}
