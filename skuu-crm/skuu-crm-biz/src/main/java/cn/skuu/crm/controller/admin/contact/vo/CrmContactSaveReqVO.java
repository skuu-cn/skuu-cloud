package cn.skuu.crm.controller.admin.contact.vo;

import cn.skuu.framework.common.validation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static cn.skuu.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

@Schema(description = "管理后台 - CRM 联系人创建/更新 Request VO")
@Data
public class CrmContactSaveReqVO {

    @Schema(description = "主键", example = "3167")
    private Long id;

    @Schema(description = "姓名", example = "芋艿")
    @NotNull(message = "姓名不能为空")
    private String name;

    @Schema(description = "客户编号", example = "10795")
    @NotNull(message = "客户编号不能为空")
    private Long customerId;

    @Schema(description = "下次联系时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private LocalDateTime contactNextTime;

    @Schema(description = "负责人用户编号", example = "14334")
    @NotNull(message = "负责人不能为空")
    private Long ownerUserId;

    @Schema(description = "手机号", example = "1387171766")
    @Mobile
    private String mobile;

    @Schema(description = "电话", example = "021-0029922")
    private String telephone;

    @Schema(description = "QQ", example = "197272662")
    private Long qq;

    @Schema(description = "微信", example = "zzz3883")
    private String wechat;

    @Schema(description = "电子邮箱", example = "1111@22.com")
    @Email
    private String email;

    @Schema(description = "地区编号", example = "20158")
    private Integer areaId;

    @Schema(description = "地址")
    private String detailAddress;

    @Schema(description = "性别")
    private Integer sex;

    @Schema(description = "是否关键决策人")
    private Boolean master;

    @Schema(description = "职位")
    private String post;

    @Schema(description = "直属上级", example = "23457")
    private Long parentId;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "关联商机 ID", example = "122233")
    private Long businessId; // 注意：该字段用于在【商机】详情界面「新建联系人」时，自动进行关联

}
