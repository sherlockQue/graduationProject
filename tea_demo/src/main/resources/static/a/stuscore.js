/*本模块基本请求路径，对应controller提供的API*/
var base_url = "http://127.0.0.1:8080/mis/stuscore/";
var list_url = base_url + "list";
var info_url = base_url + "info/";
var save_url = base_url + "save";
var update_url = base_url + "update";
var delete_url = base_url + "delete";

layui.use(['table', 'layer', 'form'],
    function () {

        /* 初始化，模块 */
        var $ = layui.$;
        var table = layui.table;
        var form = layui.form;
        var layer = layui.layer;

        //////////////////////////////////////////表格渲染

        /* 表格渲染 ：语法 kv,走list_url */
        table.render({
            elem: '#test',
            url: list_url,
            method: 'post',
            toolbar: '#toolbarDemo', // 渲染工具栏 <script type="text/html"
            // id="toolbarDemo">
            title: '业务表',
            id: 'test',
            // height:300,
            // cellMinWidth: 100, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            //列，根据需要展示
            cols: [
                [{
                    type: 'checkbox',
                    fixed: 'left',
                    width: "10%"
                }, {
                    field: 'id',
                    title: '主键id',
                    sort: true
                }, {
                    field: 'sstuid',
                    title: '学号',

                }, {
                    field: 'sgrade',
                    title: '学年',

                }, {
                    field: 'sterm',
                    title: '学期',

                }, {
                    field: 'sscore',
                    title: '总分',

                }
                ]
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
                }
            }
        });

        /* 表格头工具栏事件 ，监听 test:指定表格 ，这里根据需要添加事件 */
        table.on('toolbar(test)', function (obj) {
            // console.log(obj); 包含了事件和table/id=test
            var checkStatus = table.checkStatus(obj.config.id);
            var data = checkStatus.data;
            //console.log("------" + data);
            switch (obj.event) {

                case 'detail':
                    showData(data); // 查看
                    break;

            }
        });

        // /////////////////////////////////form监听按钮


        /* 监听查询工具栏，根据条件查找数据，条件可多个 lay-filter="search" */
        form.on('submit(search)', function (data) {
            // console.log(data.field);
            // id
            // layui,table重载，重新渲染
            table.reload('test', {
                where: data.field, // 条件 {name:v}
                page: {
                    curr: 1 // 重新第一页
                }
            });
        });

        /* 监听查询工具栏，查询全部 lay-filter="all" */
        form.on('submit(all)', function (data) {
            $("#searchForm")[0].reset(); // 清空表单
            table.reload('test', {
                where: null, // 条件设置为空
                page: {
                    curr: 1
                }
            });
        });


        //////////////////////////////////////弹出框


        /* 查看框 */
        function showData(obj) {
            // 从后台拿值
            var data = get(obj);

            if (!(typeof(data) == "undefined")) {

                // 给表单赋值，封装出来
                form.val("userForm", data);

                // 初始化工作
                // 设置表单元素只读，通用组件...将form表单里的所有元素或者指定设置为只读
                $('#userForm').find('input,textarea,select').prop('readonly', true);

                $('#userForm').find('#submit,#reset').hide();   //隐藏

                // 打开弹框
                layer.open({
                    title: "查看",
                    type: 1,
                    content: $("#myform1")
                });


            }
        };

        /////////////////////////////////////异步ajax
        /* 异步查询，得到实体 */
        function get(obj) {
            var result;
            if (obj.length == 0) {
                layer.msg("未选择对象", {
                    icon: 2
                });
            } else if (obj.length > 1) {
                layer.msg("不可选多个对象", {
                    icon: 2
                });
            } else {
                var id = obj[0].id; // 得到主键，只取一个
                // 异步
                $.ajax({
                    type: "get",
                    dataType: "json", // 预期服务器返回的数据类型
                    contentType: 'application/json;charset=UTF-8',
                    url: info_url + id, // url
                    async: false, // 保证return
                    // ，同步提交，只有执行完ajax
                    // 后才可进行后面的代码操作，否则下面对result赋值无效
                    success: function (res) {

                        if (res.code == 0) {
                            result = res.entity;
                            //console.log(result);

                        } else
                            layer.msg(res.msg, {
                                icon: 2
                            });
                    }
                });
            }
            return result;
        };


    });
