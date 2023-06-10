package cn.skuu.system.convert.logger;

import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.system.api.logger.dto.LoginLogCreateReqDTO;
import cn.skuu.system.controller.admin.logger.vo.loginlog.LoginLogExcelVO;
import cn.skuu.system.controller.admin.logger.vo.loginlog.LoginLogRespVO;
import cn.skuu.system.dal.dataobject.logger.LoginLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LoginLogConvert {

    LoginLogConvert INSTANCE = Mappers.getMapper(LoginLogConvert.class);

    PageResult<LoginLogRespVO> convertPage(PageResult<LoginLogDO> page);

    List<LoginLogExcelVO> convertList(List<LoginLogDO> list);

    LoginLogDO convert(LoginLogCreateReqDTO bean);

}
