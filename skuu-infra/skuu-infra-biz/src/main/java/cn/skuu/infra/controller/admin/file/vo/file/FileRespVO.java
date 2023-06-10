package cn.skuu.infra.controller.admin.file.vo.file;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description =   "管理后台 - 文件 Response VO,不返回 content 字段，太大" )
@Data
public class FileRespVO {

    @Schema(description = "文件编号", required = true, example = "1024")
    private Long id;

    @Schema(description = "文件路径", required = true, example = "skuu.jpg")
    private String path;

    @Schema(description = "文件 URL", required = true, example = "https://www.iocoder.cn/skuu.jpg")
    private String url;

    @Schema(description = "文件类型", example = "jpg")
    private String type;

    @Schema(description = "文件大小", example = "2048", required = true)
    private Integer size;

    @Schema(description = "创建时间", required = true)
    private LocalDateTime createTime;

}