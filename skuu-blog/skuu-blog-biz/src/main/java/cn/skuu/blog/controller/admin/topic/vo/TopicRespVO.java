package cn.skuu.blog.controller.admin.topic.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 主题列 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TopicRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6293")
    @ExcelProperty("id")
    private Integer id;

    @Schema(description = "主题名称", example = "王五")
    @ExcelProperty("主题名称")
    private String topicName;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}