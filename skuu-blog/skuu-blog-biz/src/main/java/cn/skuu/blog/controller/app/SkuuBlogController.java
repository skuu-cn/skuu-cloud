package cn.skuu.blog.controller.app;

import cn.skuu.blog.controller.app.vo.SkuuBlogDto;
import cn.skuu.blog.convert.BlogConvert;
import cn.skuu.blog.dal.dataobject.SkuuBlog;
import cn.skuu.blog.service.ISkuuBlogService;
import cn.skuu.framework.common.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static cn.skuu.framework.common.pojo.CommonResult.success;

/**
 * <p>
 * 博客 前端控制器
 * </p>
 *
 * @author dcx
 * @since 2025-04-05
 */
@Tag(name = "博客")
@RestController
@RequestMapping("/skuu-blog")
public class SkuuBlogController {

    @Autowired
    private ISkuuBlogService iSkuuBlogService;

    @Autowired
    private BlogConvert blogConvert;

    @PostMapping("/create")
    @Operation(summary = "创建博客")
    public CommonResult<Boolean> createBusiness(@Valid @RequestBody SkuuBlogDto skuuBlogDto) {
        SkuuBlog skuuBlog = blogConvert.skuuBlogDto2Entity(skuuBlogDto);
        boolean save = iSkuuBlogService.insert(skuuBlog);
        return success(save);
    }
}
