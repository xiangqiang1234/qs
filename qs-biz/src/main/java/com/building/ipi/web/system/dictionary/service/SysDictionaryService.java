package com.building.ipi.web.system.dictionary.service;

import com.building.ipi.common.feature.orm.mybatis.Page;
import com.building.ipi.common.generic.GenericService;
import com.building.ipi.web.system.dictionary.model.SysDictionary;
import com.building.ipi.web.common.TreeModel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Administrator
 * @date 2017/12/26
 */
public interface SysDictionaryService extends GenericService<SysDictionary, String> {
    /**
     * 列表查询
     *
     * @param page
     * @param request
     * @return
     */
    Page<SysDictionary> selectPageList(Page<SysDictionary> page, HttpServletRequest request);

    /**
     * 查询左侧树
     *
     * @return
     */
    List<TreeModel> showLeftTree();

    /**
     * 编码唯一性
     *
     * @param code
     * @return
     */
    List<SysDictionary> selectByperCode(String code);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int deleteDictionary(String id);

    /**
     * 查看详情
     *
     * @param id
     * @return
     */
    SysDictionary selectOneById(String id);

    /**
     * 根据编码查询下级字典数据
     *
     * @param code
     * @return
     */
    List<SysDictionary> getDictionaryByCode(String code);

    /**
     * 根据id查询下级字典数据
     *
     * @param id
     * @return
     */
    List<SysDictionary> getDictionaryById(String id);
}
