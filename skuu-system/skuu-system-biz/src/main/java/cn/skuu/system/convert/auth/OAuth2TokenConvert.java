package cn.skuu.system.convert.auth;

import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.system.api.oauth2.dto.OAuth2AccessTokenCheckRespDTO;
import cn.skuu.system.api.oauth2.dto.OAuth2AccessTokenRespDTO;
import cn.skuu.system.controller.admin.oauth2.vo.token.OAuth2AccessTokenRespVO;
import cn.skuu.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OAuth2TokenConvert {

    OAuth2TokenConvert INSTANCE = Mappers.getMapper(OAuth2TokenConvert.class);

    OAuth2AccessTokenCheckRespDTO convert(OAuth2AccessTokenDO bean);

    PageResult<OAuth2AccessTokenRespVO> convert(PageResult<OAuth2AccessTokenDO> page);

    OAuth2AccessTokenRespDTO convert2(OAuth2AccessTokenDO bean);

}
