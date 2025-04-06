package cn.skuu.blog.service;

import cn.skuu.blog.dal.dataobject.SkuuBlog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 博客 服务类
 * </p>
 *
 * @author dcx
 * @since 2025-04-05
 */
public interface ISkuuBlogService extends IService<SkuuBlog> {

    boolean insert(SkuuBlog skuuBlog);
}
