package cn.skuu.member.controller.admin.signin;

import cn.skuu.framework.common.pojo.CommonResult;
import cn.skuu.member.controller.admin.signin.vo.config.MemberSignInConfigCreateReqVO;
import cn.skuu.member.controller.admin.signin.vo.config.MemberSignInConfigRespVO;
import cn.skuu.member.controller.admin.signin.vo.config.MemberSignInConfigUpdateReqVO;
import cn.skuu.member.convert.signin.MemberSignInConfigConvert;
import cn.skuu.member.dal.dataobject.signin.MemberSignInConfigDO;
import cn.skuu.member.service.signin.MemberSignInConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


// TODO 芋艿：url
@Tag(name = "管理后台 - 签到规则")
@RestController
@RequestMapping("/member/sign-in/config")
@Validated
public class MemberSignInConfigController {

    @Resource
    private MemberSignInConfigService signInConfigService;

    @PostMapping("/create")
    @Operation(summary = "创建签到规则")
    @PreAuthorize("@ss.hasPermission('point:sign-in-config:create')")
    public CommonResult<Long> createSignInConfig(@Valid @RequestBody MemberSignInConfigCreateReqVO createReqVO) {
        return CommonResult.success(signInConfigService.createSignInConfig(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新签到规则")
    @PreAuthorize("@ss.hasPermission('point:sign-in-config:update')")
    public CommonResult<Boolean> updateSignInConfig(@Valid @RequestBody MemberSignInConfigUpdateReqVO updateReqVO) {
        signInConfigService.updateSignInConfig(updateReqVO);
        return CommonResult.success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除签到规则")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('point:sign-in-config:delete')")
    public CommonResult<Boolean> deleteSignInConfig(@RequestParam("id") Long id) {
        signInConfigService.deleteSignInConfig(id);
        return CommonResult.success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得签到规则")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('point:sign-in-config:query')")
    public CommonResult<MemberSignInConfigRespVO> getSignInConfig(@RequestParam("id") Long id) {
        MemberSignInConfigDO signInConfig = signInConfigService.getSignInConfig(id);
        return CommonResult.success(MemberSignInConfigConvert.INSTANCE.convert(signInConfig));
    }

    @GetMapping("/list")
    @Operation(summary = "获得签到规则列表")
    @PreAuthorize("@ss.hasPermission('point:sign-in-config:query')")
    public CommonResult<List<MemberSignInConfigRespVO>> getSignInConfigList() {
        List<MemberSignInConfigDO> list = signInConfigService.getSignInConfigList();
        return CommonResult.success(MemberSignInConfigConvert.INSTANCE.convertList(list));
    }

}