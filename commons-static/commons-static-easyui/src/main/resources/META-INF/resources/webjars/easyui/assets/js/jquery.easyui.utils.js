/**
 * jquery easyui工具
 *
 * @author <a href="mailto:wywuzh@163.com">伍章红</a>
 * @version v2.3.8
 * @since jquery
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
            return DateUtil.format(value, Pattern.PATTERN_DATE);
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
            return DateUtil.format(value, Pattern.PATTERN_TIME);
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
            return DateUtil.format(value, Pattern.PATTERN_DATE_TIME);
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
            return DateUtil.format(value, Pattern.PATTERN_YYYY_MM);
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
    checkOnSelect: true, // 用户点击行的时候是否选中或取消该复选框，如果为false则仅点击复选框的时候才会被选中或取消
    selectOnCheck: true, // 用户点击复选框的时候是否选中该行
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
        checkOnSelect: true, // 用户点击行的时候是否选中或取消该复选框，如果为false则仅点击复选框的时候才会被选中或取消
        selectOnCheck: true, // 用户点击复选框的时候是否选中该行
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
            // 删除“所有”下拉项，该下拉项不能选中
            if ($.inArray("", selected) > -1) {
                selected.splice($.inArray("", selected), 1);
            }
            $(this).combobox('setValues', selected);
        }
    },
    hidePanelForSingle: function () {
        var _options = $(this).combobox('options');
        var _data = $(this).combobox('getData');
        // 下拉框所有选项
        var _value = $(this).combobox('getValue');
        // 用户输入的值
        var _b = false;
        // 标识是否在下拉列表中找到了用户输入的字符
        for (var i = 0; i < _data.length; i++) {
            if (_data[i][_options.valueField] == _value) {
                _b = true;
                break;
            }
        }
        if (!_b) {
            $(this).combobox('clear');
            return false;
        }
        return true;
    },
    hidePanelForMultiple: function () {
        // 下拉框选择项
        var selected = $(this).combobox('getValues');
        if (selected.length == 0) {
            $(this).combobox('clear');
            return false;
        }

        var _options = $(this).combobox('options');
        var _data = $(this).combobox('getData');

        var array = [];
        if (selected[0].indexOf(",") == -1) {
            for (var index = 0; index < selected.length; index++) {
                var element = selected[index];
                if (element == null || element == '' || element == undefined) {
                    continue;
                }
                if ($.inArray(element, array) > -1) {
                    continue;
                }
                for (var i = 0; i < _data.length; i++) {
                    // 使用下拉框的text文本值进行匹配，匹配上则拿到对应的value值
                    if (_data[i][_options.valueField] == element) {
                        array.push(element);
                    }
                }
            }
        } else {
            // 解决开头或者中间存在逗号导致多选下拉出现异常的问题
            // 去掉第一个逗号
            // var text = values[0].replace(",", "");
            // 使用正则，将多个相邻的逗号替换成一个逗号，相当于Java的replaceAll
            var reg = new RegExp(",", "g");
            var text = selected[0].replace(reg, ",");
            // 分割文本值
            text = text.split(",");
            for (var index = 0; index < text.length; index++) {
                var element = text[index];
                if (element == null || element == '' || element == undefined) {
                    continue;
                }
                if ($.inArray(element, array) > -1) {
                    continue;
                }
                for (var i = 0; i < _data.length; i++) {
                    // 使用下拉框的text文本值进行匹配，匹配上则拿到对应的value值
                    if (_data[i][_options.textField] == element) {
                        array.push(_data[i][_options.valueField]);
                    }
                }
            }
        }
        // 删除“所有”下拉项，该下拉项不能选中
        if ($.inArray("", array) > -1) {
            array.splice($.inArray("", array), 1);
        }

        $(this).combobox('setValues', array);
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
 * @param valueField 下拉选项值，选填，传入为空时默认："id"
 * @param textField 下拉选项名称，选填，传入为空时默认："text"
 * @param onHidePanel 下拉面板隐藏事件
 * @param onLoadSuccess 数据加载成功事件
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
 * 初始化combobox控件，支持“所有”多选功能
 * <p>
 * 注意事项：
 * 1. initComboboxForCheck目前仅能支持到easyui-v1.4.5版本，v1.4.5以上版本combobox控件的setValues方法无法在onSelect事件中重新设置下拉值
 * 2. 传入的data数据中的valueField下拉选项值为数值时，需要转为字符格式。因为传入下拉选项值如果为数值0时，将会导致该下拉项无法选中
 * 3. 要使用initComboboxForCheck的“所有”多选功能，那么valueField、textField这两个入参不能传同一个值，否则会导致“所有”功能失效
 *
 * <pre>
 * 使用valueField、textField这两个参数值如果相同：
 * initComboboxForCheck("#searchForm #isEnables", data, "id", "id", hidePanelComboboxForMultiple);
 *
 * 可以通过重新构建如下数组来实现：
 * var isEnableData = [];
 $.each(data, function (index, element) {
    isEnableData.push({id: element.id, text: element.id});
});
 initComboboxForCheck("#searchForm #isEnables", isEnableData, "id", "text", hidePanelComboboxForMultiple);
 * </pre>
 *
 * 参考网址：
 * 1. https://www.jianshu.com/p/1e2e393171d8
 * 2. https://www.cnblogs.com/wgl0126/p/9230686.html
 *
 * @param selector 选择器，必填
 * @param data 数据，必填
 * @param valueField 下拉选项值，选填，传入为空时默认："id"
 * @param textField 下拉选项名称，选填，传入为空时默认："text"
 * @param onHidePanel 下拉面板隐藏事件
 * @param onLoadSuccess 数据加载成功事件
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
        multiple: true,
        panelHeight: "auto",
        onHidePanel: onHidePanel || hidePanelComboboxForMultiple,
        formatter: function (row) { // formatter方法就是实现了在每个下拉选项前面增加checkbox框的方法
            var opts = $(this).combobox('options');
            // 注意：这里的input复选框和span文字不能用<label></label>标签包裹，否则用户在点击节点时会触发两次onSelect调用事件，导致勾选功能失效
            return '<input type="checkbox" class="combobox-checkbox" style="margin:0 0px;vertical-align: -2px" id="' + row[opts.valueField] + '">&nbsp;<span>' + row[opts.textField] + '</span>';
        },
        onLoadSuccess: function () {
            $(selector).combobox('clear'); //清空

            if (onLoadSuccess) {
                onLoadSuccess();
            }
        },
        onShowPanel: function () { // 当下拉面板显示的时候触发
            var opts = $(this).combobox('options');
            // 说明：处理下拉面板展示时，复选框无法勾选的功能
            var data = $(this).combobox('getData');
            var ids = $(this).combobox('getValues');
            // 删除“所有”下拉项，该下拉项不能选中
            if ($.inArray("", ids) > -1) {
                ids.splice($.inArray("", ids), 1);
            }

            // 下拉框数据长度小于10条时，高度自适应，大于10条时最高200，防止高度占满整个屏幕
            if (data.length < 10) {
                $(this).combobox('panel').height("auto");
            } else {
                $(this).combobox('panel').height(200);
            }

            // “所有”节点id
            var allDomEl = null;
            for (var i = 0; i < data.length; i++) {
                var itemValue = data[i][opts.valueField];
                var itemText = data[i][opts.textField];
                if (itemText == '所有' || itemText === '所有') {
                    allDomEl = opts.finder.getEl(this, itemValue);
                    continue;
                }
                // 检查该列是否已经选中，对复选框进行勾选/取消处理
                if ($.inArray(itemValue, ids) > -1) {
                    var el = opts.finder.getEl(this, itemValue);
                    el.find('input.combobox-checkbox')._propAttr('checked', true);
                } else {
                    var el = opts.finder.getEl(this, itemValue);
                    el.find('input.combobox-checkbox')._propAttr('checked', false);
                }
            }
            if (ids.length >= (data.length - 1)) {
                allDomEl.find('input.combobox-checkbox')._propAttr('checked', true);
            } else {
                allDomEl.find('input.combobox-checkbox')._propAttr('checked', false);
            }
            $(this).combobox('clear');
            $(this).combobox('setValues', ids); // combobox全选
        },
        onSelect: function (row) { // 选中一个选项时调用
            var opts = $(this).combobox('options');
            var data = $(selector).combobox('getData');
            // “所有”节点id
            var allEl = null;

            var checkTotal = 0;
            for (var i = 0; i < data.length; i++) {
                var itemValue = data[i][opts.valueField];
                var itemText = data[i][opts.textField];
                if (itemText == '所有' || itemText === '所有') {
                    allEl = opts.finder.getEl(this, itemValue);
                    continue;
                }
                var el = opts.finder.getEl(this, itemValue);
                var propAttr = el.find('input.combobox-checkbox')._propAttr('checked');
                if (propAttr) {
                    checkTotal++;
                }
            }

            if (row[opts.textField] === '所有') {
                // 检查第一个复选框“所有”是否已经勾选，如果已经勾选，那么本次就是取消勾选事件，执行清空所有选项逻辑
                // 说明：第一行数据为“所有”，id值为空，在调用combobox控件的setValues方法时是无法设置上值的，因此这里通过判断已经勾选的条数来决定本次是全选还是清空

                if (checkTotal >= (data.length - 1)) {
                    for (var i = 0; i < data.length; i++) {
                        var itemValue = data[i][opts.valueField];
                        var el = opts.finder.getEl(this, itemValue);
                        el.find('input.combobox-checkbox')._propAttr('checked', false);
                    }
                    $(selector).combobox('clear'); // 清空选中项
                    $(selector).combobox('setValues', []); // 取消所有下拉项
                } else {
                    var list = [];
                    for (var i = 0; i < data.length; i++) {
                        var itemValue = data[i][opts.valueField];
                        if (itemValue != null && itemValue != '' && itemValue != undefined) {
                            list.push(itemValue);
                        }

                        var el = opts.finder.getEl(this, itemValue);
                        el.find('input.combobox-checkbox')._propAttr('checked', true);
                    }
                    // 1. 清除下拉列表框的值
                    $(this).combobox('clear');
                    // 2. 选中下拉项，此处会将“所有”下拉项选中去掉
                    $(this).combobox('setValues', list);
                }
            } else {
                // 设置选中选项所对应的复选框为选中状态
                var el = opts.finder.getEl(this, row[opts.valueField]);
                el.find('input.combobox-checkbox')._propAttr('checked', true);
                // “所有”选项
                // 需要加上本次选择的值，所以这里要+1
                if ((checkTotal + 1) >= (data.length - 1)) {
                    allEl.find('input.combobox-checkbox')._propAttr('checked', true);
                } else {
                    allEl.find('input.combobox-checkbox')._propAttr('checked', false);
                }
            }
        },
        onUnselect: function (row) { // 取消选中一个选项时调用
            var opts = $(this).combobox('options');
            var data = $(selector).combobox('getData');

            // 当取消全选勾中时，则取消所有的勾选
            if (row[opts.textField] === "所有") {
                for (var i = 0; i < data.length; i++) {
                    var el = opts.finder.getEl(this, data[i][opts.valueField]);
                    el.find('input.combobox-checkbox')._propAttr('checked', false);
                }
                $(this).combobox('clear');//清空选中项
                $(this).combobox('setValues', []); // 重新复制选中项
            } else {
                // 设置选中选项所对应的复选框为非选中状态
                var el = opts.finder.getEl(this, row[opts.valueField]);
                el.find('input.combobox-checkbox')._propAttr('checked', false);
                // 如果“所有”是选中状态,则将其取消选中
                if (data[0][opts.valueField] == "" || data[0][opts.valueField] === "") {
                    // 将“所有”选项移出数组，并且将该项的复选框设为非选中
                    var el = opts.finder.getEl(this, data[0][opts.valueField]);
                    el.find('input.combobox-checkbox')._propAttr('checked', false);
                }
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
    // 下拉框所有选项
    var _value = $(this).combobox('getValue');
    // 用户输入的值
    var _b = false;
    // 标识是否在下拉列表中找到了用户输入的字符
    for (var i = 0; i < _data.length; i++) {
        if (_data[i][_options.valueField] == _value) {
            _b = true;
            break;
        }
    }
    if (!_b) {
        $(this).combobox('clear');
        return false;
    }
    return true;
}

/**
 * 下拉框展示弹窗事件（多选）
 */
function hidePanelComboboxForMultiple() {
    // 下拉框选择项
    var selected = $(this).combobox('getValues');
    if (selected.length == 0) {
        $(this).combobox('clear');
        return false;
    }

    var _options = $(this).combobox('options');
    var _data = $(this).combobox('getData');

    var array = [];
    if (selected[0].indexOf(",") == -1) {
        for (var index = 0; index < selected.length; index++) {
            var element = selected[index];
            if ($.inArray(element, array) > -1) {
                continue;
            }
            for (var i = 0; i < _data.length; i++) {
                // 使用下拉框的text文本值进行匹配，匹配上则拿到对应的value值
                if (_data[i][_options.valueField] == element) {
                    array.push(element);
                }
            }
        }
    } else {
        // 解决开头或者中间存在逗号导致多选下拉出现异常的问题
        // 去掉第一个逗号
        // var text = values[0].replace(",", "");
        // 使用正则，将多个相邻的逗号替换成一个逗号，相当于Java的replaceAll
        var reg = new RegExp(",", "g");
        var text = selected[0].replace(reg, ",");
        // 分割文本值
        text = text.split(",");
        for (var index = 0; index < text.length; index++) {
            var element = text[index];
            if (element == null || element == '' || element == undefined) {
                continue;
            }
            if ($.inArray(element, array) > -1) {
                continue;
            }
            for (var i = 0; i < _data.length; i++) {
                // 使用下拉框的text文本值进行匹配，匹配上则拿到对应的value值
                if (_data[i][_options.textField] == element) {
                    array.push(_data[i][_options.valueField]);
                }
            }
        }
    }
    // 删除“所有”下拉项，该下拉项不能选中
    if ($.inArray("", array) > -1) {
        array.splice($.inArray("", array), 1);
    }

    $(this).combobox('setValues', array);
    return true;
}


/**
 * combogrid组件默认值
 */
$.fn.combogrid.defaults = $.extend({}, $.fn.combogrid.defaults, {
    method: "post",
    mode: 'local', // 定义在文本改变的时候如何读取数据网格数据（local、remote）。设置为'remote'，数据表格将从远程服务器加载数据。当设置为'remote'模式的时候，用户输入将会发送到名为'q'的http请求参数，向服务器检索新的数据。默认值：local
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
    checkOnSelect: true, // 用户点击行的时候是否选中或取消该复选框，如果为false则仅点击复选框的时候才会被选中或取消
    selectOnCheck: true, // 用户点击复选框的时候是否选中该行
    multiple: false, // 是否支持多选，默认为单选
    onShowPanel: showPanelCombogrid,
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
        frozenColumns: [
            {field: 'chex', checkbox: true, width: 30},
        ],
        idField: "id", // 标识字段
        textField: 'text',
        mode: 'remote', // local、remote
        fitColumns: true, // 自动使列适应表格宽度以防止出现水平滚动
        fit: true,
        async: false, // 是否异步请求，默认为true
        border: true,
        striped: true, // 交替显示行背景/是否显示斑马线效果
        nowrap: true, // 当数据长度超出列宽时将会自动截取
        loadMsg: "数据加载中，请稍候……",
        multiSort: true, // 是否允许多列排序
        rownumbers: true, // 是否显示行号列
        singleSelect: true, // 是否只允许选择一行，默认单选
        checkOnSelect: true, // 用户点击行的时候是否选中或取消该复选框，如果为false则仅点击复选框的时候才会被选中或取消
        selectOnCheck: true, // 用户点击复选框的时候是否选中该行
        multiple: false, // 是否支持多选，默认为单选
        panelWidth: 470,
        panelHeight: 230,
        readonly: false,
        autoRowHeight: false, // 根据该行的内容自适应设置行的高度。设置为false可以提高负载性能。
        showFooter: true,
        pagination: true, // 在数据表格底部显示分页工具栏
        pageNumber: 1, // 初始化分页码（页码从1开始）
        pageSize: 20, // 初始化每页显示数据量
        pageList: [10, 20, 50, 100, 200, 300, 400, 500], // 初始化每页记录数列表
        onShowPanel: showPanelCombogrid,
        onHidePanel: hidePanelCombogridForSingle,
    };
    // 将options属性值合并到defaultOptions属性中
    $.extend(defaultOptions, options);
    $(selector).combogrid(defaultOptions);
}

/**
 * combogrid控件面板显示事件
 */
function showPanelCombogrid() {
    // 重新选中值，防止多次打开窗口时，之前选中的值失效
    var selected = $(this).combogrid('getValues');
    if (selected != null && selected.length > 0) {
        // 重新加载当前页数据
        $(this).combogrid("grid").datagrid('reload');
        $(this).combogrid('setValues', selected);
    } else {
        // 注：未选中行时，第二次展开面板加载数据时“q”的查询条件还是旧的，需要清空掉
        var queryParams = $(this).combogrid("grid").datagrid('options').queryParams;
        queryParams.q = '';
        $(this).combogrid("grid").datagrid('options').queryParams = queryParams;
        $(this).combogrid("grid").datagrid('options').pageNumber = 1;
        $(this).combogrid("grid").datagrid('load');
    }
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
     * @param closeExists 是否关闭之前存在的tab页
     */
    add: function (title, url, closeExists) {
        var jq = top.jQuery;
        let $tabs = jq('#tabs');
        // 检查 tabs 是否存在，如果不存在则新打开一个标签页
        var tabsHtml = $tabs.html();
        if (tabsHtml == null || tabsHtml == '' || tabsHtml == undefined) {
            window.open(url, "_black");
            return false;
        }
        if ($tabs.tabs('exists', title)) {
            $tabs.tabs('select', title); // 选中并刷新

            if (closeExists) {
                // 先关闭之前的tab页，然后重新add打开
                $tabs.tabs('close', title);
                $tabs.tabs('add', {
                    title: title,
                    content: createFrame(url),
                    closable: true,
                    cache: false
                });
            }
        } else {
            $tabs.tabs('add', {
                title: title,
                content: createFrame(url),
                // href: url,
                closable: true,
                cache: false
            });
        }
        initTabCloseEvent();
    },
    /**
     * 关闭指定的tab页
     * @param title tab页名称
     */
    close: function (title) {
        var jq = top.jQuery;
        let $tabs = jq('#tabs');
        if (title && $tabs.tabs('exists', title)) {
            $tabs.tabs('close', title);
        }
    },
    /**
     * 关闭tab页
     */
    closeSelected: function () {
        var jq = top.jQuery;
        let $tabs = jq('#tabs');
        // 检查 mainTabs 是否存在，如果不存在则新打开一个标签页
        var tabsHtml = $tabs.html();
        if (tabsHtml == null || tabsHtml == '' || tabsHtml == undefined) {
            $.messager.confirm('确认', '即将关闭当前窗口？', function (flag) {
                if (flag) {
                    window.close();
                }
            });
            return false;
        }
        var currentTab = $tabs.tabs('getSelected');
        var tabIndex = $tabs.tabs('getTabIndex', currentTab);

        // 先关闭当前tab
        $tabs.tabs('close', tabIndex);
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
        var jq = top.jQuery;
        let $tabs = jq('#tabs');
        // 检查 mainTabs 是否存在，如果不存在则新打开一个标签页
        var tabsHtml = $tabs.html();
        if (tabsHtml == null || tabsHtml == '' || tabsHtml == undefined) {
            $.messager.confirm('确认', '即将关闭当前窗口？', function (flag) {
                if (flag) {
                    window.close();
                }
            });
            return false;
        }
        var currentTab = $tabs.tabs('getSelected');
        var tabIndex = $tabs.tabs('getTabIndex', currentTab);

        // 先关闭当前tab
        $tabs.tabs('close', tabIndex);
        if (selectTitle && $tabs.tabs('exists', selectTitle)) {
            var $target = $tabs.tabs('select', selectTitle);

            if (callback) {
                // 获取选中的tab
                var selectTab = $tabs.tabs('getSelected');
                var index = $tabs.tabs('getTabIndex', selectTab);
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

/**
 * 创建tab页
 * @param title tab页名称
 * @param url  tab页链接地址
 * @since v2.3.7
 * @requires jquery
 */
$.fn.addTab = function (title, url) {
    var $tabs = this;
    // 检查 tabs 是否存在，如果不存在则新打开一个标签页
    var tabsHtml = $tabs.html();
    if (tabsHtml == null || tabsHtml == '' || tabsHtml == undefined) {
        window.open(url, "_black");
        return false;
    }
    if ($tabs.tabs('exists', title)) {
        $tabs.tabs('select', title); // 选中并刷新
    } else {
        var content = createFrame(url);
        $tabs.tabs('add', {
            title: title,
            content: content,
            // href: url,
            closable: true,
            cache: false
        });
    }
};
$.fn.closeTab = function (title) {
    var $tabs = this;
    // 检查 tabs 是否存在，如果不存在则新打开一个标签页
    var tabsHtml = $tabs.html();
    if (tabsHtml == null || tabsHtml == '' || tabsHtml == undefined) {
        window.open(url, "_black");
        return false;
    }
    if ($tabs.tabs('exists', title)) {
        $tabs.tabs('select', title); // 选中并刷新
    } else {
        var content = createFrame(url);
        $tabs.tabs('add', {
            title: title,
            content: content,
            // href: url,
            closable: true,
            cache: false
        });
    }
};

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

function initTabCloseEvent() {
    /*双击关闭TAB选项卡*/
    $(".tabs-inner").dblclick(function () {
        var subtitle = $(this).children(".tabs-closable").text();
        $('#tabs').tabs('close', subtitle);
    })
    /*为选项卡绑定右键*/
    $(".tabs-inner").bind('contextmenu', function (e) {
        $('#mm').menu('show', {
            left: e.pageX,
            top: e.pageY
        });

        var subtitle = $(this).children(".tabs-closable").text();
        if (subtitle == null || subtitle == '' || subtitle == undefined) {
            subtitle = 'Home';
        }

        $('#mm').data("currtab", subtitle);
        $('#tabs').tabs('select', subtitle);

        var tab = $('#tabs').tabs('getTab', subtitle);
        // 取得tab的索引
        var tabIndex = $('#tabs').tabs('getTabIndex', tab);
        if (tabIndex <= 0) {
            tabIndex = 1;
        } else {
            tabIndex += 1;
        }
        tabsContextMenu(tabIndex, $('#tabs').tabs('tabs'));
        return false;
    });
}

/**
 * 设置tab右键菜单
 * @param curTabIndex tab索引，从1开始
 * @param tabs
 */
function tabsContextMenu(curTabIndex, tabs) {
    var length = tabs.length;
    // console.log('curTabIndex=' + curTabIndex + ", tabs.length=" + length);

    var tabclose = $('#mm-tabclose')[0];
    var tabcloseother = $('#mm-tabcloseother')[0];
    var tabcloseleft = $('#mm-tabcloseleft')[0];
    var tabcloseright = $('#mm-tabcloseright')[0];
    var tabcloseall = $('#mm-tabcloseall')[0];
    $('#mm').menu('enableItem', tabclose);
    $('#mm').menu('enableItem', tabcloseother);
    $('#mm').menu('enableItem', tabcloseleft);
    $('#mm').menu('enableItem', tabcloseright);
    $('#mm').menu('enableItem', tabcloseall);

    if (curTabIndex <= 1) {
        $('#mm').menu('disableItem', tabclose);
    }
    if (length > 1) {
        if (curTabIndex <= 2) {
            $('#mm').menu('disableItem', tabcloseleft);
        }

        if (curTabIndex < length) {
        } else if (curTabIndex >= length) {
            $('#mm').menu('disableItem', tabcloseright);
            if (curTabIndex == 2) {
                $('#mm').menu('disableItem', tabcloseother);
            }
        }
    } else {
        // 当tabs只有一个首页选项卡时，除了“刷新选项卡”右键菜单外，其余的右键菜单禁用
        $('#mm').menu('disableItem', tabclose);
        $('#mm').menu('disableItem', tabcloseother);

        $('#mm').menu('disableItem', tabcloseleft);
        $('#mm').menu('disableItem', tabcloseright);
        $('#mm').menu('disableItem', tabcloseall);
    }
}

/**
 * tab页右键管理
 * @type {{init: context_menu.init, refresh: context_menu.refresh, close: context_menu.close, closeOther: context_menu.closeOther, closeLeft: context_menu.closeLeft, closeRight: context_menu.closeRight, closeAll: context_menu.closeAll}}
 */
var context_menu = {
    init: function () {
    },
    refreshTab: function () { // 刷新选项卡
        var currTab = $('#tabs').tabs('getSelected');
        var url = $(currTab.panel('options').content).attr('src');
        if (url != undefined && currTab.panel('options').title != 'Home') {
            $('#tabs').tabs('update', {
                tab: currTab,
                options: {
                    content: createFrame(url)
                }
            });
        }
    },
    closeTab: function () { // 关闭选项卡
        var currtab_title = $('#mm').data("currtab");
        $('#tabs').tabs('close', currtab_title);
    },
    closeOtherTab: function () { // 关闭其他选项卡
        var prevAll = $('.tabs-selected').prevAll();
        var nextAll = $('.tabs-selected').nextAll();
        if (prevAll.length > 0) {
            prevAll.each(function (index, element) {
                var text = $('a:eq(0) span', $(element)).text();
                if (text != 'Home') {
                    $('#tabs').tabs('close', text);
                }
            });
        }
        if (nextAll.length > 0) {
            nextAll.each(function (index, element) {
                var text = $('a:eq(0) span', $(element)).text();
                if (text != 'Home') {
                    $('#tabs').tabs('close', text);
                }
            });
        }
    },
    closeLeftTab: function () { // 关闭左侧选项卡
        var prevAll = $('.tabs-selected').prevAll();
        if (prevAll.length == 0) {
            // alert('到头了，前边没有啦~~');
            return false;
        }
        prevAll.each(function (index, element) {
            var text = $('a:eq(0) span', $(element)).text();
            if (text != 'Home') {
                $('#tabs').tabs('close', text);
            }
        });
    },
    closeRightTab: function () { // 关闭右侧选项卡
        var nextAll = $('.tabs-selected').nextAll();
        if (nextAll.length == 0) {
            //msgShow('系统提示','后边没有啦~~','error');
            // alert('后边没有啦~~');
            return false;
        }
        nextAll.each(function (index, element) {
            var text = $('a:eq(0) span', $(element)).text();
            if (text != 'Home') {
                $('#tabs').tabs('close', text);
            }
        });
    },
    closeAllTab: function () { // 关闭所有选项卡
        $('.tabs-inner span').each(function (index, element) {
            var text = $(element).text();
            if (text != 'Home') {
                $('#tabs').tabs('close', text);
            }
        });
    },
};


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
    readonlyForDatebox: function (selector) {
        // 禁用验证，解决tooltip无法关闭的问题
        $(selector).datebox('disableValidation');
        $(selector).datebox({
            required: false,
            readonly: true
        });
    },
    readonlyForValidatebox: function (selector) {
        // 禁用验证，解决tooltip无法关闭的问题
        $(selector).validatebox('disableValidation');
        $(selector).validatebox({
            required: false,
            readonly: true
        });
    },
    readonlyForTextbox: function (selector) {
        // 禁用验证，解决tooltip无法关闭的问题
        $(selector).textbox('disableValidation');
        // 将文本框设置为只读，并去掉之前设置的必填控制
        $(selector).textbox({
            required: false,
            readonly: true
        });
    },
    readonlyForNumberbox: function (selector) {
        // 禁用验证，解决tooltip无法关闭的问题
        $(selector).numberbox('disableValidation');
        // 将数值输入框设置为只读，并去掉之前设置的必填控制
        $(selector).attr("readonly", "readonly");
        $(selector).numberbox({
            required: false
        });
    },
    readonlyForCombobox: function (selector) {
        // 禁用验证，解决tooltip无法关闭的问题
        $(selector).combobox('disableValidation');
        $(selector).closest("input.easyui-combobox").each(function () {
            var $this = $(this);
            // 将下拉框中的文本框设置为只读，并去掉之前设置的必填控制
            $this.parent().find('.textbox-text').textbox({
                required: false,
                readonly: true
            });
            // 将下拉框右边的按钮点击事件的class去掉
            $this.parent().find('.combo-arrow').removeClass('textbox-icon');
        });
    },
    readonlyForCombogrid: function (selector) {
        // 禁用验证，解决tooltip无法关闭的问题
        $(selector).combobox('disableValidation');
        $(selector).closest("input.easyui-combogrid").each(function () {
            var $this = $(this);
            // 将下拉框中的文本框设置为只读，并去掉之前设置的必填控制
            $this.parent().find('.textbox-text').textbox({
                required: false,
                readonly: true
            });
            // 将下拉框右边的按钮点击事件的class去掉
            $this.parent().find('.combo-arrow').removeClass('textbox-icon');
        });
    },
    readonlyForTextarea: function (selector) {
        $(selector).closest("textarea").each(function () {
            var $this = $(this);
            var id = $this.attr("id");
            if (id == null || id == '' || id == undefined) {
                id = $this.data("id");
            }

            $("#" + id).attr("readonly", true);
        });
    },
    /**
     * 设置指定的selector下的所有组件为只读
     * @param selector 选择器
     */
    readonly: function (selector) {
        $(selector).parent().find(".textbox-text").each(function () {
            var $this = $(this);
            var id = $this.attr("id");
            if (id == null || id == '' || id == undefined) {
                id = $this.data("id");
            }
            UIComponent.readonlyForDatebox("#" + id);
        });
        /*$(selector).find("input.easyui-datebox").parent().find(".textbox-text").each(function () {
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
        $(selector).find("input.easyui-validatebox").parent().find(".textbox-text").each(function () {
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
        $(selector).find("input.easyui-textbox").parent().find(".textbox-text").each(function () {
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
        });*/
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
    // Tab关闭事件
    initTabCloseEvent();
});
