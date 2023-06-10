package cn.skuu.framework.sms.core.client;

import cn.skuu.framework.common.exception.ErrorCode;
import cn.skuu.framework.sms.core.enums.SmsFrameworkErrorCodeConstants;

import java.util.function.Function;

/**
 * 将 API 的错误码，转换为通用的错误码
 *
 * @see SmsCommonResult
 * @see SmsFrameworkErrorCodeConstants
 *
 * @author dcx
 */
public interface SmsCodeMapping extends Function<String, ErrorCode> {
}
