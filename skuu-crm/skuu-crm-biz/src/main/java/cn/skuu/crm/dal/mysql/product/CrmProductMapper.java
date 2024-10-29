package cn.skuu.crm.dal.mysql.product;

import cn.skuu.crm.controller.admin.product.vo.product.CrmProductPageReqVO;
import cn.skuu.crm.dal.dataobject.product.CrmProductDO;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CRM 产品 Mapper
 *
 * @author ZanGe丶
 */
@Mapper
public interface CrmProductMapper extends BaseMapperX<CrmProductDO> {

    default PageResult<CrmProductDO> selectPage(CrmProductPageReqVO reqVO) {
//        return selectPage(reqVO, new MPJLambdaWrapperX<CrmProductDO>()
//                .likeIfPresent(CrmProductDO::getName, reqVO.getName())
//                .eqIfPresent(CrmProductDO::getStatus, reqVO.getStatus())
//                .orderByDesc(CrmProductDO::getId));
        return null;
    }

    default CrmProductDO selectByNo(String no) {
        return selectOne(CrmProductDO::getNo, no);
    }

    default Long selectCountByCategoryId(Long categoryId) {
        return selectCount(CrmProductDO::getCategoryId, categoryId);
    }

    default List<CrmProductDO> selectListByStatus(Integer status) {
        return selectList(CrmProductDO::getStatus, status);
    }

}
