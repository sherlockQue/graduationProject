/*本模块基本请求路径，对应controller提供的API*/

var base_url = "http://127.0.0.1:8080/mis/tuoproject/";

layui.use(['table', 'layer', 'form'],
    function() {

        /* 初始化，模块 */
        var $ = layui.$;
        var table = layui.table;
        var form = layui.form;
        var layer = layui.layer;

        //////////////////////////////////////////表格渲染

        /* 表格渲染 ：语法 kv,走list_url */
        table.render({
            elem: '#test',
            url: base_url+'list',
            method: 'post',
            toolbar: '#toolbarDemo', // 渲染工具栏 <script type="text/html"
            // id="toolbarDemo">
            title: '素拓项目表',
            id: 'test',
            // height:300,
            // cellMinWidth: 100, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            //列，根据需要展示
            cols: [
                [	{type: 'checkbox', fixed: 'left'}
                    ,{field: 'tuoId', title: '素拓编号', width:"12%", sort: true}
                    ,{field: 'tuoMess', title: "对应信息", width:"60%"}
                    ,{field: 'tuoSource', title: "分值", width:"8%",sort:true}
                    ,{field: 'tuoType', title: "所属", width:"8%"}
                ]
            ],
            page: true,
            parseData: function(res) { // 将原始数据解析成 table 组件所规定的数据
                return {
                    "code": res.code, // 解析接口状态
                    "msg": res.msg, // 解析提示文本
                    "count": typeof(res.page) == 'undefined' ? "" : res.page.totalCount, // 解析数据长度
                    "data": typeof(res.page) == 'undefined' ? "" : res.page.list // 解析数据列表
                };
            },
            done: function(res, curr, count) {
                if (res.code != 0) {
                    layer.alert(res.msg, {
                        icon: 7
                    });
                    $(".layui-table-main").html('<div class="layui-none">暂无数据</div>');
                }
            }
        });

        /* 表格头工具栏事件 ，监听 test:指定表格 ，这里根据需要添加事件 */
        table.on('toolbar(test)', function(obj) {
            // console.log(obj); 包含了事件和table/id=test
            var checkStatus = table.checkStatus(obj.config.id);
            var data = checkStatus.data;
            //console.log("------" + data);
            switch (obj.event) {
                case 'add':
                    addData(); // 添加
                    break;
                case 'edit':
                    editData(data); // 编辑
                    break;
                case 'detail':
                    showData(data); // 查看
                    break;
                case 'batchDel':
                    batchDelData(data); // 删除
                    break;


            };
        });

        // /////////////////////////////////form监听按钮

        /* 监听提交，添加+修改用户 ，根据 lay-filter 区分，根据form表单的button的lay-filter 区分 */
        form.on('submit(save)', function(data) {
            // console.log(data); //可拿到所有相关
            save(data.field); // 异步提交
        });

        /* 监听查询工具栏，根据条件查找数据，条件可多个 lay-filter="search" */
        form.on('submit(search)', function(data) {
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
        form.on('submit(all)', function(data) {
            $("#searchForm")[0].reset(); // 清空表单
            table.reload('test', {
                where: null, // 条件设置为空
                page: {
                    curr: 1
                }
            });
        });


        //////////////////////////////////////弹出框

        /* 添加框 */
        function addData() {

            // 初始化工作，根据需要自己修订
            // 清空表单里的所有元素数据
            $("#userForm :input").not(":button, :submit, :reset")
                .val("").removeAttr("checked").remove("selected")
                .prop("readonly", false); // 核心

            // 显示
            $('#userForm').find('#submit,#reset').show();

            // 打开弹框
            layer.open({
                title: "添加",
                type: 1,
                content: $("#myform1")
            });
        };

        /* 编辑框 */
        function editData(obj) {

            // 从后台拿回全部值
            var data = get(obj);

            if (!(typeof(data) == "undefined")) {

                // 初始化工作
                // 清空数据
                $("#userForm :input").not(":button, :submit, :reset")
                    .val("").removeAttr("checked").remove("selected")
                    .prop("readonly", false); // 核心

                // 显示
                $('#userForm').find('#submit,#reset').show();

                // 给表单赋值，通用方法 ，userForm
                form.val("userForm", data);


                // 打开弹框
                layer.open({
                    title: "编辑",
                    type: 1,
                    content: $("#myform1")
                });
            }
        };

        /* 查看框 */
        function showData(obj) {

            // 从后台拿值
            var data = get(obj);

            console.log(data);

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

        /* 异步添加或修改 */
        function save(obj) {
            var url;

            if (obj.tuoId == null || obj.tuoId == "") { // 若Id为空,则添加，否则为修改
                url = base_url+"save"; // api
            } else {
                url = base_url+"update"; // api
            }

            $.ajax({
                type: "POST", // 方法类型
                dataType: "json", // 预期服务器返回的数据类型
                data: JSON.stringify(obj), // form序列化
                contentType: 'application/json;charset=UTF-8',
                url: url, // url
                success: function(res) {
                    //console.log(res); // 请求成功后的回调函数, result
                    // 为响应内容
                    if (res.code == 0)
                        layer.alert("操作成功", {
                            icon: 6
                        }, function() {
                            table.reload("test"); // 重载表格
                            layer.closeAll(); // layer.close(index);
                        });
                    else {
                        layer.alert(res.msg, {
                            icon: 2
                        });

                    }
                }
            });

        };

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

                var id = obj[0].tuoId; // 得到主键，只取一个

                // 异步
                $.ajax({
                    type: "get",
                    dataType: "json", // 预期服务器返回的数据类型
                    contentType: 'application/json;charset=UTF-8',
                    url: base_url+"info/"+ id, // url
                    async: false, // 保证return
                    // ，同步提交，只有执行完ajax
                    // 后才可进行后面的代码操作，否则下面对result赋值无效
                    success: function(res) {

                        if (res.code == 0) {
                            result = res.tuoproject;
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

        /* 异步删除 */
        function batchDelData(obj) {
            var ids = [];
            if (obj.length == 0) {
                layer.msg("未选择对象", {
                    icon: 2
                });
            } else {
                for (var i = 0; i < obj.length; i++) {
                    ids[i] = obj[i].tuoId; // 获取主键数组
                }
                // console.log(ids);
                layer.confirm('确认要删除吗？', function(index) {

                    // 走ajax异步删除
                    $.ajax({
                        type: "POST",
                        dataType: "json", // 预期服务器返回的数据类型
                        data: JSON.stringify(ids), // 序列化@RequestBody
                        contentType: 'application/json;charset=UTF-8',
                        url: base_url+"delete", // url
                        success: function(res) {
                            //console.log(res); // 请求成功后的回调函数,
                            // result 为响应内容
                            if (res.code == 0) {
                                layer.alert("删除成功", {
                                    icon: 6
                                }, function(index) {
                                    table.reload("test"); // 重载表格
                                    layer.close(index); // layer.closeAll();
                                });
                            } else
                                layer.msg(res.msg, {
                                    icon: 2
                                });
                        }
                    });
                });

            }

        };

    });
