package cn.skuu.erp.service.product;

import cn.skuu.erp.controller.admin.product.vo.unit.ErpProductUnitPageReqVO;
import cn.skuu.erp.controller.admin.product.vo.unit.ErpProductUnitSaveReqVO;
import cn.skuu.erp.dal.dataobject.product.ErpProductUnitDO;
import cn.skuu.erp.dal.mysql.product.ErpProductUnitMapper;
import cn.skuu.erp.enums.ErrorCodeConstants;
import cn.skuu.framework.common.exception.util.ServiceExceptionUtil;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.common.util.object.BeanUtils;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * ERP 产品单位 Service 实现类
 *
 * @author skuu
 */
@Service
@Validated
public class ErpProductUnitServiceImpl implements ErpProductUnitService {

    @Resource
    private ErpProductUnitMapper productUnitMapper;

    @Resource
    @Lazy // 延迟加载，避免循环依赖
    private ErpProductService productService;

    @Override
    public Long createProductUnit(ErpProductUnitSaveReqVO createReqVO) {
        // 1. 校验名字唯一
        validateProductUnitNameUnique(null, createReqVO.getName());
        // 2. 插入
        ErpProductUnitDO unit = BeanUtils.toBean(createReqVO, ErpProductUnitDO.class);
        productUnitMapper.insert(unit);
        return unit.getId();
    }

    @Override
    public void updateProductUnit(ErpProductUnitSaveReqVO updateReqVO) {
        // 1.1 校验存在
        validateProductUnitExists(updateReqVO.getId());
        // 1.2 校验名字唯一
        validateProductUnitNameUnique(updateReqVO.getId(), updateReqVO.getName());
        // 2. 更新
        ErpProductUnitDO updateObj = BeanUtils.toBean(updateReqVO, ErpProductUnitDO.class);
        productUnitMapper.updateById(updateObj);
    }

    @VisibleForTesting
    void validateProductUnitNameUnique(Long id, String name) {
        ErpProductUnitDO unit = productUnitMapper.selectByName(name);
        if (unit == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典类型
        if (id == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.PRODUCT_UNIT_NAME_DUPLICATE);
        }
        if (!unit.getId().equals(id)) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.PRODUCT_UNIT_NAME_DUPLICATE);
        }
    }

    @Override
    public void deleteProductUnit(Long id) {
        // 1.1 校验存在
        validateProductUnitExists(id);
        // 1.2 校验产品是否使用
        if (productService.getProductCountByUnitId(id) > 0) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.PRODUCT_UNIT_EXITS_PRODUCT);
        }
        // 2. 删除
        productUnitMapper.deleteById(id);
    }

    private void validateProductUnitExists(Long id) {
        if (productUnitMapper.selectById(id) == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.PRODUCT_UNIT_NOT_EXISTS);
        }
    }

    @Override
    public ErpProductUnitDO getProductUnit(Long id) {
        return productUnitMapper.selectById(id);
    }

    @Override
    public PageResult<ErpProductUnitDO> getProductUnitPage(ErpProductUnitPageReqVO pageReqVO) {
        return productUnitMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ErpProductUnitDO> getProductUnitListByStatus(Integer status) {
        return productUnitMapper.selectListByStatus(status);
    }

    @Override
    public List<ErpProductUnitDO> getProductUnitList(Collection<Long> ids) {
         return productUnitMapper.selectBatchIds(ids);
    }

}