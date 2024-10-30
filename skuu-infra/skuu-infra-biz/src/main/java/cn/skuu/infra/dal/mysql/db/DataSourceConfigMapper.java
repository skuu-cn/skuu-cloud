package cn.skuu.infra.dal.mysql.db;

import cn.skuu.framework.mybatis.core.mapper.BaseMapperX;
import cn.skuu.infra.dal.dataobject.db.DataSourceConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据源配置 Mapper
 *
 * @author skuu
 */
@Mapper
public interface DataSourceConfigMapper extends BaseMapperX<DataSourceConfigDO> {
}
