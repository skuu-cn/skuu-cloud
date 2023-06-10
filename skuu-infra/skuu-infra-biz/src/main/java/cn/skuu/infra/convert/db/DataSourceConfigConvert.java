package cn.skuu.infra.convert.db;

import java.util.*;

import cn.skuu.infra.controller.admin.db.vo.DataSourceConfigCreateReqVO;
import cn.skuu.infra.controller.admin.db.vo.DataSourceConfigRespVO;
import cn.skuu.infra.controller.admin.db.vo.DataSourceConfigUpdateReqVO;
import cn.skuu.infra.dal.dataobject.db.DataSourceConfigDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 数据源配置 Convert
 *
 * @author dcx
 */
@Mapper
public interface DataSourceConfigConvert {

    DataSourceConfigConvert INSTANCE = Mappers.getMapper(DataSourceConfigConvert.class);

    DataSourceConfigDO convert(DataSourceConfigCreateReqVO bean);

    DataSourceConfigDO convert(DataSourceConfigUpdateReqVO bean);

    DataSourceConfigRespVO convert(DataSourceConfigDO bean);

    List<DataSourceConfigRespVO> convertList(List<DataSourceConfigDO> list);

}
