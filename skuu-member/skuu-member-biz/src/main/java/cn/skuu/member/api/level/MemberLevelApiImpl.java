package cn.skuu.member.api.level;

import cn.skuu.framework.common.exception.util.ServiceExceptionUtil;
import cn.skuu.framework.common.pojo.CommonResult;
import cn.skuu.member.api.level.dto.MemberLevelRespDTO;
import cn.skuu.member.convert.level.MemberLevelConvert;
import cn.skuu.member.enums.ErrorCodeConstants;
import cn.skuu.member.enums.MemberExperienceBizTypeEnum;
import cn.skuu.member.service.level.MemberLevelService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 会员等级 API 实现类
 *
 * @author owen
 */
@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class MemberLevelApiImpl implements MemberLevelApi {

    @Resource
    private MemberLevelService memberLevelService;

    @Override
    public CommonResult<MemberLevelRespDTO> getMemberLevel(Long id) {
        return CommonResult.success(MemberLevelConvert.INSTANCE.convert02(memberLevelService.getLevel(id)));
    }

    @Override
    public CommonResult<Boolean> addExperience(Long userId, Integer experience, Integer bizType, String bizId) {
        MemberExperienceBizTypeEnum bizTypeEnum = MemberExperienceBizTypeEnum.getByType(bizType);
        if (bizTypeEnum == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.EXPERIENCE_BIZ_NOT_SUPPORT);
        }
        memberLevelService.addExperience(userId, experience, bizTypeEnum, bizId);
        return CommonResult.success(true);
    }

    @Override
    public CommonResult<Boolean> reduceExperience(Long userId, Integer experience, Integer bizType, String bizId) {
        return addExperience(userId, -experience, bizType, bizId);
    }

}
