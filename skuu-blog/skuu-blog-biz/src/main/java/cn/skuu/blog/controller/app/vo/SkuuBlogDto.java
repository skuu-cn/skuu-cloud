package cn.skuu.blog.controller.app.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 博客
 * </p>
 *
 * @author dcx
 * @since 2025-04-05
 */
@Data
@Schema(description = "SkuuBlogDto")
public class SkuuBlogDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "广场id")
    private Integer squareId;

    @Schema(description = "话题id")
    private Integer topicId;

    @Schema(description = "分类，1:普通，2:求助，3:助力")
    private Integer categary;

    @Schema(description = "类型，1:图文，2:视频")
    private Integer blogType;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "资源列表")
    private String resources;

    @Schema(description = "共享类型。1:公开，2:私密，3:仅朋友可看")
    private Integer shareType;

}
