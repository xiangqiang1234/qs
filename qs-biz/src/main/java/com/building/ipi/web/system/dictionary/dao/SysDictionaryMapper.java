package com.building.ipi.web.system.dictionary.dao;

import com.building.ipi.common.feature.orm.mybatis.Page;
import com.building.ipi.common.generic.GenericDao;
import com.building.ipi.web.system.dictionary.model.SysDictionary;
import com.building.ipi.web.system.dictionary.model.SysDictionaryExample;
import java.util.List;

import com.building.ipi.web.common.TreeModel;
import org.apache.ibatis.annotations.Param;

public interface SysDictionaryMapper extends GenericDao<SysDictionary,String>{
    int countByExample(SysDictionaryExample example);

    int deleteByExample(SysDictionaryExample example);
    @Override
    int deleteByPrimaryKey(String id);

    int insert(SysDictionary record);
    @Override
    int insertSelective(SysDictionary record);

    List<SysDictionary> selectByExample(SysDictionaryExample example);
    @Override
    SysDictionary selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysDictionary record, @Param("example") SysDictionaryExample example);

    int updateByExample(@Param("record") SysDictionary record, @Param("example") SysDictionaryExample example);
    @Override
    int updateByPrimaryKeySelective(SysDictionary record);

    int updateByPrimaryKey(SysDictionary record);

    /**
     * 列表查询
     * @param page
     * @param dictionaryName
     * @return
     */
    List<SysDictionary> selectPageList(Page<SysDictionary> page,@Param("param") String dictionaryName,@Param("pid")String pid);

    /**
     * 左侧树查询
     * @return
     */
    List<TreeModel> showLeftTree();

    /**
     * 查询单个数据
     * @param id
     * @return
     */
    SysDictionary selectByOne(@Param("id") String id);

}