package cn.skuu.member.controller.admin.signin;

import cn.skuu.framework.common.pojo.CommonResult;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.common.util.collection.CollectionUtils;
import cn.skuu.member.controller.admin.signin.vo.record.MemberSignInRecordPageReqVO;
import cn.skuu.member.controller.admin.signin.vo.record.MemberSignInRecordRespVO;
import cn.skuu.member.convert.signin.MemberSignInRecordConvert;
import cn.skuu.member.dal.dataobject.signin.MemberSignInRecordDO;
import cn.skuu.member.dal.dataobject.user.MemberUserDO;
import cn.skuu.member.service.signin.MemberSignInRecordService;
import cn.skuu.member.service.user.MemberUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


@Tag(name = "管理后台 - 签到记录")
@RestController
@RequestMapping("/member/sign-in/record")
@Validated
public class MemberSignInRecordController {

    @Resource
    private MemberSignInRecordService signInRecordService;

    @Resource
    private MemberUserService memberUserService;

    @GetMapping("/page")
    @Operation(summary = "获得签到记录分页")
    @PreAuthorize("@ss.hasPermission('point:sign-in-record:query')")
    public CommonResult<PageResult<MemberSignInRecordRespVO>> getSignInRecordPage(@Valid MemberSignInRecordPageReqVO pageVO) {
        // 执行分页查询
        PageResult<MemberSignInRecordDO> pageResult = signInRecordService.getSignInRecordPage(pageVO);
        if (pageResult.getList().isEmpty()) {
            return CommonResult.success(PageResult.empty(pageResult.getTotal()));
        }

        // 拼接结果返回
        List<MemberUserDO> users = memberUserService.getUserList(
                CollectionUtils.convertSet(pageResult.getList(), MemberSignInRecordDO::getUserId));
        return CommonResult.success(MemberSignInRecordConvert.INSTANCE.convertPage(pageResult, users));
    }
}
