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
        //addNews();
    	layui.layer.open({
		  type: 1,
		  skin: 'layui-layer-rim', //加上边框
		  area: ['420px', '380px'], //宽高
		  content:  $("#dbInfoDialog"),
		  btn: ['加载数据', '取消'],
		  yes: function(){
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
				  dbClass: className
			  }
			  //ajaxPost("/rest/jdatax/readDataBase/showTables", data);
			  //执行重载
		      table.reload('tableList', {
		        page: {
		          curr: 1 //重新从第 1 页开始
		        },
		        where: data
		      });
			  layui.layer.closeAll();
	      },
	      btn2: function(){
	          layer.closeAll();
	      },
	      zIndex: layer.zIndex, //重点1
	      success: function(layero){
	    	  
	      }
		});
    })
    
    function ajaxPost(url, data){
    	$.ajax({
	      type: "POST",
	      async : true,
	      url: url,
	      cache: false,  //禁用缓存
	      data: data,
	      dataType: "json",
	      success: function (result) {
	     		console.log(result);
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
				  dbClass: className
			  }
        	$.ajax({
      	      type: "POST",
      	      async : true,
      	      url: '/rest/jdatax/readDataBase/showColumn',
      	      cache: false,  //禁用缓存
      	      data: {
        		  dbName: "pms",
        		  dbUser: "root",
        		  dbPwd: "andot",
        		  dbUrl: "jdbc:mysql://192.168.10.216:3306/pms?useUnicode=true&characterEncoding=UTF-8",
        		  dbType: 0,
        		  dbClass: "com.mysql.jdbc.Driver",
        		  tableName: obj.data.name
    	      },
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