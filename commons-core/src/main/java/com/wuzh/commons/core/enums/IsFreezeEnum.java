package com.wuzh.commons.core.enums;

/**
 * 类IsFreezeEnum的实现描述：是否冻结
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2020-08-19 14:22:09
 * @version v2.3.3
 * @since JDK 1.8
 */
public enum IsFreezeEnum {
    TRUE(1),
    FALSE(0);

    private Integer value;

    IsFreezeEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static IsFreezeEnum findByValue(Integer value) {
        if (value == 0) {
            return IsFreezeEnum.FALSE;
        } else if (value == 1) {
            return IsFreezeEnum.TRUE;
        }
        return null;
    }
}
