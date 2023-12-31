package cn.skuu.system.convert.sms;

import cn.skuu.system.controller.admin.sms.vo.log.SmsLogExcelVO;
import cn.skuu.system.controller.admin.sms.vo.log.SmsLogRespVO;
import cn.skuu.system.dal.dataobject.sms.SmsLogDO;
import cn.skuu.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 短信日志 Convert
 *
 * @author dcx
 */
@Mapper
public interface SmsLogConvert {

    SmsLogConvert INSTANCE = Mappers.getMapper(SmsLogConvert.class);

    SmsLogRespVO convert(SmsLogDO bean);

    List<SmsLogRespVO> convertList(List<SmsLogDO> list);

    PageResult<SmsLogRespVO> convertPage(PageResult<SmsLogDO> page);

    List<SmsLogExcelVO> convertList02(List<SmsLogDO> list);

}
