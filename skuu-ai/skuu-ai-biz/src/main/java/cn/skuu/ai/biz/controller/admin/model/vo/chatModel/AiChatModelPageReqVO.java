package cn.skuu.ai.biz.controller.admin.model.vo.chatModel;

import cn.skuu.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - API 聊天模型分页 Request VO")
@Data
public class AiChatModelPageReqVO extends PageParam {

    @Schema(description = "模型名字", example = "张三")
    private String name;

    @Schema(description = "模型标识", example = "gpt-3.5-turbo-0125")
    private String model;

    @Schema(description = "模型平台", example = "OpenAI")
    private String platform;

}