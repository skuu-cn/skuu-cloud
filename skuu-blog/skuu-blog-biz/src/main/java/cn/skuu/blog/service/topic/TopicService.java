package cn.skuu.blog.service.topic;

import cn.skuu.blog.controller.admin.topic.vo.TopicPageReqVO;
import cn.skuu.blog.controller.admin.topic.vo.TopicSaveReqVO;
import cn.skuu.blog.dal.dataobject.topic.TopicDO;
import cn.skuu.framework.common.pojo.PageResult;

import javax.validation.Valid;

/**
 * 主题列 Service 接口
 *
 * @author skuu
 */
public interface TopicService {

    /**
     * 创建主题列
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createTopic(@Valid TopicSaveReqVO createReqVO);

    /**
     * 更新主题列
     *
     * @param updateReqVO 更新信息
     */
    void updateTopic(@Valid TopicSaveReqVO updateReqVO);

    /**
     * 删除主题列
     *
     * @param id 编号
     */
    void deleteTopic(Integer id);

    /**
     * 获得主题列
     *
     * @param id 编号
     * @return 主题列
     */
    TopicDO getTopic(Integer id);

    /**
     * 获得主题列分页
     *
     * @param pageReqVO 分页查询
     * @return 主题列分页
     */
    PageResult<TopicDO> getTopicPage(TopicPageReqVO pageReqVO);

}