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
package io.github.wywuzh.commons.core.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;

import cn.hutool.core.date.LocalDateTimeUtil;

/**
 * 类DateUtilTest的实现描述：时间工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2022-03-31 12:53:22
 * @version v2.5.2
 * @since JDK 1.8
 */
@Slf4j
public class DateUtilsTest {

    @Test
    public void nativeParseForDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtils.PATTERN_YYYY_MM);
        TemporalAccessor temporalAccessor = formatter.parse("2025-07");

        LocalDateTime localDateTime = LocalDateTimeUtil.of(temporalAccessor);

        // 转换为Instant
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        log.info("采用DateTimeFormatter原生解析结果：{}", date);
        log.info("format结果：{}", DateUtils.format(date, DateUtils.PATTERN_YYYY_MM));

        log.info("new Date() 日期结果：{}", new Date());
        log.info("new Date() format结果：{}", DateUtils.format(new Date(), DateUtils.PATTERN_YYYY_MM));
    }

    @Test
    public void nativeParseForYearMonth() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtils.PATTERN_YYYY_MM).withZone(ZoneId.of("GMT+8"));
        TemporalAccessor temporalAccessor = formatter.parse("2025-07");

        /*
         * LocalDateTime localDateTime = null;
         * if (temporalAccessor instanceof Instant) {
         * localDateTime = LocalDateTime.ofInstant((Instant) temporalAccessor, ZoneId.systemDefault());
         * } else if (temporalAccessor instanceof LocalDate) {
         * localDateTime = ((LocalDate) temporalAccessor).atStartOfDay();
         * } else if (temporalAccessor instanceof YearMonth) {
         * localDateTime = ((YearMonth) temporalAccessor).atEndOfMonth().atStartOfDay();
         * } else {
         * localDateTime = LocalDateTime.from(temporalAccessor);
         * }
         */
        LocalDateTime localDateTime = LocalDateTimeUtil.of(temporalAccessor);

        // 转换为Instant
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        log.info("采用DateTimeFormatter原生解析结果：{}", date);
        log.info("format结果：{}", DateUtils.format(date, DateUtils.PATTERN_YYYY_MM));

        log.info("new Date()结果：{}", new Date());
        log.info("new Date()结果：{}", DateUtils.format(new Date(), DateUtils.PATTERN_YYYY_MM));
    }

    @Test
    public void parse() {
        log.info("parse解析结果：{}", DateUtils.parse("2023-02-01", DateUtils.PATTERN_DATE, false));
        log.info("parse解析结果：{}", DateUtils.parse("2023-02-01 18:31:00", DateUtils.PATTERN_DATE_TIME, false));
        log.info("parse解析结果：{}", DateUtils.parse("18:31:00", DateUtils.PATTERN_TIME, false));
        log.info("parse解析结果：{}", DateUtils.parse("02-01", "MM-dd", false));
    }

    // 根据时间类型添加num值
    @Test
    public void addNumWithType() {
        Date currentDate = new Date();
        int fieldType = Calendar.DAY_OF_MONTH;
        int num = 1;
        log.info("date={}, fieldType={}, num={} addNumWithType结果：{}", currentDate, fieldType, num, DateUtils.addNumWithType(currentDate, fieldType, num));
    }

    // 获取传入日期当天的开始时间
    @Test
    public void getFirstDaily() {
        Date currentDate = new Date();
        log.info("date={} getFirstDaily结果：{}", currentDate, DateUtils.getFirstDaily(currentDate));
    }

    // 获取上一年月
    @Test
    public void getPreYearMonth() {
        String yearMonth = "2022-03";
        String preYearMonth = DateUtils.getPreYearMonth(yearMonth);
        log.info("{}的上一年月是：{}", yearMonth, preYearMonth);
    }

    // 获取下一年月
    @Test
    public void getNextYearMonth() {
        String yearMonth = "2022-03";
        String preYearMonth = DateUtils.getNextYearMonth(yearMonth);
        log.info("{}的下一年月是：{}", yearMonth, preYearMonth);
    }

    // 计算两个日期的天数间隔
    @Test
    public void getDayInterval() {
        String startDate = "2022-01-01";
        String endDate = "2022-03-31";
        Integer dayInterval = DateUtils.getDayInterval(DateUtils.parse(startDate, DateUtils.PATTERN_DATE), DateUtils.parse(endDate, DateUtils.PATTERN_DATE), true);
        log.info("startDate={}, endDate={} 两个日期的天数间隔：{}", startDate, endDate, dayInterval);
    }

    // 计算两个日期的月份间隔
    @Test
    public void getMonthInterval() {
        String startDate = "2022-01-01";
        String endDate = "2022-03-31";
        Integer dayInterval = DateUtils.getMonthInterval(DateUtils.parse(startDate, DateUtils.PATTERN_DATE), DateUtils.parse(endDate, DateUtils.PATTERN_DATE), true);
        log.info("startDate={}, endDate={} 两个日期的月份间隔：{}", startDate, endDate, dayInterval);
    }

    // 获取指定年月下的所有日期
    @Test
    public void getMonthFullDay() {
        log.info("获取{}年月下的所有日期：{}", "2023-04", DateUtils.getMonthFullDay("2023-04"));

        log.info("获取{}-{}年月下的所有日期：{}", 2023, 4, DateUtils.getMonthFullDay(2023, 4));
    }

    public static void main(String[] args) {
        log.info("DateTimeFormatter.format结果：{}", DateUtils.getFormatter().format(new Date().toInstant()));
        log.info("SimpleDateFormat.format结果：{}", DateUtils.format(new Date()));

        String ym = "2014-10";
        Date parseDate = DateUtils.parse(ym, DateUtils.PATTERN_YYYY_MM);
        log.info("SimpleDateFormat.parse结果：{}", parseDate);
        log.info("SimpleDateFormat.format结果：{}", DateUtils.format(parseDate, DateUtils.PATTERN_DATE_TIME));

        Date currentDate = new Date();
        log.info("currentDate：{}", currentDate);
        log.info("DateUtils.format(currentDate, DateUtils.PATTERN_DATE_TIME)：{}", DateUtils.format(currentDate, "yyyy-MM-dd HH:mm:ss"));
        Date addNumWithType = DateUtils.addNumWithType(currentDate, DateUtils.FIELD_DATE, -10);
        log.info("DateUtils.addNumWithType(currentDate, DateUtils.FIELD_DATE, -10)：{}", addNumWithType);
        log.info("DateUtils.format(addNumWithType, DateUtils.PATTERN_DATE_TIME)：{}", DateUtils.format(addNumWithType, "yyyy-MM-dd HH:mm:ss"));
        log.info("DateUtils.format(addNumWithType, DateUtils.PATTERN_DATE_TIME)：{}", DateUtils.format(addNumWithType, DateUtils.PATTERN_DATE_TIME));

        // 日期
        String daily = "2015-08-06";
        log.info(DateUtils.format(DateUtils.getFirstDaily(daily), DateUtils.PATTERN_DATE_TIME));
        log.info(DateUtils.format(DateUtils.getLastDaily(daily), DateUtils.PATTERN_DATE_TIME));

        // 月份
        String monthly = "2015-08";
        log.info("getFirstMonthly:" + DateUtils.format(DateUtils.getFirstMonthly(monthly), DateUtils.PATTERN_DATE_TIME));
        log.info("getLastMonthly:" + DateUtils.format(DateUtils.getLastMonthly(monthly), DateUtils.PATTERN_DATE_TIME));

        // 季度
        Date quarter = new Date();
        log.info("getFirstQuarter:" + DateUtils.format(DateUtils.getFirstQuarter(quarter), DateUtils.PATTERN_DATE_TIME));
        log.info("getLastQuarter:" + DateUtils.format(DateUtils.getLastQuarter(quarter), DateUtils.PATTERN_DATE_TIME));

        long times = 1443715200000L;
        log.info(DateUtils.format(new Date(times), DateUtils.PATTERN_DATE_TIME));
    }
}
