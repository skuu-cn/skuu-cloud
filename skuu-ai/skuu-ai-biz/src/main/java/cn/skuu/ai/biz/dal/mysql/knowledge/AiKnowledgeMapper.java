package cn.skuu.ai.biz.dal.mysql.knowledge;

import cn.skuu.framework.common.enums.CommonStatusEnum;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.mybatis.core.mapper.BaseMapperX;
import cn.skuu.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.skuu.ai.biz.controller.admin.knowledge.vo.knowledge.AiKnowledgePageReqVO;
import cn.skuu.ai.biz.dal.dataobject.knowledge.AiKnowledgeDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * AI 知识库基础信息 Mapper
 *
 * @author xiaoxin
 */
@Mapper
public interface AiKnowledgeMapper extends BaseMapperX<AiKnowledgeDO> {

    default PageResult<AiKnowledgeDO> selectPage(Long userId, AiKnowledgePageReqVO pageReqVO) {
        return selectPage(pageReqVO, new LambdaQueryWrapperX<AiKnowledgeDO>()
                .eq(AiKnowledgeDO::getStatus, CommonStatusEnum.ENABLE.getStatus())
                .likeIfPresent(AiKnowledgeDO::getName, pageReqVO.getName())
                .and(e -> e.apply("FIND_IN_SET(" + userId + ",visibility_permissions)").or(m -> m.apply("FIND_IN_SET(-1,visibility_permissions)")))
                .orderByDesc(AiKnowledgeDO::getId));
    }
}
