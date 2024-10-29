package cn.skuu.crm.service.receivable;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.skuu.crm.controller.admin.receivable.vo.plan.CrmReceivablePlanPageReqVO;
import cn.skuu.crm.controller.admin.receivable.vo.plan.CrmReceivablePlanSaveReqVO;
import cn.skuu.crm.dal.dataobject.contract.CrmContractDO;
import cn.skuu.crm.dal.dataobject.receivable.CrmReceivablePlanDO;
import cn.skuu.crm.dal.mysql.receivable.CrmReceivablePlanMapper;
import cn.skuu.crm.enums.common.CrmBizTypeEnum;
import cn.skuu.crm.enums.permission.CrmPermissionLevelEnum;
import cn.skuu.crm.framework.permission.core.annotations.CrmPermission;
import cn.skuu.crm.service.contract.CrmContractService;
import cn.skuu.crm.service.permission.CrmPermissionService;
import cn.skuu.crm.service.permission.bo.CrmPermissionCreateReqBO;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.common.util.object.BeanUtils;
import cn.skuu.system.api.user.AdminUserApi;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static cn.skuu.crm.enums.ErrorCodeConstants.RECEIVABLE_PLAN_NOT_EXISTS;
import static cn.skuu.crm.enums.ErrorCodeConstants.RECEIVABLE_PLAN_UPDATE_FAIL;
import static cn.skuu.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 回款计划 Service 实现类
 *
 * @author skuu
 */
@Service
@Validated
public class CrmReceivablePlanServiceImpl implements CrmReceivablePlanService {

    @Resource
    private CrmReceivablePlanMapper receivablePlanMapper;

    @Resource
    private CrmContractService contractService;
    @Resource
    private CrmPermissionService permissionService;

    @Resource
    private AdminUserApi adminUserApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createReceivablePlan(CrmReceivablePlanSaveReqVO createReqVO) {
        // 1. 校验关联数据是否存在
        validateRelationDataExists(createReqVO);

        // 2. 插入回款计划
        CrmReceivablePlanDO maxPeriodReceivablePlan = receivablePlanMapper.selectMaxPeriodByContractId(createReqVO.getContractId());
        int period = maxPeriodReceivablePlan == null ? 1 : maxPeriodReceivablePlan.getPeriod() + 1;
        CrmReceivablePlanDO receivablePlan = BeanUtils.toBean(createReqVO, CrmReceivablePlanDO.class).setPeriod(period);
        if (createReqVO.getReturnTime() != null && createReqVO.getRemindDays() != null) {
            receivablePlan.setRemindTime(createReqVO.getReturnTime().minusDays(createReqVO.getRemindDays()));
        }
        receivablePlanMapper.insert(receivablePlan);

        // 3. 创建数据权限
        permissionService.createPermission(new CrmPermissionCreateReqBO().setUserId(createReqVO.getOwnerUserId())
                .setBizType(CrmBizTypeEnum.CRM_RECEIVABLE_PLAN.getType()).setBizId(receivablePlan.getId())
                .setLevel(CrmPermissionLevelEnum.OWNER.getLevel()));

        // 4. 记录操作日志上下文
        return receivablePlan.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_RECEIVABLE_PLAN, bizId = "#updateReqVO.id", level = CrmPermissionLevelEnum.WRITE)
    public void updateReceivablePlan(CrmReceivablePlanSaveReqVO updateReqVO) {
        updateReqVO.setOwnerUserId(null).setCustomerId(null).setContractId(null); // 防止修改这些字段
        // 1.1 校验存在
        validateRelationDataExists(updateReqVO);
        // 1.2 校验关联数据是否存在
        CrmReceivablePlanDO oldReceivablePlan = validateReceivablePlanExists(updateReqVO.getId());
        // 1.3 如果已经有对应的回款，则不允许编辑
        if (Objects.nonNull(oldReceivablePlan.getReceivableId())) {
            throw exception(RECEIVABLE_PLAN_UPDATE_FAIL);
        }

        // 2. 更新回款计划
        CrmReceivablePlanDO updateObj = BeanUtils.toBean(updateReqVO, CrmReceivablePlanDO.class);
        if (updateReqVO.getReturnTime() != null && updateReqVO.getRemindDays() != null) {
            updateObj.setRemindTime(updateReqVO.getReturnTime().minusDays(updateReqVO.getRemindDays()));
        }
        receivablePlanMapper.updateById(updateObj);

        // 3. 记录操作日志上下文
    }

    private void validateRelationDataExists(CrmReceivablePlanSaveReqVO reqVO) {
        // 校验负责人存在
        if (reqVO.getOwnerUserId() != null) {
            adminUserApi.validateUser(reqVO.getOwnerUserId());
        }
        // 校验合同存在
        if (reqVO.getContractId() != null) {
            CrmContractDO contract = contractService.getContract(reqVO.getContractId());
            reqVO.setCustomerId(contract.getCustomerId());
        }
    }

    @Override
    public void updateReceivablePlanReceivableId(Long id, Long receivableId) {
        // 校验存在
        validateReceivablePlanExists(id);
        // 更新回款计划
        receivablePlanMapper.updateById(new CrmReceivablePlanDO().setId(id).setReceivableId(receivableId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_RECEIVABLE_PLAN, bizId = "#id", level = CrmPermissionLevelEnum.OWNER)
    public void deleteReceivablePlan(Long id) {
        // 1. 校验存在
        CrmReceivablePlanDO receivablePlan = validateReceivablePlanExists(id);

        // 2. 删除
        receivablePlanMapper.deleteById(id);
        // 3. 删除数据权限
        permissionService.deletePermission(CrmBizTypeEnum.CRM_RECEIVABLE_PLAN.getType(), id);

        // 4. 记录操作日志上下文
    }

    private CrmReceivablePlanDO validateReceivablePlanExists(Long id) {
        CrmReceivablePlanDO receivablePlan = receivablePlanMapper.selectById(id);
        if (receivablePlan == null) {
            throw exception(RECEIVABLE_PLAN_NOT_EXISTS);
        }
        return receivablePlan;
    }

    @Override
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_RECEIVABLE_PLAN, bizId = "#id", level = CrmPermissionLevelEnum.READ)
    public CrmReceivablePlanDO getReceivablePlan(Long id) {
        return receivablePlanMapper.selectById(id);
    }

    @Override
    public List<CrmReceivablePlanDO> getReceivablePlanList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }
        return receivablePlanMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<CrmReceivablePlanDO> getReceivablePlanPage(CrmReceivablePlanPageReqVO pageReqVO, Long userId) {
        return receivablePlanMapper.selectPage(pageReqVO, userId);
    }

    @Override
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_CUSTOMER, bizId = "#pageReqVO.customerId", level = CrmPermissionLevelEnum.READ)
    public PageResult<CrmReceivablePlanDO> getReceivablePlanPageByCustomerId(CrmReceivablePlanPageReqVO pageReqVO) {
        return receivablePlanMapper.selectPageByCustomerId(pageReqVO);
    }

    @Override
    public Long getReceivablePlanRemindCount(Long userId) {
        return receivablePlanMapper.selectReceivablePlanCountByRemind(userId);
    }

}
