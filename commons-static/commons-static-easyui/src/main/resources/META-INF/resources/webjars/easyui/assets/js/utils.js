/**
 * easyui工具
 * @author 伍章红 2020-08-25
 * @requires jQuery, jQuery-EasyUI
 */

/**
 * easyui datagrid组件工具
 */
var Datagrid = {
    /**
     * 显示内容
     * @param value 字段值
     * @param row 行
     * @param index 索引
     * @returns {string}
     */
    showContent: function (value, row, index) {
        if (value) {
            return '<span title="' + value + '">' + value + '</span>';
        }
        return '';
    },
    /**
     * 日期格式化
     * @param value 日期数值，毫秒值
     * @param row 行
     * @param index 索引
     */
    formatDate: function (value, row, index) {
        if (value) {
            return DateUtil.format(new (value), Pattern.PATTERN_DATE);
        }
        return '';
    },
    /**
     * 时间格式化
     * @param value 时间数值，毫秒值
     * @param row 行
     * @param index 索引
     */
    formatTime: function (value, row, index) {
        if (value) {
            return DateUtil.format(new (value), Pattern.PATTERN_TIME);
        }
        return '';
    },
    /**
     * 日期时间格式化
     * @param value 日期时间数值，毫秒值
     * @param row 行
     * @param index 索引
     */
    formatDateTime: function (value, row, index) {
        if (value) {
            return DateUtil.format(new (value), Pattern.PATTERN_DATE_TIME);
        }
        return '';
    },
    /**
     * 年月格式化
     * @param value 年月数值，毫秒值
     * @param row 行
     * @param index 索引
     */
    formatYearMonth: function (value, row, index) {
        if (value) {
            return DateUtil.format(new (value), Pattern.PATTERN_YYYY_MM);
        }
        return '';
    },
    /**
     * 将数值格式化成金额
     * @param value 金额数值
     * @param row 行
     * @param index 索引
     * @requires jquery.number.js
     */
    formatAmount: function (value, row, index) {
        if (value == null || value == '' || value == undefined) {
            value = 0;
        }
        return $.number(value, 2);
    },
    /**
     * 将数值格式化成率，保留4位小数
     * @param value 率值
     * @returns {null|jQuery}
     * @requires jquery.number.js
     */
    formatRate: function (value) {
        if (value == null || value == undefined) {
            return null;
        }
        return $.number(value, 4);
    },
    /**
     * 处理datagrid数据为空时，title类展示不全的问题
     * @param data
     */
    loadSuccessForTitle: function (data) {
        if (data.total === 0) {
            // 处理没有数据的时候部分字段过多的表格字段显示不全
            var dc = $(this).data('datagrid').dc;
            var header2Row = dc.header2.find('tr.datagrid-header-row');
            dc.body2.find('table').append(header2Row.clone().css({"visibility": "hidden"}));
        }
        // 底部footer行添加“合计”
        // 注：该名称只有在设置了 “showFooter: true” 才会生效
        $('.datagrid-footer .datagrid-cell-rownumber').text('合计');
    }
};

/**
 * 重写datagrid默认值
 */
$.fn.datagrid.defaults = $.extend({}, $.fn.datagrid.defaults, {
    async: false, // 是否异步请求，默认为true
    border: true,
    striped: true, // 交替显示行背景
    nowrap: true, // 当数据长度超出列宽时将会自动截取
    idFiled: "id",
    loadMsg: "数据加载中，请稍候……",
    multiSort: true, // 是否允许多列排序
    rownumbers: true,
    showFooter: true,
    singleSelect: false, // 是否只允许选择一行
    checkOnSelect: true,
    selectOnCheck: true,
    pagination: true, // 在数据表格底部显示分页工具栏
    pageNumber: 1, // 初始化分页码（页码从1开始）
    pageSize: 20, // 初始化每页显示数据量
    pageList: [10, 20, 50, 100, 200, 300, 400, 500], // 初始化每页记录数列表
    onLoadSuccess: Datagrid.loadSuccessForTitle
});

/**
 * 初始化datagrid组件
 * @param selector 选择器，必填
 * @param options datagrid组件参数，可选
 */
function initDatagrid(selector, options) {
    options = options || {};
    // 默认属性值
    var defaultOptions = {
        method: "post",
        frozenColumns: [[
            {field: 'chex', checkbox: true, resizable: true}
        ]],
        fitColumns: false, // 自动使列适应表格宽度以防止出现水平滚动
        fit: true,
        async: false, // 是否异步请求，默认为true
        border: true,
        striped: true, // 交替显示行背景
        nowrap: true, // 当数据长度超出列宽时将会自动截取
        idFiled: "id",
        loadMsg: "数据加载中，请稍候……",
        multiSort: true, // 是否允许多列排序
        rownumbers: true,
        showFooter: true,
        singleSelect: false, // 是否只允许选择一行，默认可多选
        checkOnSelect: true,
        selectOnCheck: true,
        pagination: true, // 在数据表格底部显示分页工具栏
        pageNumber: 1, // 初始化分页码（页码从1开始）
        pageSize: 20, // 初始化每页显示数据量
        pageList: [10, 20, 50, 100, 200, 300, 400, 500], // 初始化每页记录数列表
        onLoadSuccess: Datagrid.loadSuccessForTitle
    };
    // 将options属性值合并到defaultOptions属性中
    $.extend(defaultOptions, options);
    $(selector).datagrid(defaultOptions);
}


/**
 * 下拉框
 */
var Combobox = {
    /**
     * 下拉框展示弹窗事件
     */
    showPanel: function () {
        var data = $(this).combobox('getData');
        // 下拉框数据长度小于10条时，高度自适应，大于10条时最高200，防止高度占满整个屏幕
        if (data.length < 10) {
            $(this).combobox('panel').height("auto");
        } else {
            $(this).combobox('panel').height(200);
        }
        // 重新选中值，防止多次打开窗口时，之前选中的值失效
        var selected = $(this).combobox('getValues');
        // 重新加载数据
        $(this).combobox('loadData', data);
        if (selected) {
            $(this).combobox('setValues', selected);
        }
    },
    hidePanelForSingle: function () {
        var _options = $(this).combobox('options');
        var _data = $(this).combobox('getData');
        /* 下拉框所有选项 */
        var _value = $(this).combobox('getValue');
        /* 用户输入的值 */
        var _b = false;
        /* 标识是否在下拉列表中找到了用户输入的字符 */
        for (var i = 0; i < _data.length; i++) {
            if (_data[i][_options.valueField] == _value) {
                _b = true;
                break;
            }
        }
        if (!_b) {
            $(this).combobox('setValue', '');
            return false;
        }
        return true;
    },
    hidePanelForMultiple: function () {
        var _options = $(this).combobox('options');
        var _data = $(this).combobox('getData');
        // 下拉框选择项
        var values = $(this).combobox('getValues');
        if (values.length > 0) {
            var array = [];
            if (values[0].indexOf(",") == -1) {
                $.each(values, function (index, element) {
                    for (var i = 0; i < _data.length; i++) {
                        if (_data[i][_options.valueField] == element) {
                            array.push(element);
                        }
                    }
                });
            } else {
                // 解决开头或者中间存在逗号导致多选下拉出现异常的问题
                // 去掉第一个逗号
                // var text = values[0].replace(",", "");
                // 使用正则，将多个相邻的逗号替换成一个逗号，相当于Java的replaceAll
                var reg = new RegExp(",", "g");
                var text = values[0].replace(reg, ",");
                // 分割文本值
                text = text.split(",");
                $.each(text, function (index, element) {
                    if (element != null && element != '' && element != undefined) {
                        for (var i = 0; i < _data.length; i++) {
                            // 使用下拉框的text文本值进行匹配，匹配上则拿到对应的value值
                            if (_data[i][_options.textField] == element) {
                                array.push(_data[i][_options.valueField]);
                            }
                        }
                    }
                });
            }
            $(this).combobox('setValues', array);
        } else {
            $(this).combobox('setValues', '');
        }
        return true;
    },
    /**
     * combobox下拉框加载数据
     * @param selector 选择器
     * @param data 数据
     */
    loadData: function (selector, data) {
        // 根据数据量动态调整下拉框高度
        if (data.length < 10) {
            $(selector).combobox('panel').height("auto");
        } else {
            $(selector).combobox('panel').height(200);
        }
        // 重新加载数据
        $(selector).combobox('loadData', data);
    }
};

/**
 * 重写combobox默认值
 */
$.fn.combobox.defaults = $.extend({}, $.fn.combobox.defaults, {
    panelHeight: "auto",
    onShowPanel: Combobox.showPanel,
    onLoadSuccess: function () {
        // 下拉框数据长度小于10条时，高度自适应，大于10条时最高200，防止高度占满整个屏幕
        if ($(this).combobox('getData').length < 10) {
            $(this).combobox('panel').height("auto");
        } else {
            $(this).combobox('panel').height(200);
        }
    }
});

/**
 * 初始化combobox控件
 * @param selector 选择器，必填
 * @param data 数据，必填
 * @param valueField
 * @param textField
 * @param onHidePanel
 */
function initCombobox(selector, data, valueField, textField, onHidePanel, onLoadSuccess) {
    $(selector).combobox({
        valueField: valueField || 'id',
        textField: textField || 'text',
        data: data,
        panelHeight: "auto",
        onHidePanel: onHidePanel || Combobox.hidePanelForSingle,
        /*onShowPanel: function () {
            // 下拉框数据长度小于10条时，高度自适应，大于10条时最高200，防止高度占满整个屏幕
            if ($(selector).combobox('getData').length < 10) {
                $(this).combobox('panel').height("auto");
            } else {
                $(this).combobox('panel').height(200);
            }
        },*/
        onLoadSuccess: function () {
            if (onLoadSuccess) {
                onLoadSuccess();
            }
        }
    });
}

/**
 * 初始化combobox控件
 * <b>注：要使用initComboboxForCheck的“所有”多选功能，那么valueField、textField这两个入参不能传同一个值，否则会导致“所有”功能失效</b>
 * <p>
 * 使用valueField、textField这两个参数值如果相同：
 * <code>initComboboxForCheck("#searchForm #ncBrandUnitCodes", data, "id", "id", hidePanelForMultiple);</code>
 * <p>
 * 可以通过重新构建如下数组来实现：
 * var ncBrandUnitCodes = [];
 $.each(data, function (index, element) {
ncBrandUnitCodes.push({id: element.id, text: element.id});
});
 initComboboxForCheck("#searchForm #ncBrandUnitCodes", ncBrandUnitCodes, "id", "text", hidePanelForMultiple);
 * <p>
 * 参考自 https://www.jianshu.com/p/1e2e393171d8
 *
 * @param selector 选择器，必填
 * @param data 数据，必填
 * @param valueField 必填
 * @param textField 必填
 * @param onHidePanel
 */
function initComboboxForCheck(selector, data, valueField, textField, onHidePanel, onLoadSuccess) {
    // 如果传入的“data”为空，则转到 initCombobox 函数上
    if (data == null || data == undefined || data.length == 0) {
        initCombobox(selector, data, valueField, textField, onHidePanel, onLoadSuccess);
        return true;
    }
    // valueField、textField两个参数字段配置为空时，设置默认值
    valueField = valueField || 'id';
    textField = textField || 'text';

    // 注意：这里新建一个数组来接“data”参数值，避免initComboboxForCheck函数中新增的“所有”项往后面传递，造成脏数据
    var itemData = new Array();
    itemData = itemData.concat(data);
    // 在头部添加“所有”
    var firstItem = {};
    firstItem[valueField] = '';
    firstItem[textField] = '所有';
    itemData.splice(0, 0, firstItem);

    $(selector).combobox({
        data: itemData,
        valueField: valueField,
        textField: textField,
        panelHeight: "auto",
        onHidePanel: onHidePanel || Combobox.hidePanelForMultiple,
        formatter: function (row) { // formatter方法就是实现了在每个下拉选项前面增加checkbox框的方法
            var opts = $(this).combobox('options');
            // 注意：这里的input复选框和span文字不能用<label></label>标签包裹，否则用户在点击节点时会触发两次onSelect调用事件，导致勾选功能失效
            return '<input type="checkbox" class="combobox-checkbox">&nbsp;<span>' + row[opts.textField] + '</span>';
        },
        onLoadSuccess: function () {
            $(selector).combobox('clear'); //清空

            if (onLoadSuccess) {
                onLoadSuccess();
            }
        },
        onShowPanel: function () { // 当下拉面板显示的时候触发
            // 说明：处理下拉面板展示时，复选框无法勾选的功能
            var data = $(selector).combobox('getData');
            var ids = $(selector).combobox('getValues');

            // 下拉框数据长度小于10条时，高度自适应，大于10条时最高200，防止高度占满整个屏幕
            if (data.length < 10) {
                $(this).combobox('panel').height("auto");
            } else {
                $(this).combobox('panel').height(200);
            }

            // “所有”节点id
            var allDomId = '';
            for (var i = 0; i < data.length; i++) {
                var itemText = $('#' + data[i].domId + ' span').text();
                if (itemText == '所有' || itemText === '所有') {
                    allDomId = data[i].domId;
                    continue;
                }
                // 检查该列是否已经选中，对复选框进行勾选/取消处理
                if ($.inArray(data[i][valueField], ids) > -1) {
                    $('#' + data[i].domId + ' input[type="checkbox"]').prop("checked", true);
                } else {
                    $('#' + data[i].domId + ' input[type="checkbox"]').prop("checked", false);
                }
            }
            if (ids.length >= (data.length - 1)) {
                $('#' + allDomId + ' input[type="checkbox"]').prop("checked", true);
            } else {
                $('#' + allDomId + ' input[type="checkbox"]').prop("checked", false);
            }
            $(selector).combobox('setValues', ids); // combobox全选
        },
        onSelect: function (row) { // 选中一个选项时调用
            var opts = $(this).combobox('options');
            var valueField = opts.valueField;
            var textField = opts.textField;
            // “所有”节点id
            var allDomId = '';

            var data = $(selector).combobox('getData');
            var checkTotal = 0;
            for (var i = 0; i < data.length; i++) {
                var itemText = $('#' + data[i].domId + ' span').text();
                if (itemText == '所有' || itemText === '所有') {
                    allDomId = data[i].domId;
                    continue;
                }
                if ($('#' + data[i].domId + ' input[type="checkbox"]').prop("checked")) {
                    checkTotal++;
                }
            }
            //当点击“所有”时，则勾中/取消所有的选项
            if (row[textField] === "所有") {
                // 检查第一个复选框“所有”是否已经勾选，如果已经勾选，那么本次就是取消勾选事件，执行清空所有选项逻辑
                // 说明：第一行数据为“所有”，id值为空，在调用combobox控件的setValues方法时是无法设置上值的，因此在取消选中时也无法触发onUnselect事件，因此这里通过判断已经勾选的条数来决定本次是全选还是清空

                // 说明：复选框“所有”的勾选无法触发combobox控件的onselect/onUnselect事件，因此这里采用判断已经勾选的数据行来决定是全选还是清空
                if (checkTotal >= (data.length - 1)) {
                    for (var i = 0; i < data.length; i++) {
                        $('#' + data[i].domId + ' input[type="checkbox"]').prop("checked", false);
                    }
                    $(selector).combobox('clear'); // 清空选中项
                } else {
                    var list = [];
                    for (var i = 0; i < data.length; i++) {
                        $('#' + data[i].domId + ' input[type="checkbox"]').prop("checked", true);
                        if (data[i][valueField] != null && data[i][valueField] != '' && data[i][valueField] != undefined) {
                            list.push(data[i][valueField]);
                        }
                    }
                    $(selector).combobox('setValues', list); // combobox全选
                }
            } else {
                //设置选中选项所对应的复选框为选中状态
                $('#' + row.domId + ' input[type="checkbox"]').prop("checked", true);
                // “所有”选项
                // 需要加上本次选择的值，所以这里要+1
                if ((checkTotal + 1) >= (data.length - 1)) {
                    $('#' + allDomId + ' input[type="checkbox"]').prop("checked", true);
                } else {
                    $('#' + allDomId + ' input[type="checkbox"]').prop("checked", false);
                }
            }
        },
        onUnselect: function (row) { // 取消选中一个选项时调用
            var data = $(selector).combobox('getData');
            // 当取消全选勾中时，则取消所有的勾选
            if (row[textField] === "所有") {
                for (var i = 0; i < data.length; i++) {
                    $('#' + data[i].domId + ' input[type="checkbox"]').prop("checked", false);
                }
                $(selector).combobox('clear');//清空选中项
            } else {
                // 下面是实现全选状态下取消任何一项，则取消勾选所有

                //设置选中选项所对应的复选框为非选中状态
                $('#' + row.domId + ' input[type="checkbox"]').prop("checked", false);
                var selectedList = $(selector).combobox('getValues');
                // 如果“所有”是选中状态,则将其取消选中
                if (data[0][valueField] == "" || data[0][valueField] === "") {
                    // 将“所有”选项移出数组，并且将该项的复选框设为非选中
                    // selectedList.splice(0, 1);
                    $('#' + data[0].domId + ' input[type="checkbox"]').prop("checked", false);
                }
                $(selector).combobox('clear');//清空
                $(selector).combobox('setValues', selectedList); // 重新复制选中项
            }
        }
    });
}

/**
 * 下拉框展示弹窗事件
 */
function showPanelCombobox() {
    var data = $(this).combobox('getData');
    // 下拉框数据长度小于10条时，高度自适应，大于10条时最高200，防止高度占满整个屏幕
    if (data.length < 10) {
        $(this).combobox('panel').height("auto");
    } else {
        $(this).combobox('panel').height(200);
    }
    // 重新选中值，防止多次打开窗口时，之前选中的值失效
    var selected = $(this).combobox('getValues');
    // 重新加载数据
    $(this).combobox('loadData', data);
    if (selected) {
        $(this).combobox('setValues', selected);
    }
}

/**
 * 下拉框展示弹窗事件（单选）
 */
function hidePanelComboboxForSingle() {
    var _options = $(this).combobox('options');
    var _data = $(this).combobox('getData');
    /* 下拉框所有选项 */
    var _value = $(this).combobox('getValue');
    /* 用户输入的值 */
    var _b = false;
    /* 标识是否在下拉列表中找到了用户输入的字符 */
    for (var i = 0; i < _data.length; i++) {
        if (_data[i][_options.valueField] == _value) {
            _b = true;
            break;
        }
    }
    if (!_b) {
        $(this).combobox('setValue', '');
        return false;
    }
    return true;
}

/**
 * 下拉框展示弹窗事件（多选）
 */
function hidePanelComboboxForMultiple() {
    var _options = $(this).combobox('options');
    var _data = $(this).combobox('getData');
    // 下拉框选择项
    var values = $(this).combobox('getValues');
    if (values.length > 0) {
        var array = [];
        if (values[0].indexOf(",") == -1) {
            $.each(values, function (index, element) {
                for (var i = 0; i < _data.length; i++) {
                    if (_data[i][_options.valueField] == element) {
                        array.push(element);
                    }
                }
            });
        } else {
            // 解决开头或者中间存在逗号导致多选下拉出现异常的问题
            // 去掉第一个逗号
            // var text = values[0].replace(",", "");
            // 使用正则，将多个相邻的逗号替换成一个逗号，相当于Java的replaceAll
            var reg = new RegExp(",", "g");
            var text = values[0].replace(reg, ",");
            // 分割文本值
            text = text.split(",");
            $.each(text, function (index, element) {
                if (element != null && element != '' && element != undefined) {
                    for (var i = 0; i < _data.length; i++) {
                        // 使用下拉框的text文本值进行匹配，匹配上则拿到对应的value值
                        if (_data[i][_options.textField] == element) {
                            array.push(_data[i][_options.valueField]);
                        }
                    }
                }
            });
        }
        $(this).combobox('setValues', array);
    } else {
        $(this).combobox('setValues', '');
    }
    return true;
}


/**
 * combogrid组件默认值
 */
$.fn.combogrid.defaults = $.extend({}, $.fn.combogrid.defaults, {
    method: "post",
    mode: 'remote',
    fitColumns: true, // 自动使列适应表格宽度以防止出现水平滚动
    fit: true,
    async: false, // 是否异步请求，默认为true
    border: true,
    striped: true, // 交替显示行背景
    nowrap: true, // 当数据长度超出列宽时将会自动截取
    loadMsg: "数据加载中，请稍候……",
    multiSort: true, // 是否允许多列排序
    rownumbers: true,
    singleSelect: true, // 是否只允许选择一行，默认单选
    checkOnSelect: true,
    selectOnCheck: true,
    multiple: false, // 是否支持多选，默认为单选
    onHidePanel: hidePanelCombogridForSingle,
});

/**
 * 初始化combogrid组件
 * @param selector 选择器，必填
 * @param options combogrid组件参数，可选
 */
function initCombogrid(selector, options) {
    options = options || {};
    // 默认属性值
    var defaultOptions = {
        method: "post",
        queryParams: {
            q: '' // 控件模糊匹配请求条件
        },
        idField: "id",
        textField: 'text',
        mode: 'remote',
        fitColumns: true, // 自动使列适应表格宽度以防止出现水平滚动
        fit: true,
        async: false, // 是否异步请求，默认为true
        border: true,
        striped: true, // 交替显示行背景
        nowrap: true, // 当数据长度超出列宽时将会自动截取
        loadMsg: "数据加载中，请稍候……",
        multiSort: true, // 是否允许多列排序
        rownumbers: true,
        singleSelect: true, // 是否只允许选择一行，默认单选
        checkOnSelect: true,
        selectOnCheck: true,
        multiple: false, // 是否支持多选，默认为单选
        panelWidth: 470,
        panelHeight: 230,
        readonly: false,
        autoRowHeight: false,
        showFooter: true,
        pagination: true, // 在数据表格底部显示分页工具栏
        pageNumber: 1, // 初始化分页码（页码从1开始）
        pageSize: 20, // 初始化每页显示数据量
        pageList: [10, 20, 50, 100, 200, 300, 400, 500], // 初始化每页记录数列表
        onHidePanel: hidePanelCombogridForSingle,
    };
    // 将options属性值合并到defaultOptions属性中
    $.extend(defaultOptions, options);
    $(selector).combogrid(defaultOptions);
}

/**
 * combogrid控件隐藏事件：单选
 * @returns {boolean}
 */
function hidePanelCombogridForSingle() {
    var grid = $(this).combogrid("grid");
    var data = grid.datagrid("getData");
    var _options = $(this).combogrid('options');
    var length = (data.rows ? data.rows.length : 0);
    if (length <= 0) {
        $(this).combogrid('setValue', '');
        $(this).combogrid(_options);
        return false;
    } else {
        var isFound = false;
        var inputVal = $(this).combogrid("getValue");  //当前combogrid的值
        /* 标识是否在下拉列表中找到了用户输入的字符 */
        for (var i = 0; i < length; i++) {
            if (data.rows[i][_options.idField] == inputVal) {
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            $(this).combogrid('setValue', '');
            $(this).combogrid(_options);
            return false;
        }
    }
    return true;
}

/**
 * combogrid控件隐藏事件：多选
 * @returns {boolean}
 */
function hidePanelCombogridForMultiple() {
    var grid = $(this).combogrid("grid");
    var _data = grid.datagrid("getData");
    var _options = $(this).combogrid('options');
    var length = (_data.rows ? _data.rows.length : 0);
    if (length <= 0) {
        $(this).combogrid('setValues', '');
        $(this).combogrid(_options);
        return false;
    } else {
        var values = $(this).combogrid('getValues');
        if (values.length > 0) {
            var array = [];
            if (values[0].indexOf(",") == -1) {
                $.each(values, function (index, element) {
                    for (var i = 0; i < _data.rows.length; i++) {
                        if (_data.rows[i][_options.idField] == element) {
                            array.push(element);
                        }
                    }
                });
            } else {
                // 解决开头或者中间存在逗号导致多选下拉出现异常的问题
                // 去掉第一个逗号
                var text = values[0].replace(",", "");
                // 使用正则，将多个相邻的逗号替换成一个逗号，相当于Java的replaceAll
                var reg = new RegExp(",", "g");
                text = text.replace(reg, ",");
                // 分割文本值
                text = text.split(",");
                $.each(text, function (index, element) {
                    for (var i = 0; i < _data.rows.length; i++) {
                        // 使用下拉框的text文本值进行匹配，匹配上则拿到对应的value值
                        if (_data.rows[i][_options.textField] == element) {
                            array.push(_data.rows[i][_options.idField]);
                        }
                    }
                });
            }
            $(this).combogrid('setValues', array);
        } else {
            var array = [];
            var text = $(this).combogrid('getText');
            if (text != null && text != '' && text != undefined) {
                // 解决开头或者中间存在逗号导致多选下拉出现异常的问题
                // 去掉第一个逗号
                text = text.replace(",", "");
                // 使用正则，将多个相邻的逗号替换成一个逗号，相当于Java的replaceAll
                var reg = new RegExp(",", "g");
                text = text.replace(reg, ",");
                // 分割文本值
                text = text.split(",");
                $.each(text, function (index, element) {
                    for (var i = 0; i < _data.rows.length; i++) {
                        // 使用下拉框的text文本值进行匹配，匹配上则拿到对应的value值
                        if (_data.rows[i][_options.textField] == element) {
                            array.push(_data.rows[i][_options.idField]);
                        }
                    }
                });
            }
            $(this).combogrid('setValues', array);
        }
    }
    return true;
}


/**
 * tab页操作
 */
var Tabs = {
    /**
     * 新增tab页
     * @param title tab页名称
     * @param url tab页地址
     */
    add: function (title, url) {
        var jq = top.jQuery;
        if (jq('#tabs').tabs('exists', title)) {
            jq('#tabs').tabs('select', title); // 选中并刷新

            // 先关闭之前的tab页，然后重新add打开
            // jq('#tabs').tabs('close', title);
            // jq('#tabs').tabs('add', {
            //     title: title,
            //     content: content,
            //     closable: true,
            //     cache: false
            // });
        } else {
            var content = createFrame(url);
            jq('#tabs').tabs('add', {
                title: title,
                content: content,
                // href: url,
                closable: true,
                cache: false
            });
        }
    },
    /**
     * 关闭指定的tab页
     * @param title tab页名称
     */
    close: function (title) {
        var $mainTab = top.$('#tabs');
        if (title && $mainTab.tabs('exists', title)) {
            $mainTab.tabs('close', title);
        }
    },
    /**
     * 关闭tab页
     */
    closeSelected: function () {
        var $mainTab = top.$('#tabs');
        var currentTab = $mainTab.tabs('getSelected');
        var tabIndex = $mainTab.tabs('getTabIndex', currentTab);

        // 先关闭当前tab
        $mainTab.tabs('close', tabIndex);
    },
    /**
     * 关闭tab页并刷新
     */
    refreshSelected: function () {
        var $mainTab = top.$('#tabs');
        var currentTab = $mainTab.tabs('getSelected');
        var url = $(currentTab.panel('options').content).attr('src');
        if (url != undefined && currentTab.panel('options').title != 'Home') {
            $('#tabs').tabs('update', {
                tab: currentTab,
                options: {
                    content: createFrame(url)
                }
            });
        }
    },
    /**
     * 关闭tab页
     * @param selectTitle 关闭tab页之后需要选中的tab页
     * @param callback 回调函数
     */
    closeAndSelect: function (selectTitle, callback) {
        var $mainTab = top.$('#tabs');
        var currentTab = $mainTab.tabs('getSelected');
        var tabIndex = $mainTab.tabs('getTabIndex', currentTab);

        // 先关闭当前tab
        $mainTab.tabs('close', tabIndex);
        if (selectTitle && $mainTab.tabs('exists', selectTitle)) {
            var $target = $mainTab.tabs('select', selectTitle);

            if (callback) {
                // 获取选中的tab
                var selectTab = $mainTab.tabs('getSelected');
                var index = $mainTab.tabs('getTabIndex', selectTab);
                var $targetFrame = $target.find('iframe');
                callback($targetFrame[index - 1].contentWindow);
            }
        }
    }
};

function createFrame(url) {
    var frame = '<iframe class="panel-iframe" frameborder="0" width="100%" height="100%" marginwidth="0px" marginheight="0px" scrolling="auto" src="' + url + '"></iframe>';
    return frame;
}

function addTab(title, url) {
    Tabs.add(title, url);
}

function closeTab(title) {
    if (title != null && title != '' && title != undefined) {
        Tabs.close(title);
    } else {
        Tabs.closeSelected();
    }
}

/**
 * showMsg 入参支持三种格式<br>
 * 1）showMsg(message)<br>
 * 2）showMsg(message,showType)<br>
 * 3）showMsg(title,message,showType)<br>
 *
 * showType可选值: null,slide,fade,show
 *
 * @param message 入参，采用showMsg(String... message)格式传值。message的length最大值为3
 * @author 伍章红 2015-11-20
 */
function showMsg(message) {
    var title = "提示",// title默认为“提示”
        msg,
        showType = "show";// showType默认值“show”
    var len = arguments.length;
    if (len == 1) {
        msg = message;
    } else if (len == 2) {
        msg = message;
        showType = arguments[1];
    } else if (len == 3) {
        title = arguments[0];
        msg = arguments[1];
        showType = arguments[2];
    }

    $.messager.show({
        title: title,
        msg: msg,
        showType: showType
    });
}

/**
 * alertMsg 入参支持三种格式<br>
 * 1）alertMsg(message)<br>
 * 2）alertMsg(message,icon)<br>
 * 3）alertMsg(title,message,icon)<br>
 *
 * icon可选值：error,question,info,warning
 *
 * @param message 入参，采用alertMsg(String... message)格式传值。message的length最大值为3
 * @author 伍章红 2015-11-20
 */
function alertMsg(message) {
    var len = arguments.length;
    if (len == 1) {
        $.messager.alert("提示", message);
    } else if (len == 2) {
        $.messager.alert("提示", message, arguments[1]);
    } else if (len == 3) {
        $.messager.alert(arguments[0], arguments[1], arguments[2]);
    }
}

/**
 * EasyUI组建管理
 */
var UIComponent = {
    /**
     * 设置指定的selector下的所有组件为只读
     * @param selector 选择器
     */
    readonly: function (selector) {
        $(selector).find("input.easyui-datebox").each(function () {
            var $this = $(this);
            var id = $this.attr("id");
            if (id == null || id == '' || id == undefined) {
                id = $this.data("id");
            }
            $("#" + id).datebox({
                required: false,
                readonly: true
            });
        });
        $(selector).find("input.easyui-validatebox").each(function () {
            var $this = $(this);
            var id = $this.attr("id");
            if (id == null || id == '' || id == undefined) {
                id = $this.data("id");
            }
            $("#" + id).validatebox({
                required: false,
                readonly: true
            });
        });
        $(selector).find("input.easyui-textbox").each(function () {
            var $this = $(this);
            var id = $this.attr("id");
            if (id == null || id == '' || id == undefined) {
                id = $this.data("id");
            }
            // 将文本框设置为只读，并去掉之前设置的必填控制
            $("#" + id).textbox({
                required: false,
                readonly: true
            });
        });
        $(selector).find("input.easyui-numberbox").each(function () {
            var $this = $(this);
            var id = $this.attr("id");
            if (id == null || id == '' || id == undefined) {
                id = $this.data("id");
            }
            $("#" + id).attr("readonly", "readonly");
            $("#" + id).numberbox({
                required: false
            });
        });
        $(selector).find("input.easyui-combobox").each(function () {
            var $this = $(this);
            // 将下拉框中的文本框设置为只读，并去掉之前设置的必填控制
            $this.parent().find('.textbox-text').textbox({
                required: false,
                readonly: true
            });
            // 将下拉框右边的按钮点击事件的class去掉
            $this.parent().find('.combo-arrow').removeClass('textbox-icon');
        });
        $(selector).find("input.easyui-combogrid").each(function () {
            var $this = $(this);
            // 将下拉框中的文本框设置为只读，并去掉之前设置的必填控制
            $this.parent().find('.textbox-text').textbox({
                required: false,
                readonly: true
            });
            // 将下拉框右边的按钮点击事件的class去掉
            $this.parent().find('.combo-arrow').removeClass('textbox-icon');
        });
        $(selector).find("textarea").each(function () {
            var $this = $(this);
            var id = $this.attr("id");
            if (id == null || id == '' || id == undefined) {
                id = $this.data("id");
            }

            $("#" + id).attr("readonly", true);
        });
        $(selector).find("input[type=checkbox]").each(function () {
            var $this = $(this);
            var id = $this.data("id");
            // 父div禁用
            $this.parent().addClass('disabled-click');
        });
        $(selector).find("input[type=radio]").each(function () {
            var $this = $(this);
            var id = $this.data("id");
            // 父div禁用
            $this.parent().addClass('disabled-click');
        });
    },
    getDictLabel: function (dictListJson, value, defaultValue, valueField, textField) {
        // valueField、textField两个参数字段配置为空时，设置默认值
        valueField = valueField || 'id';
        textField = textField || 'text';

        var result = [];
        for (var i = 0; i < dictListJson.length; i++) {
            var row = dictListJson[i];
            if (("," + value + ",").indexOf("," + row[valueField] + ",") != -1) {
                result.push(row[textField]);
            }
        }
        return result.length > 0 ? result.join(",") : defaultValue;
    }
};

$(function () {
});
