package cn.skuu.blog.controller.admin.blog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 博客新增/修改 Request VO")
@Data
public class BlogSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1139")
    private Long id;

    @Schema(description = "广场id", example = "20853")
    private Long squareId;

    @Schema(description = "话题id", example = "15260")
    private Integer topicId;

    @Schema(description = "分类，1:普通，2:求助，3:助力")
    private Integer categary;

    @Schema(description = "类型，1:图文，2:视频", example = "2")
    private Integer blogType;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "资源列表")
    private String resources;

    @Schema(description = "地区", example = "12897")
    private Integer addressId;

    @Schema(description = "共享类型。1:公开，2:私密，3:仅朋友可看", example = "2")
    private Integer shareType;

}