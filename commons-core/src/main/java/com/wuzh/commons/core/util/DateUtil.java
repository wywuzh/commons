/*
 * Copyright 2015-2021 the original author or authors.
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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 类DateUtil.java的实现描述：时间工具类
 *
 * <pre>
 * 时间工具类中包含的方法：
 * 1）format：将传入的java.util.Date类型的时间转换为字符串数据，支持pattern格式定制
 * 2）parse：将出入的字符串解析成java.util.Date类型的时间数据，支持pattern格式定制
 * 3）addNumWithType：根据时间类型添加num值
 * 4）getFirstDaily：获取传入日期当天的开始时间
 * 5）getLastDaily：获取传入日期当天的结束时间
 * 6）getFirstMonthly：获取传入月当月的开始时间
 * 7）getLastMonthly：获取传入月当月的结束时间
 * 8）getFirstQuarter：获取传入时间所在季度的第一天，返回java.util.Date类型数据
 * 9）getLastQuarter：获取传入时间所在季度的最后一天，返回java.util.Date类型数据
 * </pre>
 *
 * @author <a href="mailto:wywuzh@163.com">wuzh</a> 2014-4-24 下午1:57:39
 * @version 1.0.0
 * @since JDK 1.6.0_20
 */
public class DateUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

    public static final String PATTERN_YYYY = "yyyy";
    public static final String PATTERN_YYYY_MM = "yyyy-MM";
    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final String PATTERN_YYYY_MM_DD_24HH = "yyyy-MM-dd HH";
    public static final String PATTERN_YYYY_MM_DD_24HH_MI = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_TIME = "HH:mm:ss";

    public static final int FIELD_YEAR = Calendar.YEAR;
    public static final int FIELD_MONTH = Calendar.MONTH;
    public static final int FIELD_DATE = Calendar.DATE;
    public static final int FIELD_DAY_OF_MONTH = Calendar.DAY_OF_MONTH;
    public static final int FIELD_HOUR = Calendar.HOUR;
    public static final int FIELD_HOUR_OF_DAY = Calendar.HOUR_OF_DAY;
    public static final int FIELD_MINUTE = Calendar.MINUTE;
    public static final int FIELD_SECOND = Calendar.SECOND;
    public static final int FIELD_MILLISECOND = Calendar.MILLISECOND;

    /**
     * 时间格式化工具
     */
    private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(PATTERN_DATE_TIME);
        }
    };

    /**
     * 获取SimpleDateFormat实例
     *
     * <pre>
     *  创建SimpleDateFormat实例，pattern默认采用“yyyy-MM-dd HH:mm:ss”格式
     *
     *  DateUtil工具在创建SimpleDateFormat实例时，采用single单例模式，确保DateUtil类对象中永远只有一个SimpleDateFormat实例
     * </pre>
     *
     * @return SimpleDateFormat实例
     */
    public static SimpleDateFormat getInstance() {
        return threadLocal.get();
    }

    /**
     * 获取pattern格式的SimpleDateFormat实例
     *
     * <pre>
     * 根据传入的pattern格式创建SimpleDateFormat实例，如果传入pattern参数为空，则pattern默认采用“yyyy-MM-dd HH:mm:ss”格式
     *
     * DateUtil工具在创建SimpleDateFormat实例时，采用single单例模式，确保DateUtil类对象中永远只有一个SimpleDateFormat实例
     * </pre>
     *
     * @param pattern
     * @return
     */
    public static SimpleDateFormat getInstance(String pattern) {
        SimpleDateFormat dateFormat = threadLocal.get();
        dateFormat.applyPattern(pattern);
        return dateFormat;
    }

    /**
     * 将传入时间格式化成一个字符串，默认采用“yyyy-MM-dd HH:mm:ss”格式
     *
     * @param date
     * @return 返回“yyyy-MM-dd HH:mm:ss”格式字符串，如果传入时间为空，返回null
     */
    public static String format(Date date) {
        if (null == date) {
            return null;
        }

        return getInstance().format(date);
    }

    /**
     * 将传入时间格式化成一个字符串
     *
     * <pre>
     *  返回pattern格式字符串，如果pattern格式传入为空，默认采用“yyyy-MM-dd HH:mm:ss”格式；
     *  如果传入时间为空，返回null
     * </pre>
     *
     * @param date
     * @param pattern
     * @return 返回“yyyy-MM-dd HH:mm:ss”格式字符串，如果传入时间为空，返回null
     */
    public static String format(Date date, String pattern) {
        if (null == date) {
            return null;
        }

        return getInstance(pattern).format(date);
    }

    /**
     * 将传入的时间字符串解析成java.util.Date时间格式
     *
     * <pre>
     * 如果传入一个空时间字符串，则返回空对象
     * </pre>
     *
     * @param parseDate
     * @return
     */
    public static Date parse(String parseDate) {
        Date date = null;
        if (StringUtils.isNotEmpty(parseDate)) {
            try {
                date = getInstance().parse(parseDate);
            } catch (ParseException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        return date;
    }

    /**
     * 将传入的时间字符串解析成java.util.Date时间格式
     *
     * <pre>
     * 如果传入一个空时间字符串，则返回空对象
     * </pre>
     *
     * @param parseDate
     * @param pattern
     * @return
     */
    public static Date parse(String parseDate, String pattern) {
        Date date = null;
        if (StringUtils.isNotEmpty(parseDate)) {
            try {
                date = getInstance(pattern).parse(parseDate);
            } catch (ParseException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return date;
    }

    /**
     * 根据时间类型添加num值
     *
     * <pre>
     *  如：addNumWithType方法中传入的type类型为Calendar.YEAR，num为2，则表示在date时间上加两年得到的时间；
     *      num为-2，则表示在date时间上减去两年得到的时间。
     *  type类型为Calendar.YEAR、Calendar.MONTH、Calendar.DATE等
     * </pre>
     *
     * @param date      时间
     * @param fieldType 时间类型
     * @param num       添加值
     * @return
     */
    public static Date addNumWithType(Date date, int fieldType, int num) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(fieldType, num);
        return calendar.getTime();
    }

    /**
     * 获取传入日期当天的开始时间
     *
     * @param daily
     * @return
     */
    public static Date getFirstDaily(Date daily) {
        if (null == daily) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(daily);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取传入日期当天的开始时间
     *
     * @param daily yyyy-MM-dd格式
     * @return
     */
    public static Date getFirstDaily(String daily) {
        if (StringUtils.isEmpty(daily)) {
            return null;
        }

        return getFirstDaily(parse(daily, PATTERN_DATE));
    }

    /**
     * 获取传入日期当天的开始时间
     *
     * @param daily yyyy-MM-dd格式
     * @return 如果传入参数值为空，返回0
     */
    public static long getFirstDailyTime(String daily) {
        if (StringUtils.isEmpty(daily)) {
            return 0;
        }
        Date firstDaily = getFirstDaily(parse(daily, PATTERN_DATE));
        return (null == firstDaily) ? 0 : firstDaily.getTime();
    }

    /**
     * 获取传入日期当天的开始时间
     *
     * @param daily
     * @return 如果传入参数值为空，返回0
     */
    public static long getFirstDailyTime(Date daily) {
        if (null == daily) {
            return 0;
        }
        Date firstDaily = getFirstDaily(daily);
        return (null == firstDaily) ? 0 : firstDaily.getTime();
    }

    /**
     * 获取传入日期当天的结束时间
     *
     * @param daily
     * @return
     */
    public static Date getLastDaily(Date daily) {
        if (null == daily) {
            return null;
        }
        Date firstDaily = getFirstDaily(daily);
        if (null == firstDaily) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(firstDaily);
        calendar.add(Calendar.DATE, 1);
        calendar.add(Calendar.MILLISECOND, -1);

        return calendar.getTime();
    }

    /**
     * 获取传入日期当天的结束时间
     *
     * @param daily yyyy-MM-dd格式
     * @return
     */
    public static Date getLastDaily(String daily) {
        if (StringUtils.isEmpty(daily)) {
            return null;
        }

        return getLastDaily(parse(daily, PATTERN_DATE));
    }

    /**
     * 获取传入日期当天的结束时间
     *
     * @param daily yyyy-MM-dd格式
     * @return 如果传入参数值为空，返回0
     */
    public static long getLastDailyTime(String daily) {
        if (StringUtils.isEmpty(daily)) {
            return 0;
        }

        Date lastDaily = getLastDaily(parse(daily, PATTERN_DATE));
        return lastDaily == null ? 0 : lastDaily.getTime();
    }

    /**
     * 获取传入日期当天的结束时间
     *
     * @param daily
     * @return
     */
    public static long getLastDailyTime(Date daily) {
        Date lastDaily = getLastDaily(daily);
        return lastDaily == null ? 0 : lastDaily.getTime();
    }

    /**
     * 获取传入月当月的开始时间
     *
     * @param monthly
     * @return 如果传入参数值为空，返回空
     */
    public static Date getFirstMonthly(Date monthly) {
        if (null == monthly) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(monthly);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取传入月当月的开始时间
     *
     * @param monthly yyyy-MM格式
     * @return 如果传入参数值为空，返回空
     */
    public static Date getFirstMonthly(String monthly) {
        if (StringUtils.isEmpty(monthly)) {
            return null;
        }
        return getFirstMonthly(parse(monthly, PATTERN_YYYY_MM));
    }

    /**
     * 获取传入月当月的开始时间
     *
     * @param monthly yyyy-MM格式
     * @return 如果传入参数值为空，返回0
     */
    public static long getFirstMonthlyTime(String monthly) {
        if (StringUtils.isEmpty(monthly)) {
            return 0;
        }

        Date firstMonthly = getFirstMonthly(parse(monthly, PATTERN_YYYY_MM));
        return firstMonthly == null ? 0 : firstMonthly.getTime();
    }

    /**
     * 获取传入月当月的开始时间
     *
     * @param monthly
     * @return 如果传入参数值为空，返回0
     */
    public static long getFirstMonthlyTime(Date monthly) {
        Date firstMonthly = getFirstMonthly(monthly);
        return firstMonthly == null ? 0 : firstMonthly.getTime();
    }

    /**
     * 获取传入月当月的结束时间
     *
     * @param monthly
     * @return 如果传入参数值为空，返回null
     */
    public static Date getLastMonthly(Date monthly) {
        if (null == monthly) {
            return null;
        }

        Date firstMonthly = getFirstMonthly(monthly);
        if (null == firstMonthly) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(firstMonthly);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.MILLISECOND, -1);
        return calendar.getTime();
    }

    /**
     * 获取传入月当月的结束时间
     *
     * @param monthly yyyy-MM格式
     * @return 如果传入参数值为空，返回null
     */
    public static Date getLastMonthly(String monthly) {
        if (StringUtils.isEmpty(monthly)) {
            return null;
        }

        return getLastMonthly(parse(monthly, PATTERN_YYYY_MM));
    }

    /**
     * 获取传入月当月的结束时间
     *
     * @param monthly yyyy-MM格式
     * @return 如果传入参数值为空，返回0
     */
    public static long getLastMonthlyTime(String monthly) {
        if (StringUtils.isEmpty(monthly)) {
            return 0;
        }

        Date lastMonthly = getLastMonthly(parse(monthly, PATTERN_YYYY_MM));
        return lastMonthly == null ? 0 : lastMonthly.getTime();
    }

    /**
     * 获取传入月当月的结束时间
     *
     * @param monthly yyyy-MM格式
     * @return 如果传入参数值为空，返回0
     */
    public static long getLastMonthlyTime(Date monthly) {
        Date lastMonthly = getLastMonthly(monthly);
        return lastMonthly == null ? 0 : lastMonthly.getTime();
    }

    /**
     * 获取传入时间所在季度的第一天
     *
     * @param date
     * @return 传入时间所在季度的第一天
     */
    public static Date getFirstQuarter(Date date) {
        if (null == date) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(FIELD_DAY_OF_MONTH, 1);
        calendar.set(FIELD_HOUR_OF_DAY, 0);
        calendar.set(FIELD_MINUTE, 0);
        calendar.set(FIELD_SECOND, 0);
        calendar.set(FIELD_MILLISECOND, 0);
        int month = calendar.get(FIELD_MONTH);

        if (month < 3) {
            month = 0;
        } else if (month >= 3 && month < 6) {
            month = 3;
        } else if (month >= 6 && month < 9) {
            month = 6;
        } else {
            month = 9;
        }
        calendar.set(FIELD_MONTH, month);
        return calendar.getTime();
    }

    /**
     * 获取传入时间所在季度的第一天
     *
     * @param date
     * @return 如果传入参数值为空，返回0
     */
    public static long getFirstQuarterTime(Date date) {
        Date firstQuarter = getFirstQuarter(date);

        return null == firstQuarter ? 0 : firstQuarter.getTime();
    }

    /**
     * 获取传入时间所在季度的最后一天
     *
     * @param date
     * @return 传入时间所在季度的最后一天
     */
    public static Date getLastQuarter(Date date) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(FIELD_DAY_OF_MONTH, 1);
        calendar.set(FIELD_HOUR_OF_DAY, 0);
        calendar.set(FIELD_MINUTE, 0);
        calendar.set(FIELD_SECOND, 0);
        calendar.set(FIELD_MILLISECOND, 0);

        int month = calendar.get(FIELD_MONTH);
        if (month < 3) {
            month = 2;
        } else if (month >= 3 && month < 6) {
            month = 5;
        } else if (month >= 6 && month < 9) {
            month = 8;
        } else {
            month = 11;
        }
        calendar.set(FIELD_MONTH, month);
        calendar.add(FIELD_MONTH, 1);
        calendar.add(FIELD_MILLISECOND, -1);
        return calendar.getTime();
    }

    /**
     * 获取传入时间所在季度的第一天
     *
     * @param date
     * @return 如果传入参数值为空，返回0
     */
    public static long getLastQuarterTime(Date date) {
        Date lastQuarter = getLastQuarter(date);

        return null == lastQuarter ? 0 : lastQuarter.getTime();
    }

    public static void main(String[] args) {
        SimpleDateFormat instance = getInstance();
        System.out.println(instance.format(new Date()));
        System.out.println(format(new Date()));

        String ym = "2014-10";
        Date parseDate = parse(ym, PATTERN_YYYY_MM);
        System.out.println("parseDate:" + format(parseDate, PATTERN_DATE_TIME));

        Date currentDate = new Date();
        System.out.println("addNumWithType:" + format(addNumWithType(currentDate, FIELD_DATE, -10), PATTERN_DATE_TIME));

        // 日期
        String daily = "2015-08-06";
        System.out.println(format(getFirstDaily(daily), PATTERN_DATE_TIME));
        System.out.println(format(getLastDaily(daily), PATTERN_DATE_TIME));

        // 月份
        String monthly = "2015-08";
        System.out.println("getFirstMonthly:" + format(getFirstMonthly(monthly), PATTERN_DATE_TIME));
        System.out.println("getLastMonthly:" + format(getLastMonthly(monthly), PATTERN_DATE_TIME));

        // 季度
        Date quarter = new Date();
        System.out.println("getFirstQuarter:" + format(getFirstQuarter(quarter), PATTERN_DATE_TIME));
        System.out.println("getLastQuarter:" + format(getLastQuarter(quarter), PATTERN_DATE_TIME));

        long times = 1443715200000L;
        System.out.println(format(new Date(times), PATTERN_DATE_TIME));
    }
}
