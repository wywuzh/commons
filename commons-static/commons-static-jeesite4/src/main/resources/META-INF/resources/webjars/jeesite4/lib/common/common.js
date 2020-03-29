/*!
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * 项目自定义的公共JavaScript，可覆盖jeesite.js里的方法
 */
/**
 * 将form里面的内容序列化成json
 * 相同的checkbox用分号拼接起来
 * @param {dom} 指定的选择器
 * @param {obj} 需要拼接在后面的json对象
 * @method serializeJson
 * */
$.fn.serializeJson = function (otherString) {
    var serializeObj = {},
        array = this.serializeArray();
    $(array).each(function () {
        if (serializeObj[this.name]) {
            serializeObj[this.name] += ';' + this.value;
        } else {
            serializeObj[this.name] = this.value;
        }
    });
    if (otherString != undefined) {
        var otherArray = otherString.split(';');
        $(otherArray).each(function () {
            var otherSplitArray = this.split(':');
            serializeObj[otherSplitArray[0]] = otherSplitArray[1];
        });
    }
    return serializeObj;
};
/**
 * 将josn对象赋值给form。https://www.jb51.net/article/143058.htm
 * @param {dom} 指定的选择器
 * @param {obj} 需要给form赋值的json对象
 * @method serializeJson
 * */
$.fn.setForm = function (jsonValue) {
    var obj = this;
    $.each(jsonValue, function (name, ival) {
        var $oinput = obj.find("input[name=" + name + "]");
        if ($oinput.attr("type") == "checkbox") {
            if (ival !== null) {
                // var checkboxObj = $("[name=" + name + "]");
                var checkArray = ival.split(";");
                checkArray = checkArray.split(',');
                $("[name=" + name + "]").each(function () {
                    var $this = $(this);
                    for (var j = 0; j < checkArray.length; j++) {
                        if ($this.val() == checkArray[j]) {
                            console.log($this.val());
                            $this.attr('checked', true);
                        }
                    }
                });
                // for (var i = 0; i < checkboxObj.length; i++) {
                //     for (var j = 0; j < checkArray.length; j++) {
                //         if (checkboxObj[i].value == checkArray[j]) {
                //             checkboxObj[i].click();
                //         }
                //     }
                // }
            }
        } else if ($oinput.attr("type") == "radio") {
            $oinput.each(function () {
                var $this = $(this);
                if ($this.val() == ival) {
                    // 设置选中，采用iCheck控件的方法
                    $this.iCheck('check');
                }
            });
        } else if ($oinput.attr("type") == "textarea") {
            obj.find("[name=" + name + "]").html(ival);
        } else {
            obj.find("[name=" + name + "]").val(ival);
        }
    })
};

/**
 * 初始化select2组件
 * @param options
 */
function initSelect2(options) {
    var selector = options.selector, // 选择器
        width = options.width, // select2宽度
        data = options.data || new Array(), // 数据集合
        url = options.url, // 请求URL
        type = options.type, // 请求数据类型
        dataType = options.dataType, // 返回数据类型
        requestType = options.requestType, // 请求数据类型：ajax=采用select2的ajax请求方式，data=采用jQuery ajax方式先获取到，通过data设置到select2
        value = options.value, // 选中项value
        onselect = options.onselect; // 选中回调事件
    var ajax = null;
    if (url != null && url != '' && url != undefined) {
        if (requestType == 'data') {
            AjaxRequest.postForSync(url, {}, function (result) {
                var results = new Array();
                $(result).each(function (index, element) {
                    results.push({
                        id: element.code,
                        text: element.name
                    });
                });
                data = results;
            });
        } else {
            ajax = {
                url: url,
                type: type || 'POST',
                dataType: dataType || 'json',
                delay: 250,
                cache: false,
                data: function (params) {
                    return {
                        q: params.term // select2控件的查询参数是term，这里转为`q`字段查询
                    };
                },
                processResults: function (data, params) {
                    var results = new Array();
                    $(data).each(function (index, element) {
                        results.push({
                            id: element.code,
                            text: element.name
                        });
                    });
                    return {
                        results: results
                    };
                }
            };
        }
    }

    // TODO 清空选择项
    selector.select2('val', '');
    selector.on('select2:clear');
    selector.on('select2:remove');

    selector.select2({
        width: width,
        ajax: ajax,
        data: data,
        placeholder: '请选择',//默认文字提示
        language: "zh-CN",
        tags: false,//允许手动添加
        allowClear: false,//允许清空
        escapeMarkup: function (markup) {
            return markup;
        }, // 自定义格式化防止xss注入
        // minimumInputLength: 1,//最少输入多少个字符后开始查询
        formatResult: function formatRepo(repo) {
            return repo.name;
        }, // 函数用来渲染结果
        formatSelection: function formatRepoSelection(repo) {
            return repo.name;
        }, // 函数用于呈现当前的选择
    });

    // 绑定选择事件
    if (onselect) {
        $('#residenceProvince').on('select2:select', function (e) {
            // console.log("select2:select id=" + $(e.target).val() + ', text=' + $(e.target).text());
            onselect($(e.target).val());
        });
    }

    // 选中项value
    if (value != null && value != '' && value != undefined) {
        console.log("value=" + value);
        selector.val(value).trigger('change');
    }
}

/**
 * 地址通用组件
 */
var Address = {
    /**
     * 初始化省份组件
     * @param selector 选择器
     * @param onchange change回调事件
     */
    initProvince: function (selector, onchange) {
        initSelect2({
            selector: selector,
            url: contextPath + '/system/address/province/list'
        });
        // selector.select2({
        //     width: 120,
        //     ajax: {
        //         url: contextPath + '/system/address/province/list',
        //         type: 'POST',
        //         dataType: 'json',
        //         delay: 250,
        //         cache: false,
        //         data: function (params) {
        //             return {
        //                 q: params.term // select2控件的查询参数是term，这里转为`q`字段查询
        //             };
        //         },
        //         processResults: function (data, params) {
        //             var results = new Array();
        //             $(data).each(function (index, element) {
        //                 results.push({
        //                     id: element.code,
        //                     text: element.name
        //                 });
        //             });
        //             return {
        //                 results: results
        //             };
        //         }
        //     },
        //     placeholder: '请选择',//默认文字提示
        //     language: "zh-CN",
        //     tags: false,//允许手动添加
        //     allowClear: false,//允许清空
        //     escapeMarkup: function (markup) {
        //         return markup;
        //     }, // 自定义格式化防止xss注入
        //     // minimumInputLength: 1,//最少输入多少个字符后开始查询
        //     formatResult: function formatRepo(repo) {
        //         return repo.name;
        //     }, // 函数用来渲染结果
        //     formatSelection: function formatRepoSelection(repo) {
        //         return repo.name;
        //     }, // 函数用于呈现当前的选择
        // });
        //
        // if (onchange) {
        //     // 绑定change事件
        //     selector.on('select2:select', function (e) {
        //         onchange($(e.target).val());
        //     });
        // }
    },
    /**
     * 初始化市组件
     * @param selector 选择器
     * @param provinceCode 省份编码
     * @param onchange change回调事件
     */
    initCity: function (selector, provinceCode, onchange) {
        selector.select2({
            width: 120,
            ajax: {
                url: contextPath + '/system/address/city/list/' + provinceCode,
                type: 'POST',
                dataType: 'json',
                delay: 250,
                cache: false,
                data: function (params) {
                    return {
                        q: params.term // select2控件的查询参数是term，这里转为`q`字段查询
                    };
                },
                processResults: function (data, params) {
                    var results = new Array();
                    $(data).each(function (index, element) {
                        results.push({
                            id: element.code,
                            text: element.name
                        });
                    });
                    return {
                        results: results
                    };
                }
            },
            placeholder: '请选择',//默认文字提示
            language: "zh-CN",
            tags: false,//允许手动添加
            allowClear: false,//允许清空
            escapeMarkup: function (markup) {
                return markup;
            }, // 自定义格式化防止xss注入
            // minimumInputLength: 1,//最少输入多少个字符后开始查询
            formatResult: function formatRepo(repo) {
                return repo.name;
            }, // 函数用来渲染结果
            formatSelection: function formatRepoSelection(repo) {
                return repo.name;
            }, // 函数用于呈现当前的选择
        });

        if (onchange) {
            // 绑定change事件
            selector.on('select2:select', function (e) {
                onchange($(e.target).val());
            });
        }
    }
};