/**
* jQuery EasyUI 1.4.3
* Copyright (c) 2009-2015 www.jeasyui.com. All rights reserved.
*
* Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
* To use it on other terms please contact us at info@jeasyui.com
* http://www.jeasyui.com/license_commercial.php
*
* jQuery EasyUI datagrid 组件扩展
* jeasyui.extensions.datagrid.getColumnInfo.js
* 开发 流云
* 由 落阳 整理
* 最近更新：2015-12-02
*
* 依赖项：
*   1、jquery.jdirk.js
*
* Copyright (c) 2015 ChenJianwei personal All rights reserved.
*/
(function () {

    $.util.namespace("$.fn.datagrid.extensions");

    var _originalMethod = {};
    $.extend(_originalMethod, {
        getColumnFields: $.fn.datagrid.methods.getColumnFields
    });

    var getColumnFields = function (target, frozen) {
        var t = $(target);
        if (frozen == null || frozen == undefined) {
            return _originalMethod.getColumnFields.call(t, t, frozen);
        }
        return $.type(frozen) == "string"
            ? $.array.merge([],
                _originalMethod.getColumnFields.call(t, t, true),
                _originalMethod.getColumnFields.call(t, t, false))
            : _originalMethod.getColumnFields.call(t, t, frozen);
    };

    var getColumnOptions = function (target, frozen) {
        var t = $(target), fields = getColumnFields(target, frozen);
        return $.array.map(fields, function (val) { return t.datagrid("getColumnOption", val); });
    };

    var getNextColumnOption = function (target, field) {
        if (!field) {
            return undefined;
        }
        var fields = getColumnFields(target, "all");
        if (!fields || !fields.length) {
            return undefined;
        }
        var index = $.array.indexOf(fields, field);
        return index == -1 || index >= fields.length - 1
            ? undefined
            : $(target).datagrid("getColumnOption", fields[index + 1]);
    };

    var getPrevColumnOption = function (target, field) {
        if (!field) {
            return undefined;
        }
        var fields = getColumnFields(target, "all");
        if (!fields || !fields.length) {
            return undefined;
        }
        var index = $.array.indexOf(fields, field);
        return index < 1
            ? undefined
            : $(target).datagrid("getColumnOption", fields[index - 1]);
    };


    var methods = {

        //  重写 easyui-datagrid 的 getColumnFields 方法；获取 easyui-datagrid 所有列的 field 所组成的一个数组集合；参数 frozen 可以定义为如下格式：
        //      Boolean 类型值：如果是 true，则表示返回的结果集中包含 frozen(冻结)列，如果是 false 则表示返回的结果集中不包含 frozen(冻结)列；
        //      String 类型值：如果该参数定义为任意 String 类型值，则返回所有列信息(包括 frozen 冻结列和非冻结列)；
        //  返回值：如果 frozen 参数定义为 Boolean 且为 true，则返回所有 frozen(冻结) 列的 field 所构成的一个 Array 数组对象；
        //      如果 frozen 参数定义为 false，则返回所有非 frozen(非冻结) 列的 field 所构成的一个 Array 数组对象；
        //      如果 frozen 定义为任意的 String 类型值，则返回所有列的 field 所构成的一个 Array 数组对象。
        //  落阳注：重写该方法是为了使方法能返回“冻结列与非冻结列所构成的一个 Array 数组对象”。
        getColumnFields: function (jq, frozen) { return getColumnFields(jq[0], frozen); },

        //  扩展 easyui-datagrid 的自定义方法；获取 easyui-datagrid 所有列的 columnOption 所组成的一个数组集合；参数 frozen 可以定义为如下格式：
        //      Boolean 类型值：如果是 true，则表示返回的结果集中包含 frozen(冻结)列，如果是 false 则表示返回的结果集中不包含 frozen(冻结)列；
        //      String 类型值：如果该参数定义为任意 String 类型值，则返回所有列信息(包括 frozen 冻结列和非冻结列)；
        //  返回值：如果 frozen 参数定义为 Boolean 且为 true，则返回所有 frozen(冻结) 列的 columnOption 所构成的一个 Array 数组对象；
        //      如果 frozen 参数定义为 false，则返回所有非 frozen(非冻结) 列的 columnOption 所构成的一个 Array 数组对象；
        //      如果 frozen 定义为任意的 String 类型值，则返回所有列的 columnOption 所构成的一个 Array 数组对象。
        getColumnOptions: function (jq, frozen) { return getColumnOptions(jq[0], frozen); },

        //  扩展 easyui-datagrid 的自定义方法；获取指定列的下一格位置列的 列属性(columnOption) 信息；该方法的参数 field 表示指定列的 field 值。
        //  返回值：当前指定列的下一格位置的列的 列属性(columnOption) 信息。
        //      如果不存在指定的列，或者指定列的下一格位置没有其他列，则返回 undefined。
        getNextColumnOption: function (jq, field) { return getNextColumnOption(jq[0], field); },

        //  扩展 easyui-datagrid 的自定义方法；获取指定列的上一格位置列的 列属性(columnOption) 信息；该方法的参数 field 表示指定列的 field 值。
        //  返回值：当前指定列的上一格位置的列的 列属性(columnOption) 信息。
        //      如果不存在指定的列，或者指定列的上一格位置没有其他列，则返回 undefined。
        getPrevColumnOption: function (jq, field) { return getPrevColumnOption(jq[0], field); }
    };

    var defaults = {

    };

    if ($.fn.datagrid.extensions.defaults) {
        $.extend($.fn.datagrid.extensions.defaults, defaults);
    } else {
        $.fn.datagrid.extensions.defaults = defaults;
    }

    if ($.fn.datagrid.extensions.methods) {
        $.extend($.fn.datagrid.extensions.methods, methods);
    } else {
        $.fn.datagrid.extensions.methods = methods;
    }

    $.extend($.fn.datagrid.defaults, defaults);
    $.extend($.fn.datagrid.methods, methods);

})(jQuery);