package cn.skuu.framework.common.exception;

import cn.skuu.framework.common.enums.CommonResponseEnum;
import cn.skuu.framework.common.enums.ICode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常
 *
 * @author dcx
 * @date 2024/4/26 17:29
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ServerException extends RuntimeException {

    private Integer code;
    private String message;

    public ServerException() {
    }

    public ServerException(String message) {
        this.code = CommonResponseEnum.BIZ_ERROR.getCode();
        this.message = message;
    }

    public ServerException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMsg();
    }

    public ServerException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ServerException(CommonResponseEnum responseEnum) {
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }

    public ServerException(ICode iCode) {
        this.code = iCode.getCode();
        this.message = iCode.getMsg();
    }

    public ServerException(ICode exceptionCode, String... strings) {
        this.code = exceptionCode.getCode();
        this.message = String.format(exceptionCode.getMsg(), strings);
    }

}
