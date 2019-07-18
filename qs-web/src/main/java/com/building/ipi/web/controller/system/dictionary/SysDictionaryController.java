package com.building.ipi.web.controller.system.dictionary;

import com.alibaba.fastjson.JSON;
import com.building.ipi.common.entity.DataTableResult;
import com.building.ipi.common.entity.JSONResult;
import com.building.ipi.common.entity.Result;
import com.building.ipi.common.feature.orm.mybatis.Page;
import com.building.ipi.common.util.ApplicationUtils;
import com.building.ipi.common.util.DatatableUtils;
import com.building.ipi.web.common.SessionInfoUtil;
import com.building.ipi.web.controller.CommonController;
import com.building.ipi.web.system.dictionary.model.SysDictionary;
import com.building.ipi.web.system.dictionary.service.SysDictionaryService;
import com.building.ipi.web.common.TreeModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator on 2017/12/26.
 */
@RequestMapping("system/dictionary/")
@Controller
public class SysDictionaryController extends CommonController {

    @Resource
    private SysDictionaryService sysDictionaryService;

    /**
     * 跳转到查询页
     *
     * @return
     */
    @RequestMapping(value = "ui", method = RequestMethod.GET)
    public String ui() {
        return "/system/dictionary/dictionary_list";
    }

    /**
     * 跳转添加字典页面
     *
     * @return
     */
    @RequestMapping(value = "addUi", method = RequestMethod.GET)
    public String addUi() {
        return "/system/dictionary/dictionary_add";
    }

    /**
     * 跳转上级字典页面
     *
     * @return
     */
    @RequestMapping(value = "showTreeUi", method = RequestMethod.GET)
    public String showTreeUi() {
        return "/system/dictionary/dictionary_tree";
    }

    /**
     * 跳转修改页面
     *
     * @return
     */
    @RequestMapping(value = "updateUi", method = RequestMethod.GET)
    public ModelAndView updateUi(HttpServletRequest request) {
        String id = request.getParameter("id");
        return new ModelAndView("system/dictionary/dictionary_update").addObject("id", id);
    }


    /**
     * 列表查询
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public String list(HttpServletRequest request) {
        Page<SysDictionary> page = getPageBean(request);
        String sEcho = page.getsEcho();
        Page<SysDictionary> pageData = sysDictionaryService.selectPageList(page, request);
        DataTableResult<SysDictionary> dataTableResult = new DataTableResult<>();
        dataTableResult = DatatableUtils.getDataTableResult(dataTableResult, pageData, sEcho);
        return JSON.toJSONString(dataTableResult);
    }

    /**
     * 树加载
     *
     * @return
     */
    @RequestMapping(value = "showLeftTree", method = RequestMethod.POST)
    @ResponseBody
    public List<TreeModel> showLeftTree() {
        return sysDictionaryService.showLeftTree();
    }

    /**
     * 编码唯一性校验
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "noauth_verCode", method = RequestMethod.POST)
    @ResponseBody
    public String noauthVerCode(HttpServletRequest request) {
        Result result = new Result();
        String id = request.getParameter("id");
        String code = request.getParameter("code");
        if (code.isEmpty()) {
            result.setMessage("编码为空！");
            result.setSuccess(false);
        } else {
            List<SysDictionary> sysDictionary = sysDictionaryService.selectByperCode(code);
            if (!sysDictionary.isEmpty()) {
                if (sysDictionary.get(0).getId().equals(id)) {
                    result.setMessage("此标识可以使用！");
                    result.setSuccess(true);
                } else {
                    result.setMessage("此标识已存在！");
                    result.setSuccess(false);
                }
            } else {
                result.setMessage("此标识可以使用！");
                result.setSuccess(true);
            }
        }
        return JSON.toJSONString(result);
    }

    /**
     * 字典添加
     *
     * @param sysDictionary
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public String add(SysDictionary sysDictionary) {
        Result result = new Result();
        sysDictionary.setId(ApplicationUtils.getUUID());
        sysDictionary.setCreated(new Date());
        sysDictionary.setCreatedby(SessionInfoUtil.getSessionUser().getId());
        try {
            sysDictionaryService.insert(sysDictionary);
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

            int count = sysDictionaryService.deleteDictionary(id);
            if (count > 0) {
                result.setMessage("删除成功！");
                result.setSuccess(true);
            } else {
                result.setMessage("删除失败！");
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
     * @return
     */
    @RequestMapping(value = "getModel", method = RequestMethod.POST)
    @ResponseBody
    public String getModel(HttpServletRequest request) {
        String id = request.getParameter("id");
        JSONResult<SysDictionary> result = new JSONResult<>();
        try {
            SysDictionary sysDictionary = sysDictionaryService.selectOneById(id);
            if (sysDictionary == null) {
                result.setSuccess(false);
                result.setMessage("查询失败！");
            } else {
                result.setSuccess(true);
                result.setData(sysDictionary);
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
     * @param sysDictionary
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public String update(SysDictionary sysDictionary) {

        Result result = new Result();
        sysDictionary.setLastmodified(new Date());
        sysDictionary.setLastmodifiedby(SessionInfoUtil.getSessionUser().getId());
        try {
            int count = sysDictionaryService.update(sysDictionary);
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
     * 根据编码查询下级字典数据
     *
     * @return
     */
    @RequestMapping(value = "getDictionaryByCode", method = RequestMethod.POST)
    @ResponseBody
    public List<SysDictionary> getDictionaryByCode(String code) {
        return sysDictionaryService.getDictionaryByCode(code);
    }

    /**
     * 根据id查询下级字典数据
     *
     * @return
     */
    @RequestMapping(value = "getDictionaryById", method = RequestMethod.POST)
    @ResponseBody
    public List<SysDictionary> getDictionaryById(String id) {
        return sysDictionaryService.getDictionaryById(id);
    }

    /**
     * 启用/禁用
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public Result updateStatus(SysDictionary model) {
        Result result = new Result();
        model.setLastmodified(new Date());
        model.setLastmodifiedby(SessionInfoUtil.getSessionUser().getId());
        try {
            if (sysDictionaryService.update(model) == 1) {
                result.setSuccess(true);
                result.setMessage("操作成功！");
            } else {
                result.setSuccess(false);
                result.setMessage("操作失败！");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("操作失败，请稍后再试！");
        }
        return result;
    }
}
