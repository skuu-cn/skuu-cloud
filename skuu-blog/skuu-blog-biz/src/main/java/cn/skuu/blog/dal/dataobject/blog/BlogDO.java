package cn.skuu.blog.dal.dataobject.blog;

import cn.skuu.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 博客 DO
 *
 * @author skuu
 */
@TableName("skuu_blog")
@KeySequence("skuu_blog_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 广场id
     */
    private Long squareId;
    /**
     * 话题id
     */
    private Integer topicId;
    /**
     * 分类，1:普通，2:求助，3:助力
     */
    private Integer categary;
    /**
     * 类型，1:图文，2:视频
     */
    private Integer blogType;
    /**
     * 内容
     */
    private String content;
    /**
     * 资源列表
     */
    private String resources;
    /**
     * 地区
     */
    private Integer addressId;
    /**
     * 共享类型。1:公开，2:私密，3:仅朋友可看
     */
    private Integer shareType;

}