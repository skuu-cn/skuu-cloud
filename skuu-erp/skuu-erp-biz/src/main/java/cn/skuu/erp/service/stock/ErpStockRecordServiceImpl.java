package cn.skuu.erp.service.stock;

import cn.skuu.erp.controller.admin.stock.vo.record.ErpStockRecordPageReqVO;
import cn.skuu.erp.dal.dataobject.stock.ErpStockRecordDO;
import cn.skuu.erp.dal.mysql.stock.ErpStockRecordMapper;
import cn.skuu.erp.service.stock.bo.ErpStockRecordCreateReqBO;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.common.util.object.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * ERP 产品库存明细 Service 实现类
 *
 * @author skuu
 */
@Service
@Validated
public class ErpStockRecordServiceImpl implements ErpStockRecordService {

    @Resource
    private ErpStockRecordMapper stockRecordMapper;

    @Resource
    private ErpStockService stockService;

    @Override
    public ErpStockRecordDO getStockRecord(Long id) {
        return stockRecordMapper.selectById(id);
    }

    @Override
    public PageResult<ErpStockRecordDO> getStockRecordPage(ErpStockRecordPageReqVO pageReqVO) {
        return stockRecordMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createStockRecord(ErpStockRecordCreateReqBO createReqBO) {
        // 1. 更新库存
        BigDecimal totalCount = stockService.updateStockCountIncrement(
                createReqBO.getProductId(), createReqBO.getWarehouseId(), createReqBO.getCount());
        // 2. 创建库存明细
        ErpStockRecordDO stockRecord = BeanUtils.toBean(createReqBO, ErpStockRecordDO.class)
                .setTotalCount(totalCount);
        stockRecordMapper.insert(stockRecord);
    }

}