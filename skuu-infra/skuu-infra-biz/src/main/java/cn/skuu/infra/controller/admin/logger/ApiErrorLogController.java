package cn.skuu.infra.controller.admin.logger;

import cn.skuu.framework.apilog.core.annotation.ApiAccessLog;
import cn.skuu.framework.common.pojo.CommonResult;
import cn.skuu.framework.common.pojo.PageParam;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.common.util.object.BeanUtils;
import cn.skuu.framework.excel.core.util.ExcelUtils;
import cn.skuu.infra.controller.admin.logger.vo.apierrorlog.ApiErrorLogPageReqVO;
import cn.skuu.infra.controller.admin.logger.vo.apierrorlog.ApiErrorLogRespVO;
import cn.skuu.infra.dal.dataobject.logger.ApiErrorLogDO;
import cn.skuu.infra.service.logger.ApiErrorLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
import static cn.skuu.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - API 错误日志")
@RestController
@RequestMapping("/infra/api-error-log")
@Validated
public class ApiErrorLogController {

    @Resource
    private ApiErrorLogService apiErrorLogService;

    @PutMapping("/update-status")
    @Operation(summary = "更新 API 错误日志的状态")
    @Parameters({
            @Parameter(name = "id", description = "编号", required = true, example = "1024"),
            @Parameter(name = "processStatus", description = "处理状态", required = true, example = "1")
    })
    @PreAuthorize("@ss.hasPermission('infra:api-error-log:update-status')")
    public CommonResult<Boolean> updateApiErrorLogProcess(@RequestParam("id") Long id,
                                                          @RequestParam("processStatus") Integer processStatus) {
        apiErrorLogService.updateApiErrorLogProcess(id, processStatus, getLoginUserId());
        return success(true);
    }

    @GetMapping("/page")
    @Operation(summary = "获得 API 错误日志分页")
    @PreAuthorize("@ss.hasPermission('infra:api-error-log:query')")
    public CommonResult<PageResult<ApiErrorLogRespVO>> getApiErrorLogPage(@Valid ApiErrorLogPageReqVO pageReqVO) {
        PageResult<ApiErrorLogDO> pageResult = apiErrorLogService.getApiErrorLogPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ApiErrorLogRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出 API 错误日志 Excel")
    @PreAuthorize("@ss.hasPermission('infra:api-error-log:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportApiErrorLogExcel(@Valid ApiErrorLogPageReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        exportReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ApiErrorLogDO> list = apiErrorLogService.getApiErrorLogPage(exportReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "API 错误日志.xls", "数据", ApiErrorLogRespVO.class,
                BeanUtils.toBean(list, ApiErrorLogRespVO.class));
    }

}