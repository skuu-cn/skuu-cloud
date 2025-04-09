package cn.skuu.blog.dal.dataobject.topic;

import cn.skuu.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 主题列 DO
 *
 * @author skuu
 */
@TableName("skuu_topic")
@KeySequence("skuu_topic_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Integer id;
    /**
     * 主题名称
     */
    private String topicName;

}