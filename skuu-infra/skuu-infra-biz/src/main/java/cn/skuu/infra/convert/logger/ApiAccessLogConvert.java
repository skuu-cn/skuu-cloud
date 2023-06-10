package cn.skuu.infra.convert.logger;

import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogExcelVO;
import cn.skuu.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogRespVO;
import cn.skuu.infra.dal.dataobject.logger.ApiAccessLogDO;
import cn.skuu.infra.api.logger.dto.ApiAccessLogCreateReqDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * API 访问日志 Convert
 *
 * @author dcx
 */
@Mapper
public interface ApiAccessLogConvert {

    ApiAccessLogConvert INSTANCE = Mappers.getMapper(ApiAccessLogConvert.class);

    ApiAccessLogRespVO convert(ApiAccessLogDO bean);

    List<ApiAccessLogRespVO> convertList(List<ApiAccessLogDO> list);

    PageResult<ApiAccessLogRespVO> convertPage(PageResult<ApiAccessLogDO> page);

    List<ApiAccessLogExcelVO> convertList02(List<ApiAccessLogDO> list);

    ApiAccessLogDO convert(ApiAccessLogCreateReqDTO bean);

}
