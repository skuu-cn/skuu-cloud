package cn.skuu.crm.service.customer;

import cn.hutool.core.lang.Assert;
import cn.skuu.crm.controller.admin.customer.vo.limitconfig.CrmCustomerLimitConfigPageReqVO;
import cn.skuu.crm.controller.admin.customer.vo.limitconfig.CrmCustomerLimitConfigSaveReqVO;
import cn.skuu.crm.dal.dataobject.customer.CrmCustomerLimitConfigDO;
import cn.skuu.crm.dal.mysql.customer.CrmCustomerLimitConfigMapper;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.common.util.object.BeanUtils;
import cn.skuu.system.api.dept.DeptApi;
import cn.skuu.system.api.user.AdminUserApi;
import cn.skuu.system.api.user.dto.AdminUserRespDTO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.skuu.crm.enums.ErrorCodeConstants.CUSTOMER_LIMIT_CONFIG_NOT_EXISTS;
import static cn.skuu.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 客户限制配置 Service 实现类
 *
 * @author Wanwan
 */
@Service
@Validated
public class CrmCustomerLimitConfigServiceImpl implements CrmCustomerLimitConfigService {

    @Resource
    private CrmCustomerLimitConfigMapper customerLimitConfigMapper;

    @Resource
    private DeptApi deptApi;
    @Resource
    private AdminUserApi adminUserApi;

    @Override
//    @LogRecord(type = CRM_CUSTOMER_LIMIT_CONFIG_TYPE, subType = CRM_CUSTOMER_LIMIT_CONFIG_CREATE_SUB_TYPE, bizNo = "{{#limitId}}",
//            success = CRM_CUSTOMER_LIMIT_CONFIG_CREATE_SUCCESS)
    public Long createCustomerLimitConfig(CrmCustomerLimitConfigSaveReqVO createReqVO) {
        validateUserAndDept(createReqVO.getUserIds(), createReqVO.getDeptIds());
        // 插入
        CrmCustomerLimitConfigDO limitConfig = BeanUtils.toBean(createReqVO, CrmCustomerLimitConfigDO.class);
        customerLimitConfigMapper.insert(limitConfig);

        // 记录操作日志上下文
//        LogRecordContext.putVariable("limitType", CrmCustomerLimitConfigTypeEnum.getNameByType(limitConfig.getType()));
//        LogRecordContext.putVariable("limitId", limitConfig.getId());
        return limitConfig.getId();
    }

    @Override
//    @LogRecord(type = CRM_CUSTOMER_LIMIT_CONFIG_TYPE, subType = CRM_CUSTOMER_LIMIT_CONFIG_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
//            success = CRM_CUSTOMER_LIMIT_CONFIG_UPDATE_SUCCESS)
    public void updateCustomerLimitConfig(CrmCustomerLimitConfigSaveReqVO updateReqVO) {
        // 校验存在
        CrmCustomerLimitConfigDO oldLimitConfig = validateCustomerLimitConfigExists(updateReqVO.getId());
        validateUserAndDept(updateReqVO.getUserIds(), updateReqVO.getDeptIds());
        // 更新
        CrmCustomerLimitConfigDO updateObj = BeanUtils.toBean(updateReqVO, CrmCustomerLimitConfigDO.class);
        customerLimitConfigMapper.updateById(updateObj);

        // 记录操作日志上下文
//        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldLimitConfig, CrmCustomerLimitConfigSaveReqVO.class));
    }

    @Override
//    @LogRecord(type = CRM_CUSTOMER_LIMIT_CONFIG_TYPE, subType = CRM_CUSTOMER_LIMIT_CONFIG_DELETE_SUB_TYPE, bizNo = "{{#id}}",
//            success = CRM_CUSTOMER_LIMIT_CONFIG_DELETE_SUCCESS)
    public void deleteCustomerLimitConfig(Long id) {
        // 校验存在
        CrmCustomerLimitConfigDO limitConfig = validateCustomerLimitConfigExists(id);
        // 删除
        customerLimitConfigMapper.deleteById(id);

        // 记录操作日志上下文
//        LogRecordContext.putVariable("limitType", CrmCustomerLimitConfigTypeEnum.getNameByType(limitConfig.getType()));
    }

    @Override
    public CrmCustomerLimitConfigDO getCustomerLimitConfig(Long id) {
        return customerLimitConfigMapper.selectById(id);
    }

    @Override
    public PageResult<CrmCustomerLimitConfigDO> getCustomerLimitConfigPage(CrmCustomerLimitConfigPageReqVO pageReqVO) {
        return customerLimitConfigMapper.selectPage(pageReqVO);
    }

    private CrmCustomerLimitConfigDO validateCustomerLimitConfigExists(Long id) {
        CrmCustomerLimitConfigDO limitConfigDO = customerLimitConfigMapper.selectById(id);
        if (limitConfigDO == null) {
            throw exception(CUSTOMER_LIMIT_CONFIG_NOT_EXISTS);
        }
        return limitConfigDO;
    }

    /**
     * 校验入参的用户和部门
     *
     * @param userIds 用户 ids
     * @param deptIds 部门 ids
     */
    private void validateUserAndDept(Collection<Long> userIds, Collection<Long> deptIds) {
        deptApi.validateDeptList(deptIds).checkError();
        adminUserApi.validateUserList(userIds).checkError();
    }

    @Override
    public List<CrmCustomerLimitConfigDO> getCustomerLimitConfigListByUserId(Integer type, Long userId) {
        AdminUserRespDTO user = adminUserApi.getUser(userId).getCheckedData();
        Assert.notNull(user, "用户({})不存在", userId);
        return customerLimitConfigMapper.selectListByTypeAndUserIdAndDeptId(type, userId, user.getDeptId());
    }

}
