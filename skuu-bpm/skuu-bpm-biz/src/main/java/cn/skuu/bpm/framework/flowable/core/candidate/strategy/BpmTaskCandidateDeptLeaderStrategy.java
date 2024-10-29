package cn.skuu.bpm.framework.flowable.core.candidate.strategy;

import cn.skuu.bpm.framework.flowable.core.candidate.BpmTaskCandidateStrategy;
import cn.skuu.bpm.framework.flowable.core.enums.BpmTaskCandidateStrategyEnum;
import cn.skuu.framework.common.util.string.StrUtils;
import cn.skuu.system.api.dept.DeptApi;
import cn.skuu.system.api.dept.dto.DeptRespDTO;
import cn.skuu.system.api.user.AdminUserApi;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

import static cn.skuu.framework.common.util.collection.CollectionUtils.convertSet;

/**
 * 部门的负责人 {@link BpmTaskCandidateStrategy} 实现类
 *
 * @author kyle
 */
@Component
public class BpmTaskCandidateDeptLeaderStrategy extends BpmTaskCandidateAbstractStrategy {

    private final DeptApi deptApi;

    public BpmTaskCandidateDeptLeaderStrategy(AdminUserApi adminUserApi, DeptApi deptApi) {
        super(adminUserApi);
        this.deptApi = deptApi;
    }

    @Override
    public BpmTaskCandidateStrategyEnum getStrategy() {
        return BpmTaskCandidateStrategyEnum.DEPT_LEADER;
    }

    @Override
    public void validateParam(String param) {
        Set<Long> deptIds = StrUtils.splitToLongSet(param);
        deptApi.validateDeptList(deptIds);
    }

    @Override
    public Set<Long> calculateUsers(String param) {
        Set<Long> deptIds = StrUtils.splitToLongSet(param);
        List<DeptRespDTO> depts = deptApi.getDeptList(deptIds).getCheckedData();
        return convertSet(depts, DeptRespDTO::getLeaderUserId);
    }

}