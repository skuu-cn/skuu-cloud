package cn.skuu.framework.pay.core.client;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.Assert;
import cn.skuu.framework.common.exception.ErrorCode;
import cn.skuu.framework.common.pojo.CommonResult;
import cn.skuu.framework.pay.core.enums.PayFrameworkErrorCodeConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 支付的 CommonResult 拓展类
 *
 * 考虑到不同的平台，返回的 code 和 msg 是不同的，所以统一额外返回 {@link #apiCode} 和 {@link #apiMsg} 字段
 *
 * @author dcx
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PayCommonResult<T> extends CommonResult<T> {

    /**
     * API 返回错误码
     *
     * 由于第三方的错误码可能是字符串，所以使用 String 类型
     */
    private String apiCode;
    /**
     * API 返回提示
     */
    private String apiMsg;

    private PayCommonResult() {
    }

    public static <T> PayCommonResult<T> build(String apiCode, String apiMsg, T data, AbstractPayCodeMapping codeMapping) {
        Assert.notNull(codeMapping, "参数 codeMapping 不能为空");
        PayCommonResult<T> result = new PayCommonResult<T>();
        result.setApiCode(apiCode);
        result.setApiMsg(apiMsg);
        result.setData(data);
        // 翻译错误码
        if (codeMapping != null) {
            ErrorCode errorCode = codeMapping.apply(apiCode, apiMsg);
            result.setCode(errorCode.getCode());
            result.setMsg(errorCode.getMsg());
        }
        return result;
    }

    public static <T> PayCommonResult<T> error(Throwable ex) {
        PayCommonResult<T> result = new PayCommonResult<>();
        result.setCode(PayFrameworkErrorCodeConstants.EXCEPTION.getCode());
        result.setMsg(ExceptionUtil.getRootCauseMessage(ex));
        return result;
    }

}
