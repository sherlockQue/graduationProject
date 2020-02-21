/*本模块基本请求路径，对应controller提供的API*/
var base_url = "http://127.0.0.1:8080/mis/complaint/";
var list_url = base_url + "list";
var info_url = base_url + "info/";
var apply_url = base_url + "appeal/";
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
                    field: 'cpGrade',
                    title: '学年',
                }, {
                    field: 'cpTerm',
                    title: '学期',
                }, {
                    field: 'cpStatus',
                    title: '当前状态',
                }, {
                    field: 'cpReason',
                    title: '申诉原因',
                }, {
                    field: 'cpApplytime',
                    title: '申诉时间',
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
                }
                $("[data-field='cpStatus']").children().each(function () {

                    if ($(this).text() == '1') {
                        $(this).text("申诉中");
                        $(this).css("color", "yellow");
                    } else if ($(this).text() == '0') {
                        $(this).text("未申诉");
                    } else if ($(this).text() == '2' || $(this).text() == '3') {
                        $(this).text("已处理");
                        $(this).css("color", "green");
                    } else {
                    }
                });
            }

        });

        /* 表格头工具栏事件 ，监听 test:指定表格 ，这里根据需要添加事件 */
        table.on('toolbar(test)', function (obj) {
            // console.log(obj); 包含了事件和table/id=test
            var checkStatus = table.checkStatus(obj.config.id);
            var data = checkStatus.data;
            //console.log("------" + data);
            switch (obj.event) {
                case 'add':
                    addData(data); // 添加
                    break;
            }
            ;
        });


        form.on('submit(appeal)', function (data) {

            var id = data.field.id;
            var cpReason = data.field.cpReason;
            alert("id="+id);

            layer.confirm('确认提交申诉？', function (index) {
                $.ajax({
                    type: "GET",
                    dataType: "json",                     //预期服务器返回的数据类型
                    contentType: 'application/json;charset=UTF-8',
                    url: apply_url + id + "/" + cpReason,              //url
                    success: function (result) {
                        console.log(result);              // 请求成功后的回调函数, result 为响应内容
                        if (result.code == 0) {
                            //layer.msg('添加成功', {icon: 1});
                            layer.alert("操作成功", {
                                icon: 1
                            }, function (index) {
                                table.reload("test"); // 重载表格
                                layer.closeAll();  // layer.closeAll();
                            });
                        }
                        else
                            this.error(result);
                    },
                    error: function (error) {
                        var msg = "不明异常";
                        if (error.msg != null)
                            msg = error.msg;
                        layer.alert(msg, {
                            icon: 7
                        });
                        console.log(error);                   // 请求失败时的回调函数
                    }
                });
            });
        });

        function addData(data) {

            $("#userForm :input").not(":button, :submit, :reset")
                .val("").removeAttr("checked").remove("selected")
                .prop("readonly", false); // 核心
            $("#id").val(data[0].id);
            layer.open({
                title: "原因",
                type: 1,
                width: 350,
                content: $("#myform1")
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
