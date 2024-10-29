package cn.skuu.crm.job.customer;

import cn.skuu.crm.service.customer.CrmCustomerService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 客户自动掉入公海 Job
 *
 * @author skuu
 */
@Component
public class CrmCustomerAutoPutPoolJob {

    @Resource
    private CrmCustomerService customerService;

    public String execute() {
        int count = customerService.autoPutCustomerPool();
        return String.format("掉入公海客户 %s 个", count);
    }

}