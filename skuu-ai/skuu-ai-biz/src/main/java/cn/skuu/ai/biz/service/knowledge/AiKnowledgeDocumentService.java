package cn.skuu.ai.biz.service.knowledge;

import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.ai.biz.controller.admin.knowledge.vo.document.AiKnowledgeDocumentPageReqVO;
import cn.skuu.ai.biz.controller.admin.knowledge.vo.document.AiKnowledgeDocumentUpdateReqVO;
import cn.skuu.ai.biz.controller.admin.knowledge.vo.knowledge.AiKnowledgeDocumentCreateReqVO;
import cn.skuu.ai.biz.dal.dataobject.knowledge.AiKnowledgeDocumentDO;

/**
 * AI 知识库-文档 Service 接口
 *
 * @author xiaoxin
 */
public interface AiKnowledgeDocumentService {

    /**
     * 创建文档
     *
     * @param createReqVO 文档创建 Request VO
     * @return 文档编号
     */
    Long createKnowledgeDocument(AiKnowledgeDocumentCreateReqVO createReqVO);


    /**
     * 获取文档分页
     *
     * @param pageReqVO 分页参数
     * @return 文档分页
     */
    PageResult<AiKnowledgeDocumentDO> getKnowledgeDocumentPage(AiKnowledgeDocumentPageReqVO pageReqVO);

    /**
     * 更新文档
     *
     * @param reqVO 更新信息
     */
    void updateKnowledgeDocument(AiKnowledgeDocumentUpdateReqVO reqVO);
}
