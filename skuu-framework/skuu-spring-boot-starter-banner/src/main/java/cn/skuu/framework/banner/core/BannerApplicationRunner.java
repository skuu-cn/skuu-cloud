package cn.skuu.framework.banner.core;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.concurrent.TimeUnit;

/**
 * 项目启动成功后，提供文档相关的地址
 *
 * @author dcx
 */
@Slf4j
public class BannerApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        ThreadUtil.execute(() -> {
            ThreadUtil.sleep(1, TimeUnit.SECONDS); // 延迟 1 秒，保证输出到结尾
            log.info("\n----------------------------------------------------------\n\t" +
                            "项目启动成功！\n\t" +
                            "接口文档: \t{} \n\t" +
                            "开发文档: \t{} \n\t" +
                            "视频教程: \t{} \n\t" +
                            "源码解析: \t{} \n" +
                            "----------------------------------------------------------",
                    "https://cloud.iocoder.cn/api-doc/",
                    "https://cloud.iocoder.cn",
                    "https://t.zsxq.com/02Yf6M7Qn",
                    "https://t.zsxq.com/02B6ujIee");

            // 数据报表
            System.out.println("[报表模块 skuu-module-report 教程][参考 https://cloud.iocoder.cn/report/ 开启]");
            // 工作流
            System.out.println("[工作流模块 skuu-module-bpm 教程][参考 https://cloud.iocoder.cn/bpm/ 开启]");
            // 微信公众号
            System.out.println("[微信公众号 skuu-module-mp 教程][参考 https://cloud.iocoder.cn/mp/build/ 开启]");
            // 商城
            System.out.println("[商城系统 skuu-module-mall 教程][参考 https://cloud.iocoder.cn/mall/build/ 开启]");
        });
    }

}
