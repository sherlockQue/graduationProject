// 参考 https://blog.csdn.net/qq_33769914/article/details/93048353
// var base_url = "http://192.168.1.97:8080/mis/tbuser/";
// var base_url = "http://127.0.0.1:8080/sys/user/";
var base_url = "http://127.0.0.1:8080/mis/RenAuthentication/";
layui.use(['table', 'upload', 'layer', 'form', 'laydate'], function () {

    var table = layui.table;
    var layer = layui.layer;
    var $ = layui.$;
    var form = layui.form;
    var laydate = layui.laydate;
    var upload = layui.upload;


    /* 渲染表格 */

    table.render({
        elem: '#test',
        url: base_url + 'list',  //跨域url
        method: 'post',
        toolbar: '#toolbarDemo',
        title: '认证表',
        id: 'test',
        height: 540,
        // cellMinWidth: 100, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        cols: [
            [
                {type: 'checkbox', fixed: 'left'}
                , {field: 'stId', title: 'ID', width: "8%", sort: true}
                , {field: 'tuoId', title: '项目编号', width: "12%", sort: true}
                , {field: 'stMessage', title: "内容", width: "20%"}
                , {field: 'stActime', title: '活动时间', width: "12%"}
                , {field: 'stImg', title: '图片', width: "14%", templet: '#imgTpl'}
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

    //监听状态开关
    form.on('switch(switchTest)', function (data) {
        console.log(data);
        var status = this.checked ? 1 : 0;
        $("#status").attr({"value": status});
    });

    /* 通过认证 */
    function passItem(data) {

        getItems(data);

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
                ids[i] = data[i].stId; // 获取主键数组
            }
            // layer.prompt({
            //     formType: 0,
            //     value: '',
            //     title: '请说明原因',
            //     area: ['800px', '350px'] //自定义文本域宽高
            // }, function(value, index){
            layer.confirm('确认设为不通过？', function (index) {
                $.ajax({
                    type: "POST",
                    dataType: "json", // 预期服务器返回的数据类型
                    data: JSON.stringify(ids), // 序列化@RequestBody
                    contentType: 'application/json;charset=UTF-8',
                    url: base_url + "onePass?sign=false", // url
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

    /*  通过，逻辑处理  */
    function getItems(obj) {
        var ids = [];
        if (obj.length == 0) {
            layer.msg("未选择对象", {
                icon: 2
            });
        } else {
            for (var i = 0; i < obj.length; i++) {
                ids[i] = obj[i].stId; // 获取主键数组
            }
            // console.log(ids);
            layer.confirm('确认通过认证？', function (index) {

                $.ajax({
                    type: "POST",
                    dataType: "json", // 预期服务器返回的数据类型
                    data: JSON.stringify(ids), // 序列化@RequestBody
                    contentType: 'application/json;charset=UTF-8',
                    url: base_url + "onePass?sign=true", // url
                    success: function (res) {
                        //console.log(res); // 请求成功后的回调函数,
                        // result 为响应内容
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
            });

        }

    }

    /**
     *
     * 监听下拉菜单，动态显示班级
     */
    form.on('select(nianji)', function (data) {
        var nian = data.value;
        var xue = $('#xueyuan').val();
        $('#student').empty();
        $('#mclass').empty();
        if (nian != 0 && xue != 0 && typeof(nian) != "undefined") {
            showclass(nian, xue);
            form.render();
        }

    });
    form.on('select(xueyuan)', function (data) {
        var xue = data.value;
        var nian = $('#nianji').val();
        $('#student').empty();
        $('#mclass').empty();
        if (nian != 0 && xue != 0 && typeof(xue) != "undefined") {
            showclass(nian, xue);
            form.render();
        }

    });
    form.on('select(mclass)', function (data) {
        var mc = data.value;
        var xue = $('#xueyuan').val();
        var nian = $('#nianji').val();

        if (nian != 0 && xue != 0 && mc != 0 && typeof(nian) != "undefined"
            && typeof(xue) != "undefined" && typeof(mc) != "undefined") {
            showstudent(nian, xue, mc);
            form.render();
        }

    });

    //显示学生
    function showstudent(nian, xue, mc) {

        $('#student').empty();
        $.ajax({
            type: "GET",                           //方法类型
            dataType: "json",                       //预期服务器返回的数据类型
            contentType: 'application/json;charset=UTF-8',
            url: "http://127.0.0.1:8080/mis/student/" + "showstudent/" + nian + "/" + xue + "/" + mc,//url
            async: false,
            success: function (result) {
                // 请求成功后的回调函数, result 为响应内容

                var proHtml = '<option value="0' + '">' + '请选择学生' + '</option>';
                var m = result.student;

                for (i in m) {
                    proHtml = proHtml + '<option value="' + m[i].stuId + '">' + m[i].username + '</option>';
                    //('#suitem').append('<option value="' + m[i].tuoId + '">' + m[i].tuoMess +'</option>');
                }
                $('#student').html(proHtml);


            },

        });

    }

    //显示班级
    function showclass(nian, xue) {

        $.ajax({
            type: "GET",                           //方法类型
            dataType: "json",                       //预期服务器返回的数据类型
            contentType: 'application/json;charset=UTF-8',
            url: "http://127.0.0.1:8080/mis/college/" + "showclass/" + nian + "/" + xue,//url
            async: false,
            success: function (result) {
                // 请求成功后的回调函数, result 为响应内容
                $('#mclass').empty();
                var proHtml = '<option value="0' + '">' + '请选择班级' + '</option>';
                var m = result.college;

                for (i in m) {
                    proHtml = proHtml + '<option value="' + m[i].uclass + '">' + m[i].uclass + '</option>';
                    //('#suitem').append('<option value="' + m[i].tuoId + '">' + m[i].tuoMess +'</option>');

                }
                $('#mclass').html(proHtml);


            },

        });

    }

    //监听学生框,获取需要认证的内容
    form.on('select(student)', function (data) {
        var stuId = data.value;
        table.reload('test', {                              //刷新，数据重载
            url: base_url + "list" + "?stuId=" + stuId,
            method: "post"
        });

    });

});