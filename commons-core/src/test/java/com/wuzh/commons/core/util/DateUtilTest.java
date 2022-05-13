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
package com.wuzh.commons.core.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类DateUtilTest的实现描述：时间工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2022-03-31 12:53:22
 * @version v2.5.2
 * @since JDK 1.8
 */
@Slf4j
public class DateUtilTest {

    @Test
    public void getPreYearMonth() {
        String yearMonth = "2022-03";
        String preYearMonth = DateUtil.getPreYearMonth(yearMonth);
        log.info("{}的上一年月是：{}", yearMonth, preYearMonth);
    }

    @Test
    public void getNextYearMonth() {
        String yearMonth = "2022-03";
        String preYearMonth = DateUtil.getNextYearMonth(yearMonth);
        log.info("{}的下一年月是：{}", yearMonth, preYearMonth);
    }

    @Test
    public void getDayInterval() {
        String startDate = "2022-01-01";
        String endDate = "2022-03-31";
        Integer dayInterval = DateUtil.getDayInterval(DateUtil.parse(startDate, DateUtil.PATTERN_DATE), DateUtil.parse(endDate, DateUtil.PATTERN_DATE), true);
        log.info("startDate={}, endDate={} 两个日期的天数间隔：{}", startDate, endDate, dayInterval);
    }

    @Test
    public void getMonthInterval() {
        String startDate = "2022-01-01";
        String endDate = "2022-03-31";
        Integer dayInterval = DateUtil.getMonthInterval(DateUtil.parse(startDate, DateUtil.PATTERN_DATE), DateUtil.parse(endDate, DateUtil.PATTERN_DATE), true);
        log.info("startDate={}, endDate={} 两个日期的月份间隔：{}", startDate, endDate, dayInterval);
    }


    public static void main(String[] args) {
        SimpleDateFormat instance = DateUtil.getInstance();
        System.out.println(instance.format(new Date()));
        System.out.println(DateUtil.format(new Date()));

        String ym = "2014-10";
        Date parseDate = DateUtil.parse(ym, DateUtil.PATTERN_YYYY_MM);
        System.out.println("parseDate:" + DateUtil.format(parseDate, DateUtil.PATTERN_DATE_TIME));

        Date currentDate = new Date();
        System.out.println("addNumWithType:" + DateUtil.format(DateUtil.addNumWithType(currentDate, DateUtil.FIELD_DATE, -10), DateUtil.PATTERN_DATE_TIME));

        // 日期
        String daily = "2015-08-06";
        System.out.println(DateUtil.format(DateUtil.getFirstDaily(daily), DateUtil.PATTERN_DATE_TIME));
        System.out.println(DateUtil.format(DateUtil.getLastDaily(daily), DateUtil.PATTERN_DATE_TIME));

        // 月份
        String monthly = "2015-08";
        System.out.println("getFirstMonthly:" + DateUtil.format(DateUtil.getFirstMonthly(monthly), DateUtil.PATTERN_DATE_TIME));
        System.out.println("getLastMonthly:" + DateUtil.format(DateUtil.getLastMonthly(monthly), DateUtil.PATTERN_DATE_TIME));

        // 季度
        Date quarter = new Date();
        System.out.println("getFirstQuarter:" + DateUtil.format(DateUtil.getFirstQuarter(quarter), DateUtil.PATTERN_DATE_TIME));
        System.out.println("getLastQuarter:" + DateUtil.format(DateUtil.getLastQuarter(quarter), DateUtil.PATTERN_DATE_TIME));

        long times = 1443715200000L;
        System.out.println(DateUtil.format(new Date(times), DateUtil.PATTERN_DATE_TIME));
    }
}
