/**
 * panel关闭时回收内存，主要用于layout使用iframe嵌入网页时的内存泄漏问题
 *
 * @author 伍章红 2015-11-20
 * @requires jQuery-v1.11.3+,jQuery-EasyUI-v1.4.3+
 */
$.fn.panel.defaults.onBeforeDestroy = function () {
    var frame = $('iframe', this);
    try {
        if (frame.length > 0) {
            for (var i = 0; i < frame.length; i++) {
                frame[i].contentWindow.document.write('');
                frame[i].contentWindow.close();
            }
            frame.remove();
            if ($.browser.msie) {
                CollectGarbage();
            }
        }
    } catch (e) {
    }
};

/**
 * 使panel和datagrid在加载时提示
 *
 * @author 伍章红 2015-11-20
 * @requires jQuery-v1.11.3+,jQuery-EasyUI-v1.4.3+
 */
$.fn.panel.defaults.loadingMessage = '数据加载中，请稍后……';
$.fn.datagrid.defaults.loadMsg = '数据加载中，请稍后……';

/**
 * 通用错误提示
 * 用于datagrid/treegrid/tree/combogrid/combobox/form加载数据出错时的操作
 *
 * @author 伍章红 2015-11-20
 * @requires jQuery-v1.11.3+,jQuery-EasyUI-v1.4.3+
 */
var easyuiErrorFunction = function (XMLHttpRequest) {
    $.messager.progress('close');
    //$.messager.alert('错误', XMLHttpRequest.responseText);
};
$.fn.datagrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.treegrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.tree.defaults.onLoadError = easyuiErrorFunction;
$.fn.combogrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combobox.defaults.onLoadError = easyuiErrorFunction;
$.fn.form.defaults.onLoadError = easyuiErrorFunction;

/**
 * 为datagrid、treegrid增加表头菜单，用于显示或隐藏列，注意：冻结列不在此菜单中
 *
 * @author 伍章红 2015-11-20
 * @requires jQuery-v1.11.3+,jQuery-EasyUI-v1.4.3+
 */
var createGridHeaderContextMenu = function (e, field) {
    e.preventDefault();
    var grid = $(this);/* grid本身 */
    var headerContextMenu = this.headerContextMenu;/* grid上的列头菜单对象 */
    if (!headerContextMenu) {
        var tmenu = $('<div style="width:100px;"></div>').appendTo('body');
        var fields = grid.datagrid('getColumnFields');
        for (var i = 0; i < fields.length; i++) {
            var fildOption = grid.datagrid('getColumnOption', fields[i]);
            if (!fildOption.hidden) {
                $('<div iconCls="icon-ok" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(tmenu);
            } else {
                $('<div iconCls="icon-empty" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(tmenu);
            }
        }
        headerContextMenu = this.headerContextMenu = tmenu.menu({
            onClick: function (item) {
                var field = $(item.target).attr('field');
                if (item.iconCls == 'icon-ok') {
                    grid.datagrid('hideColumn', field);
                    $(this).menu('setIcon', {
                        target: item.target,
                        iconCls: 'icon-empty'
                    });
                } else {
                    grid.datagrid('showColumn', field);
                    $(this).menu('setIcon', {
                        target: item.target,
                        iconCls: 'icon-ok'
                    });
                }
            }
        });
    }
    headerContextMenu.menu('show', {
        left: e.pageX,
        top: e.pageY
    });
};
$.fn.datagrid.defaults.onHeaderContextMenu = createGridHeaderContextMenu;
$.fn.treegrid.defaults.onHeaderContextMenu = createGridHeaderContextMenu;

/**
 * easyui validatebox扩展验证
 *
 * @author 伍章红 2015-11-20
 * @requires jQuery-v1.11.3+,jQuery-EasyUI-v1.4.3+
 */
$.extend($.fn.validatebox.defaults.rules, {
    minLength: {
        validator: function (value, param) {
            var len = $.trim(value).length;
            return len >= param[0];
        },
        message: '请至少输入{0}个字符'
    },
    maxLength: {
        validator: function (value, param) {
            var len = $.trim(value).length;
            return len <= param[0];
        },
        message: '输入内容不能超过{0}个字符'
    },
    length: {
        validator: function (value, param) {
            var len = $.trim(value).length;
            return len >= param[0] && len <= param[1];
        },
        message: '输入内容长度必须介于{0}和{1}之间'
    },
    email: {
        validator: function (value, param) {
            return /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/i.test(value);
        },
        message: '邮箱格式不正确'
    },
    confirmPwd: {
        validator: function (value, param) {
            return value == $(param[0]).val();
        },
        message: '两次密码输入不一致'
    },
    age: {
        validator: function (value, param) {
            return /^(?:[1-9][0-9]?|1[01][0-9]|160)$/i.test(value);
        },
        message: '年龄必须是0到160之间的整数'
    },
    phone: {// 验证电话号码
        validator: function (value) {
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i
                .test(value);
        },
        message: '格式不正确,请使用下面格式:020-88888888'
    },
    mobile: {// 验证手机号码
        validator: function (value) {
            return /^(13|15|18)\d{9}$/i.test(value);
        },
        message: '手机号码格式不正确'
    },
    idcard: {// 验证身份证 
        validator: function (value) {
            return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
        },
        message: '身份证号码格式不正确'
    },
    qq: {// 验证QQ,从10000开始 
        validator: function (value) {
            return /^[1-9]\d{4,9}$/i.test(value);
        },
        message: 'QQ号码格式不正确'
    },
    intOrFloat: {// 验证整数或小数 
        validator: function (value) {
            return /^\d+(\.\d+)?$/i.test(value);
        },
        message: '请输入数字，并确保格式正确'
    },
    integer: {// 验证整数 
        validator: function (value) {
            return /^[+]?[1-9]+\d*$/i.test(value);
        },
        message: '请输入整数'
    },
    chinese: {// 验证中文 
        validator: function (value) {
            return /^[\Α-\￥]+$/i.test(value);
        },
        message: '请输入中文'
    },
    english: {// 验证英语
        validator: function (value) {
            return /^[A-Za-z]+$/i.test(value);
        },
        message: '请输入英文'
    },
    unnormal: {// 验证是否包含空格和非法字符 
        validator: function (value) {
            return !(/[\s`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im.test(value));
        },
        message: '输入值不能为空和包含其他非法字符'
    },
    faxno: {// 验证传真 
        validator: function (value) {
            //            return /^[+]{0,1}(\d){1,3}[// ]?([-]?((\d)|[// ]){1,12})+$/i.test(value); 
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message: '传真号码不正确'
    },
    zip: {// 验证邮政编码 
        validator: function (value) {
            return /^[1-9]\d{5}$/i.test(value);
        },
        message: '邮政编码格式不正确'
    },
    ip: {// 验证IP地址 
        validator: function (value) {
            return /d+.d+.d+.d+/i.test(value);
        },
        message: 'IP地址格式不正确'
    },
    date: {// 验证日期
        validator: function (value) {
            // 格式yyyy-MM-dd或yyyy-M-d
            return /^(?:(?!0000)[0-9]{4}([-]?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-]?)0?2\2(?:29))$/i
                .test(value);
        },
        message: '清输入合适的日期格式（yyyy-MM-dd或yyyy-M-d）'
    },
    time: {
        validator: function (value) {
            // 格式24hh:mi:ss
            return /^(([01]?[0-9])|(2[0-3])):[0-5]?[0-9]:[0-5]?[0-9]$/i.test(value);
        },
        message: '清输入合适的时间格式（24hh:mi:ss）'
    },
    datetime: {
        validator: function (value) {
            // 格式24hh:mi:ss
            return /^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)\\s(([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9])(:([0-5]?[0-9]))$/i.test(value);
        },
        message: '清输入合适的日期时间格式（yyyy-MM-dd 24hh:mi:ss或yyyy-M-d 24hh:mi:ss）'
    },
    msn: {
        validator: function (value) {
            return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
                .test(value);
        },
        message: '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
    },
    url: {
        validator: function (value) {
            var strRegex = '^((https|http|ftp|rtsp|mms)?://)'
                + '?(([0-9a-z_!~*\'().&=+$%-]+: )?[0-9a-z_!~*\'().&=+$%-]+@)?' //ftp的user@
                + '(([0-9]{1,3}.){3}[0-9]{1,3}' // IP形式的URL- 199.194.52.184
                + '|' // 允许IP和DOMAIN（域名）
                + '([0-9a-z_!~*\'()-]+.)*' // 域名- www.
                + '([0-9a-z][0-9a-z-]{0,61})?[0-9a-z].' // 二级域名
                + '[a-z]{2,6})' // first level domain- .com or .museum
                + '(:[0-9]{1,4})?' // 端口- :80
                + '((/?)|' // a slash isn't required if there is no file name
                + '(/[0-9a-z_!~*\'().;?:@&=+$,%#-]+)+/?)$';
            var re = new RegExp(strRegex);
            return re.test(str_url);
        },
        message: '请输入有效URL地址'
    }
});
