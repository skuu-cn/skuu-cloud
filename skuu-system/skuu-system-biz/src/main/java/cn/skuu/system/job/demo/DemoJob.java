package cn.skuu.system.job.demo;

import cn.skuu.framework.tenant.core.job.TenantJob;
import org.springframework.stereotype.Component;

@Component
public class DemoJob {

//    @XxlJob("demoJob")
    @TenantJob
    public void execute() {
        System.out.println("美滋滋");
    }

}
