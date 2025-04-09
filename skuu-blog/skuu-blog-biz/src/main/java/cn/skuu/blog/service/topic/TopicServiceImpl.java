package cn.skuu.blog.service.topic;

import cn.skuu.blog.controller.admin.topic.vo.TopicPageReqVO;
import cn.skuu.blog.controller.admin.topic.vo.TopicSaveReqVO;
import cn.skuu.blog.dal.dataobject.topic.TopicDO;
import cn.skuu.blog.dal.mysql.topic.TopicMapper;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.common.util.object.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.skuu.blog.enums.ErrorCodeConstants.TOPIC_NOT_EXISTS;
import static cn.skuu.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 主题列 Service 实现类
 *
 * @author skuu
 */
@Service
@Validated
public class TopicServiceImpl implements TopicService {

    @Resource
    private TopicMapper topicMapper;

    @Override
    public Integer createTopic(TopicSaveReqVO createReqVO) {
        // 插入
        TopicDO topic = BeanUtils.toBean(createReqVO, TopicDO.class);
        topicMapper.insert(topic);
        // 返回
        return topic.getId();
    }

    @Override
    public void updateTopic(TopicSaveReqVO updateReqVO) {
        // 校验存在
        validateTopicExists(updateReqVO.getId());
        // 更新
        TopicDO updateObj = BeanUtils.toBean(updateReqVO, TopicDO.class);
        topicMapper.updateById(updateObj);
    }

    @Override
    public void deleteTopic(Integer id) {
        // 校验存在
        validateTopicExists(id);
        // 删除
        topicMapper.deleteById(id);
    }

    private void validateTopicExists(Integer id) {
        if (topicMapper.selectById(id) == null) {
            throw exception(TOPIC_NOT_EXISTS);
        }
    }

    @Override
    public TopicDO getTopic(Integer id) {
        return topicMapper.selectById(id);
    }

    @Override
    public PageResult<TopicDO> getTopicPage(TopicPageReqVO pageReqVO) {
        return topicMapper.selectPage(pageReqVO);
    }

}