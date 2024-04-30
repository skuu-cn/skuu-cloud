package cn.skuu.framework.common.enums;

public enum CommonResponseEnum {
    ERROR(-1, "失败"),
    SUCCESS(200, "成功"),
    BAD_REQUEST(400, "请求参数不正确"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "无权限"),
    METHOD_NOT_ALLOWED(405, "方法不允许"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    SYSTEM_IS_BUSY(1001, "系统繁忙,请稍后再试!"),
    BIZ_ERROR(1002, "业务异常"),

    ;
    private int code;
    private String message;

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private CommonResponseEnum(final int code, final String message) {
        this.code = code;
        this.message = message;
    }
}
