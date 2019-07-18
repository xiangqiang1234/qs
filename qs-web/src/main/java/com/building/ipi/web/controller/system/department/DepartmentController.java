package com.building.ipi.web.controller.system.department;

import com.alibaba.fastjson.JSON;
import com.building.ipi.common.entity.DataTableResult;
import com.building.ipi.common.entity.JSONResult;
import com.building.ipi.common.entity.JSONListResult;
import com.building.ipi.common.entity.Result;
import com.building.ipi.common.feature.orm.mybatis.Page;
import com.building.ipi.common.util.DatatableUtils;
import com.building.ipi.web.system.department.model.SysDepartment;
import com.building.ipi.web.system.department.model.SysDepartmentModel;
import com.building.ipi.web.system.department.service.SysDepartmentService;
import com.building.ipi.web.common.TreeModel;
import com.building.ipi.web.system.user.model.SysUser;
import com.building.ipi.web.controller.CommonController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yuzj 2018-03-21
 */
@Controller
@RequestMapping(value = "system/departmentManger/")
public class DepartmentController extends CommonController {
    @Resource
    private SysDepartmentService sysDepartmentService;

    /**
     * 跳转列表页面
     *
     * @return
     */
    @RequestMapping(value = "ui", method = RequestMethod.GET)
    public String ui() {
        return "system/department/department_list";
    }

    /**
     * 跳转单位添加页面
     *
     * @param pid
     * @param pName
     * @param pSerialNumber
     * @return
     */
    @RequestMapping(value = "addUi", method = RequestMethod.GET)
    public ModelAndView addUi(String pid,String pName,String pSerialNumber) {
        return new ModelAndView("system/department/department_add").addObject("pid", pid).addObject("pName", pName).addObject("pSerialNumber", pSerialNumber);
    }

    /**
     * 跳转上级部门树页面
     *
     * @return
     */
    @RequestMapping(value = "showTreeUi", method = RequestMethod.GET)
    public ModelAndView showTreeUi(String pageId) {
        return new ModelAndView("system/department/department_addTree").addObject("pageId", pageId);
    }

    /**
     * 跳转修改页面
     *
     * @return
     */
    @RequestMapping(value = "updateUi", method = RequestMethod.GET)
    public ModelAndView updateUi(HttpServletRequest request) {
        String id = request.getParameter("id");
        return new ModelAndView("system/department/department_update").addObject("id", id);
    }

    /**
     * 部门树查询
     * @param userDeptId  要操作的用户或部门id
     * @param type  id类型 "" 为左侧部门树  1 部门 2 用户
     * @return
     */
    @RequestMapping(value = "showLeftTree", method = RequestMethod.POST)
    @ResponseBody
    public List<TreeModel> showLeftTree(String userDeptId,String type) {
        return sysDepartmentService.selectTree(userDeptId,type);
    }

    /**
     * 列表查询
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public String list(HttpServletRequest request, SysDepartmentModel model) {
        Page<SysDepartmentModel> page = getPageBean(request);
        String sEcho = page.getsEcho();
        Page<SysDepartmentModel> pageData = sysDepartmentService.selectPageList(page, model);
        DataTableResult<SysDepartment> dataTableResult = new DataTableResult<>();
        dataTableResult = DatatableUtils.getDataTableResult(dataTableResult, pageData, sEcho);
        return JSON.toJSONString(dataTableResult);
    }

    /**
     * 新增
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public String add(SysDepartmentModel model) {
        return sysDepartmentService.addModel(model);
    }

    /**
     * 检查单位编号
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "noauth_verNo", method = RequestMethod.POST)
    @ResponseBody
    public Boolean verNo(HttpServletRequest request) {
        boolean flag = false;
        String id = request.getParameter("id");
        String no = request.getParameter("no");
        if (no.isEmpty()) {
            flag = true;
        } else {
            List<SysDepartment> sysDeptment = sysDepartmentService.selectByperNo(no);
            if (!sysDeptment.isEmpty()) {
                if (sysDeptment.get(0).getId().equals(id)) {
                    flag = true;
                }
            } else {
                flag = true;
            }
        }

        return flag;
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
            //判断单位下面是否有用户
            List<SysUser> list = sysDepartmentService.selectUserBydepId(id);
            if (list.isEmpty()) {
                int count = sysDepartmentService.deleteDepart(id);
                if (count > 0) {
                    result.setMessage("删除成功！");
                    result.setSuccess(true);
                } else {
                    result.setMessage("删除失败！");
                    result.setSuccess(false);
                }
            } else {
                result.setMessage("该单位存在激活用户！");
                result.setSuccess(false);
            }

        } catch (Exception e) {
            result.setMessage("删除失败！");
            result.setSuccess(false);
        }
        return JSON.toJSONString(result);
    }


    /**
     * 查看详情
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getModel", method = RequestMethod.POST)
    @ResponseBody
    public String getModel(HttpServletRequest request) {
        String id = request.getParameter("id");
        JSONResult<SysDepartment> result = new JSONResult<>();
        try {
            SysDepartment sysDepartment = sysDepartmentService.selectOneById(id);
            if (sysDepartment == null) {
                result.setSuccess(false);
                result.setMessage("查询失败！");
            } else {
                result.setSuccess(true);
                result.setData(sysDepartment);
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
     * @param model
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public String update(SysDepartmentModel model) {
        return sysDepartmentService.updateModel(model);
    }

    /**
     * 获取单位列表  添加修改用户用
     *
     * @return
     */
    @RequestMapping(value = "getDeptList", method = RequestMethod.POST)
    @ResponseBody
    public String getDeptList() {
        JSONListResult<SysDepartment> jsonListResult = new JSONListResult<>();
        List<SysDepartment> listData = sysDepartmentService.getDeptList();
        jsonListResult.setData(listData);
        return JSON.toJSONString(jsonListResult);
    }

    /**
     * 数据赋权页面
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "setDataPermissionUi")
    public ModelAndView setDataPermissionUi(String id,String type) {
        return new ModelAndView("system/department/department_setDataPermission").addObject("id", id).addObject("type", type);
    }

    /**
     * 角色赋权
     *
     * @param ids
     * @param id
     * @return
     */
    @RequestMapping(value = "setDataPermission", method = RequestMethod.POST)
    @ResponseBody
    public String setDataPermission(@RequestParam(value = "ids[]") String[] ids, String id, String type) {
        Result result = new Result();
        try {
            sysDepartmentService.setDataPermission(ids, id, type);
            result.setMessage("赋权成功！");
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMessage("赋权失败！");
            result.setSuccess(false);
        }
        return JSON.toJSONString(result);
    }
}
