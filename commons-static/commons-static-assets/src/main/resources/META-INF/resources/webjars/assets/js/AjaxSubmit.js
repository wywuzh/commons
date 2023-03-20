/**
 * jquery.form
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a>
 * @version v2.4.5
 * @requires jquery
 */
var AjaxSubmit = {
    get: function (selector, url) {
        var deferred = $.Deferred();
        $(selector).ajaxSubmit({
            url: url,
            type: 'GET',
            dataType: 'json',
            async: true, // 是否异步请求
            success: function (data, status, xhr) {
                // 请求成功
                deferred.resolve(data, status, xhr);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR);
                console.log(textStatus);
                console.log(errorThrown);
                // 请求失败
                deferred.reject(jqXHR, textStatus, errorThrown);
            }
        });
        return deferred.promise();
    },
    post: function (selector, url, onSubmit) {
        var deferred = $.Deferred();
        $(selector).ajaxSubmit({
            url: url,
            type: 'POST', // 默认为post请求
            dataType: 'json',
            async: true, // 是否异步请求
            beforeSubmit: onSubmit,
            success: function (data, status, xhr) {
                // 请求成功
                deferred.resolve(data, status, xhr);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR);
                console.log(textStatus);
                console.log(errorThrown);
                // 请求失败
                deferred.reject(jqXHR, textStatus, errorThrown);
            }
        });
        return deferred.promise();
    },
    doRequest: function (selector, url, type) {
        var deferred = $.Deferred();
        $(selector).ajaxSubmit({
            url: url,
            type: type || 'POST', // 默认为post请求
            dataType: 'json',
            async: true, // 是否异步请求
            success: function (data, status, xhr) {
                // 请求成功
                deferred.resolve(data, status, xhr);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR);
                console.log(textStatus);
                console.log(errorThrown);
                // 请求失败
                deferred.reject(jqXHR, textStatus, errorThrown);
            }
        });
        return deferred.promise();
    }
};
