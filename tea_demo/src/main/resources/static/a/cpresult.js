/*本模块基本请求路径，对应controller提供的API*/
var base_url = "http://127.0.0.1:8080/mis/complaint/";
var list_url = base_url + "list2";
layui.use(['table', 'layer', 'form'],
    function () {

        /* 初始化，模块 */
        var $ = layui.$;
        var table = layui.table;
        var form = layui.form;
        var layer = layui.layer;

        /* 表格渲染 ：语法 kv,走list_url */
        table.render({
            elem: '#test',
            url: list_url,
            method: 'post',
            toolbar: '#toolbarDemo', // 渲染工具栏 <script type="text/html"
            title: '素拓申诉表',
            id: 'test',
            cols: [
                [{
                    type: 'checkbox',
                    fixed: 'left',
                    width: "10%"
                }, {
                    field: 'id',
                    title: '主键ID',
                    sort: true
                }, {
                    field: 'stId',
                    title: '素拓项ID',
                }, {
                    field: 'cpStatus',
                    title: '受理情况',
                }, {
                    field: 'cpApplytime',
                    title: '申诉时间',
                }, {
                    field: 'cpChargeid',
                    title: '处理人',
                }, {
                    field: 'cpDealreason',
                    title: '理由',
                }, {
                    field: 'cpDealtime',
                    title: '处理时间',
                }]
            ],
            page: true,
            parseData: function (res) { // 将原始数据解析成 table 组件所规定的数据
                return {
                    "code": res.code, // 解析接口状态
                    "msg": res.msg, // 解析提示文本
                    "count": typeof(res.page) == 'undefined' ? "" : res.page.totalCount, // 解析数据长度
                    "data": typeof(res.page) == 'undefined' ? "" : res.page.list // 解析数据列表
                };
            },
            done: function (res, curr, count) {
                if (res.code != 0) {
                    layer.alert(res.msg, {
                        icon: 7
                    });
                    $(".layui-table-main").html('<div class="layui-none">暂无数据</div>');
                } else

                    $("[data-field='cpStatus']").children().each(function () {

                        if ($(this).text() == '2') {
                            $(this).text("申诉通过");
                            $(this).css("color", "green");
                        }
                        if ($(this).text() == '3') {
                            $(this).text("申诉未通过");
                            $(this).css("color", "red");
                        }
                    });

            }
        });


    });
