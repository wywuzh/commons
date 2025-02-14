/*
 * Copyright 2015-2025 the original author or authors.
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
package io.github.wywuzh.commons.core.validator;

/**
 * 类PatternType.java的实现描述：正则表达式校验类型
 *
 * @author 伍章红 2015-8-6 下午12:58:10
 * @since JDK 1.6.0_20
 */
public enum PatternType {

    /**
     * 电话，格式020-88888888
     */
    PATTERN_PHONE("^((\\(\\d{2,3}\\))|(\\d{3}\\-))?(\\(0\\d{2,3}\\)|0\\d{2,3}-)?[1-9]\\d{6,7}(\\-\\d{1,4})?$"),
    /**
     * 手机号
     *
     * @see <a href="https://m.jihaoba.com/tools/haoduan/">手机号段</a>
     */
    PATTERN_MOBILE("^((13[0-9])|(14[0,5,6,7,9])|(15([0-3]|[5-9]))|(16[2,5,6,7])|(17([0-3]|[6-8]))|(18[0-9])|(19[1-3,5-9]))\\d{8}$"),
    /**
     * 邮箱
     */
    PATTERN_EMAIL("^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$"),
    /**
     * URL
     */
    PATTERN_URL(
            "^(http|https|www|ftp|rtsp|mms)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$"),
    /**
     * 身份证号码
     */
    PATTERN_IDCARD("^\\d{15}(\\d{2}[A-Za-z0-9])?$"),
    /**
     * 零和非零开头的数字，支持负数
     */
    PATTERN_NUMBER("^(-[1-9][0-9]*)|(0|[1-9][0-9]*)$"),
    /**
     * 数字金额的验证：
     * 支持正数和负数，不包括零
     */
    PATTERN_EX_ZERO("^(-[1-9][0-9]*)|([1-9][0-9]*)$"),
    /**
     * 数字金额的验证：
     * 支持正数，不包括零和负数
     */
    PATTERN_EX_ZERO_NEGATIVE("([1-9][0-9]*)$"),
    /**
     * 日期格式：yyyy-MM-dd
     */
    PATTERN_DATE(
            "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$"),
    /**
     * 时间格式：HH:mm:ss
     */
    PATTERN_TIME("(([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9])(:([0-5]?[0-9]))?$"),
    /**
     * 日期+时间格式：yyyy-MM-dd HH:mm:ss
     */
    PATTERN_DATE_TIME(
            "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)\\s(([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9])(:([0-5]?[0-9]))$"),;

    private String pattern;

    private PatternType(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
