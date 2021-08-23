/**
 * String工具类
 *
 * @author wywuzh@163.com 2019-01-24
 * @version v1.0.0
 * @requires jQuery
 */
var StringUtils = {
    /**
     * 增加formatString功能
     *
     * 使用方法：formatString('字符串{0}字符串{1}字符串','第一个变量','第二个变量');
     *
     * @returns 格式化后的字符串
     */
    formatString: function (str) {
        for (var i = 0; i < arguments.length - 1; i++) {
            str = str.replace("{" + i + "}", arguments[i + 1]);
        }
        return str;
    },
    /**
     * 接收一个以逗号分割的字符串，返回List，list里每一项都是一个字符串
     *
     * @returns list
     */
    stringToList: function (value) {
        if (value != undefined && value != '') {
            var values = [];
            var t = value.split(',');
            for (var i = 0; i < t.length; i++) {
                values.push('' + t[i]);/* 避免他将ID当成数字 */
            }
            return values;
        } else {
            return [];
        }
    },
    /**
     * 字符拼接功能
     * 使用方法：join('字符串1','字符串2','字符串3');
     *
     * @returns {string}
     * @since v2.4.8
     */
    join: function () {
        var result = '';
        for (var i = 0; i < arguments.length; i++) {
            result += arguments[i];
        }
        return result;
    }
};