package cn.skuu.system.api.permission;

import cn.skuu.framework.common.pojo.CommonResult;
import cn.skuu.system.api.permission.dto.DeptDataPermissionRespDTO;
import cn.skuu.system.service.permission.PermissionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;

import static cn.skuu.framework.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class PermissionApiImpl implements PermissionApi {

    @Resource
    private PermissionService permissionService;

    @Override
    public CommonResult<Set<Long>> getUserRoleIdListByRoleIds(Collection<Long> roleIds) {
        return success(permissionService.getUserRoleIdListByRoleId(roleIds));
    }

    @Override
    public CommonResult<Boolean> hasAnyPermissions(Long userId, String... permissions) {
        return success(permissionService.hasAnyPermissions(userId, permissions));
    }

    @Override
    public CommonResult<Boolean> hasAnyRoles(Long userId, String... roles) {
        return success(permissionService.hasAnyRoles(userId, roles));
    }

    @Override
    public CommonResult<DeptDataPermissionRespDTO> getDeptDataPermission(Long userId) {
        return success(permissionService.getDeptDataPermission(userId));
    }

}
