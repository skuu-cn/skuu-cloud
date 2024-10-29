package cn.skuu.crm.service.contract;

import cn.skuu.crm.controller.admin.contract.vo.config.CrmContractConfigSaveReqVO;
import cn.skuu.crm.dal.dataobject.contract.CrmContractConfigDO;

import javax.validation.Valid;

/**
 * 合同配置 Service 接口
 *
 * @author skuu
 */
public interface CrmContractConfigService {

    /**
     * 获得合同配置
     *
     * @return 合同配置
     */
    CrmContractConfigDO getContractConfig();

    /**
     * 保存合同配置
     *
     * @param saveReqVO 更新信息
     */
    void saveContractConfig(@Valid CrmContractConfigSaveReqVO saveReqVO);

}
