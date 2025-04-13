package cn.skuu.blog.service.blog;

import cn.skuu.blog.controller.admin.blog.vo.BlogPageReqVO;
import cn.skuu.blog.controller.admin.blog.vo.BlogSaveReqVO;
import cn.skuu.blog.dal.dataobject.blog.BlogDO;
import cn.skuu.blog.dal.mysql.blog.BlogMapper;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.common.util.object.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.skuu.blog.enums.ErrorCodeConstants.BLOG_NOT_EXISTS;
import static cn.skuu.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 博客 Service 实现类
 *
 * @author skuu
 */
@Service
@Validated
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogMapper blogMapper;

    @Override
    public Long createBlog(BlogSaveReqVO createReqVO) {
        // 插入
        BlogDO blog = BeanUtils.toBean(createReqVO, BlogDO.class);
        blogMapper.insert(blog);
        // 返回
        return blog.getId();
    }

    @Override
    public void updateBlog(BlogSaveReqVO updateReqVO) {
        // 校验存在
        validateBlogExists(updateReqVO.getId());
        // 更新
        BlogDO updateObj = BeanUtils.toBean(updateReqVO, BlogDO.class);
        blogMapper.updateById(updateObj);
    }

    @Override
    public void deleteBlog(Long id) {
        // 校验存在
        validateBlogExists(id);
        // 删除
        blogMapper.deleteById(id);
    }

    private void validateBlogExists(Long id) {
        if (blogMapper.selectById(id) == null) {
            throw exception(BLOG_NOT_EXISTS);
        }
    }

    @Override
    public BlogDO getBlog(Long id) {
        return blogMapper.selectById(id);
    }

    @Override
    public PageResult<BlogDO> getBlogPage(BlogPageReqVO pageReqVO) {
        return blogMapper.selectPage(pageReqVO);
    }

}