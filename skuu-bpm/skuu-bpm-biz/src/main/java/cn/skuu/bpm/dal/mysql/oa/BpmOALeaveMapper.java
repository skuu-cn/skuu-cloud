package cn.skuu.bpm.dal.mysql.oa;

import cn.skuu.bpm.controller.admin.oa.vo.BpmOALeavePageReqVO;
import cn.skuu.bpm.dal.dataobject.oa.BpmOALeaveDO;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.mybatis.core.mapper.BaseMapperX;
import cn.skuu.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * 请假申请 Mapper
 *
 * @author jason
 * @author skuu
 */
@Mapper
public interface BpmOALeaveMapper extends BaseMapperX<BpmOALeaveDO> {

    default PageResult<BpmOALeaveDO> selectPage(Long userId, BpmOALeavePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BpmOALeaveDO>()
                .eqIfPresent(BpmOALeaveDO::getUserId, userId)
                .eqIfPresent(BpmOALeaveDO::getStatus, reqVO.getStatus())
                .eqIfPresent(BpmOALeaveDO::getType, reqVO.getType())
                .likeIfPresent(BpmOALeaveDO::getReason, reqVO.getReason())
                .betweenIfPresent(BpmOALeaveDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(BpmOALeaveDO::getId));
    }

}
