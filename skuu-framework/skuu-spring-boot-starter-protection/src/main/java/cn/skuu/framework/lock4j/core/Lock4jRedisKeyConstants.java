package cn.skuu.framework.lock4j.core;

import cn.skuu.framework.redis.core.RedisKeyDefine;
import org.redisson.api.RLock;


/**
 * Lock4j Redis Key 枚举类
 *
 * @author dcx
 */
public interface Lock4jRedisKeyConstants {

    RedisKeyDefine LOCK4J = new RedisKeyDefine("分布式锁",
            "lock4j:%s", // 参数来自 DefaultLockKeyBuilder 类
            RedisKeyDefine.KeyTypeEnum.HASH, RLock.class, RedisKeyDefine.TimeoutTypeEnum.DYNAMIC); // Redisson 的 Lock 锁，使用 Hash 数据结构

}
