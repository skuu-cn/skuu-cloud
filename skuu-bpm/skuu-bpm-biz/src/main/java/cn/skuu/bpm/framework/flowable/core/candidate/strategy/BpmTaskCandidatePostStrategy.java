package cn.skuu.bpm.framework.flowable.core.candidate.strategy;

import cn.skuu.bpm.framework.flowable.core.candidate.BpmTaskCandidateStrategy;
import cn.skuu.bpm.framework.flowable.core.enums.BpmTaskCandidateStrategyEnum;
import cn.skuu.framework.common.util.string.StrUtils;
import cn.skuu.system.api.dept.PostApi;
import cn.skuu.system.api.user.AdminUserApi;
import cn.skuu.system.api.user.dto.AdminUserRespDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

import static cn.skuu.framework.common.util.collection.CollectionUtils.convertSet;

/**
 * 岗位 {@link BpmTaskCandidateStrategy} 实现类
 *
 * @author kyle
 */
@Component
public class BpmTaskCandidatePostStrategy extends BpmTaskCandidateAbstractStrategy {

    private final PostApi postApi;

    public BpmTaskCandidatePostStrategy(AdminUserApi adminUserApi, PostApi postApi) {
        super(adminUserApi);
        this.postApi = postApi;
    }

    @Override
    public BpmTaskCandidateStrategyEnum getStrategy() {
        return BpmTaskCandidateStrategyEnum.POST;
    }

    @Override
    public void validateParam(String param) {
        Set<Long> postIds = StrUtils.splitToLongSet(param);
        postApi.validPostList(postIds);
    }

    @Override
    public Set<Long> calculateUsers(String param) {
        Set<Long> postIds = StrUtils.splitToLongSet(param);
        List<AdminUserRespDTO> users = adminUserApi.getUserListByPostIds(postIds).getCheckedData();
        return convertSet(users, AdminUserRespDTO::getId);
    }

}