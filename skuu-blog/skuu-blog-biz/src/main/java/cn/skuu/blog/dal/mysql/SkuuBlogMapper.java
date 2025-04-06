package cn.skuu.blog.dal.mysql;

import cn.skuu.blog.dal.dataobject.SkuuBlog;
import cn.skuu.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 博客 Mapper 接口
 * </p>
 *
 * @author dcx
 * @since 2025-04-05
 */
@Mapper
public interface SkuuBlogMapper extends BaseMapperX<SkuuBlog> {

}
