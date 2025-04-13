package cn.skuu.blog.controller.admin.blog;

import cn.skuu.blog.controller.admin.blog.vo.BlogPageReqVO;
import cn.skuu.blog.controller.admin.blog.vo.BlogRespVO;
import cn.skuu.blog.controller.admin.blog.vo.BlogSaveReqVO;
import cn.skuu.blog.dal.dataobject.blog.BlogDO;
import cn.skuu.blog.service.blog.BlogService;
import cn.skuu.framework.apilog.core.annotation.ApiAccessLog;
import cn.skuu.framework.common.pojo.CommonResult;
import cn.skuu.framework.common.pojo.PageParam;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.common.util.object.BeanUtils;
import cn.skuu.framework.excel.core.util.ExcelUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.skuu.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.skuu.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 博客")
@RestController
@RequestMapping("/blog/blog")
@Validated
public class BlogController {

    @Resource
    private BlogService blogService;

    @PostMapping("/create")
    @Operation(summary = "创建博客")
    @PreAuthorize("@ss.hasPermission('blog:blog:create')")
    public CommonResult<Long> createBlog(@Valid @RequestBody BlogSaveReqVO createReqVO) {
        return success(blogService.createBlog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新博客")
    @PreAuthorize("@ss.hasPermission('blog:blog:update')")
    public CommonResult<Boolean> updateBlog(@Valid @RequestBody BlogSaveReqVO updateReqVO) {
        blogService.updateBlog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除博客")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('blog:blog:delete')")
    public CommonResult<Boolean> deleteBlog(@RequestParam("id") Long id) {
        blogService.deleteBlog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得博客")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('blog:blog:query')")
    public CommonResult<BlogRespVO> getBlog(@RequestParam("id") Long id) {
        BlogDO blog = blogService.getBlog(id);
        return success(BeanUtils.toBean(blog, BlogRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得博客分页")
    @PreAuthorize("@ss.hasPermission('blog:blog:query')")
    public CommonResult<PageResult<BlogRespVO>> getBlogPage(@Valid BlogPageReqVO pageReqVO) {
        PageResult<BlogDO> pageResult = blogService.getBlogPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, BlogRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出博客 Excel")
    @PreAuthorize("@ss.hasPermission('blog:blog:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportBlogExcel(@Valid BlogPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<BlogDO> list = blogService.getBlogPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "博客.xls", "数据", BlogRespVO.class,
                        BeanUtils.toBean(list, BlogRespVO.class));
    }

}