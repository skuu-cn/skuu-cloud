package cn.skuu.member.service.signin;

import cn.skuu.member.controller.admin.signin.vo.config.MemberSignInConfigCreateReqVO;
import cn.skuu.member.controller.admin.signin.vo.config.MemberSignInConfigUpdateReqVO;
import cn.skuu.member.convert.signin.MemberSignInConfigConvert;
import cn.skuu.member.dal.dataobject.signin.MemberSignInConfigDO;
import cn.skuu.member.dal.mysql.signin.MemberSignInConfigMapper;
import cn.skuu.member.enums.ErrorCodeConstants;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

import static cn.skuu.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 签到规则 Service 实现类
 *
 * @author QingX
 */
@Service
@Validated
public class MemberSignInConfigServiceImpl implements MemberSignInConfigService {

    @Resource
    private MemberSignInConfigMapper memberSignInConfigMapper;

    @Override
    public Long createSignInConfig(MemberSignInConfigCreateReqVO createReqVO) {
        // 判断是否重复插入签到天数
        validateSignInConfigDayDuplicate(createReqVO.getDay(), null);

        // 插入
        MemberSignInConfigDO signInConfig = MemberSignInConfigConvert.INSTANCE.convert(createReqVO);
        memberSignInConfigMapper.insert(signInConfig);
        // 返回
        return signInConfig.getId();
    }

    @Override
    public void updateSignInConfig(MemberSignInConfigUpdateReqVO updateReqVO) {
        // 校验存在
        validateSignInConfigExists(updateReqVO.getId());
        // 判断是否重复插入签到天数
        validateSignInConfigDayDuplicate(updateReqVO.getDay(), updateReqVO.getId());

        // 判断更新
        MemberSignInConfigDO updateObj = MemberSignInConfigConvert.INSTANCE.convert(updateReqVO);
        memberSignInConfigMapper.updateById(updateObj);
    }

    @Override
    public void deleteSignInConfig(Long id) {
        // 校验存在
        validateSignInConfigExists(id);
        // 删除
        memberSignInConfigMapper.deleteById(id);
    }

    private void validateSignInConfigExists(Long id) {
        if (memberSignInConfigMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.SIGN_IN_CONFIG_NOT_EXISTS);
        }
    }

    /**
     * 校验 day 是否重复
     *
     * @param day 天
     * @param id  编号，只有更新的时候会传递
     */
    private void validateSignInConfigDayDuplicate(Integer day, Long id) {
        MemberSignInConfigDO config = memberSignInConfigMapper.selectByDay(day);
        // 1. 新增时，config 非空，则说明重复
        if (id == null && config != null) {
            throw exception(ErrorCodeConstants.SIGN_IN_CONFIG_EXISTS);
        }
        // 2. 更新时，如果 config 非空，且 id 不相等，则说明重复
        if (id != null && config != null && !config.getId().equals(id)) {
            throw exception(ErrorCodeConstants.SIGN_IN_CONFIG_EXISTS);
        }
    }

    @Override
    public MemberSignInConfigDO getSignInConfig(Long id) {
        return memberSignInConfigMapper.selectById(id);
    }

    @Override
    public List<MemberSignInConfigDO> getSignInConfigList() {
        List<MemberSignInConfigDO> list = memberSignInConfigMapper.selectList();
        list.sort(Comparator.comparing(MemberSignInConfigDO::getDay));
        return list;
    }

    @Override
    public List<MemberSignInConfigDO> getSignInConfigList(Integer status) {
        List<MemberSignInConfigDO> list = memberSignInConfigMapper.selectListByStatus(status);
        list.sort(Comparator.comparing(MemberSignInConfigDO::getDay));
        return list;
    }

}