package cn.skuu.ai.biz.controller.admin.chat.vo.conversation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - AI 聊天对话 Response VO")
@Data
public class AiChatConversationRespVO{

    @Schema(description = "对话编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    private Long userId;

    @Schema(description = "对话标题", requiredMode = Schema.RequiredMode.REQUIRED, example = "我是一个标题")
    private String title;

    @Schema(description = "是否置顶", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean pinned;

    @Schema(description = "角色编号", example = "1")
    private Long roleId;

    @Schema(description = "模型编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long modelId;

    @Schema(description = "模型标志", requiredMode = Schema.RequiredMode.REQUIRED, example = "ERNIE-Bot-turbo-0922")
    private String model;

    @Schema(description = "模型名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    private String modelName;

    @Schema(description = "角色设定", example = "一个快乐的程序员")
    private String systemMessage;

    @Schema(description = "温度参数", requiredMode = Schema.RequiredMode.REQUIRED, example = "0.8")
    private Double temperature;

    @Schema(description = "单条回复的最大 Token 数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "4096")
    private Integer maxTokens;

    @Schema(description = "上下文的最大 Message 数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer maxContexts;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    // ========== 关联 role 信息 ==========

    @Schema(description = "角色头像", example = "https://www.iocoder.cn/1.png")
    private String roleAvatar;

    @Schema(description = "角色名字", example = "小黄")
    private String roleName;

    // ========== 仅在【对话管理】时加载 ==========

    @Schema(description = "消息数量", example = "20")
    private Integer messageCount;

}
