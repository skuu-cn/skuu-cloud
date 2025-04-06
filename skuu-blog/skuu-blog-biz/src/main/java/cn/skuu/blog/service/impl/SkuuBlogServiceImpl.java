package cn.skuu.blog.service.impl;

import cn.skuu.blog.dal.dataobject.SkuuBlog;
import cn.skuu.blog.dal.mysql.SkuuBlogMapper;
import cn.skuu.blog.service.ISkuuBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 博客 服务实现类
 * </p>
 *
 * @author dcx
 * @since 2025-04-05
 */
@Service
public class SkuuBlogServiceImpl extends ServiceImpl<SkuuBlogMapper, SkuuBlog> implements ISkuuBlogService {

    @Autowired
    private SkuuBlogMapper skuuBlogMapper;

    @Override
    public boolean insert(SkuuBlog skuuBlog) {
        return skuuBlogMapper.insert(skuuBlog) > 0;
    }
}
