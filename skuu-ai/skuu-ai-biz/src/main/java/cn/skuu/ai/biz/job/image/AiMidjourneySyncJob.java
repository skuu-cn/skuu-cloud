package cn.skuu.ai.biz.biz.job.image;

import cn.skuu.ai.biz.service.image.AiImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Midjourney 同步 Job：定时拉去 midjourney 绘制状态
 *
 * @author fansili
 */
@Component
@Slf4j
public class AiMidjourneySyncJob {

    @Resource
    private AiImageService imageService;

    public void execute(String param) {
        Integer count = imageService.midjourneySync();
        log.info("[execute][同步 Midjourney ({}) 个]", count);
    }

}
