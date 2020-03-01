
var base_url = "http://127.0.0.1:8080/sys/dict/";
layui.use(['table', 'layer','form','laydate'], function() {

	var table = layui.table;
	var layer = layui.layer;
	var $ = layui.$;
	var form = layui.form;
    var laydate = layui.laydate;
	/* 渲染表格 */
	table.render({
		elem: '#test',
		url: base_url+'list',  //跨域url
		method:'post',
		toolbar: '#toolbarDemo',
		title: '字典表',
		id:'test',
		height:540,
		// cellMinWidth: 100, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
		cols: [
			[
				{type: 'checkbox', fixed: 'left'}
				,{field: 'id', title: 'ID主键', width:"16%", sort: true}
				,{field: 'grade', title: "当前年度"}
                ,{field: 'term', title: '学期'}
            ]

		],
		page: true,
		response: {
			//statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0
		},
		parseData: function(res) { //将原始数据解析成 table 组件所规定的数据
			return {
				"code": res.code, //解析接口状态
				"msg": res.msg, //解析提示文本
				"count":typeof(res.page)=='undefined'?"":res.page.totalCount, //解析数据长度
				"data": typeof(res.page)=='undefined'?"":res.page.list //解析数据列表
			};
		},
		done : function(res, curr, count){
				if(res.code!=0){
					layer.alert(res.msg, {
						icon: 7
					});
					$(".layui-table-main").html('<div class="layui-none">暂无数据</div>');
				}
			}
	});

	/* 监听头工具栏事件 */
	table.on('toolbar(test)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id);
		var data = checkStatus.data;
		console.log(data);
		switch (obj.event) {

			case 'edit' : 
				editData(data);									   //编辑
				break;
			case 'flush':
				table.reload('test', {                              //刷新，数据重载
					url: base_url+"list",
					method: "post"
				});
				break;
		};
	});
	

    /* 修改用户: 把用户信息放到form表单中 ，解除只读，显示操作按钮，并打开弹出框*/
	function editData(user){
		var data = getData(user);
		if(!(typeof(data)=="undefined")){

            $("#userForm :input").not(":button, :submit, :reset")
                .val("").removeAttr("checked").remove("selected")
                .prop("readonly", false); // 核心
            // 显示
            $('#userForm').find('#submit,#reset').show();
            //给表单赋值，通用方法 ，userForm
            form.val("userForm", data);
			var title = '修改用户';

            layer.open({
                title: title,
                type: 1,
                btnAlign: 'c',
                area:'700px',
                shadeClose: true,
                content: $("#myform1"),
                resize:false
            });
		}
	}
	

	/* 根据user.length判断是否未选择或选择多个对象，获取stuId并传到后端同步获取Student的所有属性， */
	function getData(user){
		var result ;
		if(user.length==0){
			layer.msg("未选择对象",{icon:2});
		}else if(user.length > 1){
			layer.msg("不可选多个对象",{icon:2});
		}else{
			var id=user[0].id;
			$.ajax({
				type: "get",
				dataType: "json",                     //预期服务器返回的数据类型
				contentType: 'application/json;charset=UTF-8',
				url: base_url+"info/"+id ,              //url
				async:false,                            //同步提交，只有执行完ajax 后才可进行后面的代码操作，否则下面对result赋值无效
				success: function (res) {
					console.log(res);              // 请求成功后的回调函数, result 为响应内容
					if (res.code==0) {
						result = res.entity;
						console.log(result);
						return;
					}
					else
						this.error(res);
				},
				error: function (error) {
					var msg="不明异常";
					if(error.msg!=null)
						msg=error.msg;
					layer.alert(msg, {
						icon: 7
					});
					console.log(error);                   // 请求失败时的回调函数
				}
			});
		}
		console.log(result);
		return result;
	}
	
	



    /**
	 * 修改用户，
	 */
    form.on('submit(save)', function(data){

        layer.msg(JSON.stringify(data.field));
        layer.confirm('确认要修改吗？',function(index){
            $.ajax({
                type: "POST",
                dataType: "json",                     //预期服务器返回的数据类型
                data:JSON.stringify(data.field),
                contentType: 'application/json;charset=UTF-8',
                url: base_url+"update" ,              //url
                success: function (result) {
                    console.log(result);              // 请求成功后的回调函数, result 为响应内容
                    if (result.code==0) {

                        layer.alert("修改成功", {
                            icon: 6
                        }, function(index) {
                            table.reload("test"); // 重载表格
                            layer.closeAll(); // layer.closeAll();
                        });
                    }
                    else
                        this.error(result);
                },
                error: function (error) {
                    var msg="不明异常";
                    if(error.msg!=null)
                        msg=error.msg;
                    layer.alert(msg, {
                        icon: 7
                    });
                    console.log(error);                   // 请求失败时的回调函数
                }
            });
        });


        return false;
    });


});

