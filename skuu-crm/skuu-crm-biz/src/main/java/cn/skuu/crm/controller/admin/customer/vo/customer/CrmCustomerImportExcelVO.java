package cn.skuu.crm.controller.admin.customer.vo.customer;

import cn.skuu.crm.enums.DictTypeConstants;
import cn.skuu.framework.excel.core.annotations.DictFormat;
import cn.skuu.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 客户 Excel 导入 VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class CrmCustomerImportExcelVO {

    @ExcelProperty("客户名称")
    private String name;

    @ExcelProperty("手机")
    private String mobile;

    @ExcelProperty("电话")
    private String telephone;

    @ExcelProperty("QQ")
    private String qq;

    @ExcelProperty("微信")
    private String wechat;

    @ExcelProperty("邮箱")
    private String email;

    @ExcelProperty(value = "地区")
    private Integer areaId;

    @ExcelProperty("详细地址")
    private String detailAddress;

    @ExcelProperty(value = "所属行业", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.CRM_CUSTOMER_INDUSTRY)
    private Integer industryId;

    @ExcelProperty(value = "客户等级", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.CRM_CUSTOMER_LEVEL)
    private Integer level;

    @ExcelProperty(value = "客户来源", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.CRM_CUSTOMER_SOURCE)
    private Integer source;

    @ExcelProperty("备注")
    private String remark;

}
