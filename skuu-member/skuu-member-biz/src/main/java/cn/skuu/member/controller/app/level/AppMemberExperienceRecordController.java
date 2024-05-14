package cn.skuu.member.controller.app.level;

import cn.skuu.framework.common.pojo.CommonResult;
import cn.skuu.framework.common.pojo.PageParam;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.security.core.annotations.PreAuthenticated;
import cn.skuu.member.controller.app.level.vo.experience.AppMemberExperienceRecordRespVO;
import cn.skuu.member.convert.level.MemberExperienceRecordConvert;
import cn.skuu.member.dal.dataobject.level.MemberExperienceRecordDO;
import cn.skuu.member.service.level.MemberExperienceRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.skuu.framework.common.pojo.CommonResult.success;
import static cn.skuu.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 App - 会员经验记录")
@RestController
@RequestMapping("/member/experience-record")
@Validated
public class AppMemberExperienceRecordController {

    @Resource
    private MemberExperienceRecordService experienceLogService;

    @GetMapping("/page")
    @Operation(summary = "获得会员经验记录分页")
    @PreAuthenticated
    public CommonResult<PageResult<AppMemberExperienceRecordRespVO>> getExperienceRecordPage(
            @Valid PageParam pageParam) {
        PageResult<MemberExperienceRecordDO> pageResult = experienceLogService.getExperienceRecordPage(
                getLoginUserId(), pageParam);
        return success(MemberExperienceRecordConvert.INSTANCE.convertPage02(pageResult));
    }

}
