package cn.skuu.ai.biz.controller.admin.chat.vo.conversation;

import cn.skuu.framework.common.pojo.PageParam;
import cn.skuu.framework.common.util.date.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Schema(description = "管理后台 - AI 聊天对话的分页 Request VO")
@Data
public class AiChatConversationPageReqVO extends PageParam {

    @Schema(description = "用户编号", example = "1024")
    private Long userId;

    @Schema(description = "对话标题", example = "你好")
    private String title;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
