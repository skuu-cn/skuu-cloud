package cn.skuu.bpm.convert.definition;

import cn.hutool.core.util.ArrayUtil;
import cn.skuu.bpm.controller.admin.base.user.UserSimpleBaseVO;
import cn.skuu.bpm.controller.admin.definition.vo.model.BpmModelMetaInfoVO;
import cn.skuu.bpm.controller.admin.definition.vo.model.BpmModelRespVO;
import cn.skuu.bpm.controller.admin.definition.vo.model.BpmModelSaveReqVO;
import cn.skuu.bpm.controller.admin.definition.vo.process.BpmProcessDefinitionRespVO;
import cn.skuu.bpm.dal.dataobject.definition.BpmCategoryDO;
import cn.skuu.bpm.dal.dataobject.definition.BpmFormDO;
import cn.skuu.bpm.framework.flowable.core.util.BpmnModelUtils;
import cn.skuu.framework.common.pojo.PageResult;
import cn.skuu.framework.common.util.date.DateUtils;
import cn.skuu.framework.common.util.json.JsonUtils;
import cn.skuu.framework.common.util.object.BeanUtils;
import cn.skuu.system.api.user.dto.AdminUserRespDTO;
import org.flowable.common.engine.impl.db.SuspensionState;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ProcessDefinition;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static cn.skuu.framework.common.util.collection.CollectionUtils.convertList;

/**
 * 流程模型 Convert
 *
 * @author yunlongn
 */
@Mapper
public interface BpmModelConvert {

    BpmModelConvert INSTANCE = Mappers.getMapper(BpmModelConvert.class);

    default PageResult<BpmModelRespVO> buildModelPage(PageResult<Model> pageResult,
                                                      Map<Long, BpmFormDO> formMap,
                                                      Map<String, BpmCategoryDO> categoryMap, Map<String, Deployment> deploymentMap,
                                                      Map<String, ProcessDefinition> processDefinitionMap,
                                                      Map<Long, AdminUserRespDTO> userMap) {
        List<BpmModelRespVO> list = convertList(pageResult.getList(), model -> {
            BpmModelMetaInfoVO metaInfo = parseMetaInfo(model);
            BpmFormDO form = metaInfo != null ? formMap.get(metaInfo.getFormId()) : null;
            BpmCategoryDO category = categoryMap.get(model.getCategory());
            Deployment deployment = model.getDeploymentId() != null ? deploymentMap.get(model.getDeploymentId()) : null;
            ProcessDefinition processDefinition = model.getDeploymentId() != null ? processDefinitionMap.get(model.getDeploymentId()) : null;
            List<AdminUserRespDTO> startUsers = metaInfo != null ? convertList(metaInfo.getStartUserIds(), userMap::get) : null;
            return buildModel0(model, metaInfo, form, category, deployment, processDefinition, startUsers);
        });
        return new PageResult<>(list, pageResult.getTotal());
    }

    default BpmModelRespVO buildModel(Model model,
                                     byte[] bpmnBytes) {
        BpmModelMetaInfoVO metaInfo = parseMetaInfo(model);
        BpmModelRespVO modelVO = buildModel0(model, metaInfo, null, null, null, null, null);
        if (ArrayUtil.isNotEmpty(bpmnBytes)) {
            modelVO.setBpmnXml(BpmnModelUtils.getBpmnXml(bpmnBytes));
        }
        return modelVO;
    }

    default BpmModelRespVO buildModel0(Model model,
                                       BpmModelMetaInfoVO metaInfo, BpmFormDO form, BpmCategoryDO category,
                                       Deployment deployment, ProcessDefinition processDefinition,
                                       List<AdminUserRespDTO> startUsers) {
        BpmModelRespVO modelRespVO = new BpmModelRespVO().setId(model.getId()).setName(model.getName())
                .setKey(model.getKey()).setCategory(model.getCategory())
                .setCreateTime(DateUtils.of(model.getCreateTime()));
        // Form
        BeanUtils.copyProperties(metaInfo, modelRespVO);
        if (form != null) {
            modelRespVO.setFormName(form.getName());
        }
        // Category
        if (category != null) {
            modelRespVO.setCategoryName(category.getName());
        }
        // ProcessDefinition
        if (processDefinition != null) {
            modelRespVO.setProcessDefinition(BeanUtils.toBean(processDefinition, BpmProcessDefinitionRespVO.class));
            modelRespVO.getProcessDefinition().setSuspensionState(processDefinition.isSuspended() ?
                    SuspensionState.SUSPENDED.getStateCode() : SuspensionState.ACTIVE.getStateCode());
            if (deployment != null) {
                modelRespVO.getProcessDefinition().setDeploymentTime(DateUtils.of(deployment.getDeploymentTime()));
            }
        }
        // User
        modelRespVO.setStartUsers(BeanUtils.toBean(startUsers, UserSimpleBaseVO.class));
        return modelRespVO;
    }

    default void copyToModel(Model model, BpmModelSaveReqVO reqVO) {
        model.setName(reqVO.getName());
        model.setKey(reqVO.getKey());
        model.setCategory(reqVO.getCategory());
        model.setMetaInfo(JsonUtils.toJsonString(BeanUtils.toBean(reqVO, BpmModelMetaInfoVO.class)));
    }

    default BpmModelMetaInfoVO parseMetaInfo(Model model) {
        BpmModelMetaInfoVO vo = JsonUtils.parseObject(model.getMetaInfo(), BpmModelMetaInfoVO.class);
        if (vo == null) {
            return null;
        }
        if (vo.getManagerUserIds() == null) {
            vo.setManagerUserIds(Collections.emptyList());
        }
        if (vo.getStartUserIds() == null) {
            vo.setStartUserIds(Collections.emptyList());
        }
        return vo;
    }

}
