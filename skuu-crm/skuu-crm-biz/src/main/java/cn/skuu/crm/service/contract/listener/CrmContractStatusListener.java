package cn.skuu.crm.service.contract.listener;

import cn.skuu.crm.service.contract.CrmContractService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 合同审批的结果的监听器实现类
 *
 * @author HUIHUI
 */
@Component
public class CrmContractStatusListener {

    @Resource
    private CrmContractService contractService;

//    @Override
//    public String getProcessDefinitionKey() {
//        return CrmContractServiceImpl.BPM_PROCESS_DEFINITION_KEY;
//    }
//
//    @Override
//    protected void onEvent(BpmProcessInstanceStatusEvent event) {
//        contractService.updateContractAuditStatus(Long.parseLong(event.getBusinessKey()), event.getStatus());
//    }

}
