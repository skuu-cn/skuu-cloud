package cn.skuu.ai.biz.dal.mysql.knowledge;

import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.mybatis.core.mapper.BaseMapperX;
import cn.skuu.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.skuu.ai.biz.controller.admin.knowledge.vo.document.AiKnowledgeDocumentPageReqVO;
import cn.skuu.ai.biz.dal.dataobject.knowledge.AiKnowledgeDocumentDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * AI 知识库-文档 Mapper
 *
 * @author xiaoxin
 */
@Mapper
public interface AiKnowledgeDocumentMapper extends BaseMapperX<AiKnowledgeDocumentDO> {

    default PageResult<AiKnowledgeDocumentDO> selectPage(AiKnowledgeDocumentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AiKnowledgeDocumentDO>()
                .likeIfPresent(AiKnowledgeDocumentDO::getName, reqVO.getName())
                .orderByDesc(AiKnowledgeDocumentDO::getId));
    }

}
