package cn.skuu.blog.service.blog;

import cn.skuu.blog.controller.admin.blog.vo.BlogPageReqVO;
import cn.skuu.blog.controller.admin.blog.vo.BlogSaveReqVO;
import cn.skuu.blog.dal.dataobject.blog.BlogDO;
import cn.skuu.framework.common.pojo.PageResult;

import javax.validation.Valid;

/**
 * 博客 Service 接口
 *
 * @author skuu
 */
public interface BlogService {

    /**
     * 创建博客
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createBlog(@Valid BlogSaveReqVO createReqVO);

    /**
     * 更新博客
     *
     * @param updateReqVO 更新信息
     */
    void updateBlog(@Valid BlogSaveReqVO updateReqVO);

    /**
     * 删除博客
     *
     * @param id 编号
     */
    void deleteBlog(Long id);

    /**
     * 获得博客
     *
     * @param id 编号
     * @return 博客
     */
    BlogDO getBlog(Long id);

    /**
     * 获得博客分页
     *
     * @param pageReqVO 分页查询
     * @return 博客分页
     */
    PageResult<BlogDO> getBlogPage(BlogPageReqVO pageReqVO);

}