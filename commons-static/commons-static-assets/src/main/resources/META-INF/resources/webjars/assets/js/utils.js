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

/**
 * 组件管理
 */
var Component = {
    /**
     * 设置指定的selector下的所有组件为只读
     * @param selector 选择器
     */
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
    getJSONForSync: function (url, requestParam, successCallback) {
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
            error: function () {
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
    getJSONForASync: function (url, requestParam, successCallback) {
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
            error: function () {
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
            error: function (data) {
                if (errorCallback) {
                    errorCallback(data);
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
            error: function (data) {
                if (errorCallback) {
                    errorCallback(data);
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

/**
 * 金额管理
 */
var Amount = {
    /**
     * 转换为中文
     * @param currencyDigits 数值金额
     * @returns {string}
     */
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