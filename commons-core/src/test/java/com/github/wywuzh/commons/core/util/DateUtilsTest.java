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
package com.github.wywuzh.commons.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;

/**
 * 类DateUtilTest的实现描述：时间工具类
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a> 2022-03-31 12:53:22
 * @version v2.5.2
 * @since JDK 1.8
 */
@Slf4j
public class DateUtilsTest {

  // 根据时间类型添加num值
  @Test
  public void addNumWithType() {
    Date currentDate = new Date();
    int fieldType = Calendar.DAY_OF_MONTH;
    int num = 1;
    log.info("date={}, fieldType={}, num={} addNumWithType结果：{}", currentDate, fieldType, num, DateUtils.addNumWithType(currentDate, fieldType, num));
  }

  @Test
  public void getFirstDaily() {
    Date currentDate = new Date();
    log.info("date={} getFirstDaily结果：{}", currentDate, DateUtils.getFirstDaily(currentDate));
  }

  @Test
  public void getPreYearMonth() {
    String yearMonth = "2022-03";
    String preYearMonth = DateUtils.getPreYearMonth(yearMonth);
    log.info("{}的上一年月是：{}", yearMonth, preYearMonth);
  }

  @Test
  public void getNextYearMonth() {
    String yearMonth = "2022-03";
    String preYearMonth = DateUtils.getNextYearMonth(yearMonth);
    log.info("{}的下一年月是：{}", yearMonth, preYearMonth);
  }

  @Test
  public void getDayInterval() {
    String startDate = "2022-01-01";
    String endDate = "2022-03-31";
    Integer dayInterval = DateUtils.getDayInterval(DateUtils.parse(startDate, DateUtils.PATTERN_DATE), DateUtils.parse(endDate, DateUtils.PATTERN_DATE), true);
    log.info("startDate={}, endDate={} 两个日期的天数间隔：{}", startDate, endDate, dayInterval);
  }

  @Test
  public void getMonthInterval() {
    String startDate = "2022-01-01";
    String endDate = "2022-03-31";
    Integer dayInterval = DateUtils.getMonthInterval(DateUtils.parse(startDate, DateUtils.PATTERN_DATE), DateUtils.parse(endDate, DateUtils.PATTERN_DATE), true);
    log.info("startDate={}, endDate={} 两个日期的月份间隔：{}", startDate, endDate, dayInterval);
  }

  public static void main(String[] args) {
    SimpleDateFormat instance = DateUtils.getInstance();
    System.out.println(instance.format(new Date()));
    System.out.println(DateUtils.format(new Date()));

    String ym = "2014-10";
    Date parseDate = DateUtils.parse(ym, DateUtils.PATTERN_YYYY_MM);
    System.out.println("parseDate:" + DateUtils.format(parseDate, DateUtils.PATTERN_DATE_TIME));

    Date currentDate = new Date();
    System.out.println("addNumWithType:" + DateUtils.format(DateUtils.addNumWithType(currentDate, DateUtils.FIELD_DATE, -10), DateUtils.PATTERN_DATE_TIME));

    // 日期
    String daily = "2015-08-06";
    System.out.println(DateUtils.format(DateUtils.getFirstDaily(daily), DateUtils.PATTERN_DATE_TIME));
    System.out.println(DateUtils.format(DateUtils.getLastDaily(daily), DateUtils.PATTERN_DATE_TIME));

    // 月份
    String monthly = "2015-08";
    System.out.println("getFirstMonthly:" + DateUtils.format(DateUtils.getFirstMonthly(monthly), DateUtils.PATTERN_DATE_TIME));
    System.out.println("getLastMonthly:" + DateUtils.format(DateUtils.getLastMonthly(monthly), DateUtils.PATTERN_DATE_TIME));

    // 季度
    Date quarter = new Date();
    System.out.println("getFirstQuarter:" + DateUtils.format(DateUtils.getFirstQuarter(quarter), DateUtils.PATTERN_DATE_TIME));
    System.out.println("getLastQuarter:" + DateUtils.format(DateUtils.getLastQuarter(quarter), DateUtils.PATTERN_DATE_TIME));

    long times = 1443715200000L;
    System.out.println(DateUtils.format(new Date(times), DateUtils.PATTERN_DATE_TIME));
  }
}
