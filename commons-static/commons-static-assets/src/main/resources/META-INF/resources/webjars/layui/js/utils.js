/*"use strict";
layui.define(["layer"], function (exprots) {
    var $ = layui.jquery;
    /!**
     * Cookie管理
     * @author 伍章红 2018-10-01
     * @requires jQuery
     *!/
    var Cookie = {
        /!**
         * 设置cookie
         *
         * @param name cookie的名字
         * @param value cookie的值
         *!/
        setCookie: function (name, value) {//两个参数，一个是cookie的名字，一个是值
            var Days = 30; //此 cookie 将被保存 30 天
            var exp = new Date();
            exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
            document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
        },
        /!**
         * 获取cookie的值
         *
         * @param name cookie的名字
         * @returns {*}
         *!/
        getCookie: function (name) { //取cookies函数
            var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
            if (arr != null) {
                return unescape(arr[2]);
            }
            return null;
        }
    };

    /!**
     * 组件管理
     *!/
    var Component = {
        /!**
         * 设置指定的selector下的所有组件为只读
         * @param selector 选择器
         *!/
        readonly: function (selector) {
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
        }
    };

    /!**
     * ajax请求
     *!/
    var AjaxRequest = {
        /!**
         * 同步请求数据
         *
         * @param url 请求地址
         * @param requestParam 请求参数
         * @param successCallback 请求成功回调函数
         *!/
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
        /!**
         * 异步请求数据
         *
         * @param url 请求地址
         * @param requestParam 请求参数
         * @param successCallback 请求成功回调函数
         *!/
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
        /!**
         * 同步请求数据
         *
         * @param url 请求地址
         * @param requestParam 请求参数
         * @param successCallback 请求成功回调函数
         * @param errorCallback 请求失败回调函数
         *!/
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
        /!**
         * 同步请求数据
         *
         * @param url 请求地址
         * @param requestParam 请求参数
         * @param successCallback 请求成功回调函数
         * @param errorCallback 请求失败回调函数
         *!/
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

    /!**
     * 金额管理
     *!/
    var Amount = {
        /!**
         * 转换为中文
         * @param currencyDigits 数值金额
         * @returns {string}
         *!/
        convertCurrency: function (currencyDigits) {
            // Constants:
            var MAXIMUM_NUMBER = 99999999999.99;
            // Predefine the radix characters and currency symbols for output:
            var CN_ZERO = "零";
            var CN_ONE = "壹";
            var CN_TWO = "贰";
            var CN_THREE = "叁";
            var CN_FOUR = "肆";
            var CN_FIVE = "伍";
            var CN_SIX = "陆";
            var CN_SEVEN = "柒";
            var CN_EIGHT = "捌";
            var CN_NINE = "玖";
            var CN_TEN = "拾";
            var CN_HUNDRED = "佰";
            var CN_THOUSAND = "仟";
            var CN_TEN_THOUSAND = "万";
            var CN_HUNDRED_MILLION = "亿";
            var CN_SYMBOL = "人民币";
            var CN_DOLLAR = "元";
            var CN_TEN_CENT = "角";
            var CN_CENT = "分";
            var CN_INTEGER = "整";

            // Variables:
            var integral; // Represent integral part of digit number.
            var decimal; // Represent decimal part of digit number.
            var outputCharacters; // The output result.
            var parts;
            var digits, radices, bigRadices, decimals;
            var zeroCount;
            var i, p, d;
            var quotient, modulus;

            // Validate input string:
            currencyDigits = currencyDigits.toString();
            if (currencyDigits == "") {
                // alert("Empty input!");
                return "";
            }
            if (currencyDigits.match(/[^,.\d]/) != null) {
                // alert("Invalid characters in the input string!");
                return "";
            }
            if ((currencyDigits)
                .match(/^((\d{1,3}(,\d{3})*(.((\d{3},)*\d{1,3}))?)|(\d+(.\d+)?))$/) == null) {
                // alert("Illegal format of digit number!");
                return "";
            }

            // Normalize the format of input digits:
            currencyDigits = currencyDigits.replace(/,/g, ""); // Remove comma
            // delimiters.
            currencyDigits = currencyDigits.replace(/^0+/, ""); // Trim zeros at the
                                                                // beginning.
            // Assert the number is not greater than the maximum number.
            if (Number(currencyDigits) > MAXIMUM_NUMBER) {
                alert("您输入的金额太大，请重新输入!");
                return "";
            }

            // Process the coversion from currency digits to characters:
            // Separate integral and decimal parts before processing coversion:
            parts = currencyDigits.split(".");
            if (parts.length > 1) {
                integral = parts[0];
                decimal = parts[1];
                // Cut down redundant decimal digits that are after the second.
                decimal = decimal.substr(0, 2);
            } else {
                integral = parts[0];
                decimal = "";
            }
            // Prepare the characters corresponding to the digits:
            digits = new Array(CN_ZERO, CN_ONE, CN_TWO, CN_THREE, CN_FOUR, CN_FIVE,
                CN_SIX, CN_SEVEN, CN_EIGHT, CN_NINE);
            radices = new Array("", CN_TEN, CN_HUNDRED, CN_THOUSAND);
            bigRadices = new Array("", CN_TEN_THOUSAND, CN_HUNDRED_MILLION);
            decimals = new Array(CN_TEN_CENT, CN_CENT);
            // Start processing:
            outputCharacters = "";
            // Process integral part if it is larger than 0:
            if (Number(integral) > 0) {
                zeroCount = 0;
                for (i = 0; i < integral.length; i++) {
                    p = integral.length - i - 1;
                    d = integral.substr(i, 1);
                    quotient = p / 4;
                    modulus = p % 4;
                    if (d == "0") {
                        zeroCount++;
                    } else {
                        if (zeroCount > 0) {
                            outputCharacters += digits[0];
                        }
                        zeroCount = 0;
                        outputCharacters += digits[Number(d)] + radices[modulus];
                    }
                    if (modulus == 0 && zeroCount < 4) {
                        outputCharacters += bigRadices[quotient];
                    }
                }
                outputCharacters += CN_DOLLAR;
            }
            // Process decimal part if there is:
            if (decimal != "") {
                for (i = 0; i < decimal.length; i++) {
                    d = decimal.substr(i, 1);
                    if (d != "0") {
                        outputCharacters += digits[Number(d)] + decimals[i];
                    }
                }
            }
            // Confirm and return the final output string:
            if (outputCharacters == "") {
                outputCharacters = CN_ZERO + CN_DOLLAR;
            }
            if (decimal == "") {
                outputCharacters += CN_INTEGER;
            }
            outputCharacters = CN_SYMBOL + outputCharacters;
            return outputCharacters;
        }
    };
    exprots("Cookie", Cookie);
    exprots("Component", Component);
    exprots("AjaxRequest", AjaxRequest);
    exprots("Amount", Amount);
});*/

layui.use(["element", "table", "form", "jquery"], function () {
    var table = layui.table;
    var form = layui.form;
    let $ = layui.jquery;

    // 单击行选中事件：https://www.cnblogs.com/XuYuFan/p/11733546.html
    $(document).on("click", ".layui-table-body table.layui-table tbody tr", function () {
        var index = $(this).attr('data-index');
        var tableBox = $(this).parents('.layui-table-box');
        var tableDiv;
        //存在固定列
        if (tableBox.find(".layui-table-fixed.layui-table-fixed-l").length > 0) {
            tableDiv = tableBox.find(".layui-table-fixed.layui-table-fixed-l");
        } else {
            tableDiv = tableBox.find(".layui-table-body.layui-table-main");
        }
        var checkCell = tableDiv.find("tr[data-index=" + index + "]").find("td div.laytable-cell-checkbox div.layui-form-checkbox I");
        if (checkCell.length > 0) {
            checkCell.click();
        }
    });
    //对td的单击事件进行拦截停止，防止事件冒泡再次触发上述的单击事件（Table的单击行事件不会拦截，依然有效）
    $(document).on("click", "td div.laytable-cell-checkbox div.layui-form-checkbox", function (e) {
        e.stopPropagation();
    });

    function initDatatable(selector, options) {
        options = options || {};
        // 默认属性值
        var defaultOptions = {
            id: 'dataTable', // 设定容器唯一id
            elem: '#dataTable',
            method: 'post', // http请求类型，默认：get
            contentType: 'application/x-www-form-urlencoded', // 发送到服务端的内容编码类型。如果你要发送 json 内容，可以设置：contentType: 'application/json'
            headers: {}, // 接口的请求头。如：headers: {token: 'sasasas'}
            toolbar: '#admin-toolbar', // 开启表格头部工具栏区域。layui 2.4.0 开始新增。若需要“列显示隐藏”、“导出”、“打印”等功能，则必须开启该参数
            defaultToolbar: [ // 该参数可自由配置头部工具栏右侧的图标按钮
                'filter', 'exports', 'print',
                {layEvent: 'LAYTABLE_TIPS', title: '提示', icon: 'layui-icon-tips'}
            ],
            cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            size: 'sm',
            request: { // 重新设定分页请求参数，layui 2.1.0 版本新增
                pageName: 'page', // 页码的参数名称，默认：page
                limitName: 'limit' // 每页数据量的参数名称，默认：limit
            },
            response: { // 重新设定返回的数据格式，layui 2.1.0 版本新增
                statusName: 'code', // 规定数据状态的字段名称，默认：code
                statusCode: 0, // 规定成功的状态码，默认：0
                msgName: 'msg', // 规定状态信息的字段名称，默认：msg
                countName: 'total', // 规定数据总数的字段名称，默认：count
                dataName: 'data' // 规定数据列表的字段名称，默认：data
            },
            parseData: function (res) { // 数据格式解析的回调函数，用于将返回的任意数据格式解析成 table 组件规定的数据格式。layui 2.4.0 开始新增
                return {
                    'code': 0, // 解析接口状态
                    'msg': '', // 解析提示文本
                    'total': res.rowCount, // 解析数据长度
                    'data': res.resultList // 解析数据列表
                };
            },
            page: true, // 开启分页
            limit: 20, // 每页显示的条数
            limits: [20, 50, 100, 200, 300, 400, 500], // 每页显示条数的选择项
            done: function (res, curr, count) { // 数据渲染完的回调
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                //注：如果设置了parseData方法对请求响应结果进行解析处理，这里返回的就是parseData解析处理后的结果
                // console.log(res);

                //得到当前页码
                // console.log('当前页码=' + curr);

                //得到数据总量
                // console.log('数据总量=' + count);
            }
        };

        // 将options属性值合并到defaultOptions属性中
        $.extend(defaultOptions, options);
        var $datatable = table.render(defaultOptions);
        return $datatable;
    }
});
