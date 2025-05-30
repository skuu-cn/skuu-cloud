package cn.skuu.bpm.service.definition;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.skuu.bpm.controller.admin.definition.vo.category.BpmCategoryPageReqVO;
import cn.skuu.bpm.controller.admin.definition.vo.category.BpmCategorySaveReqVO;
import cn.skuu.bpm.dal.dataobject.definition.BpmCategoryDO;
import cn.skuu.bpm.dal.mysql.category.BpmCategoryMapper;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.common.util.object.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static cn.skuu.bpm.enums.ErrorCodeConstants.*;
import static cn.skuu.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * BPM 流程分类 Service 实现类
 *
 * @author skuu
 */
@Service
@Validated
public class BpmCategoryServiceImpl implements BpmCategoryService {

    @Resource
    private BpmCategoryMapper bpmCategoryMapper;

    @Override
    public Long createCategory(BpmCategorySaveReqVO createReqVO) {
        // 校验唯一
        validateCategoryNameUnique(createReqVO);
        validateCategoryCodeUnique(createReqVO);
        // 插入
        BpmCategoryDO category = BeanUtils.toBean(createReqVO, BpmCategoryDO.class);
        bpmCategoryMapper.insert(category);
        return category.getId();
    }

    @Override
    public void updateCategory(BpmCategorySaveReqVO updateReqVO) {
        // 校验存在
        validateCategoryExists(updateReqVO.getId());
        validateCategoryNameUnique(updateReqVO);
        validateCategoryCodeUnique(updateReqVO);
        // 更新
        BpmCategoryDO updateObj = BeanUtils.toBean(updateReqVO, BpmCategoryDO.class);
        bpmCategoryMapper.updateById(updateObj);
    }

    private void validateCategoryNameUnique(BpmCategorySaveReqVO updateReqVO) {
        BpmCategoryDO category = bpmCategoryMapper.selectByName(updateReqVO.getName());
        if (category == null
            || ObjUtil.equal(category.getId(), updateReqVO.getId())) {
            return;
        }
        throw exception(CATEGORY_NAME_DUPLICATE, updateReqVO.getName());
    }

    private void validateCategoryCodeUnique(BpmCategorySaveReqVO updateReqVO) {
        BpmCategoryDO category = bpmCategoryMapper.selectByCode(updateReqVO.getCode());
        if (category == null
            || ObjUtil.equal(category.getId(), updateReqVO.getId())) {
            return;
        }
        throw exception(CATEGORY_CODE_DUPLICATE, updateReqVO.getCode());
    }

    @Override
    public void deleteCategory(Long id) {
        // 校验存在
        validateCategoryExists(id);
        // 删除
        bpmCategoryMapper.deleteById(id);
    }

    private void validateCategoryExists(Long id) {
        if (bpmCategoryMapper.selectById(id) == null) {
            throw exception(CATEGORY_NOT_EXISTS);
        }
    }

    @Override
    public BpmCategoryDO getCategory(Long id) {
        return bpmCategoryMapper.selectById(id);
    }

    @Override
    public PageResult<BpmCategoryDO> getCategoryPage(BpmCategoryPageReqVO pageReqVO) {
        return bpmCategoryMapper.selectPage(pageReqVO);
    }

    @Override
    public List<BpmCategoryDO> getCategoryListByCode(Collection<String> codes) {
        if (CollUtil.isEmpty(codes)) {
            return Collections.emptyList();
        }
        return bpmCategoryMapper.selectListByCode(codes);
    }

    @Override
    public List<BpmCategoryDO> getCategoryListByStatus(Integer status) {
        return bpmCategoryMapper.selectListByStatus(status);
    }

}