package cn.skuu.crm.controller.admin.customer.vo.customer;

import cn.skuu.crm.enums.DictTypeConstants;
import cn.skuu.crm.enums.customer.CrmCustomerLevelEnum;
import cn.skuu.framework.common.validation.InEnum;
import cn.skuu.framework.common.validation.Mobile;
import cn.skuu.framework.common.validation.Telephone;
import cn.skuu.framework.excel.core.annotations.DictFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static cn.skuu.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - CRM 客户新增/修改 Request VO")
@Data
public class CrmCustomerSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13563")
    private Long id;

    @Schema(description = "客户名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "客户名称不能为空")
    private String name;

    @Schema(description = "下次联系时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime contactNextTime;

    @Schema(description = "负责人的用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13563")
    @NotNull(message = "负责人的用户编号不能为空")
    private Long ownerUserId;

    @Schema(description = "手机", example = "18000000000")
    @Mobile
    private String mobile;

    @Schema(description = "电话", example = "18000000000")
    @Telephone
    private String telephone;

    @Schema(description = "QQ", example = "123456789")
    @Size(max = 20, message = "QQ长度不能超过 20 个字符")
    private String qq;

    @Schema(description = "微信", example = "123456789")
    @Size(max = 255, message = "微信长度不能超过 255 个字符")
    private String wechat;

    @Schema(description = "邮箱", example = "123456789@qq.com")
    @Email(message = "邮箱格式不正确")
    @Size(max = 255, message = "邮箱长度不能超过 255 个字符")
    private String email;

    @Schema(description = "地区编号", example = "20158")
    private Integer areaId;

    @Schema(description = "详细地址", example = "北京市海淀区")
    private String detailAddress;

    @Schema(description = "所属行业", example = "1")
    @DictFormat(DictTypeConstants.CRM_CUSTOMER_INDUSTRY)
    private Integer industryId;

    @Schema(description = "客户等级", example = "2")
    @InEnum(CrmCustomerLevelEnum.class)
    private Integer level;

    @Schema(description = "客户来源", example = "3")
    private Integer source;

    @Schema(description = "备注", example = "随便")
    private String remark;

}
