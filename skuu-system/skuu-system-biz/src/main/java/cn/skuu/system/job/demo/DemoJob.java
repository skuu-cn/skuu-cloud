package cn.skuu.system.job.demo;

import cn.skuu.framework.tenant.core.job.TenantJob;
import org.springframework.stereotype.Component;

@Component
@TenantJob
public class DemoJob {

//    @XxlJob("demoJob")
    public void execute() {
        System.out.println("美滋滋");
    }

}
