package cn.skuu.erp.controller.admin.product;

import cn.skuu.erp.controller.admin.product.vo.category.ErpProductCategoryListReqVO;
import cn.skuu.erp.controller.admin.product.vo.category.ErpProductCategoryRespVO;
import cn.skuu.erp.controller.admin.product.vo.category.ErpProductCategorySaveReqVO;
import cn.skuu.erp.dal.dataobject.product.ErpProductCategoryDO;
import cn.skuu.erp.service.product.ErpProductCategoryService;
import cn.skuu.framework.common.enums.CommonStatusEnum;
import cn.skuu.framework.common.pojo.CommonResult;
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

import static cn.skuu.framework.common.pojo.CommonResult.success;
import static cn.skuu.framework.common.util.collection.CollectionUtils.convertList;

@Tag(name = "管理后台 - ERP 产品分类")
@RestController
@RequestMapping("/erp/product-category")
@Validated
public class ErpProductCategoryController {

    @Resource
    private ErpProductCategoryService productCategoryService;

    @PostMapping("/create")
    @Operation(summary = "创建产品分类")
    @PreAuthorize("@ss.hasPermission('erp:product-category:create')")
    public CommonResult<Long> createProductCategory(@Valid @RequestBody ErpProductCategorySaveReqVO createReqVO) {
        return success(productCategoryService.createProductCategory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新产品分类")
    @PreAuthorize("@ss.hasPermission('erp:product-category:update')")
    public CommonResult<Boolean> updateProductCategory(@Valid @RequestBody ErpProductCategorySaveReqVO updateReqVO) {
        productCategoryService.updateProductCategory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除产品分类")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('erp:product-category:delete')")
    public CommonResult<Boolean> deleteProductCategory(@RequestParam("id") Long id) {
        productCategoryService.deleteProductCategory(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得产品分类")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('erp:product-category:query')")
    public CommonResult<ErpProductCategoryRespVO> getProductCategory(@RequestParam("id") Long id) {
        ErpProductCategoryDO category = productCategoryService.getProductCategory(id);
        return success(BeanUtils.toBean(category, ErpProductCategoryRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得产品分类列表")
    @PreAuthorize("@ss.hasPermission('erp:product-category:query')")
    public CommonResult<List<ErpProductCategoryRespVO>> getProductCategoryList(@Valid ErpProductCategoryListReqVO listReqVO) {
        List<ErpProductCategoryDO> list = productCategoryService.getProductCategoryList(listReqVO);
        return success(BeanUtils.toBean(list, ErpProductCategoryRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获得产品分类精简列表", description = "只包含被开启的分类，主要用于前端的下拉选项")
    public CommonResult<List<ErpProductCategoryRespVO>> getProductCategorySimpleList() {
        List<ErpProductCategoryDO> list = productCategoryService.getProductCategoryList(
                new ErpProductCategoryListReqVO().setStatus(CommonStatusEnum.ENABLE.getStatus()));
        return success(convertList(list, category -> new ErpProductCategoryRespVO()
                .setId(category.getId()).setName(category.getName()).setParentId(category.getParentId())));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出产品分类 Excel")
    @PreAuthorize("@ss.hasPermission('erp:product-category:export')")
    
    public void exportProductCategoryExcel(@Valid ErpProductCategoryListReqVO listReqVO,
              HttpServletResponse response) throws IOException {
        List<ErpProductCategoryDO> list = productCategoryService.getProductCategoryList(listReqVO);
        // 导出 Excel
        ExcelUtils.write(response, "产品分类.xls", "数据", ErpProductCategoryRespVO.class,
                        BeanUtils.toBean(list, ErpProductCategoryRespVO.class));
    }

}