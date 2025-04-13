package cn.skuu.blog.enums;

import cn.skuu.framework.common.exception.ErrorCode;

/**
 * Blog 错误码枚举类
 * <p>
 * Blog 系统，使用 1-050-000-000 段
 */
public interface ErrorCodeConstants {

    ErrorCode TOPIC_NOT_EXISTS = new ErrorCode(1_050_001_001, "主题列不存在");
    ErrorCode BLOG_NOT_EXISTS = new ErrorCode(1_050_001_002, "博客不存在");


}
