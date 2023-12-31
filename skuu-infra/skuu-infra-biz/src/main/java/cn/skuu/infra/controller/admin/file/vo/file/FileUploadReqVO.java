package cn.skuu.infra.controller.admin.file.vo.file;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Schema(description =  "管理后台 - 上传文件 Request VO")
@Data
public class FileUploadReqVO {

    @Schema(description = "文件附件", required = true)
    @NotNull(message = "文件附件不能为空")
    private MultipartFile file;

    @Schema(description = "文件附件", example = "skuuyuanma.png")
    private String path;

}