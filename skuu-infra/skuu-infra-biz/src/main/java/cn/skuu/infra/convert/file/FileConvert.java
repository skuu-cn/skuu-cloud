package cn.skuu.infra.convert.file;

import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.infra.controller.admin.file.vo.file.FileRespVO;
import cn.skuu.infra.dal.dataobject.file.FileDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FileConvert {

    FileConvert INSTANCE = Mappers.getMapper(FileConvert.class);

    FileRespVO convert(FileDO bean);

    PageResult<FileRespVO> convertPage(PageResult<FileDO> page);

}
