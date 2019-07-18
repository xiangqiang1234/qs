package com.building.ipi.web.system.dictionary.service.impl;

import com.building.ipi.common.feature.orm.mybatis.Page;
import com.building.ipi.common.generic.GenericDao;
import com.building.ipi.common.generic.GenericServiceImpl;
import com.building.ipi.common.util.Decode;
import com.building.ipi.web.system.dictionary.dao.SysDictionaryMapper;
import com.building.ipi.web.system.dictionary.model.SysDictionary;
import com.building.ipi.web.system.dictionary.model.SysDictionaryExample;
import com.building.ipi.web.system.dictionary.service.SysDictionaryService;
import com.building.ipi.web.common.TreeModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Administrator on 2017/12/26.
 */
@Service
public class SysDictionaryServiceImpl extends GenericServiceImpl<SysDictionary, String> implements SysDictionaryService {

    @Resource
    private SysDictionaryMapper sysDictionaryMapper;

    @Override
    public GenericDao<SysDictionary, String> getDao() {
        return sysDictionaryMapper;
    }

    @Override
    public Page<SysDictionary> selectPageList(Page<SysDictionary> page, HttpServletRequest request) {
        String dictionaryName = request.getParameter("dictionaryName");
        String pid = request.getParameter("pid");
        dictionaryName = Decode.getUtfCode(dictionaryName);
        List<SysDictionary> list = sysDictionaryMapper.selectPageList(page, dictionaryName, pid);
        page.setResult(list);
        return page;
    }

    @Override
    public List<TreeModel> showLeftTree() {
        return sysDictionaryMapper.showLeftTree();
    }

    @Override
    public List<SysDictionary> selectByperCode(String code) {
        SysDictionaryExample sysDictionaryExample = new SysDictionaryExample();
        sysDictionaryExample.createCriteria().andCodeEqualTo(code);
        return sysDictionaryMapper.selectByExample(sysDictionaryExample);
    }

    @Override
    public int deleteDictionary(String id) {
        return sysDictionaryMapper.deleteByPrimaryKey(id);
    }


    @Override
    public SysDictionary selectOneById(String id) {
        return sysDictionaryMapper.selectByOne(id);
    }

    @Override
    public List<SysDictionary> getDictionaryByCode(String code) {
        SysDictionaryExample sysDictionaryExample = new SysDictionaryExample();
        sysDictionaryExample.createCriteria().andCodeEqualTo(code);
        List<SysDictionary> sysDictionaries = sysDictionaryMapper.selectByExample(sysDictionaryExample);
        sysDictionaryExample = new SysDictionaryExample();
        sysDictionaryExample.createCriteria().andPidEqualTo(sysDictionaries.get(0).getId()).andIsactiveEqualTo("1");
        return sysDictionaryMapper.selectByExample(sysDictionaryExample);

    }

    @Override
    public List<SysDictionary> getDictionaryById(String id) {
        SysDictionaryExample sysDictionaryExample = new SysDictionaryExample();
        sysDictionaryExample.createCriteria().andPidEqualTo(id).andIsactiveEqualTo("1");
        return sysDictionaryMapper.selectByExample(sysDictionaryExample);
    }
}
