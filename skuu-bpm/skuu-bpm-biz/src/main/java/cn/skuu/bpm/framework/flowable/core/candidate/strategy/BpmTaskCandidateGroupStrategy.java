package cn.skuu.bpm.framework.flowable.core.candidate.strategy;

import cn.skuu.bpm.dal.dataobject.definition.BpmUserGroupDO;
import cn.skuu.bpm.framework.flowable.core.candidate.BpmTaskCandidateStrategy;
import cn.skuu.bpm.framework.flowable.core.enums.BpmTaskCandidateStrategyEnum;
import cn.skuu.bpm.service.definition.BpmUserGroupService;
import cn.skuu.framework.common.util.string.StrUtils;
import cn.skuu.system.api.user.AdminUserApi;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static cn.skuu.framework.common.util.collection.CollectionUtils.convertSetByFlatMap;

/**
 * 用户组 {@link BpmTaskCandidateStrategy} 实现类
 *
 * @author kyle
 */
@Component
public class BpmTaskCandidateGroupStrategy extends BpmTaskCandidateAbstractStrategy {

    private final BpmUserGroupService userGroupService;

    public BpmTaskCandidateGroupStrategy(AdminUserApi adminUserApi, BpmUserGroupService userGroupService) {
        super(adminUserApi);
        this.userGroupService = userGroupService;
    }

    @Override
    public BpmTaskCandidateStrategyEnum getStrategy() {
        return BpmTaskCandidateStrategyEnum.USER_GROUP;
    }

    @Override
    public void validateParam(String param) {
        Set<Long> groupIds = StrUtils.splitToLongSet(param);
        userGroupService.getUserGroupList(groupIds);
    }

    @Override
    public Set<Long> calculateUsers(String param) {
        Set<Long> groupIds = StrUtils.splitToLongSet(param);
        List<BpmUserGroupDO> groups = userGroupService.getUserGroupList(groupIds);
        return convertSetByFlatMap(groups, BpmUserGroupDO::getUserIds, Collection::stream);
    }

}