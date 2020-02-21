// 参考 https://blog.csdn.net/qq_33769914/article/details/93048353
// var base_url = "http://192.168.1.97:8080/mis/tbuser/";
// var base_url = "http://127.0.0.1:8080/sys/user/";
var base_url = "http://127.0.0.1:8080/mis/student/";
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
		title: '学生用户表',
		id:'test',
		height:540,
		// cellMinWidth: 100, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
		cols: [
			[
				{type: 'checkbox', fixed: 'left'}
				,{field: 'stuId', title: '学号', width:"16%", sort: true}
				,{field: 'username', title: "姓名", width:"12%"}
                ,{field: 'stuGrade', title: '年级', width:"10%", sort: true}
                ,{field: 'stuCollege', title: '学院', width:"14%"}
                ,{field: 'stuMajor', title: '专业', width:"14%"}
                ,{field: 'stuClass', title: '班级', width:"18%", sort: true}
                ,{field: 'stuPhone', title: '电话', width:"14%"}
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
			case 'add':
				addData();                                        //添加
				break;
			case 'edit' : 
				editData(data);									   //编辑
				break;            
			case 'detail': 
				showData(data);                                    //查看
				break;           
			case 'batchDel':
				/*发送ajax请求到后台执行批量删除*/
				member_del(data);                                  //删除
				break;
			case 'flush':
				table.reload('test', {                              //刷新，数据重载
					url: base_url+"list",
					method: "post"
				});
				break;
		};
	});
	
	/* 验证所有表单各项数据，根据lay-verify监听 */
	form.verify({
		username: function(value) {
		    if (value==null || value == "") {
		        return '用户名不能为空';
		    }
		},
		password:function (value) {
		    if (value==null|| value == "") {
		        return '密码不能为空';
		    }
		}
	});
	
	

	
	/* 监听查询工具栏，根据条件查找数据，条件可多个 */
	form.on('submit(search)',function(data){
		searchData(data);
	});
	
	//监听状态开关
    form.on('switch(switchTest)', function(data){
		console.log(data);
        var status = this.checked ? 1 : 0;
        $("#status").attr({"value":status});
    });
	
	
	/* ------------增删改查----------------- */
	/*删除：判断参数Id是否为数组，若不是数组则添加到长度为1的数组上，若为数组则获取各项数组的Id*/
	function member_del(id,obj){
		var ids=[];
		if(id.length>0){
			for(var i=0;i<id.length;i++){
                ids[i] = id[i].stuId;
			}
			layer.confirm('确认要删除吗？',function(index){
				$.ajax({
					type: "POST",
					dataType: "json",                     //预期服务器返回的数据类型
					data:JSON.stringify(ids),
					contentType: 'application/json;charset=UTF-8',
					url: base_url+"delete" ,              //url
					success: function (result) {
						console.log(result);              // 请求成功后的回调函数, result 为响应内容
						if (result.code==0) {
							layer.msg('删除成功', {icon: 1});
							setTimeout(function () {
									if(obj!=null)
										obj.del();                //删除对应行（tr）的DOM结构，并更新缓存
									else
										location.reload();
							},1000)
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
		}else{
			layer.msg('未选择对象',{icon:2});
		}
	}
	

	/* 添加用户：清空form表单数据，解除只读，显示操作按钮，并打开弹出框 */
	function addData(){

        $("#userForm :input").not(":button, :submit, :reset")
            .val("").removeAttr("checked").remove("selected")
            .prop("readonly", false); // 核心
		var title = '添加用户';
		layerOpen(title);
	}

    /* 修改用户: 把用户信息放到form表单中 ，解除只读，显示操作按钮，并打开弹出框*/
	function editData(user){
		var data = getData(user);
		if(!(typeof(data)=="undefined")){

            $("#userForm2 :input").not(":button, :submit, :reset")
                .val("").removeAttr("checked").remove("selected")
                .prop("readonly", false); // 核心
            // 显示
            $('#userForm2').find('#submit2,#reset2').show();
            //给表单赋值，通用方法 ，userForm
            form.val("userForm2", data);
			var title = '修改用户';

            layer.open({
                title: title,
                type: 1,
                btnAlign: 'c',
                area:'700px',
                shadeClose: true,
                content: $("#myform2"),
                resize:false
            });
		}
	}
	
	/*  查看用户信息：设置只读，隐藏操作按钮，并打开弹出框 */
	function showData(user){
		var data = getData(user);
		console.log(data);
		if(!(typeof(data)=="undefined")){
            form.val("userForm2", data);
            $('#userForm2').find('input,textarea,select').prop('readonly', true);
            $('#userForm2').find('#submit2,#reset2').hide();   //隐藏
            var title = '修改用户';
            layer.open({
                title: title,
                type: 1,
                btnAlign: 'c',
                area:'700px',
                shadeClose: true,
                content: $("#myform2"),
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
			var stuId=user[0].stuId;
			$.ajax({
				type: "get",
				dataType: "json",                     //预期服务器返回的数据类型
				contentType: 'application/json;charset=UTF-8',
				url: base_url+"info/"+stuId ,              //url
				async:false,                            //同步提交，只有执行完ajax 后才可进行后面的代码操作，否则下面对result赋值无效
				success: function (res) {
					console.log(res);              // 请求成功后的回调函数, result 为响应内容
					if (res.code==0) {
						result = res.student;
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
	
	
	/* 打开弹出框 */
	function layerOpen(title){
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

	/* 查找用户 */
	function searchData(data){
		var name = data.field.name;           //条件1
		table.reload('test', {
			where: {
				name: name
			},
			page: {
				curr: 1
			}
		});
	}

    /**
	 * 监听添加用户,id: myform1
     */
    form.on('submit(save)', function(data) {

        layer.confirm('确认要添加吗？',function(){
            $.ajax({
                type: "POST",
                dataType: "json",                     //预期服务器返回的数据类型
                data:JSON.stringify(data.field),
                contentType: 'application/json;charset=UTF-8',
                url: base_url+"save" ,              //url
                success: function (result) {
                    console.log(result);              // 请求成功后的回调函数, result 为响应内容
                    if (result.code==0) {
                        //layer.msg('添加成功', {icon: 1});
                        layer.alert("添加成功", {
                            icon: 1
                        }, function(index) {
                            table.reload("test"); // 重载表格
                            layer.closeAll();  // layer.closeAll();
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




    });

    /**
	 * 修改用户， 监听 id：myform2
	 */
    form.on('submit(updateUser)', function(data){

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

