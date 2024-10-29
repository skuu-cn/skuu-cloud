package cn.skuu.crm.controller.admin.contract.vo.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static cn.skuu.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - CRM 合同创建/更新 Request VO")
@Data
public class CrmContractSaveReqVO {

    @Schema(description = "合同编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "10430")
    private Long id;

    @Schema(description = "合同名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotNull(message = "合同名称不能为空")
    private String name;

    @Schema(description = "客户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "18336")
    @NotNull(message = "客户编号不能为空")
    private Long customerId;

    @Schema(description = "商机编号", example = "10864")
    private Long businessId;

    @Schema(description = "负责人的用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "17144")
    @NotNull(message = "负责人不能为空")
    private Long ownerUserId;

    @Schema(description = "下单日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @NotNull(message = "下单日期不能为空")
    private LocalDateTime orderDate;

    @Schema(description = "开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime endTime;

    @Schema(description = "整单折扣", requiredMode = Schema.RequiredMode.REQUIRED, example = "55.00")
    @NotNull(message = "整单折扣不能为空")
    private BigDecimal discountPercent;

    @Schema(description = "合同金额", example = "5617")
    private BigDecimal totalPrice;

    @Schema(description = "客户签约人编号", example = "18546")
    private Long signContactId;

    @Schema(description = "公司签约人", example = "14036")
    private Long signUserId;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "产品列表")
    private List<Product> products;

    @Schema(description = "产品列表")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Product {

        @Schema(description = "产品编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "20529")
        @NotNull(message = "产品编号不能为空")
        private Long productId;

        @Schema(description = "产品单价", requiredMode = Schema.RequiredMode.REQUIRED, example = "123.00")
        @NotNull(message = "产品单价不能为空")
        private BigDecimal productPrice;

        @Schema(description = "合同价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "123.00")
        @NotNull(message = "合同价格不能为空")
        private BigDecimal contractPrice;

        @Schema(description = "产品数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "8911")
        @NotNull(message = "产品数量不能为空")
        private Integer count;

    }

}
