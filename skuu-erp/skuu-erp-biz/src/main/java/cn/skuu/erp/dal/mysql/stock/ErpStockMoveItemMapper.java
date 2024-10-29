package cn.skuu.erp.dal.mysql.stock;

import cn.skuu.erp.dal.dataobject.stock.ErpStockMoveItemDO;
import cn.skuu.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * ERP 库存调拨单项 Mapper
 *
 * @author skuu
 */
@Mapper
public interface ErpStockMoveItemMapper extends BaseMapperX<ErpStockMoveItemDO> {

    default List<ErpStockMoveItemDO> selectListByMoveId(Long moveId) {
        return selectList(ErpStockMoveItemDO::getMoveId, moveId);
    }

    default List<ErpStockMoveItemDO> selectListByMoveIds(Collection<Long> moveIds) {
        return selectList(ErpStockMoveItemDO::getMoveId, moveIds);
    }

    default int deleteByMoveId(Long moveId) {
        return delete(ErpStockMoveItemDO::getMoveId, moveId);
    }

}