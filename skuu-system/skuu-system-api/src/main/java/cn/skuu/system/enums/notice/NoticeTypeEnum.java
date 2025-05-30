package cn.skuu.system.enums.notice;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通知类型
 *
 * @author skuu
 */
@Getter
@AllArgsConstructor
public enum NoticeTypeEnum {

    NOTICE(1),
    ANNOUNCEMENT(2);

    /**
     * 类型
     */
    private final Integer type;

}
