package cn.skuu.member.api.address;

import cn.skuu.framework.common.pojo.CommonResult;
import cn.skuu.member.api.address.dto.MemberAddressRespDTO;
import cn.skuu.member.convert.address.AddressConvert;
import cn.skuu.member.service.address.AddressService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 用户收件地址 API 实现类
 *
 * @author 芋道源码
 */
@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class MemberAddressApiImpl implements MemberAddressApi {

    @Resource
    private AddressService addressService;

    @Override
    public CommonResult<MemberAddressRespDTO> getAddress(Long id, Long userId) {
        return CommonResult.success(AddressConvert.INSTANCE.convert02(addressService.getAddress(userId, id)));
    }

    @Override
    public CommonResult<MemberAddressRespDTO> getDefaultAddress(Long userId) {
        return CommonResult.success(AddressConvert.INSTANCE.convert02(addressService.getDefaultUserAddress(userId)));
    }

}