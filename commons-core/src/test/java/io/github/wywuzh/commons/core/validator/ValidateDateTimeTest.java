/*
 * Copyright 2015-2026 the original author or authors.
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

import java.util.regex.Pattern;

/**
 * 类ValidateDateTimeTest的实现描述：TODO 类实现描述
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2026-03-04 11:48:13
 * @version v3.5.0
 * @since JDK 17
 */
public class ValidateDateTimeTest {

    // 优化后的正则表达式
    private static final String DATE_TIME_REGEX = "^(?:(?!0000)\\d{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1\\d|2[0-8])|"
            + "(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|"
            + "(?:\\d{2}(?:0[48]|[2468][048]|[13579][26])|"
            + "(?:0[48]|[2468][048]|[13579][26])00)-02-29)"
            /*+ "\\s(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d\\.\\d{3}$";*/
            + "\\s(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d\\.[0-9]{1,3}$";

    private static final Pattern DATE_TIME_PATTERN = Pattern.compile(PatternType.PATTERN_DATE_TIME1.getPattern());

    public static boolean isValidDateTime(String dateTime) {
        return dateTime != null && DATE_TIME_PATTERN.matcher(dateTime).matches();
    }

    public static void main(String[] args) {
        // 测试用例
        String[] testCases = {
                "2026-03-04 10:45:22.0",  // 正常日期，毫秒只有1位
                "2026-03-04 10:45:22.00",  // 正常日期，毫秒只有2位
                "2026-03-04 10:45:22.000",  // 正常日期，毫秒有3位
                "2024-02-29 10:45:22.000",  // 闰年
                "2023-02-28 23:59:59.999",  // 边界值
                "2023-04-31 10:45:22.000",  // 无效日期（4月没有31日）- 应该返回false
                "2023-02-29 10:45:22.000",  // 非闰年2月29 - 应该返回false
                "2026-03-04 25:45:22.000",  // 无效小时 - 应该返回false
        };

        for (String testCase : testCases) {
            System.out.println(testCase + " : " + isValidDateTime(testCase));
        }
    }

}
