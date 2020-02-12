var base_url = "http://127.0.0.1:8080/sys/user/";
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
		title: '用户表',
		id:'test',
		height:540,
		// cellMinWidth: 100, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
		cols: [
			[
				{type: 'checkbox', fixed: 'left'}
				,{field: 'userId', title: '用户Id', width:"20%", sort: true}
				,{field: 'username', title: "用户名", width:"15%"}
                ,{field: 'deptName', title: '角色名称', width:"27%", sort: true}
                ,{field: 'status', title: '状态', width:"15%"}
                ,{field: 'createTime', title: '创建时间', width:"15%"}
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
	
	
	/* 监听弹出框，添加+修改用户 */
	form.on('submit(demo1)',function(data) {
		var user = data.field;
		saveOrUpdateData(user);
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
		var userIds=[];
		if(id.length>0){
			for(var i=0;i<id.length;i++){
				userIds[i] = id[i].userId;
			}
			layer.confirm('确认要删除吗？',function(index){
				$.ajax({
					type: "POST",
					dataType: "json",                     //预期服务器返回的数据类型
					data:JSON.stringify(userIds),
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
		var data;
		setForm(data,false);
		var title = '添加用户';
		layerOpen(title);
	}
    /* 修改用户: 把用户信息放到form表单中 ，解除只读，显示操作按钮，并打开弹出框*/
	function editData(user){
		var data = getData(user);
		if(!(typeof(data)=="undefined")){
			setForm(data,false);
			var title = '修改用户';
			layerOpen(title);
		}
	}
	
	/*  查看用户信息：设置只读，隐藏操作按钮，并打开弹出框 */
	function showData(user){
		var data = getData(user);
		console.log(data);
		if(!(typeof(data)=="undefined")){
			setForm(data,true);
			var title = '查看用户';
			layerOpen(title);
		}
	}
	/* 设置form表单的值，如果data未定义，则设空值，根据data是否定义以及readonly的真假设置隐藏元素与是否可编辑 */
	function setForm(data,readonly){
		var dataExit = (typeof(data) == "undefined");
		console.log(dataExit);
		$("#userId").attr({"value":dataExit? "" : data.userId});
		$("#userIdBox").css("display",dataExit?"none":"inline");
		$("#username").attr({"value":dataExit? "" : data.username, "readonly":readonly});
		$("#createBy").attr({"value":dataExit? "" : data.createBy, "readonly":readonly});
		$("#status").attr({"value":dataExit? 1 : data.status});
		form.val("userForm",{
			'switchTest':dataExit? true:(data.status==1)
					}
		);
		//$("#switchTest").attr({"disabled":readonly});
		$("#deptId").attr({"value":dataExit? "" : data.deptId, "readonly":readonly});
		$("#roleIds").attr({"value":dataExit? "" : data.roleIds, "readonly":readonly});
		$("#password").attr({"value":dataExit? "" : data.password, "readonly":readonly,"type":readonly?"text":"password"});
		$("#opration").attr({"hidden":readonly});
	}
	/* 根据user.length判断是否未选择或选择多个对象，获取userId并传到后端同步获取user的所有属性， */
	function getData(user){
		var result ;
		if(user.length==0){
			layer.msg("未选择对象",{icon:2});
		}else if(user.length > 1){
			layer.msg("不可选多个对象",{icon:2});
		}else{
			var userId=user[0].userId;
			$.ajax({
				type: "get",
				dataType: "json",                     //预期服务器返回的数据类型
				contentType: 'application/json;charset=UTF-8',
				url: base_url+"info/"+userId ,              //url
				async:false,                            //同步提交，只有执行完ajax 后才可进行后面的代码操作，否则下面对result赋值无效
				success: function (res) {
					console.log(res);              // 请求成功后的回调函数, result 为响应内容
					if (res.code==0) {
						result = res.user;
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
				content: $("#add-main"),
				resize:false,
				success:function(layero, index){
						laydate.render({
						elem: '#createTime'
						,type: 'datetime'
						});
				}
			});
			
	}
	
	/* 异步提交数据添加或修改用户 */
	function saveOrUpdateData(user){
		console.log(user);
		if(user.userId==null || user.userId==""){        //若Id为空,则添加，否则为修改
			var msg = '是否添加用户 '+user.username+'?';
			var url = base_url+"save";
		}
		else{
			var msg = '是否修改用户 '+user.username+'?';
			var url = base_url+"update";
		}
		layer.confirm(msg,function(index){
			$.ajax({
				type: "POST",                           //方法类型
				dataType: "json",                       //预期服务器返回的数据类型
				data:JSON.stringify(user),
				contentType: 'application/json;charset=UTF-8',
				url: url ,//url
				success: function (result) {
					console.log(result);                // 请求成功后的回调函数, result 为响应内容
					if(result.code==0)
						layer.alert("操作成功", {
								icon: 6
							},function(){
									location.reload();
							});
					else if(result.code==500){
						this.error(result);
					}
				},
				error : function(error) {
						var msg="不明异常";
						if(error.msg!=null)
							msg=error.msg;
						layer.alert(msg, {
							icon: 7
						});
						console.log(error);            // 请求失败时的回调函数
					}
			});
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

});

