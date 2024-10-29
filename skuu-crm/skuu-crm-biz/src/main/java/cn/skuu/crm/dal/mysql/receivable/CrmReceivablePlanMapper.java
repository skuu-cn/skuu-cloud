package cn.skuu.crm.dal.mysql.receivable;

import cn.skuu.crm.controller.admin.receivable.vo.plan.CrmReceivablePlanPageReqVO;
import cn.skuu.crm.dal.dataobject.receivable.CrmReceivablePlanDO;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 回款计划 Mapper
 *
 * @author skuu
 */
@Mapper
public interface CrmReceivablePlanMapper extends BaseMapperX<CrmReceivablePlanDO> {

    default CrmReceivablePlanDO selectMaxPeriodByContractId(Long contractId) {
//        return selectOne(new MPJLambdaWrapperX<CrmReceivablePlanDO>()
//                .eq(CrmReceivablePlanDO::getContractId, contractId)
//                .orderByDesc(CrmReceivablePlanDO::getPeriod)
//                .last("LIMIT 1"));
        return null;
    }

    default PageResult<CrmReceivablePlanDO> selectPageByCustomerId(CrmReceivablePlanPageReqVO reqVO) {
//        MPJLambdaWrapperX<CrmReceivablePlanDO> query = new MPJLambdaWrapperX<>();
//        if (Objects.nonNull(reqVO.getContractNo())) { // 根据合同编号检索
//            query.innerJoin(CrmContractDO.class, on -> on.like(CrmContractDO::getNo, reqVO.getContractNo())
//                    .eq(CrmContractDO::getId, CrmReceivablePlanDO::getContractId));
//        }
//        query.eq(CrmReceivablePlanDO::getCustomerId, reqVO.getCustomerId()) // 必须传递
//                .eqIfPresent(CrmReceivablePlanDO::getContractId, reqVO.getContractId())
//                .orderByDesc(CrmReceivablePlanDO::getPeriod);
//        return selectJoinPage(reqVO, CrmReceivablePlanDO.class, query);
        return null;
    }

    default PageResult<CrmReceivablePlanDO> selectPage(CrmReceivablePlanPageReqVO pageReqVO, Long userId) {
//        MPJLambdaWrapperX<CrmReceivablePlanDO> query = new MPJLambdaWrapperX<>();
//        // 拼接数据权限的查询条件
//        CrmPermissionUtils.appendPermissionCondition(query, CrmBizTypeEnum.CRM_RECEIVABLE_PLAN.getType(),
//                CrmReceivablePlanDO::getId, userId, pageReqVO.getSceneType());
//        // 拼接自身的查询条件
//        query.selectAll(CrmReceivablePlanDO.class)
//                .eqIfPresent(CrmReceivablePlanDO::getCustomerId, pageReqVO.getCustomerId())
//                .eqIfPresent(CrmReceivablePlanDO::getContractId, pageReqVO.getContractId())
//                .orderByDesc(CrmReceivablePlanDO::getPeriod);
//        if (Objects.nonNull(pageReqVO.getContractNo())) { // 根据合同编号检索
//            query.innerJoin(CrmContractDO.class, on -> on.like(CrmContractDO::getNo, pageReqVO.getContractNo())
//                    .eq(CrmContractDO::getId, CrmReceivablePlanDO::getContractId));
//        }
//
//        // Backlog: 回款提醒类型
//        LocalDateTime beginOfToday = LocalDateTimeUtil.beginOfDay(LocalDateTime.now());
//        if (CrmReceivablePlanPageReqVO.REMIND_TYPE_NEEDED.equals(pageReqVO.getRemindType())) { // 待回款
//            query.isNull(CrmReceivablePlanDO::getReceivableId) // 未回款
//                    .lt(CrmReceivablePlanDO::getReturnTime, beginOfToday) // 已逾期
//                    .lt(CrmReceivablePlanDO::getRemindTime, beginOfToday); // 今天开始提醒
//        } else if (CrmReceivablePlanPageReqVO.REMIND_TYPE_EXPIRED.equals(pageReqVO.getRemindType())) {  // 已逾期
//            query.isNull(CrmReceivablePlanDO::getReceivableId) // 未回款
//                    .ge(CrmReceivablePlanDO::getReturnTime, beginOfToday); // 已逾期
//        } else if (CrmReceivablePlanPageReqVO.REMIND_TYPE_RECEIVED.equals(pageReqVO.getRemindType())) { // 已回款
//            query.isNotNull(CrmReceivablePlanDO::getReceivableId);
//        }
//        return selectJoinPage(pageReqVO, CrmReceivablePlanDO.class, query);
        return null;
    }

    default Long selectReceivablePlanCountByRemind(Long userId) {
//        MPJLambdaWrapperX<CrmReceivablePlanDO> query = new MPJLambdaWrapperX<>();
//        // 我负责的 + 非公海
//        CrmPermissionUtils.appendPermissionCondition(query, CrmBizTypeEnum.CRM_RECEIVABLE_PLAN.getType(),
//                CrmReceivablePlanDO::getId, userId, CrmSceneTypeEnum.OWNER.getType());
//        // 未回款 + 已逾期 + 今天开始提醒
//        LocalDateTime beginOfToday = LocalDateTimeUtil.beginOfDay(LocalDateTime.now());
//        query.isNull(CrmReceivablePlanDO::getReceivableId) // 未回款
//                .lt(CrmReceivablePlanDO::getReturnTime, beginOfToday) // 已逾期
//                .lt(CrmReceivablePlanDO::getRemindTime, beginOfToday); // 今天开始提醒
//        return selectCount(query);
        return 0L;
    }

}
