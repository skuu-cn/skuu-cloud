package cn.skuu.ai.biz.dal.dataobject.knowledge;

import cn.skuu.framework.common.enums.CommonStatusEnum;
import cn.skuu.framework.mybatis.core.dataobject.BaseDO;
import cn.skuu.framework.mybatis.core.type.LongListTypeHandler;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * AI 知识库 DO
 *
 * @author xiaoxin
 */
@TableName(value = "ai_knowledge", autoResultMap = true)
@Data
public class AiKnowledgeDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 用户编号
     * <p>
     * 关联 AdminUserDO 的 userId 字段
     */
    private Long userId;
    /**
     * 知识库名称
     */
    private String name;
    /**
     * 知识库描述
     */
    private String description;

    /**
     * 可见权限,选择哪些人可见
     * <p>
     * -1 所有人可见，其他为各自用户编号
     */
    @TableField(typeHandler = LongListTypeHandler.class)
    private List<Long> visibilityPermissions;
    /**
     * 嵌入模型编号
     */
    private Long modelId;
    /**
     * 模型标识
     */
    private String model;

    /**
     * topK
     */
    private Integer topK;
    /**
     * 相似度阈值
     */
    private Double similarityThreshold;

    /**
     * 状态
     * <p>
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;

}
