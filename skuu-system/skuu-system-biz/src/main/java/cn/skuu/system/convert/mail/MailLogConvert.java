package cn.skuu.system.convert.mail;

import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.system.controller.admin.mail.vo.log.MailLogRespVO;
import cn.skuu.system.dal.dataobject.mail.MailLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MailLogConvert {

    MailLogConvert INSTANCE = Mappers.getMapper(MailLogConvert.class);

    PageResult<MailLogRespVO> convertPage(PageResult<MailLogDO> pageResult);

    MailLogRespVO convert(MailLogDO bean);

}
