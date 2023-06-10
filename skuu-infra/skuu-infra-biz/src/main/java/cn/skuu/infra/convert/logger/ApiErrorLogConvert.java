package cn.skuu.infra.convert.logger;

import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.infra.controller.admin.logger.vo.apierrorlog.ApiErrorLogExcelVO;
import cn.skuu.infra.controller.admin.logger.vo.apierrorlog.ApiErrorLogRespVO;
import cn.skuu.infra.dal.dataobject.logger.ApiErrorLogDO;
import cn.skuu.infra.api.logger.dto.ApiErrorLogCreateReqDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * API 错误日志 Convert
 *
 * @author dcx
 */
@Mapper
public interface ApiErrorLogConvert {

    ApiErrorLogConvert INSTANCE = Mappers.getMapper(ApiErrorLogConvert.class);

    ApiErrorLogRespVO convert(ApiErrorLogDO bean);

    PageResult<ApiErrorLogRespVO> convertPage(PageResult<ApiErrorLogDO> page);

    List<ApiErrorLogExcelVO> convertList02(List<ApiErrorLogDO> list);

    ApiErrorLogDO convert(ApiErrorLogCreateReqDTO bean);

}
