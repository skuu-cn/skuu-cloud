package cn.skuu.blog.convert;

import cn.skuu.blog.controller.app.vo.SkuuBlogDto;
import cn.skuu.blog.dal.dataobject.SkuuBlog;
import org.mapstruct.Mapper;

/**
 * @author dcx
 * @since 2023-07-13 10:25
 **/
@Mapper(componentModel = "spring")
public interface BlogConvert {

    SkuuBlog skuuBlogDto2Entity(SkuuBlogDto skuuBlogDto);
}
