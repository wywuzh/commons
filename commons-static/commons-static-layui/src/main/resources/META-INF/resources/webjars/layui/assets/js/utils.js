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
