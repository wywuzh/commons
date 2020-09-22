"use strict";
layui.define(["layer"], function (exprots) {
    var $ = layui.jquery;

    /**
     * ajax请求
     */
    var AjaxRequest = {
        /**
         * 同步请求数据
         *
         * @param url 请求地址
         * @param requestParam 请求参数
         * @param successCallback 请求成功回调函数
         */
        getJSONForSync: function (url, requestParam, successCallback) {
            $.ajax({
                url: url,
                type: 'GET',
                data: requestParam,
                dataType: 'json',
                cache: false,
                async: false,// 同步请求
                success: function (data) {
                    if (successCallback) {
                        successCallback(data);
                    }
                },
                error: function () {
                }
            });
        },
        /**
         * 异步请求数据
         *
         * @param url 请求地址
         * @param requestParam 请求参数
         * @param successCallback 请求成功回调函数
         */
        getJSONForASync: function (url, requestParam, successCallback) {
            $.ajax({
                url: url,
                type: 'GET',
                data: requestParam,
                dataType: 'json',
                cache: false,
                async: true,// 异步请求
                success: function (data) {
                    if (successCallback) {
                        successCallback(data);
                    }
                },
                error: function () {
                }
            });
        },
        /**
         * 同步请求数据
         *
         * @param url 请求地址
         * @param requestParam 请求参数
         * @param successCallback 请求成功回调函数
         * @param errorCallback 请求失败回调函数
         */
        postForSync: function (url, requestParam, successCallback, errorCallback) {
            $.ajax({
                url: url,
                type: 'POST',
                data: requestParam,
                dataType: 'json',
                cache: false,
                async: false,// 同步请求
                success: function (data) {
                    if (successCallback) {
                        successCallback(data);
                    }
                },
                error: function (data) {
                    if (errorCallback) {
                        errorCallback(data);
                    }
                }
            });
        },
        /**
         * 同步请求数据
         *
         * @param url 请求地址
         * @param requestParam 请求参数
         * @param successCallback 请求成功回调函数
         * @param errorCallback 请求失败回调函数
         */
        postForASync: function (url, requestParam, successCallback, errorCallback) {
            $.ajax({
                url: url,
                type: 'POST',
                data: requestParam,
                dataType: 'json',
                cache: false,
                async: true,// 异步请求
                success: function (data) {
                    if (successCallback) {
                        successCallback(data);
                    }
                },
                error: function (data) {
                    if (errorCallback) {
                        errorCallback(data);
                    }
                }
            });
        }
    };

    exprots("AjaxRequest", AjaxRequest);
});
