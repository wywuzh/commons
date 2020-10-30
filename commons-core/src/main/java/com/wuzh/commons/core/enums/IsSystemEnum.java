package com.wuzh.commons.core.enums;

/**
 * 类IsSystemEnum的实现描述：是否系统内置
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2020-08-19 14:22:09
 * @version v2.3.3
 * @since JDK 1.8
 */
public enum IsSystemEnum {
    TRUE(1),
    FALSE(0);

    private Integer value;

    IsSystemEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static IsSystemEnum findByValue(Integer value) {
        if (value == 0) {
            return IsSystemEnum.FALSE;
        } else if (value == 1) {
            return IsSystemEnum.TRUE;
        }
        return null;
    }
}
