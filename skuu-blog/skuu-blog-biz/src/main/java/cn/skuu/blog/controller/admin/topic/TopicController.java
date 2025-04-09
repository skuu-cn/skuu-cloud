package cn.skuu.blog.controller.admin.topic;

import cn.skuu.blog.controller.admin.topic.vo.TopicPageReqVO;
import cn.skuu.blog.controller.admin.topic.vo.TopicRespVO;
import cn.skuu.blog.controller.admin.topic.vo.TopicSaveReqVO;
import cn.skuu.blog.dal.dataobject.topic.TopicDO;
import cn.skuu.blog.service.topic.TopicService;
import cn.skuu.framework.apilog.core.annotation.ApiAccessLog;
import cn.skuu.framework.common.pojo.CommonResult;
import cn.skuu.framework.common.pojo.PageParam;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.common.util.object.BeanUtils;
import cn.skuu.framework.excel.core.util.ExcelUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.skuu.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.skuu.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 主题列")
@RestController
@RequestMapping("/blog/topic")
@Validated
public class TopicController {

    @Resource
    private TopicService topicService;

    @PostMapping("/create")
    @Operation(summary = "创建主题列")
    @PreAuthorize("@ss.hasPermission('blog:topic:create')")
    public CommonResult<Integer> createTopic(@Valid @RequestBody TopicSaveReqVO createReqVO) {
        return success(topicService.createTopic(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新主题列")
    @PreAuthorize("@ss.hasPermission('blog:topic:update')")
    public CommonResult<Boolean> updateTopic(@Valid @RequestBody TopicSaveReqVO updateReqVO) {
        topicService.updateTopic(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除主题列")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('blog:topic:delete')")
    public CommonResult<Boolean> deleteTopic(@RequestParam("id") Integer id) {
        topicService.deleteTopic(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得主题列")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('blog:topic:query')")
    public CommonResult<TopicRespVO> getTopic(@RequestParam("id") Integer id) {
        TopicDO topic = topicService.getTopic(id);
        return success(BeanUtils.toBean(topic, TopicRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得主题列分页")
    @PreAuthorize("@ss.hasPermission('blog:topic:query')")
    public CommonResult<PageResult<TopicRespVO>> getTopicPage(@Valid TopicPageReqVO pageReqVO) {
        PageResult<TopicDO> pageResult = topicService.getTopicPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TopicRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出主题列 Excel")
    @PreAuthorize("@ss.hasPermission('blog:topic:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportTopicExcel(@Valid TopicPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TopicDO> list = topicService.getTopicPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "主题列.xls", "数据", TopicRespVO.class,
                        BeanUtils.toBean(list, TopicRespVO.class));
    }

}