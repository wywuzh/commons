"use strict";
layui.define(["layer", "table"], function (exprots) {
    let table = layui.table;
    var $ = layui.jquery;

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
        },
        initDatatable: function (selector, options) {
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
    };

    exprots("Component", Component);
});
