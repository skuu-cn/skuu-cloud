package cn.skuu.bpm.controller.admin.definition.vo.form;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 流程表单精简 Response VO")
@Data
public class BpmFormSimpleRespVO {

    @Schema(description = "表单编号", required = true, example = "1024")
    private Long id;

    @Schema(description = "表单名称", required = true, example = "芋道")
    private String name;

}