package cn.skuu.erp.service.purchase;

import cn.skuu.erp.controller.admin.purchase.vo.supplier.ErpSupplierPageReqVO;
import cn.skuu.erp.controller.admin.purchase.vo.supplier.ErpSupplierSaveReqVO;
import cn.skuu.erp.dal.dataobject.purchase.ErpSupplierDO;
import cn.skuu.erp.dal.mysql.purchase.ErpSupplierMapper;
import cn.skuu.erp.enums.ErrorCodeConstants;
import cn.skuu.framework.common.enums.CommonStatusEnum;
import cn.skuu.framework.common.exception.util.ServiceExceptionUtil;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.common.util.object.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * ERP 供应商 Service 实现类
 *
 * @author skuu
 */
@Service
@Validated
public class ErpSupplierServiceImpl implements ErpSupplierService {

    @Resource
    private ErpSupplierMapper supplierMapper;

    @Override
    public Long createSupplier(ErpSupplierSaveReqVO createReqVO) {
        ErpSupplierDO supplier = BeanUtils.toBean(createReqVO, ErpSupplierDO.class);
        supplierMapper.insert(supplier);
        return supplier.getId();
    }

    @Override
    public void updateSupplier(ErpSupplierSaveReqVO updateReqVO) {
        // 校验存在
        validateSupplierExists(updateReqVO.getId());
        // 更新
        ErpSupplierDO updateObj = BeanUtils.toBean(updateReqVO, ErpSupplierDO.class);
        supplierMapper.updateById(updateObj);
    }

    @Override
    public void deleteSupplier(Long id) {
        // 校验存在
        validateSupplierExists(id);
        // 删除
        supplierMapper.deleteById(id);
    }

    private void validateSupplierExists(Long id) {
        if (supplierMapper.selectById(id) == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.SUPPLIER_NOT_EXISTS);
        }
    }

    @Override
    public ErpSupplierDO getSupplier(Long id) {
        return supplierMapper.selectById(id);
    }

    @Override
    public ErpSupplierDO validateSupplier(Long id) {
        ErpSupplierDO supplier = supplierMapper.selectById(id);
        if (supplier == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.SUPPLIER_NOT_EXISTS);
        }
        if (CommonStatusEnum.isDisable(supplier.getStatus())) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.SUPPLIER_NOT_ENABLE, supplier.getName());
        }
        return supplier;
    }

    @Override
    public List<ErpSupplierDO> getSupplierList(Collection<Long> ids) {
        return supplierMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<ErpSupplierDO> getSupplierPage(ErpSupplierPageReqVO pageReqVO) {
        return supplierMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ErpSupplierDO> getSupplierListByStatus(Integer status) {
        return supplierMapper.selectListByStatus(status);
    }

}