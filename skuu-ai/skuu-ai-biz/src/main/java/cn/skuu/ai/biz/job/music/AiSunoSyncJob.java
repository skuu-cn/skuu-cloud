package cn.skuu.ai.biz.biz.job.music;

import cn.skuu.ai.biz.service.music.AiMusicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * 同步 Suno 任务状态以及回写对应的音乐信息 Job
 *
 * @author xiaoxin
 */
@Component
@Slf4j
public class AiSunoSyncJob {

    @Resource
    private AiMusicService musicService;

    public void execute(String param) {
        Integer count = musicService.syncMusic();
        log.info("[execute][同步 Suno ({}) 个]", count);
    }

}
