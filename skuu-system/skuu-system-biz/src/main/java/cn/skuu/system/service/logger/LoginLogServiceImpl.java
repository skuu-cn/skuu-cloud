package cn.skuu.system.service.logger;

import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.system.controller.admin.logger.vo.loginlog.LoginLogExportReqVO;
import cn.skuu.system.controller.admin.logger.vo.loginlog.LoginLogPageReqVO;
import cn.skuu.system.convert.logger.LoginLogConvert;
import cn.skuu.system.dal.dataobject.logger.LoginLogDO;
import cn.skuu.system.dal.mysql.logger.LoginLogMapper;
import cn.skuu.system.api.logger.dto.LoginLogCreateReqDTO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * 登录日志 Service 实现
 */
@Service
@Validated
public class LoginLogServiceImpl implements LoginLogService {

    @Resource
    private LoginLogMapper loginLogMapper;

    @Override
    public PageResult<LoginLogDO> getLoginLogPage(LoginLogPageReqVO reqVO) {
        return loginLogMapper.selectPage(reqVO);
    }

    @Override
    public List<LoginLogDO> getLoginLogList(LoginLogExportReqVO reqVO) {
        return loginLogMapper.selectList(reqVO);
    }

    @Override
    public void createLoginLog(LoginLogCreateReqDTO reqDTO) {
        LoginLogDO loginLog = LoginLogConvert.INSTANCE.convert(reqDTO);
        loginLogMapper.insert(loginLog);
    }

}
