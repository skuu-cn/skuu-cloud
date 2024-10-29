package cn.skuu.ai.biz.controller.admin.knowledge;

import cn.skuu.framework.common.pojo.CommonResult;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.common.util.object.BeanUtils;
import cn.skuu.ai.biz.controller.admin.knowledge.vo.knowledge.AiKnowledgeCreateReqVO;
import cn.skuu.ai.biz.controller.admin.knowledge.vo.knowledge.AiKnowledgePageReqVO;
import cn.skuu.ai.biz.controller.admin.knowledge.vo.knowledge.AiKnowledgeRespVO;
import cn.skuu.ai.biz.controller.admin.knowledge.vo.knowledge.AiKnowledgeUpdateReqVO;
import cn.skuu.ai.biz.dal.dataobject.knowledge.AiKnowledgeDO;
import cn.skuu.ai.biz.service.knowledge.AiKnowledgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.skuu.framework.common.pojo.CommonResult.success;
import static cn.skuu.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - AI 知识库")
@RestController
@RequestMapping("/ai/knowledge")
@Validated
public class AiKnowledgeController {

    @Resource
    private AiKnowledgeService knowledgeService;

    @GetMapping("/page")
    @Operation(summary = "获取知识库分页")
    public CommonResult<PageResult<AiKnowledgeRespVO>> getKnowledgePage(@Valid AiKnowledgePageReqVO pageReqVO) {
        PageResult<AiKnowledgeDO> pageResult = knowledgeService.getKnowledgePage(getLoginUserId(), pageReqVO);
        return success(BeanUtils.toBean(pageResult, AiKnowledgeRespVO.class));
    }

    @PostMapping("/create")
    @Operation(summary = "创建知识库")
    public CommonResult<Long> createKnowledge(@RequestBody @Valid AiKnowledgeCreateReqVO createReqVO) {
        return success(knowledgeService.createKnowledge(createReqVO, getLoginUserId()));
    }

    @PutMapping("/update")
    @Operation(summary = "更新知识库")
    public CommonResult<Boolean> updateKnowledge(@RequestBody @Valid AiKnowledgeUpdateReqVO updateReqVO) {
        knowledgeService.updateKnowledge(updateReqVO, getLoginUserId());
        return success(true);
    }
}
