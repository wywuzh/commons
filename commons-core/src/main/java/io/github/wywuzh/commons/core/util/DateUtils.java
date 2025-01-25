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
package io.github.wywuzh.commons.core.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.wywuzh.commons.core.common.Constants;
import io.github.wywuzh.commons.core.math.CalculationUtils;

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
public class DateUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

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
        return getInstance(PATTERN_DATE_TIME);
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
     * @param pattern 时间格式
     * @return
     */
    public static SimpleDateFormat getInstance(String pattern) {
        Assert.notBlank(pattern, "[Assertion failed] - the pattern argument must not be null");

        SimpleDateFormat dateFormat = threadLocal.get();
        dateFormat.applyPattern(pattern);
        return dateFormat;
    }

    /**
     * 将传入时间格式化成一个字符串，默认采用“yyyy-MM-dd HH:mm:ss”格式
     *
     * @param date 日期
     * @return 返回“yyyy-MM-dd HH:mm:ss”格式字符串
     */
    public static String format(Date date) {
        Assert.notNull(date, "[Assertion failed] - the date argument must not be null");

        return getInstance(PATTERN_DATE_TIME).format(date);
    }

    /**
     * 将传入时间格式化成一个字符串
     *
     * <pre>
     *  返回pattern格式字符串，如果pattern格式传入为空，默认采用“yyyy-MM-dd HH:mm:ss”格式；
     * </pre>
     *
     * @param date    日期
     * @param pattern 时间格式
     * @return 返回“yyyy-MM-dd HH:mm:ss”格式字符串，如果传入时间为空，返回null
     */
    public static String format(Date date, String pattern) {
        Assert.notNull(date, "[Assertion failed] - the date argument must not be null");
        Assert.notBlank(pattern, "[Assertion failed] - the pattern argument must not be null");

        return getInstance(pattern).format(date);
    }

    /**
     * 将传入的时间字符串解析成java.util.Date时间格式，默认以“yyyy-MM-dd HH:mm:ss”格式进行解析
     *
     * @param parseDate 时间字符串
     * @return
     */
    public static Date parse(String parseDate) {
        return parse(parseDate, PATTERN_DATE_TIME, true);
    }

    /**
     * 将传入的时间字符串解析成java.util.Date时间格式
     *
     * @param parseDate 时间字符串
     * @param pattern   时间格式
     * @return
     */
    public static Date parse(String parseDate, String pattern) {
        return parse(parseDate, pattern, true);
    }

    /**
     * 将传入的时间字符串解析成java.util.Date时间格式
     *
     * @param parseDate 时间字符串
     * @param pattern   时间格式
     * @param quietly   是否隐藏解析失败时的异常信息。true时不抛出异常，false时会把异常信息封装为RuntimeException运行时异常抛出
     * @return
     * @since v2.7.0
     */
    public static Date parse(String parseDate, String pattern, boolean quietly) {
        Assert.notBlank(parseDate, "[Assertion failed] - the parseDate argument must not be null");
        Assert.notBlank(pattern, "[Assertion failed] - the pattern argument must not be null");

        Date date = null;
        try {
            date = getInstance(pattern).parse(parseDate);
        } catch (ParseException e) {
            LOGGER.error("parseDate={}, pattern={} 解析失败：", parseDate, pattern, e);
            if (!quietly) {
                throw new RuntimeException(e.getMessage(), e);
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
     * @see Calendar#YEAR
     * @see Calendar#MONTH
     * @see Calendar#DATE
     * @see Calendar#DAY_OF_MONTH
     * @see Calendar#HOUR
     * @see Calendar#MINUTE
     * @see Calendar#SECOND
     */
    public static Date addNumWithType(Date date, int fieldType, int num) {
        Assert.notNull(date, "[Assertion failed] - the date argument must not be null");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(fieldType, num);
        return calendar.getTime();
    }

    /**
     * 获取传入日期当天的开始时间
     *
     * @param daily 日期
     * @return
     */
    public static Date getFirstDaily(Date daily) {
        Assert.notNull(daily, "[Assertion failed] - the daily argument must not be null");

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
     * @param daily 日期，yyyy-MM-dd格式
     * @return
     */
    public static Date getFirstDaily(String daily) {
        Assert.notBlank(daily, "[Assertion failed] - the daily argument must not be null");

        Date parse = parse(daily, PATTERN_DATE);
        if (parse == null) {
            throw new IllegalArgumentException("数据格式有误，必须是yyyy-MM-dd格式");
        }

        return getFirstDaily(parse);
    }

    /**
     * 获取传入日期当天的开始时间
     *
     * @param daily 日期，yyyy-MM-dd格式
     * @return 如果传入参数值为空，返回0
     */
    public static long getFirstDailyTime(String daily) {
        Assert.notBlank(daily, "[Assertion failed] - the daily argument must not be null");

        Date parse = parse(daily, PATTERN_DATE);
        if (parse == null) {
            throw new IllegalArgumentException("数据格式有误，必须是yyyy-MM-dd格式");
        }
        Date firstDaily = getFirstDaily(parse);
        return (null == firstDaily) ? 0 : firstDaily.getTime();
    }

    /**
     * 获取传入日期当天的开始时间
     *
     * @param daily 日期
     * @return 如果传入参数值为空，返回0
     */
    public static long getFirstDailyTime(Date daily) {
        Assert.notNull(daily, "[Assertion failed] - the daily argument must not be null");

        Date firstDaily = getFirstDaily(daily);
        return (null == firstDaily) ? 0 : firstDaily.getTime();
    }

    /**
     * 获取传入日期当天的结束时间
     *
     * @param daily 日期
     * @return
     */
    public static Date getLastDaily(Date daily) {
        Assert.notNull(daily, "[Assertion failed] - the daily argument must not be null");

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
     * @param daily 日期，yyyy-MM-dd格式
     * @return
     */
    public static Date getLastDaily(String daily) {
        Assert.notBlank(daily, "[Assertion failed] - the daily argument must be blank");

        Date parse = parse(daily, PATTERN_DATE);
        if (parse == null) {
            throw new IllegalArgumentException("数据格式有误，必须是yyyy-MM-dd格式");
        }
        return getLastDaily(parse);
    }

    /**
     * 获取传入日期当天的结束时间
     *
     * @param daily 日期，yyyy-MM-dd格式
     * @return 如果传入参数值为空，返回0
     */
    public static long getLastDailyTime(String daily) {
        Assert.notBlank(daily, "[Assertion failed] - the daily argument must be blank");

        Date parse = parse(daily, PATTERN_DATE);
        if (parse == null) {
            throw new IllegalArgumentException("数据格式有误，必须是yyyy-MM-dd格式");
        }
        Date lastDaily = getLastDaily(parse);
        return lastDaily == null ? 0 : lastDaily.getTime();
    }

    /**
     * 获取传入日期当天的结束时间
     *
     * @param daily 日期
     * @return
     */
    public static long getLastDailyTime(Date daily) {
        Assert.notNull(daily, "[Assertion failed] - the daily argument must not be null");

        Date lastDaily = getLastDaily(daily);
        return lastDaily == null ? 0 : lastDaily.getTime();
    }

    /**
     * 获取传入月当月的开始时间
     *
     * @param monthly 月份
     * @return 如果传入参数值为空，返回空
     */
    public static Date getFirstMonthly(Date monthly) {
        Assert.notNull(monthly, "[Assertion failed] - the monthly argument must not be null");

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
     * @param monthly 月份，yyyy-MM格式
     * @return 如果传入参数值为空，返回空
     */
    public static Date getFirstMonthly(String monthly) {
        Assert.notBlank(monthly, "[Assertion failed] - the monthly argument must be blank");

        Date parse = parse(monthly, PATTERN_YYYY_MM);
        if (parse == null) {
            throw new IllegalArgumentException("数据格式有误，必须是yyyy-MM-dd格式");
        }
        return getFirstMonthly(parse);
    }

    /**
     * 获取传入月当月的开始时间
     *
     * @param monthly 年月，yyyy-MM格式
     * @return 如果传入参数值为空，返回0
     */
    public static long getFirstMonthlyTime(String monthly) {
        Assert.notBlank(monthly, "[Assertion failed] - the monthly argument must be blank");

        Date parse = parse(monthly, PATTERN_YYYY_MM);
        if (parse == null) {
            throw new IllegalArgumentException("数据格式有误，必须是yyyy-MM-dd格式");
        }
        Date firstMonthly = getFirstMonthly(parse);
        return firstMonthly == null ? 0 : firstMonthly.getTime();
    }

    /**
     * 获取传入月当月的开始时间
     *
     * @param monthly 年月
     * @return 如果传入参数值为空，返回0
     */
    public static long getFirstMonthlyTime(Date monthly) {
        Assert.notNull(monthly, "[Assertion failed] - the monthly argument must not be null");

        Date firstMonthly = getFirstMonthly(monthly);
        return firstMonthly == null ? 0 : firstMonthly.getTime();
    }

    /**
     * 获取传入月当月的结束时间
     *
     * @param monthly 年月
     * @return 如果传入参数值为空，返回null
     */
    public static Date getLastMonthly(Date monthly) {
        Assert.notNull(monthly, "[Assertion failed] - the monthly argument must not be null");

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
     * @param monthly 年月，yyyy-MM格式
     * @return 如果传入参数值为空，返回null
     */
    public static Date getLastMonthly(String monthly) {
        Assert.notBlank(monthly, "[Assertion failed] - the monthly argument must be blank");

        Date parse = parse(monthly, PATTERN_YYYY_MM);
        if (parse == null) {
            throw new IllegalArgumentException("数据格式有误，必须是yyyy-MM格式");
        }
        return getLastMonthly(parse);
    }

    /**
     * 获取传入月当月的结束时间
     *
     * @param monthly 年月，yyyy-MM格式
     * @return 如果传入参数值为空，返回0
     */
    public static long getLastMonthlyTime(String monthly) {
        Assert.notBlank(monthly, "[Assertion failed] - the monthly argument must be blank");

        Date parse = parse(monthly, PATTERN_YYYY_MM);
        if (parse == null) {
            throw new IllegalArgumentException("数据格式有误，必须是yyyy-MM格式");
        }
        Date lastMonthly = getLastMonthly(parse);
        return lastMonthly == null ? 0 : lastMonthly.getTime();
    }

    /**
     * 获取传入月当月的结束时间
     *
     * @param monthly 年月
     * @return 如果传入参数值为空，返回0
     */
    public static long getLastMonthlyTime(Date monthly) {
        Assert.notNull(monthly, "[Assertion failed] - the monthly argument must not be null");

        Date lastMonthly = getLastMonthly(monthly);
        return lastMonthly == null ? 0 : lastMonthly.getTime();
    }

    /**
     * 获取传入时间所在季度的第一天
     *
     * @param quarterly 季度
     * @return 传入时间所在季度的第一天
     */
    public static Date getFirstQuarter(Date quarterly) {
        Assert.notNull(quarterly, "[Assertion failed] - the date argument must not be null");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(quarterly);
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
     * @param quarterly 季度
     * @return 如果传入参数值为空，返回0
     */
    public static long getFirstQuarterTime(Date quarterly) {
        Assert.notNull(quarterly, "[Assertion failed] - the date argument must not be null");

        Date firstQuarter = getFirstQuarter(quarterly);

        return null == firstQuarter ? 0 : firstQuarter.getTime();
    }

    /**
     * 获取传入时间所在季度的最后一天
     *
     * @param quarterly 季度
     * @return 传入时间所在季度的最后一天
     */
    public static Date getLastQuarter(Date quarterly) {
        Assert.notNull(quarterly, "[Assertion failed] - the date argument must not be null");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(quarterly);
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
     * @param quarterly 季度
     * @return 如果传入参数值为空，返回0
     */
    public static long getLastQuarterTime(Date quarterly) {
        Assert.notNull(quarterly, "[Assertion failed] - the date argument must not be null");

        Date lastQuarter = getLastQuarter(quarterly);

        return null == lastQuarter ? 0 : lastQuarter.getTime();
    }

    /**
     * 获取传入时间的所在年月
     *
     * @param date 时间
     * @return 传入时间的所在年月
     * @since v2.7.8
     */
    public static Date getCurrentYearMonth(Date date) {
        Assert.notNull(date, "[Assertion failed] - the date argument must not be null");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(FIELD_DAY_OF_MONTH, 0);
        calendar.set(FIELD_HOUR_OF_DAY, 0);
        calendar.set(FIELD_MINUTE, 0);
        calendar.set(FIELD_SECOND, 0);
        calendar.set(FIELD_MILLISECOND, 0);

        return calendar.getTime();
    }

    /**
     * 获取传入时间的上一年月
     *
     * @param date 时间
     * @return 传入时间的\上一年月
     * @since v2.7.8
     */
    public static Date getPreYearMonth(Date date) {
        Assert.notNull(date, "[Assertion failed] - the date argument must not be null");

        return addNumWithType(getCurrentYearMonth(date), FIELD_MONTH, -1);
    }

    /**
     * 获取传入时间的下一年月
     *
     * @param date 时间
     * @return 传入时间的下一年月
     * @since v2.7.8
     */
    public static Date getNextYearMonth(Date date) {
        Assert.notNull(date, "[Assertion failed] - the date argument must not be null");

        return addNumWithType(getCurrentYearMonth(date), FIELD_MONTH, 1);
    }

    /**
     * 获取上一年月
     *
     * @param yearMonth 年月，yyyy-MM格式
     * @return 上一年月
     * @since v2.5.2
     */
    public static String getPreYearMonth(String yearMonth) {
        Assert.notBlank(yearMonth, "[Assertion failed] - the yearMonth argument must be blank");

        Date yearMonthForDate = DateUtils.parse(yearMonth, DateUtils.PATTERN_YYYY_MM);
        Assert.notNull(yearMonthForDate, "数据格式有误，必须是yyyy-MM格式");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(yearMonthForDate);
        calendar.add(Calendar.MONTH, -1);

        int fullYear = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        if (month < 10) {
            return fullYear + Constants.SEPARATE_CROSS_BAR + "0" + month;
        }
        return fullYear + Constants.SEPARATE_CROSS_BAR + month;
    }

    /**
     * 获取下一年月
     *
     * @param yearMonth 年月，yyyy-MM格式
     * @return 下一年月
     * @since v2.5.2
     */
    public static String getNextYearMonth(String yearMonth) {
        Assert.notBlank(yearMonth, "[Assertion failed] - the yearMonth argument must be blank");

        Date yearMonthForDate = DateUtils.parse(yearMonth, DateUtils.PATTERN_YYYY_MM);
        Assert.notNull(yearMonthForDate, "数据格式有误，必须是yyyy-MM格式");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(yearMonthForDate);
        calendar.add(Calendar.MONTH, 1);

        int fullYear = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        if (month < 10) {
            return fullYear + Constants.SEPARATE_CROSS_BAR + "0" + month;
        }
        return fullYear + Constants.SEPARATE_CROSS_BAR + month;
    }

    /**
     * 计算两个日期的天数间隔
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @returns 天数间隔
     * @since v2.5.2
     */
    public static Integer getDayInterval(Date startDate, Date endDate) {
        Assert.notNull(startDate, "[Assertion failed] - the startDate argument must not be null");
        Assert.notNull(endDate, "[Assertion failed] - the endDate argument must not be null");

        return getDayInterval(startDate, endDate, true);
    }

    /**
     * 计算两个日期的天数间隔
     *
     * @param startDate         开始日期
     * @param endDate           结束日期
     * @param includeCurrentDay 是否包含当前天：false=否，true=是(默认)
     * @returns 天数间隔
     * @since v2.5.2
     */
    public static Integer getDayInterval(Date startDate, Date endDate, boolean includeCurrentDay) {
        Assert.notNull(startDate, "[Assertion failed] - the startDate argument must not be null");
        Assert.notNull(endDate, "[Assertion failed] - the endDate argument must not be null");

        int dayInterval = CalculationUtils.div(new BigDecimal(endDate.getTime() - startDate.getTime()), new BigDecimal(1000 * 3600 * 24)).abs().intValue();
        if (includeCurrentDay) {
            return dayInterval + 1;
        }
        return dayInterval;
    }

    /**
     * 计算两个日期的月份间隔
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @returns 月份间隔
     * @since v2.5.2
     */
    public static Integer getMonthInterval(Date startDate, Date endDate) {
        Assert.notNull(startDate, "[Assertion failed] - the startDate argument must not be null");
        Assert.notNull(endDate, "[Assertion failed] - the endDate argument must not be null");

        return getMonthInterval(startDate, endDate, true);
    }

    /**
     * 计算两个日期的月份间隔
     *
     * @param startDate           开始日期
     * @param endDate             结束日期
     * @param includeCurrentMonth 是否包含当前月份：false=否，true=是(默认)
     * @returns 月份间隔
     * @since v2.5.2
     */
    public static Integer getMonthInterval(Date startDate, Date endDate, boolean includeCurrentMonth) {
        Assert.notNull(startDate, "[Assertion failed] - the startDate argument must not be null");
        Assert.notNull(endDate, "[Assertion failed] - the endDate argument must not be null");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        // 开始日期：月份数
        int monthForStart = calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH);
        // 结束日期：月份数
        calendar.setTime(endDate);
        int monthForEnd = calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH);

        int monthInterval = Math.abs(monthForEnd - monthForStart);
        if (includeCurrentMonth) {
            return monthInterval + 1;
        }
        return monthInterval;
    }

    /**
     * 计算两个日期的月份间隔
     *
     * @param startYearMonth    开始年月：yyyy-MM格式
     * @param endYearMonth      结束年月：yyyy-MM格式
     * @param includeCurrentDay 是否包含当前月份：0=否，1=是。默认为0
     * @returns 月份间隔
     * @since v2.5.2
     */
    public static Integer getMonthInterval(String startYearMonth, String endYearMonth, boolean includeCurrentDay) {
        Assert.notBlank(startYearMonth, "[Assertion failed] - the startYearMonth argument must not be null");
        Assert.notBlank(endYearMonth, "[Assertion failed] - the endYearMonth argument must not be null");

        Date startDate = DateUtils.parse(startYearMonth, DateUtils.PATTERN_YYYY_MM);
        Assert.notNull(startDate, "数据格式有误，必须是yyyy-MM格式");
        Date endDate = DateUtils.parse(endYearMonth, DateUtils.PATTERN_YYYY_MM);
        Assert.notNull(endDate, "数据格式有误，必须是yyyy-MM格式");

        return DateUtils.getMonthInterval(startDate, endDate, includeCurrentDay);
    }

    /**
     * 获取指定年月下的所有日期
     *
     * @param yearMonth 年月：yyyy-MM格式
     * @return 年月下的所有日期
     * @since v2.7.0
     */
    public static List<String> getMonthFullDay(String yearMonth) {
        Assert.notBlank(yearMonth, "[Assertion failed] - the yearMonth argument must not be null");

        Date yearMonthDate = DateUtils.parse(yearMonth, DateUtils.PATTERN_YYYY_MM);
        Assert.notNull(yearMonthDate, "数据格式有误，必须是yyyy-MM格式");
        return getMonthFullDay(yearMonthDate);
    }

    /**
     * 获取指定年月下的所有日期
     *
     * @param yearMonth 年月
     * @return 年月下的所有日期
     * @since v2.7.0
     */
    public static List<String> getMonthFullDay(Date yearMonth) {
        Assert.notNull(yearMonth, "[Assertion failed] - the yearMonth argument must not be null");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(yearMonth);
        // 将日期设置为该月的1号
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        // 该月的最大日期
        int maximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        List<String> monthFullDays = new LinkedList<>();
        for (int i = 1; i <= maximum; i++) {
            monthFullDays.add(DateUtils.format(calendar.getTime(), DateUtils.PATTERN_DATE));

            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return monthFullDays;
    }

    /**
     * 获取指定年月下的所有日期
     *
     * @param year  年
     * @param month 月，从1开始
     * @return 年月下的所有日期
     * @since v2.7.0
     */
    public static List<String> getMonthFullDay(int year, int month) {
        Assert.state((year > 0), "参数year必须大于0");
        Assert.state((month >= 1 && month <= 12), "参数month必须在1至12之间");

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        // 月从0开始
        calendar.set(Calendar.MONTH, month - 1);
        // 将日期设置为该月的1号
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        // 该月的最大日期
        int maximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        List<String> monthFullDays = new LinkedList<>();
        for (int i = 1; i <= maximum; i++) {
            monthFullDays.add(DateUtils.format(calendar.getTime(), DateUtils.PATTERN_DATE));

            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return monthFullDays;
    }

}
