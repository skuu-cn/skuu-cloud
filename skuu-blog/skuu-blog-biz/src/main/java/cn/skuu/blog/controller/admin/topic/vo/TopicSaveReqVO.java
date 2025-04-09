package cn.skuu.blog.controller.admin.topic.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 主题列新增/修改 Request VO")
@Data
public class TopicSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6293")
    private Integer id;

    @Schema(description = "主题名称", example = "王五")
    private String topicName;

}