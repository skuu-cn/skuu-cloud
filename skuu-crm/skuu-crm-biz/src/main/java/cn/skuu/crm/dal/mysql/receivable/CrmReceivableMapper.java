package cn.skuu.crm.dal.mysql.receivable;

import cn.hutool.core.collection.CollUtil;
import cn.skuu.crm.controller.admin.receivable.vo.receivable.CrmReceivablePageReqVO;
import cn.skuu.crm.dal.dataobject.receivable.CrmReceivableDO;
import cn.skuu.crm.enums.common.CrmAuditStatusEnum;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.mybatis.core.mapper.BaseMapperX;
import cn.skuu.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static cn.skuu.framework.common.util.collection.CollectionUtils.convertMap;

/**
 * 回款 Mapper
 *
 * @author 赤焰
 */
@Mapper
public interface CrmReceivableMapper extends BaseMapperX<CrmReceivableDO> {

    default CrmReceivableDO selectByNo(String no) {
        return selectOne(CrmReceivableDO::getNo, no);
    }

    default PageResult<CrmReceivableDO> selectPageByCustomerId(CrmReceivablePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CrmReceivableDO>()
                .eq(CrmReceivableDO::getCustomerId, reqVO.getCustomerId()) // 必须传递
                .eqIfPresent(CrmReceivableDO::getNo, reqVO.getNo())
                .eqIfPresent(CrmReceivableDO::getContractId, reqVO.getContractId())
                .eqIfPresent(CrmReceivableDO::getPlanId, reqVO.getPlanId())
                .orderByDesc(CrmReceivableDO::getId));
    }

    default PageResult<CrmReceivableDO> selectPage(CrmReceivablePageReqVO pageReqVO, Long userId) {
//        MPJLambdaWrapperX<CrmReceivableDO> query = new MPJLambdaWrapperX<>();
//        // 拼接数据权限的查询条件
//        CrmPermissionUtils.appendPermissionCondition(query, CrmBizTypeEnum.CRM_RECEIVABLE.getType(),
//                CrmReceivableDO::getId, userId, pageReqVO.getSceneType());
//        // 拼接自身的查询条件
//        query.selectAll(CrmReceivableDO.class)
//                .eqIfPresent(CrmReceivableDO::getNo, pageReqVO.getNo())
//                .eqIfPresent(CrmReceivableDO::getPlanId, pageReqVO.getPlanId())
//                .eqIfPresent(CrmReceivableDO::getContractId, pageReqVO.getContractId())
//                .eqIfPresent(CrmReceivableDO::getAuditStatus, pageReqVO.getAuditStatus())
//                .orderByDesc(CrmReceivableDO::getId);
//        return selectJoinPage(pageReqVO, CrmReceivableDO.class, query);
        return null;
    }

    default Long selectCountByAudit(Long userId) {
//        MPJLambdaWrapperX<CrmReceivableDO> query = new MPJLambdaWrapperX<>();
//        // 我负责的 + 非公海
//        CrmPermissionUtils.appendPermissionCondition(query, CrmBizTypeEnum.CRM_RECEIVABLE.getType(),
//                CrmReceivableDO::getId, userId, CrmSceneTypeEnum.OWNER.getType());
//        // 未审核
//        query.eq(CrmContractDO::getAuditStatus, CrmAuditStatusEnum.PROCESS.getStatus());
//        return selectCount(query);
        return 0L;
    }

    default List<CrmReceivableDO> selectListByContractIdAndStatus(Long contractId, Collection<Integer> auditStatuses) {
        return selectList(new LambdaQueryWrapperX<CrmReceivableDO>()
                .eq(CrmReceivableDO::getContractId, contractId)
                .in(CrmReceivableDO::getAuditStatus, auditStatuses));
    }

    default Map<Long, BigDecimal> selectReceivablePriceMapByContractId(Collection<Long> contractIds) {
        if (CollUtil.isEmpty(contractIds)) {
            return Collections.emptyMap();
        }
        // SQL sum 查询
        List<Map<String, Object>> result = selectMaps(new QueryWrapper<CrmReceivableDO>()
                .select("contract_id, SUM(price) AS total_price")
                .in("audit_status", CrmAuditStatusEnum.DRAFT.getStatus(), // 草稿 + 审批中 + 审批通过
                        CrmAuditStatusEnum.PROCESS.getStatus(), CrmAuditStatusEnum.APPROVE.getStatus())
                .groupBy("contract_id")
                .in("contract_id", contractIds));
        // 获得金额
        return convertMap(result, obj -> (Long) obj.get("contract_id"), obj -> (BigDecimal) obj.get("total_price"));
    }

    default Long selectCountByContractId(Long contractId) {
        return selectCount(CrmReceivableDO::getContractId, contractId);
    }

}
