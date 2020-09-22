"use strict";
layui.define(["layer"], function (exprots) {
    var $ = layui.jquery;
    /**
     * Cookie管理
     * @author 伍章红 2018-10-01
     * @requires jQuery
     */
    var Cookie = {
        /**
         * 设置cookie
         *
         * @param name cookie的名字
         * @param value cookie的值
         */
        setCookie: function (name, value) {//两个参数，一个是cookie的名字，一个是值
            var Days = 30; //此 cookie 将被保存 30 天
            var exp = new Date();
            exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
            document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
        },
        /**
         * 获取cookie的值
         *
         * @param name cookie的名字
         * @returns {*}
         */
        getCookie: function (name) { //取cookies函数
            var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
            if (arr != null) {
                return unescape(arr[2]);
            }
            return null;
        }
    };

    exprots("Cookie", Cookie);
});
