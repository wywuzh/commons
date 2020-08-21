package com.wuzh.commons.core.enums;

/**
 * 类IsLockEnum的实现描述：是否锁定
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2020-08-19 14:22:09
 * @version v2.3.3
 * @since JDK 1.8
 */
public enum IsLockEnum {
    TRUE(1),
    FALSE(0);

    private Integer value;

    IsLockEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static IsLockEnum findByValue(Integer value) {
        if (value == 0) {
            return IsLockEnum.FALSE;
        } else if (value == 1) {
            return IsLockEnum.TRUE;
        }
        return null;
    }
}
