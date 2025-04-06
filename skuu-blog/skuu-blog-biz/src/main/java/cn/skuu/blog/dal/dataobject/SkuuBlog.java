package cn.skuu.blog.dal.dataobject;

import cn.skuu.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 博客
 * </p>
 *
 * @author dcx
 * @since 2025-04-05
 */
@Getter
@Setter
@TableName("skuu_blog")
@Schema(description = "博客")
public class SkuuBlog extends BaseDO {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户id")
    private Integer userId;

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

    @Schema(description = "地区")
    private Integer addressId;

    @Schema(description = "共享类型。1:公开，2:私密，3:仅朋友可看")
    private Integer shareType;

}
