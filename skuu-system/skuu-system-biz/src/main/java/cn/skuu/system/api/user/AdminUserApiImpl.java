package cn.skuu.system.api.user;

import cn.skuu.framework.common.pojo.CommonResult;
import cn.skuu.system.convert.user.UserConvert;
import cn.skuu.system.dal.dataobject.user.AdminUserDO;
import cn.skuu.system.api.user.dto.AdminUserRespDTO;
import cn.skuu.system.service.user.AdminUserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static cn.skuu.framework.common.pojo.CommonResult.success;
import static cn.skuu.system.enums.ApiConstants.VERSION;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@DubboService(version = VERSION) // 提供 Dubbo RPC 接口，给 Dubbo Consumer 调用
@Validated
public class AdminUserApiImpl implements AdminUserApi {

    @Resource
    private AdminUserService userService;

    @Override
    public CommonResult<AdminUserRespDTO> getUser(Long id) {
        AdminUserDO user = userService.getUser(id);
        return success(UserConvert.INSTANCE.convert4(user));
    }

    @Override
    public CommonResult<List<AdminUserRespDTO>> getUsers(Collection<Long> ids) {
        List<AdminUserDO> users = userService.getUserList(ids);
        return success(UserConvert.INSTANCE.convertList4(users));
    }

    @Override
    public CommonResult<List<AdminUserRespDTO>> getUserListByDeptIds(Collection<Long> deptIds) {
        List<AdminUserDO> users = userService.getUserListByDeptIds(deptIds);
        return success(UserConvert.INSTANCE.convertList4(users));
    }

    @Override
    public CommonResult<List<AdminUserRespDTO>> getUserListByPostIds(Collection<Long> postIds) {
        List<AdminUserDO> users = userService.getUserListByPostIds(postIds);
        return success(UserConvert.INSTANCE.convertList4(users));
    }

    @Override
    public CommonResult<Boolean> validUserList(Set<Long> ids) {
        userService.validateUserList(ids);
        return success(true);
    }

}
