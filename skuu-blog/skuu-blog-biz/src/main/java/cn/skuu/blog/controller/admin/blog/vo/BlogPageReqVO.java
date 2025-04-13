package cn.skuu.blog.controller.admin.blog.vo;

import cn.skuu.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.skuu.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 博客分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BlogPageReqVO extends PageParam {

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

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}