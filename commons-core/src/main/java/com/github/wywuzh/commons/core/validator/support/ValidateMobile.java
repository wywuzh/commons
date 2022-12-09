/*
 * Copyright 2015-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.wywuzh.commons.core.validator.support;

import com.github.wywuzh.commons.core.validator.PatternType;
import com.github.wywuzh.commons.core.validator.Validate;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.util.Assert;

/**
 * 类ValidateMobile.java的实现描述：手机号验证
 *
 * <pre>
 *  国家号码段分配如下:
 *
 *  移动：134,135,136,137,138,139,147,150,151,152,157,158,159,172,178,182,183,184,187,188,195,197,198
 *  联通：130,131,132,140,145,146,155,156,166,175,176,185,186,196
 *  电信：133,149,153,173,177,180,181,189,191,193,199
 *  虚拟运营商：162,165,167,170,171
 * </pre>
 *
 * 手机号码段：https://m.jihaoba.com/tools/haoduan/
 *
 * @author 伍章红 2015-8-2 下午8:02:52
 * @see Validate
 * @see PatternType
 * @since JDK 1.7.0_71
 */
public class ValidateMobile extends Validate {

    private static ValidateMobile VALIDATE_MOBILE = new ValidateMobile();

    private ValidateMobile() {
    }

    @Override
    protected PatternType getPatternType() {
        return PatternType.PATTERN_MOBILE;
    }

    /**
     * @return 手机号验证实例对象，单例
     * @since v2.4.8
     */
    public static ValidateMobile getInstance() {
        return VALIDATE_MOBILE;
    }

    /**
     * 验证手机号所属运营商类型
     *
     * @param mobile
     * @return 手机号所属运营商类型：-1=手机号输入有误，校验不通过, 0=未知运营商, 1=中国移动, 2=中国联通, 3=中国电信, 4=虚拟运营商）
     * @author 伍章红 2015-8-2 ( 下午8:07:29 )
     */
    public String validateMobileType(String mobile) {
        Assert.notNull(mobile, "手机号输入不能为空");

        // 验证手机号
        if (!VALIDATE_MOBILE.matches(mobile)) {
            return MobileTypeEnum.Failure.getType();
        }

        String prefix = mobile.trim().substring(0, 3);
        // 中国移动
        List<String> segmentForCMCC = new LinkedList<>(
                Arrays.asList("134", "135", "136", "137", "138", "139", "147", "150", "151", "152", "157", "158", "159", "172", "178", "182", "183", "184", "187", "188", "195", "197", "198"));
        if (segmentForCMCC.contains(prefix)) {
            return MobileTypeEnum.CMCC.getType();
        }
        // 中国联通
        List<String> segmentForUnicom = new LinkedList<>(Arrays.asList("130", "131", "132", "140", "145", "146", "155", "156", "166", "175", "176", "185", "186", "196"));
        if (segmentForUnicom.contains(prefix)) {
            return MobileTypeEnum.Unicom.getType();
        }
        // 中国电信
        List<String> segmentForTelecom = new LinkedList<>(Arrays.asList("133", "149", "153", "173", "177", "180", "181", "189", "191", "193", "199"));
        if (segmentForTelecom.contains(prefix)) {
            return MobileTypeEnum.Telecom.getType();
        }
        // 虚拟运营商
        List<String> segmentForVirtual = new LinkedList<>(Arrays.asList("162", "165", "167", "170", "171"));
        if (segmentForVirtual.contains(prefix)) {
            return MobileTypeEnum.Virtual.getType();
        }

        // 未知运营商
        return MobileTypeEnum.UNKNOW.getType();
    }

    /**
     * 获取手机号的邮箱地址
     *
     * @param mobile
     * @return
     * @author 伍章红 2015-8-2 ( 下午8:25:49 )
     */
    public String getMobileEmail(String mobile) {
        String mobileType = validateMobileType(mobile);

        if (MobileTypeEnum.CMCC.getType().equals(mobileType)) {
            return mobile + "@139.com";
        } else if (MobileTypeEnum.Unicom.getType().equals(mobileType)) {
            return mobile + "@wo.com.cn";
        } else if (MobileTypeEnum.Telecom.getType().equals(mobileType)) {
            return mobile + "@189.com";
        }
        return null;
    }

    /**
     * 运营商类型
     *
     * @since v2.4.8
     */
    public static enum MobileTypeEnum {
        Failure("-1", "手机号输入有误，校验不通过", null), UNKNOW("0", "未知运营商", null), CMCC("1", "中国移动",
                new LinkedList<>(Arrays.asList("134", "135", "136", "137", "138", "139", "147", "150", "151", "152", "157", "158", "159", "172", "178", "182", "183", "184", "187", "188", "195", "197",
                        "198"))), Unicom("2", "中国联通", new LinkedList<>(Arrays.asList("130", "131", "132", "140", "145", "146", "155", "156", "166", "175", "176", "185", "186", "196"))), Telecom("3",
                                "中国电信", new LinkedList<>(Arrays.asList("133", "149", "153", "173", "177", "180", "181", "189", "191", "193", "199"))), Virtual("4", "虚拟运营商",
                                        new LinkedList<>(Arrays.asList("162", "165", "167", "170", "171"))),;

        /**
         * 类型标识
         */
        private String type;
        private String desc;
        /**
         * 手机号码段
         */
        private List<String> segments;

        MobileTypeEnum(String type, String desc, List<String> segments) {
            this.type = type;
            this.desc = desc;
            this.segments = segments;
        }

        public String getType() {
            return type;
        }

        public String getDesc() {
            return desc;
        }

        public List<String> getSegments() {
            return segments;
        }
    }

}
