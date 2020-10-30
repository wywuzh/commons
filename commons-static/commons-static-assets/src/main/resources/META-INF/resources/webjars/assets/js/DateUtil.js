/**
 * 自定义时间工具类
 *
 * @author wywuzh@163.com 2016-03-25
 * @version v1.0.0
 * @requires jQuery
 */
var DateUtil = {

    /**
     * 当前日期时间实例对象
     *
     * @returns {string}
     */
    currentDate: function () {
        return new Date();
    },
    /**
     * 当前时间。long型毫秒时间值
     *
     * @returns {number}
     */
    currentTime: function () {
        var date = this.currentDate();
        return date.getTime();
    },
    /**
     * 格式化时间
     *
     * @param time Long型时间数据
     * @param pattern 格式。支持格式：YYYY-MM-DD, YYYY-MM-DD HH24, YYYY-MM-DD HH24:MI, YYYY-MM-DD HH24:MI:SS
     * @returns {string}
     */
    format: function (time, pattern) {
        if (time == null || time == undefined || time == 0) {
            return "";
        }
        if (null == pattern || pattern == undefined || "" == pattern) {
            return "";
        }

        var date = new Date(time);
        var fullYear = date.getFullYear(),
            month = date.getMonth() + 1,
            day = date.getDate(),
            hour = date.getHours(),
            minute = date.getMinutes(),
            second = date.getSeconds();
        if (month < 10) {
            month = "0" + month;
        }
        if (day < 10) {
            day = "0" + day;
        }
        if (hour < 10) {
            hour = "0" + hour;
        }
        if (minute < 10) {
            minute = "0" + minute;
        }
        if (second < 10) {
            second = "0" + second;
        }

        var result = "";
        if (Pattern.PATTERN_YYYY == pattern || pattern.toUpperCase() == "YYYY") {
            result = fullYear;
        } else if (Pattern.PATTERN_YYYY_MM == pattern || pattern.toUpperCase() == "YYYY-MM") {
            result = fullYear + "-" + month;
        } else if (Pattern.PATTERN_DATE == pattern || pattern.toUpperCase() == "YYYY-MM-DD") {
            result = fullYear + "-" + month + "-" + day;
        } else if (Pattern.PATTERN_DATE_HH24 == pattern || pattern.toUpperCase() == "YYYY-MM-DD HH24") {
            result = fullYear + "-" + month + "-" + day + " " + hour;
        } else if (Pattern.PATTERN_DATE_HH24MI == pattern || pattern.toUpperCase() == "YYYY-MM-DD HH24:MI") {
            result = fullYear + "-" + month + "-" + day + " " + hour + ":" + minute;
        } else if (Pattern.PATTERN_DATE_TIME == pattern || pattern.toUpperCase() == "YYYY-MM-DD HH24:MI:SS") {
            result = fullYear + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
        } else if (Pattern.PATTERN_TIME == pattern || pattern.toUpperCase() == "HH24:MI:SS") {
            result = hour + ":" + minute + ":" + second;
        }
        return result;
    }
};

/**
 * 时间格式
 *
 * @type {{PATTERN_DATE: string, PATTERN_DATE_HH24: string, PATTERN_DATE_HH24MI: string, PATTERN_DATE_TIME: string, PATTERN_TIME: string}}
 */
var Pattern = {
    PATTERN_YYYY: "yyyy",
    PATTERN_YYYY_MM: "yyyy-MM",
    PATTERN_DATE: "YYYY-MM-DD",
    PATTERN_DATE_HH24: "YYYY-MM-DD HH24",
    PATTERN_DATE_HH24MI: "YYYY-MM-DD HH24:MI",
    PATTERN_DATE_TIME: "YYYY-MM-DD HH24:MI:SS",
    PATTERN_TIME: "HH24:MI:SS"
};