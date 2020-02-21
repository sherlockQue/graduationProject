var base_url = "http://127.0.0.1:8080/mis/complaint/";
layui.use(['table', 'upload', 'layer', 'form', 'laydate'], function () {

    var table = layui.table;
    var layer = layui.layer;
    var $ = layui.$;
    var form = layui.form;

    /* 渲染表格 */
    table.render({
        elem: '#test',
        url: base_url + "showList/",  //跨域url
        method: 'post',
        toolbar: '#toolbarDemo',
        title: '申诉表',
        id: 'test',
        height: 540,
        // cellMinWidth: 100, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        cols: [
            [
                {
                    type: 'checkbox',
                    fixed: 'left',
                    width: "10%"
                }, {
                field: 'id',
                title: '主键ID',
                sort: true
            }, {
                field: 'stuId',
                title: '学号'
            }, {
                field: 'strelation',
                title: '素拓项目',
                templet: '<div>{{d.strelation.tuoId}}</div>'
            }, {
                field: 'strelation',
                title: '素拓信息',
                templet: '<div>{{d.strelation.stMessage}}</div>'
            }, {
                field: 'strelation',
                title: '活动时间',
                templet: '<div>{{d.strelation.stActime}}</div>'
            }, {
                field: 'strelation',
                title: '照片',
                templet: '#imgTpl'
            }, {
                field: 'cpReason',
                title: '申诉原因',
            },
            ]

        ],
        page: true,
        response: {
            //statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0
        },
        parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
            return {
                "code": res.code, //解析接口状态
                "msg": res.msg, //解析提示文本
                "count": typeof(res.page) == 'NaNa' ? "" : res.page.totalCount, //解析数据长度
                "data": typeof(res.page) == 'NaNa' ? "" : res.page.list //解析数据列表
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
    layer.photos({//点击图片弹出
        photos: '.layer-photos-demo'
        , shift: 1 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
    });

    /* 监听头工具栏事件 */
    table.on('toolbar(test)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        var data = checkStatus.data;
        console.log(data);
        switch (obj.event) {
            case 'pass':
                passItem(data);                                        //通过
                break;
            case 'nopass' :
                notPassItem(data);									   //不通过
                break;
            case 'flush':
                table.reload("test");
                break;
        }
        ;
    });

    /* 通过认证 */
    function passItem(data) {

        var ids = [];
        if (data.length == 0) {
            layer.msg("未选择对象", {
                icon: 2
            });
        } else {
            for (var i = 0; i < data.length; i++) {
                ids[i] = data[i].id; // 获取主键数组
            }
            layer.prompt({
                formType: 0,
                value: '经核实，给予通过',
                title: '原因：',
                area: ['800px', '350px'] //自定义文本域宽高
            }, function (value, index) {
                $.ajax({
                    type: "POST",
                    dataType: "json", // 预期服务器返回的数据类型
                    data: JSON.stringify(ids), // 序列化@RequestBody
                    contentType: 'application/json;charset=UTF-8',
                    url: base_url + "passAppeal?sign=true&cpDealReason=" + value, // url
                    success: function (res) {

                        if (res.code == 0) {
                            layer.alert("提交成功", {
                                icon: 6
                            }, function (index) {
                                table.reload("test"); // 重载表格
                                layer.close(index); // layer.closeAll();
                            });
                        } else
                            layer.msg(res.msg, {
                                icon: 2
                            });
                    }
                });
                layer.close(index);
            });
        }


    }

    /* 不通过认证*/
    function notPassItem(data) {
        var ids = [];
        if (data.length == 0) {
            layer.msg("未选择对象", {
                icon: 2
            });
        } else {
            for (var i = 0; i < data.length; i++) {
                ids[i] = data[i].id; // 获取主键数组
            }
            layer.prompt({
                formType: 0,
                value: '',
                title: '请说明原因：',
                area: ['800px', '350px'] //自定义文本域宽高
            }, function (value, index) {

                $.ajax({
                    type: "POST",
                    dataType: "json", // 预期服务器返回的数据类型
                    data: JSON.stringify(ids), // 序列化@RequestBody
                    contentType: 'application/json;charset=UTF-8',
                    url: base_url + "passAppeal?sign=false&cpDealReason=" + value, // url
                    success: function (res) {
                        //console.log(res); // 请求成功后的回调函数,
                        // result 为响应内容
                        if (res.code == 0) {
                            layer.alert("操作成功", {
                                icon: 6
                            }, function (index) {
                                table.reload("test"); // 重载表格
                                layer.close(index); // layer.closeAll();
                            });
                        } else
                            layer.msg(res.msg, {
                                icon: 2
                            });
                    }
                });
                layer.close(index);
            });

        }
    }

    /**
     *
     * 监听下拉菜单，动态显示年级
     */
    form.on('select(nianji)', function (data) {
        var nian = data.value;
        var xue = $('#xueyuan').val();

        if (nian != 0 && xue != 0 && typeof(nian) != "undefined") {
            showStu(nian, xue);
            form.render();
        }

    });
    /**
     *
     * 监听下拉菜单，动态显示学院
     */
    form.on('select(xueyuan)', function (data) {
        var xue = data.value;
        var nian = $('#nianji').val();

        if (nian != 0 && xue != 0 && typeof(xue) != "undefined") {
            showStu(nian, xue);
            form.render();
        }

    });

    //显示学生信息
    function showStu(nian, xue) {

        var str = "?nian=" + nian + "&xue=" + xue
        table.reload('test', {                              //刷新，数据重载
            url: base_url + "showList/" + str,
            method: "post"
        });
    }
});