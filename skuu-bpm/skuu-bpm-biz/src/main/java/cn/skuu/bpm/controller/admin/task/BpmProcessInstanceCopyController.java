package cn.skuu.bpm.controller.admin.task;

import cn.hutool.core.collection.CollUtil;
import cn.skuu.bpm.controller.admin.task.vo.cc.BpmProcessInstanceCopyRespVO;
import cn.skuu.bpm.controller.admin.task.vo.instance.BpmProcessInstanceCopyPageReqVO;
import cn.skuu.bpm.dal.dataobject.task.BpmProcessInstanceCopyDO;
import cn.skuu.bpm.service.task.BpmProcessInstanceCopyService;
import cn.skuu.bpm.service.task.BpmProcessInstanceService;
import cn.skuu.framework.common.pojo.CommonResult;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.common.util.collection.MapUtils;
import cn.skuu.framework.common.util.date.DateUtils;
import cn.skuu.framework.common.util.object.BeanUtils;
import cn.skuu.system.api.user.AdminUserApi;
import cn.skuu.system.api.user.dto.AdminUserRespDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.flowable.engine.history.HistoricProcessInstance;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Stream;

import static cn.skuu.framework.common.pojo.CommonResult.success;
import static cn.skuu.framework.common.util.collection.CollectionUtils.convertListByFlatMap;
import static cn.skuu.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.skuu.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 流程实例抄送")
@RestController
@RequestMapping("/bpm/process-instance/copy")
@Validated
public class BpmProcessInstanceCopyController {

    @Resource
    private BpmProcessInstanceCopyService processInstanceCopyService;
    @Resource
    private BpmProcessInstanceService processInstanceService;

    @Resource
    private AdminUserApi adminUserApi;

    @GetMapping("/page")
    @Operation(summary = "获得抄送流程分页列表")
    @PreAuthorize("@ss.hasPermission('bpm:process-instance-cc:query')")
    public CommonResult<PageResult<BpmProcessInstanceCopyRespVO>> getProcessInstanceCopyPage(
            @Valid BpmProcessInstanceCopyPageReqVO pageReqVO) {
        PageResult<BpmProcessInstanceCopyDO> pageResult = processInstanceCopyService.getProcessInstanceCopyPage(
                getLoginUserId(), pageReqVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(new PageResult<>(pageResult.getTotal()));
        }

        // 拼接返回
        Map<String, HistoricProcessInstance> processInstanceMap = processInstanceService.getHistoricProcessInstanceMap(
                convertSet(pageResult.getList(), BpmProcessInstanceCopyDO::getProcessInstanceId));
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(convertListByFlatMap(pageResult.getList(),
                copy -> Stream.of(copy.getStartUserId(), Long.parseLong(copy.getCreator()))));
        return success(BeanUtils.toBean(pageResult, BpmProcessInstanceCopyRespVO.class, copyVO -> {
            MapUtils.findAndThen(userMap, Long.valueOf(copyVO.getCreator()), user -> copyVO.setCreatorName(user.getNickname()));
            MapUtils.findAndThen(userMap, copyVO.getStartUserId(), user -> copyVO.setStartUserName(user.getNickname()));
            MapUtils.findAndThen(processInstanceMap, copyVO.getProcessInstanceId(),
                    processInstance -> copyVO.setProcessInstanceStartTime(DateUtils.of(processInstance.getStartTime())));
        }));
    }

}
