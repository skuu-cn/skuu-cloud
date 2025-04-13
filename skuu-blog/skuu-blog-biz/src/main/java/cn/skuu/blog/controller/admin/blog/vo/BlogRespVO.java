package cn.skuu.blog.controller.admin.blog.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 博客 Response VO")
@Data
@ExcelIgnoreUnannotated
public class BlogRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1139")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "广场id", example = "20853")
    @ExcelProperty("广场id")
    private Long squareId;

    @Schema(description = "话题id", example = "15260")
    @ExcelProperty("话题id")
    private Integer topicId;

    @Schema(description = "分类，1:普通，2:求助，3:助力")
    @ExcelProperty("分类，1:普通，2:求助，3:助力")
    private Integer categary;

    @Schema(description = "类型，1:图文，2:视频", example = "2")
    @ExcelProperty("类型，1:图文，2:视频")
    private Integer blogType;

    @Schema(description = "内容")
    @ExcelProperty("内容")
    private String content;

    @Schema(description = "资源列表")
    @ExcelProperty("资源列表")
    private String resources;

    @Schema(description = "地区", example = "12897")
    @ExcelProperty("地区")
    private Integer addressId;

    @Schema(description = "共享类型。1:公开，2:私密，3:仅朋友可看", example = "2")
    @ExcelProperty("共享类型。1:公开，2:私密，3:仅朋友可看")
    private Integer shareType;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}