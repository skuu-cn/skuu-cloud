package cn.skuu.member.service.point;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.skuu.framework.common.exception.util.ServiceExceptionUtil;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.common.util.collection.CollectionUtils;
import cn.skuu.member.controller.admin.point.vo.recrod.MemberPointRecordPageReqVO;
import cn.skuu.member.controller.app.point.vo.AppMemberPointRecordPageReqVO;
import cn.skuu.member.dal.dataobject.point.MemberPointRecordDO;
import cn.skuu.member.dal.dataobject.user.MemberUserDO;
import cn.skuu.member.dal.mysql.point.MemberPointRecordMapper;
import cn.skuu.member.enums.ErrorCodeConstants;
import cn.skuu.member.enums.point.MemberPointBizTypeEnum;
import cn.skuu.member.service.user.MemberUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * 积分记录 Service 实现类
 *
 * @author QingX
 */
@Slf4j
@Service
@Validated
public class MemberPointRecordServiceImpl implements MemberPointRecordService {

    @Resource
    private MemberPointRecordMapper memberPointRecordMapper;

    @Resource
    private MemberUserService memberUserService;

    @Override
    public PageResult<MemberPointRecordDO> getPointRecordPage(MemberPointRecordPageReqVO pageReqVO) {
        // 根据用户昵称查询出用户 ids
        Set<Long> userIds = null;
        if (StringUtils.isNotBlank(pageReqVO.getNickname())) {
            List<MemberUserDO> users = memberUserService.getUserListByNickname(pageReqVO.getNickname());
            // 如果查询用户结果为空直接返回无需继续查询
            if (users.isEmpty()) {
                return PageResult.empty();
            }
            userIds = CollectionUtils.convertSet(users, MemberUserDO::getId);
        }
        // 执行查询
        return memberPointRecordMapper.selectPage(pageReqVO, userIds);
    }

    @Override
    public PageResult<MemberPointRecordDO> getPointRecordPage(Long userId, AppMemberPointRecordPageReqVO pageReqVO) {
        return memberPointRecordMapper.selectPage(userId, pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createPointRecord(Long userId, Integer point, MemberPointBizTypeEnum bizType, String bizId) {
        if (point == 0) {
            return;
        }
        // 1. 校验用户积分余额
        MemberUserDO user = memberUserService.getUser(userId);
        Integer userPoint = ObjectUtil.defaultIfNull(user.getPoint(), 0);
        int totalPoint = userPoint + point; // 用户变动后的积分
        if (totalPoint < 0) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.USER_POINT_NOT_ENOUGH);
        }

        // 2. 更新用户积分
        boolean success = memberUserService.updateUserPoint(userId, point);
        if (!success) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.USER_POINT_NOT_ENOUGH);
        }

        // 3. 增加积分记录
        MemberPointRecordDO record = new MemberPointRecordDO()
                .setUserId(userId).setBizId(bizId).setBizType(bizType.getType())
                .setTitle(bizType.getName()).setDescription(StrUtil.format(bizType.getDescription(), point))
                .setPoint(point).setTotalPoint(totalPoint);
        memberPointRecordMapper.insert(record);
    }

}