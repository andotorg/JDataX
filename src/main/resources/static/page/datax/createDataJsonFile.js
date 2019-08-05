layui.use(['form','layer','laydate','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;

    //数据表
    var tableIns = table.render({
        elem: '#tableList',
        url : '/rest/jdatax/readDataBase/showTables',
        where: {
		  dbName: "pms",
		  dbUser: "root",
		  dbPwd: "andot",
		  dbUrl: "jdbc:mysql://192.168.10.216:3306/pms?useUnicode=true&characterEncoding=UTF-8",
		  dbType: 0,
		  dbClass: "com.mysql.jdbc.Driver"
	    },
	    request: {
    	  pageName: 'flastRow' //页码的参数名称，默认：page
    	  ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
    	},
	    response: {
    	  statusName: 'flag' //数据状态的字段名称，默认：code
    	  ,statusCode: 1 //成功的状态码，默认：0
    	  ,msgName: 'msg' //状态信息的字段名称，默认：msg
    	  ,countName: 'size' //数据总数的字段名称，默认：count
    	  ,dataName: 'data' //数据列表的字段名称，默认：data
    	},
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limit : 10,
        limits : [10, 30, 50, 100],
        id : "tableList",
        cols : [[
            {field: 'name', title: '名称', align:"center"},
            {field: 'type', title: '类型', align:"center"},
            {title: '操作', width:170, templet:'#tableListBar',fixed:"right",align:"center"}
        ]]
    });
    
    $(".loadTable_Btn").click(function(){
    	layui.layer.open({
		  type: 1,
		  skin: 'layui-layer-rim', //加上边框
		  area: ['420px', '420px'], //宽高
		  content:  $("#dbInfoDialog"),
		  btn: ['创建JSON文件', '取消'],
		  yes: function(){
			  var idx = layer.load(0, {
		    		skin: 'i-loading',
		    		shade: 0.1,
		    		content: '<div class="sk-folding-cube"> <div class="sk-cube1 sk-cube"></div> <div class="sk-cube2 sk-cube"></div><div class="sk-cube4 sk-cube"></div> <div class="sk-cube3 sk-cube"></div> </div>'
		    	});
			  let type = $("#dbType").val();
			  let className, dbUrl;
			  switch(Number(type)){
				  case 0:
					  className = "com.mysql.jdbc.Driver";
					  dbUrl = "jdbc:mysql://"+$("#dbUrl").val()+"/"+$("#dbName").val()+"?useUnicode=true&characterEncoding=UTF-8";
					  break;
				  case 1:
					  className = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
					  dbUrl = "jdbc:sqlserver://"+$("#dbUrl").val()+";DatabaseName="+$("#dbName").val()+";";
					  break;
				  case 2:
					  className = "";
					  dbUrl = "jdbc:sqlserver://"+$("#dbUrl").val()+";DatabaseName="+$("#dbName").val()+";";
					  break;
			  }
			  let data = {
				  dbName: $("#dbName").val(),
				  dbUser: $("#dbUser").val(),
				  dbPwd: $("#dbPwd").val(),
				  dbUrl: dbUrl,
				  dbType: $("#dbType").val(),
				  dbClass: className,
				  filePath: $("#filePath").val()
			  }
			  $.ajax({
			      type: "GET",
			      async : true,
			      url: "/createDataXJsonFile",
			      cache: false,  //禁用缓存
			      data: data,
			      dataType: "json",
			      success: function (result) {
			    	  layer.close(idx);
			      },
			      error:function(){
			    	  layer.close(idx);
			      }
		  	  });
	      },
	      btn2: function(){
	          layer.closeAll();
	      },
	      zIndex: layer.zIndex, //重点1
	      success: function(layero){
	    	  
	      }
		});
    })

	/***
	 * Websocket 实现实时回传控制台日志
	 *
	 */
	let websocket = null;
	// 判断当前浏览器是否支持WebSocket
	if ('WebSocket' in window) {
		websocket = new WebSocket("ws://127.0.0.1:8080/webSocket");
	} else {
		alert('当前浏览器不支持WebSocket')
	}

	// 连接发生错误的回调方法
	websocket.onerror = function () {
		setMessageInnerHTML("WebSocket连接发生错误");
	};

	// 连接成功建立的回调方法
	websocket.onopen = function () {
		//setMessageInnerHTML("WebSocket连接成功");
	}

	// 接收到消息的回调方法
	websocket.onmessage = function (event) {
		setMessageInnerHTML(event.data);
	}

	// 连接关闭的回调方法
	websocket.onclose = function () {
		setMessageInnerHTML("WebSocket连接关闭");
	}

	// 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
	window.onbeforeunload = function () {
		closeWebSocket();
	}

	// 将消息显示在网页上
	function setMessageInnerHTML(text) {
		let obj = document.getElementById("message");
		obj.value += text + "\n";
		obj.scrollTop = obj.scrollHeight;
	}

	// 关闭WebSocket连接
	function closeWebSocket() {
		websocket.close();
	}

    $(".datax_Btn").click(function(){
    	layui.layer.open({
  		  type: 1,
  		  skin: 'layui-layer-rim', //加上边框
  		  area: ['420px', '420px'], //宽高
  		  content:  $("#dataDialog"),
  		  btn: ['同步数据', '取消'],
  		  yes: function(){
  			var idx = layer.load(0, {
	    		skin: 'i-loading',
	    		shade: 0.1,
	    		content: '<div class="sk-folding-cube"> <div class="sk-cube1 sk-cube"></div> <div class="sk-cube2 sk-cube"></div><div class="sk-cube4 sk-cube"></div> <div class="sk-cube3 sk-cube"></div> </div>'
	    	});
	  		 $.ajax({
	  		      type: "GET",
	  		      async : true,
	  		      url: "/isDataxJson",
	  		      cache: false,  //禁用缓存
	  		      data: {
	  		    	  jsonPath: $("#jsonPath").val()
	  		      },
	  		      dataType: "json",
	  		      success: function (result) {
	  		    	  if(result.data === true){
	  		    		$.ajax({
			  	  		      type: "GET",
			  	  		      async : true,
			  	  		      url: "/dataxStart",
			  	  		      cache: false,  //禁用缓存
			  	  		      data: {
			  	  		    	  jsonPath: $("#jsonPath").val(),
			  	  		    	  pyPath: $("#pythonPath").val(),
			  	  		          dataxPath: $("#dataxPath").val() 
			  	  		      },
			  	  		      dataType: "json",
			  	  		      success: function (result) {
			  	  		    	layui.layer.msg("同步成功"+result.data);
			  	  		        layer.close(idx);
			  	  		      },
			  	  		      error:function(){
			  	  		    	layui.layer.msg("同步异常"+result.data);
			  	  		        layer.close(idx);
			  	  		      }
			  	  	  	  });
	  		    	  }else{
	  		    		layui.layer.msg("请先生成datax json同步配置文件，点击添加数据源生成。");
	  		    	  }
	  		      },
	  		      error:function(){
	  		    	layui.layer.msg("异常发生，参数必填，请检查在执行。");
	  		      }
	  	  	  });
  		  },
		  btn2: function () {
			  closeWebSocket();
		  },
    	});
    	
    });
    
    function ajaxPost(url, data){
    	$.ajax({
	      type: "POST",
	      async : true,
	      url: url,
	      cache: false,  //禁用缓存
	      data: data,
	      dataType: "json",
	      success: function (result) {
	    	  console.log(result.data);
	      },
	      error:function(){
	      		
	      }
	  	});
    }
    
    //列表操作
    table.on('tool(tableList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'look'){ //预览
        	layui.layer.open({
      		  type: 1,
      		  skin: 'layui-layer-rim', //加上边框
      		  area: ['420px', '380px'], //宽高
      		  content:  "<div id='clist' style='padding-top:20px; padding-left:50px; overflow-y: auto; height: 250px;'></div>",
      		  btn: ['关闭'],
      		  yes: function(){
      			layui.layer.closeAll();
      		  }
        	});
        	let type = $("#dbType").val();
		  let className, dbUrl;
		  switch(Number(type)){
			  case 0:
				  className = "com.mysql.jdbc.Driver";
				  dbUrl = "jdbc:mysql://"+$("#dbUrl").val()+"/"+$("#dbName").val()+"?useUnicode=true&characterEncoding=UTF-8";
				  break;
			  case 1:
				  className = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
				  dbUrl = "jdbc:sqlserver://"+$("#dbUrl").val()+";DatabaseName="+$("#dbName").val()+";";
				  break;
			  case 2:
				  className = "";
				  dbUrl = "jdbc:sqlserver://"+$("#dbUrl").val()+";DatabaseName="+$("#dbName").val()+";";
				  break;
		  }
		  let data = {
			  dbName: $("#dbName").val(),
			  dbUser: $("#dbUser").val(),
			  dbPwd: $("#dbPwd").val(),
			  dbUrl: dbUrl,
			  dbType: $("#dbType").val(),
			  dbClass: className,
			  filePath: $("#filePath").val()
		  }
        	$.ajax({
      	      type: "POST",
      	      async : true,
      	      url: '/rest/jdatax/readDataBase/showColumn',
      	      cache: false,  //禁用缓存
      	      data: data,
      	      dataType: "json",
      	      success: function (result) {
      	    	 for (var i = 0; i < result.data.length; i++) {
					$("#clist").append("<span style='width: 100%; height: 30px; display: block; text-align: left;'>"+(i+1)+"、 "+result.data[i]+"</span>");
				 }
      	      },
      	      error:function(){
      	      		
      	      }
      	  	});
        }
    });

})