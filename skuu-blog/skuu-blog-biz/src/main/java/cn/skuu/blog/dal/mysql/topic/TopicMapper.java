package cn.skuu.blog.dal.mysql.topic;

import cn.skuu.blog.controller.admin.topic.vo.TopicPageReqVO;
import cn.skuu.blog.dal.dataobject.topic.TopicDO;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.mybatis.core.mapper.BaseMapperX;
import cn.skuu.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 主题列 Mapper
 *
 * @author skuu
 */
@Mapper
public interface TopicMapper extends BaseMapperX<TopicDO> {

    default PageResult<TopicDO> selectPage(TopicPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TopicDO>()
                .likeIfPresent(TopicDO::getTopicName, reqVO.getTopicName())
                .betweenIfPresent(TopicDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TopicDO::getId));
    }

}