/**
 * ajax请求
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a>
 * @version v2.4.5
 * @requires jquery
 */

/**
 * ajax请求
 */
var AjaxRequest = {
    /**
     * GET同步请求数据
     *
     * @param url 请求地址
     * @param requestParam 请求参数
     * @param successCallback 请求成功回调函数
     */
    getJSONForSync: function (url, requestParam, successCallback, errorCallback) {
        $.ajax({
            url: url,
            type: 'GET',
            data: requestParam,
            dataType: 'json',
            cache: false,
            async: false, // 是否发送异步请求，默认为true
            success: function (data) {
                if (successCallback) {
                    successCallback(data);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (errorCallback) {
                    errorCallback(XMLHttpRequest, textStatus, errorThrown);
                }
            }
        });
    },
    /**
     * GET异步请求数据
     *
     * @param url 请求地址
     * @param requestParam 请求参数
     * @param successCallback 请求成功回调函数
     */
    getJSONForASync: function (url, requestParam, successCallback, errorCallback) {
        $.ajax({
            url: url,
            type: 'GET',
            data: requestParam,
            dataType: 'json',
            cache: false,
            async: true, // 是否发送异步请求，默认为true
            success: function (data) {
                if (successCallback) {
                    successCallback(data);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (errorCallback) {
                    errorCallback(XMLHttpRequest, textStatus, errorThrown);
                }
            }
        });
    },
    /**
     * POST同步请求数据
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
            async: false, // 是否发送异步请求，默认为true
            success: function (data) {
                if (successCallback) {
                    successCallback(data);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (errorCallback) {
                    errorCallback(XMLHttpRequest, textStatus, errorThrown);
                }
            }
        });
    },
    /**
     * POST异步请求数据
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
            async: true, // 是否发送异步请求，默认为true
            success: function (data) {
                if (successCallback) {
                    successCallback(data);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (errorCallback) {
                    errorCallback(XMLHttpRequest, textStatus, errorThrown);
                }
            }
        });
    },
    /**
     * 发送请求
     * @param options 请求参数
     */
    doRequest: function (options) {
        options = options || {};
        var defaultOptions = {
            type: 'POST',
            dataType: 'json',
            cache: false,
            async: true, // 是否发送异步请求，默认为true
        };
        // 将options请求对象合并到defaultOptions对象中
        $.extend(defaultOptions, options);
        // 执行请求
        $.ajax(defaultOptions);
    },
};
