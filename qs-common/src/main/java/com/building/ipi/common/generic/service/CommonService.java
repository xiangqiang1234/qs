package com.building.ipi.common.generic.service;

import com.building.ipi.common.entity.Result;
import com.building.ipi.common.feature.orm.mybatis.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * service通用接口
 *
 * @author Toning
 * @date 2018-10-24
 */
public interface CommonService<Model, PKModel> {

    /**
     * 分页查询
     */
    Page<PKModel> selectPageList(Page<PKModel> page, HttpServletRequest request);

    /**
     * 新增
     */
    Result add(Model model, HttpServletRequest request);

    /**
     * 修改
     */
    Result update(Model model, HttpServletRequest request);

    /**
     * 查询
     */
    String getModel(HttpServletRequest request);

    /**
     * 删除数据
     */
    Result delete(HttpServletRequest request);

    /**
     * 启用/禁用数据
     */
    Result isActive(HttpServletRequest request);

    /**
     * 唯一性验证
     */
    Boolean uniqueValidate(HttpServletRequest request);

    /**
     * 唯一性验证
     */
    Boolean uniqueValidate(String number);

    /**
     * 查询多个对象
     */
    List<PKModel> getList(String[] ids);

}
