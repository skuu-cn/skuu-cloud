package cn.skuu.system.convert.notify;

import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.system.controller.admin.notify.vo.template.NotifyTemplateCreateReqVO;
import cn.skuu.system.controller.admin.notify.vo.template.NotifyTemplateRespVO;
import cn.skuu.system.controller.admin.notify.vo.template.NotifyTemplateUpdateReqVO;
import cn.skuu.system.dal.dataobject.notify.NotifyTemplateDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 站内信模版 Convert
 *
 * @author xrcoder
 */
@Mapper
public interface NotifyTemplateConvert {

    NotifyTemplateConvert INSTANCE = Mappers.getMapper(NotifyTemplateConvert.class);

    NotifyTemplateDO convert(NotifyTemplateCreateReqVO bean);

    NotifyTemplateDO convert(NotifyTemplateUpdateReqVO bean);

    NotifyTemplateRespVO convert(NotifyTemplateDO bean);

    List<NotifyTemplateRespVO> convertList(List<NotifyTemplateDO> list);

    PageResult<NotifyTemplateRespVO> convertPage(PageResult<NotifyTemplateDO> page);

}
