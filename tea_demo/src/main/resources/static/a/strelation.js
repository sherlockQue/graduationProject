
var base_url = "http://127.0.0.1:8080/mis/strelation/";
layui.use(['table', 'upload','layer','form','laydate'], function() {

	var table = layui.table;
	var layer = layui.layer;
	var $ = layui.$;
	var form = layui.form;
    var laydate = layui.laydate;
    var upload = layui.upload;


	/* 渲染表格 */
	table.render({
		elem: '#test',
		url: base_url+'list',  //跨域url
		method:'post',
		toolbar: '#toolbarDemo',
		title: '素拓申请项表',
		id:'test',
		height:540,
		// cellMinWidth: 100, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
		cols: [
			[
				{type: 'checkbox', fixed: 'left'}
                ,{field: 'stId', title: 'ID', width:"8%", sort: true}
				,{field: 'tuoId', title: '项目编号', width:"12%", sort: true}
				,{field: 'stMessage', title: "内容", width:"20%"}
                ,{field: 'stActime', title: '活动时间', width:"12%" }
                , {field: 'stImg', title: '图片', width: "14%",templet:'#imgTpl'}
                //,{field: 'stCheckone', title: '系负责人', width:"8%"}
                ,{field: 'stOneStatus', title: '系审核状态', width:"12%"}
               // ,{field: 'stOneTime', title: '系认证时间', width:"15%"}
               // ,{field: 'stChecktwo', title: '院负责人', width:"8%"}
                ,{field: 'stTwoStatus', title: '院审核状态', width:"12%"}
                //,{field: 'stTwoTime', title: '院认证时间', width:"15%"}
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

            // layer.photos({//点击图片弹出
            //     photos: '#Img-demo'
            //     ,shift: 2 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
            // });

            $("[data-field='stOneStatus']").children().each(function(){
                if($(this).text()=='2'){
                    $(this).text("未通过");
                    $(this).css("color", "red");
                }else if($(this).text()=='1'){
                    $(this).text("已通过");
                    $(this).css("color", "green");
                }else if($(this).text()=='0'){
                    $(this).text("待审核");
                }else{}
            });
            $("[data-field='stTwoStatus']").children().each(function(){
                if($(this).text()=='2'){
                    $(this).text("未通过");
                    $(this).css("color", "red");
                   // $(this).style.color='#7CFC00';
                }else if($(this).text()=='1'){
                    $(this).text("已通过");
                    $(this).css("color", "green");
                }else if($(this).text()=='0'){
                    $(this).text("待审核");
                }else{}
            });

			}
	});

    layer.photos({//点击图片弹出
        photos: '.layer-photos-demo'
        ,shift: 1 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
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
	
	/* 验证所有表单各项据，数根据lay-verify监听 */
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
	// form.on('submit(demo1)',function(data) {
	// 	var user = data.field;
	// 	saveOrUpdateData(user);
	// });
	//
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
	function member_del(obj){
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

    }
	

	/* 添加素拓项 */
	function addData(){
		 var title = '添加素拓项';
		layerOpen(title);
	}

    /* 修改项目,只有两个*/
	function editData(user){
        // 从后台拿回全部值
        var data = getData(user);

        if (!(typeof(data) == "undefined")) {

            // 初始化工作
            // 清空数据
            $("#userForm1 :input").not(":button, :submit, :reset")
                .val("").removeAttr("checked").remove("selected")
                .prop("readonly", false); // 核心

            // 显示
            $('#userForm1').find('#submit,#reset').show();

             //给表单赋值，通用方法 ，userForm
            form.val("userForm1", data);


            // 打开弹框
            layer.open({
                title: "编辑",
                type: 1,
                content: $("#myform1")
            });
        }
	}
	
	/*  查看用户信息：设置只读，隐藏操作按钮，并打开弹出框 */
	// function showData(user){
	// 	var data = getData(user);
	// 	console.log(data);
	// 	if(!(typeof(data)=="undefined")){
	// 		setForm(data,true);
	// 		var title = '查看用户';
	// 		layerOpen(title);
	// 	}
	// }
	//


	/* 设置form表单的值，如果data未定义，则设空值，根据data是否定义以及readonly的真假设置隐藏元素与是否可编辑 */
	function setForm(data,readonly){
		var dataExit = (typeof(data) == "undefined");
		console.log(dataExit);
		$("#stuId").attr({"value":dataExit? "" : data.stuId});
		$("#stMessage").attr({"value":dataExit? "" : data.stMessage});
		$("#stActime").attr({"value":dataExit? "" : data.stActime});
		$("#stCheckone").attr({"value":dataExit? "" : data.stCheckone});
        $("#stOneStatus").attr({"value":dataExit? "" : data.stOneStatus});
        $("#stChecktwo").attr({"value":dataExit? "" : data.stChecktwo});
        $("#stTwoStatus").attr({"value":dataExit? "" : data.stTwoStatus});

	}
	/* 根据user.length判断是否未选择或选择多个对象，获取userId并传到后端同步获取user的所有属性， */
	function getData(user){
		var result ;
		if(user.length==0){
			layer.msg("未选择对象",{icon:2});
		}else if(user.length > 1){
			layer.msg("不可选多个对象",{icon:2});
		}else{
			var stId=user[0].stId;
			$.ajax({
				type: "get",
				dataType: "json",                     //预期服务器返回的数据类型
				contentType: 'application/json;charset=UTF-8',
				url: base_url+"info/"+stId ,              //url
				async:false,                            //同步提交，只有执行完ajax 后才可进行后面的代码操作，否则下面对result赋值无效
				success: function (res) {
					console.log(res);              // 请求成功后的回调函数, result 为响应内容
					if (res.code==0) {
						result = res.strelation;
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
				area: ['900px', '600px'],
                btnAlign: 'c',
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
	
	/* 修改项目 */
        form.on('submit(updateData)', function(data){

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
                                layer.close(index); // layer.closeAll();
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


/*下拉目录*/
	form.on('select(mulu)', function(data){
		// console.log(data.elem); //得到select原始DOM对象
		// console.log(data.value); //得到被选中的值
		// console.log(data.othis); //得到美化后的DOM对象
		var tuoId=data.value;
        $('#tuoId').empty();

        $.ajax({
            type: "GET",                           //方法类型
            dataType: "json",                       //预期服务器返回的数据类型
            contentType: 'application/json;charset=UTF-8',
            url: "http://127.0.0.1:8080/mis/tuoproject/"+"infoMenu/"+tuoId ,//url
            success: function (result) {
            	// 请求成功后的回调函数, result 为响应内容
                    var proHtml = '';
					var m=result.tuoproject;

                for(i in m){
                    proHtml =proHtml+ '<option value="' + m[i].tuoId + '">' + m[i].tuoMess +'</option>';
                    //('#suitem').append('<option value="' + m[i].tuoId + '">' + m[i].tuoMess +'</option>');
                }
                $('#tuoId').html(proHtml);
                form.render();

            },

        });

	});

	//图片上传
    var uploadInst = upload.render({
        elem: '#headImg'
        , url: base_url+'uploadImg'
        , size: 1024*2
		, accept: 'images'
        , before: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#demo1').attr('src', result); //图片链接（base64）
            });
        }
        // , choose: function (obj) {
        //     //预读本地文件
        //     obj.preview(function (index, file, result) {
        //        // $('#fileName').val(file.name);  //展示文件名
		//
        //        $('#demo1').attr('src', result);
        //       // $('#demo1').attr('alt', file.name);
        //         //var img = document.getElementsByTagName('#demo1');
        //         //img.src = result+"?time=" + new Date().getTime();
        //     })
        // }
        , done: function (res) {
            //如果上传失败
            if (res.code > 0) {
                return layer.msg('上传失败');
            }
            //上传成功
            //打印后台传回的地址: 把地址放入一个隐藏的input中, 和表单一起提交到后台, 此处略..
            /*   console.log(res.data.src);*/

                document.getElementById("stImg").value = res.filepath;
				var demoText = $('#demoText');
				demoText.html('<span style="color: #7CFC00;">上传成功!!!</span>');

        }
        , error: function () {
            //演示失败状态，并实现重传
            var demoText = $('#demoText');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function () {
                uploadInst.upload();
            });
        }
    });
   // element.init();

    //添加新项目
    form.on('submit(formAdd)', function(data){

        layer.msg(JSON.stringify(data.field));
        layer.confirm('确认要添加吗？',function(index){
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
                            layer.close(index); // layer.closeAll();
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