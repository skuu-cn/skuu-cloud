package cn.skuu.ai.biz.controller.admin.knowledge;

import cn.skuu.framework.common.pojo.CommonResult;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.common.util.object.BeanUtils;
import cn.skuu.ai.biz.controller.admin.knowledge.vo.segment.AiKnowledgeSegmentPageReqVO;
import cn.skuu.ai.biz.controller.admin.knowledge.vo.segment.AiKnowledgeSegmentRespVO;
import cn.skuu.ai.biz.controller.admin.knowledge.vo.segment.AiKnowledgeSegmentUpdateReqVO;
import cn.skuu.ai.biz.controller.admin.knowledge.vo.segment.AiKnowledgeSegmentUpdateStatusReqVO;
import cn.skuu.ai.biz.dal.dataobject.knowledge.AiKnowledgeSegmentDO;
import cn.skuu.ai.biz.service.knowledge.AiKnowledgeSegmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.skuu.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - AI 知识库段落")
@RestController
@RequestMapping("/ai/knowledge/segment")
@Validated
public class AiKnowledgeSegmentController {

    @Resource
    private AiKnowledgeSegmentService segmentService;

    @GetMapping("/page")
    @Operation(summary = "获取段落分页")
    public CommonResult<PageResult<AiKnowledgeSegmentRespVO>> getKnowledgeSegmentPage(@Valid AiKnowledgeSegmentPageReqVO pageReqVO) {
        PageResult<AiKnowledgeSegmentDO> pageResult = segmentService.getKnowledgeSegmentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AiKnowledgeSegmentRespVO.class));
    }

    @PutMapping("/update")
    @Operation(summary = "更新段落内容")
    public CommonResult<Boolean> updateKnowledgeSegment(@Valid @RequestBody AiKnowledgeSegmentUpdateReqVO reqVO) {
        segmentService.updateKnowledgeSegment(reqVO);
        return success(true);
    }

    @PutMapping("/update-status")
    @Operation(summary = "启禁用段落内容")
    public CommonResult<Boolean> updateKnowledgeSegmentStatus(@Valid @RequestBody AiKnowledgeSegmentUpdateStatusReqVO reqVO) {
        segmentService.updateKnowledgeSegmentStatus(reqVO);
        return success(true);
    }

}
