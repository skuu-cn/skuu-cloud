package cn.skuu.ai.biz.controller.admin.mindmap;

import cn.skuu.framework.common.pojo.CommonResult;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.common.util.object.BeanUtils;
import cn.skuu.ai.biz.controller.admin.mindmap.vo.AiMindMapGenerateReqVO;
import cn.skuu.ai.biz.controller.admin.mindmap.vo.AiMindMapPageReqVO;
import cn.skuu.ai.biz.controller.admin.mindmap.vo.AiMindMapRespVO;
import cn.skuu.ai.biz.dal.dataobject.mindmap.AiMindMapDO;
import cn.skuu.ai.biz.service.mindmap.AiMindMapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import static cn.skuu.framework.common.pojo.CommonResult.success;
import static cn.skuu.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - AI 思维导图")
@RestController
@RequestMapping("/ai/mind-map")
public class AiMindMapController {

    @Resource
    private AiMindMapService mindMapService;

    @PostMapping(value = "/generate-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "导图生成（流式）", description = "流式返回，响应较快")
    @PermitAll  // 解决 SSE 最终响应的时候，会被 Access Denied 拦截的问题
    public Flux<CommonResult<String>> generateMindMap(@RequestBody @Valid AiMindMapGenerateReqVO generateReqVO) {
        return mindMapService.generateMindMap(generateReqVO, getLoginUserId());
    }

    // ================ 导图管理 ================

    @DeleteMapping("/delete")
    @Operation(summary = "删除思维导图")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('ai:mind-map:delete')")
    public CommonResult<Boolean> deleteMindMap(@RequestParam("id") Long id) {
        mindMapService.deleteMindMap(id);
        return success(true);
    }

    @GetMapping("/page")
    @Operation(summary = "获得思维导图分页")
    @PreAuthorize("@ss.hasPermission('ai:mind-map:query')")
    public CommonResult<PageResult<AiMindMapRespVO>> getMindMapPage(@Valid AiMindMapPageReqVO pageReqVO) {
        PageResult<AiMindMapDO> pageResult = mindMapService.getMindMapPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AiMindMapRespVO.class));
    }

}
