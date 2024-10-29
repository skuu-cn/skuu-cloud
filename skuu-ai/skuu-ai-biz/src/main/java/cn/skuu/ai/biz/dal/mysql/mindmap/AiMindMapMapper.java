package cn.skuu.ai.biz.dal.mysql.mindmap;

import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.mybatis.core.mapper.BaseMapperX;
import cn.skuu.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.skuu.ai.biz.controller.admin.mindmap.vo.AiMindMapPageReqVO;
import cn.skuu.ai.biz.dal.dataobject.mindmap.AiMindMapDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * AI 思维导图 Mapper
 *
 * @author xiaoxin
 */
@Mapper
public interface AiMindMapMapper extends BaseMapperX<AiMindMapDO> {

    default PageResult<AiMindMapDO> selectPage(AiMindMapPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AiMindMapDO>()
                .eqIfPresent(AiMindMapDO::getUserId, reqVO.getUserId())
                .eqIfPresent(AiMindMapDO::getPrompt, reqVO.getPrompt())
                .betweenIfPresent(AiMindMapDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(AiMindMapDO::getId));
    }

}
