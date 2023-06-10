package cn.skuu.framework.sms.core.client.impl.debug;

import cn.skuu.framework.common.exception.ErrorCode;
import cn.skuu.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.skuu.framework.sms.core.client.SmsCodeMapping;
import cn.skuu.framework.sms.core.enums.SmsFrameworkErrorCodeConstants;

import java.util.Objects;

/**
 * 钉钉的 SmsCodeMapping 实现类
 *
 * @author dcx
 */
public class DebugDingTalkCodeMapping implements SmsCodeMapping {

    @Override
    public ErrorCode apply(String apiCode) {
        return Objects.equals(apiCode, "0") ? GlobalErrorCodeConstants.SUCCESS : SmsFrameworkErrorCodeConstants.SMS_UNKNOWN;
    }

}
