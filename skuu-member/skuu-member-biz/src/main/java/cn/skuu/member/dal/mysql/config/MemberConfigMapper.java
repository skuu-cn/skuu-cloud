package cn.skuu.member.dal.mysql.config;

import cn.skuu.framework.mybatis.core.mapper.BaseMapperX;
import cn.skuu.member.dal.dataobject.config.MemberConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 积分设置 Mapper
 *
 * @author QingX
 */
@Mapper
public interface MemberConfigMapper extends BaseMapperX<MemberConfigDO> {
}
